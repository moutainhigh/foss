package com.deppon.foss.module.transfer.unload.server.action;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.unload.api.server.service.IManualInputService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputForkVoteEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputGoodsQtyEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ManualInputQcDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ManualInputVo;

public class ManualInputAction extends AbstractAction {

	private static final long serialVersionUID = 8102419065280466177L;

	private IManualInputService manualInputService;

	private ManualInputVo manualInputVo = new ManualInputVo();

	public ManualInputVo getManualInputVo() {
		return manualInputVo;
	}

	public void setManualInputVo(ManualInputVo manualInputVo) {
		this.manualInputVo = manualInputVo;
	}

	public void setManualInputService(IManualInputService manualInputService) {
		this.manualInputService = manualInputService;
	}

	public String manualInputDetailIndex() {

		String transferCenterCode = null;
		String transferCenterName = null;

		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		Map<String, String> transferCenter = manualInputService
				.queryParentTfrCtrCode(currentDeptCode);

		if (transferCenter != null) {
			transferCenterCode = transferCenter.get("code");
			transferCenterName = transferCenter.get("name");
		}
		manualInputVo.setParentTfrCtrCode(transferCenterCode);
		manualInputVo.setParentTfrCtrName(transferCenterName);

		return SUCCESS;
	}

	public String queryForkVoteEntities() {
		ManualInputQcDto dto = manualInputVo.getManualInputQcDto();
		List<ManualInputForkVoteEntity> forkVoteEntities = manualInputService
				.queryForkVoteEntities(dto);
		manualInputVo.setManualInputForkVoteEntities(forkVoteEntities);
		return SUCCESS;
	}

	public String queryGoodsQtyEntities() {
		ManualInputQcDto dto = manualInputVo.getManualInputQcDto();
		List<ManualInputGoodsQtyEntity> goodsQtyEntities = manualInputService
				.queryGoodsQtyEntities(dto);
		manualInputVo.setManualInputGoodsQtyEntities(goodsQtyEntities);
		return SUCCESS;
	}

	public String insertForkVoteEntity() {
		ManualInputForkVoteEntity entity = manualInputVo
				.getManualInputForkVoteEntity();
		manualInputService.insertForkVoteEntity(entity);
		return SUCCESS;
	}

	public String insertGoodsQtyEntity() {
		ManualInputGoodsQtyEntity entity = manualInputVo
				.getManualInputGoodsQtyEntity();
		manualInputService.insertGoodsQtyEntity(entity);
		return SUCCESS;
	}

}
