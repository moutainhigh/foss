package com.deppon.foss.module.pickup.waybill.shared.util;

import java.math.BigInteger;
import java.util.Date;

import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 *@项目：快递自动补录项目
 *@功能：验证各属性是否满足开单崖求
 *@author:218371-foss-zhaoyanjun
 *@date:2015-07-09下午13:59
 */
public class ExpressAutomaticMakeupUtil {
	
	/**
	 * 300
	 */
	private static final int NUM300 = 300;
	
	/**
	 * 50
	 */
	private static final int NUM50 = 50;
	//发货客户，收货客户
	public static boolean testDeliveryCustomerName(String deliveryCustomerName) {
		if (StringUtil.isEmpty(deliveryCustomerName)) {
			return false;
		} else {
			return true;
		}

	}
	
	//收货客户手机号码，发货客户手机号码
	public static boolean testMobilephone(String deliveryCustomerMobilephone) {
		// 判断电话号码的格式
		if (StringUtil.isEmpty(deliveryCustomerMobilephone)) {
			return false;
		} else {
			return true;
		}

	}
	
	//收货客户电话号码，发货客户电话号码
	public static boolean testPhone(String deliveryCustomerPhone) {
		// 判断电话的格式
		if (StringUtil.isEmpty(deliveryCustomerPhone)) {
			return false;
		} else {
			return true;
		}

	}
	
	// 收货客户详细地址
	public static boolean testReceiveCustomerAddress(
			String receiveCustomerProvCode) {
		// 判断地址是否为空 长度是否大于100
		if (StringUtil.isEmpty(receiveCustomerProvCode)
				|| receiveCustomerProvCode.length() > NUM300) {
			return false;
		} else {
			return true;
		}
	}
	
	// 收货客户详细地址
	public static boolean testDeliveryCustomerAddress(
			String deliveryCustomerProvCode) {
		// 判断地址是否为空 长度是否大于100
//		if (deliveryCustomerProvCode.length() > 300) {
//			return false;
//		} else {
			return true;
//		}
	}
	
	// 收货客户详细地址
	public static boolean testReceiveCustomerPOrC(
			String receiveCustomerProvCode) {
		// 判断地址是否为空 长度是否大于100
		if (StringUtil.isEmpty(receiveCustomerProvCode)
				|| receiveCustomerProvCode.length() > NUM50) {
			return false;
		} else {
			return true;
		}
	}
	
	// 发货客户详细地址
	public static boolean testDeliveryCustomerPOrC(String deliveryCustomerProvCode) {
		// 判断地址是否为空 长度是否大于100
//		if (deliveryCustomerProvCode.length() > 50) {
//			return false;
//		} else {
			return true;
//		}
	}
	
//	// 收货客户详细地址
//	public static boolean testReceiveCustomerDetailedAddress(
//			String receiveCustomerProvCode) {
//		// 判断地址是否为空 长度是否大于100
//		if (StringUtil.isEmpty(receiveCustomerProvCode)
//				|| receiveCustomerProvCode.length() > 150) {
//			return false;
//		} else {
//			return true;
//		}
//	}
	
//	// 发货客户详细地址
//	public static boolean testDeliveryCustomerDetailedAddress(
//			String deliveryCustomerProvCode) {
//		// 判断地址是否为空 长度是否大于100
//		if (deliveryCustomerProvCode.length() > 150) {
//			return false;
//		} else {
//			return true;
//		}
//	}
	
	//保险价值，代收货款校验
	public static boolean testInsuranceAmount(BigInteger insuranceAmount) {
		if(insuranceAmount==null){
			return false;
		}else{
			return true;
		}
		
	}

//	/**
//	 * @author shenshang
//	 * @date 2015/5/18 包装费 费用合计 不为空
//	 */
//
//	public static boolean testPackageFeeCanvas(String packageFeeCanvas) {
//		// 判断是否为数字
//		String q = "^\\+?[1-9][0-9]*$";
//		if (StringUtil.isEmpty(packageFeeCanvas)
//				|| !packageFeeCanvas.matches(q)) {
//			return false;
//		} else {
//			return true;
//		}
//	}
	
	//判断是否有联系方式
	public static boolean testAnyphone(String mobilePhone,String phone){
		if(testMobilephone(mobilePhone)||testPhone(phone)){
			return true;
		}else{
			return false;
		}
	}
	
	//判断代收货款类型
	public static boolean testRefundType(String refundType){
//		if(refundType.length()!=1||(!"1".equals(refundType)&&!"2".equals(refundType))){
//			return false;
//		}else{
			return true;
//		}
	}
	
	//判断开户名,开户行，收款帐号
	public static boolean testAccountName(String accountName){
//		if(accountName.length()>100){
//			return false;
//		}else{
			return true;
//		}
	}
	
	//判断是否有图片上传时间
	public static boolean testUplordTime(Date date){
		if(date==null){
			return false;
		}
		return true;
	}
}
