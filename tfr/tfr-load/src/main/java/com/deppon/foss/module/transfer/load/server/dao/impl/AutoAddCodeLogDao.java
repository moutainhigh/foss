package com.deppon.foss.module.transfer.load.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeLogDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeLogEntity;

/**
 * @title: AddCodeAutoStatusDaoImpl 
 * @description: 自动补码状态dao层 数据接口实现.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 10:56:44
 */
public class AutoAddCodeLogDao extends iBatis3DaoImpl implements IAutoAddCodeLogDao {
	
	private static final String NAME_SPACE = "foss.tfr.AutoAddCodeLogDao.Log";
	
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 10:56:44
	* @param entity void
	*/
	@Override
	public void insert(AutoAddCodeLogEntity entity) {
		if(entity!=null){
			entity.setWaybillNum(entity.getWaybillNo());
			getSqlSession().insert(NAME_SPACE+".insertLog", entity);
		}
	}
}