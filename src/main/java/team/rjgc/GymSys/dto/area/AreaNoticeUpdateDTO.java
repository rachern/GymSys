package team.rjgc.GymSys.dto.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class AreaNoticeUpdateDTO {
    /**
     * 场地公告id
     */
    private Integer areaNoticeId;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告信息
     */
    private String message;
}
