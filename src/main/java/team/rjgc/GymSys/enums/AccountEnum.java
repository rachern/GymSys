package team.rjgc.GymSys.enums;

import lombok.Getter;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;

/**
 * @author ReMidDream
 * @date 2018/10/23 20:55
 **/
@Getter
public enum AccountEnum{

    Error(500,"系统异常"),
    USERNAME_EXIST(501,"帐号名已经存在!"),
    EMAIL_EXIST(502,"邮箱已经存在!"),
    ERRPR_TIME(503,"錯誤時間"),
    AREA_BOOK_CONFLICT(504,"预定場地衝突"),
    AREA_TYPE_CONFLICT(505,"场地类型名冲突"),
    AREA_TYPE_USED(506,"场地类型正在使用"),
    AREA_TYPE_INVALID(507,"场地类型不正确"),
    ADMIN(1,"管理员"),
    SUPER_ADMIN(2,"超级管理员"),
    USER(3,"用户"),
    EQ_ADMIN(4,"器材管理员"),
    AREA_ADMIN(5,"场地管理员"),
    MATCH_ADMIN(6,"赛事管理员"),
    ;

    private Integer code;

    private String message;

    AccountEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDTO show() {
        return ResultUtil.Error(this.code.toString(),this.message);
    }
}
