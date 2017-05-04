/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/NumberValidate.java
 * 
 * FILE NAME        	: NumberValidate.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:校验运单录入界面数据是否匹配</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  2012-4-1 linws    新增
* </div>  
********************************************
 */
public class NumberValidate {
	
	private static final int FIVE = 5;
	
	private static final int TWENTY = 20;

	/** 
    * 检查email输入是否正确 
    * 正确的书写格式为 username@domain 
    */  
   public static boolean checkEmail(String value, int length) {  
           return value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")&&value.length()<=length;  
   }  
 
   /** 
    * 检查电话输入是否正确 
    * 正确格式 87654321,012387654321或012-87654321、0123-87654321
    */  
   public static boolean checkPhone(String value) {  
	   return value.matches("((\\d{7,})([，,、/]\\d{7,})*)|((\\d{3}[-]?\\d{7,}|\\d{4}[-]?\\d{7,})([，,、/](\\d{3}[-]?\\d{7,}|\\d{4}[-]?\\d{7,}))*)");    
   }
   
   /**
     * 检查电话和手机号码是否正确（中间用",/，、"4字符隔开）
     * @author 026123-foss-lifengteng
     * @date 2013-1-8 下午5:01:38
     */
    public static boolean checkPhoneAndMobile(String value){
	   String regStr = "((((0\\d{2}[-]?\\d{8}|0\\d{3}[-]?\\d{7}|\\d{7}|(1[0-9]{10})){1}([，、,/])?)*)(((([，、,/]){1}0\\d{2}[-]?\\d{8}|0\\d{3}[-]?\\d{7}|(1[0-9]{10})){1})))|((((0\\d{2}[-]?\\d{8}|0\\d{3}[-]?\\d{7}|\\d{7}|(1[0-9]{10})){1}))+)";
	   value = value.replace(" ", "");
	   return value.matches(regStr);
   }
 
   /** 
    * 检查手机输入是否正确 
    */  
   public static boolean checkMobile(String value) {  
       return value.matches("1[0-9]{10}");  
   }  
 
   /** 
    * 检查中文名输入是否正确 
    */  
   public static boolean checkChineseName(String value, int length) {  
       return value.matches("^[\u4e00-\u9fa5]+$")&&value.length()<=length;  
   }   
   
   /** 
    * 检查邮编是否合法 
    */  
   public static boolean checkPostCode(String value){  
       return value.matches("[1-9]\\d{5}(?!\\d)");  
   } 
   
   /** 
    * 检查身份证是否合法,15位或18位 
    */  
   public static boolean checkIDCard(String value){  
       return value.matches("\\d{15}|\\d{18}");  
   }  
   
   /** 
    * 检查输入是否超出规定长度 
    */  
   public static boolean checkLength(String value, int length) {  
       return ((value == null || "".equals(value.trim())) ? 0 : value.length()) <= length;  
   } 
   
   /** 
    * 检查输入是否在规定长度内 
    */  
   public static boolean checkBetweenLength(String value,int lLength, int mLength) {  
       return ((value == null || "".equals(value.trim())) ? 0 : value.length()) >= lLength 
    		   && ((value == null || "".equals(value.trim())) ? 0 : value.length()) <= mLength;  
   } 
 
   /** 
    * 检查是否为空字符串,空：true,不空:false 
    */  
   public static boolean checkNull(String value) {  
       return value == null || "".equals(value.trim());  
   }  
   
   /** 
    * 检查输入是否是全数字
    */  
   public static boolean checkIsAllNumber(String value) {  
	   return value.matches("^[0-9]*$");  
   }  
   
   
   /** 
    * 检查输入是否是第一位是英文后面全数字
    */  
   public static boolean checkIsAllNumberAndStartEnglish(String value) {  
	   return value.matches("^[a-zA-Z]{1}[0-9]*$");  
   }  
  
   
   /** 
    * 判断数字(整型，浮点) 正则表达式 
    */  
   public static boolean checkIntOrFloat(String value) {  
       return value.matches("[0-9]*(\\.?)[0-9]*");
   }  
   
   /** 
    * 检查输入是否符合尺寸规范(a*b*c*d +/- e*f*g*h)
    */  
   public static boolean checkIsGoodsSize(String value) {  
	   return value.matches("^\\d+(\\.\\d+)?(\\*\\d+(\\.\\d+)?){2,4}((\\+|\\-)\\d+(\\.\\d+)?(\\*\\d+(\\.\\d+)?){2,4})*$");  
   }  
   
   /** 
    * 检查电话输入是否正确 
    * 正确格式 87654321,012387654321或012-87654321、0123-87654321
    */  
   public static boolean checkPhoneWaybill(String value) {  
	   return value.matches("[0-9/-]{7,40}");    

   }
   
  /**
   * 5-20位数字（包含“—”）
   * @param value 
   * @return
   * @author 272311
   * @create 2015-9-9上午9:09:38
   */
   public static boolean checkPhoneWaybillExp(String value){
	   if(StringUtils.isNotBlank(value) && value.length()>= FIVE && value.length()<= TWENTY){
		   return value.matches("^(\\d{1,}-?\\d{1,})+?$");
	   }else{
		   return false ;
	   }
   }
   
}