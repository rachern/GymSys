package team.rjgc.GymSys.dto.area;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AreaUseSelectDTO {
    /**
     * 场地预约信息id
     */
    private Integer areaBookId;

    /**
     * 场地使用开始时间
     */
    private LocalDateTime useStartTime;

    /**
     * 场地使用结束时间
     */
    private LocalDateTime useEndTime;

    /**
     * 共花费多少钱
     */
    private String money;
}
