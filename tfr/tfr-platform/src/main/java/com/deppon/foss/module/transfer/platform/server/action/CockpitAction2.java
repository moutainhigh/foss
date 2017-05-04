package com.deppon.foss.module.transfer.platform.server.action;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.ICockpitService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CockpitResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CockpitVo2;
import com.deppon.foss.util.DateUtils;

public class CockpitAction2 extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private CockpitVo2 cockpitVo = new CockpitVo2();

	private IPlatformCommonService platformCommonService;

	private ICockpitService cockpitService;

	public void setPlatformCommonService(IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	public void setCockpitService(ICockpitService cockpitService) {
		this.cockpitService = cockpitService;
	}

	public CockpitVo2 getCockpitVo() {
		return cockpitVo;
	}

	public void setCockpitVo(CockpitVo2 cockpitVo) {
		this.cockpitVo = cockpitVo;
	}

	/**
	 * @desc 入口函数，查询当前部门所属外场
	 * @return
	 * @date 2015年8月11日下午4:22:45
	 */
	public String cockpitIndex() {
		String orgCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity transferCenter = platformCommonService.querySupTfrCtr(orgCode);
		String tfrCtrCode = null;
		String tfrCtrName = null;
		if (transferCenter != null) {
			tfrCtrCode = transferCenter.getCode();
			tfrCtrName = transferCenter.getName();
		}
		cockpitVo.setTfrCtrCode(tfrCtrCode);
		cockpitVo.setTfrCtrName(tfrCtrName);
		return SUCCESS;
	}

	/**
	 * @desc 驾驶舱查询
	 * @return
	 * @date 2015年8月11日下午4:28:09
	 */
	@JSON
	public String findCockpitResult() {
		List<CockpitResultDto> reuslt = new ArrayList<CockpitResultDto>();

		CockpitResultDto info = cockpitService.findCockpitResult(cockpitVo.getTfrCtrCode());
		reuslt.add(info);

		cockpitVo.setReuslt(reuslt);

		cockpitVo.setCurrentTimeStr(DateUtils.convert(info.getCurrentTime(), DateUtils.DATE_TIME_FORMAT));

		return SUCCESS;
	}

}
