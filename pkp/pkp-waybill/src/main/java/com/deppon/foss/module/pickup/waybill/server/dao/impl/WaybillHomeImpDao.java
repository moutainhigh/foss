package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillHomeImpDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillHomeImpEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillHomeImpPushVo;

public class WaybillHomeImpDao extends iBatis3DaoImpl implements IWaybillHomeImpDao {

	private static final String NAMESPACE = "foss.pkp.WaybillHomeImpEntityMapper.";

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillHomeImpEntity> quaryAllWaybillHomeInfo() {
		return this.getSqlSession().selectList(NAMESPACE + "quaryAllWaybillHomeInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public WaybillHomeImpEntity quaryWaybillHomeInfoById(String id) {
		List<WaybillHomeImpEntity> entitys = this.getSqlSession().selectList(NAMESPACE + "quaryWaybillHomeInfoById", id);
		if(entitys!=null){
			return entitys.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillHomeImpEntity> quaryWaybillHomeInfoByMailNo(String mailNo) {
		return this.getSqlSession().selectList(NAMESPACE + "quaryWaybillHomeInfoByMailNo",mailNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillHomeImpEntity> quaryWaybillHomeInfoByLogisticId(String logisticId) {
		return this.getSqlSession().selectList(NAMESPACE + "quaryWaybillHomeInfoByLogisticId",logisticId);
	}
	
	@Override
	public String insertWaybillHomeImpInfo(WaybillHomeImpEntity homeImpEntity) {
		this.getSqlSession().insert(NAMESPACE + "insert", homeImpEntity);
		return homeImpEntity.getId();
	}

	@Override
	public int updateWaybillHomeImpInfo(WaybillHomeImpEntity homeImpEntity) {
		int n = this.getSqlSession().update(NAMESPACE + "update", homeImpEntity);
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillHomeImpEntity> quaryUnPushedWaybillHomeInfo(WaybillHomeImpEntity waybillHomeImpEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "quaryUnPushedWaybillHomeInfo",waybillHomeImpEntity);
	}

	@Override
	public int updateHomeImpPushMessageByRowNum(WaybillHomeImpPushVo waybillHomeImpPushVo) {
		return this.getSqlSession().update(NAMESPACE + "updateHomeImpPushMessageByRowNum", waybillHomeImpPushVo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillHomeImpEntity> quaryWaybillHomeInfoByJobId(WaybillHomeImpEntity waybillHomeImpEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "quaryWaybillHomeInfoByJobId",waybillHomeImpEntity);
	}
	
	/**
	 * 根据运单号查询推送记录
	 */
	@Override
	public Long quaryWaybillHomeInfoCountByWaybillNo(String wayBillNo) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"quaryWaybillHomeInfoCountByWaybillNo" ,wayBillNo);
	}
}
