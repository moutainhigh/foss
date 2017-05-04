package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService;
import com.deppon.foss.module.transfer.platform.api.shared.define.DeliverConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.WaybillAvgTimeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DeliverGoodsAreaQueryVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;

public class DeliverGoodsAreaQueryAction extends AbstractAction {
	
	/**
	 * VO 用于前后台传参
	 */
	private DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo = new DeliverGoodsAreaQueryVo();
	
	/**
	 * 本模块service
	 */
	private IDeliverGoodsAreaQueryService deliverGoodsAreaQueryService;
	
	
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 123423423543534L;
	
	/**
	* @Title: deliverGoodsAreaQuery 
	* @Description: 派送情况查询请求主页面方法
	* @author shiwei shiwei@outlook.com
	* @date 2014年2月28日 下午4:10:11 
	* @param @return  处理结果
	* @return String    返回类型 
	* @throws
	 */
	public String deliverGoodsAreaQuery(){
		//获取服务端时间，返回前台
		Date serverTime = new Date();
		String timeString = DateUtils.convert(serverTime, DateUtils.DATE_TIME_FORMAT);
		
		//返回服务器时间及上级转运场信息，其中上级外场信息可能为空
		deliverGoodsAreaQueryVo.setServerTimeString(timeString);
		String[] outfieldInfo = deliverGoodsAreaQueryService.queryOutfieldInfo();
		if(outfieldInfo != null){
			deliverGoodsAreaQueryVo.setOutfieldCode(outfieldInfo[0]);
			deliverGoodsAreaQueryVo.setOutfieldName(outfieldInfo[1]);
		}
		
		/**
		 *@date 2015-03-30 09:55
		 *@author hongwenyong-218427 修改
		 ***/
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		List<UserOrgRoleEntity> userOrgRoleEntityList = deliverGoodsAreaQueryService.queryOrgRoleByCode(userCode);
		//String code=deliverGoodsAreaQueryService.queryIsTransferCenter(orgCode);
		if(userOrgRoleEntityList!=null){
			for(UserOrgRoleEntity u : userOrgRoleEntityList){
				if(u.getRoleCode().equals("FOSS_OPERATION_ANALYSIS_QUERY")){
					deliverGoodsAreaQueryVo.setIsAnalyst("Y");
					break;
				}else {
					deliverGoodsAreaQueryVo.setIsAnalyst("N");
				}
			}
		}
		return SUCCESS;
	}
	
	
	
	/**
	* @description 日派送率的查询
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:35:03
	*/
	@JSON
	public String sendRateDayQuery(){
		try{
			//获取外场编码
			SendRateEntity dto = deliverGoodsAreaQueryVo.getSendRateEntity();
			if(dto==null){
				dto = new SendRateEntity();
				dto.setOrgCode("");
			}
			//List<SendRateEntity> tt = deliverGoodsAreaQueryService.querySendRateList(dto, start, limit);
			//取消分页
			List<SendRateEntity> tt = deliverGoodsAreaQueryService.querySendRateList(dto, -1, -1);
		this.deliverGoodsAreaQueryVo.setSendRateList(tt); 
		//setTotalCount(deliverGoodsAreaQueryService.querySendRateListCount(deliverGoodsAreaQueryVo.getSendRateEntity()));
		//取消分页
		this.setTotalCount(0L);
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	* @description 日拉回率查询
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:45:56
	*/
	@JSON
	public String pullbackRateDayQuery(){
		try{
			//获取外场编码
			PullbackRateEntity dto = deliverGoodsAreaQueryVo.getPullbackRateEntity();
			if(dto==null){
				dto = new PullbackRateEntity();
				dto.setOrgCode("");
			}
			List<PullbackRateEntity> tt = deliverGoodsAreaQueryService.queryPullbackRateList(dto, start, limit);
		this.deliverGoodsAreaQueryVo.setPullbackRateEntityList(tt); 
		setTotalCount(deliverGoodsAreaQueryService.queryPullbackRateListCount(deliverGoodsAreaQueryVo.getPullbackRateEntity()));
		this.deliverGoodsAreaQueryVo.setServerTimeString(DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT));
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 日退单率查询
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:46:46
	*/
	@JSON
	public String returnRateDayQuery(){
		try{
		//获取外场编码
			ReturnRateEntity dto = deliverGoodsAreaQueryVo.getReturnRateEntity();
			if(dto==null){
				dto = new ReturnRateEntity();
				dto.setOrgCode("");
			}
			List<ReturnRateEntity> tt = deliverGoodsAreaQueryService.queryReturnRateList(dto, start, limit);
		this.deliverGoodsAreaQueryVo.setReturnRateList(tt);
		this.setTotalCount(deliverGoodsAreaQueryService.queryReturnRateListCount(dto));
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 累计日派送率查询
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午6:36:19
	*/
	@JSON
	public String sendRateLogQuery(){
		try{
			//获取外场编码
				SendRateEntity dto = deliverGoodsAreaQueryVo.getSendRateEntity();
				if(dto==null){
					dto = new SendRateEntity();
					dto.setOrgCode("");
				}
				if(StringUtils.isEmpty(dto.getEndDate())){
					return returnError("请输入截至日期");
				}
				dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
				List<SendRateEntity> tt = deliverGoodsAreaQueryService.querySendRateLogList(dto, start, limit);
			this.deliverGoodsAreaQueryVo.setSendRateList(tt);
			this.setTotalCount(deliverGoodsAreaQueryService.querySendRateLogListCount(dto));
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 累计拉回率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午9:34:28
	*/
	@JSON
	public String pullbackRateLogQuery(){
		try{
		//获取外场编码
		PullbackRateEntity dto = deliverGoodsAreaQueryVo.getPullbackRateEntity();
		if(dto==null){
			dto = new PullbackRateEntity();
			dto.setOrgCode("");
		}
		if(StringUtils.isEmpty(dto.getEndDate())){
			return returnError("请输入截至日期");
		}
		dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
		List<PullbackRateEntity> tt = deliverGoodsAreaQueryService.queryPullbackRateLogList(dto, start, limit);
		this.deliverGoodsAreaQueryVo.setPullbackRateEntityList(tt);
		this.setTotalCount(deliverGoodsAreaQueryService.queryPullbackRateLogListCount(dto));
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 累计退单率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:30:33
	*/
	@JSON
	public String returnRateLogQuery(){
		try{
			//获取外场编码
			ReturnRateEntity dto = deliverGoodsAreaQueryVo.getReturnRateEntity();
			if(dto==null){
				dto = new ReturnRateEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
			List<ReturnRateEntity> tt = deliverGoodsAreaQueryService.queryReturnRateLogList(dto, start, limit);
			this.deliverGoodsAreaQueryVo.setReturnRateList(tt);
			this.setTotalCount(deliverGoodsAreaQueryService.queryReturnRateLogListCount(dto));
		}catch (Exception e) {
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	
	/**
	* @description 日派送率导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午10:45:15
	*/
	public String sendRateDayExport(){
		try {
			// 文件名
			//获取外场编码
			SendRateEntity dto = deliverGoodsAreaQueryVo.getSendRateEntity();
			if(dto==null){
				dto = new SendRateEntity();
				dto.setOrgCode("");
			}
			try {
				fileName = this.encodeFileName(DeliverConstants.SEND_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("DeliverGoodsAreaQueryAction.sendRateDayExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = deliverGoodsAreaQueryService.sendRateDayExport(dto);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	* @description 导出日退单率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:12:23
	*/
	public String returnRateDayExport(){
		try {
			// 文件名
			//获取外场编码
			ReturnRateEntity dto = deliverGoodsAreaQueryVo.getReturnRateEntity();
			if(dto==null){
				dto = new ReturnRateEntity();
				dto.setOrgCode("");
			}
			try {
				fileName = this.encodeFileName(DeliverConstants.RETURN_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("DeliverGoodsAreaQueryAction.returnRateDayExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = deliverGoodsAreaQueryService.returnRateDayExport(dto);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 导出日拉回率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:56:51
	*/
	public String pullbackRateDayExport(){
		try {
			// 文件名
			PullbackRateEntity dto = deliverGoodsAreaQueryVo.getPullbackRateEntity();
			if(dto==null){
				dto = new PullbackRateEntity();
				dto.setOrgCode("");
			}
			try {
				fileName = this.encodeFileName(DeliverConstants.PULLBACK_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("DeliverGoodsAreaQueryAction.pullbackRateDayExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = deliverGoodsAreaQueryService.pullbackRateDayExport(dto);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 累计派送率导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午6:43:24
	*/
	public String sendRateLogExport(){
		try {
		//获取外场编码
			SendRateEntity dto = deliverGoodsAreaQueryVo.getSendRateEntity();
			if(dto==null){
				dto = new SendRateEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
			try {
				fileName = this.encodeFileName(DeliverConstants.SEND_LOG_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("DeliverGoodsAreaQueryAction.sendRateLogExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = deliverGoodsAreaQueryService.sendRateLogExport(dto);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	* @description 累计拉回率导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午9:35:23
	*/
	public String pullbackRateLogExport(){
		try {
			// 获取外场编码
			PullbackRateEntity dto = deliverGoodsAreaQueryVo
					.getPullbackRateEntity();
			if (dto == null) {
				dto = new PullbackRateEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
			try {
				fileName = this
						.encodeFileName(DeliverConstants.PULLBACK_LOG_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("DeliverGoodsAreaQueryAction.pullbackRateLogExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = deliverGoodsAreaQueryService.pullbackRateLogExport(dto);
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 累计退单率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:30:33
	*/
	@JSON
	public String returnRateLogExport(){
		try{
			//获取外场编码
			ReturnRateEntity dto = deliverGoodsAreaQueryVo.getReturnRateEntity();
			if(dto==null){
				dto = new ReturnRateEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
			try {
				fileName = this.encodeFileName(DeliverConstants.RETURN_LOG_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("DeliverGoodsAreaQueryAction.returnRateLogExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = deliverGoodsAreaQueryService.returnRateLogExport(dto);
		}catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/** 
	 * @return deliverGoodsAreaQueryVo 
	 */
	public DeliverGoodsAreaQueryVo getDeliverGoodsAreaQueryVo() {
		return deliverGoodsAreaQueryVo;
	}

	/** 
	 * @param deliverGoodsAreaQueryVo 要设置的 deliverGoodsAreaQueryVo 
	 */
	public void setDeliverGoodsAreaQueryVo(
			DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo) {
		this.deliverGoodsAreaQueryVo = deliverGoodsAreaQueryVo;
	}

	/** 
	 * @param deliverGoodsAreaQueryService 要设置的 deliverGoodsAreaQueryService 
	 */
	public void setDeliverGoodsAreaQueryService(
			IDeliverGoodsAreaQueryService deliverGoodsAreaQueryService) {
		this.deliverGoodsAreaQueryService = deliverGoodsAreaQueryService;
	}
	
	/**
	 * @description 获取Excel文件名
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年3月6日 下午4:10:40
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @description 设置Excel文件名
	 * @param fileName
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年3月6日 下午4:10:40
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @description 获取导出工作流
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年3月6日 下午4:10:40
	 */
	public InputStream getExcelStream() {
		return excelStream;
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
	 * @update 2014年3月6日 下午4:10:40
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
	* @description 根据截至时间获取开始时间
	* @param endTime
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 下午5:01:50
	*/
	public String beginTimeByEndtime(String endTime){
		if(endTime!=null && endTime.length()>=PlatformConstants.SONAR_NUMBER_10){
			String endTimeSub = endTime.substring(PlatformConstants.SONAR_NUMBER_0,PlatformConstants.SONAR_NUMBER_8); 
			String beginTime = endTimeSub+"01";
			return beginTime;
		}else{
			return "";
		}
	}
	
	/**
	* @description 票均装车时长
	* @return
	* @version 1.0
	* @author 218427-foss-hongwenyong
	* @update 2015年3月24日 上午8:52:28
	*/
	
	public String waybillAvgTimeQuery(){
		
		try {
			//获取外场编码
			
			List<WaybillAvgTimeEntity> waybillAvgTimeEntityList=deliverGoodsAreaQueryService.queryWaybillAvgTimeEntityList(deliverGoodsAreaQueryVo, this.start, limit);
			this.deliverGoodsAreaQueryVo.setWaybillAvgTimeEntityList(waybillAvgTimeEntityList);
			this.setTotalCount(deliverGoodsAreaQueryService.queryWaybillAvgTimeEntityListCount(deliverGoodsAreaQueryVo));
		} catch (Exception e) {
			
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	

	
}
