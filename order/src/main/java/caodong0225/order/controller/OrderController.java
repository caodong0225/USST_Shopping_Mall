package caodong0225.order.controller;

import caodong0225.common.dto.CreateOrderDTO;
import caodong0225.common.entity.Goods;
import caodong0225.common.entity.OrderDetail;
import caodong0225.common.entity.Orders;
import caodong0225.common.entity.UserInfo;
import caodong0225.common.response.BaseDataResponse;
import caodong0225.common.response.GeneralDataResponse;
import caodong0225.common.util.JwtUtil;
import caodong0225.common.vo.GoodsOrderedVO;
import caodong0225.common.vo.OrderInfoVO;
import caodong0225.order.service.IGoodsService;
import caodong0225.order.service.IOrderDetailService;
import caodong0225.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final IOrderService orderService;
    private final IGoodsService goodsService;
    private final IOrderDetailService orderDetailService;

    public static final String PAYMENT_URL = "http://localhost:8082/alipay/pay";

    @Resource
    private RestTemplate restTemplate;

    public OrderController(IOrderService orderService, IGoodsService goodsService, IOrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.goodsService = goodsService;
        this.orderDetailService = orderDetailService;
    }

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
    public ResponseEntity<BaseDataResponse> createOrder(
            HttpServletRequest request,
            @Valid @RequestBody List<CreateOrderDTO> createOrderDTOList
    ) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(new BaseDataResponse(403, "您还未登录"));
        }
        UserInfo userInfo = JwtUtil.parseToken(token);
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        createOrderDTOList.forEach(createOrderDTO -> {
            Goods goodsInfo = goodsService.getGoodsById(createOrderDTO.getGoodsId());
            totalPrice.updateAndGet(v -> v + goodsInfo.getPrice() * createOrderDTO.getNumber());
        });
        Long traceNo = orderService.countTotalOrders() + 1;
        String response = restTemplate.getForObject(PAYMENT_URL + "?subject=caodong0225&traceNo="+traceNo+"&totalAmount="+ totalPrice, String.class);
        Orders order = new Orders();
        order.setUserId(userInfo.getId());
        order.setAmount(totalPrice.get());
        order.setNumber(String.valueOf(traceNo));
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
            return ResponseEntity.ok(new BaseDataResponse(response));
        } else {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(new BaseDataResponse(500, "创建订单失败"));
        }
    }

    @GetMapping("/pay")
    @Operation(summary = "支付订单", description = "支付订单")
    public ResponseEntity<BaseDataResponse> payOrder(
            HttpServletRequest request,
            @RequestParam("traceNo") Long traceNo
    ) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(new BaseDataResponse(403, "您还未登录"));
        }
        UserInfo userInfo = JwtUtil.parseToken(token);
        Orders order = orderService.getOrderByTraceNo(String.valueOf(traceNo));
        if (order == null) {
            return ResponseEntity.ok(new BaseDataResponse(404, "订单不存在"));
        }
        if(!userInfo.getId().equals(order.getUserId()))
        {
            return ResponseEntity.ok(new BaseDataResponse(403, "您无权支付该订单"));
        }
        if(order.getStatus() != 1) {
            return ResponseEntity.ok(new BaseDataResponse(400, "订单已过期或被取消"));
        }
        String response = restTemplate.getForObject(PAYMENT_URL + "?subject=caodong0225&traceNo="+traceNo+"&totalAmount="+ order.getAmount(), String.class);
        return ResponseEntity.ok(new BaseDataResponse(response));
    }
}
