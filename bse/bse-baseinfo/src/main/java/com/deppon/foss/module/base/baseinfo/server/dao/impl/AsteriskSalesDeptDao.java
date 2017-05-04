package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAsteriskSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 加星标营业部Dao接口实现
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 下午2:18:12
 * @since
 * @version
 */

public class AsteriskSalesDeptDao extends SqlSessionDaoSupport implements
	IAsteriskSalesDeptDao{
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.asteriskSalesDept.";
	
	/**
     * <p>新增加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:18:12
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addAsteriskSalesDept(AsteriskSalesDeptEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/**
     * <p>修改加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午2:28:12
     * @param entity
     * @return
     * @see
     */
	@Override
	public int updateAsteriskSalesDept(AsteriskSalesDeptEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE + "update", entity);
	}
	
	/**
     * <p>作废加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午2:28:12
     * @param asteriskDeptVirtualCodes 虚拟编码集合
     * @param modifyUser 修改人编码
     * @return
     * @see
     */
	@Override
	public int deleteAsteriskSalesDeptByVirtualCode(
			List<String> asteriskDeptVirtualCodes, String modifyUser) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("asteriskDeptVirtualCodes", asteriskDeptVirtualCodes);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("inactive", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);

		return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
	}
	
	/**
     * 根据传入对象查询符合条件所有加星标营业部信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午2:28:12
     * @param entity
     *            加星标营业部信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AsteriskSalesDeptEntity> queryAllAsteriskSalesDept(
			AsteriskSalesDeptEntity entity, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryAllAsteriskSalesDept",
				entity, rowBounds);
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午2:28:12
     * @param entity
     *            加星标营业部信息实体
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(AsteriskSalesDeptEntity entity) {

		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);		
	}
	
	/**
     * 验证此营业部是否已是加星营业部
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午5:58:12
     * @param salesDeptCode
     *            加星标营业部编码
     * @return
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public boolean queryAsteriskDeptByCode(String salesDeptCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("salesDeptCode", salesDeptCode);
		map.put("active", FossConstants.ACTIVE);
		List<AsteriskSalesDeptEntity> asteriskDeptList = this.getSqlSession().selectList(NAMESPACE + "queryAsteriskDeptByCode", map);
		
		return CollectionUtils.isNotEmpty(asteriskDeptList);
	}

	/**
	 * 提供客户端下载接口
	 * 
	 * @author foss-shixiaowei
	 * @date 2013-5-14 下午3:33:12
	 * @param entity 下载查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AsteriskSalesDeptEntity> queryAsteriskSalesDepForDownload(
			AsteriskSalesDeptEntity entity) {
		if (entity == null) {
		    entity = new AsteriskSalesDeptEntity();
		}
		return getSqlSession().selectList(NAMESPACE + "queryAsteriskSalesDeptForDownload", entity);
	}

	

}
