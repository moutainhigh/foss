package com.deppon.pda.bdm.module.core.server.dao.impl.second;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgReadSecondDao;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;

/**
 * 异步消息读取DAO实现类
 * @author 245955
 *
 */
public class AsyncMsgReadSecondDao extends iBatis3DaoImpl implements IAsyncMsgReadSecondDao {
	private static final Logger log = Logger.getLogger(AsyncMsgReadSecondDao.class);
	/**
	 * 
	 * <p>TODO(读取异步消息)</p> 
	 * @author Administrator
	 * @date 2015-10-20
	 * @param count
	 * @param queueId
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgReadDao#readAsyncMsg(int, java.lang.String)
	 */
	@Override
	public List<AsyncMsg> readAsyncMsg(int count, String queueId) {
		//读取原始状态数据
		@SuppressWarnings("unchecked")
		List<AsyncMsg> result = getSqlSession().selectList(getClass().getName()+".readAsyncMsg",count);
	    /*	if(count>60){
			//读取错误
			@SuppressWarnings("unchecked")
			List<AsyncMsg> errorresult = getSqlSession().selectList(getClass().getName()+".readErrorAsyncMsg",count);
			
			if(errorresult!=null && errorresult.size()>0){
				result.addAll(errorresult);
			}
			count=0;
		}else{
			count++;
		}*/
			
		if(result!=null&&!result.isEmpty()){
			log.info("second result size:"+result.size());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("msgList", result);
			param.put("queueId", queueId);
			getSqlSession().update(getClass().getName()+".updReadStatus", param);
		}
		return result;
	}
	/**
	 * 
	 * <p>TODO(清理异常状态数据)</p> 
	 * @author Administrator
	 * @date 2015-10-20
	 * @param queueId 
	 * @see com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgReadDao#doInitAsyncDataStatus(java.lang.String)
	 */
	@Override
	public void doInitAsyncDataStatus(String queueId) {
		getSqlSession().update(getClass().getName()+".doInitAsyncDataStatus", queueId);
		
	}

}
