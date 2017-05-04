/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailDto;

/**
 * 制作对账单时，返回对账单及对账单明细DTO类
 * 
 * @author dp-zhangjiheng
 * @date 2012-10-11 下午6:24:38
 */
public class StatementOfAccountMakeQueryResultDto implements Serializable {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = -8196100711549354750L;

	/**
	 * 对账单实体集合
	 */
	private StatementOfAccountEntity statementOfAccount;

	/**
	 * 对账单实体列表
	 */
	private List<StatementOfAccountEntity> statementOfAccountList = new ArrayList<StatementOfAccountEntity>();

	/**
	 * 对账单本期明细实体集合
	 */
	private List<StatementOfAccountDEntity> statementOfAccountDPeriodList = new ArrayList<StatementOfAccountDEntity>();
	
	/**
	 * 对账单本期明细实体集合（提交数据使用）
	 */
	private List<StatementOfAccountDetailDto> statementOfAccountDetailPeriodList = new ArrayList<StatementOfAccountDetailDto>();

	/**
	 * 对账单期初明细实体集合
	 */
	private List<StatementOfAccountDEntity> statementOfAccountDBeginPeriodList = new ArrayList<StatementOfAccountDEntity>();

	/**
	 * 对账单明细操作集合
	 */
	private List<StatementOfAccountDEntity> statementOfAccountOperateList = new ArrayList<StatementOfAccountDEntity>();

	/**
	 * 当前登录用户
	 */
	private UserEntity user;

	/**
	 * 未完全核销的对账单明细列表，包括应付单和预收单
	 */
	private List<StatementOfAccountDEntity> statementOfAccountDUnverifyList = new ArrayList<StatementOfAccountDEntity>();

	/**
	 * 已还金额
	 */
	private BigDecimal paidAmount;

	/**
	 * 当前登录用户编码
	 */
	private String orgCode;

	/**
	 * 还款跳转来源标志
	 */
	private String repayType;
	
	/**
	 * 对账单列表本次剩余未还金额汇总
	 */
	private BigDecimal totalUnpaidAmount;
	
	/**
	 *是否发邮件标志 
	 */
	private String sendMailFlag;
	
	/**
	 *银行账号信息异常 
	 */
	private String accountException;
	
	private long totalCount;
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return 
		statementOfAccount
	 */
	public StatementOfAccountEntity getStatementOfAccount() {
		return statementOfAccount;
	}

	
	/**
	 * @param 
		statementOfAccount
	 */
	public void setStatementOfAccount(StatementOfAccountEntity statementOfAccount) {
		this.statementOfAccount = statementOfAccount;
	}

	
	/**
	 * @return 
		statementOfAccountList
	 */
	public List<StatementOfAccountEntity> getStatementOfAccountList() {
		return statementOfAccountList;
	}

	
	/**
	 * @param 
		statementOfAccountList
	 */
	public void setStatementOfAccountList(List<StatementOfAccountEntity> statementOfAccountList) {
		this.statementOfAccountList = statementOfAccountList;
	}

	
	/**
	 * @return 
		statementOfAccountDPeriodList
	 */
	public List<StatementOfAccountDEntity> getStatementOfAccountDPeriodList() {
		return statementOfAccountDPeriodList;
	}

	
	/**
	 * @param 
		statementOfAccountDPeriodList
	 */
	public void setStatementOfAccountDPeriodList(List<StatementOfAccountDEntity> statementOfAccountDPeriodList) {
		this.statementOfAccountDPeriodList = statementOfAccountDPeriodList;
	}

	
	/**
	 * @return 
		statementOfAccountDBeginPeriodList
	 */
	public List<StatementOfAccountDEntity> getStatementOfAccountDBeginPeriodList() {
		return statementOfAccountDBeginPeriodList;
	}

	
	/**
	 * @param 
		statementOfAccountDBeginPeriodList
	 */
	public void setStatementOfAccountDBeginPeriodList(List<StatementOfAccountDEntity> statementOfAccountDBeginPeriodList) {
		this.statementOfAccountDBeginPeriodList = statementOfAccountDBeginPeriodList;
	}

	
	/**
	 * @return 
		statementOfAccountOperateList
	 */
	public List<StatementOfAccountDEntity> getStatementOfAccountOperateList() {
		return statementOfAccountOperateList;
	}

	
	/**
	 * @param 
		statementOfAccountOperateList
	 */
	public void setStatementOfAccountOperateList(List<StatementOfAccountDEntity> statementOfAccountOperateList) {
		this.statementOfAccountOperateList = statementOfAccountOperateList;
	}

	
	/**
	 * @return 
		user
	 */
	public UserEntity getUser() {
		return user;
	}

	
	/**
	 * @param 
		user
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

	
	/**
	 * @return 
		statementOfAccountDUnverifyList
	 */
	public List<StatementOfAccountDEntity> getStatementOfAccountDUnverifyList() {
		return statementOfAccountDUnverifyList;
	}

	
	/**
	 * @param 
		statementOfAccountDUnverifyList
	 */
	public void setStatementOfAccountDUnverifyList(List<StatementOfAccountDEntity> statementOfAccountDUnverifyList) {
		this.statementOfAccountDUnverifyList = statementOfAccountDUnverifyList;
	}

	
	/**
	 * @return 
		paidAmount
	 */
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	
	/**
	 * @param 
		paidAmount
	 */
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	
	/**
	 * @return 
		orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	 * @param 
		orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	 * @return 
		repayType
	 */
	public String getRepayType() {
		return repayType;
	}

	
	/**
	 * @param 
		repayType
	 */
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	
	/**
	 * @return 
		totalUnpaidAmount
	 */
	public BigDecimal getTotalUnpaidAmount() {
		return totalUnpaidAmount;
	}

	
	/**
	 * @param 
		totalUnpaidAmount
	 */
	public void setTotalUnpaidAmount(BigDecimal totalUnpaidAmount) {
		this.totalUnpaidAmount = totalUnpaidAmount;
	}

	
	/**
	 * @return 
		sendMailFlag
	 */
	public String getSendMailFlag() {
		return sendMailFlag;
	}

	
	/**
	 * @param 
		sendMailFlag
	 */
	public void setSendMailFlag(String sendMailFlag) {
		this.sendMailFlag = sendMailFlag;
	}


	/**
	 * @GET
	 * @return accountException
	 */
	public String getAccountException() {
		/*
		 *@get
		 *@ return accountException
		 */
		return accountException;
	}


	/**
	 * @SET
	 * @param accountException
	 */
	public void setAccountException(String accountException) {
		/*
		 *@set
		 *@this.accountException = accountException
		 */
		this.accountException = accountException;
	}

	/**
	 * @GET
	 * @return statementOfAccountDetailPeriodList
	 */
	public List<StatementOfAccountDetailDto> getStatementOfAccountDetailPeriodList() {
		return statementOfAccountDetailPeriodList;
	}


	/**
	 * @SET
	 * @param statementOfAccountDetailPeriodList
	 */
	public void setStatementOfAccountDetailPeriodList(
			List<StatementOfAccountDetailDto> statementOfAccountDetailPeriodList) {
		this.statementOfAccountDetailPeriodList = statementOfAccountDetailPeriodList;
	}

}
