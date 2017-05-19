package awebbs.module.index.controller;

import awebbs.common.BaseController;
import awebbs.module.topic.service.TopicService;
import awebbs.module.user.entity.User;
import awebbs.module.user.service.UserService;
import awebbs.util.FileUploadEnum;
import awebbs.util.identicon.Identicon;
import awebbs.common.config.SiteConfig;
import awebbs.exception.Result;
import awebbs.module.topic.entity.Topic;
import awebbs.util.FileUtil;
import awebbs.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zgqq.
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private Identicon identicon;
    @Autowired
    private FileUtil fileUtil;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(String tab, Integer p, Model model) {
        String sectionName = tab;
        if (StringUtils.isEmpty(tab)) tab = "全部";
        if (tab.equals("全部") || tab.equals("精华") || tab.equals("等待回复")) {
            sectionName = "版块";
        }
        PageWrapper<Topic> page = new PageWrapper<>(topicService.page(p == null ? 1 : p, siteConfig.getPageSize(), tab), "/");
        model.addAttribute("page", page);
        model.addAttribute("tab", tab);
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("user", getUser());
        return render("/index");
    }

    /**
     * 搜索
     * @param p
     * @param q
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String search(Integer p, String q, Model model) {
        Page<Topic> page = topicService.search(p == null ? 1 : p, siteConfig.getPageSize(), q);
        model.addAttribute("page", page);
        model.addAttribute("q", q);
        return render("/search");
    }

    /**
     * 进入登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String toLogin(String s, Model model, HttpServletResponse response) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        model.addAttribute("s", s);
        return render("/login");
    }

    /**
     * 进入注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String toRegister(HttpServletResponse response) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        return render("/register");
    }

    /**
     * 注册验证
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public String register(String username, String password, HttpServletResponse response, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("errors", "用户名已经被注册");
        } else if (StringUtils.isEmpty(username)) {
            model.addAttribute("errors", "用户名不能为空");
        } else if (StringUtils.isEmpty(password)) {
            model.addAttribute("errors", "密码不能为空");
        } else {
            Date now = new Date();
            String avatarName = UUID.randomUUID().toString();
            identicon.generator(avatarName);
            user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setInTime(now);
            user.setBlock(false);
            user.setToken(UUID.randomUUID().toString());
            user.setAvatar(siteConfig.getStaticUrl() + "static/images/upload/avatar/" + avatarName + ".png");
            userService.save(user);
            return redirect(response, "/login?s=reg");
        }
        return render("/register");
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String requestUrl = fileUtil.uploadFile(file, FileUploadEnum.FILE);
                return Result.success(requestUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error("上传失败");
            }
        }
        return Result.error("文件不存在");
    }

    /**
     * wangEditor上传
     *
     * @param file
     * @return
     */
    @PostMapping("/wangEditorUpload")
    @ResponseBody
    public String wangEditorUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                return fileUtil.uploadFile(file, FileUploadEnum.FILE);
            } catch (IOException e) {
                e.printStackTrace();
                return "error|服务器端错误";
            }
        }
        return "error|文件不存在";
    }

    /**
     * 关于
     * @param model
     * @return
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("page_tab", "about");
        return render("/about");
    }

}
