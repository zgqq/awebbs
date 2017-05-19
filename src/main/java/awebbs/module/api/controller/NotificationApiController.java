package awebbs.module.api.controller;

import awebbs.exception.ApiException;
import awebbs.exception.ErrorCode;
import awebbs.module.user.entity.User;
import awebbs.common.BaseController;
import awebbs.exception.Result;
import awebbs.module.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zgqq.
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 查询当前用户未读的消息数量
     *
     * @return
     */
    @GetMapping("/notRead")
    public Result notRead(String token) throws ApiException {
        User user = getUser(token);
        if (user == null) throw new ApiException(ErrorCode.notLogin, "请先登录");
        return Result.success(notificationService.countByTargetUserAndIsRead(user, false));
    }

}
