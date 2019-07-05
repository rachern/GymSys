package team.rjgc.GymSys.enums;

import lombok.Getter;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;

/**
 * @author ReMidDream
 * @date 2018/10/23 20:55
 **/
@Getter
public enum RoleEnum {

    SUPER_ADMIN(1L,"超级管理员"),
    ADMIN(2L,"管理员"),
    USER(3L,"普通用户"),
    ;

    private Long roleId;

    private String role;

    RoleEnum(Long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

}
