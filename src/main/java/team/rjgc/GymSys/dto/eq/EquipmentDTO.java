package team.rjgc.GymSys.dto.eq;

import lombok.Data;
import team.rjgc.GymSys.entity.eq.EqInfo;

import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/6/17 21:04
 **/
@Data
public class EquipmentDTO {

    private List<EqInfo> equipmentList;

}
