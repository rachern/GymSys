package team.rjgc.GymSys.entity.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class AreaBook extends Model<AreaBook> {

    private static final long serialVersionUID = 1L;

    /**
     * 预约信息id
     */
    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    /**
     * 场地id
     */
    private Integer areaId;

    /**
     * 预约开始时间
     */
    private LocalDateTime bookStartTime;

    /**
     * 预约学生id
     */
    private Long userId;

    /**
     * 预约结束时间
     */
    private LocalDateTime bookEndTime;


    @Override
    protected Serializable pkVal() {
        return this.bookId;
    }

}
