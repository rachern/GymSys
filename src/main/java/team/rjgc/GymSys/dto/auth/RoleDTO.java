package team.rjgc.GymSys.dto.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ReMidDream
 * @date 2018/10/22 16:48
 **/
@Getter
@Setter
public class RoleDTO extends AuthenticationDTO<PowerDTO> {

    private String name;

}
