package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

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
public interface IPdaPosManageService {
	/**
	 * 插入T+0和明细数据(总的实现接口)
	 * 
	 * @Title: insertPdaPosCardAndDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-12 下午5:19:36
	 */
	String insertPdaPosCardAndDetail(PosCardEntity entity);

	/**
	 * 根据交易流水号和部门编码查询POS刷卡数据
	 * 
	 * @Title: queryPosCardData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param dto
	 *            :invoiceNo 和 tradeSerialNo
	 * @date 2016-3-16 上午8:47:01
	 */
	PosCardManageDto queryPosCardData(PosCardManageDto dto);

	/**
	 * 根据单据号和交易流水号去查询明细数据
	 * 
	 * @Title: queryDetailsByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	List<PosCardDetailEntity> queryPosDetailsByNo(PosCardDetailEntity entity);

	/**
	 * 单条插入POS刷卡明细数据
	 * 
	 * @Title: insertPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            明细实体
	 * @date 2016-3-16 上午8:47:01
	 */
	String addPosCardDetail(PosCardDetailEntity entity);

	/**
	 * 插入POS刷卡数据
	 * 
	 * @Title: insertPosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            :POS刷卡产生的数据
	 * @date 2016-3-16 上午8:47:01
	 */
	String insertPosCardMessage(PosCardEntity entity, String invoiceNo);

	/**
	 * 根据交流水号去更新POS刷卡已使用流水号金额，未使用金额
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	String updatePosCardMessageAmount(PosCardEntity entity);

	/**
	 * 单条更新(根据单据号和交易流水号更新明细中交易流水号金额和单据未核销金额)
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            明细数据
	 * @date 2016-3-16 上午8:47:01
	 */
	String updateSinglePosCardDetail(PosCardDetailEntity entity);

	/**
	 * 批量插入POS刷卡明细数据：提供给PDA和快递系统
	 * 
	 * @Title: insertPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	String insertPosCardDetail(PosCardEntity entity, String isKd,
			String belongModule);

	/**
	 * 批量插入POS刷卡明细数据：提供给FOSS后台
	 * 
	 * @Title: insertPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	String insertPosCardDetail(List<PosCardDetailEntity> list);

	/**
	 * (批量插入)插入POS刷卡数据到T+0报表
	 * 
	 * @param list
	 *            :刷卡数据和明细数据一起
	 * @Title: insertPosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	String insertPosCardData(List<PosCardEntity> list);

	/**
	 * 根据交易流水号去更新T+0报表已使用流水号金额和未使用金额
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	String updatePosCardByNum(PosCardDetailEntity entity);

	/**
	 * 根据单据号和交易流水号去查询明细数据,存在则更新，不存在则插入数据
	 * 
	 * @Title: insertOrUpdateDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	String insertOrUpdateDetail(PosCardDetailEntity detail);

	/********** 异常处理 ****************/
	/**
	 * 反结清 作废还款单 作废小票 作废预收单
	 * 
	 * 作废明细数据，释放T+0中的已使用流水号金额和未使用金额 参数：单据号
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-3-16 上午8:47:01
	 */
	String updatePosAndDeleteDetail(PosCardDetailEntity entity);

	/**
	 * 反核销---修改对账单总金额，释放已使用流水号金额
	 * 
	 * @Title: handleReverseBillWriterOff
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:34:00
	 */
	String handleReverseBillWriterOff(PosCardDetailEntity entity);

	/**
	 * 修改对账单总金额
	 * 
	 * @Title: updateStatementAmout
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:34:53
	 */
	String updateStatementAmout(PosCardDetailEntity entity);

	/**
	 * 作废还款单
	 * 
	 * @Title: disableRepayment
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:34:53
	 */
	String disableRepayment(PosCardDetailEntity detail);

	/**
	 * 释放数据
	 * 
	 * @Title: updateExceptionData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-28 下午2:02:07
	 */
	String updateExceptionData(PosCardDetailEntity detail);

	/**
	 * 删除手动导入的异常交易流水号
	 * 
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午2:02:07
	 */
	String deleteExceptionData(PosCardEntity posCardEntity);

	/**
	 * POS机刷卡管理--更新冻结状态
	 *
	 * @Title: frozenPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	String updatePosCardEntitys(List<PosCardEntity> checkQueryResult);
	
	/**
	 * 作废T+0 灰度更改
	 * 
	 * @Title: updatePosCardByParam
	 * @author： 269052 |周禹安
	 * @date 2017-1-9 下午7:44:21
	 */
	String updatePosCardByParam(String serialNo);
	
}
