package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillBadWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto;

/**
 * 坏账核销服务接口类
 * 
 * @author foss-wangxuemin
 * @date Dec 4, 2012 2:35:02 PM
 */
public interface IBillBadAccountWriteoffService extends IService {

	/**
	 * 查询待坏账处理的应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:54:55 PM
	 * @param queryDto
	 *      	坏账参数Dto
	 * @return List<BillReceivableEntity>
	 * 			应收单集合
	 */
	List<BillReceivableEntity> queryBadAccountReceivableList(BillBadAccountQueryDto queryDto);

	/**
	 * 核销坏账
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:55:20 PM
	 * @param badWriteoffDto
	 *      	坏账单参数Dto
	 * @return void
	 * 			
	 */
	void writeoffBadAccount(BillBadWriteoffDto badWriteoffDto);

}
