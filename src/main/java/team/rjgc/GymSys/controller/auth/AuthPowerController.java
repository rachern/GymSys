package team.rjgc.GymSys.controller.auth;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.service.auth.AuthPowerService;
import team.rjgc.GymSys.entity.auth.AuthPower;

/**
 *
 * @author ${author}
 * @since 2019-06-08
 */
@RestController
@RequestMapping("/authPower")
@CrossOrigin
public class AuthPowerController {

    @Autowired
    private AuthPowerService authPowerService;

    @GetMapping("/list")
    @ApiOperation("查询所有权限信息")
    public ResultDTO selectEntityPage()throws Exception{
        return ResultUtil.Success(authPowerService.list());
    }

}
