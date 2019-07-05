package team.rjgc.GymSys.dto.eq;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/6/18 19:28
 **/
@Data
public class EquipmentMaintenanceDTO {


    private Long orderId;

    private LocalDateTime orderCreateTime;

    private String orderStatus;

    private BigDecimal eqOrderPrice;

    private List<EquipmentNumberDTO> equipmentNumberDTO;

    public EquipmentMaintenanceDTO() {
    }

    public EquipmentMaintenanceDTO(Long orderId, LocalDateTime orderCreateTime, String orderStatus, BigDecimal eqOrderPrice) {
        this.orderId = orderId;
        this.orderCreateTime = orderCreateTime;
        this.orderStatus = orderStatus;
        this.eqOrderPrice = eqOrderPrice;
    }
}
