package team.rjgc.GymSys.rbac;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import team.rjgc.GymSys.service.auth.AuthUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author ReMidDream
 * @date 2018/9/25 16:16
 **/
@Component(value = "rbacService")
public class RbacServiceImpl implements RbacService {

    @Autowired
    private AuthUserService userService;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean isPermission(HttpServletRequest request, Authentication authentication) throws Exception {
        boolean hasPermission = false;
        String userName;

        Object principal = authentication.getPrincipal();

        /*
         *  判断用户信息是否符合格式
         */
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }else if (principal instanceof String) {
            userName = (String) principal;
        }else{
            return false;
        }

        if (StringUtils.equals("anonymousUser", userName)) {
            return false;
        }

        //读取用户所拥有的权限
        Set<String> urls = userService.findAuthority(userName);

            for (String url : urls) {
                if (pathMatcher.match(url, request.getServletPath())) {
                    hasPermission = true;
                    break;
                }
            }
            return hasPermission;

    }
}
