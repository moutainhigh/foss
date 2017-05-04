package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Map;



import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationSmsDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationSmsEntity;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 仓库饱和度的Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月22日 下午2:21:16
*/
public class StockSaturationSmsDaoImpl extends iBatis3DaoImpl implements IStockSaturationSmsDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.stock.saturation.sms.";
	
	/**
	* @description 仓库饱和度预警信息
	* @param stockSaturationSmsEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午2:12:31
	*/
	@Override
	public void insertStockSaturationSms(
			StockSaturationSmsEntity stockSaturationSmsEntity) {
		if(stockSaturationSmsEntity != null){
			stockSaturationSmsEntity.setSaturationSmsId(UUIDUtils.getUUID());
		}
		 this.getSqlSession().insert(NAME_SPACE+"insertStockSaturationSms",stockSaturationSmsEntity);
	}
	
	/**
	* @description 根据外场code和发送短信的时期查询记录
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午2:13:20
	*/
	@Override
	public StockSaturationSmsEntity queryStockSaturationSms(
			Map<String, String> map) {
		return (StockSaturationSmsEntity) this.getSqlSession().selectOne(NAME_SPACE+"queryStockSaturationSms",map);
	}
	
	
	
}
