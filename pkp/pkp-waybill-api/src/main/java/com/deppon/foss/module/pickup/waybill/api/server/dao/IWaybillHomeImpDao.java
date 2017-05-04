package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillHomeImpEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillHomeImpPushVo;

/**
 * 
 * 德邦家装运单推送信息DAO
 * 
 * @author lizhonglin 2015-09-10
 *
 */
public interface IWaybillHomeImpDao {

	/**
	 * 查询所有德邦家装运单推送信息
	 * 
	 * @return
	 */
	List<WaybillHomeImpEntity> quaryAllWaybillHomeInfo();
	
	/**
	 * 根据ID查询德邦家装运单推送信息
	 * @param id
	 * @return
	 */
	WaybillHomeImpEntity quaryWaybillHomeInfoById(String id);
	
	/**
	 * 根据运单号查询德邦家装运单推送信息
	 * @param mailno
	 * @return
	 */
	List<WaybillHomeImpEntity> quaryWaybillHomeInfoByMailNo(String mailNo);
	
	/**
	 * 根据我司对应的运单的订单号查询德邦家装运单推送信息
	 * @param logisticId
	 * @return
	 */
	List<WaybillHomeImpEntity> quaryWaybillHomeInfoByLogisticId(String logisticId);
	
	/**
	 * 查询家装运单中未推送的运单信息
	 * @param waybillHomeImpEntity
	 * @return
	 */
	List<WaybillHomeImpEntity> quaryUnPushedWaybillHomeInfo(WaybillHomeImpEntity waybillHomeImpEntity);
	/**
	 * 新增德邦家装运单推送信息
	 * @param homeImpEntity
	 * @return
	 */
	String insertWaybillHomeImpInfo(WaybillHomeImpEntity homeImpEntity);
	
	/**
	 * 更新德邦家装运单推送信息
	 * @param homeImpEntity
	 * @return
	 */
	int updateWaybillHomeImpInfo(WaybillHomeImpEntity homeImpEntity);
	
	/**
	 * 更新JobId,根据返回的参数确定执行条数
	 * @param waybillHomeImpPushVo
	 * @return
	 */
	int updateHomeImpPushMessageByRowNum(WaybillHomeImpPushVo waybillHomeImpPushVo);
	
	/**
	 * 根据JOB_ID查询德邦家装运单信息
	 * @param waybillHomeImpEntity
	 * @return
	 */
	List<WaybillHomeImpEntity> quaryWaybillHomeInfoByJobId(WaybillHomeImpEntity waybillHomeImpEntity);

	Long quaryWaybillHomeInfoCountByWaybillNo(String wayBillNo);
}


