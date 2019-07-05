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
public class AreaNotice extends Model<AreaNotice> {

    private static final long serialVersionUID = 1L;

    /**
     * 场地公告id
     */
    @TableId(value = "area_notice_id", type = IdType.AUTO)
    private Integer areaNoticeId;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告信息
     */
    private String message;

    /**
     * 工作人员id
     */
    private Long userId;


    @Override
    protected Serializable pkVal() {
        return this.areaNoticeId;
    }

}
