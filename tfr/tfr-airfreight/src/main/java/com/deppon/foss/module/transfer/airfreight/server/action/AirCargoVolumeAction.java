/**
 *  initial comments.
 */
/*
 * PROJECT NAME: tfr-airfreight
 * PACKAGE NAME: com.deppon.foss.module.transfer.airfreight.server.action
 * FILE    NAME: AirSpaceAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirCargoVolumeService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeQueryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailVolumeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirCargoVolumeVo;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.util.define.FossConstants;


/**
 *  空运货量管理相关操作
 * @author dp-liming
 * @date 2012-10-18 上午11:30:52    
 */
public class AirCargoVolumeAction extends AbstractAction {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6314197374378137332L;
	/**
	 * 空运货量统计Vo   
	 */
	private AirCargoVolumeVo airCargoVolumeVo;
	/**
	 * 空运货量统计Service
	 */
	private IAirCargoVolumeService airCargoVolumeService;
	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(AirCargoVolumeAction.class);
	
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;
	
	/**
	 * excel名称
	 */
	private String excelName;
	
	/**
	 * 获取 导出输出流.
	 *
	 * @return the 导出输出流
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 设置 导出输出流.
	 *
	 * @param inputStream the new 导出输出流
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * 获取 excel名称.
	 *
	 * @return the excel名称
	 */
	public String getExcelName() {
		return excelName;
	}

	/**
	 * 设置 excel名称.
	 *
	 * @param excelName the new excel名称
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * 查询货量
	 * @author 097457-foss-liming
	 * @date 2012-10-24 上午10:30:23
	 */
	@JSON
	public String  queryAirCargoVolume(){
		try {
			//分页得到空运货量明细列表	
			AirCargoVolumeDto dto = fillQueryParam(airCargoVolumeVo.getAirCargoVolumeEntity(), airCargoVolumeVo.getBeginCreateTime(), 
					airCargoVolumeVo.getEndCreateTime(),airCargoVolumeVo.getAssembleOrgName(),airCargoVolumeVo.getHandoverTimeBegin(),airCargoVolumeVo.getHandoverTimeEnd());
			List<AirCargoVolumeQueryEntity> airCargoVolumeList= airCargoVolumeService.queryAirCargoVolume(dto,this.getLimit(),this.getStart());//分页查询空运货量的信息
			airCargoVolumeVo.setAirCargoVolumeList(airCargoVolumeList);			
			//空运货量总条数
			Long totalCount=airCargoVolumeService.queryCount(dto);
			//设置总数
			this.setTotalCount(totalCount);
		}catch (BusinessException e) {
			LOGGER.error(e+"");
			return returnError(e);
		}		
		return returnSuccess();
	}
	
	public AirCargoVolumeDto fillQueryParam(AirCargoVolumeQueryEntity airCargoVolumeEntity,String beginCreateTime,String endCreateTime,String assembleOrgName,
			String handoverTimeBegin,String handoverTimeEnd){
		//空运货量查询条件dto
		AirCargoVolumeDto dto=new AirCargoVolumeDto();
		//空运货量信息
		dto.setAirCargoVolume(airCargoVolumeEntity);
		//开始时间
		dto.setBeginCreateTime(beginCreateTime);
		//结束时间
		dto.setEndCreateTime(endCreateTime);
		//配载开始时间
		dto.setHandoverTimeBegin(handoverTimeBegin);
		//配载结束时间
		dto.setHandoverTimeEnd(handoverTimeEnd);
		//空调总度
		dto.setAssembleOrgName(assembleOrgName);
		//运单可用
		dto.setActive(FossConstants.YES);
		//运输性质 空运
		dto.setProductCode(WaybillConstants.AIR_FLIGHT);
		//未配载
		dto.setIsLoad(AirfreightConstants.AIRFREIGHT_ISYESLOADING);
		
		List<String> stockStatusList=new ArrayList<String>();
		//如果是已开单未交接
		if (StringUtils.equals(TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE, airCargoVolumeEntity.getStockStatus())) {
			//路径明细状态 未离开 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
			//如果是库存中
		}else if (StringUtils.equals(TransportPathConstants.PATHDETAIL_STATUS_INSTORE, airCargoVolumeEntity.getStockStatus())) {
			//路径明细状态 入库 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_INSTORE);
			//如果是未配载
		}else if (StringUtils.equals(AirfreightConstants.AIRFREIGHT_ISYESLOADING, airCargoVolumeEntity.getStockStatus())) {
			//路径明细状态 未配载
			stockStatusList.add(AirfreightConstants.AIRFREIGHT_ISYESLOADING);
			//如果是已交接未入库
		}else if (StringUtils.equals(TransportPathConstants.PATHDETAIL_STATUS_HANDOVER, airCargoVolumeEntity.getStockStatus())) {
			//路径明细状态 已交接
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_HANDOVER);
			//路径明细状态 离开 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_LEAVE);
			//路径明细状态 抵达 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_ARRIVE);
			//库存未输，为全部
		}else {
			//路径明细状态 未离开 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
			//路径明细状态 入库 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_INSTORE);	
			//路径明细状态 未配载
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_HANDOVER);
			//路径明细状态 已交接
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_LEAVE);
			//路径明细状态 离开 
			stockStatusList.add(TransportPathConstants.PATHDETAIL_STATUS_ARRIVE);
			//路径明细状态 抵达 
			stockStatusList.add(AirfreightConstants.AIRFREIGHT_ISYESLOADING);
		}
		dto.setStockStatusList(stockStatusList);
		return dto;
	}
	
	/**
	 * 查询线路仓位
	 * @author 097457-foss-liming
	 * @date 2012-10-26 上午11:30:39
	 */
	@JSON
	public String queryAirSpace(){
		try {
			//线路仓位明细列表	
			List<AirSpaceDetailVolumeEntity> airSpaceDetailVolumeList=airCargoVolumeService.queryAirSpace(airCargoVolumeVo.getAirSpaceDetaiEntity());
			airCargoVolumeVo.setAirSpaceDetaiList(airSpaceDetailVolumeList);
		} catch (BusinessException e) {
			//出错日志
			LOGGER.error(e+"");
			return returnError(e);
		}
		
		return returnSuccess();
	}
	
	/**
	  *  查询某件货物的库存信息
	 * @author 097457-foss-liming
	 * @date 2012-10-24 上午9:30:30
	 */
	@JSON
	public String queryAirCargoVolumeTotal(){
		try {
			AirCargoVolumeDto dto = fillQueryParam(airCargoVolumeVo.getAirCargoVolumeEntity(), airCargoVolumeVo.getBeginCreateTime(), 
					airCargoVolumeVo.getEndCreateTime(),airCargoVolumeVo.getAssembleOrgName(),airCargoVolumeVo.getHandoverTimeBegin(),airCargoVolumeVo.getHandoverTimeEnd());
			//得到货量的总重量和总体积	
			List<AirCargoVolumeQueryEntity> airCargoVolumeTotalList=airCargoVolumeService.querySerialNumberStock(dto);	
			//统计列表
			airCargoVolumeVo.setAirCargoVolumeList(airCargoVolumeTotalList);
		} catch (BusinessException e) {
			//出错日志
			LOGGER.error(e+"");
			return returnError(e);
		}
		
		return returnSuccess();
	}
	
	/**
	 * 导出excel
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-29 上午11:17:53
	 */
	public String queryAirCargoVolumeForExport(){
		try{
			//excel名称
			String returnStr;
			String name = AirfreightConstants.AIR_CARGOVOLUME_EXCEL_NAME;
	    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
	    	if(agent != null && agent.indexOf("MSIE") == -1) {
	    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
	    	} else {
	    		returnStr = URLEncoder.encode(name, "UTF-8");
	    	}
			excelName = returnStr;
			//set查询条件
			AirCargoVolumeDto dto = fillQueryParam(airCargoVolumeVo.getAirCargoVolumeEntity(), airCargoVolumeVo.getBeginCreateTime(), 
					airCargoVolumeVo.getEndCreateTime(),airCargoVolumeVo.getAssembleOrgName(),airCargoVolumeVo.getHandoverTimeBegin(),airCargoVolumeVo.getHandoverTimeEnd());
			//获取stream，导出
			inputStream = airCargoVolumeService.exportAirCargoVolume(dto);
		}catch(BusinessException e){
			//出错日志
			LOGGER.error(e+"");
			return returnError(e);
		}catch(UnsupportedEncodingException  e1){
			//打印异常日志
			LOGGER.error(e1.getMessage(),e1);
			return returnError(e1.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 设置 空运货量统计Vo.
	 *
	 * @param airCargoVolumeVo the new 空运货量统计Vo
	 */
	public void setAirCargoVolumeVo(AirCargoVolumeVo airCargoVolumeVo) {
		this.airCargoVolumeVo = airCargoVolumeVo;
	}

	/**
	 * 设置 空运货量统计Service.
	 *
	 * @param airCargoVolumeService the new 空运货量统计Service
	 */
	public void setAirCargoVolumeService(IAirCargoVolumeService airCargoVolumeService) {
		this.airCargoVolumeService = airCargoVolumeService;
	}

	/**
	 * 获取 空运货量统计Vo.
	 *
	 * @return the 空运货量统计Vo
	 */
	public AirCargoVolumeVo getAirCargoVolumeVo() {
		return airCargoVolumeVo;
	}

	
	
}