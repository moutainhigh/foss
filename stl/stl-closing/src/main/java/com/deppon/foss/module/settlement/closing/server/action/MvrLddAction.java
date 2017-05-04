package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrLddService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLddEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLddDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 快递代理月报表查询Action.
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-6 下午1:44:07
 */
public class MvrLddAction extends AbstractAction {

	/** 日志. */
	private static final Logger LOG = LogManager.getLogger(MvrLddAction.class);

	/** 序列号. */
	private static final long serialVersionUID = 2127529594939158830L;

	/** DTO. */
	private MvrLddDto MvrLddDto;

	/** 快递代理月报表查询服务. */
	private IMvrLddService mvrLddService;

	/**
	 * 产品服务
	 */
	private IProductService productService;

	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;

	/**
	 * 查询方法
	 * 
	 * @author zengbinwen
	 * @date 2013-3-14 下午2:07:53
	 * @return
	 * @throws Exception
	 * @see org.apache.struts2.dispatcher.DefaultActionSupport#execute()
	 */
	@Override
	@JSON
	public String execute() throws Exception {

		try {

			// 查询参数不能为空
			if (MvrLddDto == null) {
				return returnError("查询参数为空");
			}
			
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			MvrLddDto.setUserCode(currentInfo.getEmpCode());
			// 查询汇总
			MvrLddDto dto = mvrLddService.queryMvrLddTotal(MvrLddDto);
			dto.setPeriod("合计");
			if (dto.getCount() > 0) {
				// 查询列表
				List<MvrLddEntity> queryList = mvrLddService.queryMvrLdd(
						MvrLddDto, getStart(), getLimit());

				// 附加统计信息
				MvrLddEntity total = new MvrLddEntity();
				BeanUtils.copyProperties(dto, total);
				queryList.add(total);

				// 设置查询结果列表
				dto.setQueryList(queryList);
			}

			// 将查询结果返回给前台
			this.setMvrLddDto(dto);

		} catch (BusinessException e) {

			// 记录日志并返回失败
			LOG.error(e);
			return returnError("查询快递代理月报表异常:" + e.getMessage());

		}

		// 返回成功
		return returnSuccess();
	}

	/**
	 * 导出快递代理月报表.
	 * 
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-8 上午10:32:56
	 */
	public String export() {

		try {

			// 查询参数不能为空
			if (MvrLddDto == null) {
				return returnError("查询参数为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			MvrLddDto.setUserCode(currentInfo.getEmpCode());
			// 查询报表期间不能为空
			if (StringUtils.isEmpty(MvrLddDto.getPeriod())) {
				return returnError("查询快递代理月报表期间为空");
			}

			// 设置导出excel名称
			String exportXlsName = "快递代理月报表_" + MvrLddDto.getPeriod();
			try {
				this.setExcelName(URLEncoder.encode(exportXlsName,
						SettlementConstants.UNICODE_UTF));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrLddEntity> queryList = mvrLddService.queryMvrLdd(
					MvrLddDto, SettlementConstants.EXPORT_EXCEL_START_NUMBER,
					SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);

			// 导出格式数据
			ExportResource exportResource = getExportResource(queryList);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(exportXlsName);
			
			//设置格式
		    Map<Integer, String> map = new HashMap<Integer, String>();
		    for(int i=SettlementReportNumber.EIGHT;i<=SettlementReportNumber.FORTY_EIGHT;i++){
		    	map.put(i, "float");
		    }
		    exportSetting.setDataType(map);

			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);

		} catch (BusinessException e) {

			// 记录日志并返回失败
			LOG.error(e);
			return returnError("导出快递代理月报表异常:" + e.getMessage());

		}

		return returnSuccess();
	}

	/**
	 * 获取需要导出的excel数据.
	 * 
	 * @param queryList
	 *            the query list
	 * @return the sheet data
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-8 上午10:42:55
	 */
	private ExportResource getExportResource(List<MvrLddEntity> queryList) {

		// 导出excel数据
		ExportResource data = new ExportResource();

		// 导出excel表头
		String[] headers = {
			"期间",
			"运输性质",
			"客户编码",
			"客户名称",
			"始发部门编码",
			"始发部门名称",
			"到达部门编码",
			"到达部门名称",
			"快递代理应付生成",
			"快递代理应付成本确认",
			"快递代理应付成本反确认",
			"其它应付成本确认",
			"快递代理成本付款申请",
			"快递代理其他应收录入",
			"还款快递代理其他应收现金",
			"还款快递代理其他应收银行",
			"快递代理还款代收货款现金已签收",
			"快递代理还款代收货款银行已签收",
			"快递代理还款代收货款现金未签收",
			"快递代理签收时已核销代收货款",
			"快递代理反签收时已核销代收货款",
			"快递代理还款代收货款银行未签收",
			"快递代理应付冲应收代收货款已签收",
			"快递代理其他应付冲应收代收货款已签收",
			"快递代理应付冲应收代收货款未签收",
			"快递代理其他应付冲应收代收货款未签收",
			"坏账冲其他应收",
			"还款现金未签收",
			"还款银行未签收",
			"还款现金已签收",
			"还款银行已签收",
			"应付代理成本冲应收到付运费已签收",
			"应付到达代理成本冲应收到付运费未签收",
			"其他应付冲应收到付运费已签收",
			"其他应付冲应收到付运费未签收",
			"应付冲其他应收",
			"预收快递代理现金",
			"预收快递代理银行",
			"预收快递代理冲应收到付运费已签收",
			"预收快递代理冲应收到付运费未签收",
			"预收快递代理冲其他应收",
			"预收快递代理冲应收代收货款已签收",
			"预收快递代理冲应收代收货款未签收",
			"快递代理退预收付款申请",
			"预付快递代理公司",
			"预付冲应付快递代理公司",
			"预付冲其他应付"
		};

		// 需要导出的excel的列
		String[] columns = {
			"period",
			"productCode",
			"customerCode",
			"customerName",
			"origOrgCode",
			"origOrgName",
			"destOrgCode",
			"destOrgName",
			"landCostAgencyGen",
			"landCostAgencyCfm",
			"landCostAgencyNcfm",
			"landCostOtherConfirm",
			"landCostPayApply",
			"landOthEntry",
			"landOthRcvCh",
			"landOthRcvCd",
			"landCodChPod",
			"landCodCdPod",
			"landCodChNpod",
			"landCodPodWoCod",
			"landCodNpodWoCod",
			"landCodCdNpod",
			"landCodWoAgencyPayPod",
			"landCodWoOthPayCod",
			"landCodWoAgencyPayNpod",
			"landCodWoOthNpod",
			"landBdrWoOthRcv",
			"landUrDestChNpod",
			"landUrDestCdNpod",
			"landUrDestChPod",
			"landUrDestCdPod",
			"landPrAgencyWoDestRcvPod",
			"landPrAgencyWoDestRcvNp",
			"landPrOtWoDestRcvPod",
			"landPrOthWoDestRcvNpod",
			"landPrOthWoOthRcv",
			"landDrCh",
			"landDrCd",
			"landDrDestRcvPod",
			"landDrDestRcvNpod",
			"landDrWoOtherRcv",
			"landDrWoCodRcvPod",
			"landDrWoCodRcvNpod",
			"landDrPayApply",
			"landAptCom",
			"landAptWoComPay",
			"landAptWoOthPay"
		};

		//获取全部有效的第三级别产品类型
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
		
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(queryList)) {
			// 列数据
			List<String> colList = null;

			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrLddEntity entity : queryList) {

				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue
							.toString());

					// 产品类型需要转换
					if ("productCode".equalsIgnoreCase(column)) {
						
			    		//如果数据产品类型编码不为空
			    		if(StringUtils.isNotEmpty(cellValue)){
			    			//将产品类型转换编码为名称
			    			cellValue=productMap.get(cellValue);
						}
					}

					colList.add(cellValue);

				}

				rowList.add(colList);
			}
		}
		// 设置Header
		data.setHeads(headers);
		
		data.setHeaderHeight(2);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SEVEN,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.EIGHT,SettlementReportNumber.TWELVE,"快递代理成本");
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.THIRTEEN, SettlementReportNumber.FIFTEEN,"快递代理其他应收");
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SIXTEEN, SettlementReportNumber.TWENTY_FIVE,"快递代理代收货款");
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_SIX, SettlementReportNumber.THIRTY_SIX,"坏账冲其他应收");
		HeaderRows rowContent6 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_SEVEN, SettlementReportNumber.THIRTY,"还款运单总运费（到付）");
		HeaderRows rowContent7 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.THIRTY_ONE, SettlementReportNumber.THIRTY_FIVE,"快递代理应付冲应收");
		HeaderRows rowContent8 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.THIRTY_SIX, SettlementReportNumber.FORTY_THREE,"预收快递代理");
		HeaderRows rowContent9 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.FORTY_FOUR, SettlementReportNumber.FORTY_SIX,"预付");
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
		headerList.add(rowContent6);
		headerList.add(rowContent7);
		headerList.add(rowContent8);
		headerList.add(rowContent9);
		data.setHeaderList(headerList);
		
		// 设置导出数据
		data.setRowList(rowList);
		return data;
	}

	/**
	 * Gets the mvr afr dto.
	 * 
	 * @return MvrLddDto
	 */
	public MvrLddDto getMvrLddDto() {
		return MvrLddDto;
	}

	/**
	 * Sets the mvr afr dto.
	 * 
	 * @param MvrLddDto
	 *            the new mvr afr dto
	 */
	public void setMvrLddDto(MvrLddDto MvrLddDto) {
		this.MvrLddDto = MvrLddDto;
	}


	/**
	 * Gets the excel name.
	 * 
	 * @return excelName
	 */
	public String getExcelName() {
		return excelName;
	}

	/**
	 * Sets the excel name.
	 * 
	 * @param excelName
	 *            the new excel name
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * Gets the input stream.
	 * 
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the input stream.
	 * 
	 * @param inputStream
	 *            the new input stream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * @SET
	 * @param mvrLddService
	 */
	public void setMvrLddService(IMvrLddService mvrLddService) {
		/*
		 *@set
		 *@this.mvrLddService = mvrLddService
		 */
		this.mvrLddService = mvrLddService;
	}

}
