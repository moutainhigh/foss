/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;

/**
 * 提供给中转  负责包装其他应收应付主要包装和辅助包装的处理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-13 下午2:19:49,content:TODO </p>
 * @author 105762
 * @date 2014-6-13 下午2:19:49
 * @since 1.6
 * @version 1.0
 */
public interface IPackingRecAndPayForTfrService {

	/**
	 * <p>新增接口</p> 
	 * @author 105762
	 * @date 2014-6-19 下午3:54:35
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	void add(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo);

	/**
	 * <p>更新</p> 
	 * @author 105762
	 * @date 2014-6-19 下午3:54:59
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	void update(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo);

	/**
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-6-19 下午3:55:09
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	void audit(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo);

	/**
	 * <p>反审核</p> 
	 * @author 105762
	 * @date 2014-6-19 下午3:55:41
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 *
	 * @param currentInfo
	 */
	void reverseAudit(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo);

	/**
	 * <p>作废</p> 
	 * @author 105762
	 * @date 2014-6-17 下午5:01:13
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	void invalid(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo);
}
