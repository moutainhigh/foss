package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.ILostCargoNotifyDao
 * @author: foss-yuting
 * @description: 丢货差错自动上报
 * @date:2014年12月3日 下午2:27:21
 */
public interface ILostCargoNotifyDao {
	/**
	 * 获取上报oa丢货数据
	 */
	List<LostCargoReportDto> queryReportOAList();

}
