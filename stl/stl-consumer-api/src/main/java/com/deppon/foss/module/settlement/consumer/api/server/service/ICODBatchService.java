package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;

/**
 * 代收货款批次号服务.
 *
 * @author 046644-foss-zengbinwen
 * @date 2012-11-29 下午4:30:31
 */
public interface ICODBatchService extends IService {


	/**
	 * 生成代收货款批次号.
	 *
	 * @param codType the cod type
	 * @return the string 代收货款类型
	 * @author guxinhua
	 * @date 2012-11-29 下午4:32:12
	 */
	String generateCODBatchNo(String codType);
	
	/**
	 * 创建代收货款批次号实体.
	 *
	 * @param currentInfo the current info
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午4:32:12
	 */
	void createCODBatchEntity(CurrentInfo currentInfo,String codBatchNo,String batchState);

	/**
	 * 根据批次号查询代收货款批次实体.
	 *
	 * @param batchNo the batch no
	 * @return the cOD batch entity
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午4:34:06
	 */
	CODBatchEntity queryByBatchNo(String batchNo);

	/**
	 * 更新代收货款状态.
	 *
	 * @param dto the dto
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午8:11:38
	 */
	void updateCODBatchStatus(CODBatchDto dto);
}
