package team.rjgc.GymSys.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author ReMidDream
 * @date 2018/11/1 15:13
 **/
@Getter
@Setter
public class PowerDTO extends AuthenticationDTO {

    private String name;

    private String url;

    private Date createTime;

    private Date updateTime;

}
