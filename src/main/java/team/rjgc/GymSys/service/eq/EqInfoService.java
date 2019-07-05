package team.rjgc.GymSys.service.eq;

import org.springframework.scheduling.annotation.Scheduled;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.entity.eq.EqInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-17
 */
public interface EqInfoService extends IService<EqInfo> {

    ResultDTO purchaseEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO);

    ResultDTO rentEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO);

    ResultDTO maintenanceEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO);

    ResultDTO deleteEquipment(Long userId, EquipmentOrderDTO equipmentOrderDTO);


}
