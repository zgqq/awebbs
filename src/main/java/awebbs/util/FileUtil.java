package awebbs.util;

import awebbs.common.config.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by zgqq.
 */
@Component
public class FileUtil {

    @Autowired
    private SiteConfig siteConfig;

    /**
     * 上传文件
     *
     * @param file
     * @param fileUploadEnum
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file, FileUploadEnum fileUploadEnum) throws IOException {
        if (!file.isEmpty()) {
            String type = file.getContentType();
            String suffix = "." + type.split("/")[1];
            String fileName = UUID.randomUUID().toString() + suffix;
            BufferedOutputStream stream = null;
            String requestPath = null;
            if (fileUploadEnum == FileUploadEnum.FILE) {
                stream = new BufferedOutputStream(new FileOutputStream(new File(siteConfig.getUploadPath() + fileName)));
                requestPath = siteConfig.getStaticUrl();
            } else if (fileUploadEnum == FileUploadEnum.AVATAR) {
                stream = new BufferedOutputStream(new FileOutputStream(new File(siteConfig.getUploadPath() + "avatar/" + fileName)));
                requestPath = siteConfig.getStaticUrl() + "avatar/";
            }
            if (stream != null) {
                stream.write(file.getBytes());
                stream.close();
                return requestPath + fileName;
            }
        }
        return null;
    }
}
