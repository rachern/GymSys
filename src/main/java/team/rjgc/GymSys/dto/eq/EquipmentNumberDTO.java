package team.rjgc.GymSys.dto.eq;

import lombok.Data;

/**
 * @author ReMidDream
 * @date 2019/6/18 16:14
 **/
@Data
public class EquipmentNumberDTO {

    private Long eqId;

    private String eqName;

    private Integer number;

    public EquipmentNumberDTO() {
    }

    public EquipmentNumberDTO(Long eqId, String eqName, Integer number) {
        this.eqId = eqId;
        this.eqName = eqName;
        this.number = number;
    }
}
