package team.rjgc.GymSys.dto.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AreaUseUpdateDTO {
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
}
