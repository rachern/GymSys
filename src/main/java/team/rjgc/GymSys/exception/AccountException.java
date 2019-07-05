package team.rjgc.GymSys.exception;


import team.rjgc.GymSys.base.exception.AbtBaseException;
import team.rjgc.GymSys.enums.AccountEnum;

/**
 * @author ReMidDream
 * @date 2018/10/23 20:55
 **/

public class AccountException extends AbtBaseException {

    public AccountException(AccountEnum Enum) {
        super(Enum.getCode().toString(),Enum.getMessage());
    }
}
