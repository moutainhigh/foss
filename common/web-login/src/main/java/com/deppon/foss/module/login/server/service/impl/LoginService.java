package com.deppon.foss.module.login.server.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.dpap.log.log4j.entity.ExpressLogin;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMacWhiteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserMenuService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.define.MD5Encrypt;
import com.deppon.foss.module.base.baseinfo.api.shared.define.SmsInfo;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.foss.module.login.server.service.ILoginService;
import com.deppon.foss.module.login.shared.exception.LoginException;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

public class LoginService implements ILoginService {

	private IUserService userService;

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	private IResourceService resourceService;

	private IUserMenuService userMenuService;
	
	private LoginValidatorCache loginValidatorCache;

	public void setLoginValidatorCache(LoginValidatorCache loginValidatorCache) {
		this.loginValidatorCache = loginValidatorCache;
	}

	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LoginService.class);
    
	public void setUserMenuService(IUserMenuService userMenuService) {
		this.userMenuService = userMenuService;
	}
	
	/**
     * MAC地址白名单Service接口.
     */
    private IMacWhiteService macWhiteService;

    /**
     * 设置 mAC地址白名单Service接口.
     *
     * @param macWhiteService the macWhiteService to set
     */
    public void setMacWhiteService(IMacWhiteService macWhiteService) {
        this.macWhiteService = macWhiteService;
    }
    /**
	 * 配置参数的Service
	 */
	private IConfigurationParamsService configurationParamsService;
	
    public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

    /**
     * 职员信息
     */
    private IEmployeeService employeeService;
    
    public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 短信验证码发送地址
	 */
	@Value(value="${sms.wsUrl}")
    private String syncSmsUrl;
	
	/**
	 * 短信验证接口用户名
	 */
	@Value(value="${sms.username}")
	private String smsUsername;
	
	/**
	 * 短信验证接口密码
	 */
	@Value(value="${sms.password}")
	private String smsPassword;
	
	/**
	 * 验证码是否需要
	 */
	private String isContainCashierRole;
	
	public String getIsContainCashierRole() {
		return isContainCashierRole;
	}

	public void setIsContainCashierRole(String isContainCashierRole) {
		this.isContainCashierRole = isContainCashierRole;
	}
	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	private static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public void attachUserInfoToEl(ExpressLogin el,EmployeeEntity employ, HttpServletRequest request){
 		el.setStation_id(employ.getOrgCode());
		el.setOperation_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
          OrgAdministrativeInfoEntity  org = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCode(employ.getOrgCode());
          orgAdministrativeInfoService.attachDistrictName(org);
          el.setStation(org.getName());
		  el.setProvince(org.getProvName());
		  el.setCity(org.getCityName());
		  el.setUser_id(employ.getEmpCode());
		  el.setUser(employ.getEmpCode());
		  el.setSource_type("web");
		  el.setSource_application("FOSS");
		  String private_ip =getIpAddress(request); 
		  //el.setSource_public_ip(public_ip); 
		 // el.setSource_private_ip(private_ip);
		  el.setSource_vpn_ip(private_ip);
		  el.setSource_mac(null);
		  el.setTarget_application("FOSS");
		  el.setTarget_public_ip("180.153.24.74");//直接取ping foss.deppon.com的ip地址
		  String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 el.setTarget_private_ip(ip);
		 el.setFeature(null);
		 
	}
	/**
     * 
     * <p>判断MAC在址是否存在</p> 
     * @author ztjie
     * @date 2013-4-19 下午4:11:53
     * @param mac
     * @return 
     * @see com.deppon.foss.module.login.server.service.ILoginService#checkMacExist(java.lang.String)
     */
    @Override
    public boolean checkMacExist(String mac) {
    	if(mac==null || "".equals(mac.trim())){
    		return false;
    	}
		return macWhiteService.queryMacAddressByMac(mac);
	}

	/**
	 * 验证当前用户是否合法 validateUser
	 * 
	 * @return
	 * @return boolean
	 * @since:
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean validateUser(String userName, String pwd) {
		try {
			UserEntity user = validate(userName, pwd);
			if (user != null) {
				return true;
			} else {
				return false;
			}
		} catch (LoginException e) {
			return false;
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	@Override
	@Transactional
	public UserEntity userLogin(String userName, String pwd)
			throws LoginException {
		// 验证用户
		UserEntity user = validate(userName, pwd);
		// 把登录用户ID、工号与默认部门编码存入session中
		// 存入用户ID
		SessionContext.setCurrentUser(user.getId());
		// 存入用户工号
		SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE",
				user.getUserName());
		// 存入默认部门编码
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",
				user.getEmployee().getDepartment().getCode());
		// 存入默认部门名称
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
				user.getEmployee().getDepartment().getName());
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public void userLogout() {
		SessionContext.invalidateSession();
	}

	private UserEntity validate(String userName, String pwd)
			throws LoginException, UserException {
		if (StringUtils.isBlank(userName)) {
			throw new LoginException(LoginException.USER_NAME_NULL);
		}
		if (StringUtils.isBlank(pwd)) {
			throw new LoginException(LoginException.USER_PASSWORD_NULL);
		}
		// 应用OA的加密方式
		pwd = CryptoUtil.digestByMD5(pwd);
		UserEntity user = userService.findByLoginName(userName);
		if (user == null) {
			throw new LoginException(LoginException.USER_NULL);
		}
		// 如果用户已经被禁用，则不能登录
		if (!FossConstants.ACTIVE.equals(user.getActive())) {
			throw new LoginException(LoginException.USER_DISABLE);
		}
		if (!pwd.equals(user.getPassword())) {
			throw new LoginException(LoginException.USER_PASSWORD_ERROR);
		}
		// 通过用户ID得到用户的完整信息
		user = userService.findByUserId(user.getId());
		//如果用户为空，则用户没有配置角色
		if(user==null){
			throw new LoginException(LoginException.CURRENT_USER_NO_ROLE);
		}
		//判断职员信息
		if(StringUtil.equals(user.getEmployee().getTitle(), "05010001")//是收银员05010001和04010003，判断密码是否过期
				||StringUtil.equals(user.getEmployee().getTitle(), "04010003")){
			//判断当前用户密码使用时间是否已超三个月（90天）
			int leftDays=userService.queryLeftDaysOfPsw(userName,pwd);
			if(leftDays<0){//过期则不让登陆
				throw new LoginException(LoginException.CASHIER_PSW_OVERDUE);
			}
		}
		return user;
	}

	/**
	 * 切换当前部门 changeCurrentDept
	 * 
	 * @see com.deppon.foss.module.login.server.service.ILoginService#changeCurrentDept(java.lang.String)
	 *      changeCurrentDept
	 * @param currenUserDeptCode
	 *            当前部门编码
	 * @return OrgAdministrativeInfoEntity 切换后的部门
	 * @throws LoginException
	 * @since:
	 */
	@Override
	public OrgAdministrativeInfoEntity changeCurrentDept(
			String currenUserDeptCode) throws LoginException {
		if (currenUserDeptCode.trim() == null) {
			throw new LoginException(LoginException.CURRENT_USER_DEPT_CODE_NULL);
		}
		// 根据编码查询组织
		OrgAdministrativeInfoEntity currentDept = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currenUserDeptCode);
		if (currentDept == null) {
			throw new LoginException(LoginException.CURRENT_DEPT_ISNOT_EXIST);
		}
		// 设置当前部编码到session中
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",
				currentDept.getCode());
		// 设置当前部编码到session中
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
				currentDept.getName());
		return currentDept;
	}

	/**
	 * 通过部门名，查询当前用户所能切换的所有部门
	 * 
	 * @see com.deppon.foss.module.login.server.service.ILoginService#queryCurrentUserManagerDepts(java.lang.String)
	 *      queryCurrentUserManagerDepts
	 * @param deptName
	 * @return
	 * @since:
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryCurrentUserChangeDepts(
			String deptName, int start, int limit) {
		UserEntity user = FossUserContext.getCurrentUser();
//		Set<String> userOrgCodes = user.getOrgids();
//		String[] orgIds = new String[userOrgCodes.size()];
//		orgIds = userOrgCodes.toArray(orgIds);
//		int length = start+limit;
//		List<OrgAdministrativeInfoEntity> orgs = new ArrayList<OrgAdministrativeInfoEntity>();
//		if (deptName != null && !deptName.trim().equals("")) {
//			orgs = orgAdministrativeInfoService.queryOrgAdministrativeInfoBatchByCode(orgIds);
//			List<OrgAdministrativeInfoEntity> orgList = new ArrayList<OrgAdministrativeInfoEntity>();
//			for (OrgAdministrativeInfoEntity org : orgs) {
//				if (org.getName().indexOf(deptName)!=-1) {
//					orgList.add(org);
//				}
//			}
//			if(length>orgList.size()){
//				length = orgList.size();
//			}
//			/**
//			 * 用于分页，校验分页时查询，
//			 */
//			if(orgList.size()>=start){
//				orgs = orgList.subList(start, length);
//			}else{
//				orgs = orgList.subList(0, length);
//			}
//			//orgs = orgList.subList(start, length);
//			length = orgList.size();
//		}else{
//			if(length>orgIds.length){
//				length = orgIds.length;
//			}
//			String[] pageOrgIds = Arrays.copyOfRange(orgIds, start, length);
//			orgs = orgAdministrativeInfoService.queryOrgAdministrativeInfoBatchByCode(pageOrgIds);
//			length = orgIds.length;
//		}
//		if(orgs.size()>0){
//			orgs.get(0).setVersionNo((long)length);			
//		}
		List<OrgAdministrativeInfoEntity> orgs=orgAdministrativeInfoService.queryCurrentUserChangeDeptsByDeptNameLike(user.getUserName(),deptName,start,limit);
		if(orgs.size()>0){
			Long totalCount=orgAdministrativeInfoService.queryCurrentUserChangeDeptsCountsByDeptNameLike(user.getUserName(),deptName);
			orgs.get(0).setVersionNo(totalCount);			
		}
		return orgs;
	}


	

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @return the resourceService
	 */
	public IResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * @param resourceService
	 *            the resourceService to set
	 */
	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Override
	public List<ResourceEntity> queryResourceBatchByCode(String[] resCodes) {
		return resourceService.queryResourceBatchByCode(resCodes);
	}

	/**
	 * 查询用户常用菜单
	 */
	@Override
	public List<UserMenuEntity> queryUserMenuByUserCode(String empCode) {
		return userMenuService.queryUserMenuByUserCode(empCode);
	}

	/**
	 * 
	 * 根据员工编码和部门编码查询出所有的GUI权限信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-7 上午10:20:49
	 */
	public List<ResourceEntity> queryGUIResourceByUserCode(String empCode,
			String orgCode){
		return resourceService.queryGUIResourceByUserCode(empCode, orgCode);
	}
	
	/**
	 *收银员当前密码到期提醒
	 *@author 187862-dujunhui
	 *@date 2015-10-28 下午2:46:25 
	 */
	@Override
	public int queryLeftDaysOfPsw(){
		UserEntity user = FossUserContext.getCurrentUser();
		int leftDays=userService.queryLeftDaysOfPsw(user.getEmployee().getEmpCode(),user.getPassword());
		if(leftDays>=0 && leftDays<5){
			return leftDays+1;
		}
		return NumberConstants.NUMBER_NEGTIVE_1;
	}
	/**
	 * 
	 * <p>收银员登录FOSS系统需要短信验证</p> 
	 * @author 187862 
	 * @param roleSet 
	 * @date 2016-5-19 上午9:10:10
	 * @see
	 */
	@Override
	public String cashierLoginValidate(String userName,Set<String> roleList){
		try{
			//1.查询是否有配置短信开关
			//收银员相关角色登陆短信验证开关配置参数
			String switchCode ="CASHIER_LOGIN_SMS_SWITCH";
			ConfigurationParamsEntity SwitchEntity =configurationParamsService.queryConfigurationParamsOneByCode(switchCode);
			if(SwitchEntity !=null && StringUtil.isNotEmpty(SwitchEntity.getConfValue())){
				//获取参数配置值
				if(StringUtil.equals(SwitchEntity.getConfValue(), FossConstants.ACTIVE)){
					//2.查询是否有配置收银员相关角色的配置参数
					String rolesCode ="CASHIER_LOGIN_SMS_ROLES";
					ConfigurationParamsEntity rolesEntity =configurationParamsService.queryConfigurationParamsOneByCode(rolesCode);
					if(rolesEntity !=null && StringUtil.isNotEmpty(rolesEntity.getConfValue())){
						//获取当前登录人角色
						for(String role:roleList){
							if(rolesEntity.getConfValue().contains(role)){
								//3.判断当前登录人角色是否包含有收银员相关角色
								//4.获取当前登录人手机号
								EmployeeEntity entity=employeeService.querySimpleEmployeeByEmpCode(userName);
								if(null!=entity && StringUtil.isNotEmpty(entity.getMobilePhone())){
									return entity.getMobilePhone();
								}else{
									throw new LoginException("收银员角色相关人员登陆需短信验证，故手机号不可为空，请至OA维护！");
								}
							}
						}
					}
				}
			}
			return null;
		}catch(BusinessException e){
			throw new LoginException(e.getErrorCode());
		}
	}
	/**
	 * 
	 * <p>发送短信验证码方法</p> 
	 * @author 187862 
	 * @date 2016-5-19 上午10:19:51
	 * @see
	 */
	@Override
	public int sendCashierValidator(String phoneNum,String validator){
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(syncSmsUrl);
		SmsInfo info = new SmsInfo();
		info.setMobile(phoneNum); 
		info.setMsgContent("您的短信验证码  "+validator+"，请妥善保管，切勿泄露给任何人！请在两分钟内使用。");
		info.setSendDept("FOSS_LOGIN");
		info.setSender("FOSS_LOGIN");
		info.setMsgType("FOSS_LOGIN_CODE");
		info.setMsgSource("FOSS");	
		info.setUnionId("FOSS"+UUID.randomUUID().toString());
		info.setSendTime(new Timestamp(new Date().getTime()));
		info.setWaybillNo("11");
		info.setServiceType("1");
		List<SmsInfo> list = new ArrayList<SmsInfo>();
		list.add(info);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		try {
			mapper.writeValue(stringWriter, list);
			String digest = MD5Encrypt.encrypt(smsUsername + smsPassword);
			postMethod.getParams().setContentCharset("UTF-8");
			postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("data_digest", digest), new NameValuePair("smsInfo", stringWriter.getBuffer().toString())});
			int status = httpClient.executeMethod(postMethod);
//			SmsReturn smsReturn= mapper.readValue(postMethod.getResponseBody(), SmsReturn.class);
			LOGGER.info("sendCashierValidator:"+postMethod.getResponseBodyAsString());
			LOGGER.info("sendCashierValidator==>status:"+status);
			return status;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
    /**
     * 校验验证码是否正确
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-9-5 上午11:16:10
     * @param loginName
     * @param validator 
     * @see com.deppon.foss.module.login.server.service.ILoginService#checkValidator(java.lang.String, java.lang.String)
     */
	@Override
	public boolean checkValidator(String loginName, String validator) {
		// TODO Auto-generated method stub
		if(validator==null){
			throw new LoginException("未获取到验证码，请重新输入！");
		}
	  // ICache<String, String> cache = CacheManager.getInstance().getCache(FossTTLCache.BSE_LOGIN_VALIDATOR);
		 String value = loginValidatorCache.get(loginName);
	    if(validator.equals(value)){
			//验证码只能使用一次，若验证码正确，则将缓存中对应的数据清除。
	    	loginValidatorCache.remove(loginName);
	    }else{
	    	throw new LoginException("验证码错误！");
	    };
	    return true;
	}
	
}