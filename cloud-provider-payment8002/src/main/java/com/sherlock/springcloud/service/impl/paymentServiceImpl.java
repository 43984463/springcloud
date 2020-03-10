package com.sherlock.springcloud.service.impl;

import com.sherlock.springcloud.dao.PaymentDao;
import com.sherlock.springcloud.entities.Payment;
import com.sherlock.springcloud.service.paymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther Sherlock
 * @date 2020/3/9 19:02
 * @Description:
 */
@Service
public class paymentServiceImpl implements paymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentDao.getPaymentById(paymentId);
    }
}
