package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;

/**
 * 
 * @ClassName KdScanBusinessErrorLogComponent.java 
 * @Description 
 * @author 245955
 * @date 2015-4-18
 */
@Component
public class ScanBusinessErrorLogComponent {
	
	private ILoadDao loadDao;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveKdScanBusinessErrorLog(KdScanBusinessErrorLog scanBusinessErrorLog){
		loadDao.saveKdScanBusinessErrorLog(scanBusinessErrorLog);
	}
	
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
}
