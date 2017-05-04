package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;

/** 
  * @ClassName KdScanBusinessErrorLogComponent 
  * @Description TODO 
  * @author cbb 
  * @date 2013-11-4 上午11:18:40 
*/ 
@Component
public class KdScanBusinessErrorLogComponent {

	private ILoadDao loadDao;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveKdScanBusinessErrorLog(KdScanBusinessErrorLog kdScanBusinessErrorLog){
		loadDao.saveKdScanBusinessErrorLog(kdScanBusinessErrorLog);
	}
	
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
}
