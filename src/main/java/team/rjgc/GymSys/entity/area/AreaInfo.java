package team.rjgc.GymSys.entity.area;


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
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AreaInfo extends Model<AreaInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 场地id
     */
    @TableId(value = "area_id", type = IdType.AUTO)
    private Integer areaId;

    /**
     * 场地名字
     */
    private String name;

    /**
     * 是不是校队预留
     */
    private Integer teamed;

    /**
     * 是不是上课使用场地
     */
    private Integer classed;

    /**
     * 收费标准
     */
    private String money;

    /**
     * 场地类型id
     */
    private Integer areaTypeId;


    @Override
    protected Serializable pkVal() {
        return this.areaId;
    }

}
