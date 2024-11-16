package caodong0225.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@Getter
@Setter
public class Goods implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID", example = "1")
    private Integer id;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称", example = "拖鞋")
    private String name;

    /**
     * 商品状态
     */
    @Schema(description = "商品状态 1正在售卖 2已卖光 3已下架", example = "1")
    private Integer status;

    /**
     * 商品价格
     */
    @Schema(description = "商品单价", example = "100.00")
    private Double price;

    /**
     * 商品图标
     */
    @Schema(description = "商品图标", example = "https://www.baidu.com")
    private String logo;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存", example = "100")
    private Integer stock;
}
