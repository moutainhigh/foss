package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LdpAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptExpressVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyCompanyAction extends AbstractAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 公司接口
	private ILdpAgencyCompanyService ldpAgencyCompanyService;

	// 公司vo
	private AgencyCompanyOrDeptExpressVo objectVo = new AgencyCompanyOrDeptExpressVo();

	public AgencyCompanyOrDeptExpressVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(AgencyCompanyOrDeptExpressVo objectVo) {
		this.objectVo = objectVo;
	}

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LdpAgencyCompanyAction.class);

	/**
	 * <p>
	 * 查询快递代理公司
	 * </p>
	 * 
	 * @author WangPeng
	 * @date 2013-07-19 11:12 AM
	 * @return String
	 */
	@JSON
	public String queryLdpAgencyCompany() {
		// 获得前台传来的快递代理公司对象
		BusinessPartnerExpressEntity entityCondition = objectVo
				.getBusinessPartnerExpressEntity();
		if(null == entityCondition){
			entityCondition = new BusinessPartnerExpressEntity();
		}
		entityCondition.setActive(FossConstants.ACTIVE);

		// 返回的结果显示在表格中：
		objectVo.setBusinessPartnerExpressEntityList(ldpAgencyCompanyService
				.queryLdpAgencyCompanys(entityCondition, limit, start));
		// 返回记录的总行数
		totalCount = ldpAgencyCompanyService.queryRecordCount(entityCondition);
		return returnSuccess();
	}

	/**
	 * <p>
	 * 快递代理公司 是否重复
	 * </p>
	 * 
	 * @author WangPeng
	 * @date 2013-07-19 11:12 AM
	 * @return String
	 */
	@JSON
	public String ldpAgencyCompanyIsExist() {
		// 获得前台传来的快递代理公司对象
		BusinessPartnerExpressEntity entityCondition = objectVo
						.getBusinessPartnerExpressEntity();
		if(null == entityCondition){
			entityCondition = new BusinessPartnerExpressEntity();
		}
		try {
			if(StringUtils.isEmpty(entityCondition.getActive())|| !"Y".equals(entityCondition.getActive())){
				//如果是否有效为空或者不等于Y,就赋值为有效的	
				entityCondition.setActive(FossConstants.ACTIVE);
			}
			//根据前台传递过来的快递代理公司信息判断是否有重复
			List<BusinessPartnerExpressEntity> list = ldpAgencyCompanyService
					.queryInfos(entityCondition, 1, 0);
			objectVo.setBusinessPartnerExpressEntity((CollectionUtils
					.isNotEmpty(list)) ? list.get(0) : null);
			return returnSuccess();
		} catch (LdpAgencyCompanyException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 根据code作废快递代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-19 11:12 AM
	 * @return String
	 * @see
	 */
	@JSON
	public String deleteLdpAgencyCompanyByCode() {
		try {
			//根据公司编码作废公司信息
			// 1：成功；-1：失败
			int returnInt = ldpAgencyCompanyService
					.deleteLdpAgencyCompanyByCode(objectVo.getCodeStr(),
							FossUserContext.getCurrentInfo().getEmpCode());
			objectVo.setReturnInt(returnInt);
			return returnSuccess();
		} catch (LdpAgencyCompanyException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 修改快递代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-19 11:12 AM
	 * @return String
	 * @see
	 */
	@JSON
	public String updateLdpAgencyCompany() {
		//获取前台传递过来的参数集合
		BusinessPartnerExpressEntity entityCondition = objectVo
				.getBusinessPartnerExpressEntity();
		if(null == entityCondition){
			entityCondition = new BusinessPartnerExpressEntity();
		}
		try {
			if(StringUtils.isEmpty(entityCondition.getId())){
				throw new LdpAgencyCompanyException("要修改的记录Id为空！");
			}
			entityCondition.setActive(FossConstants.ACTIVE);
			// 1：成功；-1：失败
			int returnInt = ldpAgencyCompanyService
					.updateLdpAgencyCompany(entityCondition);
			objectVo.setReturnInt(returnInt);
			return returnSuccess();
		} catch (LdpAgencyCompanyException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 新增快递代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-19 11:12 AM
	 * @return String
	 * @see
	 */
	@JSON
	public String addLdpAgencyCompany() {
		// 获得前台传来的快递代理公司对象
		BusinessPartnerExpressEntity entityCondition = objectVo
								.getBusinessPartnerExpressEntity();
		if(null == entityCondition){
			entityCondition = new BusinessPartnerExpressEntity();
		}
		entityCondition.setActive(FossConstants.ACTIVE);
		try {
			// 1：成功；-1：失败
			int returnInt = ldpAgencyCompanyService
					.addLdpAgencyCompany(entityCondition);
			objectVo.setReturnInt(returnInt);
			return returnSuccess();
		} catch (LdpAgencyCompanyException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * @param ldpAgencyCompanyService
	 *            the ldpAgencyCompanyService to set
	 */
	public void setLdpAgencyCompanyService(
			ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}

}
