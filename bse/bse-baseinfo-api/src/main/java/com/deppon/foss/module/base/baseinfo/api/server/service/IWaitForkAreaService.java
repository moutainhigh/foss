package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaEntity;

/**
 * @author 092020
 * @version V1.0
 * @Description 待叉区service
 * @Time 2014-4-25
 */
public interface IWaitForkAreaService extends IService{
	/**
	 * @author 李鹏飞
	 * @Description 根据参数查询待叉区（目前为外场CODE）
	 * @Time 2014-4-25
	 * @param transferCode
	 * @return
	 */
	List<WaitForkAreaEntity> queryWaitForkAreaByParams(WaitForkAreaEntity entity,int limit, int start);
	/**
	 * @author 李鹏飞
	 * @Description 新增待叉区
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	int addWaitForkArea(WaitForkAreaEntity entity);
	/**
	 * @author 李鹏飞
	 * @Description 查询符合条件待叉区总量
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	long queryWaitForkAreaCount(WaitForkAreaEntity entity);
	/**
	 * @author 李鹏飞
	 * @Description 根据待叉区虚拟编码删除待叉区
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	int deleteWaitForkAreaByVirtualCode(WaitForkAreaEntity entity);
	/**
	 * @author 李鹏飞
	 * @Description 修改待叉区
	 * @Time 2014-4-28
	 * @param entity
	 * @return
	 */
	int updateWaitForkArea(WaitForkAreaEntity entity);
	/**
	 * @author 李鹏飞
	 * @Description 根据待叉区编码查询待叉区
	 * @Time 2014-4-28
	 * @param waitForkAreaCode
	 * @return
	 */
	WaitForkAreaEntity queryWaitForkAreaByCode(String waitForkAreaCode,String transferCode);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场查询待叉区，计算待叉区到指定月台的距离
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param platformCode
	 * @return List<WaitForkAreaDistanceEntity>
	 */
	List<WaitForkAreaDistanceEntity> queryDistanceBetweenPlatform(String transferCode,String platformCode);
	/**
	 * @author 李鹏飞
	 * @Description 计算待叉区与库区距离
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param goodsAreaCode
	 * @param waitForkAreaCode
	 * @return WaitForkAreaDistanceEntity
	 */
	WaitForkAreaDistanceEntity queryDistanceBetweenGoodsArea(String transferCode,String goodsAreaCode,String waitForkAreaCode);
	/**
	 * @author lifanghong
	 * @Description 根据虚拟Code查询待叉区
	 * @Time 2014-4-28
	 * @return WaitForkAreaDistanceEntity
	 */
	WaitForkAreaEntity queryWaitForkAreaByVirtualCode(
			String virtualCode);
	

}
