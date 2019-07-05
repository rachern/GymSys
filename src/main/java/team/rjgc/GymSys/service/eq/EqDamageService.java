package team.rjgc.GymSys.service.eq;

import team.rjgc.GymSys.dto.eq.EquipmentNumberDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.entity.eq.EqDamage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-18
 */
public interface EqDamageService extends IService<EqDamage> {

    boolean setDamageEquipment(EquipmentOrderDTO equipmentOrderDTO);

    List<EquipmentNumberDTO> findDamageEquipment();

}
