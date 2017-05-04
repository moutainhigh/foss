package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.IPushUnloadTruckTaskToWKService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTruckTaskForWkService;


/**
* @description FOSS将车辆卸车任务相关信息同步给WK
* @version 1.0
* @author 328768-foss-gaojianfu
* @update 2016年5月10日 上午10:06:54
 */			
public class PushUnloadTruckTaskToWKService extends TheadPool  implements IPushUnloadTruckTaskToWKService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushUnloadTruckTaskToWKService.class);
	
	
	/**
	* @fields tfrNotifyDao
	* @author 328768-foss-gaojianfu
	* @update 2016年5月10日 上午10:06:54
	* @version V1.0
	*/
	private ITfrNotifyDao tfrNotifyDao;
	
	public void setTfrNotifyDao(ITfrNotifyDao tfrNotifyDao) {
		this.tfrNotifyDao = tfrNotifyDao;
	}
	
	private IUnloadTruckTaskForWkService unloadTruckTaskForWkService;


	public void setUnloadTruckTaskForWkService(IUnloadTruckTaskForWkService unloadTruckTaskForWkService) {
		this.unloadTruckTaskForWkService = unloadTruckTaskForWkService;
	}


	@Override
	public int pushUnloadTruckTaskToWk(Map<String,Object> param) {
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
	* @description 业务处理接口,同步车辆卸车任务信息到wk系统
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#businessExecutor(java.lang.Object)
	* @author 328768-foss-gaojianfu
	* @update 2016年5月10日 上午10:35:58
	* @version V1.0
	 */
	@Override
	public void businessExecutor(Object obj) {
		TfrNotifyEntity notifyEntity=(TfrNotifyEntity)obj;
		try {
			LOGGER.info("推送车辆任务详细到快递系统开始"+notifyEntity.getId());
			/*if("SYNC_NEW_EXPRESS_UNLOAD_TASK_TO_WK".equals(notifyEntity.getNotifyType())){
				unloadTruckTaskForWkService.syncNewExpressUnloadTaskToWk(notifyEntity);
			}else if("SYNC_UPDATE_EXPRESS_UNLOAD_TASK".equals(notifyEntity.getNotifyType())){
				unloadTruckTaskForWkService.syncupdateExpressUnloadTask(notifyEntity);
			}else*/ 
			if("SYNC_CANCEL_UNLOAD_TASK_TO_WK".equals(notifyEntity.getNotifyType())){
				/**
				 * SYNC_CANCEL_UNLOAD_TASK_TO_WK
				 * 同步取消卸车任务到WK系统
				 */
				unloadTruckTaskForWkService.syncCancelUnloadTaskToWk(notifyEntity);
			}/*else if("SYNC_CANCEL_ASSIGN_UNLOAD_TASK_TO_WK".equals(notifyEntity.getNotifyType())){
				unloadTruckTaskForWkService.syncCancelAssignUnloadTaskToWk(notifyEntity);
			}else if("SYNC_ASSIGN_UNLOAD_TASK_TO_WK".equals(notifyEntity.getNotifyType())){
				unloadTruckTaskForWkService.syncAssignUnloadTaskToWk(notifyEntity);
			}*/
			else{
				/**
				 * SYNC_CONFIRM_EXPRESS_UNLOAD_TASK_TO_WK
				 * FOSS同步确认卸车任务到WK
				 */
				unloadTruckTaskForWkService.syncConfirmExpressUnloadTaskToWk(notifyEntity);
			}
			
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
