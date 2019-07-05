package team.rjgc.GymSys.entity.match;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MatchInfo extends Model<MatchInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛id
     */
    @TableId(value = "match_id", type = IdType.AUTO)
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

    /**
     * 申请人id
     */
    private Long registerId;


    @Override
    protected Serializable pkVal() {
        return this.matchId;
    }

}
