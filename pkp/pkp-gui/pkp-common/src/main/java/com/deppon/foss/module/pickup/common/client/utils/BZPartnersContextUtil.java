package com.deppon.foss.module.pickup.common.client.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

/**
 * 合伙人信息类,用于判断是否是合伙人加盟网点、是否有控件使用权限
 * 
 * @author 272311
 * @date 2016-1-6
 */
public class BZPartnersContextUtil {
	
	private static Logger logger = LoggerFactory.getLogger(BZPartnersContextUtil.class);

	// 获得远程HessianRemoting接口
	private IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);

	private BaseDataServiceFactory baseDataServiceFactory ;
	
//	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	private BZPartnersContextUtil() {
		super() ;
	}

	private static BZPartnersContextUtil bzPartnersContext = null;

	public static BZPartnersContextUtil getBzPatnersContext() {
		if (bzPartnersContext == null) {
			bzPartnersContext = new BZPartnersContextUtil();
		}
		return bzPartnersContext;
	}

	/**
	 * 判断当前用户是否 是 加盟网点/合伙人（“合伙人营业部经理“、”合伙人收银员“、”合伙人营业员“）(推荐) 
	 * @return true 是 ; false 否 或 为空
	 * @author 272311
	 * @date 2016-1-6
	 */
	public boolean isPartner() {
		boolean flag = false;
		try {
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			if(user != null ){
				OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
				if(dept != null && StringUtils.isNotBlank(dept.getCode()) ){
					logger.info("当前用户所在的部门编码："+dept.getCode());
					flag = isPartner(dept.getCode());
				}
			}else logger.info("获取用户信息为空") ;
		} catch (Exception e) {
			logger.error("判断 是否合伙人时 获取用户信息失败"+e.getMessage());
			throw new BusinessException("获取用户信息异常："+e.getMessage());
		}
		
		logger.info("根据当前用户判断 是否合伙人 flag=" + flag);
		return flag;
	}
	
	/**
	 * 根据部门编码查询 是否属于 加盟网点/合伙人（“合伙人营业部经理“、”合伙人收银员“、”合伙人营业员“）
	 * @param deptCode 部门编码
	 * @return true 是 ; false 否 或 为空
	 * @author 272311
	 * @date 2016-1-6
	 * @see com.deppon.foss.module.pickup.common.client.utils.BZPartnersContextUtil#isPartner()
	 */
	public boolean isPartner(String deptCode) {
		boolean flag = false;
		try {
			BaseDataService baseDataService = baseDataServiceFactory.getBaseDataService() ;
			SaleDepartmentEntity saleDepartmentEntity = baseDataService.querySimpleSaleDepartmentByCodeCache(deptCode);
			if (saleDepartmentEntity != null) {
				if (StringUtils.equals(saleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.ACTIVE)) {
					flag = true;
				} else {
					flag = false;
				}
			}
		} catch (Exception e) {
			logger.error("判断 是否合伙人时 获取用户部门信息异常："+e.getMessage());
			throw new BusinessException("获取用户部门信息异常："+e.getMessage());
		}
		logger.info("是否是合伙人 部门编码deptCode："+deptCode+" ; flag:" + flag);
		return flag;
	}
	
	/**
	 * 根据当前登录人是否为合伙人,如果是则再进行判断当前登录人是否有控件编码powerCode的使用权(推荐) 
	 * @param powerCode 标杆编码
	 * @return true:有控件的使用权；false:无控件使用权
	 * @author 272311-sangwenhao
	 * @date 2016-1-6
	 */
	public boolean isBlong2BZPartner(String powerCode){
		boolean flag = false ;
		//先判断当前登录人是否为合伙人,如果是则再进行判断当前登录人是否有控件编码powerCode的使用权
		if(isPartner()){
			flag = isBZPartnerPower(powerCode) ;
		}
		return flag ;
	}	
	
	/**
	 * 根据控件编码判断数据字典中(暂存)是有该控件，如果有却启用则表示有权限，否则无权限
	 * @param powerCode 控件编码
	 * @return true:有控件的使用权；false:无控件使用权
	 * @author 272311-sangwenhao
	 * @date 2016-1-6
	 * @see com.deppon.foss.module.pickup.common.client.utils.BZPartnersContextUtil#isBlong2BZPartner(String powerCode)
	 */
	public boolean isBZPartnerPower(String powerCode){
		boolean flag = false ;
		try {
			ConfigurationParamsEntity configurationParamsEntity = waybillRemotingService.queryConfigurationParamsOneByCode(powerCode);
			if(configurationParamsEntity != null){
				if(StringUtils.equals(powerCode, configurationParamsEntity.getCode())&& StringUtils.equals(configurationParamsEntity.getConfValue(), FossConstants.ACTIVE)){
					flag = true ;
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("判断权限异常："+e.getMessage());
		}
		logger.info("合伙人权限 powerCode："+powerCode+" ; flag:" + flag);
		return flag ;
	}
	
}
