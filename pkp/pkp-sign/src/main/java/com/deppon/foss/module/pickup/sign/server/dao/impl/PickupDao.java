package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IPickupDao;

/**
 * 提货清单dao实现
 * @author foss-yuting
 * @date   2014-11-21 下午16:09:11
 * @since
 * @version
 */
public class PickupDao extends iBatis3DaoImpl implements IPickupDao {

	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "pkp.pickup.";
	
	
	/**
	 * 分页查询清单列表
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#queryPickupListByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto, int start,int limit)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupResultDto> queryPickupListByParams(PickupDto pickupDto, int start,int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<PickupResultDto>)this.getSqlSession().selectList(NAMESPACE + "queryPickupList", pickupDto, rowBounds);
	}
	
	/**
	 * 分页查询清单列表记录数
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#queryPickupInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	@Override
	public Long queryPickupInfoCountByParams(PickupDto pickupDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalCount", pickupDto);
	}
	
	
	/**
	 * 批量更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#updatePickupState(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	@Override
	public void updatePickupState(List<PickupResultDto> pickUpResultList) {
		this.getSqlSession().update(NAMESPACE+"batchUpdatePickupStates",pickUpResultList);
	}
	
	/**
	 * 单个实体更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#updatePickupStateByEntity(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto.PickupResultDto)
	 */
	@Override
	public void updatePickupStateByEntity(PickupResultDto dto) {
		this.getSqlSession().update(NAMESPACE+"updatePickupStateByEntity",dto);
	}
	
	/**
	 * 保存数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#insertEntity(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto.PickupResultDto)
	 */
	@Override
	public void insertEntity(PickupResultDto rstPickupResultDto) {
		this.getSqlSession().insert(NAMESPACE+"insertEntity",rstPickupResultDto);
	}
	
	/**
	 * 判断记录是否存在
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#insertEntity(java.lang.String waybillNo)
	 */
	@Override
	public PickupResultDto isExistByWaybill(String waybillNo) {
		return (PickupResultDto) this.getSqlSession().selectOne(NAMESPACE + "isExistByWaybill", waybillNo);
	}
	
	/**
	 * 返回Pda需要的提货清单列表数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#insertEntity(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PadPickupResultDto> queryPdaPickupList(PadPickupDto pickupDto) {
		return (List<PadPickupResultDto>)this.getSqlSession().selectList(NAMESPACE + "queryPdaPickupList", pickupDto);
	}
}
