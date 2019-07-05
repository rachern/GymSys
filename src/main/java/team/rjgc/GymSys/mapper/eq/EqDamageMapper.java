package team.rjgc.GymSys.mapper.eq;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import team.rjgc.GymSys.dto.eq.EquipmentNumberDTO;
import team.rjgc.GymSys.entity.eq.EqDamage;

import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/6/18 15:26
 **/
public interface EqDamageMapper extends BaseMapper<EqDamage> {

    List<EquipmentNumberDTO> findDamageEquipment();

}
