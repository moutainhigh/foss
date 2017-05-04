package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonLdpAgencyDeptAndOwnExpressDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLdpAgencyDeptAndOwnExpressDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

public class CommonLdpAgencyDeptAndOwnExpressDeptService implements
		ICommonLdpAgencyDeptAndOwnExpressDeptService {
	
	//快递代理网点和快递点部Dao层接口注入
	ICommonLdpAgencyDeptAndOwnExpressDeptDao ldpExpressDao;

	public void setLdpExpressDao(
			ICommonLdpAgencyDeptAndOwnExpressDeptDao ldpExpressDao) {
		this.ldpExpressDao = ldpExpressDao;
	}

	/**
     * 根据传入对象查询符合条件所有网点信息 
     * 
     * @author WangPeng
     * @date  2013-08-01 8:53 AM
     * @param entity 快递代理网点和公司快递点部
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
	@Override
	public List<LdpOuterBranchsAndOrginfoEntity> queryLdpAgencyDeptsByCondtion(
			LdpOuterBranchsAndOrginfoEntity entity, int limit, int start) {
		 if(null == entity){
			 entity = new LdpOuterBranchsAndOrginfoEntity();
		 }
		return ldpExpressDao.queryLdpAgencyDeptsByCondtion(entity, limit, start);
	}

	/**
     * 统计总记录数 
     * 
     * @author WangPeng
     * @date   2013-08-01 8:53 AM
     * @param  entity 快递代理网点和公司快递点部
     * @return 符合条件的总记录数
     */
	@Override
	public Long countRecordByCondition(LdpOuterBranchsAndOrginfoEntity entity) {
		if(null == entity){
			 entity = new LdpOuterBranchsAndOrginfoEntity();
		 }
		return ldpExpressDao.countRecordByCondition(entity);
	}

}
