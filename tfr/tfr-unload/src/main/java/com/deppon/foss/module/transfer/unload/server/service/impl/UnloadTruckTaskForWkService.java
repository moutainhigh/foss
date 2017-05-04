package com.deppon.foss.module.transfer.unload.server.service.impl;

import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTruckTaskForWkService;

	/**
	 * @description 车辆卸车任务service(快递拆分,快递对接FOSS车辆卸车任务service)
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月10日 上午11:10:11
	 */
public class UnloadTruckTaskForWkService implements IUnloadTruckTaskForWkService{

	private IFOSSToWkService fossToWkService;
	
	private ITfrNotifyService tfrNotifyService;
	
	

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}


	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}

	/**
	 * @description FOSS同步新建卸车任务给悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:17:56
	 */
	@Override
	public void syncNewExpressUnloadTaskToWk(TfrNotifyEntity entity) throws Exception {
		String requestParameter=entity.getParamJson();
		//同步数据到悟空系统
		FossToWKResponseEntity resp=fossToWkService.syncNewExpressUnloadTaskToWk(requestParameter);
		
		if("1".equals(resp.getStatus())){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(entity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(resp.getExMsg());
		}
	}

	/**
	 * @description FOSS同步修改卸车任务到悟空系统
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:19:32
	 */
	@Override
	public void syncupdateExpressUnloadTask(TfrNotifyEntity entity) throws Exception {
		String requestParameter=entity.getParamJson();
		//同步数据到悟空系统
		FossToWKResponseEntity resp=fossToWkService.syncupdateExpressUnloadTask(requestParameter);
		
		if("1".equals(resp.getStatus())){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(entity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(resp.getExMsg());
		}
	}

	/**
	 * @description FOSS同步取消卸车任务到悟空系统
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:20:30
	 */
	@Override
	public void syncCancelUnloadTaskToWk(TfrNotifyEntity entity) throws Exception {
		String requestParameter=entity.getParamJson();
		//同步数据到悟空系统
		FossToWKResponseEntity resp=fossToWkService.syncCancelUnloadTaskToWk(requestParameter);
		
		if("1".equals(resp.getStatus())){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(entity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(resp.getExMsg());
		}
	}

	/**
	 * @description Foss同步取消分配卸车任务到悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:22:08
	 */
	@Override
	public void syncCancelAssignUnloadTaskToWk(TfrNotifyEntity entity) throws Exception {
		String requestParameter=entity.getParamJson();
		//同步数据到悟空系统
		FossToWKResponseEntity resp=fossToWkService.syncCancelAssignUnloadTaskToWk(requestParameter);
		
		if("1".equals(resp.getStatus())){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(entity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(resp.getExMsg());
		}
	}

	/**
	 * @description Foss同步分配卸车任务到悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:23:01
	 */
	@Override
	public void syncAssignUnloadTaskToWk(TfrNotifyEntity entity) throws Exception {
		String requestParameter=entity.getParamJson();
		//同步数据到悟空系统
		FossToWKResponseEntity resp=fossToWkService.syncAssignUnloadTaskToWk(requestParameter);
		
		if("1".equals(resp.getStatus())){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(entity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(resp.getExMsg());
		}
	}

	/**
	 * @description FOSS同步确认卸车任务给悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:28:46
	 */
	@Override
	public void syncConfirmExpressUnloadTaskToWk(TfrNotifyEntity entity) throws Exception {
		String requestParameter=entity.getParamJson();
		//同步数据到悟空系统
		FossToWKResponseEntity resp=fossToWkService.syncConfirmExpressUnloadTaskToWk(requestParameter);
		
		if("1".equals(resp.getStatus())){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(entity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(resp.getExMsg());
		}
	}

}
