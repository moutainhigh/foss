package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;

public interface IDopSignService extends IService{
	
	
	/**
	 * 根据条件查询DOP传来的信息（总）
	 * @param dto
	 * @param start
	 * @param limet
	 * @return
	 */
	DopSignEntity queryDopCacheByParams(SignDto signDto);
	/*
	 * 查询家装反签收
	 */
	boolean queryDopSignRfc(SignDto dto);
	/**
	 * 发送签收信息给DOP
	 * @param waybillNo
	 */
	void sendDopSignMessage(String waybillNo,String situation);
	/**
	 * 更新反签收标记
	 */
	int updateRfc(String waybillNo);
	//反签收后回传给DOP签收信息
	void sendDopRfcSignMessage(String waybillNo);
}
