package caodong0225.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@Getter
@Setter
public class OrderDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer orderId;
    private Integer goodsId;
    private Integer num;
    private Double amount;
}
