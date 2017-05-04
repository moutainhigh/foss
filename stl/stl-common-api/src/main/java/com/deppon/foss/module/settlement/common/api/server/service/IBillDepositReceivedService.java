package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;

/**
 * 预收单服务
 * @author foss-qiaolifeng
 * @date 2012-10-22 下午2:07:44
 */
public interface IBillDepositReceivedService extends IService {

    /**
     * 修改应收单的对账单号及是否生成对账单字段值
     * 
     * @author 088933-foss-zhangjiheng
     * @date 2012-10-17 下午7:30:36
     */
    int updateBillDepositReceivedByMakeStatement(
	    BillDepositReceivedEntity entity,CurrentInfo info);
    
    /**
	 * 批量确认收银
	 * @author foss-pengzhen
	 * @date 2012-12-17 上午11:17:46
	 */
    void confirmCashierBillDepositReceived(BillDepositReceivedEntityDto dto, CurrentInfo currentInfo);
	
    /**
     * 批量修改应收单的对账单号及是否生成对账单字段值
     * @author foss-qiaolifeng
     * @date 2012-12-5 上午9:25:31
     */
    void batchUpdateBillDepositReceivedByMakeStatement(
    		BillDepositReceivedEntityDto dto,CurrentInfo info);

    /**
     * 核销时修改预收单的金额等字段信息
     * 
     * @author foss-qiaolifeng
     * @date 2012-10-19 上午11:21:40
     */
    void writeoffBillDepositReceived(BillDepositReceivedEntity entity, 
    		BigDecimal writeoffAmount,CurrentInfo info);

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
     * 
     * @author foss-qiaolifeng
     * @date 2012-10-19 下午1:40:42
     */
    List<BillDepositReceivedEntity> queryByDepositReceivedNOs(
	    List<String> depositReceivedNos, String active);
    
    
    /**
     * 根据传入的一个预收单号，获取一条预收单
     * @author foss-qiaolifeng
     * @date 2012-10-22 下午2:07:57
     */
    BillDepositReceivedEntity queryByDepositReceivedNo(
	    String depositReceivedNo,String active);

    
    /**
     * 新增预收单
     * @author foss-qiaolifeng
     * @date 2012-11-12 上午11:37:36
     */
    int addBillDepositReceived(BillDepositReceivedEntity entity,CurrentInfo info);
    
    /**
     * 作废预收单
     * @author foss-qiaolifeng
     * @date 2012-11-27 上午9:09:07
     */
    void disableBillDepositReceived(BillDepositReceivedEntity entity,CurrentInfo info);
    
    /**
     * 修改预收单的支付状态、付款单号、付款金额等信息
     * @author foss-qiaolifeng
     * @date 2012-11-27 上午9:09:07
     */
    void payForBillDepositReceived(BillDepositReceivedEntity entity, CurrentInfo info);
    /**
     * 取消预收单的支付状态、付款单号、付款金额等信息
     * @author foss-qiaolifeng
     * @date 2012-11-27 上午9:09:07
     */
    void cancelPayForBillDepositReceived(BillDepositReceivedEntity entity, CurrentInfo info);
    
    /**
     * 根据付款单号查询预收单
     * @author foss-qiaolifeng
     * @date 2012-12-4 下午3:19:16
     */
    List<BillDepositReceivedEntity> queryByPaymentNo(
    	   String paymentNo);
    
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
     * 根据汇款编号查询合伙人预收单是否存在
     * @param remitNo
     * @param active
     * @return BillDepositReceivedEntity 预收单
     */
    BillDepositReceivedEntity queryByRemitNo(String remitNo, String active);
}
