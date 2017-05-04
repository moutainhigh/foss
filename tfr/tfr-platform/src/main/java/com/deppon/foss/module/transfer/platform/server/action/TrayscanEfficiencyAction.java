package com.deppon.foss.module.transfer.platform.server.action;

import java.util.Map;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.ITrayscanEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TrayscanEfficiencyVo;

/**
 * 托盘绑定效率action
 * 
 * @author 200978 2015-2-2
 */
public class TrayscanEfficiencyAction extends AbstractAction {

	private static final long serialVersionUID = 2476584037721712202L;

	private IPlatformCommonService platformCommonService;

	private ITrayscanEfficiencyService trayscanEfficiencyService;

	private TrayscanEfficiencyVo trayscanEfficiencyVo = new TrayscanEfficiencyVo();

	public TrayscanEfficiencyVo getTrayscanEfficiencyVo() {
		return trayscanEfficiencyVo;
	}

	public void setTrayscanEfficiencyVo(
			TrayscanEfficiencyVo trayscanEfficiencyVo) {
		this.trayscanEfficiencyVo = trayscanEfficiencyVo;
	}

	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	public void setTrayscanEfficiencyService(
			ITrayscanEfficiencyService trayscanEfficiencyService) {
		this.trayscanEfficiencyService = trayscanEfficiencyService;
	}

	/**
	 * @desc 入口函数
	 * @return
	 * @date 2015年6月11日 下午4:42:46
	 * @author Ouyang
	 */
	public String trayscanEfficiencyIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		OrgAdministrativeInfoEntity tfrCtr = platformCommonService
				.querySupTfrCtr(currentDeptCode);

		String tfrCtrCode = null;
		String tfrCtrName = null;
		if (tfrCtr != null) {
			tfrCtrCode = tfrCtr.getCode();
			tfrCtrName = tfrCtr.getName();
			trayscanEfficiencyVo.setTfrCtrCode(tfrCtrCode);
			trayscanEfficiencyVo.setTfrCtrName(tfrCtrName);
		} else {
			Map<String, String> hq = platformCommonService
					.findSupHq(currentDeptCode);
			trayscanEfficiencyVo.setHqCode(hq.get("HQ_CODE"));
			trayscanEfficiencyVo.setHqName(hq.get("HQ_NAME"));
		}

		return SUCCESS;
	}

	/**
	 * @desc 托盘绑定查询
	 * @return
	 * @date 2015年6月11日 下午4:42:52
	 * @author Ouyang
	 */
	public String queryTrayscanEfficiency() {
		TrayscanEfficiencyEntity paramter = new TrayscanEfficiencyEntity();
		paramter.setHqCode(trayscanEfficiencyVo.getHqCode());
		paramter.setTfrCtrCode(trayscanEfficiencyVo.getTfrCtrCode());
		paramter.setStaDate(trayscanEfficiencyVo.getStaDate());
		trayscanEfficiencyVo.setEffs(trayscanEfficiencyService
				.findTrayBindingEff(paramter));
		return SUCCESS;
	}

	/**
	 * @desc 明细查询
	 * @return
	 * @date 2015年6月11日 下午4:43:16
	 * @author Ouyang
	 */
	public String queryTrayscanEfficiencyDetail() {
		TrayscanEfficiencyDetailEntity paramter = new TrayscanEfficiencyDetailEntity();
		paramter.setTfrCtrCode(trayscanEfficiencyVo.getTfrCtrCode());
		paramter.setStaDate(trayscanEfficiencyVo.getStaDate());
		trayscanEfficiencyVo.setEffDetails(trayscanEfficiencyService
				.findTrayBindingDetailEff(paramter));
		return SUCCESS;
	}
}
