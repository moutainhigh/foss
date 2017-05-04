package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgUploadDao;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;

/**
 * 异步消息上传DAO实现类
 * @author wanghongling
 *
 */
public class AsyncMsgUploadDao extends  iBatis3DaoImpl implements IAsyncMsgUploadDao{
	/**
	 * 
	 * <p>TODO(保存异步消息)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午7:46:29
	 * @param asyncMsg 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgUploadDao#saveAsyncMsg(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public void saveAsyncMsg(AsyncMsg asyncMsg) {
		getSqlSession().insert(getClass().getName()+".saveAsyncMsg", asyncMsg);
	}
	/**
	 * 
	 * <p>TODO(检验异步消息是否存在)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午7:46:43
	 * @param id
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgUploadDao#checkIsExist(java.lang.String)
	 */
	@Override
	public boolean checkIsExist(String id) {
		@SuppressWarnings("unchecked")
		List<String> result = getSqlSession().selectList(getClass().getName()+".checkIsExist", id);
		return (result!=null && !result.isEmpty());
	}

}
