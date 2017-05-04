package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfrQueryService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 空运月报表查询Action.
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-6 下午1:44:07
 */
public class MvrAfrAction extends AbstractAction {

	/** 日志. */
	private static final Logger LOG = LogManager.getLogger(MvrAfrAction.class);

	/** 序列号. */
	private static final long serialVersionUID = 2127529594939158830L;

	/** DTO. */
	private MvrAfrDto mvrAfrDto;

	/** 空运月报表查询服务. */
	private IMvrAfrQueryService mvrAfrQueryService;

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
			if (mvrAfrDto == null) {
				return returnError("查询参数为空");
			}
			
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			mvrAfrDto.setUserCode(currentInfo.getEmpCode());
			// 查询汇总
			MvrAfrDto dto = mvrAfrQueryService.queryMvrAfrTotal(mvrAfrDto);
			dto.setPeriod("合计");
			if (dto.getCount() > 0) {
				// 查询列表
				List<MvrAfrEntity> queryList = mvrAfrQueryService.queryMvrAfr(
						mvrAfrDto, getStart(), getLimit());

				// 附加统计信息
				MvrAfrEntity total = new MvrAfrEntity();
				BeanUtils.copyProperties(dto, total);
				queryList.add(total);

				// 设置查询结果列表
				dto.setQueryList(queryList);
			}

			// 将查询结果返回给前台
			this.setMvrAfrDto(dto);

		} catch (BusinessException e) {

			// 记录日志并返回失败
			LOG.error(e);
			return returnError("查询空运月报表异常:" + e.getMessage());

		}

		// 返回成功
		return returnSuccess();
	}

	/**
	 * 导出空运月报表.
	 * 
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-8 上午10:32:56
	 */
	public String export() {

		try {

			// 查询参数不能为空
			if (mvrAfrDto == null) {
				return returnError("查询参数为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			mvrAfrDto.setUserCode(currentInfo.getEmpCode());
			// 查询报表期间不能为空
			if (StringUtils.isEmpty(mvrAfrDto.getPeriod())) {
				return returnError("查询空运月报表期间为空");
			}

			// 设置导出excel名称
			String exportXlsName = "空运月报表_" + mvrAfrDto.getPeriod();
			try {
//				this.setExcelName(URLEncoder.encode(exportXlsName,
//						SettlementConstants.UNICODE_UTF));
				
				this.setExcelName(new String((exportXlsName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrAfrEntity> queryList = mvrAfrQueryService.queryMvrAfr(
					mvrAfrDto, SettlementConstants.EXPORT_EXCEL_START_NUMBER,
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
			return returnError("导出空运月报表异常:" + e.getMessage());

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
	private ExportResource getExportResource(List<MvrAfrEntity> queryList) {

		// 导出excel数据
		ExportResource data = new ExportResource();

		// 导出excel表头
		String[] headers = {
				"期间",
				//period',
				"运输性质",
				//productCode',
				"客户编码",
				//customerCode',
				"客户名称",
				//customerName',
				"始发部门编码",
				//origOrgCode',
				"始发部门名称",
				//origOrgName',
				"到达部门编码",
				//destOrgCode',
				"到达部门名称",
				//destOrgName',
				"空运航空公司运费确认",
				//airCostComConfirm',
				"空运出发代理应付确认",
				//airCostOrigAgencyCfm',
				"空运到达代理应付生成",
				//airCostDestAgencyGen',
				"空运到达代理应付成本确认",
				//airCostDestAgencyCfm",
				"空运到达代理应付成本反确认",
				//airCostDestAgencyNcfm',
				"其它应付成本确认",
				//airCostOtherConfirm',
				"空运成本付款申请",
				//airCostPayApply',
				"空运其他应收录入",
				//othEntry',
				"还款空运其他应收现金",
				//othRcvCh',
				"还款空运其他应收银行",
				//othRcvCd',
				"应付到达代理成本冲应收到付运费已签收",
				//airPrAgencyWoDestRcvPod',
				"应付到达代理成本冲应收到付运费未签收",
				//airPrAgencyWoDestRcvNpod',
				"其他应付冲应收到付运费已签收",
				//airPrOtWoDestRcvPod',
				"其他应付冲应收到付运费未签收",
				//airPrOthWoDestRcvNpod',
				"其他应付冲其他应收",
				//airPrOthWoOthRcv',
				"空运还款代收货款现金已签收",
				//airCodChPod',
				"空运还款代收货款银行已签收",
				//airCodCdPod',
				"空运签收时已核销代收货款",
				//airCodPodWoCod',
				"空运反签收时已核销代收货款",
				//airCodNpodWoCod',
				"空运还款代收货款现金未签收",
				//airCodChNpod',
				"空运还款代收货款银行未签收",
				//airCodCdNpod',
				"空运到达代理应付冲应收代收货款已签收",
				//airCodWoAgencyPayPod',
				"空运其他应付冲应收代收货款已签收",
				//airCodWoOthPayCod',
				"空运到达代理应付冲应收代收货款未签收",
				//airCodWoAgencyPayNpod',
				"空运其他应付冲应收代收货款未签收",
				//airCodWoOthNpod',
				"预收空运代理现金",
				//airDrCh',
				"预收空运代理银行",
				//airDrCd',
				"预收空运代理冲应收到付运费已签收",
				//airDrDestRcvPod',
				"预收空运代理冲应收到付运费未签收",
				//airDrDestRcvNpod',
				"预收空运代理冲其他应收",
				//airDrWoOtherRcv',
				"预收空运代理冲应收代收货款已签收",
				//airDrWoCodRcvPod',
				"预收空运代理冲应收代收货款未签收",
				//airDrWoCodRcvNpod',
				"空运退预收付款申请",
				//airDrPayApply',
				"预付航空公司",
				//aptCom',
				"预付冲应付航空公司",
				//aptWoComPay',
				"预付冲其他应付",
				//aptWoOthPay',
				"坏账冲其它应收",
				//bdrWoOthRcv',
				"还款现金未签收",
				//urDestChNpod',
				"还款银行未签收",
				//urDestCdNpod',
				"还款现金已签收",
				//urDestChPod',
				"还款银行已签收",
				//urDestCdPod'
		};

		// 需要导出的excel的列
		String[] columns = {
				//qijian
				"period",
				//业务类型',
				"productCode",
				//客户编码',
				"customerCode",
				//客户名称',
				"customerName",
				//始发部门编码',
				"origOrgCode",
				//始发部门名称',
				"origOrgName",
				//到达部门编码',
				"destOrgCode",
				//到达部门名称',
				"destOrgName",
				//空运航空公司运费确认',
				"airCostComConfirm",
				//空运出发代理应付确认',
				"airCostOrigAgencyCfm",
				//空运到达代理应付生成',
				"airCostDestAgencyGen",
				//空运到达代理应付成本确认',
				"airCostDestAgencyCfm",
				//空运到达代理应付成本反确认',
				"airCostDestAgencyNcfm",
				//其它应付成本确认',
				"airCostOtherConfirm",
				//空运成本付款申请',
				"airCostPayApply",
				//空运其他应收录入',
				"othEntry",
				//还款空运其他应收现金',
				"othRcvCh",
				//还款空运其他应收银行',
				"othRcvCd",
				//应付到达代理成本冲应收到付运费已签收',
				"airPrAgencyWoDestRcvPod",
				//应付到达代理成本冲应收到付运费未签收',
				"airPrAgencyWoDestRcvNpod",
				//其他应付冲应收到付运费已签收',
				"airPrOtWoDestRcvPod",
				//其他应付冲应收到付运费未签收',
				"airPrOthWoDestRcvNpod",
				//其他应付冲其他应收',
				"airPrOthWoOthRcv",
				//空运还款代收货款现金已签收',
				"airCodChPod",
				//空运还款代收货款银行已签收',
				"airCodCdPod",
				//空运签收时已核销代收货款',
				"airCodPodWoCod",
				//空运反签收时已核销代收货款',
				"airCodNpodWoCod",
				//空运还款代收货款现金未签收',
				"airCodChNpod",
				//空运还款代收货款银行未签收',
				"airCodCdNpod",
				//"空运到达代理应付冲应收代收货款已签收',
				"airCodWoAgencyPayPod",
				//空运其他应付冲应收代收货款已签收',
				"airCodWoOthPayCod",
				//空运到达代理应付冲应收代收货款未签收',
				"airCodWoAgencyPayNpod",
				//空运其他应付冲应收代收货款未签收',
				"airCodWoOthNpod",
				//预收空运代理现金',
				"airDrCh",
				//预收空运代理银行',
				"airDrCd",
				//预收空运代理冲应收到付运费已签收',
				"airDrDestRcvPod",
				//预收空运代理冲应收到付运费未签收',
				"airDrDestRcvNpod",
				//预收空运代理冲其他应收',
				"airDrWoOtherRcv",
				//预收空运代理冲应收代收货款已签收',
				"airDrWoCodRcvPod",
				//预收空运代理冲应收代收货款未签收',
				"airDrWoCodRcvNpod",
				//空运退预收付款申请',
				"airDrPayApply",
				//预付航空公司',
				"aptCom",
				//预付冲应付航空公司',
				"aptWoComPay",
				//预付冲其他应付',
				"aptWoOthPay",
				//坏账冲其它应收',
				"bdrWoOthRcv",
				//还款现金未签收',
				"urDestChNpod",
				//还款银行未签收',
				"urDestCdNpod",
				//还款现金已签收',
				"urDestChPod",
				//还款银行已签收
				"urDestCdPod"};

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
			for (MvrAfrEntity entity : queryList) {

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
		HeaderRows rowContent1 = new HeaderRows(0,0,0,SettlementReportNumber.SEVEN,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(0,0,SettlementReportNumber.EIGHT,SettlementReportNumber.FOURTEEN,"空运成本");
		HeaderRows rowContent3 = new HeaderRows(0,0,SettlementReportNumber.FIFTEEN, SettlementReportNumber.SEVENTEEN,"空运其他应收");
		HeaderRows rowContent4 = new HeaderRows(0,0,SettlementReportNumber.EIGHTEEN, SettlementReportNumber.TWENTY_TWO,"空运应付冲应收");
		HeaderRows rowContent5 = new HeaderRows(0,0, SettlementReportNumber.TWENTY_THREE, SettlementReportNumber.THIRTY_TWO,"空运代收货款");
		HeaderRows rowContent6 = new HeaderRows(0,0, SettlementReportNumber.THIRTY_THREE, SettlementReportNumber.FORTY,"预收空运代理");
		HeaderRows rowContent7 = new HeaderRows(0,0, SettlementReportNumber.FORTY_ONE, SettlementReportNumber.FORTY_THREE,"预付");
		HeaderRows rowContent8 = new HeaderRows(0,0, SettlementReportNumber.FORTY_FOUR, SettlementReportNumber.FORTY_FOUR,"坏账冲其他应收");
		HeaderRows rowContent9 = new HeaderRows(0,0, SettlementReportNumber.FORTY_FIVE, SettlementReportNumber.FORTY_EIGHT,"还款运单总运费（到付）");
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
	 * @return mvrAfrDto
	 */
	public MvrAfrDto getMvrAfrDto() {
		return mvrAfrDto;
	}

	/**
	 * Sets the mvr afr dto.
	 * 
	 * @param mvrAfrDto
	 *            the new mvr afr dto
	 */
	public void setMvrAfrDto(MvrAfrDto mvrAfrDto) {
		this.mvrAfrDto = mvrAfrDto;
	}

	/**
	 * Sets the mvr afr query service.
	 * 
	 * @param mvrAfrQueryService
	 *            the new mvr afr query service
	 */
	public void setMvrAfrQueryService(IMvrAfrQueryService mvrAfrQueryService) {
		this.mvrAfrQueryService = mvrAfrQueryService;
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

}
