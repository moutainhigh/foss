package com.deppon.foss.module.transfer.gps.server.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.module.transfer.departure.api.server.service.IGpsService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsVehicleDailySummaryDTO;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.gps.inteface.domain.dailyvehicleinfo.DailyVehicleInfo;
import com.deppon.gps.inteface.domain.dailyvehicleinfo.SyncDailyVehicleInfoRequest;

public class SyncDailyVehicleInfoProcessor implements IProcess{
	/**
	 * 日志
	 */
	private static Logger LOGGER = LogManager.getLogger(SyncDailyVehicleInfoProcessor.class);

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
	
	
	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.error("同步车辆日常数据给FOSS开始");
		SyncDailyVehicleInfoRequest request = (SyncDailyVehicleInfoRequest) req;
		List<DailyVehicleInfo> dailyVehicleInfos = request.getDailyVehicleInfoList();
		
		List<GpsVehicleDailySummaryDTO> gpsDtos = new ArrayList<GpsVehicleDailySummaryDTO>();
		try{
		if(CollectionUtils.isNotEmpty(dailyVehicleInfos)){
			for(DailyVehicleInfo temp:dailyVehicleInfos){
				GpsVehicleDailySummaryDTO gpsDto = new GpsVehicleDailySummaryDTO();
				//id
				gpsDto.setId(UUIDUtils.getUUID());
				//车牌号
				gpsDto.setCarnum(temp.getCarnum());
				//数据时间
				gpsDto.setDatadate(temp.getDatadate());
				//组织编码
				gpsDto.setOrgcode(temp.getOrgcode());
				//机构名称
				gpsDto.setOrgname(temp.getOrgname());
				//车辆当日运行次数
				gpsDto.setCountofdailytask(temp.getCountofdailytask()==null?null:new BigDecimal(temp.getCountofdailytask()));
				//车辆当日运行合格次数
				gpsDto.setCountofsuctask(temp.getCountofsuctask()==null?null:new BigDecimal(temp.getCountofsuctask()));
				//车辆当日运行时效
				gpsDto.setAgingofdailytask(temp.getAgingofdailytask()==null?null:new BigDecimal(temp.getAgingofdailytask()));
				//车辆当日自然时间利用率,百分比（不含百分号）
				gpsDto.setRatioofdailytask(temp.getRatioofdailytask()==null?null:new BigDecimal(temp.getRatioofdailytask()));
				//车辆当日总里程
				gpsDto.setTotalofdailymileage(temp.getTotalofdailymileage()==null?null:new BigDecimal(temp.getTotalofdailymileage()));
				//车辆线路实际运营里程
				gpsDto.setTotalofdailytask(temp.getTotalofdailytask()==null?null:new BigDecimal(temp.getTotalofdailytask()));
				//数据创建时间
				gpsDto.setCreatedtime(temp.getCreatedtime());
				//数据同步日期
				gpsDto.setSynchronousDataTime(new Date());
				gpsDtos.add(gpsDto);
				
			}
			gpsService.SynchVehicleDailySummary(gpsDtos);
			LOGGER.error("同步车辆日常数据给FOSS结束");
		}
		}catch(Exception e){
			LOGGER.error("同步车辆日常数据给FOSS系统异常："+ e);
		}
		return null;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("同步一次车辆日常数据给FOSS系统异常："+ req);
		return null;
	}

}
