package com.deppon.pda.bdm.module.foss.packagemanager.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.ThroughPackageOrg;

/** 
  * @ClassName IAcctPdaDao 
  * @Description 知道包  Dao接口 
  * @author cbb 
  * @date 2014-6-3 上午8:38:47 
*/ 
public interface IPackagePdaDao {

	/**
	 * 根据部门编码 查询部门名称，并将部门编码和对应的名称放入Map集合中
	 * @param orgCodes 部门编码
	 * @return
	 */
	public List<ThroughPackageOrg> findThroughPackArriveOrgByOrgCode(
			List<String> orgCodes);
	

}
