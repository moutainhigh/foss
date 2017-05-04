package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;

public class AgencyCompanyOrDeptExpressVo {
	// 快递代理公司信息 集合
		private List<BusinessPartnerExpressEntity> businessPartnerExpressEntityList;
		// 快递代理公司信息
		private BusinessPartnerExpressEntity businessPartnerExpressEntity;

		// 快递代理网点信息 集合
		private List<OuterBranchExpressEntity> outerBranchExpressEntityList;
		// 快递代理网点信息
		private OuterBranchExpressEntity outerBranchExpressEntity;
		
		// 快递代理公司信息 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
		private String codeStr;
		// 返回前台的INT类型属性
		private int returnInt;
		
		// 增值服务
		private String valueAddedServices;
		// 承运业务
		private String carrierBusiness;

		
		public List<BusinessPartnerExpressEntity> getBusinessPartnerExpressEntityList() {
			return businessPartnerExpressEntityList;
		}
		public void setBusinessPartnerExpressEntityList(
				List<BusinessPartnerExpressEntity> businessPartnerExpressEntityList) {
			this.businessPartnerExpressEntityList = businessPartnerExpressEntityList;
		}
		public BusinessPartnerExpressEntity getBusinessPartnerExpressEntity() {
			return businessPartnerExpressEntity;
		}
		public void setBusinessPartnerExpressEntity(
				BusinessPartnerExpressEntity businessPartnerExpressEntity) {
			this.businessPartnerExpressEntity = businessPartnerExpressEntity;
		}
		public List<OuterBranchExpressEntity> getOuterBranchExpressEntityList() {
			return outerBranchExpressEntityList;
		}
		public void setOuterBranchExpressEntityList(
				List<OuterBranchExpressEntity> outerBranchExpressEntityList) {
			this.outerBranchExpressEntityList = outerBranchExpressEntityList;
		}
		public OuterBranchExpressEntity getOuterBranchExpressEntity() {
			return outerBranchExpressEntity;
		}
		public void setOuterBranchExpressEntity(
				OuterBranchExpressEntity outerBranchExpressEntity) {
			this.outerBranchExpressEntity = outerBranchExpressEntity;
		}
		public String getCodeStr() {
			return codeStr;
		}
		public void setCodeStr(String codeStr) {
			this.codeStr = codeStr;
		}
		public int getReturnInt() {
			return returnInt;
		}
		public void setReturnInt(int returnInt) {
			this.returnInt = returnInt;
		}
		public String getValueAddedServices() {
			return valueAddedServices;
		}
		public void setValueAddedServices(String valueAddedServices) {
			this.valueAddedServices = valueAddedServices;
		}
		public String getCarrierBusiness() {
			return carrierBusiness;
		}
		public void setCarrierBusiness(String carrierBusiness) {
			this.carrierBusiness = carrierBusiness;
		}
}
