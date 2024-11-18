package caodong0225.order.service;

import caodong0225.common.entity.OrderDetail;
import caodong0225.common.entity.Orders;

import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
public interface IOrderService {
    List<Orders> getOrdersByUserId(Integer userId);
    boolean insertOrder(Orders order);
    boolean createOrderWithDetails(Orders order, List<OrderDetail> orderDetails);
    Long countTotalOrders();
    Orders getOrderByTraceNo(String traceId);
}
