package awebbs.module.collect.controller;

import awebbs.module.notification.entity.NotificationEnum;
import awebbs.module.topic.service.TopicService;
import awebbs.common.BaseController;
import awebbs.module.collect.entity.Collect;
import awebbs.module.collect.service.CollectService;
import awebbs.module.notification.service.NotificationService;
import awebbs.module.topic.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zgqq.
 */
@Controller
@RequestMapping("/collect")
public class CollectController extends BaseController {

    @Autowired
    private CollectService collectService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{topicId}/add")
    public String add(@PathVariable Integer topicId, HttpServletResponse response) {
        Topic topic = topicService.findById(topicId);
        if (topic == null) {
            return renderText(response, "话题不存在");
        } else {
            Collect collect = new Collect();
            collect.setInTime(new Date());
            collect.setTopic(topic);
            collect.setUser(getUser());
            collectService.save(collect);

            //发出通知
            notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.COLLECT.name(), topic, "", null);
            return redirect(response, "/topic/" + topic.getId());
        }
    }

    /**
     * 删除收藏
     *
     * @param topicId
     * @param response
     * @return
     */
    @GetMapping("/{topicId}/delete")
    public String delete(@PathVariable Integer topicId, HttpServletResponse response) {
        Topic topic = topicService.findById(topicId);
        Collect collect = collectService.findByUserAndTopic(getUser(), topic);
        if (collect == null) {
            return renderText(response, "你还没收藏这个话题");
        } else {
            collectService.deleteById(collect.getId());
            return redirect(response, "/topic/" + topic.getId());
        }
    }
}
