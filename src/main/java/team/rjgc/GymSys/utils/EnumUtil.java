package team.rjgc.GymSys.utils;

import team.rjgc.GymSys.enums.OrderTypeEnum;

/**
 * @author ReMidDream
 * @date 2019/6/18 22:34
 **/
public class EnumUtil {

    public static String getOrderType(Long typeId){
        for (OrderTypeEnum value : OrderTypeEnum.values()) {
            if (value.getTypeId().equals(typeId)) {
                return value.getType();
            }
        }
        return "无效类型";
    }

}
