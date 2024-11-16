package caodong0225.order.service.impl;

import caodong0225.common.entity.OrderDetail;
import caodong0225.order.mapper.OrderDetailMapper;
import caodong0225.order.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
}
