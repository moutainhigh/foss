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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfonoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfonoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNrfonoEntityVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01普通业务始发月报表
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoAction extends AbstractAction {

	/**
	 * 01普通业务始发月报表action序列号
	 */
	private static final long serialVersionUID = -7990595546324470754L;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger(MvrNrfonoAction.class);

	/**
	 * 始发应收Vo
	 */
	private MvrNrfonoEntityVo mvrNrfonoEntityVo;

	/**
	 * 始发应收service
	 * 
	 */
	private IMvrNrfonoEntityService mvrNrfonoEntityService;

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
	 * 查询01普通业务始发月报表信息
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 */
	@JSON
	public String queryMvrNrfono() {

		try {

			MvrNrfonoEntityQueryDto dto = mvrNrfonoEntityVo.getMvrNrfonoEntityQueryDto();
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 查询01普通业务始发月报表信息
			// param：参数dto，分页数据
			MvrNrfonoEntityResultDto mvrNrfonoEntityResultDto = mvrNrfonoEntityService.selectByConditionsAndPage(dto, this.getStart(),
							this.getLimit());

			// 设置返回值
			mvrNrfonoEntityVo.setMvrNrfonoEntityResultDto(mvrNrfonoEntityResultDto);
			// 设置条数
			this.setTotalCount(mvrNrfonoEntityResultDto.getMvrNrfonoEntityTotalRows());

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
	 * @author ddw
	 * @date 2013-11-08
	 */
	@SuppressWarnings("unchecked")
	public String exportMvrNrfono() {

		try {

			// 导出01普通业务始发月报表表参数不能为空
			if (mvrNrfonoEntityVo == null) {
				// 提示查询参数不能为空
				throw new SettlementException("查询参数不能为空");
			}

			MvrNrfonoEntityQueryDto dto = mvrNrfonoEntityVo.getMvrNrfonoEntityQueryDto();

			// 导出01普通业务始发月报表表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				// 提示导出01普通业务始发月报表表期间不能为空
				throw new SettlementException("导出01普通业务始发月报表期间不能为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 导出文件名称：
			// 01普通业务始发月报表表_+账期构成
			try {
				String exportExcelName = "01普通业务始发月报表" + dto.getPeriod() + ".xls";
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
			List<MvrNrfonoEntity> queryList = mvrNrfonoEntityService
					.selectByConditions(dto).getMvrNrfonoEntityList();
			// 查询结果不能为空
			if (CollectionUtils.isEmpty(queryList)) {
				// 提示导出01普通业务始发月报表表期间不能为空
				throw new SettlementException("导出01普通业务始发月报表查询数据为空");
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
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.TWELVE, "数据统计维度"),// 10
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTEEN, SettlementReportNumber.FOURTEEN, "开单运单"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FIFTEEN, SettlementReportNumber.EIGHTEEN, "还款运单总运费（月结临时欠款网上支付）"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.NINETEEN, SettlementReportNumber.TWENTY_TWO, "还款运单总运费（到付）"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_THREE, SettlementReportNumber.FIFTY_SEVEN, "签收运单"),// 35
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_THREE, SettlementReportNumber.TWENTY_NINE, "签收时现付已收款金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY, SettlementReportNumber.THIRTY_SIX, "签收时始发应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_SEVEN, SettlementReportNumber.FORTY_THREE, "签收时始发应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_FOUR, SettlementReportNumber.FIFTY, "签收时到达应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_ONE, SettlementReportNumber.FIFTY_THREE, "签收时到达应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.FIFTY_EIGHT, SettlementReportNumber.NINETY_TWO, "反签收运单"),// 35
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_EIGHT, SettlementReportNumber.SIXTY_FOUR, "反签收时现付已收款金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_FIVE, SettlementReportNumber.SEVENTY_ONE, "反签收时始发应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_TWO, SettlementReportNumber.SEVENTY_EIGHT, "反签收时始发应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_NINE, SettlementReportNumber.EIGHTY_FIVE, "反签收时到达应收已核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_SIX, SettlementReportNumber.NINETY_TWO, "反签收时到达应收未核销金额"),// 7
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIVE, "预收客户"),// 13
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIX, SettlementReportNumber.ONE_HUNDRED_AND_NINE, "代收货款"),// 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TEN, SettlementReportNumber.ONE_HUNDRED_AND_ELEVEN, "偏线代理成本"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWELVE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTEEN, "预收偏线代理"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FOURTEEN, SettlementReportNumber.ONE_HUNDRED_AND_FIFTEEN, "预收空运/快递代理"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTEEN, SettlementReportNumber.ONE_HUNDRED_AND_NINETEEN, "空运/快递代理应付冲应收"), // 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_ONE, "偏线应收冲应付") // 2
			};
			sheet.setHeaderList(Arrays.asList(headerCfg));

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();

			// 设置sheet名称
			String exprotSheetName = mvrNrfonoEntityVo.getMvrNrfonoEntityQueryDto()
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
	 * @author ddw
	 * @date 2013-11-08
	 * @return
	 */
	private String[] getExcelHeads() {

		// 始发应收报表导出数据的表头
		String[] header = {
				// 数据统计维度
				"期间", "运输性质", "客户编码", "客户名称", "始发部门编码", "始发部门名称", "始发部门标杆编码",
				"到达部门编码", "到达部门名称", "到达部门标杆编码", "统一结算类型",  "合同部门编码", "合同部门名称",
				"开单现金", "开单银行卡", "还款现金未签收",
				"还款银行未签收", "还款现金已签收", "还款银行已签收", "还款现金未签收", "还款银行未签收",
				"还款现金已签收", "还款银行已签收", "签收时现付已收款金额_公布价运费", "签收时现付已收款金额_接货费",
				"签收时现付已收款金额_送货费", "签收时现付已收款金额_包装费", "签收时现付已收款金额_代收货款手续费",
				"签收时现付已收款金额_保价费", "签收时现付已收款金额_其它费用", "签收时始发应收已核销金额_公布价运费",
				"签收时始发应收已核销金额_接货费", "签收时始发应收已核销金额_送货费", "签收时始发应收已核销金额_包装费",
				"签收时始发应收已核销金额_代收货款手续费", "签收时始发应收已核销金额_保价费",
				"签收时始发应收已核销金额_其它费用", "签收时始发应收未核销金额_公布价运费", "签收时始发应收未核销金额_接货费",
				"签收时始发应收未核销金额_送货费", "签收时始发应收未核销金额_包装费", "签收时始发应收未核销金额_代收货款手续费",
				"签收时始发应收未核销金额_保价费", "签收时始发应收未核销金额_其它费用", "签收时到达应收已核销金额_公布价运费",
				"签收时到达应收已核销金额_接货费", "签收时到达应收已核销金额_送货费", "签收时到达应收已核销金额_包装费",
				"签收时到达应收已核销金额_代收货款手续费", "签收时到达应收已核销金额_保价费",
				"签收时到达应收已核销金额_其它费用", "签收时到达应收未核销金额_公布价运费", "签收时到达应收未核销金额_接货费",
				"签收时到达应收未核销金额_送货费", "签收时到达应收未核销金额_包装费", "签收时到达应收未核销金额_代收货款手续费",
				"签收时到达应收未核销金额_保价费", "签收时到达应收未核销金额_其它费用", "反签收时现付已收款金额_公布价运费",
				"反签收时现付已收款金额_接货费", "反签收时现付已收款金额_送货费", "反签收时现付已收款金额_包装费",
				"反签收时现付已收款金额_代收货款手续费", "反签收时现付已收款金额_保价费", "反签收时现付已收款金额_其它费用",
				"反签收时始发应收已核销金额_公布价运费", "反签收时始发应收已核销金额_接货费",
				"反签收时始发应收已核销金额_送货费", "反签收时始发应收已核销金额_包装费",
				"反签收时始发应收已核销金额_代收货款手续费", "反签收时始发应收已核销金额_保价费",
				"反签收时始发应收已核销金额_其它费用", "反签收时始发应收未核销金额_公布价运费",
				"反签收时始发应收未核销金额_接货费", "反签收时始发应收未核销金额_送货费", "反签收时始发应收未核销金额_包装费",
				"反签收时始发应收未核销金额_代收货款手续费", "反签收时始发应收未核销金额_保价费",
				"反签收时始发应收未核销金额_其它费用", "反签收时到达应收已核销金额_公布价运费",
				"反签收时到达应收已核销金额_接货费", "反签收时到达应收已核销金额_送货费", "反签收时到达应收已核销金额_包装费",
				"反签收时到达应收已核销金额_代收货款手续费", "反签收时到达应收已核销金额_保价费",
				"反签收时到达应收已核销金额_其它费用", "反签收时到达应收未核销金额_公布价运费",
				"反签收时到达应收未核销金额_接货费", "反签收时到达应收未核销金额_送货费", "反签收时到达应收未核销金额_包装费",
				"反签收时到达应收未核销金额_代收货款手续费", "反签收时到达应收未核销金额_保价费",
				"反签收时到达应收未核销金额_其它费用", "预收客户现金", "预收客户银行", "01预收客户冲01应收到付运费未签收",
				"02预收客户冲01应收到付运费未签收", "01预收客户冲01应收到付运费已签收",
				"02预收客户冲01应收到付运费已签收", "01预收客户冲01应收始发运费未签收",
				"01预收客户冲02应收始发运费未签收", "02预收客户冲01应收始发运费未签收",
				"01预收客户冲01应收始发运费已签收", "01预收客户冲02应收始发运费已签收",
				"02预收客户冲01应收始发运费已签收", "01始发退预收付款申请", "应付代收货款冲01应收到付运费已签收",
				"应付代收货款冲01应收到付运费未签收", "应付代收货款冲01应收始发运费已签收",
				"应付代收货款冲01应收始发运费未签收", "应付偏线代理成本冲01应收到付运费已签收",
				"应付偏线代理成本冲01应收到付运费未签收", "预收偏线代理冲01应收到付运费已签收",
				"预收偏线代理冲01应收到付运费未签收", "预收空运/快递代理冲01应收到付运费已签收",
				"预收空运/快递代理冲01应收到付运费未签收", "应付到达代理/快递代理成本冲01应收到付运费已签收",
				"应付到达代理/快递代理成本冲01应收到付运费未签收", "其他应付冲01应收到付运费已签收",
				"其他应付冲01应收到付运费未签收","其他应付冲01应收到付运费已签收","其他应付冲01应收到付运费未签收" };

		// 返回excel表头
		return header;
	}

	/**
	 * 生成导出始发报表数据
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 * @param queryList
	 * @return
	 */
	private Object[] exportDataAndType(List<MvrNrfonoEntity> queryList) {

		String[] properties = new String[] { "period", "productCode",
				"customerCode", "customerName", "origOrgCode", "origOrgName",
				"origUnifiedCode", "destOrgCode", "destOrgName",
				"destUnifiedCode", "unifiedSettlementType", "contractOrgCode", "contractOrgName", 
				"deoCh", "deoCd", "uroOrigChNpod",
				"uroOrigCdNpod", "uroOrigChPod", "uroOrigCdPod",
				"uroDestChNpod", "uroDestCdNpod", "uroDestChPod",
				"uroDestCdPod", "podoCashCollectedFrt", "podoCashCollectedPup",
				"podoCashCollectedDel", "podoCashCollectedPkg",
				"podoCashCollectedCod", "podoCashCollectedDv",
				"podoCashCollectedOt", "podoOrigRcvWoFrt", "podoOrigRcvWoPup",
				"podoOrigRcvWoDel", "podoOrigRcvWoPkg", "podoOrigRcvWoCod",
				"podoOrigRcvWoDv", "podoOrigRcvWoOt", "podoOrigRcvNwoFrt",
				"podoOrigRcvNwoPup", "podoOrigRcvNwoDel", "podoOrigRcvNwoPkg",
				"podoOrigRcvNwoCod", "podoOrigRcvNwoDv", "podoOrigRcvNwoOt",
				"podoDestRcvWoFrt", "podoDestRcvWoPup", "podoDestRcvWoDel",
				"podoDestRcvWoPkg", "podoDestRcvWoCod", "podoDestRcvWoDv",
				"podoDestRcvWoOt", "podoDestRcvNwoFrt", "podoDestRcvNwoPup",
				"podoDestRcvNwoDel", "podoDestRcvNwoPkg", "podoDestRcvNwoCod",
				"podoDestRcvNwoDv", "podoDestRcvNwoOt", "updoCashCollectedFrt",
				"updoCashCollectedPup", "updoCashCollectedDel",
				"updoCashCollectedPkg", "updoCashCollectedCod",
				"updoCashCollectedDv", "updoCashCollectedOt",
				"updoOrigRcvWoFrt", "updoOrigRcvWoPup", "updoOrigRcvWoDel",
				"updoOrigRcvWoPkg", "updoOrigRcvWoCod", "updoOrigRcvWoDv",
				"updoOrigRcvWoOt", "updoOrigRcvNwoFrt", "updoOrigRcvNwoPup",
				"updoOrigRcvNwoDel", "updoOrigRcvNwoPkg", "updoOrigRcvNwoCod",
				"updoOrigRcvNwoDv", "updoOrigRcvNwoOt", "updoDestRcvWoFrt",
				"updoDestRcvWoPup", "updoDestRcvWoDel", "updoDestRcvWoPkg",
				"updoDestRcvWoCod", "updoDestRcvWoDv", "updoDestRcvWoOt",
				"updoDestRcvNwoFrt", "updoDestRcvNwoPup", "updoDestRcvNwoDel",
				"updoDestRcvNwoPkg", "updoDestRcvNwoCod", "updoDestRcvNwoDv",
				"updoDestRcvNwoOt", "custDrOch", "custDrOcd",
				"custDroWoDestRcvoNpod", "custDrtWoDestRcvoNpod",
				"custDroWoDestRcvoPod", "custDrtWoDestRcvoPod",
				"custDroWoOrigRcvoNpod", "custDroWoOrigRcvtNpod",
				"custDrtWoOrigRcvoNpod", "custDroWoOrigRcvoPod",
				"custDroWoOrigRcvtPod", "custDrtWoOrigRcvoPod",
				"custDroPayApply", "codPayWoDestRcvoPod",
				"codPayWoDestRcvoNpod", "codPayWoOrigRcvoPod",
				"codPayWoOrigRcvoNpod", "plCostWoDestRcvoPod",
				"plCostWoDestRcvoNpod", "plDrWoDestRcvoPod",
				"plDrWoDestRcvoNpod", "alDrWoDestRcvoPod",
				"alDrWoDestRcvoNpod", "alpwrWoDestRcvoPod",
				"alpwrWoDestRcvoNpod", "othPayWoDestRcvoPod",
				"othPayWoDestRcvoNpod","popWoDroPod","popWoDroNpod" };

		List<String> strProperties = Arrays.asList(new String[] { "period",
				"productCode", "customerCode", "customerName", "origOrgCode",
				"origOrgName", "origUnifiedCode", "destOrgCode", "destOrgName",
				"destUnifiedCode", "unifiedSettlementType", "contractOrgCode", "contractOrgName" });

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
		for (MvrNrfonoEntity entity : queryList) {

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
	 * @return mvrNrfonoEntityVo
	 */
	public MvrNrfonoEntityVo getMvrNrfonoEntityVo() {
		return mvrNrfonoEntityVo;
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
	 * @param mvrNrfonoEntityService
	 */
	public void setMvrNrfonoEntityService(IMvrNrfonoEntityService mvrNrfonoEntityService) {
		this.mvrNrfonoEntityService = mvrNrfonoEntityService;
	}

	/**
	 * @param mvrNrfonoEntityVo
	 */
	public void setMvrNrfonoEntityVo(MvrNrfonoEntityVo mvrNrfonoEntityVo) {
		this.mvrNrfonoEntityVo = mvrNrfonoEntityVo;
	}

}
