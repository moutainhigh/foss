package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionViewInfoDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionViewInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo;

/** 
 * @ClassName: PartitionViewInfoDao 
 * @Description:  分区查看Dao实现
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-19 上午9:45:51 
 *  
 */
public class PartitionViewInfoDao extends iBatis3DaoImpl implements IPartitionViewInfoDao {

	private final static String PARTITION_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionViewInfoMapper.";
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionViewInfoDao#selectPartitionByBigZone(com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo)
	 */
	@Override
	public List<PartitionViewInfoDto> selectPartitionByBigZone(PartitionViewInfoVo partitionViewInfoVo, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return super.getSqlSession().selectList(PARTITION_NAMESPACE + "selectGroupByBigZone", partitionViewInfoVo, rowBounds);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionViewInfoDao#selectPartitionByBigZoneCount(com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo)
	 */
	@Override
	public Long selectPartitionByBigZoneCount(PartitionViewInfoVo partitionViewInfoVo) {
		return (Long) super.getSqlSession().selectOne(PARTITION_NAMESPACE + "selectGroupByBigZoneCount", partitionViewInfoVo);
	}


	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionViewInfoDao#selectPartitionBySmallZone(com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo)
	 */
	@Override
	public List<PartitionViewInfoDto> selectPartitionBySmallZone(PartitionViewInfoVo partitionViewInfoVo) {
		return super.getSqlSession().selectList(PARTITION_NAMESPACE + "selectGroupBySmallZone", partitionViewInfoVo);
	}

	
}
