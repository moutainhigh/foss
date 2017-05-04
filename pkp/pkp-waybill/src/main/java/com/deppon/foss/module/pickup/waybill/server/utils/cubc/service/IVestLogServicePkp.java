package com.deppon.foss.module.pickup.waybill.server.utils.cubc.service;

import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestLogDo;

/**
 * 
    * @ClassName: IVestLogService
    * @Description: 用于记录日志的service服务接口
    * @author 323098
    * @date 2017年1月1日
    *
 */
public interface IVestLogServicePkp {
	
	/**
	 * 
	    * @Title: log
	    * @Description: 记录日志的接口方法
		* @Date: 2017年1月1日
	    * @param vestLogDo
	 */
	public void log(VestLogDo vestLogDo);
	/**
	 * 
	    * @Title: insert
	    * @Description: 用于插入日志的service服务
		* @Date: 2017年1月1日
	    * @param vestLogDo
	    * @return int
	 */
	public int insert(VestLogDo vestLogDo);
}
