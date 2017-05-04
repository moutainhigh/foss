package com.deppon.foss.module.transfer.unload.api.server.service;


import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;


/**
* @description 车辆卸车任务service(快递拆分,快递对接FOSS车辆卸车任务service)
* @version 1.0
* @author 328768-foss-gaojianfu
* @update 2016年5月10日 上午11:10:11
 */
public interface IUnloadTruckTaskForWkService {
	
	
	/**
	 * @description FOSS同步新建卸车任务给悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:17:56
	 */
	public void syncNewExpressUnloadTaskToWk(TfrNotifyEntity entity) throws Exception;

	
	/**
	 * @description FOSS同步修改卸车任务到悟空系统
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:19:32
	 */
	public void syncupdateExpressUnloadTask(TfrNotifyEntity entity) throws Exception;
	
	
	/**
	 * @description FOSS同步取消卸车任务到悟空系统
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:20:30
	 */
	public void syncCancelUnloadTaskToWk(TfrNotifyEntity entity) throws Exception;
	
	/**
	 * @description Foss同步取消分配卸车任务到悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:22:08
	 */
	public void syncCancelAssignUnloadTaskToWk(TfrNotifyEntity entity) throws Exception;
	
	/**
	 * @description Foss同步分配卸车任务到悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:23:01
	 */
	public void syncAssignUnloadTaskToWk(TfrNotifyEntity entity) throws Exception;
	
	/**
	 * @description FOSS同步确认卸车任务给悟空
	 * @param entity
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 上午11:28:46
	 */
	public void syncConfirmExpressUnloadTaskToWk(TfrNotifyEntity entity) throws Exception;
	
}
