package caodong0225.order.controller;

import caodong0225.common.entity.Goods;
import caodong0225.common.vo.GoodsInfoVO;
import caodong0225.order.service.IGoodsService;
import caodong0225.order.service.IOrderDetailService;
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
public class GoodsController {
    private final IGoodsService goodsService;
    private final IOrderDetailService orderDetailService;

    @GetMapping("/list")
    public ResponseEntity<List<GoodsInfoVO>> getGoodsList() {
        List<Goods> allGoods = goodsService.getAllGoods();
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        allGoods.forEach(goods -> {
            goodsInfoVOList.add(new GoodsInfoVO(goods, goodsService.goodsStock(goods.getId())));
        });
        return ResponseEntity.ok(goodsInfoVOList);
    }
}
