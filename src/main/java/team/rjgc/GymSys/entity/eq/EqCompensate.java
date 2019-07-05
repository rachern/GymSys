package team.rjgc.GymSys.entity.eq;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2019-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EqCompensate extends Model<EqCompensate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "compensate_id", type = IdType.AUTO)
    private Long compensateId;

    private Long eqId;

    @TableField(exist = false)
    private String eqName;

    private BigDecimal compensatePrice;


    @Override
    protected Serializable pkVal() {
        return this.compensateId;
    }

}
