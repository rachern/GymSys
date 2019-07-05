package team.rjgc.GymSys.service.eq.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.entity.eq.EqDamage;
import team.rjgc.GymSys.entity.eq.EqInfo;
import team.rjgc.GymSys.entity.eq.EqOrder;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;
import team.rjgc.GymSys.enums.OrderStatusEnum;
import team.rjgc.GymSys.enums.OrderTypeEnum;
import team.rjgc.GymSys.mapper.eq.EqInfoMapper;
import team.rjgc.GymSys.service.eq.EqDamageService;
import team.rjgc.GymSys.service.eq.EqInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.rjgc.GymSys.service.eq.EqOrderDetailService;
import team.rjgc.GymSys.service.eq.EqOrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-17
 */
@Service
public class EqInfoServiceImpl extends ServiceImpl<EqInfoMapper, EqInfo> implements EqInfoService {

    @Autowired
    private EqOrderDetailService eqOrderDetailService;
    @Autowired
    private EqOrderService eqOrderService;
    @Autowired
    private  EqInfoService eqInfoService;
    @Autowired
    private EqDamageService eqDamageService;

    @Override
    @Transactional
    public ResultDTO purchaseEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO) {
        List<EqInfo> newEquipmentList = new ArrayList<>();
        // 写入订单
        EqOrder eqOrder = new EqOrder(userId, equipmentOrderDTO.getPrice(), equipmentOrderDTO.getCreateTime());
        eqOrder.setOrderType(OrderTypeEnum.PURCHASE.getTypeId());
        eqOrderService.save(eqOrder);

        for (EqOrderDetail eqOrderDetail : equipmentOrderDTO.getEqOrderDetailList()) {
            //设置订单ID
            eqOrderDetail.setOrderId(eqOrder.getOrderId());
            // 判断是否为新器材
            EqInfo existentEq = eqInfoService.getById(eqOrderDetail.getEqId());
            //若是新器材
            if (eqOrderDetail.getEqId() == null) {
                EqInfo eqInfo = new EqInfo(eqOrderDetail.getEqName(),eqOrderDetail.getEqNumber(),eqOrderDetail.getEqNumber());
                eqInfoService.save(eqInfo);
                eqOrderDetail.setEqId(eqInfo.getEqId());
            }else{
                existentEq.setEqTotalNumber(existentEq.getEqTotalNumber()+eqOrderDetail.getEqNumber());
                existentEq.setEqUsedNumber(existentEq.getEqUsedNumber()+eqOrderDetail.getEqNumber());
                newEquipmentList.add(existentEq);
            }

        }
        // 判断是否全部为新器材, 然后更新器材和库存
        if(!newEquipmentList .isEmpty()){
            eqInfoService.saveOrUpdateBatch(newEquipmentList);
        }
        //写入订单详情
        eqOrderDetailService.saveBatch(equipmentOrderDTO.getEqOrderDetailList());
            return ResultUtil.Success();
    }

    @Override
    @Transactional
    public ResultDTO rentEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO) {

        List<EqInfo> equipmentList = new ArrayList<>();
        // 写入订单
        EqOrder eqOrder = new EqOrder(userId, equipmentOrderDTO.getPrice(), OrderTypeEnum.RENT.getTypeId(),
                equipmentOrderDTO.getBeginTime(), equipmentOrderDTO.getCreateTime(),
                equipmentOrderDTO.getEndTime(), OrderStatusEnum.NOT_PAY.getStatus());
        eqOrderService.save(eqOrder);
        //获取订单ID, 写入订单详情
        for (EqOrderDetail eqOrderDetail : equipmentOrderDTO.getEqOrderDetailList()) {
            eqOrderDetail.setOrderId(eqOrder.getOrderId());
            //减库存\
            EqInfo rentEquipment = eqInfoService.getById(eqOrderDetail.getEqId());
            rentEquipment.setEqUsedNumber(rentEquipment.getEqUsedNumber() - eqOrderDetail.getEqNumber());
            equipmentList.add(rentEquipment);
        }
        eqOrderDetailService.saveBatch(equipmentOrderDTO.getEqOrderDetailList());
        eqInfoService.updateBatchById(equipmentList);
        equipmentOrderDTO.setOrderId(eqOrder.getOrderId());
        return ResultUtil.Success(equipmentOrderDTO);
    }

    @Override
    @Transactional
    public ResultDTO maintenanceEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO) {
        // 写入订单
        EqOrder eqOrder = new EqOrder(userId, equipmentOrderDTO.getPrice(), OrderTypeEnum.MAINTENANCE.getTypeId(),
                equipmentOrderDTO.getCreateTime(), OrderStatusEnum.MAINTENANCE.getStatus());
        eqOrderService.save(eqOrder);
        //获取订单ID, 写入订单详情
        for (EqOrderDetail eqOrderDetail : equipmentOrderDTO.getEqOrderDetailList()) {
            eqOrderDetail.setOrderId(eqOrder.getOrderId());
            //减损伤列表
            QueryWrapper<EqDamage> damageQueryWrapper = new QueryWrapper<>();
            damageQueryWrapper.eq("eq_id",eqOrderDetail.getEqId());
            EqDamage damageEquipment = eqDamageService.getOne(damageQueryWrapper);
            //更改损坏数量
            damageEquipment.setDamageNumber(damageEquipment.getDamageNumber() - eqOrderDetail.getEqNumber());
            //更新损坏
            eqDamageService.updateById(damageEquipment);
        }
        eqOrderDetailService.saveBatch(equipmentOrderDTO.getEqOrderDetailList());
        return ResultUtil.Success();
    }

    @Override
    @Transactional
    public ResultDTO deleteEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO) {
        List<EqInfo> equipmentList = new ArrayList<>();
        // 写入订单
        EqOrder eqOrder = new EqOrder(userId, OrderTypeEnum.DELETE.getTypeId(),
                equipmentOrderDTO.getCreateTime());
        eqOrderService.save(eqOrder);
        //获取订单ID, 写入订单详情
        for (EqOrderDetail eqOrderDetail : equipmentOrderDTO.getEqOrderDetailList()) {
            eqOrderDetail.setOrderId(eqOrder.getOrderId());
            //减库存\
            EqInfo rentEquipment = eqInfoService.getById(eqOrderDetail.getEqId());
            rentEquipment.setEqTotalNumber(rentEquipment.getEqTotalNumber() - eqOrderDetail.getEqNumber());
            equipmentList.add(rentEquipment);
            //减损伤列表
            QueryWrapper<EqDamage> damageQueryWrapper = new QueryWrapper<>();
            damageQueryWrapper.eq("eq_id",eqOrderDetail.getEqId());
            EqDamage damageEquipment = eqDamageService.getOne(damageQueryWrapper);
            //更改损坏数量
            damageEquipment.setDamageNumber(damageEquipment.getDamageNumber() - eqOrderDetail.getEqNumber());
            //更新损坏
            eqDamageService.updateById(damageEquipment);
        }
        eqOrderDetailService.saveBatch(equipmentOrderDTO.getEqOrderDetailList());
        eqInfoService.updateBatchById(equipmentList);
        return ResultUtil.Success();
    }




}
