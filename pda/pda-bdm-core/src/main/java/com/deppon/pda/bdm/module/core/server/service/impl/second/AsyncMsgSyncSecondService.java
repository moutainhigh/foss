package com.deppon.pda.bdm.module.core.server.service.impl.second;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.service.job.IAsyncDataSyncSecondService;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgSyncSecondDao;
import com.deppon.pda.bdm.module.core.server.httpService.OperationFactory;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

/**
 * 异步消息同步服务
 * @author 245955
 *
 */
public class AsyncMsgSyncSecondService implements IAsyncDataSyncSecondService<AsyncMsg>{
	private IAsyncMsgSyncSecondDao asyncMsgSyncSecondDao;
	/**
	 * 
	 * <p>TODO(异步消息同步)</p> 
	 * @author 245955
	 * @date 2015-10-21
	 * @param asyncMsg
	 * @throws Exception 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService#syncData(com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage)
	 */
	@Transactional
	@Override
	public void syncData(AsyncMsg asyncMsg) throws Exception{
		//检验消息是否已存在
		if(!asyncMsgSyncSecondDao.checkIsExist(asyncMsg.getId())){
			//调用异步消息各个模块service
			OperationFactory.asyncService(asyncMsg);
		}
		asyncMsgSyncSecondDao.deleteAsyncMsg(asyncMsg.getId());
	}
	/**
	 * 
	 * <p>TODO(异常数据处理状态)</p> 
	 * @author 245955
	 * @date 2015-10-21
	 * @param asyncMsg
	 * @param e
	 * @throws Exception 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService#dealException(com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage, java.lang.Exception)
	 */
	@Transactional
	@Override
	public void dealException(AsyncMsg asyncMsg, Exception e) throws Exception {
		asyncMsgSyncSecondDao.updateSyncStatus(asyncMsg.getId(), Constant.SYNC_STATUS_FAILED);
		//同步次数小于1时，第一次失败
		if(asyncMsg.getSyncCount()<1){
			asyncMsgSyncSecondDao.saveSyncExcInfo(asyncMsg.getId(), LogUtil.logFormat(e));
		}
		
	}
	
	
	public IAsyncMsgSyncSecondDao getAsyncMsgSyncSecondDao() {
		return asyncMsgSyncSecondDao;
	}
	public void setAsyncMsgSyncSecondDao(
			IAsyncMsgSyncSecondDao asyncMsgSyncSecondDao) {
		this.asyncMsgSyncSecondDao = asyncMsgSyncSecondDao;
	}
	@Transactional
	@Override
	public void dealBussinessException(AsyncMsg asyncMsg, Exception e)
			throws Exception {
		asyncMsgSyncSecondDao.updateSyncStatus(asyncMsg.getId(), Constant.SYNC_STATUS_BUSSINESS_FAILED);
		asyncMsgSyncSecondDao.saveSyncExcInfo(asyncMsg.getId(), LogUtil.logFormat(e));
	}
	
}
