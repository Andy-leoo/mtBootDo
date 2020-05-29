package com.bootdo.workcode.bean;

import java.util.HashMap;
import java.util.Scanner;
/**
 * @author jiangxiao
 * @Title: CollectionTestBean
 * @Package
 * @Description: 网上收集的 一些编程小题
 * @date 2020/5/2910:58
 */
public class CollectionAllTestBean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);				//创建输入对象
        String str = sc.nextLine();							//输入字符串
        char[] arr = str.toCharArray();						//将字符串转换成字符数组
        HashMap<Character, Integer> hm = new HashMap<Character, Integer>();	//创建双列集合存储键和值
        for(char c : arr) {									//遍历字符数组
            if(!hm.containsKey(c)) {						//如果不包含这个键
                hm.put(c, 1);								//就将此键和值为1添加进集合
            }else {											//如果包含这个键
                hm.put(c, hm.get(c) + 1);					//就将此键和此键所对应的值加1再添加进来
            }
        }
        for (Character key : hm.keySet()) {					//遍历集合
            System.out.println(key + "=" + hm.get(key));
        }
        sc.close();
    }
}
