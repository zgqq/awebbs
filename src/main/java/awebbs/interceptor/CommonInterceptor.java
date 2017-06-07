package awebbs.interceptor;

import awebbs.common.config.SiteConfig;
import awebbs.util.IpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zgqq.
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

    private Logger log = Logger.getLogger(CommonInterceptor.class);

    @Autowired
    private SiteConfig siteConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        request.setAttribute("_start", start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            ModelMap modelMap = modelAndView.getModelMap();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = false;
            if (authentication != null) {
                Object o = authentication.getPrincipal();
                if (o instanceof UserDetails) {
                    isAuthenhttps://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=%E8%8B%B9%E6%9E%9C%E5%BA%8F%E5%88%97%E5%8F%B7&rsv_pq=cf5cec7100098b63&rsv_t=5188vQoofPJKgDawat7rlnZBSyMOfoa8cedCr8M6m4SZvRSRm7x6k2ZZCvg&rqlang=cn&rsv_enter=1&rsv_sug3=17&rsv_sug2=0&inputT=3389&rsv_sug4=3390ticated = true;
                    modelMap.addAttribute("_principal", ((UserDetails) o).getUsername());
                    modelMap.addAttribute("_roles", ((UserDetails) o).getAuthorities());
                    isAuthenticated = true;
                }
            }
            modelMap.addAttribute("_isAuthenticated", isAuthenticated);
            modelMap.addAttribute("baseUrl", siteConfig.getBaseUrl());
            modelMap.addAttribute("_intro", siteConfig.getIntro());
            modelMap.addAttribute("siteTitle", siteConfig.getName());
            modelMap.addAttribute("sections", siteConfig.getSections());
            modelMap.addAttribute("_editor", siteConfig.getEditor());
            modelMap.addAttribute("_search", siteConfig.isSearch());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (long) request.getAttribute("_start");

        String actionName = request.getRequestURI();
        String clientIp = IpUtil.getIpAddr(request);
        StringBuffer logstring = new StringBuffer();
        logstring.append(clientIp).append("|").append(actionName).append("|");
        Map<String, String[]> parmas = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iter = parmas.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String[]> entry = iter.next();
            logstring.append(entry.getKey());
            logstring.append("=");
            for (String paramString : entry.getValue()) {
                logstring.append(paramString);
            }
            logstring.append("|");
        }
        long executionTime = System.currentTimeMillis() - start;
        logstring.append("excutetime=").append(executionTime).append("ms");
        log.info(logstring.toString());
    }
}
