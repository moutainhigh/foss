package com.deppon.foss.module.settlement.common.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;

/**
 * 预收单公共DAO接口类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 上午10:57:44
 */
public interface IBillDepositReceivedEntityDao {

    /**
     * 新增预收单
     * @author foss-qiaolifeng
     * @date 2012-11-12 上午11:40:32
     * @param entity 预收单
     * @return
     */
    int add(BillDepositReceivedEntity entity);
    
    /**
     * 新增合伙人预收单
     * @author foss-guoxinru
     * @date 2016-1-12
     * @param entity 预收单
     * @return
     */
    int addPartner(BillDepositReceivedEntity entity);

    /**
     * 修改预收单的对账单号及是否生成对账单字段值
     * @author 088933-foss-zhangjiheng
     * @date 2012-10-17 下午7:30:36
     * @param entity 预收单
     * @return
     */
    int updateBillDepositReceivedByMakeStatement(
	    BillDepositReceivedEntity entity);
    
    /**
     * 批量修改应收单的对账单号及是否生成对账单字段值
     * @author foss-qiaolifeng
     * @date 2012-12-5 上午9:28:55
     * @param dto 预收单DTO
     * @return
     */
    int batchUpdateBillDepositReceivedByMakeStatement(BillDepositReceivedEntityDto dto);

    /**
     * 核销时修改预收单的金额等字段信息
     * @author foss-qiaolifeng
     * @date 2012-10-19 上午11:21:40
	 * @param entity 预收单
	 * @param writeOffAmount 核销金额
	 * @param currentInfo 当前用户
	 * @return
     */
	int writeOffBillDepositReceived(BillDepositReceivedEntity entity,
			BigDecimal writeOffAmount, CurrentInfo info);
    
	/**
	 * 根据传入的多个预收单id号，获取预收单列表信息
     * @author foss-pengzhen
     * @date 2012-10-19 下午1:40:42
     * @param depositReceivedIds 预收单号集合
     * @param active 是否有效
     * @return
	 */
	List<BillDepositReceivedEntity> queryByDepositReceivedIds(
			List<String> depositReceivedIds, String active);
			
    /**
     * 根据传入的多个预收单号，获取预收单列表信息
     * @author foss-qiaolifeng
     * @date 2012-10-19 下午1:40:42
     * @param depositReceivedNos 预收单号集合
     * @param active 是否有效
     * @return
     */
    List<BillDepositReceivedEntity> queryByDepositReceivedNOs(
		List<String> depositReceivedNos, String active);
    
    /**
     * 根据传入的一个预收单号，获取一条预收单
     * @author foss-qiaolifeng
     * @date 2012-10-22 下午2:07:57
     * @param depositReceivedNo 预收单号
     * @param active 是否有效
     * @return
     */
    BillDepositReceivedEntity queryByDepositReceivedNo(
	    String depositReceivedNo,String active);
    
    /**
     * 收银确认
     * @author foss-pengzhen
     * @date 2012-12-17 上午11:53:26
	 * @param dto 预收单DTO
	 * @return
     */
	int updateByConfirmCashier(BillDepositReceivedEntityDto dto);
	
    /**
     * 修改失效预收单
     * @author foss-qiaolifeng
     * @date 2012-11-27 上午9:11:06
     * @param entity 预收单
     * @return
     */
    int updateByTakeEffect(BillDepositReceivedEntity entity);
    
    /**
     * 修改预收单支付状态
     * @author foss-qiaolifeng
     * @date 2012-12-3 下午6:46:12
     * @param entity 预收单
     * @return
     */
    int updateByPaymentStatus(BillDepositReceivedEntity entity);
    
    /**
     * 根据付款单号查询预收单
     * @author foss-qiaolifeng
     * @date 2012-12-4 下午3:25:12
     * @param paymentNo 支付单号
     * @param active 是否有效
     * @param isRedBack 是否红冲
     * @return
     */
    List<BillDepositReceivedEntity> queryByPaymentNo(
    		String paymentNo, String active,String isRedBack);
    
    /**
     * 根据付款单号查询预收单
     * @author foss-pengzhen
     * @date 2012-12-4 下午3:25:12
     * @param paymentNo 支付单号
     * @param active 是否有效
     * @param isRedBack 是否红冲
     * @return
     */
    List<BillDepositReceivedEntity> queryByPaymentNos(
    		List<String> paymentNos, String active,String isRedBack);

    /**
     * 根据汇款编号查询合伙人预收单
     * @param remitNo 汇款编号
     * @param active 是否有效
     * @return List<BillDepositReceivedEntity>
     */
	List<BillDepositReceivedEntity> queryByRemitNo(String remitNo, String active);
}
