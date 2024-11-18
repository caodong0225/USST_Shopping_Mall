package caodong0225.alipay.controller;

import caodong0225.common.pojo.AliPay;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jyzxc
 * @since 2024-11-18
 */
@RestController
@RequestMapping("/alipay")
public class AliPayController {
    //这里使用的easy版本的 传过来的标题好像不能中文 要修复这个问题可以使用完整版的sdk
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public String pay(AliPay aliPay) {
        AlipayTradePagePayResponse response;
        try {
            //  发起API调用（以创建当面付收款二维码为例）
            response = Factory.Payment.Page()
                    .pay(URLEncoder.encode(aliPay.getSubject(), StandardCharsets.UTF_8), aliPay.getTraceNo(), String.valueOf(aliPay.getTotalAmount()), "");
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return response.getBody();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                //更新数据库中的订单信息
            }
        }
        return "success";
    }

    /**
     * 退款接口
     * @param tradeNo 支付宝交易号
     * @param refundAmount 退款金额
     * @return 退款结果
     */
    @PostMapping("/refund")
    public String refund(@RequestParam String tradeNo,
                         @RequestParam String refundAmount) {
        try {
            // 调用 Alipay Easy SDK 的退款方法
            // 商户订单号: 1
            AlipayTradeRefundResponse response = Factory.Payment.Common().refund(tradeNo, refundAmount);
            System.out.println(response.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    /*
    交易名称: 123456789
    交易状态: TRADE_SUCCESS
    支付宝交易凭证号: 2024110722001457460504888500
    商户订单号: 1
    交易金额: 1.00
    买家在支付宝唯一id: 2088722049257460
    买家付款时间: 2024-11-07 00:28:09
    买家付款金额: 1.00
     */
}
