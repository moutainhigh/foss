package com.deppon.pda.bdm.module.foss.login.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginPdaDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginDeviceEntity;

/**
 * 
 * <p>TODO(PDA设备登陆)</p> 
 * @author wenwuneng
 * @date 2013-7-31下午7:46:29
 * @param pdaLoginDeviceEntity 
 * @see com.deppon.pda.bdm.module.foss.login.server.dao.impl.LoginPdaDao
 */

/** 
  * @ClassName LoginPdaDao 
  * @Description 修改 
  * @author 092038 
  * @date 2014-1-10 上午11:09:37 
*/ 
public class LoginPdaDao extends  iBatis3DaoImpl implements ILoginPdaDao{
	
	@Override
	public boolean checkPdaLoginInfoData(PdaLoginDeviceEntity entity) {
		Long totalCount=(Long)getSqlSession().selectOne(getClass().getName()+".checkPdaLoginInfo",entity);
		return totalCount>=1?false:true;
	}
	@Override
	public int updPdaLoginInfoData(PdaLoginDeviceEntity entity) {
		int size=getSqlSession().update(getClass().getName()+".updPdaLoginInfo",entity);
		return size;
	}
	@Override
	public int savePdaLoginInfoData(PdaLoginDeviceEntity entity) {
		int size=getSqlSession().insert(getClass().getName()+".savePdaLoginInfo",entity);
		return size;
	}
	@Override
	public boolean chekLastLoginOutDate(PdaLoginDeviceEntity entity) {
		boolean isOk=true;
		Long size=(Long) getSqlSession().selectOne(getClass().getName()+".chekLastLoginOutDate", entity);
		if(size>0){
			isOk=false;
		}
		return isOk;
	}
	@Override
	public void updPdaLoginOutDate(PdaLoginDeviceEntity outDateEntity) {
		getSqlSession().update(getClass().getName()+".updPdaLoginOutDate",outDateEntity);
	}

}
