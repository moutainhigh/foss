/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.hessian
 * FILE    NAME: IBaseDataHessianRemoting.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IHessianService;
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
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;


/**
 * 查询基础信息远程接口服务类
 * @author 026123-foss-lifengteng
 * @date 2013-8-1 下午8:50:18
 */
public interface IBaseDataHessianRemoting extends IHessianService {

	/**
	 * 根据部门名称查询部门信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-1 下午8:56:51
	 */
	List<OrgAdministrativeInfoEntity> queryOrgInfoByName(String name);

	/**
	 * 根据营业部名称查询营业部信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午2:38:08
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByEntity(SaleDepartmentEntity entity);

	/**
	 * 根据产品编码与营业日期来筛选产品
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-26 下午3:44:55
	 */
	ProductEntity getProductByCache(String productCode, Date billDate);

	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author Rosanu
	 * @date 2013-10-27 下午6:33:35
	 */
	public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String code);

	/** 
	 * 根据营业部查询其所属全部开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-30 下午9:39:04
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IBaseDataHessianRemoting#querySalesListByBillingGroupCode(java.lang.String)
	 */
	List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code);
	
	/**
	 * 根据标杆编码查询组织
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-12 16:44:46
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCode(String unifiedCode);
	/**
	 * 开箱验货接口，验证是否进行开箱验货
	 * @param entity
	 * @param regionCode
	 * @param regionLevCode
	 * @return
	 */
	BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(OrgAdministrativeInfoEntity entity,String regionCode,String regionLevCode);
	
	/**
	 * 开箱验货
	 * @param branchCode
	 * @param branchType
	 * @return
	 */
	OuterBranchEntity queryOuterBranchByBranchCode(String branchCode,  String branchType);
	
	/**
	 * 根据客户编码、时间查询客户当前时间内的客户优惠信息
	 * @author YANGKANG
	 * @param customerCode
	 * @param date
	 * @param productCode
	 * @return
	 */
	PreferentialInfoDto queryPreferentialInfo(String customerCode,Date date,String productCode);

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
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(
			CarloadPricePlanDto carloadPricePlanDto);
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
	public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpNOCache(
			String code);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.waybill.shared.hessian.IBaseDataHessianRemoting.queryExhibitionKeywordListByTargetOrgName
	 * @Description:根据收货地址匹配展馆关键字信息
	 *
	 * @param condition
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-DONGJIALING
	 * @date:2014-12-16 下午4:13:19
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-12-16    DP-FOSS-DONGJIALING      v1.0.0         create
	 */
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(
			String condition);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.waybill.shared.hessian.IBaseDataHessianRemoting.queryExhibitionKeyword
	 * @Description:根据收货地址匹配展馆详细地址
	 *
	 * @param exhibitionKeyword
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-DONGJIALING
	 * @date:2014-12-16 下午4:14:17
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-12-16    DP-FOSS-DONGJIALING      v1.0.0         create
	 */
	public List<ExhibitionKeywordEntity> queryExhibitionKeyword(
			ExhibitionKeywordEntity exhibitionKeyword);

	
	/**
	 * @author 200945
	 * @param crmId
	 * @return
	 */
	public List<BargainAppOrgEntity> queryAppOrgByBargainCrmId(BigDecimal crmId,List<String> unifiedCodeList);
	/**
	 * @author 200945
	 * @param unifiedCode
	 * @return
	 */
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(String unifiedCode);
	/**
	 * @author 200945
	 * @param customerCode
	 * @param date
	 * @param code
	 * @return
	 */
	public PreferentialInfoDto queryPreferentialInfoForBubbleRate(String customerCode,Date date, String code);

	public List<CusLtDiscountItemDto> queryCusLtdiscountafterByCondition(
			CusLtdiscountafterEntity entity);


}
