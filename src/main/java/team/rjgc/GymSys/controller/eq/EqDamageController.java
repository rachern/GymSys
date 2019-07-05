package team.rjgc.GymSys.controller.eq;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.service.auth.AuthUserService;
import team.rjgc.GymSys.service.eq.EqDamageService;
import team.rjgc.GymSys.entity.eq.EqDamage;

import java.util.List;

/**
 *
 * @author ${author}
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/equipment")
@CrossOrigin
public class EqDamageController {

    @Autowired
    private EqDamageService eqDamageService;
    @Autowired
    private AuthUserService authUserService;

    @GetMapping("/list")
    @ApiOperation("查询损坏器材")
    public ResultDTO getDamageEqList()throws Exception{
        return ResultUtil.Success(eqDamageService.findDamageEquipment());
    }

    @PostMapping("/damage")
    @ApiOperation("设置为损坏器材(只填eqOrderDetailList里面的数据)")
    public ResultDTO setDamageEquipment(@RequestBody EquipmentOrderDTO equipmentOrderDTO)throws Exception{
        if (equipmentOrderDTO == null || equipmentOrderDTO.getEqOrderDetailList().isEmpty()) {
            throw new RuntimeException("数据异常!");
        }
        return eqDamageService.setDamageEquipment(equipmentOrderDTO)?
                ResultUtil.Success():ResultUtil.Error("500","系统错误");
    }

}
