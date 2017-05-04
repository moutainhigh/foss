package com.deppon.pda.bdm.module.core.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgUploadDao;
import com.deppon.pda.bdm.module.core.server.service.IAsyncMsgUploadService;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**
 * 异步消息上传服务
 * @author wanghongling
 *
 */
public class AsyncMsgUploadService implements IAsyncMsgUploadService {
	
	private IAsyncMsgUploadDao asyncMsgUploadDao;
	/**
	 * 
	 * <p>TODO(异步消息上传)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:41:52
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IAsyncMsgUploadService#saveAsyncMsg(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Transactional
	@Override
	public String saveAsyncMsg(AsyncMsg asyncMsg) throws PdaBusiException {
		//检验消息是否存在
		if(!asyncMsgUploadDao.checkIsExist(asyncMsg.getId())){
			asyncMsgUploadDao.saveAsyncMsg(asyncMsg);
		}
		return asyncMsg.getId();
	}

	public IAsyncMsgUploadDao getAsyncMsgUploadDao() {
		return asyncMsgUploadDao;
	}

	public void setAsyncMsgUploadDao(IAsyncMsgUploadDao asyncMsgUploadDao) {
		this.asyncMsgUploadDao = asyncMsgUploadDao;
	}

}
