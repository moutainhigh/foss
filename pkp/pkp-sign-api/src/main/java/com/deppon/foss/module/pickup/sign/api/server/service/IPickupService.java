package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PickupResultVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PickupVo;

/**
 * 
 * 提货清单service接口
 * @author foss-yuting
 * @date 2014-11-21 下午16:08:00
 * @since
 * @version
 */
public interface IPickupService {

	/**
	 * 分页查询清单列表
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPickupService#queryPickupListByParams(com.deppon.foss.module.pickup.sign.api.shared.vo.PickupVo, int start,int limit)
	 */
	PickupResultVo queryPickupListByParams(PickupVo pickupVo, int start,int limit);

	/**
	 * 批量更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPickupService#updatePickupState(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	void updatePickupState(List<PickupDto> pickupList);

	/**
	 * 判断记录是否存在
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPickupService#isExistByWaybill(java.lang.String waybillNo)
	 */
	PickupResultDto isExistByWaybill(String waybillNo);
	
	/**
	 * 单个实体更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPickupService#updatePickupStateByPickupResultDto(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	void updatePickupStateByPickupResultDto(PickupResultDto dto);

	/**
	 * 保存数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPickupService#insertEntity(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto.PickupResultDto)
	 */
	void insertEntity(PickupResultDto rstPickupResultDto);

	/**
	 * 返回Pda需要的提货清单列表数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPickupService#queryPdaPickupList(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	List<PadPickupResultDto> queryPdaPickupList(PadPickupDto pickupDto);
		
	
}
