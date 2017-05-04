package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationSmsEntity;



/**
* @description 仓库饱和度预警信息Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月24日 下午2:11:08
*/
public interface IStockSaturationSmsDao {
	
	/**
	* @description 仓库饱和度预警信息
	* @param stockSaturationSmsEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午2:12:31
	*/
	void insertStockSaturationSms(StockSaturationSmsEntity stockSaturationSmsEntity);
	
	
	
	/**
	* @description 根据外场code和发送短信的时期查询记录
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午2:13:20
	*/
	StockSaturationSmsEntity queryStockSaturationSms(Map<String,String> map);
}
