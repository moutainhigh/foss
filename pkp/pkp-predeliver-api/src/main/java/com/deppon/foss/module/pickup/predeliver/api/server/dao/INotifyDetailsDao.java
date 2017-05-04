package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsConditonDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsDto;

public interface INotifyDetailsDao{

	long queryNoticeDetailCount(
			NotifyDetailsConditonDto notifyDetailsConditonDto);

	List<NotifyDetailsDto> queryNoticeDetail(
			NotifyDetailsConditonDto notifyDetailsConditonDto, int start,
			int limit);
	
}
