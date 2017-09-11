package com.guaju.sugertea.utils;

import android.text.TextUtils;

/**
 * Created by guaju on 2017/9/5.
 */

public class SignUtils {
    final static String  privateNum="%g4509irongbei&*)($#@!QFG&^*TOUbuqu$#@JKzhgdj^4vB5fnv";
    //公钥
    private static String publicStr;


    public static String getSignInfo(String jiekoudaima,String ... params){
        //公钥
        if (TextUtils.isEmpty(publicStr)){
            publicStr=getPublicNum(jiekoudaima);
        }

        //时间戳
        long time=System.currentTimeMillis()/1000;
        //私钥
        String privateStr = Md5Utils.md5(privateNum);
        //签名主体
        StringBuilder sb=new StringBuilder();
        for(String str: params){
          sb.append("-");
          sb.append(str);
        }
        String paramSum=sb.toString();
        String signStr=caclulateSign(publicStr,paramSum,time+"",privateStr).toUpperCase();
        return  signStr;
//        return  "49932DFEA05F61D1378BF970E4B30726F6806479A29E";

    }

    private static String caclulateSign(String publicStr, String paramSum, String s, String privateStr) {
        String result="";
        String s1 = Md5Utils.md5(publicStr + paramSum + s + privateStr);
        if (!TextUtils.isEmpty(s1)){
           result=publicStr+s1;
        }
        return result;
    }

    private static String getPublicNum(String jiekoudaima) {
        String publicStr="RongbeiAppApi"+jiekoudaima;
        String s = Md5Utils.md5(publicStr);
        if (!TextUtils.isEmpty(s)){
            publicStr = s.substring(0,4)+s.substring(s.length()-8,s.length());
        }
        return publicStr;
    }

}
