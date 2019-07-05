package team.rjgc.GymSys.dto.area;

import lombok.Data;

@Data
public class AreaShowDTO {
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
     * 场地类型
     */
    private String areaType;
}
