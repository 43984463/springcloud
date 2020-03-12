package com.sherlock.springcloud.service.serviceimpl;

import com.sherlock.springcloud.service.PaymentFeignHystrixService;
import org.springframework.stereotype.Component;

/**
 * @auther Sherlock
 * @date 2020/3/12 11:25
 * @Description:
 */
@Component
public class PaymentFeignFallBackServiceImpl implements PaymentFeignHystrixService {

    @Override
    public String paymentInfo_TimeOut_OK(Integer id) {
        return "PaymentFeignFallBackServiceImpl  -> paymentInfo_TimeOut_OK";
    }

}
