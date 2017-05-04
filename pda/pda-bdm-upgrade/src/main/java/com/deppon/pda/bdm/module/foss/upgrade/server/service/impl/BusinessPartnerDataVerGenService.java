package com.deppon.pda.bdm.module.foss.upgrade.server.service.impl;

import java.io.File;
import java.util.Set;

import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeptEntity;

/**
 * 
 * TODO(偏线基础数据服务类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:chengang,date:2013-3-15 上午11:02:06,content:TODO
 * </p>
 * 
 * @author chengang
 * @date 2013-3-15 上午11:02:06
 * @since
 * @version
 */

public class BusinessPartnerDataVerGenService extends
		AbstractBaseDataVerGenService<DeptEntity> {

	@Override
	public String bulidAllPath(String currVer, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void zipAllBaseDataFiles(String filePath, String buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String bulidIncPath(String dataVer, String currVer, File file,
			String tabName) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String dealLocalDatas(Set<DeptEntity> localDatas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBaseDataClassName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getUserType() {
		return Constant.USER_TYPE.ALL;
	} 
}
