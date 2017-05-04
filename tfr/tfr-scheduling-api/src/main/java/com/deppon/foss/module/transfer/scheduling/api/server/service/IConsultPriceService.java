package com.deppon.foss.module.transfer.scheduling.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;

public interface IConsultPriceService extends IService{
	/**
	 * 将TPS传过来的询价信息添加到数据库
	 * @param consultPriceNo 询价编号
	 * @param quotedInfo 报价信息
	 * @param needVehicleDept 请车部门
	 * @return
	 */
	void addConsultPriceInfo(ConsultPriceEntity consultPriceEntity);
	
	/**
	 * 根据询价编号查找询价信息
	 * @param consultPriceNo 询价编号
	 * @return 返回查询结果
	 */
	ConsultPriceEntity queryByConsultPriceNo(String consultPriceNo);
}
