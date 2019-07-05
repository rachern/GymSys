package team.rjgc.GymSys.controller.eq;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.entity.eq.EqInfo;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;
import team.rjgc.GymSys.service.auth.AuthUserService;
import team.rjgc.GymSys.service.eq.EqInfoService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author ${author}
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/equipment")
@CrossOrigin
public class EqInfoController {

    @Autowired
    private EqInfoService eqInfoService;
    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/purchase")
    @ApiOperation(value = "购置器材(只有购置已存在器材要填eqId,eqName可不填,购置未存在器材要填eqName)" +
            "(其他情况下所以id都不填,createTime为购置时间,price订单总价,eqOrderPrice为器材总价)")
    public ResultDTO PurchaseEquipment(@RequestBody EquipmentOrderDTO equipmentOrderDTO, Authentication authentication) throws Exception {
        if (authentication==null) {
            throw new RuntimeException("未登录!");
        }
        Long userId = authUserService.getUserDetails(authentication).getUserId();
        return  eqInfoService.purchaseEquipment(userId, equipmentOrderDTO);
    }

    @PostMapping("/rent")
    @ApiOperation(value = "租借器材(填eqId,eqName可不填,price订单总价,eqOrderPrice为器材总价)")
    public ResultDTO RentEquipment(@RequestBody EquipmentOrderDTO equipmentOrderDTO, Authentication authentication) throws Exception {
        if (authentication==null) {
            throw new RuntimeException("未登录!");
        }
        //判断器材ID合法性
        Set<Long> equipmentIdSet = equipmentOrderDTO.getEqOrderDetailList().stream().map(EqOrderDetail::getEqId).collect(Collectors.toSet());
        QueryWrapper<EqInfo> wrapper = new QueryWrapper<>();
        wrapper.in("eq_id",equipmentIdSet);
        List<EqInfo> equipmentList = eqInfoService.list(wrapper);
        if(equipmentIdSet.size() != equipmentList.size()){
            throw new RuntimeException("器材信息不存在");
        }
        //调用租借方法
        Long userId = authUserService.getUserDetails(authentication).getUserId();
        return  eqInfoService.rentEquipment(userId, equipmentOrderDTO);
    }

    @PostMapping("/maintenance")
    @ApiOperation(value = "报修器材")
    public ResultDTO MaintenanceEquipment(@RequestBody EquipmentOrderDTO equipmentOrderDTO, Authentication authentication) throws Exception {
        if (authentication==null) {
            throw new RuntimeException("未登录!");
        }
        Long userId = authUserService.getUserDetails(authentication).getUserId();
        return  eqInfoService.maintenanceEquipment(userId, equipmentOrderDTO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "报废器材")
    public ResultDTO DeleteEquipment(@RequestBody EquipmentOrderDTO equipmentOrderDTO, Authentication authentication) throws Exception {
        if (authentication==null) {
            throw new RuntimeException("未登录!");
        }
        Long userId = authUserService.getUserDetails(authentication).getUserId();
        return  eqInfoService.deleteEquipment(userId, equipmentOrderDTO);
    }

    @PostMapping("/update")
    @ApiOperation("更新器材信息(eqId为更新依据), 若只是更新价格(只填eqId和eqPrice)")
    public ResultDTO UpdateEquipment(@RequestBody EquipmentDTO equipmentDTO)throws Exception{
        return eqInfoService.updateBatchById(equipmentDTO.getEquipmentList())?
                ResultUtil.Success():ResultUtil.Error("500","系统错误");
    }

    @GetMapping("/info/list")
    @ApiOperation("查询所有器材的信息")
    public ResultDTO selectEntityList()throws Exception{
        return ResultUtil.Success(eqInfoService.list());
    }

    @GetMapping("/info/page")
    @ApiOperation("查询所有器材的信息, 分页")
    public IPage<EqInfo> selectEntityPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "8") int pageSize)throws Exception{
        return eqInfoService.page(new Page<EqInfo>(pageNum, pageSize));
    }

}
