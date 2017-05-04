package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

/**
 * 应收单Dao接口
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 5:48:07 PM
 */
public interface IBillReceivableQueryDao {
	
	/**
	 * 应收单Dao接口--根据业务日期查询应收单
	 * @author foss-zhangxiaohui
	 * @date Oct 17, 2012 5:53:55 PM
	 */
	List<BillReceivableEntity> queryBillReceivaebleByBusinessDate(int offset,int start,BillReceivableDto billReceivableDto);

	/**
	 * 应收单Dao接口--根据记账日期查询应收单
	 * @author foss-zhangxiaohui
	 * @date Oct 17, 2012 5:53:55 PM
	 */
	List<BillReceivableEntity> queryBillReceivaebleByAccountDate(int offset,int start,BillReceivableDto billReceivableDto);

	
	/**
	 * 应收单Dao接口--通过业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:17:55 AM
	 */
	BillReceivableDto queryTotalAmountByBusinessDate(BillReceivableDto billReceivableDto);

	/**
	 * 应收单Dao接口--通过业务日期查询数据库中记录总条数，代收货款手续费总金额，保价费总金额，公布价运费
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:17:55 AM
	 */
	BillReceivableDto queryDsicountTotalAmountByBusinessDate(BillReceivableDto billReceivableDto);
	
	/**
	 * 应收单Dao接口--通过记账日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:18:55 AM
	 */
	BillReceivableDto queryTotalAmountByAccountDate(BillReceivableDto billReceivableDto);
	
}
