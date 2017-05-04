package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonQueryLdpAndExpressAndOrgInfosDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonQueryLdpAndExpressAndOrgInfosService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

public class CommonQueryLdpAndExpressAndOrgInfosService implements
		ICommonQueryLdpAndExpressAndOrgInfosService {

	ICommonQueryLdpAndExpressAndOrgInfosDao infosDao;
	
	public void setInfosDao(ICommonQueryLdpAndExpressAndOrgInfosDao infosDao) {
		this.infosDao = infosDao;
	}

	/**
	 * 
     * 查询自有网点+快递代理网点+虚拟网点
     * @author WangPeng
     * @date 2013-11-05 10:33AM
     * 
     */
	@Override
	public List<LdpOuterBranchsAndOrginfoEntity> queryLdpAndExpressAndOrgInfoList(
			LdpOuterBranchsAndOrginfoEntity entity, int start, int limit) {
		if(null == entity){
			throw new BusinessException("传入的网点对象为空！");
		}
		return infosDao.queryLdpAndExpressAndOrgInfoList(entity, start, limit);
	}

	/**
	 * 
     * 计数
     * @author WangPeng
     * @date 2013-11-05 10:33AM
     * 
     */
	@Override
	public Long recordRowCount(LdpOuterBranchsAndOrginfoEntity entity) {
		return infosDao.recordRowCount(entity);
	}

}
