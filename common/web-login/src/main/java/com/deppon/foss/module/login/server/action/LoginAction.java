package com.deppon.foss.module.login.server.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.log.log4j.entity.ExpressLogin;
import com.deppon.dpap.log.log4j.kit.Log4jKit;
import com.deppon.dpap.log.log4j.kit.StaticLogType;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.exception.security.UserNotLoginException;
import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.server.web.tag.WroResourcePropCache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserCubcCheckLoginEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.server.interceptor.CookieNonCheckRequired;
import com.deppon.foss.module.frameworkimpl.shared.domain.Cookie;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.login.server.service.ILoginService;
import com.deppon.foss.module.login.server.service.impl.LoginValidatorCache;
import com.deppon.foss.module.login.shared.define.MessageType;
import com.deppon.foss.module.login.shared.util.MacToken;
import com.deppon.foss.module.login.shared.util.TokenMarshal;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:登录WEB服务层</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
 * 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public class LoginAction extends AbstractAction {
	
	private static final long serialVersionUID = -5182857592065055743L;
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LoginAction.class);
   private static final  org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LoginAction.class);
    private LoginValidatorCache loginValidatorCache;
	public void setLoginValidatorCache(LoginValidatorCache loginValidatorCache) {
		this.loginValidatorCache = loginValidatorCache;
	}
	//注入loginService
	private ILoginService loginService;
	//用户名
	private String loginName;
	private  String  publicIp;
	

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	//密码
	private String password;
    //验证码
	private String cashierValidator;
	// 当前用户
	private UserEntity currentUser;
	
	//当前部门
	private OrgAdministrativeInfoEntity currentDept;
	
	//当前用户管理部门编码集合
	private List<String> currentUserDeptCodes;
	
	//当前服务器端时间
	private Date currentServerTime;;
	
	//待切换的当前部门编码
	private String currenUserDeptCode;
	
	//查询部门名
	private String deptName;
	
	private boolean doLogin;
	
	//是否开发环境
	private boolean dev;
	
	// 加密TOKEN
	private String requestToken = null;

	private String  mac;
	//收银员当前密码剩余有效期
	private int  leftDays;
	//判断当前登录人是否具有收银员相关角色
	private String isContainCashierRole;
	//当前登录人手机号
	private String phoneNum;
	//cubc返回的登录ip
	private String cubcIp;
	//请求cubc登录校验地址
	private String requestCubcLoginAddress;
	//用来存取uuid和当前登录人的用户名
	private static Map<String,String> cacheMap = new HashMap<String, String>();
	
	
	/**
	 * 用户Service
	 */
	private IUserService userService;
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
    /**
     * 系统配置参数 Service接口
     */
    private IConfigurationParamsService configurationParamsService;

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
	
	//当前用户所管理的所有部门
	private List<OrgAdministrativeInfoEntity> userManagerDepts;
	
	private IBusinessMonitorService businessMonitorService;

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	/**
	 * 获得当前用户/部门信息/服务器当前时间
	 * @return
	 */
	@JSON
	public String currentLoginUserInfo() {
		//获得当前登录用户
		currentUser = FossUserContext.getCurrentUser();
		currentUser.setOrgResCodes(null);
		currentUser.setOrgResUris(null);
		//获得当前部门
		currentDept = FossUserContext.getCurrentDept();
		//当前用户管理部门编码集合
		currentUserDeptCodes = FossUserContext.getCurrentUserManagerDeptCodes();
		//服务器当前时间
		currentServerTime = new Date();
		return returnSuccess(MessageType.GETLOGINUSERINFO_SUCCESS);
	}
	
	/**
	 * 得到当前用户所能切换的部门
	 * currentUserChangeDepts
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String currentUserChangeDepts(){
		userManagerDepts = loginService.queryCurrentUserChangeDepts(deptName, start, limit);
		if(userManagerDepts.size()>0){
			totalCount = (long) userManagerDepts.get(0).getVersionNo();			
		}else{
			totalCount = 0L;
		}
		return returnSuccess();
	}
	
	/**
	 * 切换当前部门
	 * changeCurrentDept
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String changeCurrentDept(){
		try{
			//切换部门前 清除改用户在当前部门的登陆信息
			offline();
			//修改部门
			loginService.changeCurrentDept(currenUserDeptCode);
			//修改了session中存储的当前部门,生成cookie
			Cookie.saveCookie();
			//切换部门后 统计登陆的用户信息 存入缓存中
			online();
			return this.returnSuccess();
		}catch(BusinessException e){
			return this.returnError(e);
		}
	}

	/**
	 * 登入的时候将信息存到缓存中去
	 */
	private void online() {
		CurrentInfo info = FossUserContext.getCurrentInfo();
		businessMonitorService.online(info, info.getEmpCode());
	}

	/**
	 * 切换部门的时候 将当前部门中的在线用户信息删除
	 */
	private void offline() {
		CurrentInfo info = FossUserContext.getCurrentInfo();
		businessMonitorService.offline(info);
	}
	
	
	private String doLogin(){
		if(this.doLogin){
			ExpressLogin el=new ExpressLogin();
			el.setSource_public_ip(publicIp);
			try{
				//校验验证码是否需要验证码
				UserEntity user = userService.findByLoginName(loginName);       
				if(null==user){
					return this.returnError("未查找到对应用户");
				}
				//获取当前用户所具有的角色
				user = userService.findByUserId(user.getId());
				if(null==user){
					return this.returnError("用户未配置角色");
				}
			    HttpServletRequest request = (HttpServletRequest) ServletActionContext.getRequest();
				EmployeeEntity employ = user.getEmployee();
				loginService.attachUserInfoToEl(el,employ,request);
				el.setOperation_type("login");
				 Log4jKit.setWebAppId("FOSS");
			    isContainCashierRole=loginService.cashierLoginValidate(loginName, user.getRoleids());
			    if(isContainCashierRole!=null){
			    //校验 验证码是否正确
				loginService.checkValidator(loginName, cashierValidator);
				}
				loginService.userLogin(loginName, password);
				//这时跳转到main.jsp 根据session生成cookie
				Cookie.saveCookie();
				el.setReason(null);
				el.setResult("success");
				
				Log4jKit.info(log,LoginAction.class,StaticLogType.EXPRESS_LOGIN,el );
				return this.returnSuccess(MessageType.LOGIN_SUCCESS);
			}catch(BusinessException e){
				el.setResult("fail");
				el.setReason(e.getErrorCode());
				Log4jKit.info(log,LoginAction.class,StaticLogType.EXPRESS_LOGIN,el );
				return this.returnError(e);
			}
		} else {
			try {
				FossUserContext.getCurrentUser();
			} catch(UserNotLoginException e) {
				return "login";
			}			
			return this.returnSuccess();
		}
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@CookieNonCheckRequired
	@SecurityNonCheckRequired
	public String checkHardwareInfo() {
		boolean existMac = loginService.checkMacExist(mac);
		if(existMac){
			MacToken macToken = new MacToken(mac);
			requestToken = TokenMarshal.marshal(macToken);
			return returnSuccess();
		}else{
			return returnError(MessageType.MAC_INFO_NOT_EXIST);
		}
	}
	
	/**
	 * 收银员当前密码到期提醒（90天有效）
	 * @return
	 */
	@JSON
	public String passWordDeadLineWarning() {
		try{
			String title=FossUserContext.getCurrentUser().getEmployee().getTitle();
			if(StringUtil.isNotEmpty(title) && (StringUtil.equals(title, "05010001")
					||StringUtil.equals(title, "04010003"))){//是收银员05010001和04010003
				//获得当前登录用户密码有效期(返回-1或者1-5六个值)
				int availableDays=loginService.queryLeftDaysOfPsw();
				if(availableDays !=NumberConstants.NUMBER_NEGTIVE_1){
					leftDays=availableDays;
				}
			}
			return returnSuccess(MessageType.WARNING_SUCCESS);
		}catch(BusinessException e){
			return returnError(MessageType.WARNING_FAIL);
		}
	}
	
	/**
	 * 主页面
	 */
	@Override
	@SecurityNonCheckRequired
	public String execute()  throws Exception{
		dev = WroResourcePropCache.getDevConfig();
		ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,DictionaryValueConstants.BROWSER_LOGIN_VALIDATE,"DIP");
		if(null != entity && StringUtils.equals(FossConstants.NO, entity.getConfValue())){
			return this.doLogin();			
	    }
		HttpServletResponse response = ServletActionContext.getResponse();
		javax.servlet.http.Cookie cookie = Cookie.getCookie("_MAC_TOKEN");
		if(requestToken!=null){
			MacToken macToken = TokenMarshal.unMarshal(requestToken);			
			boolean existMac = loginService.checkMacExist(macToken.getMac());
			if (!existMac) {
				return   "noexistmac";
			}else{
				cookie = new javax.servlet.http.Cookie("_MAC_TOKEN",requestToken);
				response.addCookie(cookie);
			}
		}
		if(cookie!=null&&cookie.getValue()!=null){
			return this.doLogin();			
		}else{
			return   "noexistmac";
		}
		
	}
	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	/**
	 * 登录检验，检验通过生成cookie,（不会页面跳转）
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-9-28 上午9:20:05
	 * @return
	 * @throws Exception
	 * @see
	 */
	public String execute1()  throws Exception{
		dev = WroResourcePropCache.getDevConfig();
		ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,DictionaryValueConstants.BROWSER_LOGIN_VALIDATE,"DIP");
		if(null != entity && StringUtils.equals(FossConstants.NO, entity.getConfValue())){
			return this.doLogin();
	    }
		HttpServletResponse response = ServletActionContext.getResponse();
		javax.servlet.http.Cookie cookie = Cookie.getCookie("_MAC_TOKEN");
		if(requestToken!=null){
			MacToken macToken = TokenMarshal.unMarshal(requestToken);			
			boolean existMac = loginService.checkMacExist(macToken.getMac());
			if (!existMac) {
				return 	this.returnError("您的电脑未注册，无法登陆FOSS系统，请联系IT维护员走OA工作流进行电脑注册！");			
			}else{
				cookie = new javax.servlet.http.Cookie("_MAC_TOKEN",requestToken);
				response.addCookie(cookie);
			}
		}
		if(cookie!=null&&cookie.getValue()!=null){
			return this.doLogin();			
		}else{
			return 	this.returnError("您的电脑未注册，无法登陆FOSS系统，请联系IT维护员走OA工作流进行电脑注册！");			

		}
		
	}
	/**
	 * 
	 * <p>登陆验证该用户是否需要短信验证码</p> 
	 * @author 187862 
	 * @date 2016-5-19 下午5:12:06
	 * @return
	 * @see
	 */
	public String checkUserName(){
		//异常信息记录
		String errorMsg="";
		//查询当前登录用户
		UserEntity user = userService.findByLoginName(loginName);
		if(null==user){
			return null;
		}
		//获取当前用户所具有的角色
		user = userService.findByUserId(user.getId());
		if(null==user){
			return null;
		}
		
		try{
			//返回手机号码
			isContainCashierRole=loginService.cashierLoginValidate(loginName, user.getRoleids());
		}catch(BusinessException e){
			errorMsg=e.getErrorCode();
		}
		
		LOGGER.info("checkUserName:"+this.loginName+"&&mobilePhone:"+isContainCashierRole);
		Document document=DocumentHelper.createDocument();
		Element goodses=DocumentHelper.createElement("goodses");
		document.setRootElement(goodses);
		
		Element goods=goodses.addElement("goods");
		
		Element name=goods.addElement("isContainCashierRole");
		if(StringUtil.isNotEmpty(isContainCashierRole)){
			name.setText(isContainCashierRole);
		}
		
		Element msg=goods.addElement("errorMsg");
		if(StringUtil.isNotEmpty(errorMsg)){
			msg.setText(errorMsg);
		}
				
		try {			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			
			out.flush();
			out.close();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 
	 * <p>收银员角色登录发送短信</p> 
	 * @author 187862 
	 * @date 2016-5-23 下午4:51:00
	 * @return
	 * @see
	 */
	public String sendCode(){
		StringBuilder validator = new StringBuilder();  
        Random random = new Random();  
        // 6位验证码  
        for (int i = 0; i < 6; i++) {  
        	validator.append(String.valueOf(random.nextInt(10)));  
        }
        //同步接口返回状态，200表示成功
		int returnResult=loginService.sendCashierValidator(phoneNum,validator.toString());
		boolean flag =false;
		if(returnResult==200){
 		 flag = loginValidatorCache.set(loginName,validator.toString());
		}
 		LOGGER.info("sendCode==>phoneNum:"+this.phoneNum+"&&validator:"+validator.toString());
		Document document=DocumentHelper.createDocument();
		Element goodses=DocumentHelper.createElement("goodses");
		document.setRootElement(goodses);
		Element goods=goodses.addElement("goods");
		Element name=goods.addElement("returnResult");
		if(flag){
		 name.setText(String.valueOf(returnResult));
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			
			out.flush();
			out.close();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 随机生成uuid，跟当前登录人绑定
	 * @author 378375
	 * @date 2017年3月22日 21:11:10
	 * 需求编号：DN201703160008
	 * @return
	 */
	public String generateUUID(){
		LOGGER.info("登录CUBC主页开始...");
		try {
			UserCubcCheckLoginEntity reqEntity = new UserCubcCheckLoginEntity();
			String uuid = UUIDUtils.getUUID();
			//用户名
			String userName = FossUserContext.getCurrentUser().getUserName();
			HttpSession session = ServletActionContext.getRequest().getSession();
			//把用户名和uuid绑定
			cacheMap.put(uuid, userName);
			//随机id
			reqEntity.setUuid(uuid);
			//sessionid
			reqEntity.setSessionId(session.getId());
			//用户名
			reqEntity.setUserName(userName);
			UserCubcCheckLoginEntity respEntity = (UserCubcCheckLoginEntity) HttpClientUtils.postMethod(reqEntity, new UserCubcCheckLoginEntity(), requestCubcLoginAddress);
			cubcIp = respEntity.getIp();
			LOGGER.info("登录CUBC主页结束...");
			return this.returnSuccess();
		}catch(BusinessException e){
			return this.returnError(e);
		}
	}
	/**
	 * 给cubc提供的校验当前登录人方法
	 * @author 378375
	 * @date 2017年3月22日 21:19:35
	 * 需求编号：DN201703160008
	 * @return
	 */
	public void checkLogin(){
		LOGGER.info("CUBC校验登录开始...");
		UserCubcCheckLoginEntity resp = new UserCubcCheckLoginEntity();
		String requuid = null;
		BufferedReader br = null;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setCharacterEncoding("UTF-8");
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			LOGGER.info("CUBC校验登录的请求信息："+sb.toString());
			UserCubcCheckLoginEntity requestEntity = JSONObject.parseObject(sb.toString(), UserCubcCheckLoginEntity.class);
			requuid = requestEntity.getUuid();
			String requestName = requestEntity.getUserName();
			//通过请求的uuid获取对应的用户名称
			String nowUserName = cacheMap.get(requuid);
			//如果用户名称跟当前登录人一致，则校验通过
			LOGGER.info("当前用户名："+nowUserName);
			if(requestName != null){
				if(StringUtils.equals(requestName, nowUserName)){
					resp.setUserName(nowUserName);
					resp.setResultMark(Boolean.TRUE);
				}else {
					resp.setResultMark(Boolean.FALSE);
				}
			}
		} catch (Exception e) {
			LOGGER.info("读取响应数据失败，请重试！", e);
			resp.setResultMark(Boolean.FALSE);
			throw new UserException("读取响应数据失败，请稍后重试！", e.getMessage());
		}finally {
			//校验以后，把本次记录删除
			cacheMap.remove(requuid);
			try {
				if(br != null){
					br.close();
				}
			} catch (Exception e2) {
				LOGGER.error("关闭输入流生发生系统异常，请稍后重试！", e2);
			}
		}
		ServletOutputStream outPut = null;
		try {
			outPut = ServletActionContext.getResponse().getOutputStream();
			outPut.write(JSONObject.toJSONString(resp).getBytes());
			outPut.flush();
			LOGGER.info("返回给CUBC校验登录的响应信息："+JSONObject.toJSONString(resp));
		} catch (IOException e) {
			LOGGER.info("返回cubc响应数据失败，请重试！", e);
		}finally {
			if(outPut != null){
				try {
					outPut.close();
				} catch (IOException e3) {
					LOGGER.error("关闭输出流生发生系统异常，请稍后重试！", e3);
				}
			}
			
		}
	}
	
	public OrgAdministrativeInfoEntity getCurrentDept() {
		return currentDept;
	}

	public UserEntity getCurrentUser() {
		return currentUser;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCurrenUserDeptCode(String currenUserDeptCode) {
		this.currenUserDeptCode = currenUserDeptCode;
	}

	public List<OrgAdministrativeInfoEntity> getUserManagerDepts() {
		return userManagerDepts;
	}
	
    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getCurrentServerTime() {
		return currentServerTime;
	}

	public void setDoLogin(boolean doLogin) {
		this.doLogin = doLogin;
	}

	public List<String> getCurrentUserDeptCodes() {
		return currentUserDeptCodes;
	}

	public boolean getDev() {
		return dev;
	}

	public int getLeftDays() {
		return leftDays;
	}

	public void setLeftDays(int leftDays) {
		this.leftDays = leftDays;
	}

	public String getIsContainCashierRole() {
		return isContainCashierRole;
	}

	public void setIsContainCashierRole(String isContainCashierRole) {
		this.isContainCashierRole = isContainCashierRole;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setCashierValidator(String cashierValidator) {
		this.cashierValidator = cashierValidator;
	}

	public String getCubcIp() {
		return cubcIp;
	}

	public void setRequestCubcLoginAddress(String requestCubcLoginAddress) {
		this.requestCubcLoginAddress = requestCubcLoginAddress;
	}
	
}
