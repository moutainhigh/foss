package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillCashCollectionDto;

/**
 * 现金收款单服务
 * @author Administrator
 *
 */
public interface IBillCashCollectionService extends IService {

	// -------------------- write methods --------------------
	
	/**
	 * 新增现金收款单
	 * @author ibm-zhuwei
	 * @date 2012-10-17 下午1:25:14
	 * @param entity
	 * @param currentInfo
	 */
	void addBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 红冲现金收款单
	 * @author ibm-zhuwei
	 * @date 2012-10-17 下午1:25:27
	 * @param entity
	 * @param currentInfo
	 */
	void writeBackBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo);

	/**
	 * 签收时 确认收入日期
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 上午11:57:58
	 * @param entity
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void confirmIncomeBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 反签收时，置空签收日期
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 上午11:58:42
	 * @param entity
	 * @param currentInfo
	 * @return 
	 * @see
	 */
	void reverseConfirmIncomeBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 批量确认收银
	 * @author ibm-zhuwei
	 * @date 2012-12-17 上午11:17:46
	 * @param dto
	 * @param currentInfo
	 */
	void confirmCashierBillCashCollection(BillCashCollectionDto dto, CurrentInfo currentInfo);

	// -------------------- valid methods --------------------
	
	/**
	 * 验证一个运单是否存在多条现金收款单
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 下午7:28:55
	 * @param list
	 * @see
	 */
	void validateWaybillForBillCashCollection(List<BillCashCollectionEntity> list);
	
	// -------------------- read methods --------------------
	
	/**
	 * 根据传入的一到多个现金收款单Id号，获取一到多条现金收款单信息
	 * @author foss-pengzhen
	 * @date 2013-3-26 上午9:26:46
	 * @param cashCollectionIds  现金收款单Id
	 * @return
	 * @see
	 */
	List<BillCashCollectionEntity> queryByCashCollectionIds(
			List<String> cashCollectionIds, String active);
	
	/**
	 * 
	 * 根据传入的一到多个现金收款单号，获取一到多条现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:37:53
	 * @param cashCollectionNos  现金收款单号集合
	 * @param active      是否有效
	 * @return
	 */
	List<BillCashCollectionEntity> queryByCashCollectionNOs(List<String> cashCollectionNos, String active);
	
	/**
	 * 根据传入的一个现金收款单号，获取一条现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:38:38
	 * @param cashCollectionNo  现金收款单号
	 * @param active 			是否有效
	 * @return
	 */
	BillCashCollectionEntity queryByCashCollectionNO(String cashCollectionNo, String active);
	
	/**
	 * 根据传入的一到多个来源单号和来源单据类型，获取一到多条现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午8:30:57
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param active
	 * @return
	 * @see
	 */
	List<BillCashCollectionEntity> queryBySourceBillNOs(
			List<String> sourceBillNos,String sourceBillType, String active);
	
	/**
	 * 根据来源单号集合和部门编码集合，查询现金收款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:13:44
	 * @param sourceBillNos  必填项
	 * @param sourceBillType
	 * @param active
	 * @param orgCodes
	 * @return
	 */
	List<BillCashCollectionEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos,String sourceBillType,List<String> orgCodes, String active,CurrentInfo currentInfo);
	
	/**
     * 根据银联交易流水号，查询现金收款单信息
     * @author 045738-foss-maojianqiang
     * @date 2013-7-22 上午9:18:52
     * @param sourceBillNos
     * @param sourceBillType
     * @param orgCodes
     * @param active
     * @param currentInfo
     * @return
     */
	List<BillCashCollectionEntity> queryByBatchNos(
			List<String> batchNos,String active, CurrentInfo currentInfo);
}
