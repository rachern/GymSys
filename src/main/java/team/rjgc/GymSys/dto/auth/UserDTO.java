package team.rjgc.GymSys.dto.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ReMidDream
 * @date 2018/10/22 16:45
 **/
@Getter
@Setter
public class UserDTO extends AuthenticationDTO<RoleDTO> {

    private String name;

    private String userPassword;

    private String userEmail;
}
