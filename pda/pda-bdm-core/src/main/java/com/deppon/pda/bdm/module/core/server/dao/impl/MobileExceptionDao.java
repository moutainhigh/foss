package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.UUID;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.server.dao.IMobileExceptionDao;
import com.deppon.pda.bdm.module.core.shared.domain.MobileExceptionBean;
/**
 * 
 * @ClassName MobileExceptionDao  
 * @Description TODO   
 * @author   
 * @date 2014-12-16
 */
public class MobileExceptionDao extends iBatis3DaoImpl implements IMobileExceptionDao {

	@Override
	public void saveMobileException(MobileExceptionBean bean) {
		if(bean!=null){
		    bean.setUuid(UUID.randomUUID().toString());
		    bean.setCreatTime(new Date());
			getSqlSession().insert(getClass().getName()+".saveMobileException", bean);
		}
		
	}
	

}
