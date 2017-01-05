package com.wpy.faxianbei.sk.utils.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wpy on 16-12-12.
 * 字符串转换为基本的数据类型
 */

public class StringToBaseDataType {
    /**
     * 将Object类型对象转换为int基本类型
     * @param value object 待转换的Object对象
     * @param defaultValue 转换不成功时返回的默认值
     * @return 返回转换结束后的int值
     */
    public static final int convertToInt(Object value,int defaultValue){
        if(value==null||"".equals(value.toString().trim())){
            return defaultValue;
        }
        try{
            return Integer.valueOf(value.toString().trim());
        }catch (Exception e){
            try{
                return Double.valueOf(value.toString().trim()).intValue();
            }catch (Exception e1){
                return defaultValue;

            }
        }
    }

    /**
     * 将Object类型对象转换为float基本类型
     * @param value 待转换的Object对象
     * @param defaultValue 转换不成功时返回的默认值
     * @return 返回转换结束后的float值
     */
    public static final float convertToFloat(Object value,float defaultValue){
        if(value==null||"".equals(value.toString().trim())){
            return defaultValue;
        }
        try{
            return Float.valueOf(value.toString().trim());
        }catch (Exception e){
                return defaultValue;
        }
    }

    /**
     * 将Object类型对象转换为float基本类型
     * @param value 待转换的Object对象
     * @param defaultValue 转换不成功时返回的默认值
     * @return 返回转换结束后的double值
     */
    public static final Double convertToDouble(Object value,double defaultValue){
        if(value==null||"".equals(value.toString().trim())){
            return defaultValue;
        }
        try{
            return Double.valueOf(value.toString().trim());
        }catch (Exception e){
            return defaultValue;
        }
    }
    /**
     * 将课表上的上课周次转换成整数集合ArrayList
     * @param strWeek 课程表当中的课程周次
     * @return 返回的上课周次集合
     */
    public static List<Integer> getWeekArra(String strWeek){
        String[] weekRangeArray = strWeek.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for(String weekRange:weekRangeArray)
        {
            String[] array = weekRange.split("-");
            if(array.length>1)
            {
                Integer start=convertToInt(array[0],-1);
                Integer end = convertToInt(array[1],-1);
                if(start!=-1&&end!=-1)
                {
                    for(int i=start;i<=end;i++)
                    {
                        list.add(Integer.valueOf(i));
                    }
                }
            }else if(array.length>0)
            {
                list.add(Integer.parseInt(array[0]));
            }
        }
        return list;
    }
}
