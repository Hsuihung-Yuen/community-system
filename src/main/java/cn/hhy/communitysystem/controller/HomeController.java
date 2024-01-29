package cn.hhy.communitysystem.controller;

import cn.hhy.communitysystem.entity.DiscussPost;
import cn.hhy.communitysystem.entity.Page;
import cn.hhy.communitysystem.service.DiscussPostService;
import cn.hhy.communitysystem.service.LikeService;
import cn.hhy.communitysystem.service.UserService;
import cn.hhy.communitysystem.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {

        // 方法调用钱,SpringMVC会自动实例化Model和Page,并将Page注入Model
        // 因此，在thymeleaf中可以直接访问Page对象中的数据
        page.setPath("/index");
        page.setRows(discussPostService.findDiscussPostRows(0));

        List<DiscussPost> home_posts = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> dicussPosts = new ArrayList<>();
        if (home_posts != null) {
            for (DiscussPost home_post : home_posts) {
                Map<String, Object> tmp_map = new HashMap<>();
                tmp_map.put("post", home_post);
                tmp_map.put("user", userService.findUserById(home_post.getUserId()));

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, home_post.getId());
                tmp_map.put("likeCount", likeCount);

                dicussPosts.add(tmp_map);
            }
        }
        model.addAttribute("discussPosts", dicussPosts);
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

}
