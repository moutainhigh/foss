package com.deppon.foss.module.settlement.closing.api.shared.define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * 凭证大类、小类映射
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-1 下午6:16:47
 */
public enum VDTypeMappingEnum {

	TYPE_RF000(VDRptTypes.TYPE_RFO, new VDCombo("开单运单", "KAI_DAN_YUN_DAN"), new VDCombo("开单现金",
			"DE_CH")), 
	TYPE_RF001(VDRptTypes.TYPE_RFO, new VDCombo("开单运单", "KAI_DAN_YUN_DAN"),
			new VDCombo("开单银行卡", "DE_CD")), 
	TYPE_RF002(VDRptTypes.TYPE_RFO, new VDCombo(
			"还款运单总运费（月结临时欠款网上支付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__YUE_JIE_LIN_SHI_QIAN_KUAN_WANG_SHANG_ZHI_FU_"),
			new VDCombo("还款现金未签收", "UR_ORIG_CH_NPOD")), TYPE_RF003(
			VDRptTypes.TYPE_RFO,
			new VDCombo("还款运单总运费（月结临时欠款网上支付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__YUE_JIE_LIN_SHI_QIAN_KUAN_WANG_SHANG_ZHI_FU_"),
			new VDCombo("还款银行未签收", "UR_ORIG_CD_NPOD")), TYPE_RF004(
			VDRptTypes.TYPE_RFO,
			new VDCombo("还款运单总运费（月结临时欠款网上支付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__YUE_JIE_LIN_SHI_QIAN_KUAN_WANG_SHANG_ZHI_FU_"),
			new VDCombo("还款现金已签收", "UR_ORIG_CH_POD")), TYPE_RF005(VDRptTypes.TYPE_RFO, new VDCombo(
			"还款运单总运费（月结临时欠款网上支付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__YUE_JIE_LIN_SHI_QIAN_KUAN_WANG_SHANG_ZHI_FU_"),
			new VDCombo("还款银行已签收", "UR_ORIG_CD_POD")), TYPE_RF006(VDRptTypes.TYPE_RFO, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收",
			"UR_DEST_CH_NPOD")), TYPE_RF007(VDRptTypes.TYPE_RFO, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收", "UR_DEST_CD_NPOD")), TYPE_RF008(
			VDRptTypes.TYPE_RFO, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
					"UR_DEST_CH_POD")), TYPE_RF009(VDRptTypes.TYPE_RFO, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_RF010(
			VDRptTypes.TYPE_RFO, new VDCombo("签收运单", "QIAN_SHOU_YUN_DAN"), new VDCombo(
					"签收时现付已收款金额", "POD_CASH_COLLECTED")), TYPE_RF011(VDRptTypes.TYPE_RFO,
			new VDCombo("签收运单", "QIAN_SHOU_YUN_DAN"),
			new VDCombo("签收时始发应收已核销金额", "POD_ORIG_RCV_WO")), TYPE_RF012(VDRptTypes.TYPE_RFO,
			new VDCombo("签收运单", "QIAN_SHOU_YUN_DAN"), new VDCombo("签收时始发应收未核销金额",
					"POD_ORIG_RCV_NWO")), TYPE_RF013(VDRptTypes.TYPE_RFO, new VDCombo("签收运单",
			"QIAN_SHOU_YUN_DAN"), new VDCombo("签收时到达应收已核销金额", "POD_DEST_RCV_WO")), TYPE_RF014(
			VDRptTypes.TYPE_RFO, new VDCombo("签收运单", "QIAN_SHOU_YUN_DAN"), new VDCombo(
					"签收时到达应收未核销金额", "POD_DEST_RCV_NWO")), TYPE_RF015(VDRptTypes.TYPE_RFO,
			new VDCombo("反签收运单", "FAN_QIAN_SHOU_YUN_DAN"), new VDCombo("反签收时现付已收款金额",
					"UPD_CASH_COLLECTED")), TYPE_RF016(VDRptTypes.TYPE_RFO, new VDCombo("反签收运单",
			"FAN_QIAN_SHOU_YUN_DAN"), new VDCombo("反签收时始发应收已核销金额", "UPD_ORIG_RCV_WO")), TYPE_RF017(
			VDRptTypes.TYPE_RFO, new VDCombo("反签收运单", "FAN_QIAN_SHOU_YUN_DAN"), new VDCombo(
					"反签收时始发应收未核销金额", "UPD_ORIG_RCV_NWO")), TYPE_RF018(VDRptTypes.TYPE_RFO,
			new VDCombo("反签收运单", "FAN_QIAN_SHOU_YUN_DAN"), new VDCombo("反签收时到达应收已核销金额",
					"UPD_DEST_RCV_WO")), TYPE_RF019(VDRptTypes.TYPE_RFO, new VDCombo("反签收运单",
			"FAN_QIAN_SHOU_YUN_DAN"), new VDCombo("反签收时到达应收未核销金额", "UPD_DEST_RCV_NWO")), TYPE_RF020(
			VDRptTypes.TYPE_RFO, new VDCombo("理赔", "LI_PEI"), new VDCombo("出发理赔冲收入",
					"CLAIM_ORIG_WO_INCOME")), TYPE_RF021(VDRptTypes.TYPE_RFO, new VDCombo("理赔",
			"LI_PEI"), new VDCombo("理赔入成本", "CLAIM_ORIG_COST")), TYPE_RF022(VDRptTypes.TYPE_RFO,
			new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲始发应收已签收", "CLAIM_WO_ORIG_RCV_POD")), TYPE_RF023(
			VDRptTypes.TYPE_RFO, new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲始发应收未签收",
					"CLAIM_WO_ORIG_RCV_NPOD")), TYPE_RF024(VDRptTypes.TYPE_RFO, new VDCombo("理赔",
			"LI_PEI"), new VDCombo("理赔付款申请", "CLAIM_ORIG_PAY_APPLY")), TYPE_RF025(
			VDRptTypes.TYPE_RFO, new VDCombo("理赔", "LI_PEI"), new VDCombo("到达理赔冲收入",
					"CLAIM_DEST_WO_INCOME")), TYPE_RF026(VDRptTypes.TYPE_RFO, new VDCombo("理赔",
			"LI_PEI"), new VDCombo("理赔冲到达应收已签收", "CLAIM_WO_DEST_RCV_POD")), TYPE_RF027(
			VDRptTypes.TYPE_RFO, new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲到达应收未签收",
					"CLAIM_WO_DEST_RCV_NPOD")), TYPE_RF028(VDRptTypes.TYPE_RFO, new VDCombo("装卸费",
			"ZHUANG_XIE_FEI"), new VDCombo("装卸费付款申请", "SF_PAY_APPLY")), TYPE_RF029(
			VDRptTypes.TYPE_RFO, new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo(
					"应付代收货款冲应收到付运费已签收", "COD_DEST_RCV_POD")), TYPE_RF030(VDRptTypes.TYPE_RFO,
			new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo("应付代收货款冲应收到付运费未签收",
					"COD_DEST_RCV_NPOD")), TYPE_RF031(VDRptTypes.TYPE_RFO, new VDCombo("代收货款",
			"DAI_SHOU_HUO_KUAN"), new VDCombo("应付代收货款冲应收始发运费已签收", "COD_ORIG_RCV_POD")), TYPE_RF032(
			VDRptTypes.TYPE_RFO, new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo(
					"应付代收货款冲应收始发运费未签收", "COD_ORIG_RCV_NPOD")), TYPE_RF033(VDRptTypes.TYPE_RFO,
			new VDCombo("营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("预收客户现金", "CUST_DR_CH")), TYPE_RF034(
			VDRptTypes.TYPE_RFO, new VDCombo("营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo(
					"预收客户银行", "CUST_DR_CD")), TYPE_RF035(VDRptTypes.TYPE_RFO, new VDCombo(
			"营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("预收客户冲应收到付运费未签收",
			"CUST_DR_DEST_RCV_NPOD")), TYPE_RF036(VDRptTypes.TYPE_RFO, new VDCombo("营业部预收客户",
			"YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("预收客户冲应收到付运费已签收", "CUST_DR_DEST_RCV_POD")), TYPE_RF037(
			VDRptTypes.TYPE_RFO, new VDCombo("营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo(
					"预收客户冲应收始发运费未签收", "CUST_DR_ORIG_RCV_NPOD")), TYPE_RF038(VDRptTypes.TYPE_RFO,
			new VDCombo("营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("预收客户冲应收始发运费已签收",
					"CUST_DR_ORIG_RCV_POD")), TYPE_RF039(VDRptTypes.TYPE_RFO, new VDCombo(
			"营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("始发退预收付款申请",
			"CUST_DR_ORIG_PAY_APPLY")), TYPE_RF040(VDRptTypes.TYPE_RFO, new VDCombo("异常冲收入",
			"YI_CHANG_CHONG_SHOU_RU"), new VDCombo("应收始发运费已签收", "EX_ORIG_RCV_POD")), TYPE_RF041(
			VDRptTypes.TYPE_RFO, new VDCombo("异常冲收入", "YI_CHANG_CHONG_SHOU_RU"), new VDCombo(
					"应收到付运费已签收", "EX_DEST_RCV_POD")), TYPE_RF042(VDRptTypes.TYPE_RFO, new VDCombo(
			"坏账损失", "HUAI_ZHANG_SUN_SHI"), new VDCombo("坏账冲应收始发运费已签收", "BD_ORIG_RCV_POD")), TYPE_RF043(
			VDRptTypes.TYPE_RFO, new VDCombo("坏账损失", "HUAI_ZHANG_SUN_SHI"), new VDCombo(
					"坏账冲应收到付运费已签收", "BD_DEST_RCV_POD")), TYPE_RF044(VDRptTypes.TYPE_RFO,
			new VDCombo("小票", "XIAO_PIAO"), new VDCombo("小票现金之事故赔款", "OR_CH_AC")), TYPE_RF045(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("小票现金之变卖废品收入",
					"OR_CH_SI")), TYPE_RF046(VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"),
			new VDCombo("小票现金之客户多付运费或盘点长款金额", "OR_CH_OPAY")), TYPE_RF047(VDRptTypes.TYPE_RFO,
			new VDCombo("小票", "XIAO_PIAO"), new VDCombo("小票现金之其他", "OR_CH_OTHER")), TYPE_RF048(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("小票现金主营业务收入",
					"OR_CH_MBI")), TYPE_RF049(VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"),
			new VDCombo("小票银行之事故赔款", "OR_CD_AC")), TYPE_RF050(VDRptTypes.TYPE_RFO, new VDCombo(
			"小票", "XIAO_PIAO"), new VDCombo("小票银行之收银员卡利息", "OR_CD_BANK_IT")), TYPE_RF051(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("小票银行之客户多付运费或盘点长款金额",
					"OR_CD_OPAY")), TYPE_RF052(VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"),
			new VDCombo("小票银行之其他", "OR_CD_OTHER")), TYPE_RF053(VDRptTypes.TYPE_RFO, new VDCombo(
			"小票", "XIAO_PIAO"), new VDCombo("小票银行主营业务收入", "OR_CD_MBI")), TYPE_RF054(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("小票应收主营业务收入",
					"OR_RCV_MBI")), TYPE_RF055(VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"),
			new VDCombo("还款现金冲小票应收", "OR_RCV_WO_UR_CH")), TYPE_RF056(VDRptTypes.TYPE_RFO,
			new VDCombo("小票", "XIAO_PIAO"), new VDCombo("还款银行冲小票应收", "OR_RCV_WO_UR_CD")), TYPE_RF057(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("应付代收货款冲小票应收",
					"OR_RCV_WO_COD_PAY")), TYPE_RF058(VDRptTypes.TYPE_RFO, new VDCombo("小票",
			"XIAO_PIAO"), new VDCombo("应付理赔冲小票应收", "OR_RCV_WO_CLAIM_PAY")), TYPE_RF059(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("预收客户冲小票应收",
					"OR_RCV_WO_CUST_DR")), TYPE_RF060(VDRptTypes.TYPE_RFO, new VDCombo("小票",
			"XIAO_PIAO"), new VDCombo("坏账之保险理赔冲小票应收", "OR_RCV_WO_BD_DEBT")), TYPE_RF061(
			VDRptTypes.TYPE_RFO, new VDCombo("小票", "XIAO_PIAO"), new VDCombo("坏账之坏账损失冲小票应收",
					"OR_RCV_WO_BD_INCOME")), TYPE_RF062(VDRptTypes.TYPE_RFO, new VDCombo(
			"弃货、违禁品、全票丢货", "QI_HUO__WEI_JIN_PIN__QUAN_PIAO_DIU_HUO"), new VDCombo(
			"开单为月结临时欠款网上支付未核销", "AC_CTDTOL_NWO")), TYPE_RF063(VDRptTypes.TYPE_RFO, new VDCombo(
			"弃货、违禁品、全票丢货", "QI_HUO__WEI_JIN_PIN__QUAN_PIAO_DIU_HUO"), new VDCombo(
			"开单为月结临时欠款网上支付已核销", "AC_CTDTOL_WO")), TYPE_RF064(VDRptTypes.TYPE_RFO, new VDCombo(
			"弃货、违禁品、全票丢货", "QI_HUO__WEI_JIN_PIN__QUAN_PIAO_DIU_HUO"), new VDCombo("开单为现金银行卡",
			"AC_CHCD")), TYPE_RF065(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("出发退运费冲收入",
							"RD_ORIG_WO_INCOME")), TYPE_RF066(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费入成本",
							"RD_ORIG_COST")), TYPE_RF067(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费付款申请",
							"RD_ORIG_PAY_APPLY")), TYPE_RF080(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费冲始发应收已签收",
							"RD_WO_ORIG_RCV_POD")), TYPE_RF081(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费冲始发应收未签收",
							"RD_WO_ORIG_RCV_NPOD")), TYPE_RF068(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("到达退运费冲收入",
							"RD_DEST_WO_INCOME")), TYPE_RF082(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费冲到达应收已签收",
							"RD_WO_DEST_RCV_POD")), TYPE_RF083(VDRptTypes.TYPE_RFO,
					new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费冲到达应收未签收",
							"RD_WO_DEST_RCV_NPOD")), TYPE_RF069(
			VDRptTypes.TYPE_RFO, new VDCombo("服务补救", "FU_WU_BU_JIU"), new VDCombo("始发服务补救付款申请",
					"CN_ORIG_PAY_APPLY")), TYPE_RF070(VDRptTypes.TYPE_RFO, new VDCombo("偏线代理成本",
			"PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo("应付偏线代理成本冲应收到付运费已签收",
			"PL_COST_WO_DEST_RCV_POD")), TYPE_RF071(VDRptTypes.TYPE_RFO, new VDCombo("偏线代理成本",
			"PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo("应付偏线代理成本冲应收到付运费未签收",
			"PL_COST_WO_DEST_RCV_NPOD")), TYPE_RF072(VDRptTypes.TYPE_RFO, new VDCombo("预收偏线代理",
			"YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo("预收偏线代理冲应收到付运费已签收", "PL_DR_WO_DEST_RCV_POD")), TYPE_RF073(
			VDRptTypes.TYPE_RFO, new VDCombo("预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo(
					"预收偏线代理冲应收到付运费未签收", "PL_DR_WO_DEST_RCV_NPOD")), TYPE_RF074(VDRptTypes.TYPE_RFO,
			new VDCombo("预收空运/快递代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运/快递代理冲应收到付运费已签收",
					"AIR_DR_DEST_RCV_POD")), TYPE_RF075(VDRptTypes.TYPE_RFO, new VDCombo("预收空运/快递代理",
			"YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运/快递代理冲应收到付运费未签收", "AIR_DR_DEST_RCV_NPOD")), TYPE_RF076(
			VDRptTypes.TYPE_RFO, new VDCombo("空运/快递代理应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("应付到达代理/快递代理成本冲应收到付运费已签收", "AIR_PR_AGENCY_WO_DEST_RCV_POD")), TYPE_RF077(
			VDRptTypes.TYPE_RFO, new VDCombo("空运/快递代理应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("应付到达代理/快递代理成本冲应收到付运费未签收", "AIR_PR_AGENCY_WO_DEST_RCV_NPOD")), TYPE_RF078(
			VDRptTypes.TYPE_RFO, new VDCombo("空运/快递代理应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("其他应付冲应收到付运费已签收", "AIR_PR_OT_WO_DEST_RCV_POD")), TYPE_RF079(
			VDRptTypes.TYPE_RFO, new VDCombo("空运/快递代理应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("其他应付冲应收到付运费未签收", "AIR_PR_OTH_WO_DEST_RCV_NPOD")),

	TYPE_RFD000(VDRptTypes.TYPE_RFD, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收", "UR_DEST_CH_NPOD")), TYPE_RFD001(
			VDRptTypes.TYPE_RFD, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收",
					"UR_DEST_CD_NPOD")), TYPE_RFD002(VDRptTypes.TYPE_RFD, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
			"UR_DEST_CH_POD")), TYPE_RFD003(VDRptTypes.TYPE_RFD, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_RFD004(
			VDRptTypes.TYPE_RFD, new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲收入",
					"CLAIM_DEST_WO_INCOME")), TYPE_RFD005(VDRptTypes.TYPE_RFD, new VDCombo("理赔",
			"LI_PEI"), new VDCombo("理赔入成本", "CLAIM_DEST_COST")), TYPE_RFD006(VDRptTypes.TYPE_RFD,
			new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔付款申请", "CLAIM_DEST_PAY_APPLY")), TYPE_RFD007(
			VDRptTypes.TYPE_RFD, new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲到达应收已签收",
					"CLAIM_WO_DEST_RCV_POD")), TYPE_RFD008(VDRptTypes.TYPE_RFD, new VDCombo("理赔",
			"LI_PEI"), new VDCombo("理赔冲到达应收未签收", "CLAIM_WO_DEST_RCV_NPOD")), TYPE_RFD009(
			VDRptTypes.TYPE_RFD, new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo(
					"还款代收货款现金未签收", "COD_UR_CH_NPOD")), TYPE_RFD010(VDRptTypes.TYPE_RFD,
			new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo("还款代收货款银行未签收", "COD_UR_CD_NPOD")), TYPE_RFD015(
					VDRptTypes.TYPE_RFD, new VDCombo("营业部预收客户",
							"YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("预收客户冲应收到付运费未签收",
							"CUST_DR_DEST_RCV_NPOD")), TYPE_RFD016(VDRptTypes.TYPE_RFD,
					new VDCombo("营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo(
							"预收客户冲应收到付运费已签收", "CUST_DR_DEST_RCV_POD")), TYPE_RFD011(
			VDRptTypes.TYPE_RFD, new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费冲收入",
					"RD_DEST_WO_INCOME")), TYPE_RFD012(VDRptTypes.TYPE_RFD, new VDCombo("退运费",
			"TUI_YUN_FEI"), new VDCombo("退运费入成本", "RD_DEST_COST")), TYPE_RFD013(
			VDRptTypes.TYPE_RFD, new VDCombo("退运费", "TUI_YUN_FEI"), new VDCombo("退运费付款申请",
					"RD_DEST_PAY_APPLY")), TYPE_RFD017(
							VDRptTypes.TYPE_RFD, new VDCombo("退运费", "TUI_YUN_FEI"),
							new VDCombo("退运费冲到达应收已签收", "RD_WO_DEST_RCV_POD")), TYPE_RFD018(
							VDRptTypes.TYPE_RFD, new VDCombo("退运费", "TUI_YUN_FEI"),
							new VDCombo("退运费冲到达应收未签收", "RD_WO_DEST_RCV_NPOD")), TYPE_RFD014(VDRptTypes.TYPE_RFD, new VDCombo("服务补救",
			"FU_WU_BU_JIU"), new VDCombo("到达服务补救付款申请", "CN_DEST_PAY_APPLY")),

	TYPE_PLR000(VDRptTypes.TYPE_PLR, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收", "UR_DEST_CH_NPOD")), TYPE_PLR001(
			VDRptTypes.TYPE_PLR, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收",
					"UR_DEST_CD_NPOD")), TYPE_PLR002(VDRptTypes.TYPE_PLR, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
			"UR_DEST_CH_POD")), TYPE_PLR003(VDRptTypes.TYPE_PLR, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_PLR004(
			VDRptTypes.TYPE_PLR, new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo(
					"应付偏线代理成本冲应收到付运费已签收", "PL_COST_WO_DEST_RCV_POD")), TYPE_PLR005(
			VDRptTypes.TYPE_PLR, new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo(
					"应付偏线代理成本冲应收到付运费未签收", "PL_COST_WO_DEST_RCV_NPOD")), TYPE_PLR006(
			VDRptTypes.TYPE_PLR, new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo(
					"外发单录入", "PL_COST_VECH")), TYPE_PLR007(VDRptTypes.TYPE_PLR, new VDCombo(
			"偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo("偏线代理成本确认", "PL_COST_CONFIRM")), TYPE_PLR008(
			VDRptTypes.TYPE_PLR, new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo(
					"偏线代理成本反确认", "PL_COST_NOT_CONFIRM")), TYPE_PLR009(VDRptTypes.TYPE_PLR,
			new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo("偏线代理成本付款申请",
					"PL_COST_PAY_APPLY")), TYPE_PLR010(VDRptTypes.TYPE_PLR, new VDCombo("预收偏线代理",
			"YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo("预收偏线代理冲应收到付运费已签收", "PL_DR_WO_DEST_RCV_POD")), TYPE_PLR011(
			VDRptTypes.TYPE_PLR, new VDCombo("预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo(
					"预收偏线代理冲应收到付运费未签收", "PL_DR_WO_DEST_RCV_NPOD")), TYPE_PLR012(
			VDRptTypes.TYPE_PLR, new VDCombo("预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo(
					"预收偏线代理现金", "PL_DR_CH")), TYPE_PLR013(VDRptTypes.TYPE_PLR, new VDCombo(
			"预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo("预收偏线代理银行", "PL_DR_CD")), TYPE_PLR014(
			VDRptTypes.TYPE_PLR, new VDCombo("预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo(
					"偏线退预收付款申请", "PL_DR_PAY_APPLY")),

	TYPE_AFR000(VDRptTypes.TYPE_AFR, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收", "UR_DEST_CH_NPOD")), TYPE_AFR001(
			VDRptTypes.TYPE_AFR, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收",
					"UR_DEST_CD_NPOD")), TYPE_AFR002(VDRptTypes.TYPE_AFR, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
			"UR_DEST_CH_POD")), TYPE_AFR003(VDRptTypes.TYPE_AFR, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_AFR004(
			VDRptTypes.TYPE_AFR, new VDCombo("空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo(
					"空运航空公司运费确认", "AIR_COST_COM_CONFIRM")), TYPE_AFR005(VDRptTypes.TYPE_AFR,
			new VDCombo("空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo("空运出发代理应付确认",
					"AIR_COST_ORIG_AGENCY_CFM")), TYPE_AFR006(VDRptTypes.TYPE_AFR, new VDCombo(
			"空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo("空运到达代理应付生成", "AIR_COST_DEST_AGENCY_GEN")), TYPE_AFR007(
			VDRptTypes.TYPE_AFR, new VDCombo("空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo(
					"空运到达代理应付成本确认", "AIR_COST_DEST_AGENCY_CFM")), TYPE_AFR008(VDRptTypes.TYPE_AFR,
			new VDCombo("空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo("空运到达代理应付成本反确认",
					"AIR_COST_DEST_AGENCY_NCFM")), TYPE_AFR009(VDRptTypes.TYPE_AFR, new VDCombo(
			"空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo("其它应付成本确认", "AIR_COST_OTHER_CONFIRM")), TYPE_AFR010(
			VDRptTypes.TYPE_AFR, new VDCombo("空运成本", "KONG_YUN_CHENG_BEN"), new VDCombo("空运成本付款申请",
					"AIR_COST_PAY_APPLY")), TYPE_AFR011(VDRptTypes.TYPE_AFR, new VDCombo("空运其他应收",
			"KONG_YUN_QI_TA_YING_SHOU"), new VDCombo("空运其他应收录入", "OTH_ENTRY")), TYPE_AFR012(
			VDRptTypes.TYPE_AFR, new VDCombo("空运其他应收", "KONG_YUN_QI_TA_YING_SHOU"), new VDCombo(
					"还款空运其他应收现金", "OTH_RCV_CH")), TYPE_AFR013(VDRptTypes.TYPE_AFR, new VDCombo(
			"空运其他应收", "KONG_YUN_QI_TA_YING_SHOU"), new VDCombo("还款空运其他应收银行", "OTH_RCV_CD")), TYPE_AFR014(
			VDRptTypes.TYPE_AFR, new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo(
					"预收空运代理冲应收到付运费已签收", "AIR_DR_DEST_RCV_POD")), TYPE_AFR015(VDRptTypes.TYPE_AFR,
			new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运代理冲应收到付运费未签收",
					"AIR_DR_DEST_RCV_NPOD")), TYPE_AFR016(VDRptTypes.TYPE_AFR, new VDCombo(
			"预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运代理现金", "AIR_DR_CH")), TYPE_AFR017(
			VDRptTypes.TYPE_AFR, new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo(
					"预收空运代理银行", "AIR_DR_CD")), TYPE_AFR018(VDRptTypes.TYPE_AFR, new VDCombo(
			"预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运代理冲其他应收", "AIR_DR_WO_OTHER_RCV")), TYPE_AFR019(
			VDRptTypes.TYPE_AFR, new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo(
					"预收空运代理冲应收代收货款已签收", "AIR_DR_WO_COD_RCV_POD")), TYPE_AFR020(VDRptTypes.TYPE_AFR,
			new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运代理冲应收代收货款未签收",
					"AIR_DR_WO_COD_RCV_NPOD")), TYPE_AFR021(VDRptTypes.TYPE_AFR, new VDCombo(
			"预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("空运退预收付款申请", "AIR_DR_PAY_APPLY")), TYPE_AFR022(
			VDRptTypes.TYPE_AFR, new VDCombo("空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("应付到达代理成本冲应收到付运费已签收", "AIR_PR_AGENCY_WO_DEST_RCV_POD")), TYPE_AFR023(
			VDRptTypes.TYPE_AFR, new VDCombo("空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("应付到达代理成本冲应收到付运费未签收", "AIR_PR_AGENCY_WO_DEST_RCV_NPOD")), TYPE_AFR024(
			VDRptTypes.TYPE_AFR, new VDCombo("空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("其他应付冲应收到付运费已签收", "AIR_PR_OT_WO_DEST_RCV_POD")), TYPE_AFR025(
			VDRptTypes.TYPE_AFR, new VDCombo("空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("其他应付冲应收到付运费未签收", "AIR_PR_OTH_WO_DEST_RCV_NPOD")), TYPE_AFR026(
			VDRptTypes.TYPE_AFR, new VDCombo("空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"),
			new VDCombo("其他应付冲其他应收", "AIR_PR_OTH_WO_OTH_RCV")), TYPE_AFR027(VDRptTypes.TYPE_AFR,
			new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运还款代收货款现金已签收",
					"AIR_COD_CH_POD")), TYPE_AFR028(VDRptTypes.TYPE_AFR, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运还款代收货款银行已签收", "AIR_COD_CD_POD")), TYPE_AFR029(
			VDRptTypes.TYPE_AFR, new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo(
					"空运签收时已核销代收货款", "AIR_COD_POD_WO_COD")), TYPE_AFR030(VDRptTypes.TYPE_AFR,
			new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运反签收时已核销代收货款",
					"AIR_COD_NPOD_WO_COD")), TYPE_AFR031(VDRptTypes.TYPE_AFR, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运还款代收货款现金未签收", "AIR_COD_CH_NPOD")), TYPE_AFR032(
			VDRptTypes.TYPE_AFR, new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo(
					"空运还款代收货款银行未签收", "AIR_COD_CD_NPOD")), TYPE_AFR033(VDRptTypes.TYPE_AFR,
			new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运到达代理应付冲应收代收货款已签收",
					"AIR_COD_WO_AGENCY_PAY_POD")), TYPE_AFR034(VDRptTypes.TYPE_AFR, new VDCombo(
			"空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运其他应付冲应收代收货款已签收",
			"AIR_COD_WO_OTH_PAY_COD")), TYPE_AFR035(VDRptTypes.TYPE_AFR, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运到达代理应付冲应收代收货款未签收",
			"AIR_COD_WO_AGENCY_PAY_NPOD")), TYPE_AFR036(VDRptTypes.TYPE_AFR, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运其他应付冲应收代收货款未签收", "AIR_COD_WO_OTH_NPOD")), TYPE_AFR037(
			VDRptTypes.TYPE_AFR, new VDCombo("预付", "YU_FU"), new VDCombo("预付航空公司", "APT_COM")), TYPE_AFR038(
			VDRptTypes.TYPE_AFR, new VDCombo("预付", "YU_FU"), new VDCombo("预付冲应付航空公司",
					"APT_WO_COM_PAY")), TYPE_AFR039(VDRptTypes.TYPE_AFR,
			new VDCombo("预付", "YU_FU"), new VDCombo("预付冲其他应付", "APT_WO_OTH_PAY")), TYPE_AFR040(
			VDRptTypes.TYPE_AFR, new VDCombo("坏账冲其他应收", "HUAI_ZHANG_CHONG_QI_TA_YING_SHOU"),
			new VDCombo("坏账冲其他应收", "BDR_WO_OTH_RCV")),

	TYPE_RFI000(VDRptTypes.TYPE_RFI, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收", "UR_DEST_CH_NPOD")), TYPE_RFI001(
			VDRptTypes.TYPE_RFI, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收",
					"UR_DEST_CD_NPOD")), TYPE_RFI002(VDRptTypes.TYPE_RFI, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
			"UR_DEST_CH_POD")), TYPE_RFI003(VDRptTypes.TYPE_RFI, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_RFI004(
			VDRptTypes.TYPE_RFI, new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲收入",
					"CLAIM_DEST_WO_INCOME")), TYPE_RFI005(VDRptTypes.TYPE_RFI, new VDCombo("理赔",
			"LI_PEI"), new VDCombo("理赔冲到达应收已签收", "CLAIM_WO_DEST_RCV_POD")), TYPE_RFI006(
			VDRptTypes.TYPE_RFI, new VDCombo("理赔", "LI_PEI"), new VDCombo("理赔冲到达应收未签收",
					"CLAIM_WO_DEST_RCV_NPOD")), TYPE_RFI007(VDRptTypes.TYPE_RFI, new VDCombo(
			"代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo("应付代收货款冲应收到付运费已签收", "COD_DEST_RCV_POD")), TYPE_RFI008(
			VDRptTypes.TYPE_RFI, new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo(
					"应付代收货款冲应收到付运费未签收", "COD_DEST_RCV_NPOD")), TYPE_RFI009(VDRptTypes.TYPE_RFI,
			new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo("应付代收货款冲应收始发运费已签收",
					"COD_ORIG_RCV_POD")), TYPE_RFI010(VDRptTypes.TYPE_RFI, new VDCombo("代收货款",
			"DAI_SHOU_HUO_KUAN"), new VDCombo("应付代收货款冲应收始发运费未签收", "COD_ORIG_RCV_NPOD")), TYPE_RFI011(
			VDRptTypes.TYPE_RFI, new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo(
					"还款代收货款现金未签收", "COD_UR_CH_NPOD")), TYPE_RFI012(VDRptTypes.TYPE_RFI,
			new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo("还款代收货款银行未签收", "COD_UR_CD_NPOD")), TYPE_RFI013(
			VDRptTypes.TYPE_RFI, new VDCombo("代收货款", "DAI_SHOU_HUO_KUAN"), new VDCombo(
					"应付代收货款冲小票应收", "COD_WO_OR_RCV")),TYPE_RFI015(
							VDRptTypes.TYPE_RFI, new VDCombo("营业部预收客户",
									"YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo("预收客户冲应收到付运费未签收",
									"CUST_DR_DEST_RCV_NPOD")), TYPE_RFI016(VDRptTypes.TYPE_RFI,
							new VDCombo("营业部预收客户", "YING_YE_BU_YU_SHOU_KE_HU"), new VDCombo(
									"预收客户冲应收到付运费已签收", "CUST_DR_DEST_RCV_POD")), TYPE_RFI014(VDRptTypes.TYPE_RFI, new VDCombo(
			"退运费", "TUI_YUN_FEI"), new VDCombo("退运费冲收入", "RD_DEST_WO_INCOME")), TYPE_RFI017(
					VDRptTypes.TYPE_RFI, new VDCombo("退运费", "TUI_YUN_FEI"),
					new VDCombo("退运费冲到达应收已签收", "RD_WO_DEST_RCV_POD")), TYPE_RFI018(
					VDRptTypes.TYPE_RFI, new VDCombo("退运费", "TUI_YUN_FEI"),
					new VDCombo("退运费冲到达应收未签收", "RD_WO_DEST_RCV_NPOD")),

	TYPE_PLI000(VDRptTypes.TYPE_PLI, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收", "UR_DEST_CH_NPOD")), TYPE_PLI001(
			VDRptTypes.TYPE_PLI, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收",
					"UR_DEST_CD_NPOD")), TYPE_PLI002(VDRptTypes.TYPE_PLI, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
			"UR_DEST_CH_POD")), TYPE_PLI003(VDRptTypes.TYPE_PLI, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_PLI004(
			VDRptTypes.TYPE_PLI, new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo(
					"应付偏线代理成本冲应收到付运费已签收", "PL_COST_WO_DEST_RCV_POD")), TYPE_PLI005(
			VDRptTypes.TYPE_PLI, new VDCombo("偏线代理成本", "PIAN_XIAN_DAI_LI_CHENG_BEN"), new VDCombo(
					"应付偏线代理成本冲应收到付运费未签收", "PL_COST_WO_DEST_RCV_NPOD")), TYPE_PLI006(
			VDRptTypes.TYPE_PLI, new VDCombo("预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo(
					"预收偏线代理冲应收到付运费已签收", "PL_DR_WO_DEST_RCV_POD")), TYPE_PLI007(VDRptTypes.TYPE_PLI,
			new VDCombo("预收偏线代理", "YU_SHOU_PIAN_XIAN_DAI_LI"), new VDCombo("预收偏线代理冲应收到付运费未签收",
					"PL_DR_WO_DEST_RCV_NPOD")),

	TYPE_AFI000(VDRptTypes.TYPE_AFI, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金未签收", "UR_DEST_CH_NPOD")), TYPE_AFI001(
			VDRptTypes.TYPE_AFI, new VDCombo("还款运单总运费（到付）",
					"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行未签收",
					"UR_DEST_CD_NPOD")), TYPE_AFI002(VDRptTypes.TYPE_AFI, new VDCombo(
			"还款运单总运费（到付）", "HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款现金已签收",
			"UR_DEST_CH_POD")), TYPE_AFI003(VDRptTypes.TYPE_AFI, new VDCombo("还款运单总运费（到付）",
			"HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_"), new VDCombo("还款银行已签收", "UR_DEST_CD_POD")), TYPE_AFI004(
			VDRptTypes.TYPE_AFI, new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo(
					"预收空运代理冲应收到付运费已签收", "AIR_DR_DEST_RCV_POD")), TYPE_AFI005(VDRptTypes.TYPE_AFI,
			new VDCombo("预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运代理冲应收到付运费未签收",
					"AIR_DR_DEST_RCV_NPOD")), TYPE_AFI006(VDRptTypes.TYPE_AFI, new VDCombo(
			"预收空运代理", "YU_SHOU_KONG_YUN_DAI_LI"), new VDCombo("预收空运代理冲应收代收货款已签收",
			"AIR_DR_WO_COD_RCV_POD")), TYPE_AFI007(VDRptTypes.TYPE_AFI, new VDCombo("空运应付冲应收",
			"KONG_YUN_YING_FU_CHONG_YING_SHOU"), new VDCombo("应付到达代理成本冲应收到付运费已签收",
			"AIR_PR_AGENCY_WO_DEST_RCV_POD")), TYPE_AFI008(VDRptTypes.TYPE_AFI, new VDCombo(
			"空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"), new VDCombo("应付到达代理成本冲应收到付运费未签收",
			"AIR_PR_AGENCY_WO_DEST_RCV_NPOD")), TYPE_AFI009(VDRptTypes.TYPE_AFI, new VDCombo(
			"空运应付冲应收", "KONG_YUN_YING_FU_CHONG_YING_SHOU"), new VDCombo("其他应付冲应收到付运费已签收",
			"AIR_PR_OT_WO_DEST_RCV_POD")), TYPE_AFI010(VDRptTypes.TYPE_AFI, new VDCombo("空运应付冲应收",
			"KONG_YUN_YING_FU_CHONG_YING_SHOU"), new VDCombo("其他应付冲应收到付运费未签收",
			"AIR_PR_OTH_WO_DEST_RCV_NPOD")), TYPE_AFI011(VDRptTypes.TYPE_AFI, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运还款代收货款现金已签收", "AIR_COD_CH_POD")), TYPE_AFI012(
			VDRptTypes.TYPE_AFI, new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo(
					"空运还款代收货款银行已签收", "AIR_COD_CD_POD")), TYPE_AFI013(VDRptTypes.TYPE_AFI,
			new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运签收时已核销代收货款",
					"AIR_COD_POD_WO_COD")), TYPE_AFI014(VDRptTypes.TYPE_AFI, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运反签收时已核销代收货款", "AIR_COD_NPOD_WO_COD")), TYPE_AFI015(
			VDRptTypes.TYPE_AFI, new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo(
					"空运签收时未核销代收货款", "AIR_COD_POD_NWO_COD")), TYPE_AFI016(VDRptTypes.TYPE_AFI,
			new VDCombo("空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运反签收时未核销代收货款",
					"AIR_COD_NPOD_NWO_COD")), TYPE_AFI017(VDRptTypes.TYPE_AFI, new VDCombo(
			"空运代收货款", "KONG_YUN_DAI_SHOU_HUO_KUAN"), new VDCombo("空运到达代理应付冲应收代收货款已签收",
			"AIR_COD_WO_AGENCY_PAY_POD")), TYPE_AFI018(VDRptTypes.TYPE_AFI, new VDCombo("空运代收货款",
			"KONG_YUN_DAI_SHOU_HUO_KUAN"),
			new VDCombo("空运其他应付冲应收代收货款已签收", "AIR_COD_WO_OTH_PAY_COD")),

	TYPE_LDD000(VDRptTypes.TYPE_LDD, new VDCombo("快递代理成本", "LAND_COST"), new VDCombo("快递代理应付生成","LAND_COST_AGENCY_GEN")),
	TYPE_LDD001(VDRptTypes.TYPE_LDD, new VDCombo("快递代理成本", "LAND_COST"), new VDCombo("快递代理应付成本确认","LAND_COST_AGENCY_CFM")),
	TYPE_LDD002(VDRptTypes.TYPE_LDD, new VDCombo("快递代理成本", "LAND_COST"), new VDCombo("快递代理应付成本反确认","LAND_COST_AGENCY_NCFM")),
	TYPE_LDD003(VDRptTypes.TYPE_LDD, new VDCombo("快递代理成本", "LAND_COST"), new VDCombo("其它应付成本确认","LAND_COST_OTHER_CONFIRM")),
	TYPE_LDD004(VDRptTypes.TYPE_LDD, new VDCombo("快递代理成本", "LAND_COST"), new VDCombo("快递代理成本付款申请","LAND_COST_PAY_APPLY")),
	TYPE_LDD005(VDRptTypes.TYPE_LDD, new VDCombo("快递代理其他应收", "LAND_OTH"), new VDCombo("快递代理其他应收录入","LAND_OTH_ENTRY")),
	TYPE_LDD006(VDRptTypes.TYPE_LDD, new VDCombo("快递代理其他应收", "LAND_OTH"), new VDCombo("还款快递代理其他应收现金","LAND_OTH_RCV_CH")),
	TYPE_LDD007(VDRptTypes.TYPE_LDD, new VDCombo("快递代理其他应收", "LAND_OTH"), new VDCombo("还款快递代理其他应收银行","LAND_OTH_RCV_CD")),
	TYPE_LDD008(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理还款代收货款现金已签收","LAND_COD_CH_POD")),
	TYPE_LDD009(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理还款代收货款银行已签收","LAND_COD_CD_POD")),
	TYPE_LDD010(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理还款代收货款现金未签收","LAND_COD_CH_NPOD")),
	TYPE_LDD011(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理签收时已核销代收货款","LAND_COD_POD_WO_COD")),
	TYPE_LDD012(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理反签收时已核销代收货款","LAND_COD_NPOD_WO_COD")),
	TYPE_LDD013(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理还款代收货款银行未签收","LAND_COD_CD_NPOD")),
	TYPE_LDD014(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理应付冲应收代收货款已签收","LAND_COD_WO_AGENCY_PAY_POD")),
	TYPE_LDD015(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理其他应付冲应收代收货款已签收","LAND_COD_WO_OTH_PAY_COD")),
	TYPE_LDD016(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理应付冲应收代收货款未签收","LAND_COD_WO_AGENCY_PAY_NPOD")),
	TYPE_LDD017(VDRptTypes.TYPE_LDD, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理其他应付冲应收代收货款未签收","LAND_COD_WO_OTH_NPOD")),
	TYPE_LDD018(VDRptTypes.TYPE_LDD, new VDCombo("坏账冲其他应收", "LAND_BDR"), new VDCombo("坏账冲其他应收","LAND_BDR_WO_OTH_RCV")),
	TYPE_LDD019(VDRptTypes.TYPE_LDD, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款现金未签收","LAND_UR_DEST_CH_NPOD")),
	TYPE_LDD020(VDRptTypes.TYPE_LDD, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款银行未签收","LAND_UR_DEST_CD_NPOD")),
	TYPE_LDD021(VDRptTypes.TYPE_LDD, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款现金已签收","LAND_UR_DEST_CH_POD")),
	TYPE_LDD022(VDRptTypes.TYPE_LDD, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款银行已签收","LAND_UR_DEST_CD_POD")),
	TYPE_LDD023(VDRptTypes.TYPE_LDD, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("应付代理成本冲应收到付运费已签收","LAND_PR_AGENCY_WO_DEST_RCV_POD")),
	TYPE_LDD024(VDRptTypes.TYPE_LDD, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("应付到达代理成本冲应收到付运费未签收","LAND_PR_AGENCY_WO_DEST_RCV_NP")),
	TYPE_LDD025(VDRptTypes.TYPE_LDD, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("其他应付冲应收到付运费已签收","LAND_PR_OT_WO_DEST_RCV_POD")),
	TYPE_LDD026(VDRptTypes.TYPE_LDD, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("其他应付冲应收到付运费未签收","LAND_PR_OTH_WO_DEST_RCV_NPOD")),
	TYPE_LDD027(VDRptTypes.TYPE_LDD, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("应付冲其他应收","LAND_PR_OTH_WO_OTH_RCV")),
	TYPE_LDD028(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理现金","LAND_DR_CH")),
	TYPE_LDD029(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理银行","LAND_DR_CD")),
	TYPE_LDD030(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收到付运费已签收","LAND_DR_DEST_RCV_POD")),
	TYPE_LDD031(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收到付运费未签收","LAND_DR_DEST_RCV_NPOD")),
	TYPE_LDD032(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲其他应收","LAND_DR_WO_OTHER_RCV")),
	TYPE_LDD033(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收代收货款已签收","LAND_DR_WO_COD_RCV_POD")),
	TYPE_LDD034(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收代收货款未签收","LAND_DR_WO_COD_RCV_NPOD")),
	TYPE_LDD035(VDRptTypes.TYPE_LDD, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("快递代理退预收付款申请","LAND_DR_PAY_APPLY")),
	TYPE_LDD036(VDRptTypes.TYPE_LDD, new VDCombo("预付", "LAND_APT"), new VDCombo("预付快递代理公司","LAND_APT_COM")),
	TYPE_LDD037(VDRptTypes.TYPE_LDD, new VDCombo("预付", "LAND_APT"), new VDCombo("预付冲应付快递代理公司","LAND_APT_WO_COM_PAY")),
	TYPE_LDD038(VDRptTypes.TYPE_LDD, new VDCombo("预付", "LAND_APT"), new VDCombo("预付冲其他应付","LAND_APT_WO_OTH_PAY")),
	
	TYPE_LDI000(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理签收时未核销代收货款","LAND_COD_POD_NWO_COD")),
	TYPE_LDI001(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理还款代收货款现金已签收","LAND_COD_CH_POD")),
	TYPE_LDI002(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理还款代收货款银行已签收","LAND_COD_CD_POD")),
	TYPE_LDI003(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理反签收时未核销代收货款","LAND_COD_NPOD_NWO_COD")),
	TYPE_LDI004(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理签收时已核销代收货款","LAND_COD_POD_WO_COD")),
	TYPE_LDI005(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理反签收时已核销代收货款","LAND_COD_NPOD_WO_COD")),
	TYPE_LDI006(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理应付冲应收代收货款已签收","LAND_COD_WO_AGENCY_PAY_POD")),
	TYPE_LDI007(VDRptTypes.TYPE_LDI, new VDCombo("快递代理代收货款", "LAND_COD"), new VDCombo("快递代理其他应付冲应收代收货款已签收","LAND_COD_WO_OTH_PAY_COD")),
	TYPE_LDI008(VDRptTypes.TYPE_LDI, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款现金未签收","LAND_UR_DEST_CH_NPOD")),
	TYPE_LDI009(VDRptTypes.TYPE_LDI, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款银行未签收","LAND_UR_DEST_CD_NPOD")),
	TYPE_LDI010(VDRptTypes.TYPE_LDI, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款现金已签收","LAND_UR_DEST_CH_POD")),
	TYPE_LDI011(VDRptTypes.TYPE_LDI, new VDCombo("还款运单总运费（到付）", "LAND_UR_DEST"), new VDCombo("还款银行已签收","LAND_UR_DEST_CD_POD")),
	TYPE_LDI012(VDRptTypes.TYPE_LDI, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("应付到达代理成本冲应收到付运费已签收","LAND_PR_AGENCY_WO_DEST_RCV_POD")),
	TYPE_LDI013(VDRptTypes.TYPE_LDI, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("应付到达代理成本冲应收到付运费未签","LAND_PR_AGENCY_WO_DEST_RCV_NP")),
	TYPE_LDI014(VDRptTypes.TYPE_LDI, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("其他应付冲应收到付运费已签收","LAND_PR_OT_WO_DEST_RCV_POD")),
	TYPE_LDI015(VDRptTypes.TYPE_LDI, new VDCombo("快递代理应付冲应收", "LAND_PR"), new VDCombo("其他应付冲应收到付运费未签收","LAND_PR_OTH_WO_DEST_RCV_NPOD")),
	TYPE_LDI016(VDRptTypes.TYPE_LDI, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收到付运费已签收","LAND_DR_DEST_RCV_POD")),
	TYPE_LDI017(VDRptTypes.TYPE_LDI, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收到付运费未签收","LAND_DR_DEST_RCV_NPOD")),
	TYPE_LDI018(VDRptTypes.TYPE_LDI, new VDCombo("预收快递代理", "LAND_DR"), new VDCombo("预收快递代理冲应收代收货款已签收","LAND_DR_WO_COD_RCV_POD")),
		
	TYPE_LWO000(VDRptTypes.TYPE_LWO, new VDCombo("营业部预收客户冲销快递代理应收", "CUST_TO_LAND_OR"), new VDCombo("预收客户冲应收始发运费未签收","CUST_DR_ORIG_LAND_RCV_NPOD")),
	TYPE_LWO001(VDRptTypes.TYPE_LWO, new VDCombo("营业部预收客户冲销快递代理应收", "CUST_TO_LAND_OR"), new VDCombo("预收客户冲应收始发运费已签收","CUST_DR_ORIG_LAND_RCV_POD")),
	TYPE_LWO002(VDRptTypes.TYPE_LWO, new VDCombo("营业部理赔冲销快递代理应收", "CUST_TO_LAND_CLAIM"), new VDCombo("理赔冲始发应收已签收","CLAIM_WO_ORIG_LAND_RCV_POD")),
	TYPE_LWO003(VDRptTypes.TYPE_LWO, new VDCombo("营业部理赔冲销快递代理应收", "CUST_TO_LAND_CLAIM"), new VDCombo("理赔冲始发应收未签收","CLAIM_WO_ORIG_LAND_RCV_NPOD")),
	TYPE_LWO004(VDRptTypes.TYPE_LWO, new VDCombo("营业部冲销快递代理小票应收", "CUST_TO_LAND_OTHER"), new VDCombo("应付理赔冲小票应收","OR_LAND_RCV_WO_CLAIM_PAY")),
	TYPE_LWO005(VDRptTypes.TYPE_LWO, new VDCombo("营业部冲销快递代理小票应收", "CUST_TO_LAND_OTHER"), new VDCombo("预收客户冲小票应收","OR_LAND_RCV_WO_CUST_DR")),
	TYPE_LWO006(VDRptTypes.TYPE_LWO, new VDCombo("营业部退运费冲销快递代理应收", "CUST_TO_LAND_REFUND"), new VDCombo("退运费冲始发应收已签收","REFUND_WO_ORIG_LAND_RCV_POD")),
	TYPE_LWO007(VDRptTypes.TYPE_LWO, new VDCombo("营业部退运费冲销快递代理应收", "CUST_TO_LAND_REFUND"), new VDCombo("退运费冲始发应收未签收","REFUND_WO_ORIG_LAND_RCV_NPOD")),
	TYPE_LWO008(VDRptTypes.TYPE_LWO, new VDCombo("快递代理理赔冲销营业部应收", "LAND_TO_CUST_CLAIM"), new VDCombo("理赔冲始发应收已签收","LAND_CLAIM_WO_ORIG_RCV_POD")),
	TYPE_LWO009(VDRptTypes.TYPE_LWO, new VDCombo("快递代理理赔冲销营业部应收", "LAND_TO_CUST_CLAIM"), new VDCombo("理赔冲始发应收未签收","LAND_CLAIM_WO_ORIG_RCV_NPOD")),
	TYPE_LWO010(VDRptTypes.TYPE_LWO, new VDCombo("快递代理冲销营业部应收小票", "LAND_TO_CUST_OTHER"), new VDCombo("应付理赔冲小票应收","OR_RCV_WO_LAND_CLAIM_PAY")),
	TYPE_LWO011(VDRptTypes.TYPE_LWO, new VDCombo("快递代理退运费冲销营业部应收", "LAND_TO_CUST_REFUND"), new VDCombo("退运费冲始发应收已签收","LAND_REFUND_WO_ORIG_RCV_POD")),
	TYPE_LWO012(VDRptTypes.TYPE_LWO, new VDCombo("快递代理退运费冲销营业部应收", "LAND_TO_CUST_REFUND"), new VDCombo("退运费冲始发应收未签收","LAND_REFUND_WO_ORIG_RCV_NPOD")),

	TYPE_LWO013(VDRptTypes.TYPE_LWO, new VDCombo("快递代理付款申请", "EXPRESS_PAY_APPLY"), new VDCombo("出发部门付款申请", "ORIG_ORG_PAY_APPLY")),
	TYPE_LWO014(VDRptTypes.TYPE_LWO, new VDCombo("快递代理付款申请", "EXPRESS_PAY_APPLY"), new VDCombo("到达部门付款申请", "DEST_ORG_PAY_APPLY"));

	/**
	 * 报表类型
	 */
	private String rptType;

	/**
	 * 大类
	 */
	private VDCombo type;

	/**
	 * 小类
	 */
	private VDCombo subType;

	/**
	 * 
	 * （创建一个新的实例 ）VDTypeMappingEnum
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-1 下午6:18:00
	 */
	private VDTypeMappingEnum(String rptType, VDCombo type, VDCombo subType) {
		this.rptType = rptType;
		this.type = type;
		this.subType = subType;
	}

	/**
	 * 
	 * 根据报表编码查询大类
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-1 下午6:18:15
	 */
	public static List<VDCombo> getTypeByRptCode(String rptType) {

		// 编码为空，则返回NULL
		if (StringUtils.isEmpty(rptType)) {
			return null;
		}

		// 结果
		List<VDCombo> types = new ArrayList<VDCombo>();

		// 循环遍历
		for (VDTypeMappingEnum mapping : VDTypeMappingEnum.values()) {
			if (mapping != null) {
				if (rptType.equals(mapping.getRptType()) && !types.contains(mapping.getType())) {
					types.add(mapping.getType());
				}
			}
		}

		return types;
	}

	/**
	 * 根据大类编码，查询子类combo
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-2 上午11:40:18
	 */
	public static List<VDCombo> getSubTypeComboByTypeCode(String rptType, String typeCode) {

		// 编码为空，则返回NULL
		if (StringUtils.isEmpty(rptType) || StringUtils.isEmpty(typeCode)) {
			return null;
		}

		// 结果
		List<VDCombo> subTypes = new ArrayList<VDCombo>();

		// 循环遍历
		for (VDTypeMappingEnum mapping : VDTypeMappingEnum.values()) {
			if (mapping != null && mapping.getType() != null) {

				// 根据报表类型、大类编码判断
				String rptCode = mapping.getRptType();
				String vdTypeCode = mapping.getType().getCode();

				if (rptType.equals(rptCode) && typeCode.equals(vdTypeCode)
						&& !subTypes.contains(mapping.getSubType())) {
					subTypes.add(mapping.getSubType());
				}
			}
		}

		return subTypes;
	}

	/**
	 * 
	 * 根据大类编码查询子节点编码
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-1 下午6:18:36
	 */
	public static List<String> getChildCodeByTypeCode(String rptType, String typeCode) {

		List<VDCombo> subTypeList = getSubTypeComboByTypeCode(rptType, typeCode);

		if (CollectionUtils.isNotEmpty(subTypeList)) {

			List<String> codeList = new ArrayList<String>();
			for (VDCombo combo : subTypeList) {
				codeList.add(combo.getCode());
			}

			return codeList;

		}

		return null;
	}

	/**
	 * 
	 * 根据编码获取名称，供前台界面显示
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-2 上午8:41:21
	 */
	public static Map<String, String[]> getRenderMap(String rptType) {

		// 映射
		Map<String, String[]> map = new HashMap<String, String[]>();

		// 循环遍历
		for (VDTypeMappingEnum mapping : VDTypeMappingEnum.values()) {

			if (mapping != null) {

				// 如果不是查询的报表，则继续
				String name = mapping.getRptType();
				if (!rptType.equals(name)) {
					continue;
				}

				// 大类、小类、子类
				VDCombo typeCombo = mapping.getType();
				VDCombo subTypeCombo = mapping.getSubType();

				// 以子类编码为key
				map.put(subTypeCombo.getCode(),
						new String[] { subTypeCombo.getName(), typeCombo.getName() });

			}

		}

		return map;
	}

	public String getRptType() {
		return rptType;
	}

	public VDCombo getType() {
		return type;
	}

	public VDCombo getSubType() {
		return subType;
	}
}
