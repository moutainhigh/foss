package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;


/**
 * 预收单dao
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:28:20
 * @since
 * @version
 */
public interface IBillDepositReceivedUnverifyDao{

	/**
	 * 根据日期等参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	List<BillDepositReceivedEntity> queryDepositReceivedUnverifyParams(
			BillDepositReceivedPayDto billDepositReceivedPayDto,
			int start,int limit);
	
	/**
	 * 根据日期等参数查询对应的预收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public List<BillDepositReceivedEntity> queryDepositReceivedUnverifyParam(
			BillDepositReceivedPayDto billDepositReceivedPayDto);
	
	/**
	 * 根据日期等参数查询对应的预收单总数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	BillDepositReceivedPayDto queryDepositReceivedUnverifyParamsCount(
			BillDepositReceivedPayDto billDepositReceivedPayDto);

	/**
	 * 根据预收单参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	List<BillDepositReceivedEntity> queryDepositReceivedNos(
			List<String> depositReceivedNos,CurrentInfo currentInfo, String active,
			int start,int limit) ;
	
	/**
	 * 根据预收单参数查询对应的预收单总数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	BillDepositReceivedPayDto queryDepositReceivedNosCount(
			List<String> depositReceivedNos,CurrentInfo currentInfo, String active) ;

}
