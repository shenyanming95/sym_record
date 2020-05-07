package com.sym.collection.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 一些集合操作的收集
 *
 *
 * @author shenym
 * @date 2019/11/13
 */
public class ListTest {

    @Test
    public void batchHandleListTest(){
        List<String> list = new ArrayList<>(9527);
        for( int i = 0; i < 9527; i++ ){
            list.add(String.valueOf(i));
        }
        batchHandleList(list, 666, (paramList)-> System.out.println(paramList.size()));
    }


    /**
     * 按指定个数, 批次处理集合
     * @param dataList 数据集, 别为空
     * @param limit 每次处理的最大值
     */
    public static void batchHandleList(List<?> dataList, int limit, Consumer<List<?>> handler){
        int size;
        if(null == dataList || (size = dataList.size()) == 0) {
            return;
        }
        //求出集合数量对于limit的整数倍
        int times = size / limit;
        //求出集合数量对于limit的余数
        int remainder = size % limit;

        int startIndex = 0;
        for( int j = 0; j < times; j++ ){
            // 每次截取集合, 数量为limit个, 下标依次为：[0,limit-1],[limit,2limit-1],...以此类推
            // 注意subList返回的List实体类只能遍历, 不能再进行数据处理
            List<?> subList = dataList.subList(startIndex, startIndex += limit);
            handler.accept(subList);
        }
        // 处理余数, 或者压根集合的数量就没有达到limit

        // 已处理的集合数量
        int processed = times * limit;
        //根据余数, 截取余下未处理的集合元素
        List<?> subList = dataList.subList(processed, processed + remainder);
        if(!subList.isEmpty()){
            handler.accept(subList);
        }
    }
}
