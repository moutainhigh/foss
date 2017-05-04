package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountQueryResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
/**
 * 对账单公共service接口类
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 下午12:28:28
 */
public interface IStatementOfAccountService extends IService {
	/**
	 * 保存对账单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:41:48
	 */
    int add(StatementOfAccountEntity entity);
    
    /**
     * 根据对账单ID查询
     * @author 088933-foss-zhangjiheng
     * @date 2012-11-7 上午9:09:38
     */
    StatementOfAccountEntity queryByPrimaryKey(String id);
    /**
	 * 根据对账单号查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午9:09:38
	 */
	 StatementOfAccountEntity queryByStatementNo(String statementNo);
    
    /**
     * 根据对账单单号查询对账单是否确认，并返回已确认对账单的单号（传入）
     * @author 088933-foss-zhangjiheng
     * @date 2012-10-26 上午10:26:47
     */
     List<String> queryConfirmStatmentOfAccount(List<String> sourcesStatementNos);
     
     /**
      * 新增对账单明细时修改对账单信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-3 下午4:37:56
      */
     void updateStatementForAddDetail(StatementOfAccountEntity entity,CurrentInfo info);
     
     /**
      * 删除对账单明细时修改对账单信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-3 下午4:37:56
      */
     void updateStatementForDeleteDetail(StatementOfAccountEntity entity,CurrentInfo info);
     
     /**
      * 反确认对账单
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-5 下午5:21:47
      */
     void unConfirmStatement(List<StatementOfAccountEntity> list,CurrentInfo info);
     
     /**
      * 确认对账单
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-5 下午5:21:47
      */
     void confirmStatement(List<StatementOfAccountEntity> list,CurrentInfo info);
     
     /**
      * 核销时，修改对账单及对账单明细信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-7 上午8:44:43
      */
     void updateStatementAndDetailForWriteOff(StatementOfAccountUpdateDto dto,CurrentInfo info);
     
     /**
      * 反核销时，修改对账单及对账单明细信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-7 上午8:44:43
      */
     void updateStatementAndDetailForBackWriteOff(StatementOfAccountUpdateDto dto,CurrentInfo info);
     
     /**
      * 红冲或作废时，修改对账单及对账单明细信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-7 上午8:51:13
      */
     void updateStatementAndDetailForRedBack(StatementOfAccountUpdateDto dto,CurrentInfo info);
     
     /**
      * 根据日期查询对账单信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-9 下午5:43:53
      */
     List<StatementOfAccountEntity> queryStatementByCreateDate(StatementOfAccountUpdateDto updateDto, int start, int limit);
     StatementOfAccountQueryResultDto countStatementByCreateDate(StatementOfAccountUpdateDto updateDto);
     /**
 	 * 根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号、明细单号查询查询对账单使用)
 	 * 
 	 * @author 088933-foss-zhangjiheng
 	 * @date 2013-1-8 下午2:23:39
 	 * @param list
 	 *            对账单号集合
 	 * @param orgCode
 	 *            当前操作网点编码
 	 * @return
 	 */
     List<StatementOfAccountEntity> queryCurrentStatementNos(
 			List<String> statementNos, String orgCode);
     /**
      * 根据对账单号集合查询对账单信息
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-9 下午6:35:48
      */
     List<StatementOfAccountEntity> queryByStatementNos(List<String> statementNos);
     
     /**
      * 根据对账单的本期发生金额自动更新未核销发生金额
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-13 下午12:37:38
      */
     StatementOfAccountEntity updatePeriodUnverifyAmountCommon(StatementOfAccountEntity entity);
     
     /**
      * 对账单核销时修改对账单的本期未还金额
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-15 上午11:53:57
      */
     void updateStatementForWriteOff(StatementOfAccountEntity entity,CurrentInfo info);
     
	/**
	 * 
	 * 按明细反核销时，更新对账单金额，并释放应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Apr 2, 2013 10:15:52 AM
	 */
	public void updateStatementForReverseBillWriteoff(
			BillWriteoffEntity writeoffEntity,
			StatementOfAccountEntity soaEntity, CurrentInfo info);
     
     /**
      * 根据对账单号和版本号查询
      * @author 088933-foss-zhangjiheng
      * @date 2012-11-7 上午9:09:38
      */
    StatementOfAccountEntity queryByStatementNoAndVersion(String statement,short versionNo);
     
    /**
     * 坏账申请之后，修改对账单已确认的对账单
     * @author 095793-foss-LiQin
     * @date 2013-7-26 上午9:16:02
     * @param dto
     * @param info
     */
    void updateBadAccountStatementAndDetailForWriteOff(StatementOfAccountUpdateDto dto,CurrentInfo info);
     
}
