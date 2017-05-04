package com.deppon.foss.module.settlement.pay.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
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
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IRentCarReportService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.RentCarReportVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkDetailEntity;
import com.deppon.foss.util.DateUtils;

/**
 * @author 045738
 * 临时租车Action
 */
public class RentCarReportAction extends AbstractAction {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2443746668734345235L;
	
	/**
	 * 预付单Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(RentCarReportAction.class);
	
	/**
	 * 查询VO
	 */
	private RentCarReportVo vo;
	
	/**
	 * 临时租车service
	 */
	private IRentCarReportService rentCarReportService;
	
	/**
	 * 注入临时租车service
	 */
	private ITemprentalMarkService temprentalMarkService;
	
	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	
	/**
	 * 功能：临时租车查询
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-16
	 * @return
	 */
	@JSON
	public String queryRentCarReport(){
		try{
			//声明vo
			if(vo==null){
				throw new SettlementException("查询参数不能为空");
			}
			//获取用户登录信息
			CurrentInfo cinfo = FossUserContext.getCurrentInfo();
			//按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getDto().getQueryType())){
				//按日期查询
				if(vo.getDto().getStartDate()==null || vo.getDto().getEndDate()==null){
					throw new SettlementException("查询日期不能为空！");
				}
				//获取第结束日期后面一天
				vo.getDto().setEndDate(DateUtils.getStartDatetime(vo.getDto().getEndDate(), 1));
			}
			//设置当前登录人、当前登录部门
			if(vo.getDto()!=null){
				vo.getDto().setEmpCode(cinfo.getEmpCode());
				vo.getDto().setCurrentDeptCode(cinfo.getCurrentDeptCode());
			}
			//查询汇总
			RentCarReportDto dto = rentCarReportService.queryRentCarReportCount(vo.getDto(),cinfo);
			if(dto!=null && dto.getTotalCount()>0){
				//调用查询接口查询报表
				List<RentCarReportDto> resultList = rentCarReportService.queryRentCarReport(vo.getDto(), cinfo, this.getStart(), this.getLimit());
				//设置总条数
				vo.setResultList(resultList);
				this.setTotalCount((long) dto.getTotalCount());
			}
			vo.setDto(dto);
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * 功能：校验运单重复项
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-11
	 * @return 校验租车明细重复项
	 */
	@JSON
	public String validateRepeatModel(){
		try{
			//校验传入参数
			if(vo==null || vo.getDto()==null || StringUtils.isEmpty(vo.getDto().getRentCarNo())){
				throw new SettlementException("传入参数不能为空！");
			}else{
				//调用中转接口查询重复数据
				List<TempRentalMarkDetailEntity> list;
				try {
					list = temprentalMarkService.queryRentalMarkDuplicates(vo.getDto().getRentCarNo());
				} catch (Exception e) {
					throw new SettlementException("调用中转查询校验重复数据接口异常！");
				}
				if(!CollectionUtils.isEmpty(list)){
					vo.setRepeatList(getRepeatList(list));
				}
			}
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/**
	 * 功能：取消临时租车标记
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	@JSON
	public String disableRentCar(){
		try{
			//声明vo
			if(vo==null){
				throw new SettlementException("查询参数不能为空");
			}
			//获取用户登录信息
			CurrentInfo cinfo = FossUserContext.getCurrentInfo();
			//设置当前登录人
			if(vo.getDto()!=null){
				vo.getDto().setEmpCode(cinfo.getEmpCode());
				vo.getDto().setCurrentDeptCode(cinfo.getCurrentDeptCode());
			}
			rentCarReportService.disableRentCar(vo.getDto(),cinfo);
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 功能：预提
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-17
	 * @return
	 */
	@JSON
	public String withholding(){
		try{
			//声明vo
			if(vo==null){
				throw new SettlementException("查询参数不能为空");
			}
			//获取用户登录信息
			CurrentInfo cinfo = FossUserContext.getCurrentInfo();
			//设置当前登录人
			if(vo.getDto()!=null){
				vo.getDto().setEmpCode(cinfo.getEmpCode());
				vo.getDto().setCurrentDeptCode(cinfo.getCurrentDeptCode());
			}
			vo = rentCarReportService.withholding(vo.getDto(),cinfo);
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 功能：预提
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-17
	 * @return
	 */
	@JSON
	public String saveWithholding(){
		try{
			//声明vo
			if(vo==null){
				throw new SettlementException("查询参数不能为空");
			}
			//获取用户登录信息
			CurrentInfo cinfo = FossUserContext.getCurrentInfo();
			//设置当前登录人
			if(vo.getDto()!=null){
				vo.getDto().setEmpCode(cinfo.getEmpCode());
				vo.getDto().setCurrentDeptCode(cinfo.getCurrentDeptCode());
			}
			vo = rentCarReportService.saveWithholding(vo,cinfo);
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	/**
	 * 功能：导出临时租车标记
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public String exportRentCar(){
		try {
			//声明vo
			if(vo==null){
				throw new SettlementException("查询参数不能为空");
			}
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			//按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getDto().getQueryType())){
				//按日期查询
				if(vo.getDto().getStartDate()==null || vo.getDto().getEndDate()==null){
					throw new SettlementException("查询日期不能为空！");
				}
				//获取第结束日期后面一天
				vo.getDto().setEndDate(DateUtils.getStartDatetime(vo.getDto().getEndDate(), 1));
			}

			// 设置导出excel名称
			String exportXlsName = "临时租车报表";
			try {
				this.setExcelName(URLEncoder.encode(exportXlsName,
						SettlementConstants.UNICODE_UTF));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			if(vo.getDto()!=null){
				vo.getDto().setEmpCode(currentInfo.getEmpCode());
				vo.getDto().setCurrentDeptCode(currentInfo.getCurrentDeptCode());
			}
			// 查询导出数据
			List<RentCarReportDto> queryList = rentCarReportService.queryRentCarReportForExport(vo.getDto(), currentInfo);

			// 导出格式数据
			ExportResource exportResource = getExportResource(queryList);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(exportXlsName);
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志并返回失败
			LOGGER.error(e);
			return returnError("导出临时租车报表异常:" + e.getMessage());

		}
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
	private ExportResource getExportResource(List<RentCarReportDto> queryList) {

		// 导出excel数据
		ExportResource data = new ExportResource();
		// 导出excel表头
		String[] headers = {
				"应付单号",
				"租车编号",
				"付款状态",
				"报销/付款工作流号",
				"是否已报销/转报销",
				"车牌号",
				"租车用途",
				"租车金额",
				"付款费用承担部门",
				"付款费用类型",
				"业务发生日期",
				"预提状态",
				"预提费用承担部门",
				"预提工作流号",
				"预提工作流处理结果",
				"费用所属月份",
				"事业部",
				"大区",
				"小区",
				"总重量",
				"总体积",
				"公里数",
				"目的地",
				"出发地",
				"用车日期",
				"用车原因",
				"应走货票数",
				"实际走货票数",
				"车型",
				"司机姓名",
				"租车标记时间",
				"租车标记部门",
				"租车标记操作人",
				"是否多次标记",
				"租车标记备注"
		};

		// 需要导出的excel的列
		String[] columns = {
				"payableNo",
				"rentCarNo",
				"payStatus",
				"payWorkFlowNo",
				"reimbursement",
				"vehicleNo",
				"rentCarUseType",
				"rentCarAmount",
				"costDeptName",
				"costType",
				"businessDate",
				"withholdingStatus",
				"withholdingCostDeptName",
				"withholdingWorkFlowNo",
				"withholdingResult",
				"costDate",
				"division",
				"bigArea",
				"smallArea",
				"totalWeight",
				"totalVolume",
				"kilometre",
				"destination",
				"departure",
				"useCarDate",
				"useCarReasion",
				"shallTakegoodsQyt",
				"actualTakegoodsQyt",
				"carType",
				"driverName",
				"rentCarTime",
				"rentCarDeptName",
				"rentMarkUserInfo",
				"isRepeateMark",
				"notes"
		};

		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(queryList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			
			
			//声明字典集合
			List<String> termCodes = new ArrayList<String>();
			termCodes.add(DictionaryConstants.BILL_PAYMENT__REMIT_STATUS);
			termCodes.add(DictionaryConstants.WITHHOLDING_STATUS);
			termCodes.add(DictionaryConstants.RENTCAR_COST_TYPE);
			termCodes.add(DictionaryConstants.RENTCAR_USE_TYPE);
			termCodes.add(DictionaryConstants.FOSS_BOOLEAN);
			//后台获取数据字典对应的数据
			Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
			
			// 循环结果集
			for (RentCarReportDto entity : queryList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String columnName : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, columnName);
					cellValue = (fieldValue == null ? "" : fieldValue.toString());
					if(StringUtils.isNotBlank(cellValue) && fieldValue != null){
						// 如果为日期，需要进行格式化 
						if (columnName.equals("businessDate")||columnName.equals("costDate")
								||columnName.equals("useCarDate")||columnName.equals("rentCarTime")) {
							//日期转化
							cellValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
						}
						//数据字典转话类型
						if(columnName.equals("payStatus")){
							cellValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_PAYMENT__REMIT_STATUS,fieldValue.toString());
						}else if(columnName.equals("rentCarUseType")){
							cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.RENTCAR_USE_TYPE,fieldValue.toString());
						}else if(columnName.equals("costType")){
							cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.RENTCAR_COST_TYPE,fieldValue.toString());
						}else if(columnName.equals("withholdingStatus")){
							cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.WITHHOLDING_STATUS,fieldValue.toString());
						}else if(columnName.equals("reimbursement")||columnName.equals("isRepeateMark")){
							cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.FOSS_BOOLEAN,fieldValue.toString());
						}
					}
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}
		// 设置Header
		data.setHeads(headers);
		// 设置导出数据
		data.setRowList(rowList);
		return data;
	}
		
	public RentCarReportVo getVo() {
		return vo;
	}

	public void setVo(RentCarReportVo vo) {
		this.vo = vo;
	}

	public void setRentCarReportService(IRentCarReportService rentCarReportService) {
		this.rentCarReportService = rentCarReportService;
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
	/**
	 * 转话数据
	 * @param repeatlist
	 * @return
	 */
	private List<WithholdingEntityDetail> getRepeatList(List<TempRentalMarkDetailEntity> repeatlist ){
		//声明预提列表
		List<WithholdingEntityDetail> list = new ArrayList<WithholdingEntityDetail>();

		//循环封装成结算的detail
		for(TempRentalMarkDetailEntity detail:repeatlist){
			WithholdingEntityDetail we = new WithholdingEntityDetail();
			we.setBillNo(detail.getBillNo()); //封装单据号
			we.setPayableNo(detail.getPayableNo());//应付单号
			we.setRentCarNo(detail.getTempRentalMarkNo());//临时租车编号
			we.setRentCarUseType(detail.getRentalCarUserType());//租车类型
			we.setVehicleNo(detail.getVehicleNo());//车牌号
			we.setRentCarAmount(detail.getRentCarAmount());//租车金额
			we.setNotes(detail.getNotes());//备注
			list.add(we);
		}
		return list;
	}

	public void setTemprentalMarkService(ITemprentalMarkService temprentalMarkService) {
		this.temprentalMarkService = temprentalMarkService;
	}
	
	
}
