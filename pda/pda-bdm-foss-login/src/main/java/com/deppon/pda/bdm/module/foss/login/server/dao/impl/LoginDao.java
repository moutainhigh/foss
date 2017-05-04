package com.deppon.pda.bdm.module.foss.login.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PdaLoginInfo;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DepartAssemblyDeptEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DestDeptInfoEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PrivilegeEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.UserInfoEntity;

public class LoginDao extends SqlSessionDaoSupport implements ILoginDao {

	@Override
	public boolean checkUserLogin(PdaLoginInfo pdaLoginInfo) {
		int count = 0;
		count = (Integer) getSqlSession().selectOne(
				getClass().getName() + ".checkUserLogin", pdaLoginInfo);
		return count > 0 ? true : false;
	}

	@Override
	public boolean checkUserPdaLogin(PdaLoginInfo pdaLoginInfo) {
		int count = 0;
		count = (Integer) getSqlSession().selectOne(
				getClass().getName() + ".checkUserPdaLogin", pdaLoginInfo);
		return count > 0 ? true : false;
	}

	@Override
	public void saveLoginInfo(PdaLoginInfo pdaLoginInfo) {
		getSqlSession().insert(getClass().getName() + ".saveLoginInfo",
				pdaLoginInfo);
	}

	@Override
	public int login(PdaLoginInfo pdaLoginInfo) {
		// TODO Auto-generated method stub
		return (Integer) getSqlSession().selectOne(
				getClass().getName() + ".login", pdaLoginInfo);
	}

	@Override
	public List<PrivilegeEntity> getUserPrivilege(String userCode) {
		List<PrivilegeEntity> privilegeEntityList = getSqlSession().selectList(
				getClass().getName() + ".getUserPrivilege", userCode);
		return privilegeEntityList;
	}

	@Override
	public List<UserInfoEntity> getUserInfoByDeptId(String deptId) {
		List<UserInfoEntity> userList = getSqlSession().selectList(
				getClass().getName() + ".getUserInfoByDeptId", deptId);
		return userList;
	}

	@Override
	public List<DestDeptInfoEntity> getLongDestDepts(Map<String, String> map) {
		List<DestDeptInfoEntity> deptList = getSqlSession().selectList(
				getClass().getName() + ".getLongDestDepts", map);
		return deptList;
	}

	@Override
	public List<DestDeptInfoEntity> getShortDestDepts(Map<String, String> map) {
		List<DestDeptInfoEntity> deptList = getSqlSession().selectList(
				getClass().getName() + ".getShortDestDepts", map);
		return deptList;
	}

	@Override
	public List<DestDeptInfoEntity> getSalesShortDestDepts(
			Map<String, String> map) {
		List<DestDeptInfoEntity> deptList = getSqlSession().selectList(
				getClass().getName() + ".getSalesShortDestDepts", map);
		return deptList;
	}

	@Override
	public String getOutStorageCode(String platoonCode) {
		return (String) getSqlSession().selectOne(
				getClass().getName() + ".getOutStorageCode", platoonCode);
	}

	@Override
	public DepartAssemblyDeptEntity getDepartAssemblyDept(String deptId) {
		List list = getSqlSession().selectList(
				getClass().getName() + ".getDepartAssemblyDept", deptId);
		if (list.size() > 0) {
			DepartAssemblyDeptEntity entity = (DepartAssemblyDeptEntity) list
					.get(0);
			return entity;
		} else {
			return null;
		}
	}

	@Override
	public DeptEntity querySourceStaionSalesDept(String transferCode) {
		List<DeptEntity> list = getSqlSession().selectList(
				getClass().getName() + ".querySourceStaionSalesDept",
				transferCode);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryEffRegionOrg(String sourceStaionSalesDept) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryEffRegionOrg",
				sourceStaionSalesDept);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryRegionByCounty(String deptCounty) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryRegionByCounty", deptCounty);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryRegionByCity(String deptCity) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryRegionByCity", deptCity);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryRegionByProvince(String deptProvince) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryRegionByProvince", deptProvince);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryExpEffRegionOrg(String sourceStaionSalesDept) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryExpEffRegionOrg",
				sourceStaionSalesDept);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryExpRegionByCounty(String deptCounty) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryExpRegionByCounty", deptCounty);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryExpRegionByCity(String deptCity) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryExpRegionByCity", deptCity);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String queryExpRegionByProvince(String deptProvince) {
		List<String> list = getSqlSession().selectList(
				getClass().getName() + ".queryExpRegionByProvince", deptProvince);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public DeptEntity queryDeptByCode(String deptCode) {
		DeptEntity dept=(DeptEntity) getSqlSession().selectOne(
				getClass().getName() + ".getEntityByCode", deptCode);
		return dept;
	}

	@Override
	public String queryExpressBranch(String userCode) {
		return (String) getSqlSession().selectOne(
				getClass().getName() + ".getExpressBranch", userCode);
	}

	@Override
	public void updatePdaSimCardCode(PdaLoginInfo pdaLoginInfo) {
		getSqlSession().update(getClass().getName()+".updatePdaSimCardCode", pdaLoginInfo);
	}
   
}
