package caodong0225.common.vo;


import lombok.Getter;
import lombok.Setter;

/**
 * @author jyzxc
 * @since 2024-11-17
 */
@Getter
@Setter
public class GoodsOrderedVO {
    // 商品id
    private Integer id;
    // 商品名称
    private String goodsName;
    // 商品单价
    private Double price;
    // 购买数量
    private Integer number;
    // 总金额
    private Double totalPrice;
}
