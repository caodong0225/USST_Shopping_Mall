package caodong0225.order.controller;

import caodong0225.order.service.IGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jyzxc
 * @since 2024-11-16
 */
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final IGoodsService goodsService;
}
