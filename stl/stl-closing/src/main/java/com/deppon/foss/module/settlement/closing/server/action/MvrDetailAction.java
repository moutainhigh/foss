package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportInfo;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.IExportListener;
import com.deppon.foss.framework.server.components.export.excel.IResourceListener;
import com.deppon.foss.framework.server.components.ftp.FTPClient;
import com.deppon.foss.framework.server.components.ftp.FTPConfig;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IrecordImportExcelFileInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDetailService;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDCombo;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDTypeMappingEnum;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.VoucherDetailVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * 始发到达往来月报表明细action
 * 
 * @author 045738-foss-maojianqiang
 * @date 2013-4-2 上午10:32:01
 */
public class MvrDetailAction extends AbstractAction {

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MvrDetailAction.class);

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4661468400358714051L;

	/**
	 * 注入service
	 */
	private IMvrDetailService mvrDetailService;

	/**
	 * 产品服务
	 */
	private IProductService productService;

	/**
	 * FTP客户端
	 */
	private FTPClient ftpClient;

	/**
	 * 下载ftp配置
	 */
	private FTPConfig downloadFtpConfig;

	/**
	 * 文件下载服务
	 */
	private IrecordImportExcelFileInfoService recordImportExcelFileInfoService;

	/**
	 * excel名称
	 * */
	private String excelName;

	/**
	 * excel导出流
	 * */
	private InputStream inputStream;

	/**
	 * 声明vo
	 */
	private VoucherDetailVo vo;

	/**
	 * 是否同步导出
	 */
	private boolean syncExport;

	/**
	 * 获取指标大类
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-2 上午10:33:28
	 * @return
	 */
	@JSON
	public String queryType() {
		// 获取始发到达往来月报表明细的指标大类
		List<VDCombo> list = VDTypeMappingEnum.getTypeByRptCode(vo.getDto()
				.getRptType());

		// 设置指标大类
		vo.setComboList(list);

		return SUCCESS;
	}

	/**
	 * 获取指标小类
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-2 上午10:33:28
	 * @return
	 */
	@JSON
	public String querySubType() {

		// 获取获取始发到达往来月报表明细的指标小类列表
		if (StringUtils.isNotBlank(vo.getDto().getRptType())
				&& StringUtils.isNotBlank(vo.getDto().getTypeCode())) {
			List<VDCombo> list = VDTypeMappingEnum.getSubTypeComboByTypeCode(vo
					.getDto().getRptType(), vo.getDto().getTypeCode());
			vo.setComboList(list);
		}

		return SUCCESS;
	}

	/**
	 * 查询报表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-2 下午6:59:36
	 * @return
	 */
	@JSON
	public String queryMvrDetail() {
		try {

			// 校验查询参数
			if (vo == null || vo.getDto() == null) {
				throw new SettlementException("查询参数没有正确注入到后台！");
			}

			VoucherDetailsDto dto = vo.getDto();

			// 校验dto参数
			if (dto.getStartDate() == null) {
				throw new SettlementException("开始日期不能为空！");
			}

			if (dto.getEndDate() == null) {
				throw new SettlementException("结束日期不能为空！");
			}

			// 结束日期加1天
			dto.setEndDate(DateUtils.convert(DateUtils.addDay(dto.getEndDate(),
					1)));
			dto.setStartPeriod(DateUtils.getDay(dto.getStartDate()));
			dto.setEndPeriod(DateUtils.getDay(dto.getEndDate()));
			
			dto.setUserCode(FossUserContext.getCurrentInfo().getEmpCode()); // 设置当前登录用户

			// 调用service查询合计项
			VoucherDetailResultDto resultDto = mvrDetailService
					.queryTotalCounts(vo.getDto());
			// 如果合计项，则表明所有数据，再去查询明细
			if (resultDto != null && resultDto.getCount() > 0) {
				List<VoucherDetailResultDto> resultList = mvrDetailService
						.selectByConditions(vo.getDto(), this.start, this.limit);
				// 判空
				if (CollectionUtils.isNotEmpty(resultList)) {
					resultDto.setProductCode("合计");
					// 添加合计项
					resultList.add(resultDto);
				}
				// 设置结果集
				vo.setResultList(resultList);
				this.setTotalCount((long) resultDto.getCount());
			}
			return returnSuccess();

		} catch (BusinessException e) {
			// 记录错误
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 查询导出数据量大小
	 * 
	 * @return
	 */
	@JSON
	public String exportSynchronized() {
		try {
			VoucherDetailsDto dto = commonValidate();

			// 查询大小
			VoucherDetailResultDto resultDto = mvrDetailService
					.queryTotalCounts(dto);
			if (resultDto != null) {
				int exportListSize = resultDto.getCount();
				this.syncExport = (exportListSize < SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录错误
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 共用私用方法
	 * 
	 * @return
	 */
	private VoucherDetailsDto commonValidate() {
		// 校验查询参数
		if (vo == null || vo.getDto() == null) {
			throw new SettlementException("查询参数没有正确注入到后台！");
		}

		VoucherDetailsDto dto = vo.getDto();

		// 校验dto参数
		if (dto.getStartDate() == null) {
			throw new SettlementException("开始日期不能为空！");
		}

		if (dto.getEndDate() == null) {
			throw new SettlementException("结束日期不能为空！");
		}

		// 报表类型不能为空
		if (StringUtils.isEmpty(dto.getRptType())) {
			throw new SettlementException("报表类型不能为空");
		}

		// 结束日期加1天
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(dto.getEndDate(), 1)));
		dto.setStartPeriod(DateUtils.getDay(dto.getStartDate()));
		dto.setEndPeriod(DateUtils.getDay(dto.getEndDate()));

		dto.setUserCode(FossUserContext.getCurrentInfo().getEmpCode()); // 设置当前登录用户
		dto.setCurrentDeptCode(FossUserContext.getCurrentInfo()
				.getCurrentDeptCode());

		return dto;
	}

	/**
	 * 同步导出
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-8 下午5:01:12
	 * @return
	 */
	public String exportExcelSync() {

		try {

			VoucherDetailsDto dto = commonValidate();

			this.excelName = getExcelName(dto.getRptType());

			try {
//				this.setExcelName(URLEncoder.encode(excelName,
//						SettlementConstants.UNICODE_UTF));
				
				this.setExcelName(new String((excelName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			List<VoucherDetailResultDto> exportList = mvrDetailService
					.selectByConditions(dto, 0,
							SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);

			ExportResource resource = new ExportResource();
			resource.setHeads(this.getExcelHeader());
			resource.setRowList(this.getExportDataList(exportList));

			ExportSetting settings = new ExportSetting();
			settings.setSheetName(dto.getRptType());
			settings.setDataType(this.getDataType());

			// 创建导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			inputStream = objExporterExecutor.exportSync(resource, settings);
			// 正常返回
			return returnSuccess();

		} catch (BusinessException e) {
			// 记录错误
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 异步导出
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-8 下午5:01:12
	 * @return
	 */
	public String exportExcelAsyn() {

		try {

			// 校验数据
			final VoucherDetailsDto dto = commonValidate();

			// 创建导出类
			ExportResource resource = new ExportResource();
			ExporterExecutor executor = new ExporterExecutor();
			final ExportSetting settings = new ExportSetting();

			// 设置表头
			resource.setHeads(this.getExcelHeader());

			// 导出设置
			settings.setFileName(getExcelName(dto.getRptType()));
			settings.setSize(SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
			settings.setDataType(this.getDataType());
			settings.setFileDesc(String.format("%s导出", settings.getFileName()));

			// 导出后结果处理
			IExportListener exportListener = new IExportListener() {
				@Override
				public void doExported(ExportInfo info) {
					DownloadInfoEntity entity = new DownloadInfoEntity();

					String encodedUsername = downloadFtpConfig.getUserName();
					String encodedPassword = downloadFtpConfig.getPassword();

					//对用户名密码加密
					try {
						encodedUsername = URLEncoder.encode(encodedUsername,
								SettlementConstants.UNICODE_UTF);
						encodedPassword = URLEncoder.encode(encodedPassword,
								SettlementConstants.UNICODE_UTF);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						LOG.error(e.getMessage(), e);
					}

					entity.setEmpCode(dto.getUserCode());
					entity.setOrgCode(dto.getCurrentDeptCode());
					entity.setFileName(settings.getFileDesc());
					entity.setFileLoadPath(String.format(
							"ftp://%s:%s@%s/%s/%s", encodedUsername,
							encodedPassword, downloadFtpConfig.getHost(),
							downloadFtpConfig.getPath(),
							info.getRemoteFileName()));
					recordImportExcelFileInfoService
							.saveImportExcelFileInfo(entity);
				}
			};

			// 导出数据来源：包括总分页数和每页数据
			IResourceListener resourceListener = new IResourceListener() {

				@Override
				public int getDataPage() {

					// 查询总条数
					VoucherDetailResultDto resultDto = mvrDetailService
							.queryTotalCounts(dto);

					int size = 0;
					if (resultDto != null) {
						size = resultDto.getCount();
					}

					// 返回总导出页数
					return (size + SettlementConstants.EXPORT_EXCEL_MAX_COUNTS - 1)
							/ SettlementConstants.EXPORT_EXCEL_MAX_COUNTS;
				}

				@Override
				public List<List<String>> getDataList(int page) {

					// 查询当前页数据
					List<VoucherDetailResultDto> exportList = mvrDetailService
							.selectByConditions(
									dto,
									page
											* SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,
									SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);

					// 数据转换成导出格式的数据
					List<List<String>> dataList = getExportDataList(exportList);

					return dataList;
				}

			};

			// 异步导出
			executor.exportAsync(resource, settings, ftpClient, exportListener,
					resourceListener);

			// 正常返回
			return returnSuccess();

		} catch (BusinessException e) {
			// 记录错误
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 获取导出excel表头
	 * 
	 * @return
	 */
	private String[] getExcelHeader() {
		return new String[] { "运输性质", "客户名称", "客户编码", "始发部门名称", "始发部门编码",
				"到达部门名称", "到达部门编码", "指标大类", "指标小类", "运单号", "会计日期", "业务日期",
				"单据类型", "单据子类型", "金额", "运费", "接货费", "送货费", "包装费", "保价费",
				"代收货款手续费", "其他费用" };
	}

	/**
	 * 获取列数据属性
	 * 
	 * @return
	 */
	private Map<Integer, String> getDataType() {
		List<String> amountList = Arrays.asList(new String[] { "运费", "接货费",
				"送货费", "包装费", "保价费", "代收货款手续费", "其他费用" });

		String[] headers = this.getExcelHeader();
		Map<Integer, String> dataType = new HashMap<Integer, String>();
		for (int i = 0; i < headers.length; i++) {
			dataType.put(i, amountList.contains(headers[i]) ? "float"
					: "string");
		}

		return dataType;
	}

	/**
	 * 获取产品编码
	 * 
	 * @return
	 */
	private Map<String, String> getProductMap() {
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

		return productMap;
	}

	/**
	 * 根据编码获取对应MAP
	 * 
	 * @param code
	 * @return
	 */
	private Map<String, String> getDictMap(String code) {

		Map<String, String> map = new HashMap<String, String>();

		DataDictionaryEntity entity = DictUtil.getDataByTermsCode(code);

		if (entity != null) {
			List<DataDictionaryValueEntity> valueList = entity
					.getDataDictionaryValueEntityList();
			if (CollectionUtils.isNotEmpty(valueList)) {

				for (DataDictionaryValueEntity ve : valueList) {
					map.put(ve.getValueCode(), ve.getValueName());
				}

			}
		}

		return map;
	}

	/**
	 * 获取导出数据
	 * 
	 * @param exportList
	 * @return
	 */
	private List<List<String>> getExportDataList(
			List<VoucherDetailResultDto> exportList) {

		List<List<String>> dataList = new ArrayList<List<String>>();
		List<String> data = null;
		Map<String, String> productMap = this.getProductMap();
		Map<String, String> billParentMap = this
				.getDictMap(DictionaryConstants.BILL_PARENT_TYPE);

		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE);// 现金收款单单据类型
		types.add(DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);// 应收单单据子类型
		types.add(DictionaryConstants.BILL_PAYABLE__BILL_TYPE);// 应付单单据子类型
		types.add(DictionaryConstants.BILL_REPAYMENT__BILL_TYPE);// 还款单单据类型
		types.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE);// 预收单单据子类型
		types.add(DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE);// 对账单单据类型
		types.add(DictionaryConstants.BILL_PAYMENT__BILL_TYPE);// 付款单单据类型
		types.add(DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE);// 预付单单据子类型
		// 后台获取数据字典对应的数据
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);

		for (VoucherDetailResultDto dto : exportList) {
			data = new ArrayList<String>();

			/*
			 * "运输性质", "客户名称", "客户编码", "始发部门名称", "始发部门编码", "到达部门名称", "到达部门编码",
			 * "指标大类", "指标小类", "运单号", "会计日期", "业务日期", "单据类型", "单据子类型", "金额",
			 * "运费", "接货费", "送货费", "包装费", "保价费", "代收货款手续费", "其他费用"
			 */
			if (StringUtils.isNotEmpty(dto.getProductCode())) {
				data.add(productMap.get(dto.getProductCode()));
			} else {
				data.add("");
			}
			data.add(dto.getCustomerName());
			data.add(dto.getCustomerCode());
			data.add(dto.getOrigOrgName());
			data.add(dto.getOrigOrgCode());
			data.add(dto.getDestOrgName());
			data.add(dto.getDestOrgCode());
			data.add(dto.getTypeCode());
			data.add(dto.getSubTypeCode());

			data.add(dto.getWaybillNo());

			data.add(dto.getAccountDate() == null ? "" : DateUtils.convert(
					dto.getAccountDate(), DateUtils.DATE_FORMAT));
			data.add(dto.getBusinessDate() == null ? "" : DateUtils.convert(
					dto.getBusinessDate(), DateUtils.DATE_FORMAT));

			validaExtracted(data, billParentMap, map, dto);

			data.add(dto.getAmount() == null ? "0" : dto.getAmount().toString());
			data.add(dto.getAmountFrt() == null ? "0" : dto.getAmountFrt()
					.toString());
			data.add(dto.getAmountPup() == null ? "0" : dto.getAmountPup()
					.toString());
			data.add(dto.getAmountDel() == null ? "0" : dto.getAmountDel()
					.toString());
			data.add(dto.getAmountPkg() == null ? "0" : dto.getAmountPkg()
					.toString());
			data.add(dto.getAmountDv() == null ? "0" : dto.getAmountDv()
					.toString());
			data.add(dto.getAmountCod() == null ? "0" : dto.getAmountCod()
					.toString());
			data.add(dto.getAmountOt() == null ? "0" : dto.getAmountOt()
					.toString());

			dataList.add(data);
		}

		return dataList;
	}

	private void validaExtracted(List<String> data,
			Map<String, String> billParentMap,
			Map<String, Map<String, Object>> map, VoucherDetailResultDto dto) {
		if (StringUtils.isNotEmpty(dto.getBillParentType())) {
			data.add(billParentMap.get(dto.getBillParentType()));

			String billTypeDictionaryStr = "";
			if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__XS)) {
				billTypeDictionaryStr = DictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE;
			} else if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__YS)) {
				billTypeDictionaryStr = DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE;
			} else if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__YF)) {
				billTypeDictionaryStr = DictionaryConstants.BILL_PAYABLE__BILL_TYPE;
			} else if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__HK)) {
				billTypeDictionaryStr = DictionaryConstants.BILL_REPAYMENT__BILL_TYPE;
			} else if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__US)) {
				billTypeDictionaryStr = DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE;
			} else if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__DZ)) {
				billTypeDictionaryStr = DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE;
			} else if (StringUtils
					.equals(dto.getBillParentType(),
							SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__UF)) {
				billTypeDictionaryStr = DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE;
			} else {
				billTypeDictionaryStr = dto.getBillType();
			}

			String billTypeValue = SettlementUtil
					.getDataDictionaryByTermsName(map,
							billTypeDictionaryStr, dto.getBillType());

			data.add(StringUtils.isBlank(billTypeValue) ? ""
					: billTypeValue);
		} else {
			data.add("");
			data.add(dto.getBillType());
		}
	}

	/**
	 * 根据报表类型得到导出时的excel名称
	 * 
	 * @param rptType
	 * @return
	 */
	private String getExcelName(String rptType) {

		if (VDRptTypes.TYPE_RFO.equals(rptType)) {
			return "始发月报表明细";
		}

		else if (VDRptTypes.TYPE_RFD.equals(rptType)) {
			return "专线月报表明细";
		}

		if (VDRptTypes.TYPE_PLR.equals(rptType)) {
			return "偏线月报表明细";
		}

		if (VDRptTypes.TYPE_AFR.equals(rptType)) {
			return "空运月报表明细";
		}

		if (VDRptTypes.TYPE_RFI.equals(rptType)) {
			return "始发到达往来报表明细";
		}

		return validaRepType(rptType);

	}

	private String validaRepType(String rptType) {
		if (VDRptTypes.TYPE_PLI.equals(rptType)) {
			return "始发偏线往来报表明细";
		}

		if (VDRptTypes.TYPE_AFI.equals(rptType)) {
			return "始发空运往来报表明细";
		}
		
		if (VDRptTypes.TYPE_LDD.equals(rptType)) {
			return "快递代理月报表明细";
		}
		
		if (VDRptTypes.TYPE_LDI.equals(rptType)) {
			return "快递代理往来月报表明细";
		}
		
		if (VDRptTypes.TYPE_LWO.equals(rptType)) {
			return "营业部核销月报表明细";
		}

		else {
			throw new SettlementException("不支持的报表类型");
		}
	}

	/**
	 * @SET
	 * @param vo
	 */
	public void setVo(VoucherDetailVo vo) {
		/*
		 * @set
		 * 
		 * @this.vo = vo
		 */
		this.vo = vo;
	}

	public VoucherDetailVo getVo() {
		return vo;
	}

	public void setMvrDetailService(IMvrDetailService mvrDetailService) {
		this.mvrDetailService = mvrDetailService;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public boolean isSyncExport() {
		return syncExport;
	}

	public void setSyncExport(boolean syncExport) {
		this.syncExport = syncExport;
	}

	public void setRecordImportExcelFileInfoService(
			IrecordImportExcelFileInfoService recordImportExcelFileInfoService) {
		this.recordImportExcelFileInfoService = recordImportExcelFileInfoService;
	}

	public void setDownloadFtpConfig(FTPConfig downloadFtpConfig) {
		this.downloadFtpConfig = downloadFtpConfig;
	}
}
