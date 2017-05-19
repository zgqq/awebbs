package awebbs.module.reply.controller;

import awebbs.module.reply.service.ReplyService;
import awebbs.common.BaseController;
import awebbs.common.config.SiteConfig;
import awebbs.module.reply.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zgqq.
 */
@Controller
@RequestMapping("/admin/reply")
public class ReplyAdminController extends BaseController {

    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private ReplyService replyService;

    /**
     * 回复列表
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        Page<Reply> page = replyService.page(p == null ? 1 : p, siteConfig.getPageSize());
        model.addAttribute("page", page);
        return render("/admin/reply/list");
    }

}
