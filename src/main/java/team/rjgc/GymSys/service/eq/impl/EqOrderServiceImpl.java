package team.rjgc.GymSys.service.eq.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.dto.eq.ProfitDTO;
import team.rjgc.GymSys.entity.auth.AuthUser;
import team.rjgc.GymSys.entity.eq.EqDamage;
import team.rjgc.GymSys.entity.eq.EqInfo;
import team.rjgc.GymSys.entity.eq.EqOrder;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;
import team.rjgc.GymSys.enums.OrderStatusEnum;
import team.rjgc.GymSys.mapper.eq.EqOrderMapper;
import team.rjgc.GymSys.service.auth.AuthUserService;
import team.rjgc.GymSys.service.eq.EqDamageService;
import team.rjgc.GymSys.service.eq.EqInfoService;
import team.rjgc.GymSys.service.eq.EqOrderDetailService;
import team.rjgc.GymSys.service.eq.EqOrderService;
import team.rjgc.GymSys.utils.EnumUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-17
 */
@Service
public class EqOrderServiceImpl extends ServiceImpl<EqOrderMapper, EqOrder> implements EqOrderService {

    @Autowired
    private EqOrderDetailService eqOrderDetailService;
    @Autowired
    private EqInfoService eqInfoService;
    @Autowired
    private EqOrderService eqOrderService;
    @Autowired
    private EqDamageService eqDamageService;
    @Autowired
    private AuthUserService userService;

    @Override
    @Transactional
    public ResultDTO returnEquipment(Long orderId, Integer orderStatus, EquipmentOrderDTO equipmentOrderDTO) {
        //判断订单是否在租借中和租借超时
        EqOrder returnOrder = eqOrderService.getById(orderId);
        if(!returnOrder.getOrderStatus().equals(OrderStatusEnum.RENTING.getStatus())){
            if (!returnOrder.getOrderStatus().equals(OrderStatusEnum.RENT_OVERTIME.getStatus())) {
                throw new RuntimeException("订单状态异常!");
            }
        }

        if(orderStatus.equals(1) && equipmentOrderDTO == null) {
            throw new RuntimeException("损坏列表为空!");
        }
        if (orderStatus.equals(1) && equipmentOrderDTO.getEqOrderDetailList().isEmpty()){
                throw new RuntimeException("损坏列表为空!");
        }

        //获取租借订单详情列表
        QueryWrapper<EqOrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq("order_id",orderId);
        List<EqOrderDetail> orderDetailList = eqOrderDetailService.list(orderDetailQueryWrapper);

        //如果无损坏
        if(orderStatus.equals(0)){
            //更改租借状态
            EqOrder order = getById(orderId);
            order.setOrderStatus(OrderStatusEnum.FINISH.getStatus());
            updateById(order);
            //还原库存
            for (EqOrderDetail eqOrderDetail : orderDetailList) {
                EqInfo equipment = eqInfoService.getById(eqOrderDetail.getEqId());
                equipment.setEqUsedNumber(equipment.getEqUsedNumber() + eqOrderDetail.getEqNumber());
                eqInfoService.updateById(equipment);
            }
        }else {
            //更改租借状态
            EqOrder order = getById(orderId);
            order.setOrderStatus(OrderStatusEnum.FINISH.getStatus());
            updateById(order);
            //还原库存
            for (EqOrderDetail rentOrderDetail : orderDetailList) {
                EqInfo rentEquipment = eqInfoService.getById(rentOrderDetail.getEqId());
                for (EqOrderDetail damageOrderDetail : equipmentOrderDTO.getEqOrderDetailList()) {
                    //判断是否有损伤
                    if (rentOrderDetail.getEqId().equals(damageOrderDetail.getEqId())) {
                        // 有损, 在库库存 - 损伤
                        rentEquipment.setEqUsedNumber(rentEquipment.getEqUsedNumber() - damageOrderDetail.getEqNumber());
                        //写入损伤列表
                        QueryWrapper<EqDamage> damageQueryWrapper = new QueryWrapper<>();
                        damageQueryWrapper.eq("eq_id",damageOrderDetail.getEqId());
                        EqDamage damageEquipment = eqDamageService.getOne(damageQueryWrapper);
                        //判断器材曾经是否损伤过
                        if (damageEquipment == null) {
                            damageEquipment = new EqDamage(damageOrderDetail.getEqId(), damageOrderDetail.getEqNumber());
                            eqDamageService.save(damageEquipment);
                        } else {
                            damageEquipment.setDamageNumber(damageEquipment.getDamageNumber()
                                    + damageOrderDetail.getEqNumber());
                            eqDamageService.updateById(damageEquipment);
                        }
                    }
                }
                // 在库库存 + 租借数量
                rentEquipment.setEqUsedNumber(rentEquipment.getEqUsedNumber() + rentOrderDetail.getEqNumber());
                eqInfoService.updateById(rentEquipment);
            }
        }
        return ResultUtil.Success();
    }

    @Override
    @Transactional
    public ResultDTO findMineOrder(Long userId,Long type) {
        //查询用户的所有订单
        QueryWrapper<EqOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        if(type != null){
            queryWrapper.eq("order_type",type);
        }
        queryWrapper.orderByDesc("order_create_time");
        List<EqOrder> orderList = eqOrderService.list(queryWrapper);
        return ResultUtil.Success(EqOrderList2EquipmentOrderDTOList(orderList));
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long orderId) {
        EqOrder eqOrder = getById(orderId);
        if(eqOrder == null){
            throw new RuntimeException("订单查询异常");
        }
        if(eqOrder.getOrderStatus().equals(OrderStatusEnum.NOT_PAY.getStatus()) ||
                eqOrder.getOrderStatus().equals(OrderStatusEnum.PAY_NOT_RENT.getStatus()) ){
                return  returnNumberAndSetStatus(eqOrder,OrderStatusEnum.ORDER_CANCEL.getStatus());
        }else{
            throw new RuntimeException("订单状态异常");
        }
    }

    @Override
    @Transactional
    public boolean maintenanceFinish(Long orderId) {
        EqOrder eqOrder = getById(orderId);
        if(eqOrder == null){
            throw new RuntimeException("订单查询异常");
        }
        if(eqOrder.getOrderStatus().equals("报修中")){
            return  returnNumberAndSetStatus(eqOrder,OrderStatusEnum.MAINTENANCE_FINISH.getStatus());
        }else{
            throw new RuntimeException("订单状态异常");
        }
    }

    @Override
    @Transactional
    public ResultDTO getAllOrder(Long type,int pageNum, int pageSize) {
        QueryWrapper<EqOrder> queryWrapper = new QueryWrapper<>();
        if(type != null){
            queryWrapper.eq("order_type",type);
        }
        queryWrapper.orderByDesc("order_create_time");
        IPage<EqOrder> orderPage = eqOrderService.page(new Page<>(pageNum, pageSize),queryWrapper);
        IPage<EquipmentOrderDTO> resultPage = new Page<>(pageNum, pageSize);
        BeanUtils.copyProperties(orderPage,resultPage);
        resultPage.setRecords(EqOrderList2EquipmentOrderDTOList(orderPage.getRecords()));
        return ResultUtil.Success(resultPage);
    }

    @Override
    public ResultDTO getOrderByUsername(String username, Long type, int pageNum, int pageSize) {
        //获取userId
        QueryWrapper<AuthUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("user_name",username);
        Long userId = userService.getOne(userQueryWrapper).getUserId();

        QueryWrapper<EqOrder> queryWrapper = new QueryWrapper<>();
        if(type != null){
            queryWrapper.eq("order_type",type);
        }
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("order_create_time");
        IPage<EqOrder> orderPage = eqOrderService.page(new Page<>(pageNum, pageSize),queryWrapper);
        IPage<EquipmentOrderDTO> resultPage = new Page<>(pageNum, pageSize);
        BeanUtils.copyProperties(orderPage,resultPage);
        resultPage.setRecords(EqOrderList2EquipmentOrderDTOList(orderPage.getRecords()));
        return ResultUtil.Success(resultPage);
    }

    @Override
    public ResultDTO getOrderByOrderId(Long orderId) {
        QueryWrapper<EqOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        queryWrapper.orderByDesc("order_create_time");
        List<EqOrder> orderList = eqOrderService.list(queryWrapper);
        return ResultUtil.Success(EqOrderList2EquipmentOrderDTOList(orderList).get(0));
    }

    @Override
    public ResultDTO getProfitEveryMonth(Integer year, Integer month) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, 15, 1, 1, 1);
        LocalDateTime thisMonthFirst = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime thisMonthLast = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        // 获取支出
        QueryWrapper<EqOrder> negativeWrapper = new QueryWrapper<>();
        negativeWrapper.in("order_type", Arrays.asList(3,2));
        negativeWrapper.between("order_create_time",thisMonthFirst,thisMonthLast);
        negativeWrapper.orderByDesc("order_type");
        BigDecimal negativePrice = new BigDecimal(0);
        List<EqOrder> negativeOrders = eqOrderService.list(negativeWrapper);
        if (negativeOrders != null && !negativeOrders.isEmpty()) {
            negativePrice = negativeOrders.stream()
                    .reduce((eqOrder, eqOrder2) ->
                            new EqOrder(eqOrder.getOrderPrice().add(eqOrder2.getOrderPrice()))).get().getOrderPrice();
        }
        // 获取收入
        QueryWrapper<EqOrder> positiveWrapper = new QueryWrapper<>();
        positiveWrapper.eq("order_type", 1);
        positiveWrapper.eq("order_status", "租借完成");
        positiveWrapper.between("order_create_time",thisMonthFirst,thisMonthLast);
        List<EqOrder> positiveOrders = eqOrderService.list(positiveWrapper);
        BigDecimal positivePrice = new BigDecimal(0);
        if (positiveOrders != null && !positiveOrders.isEmpty()) {
            positivePrice = positiveOrders.stream()
                    .reduce((eqOrder, eqOrder2) ->
                            new EqOrder(eqOrder.getOrderPrice().add(eqOrder2.getOrderPrice()))).get().getOrderPrice();
        }
        return ResultUtil.Success(new ProfitDTO(positivePrice,positiveOrders,negativePrice,negativeOrders));
    }

    private boolean returnNumberAndSetStatus(EqOrder eqOrder,String status){
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
        return eqOrderService.updateById(eqOrder);
    }

    private List<EquipmentOrderDTO> EqOrderList2EquipmentOrderDTOList(List<EqOrder> orderList){
        //初始化变量
        List<EquipmentOrderDTO> orderDTOList = new ArrayList<>();
        for (EqOrder order : orderList) {
            //订单简略信息封装
            EquipmentOrderDTO orderDTO = new EquipmentOrderDTO();
            BeanUtils.copyProperties(order,orderDTO);
            orderDTO.setCreateTime(order.getOrderCreateTime());
            orderDTO.setBeginTime(order.getOrderBeginTime());
            orderDTO.setEndTime(order.getOrderEndTime());
            orderDTO.setPrice(order.getOrderPrice());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setOrderType(EnumUtil.getOrderType(order.getOrderType()));
            //订单详情分钟
            QueryWrapper<EqOrderDetail> detailQueryWrapper = new QueryWrapper<>();
            detailQueryWrapper.eq("order_id",order.getOrderId());
            List<EqOrderDetail> orderDetailList = eqOrderDetailService.list(detailQueryWrapper);
            //封装器材名
            for (EqOrderDetail orderDetail : orderDetailList) {
                orderDetail.setEqName(eqInfoService.getById(orderDetail.getEqId()).getEqName());
            }
            orderDTO.setEqOrderDetailList(orderDetailList);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

     /*
    @Override
    public ResultDTO getMaintenanceOrder() {
        //定义变量
        List<EquipmentMaintenanceDTO> maintenanceDTOList = new ArrayList<>();
        List<EquipmentNumberDTO> numberDTOList = new ArrayList<>();
        //获取维护订单
        QueryWrapper<EqOrder> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.in("order_status",OrderStatusEnum.MAINTENANCE.getStatus(),OrderStatusEnum.MAINTENANCE_FINISH.getStatus());
        orderQueryWrapper.eq("order_type",OrderTypeEnum.MAINTENANCE.getTypeId());
        List<EqOrder> orderList = eqOrderService.list(orderQueryWrapper);
        for (EqOrder eqOrder : orderList) {
            EquipmentMaintenanceDTO maintenanceEq = new EquipmentMaintenanceDTO(eqOrder.getOrderId(),
                    eqOrder.getOrderCreateTime(),eqOrder.getOrderStatus(),eqOrder.getOrderPrice());
            maintenanceDTOList.add(maintenanceEq);
        }
        //获取所有order的ID
        List<Long> orderIdList = orderList.stream().map(EqOrder::getOrderId).collect(Collectors.toList());
        //获得订单详情
        QueryWrapper<EqOrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.in("order_id",orderIdList);
        List<EqOrderDetail> orderDetailList = eqOrderDetailService.list(orderDetailQueryWrapper);
        for (EqOrderDetail orderDetail : orderDetailList) {
            for (EquipmentMaintenanceDTO maintenanceDTO : maintenanceDTOList) {
                if (maintenanceDTO.getOrderId().equals(orderDetail.getOrderId())) {
                    EquipmentNumberDTO equipmentNumberDTO = new EquipmentNumberDTO(orderDetail.getEqId(),
                            eqInfoService.getById(orderDetail.getEqId()).getEqName(),orderDetail.getEqNumber());
                    numberDTOList.add(equipmentNumberDTO);
                }
                maintenanceDTO.setEquipmentNumberDTO(numberDTOList);
            }
        }
        return ResultUtil.Success(maintenanceDTOList);
    }*/

}
