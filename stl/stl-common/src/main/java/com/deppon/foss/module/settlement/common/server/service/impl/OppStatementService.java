package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IOppStatementDao;
import com.deppon.foss.module.settlement.common.api.server.service.IOppStatementService;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.OppStatementDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 302307 on 2016/4/26.
 */
public class OppStatementService implements IOppStatementService {

	private IOppStatementDao oppStatementDao;

	public IOppStatementDao getOppStatementDao() {
		return oppStatementDao;
	}

	public void setOppStatementDao(IOppStatementDao oppStatementDao) {
		this.oppStatementDao = oppStatementDao;
	}
	
	/**
	 * 空运代理
	 */
	private IAirAgencyCompanyDao airAgencyCompanyDao;
	
	
	public IAirAgencyCompanyDao getAirAgencyCompanyDao() {
		return airAgencyCompanyDao;
	}

	public void setAirAgencyCompanyDao(IAirAgencyCompanyDao airAgencyCompanyDao) {
		this.airAgencyCompanyDao = airAgencyCompanyDao;
	}

	/**
	 * 查询OPP对账单信息
	 */
	@Override
	public OppStatementDto queryStatementsByCondition(OppStatementDto dto) {
		OppStatementDto result = new OppStatementDto();
		Date startDate = dto.getStartDate();
		Date endDate = dto.getEndDate();
		String customerCode = dto.getCustomerCode();
		if (startDate == null || endDate == null
				|| StringUtils.isEmpty(customerCode)) {
			throw new SettlementException("传入参数不符合要求！");
		}
		/**
		 * 客户编码存在的数据为空运到达代理,然后FOSS中对账单数据中是空运代理公司,则需要转换
		 */
		String agentCompanyCode = getAirAgentCompanyCode(customerCode);
		/**
		 * 重新赋值到dto中。
		 */
		dto.setCustomerCode(agentCompanyCode);
		
		// 查询对账单信息
		int count = oppStatementDao.queryOppStatementByConditionCount(dto);
		List<StatementOfAccountEntity> statementOfAccountEntityList = null;
		if (count > 0) {
			// 查询对账单数据
			statementOfAccountEntityList = oppStatementDao
					.queryOppStatementByCondition(dto);
		} else {
			throw new SettlementException("没有数据!");
		}
		result.setAccounts(statementOfAccountEntityList);
		result.setCount(count);
		return result;
	}
	
	/**
	 * 查询对账单的明细数据
	 * * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-12 下午5:33:14
	 */
	@Override
	public OppStatementDto queryOppStatementDetailsByNos(OppStatementDto dto) {
		OppStatementDto result = new OppStatementDto();
		List<StatementOfAccountDEntity> statementOfAccountDEntityList = new ArrayList<StatementOfAccountDEntity>();
		//根据对账单号查询对账单明细 
		int count = oppStatementDao.queryOppStatementDetailsByNoCount(dto);
		if (count > 0) {
			statementOfAccountDEntityList = oppStatementDao
					.queryOppStatementDetailsByNo(dto);
		} else {
			throw new SettlementException("没有数据!");
		}
		result.setCount(count);
		result.setAccountsDetail(statementOfAccountDEntityList);
		return result;
	}
	
	/**
	 * 查询对账单的明细数据
	 *  
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-12 下午5:33:14
	 */
	@Override
	public OppStatementDto queryOppStatementDetailsAllByNos(OppStatementDto dto) {
		OppStatementDto result = new OppStatementDto();
		List<StatementOfAccountDEntity> statementOfAccountDEntityList = new ArrayList<StatementOfAccountDEntity>();
		statementOfAccountDEntityList = oppStatementDao
				.queryOppStatementDetailsAllByNo(dto);
		result.setAccountsDetail(statementOfAccountDEntityList);
		return result;
	}
	
	
	@Override
	public OppStatementDto queryStatementByBillNos(OppStatementDto dto) {
		OppStatementDto result = new OppStatementDto();
		List<String> statementBillNos = dto.getStatementBillNos();
		if (statementBillNos.isEmpty()) {
			throw new SettlementException("传入参数不符合要求！");
		}
		// 根据对账单号查询对账单信息
		List<StatementOfAccountEntity> statementOfAccountEntities = oppStatementDao
				.queryOppStatementByNo(dto);
		result.setAccounts(statementOfAccountEntities);
		return result;
	}

	/**
	 * 通过空运代理网点编码，获取空运公司编码
	 * @Title: getAirAgentCompanyCode
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-6-16 下午8:35:06
	 */
	public String getAirAgentCompanyCode(String code) {
		//查询
		BusinessPartnerEntity entity = airAgencyCompanyDao.queryBusinessPartnerByAgencyDeptCode(code);
		if(entity == null){
			throw new SettlementException("空运代理编码："+code+" ,对应代理公司不存在！");
		}
		return entity.getAgentCompanyCode();
	}
	
}
