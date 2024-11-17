package caodong0225.order.controller;

import caodong0225.common.entity.Goods;
import caodong0225.common.entity.OrderDetail;
import caodong0225.common.entity.Orders;
import caodong0225.common.entity.UserInfo;
import caodong0225.common.util.JwtUtil;
import caodong0225.common.vo.GoodsOrderedVO;
import caodong0225.common.vo.OrderInfoVO;
import caodong0225.order.service.IGoodsService;
import caodong0225.order.service.IOrderDetailService;
import caodong0225.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final IGoodsService goodsService;
    private final IOrderDetailService orderDetailService;

    @GetMapping("/list")
    @Operation(summary = "获取订单列表", description = "获取当前用户所有下单的列表")
    public ResponseEntity<List<OrderInfoVO>> getOrderList(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ResponseEntity.status(403).build(); // 返回 403 状态码
        }
        UserInfo userInfo = JwtUtil.parseToken(token);
        List<Orders> orderList = orderService.getOrdersByUserId(userInfo.getId());
        List<OrderInfoVO> orderInfoList = new ArrayList<>();
        orderList.forEach(order -> {
            OrderInfoVO orderInfoVO = new OrderInfoVO();
            orderInfoVO.setId(order.getId());
            orderInfoVO.setOrderNo(order.getNumber());
            orderInfoVO.setStatus(order.getStatus());
            orderInfoVO.setTotalPrice(order.getAmount());
            orderInfoVO.setCreateTime(order.getOrderTime());
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderId(order.getId());
            List<GoodsOrderedVO> goodsOrderedVOList = new ArrayList<>();
            orderDetailList.forEach(orderDetail -> {
                GoodsOrderedVO goodsOrderedVO = new GoodsOrderedVO();
                Goods goodsInfo = goodsService.getGoodsById(orderDetail.getGoodsId());
                goodsOrderedVO.setId(goodsInfo.getId());
                goodsOrderedVO.setGoodsName(goodsInfo.getName());
                goodsOrderedVO.setPrice(goodsInfo.getPrice());
                goodsOrderedVO.setNumber(orderDetail.getNum());
                goodsOrderedVO.setTotalPrice(goodsInfo.getPrice() * orderDetail.getNum());
                goodsOrderedVOList.add(goodsOrderedVO);
            });
            orderInfoVO.setOrderDetailInfoVOList(goodsOrderedVOList);
            orderInfoList.add(orderInfoVO);
        });
        return ResponseEntity.ok(orderInfoList);
    }
}
