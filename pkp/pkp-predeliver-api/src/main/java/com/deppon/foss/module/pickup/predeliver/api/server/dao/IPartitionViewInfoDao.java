package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionViewInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo;

/** 
 * @ClassName: IPartitionViewInfoDao 
 * @Description: 分区查看Dao
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-19 上午9:41:58 
 *  
 */
public interface IPartitionViewInfoDao {

	/**
	 * @Title: selectPartitionByBigZone
	 * @Description: 根据大区分区
	 * @param partitionViewInfoVo 传入参数对象
	 * @return 分区信息集合
	 */
	List<PartitionViewInfoDto> selectPartitionByBigZone(PartitionViewInfoVo partitionViewInfoVo, int start, int limit);
	
	
	/**
	 * @Title: selectPartitionByBigZoneCount
	 * @Description: 根据大区分区总数量
	 * @param partitionViewInfo 传入参数对象
	 * @return 总条数
	 */
	Long selectPartitionByBigZoneCount(PartitionViewInfoVo partitionViewInfoVo);
	
	/**
	 * @Title: selectPartitionBySmallZone
	 * @Description: 根据小区分区
	 * @param partitionViewInfoVo 传入参数对象
	 * @return 分区信息集合
	 */
	List<PartitionViewInfoDto> selectPartitionBySmallZone(PartitionViewInfoVo partitionViewInfoVo);
}
