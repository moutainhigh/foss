package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LdpAgencyDeptException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptExpressVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyDeptAction extends AbstractAction {


    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 快递代理网点service接口
    private ILdpAgencyDeptService ldpAgencyDeptService;

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	// 快递代理网点 action使用VO
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
	    .getLogger(LdpAgencyDeptAction.class);

    /**
     * <p>
     * 查询快递代理网点
     * </p>
     * 
     * 1.6.1 查询快递代理网点 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入快递代理网点查询界面
     * 
     * 2 输入查询条件，点击“查询”按钮
     * 
     * 【快递代理网点查询条件】 【快递代理网点查询结果列表】 后台执行查询，并把查询结果返回到前台
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     * 
     * @return String
     */
    public String queryLdpAgencyDept() {
	objectVo = dealCarrierBusiness(dealValueAddedServices(objectVo));
	OuterBranchExpressEntity entityCondition = objectVo.getOuterBranchExpressEntity();
	// 返回的结果显示在表格中：
	objectVo.setOuterBranchExpressEntityList(ldpAgencyDeptService
		.queryLdpAgencyDepts(entityCondition, limit, start));
	totalCount = ldpAgencyDeptService.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * <p>
     * 快递代理网点 是否重复
     * </p>
     * 
     * SR-1
     * 
     * “快递代理网点编码”由操作员根据定义好的编码规则进行输入，不可重复；
     * 
     * “快递代理网点名称”不可重复；
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     * 
     * @return String
     */
    public String ldpAgencyDeptIsExist() {
	try {
		List<OuterBranchExpressEntity> list = ldpAgencyDeptService
			    .queryLdpAgencyDepts(objectVo.getOuterBranchExpressEntity(), 1, 0);
	    objectVo.setOuterBranchExpressEntity((CollectionUtils
				.isNotEmpty(list)) ? list.get(0) : null);
	    return returnSuccess();
	} catch (LdpAgencyDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 根据代理公司虚拟编码查询该公司的所有代理网点
     * 
     * 1.6.1 查询快递代理网点 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入快递代理网点查询界面
     * 
     * 2 输入查询条件，点击“查询”按钮【快递代理网点查询条件】 【快递代理网点查询结果列表】 后台执行查询，并把查询结果返回到前台
     * 
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     * 
     * @return String
     * 
     * @see
     */
    public String queryOuterBranchsByComCode() {
    	String comCode = objectVo.getBusinessPartnerExpressEntity().getAgentCompanyCode();
    	objectVo.setOuterBranchExpressEntityList(ldpAgencyDeptService.queryLdpAgencyDeptsByagencyCompanyCode(comCode, FossConstants.ACTIVE, limit,start));
    	OuterBranchExpressEntity entityCondition = new OuterBranchExpressEntity();
    	entityCondition.setActive(FossConstants.ACTIVE);
    	entityCondition.setAgentCompany(comCode);
    	entityCondition.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
    	totalCount = ldpAgencyDeptService.queryRecordCount(entityCondition);
	// 返回的结果显示在表格中：
	return returnSuccess();
    }

    /**
     * 根据code作废快递代理网点
     * 
     * 1.6.4 作废快递代理网点 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 点击查询结果列表中的操作列中的“作废”按钮
     * 
     * 校验是否可作废，若可作废则弹出“确认/取消”的对话框，内容为“您确定要作废该快递代理网点信息吗？”
     * 
     * 2 在弹出的“确认/取消”对话框中，点击“确认”按钮 更新该快递代理网点信息状态为“已作废”，同时刷新【快递代理网点查询结果列表】
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 1a 除步骤1的操作方式外，还可以勾选列表中的记录，点击列表顶部或下部的“作废”按钮 【快递代理网点查询结果列表】
     * 
     * 2a 步骤2中，点击“取消”按钮，则回到查询界面
     * 
     * 
     * SR-4
     * 
     * 只有新增快递代理网点时的管理部门或者系统管理员才有权限修改、作废快递代理网点；
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     * 
     * @return String
     * 
     * @see
     */
    public String deleteLdpAgencyDeptByCode() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = ldpAgencyDeptService.deleteLdpAgencyDeptByCode(
		    objectVo.getCodeStr(), FossUserContext.getCurrentInfo()
			    .getEmpCode());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (LdpAgencyDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改快递代理网点
     * 
     * 1.6.3 修改快递代理网点 序号 基本步骤 相关数据 补充步骤 1 点击查询结果列表中的操作列中的“修改”按钮 打开快递代理网点修改界面(图2)
     * 
     * 2 输入【快递代理网点信息】，点击修改界面的“保存”按钮 校验通过后，更新快递代理网点信息
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新填充各控件的值为修改前的值
     * 
     * 2b 步骤2中，点击“取消”按钮 退出当前界面
     * 
     * SR-4
     * 
     * 只有新增快递代理网点时的管理部门或者系统管理员才有权限修改、作废快递代理网点；
     * 
     * 步骤2中，点击“取消”按钮 退出当前界面
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     * 
     * @return String
     * 
     * @see
     */
    public String updateLdpAgencyDept() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = ldpAgencyDeptService.updateLdpAgencyDept(objectVo
		    .getOuterBranchExpressEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (LdpAgencyDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增快递代理网点
     * 
     * 1.6.2 新增快递代理网点 序号 基本步骤 相关数据 补充步骤 1 点击悬浮工具条中的“新增”按钮 打开新增界面(图2) 2
     * 
     * 输入快递代理网点信息，点击“保存”按钮 校验通过后，保存快递代理网点信息
     * 
     * SR-2
     * 
     * 新增或修改快递代理网点时：
     * 
     * 1、新增、修改时，快递代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
     * 
     * 2、必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
     * 
     * 3、必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
     * 
     * SR-4
     * 
     * 只有新增快递代理网点时的管理部门或者系统管理员才有权限修改、作废快递代理网点；
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新初始化各控件的值
     * 
     * 2b 步骤2中，点击“取消”按钮退出当前界面
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     * 
     * @return String
     * 
     * @see
     */
    public String addLdpAgencyDept() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = ldpAgencyDeptService.addLdpAgencyDept(objectVo
		    .getOuterBranchExpressEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (LdpAgencyDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 处理增值服务，前台传数据字典转换到后台查询
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     */
    private AgencyCompanyOrDeptExpressVo dealValueAddedServices(
	    AgencyCompanyOrDeptExpressVo agencyCompanyOrDeptExpressVo) {
	OuterBranchExpressEntity entityCondition = agencyCompanyOrDeptExpressVo
		.getOuterBranchExpressEntity();
	if (DictionaryValueConstants.PAY_COLLECTION
		.equals(agencyCompanyOrDeptExpressVo.getValueAddedServices())) {
	    entityCondition.setHelpCharge(FossConstants.YES);
	} else if (DictionaryValueConstants.CASH_DELIVERY
		.equals(agencyCompanyOrDeptExpressVo.getValueAddedServices())) {
	    entityCondition.setArriveCharge(FossConstants.YES);
	} else if (DictionaryValueConstants.BACK_SIGN
		.equals(agencyCompanyOrDeptExpressVo.getValueAddedServices())) {
	    entityCondition.setReturnBill(FossConstants.YES);
	}
	agencyCompanyOrDeptExpressVo.setOuterBranchExpressEntity(entityCondition);
	return agencyCompanyOrDeptExpressVo;
    }

    /**
     * 处理承运业务，前台传数据字典转换到后台查询
     * 
     * @author WangPeng
     * 
     * @date 2013-07-19 8:58 PM
     */
    private AgencyCompanyOrDeptExpressVo dealCarrierBusiness(
	    AgencyCompanyOrDeptExpressVo agencyCompanyOrDeptExpressVo) {
	OuterBranchExpressEntity entityCondition = agencyCompanyOrDeptExpressVo
		.getOuterBranchExpressEntity();
	if (DictionaryValueConstants.DEPARTURE.equals(agencyCompanyOrDeptExpressVo
		.getCarrierBusiness())) {
	    entityCondition.setLeave(FossConstants.ACTIVE);
	} else if (DictionaryValueConstants.ARRIVE.equals(agencyCompanyOrDeptExpressVo
		.getCarrierBusiness())) {
	    entityCondition.setArrive(FossConstants.ACTIVE);
	} else if (DictionaryValueConstants.TRANSFER
		.equals(agencyCompanyOrDeptExpressVo.getCarrierBusiness())) {
	    entityCondition.setTransfer(FossConstants.ACTIVE);
	}
	agencyCompanyOrDeptExpressVo.setOuterBranchExpressEntity(entityCondition);
	return agencyCompanyOrDeptExpressVo;
    }

}
