package caodong0225.common.vo;

import caodong0225.common.entity.Goods;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@Getter
@Setter
public class GoodsInfoVO {
    // 商品信息
    private Goods goods;
    // 商品已卖数量
    private Integer sold;
    // 商品剩余数量
    private Integer number;

    public GoodsInfoVO(Goods goods, Integer sold) {
        this.goods = goods;
        this.sold = sold;
        this.number = goods.getStock() - sold;
    }
}
