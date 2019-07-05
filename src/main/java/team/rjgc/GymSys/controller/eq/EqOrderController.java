package team.rjgc.GymSys.controller.eq;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.enums.OrderStatusEnum;
import team.rjgc.GymSys.service.auth.AuthUserService;
import team.rjgc.GymSys.service.eq.EqOrderService;
import team.rjgc.GymSys.entity.eq.EqOrder;

/**
 *
 * @author ${author}
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/equipment/order")
@CrossOrigin
public class EqOrderController {

    @Autowired
    private EqOrderService eqOrderService;
    @Autowired
    private AuthUserService authUserService;

    @GetMapping("/mine")
    @ApiOperation("用户查看自己订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",
                    value = "订单类型(不填全部类型的订单,1:租借,2.购置,3.维修,4.报废)",paramType = "query",required = false),
    })
    public ResultDTO findMyOrder(Authentication authentication,@RequestParam(required = false) Long type)throws Exception{
        if (authentication==null) {
            throw new RuntimeException("未登录!");
        }
        return eqOrderService.findMineOrder(authUserService.getUserDetails(authentication).getUserId(),type);
    }

    @GetMapping("/admin")
    @ApiOperation("管理员查询全部订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",
                    value = "订单类型(不填全部类型的订单,1:租借,2.购置,3.维修,4.报废)",paramType = "query",required = false),
    })
    public ResultDTO getAllOrder(@RequestParam(required = false) Long type,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "8") int pageSize)throws Exception{
        return eqOrderService.getAllOrder(type,pageNum,pageSize);
    }

    @GetMapping("/admin/{username}")
    @ApiOperation("管理员根据用户名查询全部订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",
                    value = "订单类型(不填全部类型的订单,1:租借,2.购置,3.维修,4.报废)",paramType = "query",required = false),
            @ApiImplicitParam(name = "username",
                    value = "用户名",paramType = "path",required = true),
    })
    public ResultDTO getOrderByUsername(@RequestParam(required = false) Long type,
                                 @PathVariable String username,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "8") int pageSize)throws Exception{
        return eqOrderService.getOrderByUsername(username,type,pageNum,pageSize);
    }

    @GetMapping("/{orderId}")
    @ApiOperation("管理员根据用户名查询全部订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId",
                    value = "订单号",paramType = "path",required = true),
    })
    public ResultDTO getOrderByOrderId(@PathVariable Long orderId)throws Exception{
        return eqOrderService.getOrderByOrderId(orderId);
    }

    /* 暂时作废

         @GetMapping("/maintenance")
        @ApiOperation("查看报修订单")
        public ResultDTO getMaintenanceOrder()throws Exception{
            return eqOrderService.getMaintenanceOrder();
        }
     */


    @PostMapping("/status/{orderStatus}/{orderId}")
    @ApiOperation("修改订单状态, 租借+维修")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId",value = "订单ID",paramType = "path",required = true),
            @ApiImplicitParam(name = "orderStatus",value = "1:已支付未到租借时间,2:租借中,3:取消订单,4.维护完成 ",
                    paramType = "path",required = true),
    })
    public ResultDTO updateOrderStatus(@PathVariable Long orderId,@PathVariable Integer orderStatus) throws Exception {
        EqOrder order = eqOrderService.getById(orderId);
        switch (orderStatus){
            case 1:
                order.setOrderStatus(OrderStatusEnum.PAY_NOT_RENT.getStatus());
                break;
            case 2:
                order.setOrderStatus(OrderStatusEnum.RENTING.getStatus());
                break;
            case 3:
                return eqOrderService.cancelOrder(orderId)?
                        ResultUtil.Success():ResultUtil.Error("502","更新订单状态失败");
            case 4:
                return eqOrderService.maintenanceFinish(orderId)?
                        ResultUtil.Success():ResultUtil.Error("502","更新订单状态失败");
            default:
                throw new RuntimeException("状态参数有误");
        }
        return eqOrderService.updateById(order)?
                ResultUtil.Success():ResultUtil.Error("502","更新订单状态失败");
    }

    @PostMapping("/return/{orderStatus}/{orderId}")
    @ApiOperation("归还器材")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderStatus",value = "损坏状态 0:无损坏 1: 有损坏",paramType = "path",required = true),
    })
    public ResultDTO returnEquipment(@RequestBody(required = false) EquipmentOrderDTO equipmentOrderDTO, @PathVariable Integer orderStatus,
                                      @PathVariable Long orderId)throws Exception{
        return eqOrderService.returnEquipment(orderId ,orderStatus, equipmentOrderDTO);
    }

    @GetMapping("/profit")
    public ResultDTO getProfitEveryMonth(Integer year, Integer month){
        return eqOrderService.getProfitEveryMonth(year,month);
    }


}
