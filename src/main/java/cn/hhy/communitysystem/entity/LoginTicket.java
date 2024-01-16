package cn.hhy.communitysystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket {

    private int id;
    private int userId;
    private String ticket;
    private int status;

    //设定的ticket持续时间
    private Date expired;
}
