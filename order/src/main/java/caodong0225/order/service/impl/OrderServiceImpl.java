package caodong0225.order.service.impl;

import caodong0225.common.entity.OrderDetail;
import caodong0225.common.entity.Orders;
import caodong0225.order.mapper.OrderMapper;
import caodong0225.order.service.IOrderDetailService;
import caodong0225.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements IOrderService {
    private final IOrderDetailService orderDetailService;

    public OrderServiceImpl(IOrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @Override
    public List<Orders> getOrdersByUserId(Integer userId) {
        return this.lambdaQuery().eq(Orders::getUserId, userId).list();
    }

    @Override
    public boolean insertOrder(Orders order) {
        return this.save(order);
    }

    @Override
    @Transactional
    public boolean createOrderWithDetails(Orders order, List<OrderDetail> orderDetails) {
        // 插入订单
        boolean orderInserted = insertOrder(order);
        if (!orderInserted) {
            return false;
        }

        // 插入订单详情
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getId()); // 设置订单ID
            boolean detailInserted = orderDetailService.save(orderDetail);
            if (!detailInserted) {
                throw new RuntimeException("订单详情保存失败，事务回滚"); // 抛出异常触发事务回滚
            }
        }
        return true;
    }
}
