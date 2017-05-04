package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TaobaoDepponDistrictMapEntity;

public interface IRookieWaybillJBDDao {
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
	 * <p>通过地址查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	List<RookieWaybillJBDEntity> queryRookieWaybillJBDByAddress(RookieWaybillJBDEntity entity);
	/**
	 * <p> 通过ID查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	List<RookieWaybillJBDEntity> queryRookieWaybillJBDByIds(List<String> ids);
	/**
	 * <p> 查询淘宝德邦行政区域异同</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	TaobaoDepponDistrictMapEntity queryDistrictIfEqual(TaobaoDepponDistrictMapEntity entity);
}
