package team.rjgc.GymSys.dto.area;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AreaBookShowDTO {


    private Integer bookId;

    private Integer areaId;
    /**
     * 预约开始时间
     */
    private LocalDateTime bookStartTime;

    /**
     * 预约结束时间
     */
    private LocalDateTime bookEndTime;

    /**
     * 预约学生名字
     */
    private String userName;


    /**
     * 场地名字
     */
    private String areaName;

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


    private String areaTypeName;

}
