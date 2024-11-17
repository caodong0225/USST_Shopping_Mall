package caodong0225.order.service.impl;

import caodong0225.common.entity.Orders;
import caodong0225.order.mapper.OrderMapper;
import caodong0225.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements IOrderService {
    @Override
    public List<Orders> getOrdersByUserId(Integer userId) {
        return this.lambdaQuery().eq(Orders::getUserId, userId).list();
    }
}
