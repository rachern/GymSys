package team.rjgc.GymSys.dto.match;

import lombok.Data;

@Data
public class MatchInfoUpdateDTO {
    /**
     * 比赛id
     */
    private Integer matchId;

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
}
