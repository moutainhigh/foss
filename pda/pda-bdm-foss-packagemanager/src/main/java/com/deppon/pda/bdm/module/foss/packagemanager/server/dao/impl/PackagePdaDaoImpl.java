/**
*  2014-10-30
*  2014
   201638
*
*/

package com.deppon.pda.bdm.module.foss.packagemanager.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.packagemanager.server.dao.IPackagePdaDao;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.ThroughPackageOrg;

/**  
 *@author 201638
 *@date   2014-10-30
 */
public class PackagePdaDaoImpl extends SqlSessionDaoSupport implements IPackagePdaDao {
	

	/* (non-Javadoc)
	 * @see com.deppon.pda.bdm.module.foss.packagemanager.server.dao.IPackagePdaDao#findThroughPackArriveOrgsByOrgCode(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ThroughPackageOrg> findThroughPackArriveOrgByOrgCode(
			List<String> orgCodes) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectList(getClass().getName() + ".findThroughPackArriveOrgsByOrgCode", orgCodes);
	}


}
