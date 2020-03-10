package com.sherlock.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther Sherlock
 * @date 2020/3/9 18:33
 * @Description: 本类可以用ResponseEntity替代
 * @see org.springframework.http.ResponseEntity
 * @see org.springframework.http.HttpStatus
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code,String message){
        this(code, message, null);
    }
}
