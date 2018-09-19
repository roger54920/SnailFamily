package com.example.ysww.snailfamily.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by me-jie on 2017/4/26.
 * 电话号码匹配工具类
 */

public class TelNumMatch {
    /**
     * 判断电话号码是否符合格式.
     *
     * @param inputText the input text
     * @return true, if is phone
     */
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

}

