package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IReturnRateDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 退单率Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月3日 下午3:59:00
*/
public class ReturnRateDaoImpl extends iBatis3DaoImpl implements IReturnRateDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.returnRate.";
	
	
	/**
	* @description 退单率分页查询
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.IReturnRateDao#queryReturnRateList(com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity, int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:21:34
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnRateEntity> queryReturnRateList(
			Map<String,String> map, int start, int limit) {
		if(start>=0 && limit >=0){
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateList", map);
		}
	}

	
	/**
	* @description 退单率分页查询的总数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.IReturnRateDao#queryReturnRateListCount(com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:22:05
	* @version V1.0
	*/
	@Override
	public int queryReturnRateListCount(Map<String,String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryReturnRateListCount", map);
	}

	/**
	* @description 累计退单率查询分页
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnRateEntity> queryReturnRateLogList(
			Map<String, String> map, int start, int limit) {
		if(start>=0 && limit >=0){
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateLogList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateLogList", map);
		}
	}

	/**
	* @description 累计退单率查询分页总记录数
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	@Override
	public int queryReturnRateLogListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryReturnRateLogListCount", map);
	}

	/**
	* @description 累计退单率查询分页 全国的
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnRateEntity> queryReturnRateAllLogList(
			Map<String, String> map, int start, int limit) {
		if(start>=0 && limit >=0){
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateLogAllList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateLogAllList", map);
		}
	}

	/**
	* @description 累计退单率查询分页总记录数 全国的
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	@Override
	public int queryReturnRateAllLogListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryReturnRateLogAllListCount", map);
	}


	/**
	* @description 退单率定时执行Dao
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午10:57:29
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnRateEntity> queryReturnRateJobList(Map<String, String> map) {
		return this.getSqlSession().selectList(NAME_SPACE+"queryReturnRateJobList", map);
	}


	/**
	* @description 添加退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午1:54:10
	*/
	@Override
	public int insertReturnRatePojo(ReturnRateEntity returnRateEntity) {
		if(returnRateEntity!=null){
			returnRateEntity.setReturnRateId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(NAME_SPACE+"insertReturnRatePojo", returnRateEntity);
	}


	@Override
	public void batchInsertReturnRatePojo(
			List<ReturnRateEntity> returnRateEntityList) {
		this.getSqlSession().insert(NAME_SPACE+"batchInsertReturnRatePojo", returnRateEntityList);
	}



	/** 
	 *@desc 查询外场提交派送装车任务票数
	 *@param orgCodeList 外场编码
	 *@return List<ReturnRateEntity>
	 *@author 105795
	 *@date 2015年3月16日下午4:33:33 
	 */
	@SuppressWarnings("unchecked")
	public List<ReturnRateEntity> queryDeliverLoadWaybillQty(
			List<String> orgCodeList) {
		return this.getSqlSession().selectList(NAME_SPACE+"queryDeliverLoadWaybillQty", orgCodeList);
	}

}
