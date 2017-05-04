package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;

/**
 * 
 * 给网点规划同步线路信息客户端接口
 * @author 130566
 *
 */
public interface ISendLineInfoToWDGHService {
	/**
	 * 
	 *<p>接口同步方法</p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-12下午1:52:28
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	void syncLineInfo(List<LineEntity> dtos,String operateType);
	/**
	 * 
	 *<p>新接口同步方法</p>
	 *@author 130566 -qirongsheng
	 *@date 2016-1-27下午1:52:28
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	void syncLineInfos(LineEntity dtos, String operateType);
}
