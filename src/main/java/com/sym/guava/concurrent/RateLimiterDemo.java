package com.sym.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;

/**
 * guava提供的基于令牌桶算法的限流器.算法思想：一个固定容量的桶, 桶里存放着令牌（token）
 * 桶一开始是空的, token以一个固定的速率r往桶里填充, 直到达到桶的容量,多余的令牌将会被丢弃.
 * 每当一个请求过来时, 就会尝试从桶里移除一个令牌, 如果没有令牌的话, 请求无法通过.
 *
 * @author shenym
 * @date 2020/3/6 8:29
 */

public class RateLimiterDemo {
    public static void main(String[] args) {
        // 创建一个1s只允许1次请求的限流器
        RateLimiter limiter = RateLimiter.create(1);
        // 在一个死循环中调用限流器, 会发现1s只有一个输出
        new Thread(()->{
            for(;;){
                if(limiter.tryAcquire()){
                    System.out.println("允许请求" + LocalDateTime.now());
                }
            }
        }).start();
    }
}
