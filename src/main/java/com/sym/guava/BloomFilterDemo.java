package com.sym.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * guava提供的布隆过滤器实现：{@link BloomFilter}
 *
 * @author shenym
 * @date 2020/3/6 8:42
 */

public class BloomFilterDemo {
    public static void main(String[] args) {
        // expectedInsertions：是指定布隆过滤器表示hash的值大小, 一般这值越大, 误判率越小
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),
                10000);

        // 初始化, 将0-5554的值保存到布隆过滤器中
        for (int i = 0; i < 5555; i++) {
            bloomFilter.put(Integer.toString(i));
        }

        //命中集合
        List<String> hitList = new ArrayList<>(10000);
        //未命中集合
        List<String> unHitList = new ArrayList<>(10000);
        //误判命中集合
        List<String> wrongExistList = new ArrayList<>(10000);
        //误判未命中集合
        List<String> wrongNotExistList = new ArrayList<>(10000);

        // 从 -100 到 8000, 依次遍历看每个数字是否被布隆过滤器命中or过滤
        for (int j = -100; j < 8000; j++) {
            String s = String.valueOf(j);
            if (bloomFilter.mightContain(s)) {
                // 命中
                hitList.add(s);
                if( j < 0 || j > 5554 ){
                    // 因为之前布隆过滤器初始化时, 只加入了0~5554的数据, 如果当前值不再这个范围内, 但是却被判断存在于布隆过滤器, 就是误判
                    wrongExistList.add(s);
                }
            } else {
                // 未命中
                unHitList.add(s);
                if( 0 <= j && j < 5555 ){
                    // 因为之前布隆过滤器初始化时, 只加入了0~5554的数据, 如果当前值属于这个范围内, 但是却被判断不存在于布隆过滤器, 就是误判
                    // PS：布隆过滤器只会误判存在, 不会误判不存在滴~~
                    wrongNotExistList.add(s);
                }
            }
        }

        // 打印结果
        System.out.println("命中数量：" + hitList.size());
        System.out.println("未命中数量：" + unHitList.size());
        System.out.println("误判数量：" + (wrongExistList.size() + wrongNotExistList.size()));
        System.out.println("误判存在："+ wrongExistList);
        System.out.println("误判不存在："+ wrongNotExistList);
    }
}
