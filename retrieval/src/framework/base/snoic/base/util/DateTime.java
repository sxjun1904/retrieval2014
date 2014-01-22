/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTime 当前时间
 * @author 
 *
 */
public class DateTime {
    public DateTime() {

    }
    
    /**
     * 取得当前日期和时间
     * @return String
     */
    public String getNowDateTime(){
    	String nowdatetime="";
    	nowdatetime=getNowDate()+" "+getNowTime();
    	return nowdatetime;
    }
    
    /**
     * 取得当前的系统日期
     * 
     * @return String
     */
    public String getNowDate() {
        String thedate = "";
        String themonth = "";
        int thedate1 = 0;
        int themonth1 = 0;
        String nowday = "";
        Calendar calendar = Calendar.getInstance();
        
        thedate1 = calendar.get(Calendar.DATE);
        if ((thedate1 == 1) || (thedate1 == 2) || (thedate1 == 3) || (thedate1 == 4) || (thedate1 == 5) || (thedate1 == 6) || (thedate1 == 7) || (thedate1 == 8) || (thedate1 == 9)) {
            thedate = "0" + thedate1;
        }else {
            thedate = String.valueOf(thedate1);
        }
        themonth1 = calendar.get(Calendar.MONTH) + 1;
        if ((themonth1 == 1) || (themonth1 == 2) || (themonth1 == 3) || (themonth1 == 4) || (themonth1 == 5) || (themonth1 == 6) || (themonth1 == 7) || (themonth1 == 8) || (themonth1 == 9)) {
            themonth = "0" + themonth1;
        }else {
            themonth = String.valueOf(themonth1);
        }
        nowday = calendar.get(Calendar.YEAR) + "-" + (themonth) + "-" + (thedate); //当前日期
        return nowday;
    }
    
    /**
     * 取得当前系统时间
     * 
     * @return String
     */
    public String getNowTime() {
        String nowtime = "";
        int nowhour = 0;
        int nowminute = 0;
        int nowsecond = 0;
        Calendar calendar = Calendar.getInstance();
        nowhour = calendar.get(Calendar.HOUR_OF_DAY);
        nowminute = calendar.get(Calendar.MINUTE);
        nowsecond = calendar.get(Calendar.SECOND);
        
        String nowhourstring=String.valueOf(nowhour);
        String nowminutestring=String.valueOf(nowminute);
        String nowsecondstring=String.valueOf(nowsecond);
        
        if(nowhourstring.length()<2){
        	nowhourstring="0"+nowhourstring;
        }
        if(nowminutestring.length()<2){
        	nowminutestring="0"+nowminutestring;
        }
        if(nowsecondstring.length()<2){
        	nowsecondstring="0"+nowsecondstring;
        }
        nowtime = nowhourstring + ":" + nowminutestring + ":" + nowsecondstring;
        return nowtime;
    }

    /**
     * 取得当前是一周中的第几天
     * 
     * @return String
     */
    public String getNowWeekDay() {
        String nowweekday = "";
        Calendar calendar = Calendar.getInstance();
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekday == 7) {
            nowweekday = "星期日";
        } else if (weekday == 1) {
            nowweekday = "星期一";
        } else if (weekday == 2) {
            nowweekday = "星期二";
        } else if (weekday == 3) {
            nowweekday = "星期三";
        } else if (weekday == 4) {
            nowweekday = "星期四";
        } else if (weekday == 5) {
            nowweekday = "星期五";
        } else if (weekday == 6) {
            nowweekday = "星期六";
        }
        return (nowweekday);
    }
    
    /**
     * 获取一个时间是星期几
     * @param thedate
     * @return String
     */
    public String getWeekDay(String thedate){
    	String nowdate=getNowDate()+" 00:00:00";
    	int days=(int)timeCalculate(nowdate,thedate)-1;
        String theweekday = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekday == 7) {
        	theweekday = "星期日";
        } else if (weekday == 1) {
        	theweekday = "星期一";
        } else if (weekday == 2) {
        	theweekday = "星期二";
        } else if (weekday == 3) {
        	theweekday = "星期三";
        } else if (weekday == 4) {
        	theweekday = "星期四";
        } else if (weekday == 5) {
        	theweekday = "星期五";
        } else if (weekday == 6) {
        	theweekday = "星期六";
        }
        return (theweekday);
    }
    
    /**
     * 获取一个时间是星期几
     * @param thedate
     * @return String
     */
    public String getWeekDay(Date thedate){
    	String nowdate=getNowDate()+" 00:00:00";
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = null;
    	try{
    		date=format.parse(nowdate);
    	}catch(Exception e){
    		
    	}
    	int days=(int)timeCalculate(thedate,date)-1;
        String theweekday = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekday == 7) {
        	theweekday = "星期日";
        } else if (weekday == 1) {
        	theweekday = "星期一";
        } else if (weekday == 2) {
        	theweekday = "星期二";
        } else if (weekday == 3) {
        	theweekday = "星期三";
        } else if (weekday == 4) {
        	theweekday = "星期四";
        } else if (weekday == 5) {
        	theweekday = "星期五";
        } else if (weekday == 6) {
        	theweekday = "星期六";
        }
        return (theweekday);
    }
    
    /**
     * 日期加减
     * @param days
     * @return String
     */
    public String getDateAddDate(int days){
    	String thedate="";
        String themonth = "";
        int thedate1 = 0;
        int themonth1 = 0;
        String nowday = "";
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        
        thedate1 = calendar.get(Calendar.DATE);
        if ((thedate1 == 1) || (thedate1 == 2) || (thedate1 == 3) || (thedate1 == 4) || (thedate1 == 5) || (thedate1 == 6) || (thedate1 == 7) || (thedate1 == 8) || (thedate1 == 9)) {
            thedate = "0" + thedate1;
        }else {
            thedate = String.valueOf(thedate1);
        }
        
        themonth1 = calendar.get(Calendar.MONTH) + 1;
        if ((themonth1 == 1) || (themonth1 == 2) || (themonth1 == 3) || (themonth1 == 4) || (themonth1 == 5) || (themonth1 == 6) || (themonth1 == 7) || (themonth1 == 8) || (themonth1 == 9)) {
            themonth = "0" + themonth1;
        }else {
            themonth = String.valueOf(themonth1);
        }
        nowday = calendar.get(Calendar.YEAR) + "-" + (themonth) + "-"
                + (thedate); //当前日期
 
    	return nowday;
    }
    
    /**
     * 小时加减
     * @param date
     * @param hour
     * @return String
     */
    public String getDateAddHour(String date,int hour) {
    	String nowdate=getNowDateTime();
    	date=getTime(date);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date thedate_nowdate = null;
    	Date insideDate_date=null;
    	try{
    		thedate_nowdate=format.parse(nowdate);
    		insideDate_date=format.parse(date);
    	}catch(Exception e){
    		
    	}
    	int hours=(int)timeCalculateHour(thedate_nowdate,insideDate_date);
    	
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,hours+hour);
        
        return getDate(calendar);
    }
    
    /**
     * 小时加减
     * @param date
     * @param hour
     * @return String
     */
    public String getDateAddHour(Date date,int hour) {
    	String nowdate=getNowDateTime();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date thedate_nowdate = null;
    	try{
    		thedate_nowdate=format.parse(nowdate);
    	}catch(Exception e){
    		
    	}
    	int hours=(int)timeCalculateHour(thedate_nowdate,date);
    	
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,hours+hour);
        
        return getDate(calendar);
    }
    
    /**
     * 比较日期的大小，判断time1和time2的大小time1>time2时返回1,time1 <time2是返回-1,time1=time2返回0；
     * 
     * @param time1
     *            String格式日期
     * @param time2
     *            String格式日期
     * @return int
     */
    public int compareTime(String time1, String time2) {
    	time1=getTime(time1);
    	time2=getTime(time2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int flag = 0;
        try {
            Date date1, date2;
            date1 = format.parse(time1);
            date2 = format.parse(time2);
            long millisecond = date1.getTime() - date2.getTime();
            if (millisecond > 0) {
                flag = 1;
            } else if (millisecond < 0) {
                flag = -1;
            } else if (millisecond == 0) {
                flag = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (flag);
    }

    /**
     * 比较日期的大小，判断time1和time2的大小time1>time2时返回1,time1 <time2是返回-1,time1=time2返回0；
     * 
     * @param time1
     *            Date格式日期
     * @param time2
     *            Date格式日期
     * @return int
     */
    public int compareTime(Date time1, Date time2) {
        int flag = 0;
        try {
            long millisecond = time1.getTime() - time2.getTime();
            if (millisecond > 0) {
                flag = 1;
            } else if (millisecond < 0) {
                flag = -1;
            } else if (millisecond == 0) {
                flag = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (flag);
    }

    /**
     * 计算time2-time1得到的天数差
     * 
     * @param time1
     *            String格式日期
     * @param time2
     *            String格式日期
     * @return int
     */
    public float timeCalculate(String time1, String time2) {
    	time1=getTime(time1);
    	time2=getTime(time2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        float day = 0;
        Date date1=null;
        Date date2=null;
        
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
            long millisecond = date2.getTime() - date1.getTime();
            day = millisecond / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (day);
    }

    /**
     * 计算time2-time1的天数差
     * 
     * @param time1
     *            Date格式日期
     * @param time2
     *            Date格式日期
     * @return double
     */
    public float timeCalculate(Date time1, Date time2) {
    	float day = 0;
        try {
            Date date1, date2;
            date1 = time1;
            date2 = time2;
            long millisecond = date2.getTime() - date1.getTime();
            day = millisecond / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (day);
    }

    /**
     * 计算小时差
     * @param time1
     * @param time2
     * @return double
     */
    public double timeCalculateHour(String time1,String time2) {
    	time1=getTime(time1);
    	time2=getTime(time2);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	double hour = 0;
        try {
            Date date1, date2;
            date1 = format.parse(time1);
            date2 = format.parse(time2);
            double millisecond = date2.getTime() - date1.getTime();
            hour = millisecond /( 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    	return hour;
    }
    

    /**
     * 计算时差
     * @param date1
     * @param date2
     * @return double
     */
    public double timeCalculateHour(Date date1,Date date2) {
    	double hour = 0;
        double millisecond = date2.getTime() - date1.getTime();
        hour = millisecond /( 60 * 60 * 1000);
    	return hour;
    }
    
    /**
     * 取得当前时间
     * @param time
     * @return long
     */
    public long getLongTime(String time){
    	time=getTime(time);
    	long longtime=0;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try{
        	date = format.parse(time);
        	longtime=date.getTime();
        }catch(Exception e){
        	
        }
        return longtime;
    }
    
    /**
     * 字符串转换时间
     * @param datestring
     * @param theformat
     * @return Date
     */
    public Date parseDate(String datestring,String theformat){
    	if(theformat==null){
    		datestring=getTime(datestring);
    		theformat="yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat format = new SimpleDateFormat(theformat);
        Date date=null;
        try{
        	date = format.parse(datestring);
        }catch(Exception e){
        	e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 判断是不是正确的日期
     * @param datestring
     * @param theformat
     * @return boolean
     */
    public boolean isDateTime(String datestring,String theformat) {
    	boolean flag=false;
    	if(theformat==null){
    		datestring=getTime(datestring);
    		theformat="yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat format = new SimpleDateFormat(theformat);
        try{
        	format.parse(datestring);
        	flag=true;
        }catch(Exception e){
        	flag=false;
        }
    	return flag;
    }
    
    /**
     * 判断是不是正确的日期
     * @param datestring
     * @param theformat
     * @return boolean
     */
    public boolean isDate(String datestring,String theformat) {
    	boolean flag=false;
    	if(theformat==null){
    		datestring=getTime(datestring);
    		theformat="yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat format = new SimpleDateFormat(theformat);
        try{
        	format.parse(datestring+" "+this.getNowTime());
        	flag=true;
        	return flag;
        }catch(Exception e){
        	flag=false;
        	return flag;
        }
    }
    
    /**
     * 判断是不是正确的时间
     * @param timestring
     * @param theformat
     * @return boolean
     */
    public boolean isTime(String timestring,String theformat) {
    	boolean flag=false;
    	if(theformat==null){
    		timestring=getTime(timestring);
    		theformat="yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat format = new SimpleDateFormat(theformat);
        try{
        	format.parse(this.getNowDate()+" "+timestring);
        	flag=true;
        }catch(Exception e){
        	flag=false;
        }
    	return flag;
    }
    
    /**
     * 取得月份的天数
     * @param year
     * @param month
     * @return int
     */
    public int getDaysOfMonth(int year,int month) {
    	int days=0;
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(year,month-1,1);
    	days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	return days;
    }
    
    /**
     * 取得当前月份天数
     * @return int
     */
    public int getDaysOfMonth() {
    	int days=0;
    	int year=getNowYearOnly();
    	int month=getNowMonthOnly();
    	days=getDaysOfMonth(year,month);
    	return days;
    }
    
    /**
     * 取得当前年份
     * @return int
     */
    public int getNowYearOnly() {
    	int year;
    	Calendar calendar = Calendar.getInstance();
    	year=calendar.get(Calendar.YEAR);
    	return year;
    }
    
    /**
     * 取得当前月份
     * @return int
     */
    public int getNowMonthOnly() {
    	int month;
    	Calendar calendar = Calendar.getInstance();
    	month=calendar.get(Calendar.MONTH)+1;
    	return month;
    }
    
    /**
     * 取得当前日期
     * @return int
     */
    public int getNowDateOnly() {
    	int date;
    	Calendar calendar = Calendar.getInstance();
    	date=calendar.get(Calendar.DATE);
    	return date;
    }
    
    /**
     * 取得单前小时
     * @return int
     */
    public int getNowHourOnly() {
    	int hour;
    	Calendar calendar = Calendar.getInstance();
    	hour=calendar.get(Calendar.HOUR_OF_DAY);
    	return hour;
    }
    
    /**
     * 取得单前分钟
     * @return int
     */
    public int getNowMinuteOnly() {
    	int hour;
    	Calendar calendar = Calendar.getInstance();
    	hour=calendar.get(Calendar.MINUTE);
    	return hour;
    }
    
    /**
     * 取得单前秒钟
     * @return int
     */
    public int getNowSecondOnly() {
    	int hour;
    	Calendar calendar = Calendar.getInstance();
    	hour=calendar.get(Calendar.SECOND);
    	return hour;
    }
    
    /**
     * 取得日期
     * @param calendar
     * @return String
     */
    public String getDate(Calendar calendar) {
        String thedate = "";
        String themonth = "";
        int thedate1 = 0;
        int themonth1 = 0;
        
        String nowtime = "";
        int nowhour = 0;
        int nowminute = 0;
        int nowsecond = 0;
        
        String nowday = "";
        
        thedate1 = calendar.get(Calendar.DATE);
        if ((thedate1 == 1) || (thedate1 == 2) || (thedate1 == 3) || (thedate1 == 4) || (thedate1 == 5) || (thedate1 == 6) || (thedate1 == 7) || (thedate1 == 8) || (thedate1 == 9)) {
            thedate = "0" + thedate1;
        }else {
            thedate = String.valueOf(thedate1);
        }
        themonth1 = calendar.get(Calendar.MONTH) + 1;
        if ((themonth1 == 1) || (themonth1 == 2) || (themonth1 == 3) || (themonth1 == 4) || (themonth1 == 5) || (themonth1 == 6) || (themonth1 == 7) || (themonth1 == 8) || (themonth1 == 9)) {
            themonth = "0" + themonth1;
        }else {
            themonth = String.valueOf(themonth1);
        }
        nowday = calendar.get(Calendar.YEAR) + "-" + (themonth) + "-" + (thedate); //当前日期


        nowhour = calendar.get(Calendar.HOUR_OF_DAY);
        nowminute = calendar.get(Calendar.MINUTE);
        nowsecond = calendar.get(Calendar.SECOND);
        
        String nowhourstring=String.valueOf(nowhour);
        String nowminutestring=String.valueOf(nowminute);
        String nowsecondstring=String.valueOf(nowsecond);
        
        if(nowhourstring.length()<2){
        	nowhourstring="0"+nowhourstring;
        }
        if(nowminutestring.length()<2){
        	nowminutestring="0"+nowminutestring;
        }
        if(nowsecondstring.length()<2){
        	nowsecondstring="0"+nowsecondstring;
        }
        nowtime = nowhourstring + ":" + nowminutestring + ":" + nowsecondstring;
        
        return nowday+" "+nowtime;
    }
    
    private String getTime(String time) {
        if(StringClass.getInterString(" ",time).size()<2){
        	time=time+" 00:00:00";
        }
    	return time;
    }
    
    public static void main(String[] args) {
    	DateTime dateTime=new DateTime();
    	float days=dateTime.timeCalculate("2004-05-11","2005-12-05");
    	System.out.println("days="+days);
    }
}