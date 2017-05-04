package com.deppon.foss.module.transfer.unload.server.action;
/*
 * Action开发规范
 * 
 * 1.必须继承com.deppon.foss.framework.server.web.action.AbstractAction
 * 
 * 2.必须生成serialVersionUID
 * 
 * 3.类名必须以Action结尾
 * 
 * 4.前台传参必须封装到Vo中,不能直接使用Entity,Vo必须添加getter、setter方法
 * 
 * 5.方法上必须添加com.deppon.foss.framework.server.web.result.json.annotation.JSON @JSON 注解
 * 
 * 6.方法中必须try catch异常,成功调用returnSuccess系列重载函数,异常调用returnError系列重载函数
 * 
 * 7.禁止添加Service的getter方法
 * 
 * 8.禁止注入Dao、只允许注入Service
 */
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.vo.TrayScanVo;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadbindTrayService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayQueryConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.UnloadbindTrayVo;

public class TrayBindAction extends AbstractAction{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//卸车任务绑定托盘
    private UnloadbindTrayVo unloadbindTrayVo;
     
	/**
	 * 托盘扫描Vo
	 * */
	TrayScanVo trayScanVo = new TrayScanVo();
	
    //IUnloadbindTrayService
    private IUnloadbindTrayService unloadbindTrayService;
    
    private IConfigurationParamsService configurationParamsService;
    
    
    
    //导出卸车任务绑定托盘
    private String excelFileName; 
	/**
	 * 导出卸车绑定托盘任务
	 */
    
    transient InputStream unloadTaskbindTrayExcelStream;
	
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(TrayBindAction.class);
	
    public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getUnloadTaskbindTrayExcelStream() {
		return unloadTaskbindTrayExcelStream;
	}

	public void setUnloadTaskbindTrayExcelStream(
			InputStream unloadTaskbindTrayExcelStream) {
		this.unloadTaskbindTrayExcelStream = unloadTaskbindTrayExcelStream;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/*
     * @author wqh
     * @date   2013-12-27
     * @desc 查询卸车托盘绑定管理信息     分页 零担
     * */
	@JSON
	public String queryUnloadTaskbindTrayList()
	{
		
		List<UnloadbindTrayEntity> unloadbindTrayList;
    	UnloadbindTrayQueryConditionDto condition =new UnloadbindTrayQueryConditionDto();
    	//卸车任务号
    	String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
    	if(StringUtils.isNotEmpty(unloadTaskNo))
    	{
    		condition.setUnloadTaskNo(unloadTaskNo);
    	}
    	//车牌号
    	String vehicleNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getVehicleNo();
    	if(StringUtils.isNotEmpty(vehicleNo))
    	{
    		condition.setVehicleNo(vehicleNo);
    		
    	}
    	//创建人code
    	String createCode=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreaterCode();
    	if(StringUtils.isNotEmpty(createCode))
    	{
    		condition.setCreaterCode(createCode);
    	}
    	//创建时间
    	condition.setCreateStartDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateStartDate());
    	condition.setCreateEndDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateEndDate());
    	try {
    		//访问service
    		unloadbindTrayList=unloadbindTrayService.queryUnloadTaskbindTrayList(condition,this.getLimit(),this.getStart());
    		//设置任务总数,前台分页数
    		long totalCount=unloadbindTrayService.queryUnloadTaskbindTray(condition).size();
    		
    		this.setTotalCount(totalCount);
    		
    		unloadbindTrayVo.setUnloadbindTrayList(unloadbindTrayList);
    		
		} catch (TfrBusinessException e) {
			LOGGER.error(e+"");
			return this.returnError(e);
		}
    	
    	return this.returnSuccess();
	}
	
	/*
     * @author wqh
     * @date   2016-4-22
     * @desc 查询卸车托盘绑定管理信息     分页 快递
     * */
	@JSON
	public String queryUnloadTaskbindTrayListExpress()
	{
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("查询卸车托盘绑定管理信息开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		List<UnloadbindTrayExpressEntity> unloadbindTrayList;
		UnloadbindTrayQueryConditionDto condition = unloadbindTrayVo.getUnloadbindTrayQueryConditionDto();
		// 获取当前用户的部门编号
		String userDeptCode = FossUserContext.getCurrentDeptCode();
		condition.setOptionOrgCode(userDeptCode);
		try {
			// 分页转换
			int page = (this.getStart() / this.getLimit()) + 1;
			int size = this.getLimit();
			LOGGER.info("进到方法体了，  page=" + page + "    size=" + size);

			// 访问service
			unloadbindTrayList = unloadbindTrayService.queryUnloadTaskbindTrayListExpress(condition, size, page);
			LOGGER.info("返回参数=" + unloadbindTrayList.toString());
			// 设置任务总数,前台分页数
			long totalCount = unloadbindTrayList.get(0).getCount();
			LOGGER.info("数据总数=" + totalCount);
			this.setTotalCount(totalCount);

			unloadbindTrayVo.setUnloadbindTrayListExpress(unloadbindTrayList);

		} catch (TfrBusinessException e) {
			LOGGER.error(e+"");
			return this.returnError(e);
		}
		return this.returnSuccess();
	}
	

	/*
	 *  零担
     * @author wqh
     * @date   2013-12-27
     * @desc 查询卸车托盘绑定管理信息
     * */
	@JSON
	public String queryUnloadTaskbindTray()
	{
		
		List<UnloadbindTrayEntity> unloadbindTrayList;
    	UnloadbindTrayQueryConditionDto condition =new UnloadbindTrayQueryConditionDto();
    	//卸车任务号
    	String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
    	if(StringUtils.isNotEmpty(unloadTaskNo))
    	{
    		condition.setUnloadTaskNo(unloadTaskNo);
    	}
    	//车牌号
    	String vehicleNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getVehicleNo();
    	if(StringUtils.isNotEmpty(vehicleNo))
    	{
    		condition.setVehicleNo(vehicleNo);
    		
    	}
    	//创建人code
    	String createCode=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreaterCode();
    	if(StringUtils.isNotEmpty(createCode))
    	{
    		condition.setCreaterCode(createCode);
    	}
    	//创建时间
    	condition.setCreateStartDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateStartDate());
    	condition.setCreateEndDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateEndDate());
    	try {
    		//访问service
    		unloadbindTrayList=unloadbindTrayService.queryUnloadTaskbindTray(condition);
    		unloadbindTrayVo.setUnloadbindTrayList(unloadbindTrayList);
		} catch (TfrBusinessException e) {
			LOGGER.error(e+"");
			return this.returnError(e);
		}
    	
    	return this.returnSuccess();
	}
	
	/*
	 *  快递
     * @author wqh
     * @date   2013-12-27
     * @desc 查询卸车托盘绑定管理信息
     * */
	@JSON
	public String queryUnloadTaskbindTrayExpress()
	{
		
		List<UnloadbindTrayExpressEntity> unloadbindTrayList;
    	UnloadbindTrayQueryConditionDto condition =new UnloadbindTrayQueryConditionDto();
    	//当前用户部门编号
    	condition.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
    	//卸车任务号
    	String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
    	if(StringUtils.isNotEmpty(unloadTaskNo))
    	{
    		condition.setUnloadTaskNo(unloadTaskNo);
    	}
    	//车牌号
    	String vehicleNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getVehicleNo();
    	if(StringUtils.isNotEmpty(vehicleNo))
    	{
    		condition.setVehicleNo(vehicleNo);
    		
    	}
    	//创建人code
    	String createCode=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreaterCode();
    	if(StringUtils.isNotEmpty(createCode))
    	{
    		condition.setCreaterCode(createCode);
    	}
    	//创建时间
    	condition.setCreateStartDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateStartDate());
    	condition.setCreateEndDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateEndDate());
    	try {
    		System.out.println("访问参数="+condition.toString());
    		//访问service
    		unloadbindTrayList=unloadbindTrayService.queryUnloadTaskbindTrayExpress(condition);
    		unloadbindTrayVo.setUnloadbindTrayListExpress(unloadbindTrayList);
		} catch (TfrBusinessException e) {
			LOGGER.error(e+"");
			return this.returnError(e);
		}
    	
    	return this.returnSuccess();
	}
	
	/*
	 *  零担
     * @author wqh
     * @date   2014-01-03
     * @desc 根据卸车任务号查询卸车绑定托盘明细  分页
     * */
	@JSON
	public String queryUnloadbindTrayDetailByUnloadTaskNo()
	{
		
		//卸车任务编号
		String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
		try {
			//访问service
			//设置任务总数,前台分页数
    		long totalCount=unloadbindTrayService.queryUnloadbindTrayDetailListByUnloadTaskNo(unloadTaskNo).size();
    		
    		this.setTotalCount(totalCount);
    		
			unloadbindTrayVo.setUnloadBindTrayDetailList(unloadbindTrayService.queryUnloadbindTrayDetailByUnloadTaskNo
					(unloadTaskNo, this.getLimit(),this.getStart()));
		} catch (TfrBusinessException e) {
			return this.returnError(e.getMessage());
		}
		return this.returnSuccess();
	}
	
	/*
	 *  快递
     * @author wqh
     * @date   2014-01-03
     * @desc 根据卸车任务号查询卸车绑定托盘明细  分页
     * */
	@JSON
	public String queryUnloadbindTrayDetailByUnloadTaskNoExpress()
	{
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("根据卸车任务号查询快递卸车绑定托盘明细开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		List<UnloadbindTrayDetailDto> result;
		//卸车任务编号
		String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
		//当前用户部门编号
    	String deptCode=FossUserContext.getCurrentDeptCode();
		try {
			//分页转换
    		int page=(this.getStart()/this.getLimit())+1;
    		int size=this.getLimit();
			//访问service
			result=unloadbindTrayService.queryUnloadbindTrayDetailByUnloadTaskNoExpress(unloadTaskNo,deptCode, size,page);
			//设置任务总数,前台分页数
    		long totalCount=result.get(0).getCount();
    		
    		this.setTotalCount(totalCount);
    		
			unloadbindTrayVo.setUnloadBindTrayDetailList(result);
		} catch (TfrBusinessException e) {
			return this.returnError(e.getMessage());
		}
		return this.returnSuccess();
	}
	
	/*
     * @author wqh
     * @date   2014-01-03
     * @desc 根据卸车任务号查询卸车绑定托盘明细 
     * */
	@JSON
	public String queryUnloadbindTrayDetailListByUnloadTaskNo()
	{
		
		//卸车任务编号
		String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
		try {
			//访问service
			unloadbindTrayVo.setUnloadBindTrayDetailList(unloadbindTrayService.queryUnloadbindTrayDetailListByUnloadTaskNo
					(unloadTaskNo));
			
		} catch (TfrBusinessException e) {
			return this.returnError(e.getMessage());
		}
		return this.returnSuccess();
	}
	
	/**
	 * @function：查询托盘扫描明细
	 * @author: wqh
	 * @date: 2014-01-09
	 * */
	@JSON
	public String queryTrayScanDetailByTaskNoAndWaybillNo(){
		//获取查询条件任务号
		String taskNo=unloadbindTrayVo.getTaskNo();
		//获取运单号
		String waybillNo=unloadbindTrayVo.getWaybillNo();
		try {
			//查询托盘扫描任务明细
			List<TrayScanDetaiEntity> serialNoList = unloadbindTrayService.queryTrayScanDetailByTaskNoAndWaybillNo(taskNo,waybillNo);
			//设置托盘扫描明细
			trayScanVo.setSerialNoList(serialNoList);
			
		} catch (TfrBusinessException e) {
			return this.returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/*
	 *   零担
     * @author wqh
     * @date   2013-12-30
     * @desc 导出卸车托盘绑定管理信息     
     * */
	@SuppressWarnings("rawtypes")
	public String exportUnloadTaskbindTrayExcel()
	{
		//调用service获取文件名，输入流
    	List list = null;
    	UnloadbindTrayQueryConditionDto condition =new UnloadbindTrayQueryConditionDto();
    	//卸车任务号
    	String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
    	if(StringUtils.isNotEmpty(unloadTaskNo))
    	{
    		condition.setUnloadTaskNo(unloadTaskNo);
    	}
    	//车牌号
    	String vehicleNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getVehicleNo();
    	if(StringUtils.isNotEmpty(vehicleNo))
    	{
    		condition.setVehicleNo(vehicleNo);
    		
    	}
    	//创建人code
    	String createCode=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreaterCode();
    	if(StringUtils.isNotEmpty(createCode))
    	{
    		condition.setCreaterCode(createCode);
    	}
    	//创建时间
    	condition.setCreateStartDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateStartDate());
    	condition.setCreateEndDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateEndDate());
    	
    	try {
    		list=unloadbindTrayService.exportUnloadTaskbindTrayExcel(condition);
    		excelFileName =(String) list.get(0);
    		unloadTaskbindTrayExcelStream = (InputStream)list.get(1);
    		
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			LOGGER.error(e+"");
			return returnError(e.getMessage());
			
		}
    	
    	return this.returnSuccess();
	}
	
	/*
	 *   快递
     * @author wqh
     * @date   2013-12-30
     * @desc 导出卸车托盘绑定管理信息     
     * */
	@SuppressWarnings("rawtypes")
	public String exportUnloadTaskbindTrayExcelExpress()
	{
		//调用service获取文件名，输入流
    	List list = null;
    	UnloadbindTrayQueryConditionDto condition =new UnloadbindTrayQueryConditionDto();
    	//卸车任务号
    	String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
    	if(StringUtils.isNotEmpty(unloadTaskNo))
    	{
    		condition.setUnloadTaskNo(unloadTaskNo);
    	}
    	//车牌号
    	String vehicleNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getVehicleNo();
    	if(StringUtils.isNotEmpty(vehicleNo))
    	{
    		condition.setVehicleNo(vehicleNo);
    		
    	}
    	//创建人code
    	String createCode=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreaterCode();
    	if(StringUtils.isNotEmpty(createCode))
    	{
    		condition.setCreaterCode(createCode);
    	}
    	//创建时间
    	condition.setCreateStartDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateStartDate());
    	condition.setCreateEndDate(unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getCreateEndDate());
    	//当前用户的部门信息
    	condition.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
    	try {
    		list=unloadbindTrayService.exportUnloadTaskbindTrayExcelExpress(condition);
    		excelFileName =(String) list.get(0);
    		unloadTaskbindTrayExcelStream = (InputStream)list.get(1);
    		
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			LOGGER.error(e+"");
			return returnError(e.getMessage());
			
		}
    	
    	return this.returnSuccess();
	}
	
	/*
	 *  零担
     * @author wqh
     * @date   2013-12-30
     * @desc 导出卸车绑定托盘明细 
     * */
	@SuppressWarnings("rawtypes")
	public String exportUnloadTaskbindTrayDetailExcel()
	{
		//卸车任务编号
		String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
		//调用service获取文件名，输入流
    	List list = null;
    	try {
    		list=unloadbindTrayService.exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo(unloadTaskNo);
    		excelFileName =(String) list.get(0);
    		unloadTaskbindTrayExcelStream = (InputStream)list.get(1);
    		
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			LOGGER.error(e+"");
			return returnError(e.getMessage());
			
		}
    	return this.returnSuccess();
		
	}
	
	/*
	 *  快递
     * @author wqh
     * @date   2013-12-30
     * @desc 导出卸车绑定托盘明细 
     * */
	@SuppressWarnings("rawtypes")
	public String exportUnloadTaskbindTrayDetailExcelExpress()
	{
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("导出卸车绑定托盘明细 开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		//卸车任务编号
		String unloadTaskNo=unloadbindTrayVo.getUnloadbindTrayQueryConditionDto().getUnloadTaskNo();
		//当前用户的部门信息
		String deptCode=FossUserContext.getCurrentDeptCode();
		//调用service获取文件名，输入流
    	List list = null;
    	try {
    		list=unloadbindTrayService.exportUnloadTaskbindTrayDetailExcelByUnloadTaskNoExpress(unloadTaskNo,deptCode);
    		excelFileName =(String) list.get(0);
    		unloadTaskbindTrayExcelStream = (InputStream)list.get(1);
    		
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			LOGGER.error(e+"");
			return returnError(e.getMessage());
			
		}
    	return this.returnSuccess();
		
	}
	
	public void setUnloadbindTrayService(
			IUnloadbindTrayService unloadbindTrayService) {
		this.unloadbindTrayService = unloadbindTrayService;
	}


	public UnloadbindTrayVo getUnloadbindTrayVo() {
		return unloadbindTrayVo;
	}

	public void setUnloadbindTrayVo(UnloadbindTrayVo unloadbindTrayVo) {
		this.unloadbindTrayVo = unloadbindTrayVo;
	}

	public TrayScanVo getTrayScanVo() {
		return trayScanVo;
	}

	public void setTrayScanVo(TrayScanVo trayScanVo) {
		this.trayScanVo = trayScanVo;
	}

	
	
	
}
