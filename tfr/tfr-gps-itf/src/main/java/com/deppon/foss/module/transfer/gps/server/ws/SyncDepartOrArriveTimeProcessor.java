package com.deppon.foss.module.transfer.gps.server.ws;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.departure.api.server.service.IGpsService;
import com.deppon.gps.inteface.domain.gpstime.SyncDepartOrArriveTimeRequest;
import com.deppon.gps.inteface.domain.gpstime.SyncDepartOrArriveTimeResponse;

public class SyncDepartOrArriveTimeProcessor implements IProcess {

	/**
	 * 日志
	 */
	private static Logger LOGGER = LogManager.getLogger(SyncDepartOrArriveTimeProcessor.class);
	
	/**
	 * 业务模块接口
	 */
	private IGpsService gpsService;
	
	/**
	 * 设置 业务模块接口.
	 * 
	 * @param gpsService
	 *            the new 业务模块接口
	 */
	public void setGpsService(IGpsService gpsService) {
		this.gpsService = gpsService;
	}
	
	
	
	/**
	 * 
	 * <p>GPS系统同步带任务车辆的出发/到达时间给FOSS系统</p> 
	 * @author alfred
	 * @date 2014-3-20 上午10:30:48
	 * @param req
	 * @return
	 * @throws ESBBusinessException 
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		SyncDepartOrArriveTimeRequest request = (SyncDepartOrArriveTimeRequest) req;
		SyncDepartOrArriveTimeResponse response = new SyncDepartOrArriveTimeResponse();
		//IGpsService gpsService = (IGpsService) WebApplicationContextHolder.getWebApplicationContext().getBean("gpsService");
		
		//gps任务id
		String vehicleId = request.getTaskNum();
		//1、出发   2、到达
		int deptOrDest = request.getDepartOrArrive();
		// 出发时间
		Date gpsDate = request.getGpsTime();
		//出发
		if(deptOrDest==1){
			try {
				if (StringUtils.isEmpty(vehicleId) || gpsDate == null) {
					LOGGER.error("GPS出发接口参数不符合要求");
					LOGGER.error("任务车辆唯一标示：" + vehicleId );
					LOGGER.error("出发时间：" + gpsDate );
					//返回失败
					response.setResult(0);
					response.setDepartOrArrive(1);
					response.setTaskNum(vehicleId);
					response.setReason("GPS出发接口参数不符合要求");
					return response;
				} else {
					LOGGER.error("GPS出发接口参数符合要求");
					LOGGER.error("任务车辆唯一标示：" + vehicleId );
					LOGGER.error("出发时间：" + gpsDate );
					gpsService.notifyStarttime(vehicleId, gpsDate);
					//返回成功
					response.setResult(1);
					response.setDepartOrArrive(1);
					response.setTaskNum(vehicleId);
					LOGGER.error("***********************" + vehicleId );
					return response;
				}
			} catch (BusinessException e) {
				response.setResult(0);
				response.setDepartOrArrive(1);
				response.setTaskNum(vehicleId);
				response.setReason(e.getMessage());
				return response;
			}
		}else if (deptOrDest==2){//到达
			try {
				if (StringUtils.isEmpty(vehicleId) || gpsDate == null) {
					LOGGER.error("GPS到达接口参数不符合要求");
					LOGGER.error("GPS到达接口，任务车辆唯一标示：" +vehicleId );
					LOGGER.error("到达时间：" +gpsDate );
					response.setResult(0);
					response.setDepartOrArrive(1);
					response.setTaskNum(vehicleId);
					response.setReason("GPS出发接口参数不符合要求");
					return response;
				} else {
					LOGGER.error("GPS到达接口参数符合要求");
					LOGGER.error("GPS到达接口，任务车辆唯一标示：" +vehicleId );
					LOGGER.error("到达时间：" +gpsDate );
					gpsService.notifyArrivaltime(vehicleId, gpsDate);
					response.setResult(1);
					response.setDepartOrArrive(2);
					response.setTaskNum(vehicleId);
					return response;
				}
			}  catch (BusinessException e) {
				response.setResult(0);
				response.setDepartOrArrive(2);
				response.setTaskNum(vehicleId);
				response.setReason(e.getMessage());
				return response;
			}
		}else{
			LOGGER.error("GPS传入出发到达类型不符合要求");
			response.setResult(0);
			response.setDepartOrArrive(2);
			response.setTaskNum(vehicleId);
			response.setReason("GPS传入出发到达类型不符合要求");
			return response;
		}
	}

	
	/**
	 * 
	 * <p>异常处理</p> 
	 * @author alfred
	 * @date 2014-3-20 上午10:30:12
	 * @param req
	 * @return
	 * @throws ESBBusinessException 
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("GPS系统同步带任务车辆的出发/到达时间给FOSS系统异常："+ req);
		return null;
	}
	
}
