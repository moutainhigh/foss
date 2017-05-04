package com.deppon.foss.module.settlement.dubbo.api.define;

import java.util.EnumSet;

import org.apache.commons.lang.StringUtils;


/**
 * 结算单据编号定义
 * @author ibm-zhuwei
 * @date 2012-10-15 上午10:24:30
 */
public enum SettlementNoRuleEnum {
	
	YS1("YS1", "STL.SQ_YS1", 9, "始发应收运费", "YS", "应收单"),
	YS2("YS2", "STL.SQ_YS2", 9, "到付运费", "YS", "应收单"),
	YS3("YS3", "STL.SQ_YS3", 9, "专线到付运费", "YS", "应收单"),
	YS4("YS4", "STL.SQ_YS4", 9, "偏线到付运费", "YS", "应收单"),
	YS5("YS5", "STL.SQ_YS5", 9, "空运到付运费", "YS", "应收单"),
	YS6("YS6", "STL.SQ_YS6", 9, "应收代收货款", "YS", "应收单"),
	YS7("YS7", "STL.SQ_YS7", 9, "小票应收", "YS", "应收单"),
	YS81("YS81", "STL.SQ_YS81", 8, "家装应收", "YS", "应收单"),
	YS90("YS90", "STL.SQ_YS90", 8, "空运其它应收", "YS", "应收单"),
	YS93("YS93", "STL.SQ_YS93", 8, "快递代理其它应收", "YS", "应收单"),
	YS95("YS95", "STL.SQ_YS95", 8, "偏线其它应收", "YS", "应收单"),
	YS96("YS96", "STL.SQ_YS96", 8, "包装其它应收", "YS", "应收单"),
	
	YS82("YS82", "STL.SQ_YS82", 8, "合伙人始发提成应收", "YS", "应收单"),
	YS83("YS83", "STL.SQ_YS83", 8, "合伙人委托派费应收", "YS", "应收单"),
	YS84("YS84", "STL.SQ_YS84", 8, "合伙人到付运费应收", "YS", "应收单"),
	YS85("YS85", "STL.SQ_YS85", 8, "合伙人代收货款应收", "YS", "应收单"),
	YS86("YS86", "STL.SQ_YS86", 8, "合伙人罚款应收", "YS", "应收单"),
	YS87("YS87", "STL.SQ_YS87", 8, "合伙人培训会务应收", "YS", "应收单"),
	YS88("YS88", "STL.SQ_YS88", 8, "合伙人差错应收", "YS", "应收单"),
	
	YF1("YF1", "STL.SQ_YF1", 9, "应付代收货款", "YF", "应付单"),
	YF2("YF2", "STL.SQ_YF2", 9, "装卸费", "YF", "应付单"),
	YF3("YF3", "STL.SQ_YF3", 9, "理赔", "YF", "应付单"),
	YF4("YF4", "STL.SQ_YF4", 9, "退运费", "YF", "应付单"),
	YF5("YF5", "STL.SQ_YF5", 9, "服务补救", "YF", "应付单"),
	YF61("YF61", "STL.SQ_YF61", 8, "外请车/整车运费首款", "YF", "应付单"),
	YF62("YF62", "STL.SQ_YF62", 8, "外请车/整车运费尾款", "YF", "应付单"),
	YF7("YF7", "STL.SQ_YF7", 9, "偏线成本应付", "YF", "应付单"),
	YF81("YF81", "STL.SQ_YF81", 8, "应付航空公司成本", "YF", "应付单"),
	YF82("YF82", "STL.SQ_YF82", 8, "应付始发代理成本", "YF", "应付单"),
	YF83("YF83", "STL.SQ_YF83", 8, "家装应付", "YF", "应付单"),
	YF90("YF90", "STL.SQ_YF90", 8, "空运其他应付", "YF", "应付单"),
	YF91("YF91", "STL.SQ_YF91", 8, "应付中转代理成本", "YF", "应付单"),
	YF92("YF92", "STL.SQ_YF92", 8, "应付到达代理成本", "YF", "应付单"),
	YF93("YF93", "STL.SQ_YF93", 8, "快递代理其他应付", "YF", "应付单"),
	YF95("YF95", "STL.SQ_YF95", 8, "偏线其他应付", "YF", "应付单"),
	YF96("YF96", "STL.SQ_YF96", 8, "包装其他应付", "YF", "应付单"),
	YF97("YF97", "STL.SQ_YF97", 8, "包装应付", "YF", "应付单"),
	YF10("YF10", "STL.SQ_YF10", 8, "折扣应付", "YF", "应付单"),
	
	YF63("YF63", "STL.SQ_YF63", 8, "合伙人到达提成应付", "YF", "应付单"),
	YF64("YF64", "STL.SQ_YF64", 8, "合伙人委托派费应付", "YF", "应付单"),
	YF65("YF65", "STL.SQ_YF65", 8, "合伙人到付运费应付", "YF", "应付单"),
	YF66("YF66", "STL.SQ_YF66", 8, "合伙人快递差错应付", "YF", "应付单"),
	YF67("YF67", "STL.SQ_YF67", 8, "合伙人业务奖励应付", "YF", "应付单"),
	
	XS1("XS1", "STL.SQ_XS1", 9, "运单现金", "XS", "现金收款单"),
	XS2("XS2", "STL.SQ_XS2", 9, "小票现金", "XS", "现金收款单"),
	FK1("FK1", "STL.SQ_FK1", 9, "手工生成的付款单", "FK", "付款单"),
	FK2("FK2", "STL.SQ_FK2", 9, "自动生成的付款单", "FK", "付款单"),
	FK_BN("FK_BN", "STL.SQ_FK_BN", 9, "-", "FK_BN", "付款批次号"),
	US("US", "STL.SQ_US", 9, "-", "US", "预收单"),
	
	DP("DP", "STL.SQ_DP", 9, "-", "DP", "合伙人预收单"),
	
	UF("UF", "STL.SQ_UF", 9, "-", "UF", "预付单"),
	HK1("HK1", "STL.SQ_HK1", 9, "手工生成的还款单", "HK", "还款单"),
	HK2("HK2", "STL.SQ_HK2", 9, "自动生成的还款单", "HK", "还款单"),
	HX("HX", "STL.SQ_HX", 9, "-", "HX", "核销单"),
	HX_BN("HX_BN", "STL.SQ_HX_BN", 9, "-", "HX_BN", "核销批次号"),
	HZ("HZ", "STL.SQ_HZ", 9, "-", "HZ", "坏账"),
	LP("LP", "STL.SQ_LP", 9, "-", "LP", "理赔"),
	TY("TY", "STL.SQ_TY", 9, "-", "TY", "退运费"),
	BJ("BJ", "STL.SQ_BJ", 9, "-", "BJ", "服务补救"),
	DZ("DZ", "STL.SQ_DZ", 9, "-", "DZ", "对账单"),
	YCCK("YCCK", "STL.SQ_YCCK", 9, "-", "YCCK", "异常出库"),
	SJ("SJ", "STL.SQ_SJ", 9, "-", "SJ", "司机"),
	JK("JK", "STL.SQ_JK", 9, "-", "JK", "缴款"),
	YT("YT", "STL.SQ_YT", 9, "-", "YT", "预提"),
	HB("h", "STL.SQ_COD_HB", 9, "-", "h", "代收货款合并编号"),
	ZK("ZK", "STL.SQ_ZK01", 8, "折扣单", "ZK", "折扣单编号"),
	DZHY("DZHY","STL.SQ_DZHY",9,"-","DZHY","合伙人对账单")
	;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 数据库序列号
	 */
	private String sequence;

	/**
	 * 编码长度
	 */
	private int length;

	/**
	 * 编码名称
	 */
	private String name;

	/**
	 * 父类型编码
	 */
	private String baseCode;

	/**
	 * 父类型名称
	 */
	private String baseName;

	/**
	 * 通过CODE获取枚举值
	 * @author ibm-zhuwei
	 * @date 2012-11-14 下午1:04:13
	 * @param code
	 * @return
	 */
	public static SettlementNoRuleEnum getByCode(String code) {
		
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		
		for (SettlementNoRuleEnum s : EnumSet.allOf(SettlementNoRuleEnum.class)) {
			if ( code.startsWith(s.getCode()) ) {
				return s;
			}
		}
		
		return null;
	}
	
	/**
	 * Constructor
	 * @author ibm-zhuwei
	 * @date 2012-11-14 下午1:03:36
	 */
	private SettlementNoRuleEnum(String code, String sequence, int length, 
			String name, String baseCode, String baseName) {
		this.sequence = sequence;
		this.code = code;
		this.name = name;
		this.length = length;
		this.baseCode = baseCode;
		this.baseName = baseName;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return baseCode
	 */
	public String getBaseCode() {
		return baseCode;
	}

	/**
	 * @param baseCode
	 */
	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	/**
	 * @return baseName
	 */
	public String getBaseName() {
		return baseName;
	}

	/**
	 * @param baseName
	 */
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

}
