package com.deppon.pda.bdm.module.core.server.dao;

import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;

/**
 * 异步消息上传DAO接口
 * @author whl126
 *
 */
public interface IAsyncMsgUploadDao {

	/**
	 * 保存异步消息
	 * @param asyncMsg
	 */
	public void saveAsyncMsg(AsyncMsg asyncMsg);
	
	/**
	 * 检查是否是重复消息
	 * @param id
	 * @return
	 */
	public boolean checkIsExist(String id);

}
