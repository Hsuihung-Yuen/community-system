package cn.hhy.communitysystem.controller;

import cn.hhy.communitysystem.entity.Comment;
import cn.hhy.communitysystem.entity.DiscussPost;
import cn.hhy.communitysystem.entity.Event;
import cn.hhy.communitysystem.event.EventProducer;
import cn.hhy.communitysystem.service.CommentService;
import cn.hhy.communitysystem.service.DiscussPostService;
import cn.hhy.communitysystem.util.CommunityConstant;
import cn.hhy.communitysystem.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {
    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId);

        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());

            //触发发帖事件给es
            Event event_pub = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducer.fireEvent(event_pub);
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);

//        if (comment.getEntityType() == ENTITY_TYPE_POST) {
//            // 触发发帖事件
//            event = new Event()
//                    .setTopic(TOPIC_PUBLISH)
//                    .setUserId(comment.getUserId())
//                    .setEntityType(ENTITY_TYPE_POST)
//                    .setEntityId(discussPostId);
//            eventProducer.fireEvent(event);
//        }

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
