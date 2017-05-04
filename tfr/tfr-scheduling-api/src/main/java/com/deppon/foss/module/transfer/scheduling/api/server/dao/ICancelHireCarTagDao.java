package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.HashMap;

import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalTempMarkEntity;


/**
 * 配合CUBC传递租车编码，取消临时租车标记
 * @author liping
 * 
 * @date 2017-04-06 下午23:24:06
 */
public interface ICancelHireCarTagDao {

	void doCancelHireCarDao(HashMap<String,Object> map);
	
	/**
	 * author:106162 date:2017-04-18 note:CUBC取消租车标价释放金额.根据部门编码获取标杆标码
	 */
	String  queryUnifyCodeByDeptCode(String deptCode);
	
	/**
	 * author:106162 date:2017-04-18 note:根据租车编码查询租车标记实体
	 */
	RentalTempMarkEntity queryTempMarkEnt(String temprentalMarkNo);
	
	/**
	 * author:106162 date:2017-04-18 note:CUBC取消租车标价释放金额.根据用户工号获取对应组织的标杆标码
	 */
	String queryUnifyCodeByEmpCode(String empCode);
}
