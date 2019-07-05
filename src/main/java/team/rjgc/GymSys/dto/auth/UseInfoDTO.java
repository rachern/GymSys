package team.rjgc.GymSys.dto.auth;

import lombok.Data;
import team.rjgc.GymSys.entity.auth.AuthPower;

import java.util.List;

@Data
public class UseInfoDTO {

    private Long userId;

    private String userName;

    private String userPassword;

    private String userEmail;

    private List<AuthPower> authPowerList;
}
