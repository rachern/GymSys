package team.rjgc.GymSys.dto.match;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MatchInfoDTO {
    /**
     * 裁判id
     */
    private Integer matchId;

    private Integer refereeId;

    private String refereeName;

    /**
     * 场地预定id
     */
    private Integer areaBookId;

    private String areaName;

    /**
     * 比赛名称
     */
    private String title;

    /**
     * 比赛内容
     */
    private String message;

    public MatchInfoDTO(String refereeName, String areaName) {
        this.refereeName = refereeName;
        this.areaName = areaName;
    }
}
