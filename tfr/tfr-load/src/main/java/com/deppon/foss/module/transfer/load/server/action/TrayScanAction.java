package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanExpressEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.TrayScanVo;

public class TrayScanAction extends AbstractAction{
	
	private static final long serialVersionUID = -4208465958064034570L;
	
	/**
	 * 定义service
	 * */
	ITrayScanService trayScanService;
	
	/**
	 * 托盘扫描Vo
	 * */
	TrayScanVo trayScanVo = new TrayScanVo();
	
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String excelFileName; 
	
	/**
	 * 导出托盘扫描任务
	 */
	private InputStream trayScanExcelStream;
	
	private IConfigurationParamsService configurationParamsService;
	
	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getTrayScanExcelStream() {
		return trayScanExcelStream;
	}

	public void setTrayScanExcelStream(InputStream trayScanExcelStream) {
		this.trayScanExcelStream = trayScanExcelStream;
	}

	public TrayScanVo getTrayScanVo() {
		return trayScanVo;
	}

	public void setTrayScanVo(TrayScanVo trayScanVo) {
		this.trayScanVo = trayScanVo;
	}

	public void setTrayScanService(ITrayScanService trayScanService) {
		this.trayScanService = trayScanService;
	}
	
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 查询交接单页面跳转，同时获取大部门code
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 下午4:16:12
	 */
	
	
	@JSON
	public String trayscanqueryindex(){
		try{
			String superOrgCode = trayScanService.querySuperiorOrgCode();
			trayScanVo.setSuperOrgCode(superOrgCode);
			this.setTotalCount(totalCount);
		}catch(BusinessException e){
			trayScanVo.setErrorMsg(e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}
	
	

	/**
	 * @function：查询托盘扫描任务  零担
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-1 8:34:34
	 * */
	@JSON
	public String queryTrayScanList(){
		//获取查询条件
		QueryTrayScanConditionDto queryTrayScanConditiondto = trayScanVo.getQueryTrayScanConditionDto();
		//查询托盘扫描任务list
		List<TrayScanDto>   queryTrayScanList=  trayScanService.queryTrayScanList(queryTrayScanConditiondto,this.getLimit(),this.getStart());
		//设置托盘扫描任务list
		trayScanVo.setTrayScanList(queryTrayScanList);
		//查询任务总数
		Long totalCount = trayScanService.getTrayScanListCount(queryTrayScanConditiondto);
		//设置任务总数,前台分页数
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**
	 * @function：查询托盘扫描任务  快递
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-1 8:34:34
	 * */
	@JSON
	public String queryTrayScanListExpress(){
		//获取查询条件
		QueryTrayScanConditionDto queryTrayScanConditiondto = trayScanVo.getQueryTrayScanConditionDto();
		if (null == queryTrayScanConditiondto) {
			throw new BusinessException("传入查询条件为空");
		}
		queryTrayScanConditiondto.setForkLiftDriverCode(queryTrayScanConditiondto.getForkLiftDriverName());//获取叉车司机编号
		queryTrayScanConditiondto.setBindingCode(queryTrayScanConditiondto.getBindingName());
		//获取当前用户的部门
		queryTrayScanConditiondto.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
		//判断是已扫描还是未扫描
		if("SCANNED".equals(queryTrayScanConditiondto.getTrayScanStatus())){
			//状态为已扫描的，  清除托盘创建时间
			queryTrayScanConditiondto.setBeginTrayCreateTime(null);
			queryTrayScanConditiondto.setEndTrayCreateTime(null);
		}else if("UNSCAN".equals(queryTrayScanConditiondto.getTrayScanStatus())||"HANDSCAN".equals(queryTrayScanConditiondto.getTrayScanStatus())){
			//状态为未扫描或手动拉车的，清除叉车扫描时间
			queryTrayScanConditiondto.setBeginTrayScanTime(null);
			queryTrayScanConditiondto.setEndTrayScanTime(null);
		}
		
		//分页转换
 		int page=(this.getStart()/this.getLimit())+1;
 		int size=this.getLimit();
		
		System.out.println("带进来的参数="+queryTrayScanConditiondto.toString());
		//查询托盘扫描任务list
		List<TrayScanExpressEntity>   queryTrayScanList=  trayScanService.queryTrayScanListExpress(queryTrayScanConditiondto,size,page);
		//设置托盘扫描任务list
		trayScanVo.setTrayScanListExpress(queryTrayScanList);
		//查询任务总数
		Long totalCount = 0l;
		if(queryTrayScanList.size() != 0){
			totalCount = queryTrayScanList.get(0).getCount();
		}
		
		System.out.println("任务总数="+totalCount);
		//设置任务总数,前台分页数
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**  零担
	 * @function：查询托盘扫描明细
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-1 8:34:34
	 * */
	@JSON
	public String queryWaybillByTaskNo(){
		//获取查询条件任务号
		String traytaskCode = trayScanVo.getTraytaskCode();
		//查询托盘扫描任务明细
		List<TrayScanDetaiEntity> serialNoList = trayScanService.queryWaybillByTaskNo(traytaskCode);
		//设置托盘扫描明细
		trayScanVo.setSerialNoList(serialNoList);
		return returnSuccess();
	}
	
	/**  快递
	 * @function：查询托盘扫描明细
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-1 8:34:34
	 * */
	@JSON
	public String queryWaybillByTaskNoExpress(){
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("查询卸车托盘绑定管理信息开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		//获取查询条件任务号
		String traytaskCode = trayScanVo.getTraytaskCode();
		//查询托盘扫描任务明细
		List<TrayScanExpressEntity> serialNoList = trayScanService.queryWaybillByTaskNoExpress(traytaskCode);
		//设置托盘扫描明细
		trayScanVo.setTrayScanListExpress(serialNoList);
		return returnSuccess();
	}
	
	/**
	 *  零担
	 * @function：导出托盘任务
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-2 17:40:53
	 * */
	@SuppressWarnings("rawtypes")
	public String exportTrayScanTaskExcel(){
		//获取查询条件
		QueryTrayScanConditionDto queryTrayScanConditiondto = trayScanVo.getQueryTrayScanConditionDto();
		//调用service获取文件名，输入流
		List list = null;
		try{
			//调用service
			list = trayScanService.getTrayScanTaskInputStream(queryTrayScanConditiondto);
		}catch(BusinessException e){
			return returnError(e);
		}
		excelFileName =(String) list.get(0);
		trayScanExcelStream = (InputStream)list.get(1);
		return returnSuccess();
	}
	
	/**
	 *  快递
	 * @function：导出托盘任务
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-2 17:40:53
	 * */
	@SuppressWarnings("rawtypes")
	public String exportTrayScanTaskExcelExpress(){
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("导出托盘任务开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		//获取查询条件
		QueryTrayScanConditionDto queryTrayScanConditiondto = trayScanVo.getQueryTrayScanConditionDto();
		queryTrayScanConditiondto.setTrayBindingTaskNo(queryTrayScanConditiondto.getBindingName());//获取绑定任务人编号
		queryTrayScanConditiondto.setForkLiftDriverCode(queryTrayScanConditiondto.getForkLiftDriverName());//获取叉车司机编号
		//获取当前用户的部门
		queryTrayScanConditiondto.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
		//调用service获取文件名，输入流
		List list = null;
		try{
			//调用service
			list = trayScanService.getTrayScanTaskInputStreamExpress(queryTrayScanConditiondto);
		}catch(BusinessException e){
			return returnError(e);
		}
		excelFileName =(String) list.get(0);
		trayScanExcelStream = (InputStream)list.get(1);
		return returnSuccess();
	}
	
	
	/**
	 * @function：查询叉车扫描总票数  零担
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-5 11:52:33
	 * */
	@JSON
	public String queryTrayScanSummary(){
		//获取查询条件
		QueryTrayScanConditionDto queryTrayScanConditiondto = trayScanVo.getQueryTrayScanConditionDto();
		//返回查询结果
		List<TrayScanDto>  queryTrayScanSummary=trayScanService.queryTrayScanSummary(queryTrayScanConditiondto);
		//传递属性
		trayScanVo.setTrayScanList(queryTrayScanSummary);
		return returnSuccess();
	}
	
	/**
	 * @function：查询叉车扫描总票数  快递
	 * @author: 105869-foss-heyongdong
	 * @date: 2013-8-5 11:52:33
	 * */
	@JSON
	public String queryTrayScanSummaryExpress(){
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("查询叉车扫描总票数开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		//获取查询条件
		QueryTrayScanConditionDto queryTrayScanConditiondto = trayScanVo.getQueryTrayScanConditionDto();
		queryTrayScanConditiondto.setForkLiftDriverCode(queryTrayScanConditiondto.getForkLiftDriverName());//获取叉车司机编号
		queryTrayScanConditiondto.setBindingCode(queryTrayScanConditiondto.getBindingName());
		//获取当前用户的部门
		queryTrayScanConditiondto.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
		//判断是已扫描还是未扫描
		if("SCANNED".equals(queryTrayScanConditiondto.getTrayScanStatus())){
			//状态为已扫描的，  清除托盘创建时间
			queryTrayScanConditiondto.setBeginTrayCreateTime(null);
			queryTrayScanConditiondto.setEndTrayCreateTime(null);
		}else if("UNSCAN".equals(queryTrayScanConditiondto.getTrayScanStatus())||"HANDSCAN".equals(queryTrayScanConditiondto.getTrayScanStatus())){
			//状态为未扫描的，清除叉车扫描时间
			queryTrayScanConditiondto.setBeginTrayScanTime(null);
			queryTrayScanConditiondto.setEndTrayScanTime(null);
		}
		//返回查询结果
		List<TrayScanDto>  queryTrayScanSummary=trayScanService.queryTrayScanSummaryExpress(queryTrayScanConditiondto);
		//传递属性
		trayScanVo.setTrayScanList(queryTrayScanSummary);
		return returnSuccess();
	}
}
