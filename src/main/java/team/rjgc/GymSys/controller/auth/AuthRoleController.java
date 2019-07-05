package team.rjgc.GymSys.controller.auth;


import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.auth.RoleDTO;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.service.auth.AuthRoleService;
import team.rjgc.GymSys.entity.auth.AuthRole;
import team.rjgc.GymSys.service.auth.AuthTableService;

import javax.validation.Valid;

/**
 *
 * @author ${author}
 * @since 2019-06-08
 */
@RestController
@RequestMapping("/authRole")
@CrossOrigin
public class AuthRoleController {

    @Autowired
    private AuthRoleService authRoleService;
    @Autowired
    private AuthTableService tableService;

    @PostMapping
    @ApiOperation(value = "新增角色")
    public ResultDTO insertOne(@RequestBody @Valid AuthRole authRole, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResultUtil.Error(AccountEnum.Error.getCode().toString(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        authRoleService.save(authRole);
        return ResultUtil.Success(authRole);
    }

    @PutMapping
    @ApiOperation(value = "修改角色信息")
    public ResultDTO updateById(@RequestBody @Valid AuthRole authRole, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResultUtil.Error(AccountEnum.Error.getCode().toString(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        authRoleService.updateById(authRole);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("删除角色")
    public ResultDTO deleteById(@PathVariable Long id) throws Exception {
        authRoleService.removeById(id);
        return ResultUtil.Success();
    }

    @GetMapping("/list")
    @ApiOperation("查询全部角色")
    public ResultDTO selectEntityPage()throws Exception{
        return ResultUtil.Success(authRoleService.list());
    }

    @ApiOperation(value="更改角色权限关系")
    @PutMapping("/rolePowerTable")
    public ResultDTO updateTable(@RequestBody RoleDTO roleDto) throws Exception {
            String type = StringUtils.substringBefore(roleDto.getClass().getSimpleName().toLowerCase() ,"dto");
            tableService.updateTable(roleDto,type);
            return ResultUtil.Success();
    }
}
