package com.example.financeproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date convertStringToDate(String strDate) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            return fmt.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date);
    }

    public static String convertDateToStringVNFmt(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("'ngày' dd 'tháng' MM 'năm' yyyy");
        return sdf.format(date);
    }

}
