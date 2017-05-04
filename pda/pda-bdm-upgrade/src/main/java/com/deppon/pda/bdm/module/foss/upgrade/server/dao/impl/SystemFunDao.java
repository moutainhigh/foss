package com.deppon.pda.bdm.module.foss.upgrade.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.BaseDataVerEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DepartAssemblyDept;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.UserEntity;

/**
 * 
 * 数据版本实现DAO
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-19 下午05:58:24
 */

public class SystemFunDao extends SqlSessionDaoSupport implements ISystemFunDao {

	/**
	 * 
	 * <p>
	 * TODO(查询数据版本号)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-20 下午5:38:07
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao#getDataVerInfo()
	 */
	@Override
	public BaseDataVerEntity getDataVerInfo() {
		// TODO Auto-generated method stub
		return (BaseDataVerEntity) getSqlSession().selectOne(
				getClass().getName() + ".getDataVerInfo");
	}

	/**
	 * 
	 * <p>
	 * TODO(保存数据版本号)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-20 下午5:38:49
	 * @param basEntity
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao#saveBaseDataVer(com.deppon.pda.bdm.module.foss.upgrade.shared.domain.BaseDataVerEntity)
	 */
	@Override
	public void saveBaseDataVer(BaseDataVerEntity basEntity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName() + ".saveBaseDataVer",
				basEntity);
	}

	/**
	 * 
	 * <p>
	 * TODO(修改版本号)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-20 下午5:39:24
	 * @param baseEntity
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao#updDataVer(com.deppon.pda.bdm.module.foss.upgrade.shared.domain.BaseDataVerEntity)
	 */
	@Override
	public void updDataVer(BaseDataVerEntity baseEntity) {
		getSqlSession()
				.update(getClass().getName() + ".updDataVer", baseEntity);
	}

	/**
	 * 
	 * <p>
	 * TODO(查询用户信息)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-20 下午5:39:40
	 * @param userCode
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao#getUserInfo(java.lang.String)
	 */
	@Override
	public UserEntity getUserInfo(String userCode) {

		return (UserEntity) getSqlSession().selectOne(
				getClass().getName() + ".getUserInfo", userCode);
	}

	/**
	 * 
	 * <p>
	 * TODO(查询部门信息)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-20 下午5:39:57
	 * @param deptCode
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao#getDeptCode(java.lang.String)
	 */
	@Override
	public DeptEntity getDeptCode(String deptCode) {
		// TODO Auto-generated method stub
		return (DeptEntity) getSqlSession().selectOne(
				getClass().getName() + ".getDeptCode", deptCode);
	}

	/**
	 * 
	 * @param topFleetCode
	 * @return
	 * @description 通过顶级车队编码查询出对应的开单组Code
	 * @version 1.0
	 * @author wenwuneng
	 * @update 2013-8-10 下午4:05:36
	 */
	@Override
	public String getBillGroupCodeByFleetCode(String topFleetCode) {
		return (String) getSqlSession().selectOne(
				getClass().getName() + ".getBillGroupCodeByFleetCode",
				topFleetCode);
	}

	/**
	 * 
	 * @param salesDeptCode
	 * @return
	 * @description 通过快递员所属部门到始发配载表中查询配载部门
	 * @version 1.0
	 * @author xujun
	 * @update 2013-10-07 下午11:05:36
	 */

	@Override
	public DepartAssemblyDept getDepartAssemblyDept(String salesDeptCode) {
		return (DepartAssemblyDept) getSqlSession().selectOne(
				getClass().getName() + ".getDepartAssemblyDept", salesDeptCode);
	}

	/**
	 * 
	 * <p>
	 * TODO(根据快递员工号查找对应的出发部门)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-10-19 上午9:45:35
	 * @param userCode
	 * @return
	 * @see
	 */
	@Override
	public String getCourierSourceDeptCode(String userCode) {
		@SuppressWarnings("unchecked")
		List<String> l = getSqlSession().selectList(
				getClass().getName() + ".getCourierSourceDeptCode", userCode);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryTransCenter(String topFleetCode) {
		@SuppressWarnings("unchecked")
		List<String> l = getSqlSession().selectList(
				getClass().getName() + ".queryTransCenter", topFleetCode);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<String> getExpressRegionCodeByDeptCode(String deptCode) {
		List<String> regionCodes=new ArrayList<String>();
		List<String> list=getSqlSession().selectList(getClass().getName()+".getExpressRegionCodeByDeptCode",deptCode);
			if(list.size()==0){
				return regionCodes;	
			}
		return list;
	}
	@Override
	public List<String> getRegionCodeByDeptCode(String deptCode) {
		List<String> regionCodes=new ArrayList<String>();
		List<String> list=getSqlSession().selectList(getClass().getName()+".getRegionCodeByDeptCode",deptCode);
			if(list.size()==0){
				return regionCodes;	
			}
		return list;
	}
	@Override
	public DeptEntity getDeptInfoByCode(String deptCode) {
		List<DeptEntity> depts=getSqlSession().selectList(getClass().getName() + ".getDeptInfoByCode", deptCode);
		if(depts.isEmpty()){
			return null;
		}
		return depts.get(0);
	}
	/**
	 * 通过区县编码查找时效区域编码查找 ---------零担
	 */
	@Override
	public String getRegionByCount(String deptCounty) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".getRegionByCount", deptCounty);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	/**
	 * 通过区县编码查找时效区域编码查找 ---------零担
	 */
	@Override
	public String getRegionByCity(String deptCity) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".getRegionByCity", deptCity);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	/**
	 * 通过省编码找时效区域编码查找------------零担
	 */
	@Override
	public String getRegionByProvince(String deptProvince) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".getRegionByProvince", deptProvince);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
  /**
   *  通过外场编码查找驻地营业部
   */
	@Override
	public String queryResidentDeptCode(String sourceTransCenter) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".queryResidentDeptCode", sourceTransCenter);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	/**
	 * 通过区县编码查找时效区域编码查找 ---------快递
	 */
	@Override
	public String getExpressRegionByCount(String deptCounty) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".getExpressRegionByCount", deptCounty);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	/**
	 * 通过城市编码找时效区域编码查找 --快递
	 */
	@Override
	public String getExpressRegionByCity(String deptCity) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".getExpressRegionByCity", deptCity);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	/**
	 * 通过省编码找时效区域编码查找--快递
	 */
	@Override
	public String getExpressRegionByProvince(String deptProvince) {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".getExpressRegionByProvince", deptProvince);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public String queryHttpHost() {
		List<String> list=getSqlSession().selectList(getClass().getName() + ".queryHttpHost");
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
}
