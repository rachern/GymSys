package team.rjgc.GymSys.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置方法详见 team.cs.security.UserAuthorizeConfigProvider
 * @author ReMidDream
 * @date 2018/9/25 16:15
 **/
public interface RbacService {

    boolean isPermission(HttpServletRequest request, Authentication authentication) throws Exception;

}
