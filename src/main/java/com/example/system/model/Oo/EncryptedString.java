package com.example.system.model.Oo;

import lombok.Data;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@Data
public class EncryptedString {

    public static  String key = "siec123456789wyu";//长度为16个字符

    public static  String iv  = "wyu123456780siec";//长度为16个字符
}
