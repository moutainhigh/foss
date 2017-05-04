package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.platform.api.server.service.IQueryUnloadingPlatformService;
import com.deppon.foss.module.transfer.platform.api.shared.define.UnloadPlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.QueryUnloadingPlatformVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;



public class QueryUnloadingPlatformAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5830803984693538711L;
	
	/**
	 * 查询卸车进度service
	 */
	private IQueryUnloadingPlatformService queryUnloadingPlatformService;
	/**
	 * vo
	 */
	private QueryUnloadingPlatformVo queryUnloadingProgressVo = new QueryUnloadingPlatformVo();
	
	private final Logger LOGGER = LoggerFactory.getLogger(QueryUnloadingPlatformAction.class);
	
	/**
	 * 任务ID 页面传参
	* @fields taskId
	* @author 14022-foss-songjie
	* @update 2014年4月17日 上午10:47:34
	* @version V1.0
	*/
	private String taskId;
	
	
	/**
	 * 导出Excel 文件流
	* @fields excelStream
	* @author 14022-foss-songjie
	* @update 2014年4月19日 下午2:40:35
	* @version V1.0
	*/
	transient InputStream excelStream;
	
	/**
	 * 导出Excel 文件名
	* @fields fileName
	* @author 14022-foss-songjie
	* @update 2014年4月19日 下午2:40:35
	* @version V1.0
	*/
	private String fileName;
	
	
	/**
	* @description 首页跳转
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月11日 下午4:52:08
	*/
	public String queryunloadingprogressindex(){
		return SUCCESS;
	}
	
	
	/**
	* @description 页面跳转
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月11日 下午4:52:52
	*/
	public String queryunloadingprogressindex_include(){
		return SUCCESS;
	}
	
	
	/**
	* @description 未卸车明细
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月15日 下午4:23:14
	*/
	public String noUnloadingProgressDetail(){
		queryUnloadingProgressVo.setTaskId(taskId);
		return SUCCESS;
	}
	
	
	/**
	* @description 卡货
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:17:12
	*/
	@JSON
	public String queryNoUnloadGoodsDetailKH(){
		queryUnloadingProgressVo.setNoUnloadGoodsDetailListKH(queryUnloadingPlatformService.queryNoUnloadGoodsDetailListKH(queryUnloadingProgressVo.getTaskId(), start, limit));
		queryUnloadingProgressVo.setTotalPageSize(queryUnloadingPlatformService.queryNoUnloadGoodsDetailListKHCount(queryUnloadingProgressVo.getTaskId()));
		return SUCCESS;
	}
	
	
	/**
	* @description  卡货 导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 下午2:43:44
	*/
	@JSON
	public String exportNoUnloadGoodsDetailKH(){
		try{
			try {
				fileName = this.encodeFileName(UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_KH_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("QueryUnloadingPlatformAction.exportNoUnloadGoodsDetailKH 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = queryUnloadingPlatformService.exportNoUnloadGoodsDetailKH(queryUnloadingProgressVo.getTaskId());
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	* @description 城际
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:17:43
	*/
	@JSON
	public String queryNoUnloadGoodsDetailCJ(){
		queryUnloadingProgressVo.setNoUnloadGoodsDetailListCJ(queryUnloadingPlatformService.queryNoUnloadGoodsDetailListCJ(queryUnloadingProgressVo.getTaskId(), start, limit));
		queryUnloadingProgressVo.setTotalPageSize(queryUnloadingPlatformService.queryNoUnloadGoodsDetailListCJCount(queryUnloadingProgressVo.getTaskId()));
		return SUCCESS;
	}
	
	
	/**
	* @description 城际 导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 下午2:47:02
	*/
	@JSON
	public String exportNoUnloadGoodsDetailCJ(){
		try{
			try {
				fileName = this.encodeFileName(UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("QueryUnloadingPlatformAction.exportNoUnloadGoodsDetailCJ 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = queryUnloadingPlatformService.exportNoUnloadGoodsDetailCJ(queryUnloadingProgressVo.getTaskId());
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * 查询卸车进度信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:12:03
	 */
	public String queryUnloadingProgressInfo(){
		try {
			//查询卸车进度
			List<QueryUnloadingPlatformResultDto> unloadingProgressList = 
					queryUnloadingPlatformService.queryUnloadingProgressInfo(
							queryUnloadingProgressVo.getQueryUnloadingProgressConditionDto(),
							queryUnloadingProgressVo.getPageSize(),
							queryUnloadingProgressVo.getInitStart());
			//查询卸车进度
			super.totalCount = 
					queryUnloadingPlatformService.queryUnloadingProgressInfoCount(
							queryUnloadingProgressVo.getQueryUnloadingProgressConditionDto());
			
			//共几页
			Long totalPageSize=super.totalCount%queryUnloadingProgressVo.getPageSize()==0?super.totalCount/queryUnloadingProgressVo.getPageSize():super.totalCount/queryUnloadingProgressVo.getPageSize()+1;
			queryUnloadingProgressVo.setTotalPageSize(totalPageSize);
			//当前第几页
			Long currentPageSize=(long) (queryUnloadingProgressVo.getInitStart()%queryUnloadingProgressVo.getPageSize()==0?queryUnloadingProgressVo.getInitStart()/queryUnloadingProgressVo.getPageSize()+1:queryUnloadingProgressVo.getInitStart()/queryUnloadingProgressVo.getPageSize()+2);
			queryUnloadingProgressVo.setCurrentPageSize(currentPageSize);
			
			
			queryUnloadingProgressVo.setQueryUnloadingProgressResultDtoList(unloadingProgressList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 查询当前部门的卸车标准
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-19 上午8:17:47
	 */
	public String queryLoadAndUnloadStd(){
		try {
			LoadAndUnloadStandardDto standardDto = queryUnloadingPlatformService.queryLoadAndUnloadStd();
			queryUnloadingProgressVo.setLoadAndUnloadStandardDto(standardDto);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	

	/**
	 * 转换导出文件的文件名
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @description 转换导出文件的文件名
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月19日 下午2:42:01
	 */
	public String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(name, "UTF-8");
		}
		return returnStr;
	}
	
	
	/**
	 * 获取 vo.
	 *
	 * @return the vo
	 */
	public QueryUnloadingPlatformVo getQueryUnloadingProgressVo() {
		return queryUnloadingProgressVo;
	}
	
	/**
	 * 设置 vo.
	 *
	 * @param queryUnloadingProgressVo the new vo
	 */
	public void setQueryUnloadingProgressVo(
			QueryUnloadingPlatformVo queryUnloadingProgressVo) {
		this.queryUnloadingProgressVo = queryUnloadingProgressVo;
	}
	
	/**
	 * 设置 查询卸车进度service.
	 *
	 * @param queryUnloadingProgressService the new 查询卸车进度service
	 */

	public void setQueryUnloadingPlatformService(
			IQueryUnloadingPlatformService queryUnloadingPlatformService) {
		this.queryUnloadingPlatformService = queryUnloadingPlatformService;
	}


	public String getTaskId() {
		return taskId;
	}




	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	
}
