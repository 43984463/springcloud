package com.sherlock.springcloud.dao;

import com.sherlock.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther Sherlock
 * @date 2020/3/9 18:37
 */
@Mapper
public interface PaymentDao {

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
