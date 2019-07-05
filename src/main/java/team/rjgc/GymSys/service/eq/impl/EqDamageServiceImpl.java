package team.rjgc.GymSys.service.eq.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import team.rjgc.GymSys.dto.eq.EquipmentNumberDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.entity.eq.EqDamage;
import team.rjgc.GymSys.entity.eq.EqInfo;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;
import team.rjgc.GymSys.mapper.eq.EqDamageMapper;
import team.rjgc.GymSys.service.eq.EqDamageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.rjgc.GymSys.service.eq.EqInfoService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-18
 */
@Service
public class EqDamageServiceImpl extends ServiceImpl<EqDamageMapper, EqDamage> implements EqDamageService {

    @Autowired
    private EqInfoService eqInfoService;
    @Autowired
    private EqDamageMapper eqDamageMapper;

    @Override
    @Transactional
    public boolean setDamageEquipment(EquipmentOrderDTO equipmentOrderDTO) {
        for (EqOrderDetail orderDetail : equipmentOrderDTO.getEqOrderDetailList()) {
            //减损伤列表
            QueryWrapper<EqDamage> damageQueryWrapper = new QueryWrapper<>();
            damageQueryWrapper.eq("eq_id",orderDetail.getEqId());
            EqDamage damageEquipment = getOne(damageQueryWrapper);
            //判断器材曾经是否损伤过
            if (damageEquipment == null) {
                damageEquipment = new EqDamage(orderDetail.getEqId(), orderDetail.getEqNumber());
                save(damageEquipment);
            } else {
                damageEquipment.setDamageNumber(damageEquipment.getDamageNumber()
                        + orderDetail.getEqNumber());
                updateById(damageEquipment);
            }
            //减库存
            EqInfo equipment = eqInfoService.getById(orderDetail.getEqId());
            if (equipment == null) {
                throw new RuntimeException("器材信息错误!");
            }else{
                if(orderDetail.getEqNumber() > equipment.getEqUsedNumber()){
                    throw new RuntimeException("损坏数量有误!");
                }
                equipment.setEqUsedNumber(equipment.getEqUsedNumber() - orderDetail.getEqNumber());
                eqInfoService.updateById(equipment);
            }
        }
        return true;
    }

    @Override
    public List<EquipmentNumberDTO> findDamageEquipment() {
        return eqDamageMapper.findDamageEquipment();
    }

}
