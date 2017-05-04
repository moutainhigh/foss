package com.deppon.foss.module.settlement.writeoff.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementToPaymentResultDto;

/**
 * 修改对账单service接口类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-3 上午9:29:39
 */
public interface IStatementModifyService extends IService {

	/**
	 * 根据日期查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午7:05:23
	 * @param queryDto
	 * 			制作对账单参数Dto,
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 */
	StatementOfAccountMakeQueryResultDto queryStatement(
			StatementOfAccountMakeQueryDto queryDto, int start, int limit);

	/**
	 * 修改对账单界面，新增对账单明细接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 上午9:33:00
	 * @param resultDto,
	 * 			cInfo
	 * 			制作对账单及明细Dto,
	 *  		当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 * 
	 */
	StatementOfAccountMakeQueryResultDto addStatementDetail(
			StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info);

	/**
	 * 修改对账单界面，删除对账单明细接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午10:33:13
	 * @param resultDto,
	 * 			cInfo
	 * 			制作对账单及明细Dto,
	 *  		当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 */
	StatementOfAccountMakeQueryResultDto deleteStatementDetail(
			StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info);

	/**
	 * 修改对账单界面：确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午4:46:59
	 * @param resultDto,
	 * 			cInfo
	 * 			制作对账单及明细Dto,
	 *  		当前登录用户
	 * @return void
	 * 			
	 */
	void confirmStatement(StatementOfAccountMakeQueryResultDto resultDto,
			CurrentInfo info);

	/**
	 * 核销、还款、付款时：确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午4:46:59
	 * @param resultDto,
	 * 			cInfo
	 * 			制作对账单及明细Dto,
	 *  		当前登录用户
	 * @return void
	 */
	void confirmStatementForWriteOff(
			StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info);

	/**
	 * 修改对账单界面：反确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午4:46:59
	 * @param resultDto,
	 * 			cInfo
	 * 			制作对账单及明细Dto,
	 *  		当前登录用户
	 * @return void
	 */
	void unConfirmStatement(StatementOfAccountMakeQueryResultDto resultDto,
			CurrentInfo info);

	/**
	 * 对账单核销操作时，释放对账单明细后修改对账单的本期未还金额信息
	 * 
	 * @author @author 088933-foss-zhangjiheng
	 * @date 2012-11-15 上午11:05:07
	 * @param entity,
	 * 			cInfo
	 * 			对账单,
	 *  		当前登录用户
	 * @return void
	 */
	void releaseSOADUpdateStatement(StatementOfAccountEntity entity,
			CurrentInfo info);

	/**
	 * 根据客户编码查询客户信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 上午11:30:46
	 * @param custCode
	 *  		客户编码
	 * @return CustomerDto
	 * 			客户信息
	 */
	CustomerDto queryCustInfoByCode(String custCode);

	/**
	 * 批量作废还款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 下午8:18:42
	 * @param queryDto,
	 * 			cInfo
	 * 			制作对账单参数Dto,
	 *  		当前登录用户
	 * @return StatementToPaymentResultDto
	 * 			按对账单还款界面返回Dto
	 */
	StatementToPaymentResultDto repaymentBatch(
			StatementOfAccountMakeQueryResultDto queryDto, CurrentInfo info);

	StatementOfAccountMakeQueryResultDto queryStatementXLS(StatementOfAccountMakeQueryDto queryDto);
	//发票已申请
	void appliedStatement(StatementOfAccountMakeQueryResultDto queryDto,
			CurrentInfo currentInfo);
	//发票未申请
	void notApplyStatement(StatementOfAccountMakeQueryResultDto queryDto,
			CurrentInfo currentInfo);

	void updateInstationMsgByIds(StatementOfAccountEntity entity,CurrentInfo info);

	String queryStatementChapter(String companyCode);

}
