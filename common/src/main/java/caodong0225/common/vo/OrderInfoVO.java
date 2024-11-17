package caodong0225.common.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-17
 */
@Getter
@Setter
public class OrderInfoVO {
    // 订单ID
    private Integer id;
    // 订单流水号
    private String orderNo;
    // 订单状态
    private Integer status;
    // 订单总价
    private Double totalPrice;
    // 订单创建时间
    private LocalDateTime createTime;
    // 订单商品列表
    private List<GoodsOrderedVO> orderDetailInfoVOList;
}
