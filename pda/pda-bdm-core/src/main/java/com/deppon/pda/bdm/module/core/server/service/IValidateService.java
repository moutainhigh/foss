package com.deppon.pda.bdm.module.core.server.service;

import com.deppon.pda.bdm.module.core.shared.domain.PdaInfo;
import com.deppon.pda.bdm.module.core.shared.domain.PdaLoginInfo;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**
 * 
  * @ClassName IValidateService 
  * @Description 验证接口 
  * @author xujun cometzb@126.com 
  * @date 2012-12-25
 */
public interface IValidateService {
	/**
	 * 校验PDA信息是否合法，用户信息，部门信息是否存在
	 * 
	 * @param pdaInfo
	 */
	public void check(PdaInfo pdaInfo) throws PdaBusiException;

	/**
	 * 校验用户登录账号和密码是否存在或者一致
	 * 
	 * @param pdaLoginInfo
	 *            PdaLoginInfo 登录信息
	 */
	public void checkPwd(PdaLoginInfo pdaLoginInfo) throws PdaBusiException;

	/**
	 * 校验基础数据版本
	 * 
	 * @param dataVer
	 *            String 基础数据版本号
	 */
	public boolean checkDataVer(String dataVer);

	/**
	 * 校验程序版本
	 * 
	 * @param pgmVer
	 *            String 程序版本号
	 */
	public String checkPgmVer(String pgmVer, String pdaModel);
}
