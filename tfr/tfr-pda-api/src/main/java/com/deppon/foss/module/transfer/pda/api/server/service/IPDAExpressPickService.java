package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.CreateDeliveryReceiptEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;

/**
 * 
 * 新增快递接货装车类型
 * @author alfred
 * @date 2015-1-28 下午2:59:53
 * @see
 */
public interface IPDAExpressPickService {
	/**新建任务:返回任务号*/
	public String createTask(LoadTaskDto loadTask);
	/**零担电子运单生成交接单生成*/
	public String createLTLPackHandoverbill(CreateDeliveryReceiptEntity deliveryReceiptEntity);
	//326027
	/**根据单号查询交接单信息*/
	public List<CreateDeliveryReceiptEntity> queryLTLPackHandoverbill(String waybillNo);
	/**提交任务*/
	public String submitLoadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode);
	/**扫描*/
	public void scan(LoadScanDetailDto scanDto);
	/**完成接货任务
	 * 为满足PDA新增接货 支持营业部卸车提供给接口
	 * 接送货完成接货接口**/
	public void finishPickupTask(String taskID,String orgCode,String vehicleNo,String operatorCode);
}
