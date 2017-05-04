package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaEntity;

/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description 待叉区dao
 * @Time 2014-4-25
 */
public interface IWaitForkAreaDao {
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码查询待叉区
	 * @Time 2014-4-25
	 * @param transferCode
	 * @param limit
	 * @param start
	 * @return
	 */
	List<WaitForkAreaEntity> queryWaitForkAreaByTransferCode(String organizationCode,int limit, int start);
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
	 * @Description 新增待叉区与月台、货区距离
	 * @Time 2014-4-25
	 * @param distanceEntity
	 * @return
	 */
	int addWaitForkAreaDistance(List<WaitForkAreaDistanceEntity> distanceEntity);
	/**
	 * @author 李鹏飞
	 * @Description 根据虚拟编码删除待叉区
	 * @Time 2014-4-25
	 * @param virtualCode
	 * @return
	 */
	int deleteWaitForkAreaByVirtualCode(WaitForkAreaEntity entity);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码查询待叉区与月台、货区的距离
	 * @Time 2014-4-28
	 * @param waitForkAreaCode
	 * @return
	 */
	List<WaitForkAreaDistanceEntity> queryWaitForkAreaDistanceByCode(String waitForkAreaCode,String transferCode);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码删除待叉区与月台、货区距离
	 * @Time 2014-4-25
	 * @param waitForkAreaCode
	 * @return
	 */
	int deleteWaitForkAreaDistanceByCode(WaitForkAreaEntity entity);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码查询待叉区
	 * @Time 2014-4-25
	 * @param waitForkAreaCode
	 * @return
	 */
	WaitForkAreaEntity queryWaitForkAreaByCode(String waitForkAreaCode,String transferCode);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码统计数量
	 * @Time 2014-4-28
	 * @return
	 */
	long countWaitForkAreaByTransferCode(String transferCode);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、月台编码查询待叉区与月台距离
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param platformCode
	 * @return List<WaitForkAreaDistanceEntity> 
	 */
	List<WaitForkAreaDistanceEntity> queryDistanceBetweenPlatform(String transferCode,String platformCode);
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码、库区编码查询待叉区与库区距离
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param goodsAreaCode
	 * @param waitForkAreaCode
	 * @return WaitForkAreaDistanceEntity 
	 */
	WaitForkAreaDistanceEntity queryDistanceBetweenGoodsArea(String transferCode,String goodsAreaCode,String waitForkAreaCode);
	
	/**
	 * @author lifanghong
	 * @Description 根据待叉区编码查询待叉区
	 * @Time 2014-4-25
	 * @param waitForkAreaCode
	 * @return
	 */
	 WaitForkAreaEntity queryWaitForkAreaByTransferCodeAndWaitForkAreaCode(
			String waitForkAreaCode,String transferCode);
}
