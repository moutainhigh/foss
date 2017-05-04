/**
 * company   : com.deppon
 * poroject   : foss结算
 * copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : panshiqi (309613)
 * @date     : 2016年3月3日 上午10:04:46
 * @version  : v1.0
 */
package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;

/**
 * 
* @description: 待刷卡运单业务层接口(管理系统页面使用)
* @className: IWscWayBillManageService
* 
* @author panshiqi 309613
* @date 2016年3月3日 上午10:05:11 
*
 */
public interface IWscWayBillManageService {

	/**
	 * 
	* @description: 根据查询条件查询待刷卡运单数据
	* @title: queryBySearchCondition
	* @author panshiqi 309613
	* @date 2016年3月3日 上午10:06:47 
	* @param dto 待刷卡运单管理数据传输对象
	* @param start 查询开始位置
	* @param limit 查询范围
	* @param currentInfo 当前登录用户对象
	* @return 待刷卡运单管理数据传输对象
	 */
	public WscWayBillManageDto queryBySearchCondition(WscWayBillManageDto dto, int start, int limit,
			CurrentInfo currentInfo) throws Exception;

	/**
	 * 
	* @description: 查询在id集合中的待刷卡运单数据
	* @title: queryByIDs
	* @author panshiqi 309613
	* @date 2016年3月4日 下午3:14:24 
	* @param dto
	* @param currentInfo
	* @return
	* @throws Exception
	 */
	public WscWayBillManageDto queryByIDs(List<String> ids, CurrentInfo currentInfo) throws Exception;
}
