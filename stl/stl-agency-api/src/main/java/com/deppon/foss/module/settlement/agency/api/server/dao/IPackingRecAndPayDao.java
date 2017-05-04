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
package com.deppon.foss.module.settlement.agency.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-7 下午5:15:57,content: </p>
 * @author 105762
 * @date 2014-6-7 下午5:15:57
 * @since
 * @version
 */
public interface IPackingRecAndPayDao {

	/**
	 * <p>按记账日期查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:18:25
	 * @param dto
	 * @return 
	 * @see
	 */
	List<PackingRecAndPayDto> queryByAccountDate(PackingRecAndPayQueryDto dto);

	/**
	 * <p>按运单号查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:18:31
	 * @param dto
	 * @see
	 */
	List<PackingRecAndPayDto> queryByWaybillNo(PackingRecAndPayQueryDto dto);

	/**
	 * <p>按应收应付单号查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:18:46
	 * @param dto
	 * @see
	 */
	List<PackingRecAndPayDto> queryByBillNo(PackingRecAndPayQueryDto dto);

	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 105762
	 * @date 2014-7-8 下午6:02:50
	 * @param packingRecAndPayTfrDtoList
	 * @param billPayableBillTypeWoodenPayable
	 * @param payableType
	 * @return
	 * @see
	 */
	List<BillPayableEntity> queryByDtosAndBillTypePayableType(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList,
			String billPayableBillTypeWoodenPayable, String payableType);

	/**
	 * <p>按记账日期查询总数</p> 
	 * @author 105762
	 * @date 2014-7-15 上午10:11:11
	 * @param dto
	 * @return
	 * @see
	 */
    Map queryTotalCountByAccountDate(PackingRecAndPayQueryDto dto);
}
