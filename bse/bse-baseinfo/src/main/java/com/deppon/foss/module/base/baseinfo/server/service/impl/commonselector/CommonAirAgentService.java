package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirAgentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirAgentEntity;
/**
 * 公共查询组件--“航空代理”（包含航空公司代理人信息，空运代理公司）的数据库对应数据访问service实现类
 * @author 130346-foss-lifanghong
 * @date 2013-07-27 上午8:50:06
 * @since
 * @version
 */
public class CommonAirAgentService implements ICommonAirAgentService {
	private ICommonAirAgentDao commonAirAgentDao;
	/**
	 * 公共查询组件-查询航空代理
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-27 上午8:50:06
	 * @since
	 * @version
	 */
	@Override
	public List<AirAgentEntity> queryAirAgentEntityByEntity(
			AirAgentEntity airAgentEntity, int limit, int start) {
		
		return commonAirAgentDao.queryAirAgentEntityByEntity(airAgentEntity, limit, start);
	}
	
	@Override
	public Long countAirAgentEntityByEntity(AirAgentEntity airAgentEntity) {
		// TODO Auto-generated method stub
		return commonAirAgentDao.countAirAgentEntityByEntity(airAgentEntity);
	}
	public ICommonAirAgentDao getCommonAirAgentDao() {
		return commonAirAgentDao;
	}
	public void setCommonAirAgentDao(ICommonAirAgentDao commonAirAgentDao) {
		this.commonAirAgentDao = commonAirAgentDao;
	}
}
