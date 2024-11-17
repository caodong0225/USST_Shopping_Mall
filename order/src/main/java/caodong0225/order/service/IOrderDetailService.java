package caodong0225.order.service;

import caodong0225.common.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
public interface IOrderDetailService extends IService<OrderDetail> {
    List<OrderDetail> getOrderDetailByOrderId(Integer orderId);
}
