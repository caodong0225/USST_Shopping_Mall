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

    Integer goodsStock(Integer goodsId);

}
