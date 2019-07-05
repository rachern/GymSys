package team.rjgc.GymSys.service.auth;

import org.springframework.security.core.Authentication;
import team.rjgc.GymSys.dto.auth.UseInfoDTO;
import team.rjgc.GymSys.dto.auth.UserDTO;
import team.rjgc.GymSys.entity.auth.AuthUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-08
 */
public interface AuthUserService extends IService<AuthUser> {

    public Set<String> findAuthority(String userName) throws Exception;

    public List<UserDTO> findAuthorityInfo(String userName) throws Exception;

    public AuthUser signIn(AuthUser user,Long roleId)throws Exception;

    public AuthUser getUserDetails(Authentication authentication) throws  Exception;

}
