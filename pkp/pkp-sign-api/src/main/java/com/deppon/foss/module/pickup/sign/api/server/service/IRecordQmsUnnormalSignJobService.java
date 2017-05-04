package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;

/**
 * @author: foss-231434-bieyexiong
 * @description: 新增异常线上划责差错 FOSS自动上报QMS	每XX分钟执行一次
 * @date:2016年02月19日 下午17:00:58
 */
public interface IRecordQmsUnnormalSignJobService extends IService{

	/**
	 * 新增异常线上划责差错 FOSS自动上报QMS	每XX分钟执行一次
	 * @author: foss-231434-bieyexiong
	 * @date:2016年02月19日 下午17:01:20
	 */
	void processRecordQmsUnnormalSign();
	
	/**
	 * 上报差错（防止一个单号出现异常而导致所有单号回滚，故将每个单号当成独立的事物，不互相影响）
	 * @author: foss-231434-bieyexiong
	 * @date:2016年02月19日 下午17:32:16
	 */
	void sendUnnormalSignInfo(RecordUnnormalSignWaybillDto unnormalDto);
	
}
