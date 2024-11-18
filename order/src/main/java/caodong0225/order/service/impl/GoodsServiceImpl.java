package caodong0225.order.service.impl;

import caodong0225.common.entity.Goods;
import caodong0225.common.entity.OrderDetail;
import caodong0225.order.mapper.GoodsMapper;
import caodong0225.order.service.IGoodsService;
import caodong0225.order.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Override
    public List<Goods> getAllGoods() {
        return this.lambdaQuery().list();
    }

    @Override
    public Goods getGoodsById(Integer goodsId) {
        return this.getById(goodsId);
    }


}
