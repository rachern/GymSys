package team.rjgc.GymSys.dto.match;

import lombok.Data;

@Data
public class MatchInfoSelectDTO {

    /**
     * 裁判id
     */
    private Integer refereeId;

    /**
     * 场地预定id
     */
    private Integer areaBookId;

    /**
     * 比赛名称
     */
    private String title;

    /**
     * 比赛内容
     */
    private String message;
    /**
     * 申请人id
     */
    private Long registerId;
}
