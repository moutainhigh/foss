package com.deppon.foss.module.login.server.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.deppon.dpap.log.log4j.entity.ExpressLogin;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.login.shared.exception.LoginException;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:登录服务接口</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 
* </div>  
********************************************
 */
public interface ILoginService extends IService {
	
	/**
	 * 验证当前用户是否合法
	 * validateUser
	 * @return
	 * @return boolean
	 * @since:
	 */
	public boolean validateUser(String userName, String pwd);

	/**
	 * 用户登录
	 * @param username
	 * @param pwd
	 * @return
	 */
	UserEntity userLogin(String username,String pwd) throws LoginException,UserException;
	
	/**
	 * 用户退出
	 */
	void userLogout();

	/**
	 * 
	 * 切换当前部门
	 * changeCurrentDept
	 * @param currenUserDeptCode 当前部门编码
	 * @return OrgAdministrativeInfoEntity 切换后的部门
	 * @throws LoginException
	 * @since:
	 */
	public OrgAdministrativeInfoEntity changeCurrentDept(String currenUserDeptCode) throws LoginException;

	/**
	 * 通过部门名，查询当前用户所能切换的所有部门
	 * queryCurrentUserManagerDepts
	 * @param deptName
	 * @return
	 * @return List<OrgAdministrativeInfoEntity>
	 * @since:
	 */
	public List<OrgAdministrativeInfoEntity> queryCurrentUserChangeDepts(
			String deptName, int start, int limit);
	
	/**
	 * 通过resource Codes 找到所有的resources
	 */
	List<ResourceEntity>  queryResourceBatchByCode(String[] resCodes);
	
	
	/**
	 * 常用菜单
	 */
	List<UserMenuEntity>  queryUserMenuByUserCode(String empCode);
	/**
	 * 
	 * 根据员工编码和部门编码查询出所有的GUI权限信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-7 上午10:20:49
	 */
	List<ResourceEntity> queryGUIResourceByUserCode(String empCode,String orgCode);

	/**
	 * 
	 * <p>判断MAC在址是否存在</p> 
	 * @author ztjie
	 * @date 2013-4-19 下午4:11:29
	 * @param mac
	 * @return
	 * @see
	 */
	boolean checkMacExist(String mac);

	/**
	 *收银员当前密码到期提醒
	 *@author 187862-dujunhui
	 *@date 2015-10-28 下午2:44:23 
	 */
	int queryLeftDaysOfPsw();

	/**
	 * <p>收银员登录FOSS系统需要短信验证</p> 
	 * @author 187862 
	 * @date 2016-5-19 下午5:26:39
	 * @param roleSet
	 * @return
	 * @see
	 */
	String cashierLoginValidate(String userName,Set<String> roleList);

	/**
	 * <p>发送短信验证码方法</p> 
	 * @author 187862 
	 * @date 2016-5-19 下午5:26:50
	 * @see
	 */
	int sendCashierValidator(String phoneNum,String validator);
    /**
     * 校验验证码是否正确
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-9-5 上午11:15:26
     * @param loginName
     * @param validator
     * @see
     */
	public boolean checkValidator(String loginName, String validator);

	public void attachUserInfoToEl(ExpressLogin el, EmployeeEntity employ,
			HttpServletRequest request);
}
