package com.deppon.foss.module.login.server.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICheckLoginByOAService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.common.api.shared.util.MD5Encrypt;
import com.deppon.foss.module.frameworkimpl.server.interceptor.CookieNonCheckRequired;
import com.deppon.foss.module.login.shared.util.DesUtil;

/**
 * 
 * @author WangPeng-078816
 * @date   2014-03-04
 * @desc   FOSS密码重置
 * 
 */
public class ResetFossPasswordAction extends AbstractAction {

	private static final long serialVersionUID = -4302907726239791349L;
	
	//FOSS用户的service
	private IUserService userService;
	//OA校验账号和密码
	private ICheckLoginByOAService checkLoginByOAService;
	//OA账号
	private String emp_code;
	//OA密码
	private String emp_password;
	//重置密码的工号
	private String userCode;
	//新置的密码
	private String newPassword;
	//密文
	private String token;
	
	

	//获取前台界面的值得接口
	private HttpServletRequest request = ServletActionContext.getRequest();
	
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
	/**
	 * @param businessLockService the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_password() {
		return emp_password;
	}

	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}

	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String oAPasswordAuthentication(){
		return returnSuccess();
	}
	
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String checkFossPasswordByOa() throws Exception{
		try {
			String userCode = request.getParameter("emp_code");
			String passWord = request.getParameter("emp_password");
			if(StringUtils.isBlank(userCode)||StringUtils.isBlank(passWord)){
				userCode = (String)request.getSession().getAttribute("web_foss_login_emp_code");
				passWord = (String)request.getSession().getAttribute("web_foss_login_emp_password");
				if(StringUtils.isBlank(userCode)||StringUtils.isBlank(passWord)){
					throw new BusinessException("对不起，用户或密码不能为空！");
				}
			}
			UserEntity  entity = userService.queryUserByEmpCode(userCode);
			if(null == entity){
				throw new BusinessException("对不起，该用户不是FOSS用户，不能进行密码重置操作！");
			}
			boolean flag = true;
			//记得解开
			flag = checkLoginByOAService.checkLoginByOA(userCode, passWord);
			if(!flag){
				throw new BusinessException("对不起，您输入的OA账号或密码有误，调用OA接口验证不通过，不能进行下一步FOSS密码重置操作！");
			}
			request.setAttribute("userCode", userCode);
			request.getSession().setAttribute("web_foss_login_emp_code", userCode);
			request.getSession().setAttribute("web_foss_login_emp_password", passWord);
			StringBuilder toEncryptStr = null;
			int index = 0;
			String key = null;
				toEncryptStr =new StringBuilder();
				key = DesUtil.initKey();
				//需要加密的字符串(带账号密码)
				toEncryptStr.append(userCode).append("#9k]t!X").append(passWord);
				index = toEncryptStr.toString().indexOf("#9k]t!X");
				//如果下标相等，进一步判断index后面的字符是不是包含key
				if(index==userCode.length()){
					index = toEncryptStr.toString().indexOf(key,index+1);
					//如果匹配不到，说明加密的字符串中只有只能匹配一次key
					if(index==-1){
						flag = false;
					}
				}
			request.setAttribute("token", DesUtil.encryptStr(toEncryptStr.toString(), key)+key);
		} catch (BusinessException e) {
			request.setAttribute("checkxception", e.getMessage());
			return ERROR;
		} 
		return returnSuccess();
	}

	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String resetFossLoginPassword() {
		try {
			String newPassword = request.getParameter("newPassword");
			token = request.getParameter("token");
			//获取最后一个字符所对应的下标，即为加密的key
			if(StringUtils.isBlank(token)||token.length()<=14){
				request.getSession().setAttribute("web_foss_login_message", "对不起，token不能为空或无效，请重新进行身份验证");
				return "redirect";
			}
			String key = token.substring(token.length()-14);
			String oriData = DesUtil.decryptStr(token.substring(0,token.length()-14),key);
			String[] uk = oriData.split("#9k]t!X");
			if(uk == null||uk.length!=2){
				request.getSession().setAttribute("web_foss_login_message", "对不起，token不能为空或无效，请重新进行身份验证");
				return "redirect";
			}
			emp_code = uk[0];
			emp_password = uk[1];
			//再次对用户密码进行校验
		    UserEntity  userEntity = userService.queryUserByEmpCode(emp_code);
		    if(null == userEntity){
			   throw new BusinessException("对不起，该用户不是FOSS用户，不能进行密码重置操作！");
		    }
			boolean flag = true;
			flag =	checkLoginByOAService.checkLoginByOA(emp_code, emp_password); 
			if(!flag){
				request.getSession().setAttribute("web_foss_login_message", "对不起，您输入的OA账号或密码有误，调用OA接口验证不通过，不能进行下一步FOSS密码重置操作！");
				//throw new BusinessException("对不起，您输入的OA账号或密码有误，调用OA接口验证不通过，不能进行下一步FOSS密码重置操作！");
				return "redirect";
			}
			UserEntity entity = new UserEntity();
			if (!StringUtils.isEmpty(emp_code) && !StringUtils.isEmpty(newPassword)) {
				String regex="^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,16}$";
				if (!newPassword.matches(regex)) {
					request.getSession().setAttribute("web_foss_login_message", "密码应包含字母和数字且长度6-16位！");
					throw new BusinessException("密码应包含字母和数字且长度6-16位！");
				}
				entity = userService.queryUserByEmpCode(emp_code);
				String newPsw = MD5Encrypt.encrypt(newPassword);
				if(newPsw.equals(entity.getPassword())){
					request.getSession().setAttribute("web_foss_login_message", "新密码不能与原来密码相同！");
					throw new BusinessException("新密码不能与原来密码相同！");
				}
				entity.setPassword(newPsw);
				entity.setCreateDate(new Date());
				entity.setCreateUser(emp_code);
				entity.setModifyDate(new Date(NumberConstants.ENDTIME));
				// 业务锁
				MutexElement mutex = new MutexElement(entity.getEmpCode(),
						"UUMS_USER_CODE", MutexElementType.UUMS_USER_CODE);
				boolean result = businessLockService.lock(mutex,
						NumberConstants.ZERO);
				//加锁成功
				if (result) {
					userService.updateUser(entity, emp_code);
				}
				//解锁
				businessLockService.unlock(mutex);
				request.getSession().setAttribute("resetSuccuess", "重置密码成功！");
			}

		} catch (BusinessException e) {
			request.setAttribute("checkxception1", e.getMessage());
			return ERROR;
		}
		return returnSuccess();
	}
	public boolean checkPSW(String password) {
		 //返回值flag为true则表示密码全相同字符，返回值false则表示密码非全相同字符
		  boolean flag = true;
		  char str = password.charAt(0);
		  for (int i = 0; i < password.length(); i++) {
		      if (str != password.charAt(i)) {
		          flag = false;
		          break;
		      }
		  }
		  return flag;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setCheckLoginByOAService(ICheckLoginByOAService checkLoginByOAService) {
		this.checkLoginByOAService = checkLoginByOAService;
	}
	
}
