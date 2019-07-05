package team.rjgc.GymSys.enums;

import lombok.Getter;

/**
 * @author ReMidDream
 * @date 2019/6/17 15:47
 **/
@Getter
public enum OrderStatusEnum {

    NOT_PAY("未支付"),
    PAY_NOT_RENT("已支付未到租借时间"),
    RENTING("租借中"),
    FINISH("租借完成"),
    RENT_OVERTIME("租借超时未归还"),
    ORDER_OVERTIME("订单超时"),
    ORDER_CANCEL("取消订单"),
    MAINTENANCE("报修中"),
    MAINTENANCE_FINISH("维修结束"),
    PURCHASE_FINISH("购置完成")
    ;


    private String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

}
