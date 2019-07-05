package team.rjgc.GymSys.service.eq;

import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EquipmentOrderDTO;
import team.rjgc.GymSys.entity.eq.EqOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-17
 */
public interface EqOrderService extends IService<EqOrder> {

    ResultDTO returnEquipment(Long orderId, Integer orderStatus, EquipmentOrderDTO equipmentOrderDTO);

    ResultDTO findMineOrder(Long userId,Long type);

    boolean cancelOrder(Long orderId);

    boolean maintenanceFinish(Long orderId);

//    ResultDTO getMaintenanceOrder();

    ResultDTO getAllOrder(Long type,int pageNum, int pageSize);

    ResultDTO getOrderByUsername(String username, Long type, int pageNum, int pageSize);

    ResultDTO getOrderByOrderId(Long orderId);

    ResultDTO getProfitEveryMonth(Integer year, Integer month);

}
