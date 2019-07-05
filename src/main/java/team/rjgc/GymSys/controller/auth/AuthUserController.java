package team.rjgc.GymSys.controller.auth;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.auth.UseInfoDTO;
import team.rjgc.GymSys.dto.auth.UserDTO;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.enums.RoleEnum;
import team.rjgc.GymSys.service.auth.AuthPowerService;
import team.rjgc.GymSys.service.auth.AuthTableService;
import team.rjgc.GymSys.service.auth.AuthUserService;
import team.rjgc.GymSys.entity.auth.AuthUser;

import javax.validation.Valid;

/**
 * @author ${author}
 * @since 2019-06-08
 */
@RestController
@RequestMapping("/authUser")
@CrossOrigin
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AuthTableService tableService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthPowerService authPowerService;

    @GetMapping("/info")
    @ApiOperation(value = "查询登录后用户信息")
    public ResultDTO getUserDetail(Authentication authentication) throws Exception {
        if (authentication == null) {
            throw new RuntimeException("未登录!");
        }
        return ResultUtil.Success(authUserService.getUserDetails(authentication));
    }

    @PostMapping
    @ApiOperation(value = "注册普通用户")
    public ResultDTO insertOne(@RequestBody @Valid AuthUser authUser, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResultUtil.Error(AccountEnum.Error.getCode().toString(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        authUserService.signIn(authUser, RoleEnum.USER.getRoleId());
        return ResultUtil.Success(authUser);
    }

    @PostMapping("/admin/{roleId}")
    @ApiOperation(value = "注册管理员用户(1.管理员4.器材管理员,5.场地管理员6.赛事管理员)")
    public ResultDTO insertAdmin(@RequestBody @Valid AuthUser authUser,@PathVariable Long roleId, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResultUtil.Error(AccountEnum.Error.getCode().toString(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        authUserService.signIn(authUser, roleId);
        return ResultUtil.Success(authUser);
    }

    @PutMapping
    @ApiOperation(value = "修改用户信息, 包含改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id(必填)", paramType = "query", required = true),
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query", required = false),
            @ApiImplicitParam(name = "userPassword", value = "密码", paramType = "query", required = false),
            @ApiImplicitParam(name = "userEmail", value = "邮箱", paramType = "query", required = false),
    })
    public ResultDTO updateById(@RequestBody AuthUser authUser) throws Exception {
        if (authUser.getUserPassword() != null) {
            AuthUser user = authUserService.getById(authUser.getUserId());
            if (user == null) {
                throw new RuntimeException("用户不存在!");
            }
                authUser.setUserPassword(passwordEncoder.encode(authUser.getUserPassword()));
        }
        authUserService.updateById(authUser);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("根据Id删除用户")
    public ResultDTO deleteById(@PathVariable Long id) throws Exception {
        authUserService.removeById(id);
        return ResultUtil.Success();
    }

    @GetMapping
    @ApiOperation("查询全部用户")
    public ResultDTO getUserList() throws Exception {
        return ResultUtil.Success(authUserService.list(),authUserService.list().size());
    }

    @ApiOperation(value = "更改用户角色关系")
    @PutMapping("/userRoleTable")
    public ResultDTO updateUserRole(@RequestBody UserDTO userDto) throws Exception {
        String type = StringUtils.substringBefore(userDto.getClass().getSimpleName().toLowerCase(), "dto");
        tableService.updateTable(userDto, type);
        return ResultUtil.Success();
    }

}
