package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.PodDto;

/**
 * 签收记录查询接口
 * @author 198704 weitao
 * @date 2014-08-23
 */
public interface IQueryPodInfoDao {

     /**
      * 通过输入的数据分页查询签收记录信息
      * @param dto
      * @param start
      * @param limit
      * @return List<PodDto>
      */
	 List<PodDto> queryPodInfo(PodDto dto, int start, int limit); 
	 
	 /**
	  * 得到查询的总数据条数
	  * @param dto
	  * @return count
	  */
	 int queryPodInfoCount(PodDto dto);
}
