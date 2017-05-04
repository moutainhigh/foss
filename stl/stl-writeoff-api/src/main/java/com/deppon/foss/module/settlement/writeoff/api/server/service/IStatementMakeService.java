/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;

/**
 * 制作对账单服务接口类
 * 
 * @author dp-zhangjiheng
 * @date 2012-10-11 下午5:01:04
 */

public interface IStatementMakeService extends IService {

	/**
	 * 查询对账单明细包含的（应收、应付、预收、预付）单据及对账单信息服务接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-16 下午8:38:10
	 * @param statementOfAccountMakeQueryDto,
	 * 			cInfo
	 * 			制作对账单参数Dto,
	 *  		当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 */
	StatementOfAccountMakeQueryResultDto queryForStatementMake(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto,
			CurrentInfo info);

	/**
	 * 制作对账单时，保存对账单及对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午3:46:35
	 * @param statementOfAccountMakeQueryDto,
	 * 			cInfo
	 * 			制作对账单参数Dto,
	 *  		当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 */
	StatementOfAccountMakeQueryResultDto addStatement(
			StatementOfAccountMakeQueryResultDto soaMakeQueryResultDto,
			CurrentInfo info);

	/**
	 * 在保存对账单和新增对账单明细时,校验明细单据是否发生了变化
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-24 下午5:54:37
	 * @param list,statementNo
	 * 			cInfo
	 * 			对账单明细集合,对账单号
	 *  		当前登录用户
	 * @return List<StatementOfAccountDEntity>
	 */
	List<StatementOfAccountDEntity> validateForAddDetail(
			List<StatementOfAccountDetailDto> list, String statementNo,
			CurrentInfo info);

	/**
	 * 新增对账单明细时,校验对账单明细统计金额和对账单信息的金额是否一致
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-2 下午1:55:49
	 * @param beginPeriodList,soaEntity,periodList
	 * 			cInfo
	 * 			期初对账单明细集合,对账单,本期对账单明细集合
	 *  		当前登录用户
	 * @return boolean
	 * 			true/false
	 */
	void validateTotalAmountForAddDetail(
			List<StatementOfAccountDEntity> beginPeriodList,
			StatementOfAccountEntity soaEntity,
			List<StatementOfAccountDEntity> periodList, CurrentInfo info);

	/**
	 * 新增对账单明细时，修改进入对账单的应收单、应付单、预收单、预付单明细的对账单号
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-1 上午10:04:08
	 * @param receivableEntityList,payableEntityList
	 * 			depositReceivedEntityList,advancedPaymentEntityList
	 * 			statementNo,cInfo
	 * 			应收单集合,应付单集合
	 * 			预收单集合,预付单集合
	 *  		对账单号,当前登录用户
	 * @return void
	 */
	void updateDetailBillNoForAddDetail(
			List<BillReceivableEntity> receivableEntityList,
			List<BillPayableEntity> payableEntityList,
			List<BillDepositReceivedEntity> depositReceivedEntityList,
			List<BillAdvancedPaymentEntity> advancedPaymentEntityList,
			String statementNo,CurrentInfo info);

	/**
	 * 修改对账单，添加对账单明细信息时，查询对账单明细包含的（应收、应付、预收、预付）单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-16 下午8:38:10
	 * @param statementOfAccountMakeQueryDto,
	 * 			cInfo
	 * 			制作对账单参数Dto,
	 *  		当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 */
	StatementOfAccountMakeQueryResultDto queryForAddDetailBill(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto,
			CurrentInfo info);

	/**
	 * 重置与对账单明细相关联的应收单、应付单、预收单、预付单单据信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午3:38:55
	 * @param list,statementNo
	 * 			cInfo
	 * 			对账单明细单号集合,对账单号
	 *  		当前登录用户
	 * @return void
	 */
	void updateDetailBillNoForRelease(List<String> list,String statementBillNo, CurrentInfo info);

	/**
	 * 设置对账单的本期未还金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-28 下午12:59:11
	 * @param entity
	 * 			对账单
	 * @return StatementOfAccountEntity
	 * 			对账单
	 */
	StatementOfAccountEntity updateUnpaidAmount(StatementOfAccountEntity entity);
	
	/**
	 * 查询对账单信息
	 * @author ddw
	 * @date 2014-08-19
	 */
	List<StatementOfAccountEntity> queryStatementBIllEntity(StatementOfAccountMakeQueryDto statementQueryDto);

}
