/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年3月3日 上午10:18:12
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWscWayBillManageDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IWscWayBillManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;

/**
 * 
* @description: 待刷卡运单管理业务接口实现(管理系统页面使用)
* @className: WscWayBillManageService
* 
* @author panshiqi 309613
* @date 2016年3月3日 上午10:18:16 
*
 */
public class WscWayBillManageService implements IWscWayBillManageService {

	/**
	 * 数据操作对象
	 */
	IWscWayBillManageDao stlPayWscWayBillManageDao;

	/**  
	 * 设置 数据操作对象  
	 * @param stlPayWscWayBillManageDao 数据操作对象  
	 */
	public void setStlPayWscWayBillManageDao(IWscWayBillManageDao stlPayWscWayBillManageDao) {
		this.stlPayWscWayBillManageDao = stlPayWscWayBillManageDao;
	}

	/**
	 * 获取日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WscWayBillManageService.class);

	/**
	 * 根据查询条件获取待刷卡运单数据
	 */
	@Override
	public WscWayBillManageDto queryBySearchCondition(WscWayBillManageDto dto, int start, int limit,
			CurrentInfo currentInfo) throws Exception {

		// 记录日志
		LOGGER.info("查询待刷卡运单开始，开始日期和结束日期：" + dto.getQueryStartDate() + " ," + dto.getQueryEndDate());

		// 实例化dto
		WscWayBillManageDto resultDto = new WscWayBillManageDto();

		// 封装分页参数
		RowBounds rb = new RowBounds(start, limit);

		// 获取符合条件的数据总条数
		Map<String, BigDecimal> countIDAndSumAmount = stlPayWscWayBillManageDao.queryCountBySearchCondition(dto);

		// 数据总条数
		int totalCount = 0;
		// 查询到的数据总待刷卡金额
		double totalAmount = 0;

		if (countIDAndSumAmount != null) {
			if (countIDAndSumAmount.get("COUNTID") != null) {
				totalCount = countIDAndSumAmount.get("COUNTID").intValue();
			}
			if (countIDAndSumAmount.get("SUMAMOUNT") != null) {
				totalAmount = countIDAndSumAmount.get("SUMAMOUNT").doubleValue();
			}
		}

		// 如果存在符合条件的
		List<WSCWayBillEntity> wscWayBillList = new ArrayList<WSCWayBillEntity>();

		// 仅当存在符合条件的数据时才查询数据
		if (totalCount > 0) {
			// 调用dao层进行数据查询
			wscWayBillList = stlPayWscWayBillManageDao.queryBySearchCondition(rb, dto);
		}

		// 设置数据总条数(分页)
		resultDto.setTotalCount(totalCount);

		// 设置数据总待刷卡金额(合计)
		resultDto.setTotalAmount(totalAmount);

		// 设置查询数据结果
		resultDto.setResultList(wscWayBillList);

		// 记录日志
		LOGGER.info("查询待刷卡运单结束");
		return resultDto;
	}

	/**
	 * 查询在id集合中的待刷卡运单数据
	 */
	@Override
	public WscWayBillManageDto queryByIDs(List<String> ids, CurrentInfo currentInfo) throws Exception {

		// 记录日志
		LOGGER.info("根据ID集合查询待刷卡运单开始，ID：" + ids.toString());

		// 实例化dto
		WscWayBillManageDto resultDto = new WscWayBillManageDto();

		// 如果存在符合条件的
		List<WSCWayBillEntity> wscWayBillList = stlPayWscWayBillManageDao.queryByIDs(ids, currentInfo);

		// 设置查询数据结果
		resultDto.setResultList(wscWayBillList);

		// 记录日志
		LOGGER.info("根据ID集合查询待刷卡运单结束");
		return resultDto;
	}
}
