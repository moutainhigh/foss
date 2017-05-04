package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirAgentEntity;

/**
 * 公共查询组件--“航空代理”（包含航空公司代理人信息，空运代理公司）的数据库对应数据访问DAO接口
 * @author 130346-foss-lifanghong
 * @date 2013-07-27 上午8:50:06
 * @since
 * @version
 */
public interface ICommonAirAgentDao {
	/**
	 * <p>根据条件（分页模糊）有选择的统计出符合条件的“航空公司代理人”记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-lifanghong
	 * @date  2013-4-25 
	 * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
	 * @return 符合条件的“航空公司代理人”记录数
	 * @see
	 */		
	Long countAirAgentEntityByEntity(
			AirAgentEntity airAgentEntity);
/**
 * 公共查询组件-查询航空代理
 * @author 130346-foss-lifanghong
 * @date 2013-07-27 上午8:50:06
 * @since
 * @version
 */
	List<AirAgentEntity> queryAirAgentEntityByEntity(AirAgentEntity airAgentEntity,int limit,int start);
}
