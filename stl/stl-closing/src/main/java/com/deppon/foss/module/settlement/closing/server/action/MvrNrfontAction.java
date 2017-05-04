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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfontEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNrfontEntityVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发应收月报
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-7 上午10:17:01
 */
public class MvrNrfontAction extends AbstractAction {

	/**
	 * 始发应收月报action序列号
	 */
	private static final long serialVersionUID = -7990595546324470754L;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger(MvrNrfontAction.class);

	/**
	 * 始发应收Vo
	 */
	private MvrNrfontEntityVo mvrNrfontEntityVo;

	/**
	 * 始发应收service
	 * 
	 */
	private IMvrNrfontEntityService mvrNrfontEntityService;

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
	public String queryMvrNrfont() {

		try {

			MvrNrfontEntityQueryDto dto = mvrNrfontEntityVo
					.getMvrNrfontEntityQueryDto();
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 查询始发应收月报信息
			// param：参数dto，分页数据
			MvrNrfontEntityResultDto mvrNrfontEntityResultDto = mvrNrfontEntityService
					.selectByConditionsAndPage(dto, this.getStart(),
							this.getLimit());

			// 设置返回值
			mvrNrfontEntityVo
					.setMvrNrfontEntityResultDto(mvrNrfontEntityResultDto);
			// 设置条数
			this.setTotalCount(mvrNrfontEntityResultDto
					.getMvrNrfontEntityTotalRows());

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
	@SuppressWarnings("unchecked")
	public String exportMvrNrfont() {

		try {

			// 导出02普通业务始发月报表参数不能为空
			if (mvrNrfontEntityVo == null) {
				// 提示查询参数不能为空
				throw new SettlementException("查询参数不能为空");
			}

			MvrNrfontEntityQueryDto dto = mvrNrfontEntityVo
					.getMvrNrfontEntityQueryDto();

			// 导出02普通业务始发月报表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				// 提示导出02普通业务始发月报表期间不能为空
				throw new SettlementException("导出02普通业务始发月报表期间不能为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 导出文件名称：
			// 02普通业务始发月报表_+账期构成
			try {
				String exportExcelName = "02普通业务始发月报表" + dto.getPeriod()
						+ ".xls";
				/*
				 * this.setExeclName(URLEncoder.encode(exportExcelName,
				 * SettlementConstants.UNICODE_UTF));
				 */

				// 转化编码
				this.setExeclName(new String((exportExcelName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrNrfontEntity> queryList = mvrNrfontEntityService
					.selectByConditions(dto).getMvrNrfontEntityList();
			// 查询结果不能为空
			if (CollectionUtils.isEmpty(queryList)) {
				// 提示导出02普通业务始发月报表期间不能为空
				throw new SettlementException("导出02普通业务始发月报表查询数据为空");
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

			HeaderRows[] headerCfg = {
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.TEN, "数据统计维度"),// 8
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ELEVEN, SettlementReportNumber.TWELVE, "开单运单【01】"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTEEN, SettlementReportNumber.FOURTEEN, "开单运单【02】"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FIFTEEN, SettlementReportNumber.EIGHTEEN, "还款运单总运费（月结临时欠款网上支付）【01】"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.NINETEEN, SettlementReportNumber.TWENTY_TWO, "还款运单总运费（月结临时欠款网上支付）【02】"),// 35
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_THREE, SettlementReportNumber.TWENTY_SIX, "还款运单总运费（到付）【02】"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_SEVEN, SettlementReportNumber.SIXTY_ONE, "签收运单【02】"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_SEVEN, SettlementReportNumber.THIRTY_THREE, "签收时现付已收款金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_FOUR, SettlementReportNumber.FORTY, "签收时始发应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_ONE, SettlementReportNumber.FORTY_SEVEN, "签收时始发应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_EIGHT, SettlementReportNumber.FIFTY_FOUR, "签收时到达应收已核销金额"),
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_FIVE, SettlementReportNumber.SIXTY_ONE, "签收时到达应收未核销金额"),
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.SIXTY_TWO, SettlementReportNumber.NINETY_SIX, "反签收运单【02】"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_TWO, SettlementReportNumber.SIXTY_EIGHT, "反签收时现付已收款金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_NINE, SettlementReportNumber.SEVENTY_FIVE, "反签收时始发应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_SIX, SettlementReportNumber.EIGHTY_TWO, "反签收时始发应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_THREE, SettlementReportNumber.EIGHTY_NINE, "反签收时到达应收已核销金额"),
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.NINETY, SettlementReportNumber.NINETY_SIX, "反签收时到达应收未核销金额"),
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.NINETY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_TWELVE, "预收客户"),// 35
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_SEVEN, SettlementReportNumber.NINETY_EIGHT, "预收客户【01】"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_NINE, SettlementReportNumber.ONE_HUNDRED, "预收客户【02】"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTEEN, SettlementReportNumber.ONE_HUNDRED_AND_SIXTEEN, "代收货款"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SEVENTEEN, SettlementReportNumber.ONE_HUNDRED_AND_EIGHTEEN, "偏线代理成本"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_NINETEEN, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY, "预收偏线代理"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, "预收空运/快递代理"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SIX, "空运/快递代理应付冲应收"), // 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_EIGHT, "偏线应收冲应付") // 2
			};
			sheet.setHeaderList(Arrays.asList(headerCfg));

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();

			// 设置sheet名称
			String exprotSheetName = mvrNrfontEntityVo
					.getMvrNrfontEntityQueryDto().getPeriod().toString();
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
				"到达部门名称", "统一结算类型",  "合同部门编码", "合同部门名称",
				"开单现金", "开单银行卡", "开单现金", "开单银行卡", "还款现金未签收",
				"还款银行未签收", "还款现金已签收", "还款银行已签收", "还款现金未签收", "还款银行未签收",
				"还款现金已签收", "还款银行已签收", "还款现金未签收", "还款银行未签收", "还款现金已签收",
				"还款银行已签收", "公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费",
				"其他费用", "公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"公布价运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用",
				"预收客户现金", "预收客户银行", "预收客户现金", "预收客户银行", "01预收客户冲02应收到付运费未签收",
				"02预收客户冲02应收到付运费未签收", "01预收客户冲02应收到付运费已签收",
				"02预收客户冲02应收到付运费已签收", "02预收客户冲02应收始发运费未签收",
				"01预收客户冲02应收始发运费未签收", "02预收客户冲01应收始发运费未签收",
				"02预收客户冲02应收始发运费已签收", "01预收客户冲02应收始发运费已签收",
				"02预收客户冲01应收始发运费已签收", "01始发退预收付款申请", "02始发退预收付款申请",
				"应付代收货款冲02应收到付运费已签收", "应付代收货款冲02应收到付运费未签收",
				"应付代收货款冲02应收始发运费已签收", "应付代收货款冲02应收始发运费未签收",
				"应付偏线代理成本冲02应收到付运费已签收", "应付偏线代理成本冲02应收到付运费未签收",
				"预收偏线代理冲02应收到付运费已签收", "预收偏线代理冲02应收到付运费未签收",
				"预收空运/快递代理冲02应收到付运费已签收", "预收空运/快递代理冲02应收到付运费未签收",
				"应付到达代理/快递代理成本冲02应收到付运费已签收", "应付到达代理/快递代理成本冲02应收到付运费未签收",
				"其他应付冲02应收到付运费已签收", "其他应付冲02应收到付运费未签收", "其他应付冲02应收到付运费已签收",
				"其他应付冲02应收到付运费未签收" };

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
	private Object[] exportDataAndType(List<MvrNrfontEntity> queryList) {

		String[] properties = new String[] { "period", "productCode",
				"customerCode", "customerName", "origOrgCode", "origOrgName",
				"destOrgCode", "destOrgName", "unifiedSettlementType", "contractOrgCode", "contractOrgName", 
				"deoCh", "deoCd", "detCh", "detCd", 
				"uroOrigChNpod", "uroOrigCdNpod", "uroOrigChPod",
				"uroOrigCdPod", "urtOrigChNpod", "urtOrigCdNpod",
				"urtOrigChPod", "urtOrigCdPod", "urtDestChNpod",
				"urtDestCdNpod", "urtDestChPod", "urtDestCdPod",
				"podtCashCollectedFrt", "podtCashCollectedPup",
				"podtCashCollectedDel", "podtCashCollectedPkg",
				"podtCashCollectedDv", "podtCashCollectedCod",
				"podtCashCollectedOt", "podtOrigRcvWoFrt", "podtOrigRcvWoPup",
				"podtOrigRcvWoDel", "podtOrigRcvWoPkg", "podtOrigRcvWoDv",
				"podtOrigRcvWoCod", "podtOrigRcvWoOt", "podtOrigRcvNwoFrt",
				"podtOrigRcvNwoPup", "podtOrigRcvNwoDel", "podtOrigRcvNwoPkg",
				"podtOrigRcvNwoDv", "podtOrigRcvNwoCod", "podtOrigRcvNwoOt",
				"podtDestRcvWoFrt", "podtDestRcvWoPup", "podtDestRcvWoDel",
				"podtDestRcvWoPkg", "podtDestRcvWoDv", "podtDestRcvWoCod",
				"podtDestRcvWoOt", "podtDestRcvNwoFrt", "podtDestRcvNwoPup",
				"podtDestRcvNwoDel", "podtDestRcvNwoPkg", "podtDestRcvNwoDv",
				"podtDestRcvNwoCod", "podtDestRcvNwoOt",
				"updtCashCollectedFrt", "updtCashCollectedPup",
				"updtCashCollectedDel", "updtCashCollectedPkg",
				"updtCashCollectedDv", "updtCashCollectedCod",
				"updtCashCollectedOt", "updtOrigRcvWoFrt", "updtOrigRcvWoPup",
				"updtOrigRcvWoDel", "updtOrigRcvWoPkg", "updtOrigRcvWoDv",
				"updtOrigRcvWoCod", "updtOrigRcvWoOt", "updtOrigRcvNwoFrt",
				"updtOrigRcvNwoPup", "updtOrigRcvNwoDel", "updtOrigRcvNwoPkg",
				"updtOrigRcvNwoDv", "updtOrigRcvNwoCod", "updtOrigRcvNwoOt",
				"updtDestRcvWoFrt", "updtDestRcvWoPup", "updtDestRcvWoDel",
				"updtDestRcvWoPkg", "updtDestRcvWoDv", "updtDestRcvWoCod",
				"updtDestRcvWoOt", "updtDestRcvNwoFrt", "updtDestRcvNwoPup",
				"updtDestRcvNwoDel", "updtDestRcvNwoPkg", "updtDestRcvNwoDv",
				"updtDestRcvNwoCod", "updtDestRcvNwoOt", "custDrOch",
				"custDrOcd", "custDrTch", "custDrTcd", "custDroWoDestRcvtNpod",
				"custDrtWoDestRcvtNpod", "custDroWoDestRcvtPod",
				"custDrtWoDestRcvtPod", "custDrtWoOrigRcvtNpod",
				"custDroWoOrigRcvtNpod", "custDrtWoOrigRcvoNpod",
				"custDrtWoOrigRcvtPod", "custDroWoOrigRcvtPod",
				"custDrtWoOrigRcvoPod", "custDroPayApply", "custDrtPayApply",
				"codPayWoDestRcvtPod", "codPayWoDestRcvtNpod",
				"codPayWoOrigRcvtPod", "codPayWoOrigRcvtNpod",
				"plCostWoDestRcvtPod", "plCostWoDestRcvtNpod",
				"plDrWoDestRcvtPod", "plDrWoDestRcvtNpod", "alDrWoDestRcvtPod",
				"alDrWoDestRcvtNpod", "alpwrWoDestRcvtPod",
				"alpwrWoDestRcvtNpod", "othPayWoDestRcvtPod",
				"othPayWoDestRcvtNpod","popWoDrtPod","popWoDrtNpod" };

		List<String> strProperties = Arrays.asList(new String[] { "period",
				"productCode", "customerCode", "customerName", "origOrgCode",
				"origOrgName", "destOrgCode", "destOrgName", 
				"unifiedSettlementType", "contractOrgCode", "contractOrgName" });

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
		for (MvrNrfontEntity entity : queryList) {

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
				// 统一结算类型
				if ("unifiedSettlementType".equalsIgnoreCase(property)) {
					// 如果统一结算类型不为空
					if (StringUtils.isNotEmpty(cellValue)) {
						if(SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_ORIG.equals(cellValue)){
							cellValue = "始发统一结算";
						}else if(SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_DEST.equals(cellValue)){
							cellValue = "到达统一结算";
						}else{
							cellValue = "非统一结算";
						}
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
	 * @return mvrNrfontEntityVo
	 */
	public MvrNrfontEntityVo getMvrNrfontEntityVo() {
		return mvrNrfontEntityVo;
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
	 * @param mvrNrfontEntityService
	 */
	public void setMvrNrfontEntityService(
			IMvrNrfontEntityService mvrNrfontEntityService) {
		this.mvrNrfontEntityService = mvrNrfontEntityService;
	}

	/**
	 * @param mvrNrfontEntityVo
	 */
	public void setMvrNrfontEntityVo(MvrNrfontEntityVo mvrNrfontEntityVo) {
		this.mvrNrfontEntityVo = mvrNrfontEntityVo;
	}

}
