package com.babyTalk.base.utils;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.List;


public class StringArrUtil {
    /**
     * 把 "1,2,3" 转化为[1, 2, 3]
     * 把 "3,1,2" 转化为[1, 2, 3]
     * @param str
     * @return List<Integer>
     */
    public static List<Integer> stringArrToList_Integer(String str){
        final List<Integer> list = JSON.parseArray("[" + str + "]", Integer.class);
        Collections.sort(list);//升序排序
        return list;
    }

    /**
     * 把 "1,2,3" 转化为"[1, 2, 3]"
     * 把 "3,1,2" 转化为"[1, 2, 3]"
     * @param str
     * @return String
     */
    public static String stringArrTOStringList(String str){
        final List<Integer> list = JSON.parseArray("["+str+"]", Integer.class);
        Collections.sort(list);//升序排序
        return list.toString();
    }

    /**
     * 把 "1,2,3" 转化为[1, 2, 3]
     * 把 "3,1,2" 转化为[1, 2, 3]
     * @param str
     * @return List<Integer>
     */
    public static List<String> stringArrToList_string(String str){
        final List<String> list = JSON.parseArray("[" + str + "]", String.class);
        Collections.sort(list);//升序排序
        return list;
    }



}
