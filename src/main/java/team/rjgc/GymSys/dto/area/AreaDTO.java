package team.rjgc.GymSys.dto.area;

import lombok.Data;

//新增场地/查询场地对象
@Data
public class AreaDTO {
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

}
