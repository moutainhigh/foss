package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICallCenterWaybillInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * cc催运单信息DAO接口实现类
 * @author 132599-foss-shenweihua
 * @date 2014-07-21 上午 11:17:54
 * @since
 * @version
 */
public class CallCenterWaybillInfoDao extends SqlSessionDaoSupport implements ICallCenterWaybillInfoDao{
	
	 private static final String NAMESPACE = "foss.bse.bse-baseinfo.callCenterWaybillInfo.";
	/**
	 * 新增CC催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param entity
	 * @return
	 */
	@Override
	public int addCallCenterWaybillInfo(CallCenterWaybillInfoEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/**
	 * 修改CC催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param entity
	 * @return
	 */
	@Override
	public int updateCallCenterWaybillInfo(CallCenterWaybillInfoEntity entity) {

		return this.getSqlSession().update(NAMESPACE + "update", entity);
	}
	
	/**
	 * 根据运单凭证号查询催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param callCenterWaybillNo
	 * @return
	 */
	@Override
	public CallCenterWaybillInfoEntity queryCcInfoByCallCenterWaybillNo(
			String callCenterWaybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("hasDone", FossConstants.INACTIVE); // 只查询未解决的催单信息
		map.put("pressWaybillNo", callCenterWaybillNo);
		return (CallCenterWaybillInfoEntity)this.getSqlSession().selectOne(
				NAMESPACE + "queryCallInfoByCallCenterWaybillNo", map);
	}
	
	/**
	 * 根据运单凭证号判断催运单信息是否已存在
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param callCenterWaybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean queryCallInfoByCallCenterWaybillNo(String callCenterWaybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pressWaybillNo", callCenterWaybillNo);
		List<CallCenterWaybillInfoEntity> list = this.getSqlSession().selectList(
			NAMESPACE + "queryCallInfoByCallCenterWaybillNo", map);
		return CollectionUtils.isNotEmpty(list);
	}
	
	/**
     * 根据传入对象查询符合条件催运单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2014-07-21 下午10:06:59
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CallCenterWaybillInfoEntity> queryCallCenterInfos(
			CallCenterWaybillInfoEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryCallCenterInfos",
			entity, rowBounds);
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2014-07-21 下午10:06:59
     * @param entity
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(CallCenterWaybillInfoEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}

}
