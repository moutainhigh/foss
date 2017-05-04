package com.deppon.pda.bdm.module.foss.load.server.dao;


import java.util.List;

import com.deppon.pda.bdm.module.foss.load.shared.domain.HandoverDept;

/**
 * 装车模块接口
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public interface ILoadPdaDao {

	/**   
	 * @Title: queryExpressBranch  
	 * @Description: 根据工号查询对应营业映射的点部编码
	 * @param @param userCode
	 * @param @return    设定文件   
	 * @return String    返回类型  
	 * @throws  
	 */
	public String queryExpressBranch(String userCode);

	public List<HandoverDept> getHandoverDepts(String userCode);

	public List<HandoverDept> getHandoverDeptsByCityCode(String cityCode);
	
	/**
	 * 判断登陆员工与交接员工是否是同一部门
	 * @description 
	 * @param userCode
	 * @param TallyerCode
	 * @return
	 * @author 245955
	 * @date 2015-4-27
	 */
	public int queryDiffQueryDept(String userCode,String tallyerCode);
}
