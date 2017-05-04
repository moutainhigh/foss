package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IWaybillChangeMsgEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;

/**
 * 财务变更消息DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:38:16
 * @since
 * @version
 */
public class WaybillChangeMsgEntityDao extends iBatis3DaoImpl implements
		IWaybillChangeMsgEntityDao {

	private static final String NAMESPACES = "foss.stl.WaybillChangeMsgEntityDao.";// 命名空间路径

	/**
	 * 新加完结消息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:16:49
	 * @param item 财务变更消息实体
	 * @return
	 */
	@Override
	public int addChangeMsg(WaybillChangeMsgEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/**
	 * 批量删除完结消息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:17:10
	 * @param list 财务变更消息列表
	 * @return
	 */
	@Override
	public int deleteByBatch(List<WaybillChangeMsgEntity> list) {
		
		return this.getSqlSession().delete(NAMESPACES + "deleteByBatch", list);
	}

	/**
	 * 查询待处理业务完结消息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:17:46
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param limit 行限制
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillChangeMsgEntity> queryChangeMsg(Date beginTime, Date endTime, int limit) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		
		RowBounds rowBounds = new RowBounds(0, limit);
		
		return this.getSqlSession().selectList(NAMESPACES + "selectChangeMsg", map, rowBounds);
	}

	/**
	 * 根据运单号查询财务变更信息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:18:23
	 * @param waybillNo 运单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillChangeMsgEntity> queryChangeMsgByWaybillNO(
			String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACES+"selectChangeMsgByWaybillNo",waybillNo);
	}

}
