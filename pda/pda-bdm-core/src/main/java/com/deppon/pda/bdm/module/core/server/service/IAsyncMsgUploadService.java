package com.deppon.pda.bdm.module.core.server.service;

import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**
 * 异步消息上传服务接口
 * @author wanghongling
 *
 */
public interface IAsyncMsgUploadService {
	/**
	 * 保存异步消息
	 * @param asyncMsg
	 * @throws PdaBusiException
	 */
	public String saveAsyncMsg(AsyncMsg asyncMsg) throws PdaBusiException;
}
