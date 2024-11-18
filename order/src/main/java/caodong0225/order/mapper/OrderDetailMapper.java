package caodong0225.order.mapper;

import caodong0225.common.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    @Select("SELECT SUM(num) FROM order_detail WHERE goods_id = #{goodsId}")
    Integer goodsSold(Integer goodsId);
}
