package team.rjgc.GymSys.entity.eq;

import java.math.BigDecimal;
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
 * @since 2019-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EqInfo extends Model<EqInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 器材ID
     */
    @TableId(value = "eq_id", type = IdType.AUTO)
    private Long eqId;

    /**
     * 器材名
     */
    private String eqName;

    /**
     * 器材总数
     */
    private Integer eqTotalNumber;

    /**
     * 器材在库数量
     */
    private Integer eqUsedNumber;

    /**
     * 租借价格
     */
    private BigDecimal eqPrice;

    public EqInfo(String eqName, Integer eqTotalNumber, Integer eqUsedNumber) {
        this.eqName = eqName;
        this.eqTotalNumber = eqTotalNumber;
        this.eqUsedNumber = eqUsedNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.eqId;
    }

}
