package awebbs.module.api.controller;

import awebbs.common.BaseController;
import awebbs.exception.ApiException;
import awebbs.module.reply.service.ReplyService;
import awebbs.module.topic.service.TopicService;
import awebbs.module.user.entity.User;
import awebbs.common.config.SiteConfig;
import awebbs.exception.Result;
import awebbs.module.collect.service.CollectService;
import awebbs.module.reply.entity.Reply;
import awebbs.module.topic.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgqq.
 */
@RestController
@RequestMapping("/api/topic")
public class TopicApiController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private SiteConfig siteConfig;

    /**
     * 话题详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(String token, @PathVariable Integer id) throws ApiException {
        Topic topic = topicService.findById(id);
        if(topic == null) throw new ApiException("话题不存在");
        Map<String, Object> map = new HashMap<>();
        //浏览量+1
        topic.setView(topic.getView() + 1);
        topicService.save(topic);//更新话题数据
        List<Reply> replies = replyService.findByTopic(topic);
        map.put("topic", topic);
        map.put("replies", replies);
        map.put("author", topic.getUser());
        map.put("collectCount", collectService.countByTopic(topic));
        return Result.success(map);
    }

    /**
     * 保存话题
     * @param title
     * @param content
     * @param tab
     * @param token
     * @return
     * @throws ApiException
     */
    @PostMapping("/save")
    public Result save(String title, String content, String tab, String token, String editor) throws ApiException {
        User user = getUser(token);
        if(user == null) throw new ApiException("用户不存在");
        if(user.isBlock()) throw new ApiException("你的帐户已经被禁用了，不能进行此项操作");

        if(StringUtils.isEmpty(title)) throw new ApiException("标题不能为空");
        if(title.length() > 120) throw new ApiException("标题不能超过120个字");
        if(!siteConfig.getSections().contains(tab)) throw new ApiException("版块不存在");
        if(StringUtils.isEmpty(editor)) editor = "markdown";
        if(!editor.equals("markdown") || !editor.equals("wangeditor")) editor = "markdown";

        Topic topic = new Topic();
        topic.setTab(tab);
        topic.setTitle(title);
        topic.setContent(content);
        topic.setInTime(new Date());
        topic.setView(0);
        topic.setUser(user);
        topic.setGood(false);
        topic.setTop(false);
        topic.setEditor(editor);
        topic.setLock(false);
        topicService.save(topic);

        return Result.success(topic);
    }
}
