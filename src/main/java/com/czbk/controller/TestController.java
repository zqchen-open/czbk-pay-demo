package com.czbk.controller;


import com.czbk.config.CzbkConfig;
import com.github.czq.config.SnowflakeConfig;
import com.github.czq.domain.PayResponse;
import com.github.czq.domain.UnifyOrderRequest;
import com.github.czq.factory.Factory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TestController {

    /**
     * 支付宝下单
     * Factory.能力类别.支付平台.场景类别.连接方式.接口方法名称( ... )
     *
     * @return 订单 deata
     * @throws Exception 下单异常
     */
    @GetMapping("/unifyOrder")
    public PayResponse unifyOrder() throws Exception {
        SnowflakeConfig snowflakeConfig = new SnowflakeConfig();
        Factory.setOptions(CzbkConfig.Configs.CONFIG);
        String merchantOrderNo = snowflakeConfig.getIdWorker().nextId("K", true);
        UnifyOrderRequest build = UnifyOrderRequest.builder()
                .merchantOrderNo(merchantOrderNo)
                .totalAmount(new BigDecimal(1000).setScale(2))
                .originalPrice(new BigDecimal(1000).setScale(2))
                .productName("商品名称")
                .orderDesc("订单描述")
                .clientIp("117.136.38.165")
                .build();
        PayResponse pay = Factory.Payment.Alipay.Pc().connTypeIndirect().pay(build);
        return pay;
    }

}

