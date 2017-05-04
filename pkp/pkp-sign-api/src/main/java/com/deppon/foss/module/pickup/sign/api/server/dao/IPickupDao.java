package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;

/**
 * 提货清单dao接口
 * @author  foss-yuting
 * @date   2014-11-21 下午16:09:11
 * @since
 * @version
 */
public interface IPickupDao {

	/**
	 * 分页查询清单列表
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#queryPickupListByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto, int start,int limit)
	 */
	List<PickupResultDto> queryPickupListByParams(PickupDto pickupDto, int start,int limit);

	/**
	 * 分页查询清单列表记录数
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#queryPickupInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	Long queryPickupInfoCountByParams(PickupDto pickupDto);

	/**
	 * 批量更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#updatePickupState(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	void updatePickupState(List<PickupResultDto> pickUpResultList);

	/**
	 * 单个实体更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#updatePickupStateByEntity(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto.PickupResultDto)
	 */
	void updatePickupStateByEntity(PickupResultDto dto);

	/**
	 * 保存数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#insertEntity(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto.PickupResultDto)
	 */
	void insertEntity(PickupResultDto rstPickupResultDto);

	/**
	 * 判断记录是否存在
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#insertEntity(java.lang.String waybillNo)
	 */
	PickupResultDto isExistByWaybill(String waybillNo);

	/**
	 * 返回Pda需要的提货清单列表数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.PickupDao#insertEntity(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	List<PadPickupResultDto> queryPdaPickupList(PadPickupDto pickupDto);

}
