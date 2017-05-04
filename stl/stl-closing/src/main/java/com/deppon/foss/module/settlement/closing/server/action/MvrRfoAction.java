package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrRfoEntityVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发应收月报
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-7 上午10:17:01
 */
public class MvrRfoAction extends AbstractAction {

	/**
	 * 始发应收月报action序列号
	 */
	private static final long serialVersionUID = -7990595546324470754L;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger(MvrRfoAction.class);

	/**
	 * 始发应收Vo
	 */
	private MvrRfoEntityVo mvrRfoEntityVo;

	/**
	 * 始发应收service
	 * 
	 */
	private IMvrRfoEntityService mvrRfoEntityService;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService productService;

	/**
	 * @SET
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		/*
		 * @set
		 * 
		 * @this.productService = productService
		 */
		this.productService = productService;
	}

	/**
	 * 查询始发应收月报信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-7 上午10:10:54
	 */
	@JSON
	public String queryMvrRfo() {

		try {

			MvrRfoEntityQueryDto dto = mvrRfoEntityVo.getMvrRfoEntityQueryDto();
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 查询始发应收月报信息
			// param：参数dto，分页数据
			MvrRfoEntityResultDto mvrRfoEntityResultDto = mvrRfoEntityService
					.selectByConditionsAndPage(dto, this.getStart(),
							this.getLimit());

			// 设置返回值
			mvrRfoEntityVo.setMvrRfoEntityResultDto(mvrRfoEntityResultDto);
			// 设置条数
			this.setTotalCount(mvrRfoEntityResultDto.getMvrRfoEntityTotalRows());

			// 正常返回
			return returnSuccess();
			// 异常处理
		} catch (BusinessException be) {
			// 记录日志
			logger.error(be.getMessage(), be);
			// 异常返回
			return returnError(be);
		}

	}
     
	/**
	 * 导出始发应收
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-13 下午1:40:58
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String exportMvrRfo() {

		try {

			// 导出始发应收月报表参数不能为空
			if (mvrRfoEntityVo == null) {
				// 提示查询参数不能为空
				throw new SettlementException("查询参数不能为空");
			}

			MvrRfoEntityQueryDto dto = mvrRfoEntityVo.getMvrRfoEntityQueryDto();

			// 导出始发应收月报表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				// 提示导出始发应收月报表期间不能为空
				throw new SettlementException("导出始发应收月报表期间不能为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 导出文件名称：
			// 始发应收月报表_+账期构成
			try {
				String exportExcelName = "始发月报表" + dto.getPeriod() + ".xls";
				/*this.setExeclName(URLEncoder.encode(exportExcelName,
						SettlementConstants.UNICODE_UTF));*/
				
				// 转化编码
				this.setExeclName(new String((exportExcelName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrRfoEntity> queryList = mvrRfoEntityService
					.selectByConditions(dto).getMvrRfoEntityList();
			// 查询结果不能为空
			if (CollectionUtils.isEmpty(queryList)) {
				// 提示导出始发应收月报表期间不能为空
				throw new SettlementException("导出始发月报表查询数据为空");
			}

			// 生成导出数据源类
			ExportResource sheet = new ExportResource();
			// 设置表单表头
			sheet.setHeads(this.getExcelHeads());

			Object[] dataAndType = this.exportDataAndType(queryList);
			// 处理返回的结果集

			List<List<String>> dataList = (List<List<String>>) dataAndType[0];
			Map<Integer, String> type = (Map<Integer, String>) dataAndType[1];

			// 设置表单数据
			sheet.setRowList(dataList);

			sheet.setHeaderHeight(SettlementReportNumber.THREE);

			HeaderRows[] headerCfg = { new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.SEVEN, "数据统计维度"),// 8
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.EIGHT, SettlementReportNumber.NINE, "开单运单"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TEN, SettlementReportNumber.THIRTEEN, "还款运单总运费（月结临时欠款网上支付）"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FOURTEEN, SettlementReportNumber.SEVENTEEN, "还款运单总运费（到付）"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.EIGHTEEN, SettlementReportNumber.FIFTY_TWO, "签收运单"),// 35
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTEEN, SettlementReportNumber.TWENTY_FOUR, "签收时现付已收款金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_FIVE, SettlementReportNumber.THIRTY_ONE, "签收时始发应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_TWO, SettlementReportNumber.THIRTY_EIGHT, "签收时始发应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_NINE, SettlementReportNumber.FORTY_FIVE, "签收时到达应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_SIX, SettlementReportNumber.FIFTY_TWO, "签收时到达应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.FIFTY_THREE, SettlementReportNumber.EIGHTY_SEVEN, "反签收运单"),// 35
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_THREE, SettlementReportNumber.FIFTY_NINE, "反签收时现付已收款金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY, SettlementReportNumber.SIXTY_SIX, "反签收时始发应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_SEVEN, SettlementReportNumber.SEVENTY_THREE, "反签收时始发应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_FOUR, SettlementReportNumber.EIGHTY, "反签收时到达应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_ONE, SettlementReportNumber.EIGHTY_SEVEN, "反签收时到达应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.EIGHTY_EIGHT, SettlementReportNumber.NINETY_FIVE, "理赔"),// 8
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_EIGHT, SettlementReportNumber.NINETY_TWO, "出发部门申请"),// 5
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_THREE, SettlementReportNumber.NINETY_FIVE, "到达部门申请"),// 3
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_SIX, SettlementReportNumber.NINETY_SIX, "装卸费"),// 1
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_SEVEN, SettlementReportNumber.ONE_HUNDRED, "代收货款"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_ONE, SettlementReportNumber.ONE_HUNDRED_AND_SEVEN, "营业部预收客户"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_NINE, "异常冲收入"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_TEN, SettlementReportNumber.ONE_HUNDRED_AND_ELEVEN, "坏账损失"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_TWELVE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, "小票"),// 18
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWELVE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTEEN, "小票录入现金"),// 5
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SEVENTEEN, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_ONE, "小票录入银行"),// 5
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, "小票录入应收"),// 1
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, "小票应收核销"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_TWO, "弃货、违禁品、全票丢货"), // 3
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY, "退运费"), // 4 ==> 8
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SEVEN, "出发部门申请"),// 3 ==> 5
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_FORTY, "到达部门申请"),// 1 ==> 3
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_ONE, "服务补救"), // 1
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_THREE, "偏线代理成本"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FIVE, "预收偏线代理"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_SIX, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_SEVEN, "预收空运/快递代理"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_ONE, "空运/快递代理应付冲应收") // 4
			};
			sheet.setHeaderList(Arrays.asList(headerCfg));

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();

			// 设置sheet名称
			String exprotSheetName = mvrRfoEntityVo.getMvrRfoEntityQueryDto()
					.getPeriod().toString();
			exportSetting.setSheetName(exprotSheetName);
			exportSetting.setDataType(type);

			// 创建导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();

			// 导出成文件
			inputStream = objExporterExecutor.exportSync(sheet, exportSetting);

			// 正常返回
			return returnSuccess();
			// 异常处理
		} catch (BusinessException e1) {
			// 记录日志
			logger.error(e1.getMessage(), e1);
			// 异常返回
			return returnError(e1);
			// 异常处理
		}
	}

	/**
	 * 始发应收报表表头
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午6:06:58
	 * @return
	 */
	private String[] getExcelHeads() {

		// 始发应收报表导出数据的表头
		String[] header = {
				// 数据统计维度
				"期间", "运输性质", "客户编码", "客户名称", "始发部门编码", "始发部门名称", "到达部门编码",
				"到达部门名称", "开单现金", "开单银行卡", "还款现金未签收", "还款银行未签收", "还款现金已签收",
				"还款银行已签收", "还款现金未签收", "还款银行未签收", "还款现金已签收", "还款银行已签收", "公布价运费",
				"接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用", "公布价运费", "接货费",
				"送货费", "包装费", "保价费", "代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费",
				"包装费", "保价费", "代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费",
				"保价费", "代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用", "理赔冲收入", "理赔入成本", "理赔冲始发应收已签收",
				"理赔冲始发应收未签收", "理赔付款申请", "理赔冲收入", "理赔冲到达应收已签收", "理赔冲到达应收未签收",
				"装卸费付款申请", "应付代收货款冲应收到付运费已签收", "应付代收货款冲应收到付运费未签收",
				"应付代收货款冲应收始发运费已签收", "应付代收货款冲应收始发运费未签收", "预收客户现金", "预收客户银行",
				"预收客户冲应收到付运费未签收", "预收客户冲应收到付运费已签收", "预收客户冲应收始发运费未签收",
				"预收客户冲应收始发运费已签收", "始发退预收付款申请", "应收始发运费已签收", "应收到付运费已签收",
				"坏账冲应收始发运费已签收", "坏账冲应收到付运费已签收", "小票现金之事故赔款", "小票现金之变卖废品收入",
				"小票现金之客户多付运费或盘点长款金额", "小票现金之其他", "小票现金主营业务收入", "小票银行之事故赔款",
				"小票银行之收银员卡利息", "小票银行之客户多付运费或盘点长款金额", "小票银行之其他", "小票银行主营业务收入",
				"小票应收主营业务收入", "还款现金冲小票应收", "还款银行冲小票应收", "应付代收货款冲小票应收",
				"应付理赔冲小票应收", "预收客户冲小票应收", "坏账之保险理赔冲小票应收", "坏账之坏账损失冲小票应收",
				"开单为月结临时欠款网上支付未核销", "开单为月结临时欠款网上支付已核销", "开单为现金银行卡", "退运费冲收入",
				"退运费入成本", "退运费付款申请", "退运费冲始发应收已签收", "退运费冲始发应收未签收", "退运费冲收入",
				"退运费冲到达应收已签收", "退运费冲到达应收未签收", "始发服务补救付款申请",
				"应付偏线代理成本冲应收到付运费已签收", "应付偏线代理成本冲应收到付运费未签收", "预收偏线代理冲应收到付运费已签收",
				"预收偏线代理冲应收到付运费未签收", "预收空运/快递代理冲应收到付运费已签收", "预收空运/快递代理冲应收到付运费未签收",
				"应付到达代理/快递代理成本冲应收到付运费已签收", "应付到达代理/快递代理成本冲应收到付运费未签收", "其他应付冲应收到付运费已签收",
				"其他应付冲应收到付运费未签收" };

		// 返回excel表头
		return header;
	}

	/**
	 * 生成导出始发报表数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午4:09:28
	 * @param queryList
	 * @return
	 */
	private Object[] exportDataAndType(List<MvrRfoEntity> queryList) {

		String[] properties = new String[] { "period", "productCode",
				"customerCode", "customerName", "origOrgCode", "origOrgName",
				"destOrgCode", "destOrgName", "deCh", "deCd", "urOrigChNpod",
				"urOrigCdNpod", "urOrigChPod", "urOrigCdPod", "urDestChNpod",
				"urDestCdNpod", "urDestChPod", "urDestCdPod",
				"podCashCollectedFrt", "podCashCollectedPup",
				"podCashCollectedDel", "podCashCollectedPkg",
				"podCashCollectedDv", "podCashCollectedCod",
				"podCashCollectedOt", "podOrigRcvWoFrt", "podOrigRcvWoPup",
				"podOrigRcvWoDel", "podOrigRcvWoPkg", "podOrigRcvWoDv",
				"podOrigRcvWoCod", "podOrigRcvWoOt", "podOrigRcvNwoFrt",
				"podOrigRcvNwoPup", "podOrigRcvNwoDel", "podOrigRcvNwoPkg",
				"podOrigRcvNwoDv", "podOrigRcvNwoCod", "podOrigRcvNwoOt",
				"podDestRcvWoFrt", "podDestRcvWoPup", "podDestRcvWoDel",
				"podDestRcvWoPkg", "podDestRcvWoDv", "podDestRcvWoCod",
				"podDestRcvWoOt", "podDestRcvNwoFrt", "podDestRcvNwoPup",
				"podDestRcvNwoDel", "podDestRcvNwoPkg", "podDestRcvNwoDv",
				"podDestRcvNwoCod", "podDestRcvNwoOt", "updCashCollectedFrt",
				"updCashCollectedPup", "updCashCollectedDel",
				"updCashCollectedPkg", "updCashCollectedDv",
				"updCashCollectedCod", "updCashCollectedOt", "updOrigRcvWoFrt",
				"updOrigRcvWoPup", "updOrigRcvWoDel", "updOrigRcvWoPkg",
				"updOrigRcvWoDv", "updOrigRcvWoCod", "updOrigRcvWoOt",
				"updOrigRcvNwoFrt", "updOrigRcvNwoPup", "updOrigRcvNwoDel",
				"updOrigRcvNwoPkg", "updOrigRcvNwoDv", "updOrigRcvNwoCod",
				"updOrigRcvNwoOt", "updDestRcvWoFrt", "updDestRcvWoPup",
				"updDestRcvWoDel", "updDestRcvWoPkg", "updDestRcvWoDv",
				"updDestRcvWoCod", "updDestRcvWoOt", "updDestRcvNwoFrt",
				"updDestRcvNwoPup", "updDestRcvNwoDel", "updDestRcvNwoPkg",
				"updDestRcvNwoDv", "updDestRcvNwoCod", "updDestRcvNwoOt",
				"claimOrigWoIncome", "claimOrigCost", "claimWoOrigRcvPod",
				"claimWoOrigRcvNpod", "claimOrigPayApply", "claimDestWoIncome",
				"claimWoDestRcvPod", "claimWoDestRcvNpod", "sfPayApply",
				"codDestRcvPod", "codDestRcvNpod", "codOrigRcvPod",
				"codOrigRcvNpod", "custDrCh", "custDrCd", "custDrDestRcvNpod",
				"custDrDestRcvPod", "custDrOrigRcvNpod", "custDrOrigRcvPod",
				"custDrOrigPayApply", "exOrigRcvPod", "exDestRcvPod",
				"bdOrigRcvPod", "bdDestRcvPod", "orChAc", "orChSi", "orChOpay",
				"orChOther", "orChMbi", "orCdAc", "orCdBankIt", "orCdOpay",
				"orCdOther", "orCdMbi", "orRcvMbi", "orRcvWoUrCh",
				"orRcvWoUrCd", "orRcvWoCodPay", "orRcvWoClaimPay",
				"orRcvWoCustDr", "orRcvWoBdDebt", "orRcvWoBdIncome",
				"acCtdtolNwo", "acCtdtolWo", "acChcd", "rdOrigWoIncome",
				"rdOrigCost", "rdOrigPayApply", "rdWoOrigRcvPod",
				"rdWoOrigRcvNpod", "rdDestWoIncome", "rdWoDestRcvPod",
				"rdWoDestRcvNpod", "cnOrigPayApply", "plCostWoDestRcvPod",
				"plCostWoDestRcvNpod", "plDrWoDestRcvPod", "plDrWoDestRcvNpod",
				"airDrDestRcvPod", "airDrDestRcvNpod",
				"airPrAgencyWoDestRcvPod", "airPrAgencyWoDestRcvNpod",
				"airPrOtWoDestRcvPod", "airPrOthWoDestRcvNpod" };

		List<String> strProperties = Arrays.asList(new String[] { "period",
				"productCode", "customerCode", "customerName", "origOrgCode",
				"origOrgName", "destOrgCode", "destOrgName" });

		// 生成返回数据集合
		List<List<String>> resultList = new ArrayList<List<String>>();

		// 获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (CollectionUtils.isNotEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}

		List<String> data = null;
		Object fieldValue = null;
		String cellValue = null;

		// 循环处理
		for (MvrRfoEntity entity : queryList) {

			data = new ArrayList<String>();
			for (String property : properties) {
				// 获取对象的值，如果为空，则设置其为空字符串
				fieldValue = ReflectionUtils.getFieldValue(entity, property);
				cellValue = (fieldValue == null ? "" : fieldValue.toString());

				// 产品类型需要转换
				if ("productCode".equalsIgnoreCase(property)) {

					// 如果数据产品类型编码不为空
					if (StringUtils.isNotEmpty(cellValue)) {
						// 将产品类型转换编码为名称
						cellValue = productMap.get(cellValue);
					}
				}

				data.add(cellValue);
			}

			resultList.add(data);
		}

		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		for (int i = 0; i < properties.length; i++) {
			String type = strProperties.contains(properties[i]) ? "string"
					: "float";
			typeMap.put(i, type);
		}

		// 返回
		return new Object[] { resultList, typeMap };
	}

	/**
	 * @return mvrRfoEntityVo
	 */
	public MvrRfoEntityVo getMvrRfoEntityVo() {
		return mvrRfoEntityVo;
	}

	/**
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * @param execlName
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param mvrRfoEntityService
	 */
	public void setMvrRfoEntityService(IMvrRfoEntityService mvrRfoEntityService) {
		this.mvrRfoEntityService = mvrRfoEntityService;
	}

	/**
	 * @param mvrRfoEntityVo
	 */
	public void setMvrRfoEntityVo(MvrRfoEntityVo mvrRfoEntityVo) {
		this.mvrRfoEntityVo = mvrRfoEntityVo;
	}

}
