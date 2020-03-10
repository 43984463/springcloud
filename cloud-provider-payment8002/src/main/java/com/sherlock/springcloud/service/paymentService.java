package com.sherlock.springcloud.service;

import com.sherlock.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @auther Sherlock
 * @date 2020/3/9 19:00
 * @Description:
 */
public interface paymentService {

    /**
     *创建payment
     *
     * @param payment
     * @return
     */
    int create(Payment payment);

    /**
     * 根据ID获取payment
     *
     * @param paymentId
     * @return
     */
    Payment getPaymentById(@Param("id") Long paymentId);
}
