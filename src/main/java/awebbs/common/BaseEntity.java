package awebbs.common;

import awebbs.util.Constants;
import awebbs.util.MarkdownUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zgqq.
 */
public class BaseEntity {

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public String formatDate(Date date) {
        String dateStr = "";
        if (date != null) {
            PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
            dateStr = prettyTime.format(date);
        }
        return dateStr.replace(" ", "");
    }

    /**
     * 解析markdown文章
     *
     * @param content
     * @return
     */
    public String marked(String content) {
        if (StringUtils.isEmpty(content)) return "";
        //处理@
        List<String> users = fetchUsers(null, content);
        for (String user : users) {
            content = content.replace(user, "[" + user + "](/user/" + user + ")");
        }
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

    /**
     * 查找一段文本里以 @ 开头的字符串
     *
     * @param str
     * @return
     */
    public static List<String> fetchUsers(String pattern, String str) {
        List<String> ats = new ArrayList<>();
        if (StringUtils.isEmpty(pattern)) pattern = "@[^\\s]+\\s?";
        Pattern regex = Pattern.compile(pattern);
        Matcher regexMatcher = regex.matcher(str);
        while (regexMatcher.find()) {
            ats.add(regexMatcher.group());
        }
        return ats;
    }

    /**
     * 解析markdown文章(不解析@)
     *
     * @param content
     * @return
     */
    public String markedNotAt(String content) {
        if (StringUtils.isEmpty(content)) return "";
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

    /**
     * 高亮title里的搜索关键字
     * @param q
     * @param title
     * @return
     */
    public String lightTitle(String q, String title) {
        return title.replace(q, "<b style='color: red;'>"+ q +"</b>");
    }

    /**
     * 搜索结果内容截取
     * @param _editor
     * @param q
     * @param content
     * @return
     */
    public String subContent(String _editor, String q, String content) {
        int index = content.indexOf(q);
        if(_editor.equals("markdown")) content = marked(content);
        content = Jsoup.parse(content).text();
        if(index < 0) {
            if(content.length() >= 150) content = content.substring(0, 150);
        } else {
            if(index < 20) {
                if(content.length() >= 150) content = content.substring(0, 150);
            } else {
                if(content.length() >= 150) content = content.substring(0, 150);
            }
        }
        return content.replace(q, "<b style='color: red;'>"+ q +"</b>") + "...";
    }

    public boolean isUp(int userId, String upIds) {
        return upIds != null && upIds.contains(Constants.COMMA + userId + Constants.COMMA);
    }

    public boolean isDown(int userId, String downIds) {
        return downIds != null && downIds.contains(Constants.COMMA + userId + Constants.COMMA);
    }
}
