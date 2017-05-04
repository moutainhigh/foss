package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;

public interface ICreateForkliftWorkService {
	public void createForkliftWork(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount);
	public int calculateForkliftCounts(PDATrayScanTaskEntity trayScanTask);
}
