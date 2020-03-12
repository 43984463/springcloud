package com.sherlock.springcloud;

import java.time.ZonedDateTime;

/**
 * @auther Sherlock
 * @date 2020/3/12 20:12
 * @Description:
 */
public class GetTimeMain {
    public static void main(String[] args) {
        System.out.println(ZonedDateTime.now()); //获取默认时区的时间
    }
}
