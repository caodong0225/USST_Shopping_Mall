package caodong0225.common.pojo;

import lombok.Data;

/**
 * @author jyzxc
 * @since 2024-11-18
 */
@Data
public class AliPay {
    private String traceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;
}