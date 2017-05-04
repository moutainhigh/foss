package com.deppon.pda.bdm.module.foss.load.server.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadPdaDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.HandoverDept;

/**
 * 装车模块接口实现
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class LoadPdaDao extends SqlSessionDaoSupport  implements ILoadPdaDao{
	
	@Override
	public String queryExpressBranch(String userCode) {
		return  (String) getSqlSession().selectOne(
				getClass().getName() + ".getLoadExpressBranch", userCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverDept> getHandoverDepts(String userCode) {
		return getSqlSession().selectList(getClass().getName() + ".getHandoverDepts", userCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverDept> getHandoverDeptsByCityCode(String cityCode) {
		return getSqlSession().selectList(getClass().getName() + ".getHandoverDeptsByCityCode", cityCode);
	}


	/**
	    * 判断登陆员工与交接员工是否是同一部门
	    * @description 
	    * @param userCode
	    * @param TallyerCode
	    * @return
	    * @author 245955
	    * @date 2015-4-27
	    */
		@Override
		public int queryDiffQueryDept(String userCode, String tallyerCode) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userCode", userCode);
			param.put("tallyerCode", tallyerCode);	
			Object value=getSqlSession().selectOne(getClass().getName() +".queryDiffQueryDept",param);
			return Integer.parseInt(String.valueOf(value));
		}
}
