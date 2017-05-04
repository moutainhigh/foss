package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.ICreateWkTruckTaskService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService;

public class CreateWkTruckTaskService extends TheadPool implements ICreateWkTruckTaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateWkTruckTaskService.class);
	
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
	public int createWkTruckTask(Map<String, Object> param) {
		//LOGGER.info("[生成快递车辆任务]任务通知前更新开始"+param.get("notifyType"));
		List<TfrNotifyEntity> notifyList=tfrNotifyDao.selectTfrNotifyList(param);
		List<String> ids=new ArrayList<String>();
		for(TfrNotifyEntity entity:notifyList){
			ids.add(entity.getId());	
		}
		//**更新通知任务状态*//*
		LOGGER.info("[生成快递车辆任务]任务通知前更新开始"+ids.toString());
		if(ids.size()>=0)
			tfrNotifyDao.updateTfrNotifyIng(ids);
		for(TfrNotifyEntity entity:notifyList){
			//**添加至线程池*//*
			LOGGER.info("[生成快递车辆任务]任务添加到线程池"+entity.getId());
			pushThreadsPool(entity);
		}
		return ids.size();
	}

	@Override
	public void businessExecutor(Object obj) {
		//Integer
		TfrNotifyEntity notifyEntity=(TfrNotifyEntity)obj;
		try {
			LOGGER.info("[生成快递车辆任务]任务开始:"+notifyEntity.getId());
			truckTaskForWkService.createTruckTaskByHandOverBillFromWk(notifyEntity);
		} catch (Exception e) {
			LOGGER.error("[生成快递车辆任务]任务异常:"+notifyEntity.getId(),e);
			notifyEntity.setNotifyErrorInfo(e.getMessage());
			notifyEntity.setJobId("N/A");
			tfrNotifyDao.updateTfrNotifyFail(notifyEntity);
		}
	}

	@Override
	public void outOfUnloadPool(Object obj) {
		TfrNotifyEntity notifyEntity=
				(TfrNotifyEntity)obj;
		notifyEntity.setNotifyNum(new Integer(1));
		notifyEntity.setJobId("N/A");
		notifyEntity.setNotifyErrorInfo("outOfUnloadPool");
		tfrNotifyDao.updateTfrNotifyFail(notifyEntity);
	}

	@Override
	public int getActiveThreads() {
		return TransferConstants.SONAR_NUMBER_50;
	}
}
