package team.rjgc.GymSys.entity.area;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AreaUse extends Model<AreaUse> {

    private static final long serialVersionUID = 1L;

    /**
     * 场地使用id
     */
    @TableId(value = "area_use_id", type = IdType.AUTO)
    private Integer areaUseId;

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


    @Override
    protected Serializable pkVal() {
        return this.areaUseId;
    }

}
