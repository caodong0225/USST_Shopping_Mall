package caodong0225.order.service;

import caodong0225.common.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
public interface IGoodsService extends IService<Goods> {
    List<Goods> getAllGoods();
    // 商品剩余库存
    Integer goodsStock(Integer goodsId);
    // 通过商品ID获取商品信息
    Goods getGoodsById(Integer goodsId);
}
