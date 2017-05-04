package com.deppon.foss.module.pickup.pricing.api.shared.util;

import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;

public class Google{
	 //阿拉伯数字,字符串类型
	 public String number;
	 //阿拉伯数字,int类型
	 public int inumber;
	//阿拉伯数字的位数
	 public int size;
	 //构造函数
	 public Google(String value){
	  number = value;
	  inumber = Integer.parseInt(value);
	  size = Google.getLength(inumber);
	  }
	 //阿拉伯数字每一位
	 public static final String[] num = {"0","1","2","3","4","5","6","7","8","9"};
	 //中文数字每一位
	 public static final String[] china = {"零","一","二","三","四","五","六","七","八","九"};
	 //中文数字进位度量
	 public static final String[] degree = {"十","百","千","万","亿"};
	 //计算输入数据长度
	 public static int getLength(int value){
	    return (int)Math.floor((int)(Math.log10(value)+1));
	 }
	 //将输入数字转换成相应文字,不添加度量
	 public static String convertAtoW(String value){
	   for(int i=0;i<num.length;i++){
	    value = value.replaceAll(num[i],china[i]);
	   }
	return value;
	 }
	 //度量处理方法,处理到最高位为九位的数字,如果处理更高位的数字则需要做微小改动
	 public String degree(String value){
	  StringBuffer temp = new StringBuffer(value);
	  //添加"十"
	  if(size>NumericConstants.NUM_1){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_1,degree[NumericConstants.NUM_0]);
	  }
	  //添加"百"
	  if(size>NumericConstants.NUM_2){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_3,degree[NumericConstants.NUM_1]);
	  }
	  //添加"千"
	  if(size>NumericConstants.NUM_3){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_5,degree[NumericConstants.NUM_2]);
	  }
	  //添加"万"
	  if(size>NumericConstants.NUM_4){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_7,degree[NumericConstants.NUM_3]);
	  }
	//添加"十万"
	  if(size>NumericConstants.NUM_5){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_9,degree[NumericConstants.NUM_0]);
	  }
	  //添加"百万"
	  if(size>NumericConstants.NUM_6){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_11,degree[NumericConstants.NUM_1]);
	  }
	  //添加"千万"
	  if(size>NumericConstants.NUM_7){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_13,degree[NumericConstants.NUM_2]);
	  }
	  //添加"亿"
	  if(size>NumericConstants.NUM_8){
	   temp = temp.insert(temp.length()-NumericConstants.NUM_15,degree[NumericConstants.NUM_4]);
	  }
	   //添加"十亿"以后处理类似,需要用BigInteger
	  return temp.toString();
	 }
	 //处理多零情况
	  public String degreezero(String value){
	  String temp = new String(value);
	while((temp.indexOf("零千")!=-1)||(temp.indexOf("零百")!=-1)||(temp.indexOf("零十")!=-1)||(temp.indexOf("零零")!=-1)){
	   if(temp.indexOf("零千")!=-1){
	   temp = temp.replaceAll("零千","零");
	   }
	   if(temp.indexOf("零百")!=-1){
	      temp = temp.replaceAll("零百","零");
	   }
	   if(temp.indexOf("零十")!=-1){
	   temp = temp.replaceAll("零十","零");
	   }
	   if(temp.indexOf("零零")!=-1){
	   temp = temp.replaceAll("零零","零");
	   }
	  }
	  if((temp.indexOf("零万")!=-1)){
	   temp = temp.replaceAll("零万","万");
	  }
	        if((temp.indexOf("亿万")!=-1)){
	   temp = temp.replaceAll("万","");
	  }
	  //末尾不能有零
	  if(temp.lastIndexOf("零")==temp.length()-1){
	   temp = temp.substring(0,temp.length()-1);
	  }
	return temp;
	    }
	     
	 public static void main(String[] args) {
	      Google google = new Google("210");
	      String result = google.degreezero(google.degree(Google.convertAtoW(google.number)));
	        System.out.println(result);
	       
	 }
	}