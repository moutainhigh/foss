package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.dto.ErrorLoseStartingDto;


/**
* @description 出发丢货上报OA的Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年12月27日 上午8:29:31
*/
public interface IErrorLoseStartingDao {
	
	/**
	* @description 查找出发部门在库时间超过3的运单
	* @param findTime
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年12月27日 上午8:31:09
	*/
	public List<ErrorLoseStartingDto> findStockOverTime(Date findTime);
}
