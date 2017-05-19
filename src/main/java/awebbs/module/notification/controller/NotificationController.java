package awebbs.module.notification.controller;

import awebbs.common.BaseController;
import awebbs.common.config.SiteConfig;
import awebbs.exception.ApiException;
import awebbs.exception.ErrorCode;
import awebbs.module.user.entity.User;
import awebbs.exception.Result;
import awebbs.module.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zgqq.
 */
@Controller
@RequestMapping("/notification")
public class NotificationController extends BaseController {

    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private NotificationService notificationService;

    /**
     * 通知列表
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        model.addAttribute("page", notificationService.findByTargetUserAndIsRead(p == null ? 1 : p, siteConfig.getPageSize(), getUser(), null));
        //将未读消息置为已读
        notificationService.updateByIsRead(getUser());
        return render("/notification/list");
    }

    /**
     * 查询当前用户未读的消息数量
     *
     * @return
     */
    @GetMapping("/notRead")
    @ResponseBody
    public Result notRead() throws ApiException {
        User user = getUser();
        if (user == null) throw new ApiException(ErrorCode.notLogin, "请先登录");
        return Result.success(notificationService.countByTargetUserAndIsRead(user, false));
    }
}
