package com.deppon.foss.module.pickup.sign.api.shared.define;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants
 * @author: foss-yuting
 * @description: 上报oa的常量
 * @date:2014年12月19日 下午2:27:21
 */
public class ReportConstants {
	
	//内物短少常量
	/**
	 * 上报失败
	 */
	public static final String REPORT_OA_ERROR="error";
	
	/**
	 * 上报成功
	 */
	public static final String REPORT_OA_SUCCESS="success";
	/**
	 * OA返回状态码(上报出现异常)
	 */
	public static final String REPORT_RETURN_STATE_ERROR="0";
	
	/**
	 * OA返回状态码(上报成功)
	 */
	public static final String REPORT_RETURN_STATE_SUCCESS="1";
	
	/**
	 * OA返回状态码(重复上报)
	 */
	public static final String REPORT_RETURN_STATE_REPEAT="2";
	
	/**
	 * QMS返回状态码(上报成功)
	 */
	public static final String QMS_RETURN_SUCCESS = "0";
	
	/**
	 * QMS返回状态码(重复上报)
	 */
	public static final String QMS_RETURN_REPEAT = "9";
	
	public static final String ESB_FOSS2FOSS_REPORT_SHORTGOODS="ESB_FOSS2FOSS_REPORT_SHORTGOODS";
	
	/**
	 * 短少类别(外包装完好)
	 */
	public static final String SHORT_TYPE_PACKAGE_GOOD="外包装完好";
	
	/**
	 * 短少类别(破损短少)
	 */
	public static final String SHORT_TYPE_PACKAGE_DAMAGED="破损短少";
	
	/**
	 * 支付方式词条
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE = "SETTLEMENT__PAYMENT_TYPE";
	/**
	 * 结清货款付款方式词条 by 243921
	 */
	public static final String REPAYMENT_PAYMENT_TYPE = "REPAYMENT_PAYMENT_TYPE";
	/**
	 * 空运-运输类型
	 */
	public static final String TRANS_AIRCRAFT = "TRANS_AIRCRAFT";
	/**
	 * 提货方式词条-空运
	 */
	public static final String PICKUPGOODSAIR = "PICKUPGOODSAIR";
	//三级产品-精准空运
	public static final String PRICING_PRODUCT_AIR_FREIGHT = "AF";
	//三级产品-汽运偏线
	public static final String PRICING_PRODUCT_PARTIAL_LINE = "PLF";
	/**
	 * 提货方式词条-家装
	 */
	public static final String PICKUPGOODSSPECIALDELIVERYTYPE = "PICKUPGOODSSPECIALDELIVERYTYPE";
	/**
	 * 提货方式词条-汽运
	 */
	public static final String PICKUPGOODSHIGHWAYS = "PICKUPGOODSHIGHWAYS";
	/**
	 * 返单类型词条
	 */
	public static final String RETURNBILLTYPE = "RETURNBILLTYPE";
	/**
	 * 运输类型词条
	 */
	public static final String TRANS_TYPE = "TRANS_TYPE";
	/**
	 * 签收情况词条
	 */
	public static final String PKP_SIGN_SITUATION = "PKP_SIGN_SITUATION";
	
	/**
	 * 签收情况(异常内物短少)
	 */
	public static final String SIGN_SITUATION_UNNORMAL_GOODSHORT="UNNORMAL_GOODSHORT";
	
	/**
	 * 签收情况(同票多类)
	 */
	public static final String SIGN_SITUATION_UNNORMAL_SAMEVOTEODD="UNNORMAL_SAMEVOTEODD";
	
	/** 
	 * 签收情况--正常签收
	 */
	public static final String NORMAL_SIGN = "正常签收";
	
	/**
	 *  签收情况---异常签收
	 */
	public static final String UNNORMAL_SIGN = "异常签收";
	
	/** 
	 * 签收情况--异常-破损
	 */
	public static final String UNNORMAL_BREAK = "异常-破损";
	
	/**
	 *  签收情况---异常-潮湿
	 */
	public static final String UNNORMAL_DAMP = "异常-潮湿";
	
	/** 
	 * 签收情况---异常-污染
	 */
	public static final String UNNORMAL_POLLUTION = "异常-污染";
	
	/** 
	 * 签收情况---异常内物短少
	 */
	public static final String UNNORMAL_GOODSHORT= "异常-内物短少";
	
	/** 
	 * 签收情况--异常-其他
	 */
	public static final String UNNORMAL_ELSE = "异常-其他";
	
	/** 
	 * 签收情况---部分签收
	 */
	public static final String PARTIAL_SIGN = "部分签收";
	
	/**
	 *  签收情况---异常-违禁品
	 */
	public static final String UNNORMAL_CONTRABAND = "异常-违禁品";
	
	/** 
	 *  签收情况---异常-弃货
	 */
	public static final String UNNORMAL_ABANDONGOODS = "异常-弃货";
	
	/** 
	 *  签收情况---同票多类异常
	 */
	public static final String UNNORMAL_SAMEVOTEODD = "同票多类异常";
	
	/**
	 * 签收情况---异常-丢货
	 */
	public static final String UNNORMAL_LOSTCARGO = "异常-丢货";
	
	/**
	 * 差错业务类型 快递：“K”
	 */
	public static final String BUSINESSEXP = "K";
	
	/**
	 * 差错业务类型 零担：“L”
	 */
	public static final String BUSINESSLTL = "L";
	
	/**
	 * 快递异常货物管理
	 */
	public static final String QMS_K_YCH ="QMS_K_YCH";
	
	/**
	 * 快递反签收差错文件标准id
	 */
	public static final String REVERSE_DOCSTANDAR_ID = "2012";
	
	/**
	 * 零担反签收差错文件标准id
	 */
	public static final String LD_REVERSE_DOCSTANDAR_ID = "1043";
	
	/**
	 * 快递内物短少差错文件标准id(单责)
	 */
	public static final String SHORT_DOCSTANDAR_ID = "2003";
	
	/**
	 * 零担内物短少差错文件标准id
	 */
	public static final String LD_SHORT_DOCSTANDAR_ID = "1206";
	
	/**
	 * 零担标签差错 的差错类型编号
	 */
	public static final String QMS_FOSS_BQ = "L201503250006";
	
	/**
	 * 快递反签收差错 的差错类型编号
	 */
	public static final String QMS_FOSS_REVERSE = "K201503250008";
	
	/**
	 * 零担反签收差错 的差错类型编号
	 */
	public static final String QMS_FOSS_REVERSE_LD = "L201503250009";
	
	/**
	 * 快递内物短少差错 的差错类型编号
	 */
	public static final String QMS_FOSS_SHORT = "K201503250001";
	
	/**
	 * 零担内物短少差错 的差错类型编号
	 */
	public static final String QMS_FOSS_SHORT_LD = "L201503250004";
	/**
	 * 零担重大货物差错 的差错类型编号
	 */
	public static final String QMS_FOSS_IMPORTANT_LD = "L201611010004";
	
	/**
	 * qms差错类型编号key（Map）
	 */
	public static final String ERRORTYPEID = "errorTypeTd";
	
	/**
	 * qms业务类型key
	 */
	public static final String ERRCATEGOTY = "errCategoty";
	
	/**
	 * qms自动上报主信息key
	 */
	public static final String MAININFO = "mainInfo";
	
	/**
	 * qms快递有单号自动上报子信息key
	 */
	public static final String KDSUBHASINFO = "kdsubHasInfo";
	
	/**
	 * qms零担有单号自动上报子信息key
	 */
	public static final String LDSUBHASINFO = "ldsubHasInfo";
	
	//丢货上报常量
	//OA上报丢货
	public static final String OA_LOST_GOODS_SERVICE_CODE ="ESB_FOSS2ESB_REPORT_CLEARLESS";
	
	/**
	 * 行业货源品类上级词条
	 */
	public static final String BSE_SOURCE_CATEGORY = "BSE_SOURCE_CATEGORY";
	
	/**
	 * 部门编码
	 */
	public static final String DEPT_STAND_CODE = "deptStandCode";
	
	/**
	 * 部门名称
	 */
	public static final String DEPT_NAME = "deptName";
}
