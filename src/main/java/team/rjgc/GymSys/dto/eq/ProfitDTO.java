package team.rjgc.GymSys.dto.eq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.rjgc.GymSys.entity.eq.EqOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/6/27 19:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitDTO {

    private BigDecimal positivePrice;

    private List<EqOrder> positiveOrderList;

    private BigDecimal negativePrice;

    private List<EqOrder> negativeOrderList;
}
