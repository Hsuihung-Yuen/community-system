package cn.hhy.communitysystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private int id;
    private int userId;

    //是对整个帖子评论还是对帖子里的评论进行评论
    private int entityType;

    //对具体哪个帖子或者帖子里的哪个评论进行评论
    private int entityId;
    private int targetId;
    private String content;
    private int status;
    private Date createTime;
}
