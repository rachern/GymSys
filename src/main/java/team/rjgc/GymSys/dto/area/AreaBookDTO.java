package team.rjgc.GymSys.dto.area;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AreaBookDTO {
    /**
     * 场地id
     */
    private Integer areaId;

    /**
     * 预约开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookStartTime;

    /**
     * 预约学生id
     */
    private Long userId;

    /**
     * 预约结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookEndTime;
}
