package com.deppon.foss.module.transfer.common.api.server.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;

public interface ITPSToFOSSService extends IService{
	/**
	 * TPS同步交易信息到FOSS，即TPS审核约车后同步到FOSS，使FOSS约车为审核状态。 
	 * 如果FOSS约车编号为已审核则 不需要再次审核。
	 * @author 105869
	 * @date 2015年1月5日 14:49:22
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value="/synchTradVehicleInfo")
	public ResponseParameterEntity synchTradVehicleInfo(String request);
	
	/**
	 * 
	 * TPS定时取FOSS中外请车发车趟数
	 * 当年1月1号到当日长途外请车和合同车累计发车趟数 
	 * 当月1号到当日累计长途外请车和合同车累计发车趟数
	 * @author 105869
	 * @date 2015年1月5日 14:49:22
	 * 
	 * */
	/**
	 * TPS同步将询价编号，询价信息，询价部门添加到FOSS的询价信息表中
	 * @param request
	 * @return
	 * @author 268084
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value="/synchConsultPriceNo")
	public ResponseParameterEntity synchConsultPriceNo(String request);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value="/cumulativeDepartureTimes")
	public String cumulativeDepartureTimes();
	
	
	
	/**
	 * 目前使用干线外请车需在FOSS系统中约车和TPS进行推送约车信息，
	 * 存在重复性工作，操作环节多，时间长增加一线工作量，故对现有系统进行整合，
	 * 通过上线TPS系统推送报价代替现有操作模式，让TPS系统更好满足业需求。
	 * 
	 * TPS干线的约车信息同步至FOSS接口
	 * @description 用一句话说明这个方法做什么
	 * @param request
	 * @author 283250-foss-liuyi
	 * @date 2016年1月19日 上午9:14:42
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value="/synchAboutTradVehicleInfo")
	public ResponseParameterEntity synchAboutTradVehicleInfo(String request);
}
