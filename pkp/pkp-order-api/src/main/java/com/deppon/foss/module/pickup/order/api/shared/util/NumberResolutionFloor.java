/**
 * @remark 
 * @author YANGBIN
 * @date 2014-4-1 下午3:41:42
 */
package com.deppon.foss.module.pickup.order.api.shared.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/** 
 * @ClassName: NumberResolutionFloor 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author YANGBIN
 * @date 2014-4-1 下午3:41:42 
 *  
 */
public class NumberResolutionFloor {
	//截取关键字
	private static String[] floors = {"楼","层","室"};
	//只允许是数字
	private static String patternStr = "\\d+"; 
	/**
	 * 
	 * @Title: getFloorNum 
	 * @Description: TODO(根据传入的字符串str，以字符楼/层/室为标记，获取阿拉伯数字，并只取前2位) 
	 * @param @param str
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	public static Integer getFloorNum(String str){
		//初始化参数为零
		Integer floorNum = null;
		//取得楼层数字字符串
		String intString = getFloorNumString(str);
		//如果楼层号不为空
		if(StringUtils.isNotEmpty(intString)){
			//最终楼层数字
			floorNum = Integer.valueOf(intString);
		}
		return floorNum;
	}
	
	/**
	 * 
	 * @Title: getStringByFlag 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param str
	 * @param @param flg
	 * @param @return    设定文件 
	 * @return string    返回类型 
	 * @throws
	 */
	private static String getFloorNumString(String str){
		String numString = "";
		//正则表达式匹配
		Pattern pattern = Pattern.compile(patternStr);
		//匹配结果对象
		Matcher ma = pattern.matcher(str);
		//定义一个标志位，用来判断是否优先找到X楼>X层>X室
		int count = 0;
		//安装数字匹配分组
		while (ma.find()) {
			StringBuffer subStr = null;
			//取得数字字符串
			numString = ma.group();
			//按照X楼>X层>X室优先找楼层数字
			for(String flg : floors){
				subStr = new StringBuffer();
				//分组的数字与楼|层|室拼接
				String newStr = subStr.append(numString).append(flg).toString();
				//如果第一次匹配成功，就跳出当前for循环，否则继续
				if(str.indexOf(newStr) > 0){
					if(flg.equals(floors[0])){
						//并将标志位置为1，表示已经楼匹配成功
						count = 1;
						break;
					}
					if(flg.equals(floors[1])){
						//并将标志位置为2，表示已经层匹配成功
						count = 2;
						break;
					}
					if(flg.equals(floors[2])){
						//并将标志位置为2，表示已经层匹配成功
						count = SettlementReportNumber.THREE;
						break;
					}
				
				}
			}
			//判断只要匹配成功，跳槽while循环
			if(count > 0){
				break;
			}
			
		}
		if(count==0){
			return null;
		}
		return splitString(numString,count);
	}
	/**
	 * 
	 * @Title: splitString 
	 * @Description: 如果字符串的长度超过2位，则取前两位
	 * @param @param str
	 * @param @param flag
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	private static String splitString(String str,int flag){
		//初始化变量
		String subString = "";
		//如果截取字符不为空
		if(StringUtils.isNotEmpty(str)){
			//封装成整数类，防止0在首位
			Integer strNum = new Integer(str); 
			//取得最新截取字符串
			String newStr = String.valueOf(strNum.intValue());
			//判断截取标志位3,按层解析，则有3位取1，有4为取2
			if(flag == SettlementReportNumber.THREE){
				if(str.length() > SettlementReportNumber.THREE){
					//超过2位，则取前两位
					subString = newStr.substring(0,2);
				}else {
					//否则不截取
					subString = newStr.substring(0,1);
				}
			}else {
				subString = newStr;
			}
			
		}
		return subString;
	}
	public static void main(String[] args){
		System.out.println(NumberResolutionFloor.getFloorNum("上海上海市青浦区明珠路1018号18楼1801室")+"===");
	}
}
