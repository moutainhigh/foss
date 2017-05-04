package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillHomeImpEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillHomeImpPushVo;

/**
 * @author lizhonglin 2015-09-10
 * 
 * 德邦家装信息推送业务逻辑处理类
 */
public interface IWaybillHomeImpService extends IService{
	
	/**
	 * 保存家装运单提交时需要向家装模块推送信息 ,将带有特殊增值服务标识的运单信息保存
	 * @param waybillEntity
	 */
	void saveWaybillHomeImpInfo(WaybillDto waybillDto);
	
	/**
	 * 保存家装运单更改提交时需要向家装模块推送信息 ,将带有特殊增值服务标识的运单更改信息保存
	 * @param waybillRfcEntity
	 */
	void saveWaybillHomeImpInfoRfc(WaybillRfcEntity waybillRfcEntity);
	
	/**
	 * 更改家装运单推送信息
	 * @param waybillHomeImpEntity
	 */
	void updateWaybillHomeImpInfo(WaybillHomeImpEntity waybillHomeImpEntity);
	
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
	List<WaybillHomeImpEntity> quaryUnPushedWaybillHomeInfo();
	
	/**
	 * FOSS向DOP推送带有特殊增值服务标识的家装运单信息
	 */
	void pushWaybillHomeInfo();
	
	/*void rePushWaybillHomeInfo(WaybillHomeImpEntity waybillHomeImpEntity);*/
	
	
	/**
	 * 更新德邦家装运单job_id进行分组推送
	 * @param jobId
	 * @param queryNum
	 * @return
	 */
	WaybillHomeImpPushVo updateWaybillInfoPushForJobTopNum(String jobId, String queryNum);
	
	/**
	 * 根据Job_id查询用于推送德邦家装运单信息集合。
	 * @param jobId
	 * @return
	 */
	List<WaybillHomeImpEntity> queryWaybillHomeInfoPushMessageByJobId(String jobId);

	void pushInfo(List<WaybillHomeImpEntity> waybillList);

	boolean isAbledPush(String wayBillNo);

}










