package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCODEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCodAuditEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillTransactionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntityForVTS;
import com.deppon.foss.module.settlement.common.api.shared.dto.ActualFreightVTSEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VTSCodAuditDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillFRcQueryByWaybillNoVTSDto;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 17:12:20
 * vts自动反签收反结清Dao
 *
 */
public interface IReverseSignSettleDao {
	
	 /**
	  * 根据条件查询代收货款支付审核
	  * @param dto
	  * @return
	  */
	 List<VTSCodAuditEntity> selectByCondition(VTSCodAuditDto dto);
	 
	 /**
	  * 按单号查询代收货款数据
	  * @param dto
	  * @return
	  */
	VTSCODEntity queryByWaybillNo(VTSCODEntity dto);
	
	/**
	 * 反标识业务完结
	 * @author 218392 张永雪
	 * @date 
	 */
	boolean updateReverseBusinessOver(VTSWaybillTransactionEntity waybillTransactionEntity);
	
	/**
	 * 查询签收结果表
	 * @218392 zhangyongxue
	 */
	 VTSWaybillSignResultEntity queryWaybillSignResult(VTSWaybillSignResultEntity entity);
	
	
	/**
	 * 修改签收结果表
	 * @218392 zhangyongxue
	 */
	 int updateWaybillSignResult(VTSWaybillSignResultEntity entity);
	 
	 /**
	  * 根据ID查询到达付款信息表
	  * @218392 zhangyongxue 
	  * @date 2016-06-15 21:15:20
	  */
	 List<VTSRepaymentEntity> searchRepaymentById(List<String> id);
	 
	 /** 修改付款数据
	 * @author FOSS结算开发组 @218392 张永雪
	 * @date 2016-06-16  10:30:00
	 */
	int updateRepaymentById(VTSRepaymentEntity record);
	
	/**
	 * 根据单据号和交易流水号去查询明细数据
	 * 
	 * @Title: queryDetailsByNo
	 * @author：218392  张永雪
	 */
	List<PosCardDetailEntity> queryDetailsByNo(PosCardDetailEntity entity);
	
	/**
	 * 反结清 作废还款单 作废小票 作废预收单 更新T+0已使用流水号金额，未使用金额
	 * 参数：单据号和流水号金额和减少的金额
	 * @Title: updatePosByNoForMoney
	 * @author： 218392  张永雪
	 */
	int updatePosByNoForMoney(PosCardDetailEntity entity);
	 
	/**
	 * 反结清 作废还款单 作废小票 作废预收单 作废明细数据，释放T+0中的已使用流水号金额和未使用金额 参数：单据号和交易流水号、单据金额
	 * @Title: deleteDetailByNo
	 * @author： 218392 张永雪
	 */
	int deleteDetailByNo(PosCardDetailEntity entity);
	 
	/**
	 * 根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据
	 */
	int insertOrUpdateDetail(PosCardDetailEntity entity);
	
	/**
	 * 通过运单编号查询运单
	 * @param waybill
	 * @218392 张永雪
	 * @2016-06-16 21:32:00
	 */
	WaybillEntityForVTS queryWaybillByNo(String waybillNo);
	
	/**
	 * 传入运单号，判断传入的运单号是否存在未处理的更改单
	 * @author 218392 zhangyongxue
	 * @date 2016-06-16 23:02:00
	 */
	String queryWaybillRfcByWaybillNo(WaybillFRcQueryByWaybillNoVTSDto waybillFRcQueryByWaybillNoVTSDto);
	
	/**
	 * 反结清:修改实际承运表的状态
	 */
	void updateActualFreightSettleStatusByNo(ActualFreightVTSEntity actualFreightVTSEntity);
	
	/**
	 * 查询结清货款的时候，到达付款信息表：运单号，有效，财务单据已生成，财务单据未生成
	 */
	List<VTSResverSettleRepaymentEntity> queryRepaymentList(VTSResverSettleRepaymentEntity re);
	
}
