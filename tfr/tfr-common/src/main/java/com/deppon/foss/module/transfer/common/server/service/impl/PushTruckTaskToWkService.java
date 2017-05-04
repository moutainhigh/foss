package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.IPushTruckTaskToWkService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService;


/**
* @description FOSS将任务车辆相关信息同步给WK
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月22日 下午2:12:04
*/
public class PushTruckTaskToWkService extends TheadPool  implements IPushTruckTaskToWkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushTruckTaskToWkService.class);
	
	
	/**
	* @fields tfrNotifyDao
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 下午3:12:37
	* @version V1.0
	*/
	private ITfrNotifyDao tfrNotifyDao;
	
	public void setTfrNotifyDao(ITfrNotifyDao tfrNotifyDao) {
		this.tfrNotifyDao = tfrNotifyDao;
	}
	
	private ITruckTaskForWkService truckTaskForWkService;

	public void setTruckTaskForWkService(
			ITruckTaskForWkService truckTaskForWkService) {
		this.truckTaskForWkService = truckTaskForWkService;
	}

	@Override
	public int pushTruckTaskToWk(Map<String,Object> param) {
		List<TfrNotifyEntity> notifyList=tfrNotifyDao.selectTfrNotifyList(param);
		List<String> ids=new ArrayList<String>();
		for(TfrNotifyEntity entity:notifyList){
			ids.add(entity.getId());	
		}
		//**更新通知任务状态*//*
		LOGGER.info("任务通知前更新开始"+ids.toString());
		if(ids.size()>=0)
			tfrNotifyDao.updateTfrNotifyIng(ids);
		for(TfrNotifyEntity entity:notifyList){
			//**添加至线程池*//*
			LOGGER.info("任务添加到线程池"+entity.getId());
			pushThreadsPool(entity);
		}
		return ids.size();
		
	}

	
	/**
	* @description 业务处理接口,同步车辆任务信息到wk系统
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#businessExecutor(java.lang.Object)
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 下午3:16:46
	* @version V1.0
	*/
	@Override
	public void businessExecutor(Object obj) {
		TfrNotifyEntity notifyEntity=(TfrNotifyEntity)obj;
		try {
			LOGGER.info("推送车辆任务详细到快递系统开始"+notifyEntity.getId());
			truckTaskForWkService.pushTruckTaskToWk(notifyEntity);
		} catch (Exception e) {
			LOGGER.error("推送车辆任务详细到快递系统异常",e);
			notifyEntity.setNotifyErrorInfo(e.getMessage());
			tfrNotifyDao.updateTfrNotifyFail(notifyEntity);
		}
	}

	@Override
	public void outOfUnloadPool(Object obj) {
		TfrNotifyEntity notifyEntity=
				(TfrNotifyEntity)obj;
		notifyEntity.setNotifyErrorInfo("outOfUnloadPool");
		tfrNotifyDao.updateTfrNotifyFail(notifyEntity);
	}

	@Override
	public int getActiveThreads() {
		return TransferConstants.SONAR_NUMBER_20;
	}
	
}
