package team.rjgc.GymSys.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ReMidDream
 * @date 2018/11/1 15:11
 **/
@Getter
@Setter
public class AuthenticationDTO<T extends AuthenticationDTO>{

    protected Long id;

    protected List<T> children;

}
