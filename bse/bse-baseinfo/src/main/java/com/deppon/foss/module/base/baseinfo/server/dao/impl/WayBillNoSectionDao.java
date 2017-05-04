package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWayBillNoSectionDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;
/**
 * 运单号段管理DAO接口实现
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 下午1:35:18
 */
public class WayBillNoSectionDao extends SqlSessionDaoSupport implements IWayBillNoSectionDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.wayBillNoSection.";
	/**
	 * @description 根据传入对象查询符合条件所有运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午1:39:52
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WayBillNoSectionEntity> queryWayBillNoSection(
			WayBillNoSectionEntity entity, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryAllWayBillNoSection", entity, rowBounds);
		
	}
	/**
	 * @description 统计总记录数
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午2:19:43
	 */
	@Override
	public Long queryRecordCount(WayBillNoSectionEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
	}
	/**
	 * @description 
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午1:47:08
	 */
	@Override
	public int addWayBillNoSection(WayBillNoSectionEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	/**
	 * @description 查询运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:44:23
	 */
	@Override
	public Long queryWayBillStart(WayBillNoSectionEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryStartValue", entity);
	}
	/**
	 * @description修改运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:44:46
	 */
	@Override
	public int updateWayBillStart(WayBillNoSectionEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateStartValue", entity);
	}

}
