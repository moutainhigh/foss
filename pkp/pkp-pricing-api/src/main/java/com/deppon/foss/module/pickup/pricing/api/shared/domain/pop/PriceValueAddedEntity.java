package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;

public class PriceValueAddedEntity extends BaseEntity {

	/**
	 * @fields serialVersionUID
	 */
	private List<PriceValueAddedDetailEntity> valueAddedDetailList;
	/**
	 * 产品列表
	 */
	private List<PriceProductEntity> productList;
	/**
	 * 行业列表
	 */
	private List<PriceIndustryEntity> industryList;
	/**
	 * 行业列表
	 */
	private List<CustomerIndustryEntity> customerIndustryEntityList;
	private static final long serialVersionUID = -9199633293513905632L;

	/**
     * 
     */
	private static final String YES = "是";

	/**
     * 
     */
	private static final String NO = "否";

	/**
	 * 是否当前版本.
	 */
	private String currentUsedVersion;
	/**
	 * 是否激活.
	 */
	private String active;

	/**
	 * 有效起始时间.
	 */
	private Date beginTime;

	/**
	 * 有效截止时间.
	 */
	private Date endTime;
	/**
	 * 最后修改人姓名.
	 */
	private String modifyUserName;

	/**
	 * 创建人姓名.
	 */
	private String createUserName;

	/**
	 * 最后修改人所在部门.
	 */
	private String modifyOrgCode;

	/**
	 * 创建人所在部门.
	 */
	private String createOrgCode;
	/**
	 * 操作类型：仅限新增和升级版本使用
	 */
	private String operateTypeForAddAndUpdateVersion;
	/**
	 * 增值服务方案名称
	 */
	private String name;
	/**
	 * 增值服务方案编码
	 */
	private String code;
	/**
	 * 增值服务类型
	 */
	private String type;
	/**
	 * 业务发生时间
	 */
	private String businessBeginTime;
	/**
	 * 业务结束时间
	 */
	private String businessEndTime;
	/**
	 * 增值服务编码
	 */
	private String pricingEntryCode;
	/**
	 * 出发区域id
	 */
	private String deptRegionId;
	/**
	 * 到达区域id
	 */
	private String arrvRegionId;
	/**
	 * 出发区域id
	 */
	private String deptRegionName;
	/**
	 * 到达区域id
	 */
	private String arrvRegionName;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 版本
	 */
	private long versionNo;
	/**
	 * 币种.
	 */
	private String currencyCode;
	private String subType;

	private List<String> valueAddedIds;

	/**
	 * 获取 是否当前版本.
	 *
	 * @return the 是否当前版本
	 */
	public String getCurrentUsedVersion() {
		Date now = new Date();
		if (this.getBeginTime() != null && this.getEndTime() != null) {
			if (now.compareTo(this.getBeginTime()) >= 0 && now.compareTo(this.getEndTime()) < 0) {
				currentUsedVersion = YES;
				return currentUsedVersion;
			} else {
				currentUsedVersion = NO;
				return currentUsedVersion;
			}
		} else {
			return "";
		}

	}

	/**
	 * 设置 是否当前版本.
	 *
	 * @param currentUsedVersion
	 *            the new 是否当前版本
	 */
	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getOperateTypeForAddAndUpdateVersion() {
		return operateTypeForAddAndUpdateVersion;
	}

	public void setOperateTypeForAddAndUpdateVersion(String operateTypeForAddAndUpdateVersion) {
		this.operateTypeForAddAndUpdateVersion = operateTypeForAddAndUpdateVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * <p>
	 * Description:businessBeginTime<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 */
	public String getBusinessBeginTime() {
		return businessBeginTime;
	}

	/**
	 * <p>
	 * Description:businessBeginTime<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 */
	public void setBusinessBeginTime(String businessBeginTime) {
		this.businessBeginTime = businessBeginTime;
	}

	/**
	 * <p>
	 * Description:businessEndTime<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 */
	public String getBusinessEndTime() {
		return businessEndTime;
	}

	/**
	 * <p>
	 * Description:businessEndTime<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 */
	public void setBusinessEndTime(String businessEndTime) {
		this.businessEndTime = businessEndTime;
	}

	public String getDeptRegionId() {
		return deptRegionId;
	}

	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	public String getArrvRegionId() {
		return arrvRegionId;
	}

	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}

	public List<PriceValueAddedDetailEntity> getValueAddedDetailList() {
		return valueAddedDetailList;
	}

	public void setValueAddedDetailList(List<PriceValueAddedDetailEntity> valueAddedDetailList) {
		this.valueAddedDetailList = valueAddedDetailList;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * <p>
	 * Description:productList<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceProductEntity> getProductList() {
		return productList;
	}

	/**
	 * <p>
	 * Description:productList<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setProductList(List<PriceProductEntity> productList) {
		this.productList = productList;
	}

	/**
	 * <p>
	 * Description:industryList<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceIndustryEntity> getIndustryList() {
		return industryList;
	}

	/**
	 * <p>
	 * Description:industryList<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setIndustryList(List<PriceIndustryEntity> industryList) {
		this.industryList = industryList;
	}

	/**
	 * <p>
	 * Description:subType<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * <p>
	 * Description:subType<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * <p>
	 * Description:deptRegionName<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}

	/**
	 * <p>
	 * Description:deptRegionName<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

	/**
	 * <p>
	 * Description:arrvRegionName<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * <p>
	 * Description:arrvRegionName<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * <p>
	 * Description:valueAddedIds<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public List<String> getValueAddedIds() {
		return valueAddedIds;
	}

	/**
	 * <p>
	 * Description:valueAddedIds<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setValueAddedIds(List<String> valueAddedIds) {
		this.valueAddedIds = valueAddedIds;
	}

	/**
	 * <p>
	 * Description:pricingEntryCode<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	/**
	 * <p>
	 * Description:pricingEntryCode<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	/**
	 * <p>
	 * Description:remark<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月27日
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * <p>
	 * Description:remark<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月27日
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CustomerIndustryEntity> getCustomerIndustryEntityList() {
		return customerIndustryEntityList;
	}

	public void setCustomerIndustryEntityList(
			List<CustomerIndustryEntity> customerIndustryEntityList) {
		this.customerIndustryEntityList = customerIndustryEntityList;
	}

}