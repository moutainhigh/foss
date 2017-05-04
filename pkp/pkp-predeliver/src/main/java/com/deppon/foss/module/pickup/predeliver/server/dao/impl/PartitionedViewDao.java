package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionedViewDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AddressLabel;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionedViewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionedViewVo;

public class PartitionedViewDao extends SqlSessionDaoSupport implements IPartitionedViewDao{
	
	/**
	 * 分区查看NameSpace
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionedViewDao";

	@Override
	public List<PartitionedViewDto> queryWaybillList(PartitionedViewDto partitionedViewDto) {
		if(partitionedViewDto==null){
			return null;
		}
		return this.getSqlSession().selectList(NAMESPACE+".queryWaybillList",partitionedViewDto);
	}

	@Override
	public Long queryWaybillNum(PartitionedViewDto partitionedViewDto) {
		if(partitionedViewDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(NAMESPACE+".queryWaybillNum",partitionedViewDto);
	}

	@Override
	public List<PartitionedViewDto> queryWaybillList_Smallzone(PartitionedViewDto partitionedViewDto) {
		if(partitionedViewDto==null){
			return null;
		}
		return this.getSqlSession().selectList(NAMESPACE+".queryWaybillList_Smallzone",partitionedViewDto);
	}

	@Override
	public List<String> querywaybills_BigZoneAndSmallZone(
			PartitionedViewDto partitionedViewDto) {
		if(partitionedViewDto==null){
			return null;
		}
		return this.getSqlSession().selectList(NAMESPACE+".querywaybills_BigZoneAndSmallZone",partitionedViewDto);
	}

	@Override
	public List<WaybillToArrangeDto> queryWaybillDetails(
			PartitionedViewVo partitionedViewVo) {
		if(partitionedViewVo==null)
			return null;
		return this.getSqlSession().selectList(NAMESPACE+".queryWaybillDetails",partitionedViewVo);
	}

	@Override
	public List<AddressLabel> queryGisWaybillNoDetail(
			PartitionedViewVo partitionedViewVo) {
		if(partitionedViewVo==null)
			return null;
		return this.getSqlSession().selectList(NAMESPACE+".queryGisWaybillNoDetail",partitionedViewVo);
	}

	@Override
	public PartitionedViewDto queryTotal(PartitionedViewDto partitionedViewVo) {
		if(partitionedViewVo==null)
			return null;
		return (PartitionedViewDto) this.getSqlSession().selectOne(NAMESPACE+".queryTotal", partitionedViewVo);
	}

}
