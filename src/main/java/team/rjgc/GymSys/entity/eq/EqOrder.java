package team.rjgc.GymSys.entity.eq;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2019-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EqOrder extends Model<EqOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    private Long userId;

    private BigDecimal orderPrice;

    /**
     * 订单类型
     */
    private Long orderType;

    /**
     * 租借开始时间
     */
    private LocalDateTime orderBeginTime;

    /**
     * 订单创建时间
     */
    private LocalDateTime orderCreateTime;

    /**
     * 租借结束时间
     */
    private LocalDateTime orderEndTime;

    /**
     * 状态
     */
    private String orderStatus;

    public EqOrder() {
    }

    public EqOrder(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public EqOrder(Long userId, BigDecimal orderPrice, LocalDateTime purchaseTime) {
        this.userId = userId;
        this.orderPrice = orderPrice;
        this.orderCreateTime = purchaseTime;
    }

    public EqOrder(Long userId, BigDecimal orderPrice, Long orderType, LocalDateTime orderCreateTime, String orderStatus) {
        this.userId = userId;
        this.orderPrice = orderPrice;
        this.orderType = orderType;
        this.orderCreateTime = orderCreateTime;
        this.orderStatus = orderStatus;
    }

    public EqOrder(Long userId, BigDecimal orderPrice, Long orderType, LocalDateTime orderBeginTime, LocalDateTime orderCreateTime, LocalDateTime orderEndTime, String orderStatus) {
        this.userId = userId;
        this.orderPrice = orderPrice;
        this.orderType = orderType;
        this.orderBeginTime = orderBeginTime;
        this.orderCreateTime = orderCreateTime;
        this.orderEndTime = orderEndTime;
        this.orderStatus = orderStatus;
    }

    public EqOrder(Long userId, Long orderType, LocalDateTime orderCreateTime) {
        this.userId = userId;
        this.orderType = orderType;
        this.orderCreateTime = orderCreateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
