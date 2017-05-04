package com.deppon.foss.module.transfer.common.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.server.dao.ISerialNumberRuleDao;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.shared.define.BillNumContants;
import com.deppon.foss.module.transfer.common.api.shared.domain.BillNoRuleEntity;

public class BillNumService extends BillNumContants implements IBillNumService {

	private ISerialNumberRuleDao serialNumberRuleDao;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setSerialNumberRuleDao(ISerialNumberRuleDao serialNumberRuleDao) {
		this.serialNumberRuleDao = serialNumberRuleDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	private String generateBillNoBySequence(BillNoRuleEntity rules) {
		String result = "";

		if (rules.isNeedPrefix()) {
			result += rules.getPrefix();
		}

		if (rules.isNeedTime()) {
			Date currentTime = new Date();
			String timeStr = new SimpleDateFormat(rules.getTimePattern()).format(currentTime);
			result += timeStr;
		}

		long seqValue = serialNumberRuleDao.getNextSequenceValue(rules.getSequenceName());
		String seqStr = String.format("%0" + rules.getNumLength() + "d", seqValue);
		result += seqStr;
		return result;
	}

	/**
	 * @desc 判断给定部门编码是否外场或外场子部门
	 * @param orgCode
	 * @return
	 * @date 2016年3月17日 下午5:19:25
	 */
	private boolean checkOrgIsTfrCtr(String orgCode) {

		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode,
				bizTypes);

		return org == null ? false : true;
	}

	/**
	 * @desc 生成交接单号，对应之前TfrSerialNumberRuleEnum中的JJD
	 * @return
	 * @date 2016年3月17日 下午12:03:43
	 */
	@Override
	public String generateHandoverbillNo() {
		BillNoRuleEntity rules = new BillNoRuleEntity();
		rules.setNumLength(LENGTH_HANDOVERBILL);
		rules.setSequenceName(SEQ_HANDOVERBILL);
		return generateBillNoBySequence(rules);
	}

	/**
	 * @desc 生成装车任务号，对应之前TfrSerialNumberRuleEnum中的ZC
	 * @return
	 * @date 2016年3月17日 下午12:03:53
	 */
	@Override
	public String generateLoadTaskNo(String orgCode) {
		boolean isTfrCtr = checkOrgIsTfrCtr(orgCode);
		String prefix = PREFIX_LOAD_TASK_NO_TFR;
		if (isTfrCtr) {
			prefix = PREFIX_LOAD_TASK_TFR;
		}

		BillNoRuleEntity rules = new BillNoRuleEntity();
		rules.setNumLength(LENGTH_LOAD_TASK);
		rules.setNeedPrefix(true);
		rules.setPrefix(prefix);
		rules.setNeedTime(true);
		rules.setTimePattern(EIGHT_DIGITS_PATTERN);
		rules.setSequenceName(SEQ_LOAD_TASK);

		return generateBillNoBySequence(rules);
	}
	/**
	 * @desc 生成派送任务号，对应之前TfrSerialNumberRuleEnum中的PSZC
	 * @return
	 * @date 2016年8月22日 上午10:25:33
	 */
	@Override
	public String generateDeliverTaskNo(String orgCode) {
		
		BillNoRuleEntity rules = new BillNoRuleEntity();
		rules.setNumLength(LENGTH_LOAD_TASK);
		rules.setNeedPrefix(true);
		rules.setPrefix(PREFIX_LOAD_TASK_SEVEN);
		rules.setNeedTime(true);
		rules.setTimePattern(EIGHT_DIGITS_PATTERN);
		rules.setSequenceName(SEQ_LOAD_TASK);

		return generateBillNoBySequence(rules);
	}

	/**
	 * @desc 生成卸车任务号，对应之前TfrSerialNumberRuleEnum中的XC
	 * @return
	 * @date 2016年3月17日 下午12:03:53
	 */
	@Override
	public String generateUnLoadTaskNo(String orgCode) {
		boolean isTfrCtr = checkOrgIsTfrCtr(orgCode);
		String prefix = PREFIX_UNLOAD_TASK_NO_TFR;
		if (isTfrCtr) {
			prefix = PREFIX_UNLOAD_TASK_TFR;
		}

		BillNoRuleEntity rules = new BillNoRuleEntity();
		rules.setNumLength(LENGTH_UNLOAD_TASK);
		rules.setNeedPrefix(true);
		rules.setPrefix(prefix);
		rules.setNeedTime(true);
		rules.setTimePattern(EIGHT_DIGITS_PATTERN);
		rules.setSequenceName(SEQ_UNLOAD_TASK);

		return generateBillNoBySequence(rules);
	}

	/**
	 * @desc 生成清仓任务号，对应之前TfrSerialNumberRuleEnum中的QC
	 * @param orgCode
	 * @return
	 * @date 2016年3月17日 下午5:14:14
	 */
	@Override
	public String generateStTaskNo(String orgCode) {
		boolean isTfrCtr = checkOrgIsTfrCtr(orgCode);
		String prefix = PREFIX_ST_TASK_NO_TFR;
		if (isTfrCtr) {
			prefix = PREFIX_ST_TASK_TFR;
		}

		BillNoRuleEntity rules = new BillNoRuleEntity();
		rules.setNumLength(LENGTH_ST_TASK);
		rules.setNeedPrefix(true);
		rules.setPrefix(prefix);
		rules.setNeedTime(true);
		rules.setTimePattern(EIGHT_DIGITS_PATTERN);
		rules.setSequenceName(SEQ_ST_TASK);

		return generateBillNoBySequence(rules);
	}

}
