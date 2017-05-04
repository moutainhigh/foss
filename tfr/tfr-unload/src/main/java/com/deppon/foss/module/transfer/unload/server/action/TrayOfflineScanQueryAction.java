package com.deppon.foss.module.transfer.unload.server.action;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.unload.api.server.service.ITrayOfflineScanService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryTrayOfflineScanConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.TrayOfflineScanVo;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanExpressEntity;

public class TrayOfflineScanQueryAction extends AbstractAction{
	
	private static final long serialVersionUID = 2540775675939326887L;

	/**
	 * 定义service
	 * */
	ITrayOfflineScanService trayOfflineScanService;
	
	/**
	 * 托盘扫描Vo
	 * */
	TrayOfflineScanVo trayOfflineScanVo = new TrayOfflineScanVo();
	
	
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String excelFileName; 
	
	/**
	 * 导出托盘扫描任务
	 */
	private InputStream trayOfflineScanExcelStream;
	
	private IConfigurationParamsService configurationParamsService;
	
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getTrayOfflineScanExcelStream() {
		return trayOfflineScanExcelStream;
	}

	public void setTrayOfflineScanExcelStream(InputStream trayOfflineScanExcelStream) {
		this.trayOfflineScanExcelStream = trayOfflineScanExcelStream;
	}
	public void setTrayOfflineScanService(
			ITrayOfflineScanService trayOfflineScanService) {
		this.trayOfflineScanService = trayOfflineScanService;
	}

	public void setTrayOfflineScanVo(TrayOfflineScanVo trayOfflineScanVo) {
		this.trayOfflineScanVo = trayOfflineScanVo;
	}


	public TrayOfflineScanVo getTrayOfflineScanVo() {
		return trayOfflineScanVo;
	}

	/**
	 * 查询托盘离线扫描信息 零担
	 * @author 105869-foss-heyongdong
	 * @date 2014年1月2日 08:33:35
	 */
	@JSON
	public String queryTrayOfflineScanList(){
		QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto=trayOfflineScanVo.getQueryTrayOfflineScanConditionDto();
		List<TrayOfflineScanEntity> trayOfflineScanList= trayOfflineScanService.querytrayOfflineScanInfo(queryTrayOfflineScanConditionDto,this.getLimit(),this.getStart());
		trayOfflineScanVo.setTrayOfflineScanList(trayOfflineScanList);
		Long totalCount = trayOfflineScanService.querytrayOfflineScanTotal(queryTrayOfflineScanConditionDto);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**
	 * 查询托盘离线扫描信息 快递
	 * @author 105869-foss-heyongdong
	 * @date 2014年1月2日 08:33:35
	 */
	@JSON
	public String queryTrayOfflineScanListExpress(){
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("查询托盘离线扫描信息开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto=trayOfflineScanVo.getQueryTrayOfflineScanConditionDto();
		//获取当前用户的部门
		queryTrayOfflineScanConditionDto.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
		System.out.println("带进来的参数="+queryTrayOfflineScanConditionDto.toString());
		//分页转换
		int page=(this.getStart()/this.getLimit())+1;
		int size=this.getLimit();
		
		List<TrayOfflineScanExpressEntity> trayOfflineScanList= trayOfflineScanService.querytrayOfflineScanInfoExpress(queryTrayOfflineScanConditionDto,size,page);
		trayOfflineScanVo.setTrayOfflineScanListExpress(trayOfflineScanList);
		Long totalCount=0l;
		if(trayOfflineScanList.size() != 0){
			totalCount= trayOfflineScanList.get(0).getCount();
		}
		 
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

	
	/**
	 *  零担
	 * @function：导出叉车离线扫描信息
	 * @author: 105869-foss-heyongdong
	 * @date: 2014年1月8日 10:46:37
	 * */
	@SuppressWarnings("rawtypes")
	public String exportTrayOfflineScanTaskExcel(){
		//获取查询条件
		QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto = trayOfflineScanVo.getQueryTrayOfflineScanConditionDto();
		//调用service获取文件名，输入流
		List list = null;
		try{
			//调用service
			list = trayOfflineScanService.getTrayOfflineScanTaskInputStream(queryTrayOfflineScanConditionDto);
		}catch(BusinessException e){
			return returnError(e);
		}
		excelFileName =(String) list.get(0);
		trayOfflineScanExcelStream = (InputStream)list.get(1);
		return returnSuccess();
	}
	
	/**
	 *  快递
	 * @function：导出叉车离线扫描信息
	 * @author: 105869-foss-heyongdong
	 * @date: 2014年1月8日 10:46:37
	 * */
	@SuppressWarnings("rawtypes")
	public String exportTrayOfflineScanTaskExcelExpress(){
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("导出叉车离线扫描信息开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
		//获取查询条件
		QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto = trayOfflineScanVo.getQueryTrayOfflineScanConditionDto();
		//获取当前用户的部门
		queryTrayOfflineScanConditionDto.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
		//调用service获取文件名，输入流
		List list = null;
		try{
			//调用service
			list = trayOfflineScanService.getTrayOfflineScanTaskInputStreamExpress(queryTrayOfflineScanConditionDto);
		}catch(BusinessException e){
			return returnError(e);
		}
		excelFileName =(String) list.get(0);
		trayOfflineScanExcelStream = (InputStream)list.get(1);
		return returnSuccess();
	}
}
