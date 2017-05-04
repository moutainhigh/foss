package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IErrorLoseStartingDao;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ErrorLoseStartingDto;


/**
* @description 出发丢货上报OA的Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年1月4日 上午8:16:24
*/
public class ErrorLoseStartingDao extends iBatis3DaoImpl implements IErrorLoseStartingDao {

	
	/**
	* @description 查找出发部门在库时间超过3的运单
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IErrorLoseStartingDao#findStockOverTime(java.util.Date)
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午8:16:39
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ErrorLoseStartingDto> findStockOverTime(Date findTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inStockTime", findTime);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "findStockOverTime", params);
	}

}
