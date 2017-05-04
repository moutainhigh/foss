package com.deppon.foss.module.pickup.sign.api.server.service;


import com.deppon.esb.inteface.domain.gis.HisSignDataRequest;
import com.deppon.esb.inteface.domain.gis.HisSignDataTeamRequest;
import com.deppon.foss.framework.service.IService;

import java.util.List;

/* 	一、FOSS信息发送
     1、FOSS将已全部签收（包含正常签收、异常签收）运单的【运单号】、【收货人地址的省/市/区/四五级地址（分开传输）】、【运输性质】、【提货方式】、【目的站】传给GIS；
     2、传输时间为：每日凌晨4点传给GIS。（与原有时间点保持一致）
      3、FOSS系统进行运单识别：
     ①对于有【更改单-客户要求-变更类型：转货/返货】的运单进行剔除，不传给GIS；
     ②对于【运输性质】为【精准空运】、【汽运偏线】的运单进行剔除，不传给GIS；
     ③对于【提货方式】为【内部带货自提】的运单进行剔除，不传给GIS；*/

public interface IGisLTLSignResultService extends IService {



	/**
	 *FOSS将已全部签收（包含正常签收、异常签收）运单的【运单号】、【收货人地址的省/市/区/四五级地址（分开传输）】/code编码、【运输性质】、【提货方式】、【目的站】传给GIS；
	 传输时间为：每日凌晨4点传给GIS
	 *306566
	 */
	void sendGisLTLSignResultInfo();

	/**
	 * 调用异步接口同步到GIS
	 * @param HisSignDataRequest
	 */
    //void sendSignResultINfoToGIS(List<HisSignDataRequest> hisSignDatalist);
    void sendSignResultINfoToGIS( HisSignDataTeamRequest hisSignDataTeamRequest);
	/**
	 * Created by 306566 on 2017/3/2.
	 * 根据条件查询签收接结果目的站匹配信息
	 */
	List<HisSignDataRequest> queryLTLSignResultInfo();
}
