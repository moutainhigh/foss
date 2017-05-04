package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcChangeDetailDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

public class WaybillRfcChangeDetailDao extends iBatis3DaoImpl implements
		IWaybillRfcChangeDetailDao {
	private static final String NAMESPACE = "foss.pkp.WaybillRfcChangeDetailEntityMapper."; 
	
	/**
	 * 根据主键查询更改详情
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:33:51
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillRfcChangeDetailEntity selectByPrimaryKey(String id) {
		List<WaybillRfcChangeDetailEntity> list = this.getSqlSession().selectList(NAMESPACE+"selectByPrimaryKey", id);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:34:08
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("waybillRfcId", id);
		return this.getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", id);
	}
	/**
	 * 新增数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:34:31
	 */

	@Override
	public int insert(WaybillRfcChangeDetailEntity record) {
		//创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return this.getSqlSession().insert(NAMESPACE+"insert", record);
	}

	/**
	 * 新增数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:34:31
	 */
	@Override
	public int insertSelective(WaybillRfcChangeDetailEntity record) {
		//创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return this.getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

	/**
	 * 修改数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:34:31
	 */
	@Override
	public int updateByPrimaryKeySelective(WaybillRfcChangeDetailEntity record) {
		//修改时间
		record.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
	}

	/**
	 * 修改数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:34:31
	 */
	@Override
	public int updateByPrimaryKey(WaybillRfcChangeDetailEntity record) {
		//修改时间
		record.setModifyDate(new Date());
		return this.getSqlSession().delete(NAMESPACE+"updateByPrimaryKey", record);
	}

	/**
	 * 根据更改单ID查询更改详情数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月17日 15:34:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcChangeDetailEntity> queryRfcChangeDetail(String waybillRfcId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("waybillRfcId", waybillRfcId);
		List<WaybillRfcChangeDetailEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryRfcChangeDetail", map);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}
	
}
