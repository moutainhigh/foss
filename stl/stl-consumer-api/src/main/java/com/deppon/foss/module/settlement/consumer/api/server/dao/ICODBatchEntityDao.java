package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;

/**
 * 
 * 代收货款批次号实体DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-29 下午2:27:25
 */
public interface ICODBatchEntityDao {

	/**
	 * 
	 * 新增代收货款批次号
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午2:28:55
	 */
	int insert(CODBatchEntity entity);

	/**
	 * 
	 * 根据批次号查询代收货款批次号
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午2:29:23
	 */
	CODBatchEntity queryByBatchNo(String batchNo);

	/**
	 * 
	 * 获取批次号SEQ
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午2:30:03
	 */
	long selectBatchNoSeq();

	/**
	 * 
	 * 更新代收货款批次状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午8:09:12
	 */
	int updateCODBatchStatus(CODBatchDto dto);
}
