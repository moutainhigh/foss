package com.deppon.foss.module.settlement.consumer.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 查询部门预警信息
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-11 上午9:58:37
 */
public class QueryOrgAlarmAction extends AbstractAction {

	/**
	 * 声明日志对象.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryOrgAlarmAction.class);

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8259208958581289377L;

	/**
	 * 组织信息额度服务
	 */
	private ICreditOrgService creditOrgService;

	/**
	 * 组织信息服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 返回信息
	 */
	private String alarmMessage;

	@JSON
	public String queryOrgAlarm() {

		try {

			// 获取当前登录用户部门编码
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			String orgCode = cInfo.getCurrentDeptCode();

			// 根据编码查询组织信息
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(orgCode);

			// 如果是营业部，则查询预警信息
			if (FossConstants.YES.equals(orgInfo.getSalesDepartment())) {

				// 查询部门预警信息
				alarmMessage = creditOrgService.queryOrgAlarm(orgCode);

			}

		}// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("查询部门信用额度、账期预警信息出错：" + e.getMessage(), e);
			// 返回错误信息

			return returnError(e);
		}

		// 返回信息
		return returnSuccess();
	}

	public void setCreditOrgService(ICreditOrgService creditOrgService) {
		this.creditOrgService = creditOrgService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public String getAlarmMessage() {
		return alarmMessage;
	}
}
