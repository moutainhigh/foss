package com.deppon.pda.bdm.module.core.server.dao.impl.second;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgSyncSecondDao;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;

/**
 * 异步消息同步DAO实现类
 * @author 245955
 *
 */
public class AsyncMsgSyncSecondDao extends iBatis3DaoImpl implements IAsyncMsgSyncSecondDao{
	/**
	 * 
	 * <p>TODO(更改异步消息状态)</p> 
	 * @author Administrator
	 * @date 2015-10-22
	 * @param asyncMsgId
	 * @param syncStatus 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgSyncDao#updateSyncStatus(java.lang.String, int)
	 */
	@Override
	public void updateSyncStatus(String asyncMsgId, int syncStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("asyncMsgId", asyncMsgId);
		param.put("syncStatus", syncStatus);
		getSqlSession().update(getClass().getName()+".updateSyncStatus", param);
	}
	/**
	 * 
	 * <p>TODO(删除异步消息)</p> 
	 * @author Administrator
	 * @date 2015-10-22
	 * @param id 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgSyncDao#deleteAsyncMsg(java.lang.String)
	 */
	@Override
	public void deleteAsyncMsg(String id) {
		getSqlSession().delete(getClass().getName()+".deleteAsyncMsg", id);
	}

	@Override
	public void saveSyncExcInfo(String asyncMsgId, String errInfo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id",UUIDUtils.getUUID());
		param.put("asyncMsgId", asyncMsgId);
		param.put("errInfo", errInfo);
		getSqlSession().update(getClass().getName()+".saveSyncExcInfo", param);
	}
	/**
	 * 
	 * <p>TODO(检查异步消息是否存在)</p> 
	 * @author Administrator
	 * @date 2015-10-22
	 * @param id
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgSyncDao#checkIsExist(java.lang.String)
	 */
	@Override
	public boolean checkIsExist(String id) {
		@SuppressWarnings("unchecked")
		List<String> result = getSqlSession().selectList(getClass().getName()+".checkIsExist", id);
		return (result!=null && !result.isEmpty());
		
	}

}
