package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryTrayOfflineScanConditionDto;

public class TrayOfflineScanVo implements Serializable{

	private static final long serialVersionUID = 9096676474779004836L;
	/**离线扫描信息 零担*/
	private List<TrayOfflineScanEntity> trayOfflineScanList;
	/**离线扫描信息 快递*/
	private List<TrayOfflineScanExpressEntity> trayOfflineScanListExpress;
	/**查询离线扫描条件dto*/
	private QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto;
	
	public List<TrayOfflineScanEntity> getTrayOfflineScanList() {
		return trayOfflineScanList;
	}
	public void setTrayOfflineScanList(
			List<TrayOfflineScanEntity> trayOfflineScanList) {
		this.trayOfflineScanList = trayOfflineScanList;
	}
	public QueryTrayOfflineScanConditionDto getQueryTrayOfflineScanConditionDto() {
		return queryTrayOfflineScanConditionDto;
	}
	public void setQueryTrayOfflineScanConditionDto(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		this.queryTrayOfflineScanConditionDto = queryTrayOfflineScanConditionDto;
	}
	public List<TrayOfflineScanExpressEntity> getTrayOfflineScanListExpress() {
		return trayOfflineScanListExpress;
	}
	public void setTrayOfflineScanListExpress(
			List<TrayOfflineScanExpressEntity> trayOfflineScanListExpress) {
		this.trayOfflineScanListExpress = trayOfflineScanListExpress;
	}
	
}
