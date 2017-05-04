package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirAgentEntity;

/**
 * 公共查询选择器--“代理信息”Service接口
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-07-27
 */
public interface ICommonAirAgentService {
	/**
     * 统计总记录数 
     * @author 130346-foss-lifanghong
	 * @date 2013-07-27 上午8:50:06
	 * @since
	 * @version
	 */
    Long countAirAgentEntityByEntity(AirAgentEntity airAgentEntity);
	/**
	 * 公共查询组件-查询航空代理
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-27 上午8:50:06
	 * @since
	 * @version
	 */
	List<AirAgentEntity> queryAirAgentEntityByEntity(AirAgentEntity airAgentEntity,int limit,int start);
}
