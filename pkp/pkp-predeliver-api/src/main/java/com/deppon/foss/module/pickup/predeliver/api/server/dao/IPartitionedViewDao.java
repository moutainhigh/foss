package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AddressLabel;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionedViewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionedViewVo;

/**
 * 分区查看DAO
 * @author 中软国际—李顺翔
 * @date 2015-04-23 15:16:45
 */
public interface IPartitionedViewDao {
	/**
	 * 查询符合条件的运单信息，前提是queryWaybillNum(Map<String,Object> map)执行结果大于0
	 */
	public List<PartitionedViewDto> queryWaybillList(PartitionedViewDto partitionedViewDto);
	
	/**
	 * 查询符合条件的运单信息，如果记录数为0，则表示未查找到数据
	 */
	public Long queryWaybillNum(PartitionedViewDto partitionedViewDto);
	
	/**
	 * 查询大区下属的小区
	 */
	public List<PartitionedViewDto> queryWaybillList_Smallzone(PartitionedViewDto partitionedViewDto);

	/**
	 * 查询大区小区所属的运单集合
	 */
	public List<String> querywaybills_BigZoneAndSmallZone(PartitionedViewDto partitionedViewDto);
	
	/**
	 * 查询运单明细
	 */
	public List<WaybillToArrangeDto> queryWaybillDetails(PartitionedViewVo partitionedViewVo);
	
	/**
	 * 查询需要GIS处理的运单
	 */
	public List<AddressLabel> queryGisWaybillNoDetail(PartitionedViewVo partitionedViewVo);
	
	/**
	 * 查询汇总信息
	 */
	public PartitionedViewDto queryTotal(PartitionedViewDto partitionedViewVo);
}
