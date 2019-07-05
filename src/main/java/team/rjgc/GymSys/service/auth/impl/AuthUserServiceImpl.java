package team.rjgc.GymSys.service.auth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import team.rjgc.GymSys.base.exception.AbtBaseException;
import team.rjgc.GymSys.dto.auth.UserDTO;
import team.rjgc.GymSys.entity.auth.AuthUser;
import team.rjgc.GymSys.entity.auth.AuthUserRoleTable;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.exception.AccountException;
import team.rjgc.GymSys.mapper.auth.AuthUserMapper;
import team.rjgc.GymSys.mapper.auth.AuthUserRoleTableMapper;
import team.rjgc.GymSys.service.auth.AuthRoleService;
import team.rjgc.GymSys.service.auth.AuthUserRoleTableService;
import team.rjgc.GymSys.service.auth.AuthUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-08
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {

    @Autowired
    private AuthUserMapper authUserMapper;
    @Autowired
    private AuthUserRoleTableService authUserRoleTableService;
    @Autowired
    private AuthRoleService roleService;

    @Override
    public Set<String> findAuthority(String userName) throws Exception {
        return authUserMapper.findAuthority(userName);
    }

    @Override
    public List<UserDTO> findAuthorityInfo(String userName) throws Exception {
        return authUserMapper.findAuthorityInfo(userName);
    }

    @Override
    @Transactional
    public AuthUser signIn(AuthUser user,Long roleId) throws Exception {
        /*
         * 获取所有用户名, 判断是否重复
         * */
        AuthUser checkNameUser = new AuthUser(user.getUserName());
        QueryWrapper<AuthUser> userQueryWrapper= new QueryWrapper<>();
        userQueryWrapper.eq("user_name",user.getUserName());
        List<AuthUser> checkNameUserList = list(userQueryWrapper);


        if (checkNameUserList.size() > 0) {
            throw new AccountException(AccountEnum.USERNAME_EXIST);
        }
        /*
         * 注册用户
         * */
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        save(user);

        /*
         * 给用户赋予最初始角色
         * */
        AuthUserRoleTable userRoleTable = new AuthUserRoleTable(user.getUserId(),roleId);
        authUserRoleTableService.save(userRoleTable);

        return user;
    }

    @Override
    public AuthUser getUserDetails(Authentication authentication) throws Exception {
        if(authentication == null){
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        QueryWrapper<AuthUser> userQueryWrapper= new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userDetails.getUsername());
        List<AuthUser> params = list(userQueryWrapper);

        if(params.size() == 0){
            throw new AbtBaseException("500","没有对应的用户信息");
        }
        return params.get(0);
    }

}
