package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto;

/**
 * 查询现金还款单Service接口
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 3:23:41 PM
 */
public interface IBillCashCollectionQueryService {

	/**
	 * 查询现金还款单Service接口--根据业务日期查询现金收款单
	 * @author foss-zhangxiaohui
	 * @date Nov 2, 2012 3:35:09 PM
	 */
	List<BillCashCollectionEntity> queryBillCashCollectionByBusinessDate(int offset,int start,BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo);

	/**
	 * 查询现金还款单Service接口--根据记账日期查询现金收款单
	 * @author foss-zhangxiaohui
	 * @date Dec 4, 2012 9:09:19 AM
	 */
	List<BillCashCollectionEntity> queryBillCashCollectionByAccountDate(int offset,int start,BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo);
	
	/**
	 * 查询现金还款单Service接口--根据业务日期查询数据库中记录总条数,总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:41:23 AM
	 */
	BillCashCollectionQueryDto queryTotalAmountByBusinessDate(BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo);
	
	/**
	 * 查询现金还款单Service接口--根据记账日期查询数据库中记录总条数,总金额
	 * @author foss-zhangxiaohui
	 * @date Dec 10, 2012 10:41:23 AM
	 */
	BillCashCollectionQueryDto queryTotalAmountByAccountDate(BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo);
	
	
	/**
	 * 按来源单号查询现金收款单
	 * @author 095793-foss-LiQin
	 * @date 2013-4-3 上午9:30:40
	 * @param billCashCollectionQueryDto
	 * @param cInfo
	 * @return
	 */
	List<BillCashCollectionEntity> queryBillCashCollectionByWaybillNO(BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo);
}
