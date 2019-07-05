package team.rjgc.GymSys.entity.area;

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
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AreaType extends Model<AreaType> {

    private static final long serialVersionUID = 1L;

    /**
     * 场地类型id
     */
    @TableId(value = "area_type_id", type = IdType.AUTO)
    private Integer areaTypeId;

    /**
     * 场地类型名字
     */
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.areaTypeId;
    }

}
