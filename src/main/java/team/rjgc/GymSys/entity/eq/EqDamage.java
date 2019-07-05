package team.rjgc.GymSys.entity.eq;

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
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EqDamage extends Model<EqDamage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "eq_damage_id", type = IdType.AUTO)
    private Long eqDamageId;

    private Long eqId;

    private Integer damageNumber;

    public EqDamage() {
    }

    public EqDamage(Long eqId, Integer damageNumber) {
        this.eqId = eqId;
        this.damageNumber = damageNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.eqDamageId;
    }

}
