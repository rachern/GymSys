package team.rjgc.GymSys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.rjgc.GymSys.entity.eq.EqInfo;
import team.rjgc.GymSys.entity.eq.EqOrder;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;
import team.rjgc.GymSys.enums.OrderStatusEnum;
import team.rjgc.GymSys.service.eq.EqInfoService;
import team.rjgc.GymSys.service.eq.EqOrderDetailService;
import team.rjgc.GymSys.service.eq.EqOrderService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/6/17 22:49
 **/
@Component
public class OrderScheduledService {

    @Autowired
    private EqOrderDetailService eqOrderDetailService;
    @Autowired
    private EqOrderService eqOrderService;
    @Autowired
    private EqInfoService eqInfoService;

    //设置定时（秒分时日月年）
    @Scheduled(cron = "0 */1 *  * * * ")
    void checkOrderStatus(){
        //超过归还时间未归还的订单
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<EqOrder> rentOverTimeQueryWrapper = new QueryWrapper<>();
        rentOverTimeQueryWrapper.eq("order_status",OrderStatusEnum.RENTING.getStatus());
        rentOverTimeQueryWrapper.lt("order_end_time",now);
        List<EqOrder> rentOverTimeOrderList = eqOrderService.list(rentOverTimeQueryWrapper);
        //设置超时状态
        for (EqOrder eqOrder : rentOverTimeOrderList) {
            eqOrder.setOrderStatus(OrderStatusEnum.RENT_OVERTIME.getStatus());
        }
        //存储订单状态
        if (!rentOverTimeOrderList.isEmpty()) {
            eqOrderService.updateBatchById(rentOverTimeOrderList);
        }

        //查询未预约后超过5分钟未支付的订单
        QueryWrapper<EqOrder> orderOverTimeQueryWrapper = new QueryWrapper<>();
        orderOverTimeQueryWrapper.eq("order_status",OrderStatusEnum.NOT_PAY.getStatus());
        orderOverTimeQueryWrapper.lt("order_create_time",now.minusMinutes(5));
        List<EqOrder> orderOverTimeList = eqOrderService.list(orderOverTimeQueryWrapper);
        //更新订单租借状态, 恢复库存
        returnNumberAndSetStatus(orderOverTimeList,OrderStatusEnum.ORDER_OVERTIME.getStatus());

        //查询已支付但超过归还时间且没有租借器材的情况
        QueryWrapper<EqOrder> notRentQueryWrapper = new QueryWrapper<>();
        notRentQueryWrapper.eq("order_status",OrderStatusEnum.PAY_NOT_RENT.getStatus());
        notRentQueryWrapper.lt("order_end_time",now);
        List<EqOrder> notRentList = eqOrderService.list(notRentQueryWrapper);
        //更新订单租借状态, 恢复库存
        returnNumberAndSetStatus(notRentList,OrderStatusEnum.FINISH.getStatus());
    }

    private void returnNumberAndSetStatus(List<EqOrder> orderList,String status){
        for (EqOrder eqOrder : orderList) {
            //设置超时状态
            eqOrder.setOrderStatus(status);
            //还原库存
            QueryWrapper<EqOrderDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id",eqOrder.getOrderId());
            List<EqOrderDetail> orderDetailListList = eqOrderDetailService.list(queryWrapper);
            for (EqOrderDetail eqOrderDetail : orderDetailListList) {
                EqInfo equipment = eqInfoService.getById(eqOrderDetail.getEqId());
                equipment.setEqUsedNumber(equipment.getEqUsedNumber() + eqOrderDetail.getEqNumber());
                eqInfoService.updateById(equipment);
            }
        }
        if (!orderList.isEmpty()) {
            eqOrderService.updateBatchById(orderList);
        }
    }
}
