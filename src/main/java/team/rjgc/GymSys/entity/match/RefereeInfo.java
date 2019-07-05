package team.rjgc.GymSys.entity.match;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2019-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RefereeInfo extends Model<RefereeInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "referee_id", type = IdType.AUTO)
    private Integer refereeId;

    private String name;

    private String message;


    @Override
    protected Serializable pkVal() {
        return this.refereeId;
    }

}
