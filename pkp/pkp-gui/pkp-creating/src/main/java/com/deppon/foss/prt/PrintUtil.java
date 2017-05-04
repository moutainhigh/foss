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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/PrintUtil.java
 * 
 * FILE NAME        	: PrintUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.prt;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * (打印公共方法类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-18 下午2:03:14,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-18 下午2:03:14
 * @since
 * @version
 */
public class PrintUtil {

	/**
	 * logger
	 */
	private static final Log LOG = LogFactory.getLog(PrintUtil.class);
	
	private static final II18n i18n = I18nManager.getI18n(PrintUtil.class);
	
	/**
	 * digit
	 */
//	private static String[] digit = { "", "拾", "佰", "千", "万", "拾万", "佰万", "千万",
//			"亿", "拾亿", "佰亿", "千亿", "万亿" };
	
	
	private static final String CENT = i18n.get("foss.gui.creating.printUtil.chinese.cent");//分
	private static final String DIME = i18n.get("foss.gui.creating.printUtil.chinese.dime");//角
	private static final String TEN = i18n.get("foss.gui.creating.printUtil.chinese.ten");//拾
	private static final String HUNDRED = i18n.get("foss.gui.creating.printUtil.chinese.hundred");//佰
	private static final String THOUSAND = i18n.get("foss.gui.creating.printUtil.chinese.thousand");//千
	private static final String TENTHOUSAND = i18n.get("foss.gui.creating.printUtil.chinese.tenThousand");//万
	private static final String ONE_HUNDRED_THOUSAND  = i18n.get("foss.gui.creating.printUtil.chinese.oneHundredThousand");//拾万
	private static final String MILLION  = i18n.get("foss.gui.creating.printUtil.chinese.million");//佰万
	private static final String TENMILLION = i18n.get("foss.gui.creating.printUtil.chinese.tenMillion");//千万
	private static final String ONEHUNDREDMILLION = i18n.get("foss.gui.creating.printUtil.chinese.oneHundredMillion");//亿
	private static final String BILLION = i18n.get("foss.gui.creating.printUtil.chinese.billion");//拾亿
	private static final String TENBILLION = i18n.get("foss.gui.creating.printUtil.chinese.tenBillion");//佰亿
	private static final String ONEHUNDREDBILLION = i18n.get("foss.gui.creating.printUtil.chinese.oneHundredBillion");//千亿
	private static final String ONETHOUSANDBILLION = i18n.get("foss.gui.creating.printUtil.chinese.oneThousandBillion");//万亿
	
	
	private static final String ZERO = i18n.get("foss.gui.creating.printUtil.chinese.zero");//零
	private static final String ONE = i18n.get("foss.gui.creating.printUtil.chinese.one");//壹
	private static final String TWO = i18n.get("foss.gui.creating.printUtil.chinese.two");//贰
	private static final String THREE = i18n.get("foss.gui.creating.printUtil.chinese.three");//叁
	private static final String FOUR = i18n.get("foss.gui.creating.printUtil.chinese.four");//肆
	private static final String FIVE = i18n.get("foss.gui.creating.printUtil.chinese.five");//伍
	private static final String SIX = i18n.get("foss.gui.creating.printUtil.chinese.six");//陆
	private static final String SEVEN = i18n.get("foss.gui.creating.printUtil.chinese.seven");//柒
	private static final String EIGHT = i18n.get("foss.gui.creating.printUtil.chinese.eight");//捌
	private static final String NINE = i18n.get("foss.gui.creating.printUtil.chinese.nine");//玖
	
	
	
	private static String[] digit = { "", TEN, HUNDRED, THOUSAND, TENTHOUSAND, ONE_HUNDRED_THOUSAND, MILLION, TENMILLION,
	    ONEHUNDREDMILLION, BILLION, TENBILLION, ONEHUNDREDBILLION, ONETHOUSANDBILLION };

	/**
	 * sub point
	 */
	private static String[] bb = { CENT, DIME };
	
	/**
	 * 元
	 */
	private static final String YUAN = "元";

	/**
	 * 整
	 */
	private static final String ZHENG = "整";
	
	/**
	 * 斜线
	 */
	public static final String OBLIQUE_LINE = "/";
	public static final String COMPANY_PHONE = "400-830-5555";
	@Inject
	private IWaybillService waybillService;
	
	public PrintUtil(){
		waybillService = WaybillServiceFactory.getWaybillService();
	}

	
	/**
	 * <p>
	 * (取得数字对应的中文)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-25 上午11:17:35
	 * @param money
	 * @return
	 * @see
	 */
	public static String numtochinese(double money) {
		if (money >= 0) {
			BigDecimal b = new BigDecimal(String.valueOf(money));

			String strMoney = "" + b.setScale(2, BigDecimal.ROUND_UNNECESSARY);
			// System.out.println(strMoney);
			String[] amt = strMoney.split("\\.");
			strMoney = getYuan(amt[0]) + YUAN + getJiaoFen(amt[1]);// +"整";
			return strMoney;
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * ( 得到分角部分)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-25 上午11:17:50
	 * @param s
	 * @return
	 * @see
	 */
	public static String getJiaoFen(String s) {
		char[] c = s.toCharArray();
		int len = c.length;
		StringBuffer ch = new StringBuffer("");
		String d = "";
		for (int i = 0; i < c.length; i++) {
			if (i > 0 && c[i] == '0' && c[i] == c[i - 1]) {
				--len;
				continue;
			}
			// 得到数字对应的中文
			ch.append(getChinese(c[i]));
			// 非零时, 显示是几佰, 还是几千

			if (!getChinese(c[i]).equals(ZERO)) {
				if (c.length == 2) {
					d = bb[--len];
				} else {
					d = bb[len];
				}
				ch.append(d);
			} else {
				--len; // 如果是0则不取位数
			}
		}
		// 如果最后一位是 0, 则去掉
		if ((ch.charAt(ch.length() - 1)) == '零') {
			ch = new StringBuffer(ch.substring(0, ch.length() - 1));
		}

		if (ch.indexOf(DIME) == -1 && ch.indexOf(CENT) == -1) {
			ch.append(ZHENG);
		}
		return ch.toString();
	}

	/**
	 * <p>
	 * (得到元的部分)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-25 上午11:18:09
	 * @param s
	 * @return
	 * @see
	 */
	public static String getYuan(String s) {
		char[] c = s.toCharArray();
		StringBuffer chSb = new StringBuffer();
		int len = c.length;
		List<String> list = new ArrayList<String>();
		String d = "";
		for (int i = 0; i < c.length; i++) {
			// 如果有几个0挨在一起时, 只显示一个零即可
			if (i > 0 && c[i] == '0' && c[i] == c[i - 1]) {
				--len;
				continue;
			}
			// 得到数字对应的中文
			chSb.append(getChinese(c[i]));

			// 非零时, 显示是几佰, 还是几千
			if (!getChinese(c[i]).equals(ZERO)) {
				d = digit[--len];
				list.add(d);// 当数字中有万和拾万时, 要去掉拾万
				chSb.append(d);
			} else {
				--len; // 如果是0则不取位数
			}
		}
		String chStr = chSb.toString();
		// 如果同时包含有万和拾万, 则将拾万中的万去掉
		if (list.contains(TENTHOUSAND) && list.contains(ONE_HUNDRED_THOUSAND)) {
			chStr = chStr.replaceAll(ONE_HUNDRED_THOUSAND, TEN);
		}
		if (list.contains(TENTHOUSAND) && list.contains(MILLION)) {
			chStr = chStr.replaceAll(MILLION, HUNDRED);
		}
		if (list.contains(TENTHOUSAND) && list.contains(TENMILLION)) {
			chStr = chStr.replaceAll(TENMILLION, THOUSAND);
		}
		// 如果同时包含亿和拾亿, 则将拾亿中的亿字去掉
		if (list.contains(ONEHUNDREDMILLION) && list.contains(BILLION)) {
			chStr = chStr.replaceAll(BILLION, TEN);
		}
		if (list.contains(ONEHUNDREDMILLION) && list.contains(TENBILLION)) {
			chStr = chStr.replaceAll(BILLION, HUNDRED);
		}
		if (list.contains(ONEHUNDREDMILLION) && list.contains(ONEHUNDREDBILLION)) {
			chStr = chStr.replaceAll(ONEHUNDREDBILLION, THOUSAND);
		}
		if (list.contains(ONEHUNDREDMILLION) && list.contains(ONETHOUSANDBILLION)) {
			chStr = chStr.replaceAll(ONEHUNDREDBILLION, TENTHOUSAND);
		}
		// 如果最后一位是 0, 则去掉
		if ((chSb.charAt(chSb.length() - 1)) == '零') {
			chStr = chStr.substring(0, chStr.length() - 1);
		}
		return chStr;
	}

	/**
	 * <p>
	 * (取得数字对应的中文)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-25 上午11:19:03
	 * @param i
	 * @return
	 * @see
	 */
	public static String getChinese(char i) {
		String ch = "";
		switch (i) {
		case '0':
			ch = ZERO;
			break;
		case '1':
			ch = ONE;
			break;
		case '2':
			ch = TWO;
			break;
		case '3':
			ch = THREE;
			break;
		case '4':
			ch = FOUR;
			break;
		case '5':
			ch = FIVE;
			break;
		case '6':
			ch = SIX;
			break;
		case '7':
			ch = SEVEN;
			break;
		case '8':
			ch = EIGHT;
			break;
		case '9':
			ch = NINE;
			break;
		default :
			ch = "";
		}
		return ch;
	}

	/**
	 * <p>
	 * (获得yy-MM-dd格式的日期)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-19 下午1:54:29
	 * @param date
	 * @return
	 * @see
	 */
	@SuppressWarnings("finally")
	public static String getDate(Date date) {

		String str = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (date != null) {
				str = sf.format(date);
			}
		} catch (Exception e) {
			LOG.error("日期转换异常",e);
		} finally {
			return str;
		}
	}
	
	/**
	 * <p>
	 * (判断对象是否为空)
	 * </p>
	 * @author foss-jiangfei
	 * @date 2012-11-9 下午2:24:16
	 * @param value
	 * @return
	 * @see
	 */
	private String isBeanValueNull(Object value) {

		if (value == null) {
			return "";
		} else {

			return value.toString();
		}

	}
	
	/**
	 * 截取运单包装信息 10个字符，汉字算2个字符，数字一个
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-21 下午8:27:12
	 */
	private static String getPackageValue(String arg){
		if (StringUtils.isEmpty(arg)) {
			return "";
		}else {
//			StringBuffer sb = new StringBuffer();
//			//正哲表达式，以汉字区分
//			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
//			Matcher m = p.matcher(arg.trim());
//			int rleng=0;
//			String mstr;
//			while ( m.find()) {
//				if("0".equals(m.group())){
//					m.find();
//				}else{
//					mstr = m.group();
//					if( mstr.getBytes().length == mstr.length()){
//						rleng = rleng + mstr.getBytes().length;
//					}else{
//						rleng = rleng + mstr.length()*2;
//					}
//					if(rleng<=10){
//						sb.append(mstr);
//					}else{
//						continue;
//					}
//				}
//			}
//			return sb.toString();
//			StringBuffer sb = new StringBuffer();
//			//正哲表达式，以汉字区分
//			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
//			Matcher m = p.matcher(arg.trim());
//			int rleng=0;
//			String mstr;
//			while ( m.find()) {
//				if("0".equals(m.group())){
//					m.find();
//				}else{
//					mstr = m.group();
//					if( mstr.getBytes().length == mstr.length()){
//						rleng = rleng + mstr.getBytes().length;
//					}else{
//						rleng = rleng + mstr.length()*2;
//					}
//					if(rleng<=10){
//						sb.append(mstr);
//					}else{
//						continue;
//					}
//				}
//			}
//			return sb.toString();
			if(arg.length() <= NumberConstants.NUMBER_10){
				return arg;
			}else{
				return arg.substring(0, NumberConstants.NUMBER_10);
			}
		}
	}

/**
 * 封装 转寄退回件打印信息
 * 
 */
	public LabelPrintContext printForwardReturnData(BarcodePrintLabelDto printLabelBean){
		LabelPrintContext ctx = new LabelPrintContext();
		//单号
		ctx.put("number",isBeanValueNull(printLabelBean.getWaybillNumber()));
		//流水号
		ctx.put("serialnos",isBeanValueNull(printLabelBean.getPrintSerialnos()));
		//第二城市外场
		ctx.put("secLoadOrgName", StringUtil.defaultIfNull(printLabelBean.getSecLoadOrgName()));
		//到达 
		ctx.put("lastTransCenterCity",isBeanValueNull(printLabelBean.getLastTransCenterCity()));
		//总件数
		ctx.put("totalpieces",isBeanValueNull(printLabelBean.getTotalPieces()));
		//收货人姓名
		ctx.put("receiveCustomerContact",isBeanValueNull(printLabelBean.getWaybillBean().getReceiveCustomerContact()));
		//收货人电话或者手机
		if(StringUtils.isNotEmpty(printLabelBean.getWaybillBean().getReceiveCustomerMobilephone())){
			ctx.put("receiveCustomerphone",printLabelBean.getWaybillBean().getReceiveCustomerMobilephone());
		}else{
			ctx.put("receiveCustomerPhone",printLabelBean.getWaybillBean().getReceiveCustomerPhone());
		}
		//收货客户的地址 省市区 详细地址
		ctx.put("receiveCustomerProvName", isBeanValueNull(printLabelBean.getReceiveCustomerProvName()));
		ctx.put("receiveCustomerCityName", isBeanValueNull(printLabelBean.getReceiveCustomerCityName()));
		ctx.put("receiveCustomerDistName",isBeanValueNull(printLabelBean.getReceiveCustomerDistName()));
		ctx.put("receiveCustomerAddress", isBeanValueNull(printLabelBean.getWaybillBean().getReceiveCustomerAddress()));
		//保价金额
		ctx.put("insuranceFee",printLabelBean.getWaybillBean().getInsuranceAmount());
		//代收货款
		ctx.put("codAmount",printLabelBean.getWaybillBean().getCodAmount());
		//签回单
		if(StringUtils.isEmpty(printLabelBean.getWaybillBean().getReturnBillType())){
			ctx.put("returnBillType","");
		}else if("NONE".equals(printLabelBean.getWaybillBean().getReturnBillType())){
			ctx.put("returnBillType","无");
		}else{
			ctx.put("returnBillType","有");
		}
		//包装费
		ctx.put("packageFee",printLabelBean.getWaybillBean().getPackageFee());
		 //到付款(总费用)
		ctx.put("toPayAmount",printLabelBean.getWaybillBean().getToPayAmount());
		//运费(公布价)
		ctx.put("transportFee",printLabelBean.getWaybillBean().getTransportFee());
		//转寄退回
		ctx.put("forwardReturn", isBeanValueNull(printLabelBean.getForwardReturn()));
		//提货网点编码
		ctx.put("stationnumber",isBeanValueNull(printLabelBean.getDestinationCode()));
		//总件数
		ctx.put("totalpieces",isBeanValueNull(printLabelBean.getTotalPieces()));
		return ctx;
				
	}
	
	/**
	 * 封装打木标签数据
	 */
	public LabelPrintContext printWoodenLabelData(BarcodePrintLabelDto printLabelBean){
		LabelPrintContext ctx = new LabelPrintContext();
		//运单号
		ctx.put("waybillNo", isBeanValueNull(printLabelBean.getWaybillNumber()));
		//运输性质
		ctx.put("transtype", printLabelBean.getTranstype());
		//总件数
		ctx.put("NumTotal",printLabelBean.getTotalPieces());
		//总重量
		ctx.put("weight", printLabelBean.getWeight());
		//流水号
		ctx.put("serialnos",isBeanValueNull(printLabelBean.getPrintSerialnos()));
		//打印日期
		ctx.put("printdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		//用户工号
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(user!= null && user.getEmployee()!=null){
			ctx.put("optusernum",isBeanValueNull(user.getEmployee().getEmpCode()));
		}
		//是否显示“德邦物流”
		ctx.put("isPrintLogo", printLabelBean.getIsPrintLogo());
		//包装类型
		ctx.put("packing",isBeanValueNull(printLabelBean.getPacking()));
		if(StringUtils.isNotEmpty(printLabelBean.getStandGoodsSize())){
			int index=printLabelBean.getStandGoodsSize().indexOf("+");
			if(-1!=index){
				//打木架包装尺寸
				ctx.put("standGoodsSize",isBeanValueNull(printLabelBean.getStandGoodsSize().substring(0, printLabelBean.getStandGoodsSize().indexOf("+"))));
			}else{
				//打木架包装尺寸
				ctx.put("standGoodsSize",isBeanValueNull(printLabelBean.getStandGoodsSize()));
			}
		}else{
			//打木架包装尺寸
			ctx.put("standGoodsSize",isBeanValueNull(printLabelBean.getStandGoodsSize()));
		}
		//打木架货物件数
		ctx.put("standGoodsNum",isBeanValueNull(printLabelBean.getStandGoodsNum()));
		//打木架货物体积
		ctx.put("standGoodsVolume",isBeanValueNull(printLabelBean.getStandGoodsVolume()));
		//打木箱货物件数
		ctx.put("boxGoodsNum",isBeanValueNull(printLabelBean.getBoxGoodsNum()));
		if(StringUtils.isNotEmpty(printLabelBean.getBoxGoodsSize())){
			int indexz=printLabelBean.getBoxGoodsSize().indexOf("+");
			if(-1!=indexz){
				//打木箱货物尺寸
				ctx.put("boxGoodsSize",isBeanValueNull(printLabelBean.getBoxGoodsSize().substring(0, printLabelBean.getBoxGoodsSize().lastIndexOf("+"))));
			}else{
				//打木箱货物尺寸
				ctx.put("boxGoodsSize",isBeanValueNull(printLabelBean.getBoxGoodsSize()));
			}
		}else{
			//打木箱货物尺寸
			ctx.put("boxGoodsSize",isBeanValueNull(printLabelBean.getBoxGoodsSize()));
		}
		//打木箱货物体积
		ctx.put("boxGoodsVolume",isBeanValueNull(printLabelBean.getBoxGoodsVolume()));
		//打木托件数
		ctx.put("salverGoodsNum",isBeanValueNull(printLabelBean.getSalverGoodsNum()));
		//打木架要求
		ctx.put("standRequirement",isBeanValueNull(printLabelBean.getStandRequirement()));
		//木箱要求
		ctx.put("boxRequirement",isBeanValueNull(printLabelBean.getBoxRequirement()));
		//木托要求
		ctx.put("salverRequirement",isBeanValueNull(printLabelBean.getSalverRequirement()));
		return ctx;
	}
	/**
	 * <p>
	 * 封装打印数据
	 * </p> 
	 * @author foss-jiangfei
	 * @date 2012-11-9 下午2:24:16
	 * @param value
	 * @return
	 * @see
	 */
	public LabelPrintContext printLabelData(BarcodePrintLabelDto printLabelBean){
		LabelPrintContext ctx = new LabelPrintContext();
//		PrintUtil printUtil = new PrintUtil();
		// input param
		// ctx.put(key, value)
		
		ctx.put("addr1", isBeanValueNull(printLabelBean.getAddr1()));
		ctx.put("addr2", isBeanValueNull(printLabelBean.getAddr2()));
		ctx.put("addr3", isBeanValueNull(printLabelBean.getAddr3()));
		ctx.put("addr4", isBeanValueNull(printLabelBean.getAddr4()));
		ctx.put("location1",
				isBeanValueNull(printLabelBean.getLocation1()));
		ctx.put("location2",
				isBeanValueNull(printLabelBean.getLocation2()));
		ctx.put("location3",
				isBeanValueNull(printLabelBean.getLocation3()));
		ctx.put("location4",
				isBeanValueNull(printLabelBean.getLocation4()));
		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(user!= null && user.getEmployee()!=null){
			ctx.put("optusernum",isBeanValueNull(user.getEmployee().getEmpCode()));
		}

		//单号
		ctx.put("channelNumber",isBeanValueNull(printLabelBean.getChannelNumber()));
		//单号
		ctx.put("number",isBeanValueNull(printLabelBean.getWaybillNumber()));
		//流水号
		ctx.put("serialnos",isBeanValueNull(printLabelBean.getPrintSerialnos()));
		//始发站
		ctx.put("leavecity",isBeanValueNull(printLabelBean.getLeavecity()));
		//第二城市外场
		ctx.put("secLoadOrgName", StringUtil.defaultIfNull(printLabelBean.getSecLoadOrgName()));
		//目的站
		ctx.put("destination",isBeanValueNull(printLabelBean.getDestination()));
				//printUtil.getOrgSimpleName(waybillEntity.getCustomerPickupOrgCode())
		//运输性质  是否偏线
		ctx.put("isagent",isBeanValueNull(printLabelBean.getIsAgent()));

		// 打印标签，条码部分目的站编码，也就是提货网点编码（倒数第一位）
		ctx.put("stationnumber",isBeanValueNull(printLabelBean.getDestinationCode()));

		// 打印标签，条码部分最终外场编码（倒数第二位）
		ctx.put("deptno", isBeanValueNull(printLabelBean.getLastTransCenterNo()));

		// 最终外场ID
		ctx.put("finaloutfieldid",isBeanValueNull(printLabelBean.getFinaloutfieldid()));
		
		//最终外场简称
		ctx.put("finaloutname", isBeanValueNull(printLabelBean.getLastTransCenterCity()));
		
		// 350909     郭倩云       最终外场简称(例如武汉转运场简称武汉)
		ctx.put("lastFirstFinalOutName", isBeanValueNull(printLabelBean.getLastFirstTransCenterCity()));
		
		// 350909     郭倩云       倒数第二外场ID
		ctx.put("lastSecondFinaloutFieldId",isBeanValueNull(printLabelBean.getLastSecondFinaloutFieldId()));
		
		// 350909     郭倩云       倒数第二外场简称(例如武汉转运场简称武汉)
		ctx.put("lastSecondFinalOutName", isBeanValueNull(printLabelBean.getLastSecondTransCenterCity()));
		
		// 350909     郭倩云        倒数第三外场ID
		ctx.put("lastThirdFinaloutFieldId",isBeanValueNull(printLabelBean.getLastThirdFinaloutFieldId()));
		
		// 350909     郭倩云       倒数第三外场简称(例如武汉转运场简称武汉)
		ctx.put("lastThirdFinalOutName", isBeanValueNull(printLabelBean.getLastThirdTransCenterCity()));
		
		//重量
		ctx.put("weight", printLabelBean.getWeight());
		//总件数
		ctx.put("totalpieces",isBeanValueNull(printLabelBean.getTotalPieces()));
		//是否异常
		ctx.put("unusual",isBeanValueNull(printLabelBean.getUnusual()));
		//运输类型
		ctx.put("transtype",isBeanValueNull(printLabelBean.getTranstype()));
		//打印日期
		ctx.put("printdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		//提货方式
		ctx.put("deliver",isBeanValueNull(printLabelBean.getDeliverToDoor()));
		//货物类型
		ctx.put("goodstype",isBeanValueNull(printLabelBean.getGoodstype()));
		// 包装
		ctx.put("packing",getPackageValue(printLabelBean.getPacking()));
		//其他包装(用于零担电子运单)
		ctx.put("otherPackage", printLabelBean.getOtherPackage());
		ctx.put("preassembly", isBeanValueNull(printLabelBean.getPreassembly()));
		//是否打星标
		ctx.put("signFlag", printLabelBean.getIsStarFlag());
		//是否发货大客户
		ctx.put("deliveryBigCustomer", printLabelBean.getDeliveryBigCustomer());
		//是否收货大客户
		ctx.put("receiveBigCustomer", printLabelBean.getReceiveBigCustomer());
		//快递营业部是否是出发营业部
		ctx.put("isNoStop", printLabelBean.getIsNoStop());
		//wutao === start
		//DMANA-3745 :FOSS标签打印：货物标签左上角添加收货地址行政区域
		ctx.put("countyRegion", printLabelBean.getCountyRegion());
		//wutao == end
		//打印展会货标识
		ctx.put("isExhibitCargo", printLabelBean.getIsExhibitCargo());
		//是否显示“德邦物流”
		ctx.put("isPrintLogo", printLabelBean.getIsPrintLogo());
		//当运输性质为商务专递时打印“空”
		ctx.put("productCode", printLabelBean.getProductCode());
		//是否零担电子运单，如果是零担电子运单则添加“零担电子运单”标记
		ctx.put("isElecLtlWaybill", printLabelBean.getIsElecLtlWaybill());
		//对接外场
		ctx.put("partnerBillingLogo", printLabelBean.getPartnerBillingLogo());
		//350909       郭倩云       零担轻货上分拣结果
		ctx.put("sortingResult", printLabelBean.getSortingResult());
		//350909       郭倩云       零担轻货上分拣取得是城市简称
		ctx.put("simpleLeaveCity", printLabelBean.getSimpleLeaveCity());
		return ctx;
	}
	
	/**
	 * <p>
	 * 封装电子运单打印数据
	 * </p>
	 * @author foss-jiangfei
	 * @date 2012-11-9 下午2:24:16
	 * @param value
	 * @return
	 * @see
	 */
	public LabelPrintContext printEWaybillLabelData(EWaybillPrintDto printLabelBean){
		LabelPrintContext ctx = new LabelPrintContext();
		//单号
		ctx.put("waybillNo",isBeanValueNull(printLabelBean.getWaybillNo()));
		//订单号
		ctx.put("orderNo", StringUtil.defaultIfNull(printLabelBean.getOrderNo()));
		//客户订单号
		ctx.put("custOrderLine",StringUtil.defaultIfNull(printLabelBean.getCustOrderLine()));
		//订单创建时间
		ctx.put("orderTime", StringUtil.defaultIfNull(printLabelBean.getOrderTime()));
		//订单备注
		ctx.put("orderNotes", StringUtil.defaultIfNull(printLabelBean.getOrderNotes()));
		//订单支付方式
		ctx.put("orderPaidMethod",isBeanValueNull(printLabelBean.getOrderPaidMethod()));
		//发货客户名称
		ctx.put("deliveryCustomerName", StringUtil.defaultIfNull(printLabelBean.getDeliveryCustomerName()));
		//发货客户手机/联系方式
		ctx.put("deliveryCustomerMobilephone", StringUtil.defaultIfNull(printLabelBean.getDeliveryCustomerMobilephone()));
		ctx.put("deliveryCustomerPhone", StringUtil.defaultIfNull(printLabelBean.getDeliveryCustomerPhone()));
		//发货客户联系人
		ctx.put("deliveryCustomerContact", StringUtil.defaultIfNull(printLabelBean.getDeliveryCustomerContact()));
		//发货人地址
		ctx.put("deliveryCustomerAddress",isBeanValueNull(printLabelBean.getDeliveryCustomerAddress()));

		
		//收货客户名称
		ctx.put("receiveCustomerName", StringUtil.defaultIfNull(printLabelBean.getReceiveCustomerName()));
		//收货客户手机/联系方式
		ctx.put("receiveCustomerMobilephone", StringUtil.defaultIfNull(printLabelBean.getReceiveCustomerMobilephone()));
		//收货客户联系方式
		ctx.put("receiveCustomerPhone", StringUtil.defaultIfNull(printLabelBean.getReceiveCustomerPhone()));
		//收货客户联系人
		ctx.put("receiveCustomerContact", StringUtil.defaultIfNull(printLabelBean.getReceiveCustomerContact()));
		//收货人地址
		ctx.put("receiveCustomerAddress",isBeanValueNull(printLabelBean.getReceiveCustomerAddress()));
		//收货部门
		ctx.put("receiveOrgName", StringUtil.defaultIfNull(printLabelBean.getReceiveOrgName()));
		//开单部门
		ctx.put("createOrgName", StringUtil.defaultIfNull(printLabelBean.getCreateOrgName()));
		//收货部门
		ctx.put("customerPickupOrgName", StringUtil.defaultIfNull(printLabelBean.getCustomerPickupOrgName()));
		//货物名称
		ctx.put("goodsName", StringUtil.defaultIfNull(printLabelBean.getGoodsName()));
		//货物总件数
		ctx.put("goodsQtyTotal", printLabelBean.getGoodsQtyTotal());
		
		//货物总体积
		ctx.put("goodsVolumeTotal", StringUtil.defaultIfNull(printLabelBean.getGoodsVolumeTotal()));
		//货物总重量
		ctx.put("goodsWeightTotal", StringUtil.defaultIfNull(printLabelBean.getGoodsWeightTotal()));
		//计费重量
		ctx.put("billWeight", StringUtil.defaultIfNull(printLabelBean.getBillWeight()));
		//运输性质名称
		ctx.put("productName", StringUtil.defaultIfNull(printLabelBean.getProductName()));
		

		//提货方式
		ctx.put("receiveMethod", StringUtil.defaultIfNull(printLabelBean.getReceiveMethod()));
		//开单付款方式
		ctx.put("paidMethod", StringUtil.defaultIfNull(printLabelBean.getPaidMethod()));
		//总运费
		ctx.put("totalFee", StringUtil.defaultIfNull(printLabelBean.getTotalFee()));
		//公布价
		ctx.put("transportFee", StringUtil.defaultIfNull(printLabelBean.getTransportFee()));
		
		//货物类型名称
		ctx.put("goodsTypeName", StringUtil.defaultIfNull(printLabelBean.getGoodsTypeName()));
		//是否贵重物品
		ctx.put("preciousGoods", StringUtil.defaultIfNull(printLabelBean.getPreciousGoods()));
		//货物包装
		ctx.put("goodsPackage", StringUtil.defaultIfNull(printLabelBean.getGoodsPackage()));
		//保价声明价值
		ctx.put("insuranceAmount", StringUtil.defaultIfNull(printLabelBean.getInsuranceAmount()));
		//保价费用
		ctx.put("insuranceFee", StringUtil.defaultIfNull(printLabelBean.getInsuranceFee()));
		//包装费
		ctx.put("packageFee", StringUtil.defaultIfNull(printLabelBean.getPackageFee()));
		//返单类别
		ctx.put("returnBillType", StringUtil.defaultIfNull(printLabelBean.getReturnBillType()));
		//代收货款
		ctx.put("codAmount", StringUtil.defaultIfNull(printLabelBean.getCodAmount()));
		//代收账号
		ctx.put("accountNo", StringUtil.defaultIfNull(printLabelBean.getAccountNo()));

		//快递员(收)
		ctx.put("deliverMan", StringUtil.defaultIfNull(printLabelBean.getDeliverMan()));
		//收货日期
		ctx.put("receiveDate", StringUtil.defaultIfNull(printLabelBean.getReceiveDate()));
		//快递员(送)
		ctx.put("receiverMan", StringUtil.defaultIfNull(printLabelBean.getReceiverMan()));
		//派送日期
		ctx.put("deliverDate", StringUtil.defaultIfNull(printLabelBean.getDeliverDate()));
		
		//第二城市外场名称
		ctx.put("secondOutfieldName", StringUtil.defaultIfNull(printLabelBean.getSecondOutfieldName()));
		//最终配载部门名称
		ctx.put("lastLoadOrgName", StringUtil.defaultIfNull(printLabelBean.getLastLoadOrgName()));
		//出发城市
		ctx.put("leavecity", StringUtil.defaultIfNull(printLabelBean.getLeavecity()));
		//派送日期
		ctx.put("deliverDate", StringUtil.defaultIfNull(printLabelBean.getDeliverDate()));
		
		//六大外场简称
		ctx.put("outerField1", StringUtil.defaultIfNull(printLabelBean.getOuterField1()));
		ctx.put("outerField2", StringUtil.defaultIfNull(printLabelBean.getOuterField2()));
		ctx.put("outerField3", StringUtil.defaultIfNull(printLabelBean.getOuterField3()));
		ctx.put("outerField3", StringUtil.defaultIfNull(printLabelBean.getOuterField4()));
		ctx.put("outerField5", StringUtil.defaultIfNull(printLabelBean.getOuterField5()));
		ctx.put("outerField6", StringUtil.defaultIfNull(printLabelBean.getOuterField6()));

		//六大库位
		ctx.put("location1", StringUtil.defaultIfNull(printLabelBean.getLocation1()));
		ctx.put("location2", StringUtil.defaultIfNull(printLabelBean.getLocation2()));
		ctx.put("location3", StringUtil.defaultIfNull(printLabelBean.getLocation3()));
		ctx.put("location4", StringUtil.defaultIfNull(printLabelBean.getLocation4()));
		ctx.put("location5", StringUtil.defaultIfNull(printLabelBean.getLocation5()));
		ctx.put("location6", StringUtil.defaultIfNull(printLabelBean.getLocation6()));
		
		//是否打印星标
		ctx.put("isPrintStar", StringUtil.defaultIfNull(printLabelBean.getIsPrintStar()));
		//对应的流水号
		ctx.put("printSerialNos", StringUtil.defaultIfNull(printLabelBean.getPrintSerialNos()));
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(user!= null && user.getEmployee()!=null){
			ctx.put("optusernum",isBeanValueNull(user.getEmployee().getEmpCode()));
		}
		//是否打印@
		ctx.put("isPrintAt",isBeanValueNull(printLabelBean.getIsPrintAt()));

		// 打印标签，条码部分目的站编码，也就是提货网点编码（倒数第一位）
		ctx.put("stationnumber",isBeanValueNull(printLabelBean.getStationnumber()));
		//是否发货大客户
		ctx.put("deliveryBigCustomer", printLabelBean.getDeliveryBigCustomer());
		//是否收货大客户
		ctx.put("receiveBigCustomer", printLabelBean.getReceiveBigCustomer());
		//目的站       电子运单二期打印时要求的目的站城市
		ctx.put("destination", printLabelBean.getDestination());
		//公司微信地址       电子运单二期要求的打印公司微信地址 
		//ctx.put("weixinAddr", printLabelBean.getWeixinAddr());
		//是否派送
		ctx.put("isDeliver", printLabelBean.getIsDeliver());
		//是否隐藏运费
		ctx.put("isHideTransportFee", FossConstants.NO);
		//是否隐藏费用合计
		ctx.put("isHideTotalFee", FossConstants.NO);
		//是否隐藏计费重量
		ctx.put("isHideBillWeight",FossConstants.NO);
		//是否隐藏发货人信息
		ctx.put("isHideDeliveryCustomerInfo", FossConstants.NO);
		return ctx;
	}
	
	/**
	 * 根据出发部门编码查询所属城市名称  （始发站）
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 下午4:54:33
	 */
	public String gainCityNameByCode(String code){
		OrgAdministrativeInfoEntity orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(code));
		AdministrativeRegionsEntity regions = waybillService.queryAdministrativeRegionsByCode(StringUtil.defaultIfNull(orgInfo.getCityCode()));
		if(regions != null){
			return regions.getName();
		}else{
			return null;
		}
	}
	
	/**
	 * 根据部门编码查询部门名称 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 下午4:54:33
	 */
	public OrgAdministrativeInfoEntity getOrg(String code){
		OrgAdministrativeInfoEntity orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(code));
		return orgInfo;
	}
	
	/**
	 * 根据部门编码查询电话号码 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 下午4:54:33
	 */
	public String getOrgTelphone(String code){
		OrgAdministrativeInfoEntity orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(code));
		if(orgInfo!=null){
			return isBeanValueNull(orgInfo.getDepTelephone());
		}else{
			return null;
		}	
	} 
	
	/**
	 * 根据部门编码查部门简称
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 下午4:54:33
	 */
	public String getOrgSimpleName(String code){
		OrgAdministrativeInfoEntity orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(code));
		if (orgInfo != null) {
			return isBeanValueNull(orgInfo.getOrgSimpleName());
		}else {
			return "";
		}
	}
	
	/**
	 * 获取城市对应的广告语
	 * @param cityCode
	 * @return
	 */
	public String getWaybillDocAd(String receiverOgCode, String cityPattern){
		return waybillService.getWaybillDocAd(StringUtil.defaultIfNull(receiverOgCode), StringUtil.defaultIfNull(cityPattern));
	}
	
	/** 
	 * 格式化金额为千分位显示;
	 * @param 要格式化的金额; 
	 * @return 
	 */  
	public String fmtMicrometer(String text)  
	{  
	    DecimalFormat df = new DecimalFormat("###,###.##");  
//	    if(text.indexOf(".") > 0)  
//	    {  
//	        if(text.length() - text.indexOf(".")-1 == 0)  
//	        {  
//	            df = new DecimalFormat("###,##0.");  
//	        }else if(text.length() - text.indexOf(".")-1 == 1)  
//	        {  
//	            df = new DecimalFormat("###,##0.0");  
//	        }else  
//	        {  
//	            df = new DecimalFormat("###,##0.00");  
//	        }  
//	    }else   
//	    {  
//	        df = new DecimalFormat("###,##0");  
//	    }
	    double number = 0.0;  
	    try {  
	         number = Double.parseDouble(text);  
	    } catch (Exception e) {  
	        number = 0.0;  
	    }  
	    return df.format(number);  
	}
}