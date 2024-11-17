package caodong0225.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jyzxc
 * @since 2024-11-17
 */
@Getter
@Setter
@Schema(name = "CreateOrderDTO", description = "创建一个订单")
public class CreateOrderDTO {
    @Schema(description = "商品ID")
    private Integer goodsId;
    @Schema(description = "购买数量")
    private Integer number;
}
