package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IPullbackRateDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 拉回率操作Dao类
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月3日 下午3:58:25
*/
public class PullbackRateDaoImpl extends iBatis3DaoImpl implements IPullbackRateDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.pullbackRate.";
	
	
	/**
	* @description 分页查询
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.IPullbackRateDao#queryPullbackRateList(com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity, int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:14:26
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PullbackRateEntity> queryPullbackRateList(
			Map<String,String> map, int start, int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateList", map);
		}
		
	}

	
	/**
	* @description 分页查询的总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.IPullbackRateDao#queryPullbackRateListCount(com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:14:42
	* @version V1.0
	*/
	@Override
	public int queryPullbackRateListCount(Map<String,String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryPullbackRateListCount", map);
	}

	
	/**
	* @description 拉回率定时执行Dao
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午10:58:11
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PullbackRateEntity> queryPullbackRateJobList(
			Map<String, String> map) {
		return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateJobList", map);
	}
	

	/**
	* @description 累计拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PullbackRateEntity> queryPullbackRateLogList(
			Map<String,String> map, int start, int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateLogList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateLogList", map);
		}
	}

	/**
	* @description 累计拉拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	@Override
	public int queryPullbackRateLogListCount(
			Map<String,String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryPullbackRateLogListCount", map);
	}

	/**
	* @description 累计拉回率查询分页 全国的
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PullbackRateEntity> queryPullbackRateAllLogList(
			Map<String, String> map, int start, int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateLogAllList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE+"queryPullbackRateLogAllList", map);
		}
	}

	/**
	* @description 累计拉拉回率查询分页总记录数  全国的
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	@Override
	public int queryPullbackRateAllLogListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryPullbackRateLogAllListCount", map);
	}


	/**
	* @description 添加拉回率记录
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:54:42
	*/
	@Override
	public int insertPullbackRatePojo(PullbackRateEntity pojo) {
		if(pojo!=null){
			pojo.setPullbackRateId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(NAME_SPACE+"insertPullbackRatePojo", pojo);
	}

	/**
	* @description 批量插入
	* @param list
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午4:14:18
	*/
	@Override
	public void batchInsertPullbackRatePojo(List<PullbackRateEntity> list) {
		if(list!=null){
			this.getSqlSession().insert(NAME_SPACE+"batchInsertPullbackRatePojo", list);
		}
	}
	
	
}
