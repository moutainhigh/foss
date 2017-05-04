/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.hessian
 * FILE    NAME: BaseDataHessianRemoting.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillInspectionRemindService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILtDiscountBackInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICarloadPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IBaseDataHessianRemoting;
import com.deppon.foss.util.define.FossConstants;


/**
 * 基础服务接口的实现类
 * @author 026123-foss-lifengteng
 * @date 2013-8-1 下午8:53:13
 */
@Remote()
public class BaseDataHessianRemoting implements IBaseDataHessianRemoting {
	
	/**
	 * 组织服务
	 */
	@Resource
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 营业部服务接口
	 */
	@Resource
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 客户合同优惠信息接口
	 */
	@Resource
	IPreferentialService preferentialService;
	
	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}
	/**
	 * 产品类型服务
	 */
	@Resource
	private IProductService productService;

	@Resource
	private ISalesBillingGroupService salesBillGroupService;
	@Resource
	private ICarloadPriceService carloadPriceService;//整车价格波动参数  @author  yangkang

	@Resource
	private IOrgAdministrativeInfoComplexService  orgAdministrativeInfoComplexService;//组织服务  @author  yangkang
	//wutao start...
	@Resource
	private IBillInspectionRemindService billInspectionRemindService;
	/**   
	 *展馆信息
	 */
	@Resource
	private IExhibitionKeywordService exhibitionKeywordService;
	
	@Resource
	private IBargainAppOrgService bargainAppOrgService;
	@Resource
	private ILtDiscountBackInfoService ltDiscountBackInfoService;
	
	public ILtDiscountBackInfoService getLtDiscountBackInfoService() {
		return ltDiscountBackInfoService;
	}
	public void setLtDiscountBackInfoService(
			ILtDiscountBackInfoService ltDiscountBackInfoService) {
		this.ltDiscountBackInfoService = ltDiscountBackInfoService;
	}
	public void setBargainAppOrgService(
			IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}
	public IBargainAppOrgService getBargainAppOrgService() {
		return bargainAppOrgService;
	}
	
	public IExhibitionKeywordService getExhibitionKeywordService() {
		return exhibitionKeywordService;
	}
	public void setExhibitionKeywordService(
			IExhibitionKeywordService exhibitionKeywordService) {
		this.exhibitionKeywordService = exhibitionKeywordService;
	}
	public void setBillInspectionRemindService(
			IBillInspectionRemindService billInspectionRemindService) {
		this.billInspectionRemindService = billInspectionRemindService;
	}
	public IBillInspectionRemindService getBillInspectionRemindService() {
		return billInspectionRemindService;
	}
	@Resource
	private IVehicleAgencyDeptService iVehicleAgencyDeptService;

	public IVehicleAgencyDeptService getiVehicleAgencyDeptService() {
		return iVehicleAgencyDeptService;
	}
	public void setiVehicleAgencyDeptService(
			IVehicleAgencyDeptService iVehicleAgencyDeptService) {
		this.iVehicleAgencyDeptService = iVehicleAgencyDeptService;
	}
	//wutao ..end
	/**
	 * 设置组织服务
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-1 下午5:19:43
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 设置营业部服务
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-1 下午5:19:43
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public void setSalesBillGroupService(
			ISalesBillingGroupService salesBillGroupService) {
		this.salesBillGroupService = salesBillGroupService;
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.waybill.server.hessian.BaseDataHessianRemoting.setOrgAdministrativeInfoComplexService
	 * @Description:设置组织服务
	 *
	 * @param orgAdministrativeInfoComplexService
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-17 下午6:20:16
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-17    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
	public void setCarloadPriceService(ICarloadPriceService carloadPriceService) {
		this.carloadPriceService = carloadPriceService;
	}

	/**
	 * 根据部门名称查询部门信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-1 下午8:56:51
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgInfoByName(String name){
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		org.setActive(FossConstants.ACTIVE);
		org.setName(name);
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByEntity(org, 0, NumberConstants.NUMBER_100);
	}
	
	/**
	 * 根据营业部名称查询营业部信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午2:38:08
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentByEntity(SaleDepartmentEntity entity){
		return saleDepartmentService.querySaleDepartmentByEntity(entity, 0, NumberConstants.NUMBER_100);
	}
	
	/**
	 * 根据产品编码与营业日期来筛选产品
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-26 下午3:44:55
	 */
	@Override
	public ProductEntity getProductByCache(String productCode,Date billDate){
		return productService.getProductByCache(productCode, billDate);
	}

	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author Rosanu
	 * @date 2013-10-27 下午6:33:35
	 */
	public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String code){
		return salesBillGroupService.querySalesListByBillingGroupCode(code);
	}
	

	/** 
	 * 根据营业部查询其所属全部开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-30 下午9:39:04
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IBaseDataHessianRemoting#querySalesListByBillingGroupCode(java.lang.String)
	 */
	@Override
	public List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code){
		return salesBillGroupService.queryBillingGroupListBySalesCode(code);
	}

	/**
	 * 根据标杆编码查询组织
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-12 16:44:46
	 */
	@Override
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCode(String unifiedCode) {
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(unifiedCode);
	}
	/**
	 * wutao start
	 */
	@Override
	public BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(OrgAdministrativeInfoEntity entity,String regionCode,String regionLevCode){
		return billInspectionRemindService.queryBillInspectionRemindByRegionCode(entity, regionCode, regionLevCode);
	}
	/**
	 * @author 200945-wutao
	 * Hessian中配置偏线，空运信息获取的方法
	 */
	@Override
	public OuterBranchEntity queryOuterBranchByBranchCode(String branchCode,
			String branchType) {
		// TODO Auto-generated method stub
		return iVehicleAgencyDeptService.queryOuterBranchByBranchCode(branchCode,branchType);
	}
	
	
	//wutao end ..
	/**
	 * 根据客户编码、时间查询客户当前时间内的客户优惠信息
	 * @author YANGKANG
	 * @param customerCode
	 * @param date
	 * @param productCode
	 * @return
	 */
	@Override
	public PreferentialInfoDto queryPreferentialInfo(String customerCode,Date date,String productCode){
		return preferentialService.queryPreferentialInfo(customerCode, date,productCode);
	}

	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.CarloadPricePlanDao.selectCarloadPricePlanDetailByOrganizationCode
	 * @Description:通过组织编码查询当前版本的整车价格波动参数方案
	 *
	 * @param carloadPricePlanDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午3:29:29
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-15    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(
			CarloadPricePlanDto carloadPricePlanDto) {
		return carloadPriceService.selectCarloadPricePlanDetailByOrganizationCode(carloadPricePlanDto);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService.queryOrgAdministrativeInfoEntityAllUpNOCache
	 * @Description:通过部门编码查询本部门和所以的上级部门信息
	 *
	 * @param code
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-17 下午6:14:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-17    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpNOCache(
			String code){
		return orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpNOCache(code);
	}
	
	@Override
	public List<BargainAppOrgEntity> queryAppOrgByBargainCrmId(BigDecimal crmId,List<String> unifiedCodeList) {
		// TODO Auto-generated method stub
		return bargainAppOrgService.queryAppOrgByBargainCrmId(crmId,unifiedCodeList);
	}
	
	@Override
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(
			String unifiedCode) {
		// TODO Auto-generated method stub
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
	}
	@Override
	public PreferentialInfoDto queryPreferentialInfoForBubbleRate(
			String customerCode, Date date, String code) {
		// TODO Auto-generated method stub
		return  preferentialService.queryPreferentialInfo(customerCode, date, code);
	}

	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService.queryExhibitionKeywordListByTargetOrgName
	 * @Description:根据收货地址匹配展馆关键字信息
	 *
	 * @param condition
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-DONGJIALING
	 * @date:2014-12-16 下午4:19:19
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-12-16    DP-FOSS-DONGJIALING      v1.0.0         create
	 */
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(
			String condition) {
		// TODO Auto-generated method stub
		return exhibitionKeywordService.queryExhibitionKeywordListByTargetOrgName(condition);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService.queryExhibitionKeyword
	 * @Description:根据收货地址匹配展馆详细地址
	 *
	 * @param exhibitionKeyword
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-DONGJIALING
	 * @date:2014-12-16 下午4:19:19
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-12-16    DP-FOSS-DONGJIALING      v1.0.0         create
	 */
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeyword(
			ExhibitionKeywordEntity exhibitionKeyword) {
		// TODO Auto-generated method stub
		return exhibitionKeywordService.queryExhibitionKeyword(exhibitionKeyword);
	}
	@Override
	public  List<CusLtDiscountItemDto> queryCusLtdiscountafterByCondition(CusLtdiscountafterEntity entity) {
		return ltDiscountBackInfoService.queryCusLtdiscountafterByCondition(entity);
	}
}
