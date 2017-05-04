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
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrSnrfiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 始发专线往来月报表（特殊）Action.
 * 
 * @author shenweihua
 * @date 2014-05-29 下午3:37:31
 */
public class MvrSnrfiEntityAction extends AbstractAction{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrSnrfiEntityAction.class);
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** 始发专线往来月报表Service. */
	private IMvrSnrfiEntityService mvrSnrfiEntityService;

	/**
	 * 产品productService
	 */
	private IProductService productService;

	/** 始发专线往来月报表DTO. */
	private MvrNrfiDto mvrNrfiDto;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String excelName;
	
	private static final String TERMS_CODE_INVOICE_MARK = "SETTLEMENT_INVOICE_MARK";
	private static final String TERMS_CODE_ORG_TYPE = "VOUCHER__ORG_TYPE";
	
	/**
	 * 查询始发专线往来.
	 * 
	 * @return the string
	 * @author shenweihua
	 * @date 2014-5-29 下午3:39:28
	 */
	@JSON
	public String queryMvrSnrfi() {

		try {

			// 查询始发专线往来月报表（特殊）合计
			MvrNrfiEntity total = mvrSnrfiEntityService
					.selectTotalByConditions(mvrNrfiDto);

			// 总条数>0时查询
			if (null != total && total.getCount() > 0) {
				// 查询始发专线往来月报表（特殊）
				List<MvrNrfiEntity> resultList = mvrSnrfiEntityService
						.selectColumnsByConditions(mvrNrfiDto, start, limit);

				total.setCustomerCode("汇总");
				total.setCustomerName("总条数:" + total.getCount());
				resultList.add(total);
				mvrNrfiDto.setList(resultList);
				this.setTotalCount(total.getCount());
			}
		} catch (SettlementException e) {
			// 查询始发专线往来月报表异常
			LOGGER.error("查询始发专线往来月报表（特殊）异常：" + e.getErrorCode(), e);
			return this.returnError(e.getErrorCode());
		}

		return this.returnSuccess();
	}

	/**
	 * 始发偏线往来月报表（特殊）导出
	 * 
	 * @author shenweihua
	 * @date 2014-5-29 下午3:27:30
	 * @return
	 */
	public String exportMvrSnrfi() {

		try {

			List<MvrNrfiEntity> result = this.mvrSnrfiEntityService
					.selectColumnsByConditions(mvrNrfiDto, 0, Integer.MAX_VALUE);

			if (result == null) {
				throw new SettlementException(mvrNrfiDto.getPeriod()
						+ "账期的始发专线往来月报表数据为空!");
			}

			// 导出文件名称：
			try {
				String exportExeclName = "始发专线往来月报表（特殊）" + mvrNrfiDto.getPeriod();

				// 转化编码
				this.setExcelName(new String((exportExeclName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}

			// 生成导出数据源类
			ExportResource sheet = this.exportNRfiResource(result);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName(mvrNrfiDto.getPeriod());

			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int i = SettlementReportNumber.SIX; i < getExcelHeads().length; i++) {
				map.put(i, "float");
			}

			exportSetting.setDataType(map);

			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,
					exportSetting));

			return this.returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("始发专线往来月报表（特殊）导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("始发专线往来月报表（特殊）导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("始发专线往来月报表（特殊）导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("始发专线往来月报表（特殊）导出异常：" + e.getMessage());
			// 异常处理
		}
	}

	/**
	 * 获得execl报表数据
	 * 
	 * @author shenweihua
	 * @date 2014-5-29 下午6:42:11
	 * @param mvrNpliEntityList
	 */
	private ExportResource exportNRfiResource(List<MvrNrfiEntity> list) {

		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());
		// 处理返回的结果集
		List<List<String>> resultList = this.exportMvrNrfi(list);

		sheet.setHeaderHeight(SettlementReportNumber.THREE);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.FIVE, "数据统计维度"); // 6
		HeaderRows rowContent6 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.SIX, SettlementReportNumber.TWENTY_ONE, "理赔"); // 16
		HeaderRows rowContent6_1 = new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIX, SettlementReportNumber.TEN, "出发部门申请"); // 5
		HeaderRows rowContent6_2 = new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ELEVEN, SettlementReportNumber.TWENTY_ONE, "到达部门申请"); // 11
		HeaderRows rowContent7 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_TWO, SettlementReportNumber.THIRTY_SIX, "预收客户"); // 15
		HeaderRows rowContent8 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_SEVEN, SettlementReportNumber.FORTY_FOUR, "小票"); // 8
		HeaderRows rowContent10 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.FORTY_FIVE, SettlementReportNumber.SIXTY, "退运费"); // 16
		HeaderRows rowContent10_1 = new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_FIVE, SettlementReportNumber.FORTY_NINE, "出发部门申请"); // 5
		HeaderRows rowContent10_2 = new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY, SettlementReportNumber.SIXTY, "到达部门申请"); // 11
		HeaderRows rowContent11 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_ONE, SettlementReportNumber.SIXTY_ONE, "装卸费"); // 1
		HeaderRows rowContent12 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_TWO, SettlementReportNumber.SIXTY_THREE, "服务补救"); // 2
		headerList.add(rowContent1);
		headerList.add(rowContent6);
		headerList.add(rowContent6_1);
		headerList.add(rowContent6_2);
		headerList.add(rowContent7);
		headerList.add(rowContent8);
		headerList.add(rowContent10);
		headerList.add(rowContent10_1);
		headerList.add(rowContent10_2);
		headerList.add(rowContent11);
		headerList.add(rowContent12);
		sheet.setHeaderList(headerList);

		// 设置表单数据
		sheet.setRowList(resultList);

		return sheet;
	}

	/**
	 * 始发专线往来月报表表头
	 * 
	 * @author shenweihua
	 * @date 2014-05-29 下午5:28:39
	 * @return
	 */
	private String[] getExcelHeads() {
		return new String[] { "期间", "发票标记", "产品类型", "部门类型", "部门编码", "部门名称",
				"出发部门申请-01理赔冲02始发应收已签收",
				"出发部门申请-02理赔冲01始发应收已签收", "出发部门申请-01理赔冲02始发应收未签收",
				"出发部门申请-02理赔冲01始发应收未签收", "出发部门申请-01理赔付款申请", "到达部门申请-01理赔冲收入",
				"到达部门申请-01理赔付款申请", "到达部门申请-02理赔冲收入", "到达部门申请-01理赔冲01到达应收已签收",
				"到达部门申请-01理赔冲02到达应收已签收", "到达部门申请-02理赔冲01到达应收已签收",
				"到达部门申请-02理赔冲02到达应收已签收", "到达部门申请-01理赔冲01到达应收未签收",
				"到达部门申请-02理赔冲01到达应收未签收", "到达部门申请-01理赔冲02到达应收未签收",
				"到达部门申请-02理赔冲02到达应收未签收", "预收客户现金", "预收客户银行",
				"01预收客户冲01应收到付运费未签收", "01预收客户冲02应收到付运费未签收",
				"02预收客户冲01应收到付运费未签收", "02预收客户冲02应收到付运费未签收",
				"01预收客户冲01应收到付运费已签收", "01预收客户冲02应收到付运费已签收",
				"02预收客户冲01应收到付运费已签收", "02预收客户冲02应收到付运费已签收",
				"01预收客户冲02应收始发运费未签收", "02预收客户冲01应收始发运费未签收",
				"01预收客户冲02应收始发运费已签收", "02预收客户冲01应收始发运费已签收", "01始发退预收付款申请",
				"01小票现金主营业务收入", "01小票银行主营业务收入", "还款现金冲01小票应收", "还款银行冲01小票应收",
				"01应付理赔冲02小票应收", "02应付理赔冲01小票应收", "01预收客户冲02小票应收",
				"02预收客户冲01小票应收", 
				"出发部门申请-01退运费付款申请", "出发部门申请-01退运费冲02始发应收已签收",
				"出发部门申请-02退运费冲01始发应收已签收", "出发部门申请-01退运费冲02始发应收未签收",
				"出发部门申请-02退运费冲01始发应收未签收", "到达部门申请-01退运费冲收入", "到达部门申请-02退运费冲收入",
				"到达部门申请-01退运费付款申请", "到达部门申请-01退运费冲01到达应收已签收",
				"到达部门申请-02退运费冲01到达应收已签收", "到达部门申请-01退运费冲02到达应收已签收",
				"到达部门申请-02退运费冲02到达应收已签收", "到达部门申请-01退运费冲01到达应收未签收",
				"到达部门申请-02退运费冲01到达应收未签收", "到达部门申请-01退运费冲02到达应收未签收",
				"到达部门申请-02退运费冲02到达应收未签收", "01装卸费付款申请", "01到达服务补救付款申请",
				"01始发服务补救付款申请" };
	}

	/**
	 * 始发专线往来月报表数据
	 * 
	 * @author shenweihua
	 * @date 2014-5-2 下午5:28:48
	 * @param mvrNpliEntityList
	 * @return
	 */
	private List<List<String>> exportMvrNrfi(List<MvrNrfiEntity> list) {

		String[] columns = { "period", "invoiceMark", "productCode", "orgType",
				"orgCode", "orgName", 
				"claimOrigoWoOrigRcvtPod",
				"claimOrigtOrigRcvoPod", "claimOrigoWoOrigRcvtNpod",
				"claimOrigtWoOrigRcvoNpod", "claimOrigoPayApply",
				"claimDestoIncome", "claimDestoPayApply", "claimDesttIncome", 
				"claimDestoWoDestRcvoPod", "claimDestoWoDestRcvtPod",
				"claimDesttWoDestRcvoPod", "claimDesttWoDestRcvtPod",
				"claimDestoWoDestRcvoNpod", "claimDesttWoDestRcvoNpod",
				"claimDestoWoDestRcvtNpod", "claimDesttWoDestRcvtNpod",
				"custDrOch", "custDrOcd", "custDroWoDestRcvoNpod",
				"custDroWoDestRcvtNpod", "custDrtWoDestRcvoNpod",
				"custDrtWoDestRcvtNpod", "custDroWoDestRcvoPod",
				"custDroWoDestRcvtPod", "custDrtWoDestRcvoPod",
				"custDrtWoDestRcvtPod", "custDroWoOrigRcvtNpod",
				"custDrtWoOrigRcvoNpod", "custDroWoOrigRcvtPod",
				"custDrtWoOrigRcvoPod", "custDroPayApply", "orChPbio",
				"orCdPbio", "orChUrRcvo", "orCdUrRcvo", "orClaimPayoWoRcvt",
				"orClaimPaytWoRcvo", "orCustDroWoRcvt", "orCustDrtWoRcvo",
				"rdOrigoPayApply", "rdOrigoWoOrigRcvtPod",
				"rdOrigtWoOrigRcvoPod", "rdOrigoWoOrigRcvtNpod",
				"rdOrigtWoOrigRcvoNpod", "rdDestoIncome", "rdDesttIncome",
				"rdDestoPayApply", "rdDestoDestRcvoPod",
				"rdDesttWoDestRcvoPod", "rdDestoDestRcvtPod",
				"rdDesttWoDestRcvtPod", "rdDestoWoDestRcvoNpod",
				"rdDesttWoDestRcvoNpod", "rdDestoWoDestRcvtNpod",
				"rdDesttWoDestRcvtNpod", "sfoPayApply", "cpoDestPayApply",
				"cpoOrigPayApply" };

		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();

		// 获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}

		Map<String, Object> invoiceMarkCache = null;
		Map<String, Map<String, Object>> cache = SettlementUtil
				.getDataDictionaryByTermsCodes(Arrays
						.asList(TERMS_CODE_INVOICE_MARK));
		if (cache != null && CollectionUtils.isNotEmpty(cache.keySet())) {
			invoiceMarkCache = cache.get(TERMS_CODE_INVOICE_MARK);
		}

		Map<String, Object> orgTypeCache = null;
		cache = SettlementUtil.getDataDictionaryByTermsCodes(Arrays
				.asList(TERMS_CODE_ORG_TYPE));
		if (cache != null && CollectionUtils.isNotEmpty(cache.keySet())) {
			orgTypeCache = cache.get(TERMS_CODE_ORG_TYPE);
		}

		if (CollectionUtils.isNotEmpty(list)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrNrfiEntity entity : list) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					cellValue = validaExtracted(productMap, invoiceMarkCache,
							orgTypeCache, entity, column);
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}

		return rowList;
	}

	private String validaExtracted(Map<String, String> productMap,
			Map<String, Object> invoiceMarkCache,
			Map<String, Object> orgTypeCache, MvrNrfiEntity entity,
			String column) {
		Object fieldValue;
		String cellValue;
		fieldValue = ReflectionUtils.getFieldValue(entity, column);
		cellValue = (fieldValue == null ? "" : fieldValue
				.toString());
		if (StringUtils.equals(column, "productCode")) { // 转换productCode值
			if (StringUtils.isNotBlank(cellValue)) {

				// 默认的产品类型为空
				String productEntityName = "";
				// 如果数据产品类型编码不为空
				if (StringUtils.isNotEmpty(cellValue)) {
					// 将产品类型转换编码为名称
					productEntityName = productMap.get(cellValue);
				}
				cellValue = productEntityName;
			}
		} else if (StringUtils.equals(column, "invoiceMark")) {
			if (StringUtils.isNotEmpty(cellValue)) {
				Object value = invoiceMarkCache.get(cellValue);
				if (value != null) {
					cellValue = String.valueOf(value);
				}
			}
		} else if (StringUtils.equals(column, "orgType")) {
			if (StringUtils.isNotEmpty(cellValue)) {
				Object value = orgTypeCache.get(cellValue);
				if (value != null) {
					cellValue = String.valueOf(value);
				}

			}
		}
		return cellValue;
	}
	
	
	public MvrNrfiDto getMvrNrfiDto() {
		return mvrNrfiDto;
	}

	public void setMvrNrfiDto(MvrNrfiDto mvrNrfiDto) {
		this.mvrNrfiDto = mvrNrfiDto;
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

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setMvrSnrfiEntityService(
			IMvrSnrfiEntityService mvrSnrfiEntityService) {
		this.mvrSnrfiEntityService = mvrSnrfiEntityService;
	}
	
}
