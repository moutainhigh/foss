package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillCashCollectionDto;

/**
 * 新增现金收款单DAO
 * @author ibm-zhuwei
 * @date 2012-10-24 上午11:32:49
 */
public interface IBillCashCollectionEntityDao {

	// -------------------- write methods --------------------
	
    /**
     * 新增现金收款单
     * @author ibm-zhuwei
     * @date 2012-10-24 上午11:33:00
     * @param entity 现金收款单
     * @return
     */
    int add(BillCashCollectionEntity entity);
    
    /**
     * 红冲现金收款单，原记录置为失效状态
     * @author ibm-zhuwei
     * @date 2012-10-23 下午3:35:31
     * @param entity 现金收款单
     * @return
     */
    int updateByWriteBack(BillCashCollectionEntity entity);

    /**
     * 签收确认(反确认)收入日期
     * @author 099995-foss-wujiangtao
     * @date 2012-10-22 上午11:52:25
     * @param entity 现金收款单
     * @return
     * @see
     */
    int updateByConfirmIncome(BillCashCollectionEntity entity);
    
    /**
     * 收银确认
     * @author ibm-zhuwei
     * @date 2012-12-17 上午11:53:26
     * @param dto 现金收款单DTO
     * @return
     */
    int updateByConfirmCashier(BillCashCollectionDto dto);

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
     * 根据传入的一到多个现金收款单号，获取一到多条现金收款单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-12 下午6:35:53
     * @param cashCollectionNos  现金收款单号集合
     * @param active             是否有效
     * @return
     */
    List<BillCashCollectionEntity> queryByCashCollectionNOs(List<String> cashCollectionNos,String active);
    
    /**
     * 根据传入的一到多个来源单号和来源单据类型，获取一到多条现金收款单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-22 下午7:42:14
     * @param sourceBillNos 来源单据号
     * @param sourceBillType 来源单据类型
     * @param active 是否有效
     * @return
     * @see
     */
    List<BillCashCollectionEntity> queryBySourceBillNOs(
			List<String> sourceBillNos,String sourceBillType,String active);
    
    /**
     * 根据来源单号集合和部门编码集合，查询现金收款单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-22 上午9:15:06
     * @param sourceBillNos
     * @param sourceBillType
     * @param orgCodes
     * @param active
     * @param currentInfo
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
