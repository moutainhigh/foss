package com.deppon.foss.module.transfer.gps.server.ws;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.module.transfer.departure.api.server.service.IGpsService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO;
import com.deppon.gps.inteface.domain.vehposition.SyncVehPositionRequest;
import com.deppon.gps.inteface.domain.vehposition.VehPosition;

public class SyncVehPositionProcessor implements IProcess {
	
	/**
	 * 日志
	 */
	private static Logger LOGGER = LogManager.getLogger(SyncVehPositionProcessor.class);

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
	 * <p>GPS系统同步一次带任务车辆位置及预计到达时间给FOSS系统；</p> 
	 * @author alfred
	 * @date 2014-3-11 下午3:22:57
	 * @param req
	 * @return
	 * @throws ESBBusinessException 
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		SyncVehPositionRequest request = (SyncVehPositionRequest) req;
		List<VehPosition> vehPositions = request.getVehPosition();
		if(CollectionUtils.isNotEmpty(vehPositions)){
			for(VehPosition vehPosition : vehPositions){
				try {
					// 任务车辆标示
					String vehicleId = vehPosition.getTaskNum();
					//获取跟踪时间
					Date gpsDate = vehPosition.getGpsTime();
					//获取到达时间
					Date preArrivalDate = vehPosition.getPreArriveTime();
					//位置
					String position = vehPosition.getPositionText();
					// 业务实体
					GpsNotifyDTO gpsNotifyDTO = new GpsNotifyDTO();
					// 唯一标示符
					gpsNotifyDTO.setVehicleId(vehicleId);
					// 跟踪时间
					gpsNotifyDTO.setTrackingTime(gpsDate);
					// 预计到达时间
					gpsNotifyDTO.setPreArrivalDate(preArrivalDate);
					// 当前位置
					gpsNotifyDTO.setRoadSegmengt(position);
					// 判断标示符是否为空
					if (StringUtils.isEmpty(vehicleId)) {
						LOGGER.debug("GPS在途接口，任务车辆唯一标示：" + vehicleId );
						// 不做处理
						LOGGER.info(gpsNotifyDTO);
					} else {
						gpsService.updateVehicleTrack(gpsNotifyDTO);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return null;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("同步一次带任务车辆位置及预计到达时间给FOSS系统异常："+ req);
		return null;
	}

}
