package caodong0225.common.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
public class OrderDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int orderId;

    private int goodsId;
    private int num;
    private double amount;
}
