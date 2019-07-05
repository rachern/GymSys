package team.rjgc.GymSys.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import team.rjgc.GymSys.entity.auth.AuthRole;
import team.rjgc.GymSys.entity.auth.AuthUser;
import team.rjgc.GymSys.service.auth.AuthRoleService;
import team.rjgc.GymSys.service.auth.AuthUserService;

import java.util.List;

/**
 * 登录时的认证
 * 通过用户名获取用户信息, 进行帐号信息验证
 * 验证是Spring security 内部实现的
 *  *需要在config里 注入PasswordEncoder的bean*
 * @author ReMidDream
 * @date 2018-08-17 14:55
 **/
@Component
@Slf4j
public class UserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthUserService userService;
    @Autowired
    private AuthRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名 : {}",username);
        List<AuthUser> user;

        // 根据用户名查找用户信息
        try {
            QueryWrapper<AuthUser> authUserQueryWrapper = new QueryWrapper<>();
            authUserQueryWrapper.eq("user_name",username);
            user = userService.list(authUserQueryWrapper);
        } catch (Exception e) {
            throw new UsernameNotFoundException("系统异常");
        }

        if (user.get(0) == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        if (user.size()>1) {
            throw new UsernameNotFoundException("系统异常");
        }

        String userPassword = user.get(0).getUserPassword();

        return new User(username,userPassword,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
