package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;

/**
 * @项目：快递自动补录项目
 * @功能：与其它部门合作的所有接口（只针对快递自动补录项目）
 * @author:218371-foss-zhaoyanjun
 * @date:2015-07-15上午09:24
 */
public interface IFossWithOthersForEmaService{
	//将快递自动补录项目运单开单结果传回录单中心
	public String postRecordCenter(String waybillNo,String billNumberState,Date uploadTime,String context);
}
