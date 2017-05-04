package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AddressLabel;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionedViewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionedViewVo;

/**
 * 分区查看Service
 * @author 中软国际—李顺翔
 * @date 2015-04-23 16:11:24
 */
public interface IPartitionedViewService {
	/**
	 * 查询符合条件的运单信息，前提是queryWaybillNum(Map<String,Object> map)执行结果大于0
	 * @param map
	 * @return List<PartitionedViewDto>
	 */
	public List<PartitionedViewDto> queryWaybillList(PartitionedViewDto partitionedViewDto);
	
	/**
	 * 查询符合条件的运单信息，如果记录数为0，则表示未查找到数据
	 * @param map
	 * @return Integer
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
	 * 设置装载率
	 */
	public DeliverbillEntity LoadingRate(DeliverbillEntity entity);
	
	/**
	 * 查询汇总信息
	 */
	public PartitionedViewDto queryTotal(PartitionedViewDto partitionedViewVo);
}
