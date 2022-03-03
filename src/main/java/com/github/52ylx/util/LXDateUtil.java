package com.gitee.ylx.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LXDateUtil {

    public static List<String> getMonthBetween(Date minDate, Date maxDate) {
        try{
            ArrayList<String> result = new ArrayList<String>();
            //格式化为年月
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(minDate);
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(maxDate);
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
            return result;
        }catch (Exception e){
            return LX.exMsg(e);
        }
    }
}
