package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;

public interface ICheckLoginByOAService extends IService {

	public boolean checkLoginByOA(String empCode,String passWord);
}
