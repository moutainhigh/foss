package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ISendRateDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 日派送率操作类
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月4日 上午11:22:00
*/
public class SendRateDaoImpl extends iBatis3DaoImpl implements ISendRateDao {
	
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.sendRate.";
	
	
	/**
	* @description 查询派送率
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.ISendRateDao#querySendRateList(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity, int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:22:17
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SendRateEntity> querySendRateList(
			Map<String,String> map, int start, int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAME_SPACE + "querySendRateList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE + "querySendRateList", map);
		}
	}

	
	/**
	* @description 查询派送率的总结记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.ISendRateDao#querySendRateListCount(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:22:21
	* @version V1.0
	*/
	@Override
	public int querySendRateListCount(Map<String,String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE + "querySendRateListCount", map);
	}
	

	/**
	* @description 累计日派送率查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:19
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SendRateEntity> querySendRateLogList(Map<String, String> map,
			int start, int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAME_SPACE + "querySendRateLogList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE + "querySendRateLogList", map);
		}
	}

	/**
	* @description 累计日派送率查询总数
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:36
	*/
	@Override
	public int querySendRateLogListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE + "querySendRateLogListCount", map);
	}
	
	/**
	* @description 累计日派送率查询  全国
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:19
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SendRateEntity> querySendRateLogListAll(
			Map<String, String> map, int start, int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAME_SPACE + "querySendRateLogAllList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE + "querySendRateLogAllList", map);
		}
	}

	/**
	* @description 累计日派送率查询总数 全国
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:36
	*/
	@Override
	public int querySendRateLogAllListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE + "querySendRateLogAllListCount", map);
	}


	/**
	* @description 派送率定时任务查询 dao
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午10:56:38
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SendRateEntity> querySendRateJobList(Map<String, String> map) {
		return this.getSqlSession().selectList(NAME_SPACE + "querySendRateJobList", map);
	}

	/**
	* @description 派送率添加 Dao
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午4:35:56
	*/
	@Override
	public int insertSendRatePojo(SendRateEntity sendRateEntity) {
		if(sendRateEntity != null){
			sendRateEntity.setSendRateId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(NAME_SPACE + "insertSendRatePojo", sendRateEntity);
	}

	/**
	* @description 派送率批量添加 Dao
	* @param sendRateEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午6:07:32
	*/
	@Override
	public void batchInsertSendRatePojo(List<SendRateEntity> sendRateEntityList) {
		this.getSqlSession().insert(NAME_SPACE + "batchInsertSendRatePojo", sendRateEntityList);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据部门and日期 查询当日的预计派送到达货量
	 *@param 
	 *@return List<SendRateEntity>
	 *@author 105795
	 *@date 2015年4月10日下午3:59:10 
	 */
	@SuppressWarnings("unchecked")
	public List<SendRateEntity> queryForeCastDeliverGoodsByDate(String orgCode,String queryDate){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("orgCode", orgCode);
		dataMap.put("queryDate", queryDate);
		return this.getSqlSession().selectList(NAME_SPACE+"queryForeCastDeliverGoodsByDate", dataMap);
	}
	
	
}
