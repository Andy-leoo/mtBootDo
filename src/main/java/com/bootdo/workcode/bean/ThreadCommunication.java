package com.bootdo.workcode.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangxiao
 * @Title: ThreadCommunication
 * @Package
 * @Description: 了解   notify和wait 方法特点。
 * @date 2020/5/2618:01
 */
public class ThreadCommunication     {


        private final List<Integer> list =new ArrayList<>();

        public static void main(String[] args) {
            ThreadCommunication demo =new ThreadCommunication();
            new Thread(()->{
                for (int i=0;i<10;i++){
                    synchronized (demo.list){
                        if(demo.list.size()%2==1){
                            try {
                                demo.list.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        demo.list.add(i);
                        System.out.print(Thread.currentThread().getName());
                        System.out.println(demo.list);
                        demo.list.notify();//不会释放锁
                    }
                }

            }).start();

            new Thread(()->{
                for (int i=0;i<10;i++){
                    synchronized (demo.list){
                        if(demo.list.size()%2==0){
                            try {
                                demo.list.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        demo.list.add(i);
                        System.out.print(Thread.currentThread().getName());
                        System.out.println(demo.list);
                        demo.list.notify();
                    }
                }
            }).start();
        }


}
