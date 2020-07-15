package com.sym.system;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * java与操作系统之间的交互
 *
 * @author shenyanming
 * @date 2019/10/14
 */
public class SystemUtil {

    /**
     * 获取当前操作系统的CPU数量
     */
    @Test
    public void getCpuTest() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("当前操作系统的CPU数量：" + i);
    }


    /**
     * 获取IP地址
     */
    @Test
    public void getIpTest() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : addresses) {
                    String ip = interfaceAddress.getAddress().getHostAddress();
                    if (ip.length() < 20) {
                        System.out.println("IP:" + ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统的运行内存
     */
    @Test
    public void getMemoryTest() {
        final long MB = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();

        //Java虚拟机中的可用内存量
        System.out.println(runtime.freeMemory() / MB + "M");

        //Java虚拟机中的内存总量
        System.out.println(runtime.totalMemory() / MB + "M");

        //Java虚拟机将尝试使用的最大内存量
        System.out.println(runtime.maxMemory() / MB + "M");

        runtime.gc();
    }

    @Test
    public void getThreadInfoTest(){
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的 monitor 和 Synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
        //遍历线程信息，仅打印线程id和线程名称信息
        for(ThreadInfo info : threadInfo){
            System.out.println(info);
        }
    }
}
