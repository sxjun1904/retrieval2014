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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * 随机数 
 * @author 
 *
 */
public class RandomSeed {


    /**
     * 字符串全部由小写字符组成
     */
	public static final int LOWERCASE_TYPE=0;
	/**
	 * 字符串全部由大写字母组成
	 */
	public static final int UPPERCASE_TYPE=1;
	/**
	 * 字符串全部由数字组成
	 */
	public static final int NUMBER_TYPE=2;
	/**
	 * 字符串由大小写字母混合组成
	 */
	public static final int LOWERCASEANDUPPERCASE_TYPE=3;
	/**
	 * 字符串由小写字母和数字混合组成
	 */
	public static final int LOWERCASEANDNUMBER_TYPE=4;
	/**
	 * 字符串由大写字母和数字混合组成
	 */
	public static final int UPPERCASEANDNUMBER_TYPE=5;
	/**
	 * 字符串由大小写字母和数字混合组成
	 */
	public static final int ALL_TYPE=6;
	/**
	 * 字符串由大小写字母和数字和特殊字符混合组成
	 */
	public static final int OTHER_TYPE=7;
	
	/**
	 * 字符串由16位的数字组成
	 */
	public static final int HEX_TYPE=8;
	
    /**
     * 取得一个随机生成的任意长度的数字字符串
     * @return String
     */
    public static String getRandom(){
    	String randomstring="";
    	String temp2=getRandomString(NUMBER_TYPE);
    	randomstring=temp2;
    	return  randomstring;
    }

    /**
     * 取得一个按照当前时间计算的出的固定包括前面8位时间的随机字符串 前八位的字符例如 : 20041011
     * @return String
     */
    public static String getSeedShort() {
        StringBuffer nowseed = null;
        Calendar calendar = Calendar.getInstance();
        //随机号码
    	nowseed=new StringBuffer();

        nowseed.append(calendar.get(Calendar.YEAR));
        String temp="";
        temp=String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if(temp.length()<2){
        	temp="0"+temp;
        }
        nowseed.append(temp);
        
        temp=String.valueOf(calendar.get(Calendar.DATE));
        if(temp.length()<2){
        	temp="0"+temp;
        }
        nowseed.append(temp);
        nowseed.append(getRandomString(NUMBER_TYPE));
        return nowseed.toString();
    }
    
    /**
     * 取得一个固定长度按照当前时间计算的出的固定包括前面8位时间的短随机字符串 前八位的字符例如 : 20041011
     * @param length  必须至少大于8 if(length<=8) return null
     * @return String
     */
    public static String getSeedShort(int length){
    	if((length>0)&&(length<=8)) {
    		return null;
    	}
    	String randomstring="";
    	randomstring=getSeedShort();
    	while(randomstring.length()<length){
    		randomstring+=getRandomString(NUMBER_TYPE);
    	}
    	randomstring=randomstring.substring(0,length);
    	return randomstring;
    }
    
    /**
     * 获取一定数量的不重复的按照当前时间计算的出的固定包括前面8位时间的短随机字符串 前八位的字符例如 : 20041011
     * @param length 字符串长度，if(length<=0) {则不限制长度} else if((length>0)&&(length<=8)) {return null}
     * @param amount if(amount<1) return null
     * @return ArrayList
     */
    public static ArrayList getSeedShort(int length,int amount) {
    	if((length>0)&&(length<=8)) {
    		return null;
    	}
    	ArrayList randomstringList=new ArrayList();
    	if(amount<1) {
    		return null;
    	}
    	for(int i=0;i<amount;) {
    		String randomstring=null;
    		if(length<=0) {
    			randomstring=getSeedShort();
    		}else {
    			randomstring=getSeedShort(length);
    		}
			if(!randomstringList.contains(randomstring)) {
				randomstringList.add(randomstring);
				i++;
			}
    	}
    	return randomstringList;
    }
    
    /**
     * 取得一个按照当前时间计算的出的包括前面14位表示单前时间的随机字符串
     * @return String
     */
    public static String getSeed() {
        StringBuffer nowseed = null;
        Calendar calendar = Calendar.getInstance();
        //随机号码
    	nowseed=new StringBuffer();

        nowseed.append(calendar.get(Calendar.YEAR));
        String temp="";
        temp=String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if(temp.length()<2){
        	temp="0"+temp;
        }
        nowseed.append(temp);
        
        temp=String.valueOf(calendar.get(Calendar.DATE));
        if(temp.length()<2){
        	temp="0"+temp;
        }        
        nowseed.append(temp);
        
        temp=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if(temp.length()<2){
        	temp="0"+temp;
        }        
        nowseed.append(temp);

        temp=String.valueOf(calendar.get(Calendar.MINUTE));
        if(temp.length()<2){
        	temp="0"+temp;
        }   
        nowseed.append(temp);
        
        temp=String.valueOf(calendar.get(Calendar.SECOND));
        if(temp.length()<2){
        	temp="0"+temp;
        }
        nowseed.append(temp);
        nowseed.append(getRandomString(NUMBER_TYPE));
        return nowseed.toString();
    }

    /**
     * 取得固定长度的包括前面14位表示单前时间的随机字符串
     * @param length 字符串长度，if(length<=0) {则不限制长度} else if((length>0)&&(length<=14)) {return null}
     * @return String
     */
    public static String getSeed(int length){
    	if((length>0)&&(length<=14)) {
    		return null;
    	}
    	String randomstring="";
    	randomstring=getSeed();
    	while(randomstring.length()<length){
    		randomstring+=getRandomString(NUMBER_TYPE);
    	}
    	randomstring=randomstring.substring(0,length);
    	return randomstring;
    }
    
    /**
     * 获取一定数量的不重复的固定长度的包括前面14位表示单前时间的随机字符串
     * @param length 字符串长度，if(length<=0) {则不限制长度} else if((length>0)&&(length<=14)) {return null}
     * @param amount if(amount<1) return null
     * @return ArrayList
     */
    public static ArrayList getSeed(int length,int amount) {
    	if((length>0)&&(length<=14)) {
    		return null;
    	}
    	ArrayList randomstringList=new ArrayList();
    	if(amount<1) {
    		return null;
    	}
    	for(int i=0;i<amount;) {
    		String randomstring=null;
    		if(length<=0) {
    			randomstring=getSeed();
    		}else {
    			randomstring=getSeed(length);
    		}
			if(!randomstringList.contains(randomstring)) {
				randomstringList.add(randomstring);
				i++;
			}
    	}
    	return randomstringList;
    }
    
    /**
     * 取得一个由数字、大小写字母、和特殊字符组成的随机字符串
     * @param randomtype 
     *        <br> type=0(RandomSeed.LOWERCASE_TYPE) -- 字符串全部由小写字符组成
     *        <br> type=1(RandomSeed.UPPERCASE_TYPE) -- 字符串全部由大写字母组成
     *        <br> type=2(RandomSeed.NUMBER_TYPE) -- 字符串全部由数字组成
     *        <br> type=3(RandomSeed.LOWERCASEANDUPPERCASE_TYPE) 字符串由大小写字母混合组成
     *        <br> type=4(RandomSeed.LOWERCASEANDNUMBER_TYPE) 字符串由小写字母和数字混合组成
     *        <br> type=5(RandomSeed.UPPERCASEANDNUMBER_TYPE) 字符串由大写字母和数字混合组成
     *        <br> type=6(RandomSeed.ALL_TYPE) -- 字符串由大小写字母和数字混合组成
     *        <br> type=7(RandomSeed.OTHER_TYPE) -- 字符串由大小写字母和数字和特殊字符混合组成
     *        <br> type=8(RandomSeed.HEX_TYPE) -- 字符串全部由16位数组成
     * @return String
     */
    public static String getRandomString(int randomtype){
    	String randomstring=getRandomString(randomtype,-1);
    	return randomstring;
    }
    
    /**
     * 取得一个由数字、大小写字母、和特殊字符组成的随机字符串
     * @param randomtype 
     *        <br> type=0(RandomSeed.LOWERCASE_TYPE) -- 字符串全部由小写字符组成
     *        <br> type=1(RandomSeed.UPPERCASE_TYPE) -- 字符串全部由大写字母组成
     *        <br> type=2(RandomSeed.NUMBER_TYPE) -- 字符串全部由数字组成
     *        <br> type=3(RandomSeed.LOWERCASEANDUPPERCASE_TYPE) 字符串由大小写字母混合组成
     *        <br> type=4(RandomSeed.LOWERCASEANDNUMBER_TYPE) 字符串由小写字母和数字混合组成
     *        <br> type=5(RandomSeed.UPPERCASEANDNUMBER_TYPE) 字符串由大写字母和数字混合组成
     *        <br> type=6(RandomSeed.ALL_TYPE) -- 字符串由大小写字母和数字混合组成
     *        <br> type=7(RandomSeed.OTHER_TYPE) -- 字符串由大小写字母和数字和特殊字符混合组成
     *        <br> type=8(RandomSeed.HEX_TYPE) -- 字符串全部由16位数组成
     * @param length 
     *        <br> 生成的字符串的长度
     * @return String
     */
    public static String getRandomString(int randomtype,int length){
    	if(length<=0){
        	int biglength=100;
        	int templength=0;
        	String temp=String.valueOf(System.currentTimeMillis());
        	templength=temp.length();
        	int nowlength=biglength-templength;
        	int[] thelength=new int[nowlength];
        	for(int i=0;i<thelength.length;i++) {
        		thelength[i]=templength+i;
        	}
        	length=getRandomFromArray(thelength);
    	}
    	
    	StringBuffer random=new StringBuffer();
    	for(int i=0;i<length;i++){
    		switch(randomtype){
 		     case LOWERCASE_TYPE:
     		    char c0=(char)(Math.random()*26+'a');
     		    random.append(c0);
     		    break;
   		     case UPPERCASE_TYPE:
     		    char c1=(char)(Math.random()*26+'A');
     		    random.append(c1);
     		    break;
   		     case NUMBER_TYPE:
      		    char c2=(char)(Math.random()*10+'0');
      		    random.append(c2);
      		    break;
   		     case LOWERCASEANDUPPERCASE_TYPE:
    			char[] limit3={'a','A'};
      		    char temp3=getRandomFromArray(limit3);
      		    char c3=(char)(Math.random()*26+temp3);
      		    random.append(c3);
      		    break;
   		     case LOWERCASEANDNUMBER_TYPE:
      			char[] limit4={'a','0'};
      		    char temp4=getRandomFromArray(limit4);
      		    char c4='\u0000';
      		    if(temp4=='0'){
      		    	c4=(char)(Math.random()*10+temp4);
      		    }else{
      		    	c4=(char)(Math.random()*26+temp4);
      		    }
      		    random.append(c4);
      		    break;
   		     case UPPERCASEANDNUMBER_TYPE:
       			char[] limit5={'A','0'};
       		    char temp5=getRandomFromArray(limit5);
       		    char c5='\u0000';
       		    if(temp5=='0'){
       		    	c5=(char)(Math.random()*10+temp5);
       		    }else{
       		    	c5=(char)(Math.random()*26+temp5);
       		    }
       		    random.append(c5);
       		    break;     
    		 case ALL_TYPE:
       			char[] limit6={'a','A','0'};
        		char temp6=getRandomFromArray(limit6);
        		char c6='\u0000';
        		if(temp6=='0'){
        		    c6=(char)(Math.random()*10+temp6);
        		}else{
        		    c6=(char)(Math.random()*26+temp6);
        		}
        		random.append(c6);
        		break; 
    		 case HEX_TYPE:
        		char[] limit8={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
            	char c8=getRandomFromArray(limit8);
            	random.append(c8);
            	break; 
     		 case OTHER_TYPE:
     		 default :
        	    char[] limit7={'a','@','0','#','('};
     		    char temp7=getRandomFromArray(limit7);
     		    char c7='\u0000';
     		    if(temp7=='0'){
     		    	c7=(char)(Math.random()*10+temp7);
     		    }else if(temp7=='a'){
     		    	c7=(char)(Math.random()*26+temp7);
     		    }else if(temp7=='@'){
     		    	c7=(char)(Math.random()*27+temp7);
     		    }else if(temp7=='#'){
     		    	c7=(char)(Math.random()*4+temp7);
     		    }else if(temp7=='('){
     		    	c7=(char)(Math.random()*4+temp7);
     		    }
     		    random.append(c7);
     		    break;   			 
   		    }
    	}
    	return random.toString();
    }
    
    /**
     * 获取一定数量的不重复的由数字、大小写字母、和特殊字符组成的随机字符串
     * @param randomtype 
     *        <br> type=0(RandomSeed.LOWERCASE_TYPE) -- 字符串全部由小写字符组成
     *        <br> type=1(RandomSeed.UPPERCASE_TYPE) -- 字符串全部由大写字母组成
     *        <br> type=2(RandomSeed.NUMBER_TYPE) -- 字符串全部由数字组成
     *        <br> type=3(RandomSeed.LOWERCASEANDUPPERCASE_TYPE) 字符串由大小写字母混合组成
     *        <br> type=4(RandomSeed.LOWERCASEANDNUMBER_TYPE) 字符串由小写字母和数字混合组成
     *        <br> type=5(RandomSeed.UPPERCASEANDNUMBER_TYPE) 字符串由大写字母和数字混合组成
     *        <br> type=6(RandomSeed.ALL_TYPE) -- 字符串由大小写字母和数字混合组成
     *        <br> type=7(RandomSeed.OTHER_TYPE) -- 字符串由大小写字母和数字和特殊字符混合组成
     *        <br> type=8(RandomSeed.HEX_TYPE) -- 字符串全部由16位数组成
     * @param length 字符串长度，if(length<0) 则不限制长度
     * @param amount if(amount<1) return null
     * @return ArrayList
     */
    public static ArrayList getRandomString(int randomtype,int length,int amount) {
    	ArrayList randomstringList=new ArrayList();
    	if(amount<1) {
    		return null;
    	}
    	for(int i=0;i<amount;) {
    		String randomstring=null;
    		if(length<=0) {
    			randomstring=getRandomString(randomtype);
    		}else {
    			randomstring=getRandomString(randomtype,length);
    		}
			if(!randomstringList.contains(randomstring)) {
				randomstringList.add(randomstring);
				i++;
			}
    	}
    	return randomstringList;
    }
    
    /**
     * 取得一个随即颜色
     * @return String
     */
    public static String getRandomColor() {
    	StringBuffer randomColor=new StringBuffer();
    	int colorlength=6;
    	String[] array= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    	for(int i=0;i<colorlength;i++) {
    		randomColor.append(getRandomFromArray(array));
    	}
    	return randomColor.toString();
    }
    
    /**
     * 取得不重复的颜色
     * @param colortype
     * @return ArrayList
     */
    public static ArrayList getRandomColor(int colortype) {
    	if(colortype<1) {
    		return null;
    	}
    	ArrayList colorList=new ArrayList();
    	for(int i=0;i<colortype;) {
    		String newcolor=getRandomColor();
    		if(!colorList.contains(newcolor)) {
    			colorList.add(newcolor);
    			i++;
    		}
    	}
    	return colorList;
    }
    
    /**
     * 从一个数组中随机取出一个
     * @param array
     * @return String
     */
    public static String getRandomFromArray(String[] array){
    	String random="";
    	int length=array.length;
    	for(int i=0;i<length;i++){
    		int index=(int)(Math.random()*length);
    		random=array[index];
    	}
    	return random;
    }
    
    /**
     * 从一个数组中随机取出一个
     * @param array
     * @return char
     */
    public static char getRandomFromArray(char[] array){
    	char random='\u0000';
    	int length=array.length;
    	for(int i=0;i<length;i++){
    		int index=(int)(Math.random()*length);
    		random=array[index];
    	}
    	return random;
    }

    /**
     * 从一个数组中随机取出一个
     * @param array
     * @return int
     */
    public static int getRandomFromArray(int[] array){
    	int random=0;
    	int length=array.length;
    	for(int i=0;i<length;i++){
    		int index=(int)(Math.random()*length);
    		random=array[index];
    	}
    	return random;
    }

    /**
     * 从一个数组中随机取出一个
     * @param array
     * @return long
     */
    public static long getRandomFromArray(long[] array){
    	long random=0;
    	int length=array.length;
    	for(int i=0;i<length;i++){
    		int index=(int)(Math.random()*length);
    		random=array[index];
    	}
    	return random;
    }
    
    /**
     * 从一个数组中随机取出一个
     * @param array
     * @return Object
     */
    public static Object getRandomFromArray(Object[] array){
    	Object random=null;
    	int length=array.length;
    	for(int i=0;i<length;i++){
    		int index=(int)(Math.random()*length);
    		random=array[index];
    	}
    	return random;
    }
    
    /**
     * 从预先设定的数组中随机取出一定数量的不重复的数据
     * @param array
     * @param length
     * @return
     */
    public static List getRandomListFromArray(String[] array,int length){
    	List list=new ArrayList();
    	for(int i=0;i<length;i++){
    		String string=getRandomFromArray(array);
    		while(list.contains(string)){
    			string=getRandomFromArray(array);
    		}
    		list.add(string);
    	}
    	return list;
    }
    

    public static void main(String[] args) {
    	String[] countArray={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
    	List list=new ArrayList();
    	for(int i=0;i<7;i++){
    		String string=getRandomFromArray(countArray);
    		while(list.contains(string)){
    			string=getRandomFromArray(countArray);
    		}
    		list.add(string);
    	}
    	System.out.println(list);
    }
}