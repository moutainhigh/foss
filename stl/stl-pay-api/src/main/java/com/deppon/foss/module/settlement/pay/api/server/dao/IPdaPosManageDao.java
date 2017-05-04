package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;

/**
 * 提供接口功PDA和FOSS系统对POS刷卡管理和明细数据的新增和修改
 * 
 * @ClassName: IPdaPosManageService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-17 下午1:38:55
 */
public interface IPdaPosManageDao {
	/**
	 * 插入POS刷卡流水信息
	 * 
	 * @Title: insertPosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            :POS刷卡产生的数据
	 */
	int addPosCardMessage(PosCardEntity entity);

	/**
	 * 插入POS刷卡明细数据
	 * 
	 * @Title: insertPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param dto
	 *            明细集合
	 */
	int addPosCardDetail(List<PosCardDetailEntity> dto);

	/**
	 * 更新POS刷卡金额
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            修改的数据
	 */
	int updatePosCardMessageAmount(PosCardEntity entity);

	/**
	 * 更新明细金额
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param dto
	 *            修改明细集合
	 */
	int updatePosCardDetailAmount(PosCardManageDto dto);

	/**
	 * 根据交易流水号去查询刷卡数据
	 * 
	 * @Title: queryPosCardData
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	List<PosCardEntity> queryPosCardData(PosCardManageDto dto);

	/**
	 * 单条插入POS刷卡明细数据
	 * 
	 * @Title: addPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	int addPosCardDetail(PosCardDetailEntity entity);

	/**
	 * 单条更新
	 * 
	 * @Title: updateSinglePosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	int updateSinglePosCardDetail(PosCardDetailEntity entity);

	/**
	 * 根据单据号去更新T+0已使用流水号金额和未使用金额
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	int updatePosCardByNumber(PosCardDetailEntity entity);

	/**
	 * 根据运单号去查询到达应收部门
	 * 
	 * @Title: queryDRDeptByWaybillNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	PosCardEntity queryDRDeptByWaybillNo(PosCardDetailEntity entity);

	/**
	 * 根据运单号去查询到达应收部门或者出发部门
	 * 
	 * @Title: queryDRORDeptByWaybillNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	PosCardEntity queryDRORDeptByWaybillNo(PosCardDetailEntity entity);

	/**
	 * 更具运单号去查询对账单未核销金额
	 * 
	 * @Title:queryUnpaidAmountByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	PosCardEntity queryUnpaidAmountByNo(String invoiceNo);

	/**
	 * 根据单据号获取结清货款的未核销金额
	 * 
	 * @Title:queryUnverifyAmountByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	PosCardEntity queryUnverifyAmountByNo(String invoiceNo);

	/**
	 * 根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据
	 */
	int insertOrUpdateDetail(PosCardDetailEntity entity);

	/***************** 业务异常处理接口 *********************/

	/**
	 * 反结清 作废还款单 作废小票 作废预收单 更新T+0已使用流水号金额，未使用金额
	 * 
	 * 参数：单据号和流水号金额和减少的金额
	 * 
	 * @Title: updatePosByNoForMoney
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	int updatePosByNoForMoney(PosCardDetailEntity entity);

	/**
	 * 反结清 作废还款单 作废小票 作废预收单 作废明细数据，释放T+0中的已使用流水号金额和未使用金额 参数：单据号和交易流水号、单据金额
	 * 
	 * @Title: deleteDetailByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	int deleteDetailByNo(PosCardDetailEntity entity);

	/**
	 * 根据单据号和交易流水号去查询明细数据
	 * 
	 * @Title: queryDetailsByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	List<PosCardDetailEntity> queryDetailsByNo(PosCardDetailEntity entity);

	/**
	 * 根据单据号去更新对账单总金额
	 * 
	 * @Title: updateStatementAmout
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:44:31
	 */
	int updateStatementAmount(PosCardDetailEntity entity);

	/**
	 * 删除手动导入的异常流水数据
	 *
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午1:35:49
	 */
	int deleteExceptionData(PosCardEntity posCardEntity);
	
	/**
	 * 更新流水号金额，异常数据操作
	 * 
	 * @Title: updateExceptionDataByNum
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-29 下午1:35:49
	 */
	int updateExceptionDataByNum(PosCardDetailEntity entity);
	 
	/**
	 * 作废打款信息
	 * @Title: cancelPosCardbyParam 
	 * @author： 269052 |周禹安
	 * @date 2017-1-9 下午7:39:56
	 */
	int cancelPosCardbyParam(Map<String,String> map);
	
	/**
	 * 暂用于更新冻结状态
	 *
	 * @Title: updatePosCardEntitys
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午1:35:49
	 */
	int updatePosCardEntitys(PosCardEntity posCardEntity);

	
}
