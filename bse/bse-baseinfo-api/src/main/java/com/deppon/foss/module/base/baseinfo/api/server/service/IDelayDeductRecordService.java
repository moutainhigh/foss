package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DelayDeductRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordCheckResponseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordResponseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DelayDeductRecordQueryDto;

/**
 * 
 * 延时扣款记录申请审核服务
 * @author 308861 
 * @date 2016-10-29 下午2:29:37
 * @since
 * @version
 */
public interface IDelayDeductRecordService extends IService{
	
	/**
	 * 
	 * 条件查询延时扣款记录条数  
	 * @author 308861 
	 * @date 2016-11-7 下午2:30:12
	 * @param delayDeductRecordQueryDto
	 * @return
	 * @see
	 */
	SyncDelayDeductRecordResponseEntity queryDelayDeductRecordForPage(DelayDeductRecordQueryDto delayDeductRecordQueryDto);
	/**
	 * 
	 * 通过id审核 
	 * @author 308861 
	 * @date 2016-11-11 上午11:26:02
	 * @param delayDeductRecordEntity
	 * @return
	 * @see
	 */
	SyncDelayDeductRecordCheckResponseEntity updateCheckStatusById(DelayDeductRecordEntity delayDeductRecordEntity);
}
