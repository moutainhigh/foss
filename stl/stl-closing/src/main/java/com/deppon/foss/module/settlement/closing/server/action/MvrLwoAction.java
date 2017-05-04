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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrLwoService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLwoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLwoDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 零担快递代理核销月报表Action.
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-6 下午1:44:07
 */
public class MvrLwoAction extends AbstractAction {

	/** 日志. */
	private static final Logger LOG = LogManager.getLogger(MvrLwoAction.class);

	/** 序列号. */
	private static final long serialVersionUID = 2127529594939158830L;

	/** DTO. */
	private MvrLwoDto mvrLwoDto;

	/** 零担快递代理核销月报表查询服务. */
	private IMvrLwoService mvrLwoService;

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
			if (mvrLwoDto == null) {
				return returnError("查询参数为空");
			}
			
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			mvrLwoDto.setUserCode(currentInfo.getEmpCode());
			// 查询汇总
			MvrLwoDto dto = mvrLwoService.queryMvrLwoTotal(mvrLwoDto);
			dto.setPeriod("合计");
			if (dto.getCount() > 0) {
				// 查询列表
				List<MvrLwoEntity> queryList = mvrLwoService.queryMvrLwo(
						mvrLwoDto, getStart(), getLimit());

				// 附加统计信息
				MvrLwoEntity total = new MvrLwoEntity();
				BeanUtils.copyProperties(dto, total);
				queryList.add(total);

				// 设置查询结果列表
				dto.setQueryList(queryList);
			}

			// 将查询结果返回给前台
			this.setMvrLwoDto(dto);

		} catch (BusinessException e) {

			// 记录日志并返回失败
			LOG.error(e);
			return returnError("查询零担快递代理核销月报表异常:" + e.getMessage());

		}

		// 返回成功
		return returnSuccess();
	}

	/**
	 * 导出零担快递代理核销月报表.
	 * 
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-8 上午10:32:56
	 */
	public String export() {

		try {

			// 查询参数不能为空
			if (mvrLwoDto == null) {
				return returnError("查询参数为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			mvrLwoDto.setUserCode(currentInfo.getEmpCode());
			// 查询报表期间不能为空
			if (StringUtils.isEmpty(mvrLwoDto.getPeriod())) {
				return returnError("查询零担快递代理核销月报表期间为空");
			}

			// 设置导出excel名称
			String exportXlsName = "零担快递代理核销月报表_" + mvrLwoDto.getPeriod();
			try {
				this.setExcelName(URLEncoder.encode(exportXlsName,
						SettlementConstants.UNICODE_UTF));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrLwoEntity> queryList = mvrLwoService.queryMvrLwo(
					mvrLwoDto, SettlementConstants.EXPORT_EXCEL_START_NUMBER,
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
			return returnError("导出零担快递代理核销月报表异常:" + e.getMessage());

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
	private ExportResource getExportResource(List<MvrLwoEntity> queryList) {

		// 导出excel数据
		ExportResource data = new ExportResource();

		// 导出excel表头
		String[] headers = {
			"期间",
			"客户名称",
			"客户编码",
			"营业部编码",
			"营业部名称",
			"快递代理点部编码",
			"快递代理点部名称",
			"预收客户冲应收始发运费未签收",
			"预收客户冲应收始发运费已签收",
			"理赔冲始发应收已签收",
			"理赔冲始发应收未签收",
			"应付理赔冲小票应收",
			"预收客户冲小票应收",
			"退运费冲始发应收已签收",
			"退运费冲始发应收未签收",
			"理赔冲始发应收已签收",
			"理赔冲始发应收未签收",
			"应付理赔冲小票应收",
			"退运费冲始发应收已签收",
			"退运费冲始发应收未签收",
			"出发部门付款申请",
			"到达部门付款申请"
		};

		// 需要导出的excel的列
		String[] columns = {
			"period",
			"customerName",
			"customerCode",
			"origOrgCode",
			"origOrgName",
			"destOrgCode",
			"destOrgName",
			"custDrOrigLandRcvNpod",
			"custDrOrigLandRcvPod",
			"claimWoOrigLandRcvPod",
			"claimWoOrigLandRcvNpod",
			"orLandRcvWoClaimPay",
			"orLandRcvWoCustDr",
			"refundWoOrigLandRcvPod",
			"refundWoOrigLandRcvNpod",
			"landClaimWoOrigRcvPod",
			"landClaimWoOrigRcvNpod",
			"orRcvWoLandClaimPay",
			"landRefundWoOrigRcvPod",
			"landRefundWoOrigRcvNpod",
			"origOrgPayApply",
			"destOrgPayApply"
		};

		
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(queryList)) {
			// 列数据
			List<String> colList = null;

			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrLwoEntity entity : queryList) {

				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue
							.toString());
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}
		// 设置Header
		data.setHeads(headers);
		
		data.setHeaderHeight(SettlementReportNumber.THREE);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE,SettlementReportNumber.ZERO,SettlementReportNumber.SIX,"数据统计维度");
		
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SEVEN,SettlementReportNumber.EIGHT,"营业部预收客户冲销快递代理应收");
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.SEVEN,SettlementReportNumber.EIGHT,"营业部预收客户");
		
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.NINE,SettlementReportNumber.TEN,"营业部理赔冲销快递代理应收");
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.NINE,SettlementReportNumber.TEN,"出发部门申请理赔");
		
		HeaderRows rowContent6 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.ELEVEN,SettlementReportNumber.TWELVE,"营业部冲销快递代理小票应收");
		HeaderRows rowContent7 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.ELEVEN,SettlementReportNumber.TWELVE,"小票应收核销");
		
		HeaderRows rowContent8 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.THIRTEEN,SettlementReportNumber.FOURTEEN,"营业部退运费冲销快递代理应收");
		HeaderRows rowContent9 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.THIRTEEN,SettlementReportNumber.FOURTEEN,"出发部门申请");
		
		HeaderRows rowContent10 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FIFTEEN,SettlementReportNumber.SIXTEEN,"快递代理理赔冲销营业部应收");
		HeaderRows rowContent11 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.FIFTEEN,SettlementReportNumber.SIXTEEN,"出发部门申请理赔");
		
		HeaderRows rowContent12 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SEVENTEEN,SettlementReportNumber.SEVENTEEN,"快递代理冲销营业部应收小票");
		HeaderRows rowContent13 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.SEVENTEEN,SettlementReportNumber.SEVENTEEN,"小票应收核销");
		
		HeaderRows rowContent14 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.EIGHTEEN,SettlementReportNumber.NINETEEN,"快递代理退运费冲销营业部应收");
		HeaderRows rowContent15 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.EIGHTEEN,SettlementReportNumber.NINETEEN,"出发部门申请");

		HeaderRows rowContent16 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE,SettlementReportNumber.TWENTY,SettlementReportNumber.TWENTY_ONE,"快递代理付款申请");
		
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
		headerList.add(rowContent6);
		headerList.add(rowContent7);
		headerList.add(rowContent8);
		headerList.add(rowContent9);
		headerList.add(rowContent10);
		headerList.add(rowContent11);
		headerList.add(rowContent12);
		headerList.add(rowContent13);
		headerList.add(rowContent14);
		headerList.add(rowContent15);
		headerList.add(rowContent16);
		data.setHeaderList(headerList);
		
		// 设置导出数据
		data.setRowList(rowList);
		return data;
	}

	/**
	 * @GET
	 * @return mvrLwoDto
	 */
	public MvrLwoDto getMvrLwoDto() {
		/*
		 *@get
		 *@ return mvrLwoDto
		 */
		return mvrLwoDto;
	}

	/**
	 * @SET
	 * @param mvrLwoDto
	 */
	public void setMvrLwoDto(MvrLwoDto mvrLwoDto) {
		/*
		 *@set
		 *@this.mvrLwoDto = mvrLwoDto
		 */
		this.mvrLwoDto = mvrLwoDto;
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
	 * @SET
	 * @param mvrLwoService
	 */
	public void setMvrLwoService(IMvrLwoService mvrLwoService) {
		/*
		 *@set
		 *@this.mvrLwoService = mvrLwoService
		 */
		this.mvrLwoService = mvrLwoService;
	}

}
