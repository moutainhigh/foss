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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfosoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfosoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNrfosoEntityVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01特殊业务始发月报表
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfosoAction extends AbstractAction {

	/**
	 * 01特殊业务始发月报表action序列号
	 */
	private static final long serialVersionUID = -7990595546324470754L;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger(MvrNrfosoAction.class);

	/**
	 * 始发应收Vo
	 */
	private MvrNrfosoEntityVo mvrNrfosoEntityVo;

	/**
	 * 始发应收service
	 * 
	 */
	private IMvrNrfosoEntityService mvrNrfosoEntityService;

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
	 * 查询01特殊业务始发月报表信息
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 */
	@JSON
	public String queryMvrNrfoso() {

		try {

			MvrNrfosoEntityQueryDto dto = mvrNrfosoEntityVo.getMvrNrfosoEntityQueryDto();
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 查询01特殊业务始发月报表信息
			// param：参数dto，分页数据
			MvrNrfosoEntityResultDto mvrNrfosoEntityResultDto = mvrNrfosoEntityService
					.selectByConditionsAndPage(dto, this.getStart(),
							this.getLimit());

			// 设置返回值
			mvrNrfosoEntityVo.setMvrNrfosoEntityResultDto(mvrNrfosoEntityResultDto);
			// 设置条数
			this.setTotalCount(mvrNrfosoEntityResultDto.getMvrNrfosoEntityTotalRows());

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
	public String exportMvrNrfoso() {

		try {

			// 导出01特殊业务始发月报表表参数不能为空
			if (mvrNrfosoEntityVo == null) {
				// 提示查询参数不能为空
				throw new SettlementException("查询参数不能为空");
			}

			MvrNrfosoEntityQueryDto dto = mvrNrfosoEntityVo.getMvrNrfosoEntityQueryDto();

			// 导出01特殊业务始发月报表表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				// 提示导出01特殊业务始发月报表表期间不能为空
				throw new SettlementException("导出01特殊业务始发月报表期间不能为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 导出文件名称：
			// 01特殊业务始发月报表表_+账期构成
			try {
				String exportExcelName = "01特殊业务始发月报表" + dto.getPeriod() + ".xls";
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
			List<MvrNrfosoEntity> queryList = mvrNrfosoEntityService
					.selectByConditions(dto).getMvrNrfosoEntityList();
			// 查询结果不能为空
			if (CollectionUtils.isEmpty(queryList)) {
				// 提示导出01特殊业务始发月报表表期间不能为空
				throw new SettlementException("导出01特殊业务始发月报表查询数据为空");
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
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTEEN, SettlementReportNumber.TWENTY_FIVE, "预收客户"),// 13
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_SIX, SettlementReportNumber.TWENTY_SEVEN, "偏线代理成本"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_EIGHT, SettlementReportNumber.TWENTY_NINE, "预收偏线代理"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY, SettlementReportNumber.THIRTY_ONE, "预收空运/快递代理"), // 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_TWO, SettlementReportNumber.THIRTY_FIVE, "空运/快递代理应付冲应收"), // 4
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.THIRTY_SIX, SettlementReportNumber.FORTY_NINE, "理赔"),// 14
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_SIX, SettlementReportNumber.FORTY_FOUR, "出发部门申请"),// 9
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_FIVE, SettlementReportNumber.FORTY_NINE, "到达部门申请"),// 5
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY, SettlementReportNumber.FIFTY_ONE, "异常冲收入"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_TWO, SettlementReportNumber.FIFTY_THREE, "坏账损失"),// 2
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.FIFTY_FOUR, SettlementReportNumber.SIXTY_SEVEN, "小票"),// 14
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_FOUR, SettlementReportNumber.FIFTY_FOUR, "小票录入现金"),// 1
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_FIVE, SettlementReportNumber.FIFTY_FIVE, "小票录入银行"),// 1
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_SIX, SettlementReportNumber.FIFTY_SIX, "小票录入应收"),// 1
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_SEVEN, SettlementReportNumber.FIFTY_SEVEN, "小票应收核销"),// 11
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_EIGHT, SettlementReportNumber.SEVENTY, "弃货、违禁品、全票丢货"),// 3
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.SEVENTY_ONE, SettlementReportNumber.EIGHTY_FOUR, "退运费"),// 14
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_ONE, SettlementReportNumber.SEVENTY_NINE, "出发部门申请"),// 9
					new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY, SettlementReportNumber.EIGHTY_FOUR, "到达部门申请"),// 5
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_FIVE, SettlementReportNumber.EIGHTY_FIVE, "装卸费"),// 1
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_SIX, SettlementReportNumber.EIGHTY_SIX, "服务补救"),// 1
					new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_SEVEN, SettlementReportNumber.EIGHTY_EIGHT, "偏线应收冲应付") // 2
			};
			sheet.setHeaderList(Arrays.asList(headerCfg));

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();

			// 设置sheet名称
			String exprotSheetName = mvrNrfosoEntityVo.getMvrNrfosoEntityQueryDto()
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
				"预收客户现金", "预收客户银行", "01预收客户冲01应收到付运费未签收",
				"02预收客户冲01应收到付运费未签收", "01预收客户冲01应收到付运费已签收",
				"02预收客户冲01应收到付运费已签收", "01预收客户冲01应收始发运费未签收",
				"01预收客户冲02应收始发运费未签收", "02预收客户冲01应收始发运费未签收",
				"01预收客户冲01应收始发运费已签收", "01预收客户冲02应收始发运费已签收",
				"02预收客户冲01应收始发运费已签收", "01始发退预收付款申请", "应付偏线代理成本冲01应收到付运费已签收",
				"应付偏线代理成本冲01应收到付运费未签收", "预收偏线代理冲01应收到付运费已签收",
				"预收偏线代理冲01应收到付运费未签收", "预收空运/快递代理冲01应收到付运费已签收",
				"预收空运/快递代理冲01应收到付运费未签收", "应付到达代理/快递代理成本冲01应收到付运费已签收",
				"应付到达代理/快递代理成本冲01应收到付运费未签收", "其他应付冲01应收到付运费已签收",
				"其他应付冲01应收到付运费未签收", "01理赔冲收入", "01理赔入成本",
				"01理赔冲01始发应收已签收", "01理赔冲02始发应收已签收", "02理赔冲01始发应收已签收",
				"01理赔冲01始发应收未签收", "01理赔冲02始发应收未签收", "02理赔冲01始发应收未签收",
				"01理赔付款申请", "01理赔冲收入", "01理赔冲到01达应收已签收", "02理赔冲01到达应收已签收",
				"01理赔冲01到达应收未签收", "02理赔冲01到达应收未签收", "01应收始发运费已签收",
				"01应收到付运费已签收", "坏账冲01应收始发运费已签收", "坏账冲01应收到付运费已签收",
				"01小票现金主营业务收入", "01小票银行主营业务收入", "01小票应收主营业务收入", "还款现金冲01小票应收",
				"还款银行冲01小票应收", "应付代收货款冲01小票应收", "01应付理赔冲01小票应收",
				"01应付理赔冲02小票应收", "02应付理赔冲01小票应收", "01预收客户冲01小票应收",
				"01预收客户冲02小票应收", "02预收客户冲01小票应收", "异常冲收入冲01小票应收",
				"坏账损失冲01小票应收", "开单且为月结临时欠款网上支付未核销", "开单且为月结临时欠款网上支付已核销",
				"开单且为现金银行卡", "01退运费冲收入", "01退运费入成本", "01退运费付款申请",
				"01退运费冲01始发应收已签收", "01退运费冲02始发应收已签收", "02退运费冲01始发应收已签收",
				"01退运费冲01始发应收未签收", "01退运费冲02始发应收未签收", "02退运费冲01始发应收未签收",
				"01退运费冲收入", "01退运费冲01到达应收已签收", "02退运费冲01到达应收已签收",
				"01退运费冲01到达应收未签收", "02退运费冲01到达应收未签收", "01装卸费付款申请",
				"01始发服务补救付款申请","其他应付冲01应收到付运费已签收","其他应付冲01应收到付运费未签收" };

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
	private Object[] exportDataAndType(List<MvrNrfosoEntity> queryList) {

		String[] properties = new String[] { "period", "productCode",
				"customerCode", "customerName", "origOrgCode", "origOrgName",
				"origUnifiedCode", "destOrgCode", "destOrgName",
				"destUnifiedCode", "unifiedSettlementType", "contractOrgCode", "contractOrgName", 
				"custDrOch", "custDrOcd",
				"custDroWoDestRcvoNpod", "custDrtWoDestRcvoNpod",
				"custDroWoDestRcvoPod", "custDrtWoDestRcvoPod",
				"custDroWoOrigRcvoNpod", "custDroWoOrigRcvtNpod",
				"custDrtWoOrigRcvoNpod", "custDroWoOrigRcvoPod",
				"custDroWoOrigRcvtPod", "custDrtWoOrigRcvoPod",
				"custDroPayApply","plCostWoDestRcvoPod",
				"plCostWoDestRcvoNpod", "plDrWoDestRcvoPod",
				"plDrWoDestRcvoNpod", "alDrWoDestRcvoPod",
				"alDrWoDestRcvoNpod", "alpwrWoDestRcvoPod",
				"alpwrWoDestRcvoNpod", "othPayWoDestRcvoPod",
				"othPayWoDestRcvoNpod", "claimOrigoIncome", "claimOrigoCost",
				"claimOrigoWoOrigRcvoPod", "claimOrigoWoOrigRcvtPod",
				"claimOrigtOrigRcvoPod", "claimOrigoOrigRcvoNpod",
				"claimOrigoWoOrigRcvtNpod", "claimOrigtWoOrigRcvoNpod",
				"claimOrigoPayApply", "claimDestoIncome",
				"claimDestoWoDestRcvoPod", "claimDesttWoDestRcvoPod",
				"claimDestoWoDestRcvoNpod", "claimDesttWoDestRcvoNpod",
				"exOrigRcvoPod", "exDestRcvoPod", "bdWoOrigRcvoPod",
				"bdWoDestRcvoPod", "orChPbio", "orCdPbio", "orRcvoPbi",
				"orChUrRcvo", "orCdUrRcvo", "orCodPayWoRcvo",
				"orClaimPayoWoRcvo", "orClaimPayoWoRcvt", "orClaimPaytWoRcvo",
				"orCustDroWoRcvo", "orCustDroWoRcvt", "orCustDrtWoRcvo",
				"orExWoRcvo", "orBadWoRcvo", "acOrigRcvNwo", "acOrigRcvWo",
				"acCash", "rdOrigoIncome", "rdOrigoCost", "rdOrigoPayApply",
				"rdOrigoWoOrigRcvoPod", "rdOrigoWoOrigRcvtPod",
				"rdOrigtWoOrigRcvoPod", "rdOrigoWoOrigRcvoNpod",
				"rdOrigoWoOrigRcvtNpod", "rdOrigtWoOrigRcvoNpod",
				"rdDestoIncome", "rdDestoDestRcvoPod", "rdDesttWoDestRcvoPod",
				"rdDestoWoDestRcvoNpod", "rdDesttWoDestRcvoNpod",
				"sfoPayApply", "cpoOrigPayApply","popWoDroPod","popWoDroNpod" };

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
		for (MvrNrfosoEntity entity : queryList) {

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
	 * @return mvrNrfosoEntityVo
	 */
	public MvrNrfosoEntityVo getMvrNrfosoEntityVo() {
		return mvrNrfosoEntityVo;
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
	 * @param mvrNrfosoEntityService
	 */
	public void setMvrNrfosoEntityService(IMvrNrfosoEntityService mvrNrfosoEntityService) {
		this.mvrNrfosoEntityService = mvrNrfosoEntityService;
	}

	/**
	 * @param mvrNrfosoEntityVo
	 */
	public void setMvrNrfosoEntityVo(MvrNrfosoEntityVo mvrNrfosoEntityVo) {
		this.mvrNrfosoEntityVo = mvrNrfosoEntityVo;
	}

}
