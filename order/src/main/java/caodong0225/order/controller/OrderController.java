package caodong0225.order.controller;

import caodong0225.common.dto.CreateOrderDTO;
import caodong0225.common.entity.Goods;
import caodong0225.common.entity.OrderDetail;
import caodong0225.common.entity.Orders;
import caodong0225.common.entity.UserInfo;
import caodong0225.common.response.BaseDataResponse;
import caodong0225.common.response.BaseResponse;
import caodong0225.common.response.GeneralDataResponse;
import caodong0225.common.util.JwtUtil;
import caodong0225.common.vo.GoodsOrderedVO;
import caodong0225.common.vo.OrderInfoVO;
import caodong0225.order.service.IGoodsService;
import caodong0225.order.service.IOrderDetailService;
import caodong0225.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    public ResponseEntity<GeneralDataResponse<List<OrderInfoVO>>> getOrderList(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(new GeneralDataResponse<>(403, "您还未登录"));
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
        return ResponseEntity.ok(new GeneralDataResponse<>(orderInfoList));
    }

    @PostMapping("/create")
    @Operation(summary = "创建订单", description = "创建一个新的订单")
    public ResponseEntity<BaseResponse> createOrder(
            HttpServletRequest request,
            @Valid @RequestBody List<CreateOrderDTO> createOrderDTOList
    ) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(new BaseDataResponse(403, "您还未登录"));
        }
        // TODO: 获取订单流水号
        UserInfo userInfo = JwtUtil.parseToken(token);
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        createOrderDTOList.forEach(createOrderDTO -> {
            Goods goodsInfo = goodsService.getGoodsById(createOrderDTO.getGoodsId());
            totalPrice.updateAndGet(v -> v + goodsInfo.getPrice() * createOrderDTO.getNumber());
        });
        Orders order = new Orders();
        order.setUserId(userInfo.getId());
        order.setAmount(totalPrice.get());
        order.setStatus(1);
        List<OrderDetail> orderDetails = new ArrayList<>();
        createOrderDTOList.forEach(createOrderDTO -> {
            Goods goodsInfo = goodsService.getGoodsById(createOrderDTO.getGoodsId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setGoodsId(goodsInfo.getId());
            orderDetail.setNum(createOrderDTO.getNumber());
            orderDetail.setAmount(goodsInfo.getPrice() * createOrderDTO.getNumber());
            orderDetails.add(orderDetail);
        });
        if (orderService.createOrderWithDetails(order, orderDetails)) {
            return ResponseEntity.ok(new BaseResponse());
        } else {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(new BaseDataResponse(500, "创建订单失败"));
        }
    }
}
