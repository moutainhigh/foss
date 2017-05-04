/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年3月3日 上午10:10:31
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;

/**
 * 
* @description: 待刷卡运单管理数据层接口(管理系统页面使用)
* @className: IWscWayBillManageDao
* 
* @author panshiqi 309613
* @date 2016年3月3日 上午10:10:35 
*
 */
public interface IWscWayBillManageDao {

	/**
	 * 
	* @description: 根据查询条件获取待刷卡运单数据
	* @title: queryBySearchCondition
	* @author panshiqi 309613
	* @date 2016年3月3日 上午10:14:54 
	* @param rb 分页参数
	* @param dto 查询条件
	* @return 待刷卡运单数据集合
	 */
	public List<WSCWayBillEntity> queryBySearchCondition(RowBounds rb, WscWayBillManageDto dto) throws Exception;

	/**
	 * 
	* @description: 根据查询条件获取待刷卡运单数据条数
	* @title: queryCountBySearchCondition
	* @author panshiqi 309613
	* @date 2016年3月3日 下午2:07:19 
	* @param dto
	* @return
	* @throws Exception
	 */
	public Map<String, BigDecimal> queryCountBySearchCondition(WscWayBillManageDto dto) throws Exception;

	/**
	 * 
	* @description: 查询在id集合中的待刷卡运单数据
	* @title: queryByIDs
	* @author panshiqi 309613
	* @date 2016年3月4日 下午3:19:30 
	* @param ids
	* @param currentInfo
	* @return
	* @throws Exception
	 */
	public List<WSCWayBillEntity> queryByIDs(List<String> ids, CurrentInfo currentInfo) throws Exception;

}
