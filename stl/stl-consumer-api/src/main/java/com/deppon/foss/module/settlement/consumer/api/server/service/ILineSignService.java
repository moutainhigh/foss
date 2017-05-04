package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/**
 * 
 * 签收确认收入服务（接送货专线签收、接送货空运偏线签收、接送货签收变更调用）
 * @author 099995-foss-wujiangtao
 * @date 2012-10-13 上午9:00:37
 * @since
 * @version
 */
public interface ILineSignService extends IService{
	/**
	 * 确认签收 
	 * @author 099995-foss-wujiangtao
	 * @date   2012-10-13 上午10:42:17
	 * @param  dto  存放接送货接口参数
	 * @param dto【waybillNo  运单号       productType 运输性质
	 * 				signOrgCode 签收部门编码     signOrgName    签收部门名称
	 *				businessDate 签收日期       isWholeVehicle 是否整车运单
	 * 				isPdaSign      是否PDA签收】
	 * @return
	 */
	void confirmTaking(LineSignDto dto,CurrentInfo currentInfo);
	
	/**
	 * 反确认签收
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 上午10:42:53
	 * @param dto【waybillNo      运单号
	 * 				productType    运输性质
	 * 				signOrgCode    反签收部门编码
	 *				signOrgName    反签收部门名称
	 *				businessDate   业务日期 】
	 * @return
	 */
	void reverseConfirmTaking(LineSignDto dto,CurrentInfo currentInfo);
	
	/**
	 * 异常签收转正常签收
	 * @author lianghaisheng
	 * @date 2013-08-05
	 * 
	 * */
	void reverseToNormalSignal(String waybillNo,CurrentInfo currentInfo);
}
