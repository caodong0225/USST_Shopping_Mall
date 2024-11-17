package caodong0225.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jyzxc
 * @since 2024-11-15
 */
@Getter
@Setter
public class Orders implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "用户ID", example = "1")
    private Integer id;

    /**
     * 订单号
     */
    @Schema(description = "订单号", example = "20241114000001")
    private String number;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款", example = "1")
    private Integer status;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1")
    private Integer userId;

    /**
     * 用户备注
     */
    @Schema(description = "用户备注", example = "请尽快送达")
    private String comment;

    /**
     * 收款金额
     */
    @Schema(description = "收款金额", example = "100.00")
    private Double amount;

    /**
     * 创建时间
     */
    @Schema(description = "下单时间", example = "1720524833000L")
    private LocalDateTime orderTime;
}
