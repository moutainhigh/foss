package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

/**
 * 应收单查询service接口
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 3:46:05 PM
 */
public interface IBillReceivableQueryService extends IService{
	
	/**
	 * 应收单查询service接口--根据业务日期查询应收单
	 * @author foss-zhangxiaohui
	 * @date Oct 17, 2012 3:48:05 PM
	 */
	List<BillReceivableEntity> queryBillReceivableByBusinessDate(int offset,int start,BillReceivableDto billReceivableDto,
			CurrentInfo cInfo);

	/**
	 * 应收单查询service接口--根据记账日期查询应收单
	 * @author foss-zhangxiaohui
	 * @date Dec 4, 2012 9:26:17 AM
	 */
	List<BillReceivableEntity> queryBillReceivableByAccountDate(int offset,int start,BillReceivableDto billReceivableDto,
			CurrentInfo cInfo);

	/**
	 * 应收单查询service接口--根据业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:19:55 AM
	 */
	BillReceivableDto queryTotalAmountByBusinessDate(BillReceivableDto billReceivableDto,
			CurrentInfo cInfo);

	/**
	 * 应收单查询service接口--根据业务日期查询数据库中记录总条数，公布价运费、代收货款手续费、保价费
	 * @date Nov 6, 2012 10:19:55 AM
	 */
	BillReceivableDto queryDiscountTotalAmountByBusinessDate(BillReceivableDto billReceivableDto);
	
	/**
	 * 应收单查询service接口--根据记账日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Dec 10, 2012 11:53:55 AM
	 */
	BillReceivableDto queryTotalAmountByAccountDate(BillReceivableDto billReceivableDto,
			CurrentInfo cInfo);
	
	/**
	 * 标记、反标记应收单
	 * @param receivableNosArray 
	 */
	void stampReceivable(String[] receivableNosArray,String stamp,CurrentInfo cInfo);
}
