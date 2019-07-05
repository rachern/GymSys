package team.rjgc.GymSys.enums;

import lombok.Getter;
import org.springframework.core.annotation.Order;

/**
 * @author ReMidDream
 * @date 2019/6/17 15:47
 **/
@Getter
public enum OrderTypeEnum {

    RENT(1L,"器材租借"),
    PURCHASE(2L,"器材购置"),
    MAINTENANCE(3L,"器材维护"),
    DELETE(4L,"器材报废")
    ;

    private Long typeId;

    private String type;

    OrderTypeEnum(Long typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }


}
