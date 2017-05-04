package com.deppon.pda.bdm.module.core.server.dao;


/**
 * 异步消息同步DAO接口
 * @author 245955
 *
 */
public interface IAsyncMsgSyncSecondDao {
	
	/**
	 * 更新同步状态
	 * @param asyncMsgId
	 * @param syncStatus
	 */
	public void updateSyncStatus(String asyncMsgId, int syncStatus);
	
	/**
	 * 删除异步消息
	 * @param id
	 */
	public void deleteAsyncMsg(String id);
	
	/**
	 * 保存同步异常信息
	 * @param asyncMsgId
	 * @param errInfo
	 */
	public void saveSyncExcInfo(String asyncMsgId, String errInfo);
	/**
	 * 
	 * <p>TODO(检验消息是否存在)</p> 
	 * @author Administrator
	 * @date 2015-10-18
	 * @param id
	 * @see
	 */
	public boolean checkIsExist(String id);
}
