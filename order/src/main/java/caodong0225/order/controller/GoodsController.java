package caodong0225.order.controller;

import caodong0225.common.entity.Goods;
import caodong0225.common.vo.GoodsInfoVO;
import caodong0225.order.service.IGoodsService;
import caodong0225.order.service.IOrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "商品管理相关接口")
public class GoodsController {
    private final IGoodsService goodsService;
    private final IOrderDetailService orderDetailService;

    @GetMapping("/list")
    @Operation(summary = "获取商品列表", description = "获取目前所有已有的商品")
    public ResponseEntity<List<GoodsInfoVO>> getGoodsList() {
        List<Goods> allGoods = goodsService.getAllGoods();
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        allGoods.forEach(goods -> goodsInfoVOList.add(new GoodsInfoVO(goods, goodsService.goodsStock(goods.getId()))));
        return ResponseEntity.ok(goodsInfoVOList);
    }
}
