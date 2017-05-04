package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;

public interface IConsultPriceDao {

	/**
	 * 将TPS传过来的信息添加到数据表
	 * @param consultPriceEntity 询价信息实体
	 * @return 返回是否插入成功的结果
	 */
	void addConsultPriceInfo(ConsultPriceEntity consultPriceEntity);
	
	/**
	 * 根据询价编号查询询价信息
	 * @param consultPriceNo 询价编号
	 * @return 返回查询结果
	 */
	List<ConsultPriceEntity> queryByConsultPriceNo(String consultPriceNo);

    /**
     * 更具询价编号查询是不是已经使用过
     * @param consultPriceNo
     * @return
     */
	List<ConsultPriceEntity> queryIfConsultPriceNoUsed(String consultPriceNo);
	
}
