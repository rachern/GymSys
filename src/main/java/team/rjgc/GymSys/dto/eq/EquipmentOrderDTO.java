package team.rjgc.GymSys.dto.eq;

import lombok.Data;
import team.rjgc.GymSys.entity.eq.EqOrderDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/6/17 16:11
 **/
@Data
public class EquipmentOrderDTO {

    private Long orderId;

    private BigDecimal price;

    private LocalDateTime createTime;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String orderStatus;

    private String orderType;

    private List<EqOrderDetail> eqOrderDetailList;
}
