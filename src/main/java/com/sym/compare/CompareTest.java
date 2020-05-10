package com.sym.compare;

import com.sym.compare.component.Order;
import com.sym.compare.component.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 一个类若实现了{@link Comparable}, 表该类支持内部排序, 即不借助其它类便可以排序;
 * 而{@link Comparator}是给那些未实现{@link Comparable}的类做外部排序用的, 它
 * 相当于一个比较器.
 *
 * @author shenyanming
 * @date 2020/5/10 8:52.
 */
@Slf4j
public class CompareTest {

    /**
     * 实现了{@link Comparable}接口的元素, 说明它自己本身支持排序, 所以
     * 可以直接拿来排序
     */
    @Test
    public void test01(){
        Order order1 = new Order(10);
        Order order2 = new Order(25);
        Order order3 = new Order(30);
        Order[] orderArray = new Order[]{order3, order2, order1};
        log.info("排序前：{}", Arrays.toString(orderArray));
        Arrays.sort(orderArray);
        log.info("排序后：{}", Arrays.toString(orderArray));
    }

    /**
     * {@link Product}未实现{@link Comparable}, 所以它自身不支持排序,
     * 这时候如果对其排序的话, 就要借助{@link Comparator}接口
     */
    @Test
    public void test02(){
        Product product1 = new Product(5);
        Product product2 = new Product(11);
        Product product3 = new Product(2);
        Product product4 = new Product(8);
        Product[] productArray = new Product[]{product1, product2, product3, product4};

        // 创建比较器
        Comparator<Product> comparator = Product.getComparator();

        log.info("排序前: {}", Arrays.toString(productArray));
        Arrays.sort(productArray, comparator);
        log.info("排序后: {}", Arrays.toString(productArray));
    }

    /**
     * 倒序排序
     */
    @Test
    public void test03(){
        Product product1 = new Product(5);
        Product product2 = new Product(11);
        Product product3 = new Product(2);
        Product product4 = new Product(8);
        Product[] productArray = new Product[]{product1, product2, product3, product4};

        log.info("排序前: {}", Arrays.toString(productArray));
        Arrays.sort(productArray, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                /*
                 * compare()方法有3种返回值：负数、0、正数. 数组排序Arrays.sort()的规则是谁小谁排前面.
                 * 假设o1为2, o2为5.
                 * 若是用 o1 - o2, 得到结果:-3, 说明o1比o2小, 所以o1会排在o2前面;
                 * 若是用 o2 - o1, 得到结果: 3, 由于返回值大于0, 即告诉外部“o1 比 o2 大”, 所以o1会排在o2后面, 也就是倒序排序了！
                 */
                return o2.getId() - o1.getId();
            }
        });
        log.info("排序后: {}", Arrays.toString(productArray));
    }
}
