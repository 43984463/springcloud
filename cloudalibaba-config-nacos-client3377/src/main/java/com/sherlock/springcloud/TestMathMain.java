package com.sherlock.springcloud;

import java.util.Scanner;

/**
 * @auther Sherlock
 * @date 2020/3/14 17:56
 * @Description:
 */
public class TestMathMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            if((scanner.nextInt()&1) == 1){
                System.out.println("奇数");
            }else System.out.println("偶数");
        }
    }


}
