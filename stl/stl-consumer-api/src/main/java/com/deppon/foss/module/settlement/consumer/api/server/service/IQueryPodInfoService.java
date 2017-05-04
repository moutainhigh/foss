package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.PodDto;

/**
 * 签收记录查询service层接口
 * @author 198704 weitao
 * @date 2014-08-23
 */
public interface IQueryPodInfoService extends IService {

	 /**
	  * 分页查询签收记录的方法
	  * @param dto
	  * @param start
	  * @param limit
	  * @return List<PodDto>
	  */
	 List<PodDto> queryPodInfo(PodDto dto, int start, int limit);
	 
	 /**
	  * 查询出的记录总条数
	  * @param dto
	  * @return
	  */
	 int queryPodInfoCount(PodDto dto);
}
