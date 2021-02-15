package com.siec.system.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @Description: 随机数生成工具类
 * @author: 老骨头（lgt）
 * @date: 2019/10/6
 */
@Slf4j
public class RandomUtil {

   public static String randomNumber(Integer code){
       StringBuilder str = new StringBuilder();
       Random random = new Random();  //生成随机数
       if(code == 6){
           for (int i = 0; i < 6; i++) {   ////生成6位数随机数
               str.append(random.nextInt(10));
           }
       }else if(code == 4){
           for (int i = 0; i < 4; i++) {   ////生成4位数随机数
               str.append(random.nextInt(10));
           }
       }
       return str.toString();
   }

   /**测试生成的随机数*/
   public static void main(String [] args){
       String number = randomNumber(4);
       System.out.println(number);

   }

}
