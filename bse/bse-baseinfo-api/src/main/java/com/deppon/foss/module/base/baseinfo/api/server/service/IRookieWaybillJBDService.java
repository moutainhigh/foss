package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TaobaoDepponDistrictMapEntity;

public interface IRookieWaybillJBDService extends IService {
	/**
	 * <p> 新增信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	long addRookieWaybillJBD(RookieWaybillJBDEntity entity);
	/**
	 * <p> 分页查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	List<RookieWaybillJBDEntity> queryRookieWaybillJBDByCondition(RookieWaybillJBDEntity entity, int start, int limit);
	/**
	 * <p> 查询总数</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	long queryCount(RookieWaybillJBDEntity entity);
	/**
	 * <p> 通过ID删除</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	long deleteRookieWaybillJBD(List<String> ids);
	/**
	 * <p>跟新数据</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	long updateRookieWaybillJBD(RookieWaybillJBDEntity entity);
	/**
	 * <p>查询菜鸟映射地址</p> 
	 * @author 273311 
	 * @date 2016-02-22 下午4:50:20
	 * @return 
	 * @see
	 */
	TaobaoDepponDistrictMapEntity queryDistrictIfEqual(TaobaoDepponDistrictMapEntity entity);
}
