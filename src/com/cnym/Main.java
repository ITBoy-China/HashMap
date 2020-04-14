package com.cnym;

import com.cnym.HashMap.HashMap;

public class Main {

    public static void main(String[] args) {
	    //创建HashMap进行测试
        HashMap<String,String> hash = new HashMap<>();
        //put 32 个数据进去
        hash.put("01号","城南有梦");
        hash.put("02号","唐三");
        hash.put("03号","小舞");
        hash.put("04号","戴沐白");
        hash.put("05号","朱竹清");
        hash.put("06号","奥斯卡");
        hash.put("07号","宁荣荣");
        hash.put("08号","马红俊");
        hash.put("09号","胡列娜");
        hash.put("10号","大师");
        hash.put("11号","比比东");
        hash.put("12号","千仞雪");
        hash.put("13号","泰坦巨猿");
        hash.put("14号","天青牛蟒");
        hash.put("15号","星罗帝国");
        hash.put("16号","武魂殿");
        hash.put("17号","天斗帝国");
        hash.put("18号","海神");
        hash.put("19号","天使之神");
        hash.put("20号","魂骨");
        hash.put("21号","外附魂骨");
        hash.put("22号","暴雨梨花针");
        hash.put("23号","佛怒唐莲");
        hash.put("24号","字母追魂夺命胆");
        hash.put("25号","八角玄冰草");
        hash.put("26号","烈火杏娇疏");
        hash.put("27号","唐昊");
        hash.put("28号","封号斗罗");
        hash.put("29号","唐晨");
        hash.put("30号","杀戮领域");
        hash.put("31号","无敌金身");
        hash.put("32号","九宝琉璃塔");
        System.out.println(hash.get("5"));
    }
}
