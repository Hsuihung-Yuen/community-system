package cn.hhy.communitysystem.entity;

import java.util.Date;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;

    //头像图片url
    private String headerUrl;
    private Date createTime;
}
