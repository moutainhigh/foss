package com.deppon.foss.module.pickup.order.server.action;

import java.io.InputStream;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerCompleteDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.vo.ExpressWorerStatusConditionVo;
import com.deppon.foss.util.CollectionUtils;

public class ExpressWorkerStatusAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager
			.getLogger(ExpressWorkerStatusAction.class);
	//更改状态service
	private IExpressWorkerStatusService expressWorkerStatusService;
	//操作条件Vo
	private ExpressWorerStatusConditionVo  expressWorerStatusConditionVo = new ExpressWorerStatusConditionVo();
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;
	
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：开启快递员action
	 * @date:2014-4-24 下午2:40:26
	 * @return String
	 */
	@JSON
    public String openExpressWorker(){
		ExpressWorkerStatusDto dto = new ExpressWorkerStatusDto();
		//Foss系统开启
		dto.setOperateSystem(ExpressWorkerStatusConstants.FOSS_SYSTEM);
		//状态为开启
		dto.setOperateType(ExpressWorkerStatusConstants.OPEN_STATUS);
		//业务类型为快递
//		dto.setBusinessArea(ExpressWorkerStatusConstants.EXPRESS_BUSINESS); //zxy 20140707 内部优化  修改:删除无用字段
		//操作人为
		UserEntity entity = FossUserContext.getCurrentUser();
		if(entity != null){
			dto.setCreatorCode(entity.getEmployee().getEmpCode());
		}else{
			return returnError("无法获取当前登录人信息！");
		}
		dto.setExpressEmpCodes(expressWorerStatusConditionVo.getChangeStatusEmpCodes());
		try{
			expressWorkerStatusService.openExpressWorker(dto);
		}catch(BusinessException e ){
			return returnError(e);
		}
	    List<ExpressWorkerCompleteDto> list = expressWorkerStatusService.queryExpressWorkerComplete(expressWorerStatusConditionVo);
		
		//对数据分页
		int end = start+limit;
		end = end> list.size()?list.size():end;		
		expressWorerStatusConditionVo.setExpressWorkerStatusTatal(list.subList(start, end));
		this.setTotalCount((long) list.size());
		return returnSuccess();
		
    }
	
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：暂停快递员action
	 * @date:2014-4-24 下午2:40:01
	 * @return String
	 */
	@JSON
	public String stopExpressWorker(){
		ExpressWorkerStatusDto dto = new ExpressWorkerStatusDto();
		//Foss系统开启
		dto.setOperateSystem(ExpressWorkerStatusConstants.FOSS_SYSTEM);
		//状态为开启
		dto.setOperateType(ExpressWorkerStatusConstants.STOP_STATUS);
		//业务类型为快递
//		dto.setBusinessArea(ExpressWorkerStatusConstants.EXPRESS_BUSINESS); //zxy 20140707 内部优化  修改:删除无用字段
		//操作人为		
		UserEntity entity = FossUserContext.getCurrentUser();
		if(entity != null&& entity.getEmployee()!= null){
			dto.setCreatorCode(entity.getEmployee().getEmpCode());
		}else{
			return returnError("无法获取当前登录人信息！");
		}
		dto.setExpressEmpCodes(expressWorerStatusConditionVo.getChangeStatusEmpCodes());
		try{
		//liding mod for NCI
		expressWorkerStatusService.stopExrpessWorker(dto,"");
		}catch(BusinessException e ){
			return returnError(e);
		}
		
	    List<ExpressWorkerCompleteDto> list = expressWorkerStatusService.queryExpressWorkerComplete(expressWorerStatusConditionVo);		
		//对数据分页
		int end = start+limit;
		end = end> list.size()?list.size():end;		
		expressWorerStatusConditionVo.setExpressWorkerStatusTatal(list.subList(start, end));
		this.setTotalCount((long) list.size());
		return returnSuccess();
	}
	
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：查询快递员工作状态action
	 * @date:2014-4-24 下午2:39:36
	 * @return String
	 */
	@JSON
	public String queryExpressWorkerComplete(){
		/*if(CollectionUtils.isEmpty(expressWorerStatusConditionVo.getQueryEmployeeCode())
				||"".equals(expressWorerStatusConditionVo.getQueryEmployeeCode().get(0).toString())){
			List<String> list = new ArrayList<String>();
			list.add("030443");
			list.add("072418");
			list.add("042847");
			list.add("002216");
			list.add("096755");
			list.add("048593");
			expressWorerStatusConditionVo.setQueryEmployeeCode(list);
		}*/
		//TODO 调用综合接口返回对应的快递员工号
		if((CollectionUtils.isEmpty(expressWorerStatusConditionVo.getOperateBigRegionCode())||
				expressWorerStatusConditionVo.getOperateBigRegionCode().size()==0||expressWorerStatusConditionVo.getOperateBigRegionCode().get(0).equals(""))&&CollectionUtils.isEmpty(expressWorerStatusConditionVo.getBigRegionCode())
				&&CollectionUtils.isEmpty(expressWorerStatusConditionVo.getSmallRegionCode())
				&&StringUtils.isEmpty(expressWorerStatusConditionVo.getExpressWorkerCode())){
			return returnError("收派大区，收派小区和快递员不能同时为空！");
		}else if(StringUtils.isNotEmpty(expressWorerStatusConditionVo.getExpressWorkerCode())){
			List<String> queryEmployeeCodes = new ArrayList<String>();
			queryEmployeeCodes.add(expressWorerStatusConditionVo.getExpressWorkerCode());
			expressWorerStatusConditionVo.setQueryEmployeeCodes(queryEmployeeCodes);
		}
		
		List<ExpressWorkerCompleteDto> list = expressWorkerStatusService.queryExpressWorkerComplete(expressWorerStatusConditionVo);
		//zxy 20140703 AUTO-80 start 新增:如果为空，则new
		if(list == null)
			list = new ArrayList<ExpressWorkerCompleteDto>(); 
		//zxy 20140703 AUTO-80 end 新增:如果为空，则new
		//对数据分页
		int end = start+limit;
		end = end> list.size()?list.size():end;		
		expressWorerStatusConditionVo.setExpressWorkerStatusTatal(list.subList(start, end));
		this.setTotalCount((long) list.size());
		return returnSuccess();
	}
	/**
	 * 
	 * @author:lianghaishengW
	 * @Description：导出数据action
	 * @date:2014-4-24 下午2:40:59
	 * @return String
	 */
    @JSON
    public String exportWorkerComplete(){
    	
    /*	if(CollectionUtils.isEmpty(expressWorerStatusConditionVo.getQueryEmployeeCode())
				||"".equals(expressWorerStatusConditionVo.getQueryEmployeeCode().get(0).toString())){
			List<String> list = new ArrayList<String>();
			list.add("030443");
			list.add("072418");
			list.add("042847");
			list.add("002216");
			list.add("096755");
			list.add("048593");
			expressWorerStatusConditionVo.setQueryEmployeeCode(list);
		}*/
    	
    	//TODO 调用综合接口返回对应的快递员工号
    	

    	if((CollectionUtils.isEmpty(expressWorerStatusConditionVo.getOperateBigRegionCode())||
				expressWorerStatusConditionVo.getOperateBigRegionCode().size()==0||expressWorerStatusConditionVo.getOperateBigRegionCode().get(0).equals(""))&&CollectionUtils.isEmpty(expressWorerStatusConditionVo.getBigRegionCode())
				&&CollectionUtils.isEmpty(expressWorerStatusConditionVo.getSmallRegionCode())
				&&StringUtils.isEmpty(expressWorerStatusConditionVo.getExpressWorkerCode())){
			return returnError("收派大区，收派小区和快递员不能同时为空！");
		}else if(StringUtils.isNotEmpty(expressWorerStatusConditionVo.getExpressWorkerCode())){
			List<String> queryEmployeeCodes = new ArrayList<String>();
			queryEmployeeCodes.add(expressWorerStatusConditionVo.getExpressWorkerCode());
			expressWorerStatusConditionVo.setQueryEmployeeCodes(queryEmployeeCodes);
		}
		
    	
		List<ExpressWorkerCompleteDto> list = expressWorkerStatusService.queryExpressWorkerComplete(expressWorerStatusConditionVo);
		try {
			// 导出文件名称：
			String exportExeclName = "快递员工作状态信息";
			this.setExeclName(new String(exportExeclName.getBytes("GBK"),"ISO8859_1"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("导出Excel失败：" + e.getMessage());
		}
		// 生成导出数据源类
		ExportResource sheet = this.exportRfdResource(list);
		// 创建导出表头对象
		ExportSetting exportSetting = new ExportSetting();
		// 设置sheet名称
		exportSetting.setSheetName("快递员工作状态");
		// 创建导出工具类,导出成文件
		this.setInputStream(new ExporterExecutor().exportSync(sheet,
				exportSetting));

		return returnSuccess();
    }
    /**
     * 
     * @author:lianghaisheng
     * @Description：构造Excel表头
     * @date:2014-4-23 下午2:30:38
     * @return ExportResource
     */
    private ExportResource exportRfdResource (List<ExpressWorkerCompleteDto> list){
    ExportResource sheet = new ExportResource();
	//设置表单表头
	sheet.setHeads(new String[]{"姓名","快递员工号","收派大区","收派小区","属性","已接收订单"	,"已完成订单","无订单开单","应派送订单","已派送订单"});
	// 处理返回的结果集
	List<List<String>> resultList = this.exportWorkerCompelet(list);
	//设置表单数据
	sheet.setRowList(resultList);
	
	return sheet;
	}
    /**
     * 
     * @author:lianghaisheng
     * @Description：构建Exel列
     * @date:2014-4-23 下午2:32:07
     * @return List<List<String>>
     */
    private List<List<String>> exportWorkerCompelet(List<ExpressWorkerCompleteDto> expressWorkerCompleteDtoList) {

		String[] columns = { "emp_name", "emp_code", "bigRegion",
				"smallRegion", "emptype", "getOrder", "receiveOrder",
				"noOrderWaybill", "getWaybill", "deliverWaybill" };
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(expressWorkerCompleteDtoList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (ExpressWorkerCompleteDto entity : expressWorkerCompleteDtoList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					if (StringUtils.equals(column, "emp_name")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "emp_code")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());

					} else if (StringUtils.equals(column, "bigRegion")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "smallRegion")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "emptype")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "getOrder")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "receiveOrder")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "noOrderWaybill")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "getWaybill")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "deliverWaybill")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					}
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
	}
		
		return rowList;
	}
	public void setExpressWorkerStatusService(
			IExpressWorkerStatusService expressWorkerStatusService) {
		this.expressWorkerStatusService = expressWorkerStatusService;
	}

	public ExpressWorerStatusConditionVo getExpressWorerStatusConditionVo() {
		return expressWorerStatusConditionVo;
	}

	public void setExpressWorerStatusConditionVo(
			ExpressWorerStatusConditionVo expressWorerStatusConditionVo) {
		this.expressWorerStatusConditionVo = expressWorerStatusConditionVo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getExeclName() {
		return execlName;
	}

	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}
    
	
    
}
