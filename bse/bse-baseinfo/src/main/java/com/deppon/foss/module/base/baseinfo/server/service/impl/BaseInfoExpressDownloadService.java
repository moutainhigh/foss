/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoExpressDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class BaseInfoExpressDownloadService implements IBaseInfoExpressDownloadService {
	/**
	 * 
	 */
	private static final int BEFOREAMOUNT = 200;
	
	/**
	 * 
	 */
	private static final int THOUSAND = 1000;


	private IExpressCityDao expressCityDao;
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoExpressDownloadService#downloadExpressCityData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadExpressCityData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		ExpressCityEntity entity = new ExpressCityEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		
		List<ExpressCityEntity> entitys = null;
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			entitys =expressCityDao
					.queryExpressCityForDownloadByPage(entity, 
					(clientInfo.getSyncPage())*THOUSAND-(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);
		}else{
			entitys =expressCityDao
					.queryExpressCityForDownload(entity);
		}


		return db.setObject(entitys);
	}

	public IExpressCityDao getExpressCityDao() {
		return expressCityDao;
	}

	public void setExpressCityDao(IExpressCityDao expressCityDao) {
		this.expressCityDao = expressCityDao;
	}

}
