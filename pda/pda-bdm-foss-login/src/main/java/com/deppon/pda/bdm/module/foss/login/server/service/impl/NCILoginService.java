package com.deppon.pda.bdm.module.foss.login.server.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.IValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.NCILoginReInfo;
import com.deppon.pda.bdm.module.core.shared.domain.PdaLoginInfo;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;

/**
 * 
 * @ClassName: NCILoginService
 * @Description: TODO(NCI登录)
 * @author &268974 wangzhili
 * @date 2016-2-27 下午3:05:01
 * 
 */
public class NCILoginService implements
		IBusinessService<NCILoginReInfo, PdaLoginInfo> {
	private static final Logger LOG = Logger.getLogger(NCILoginService.class);

	private IPdaSigninLogoutService pdaSigninLogoutService;
	
	private UserCache userCache;
	
	private DeptCache deptCache;
	
	private IValidateService validateService;

	@Override
	public PdaLoginInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PdaLoginInfo pdaLoginInfo = JsonUtil.parseJsonToObject(
				PdaLoginInfo.class, asyncMsg.getContent());
		// 部门编码
		pdaLoginInfo.setDeptCode(asyncMsg.getDeptCode());
		// pos编码
		pdaLoginInfo.setPdaCode(asyncMsg.getPdaCode());
		// 设备版本号
		pdaLoginInfo.setPdaPgmVer(asyncMsg.getPgmVer());
		// 用户编码
		pdaLoginInfo.setUserCode(asyncMsg.getUserCode());
		return pdaLoginInfo;
	}

	@Override
	public NCILoginReInfo service(AsyncMsg asyncMsg, PdaLoginInfo param)
			throws PdaBusiException {
		LOG.info("username:"+param.getUserCode()+"   password:"+param.getPassword());
		//参数验证
		this.validate(asyncMsg,param);
		//检验用户名和密码
		validateService.checkPwd(param);
		//登录返回值信息实体
		NCILoginReInfo loginReInfo = new NCILoginReInfo();
		//员工信息实体
		UserEntity userEntity = userCache.getUser(param.getUserCode());
		//员工部门信息实体
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		if(deptEntity==null){
			throw new FossInterfaceException(null,"用户部门信息不存在！");
		}
		//判断该工号是否为司机
		String isDriver = getTopFleetCodeByDeptId(deptEntity.getId());
		//设置用户类型
		if(!StringUtils.isEmpty(isDriver)){
			//司机
			param.setUserType("NCI_DRIVER");
		} else{
			//非司机
			param.setUserType("NCI_USER");
		}
		//设置参数
		PdaSigninDto pdaSigninDto = new PdaSigninDto();
		//设备号
		pdaSigninDto.setDeviceNo(asyncMsg.getPdaCode());
		//用户工号
		pdaSigninDto.setDriverCode(asyncMsg.getUserCode());
		//部门编码
		pdaSigninDto.setOrgCode(deptEntity.getDeptCode());
		//登录时间
		pdaSigninDto.setSignTime(new Date());
		//用户角色
		pdaSigninDto.setUserType("NCI_USER");
		try{
			//调用foss接口
			pdaSigninLogoutService.signIn(pdaSigninDto);
		}catch (BusinessException e) {
			//LOG.error("登陆异常:"+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(null,"调用foss接口出现位置异常");
		}
		
		//封装返回数据
		//用户类型
		loginReInfo.setUserType(param.getUserType());
		//员工号
		loginReInfo.setEmpCode(userEntity.getEmpCode());
		//员工姓名
		loginReInfo.setEmpName(userEntity.getUserName());
		//部门编码
		loginReInfo.setDeptCode(deptEntity.getDeptCode());
		//部门名称
		loginReInfo.setDeptName(deptEntity.getDeptName());
		return loginReInfo;
	}

	//查找司机所在的顶级车队
	private String getTopFleetCodeByDeptId(String deptId){
		while(true){
			if(StringUtils.isEmpty(deptId)){
				return null;
			}
			DeptEntity dept = deptCache.getDept(deptId);
			if(dept==null){
				return null;
			}
			/**
			 * @需求：NCI登录优化
			 * @功能：如果部门名称中含有“派送部”活着“派送部中心”则定为营业性质
			 * @author 218371-foss-Zhaoyanjun
			 * @date:2016-11-16下午18:08
			 */
			if(dept.getDeptName()!=null){
				if(dept.getDeptName().contains("派送部")||dept.getDeptName().contains("派送中心")){
					return null;
				}
			}
			if("Y".equals(dept.getIsTopFleet())){
				return dept.getDeptCode();
			}
			if(StringUtils.isEmpty(dept.getParentOrgCode())||dept.getParentOrgCode().equals(dept.getId())){
				return null;
			}
			deptId = dept.getParentOrgCode();
		}
		
	}

	//校验请求参数
	private void validate(AsyncMsg asyncMsg, PdaLoginInfo param) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(param, "PdaLoginInfo");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.UserCode");
	}

	//操作类型
	@Override
	public String getOperType() {

		return LoginConstant.OPER_TYPE_NCI_LOGIN.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setValidateService(IValidateService validateService) {
		this.validateService = validateService;
	}

	public void setPdaSigninLogoutService(
			IPdaSigninLogoutService pdaSigninLogoutService) {
		this.pdaSigninLogoutService = pdaSigninLogoutService;
	}

	
}
