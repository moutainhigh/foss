package com.deppon.foss.module.generalquery.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.generalquery.server.dao.IEagleEyeDao;
import com.deppon.foss.module.generalquery.shared.domain.EagleEyeEntity;

public class EagleEyeDao extends SqlSessionDaoSupport implements IEagleEyeDao{

	/**
	 * @Description 查询鹰眼位置开关
	 * @author  268974
	 * @date 2016-01-06
	 */
	@Override
	public EagleEyeEntity queryEagleEyeStatus() {
		return  (EagleEyeEntity) getSqlSession().selectOne(getClass().getName()  + ".queryEagleEyeStatus");

	}
}
