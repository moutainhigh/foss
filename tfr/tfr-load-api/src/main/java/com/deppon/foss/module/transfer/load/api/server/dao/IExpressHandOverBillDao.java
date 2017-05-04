package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;

/** 
 * @className: IExpressHandOverBillDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 包交接单dao接口
 * @date: 2013-7-26 下午2:14:29
 * 
 */
public interface IExpressHandOverBillDao {
	
	/**
	 * 根据包号获取运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:05:10
	 */
	List<HandOverBillDetailEntity> queryWaybillListByPackageNo(String packageNo);
	
	/**
	 * 根据包号和运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:06:03
	 */
	List<SerialNoStockEntity> querySerialNoListByPackageNoAndWaybillNo(String packageNo,String waybillNo);

}
