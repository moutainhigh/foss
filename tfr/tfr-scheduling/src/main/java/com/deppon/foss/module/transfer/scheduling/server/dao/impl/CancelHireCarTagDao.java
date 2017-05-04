package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.HashMap;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ICancelHireCarTagDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalTempMarkEntity;

public class CancelHireCarTagDao extends iBatis3DaoImpl implements ICancelHireCarTagDao {

	/**命名空间常量*/
	private static final String NAMESPACE = "foss.scheduling.temprentalMark.";
	
	@Override
	public void doCancelHireCarDao(HashMap<String, Object> map) {
		System.out.println("---------dao层---------------");
		this.getSqlSession().update(NAMESPACE+"cancelRentalMark", map);
	}
     
	/**
	 * author:106162 date:2017-04-18 note:根据部门code获取标杆标码
	 */
	@Override
	public String queryUnifyCodeByDeptCode(String deptCode) {
		return (String)this.getSqlSession().selectOne(NAMESPACE+"queryUnifyCodeByDeptCode", deptCode);
	}

	/**
	 * author:106162 date:2017-04-18 note:根据租车编码查询租车标记实体信息
	 */
	@Override
	public RentalTempMarkEntity queryTempMarkEnt(String temprentalMarkNo) {
		RentalTempMarkEntity ent = (RentalTempMarkEntity)this.getSqlSession().selectOne(NAMESPACE+"queryTempMarkEntByNo", temprentalMarkNo);
		return ent;
	}

	@Override
	public String queryUnifyCodeByEmpCode(String empCode) {
		return (String)this.getSqlSession().selectOne(NAMESPACE+"queryUnifyCodeByEmpCode", empCode);
	}
}
