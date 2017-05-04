package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.VehicleEntity;

/**
 * 
  * @ClassName VehicleDao 
  * @Description TODO 快递部门dao
  * @author mt hyssmt@vip.qq.com
  * @date 2013-9-7 下午3:33:33
 */
public class VehicleDao extends SqlSessionDaoSupport implements IBaseDao<VehicleEntity>{
	@Override
	public VehicleEntity getEntityById(String empCode) {
		return (VehicleEntity)getSqlSession().selectOne(VehicleDao.class.getName()+".getEntityById",empCode);
	}

	@Override
	public Date getLastModifyTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleEntity> getEntitiesByLastModifyTime(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleEntity> getEntityByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}
}
