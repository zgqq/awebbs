package awebbs.util.identicon;

import awebbs.util.HashUtil;
import awebbs.util.StrUtil;
import awebbs.util.identicon.generator.IBaseGenartor;
import awebbs.common.config.SiteConfig;
import awebbs.util.identicon.generator.impl.MyGenerator;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author: Bryant Hang
 * Date: 15/1/10
 * Time: 下午2:42
 */
@Component
public class Identicon {

    @Autowired
    private SiteConfig siteConfig;

    private IBaseGenartor genartor;

    public Identicon() {
        this.genartor = new MyGenerator();
    }

    public BufferedImage create(String hash, int size) {
        Preconditions.checkArgument(size > 0 && !StringUtils.isEmpty(hash));

        boolean[][] array = genartor.getBooleanValueArray(hash);

//        int ratio = DoubleMath.roundToInt(size / 5.0, RoundingMode.HALF_UP);
        int ratio = size / 6;

        BufferedImage identicon = new BufferedImage(ratio * 6, ratio * 6, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = identicon.getGraphics();

        graphics.setColor(genartor.getBackgroundColor()); // 背景色
        graphics.fillRect(0, 0, identicon.getWidth(), identicon.getHeight());

        graphics.setColor(genartor.getForegroundColor()); // 图案前景色
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (array[i][j]) {
                    graphics.fillRect(j * ratio + 35, i * ratio + 35, ratio, ratio);
                }
            }
        }

        return identicon;
    }

    public void generator(String fileName) {
        Identicon identicon = new Identicon();

        String md5 = HashUtil.md5(StrUtil.randomString(6));

        BufferedImage image = identicon.create(md5, 420);

        try {
            File file = new File(siteConfig.getUploadPath() + "avatar/" + fileName + ".png");
            if (!file.exists()) file.createNewFile();
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
