/**
 * 
 */
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;

/**
 * @author niuly
 * @date 2014-02-12 09:02:35 
 * @funciton 运单补录dao
 */
public interface IMakeUpWaybillDao {
	/**
	 * @author niuly
	 * @date 2014-02-12 09:39:20
	 * @function 运单补录重量或体积发生变化或PDA更新重量体积时调用此方法
	 * @param entity
	 * @return int
	 */
	int addWaybillInfo(MakeUpWaybillEntity entity);

	/**
	 * @author niuly
	 * @date 2014-2-12下午3:32:27
	 * @function 查询该运单补录或PDA更新记录
	 * @return List<MakeUpWaybillEntity>
	 */
	List<MakeUpWaybillEntity> queryMakeUpWaybill();

	/**
	 * @author niuly
	 * @date 2014-2-12下午3:34:27
	 * @function 更新该运单的处理标志位和处理时间
	 * @param waybillId
	 * @param doneTime 
	 */
	int updateMakeUpWaybill(String waybillId, Date doneTime);
}
