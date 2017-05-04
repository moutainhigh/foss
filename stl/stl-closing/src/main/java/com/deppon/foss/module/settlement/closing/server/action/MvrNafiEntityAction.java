package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNafiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNafiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNafiDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNafiVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 空运往来月报表Action.
 * 
 * @author guxinhua
 * @date 2013-3-6 下午3:37:31
 */
public class MvrNafiEntityAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrNafiEntityAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 空运往来月报表Service. */
	private IMvrNafiEntityService mvrNafiEntityService;

	/**
	 * 产品productService
	 */
	private IProductService productService;

	/** 空运往来月报表VO. */
	private MvrNafiVo mvrNafiVo;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;   


	/**
	 * 查询专线到达.
	 * 
	 * @return the string
	 * @author guxinhua
	 * @date 2013-3-6 下午3:39:28
	 */
	@JSON
	public String query() {

		try {
			// 检查并封装查询条件
			MvrNafiDto dto = this.checkAndPackageVoToDto(mvrNafiVo);

			// 查询空运往来月报表合计
			MvrNafiDto totalDto = mvrNafiEntityService
					.selectTotalByConditions(dto);

			// 空运往来月报表集合
			List<MvrNafiEntity> mvrNafiEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询空运往来月报表
				mvrNafiEntityList = mvrNafiEntityService.selectByConditions(dto,
						start, limit);
				
				totalDto.setPeriod("汇总");
				totalDto.setInvoiceMark("总条数:"+totalDto.getCount());
				mvrNafiEntityList.add(totalDto);
			}

			// 设置Vo的统计Dto
			mvrNafiVo.setMvrNafiDto(totalDto);
			// 设置Vo的报表集合
			mvrNafiVo.setMvrNafiEntityList(mvrNafiEntityList);
		} catch (SettlementException e) {
			// 查询空运往来月报表异常
			LOGGER.error("查询空运往来月报表异常：" + e.getErrorCode(), e);
			return this.returnError(e.getErrorCode());
		}

		return this.returnSuccess();
	}

	/**
	 * 空运往来月报表导出
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午3:27:30
	 * @return
	 */
	public String export() {

		try {
			// 检查并封装查询条件
			MvrNafiDto dto = this.checkAndPackageVoToDto(mvrNafiVo);

			// 查询空运往来月报表合计
			MvrNafiDto totalDto = mvrNafiEntityService
					.selectTotalByConditions(dto);

			// 空运往来月报表集合
			List<MvrNafiEntity> mvrNafiEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询空运往来月报表
				mvrNafiEntityList = mvrNafiEntityService.selectByConditions(dto,
						0, totalDto.getCount().intValue());
			} else {
				// 提示导出空运往来月报表不能为空
				throw new SettlementException(mvrNafiVo.getPeriod()
						+ "账期的空运往来月报表数据为空!");
			}

			// 导出文件名称：
			// 空运往来月报表_+账期构成
			try {
				String exportExeclName = "空运往来月报表" + mvrNafiVo.getPeriod();				
				
				// 转化编码
				this.setExeclName(new String((exportExeclName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(mvrNafiEntityList);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName(mvrNafiVo.getPeriod());

			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int i = SettlementReportNumber.SEVEN; i <= SettlementReportNumber.THIRTY_FIVE; i++) {
				map.put(i, "float");
			}

			exportSetting.setDataType(map);

			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,
					exportSetting));

			return this.returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("空运往来月报表导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("空运往来月报表导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("空运往来月报表导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("空运往来月报表导出异常：" + e.getMessage());
			// 异常处理
		}
	}

	/**
	 * 获得execl报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrNafiEntityList
	 */
	private ExportResource exportRfdResource(List<MvrNafiEntity> mvrNafiEntityList) {

		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());
		// 处理返回的结果集
		List<List<String>> resultList = this.exportMvrNafi(mvrNafiEntityList);

		sheet.setHeaderHeight(SettlementReportNumber.THREE);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.SIX, "数据统计维度"); // 7
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.SEVEN, SettlementReportNumber.FOURTEEN, "空运应付冲应收"); // 8
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FIFTEEN, SettlementReportNumber.TWENTY_TWO, "空运代收货款"); // 8
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_THREE, SettlementReportNumber.TWENTY_SEVEN, "预收空运代理"); // 5
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_EIGHT, SettlementReportNumber.THIRTY_ONE, "还款运单总运费（到付）【01】"); // 4
		HeaderRows rowContent6 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_TWO, SettlementReportNumber.THIRTY_FIVE, "还款运单总运费（到付）【02】"); // 4
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
		headerList.add(rowContent6);
		sheet.setHeaderList(headerList);

		// 设置表单数据
		sheet.setRowList(resultList);

		return sheet;
	}

	/**
	 * 空运往来月报表表头
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:39
	 * @return
	 */
	private String[] getExcelHeads() {
		return new String[] {
				"期间",
				"发票标记",
				//"产品类型",
				"客户编码",
				"客户名称",
				"部门类型",
				"部门编码",
				"部门名称",
				"应付到达代理成本冲01应收到付运费已签收",
				"应付到达代理成本冲01应收到付运费未签收",
				"其他应付冲01应收到付运费已签收",
				"其他应付冲01应收到付运费未签收",
				"应付到达代理成本冲02应收到付运费已签收",
				"应付到达代理成本冲02应收到付运费未签收",
				"其他应付冲02应收到付运费已签收",
				"其他应付冲02应收到付运费未签收",
				"空运签收时未核销代收货款",
				"空运反签收时未核销代收货款",
				"空运还款代收货款现金已签收",
				"空运还款代收货款银行已签收",
				"空运签收时已核销代收货款",
				"空运反签收时已核销代收货款",
				"空运到达代理应付冲应收代收货款已签收",
				"空运其他应付冲应收代收货款已签收",
				"预收空运代理冲02应收到付运费已签收",
				"预收空运代理冲02应收到付运费未签收",
				"预收空运代理冲01应收到付运费已签收",
				"预收空运代理冲01应收到付运费未签收",
				"预收空运代理冲应收代收货款已签收",
				"还款现金未签收",
				"还款银行未签收",
				"还款现金已签收",
				"还款银行已签收",
				"还款现金未签收",
				"还款银行未签收",
				"还款现金已签收",
				"还款银行已签收" };
	}

	/**
	 * 空运往来月报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param mvrNafiEntityList
	 * @return
	 */
	private List<List<String>> exportMvrNafi(List<MvrNafiEntity> mvrNafiEntityList) {

		String[] columns = {
				"period", 
				"invoiceMark",
				//"productCode",
				"customerCode",
				"customerName",
				"orgType",
				"orgCode",
				"orgName",
				"apwrCostWoDestRcvoPod",
				"apwrCostWoDestRcvoNpod",
				"apwrOthPayWoDestRcvoPod",
				"apwrOthPayWoDestRcvoNpod",
				"apwrCostWoDestRcvtPod",
				"apwrCostWoDestRcvtNpod",
				"apwrOthPayWoDestRcvtPod",
				"apwrOthPayWoDestRcvtNpod",
				"airCodPodNwo",
				"airCodUpdNwo",
				"airCodChUrPod",
				"airCodCdUrPod",
				"airCodPodWo",
				"airCodUpdWo",
				"airCodDpayWoCodRcvPod",
				"airCodOthPayWoCodRcvPod",
				"airDrWoDestRcvtPod",
				"airDrWoDestRcvtNpod",
				"airDrWoDestRcvoPod",
				"airDrWoDestRcvoNpod",
				"airDrWoCodRcvPod",
				"uroaDestChNpod",
				"uroaDestCdNpod",
				"uroaDestChPod",
				"uroaDestCdPod",
				"urtaDestChNpod",
				"urtaDestCdNpod",
				"urtaDestChPod",
				"urtaDestCdPod"
			};

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

		if (CollectionUtils.isNotEmpty(mvrNafiEntityList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrNafiEntity entity : mvrNafiEntityList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
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
					}
					if(StringUtils.equals(column, "orgType")){
						if(cellValue.equals(SettlementDictionaryConstants.VOUCHER_ORG_TYPE_ORIG)){
							cellValue = "始发";
						}else if(cellValue.equals(SettlementDictionaryConstants.VOUCHER_ORG_TYPE_DEST)){
							cellValue = "到达";
						}
					}
					if(StringUtils.equals(column, "invoiceMark")){
						if(cellValue.equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)){
							cellValue = "01-运输专票11%";
						}else if(cellValue.equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)){
							cellValue = "02-非运输专票";
						}
					}
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}

		return rowList;
	}

	/**
	 * 检查并封装查询条件.
	 * 
	 * @param vo
	 *            the vo
	 * @return the mvr rfd dto
	 * @author guxinhua
	 * @date 2013-3-6 上午11:44:51
	 */
	private MvrNafiDto checkAndPackageVoToDto(MvrNafiVo vo) {

		// 专线到达VO非空判断
		if (null == vo) {
			// 内部错误，专线到达VO参数为空！
			throw new SettlementException("内部错误，专线到达VO参数为空！");
		}

		// 统计期间非空判断
		if (StringUtil.isBlank(vo.getPeriod())) {
			// 统计期间不能为空！
			throw new SettlementException("统计期间不能为空！");
		}

		MvrNafiDto dto = new MvrNafiDto();
		try {
			// 把vo上属性拷贝到dto上
			BeanUtils.copyProperties(dto, vo);

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

		} catch (IllegalAccessException e) {
			// BeanUtils.copyProperties异常
			LOGGER.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			// BeanUtils.copyProperties异常
			LOGGER.error(e.getMessage(), e);
		}

		return dto;
	}

	/**
	 * Gets the mvr rfd vo.
	 * 
	 * @return mvrNafiVo
	 */
	public MvrNafiVo getMvrNafiVo() {
		return mvrNafiVo;
	}

	/**
	 * Sets the mvr rfd vo.
	 * 
	 * @param mvrNafiVo
	 *            the new mvr rfd vo
	 */
	public void setMvrNafiVo(MvrNafiVo mvrNafiVo) {
		this.mvrNafiVo = mvrNafiVo;
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
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * @param mvrNafiEntityService the mvrNafiEntityService to set
	 */
	public void setMvrNafiEntityService(
			IMvrNafiEntityService mvrNafiEntityService) {
		this.mvrNafiEntityService = mvrNafiEntityService;
	}

	
}
