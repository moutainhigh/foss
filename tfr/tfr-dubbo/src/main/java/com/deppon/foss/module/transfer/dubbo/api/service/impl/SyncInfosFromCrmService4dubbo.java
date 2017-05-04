package com.deppon.foss.module.transfer.dubbo.api.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.crm.api.define.exception.TfrBusinessException;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CrmSyncDataEntity;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustAccount;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustContract;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustContractTax;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustContractdept;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustCustbasedata;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustCustlinkman;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustPreferenceaddress;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustPreferential;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustShuttleaddress;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContractTaxService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.transfer.dubbo.api.service.ISyncInfosFromCrmService4dubbo;

public class SyncInfosFromCrmService4dubbo implements ISyncInfosFromCrmService4dubbo {
	private static final String N = "N";
	private static final String Y = "Y";
	private static final String _0 = "0";
	private static final String USER_CODE = "CRM";
	public static final String CUSTOMER_SC_CUSTOMER = "SC_CUSTOMER";

	private final static Logger LOGGER = LogManager.getLogger(SyncInfosFromCrmService4dubbo.class);
	private ICustomerService customerService;
	private ICusAccountService cusAccountService;
	private ICusBargainService cusBargainService;
	private ICusContactService cusContactService;
	private ICusAddressService cusAddressService;
	private IContactAddressService contactAddressService;
	private IBargainAppOrgService bargainAppOrgService;
	private IPreferentialService preferentialService;
	private ICusContractTaxService cusContractTaxService;

	class SyncResponse {
		Boolean _return;

		public Boolean isReturn() {
			return _return;
		}

		public void setReturn(Boolean value) {
			this._return = value;
		}

	}

	@Override
	public boolean syncInfo(CrmSyncDataEntity crmSyncRequest) {
		LOGGER.info("同步客户信息开始.......");

		SyncResponse response = new SyncResponse();
		// 078816_wp_2014-03-20
		// 客户类别
		String customerNature = "";
		// 客户fossId
		String custFossId = "";
		// 散客主键ID
		// String convertid = "";
		if (null == crmSyncRequest) {
			return false;
		}
		// 获取客户基本信息
		CustCustbasedata custbasedata = crmSyncRequest.gettCustCustbasedata();
		if (null != custbasedata) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getCustomerService().queryCustomerByCrmId(custbasedata.getFid());

			LOGGER.info("客户信息CRM_ID :" + custbasedata.getFid());
			CustomerEntity entity = convertCustomer(custbasedata);
			if (StringUtils.isEmpty(custbasedata.getCustGroup())) {
				throw new TfrBusinessException("客户类别为空！");
			}

			try {
				boolean isSaveOrUpdateCustomer = true;
				if (flag || StringUtils.equals(custbasedata.getCustGroup(), CUSTOMER_SC_CUSTOMER)) {
					if (!flag) {
						// 078816_wp_2014-03-20
						customerNature = custbasedata.getCustGroup();
						custFossId = custbasedata.getFossId();

					}
					isSaveOrUpdateCustomer = saveOrUpdateCustomer(response, custbasedata, entity);
				} else {
					isSaveOrUpdateCustomer = saveOrUpdateCustomer(response, entity);
				}
				if (!isSaveOrUpdateCustomer) {
					LOGGER.info("保存客户信息失败！");
					return false;
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new TfrBusinessException(e.getMessage(), e);
			}
		}
		// 客户开户银行信息列表
		List<CustAccount> custAccounts = crmSyncRequest.gettCustAccount();
		try {
			boolean isSaveOrUpdatecustAccounts = true;
			if (CollectionUtils.isNotEmpty(custAccounts)) {

				isSaveOrUpdatecustAccounts = saveOrUpdateCustAccount(response, custAccounts);
			}
			if (!isSaveOrUpdatecustAccounts) {
				LOGGER.info("新增或修改客户开户银行信息失败！");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new TfrBusinessException(e.getMessage(), e);
		}
		// 客户合同信息
		List<CustContract> contracts = crmSyncRequest.gettCustContract();
		boolean isSaveOrUpdateCustContract = true;
		if (CollectionUtils.isNotEmpty(contracts)) {

			isSaveOrUpdateCustContract = saveOrUpdateCustContract(response, contracts, isSaveOrUpdateCustContract);
		}
		if (!isSaveOrUpdateCustContract) {
			LOGGER.info("新增或修改客户合同信息失败！");
			return false;
		}
		// 客户联系人信息
		List<CustCustlinkman> linkmanList = crmSyncRequest.gettCustCustlinkman();
		boolean isSaveOrUpdateCustCustlinkman = true;
		if (CollectionUtils.isNotEmpty(linkmanList)) {
			isSaveOrUpdateCustCustlinkman = saveOrUpdateCustCustlinkman(response, customerNature, custFossId,
					linkmanList);
		}
		if (!isSaveOrUpdateCustCustlinkman) {
			LOGGER.info("新增或修改客户联系人信息失败！");
			return false;
		}

		// 接送货地址信息
		List<CustShuttleaddress> addressList = crmSyncRequest.gettCustShuttleaddress();
		boolean isSaveOrUpdateCustShuttleaddress = true;
		if (CollectionUtils.isNotEmpty(addressList)) {
			isSaveOrUpdateCustShuttleaddress = saveOrUpdateCustShuttleaddress(response, customerNature, custFossId,
					isSaveOrUpdateCustCustlinkman, addressList);
		}
		if (!isSaveOrUpdateCustShuttleaddress) {
			LOGGER.info("新增或修改客户接送货地址信息失败！");
			return false;
		}
		// 联系人-地址关系信息
		List<CustPreferenceaddress> contactAddressList = crmSyncRequest.gettCustPreferenceaddress();
		boolean isSaveOrUpdateCustPreferenceaddress = true;
		if (CollectionUtils.isNotEmpty(contactAddressList)) {
			isSaveOrUpdateCustPreferenceaddress = saveOrUpdateCustPreferenceaddress(response, contactAddressList);
		}
		if (!isSaveOrUpdateCustPreferenceaddress) {
			LOGGER.info("新增或修改客户联系人-地址关系信息失败！");
			return false;
		}
		// 合同-部门关系信息
		List<CustContractdept> contractDeptList = crmSyncRequest.gettCustContractdept();
		boolean isSaveOrUpdateCustContractdept = true;
		if (CollectionUtils.isNotEmpty(contractDeptList)) {
			isSaveOrUpdateCustContractdept = saveOrUpdateCustContractdept(response, contractDeptList);
		}
		if (!isSaveOrUpdateCustContractdept) {
			LOGGER.info("新增或修改客户合同-部门关系信息失败！");
			return false;
		}
		// 客户优惠信息
		List<CustPreferential> preferentialList = crmSyncRequest.gettCustPreferential();
		boolean isSaveOrUpdateCustPreferential = true;
		if (CollectionUtils.isNotEmpty(preferentialList)) {
			isSaveOrUpdateCustPreferential = saveOrUpdateCustPreferential(response, preferentialList);
		}
		if (!isSaveOrUpdateCustPreferential) {
			LOGGER.info("新增或修改客户优惠信息失败！");
			return false;
		}
		// 客户发票标记信息
		List<CustContractTax> cusContractTaxList = crmSyncRequest.gettCustContractTax();
		boolean isSaveOrUpdateCustContractTax = true;
		if (CollectionUtils.isNotEmpty(cusContractTaxList)) {
			isSaveOrUpdateCustContractTax = saveOrUpdateCustContractTax(response, cusContractTaxList);
		}
		if (!isSaveOrUpdateCustContractTax) {
			LOGGER.info("新增或修改客户发票标记信息失败！");
			return false;
		}
		LOGGER.info("同步客户主信息结束.......");
		return true;

	}

	private boolean saveOrUpdateCustContractTax(SyncResponse response, List<CustContractTax> cusContractTaxList) {
		for (CustContractTax contractTax : cusContractTaxList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getCusContractTaxService().queryCusContractTaxByCrmId(contractTax.getFid(), null);
			LOGGER.info("客户发票标记信息CRM_ID :" + contractTax.getFid());
			// 信息转换封装
			CusContractTaxEntity entity = convertCusContractTax(contractTax);
			if (flag) {
				try {
					int result = getCusContractTaxService().updateCusContractTax(entity);
					if (result > 0) {
						LOGGER.info("修改客户发票标记信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {
				try {
					int result = getCusContractTaxService().addCusContractTax(entity);
					if (result > 0) {
						LOGGER.info("新增客户发票标记信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private CusContractTaxEntity convertCusContractTax(CustContractTax obj) {
		// 创建客户发票标记信息对象
		CusContractTaxEntity entity = new CusContractTaxEntity();
		// 发票标记信息对应的合同ID
		entity.setBargainCrmId(obj.getFcontractid());
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 发票标记开始使用时间
		entity.setBeginTime(obj.getFbegintime());
		// 发票标记结束使用时间
		entity.setEndTime(obj.getFendtime());
		// 签署合同公司
		entity.setSignCompany(obj.getSignCompany());
		// 发票标记
		entity.setInvoiceType(obj.getFinvoiceType());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);
		return entity;
	}

	private boolean saveOrUpdateCustPreferential(SyncResponse response, List<CustPreferential> preferentialList) {
		for (CustPreferential preferential : preferentialList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getPreferentialService().queryPreferentialByCrmId(preferential.getFid(), null);
			LOGGER.info("客户优惠信息CRM_ID :" + preferential.getFid());
			// 信息转换封装
			PreferentialEntity entity = convertPreferential(preferential);
			if (flag) {
				try {
					int result = getPreferentialService().updatePreferential(entity);
					if (result > 0) {
						LOGGER.info("修改客户优惠信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {
				try {
					int result = getPreferentialService().addPreferential(entity);
					if (result > 0) {
						LOGGER.info("新增客户优惠信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private PreferentialEntity convertPreferential(CustPreferential obj) {
		// 创建客户优惠信息对象
		PreferentialEntity entity = new PreferentialEntity();
		/**
		 * @author 218392 zhangyongxue 进仓派送费折扣
		 */
		entity.setIntoHouseDeliverDiscount(obj.getFaccessDepotRate());
		// 运费折扣费率
		entity.setChargeRebate(obj.getFchargerebate());
		// 代收货款费率
		entity.setAgentGathRate(obj.getFagentgathrate());
		// 保价费率
		entity.setInsureDpriceRate(obj.getFinsuredpricerate());
		// 接货费率
		entity.setReceivePriceRate(obj.getFreceivepricerate());
		// 送货费率
		entity.setDeliveryFeeRate(obj.getFdeliverypricerate());
		// 优惠信息对应的合同ID
		entity.setCusBargainId(obj.getFcontractid());
		// --------------CRM信息缺少是否启用
		// entity.setActive();
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 设置 执行起始日期.
		entity.setEffectiveDate(obj.getEffectiveDate());
		// 设置 执行到期日期.
		entity.setExpirationDate(obj.getExpirationDate());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);
		// 优惠所属类型(取值LTT或者EXPRESS,分别表示零担和快递)
		entity.setFtype(obj.getFtype());
		// 超重货操作费折扣
		entity.setOverweightOperatDiscount(obj.getFoverWeightRate());
		// 包装费折扣
		entity.setPackingChargesDiscount(obj.getFpackingRate());
		// 签收单返单费折扣
		entity.setSingleSignDiscount(obj.getFsignBillRate());
		// 代收最低手续费
		entity.setLowestCharge(obj.getFlowestCharge());
		// 重泡比值
		entity.setWeightBubbleRate(obj.getFexpweightbubblerate());
		// 大件上楼费折扣
		entity.setBigUprate(obj.getFbiguprate());
		// 续重最低费率
		entity.setContinueHeavyLowestRate(obj.getConHeavyLowestRate());
		// 续重折扣
		entity.setContinueHeavyDiscount(obj.getConHeavyDisCount());
		// 快递返货保价折扣类型(原折扣 OLD_PREFE 新折扣 NEW_PREFE 单票固定价 FIXED_PRICE 原价
		// OLD_PRICE)
		entity.setExpBackCollPreType(obj.getExpBackCollPreType());
		// 快递返货运费折扣类型(原折扣 OLD_PREFE 新折扣 NEW_PREFE 单票固定价 FIXED_PRICE 原价
		// OLD_PRICE)
		entity.setExpBackFreghtType(obj.getExpBackPreType());
		// 返货运费固定值
		entity.setBackFreghtFixed(obj.getCarriageFixed());
		// 返货保价固定值
		entity.setBackCollFixed(obj.getCollFixed());
		// 单票代收手续费
		entity.setSinTicketCollCharge(obj.getOneTickCollCharge());
		// 单票保价手续费
		entity.setSinTicketSurePriceCharge(obj.getEnsuredPriceCharge());
		// 单票包装费
		entity.setSinTicketPackCharge(obj.getOneTickPackCharge());
		// 是否精准包裹1 Y， 0 N
		if (obj.getIfAccurateElect() != null && "1".equals(obj.getIfAccurateElect())) {
			entity.setIsAccuratePackage(Y);
		} else {
			entity.setIsAccuratePackage(N);
		}
		// 零担重泡比参数
		entity.setLttWeightBubbleRate(obj.getLttWeightBubbleRate());
		// 是否代收退费 true 是 false 否
		if (obj.isCollReturnCharge()) {
			entity.setCollReturnCharge(Y);
		} else {
			entity.setCollReturnCharge(N);
		}
		return entity;
	}

	private boolean saveOrUpdateCustContractdept(SyncResponse response, List<CustContractdept> contractDeptList) {
		for (CustContractdept contractdept : contractDeptList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getBargainAppOrgService().queryBargainAppOrgByCrmId(contractdept.getFid(), null);
			LOGGER.info("合同-部门关系信息CRM_ID :" + contractdept.getFid());
			// 信息转换封装
			BargainAppOrgEntity entity = convertBargainAppOrg(contractdept);
			if (flag) {
				try {
					int result = getBargainAppOrgService().updateBargainAppOrg(entity);
					if (result > 0) {
						LOGGER.info("修改客户合同-部门关系信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {
				try {
					int result = getBargainAppOrgService().addBargainAppOrg(entity);
					if (result > 0) {
						LOGGER.info("新增客户合同-部门关系信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private BargainAppOrgEntity convertBargainAppOrg(CustContractdept obj) {
		// 创建一个客户合同适用部门实体
		BargainAppOrgEntity entity = new BargainAppOrgEntity();
		//sonar-352203-if-else内容相同
/*		if (StringUtil.isNotBlank(obj.getFdeptidStandardcode())) {
			// 对应部门标杆编码
			entity.setUnifiedCode(obj.getFdeptidStandardcode());
		} else {
			// 对应部门标杆编码
			entity.setUnifiedCode(obj.getFdeptidStandardcode());
		}*/
		entity.setUnifiedCode(obj.getFdeptidStandardcode());

		// 开始时间
		entity.setBeginTime(obj.getFbegintime());
		// 结束时间
		entity.setEndTime(obj.getFendtime());
		// 工作流号
		entity.setWorkflowNo(obj.getFworkflowid());
		// 审批人
		entity.setApprover(obj.getFapprovalman());
		// 是否归属部门---------需要查清状态类型进行转换
		entity.setOrgBelonging(obj.getFisdept());
		// CRM--状态 1正常，0已作废
		if (StringUtil.equals(obj.getFstate(), "0")) {// CRM
			// CRM--状态 1正常，0已作废
			entity.setActive(N);
		} else if (StringUtil.equals(obj.getFstate(), "1")) {
			// CRM--状态 1正常，0已作废
			entity.setActive(Y);
		} else {
			LOGGER.info("客户合同适用部门的状态有问题啊.........." + obj.getFstate());
			throw new TfrBusinessException("客户合同适用部门的状态有问题啊.........." + obj.getFstate());
		}
		// 合同ID
		entity.setBargainId(obj.getFcontractid());
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);

		return entity;
	}

	private boolean saveOrUpdateCustPreferenceaddress(SyncResponse response,
			List<CustPreferenceaddress> contactAddressList) {
		for (CustPreferenceaddress preferenceAddress : contactAddressList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getContactAddressService().queryContactAddressByCrmId(preferenceAddress.getFid(), null);
			LOGGER.info("联系人-地址关系信息CRM_ID :" + preferenceAddress.getFid());
			// 信息转换封装
			ContactAddressEntity entity = convertContactAddress(preferenceAddress);
			if (flag) {
				try {
					int result = getContactAddressService().updateContactAddress(entity);
					if (result > 0) {
						LOGGER.info("修改联系人-地址关系信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {
				try {
					int result = getContactAddressService().addContactAddress(entity);
					if (result > 0) {
						LOGGER.info("新增联系人-地址关系信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private ContactAddressEntity convertContactAddress(CustPreferenceaddress obj) {
		// 创建实体
		ContactAddressEntity entity = new ContactAddressEntity();
		// 接送货地址ID
		entity.setServiceAddressId(obj.getFshuttleaddressid());
		// 联系人ID
		entity.setContact(obj.getFlinkmanid());
		// 地址类型----------需要类型编码转换
		entity.setAddressType(obj.getFaddresstype());
		// 地址
		entity.setContactAddress(obj.getFaddress());
		// 发票要求
		entity.setBillDemand(obj.getFbillrequest());
		// 付款方式----------需要类型编码转换
		entity.setChargeType(obj.getFpaytype());
		// 其他要求
		entity.setOtherDemand(obj.getFotherneed());
		// 是否有停车费用---------需要类型编码转换
		if (StringUtil.equals(_0, obj.getFhasstopcost())) {
			// 是否有停车费用
			entity.setParkingFee(N);
		} else {
			// 是否有停车费用
			entity.setParkingFee(Y);
		}
		// 是：1 否：0
		if (StringUtil.equals("0", obj.getFismainaddress())) {
			// 是否主地址
			entity.setMainAddress(N);
		} else {
			// 是否主地址
			entity.setMainAddress(Y);
		}
		// 状态---------与Active重复
		// entity.setStatus(status);
		// 偏好起始时间
		entity.setBeginTime(obj.getFstarttime());
		// 偏好结束时间
		entity.setEndTime(obj.getFendtime());
		// 是：1 否：0
		if (StringUtil.equals("0", obj.getFissendtofloor())) {
			// 是否送货上楼
			entity.setDeliveryUpstairs(N);
		} else {
			// 是否送货上楼
			entity.setDeliveryUpstairs(Y);
		}
		// 0 为有效 2为无效
		if (StringUtil.equals(String.valueOf(obj.getFstatus()), "0")) {
			// 为有效
			entity.setActive(Y);
		} else if (StringUtil.equals(String.valueOf(obj.getFstatus()), "2")) {
			// 0 为有效 2为无效
			entity.setActive(N);
		} else {
			LOGGER.info("联系人-接送货地址信息的状态有问题啊.........." + obj.getFstatus());
			throw new TfrBusinessException("联系人-接送货地址信息的状态有问题啊.........." + obj.getFstatus());
		}
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);

		return entity;
	}

	private boolean saveOrUpdateCustShuttleaddress(SyncResponse response, String customerNature, String custFossId,
			boolean isSaveOrUpdateCustCustlinkman, List<CustShuttleaddress> addressList) {
		for (CustShuttleaddress address : addressList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getCusAddressService().queryCusAddressByCrmId(address.getFid(), null);
			LOGGER.info("接送货地址信息CRM_ID :" + address.getFid());
			// 信息转换封装
			CusAddressEntity entity = convertCusAddress(address);
			if (flag || StringUtils.equals(customerNature, CUSTOMER_SC_CUSTOMER)) {
				try {
					if (!flag) {
						// 078816_wp_2014-03-20
						entity.setCustomserNature(customerNature);
						entity.setOwnCustId(custFossId);

					}
					int result = getCusAddressService().updateCusAddress(entity);
					if (result > 0) {
						LOGGER.info("修改客户接送货地址信息成功！");
					} // wp_078816_20140818
					else if (result == 0) {
						getCusAddressService().addCusAddress(entity);
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {
				try {

					int result = getCusAddressService().addCusAddress(entity);
					if (result > 0) {
						LOGGER.info("新增客户接送货地址信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}

		return true;
	}

	private CusAddressEntity convertCusAddress(CustShuttleaddress obj) {
		// 创建客户接送货地址对象
		CusAddressEntity entity = new CusAddressEntity();
		// 客户ID
		entity.setCustomerCode(obj.getFmemberid());
		// 详细地址
		entity.setAddress(obj.getFaddress());
		// 邮编
		entity.setZipCode(obj.getFpostcode());
		// 省份
		entity.setProvinceName(obj.getFprovince());
		// 城市
		entity.setCityCode(obj.getFcity());
		// 区县
		entity.setCountyCode(obj.getFarea());
		// 地址类型(RECEIVE_GOODS 接货地址 SEND_GOODS 发货地址 RECEIVE_SEND_GOODS
		// 接送货地址)---------需要类型编码转换
		entity.setAddressType(obj.getFaddresstype());
		// CRM 0为有效
		// 2为无效
		if (StringUtil.equals(String.valueOf(obj.getFstatus()), "0")) {
			// CRM 0为有效
			// 2为无效
			entity.setActive(Y);
		} else if (StringUtil.equals(String.valueOf(obj.getFstatus()), "2")) {
			// CRM 0为有效
			// 2为无效
			entity.setActive(N);
		} else {
			LOGGER.info("客户接送货地址的状态有问题啊.........." + obj.getFstatus());
			throw new TfrBusinessException("客户接送货地址的状态有问题啊.........." + obj.getFstatus());
		}
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// 地址备注信息
		entity.setAddressRemark(obj.getFaddressRemark());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);

		return entity;
	}

	private boolean saveOrUpdateCustCustlinkman(SyncResponse response, String customerNature, String custFossId,
			List<CustCustlinkman> linkmanList) {
		for (CustCustlinkman linkman : linkmanList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getCusContactService().queryCusContactByCrmId(linkman.getFid(), null);
			LOGGER.info("客户联系人信息CRM_ID :" + linkman.getFid());
			// 信息转换封装
			ContactEntity entity = convertContact(linkman);
			if (flag || StringUtils.equals(customerNature, CUSTOMER_SC_CUSTOMER)) {// 修改
				try {
					if (!flag) {
						// 078816_wp_2014-03-20
						entity.setCustomserNature(customerNature);
						entity.setOwnCustId(custFossId);

					}
					int result = getCusContactService().updateCusContact(entity);
					if (result > 0) {
						LOGGER.info("修改客户联系人信息成功！");
					} // wp_078816_20140818
					else if (result == 0) {
						getCusContactService().addCusContact(entity);
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {// 新增
				try {
					int result = getCusContactService().addCusContact(entity);
					if (result > 0) {
						LOGGER.info("新增客户联系人信息成功！");
					} else {
						LOGGER.info("新增客户联系人信息失败！");
						response.setReturn(false);
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private ContactEntity convertContact(CustCustlinkman obj) {
		// 创建客户联系人实体
		ContactEntity entity = new ContactEntity();
		// 性别:男
		if (StringUtil.equals("MAN", obj.getFsex())) {
			// 性别:男
			entity.setGender("M");
		} else {
			// 性别:女
			entity.setGender("W");
		}

		// 办公电话
		entity.setContactPhone(obj.getFoffertel());
		// 移动电话
		entity.setMobilePhone(obj.getFmobiletel());
		// 传真
		entity.setFaxNo(obj.getFax());
		// 联系人地址
		entity.setAddress(obj.getFlinkmanaddress());
		// 电子邮箱
		entity.setEmail(obj.getFemail());
		// 生日
		entity.setBirthday(obj.getFborndate());
		// 身份证号
		entity.setIdCard(obj.getFidcard());
		// 个人爱好
		entity.setHobby(obj.getFpersonlove());
		// 获知公司途径
		entity.setWay(obj.getFgainave());
		// 民族
		entity.setNation(obj.getFfolk());
		// 籍贯
		entity.setHometown(obj.getFnativeplace());
		// 职务
		entity.setTitle(obj.getFduty());
		// 任职部门
		entity.setWorkingDept(obj.getFdutydept());
		// 联系人姓名
		entity.setContactName(obj.getFname());
		// 备注---------------------CRM中没有此字段
		// entity.setNotes(obj.get);
		// 联系人类型
		entity.setContactType(obj.getFlinkmantype());
		// 是否主联系人
		if (StringUtil.equals("0", obj.getFismainlinkman())) {
			// 是否主联系人
			entity.setMainContract(N);
		} else {
			// 是否主联系人
			entity.setMainContract(Y);
		}
		// CRM 0 为有效 2为无效
		if (StringUtil.equals(obj.getFstatus(), "0")) {
			// CRM 0 为有效 2为无效
			entity.setActive(Y);
		} else if (StringUtil.equals(obj.getFstatus(), "2")) {
			// CRM 0 为有效 2为无效
			entity.setActive(N);
		} else {
			LOGGER.info("客户联系人的状态有问题啊.........." + obj.getFstatus());
			throw new TfrBusinessException("客户联系人的状态有问题啊.........." + obj.getFstatus());
		}
		// 客户编码
		entity.setCustomerCode(obj.getFcustid());
		// 联系人编码
		entity.setCode(obj.getFnumber());
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);

		return entity;
	}

	private boolean saveOrUpdateCustContract(SyncResponse response, List<CustContract> contracts,
			boolean isSaveOrUpdateCustContract) {
		for (CustContract custContract : contracts) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getCusBargainService().queryCusBargainByCrmId(custContract.getFid(), null);
			LOGGER.info("客户合同信息CRM_ID :" + custContract.getFid());
			// 信息转换封装
			CusBargainEntity entity = convertCusBargain(custContract);
			if (flag) {// 如果存在修改
				try {
					int result = getCusBargainService().updateCusBargain(entity);
					if (result > 0) {
						LOGGER.info("修改客户合同信息成功！");
					} else {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else {
				try {
					int result = getCusBargainService().addCusBargain(entity);
					if (result > 0) {
						LOGGER.info("新增客户合同信息成功！");
					} else {
						LOGGER.info("新增客户合同信息失败！");
						response.setReturn(false);
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private CusBargainEntity convertCusBargain(CustContract obj) {
		// 创建客户合同信息
		CusBargainEntity entity = new CusBargainEntity();
		// 付款方式(MONTH_END 月结 NOT_MONTH_END 无)
		entity.setChargeType(obj.getFpayway());
		// 申请欠款额度
		entity.setArrearsAmount(obj.getFarrearamount());
		// 协议联系人姓名
		entity.setName(obj.getFlinkmanname());
		// 联系人固定电话
		entity.setContactPhone(obj.getFlinkmanphone());
		// 联系人手机
		entity.setMobilePhone(obj.getFlinkmanmobile());
		// 联系人详细地址
		entity.setAddress(obj.getFlinkmanaddress());
		// 对账日期
		entity.setStatementDate(obj.getFrecondate());
		// 开发票日期
		entity.setInvoicingDate(obj.getFinvoicdate());
		// 结款日期
		entity.setCheckoutDate(obj.getFresultdate());
		// 申请事由
		entity.setApplyReason(obj.getFapplication());
		if (StringUtil.isNotBlank(obj.getFdeptidStandardcode())) {
			// 创建部门标杆编码
			entity.setUnifiedCode(obj.getFdeptidStandardcode().trim());
		} else {
			// 创建部门标杆编码
			entity.setUnifiedCode(obj.getFdeptidStandardcode());
		}

		// 创建部门ID
		entity.setApplicateOrgId(obj.getFdeptid().toString());
		// 是否折扣
		if (StringUtil.equals("0", obj.getFiddiscount())) {
			// 是否折扣
			entity.setDiscount(N);
		} else {
			// 是否折扣
			entity.setDiscount(Y);
		}
		// 合同状态---------------与active字段重复
		// entity.setStatus(obj.getFcontractstatus());
		// 合同主体
		entity.setBargainSubject(obj.getFcontractsubject());
		// 注册资金
		entity.setRegisterFunds(obj.getFregicapital());
		if (StringUtil.isNotBlank(obj.getFbeforecontractnum())) {
			// 原合同编号
			entity.setLastBargain(obj.getFbeforecontractnum().trim());
		} else {
			// 原合同编号
			entity.setLastBargain(obj.getFbeforecontractnum());
		}
		if (StringUtil.isNotBlank(obj.getFcontractnum())) {
			// 合同编号
			entity.setBargainCode(obj.getFcontractnum().trim());
		} else {
			// 合同编号
			entity.setBargainCode(obj.getFcontractnum());
		}

		// 走货名称
		entity.setTransName(obj.getFgoodsname());
		// 客户全称
		entity.setCustomerName(obj.getFcustcompany());
		// 协议联系人
		entity.setLinkmanId(obj.getFcontactid().toString());
		// 结算方式 -----------------------多余字段
		// entity.setChargeMode(obj.get);
		// 优惠类型
		entity.setPreferentialType(obj.getFpreferentialtype());
		// 月结合同天数
		entity.setDebtDays(obj.getDebtDays());
		// 合同起始日期
		entity.setBeginTime(obj.getFcontractbegindate());
		// 合同到期日期
		entity.setEndTime(obj.getFcontractenddate());
		// 客户ID
		entity.setCustomerCode(obj.getFcustid());
		// CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
		if (StringUtil.equals(obj.getFcontractstatus(), "1")) {
			// CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
			entity.setActive(Y);
			// CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
		} else if (StringUtil.equals(obj.getFcontractstatus(), "2")) {
			// CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
			entity.setActive(N);
		} else {
			LOGGER.info("客户合同的状态有问题啊.........." + obj.getFcontractstatus());
			throw new TfrBusinessException("客户合同的状态有问题啊.........." + obj.getFcontractstatus());
		}
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);
		// 异地调货标志
		if (obj.getAsyntakegoodscode()) {
			entity.setAsyntakegoodsCode(Y);
		} else {
			entity.setAsyntakegoodsCode(N);
		}
		if (StringUtil.isNotBlank(obj.getHastenfunddeptcode())) {
			// 设置 催款部门标杆编码.
			entity.setHastenfunddeptCode(obj.getHastenfunddeptcode().trim());
		} else {
			// 设置 催款部门标杆编码.
			entity.setHastenfunddeptCode(obj.getHastenfunddeptcode());
		}
		entity.setPriceVersionDate(obj.getPriceVersionDate());

		// 快递结款方式
		entity.setExPayWay(obj.getExPayWay());
		// 快递优惠类型
		entity.setExPreferentialType(obj.getExPreferentialType());
		// 快递价格版本时间
		entity.setExPriceVersionDate(obj.getExPriceVersionDate());
		// 是否单独报价 true false
		if (obj.isfIsSeparateQuote()) {
			entity.setIsAloneQuotation(Y);
		} else {
			entity.setIsAloneQuotation(N);
		}
		if (obj.isIfElecEnjoy()) {
			entity.setIfElecEnjoy(Y);
		} else {
			entity.setIfElecEnjoy(N);
		}
		return entity;
	}

	private boolean saveOrUpdateCustAccount(SyncResponse response, List<CustAccount> custAccounts) {
		for (CustAccount custAccount : custAccounts) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = getCusAccountService().queryCusAccountByCrmId(custAccount.getFid(), null);
			LOGGER.info("客户开户银行信息CRM_ID :" + custAccount.getFid());
			// 信息转换封装
			CusAccountEntity entity = convertCusAccount(custAccount);
			if (flag) {// 如果存在修改

				int result = getCusAccountService().updateCusAccount(entity);
				if (result > 0) {
					LOGGER.info("修改客户开户银行信息成功！");
				} else {

					return false;
				}
			} else {// 新增
				int result = getCusAccountService().addCusAccount(entity);
				if (result > 0) {
					LOGGER.info("新增客户开户银行信息成功！");
				} else {
					return false;
				}
			}
		}
		return true;
	}

	private CusAccountEntity convertCusAccount(CustAccount obj) {
		// 创建一个对象
		CusAccountEntity entity = new CusAccountEntity();
		// 其他支行名称
		// entity.setOtherBranchBankName(obj.getFsubbankname());
		// 支行名称
		entity.setBranchBankName(obj.getFsubbankname());
		if (StringUtil.isNotBlank(obj.getFsubbanknameid())) {
			// 支行编码
			entity.setBranchBankCode(obj.getFsubbanknameid().trim());
		} else {
			// 支行编码
			entity.setBranchBankCode(obj.getFsubbanknameid());
		}

		// 开户账号
		entity.setAccountNo(obj.getFbankaccount());
		// 开户人姓名
		entity.setAccountName(obj.getFcountname());
		if (StringUtil.isNotBlank(obj.getFbankcityid())) {
			// 开户行城市编码
			entity.setCityCode(obj.getFbankcityid().trim());
		} else {
			// 开户行城市编码
			entity.setCityCode(obj.getFbankcityid());
		}
		if (StringUtil.isNotBlank(obj.getFbankprovinceid())) {
			// 开户行省份编码
			entity.setProvCode(obj.getFbankprovinceid().trim());
		} else {
			// 开户行省份编码
			entity.setProvCode(obj.getFbankprovinceid());
		}
		if (StringUtil.isNotBlank(obj.getFbankid())) {
			// 开户行编码
			entity.setBankCode(obj.getFbankid().trim());
		} else {
			// 开户行编码
			entity.setBankCode(obj.getFbankid());
		}

		// 开户行名称
		entity.setOpeningBankName(obj.getFbank());
		// 手机号码
		entity.setMobilePhone(obj.getFlinkmanmobile());
		// 账号与客户关系
		entity.setCustomer(obj.getFrelation());
		// 是否默认账号
		entity.setDefaultAccount(obj.getFisdefaultaccount());
		// entity.setNotes(obj.get);//备注
		if (StringUtil.equals(String.valueOf(obj.getFstatus()), "0")) {
			// CRM 0有效
			entity.setActive(Y);
		} else if (StringUtil.equals(String.valueOf(obj.getFstatus()), "2")) {
			// CRM 2 无效
			entity.setActive(N);
		} else {
			LOGGER.info("客户银行账号的状态有问题啊.........." + obj.getFstatus());
			throw new TfrBusinessException("客户银行账号的状态有问题啊.........." + obj.getFstatus());
		}
		// 账户性质 对公；对私两种
		entity.setAccountNature(obj.getFaccountnature());
		// 在CRM中fid
		entity.setCrmId(obj.getFid());
		// 开户省份名称
		entity.setProvinceName(obj.getFbankprovincename());
		// 开户城市名称
		entity.setCityName(obj.getFbankcityname());
		// 所属客户ID
		entity.setBelongCustom(obj.getFbelongcustom());
		// 财务联系人名称
		entity.setFinanceLinkman(obj.getFfinancelinkman());
		// 账户用途(代收货款、月发月送、代收货款和月发月送)
		entity.setAccountUse(obj.getFaccountuse());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);

		return entity;
	}

	private boolean saveOrUpdateCustomer(SyncResponse response, CustCustbasedata custbasedata, CustomerEntity entity) {
		entity.setCusCode(custbasedata.getFcustnumber());
		entity.setId(custbasedata.getFossId());
		int result = customerService.updateCustomer(entity);
		if (result > 0) {
			LOGGER.info("修改客户基本信息成功！");
		} // wp_078816_20140818
		else if (result == 0) {
			String code = entity.getCusCode();
			CustomerDto custDto = customerService.queryCustInfoByCode(code);
			if (null == custDto) {
				customerService.addCustomer(entity);
			} else {
				customerService.updateCustomer(entity);
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean saveOrUpdateCustomer(SyncResponse response, CustomerEntity entity) {
		int result = 0;
		String code = entity.getCusCode();
		CustomerDto custDto = customerService.queryCustInfoByCode(code);
		if (null == custDto) {
			result = customerService.addCustomer(entity);
			if (result > 0) {
				LOGGER.info("新增客户基本信息成功！");
			} else {
				LOGGER.info("新增客户基本信息失败！");
				response.setReturn(false);
			}
		} else {
			result = customerService.updateCustomer(entity);
			if (result > 0) {
				LOGGER.info("修改客户基本信息成功！");
			} else {
				return false;
			}
		}
		return true;
	}

	private CustomerEntity convertCustomer(CustCustbasedata obj) {
		// 创建客户实体
		CustomerEntity entity = new CustomerEntity();
		// 客户公司地址
		entity.setAddress(obj.getFcompaddr());
		// 客户属性----------需要类型编码转换
		entity.setProperty(obj.getFcustnature());
		// 客户类型
		entity.setType(obj.getFcusttype());
		// 信用额度-------是CRM中临欠额度
		entity.setCreditAmount(obj.getFprocredit());
		// 客户名称
		entity.setName(obj.getFcustname());
		// 税务营业执照号
		entity.setLicense(obj.getFtaxregnumber());
		// 客户发票标记
		entity.setInvoiceType(obj.getFinvoiceType());

		// 精准计价
		entity.setIsAccuratePrice(obj.getIsAccuratePrice());

		if (StringUtil.isNotBlank(obj.getFdeptidStandardcode())) {
			// 客户所属部门标杆编码
			entity.setUnifiedCode(obj.getFdeptidStandardcode().trim());
		} else {
			// 客户所属部门标杆编码
			entity.setUnifiedCode(obj.getFdeptidStandardcode());
		}

		//sonar-352203-if-else内容相同
/*		if (StringUtil.isNotBlank(obj.getFcustnumber())) {
			// 客户编码
			entity.setCusCode(obj.getFcustnumber());
		} else {
			// 客户编码
			entity.setCusCode(obj.getFcustnumber());
		}*/
		entity.setCusCode(obj.getFcustnumber());

		// 客户是否有效---------------与Active字段重复
		// entity.setActiveCus(activeCus);
		// 月结客户总欠款---------------------CRM中没有此字段
		// entity.setTotalArrears(obj.get);
		// 结算方式---------------------CRM中没有此字段
		// entity.setChargeMode(obj);
		// 客户等级
		entity.setDegree(obj.getFdegree());
		// CRM客户ID
		entity.setCrmCusId(obj.getFid());

		// CRM-状态：正常：0； 审批中：1 ；无效 ：2；
		if (StringUtil.equals(obj.getFcuststatus(), _0)) {
			// CRM-状态：正常：0； 审批中：1 ；无效 ：2；
			entity.setActive(Y);
		} else if (StringUtil.equals(obj.getFcuststatus(), "2")) {// 2为无效
			// CRM-状态：正常：0； 审批中：1 ；无效 ：2；
			entity.setActive(N);
		} else {
			LOGGER.info("传入的状态有问题啊.........." + obj.getFcuststatus());
			throw new TfrBusinessException("传入的状态有问题啊.........." + obj.getFcuststatus());
		}
		// 所在省份
		entity.setProvName(obj.getFprovince());
		// 所在城市
		entity.setCityName(obj.getFcity());
		// 客户简称
		entity.setSimpleName(obj.getFsimplename());
		// 是：1 否：0
		if (StringUtil.equals(_0, obj.getFisimportcustor())) {
			// 是否重要客户
			entity.setIsImport(N);
		} else {
			// 是否重要客户
			entity.setIsImport(Y);
		}
		// 是：1 否：0
		if (StringUtil.equals(_0, obj.getFisspecial())) {
			// 是否特殊客户
			entity.setIsSpecial(N);
		} else {
			// 是否特殊客户
			entity.setIsSpecial(Y);
		}
		// 是：1 否：0
		if (StringUtil.equals(_0, obj.getFistrangoods())) {
			// 是否异地调货
			entity.setFistrangoods(N);
		} else {
			// 是否异地调货
			entity.setFistrangoods(Y);
		}
		// 发票抬头
		entity.setBillTitle(obj.getFbilltitle());
		// CRM创建时间
		entity.setCreateDate(obj.getFcreatetime());
		// 修改时间
		entity.setModifyDate(obj.getFlastupdatetime());
		// 是否财务作废
		if (obj.isFinanceCancel()) {
			entity.setIsDelete(Y);
		} else {
			entity.setIsDelete(N);
		}
		// 发件人短信
		entity.setShipperSms(obj.getFshipperSms());
		// 收件人短信
		entity.setReceiverSms(obj.getFreceiverSms());
		// 创建用户
		entity.setCreateUser(USER_CODE);
		// 修改用户
		entity.setModifyUser(USER_CODE);
		// 是否精准代收
		if (obj.isFisAccurateCollection()) {
			entity.setAccurateCollection(Y);
		} else {
			entity.setAccurateCollection(N);
		}
		// 078816_wp_2014-03-20
		// 是否大客户
		if (obj.isIfBigCustomer()) {
			entity.setIsLargeCustomers(Y);
		} else {
			entity.setIsLargeCustomers(N);
		}
		// 是否快递大客户 132599—2014-10-28
		if (obj.isFisExpKeycustomer()) {
			entity.setIsExpressBigCust(Y);
		} else {
			entity.setIsExpressBigCust(N);
		}
		// 业务类别
		String businessType = obj.getFcustcategory().trim();
		entity.setBusinessType(businessType);

		// 客户类型
		String customerNature = obj.getCustGroup().trim();
		if (StringUtils.isEmpty(customerNature)) {
			throw new TfrBusinessException("客户CRM_ID为" + obj.getFcustnumber() + "的客户，客户类型为空！");
		} else {
			entity.setCustomserNature(customerNature);
		}
		// 收货人固定手机号
		entity.setFixedReceiveMobile(obj.getFixedReceiveMobile());
		// 一级客户
		entity.setOneLevelIndustry(obj.getFtradeid());
		// 二级客户
		entity.setTwoLevelIndustry(obj.getSecondTrade());
		// 是否电子运单大客户
		entity.setIsElecBillBigCust(obj.getIfElecBillBigCust());
		// 客户地址备注
		entity.setCustAddrRemark(obj.getFcompaddrRemark());
		// 特安上限
		entity.setTeanLimit(obj.getFteanLimit());
		// 名单类别 2015-06-04 FOSS_261997 黑名单延期
		entity.setBlackListCategory(obj.getBlackListCategory());
		// 客户分群
		entity.setFlabelleavemonth(obj.getFlabelleavemonth());
		// 快递装卸费业务类型
		entity.setExpHandChargeBusiType(obj.getExpHandChargeBusiType());
		// 设置比例
		entity.setSetProportion(obj.getSetProportion());
		return entity;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	@Autowired
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public ICusAccountService getCusAccountService() {
		return cusAccountService;
	}

	@Autowired
	public void setCusAccountService(ICusAccountService cusAccountService) {
		this.cusAccountService = cusAccountService;
	}

	public ICusBargainService getCusBargainService() {
		return cusBargainService;
	}

	@Autowired
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public ICusContactService getCusContactService() {
		return cusContactService;
	}

	@Autowired
	public void setCusContactService(ICusContactService cusContactService) {
		this.cusContactService = cusContactService;
	}

	public ICusAddressService getCusAddressService() {
		return cusAddressService;
	}

	@Autowired
	public void setCusAddressService(ICusAddressService cusAddressService) {
		this.cusAddressService = cusAddressService;
	}

	public IContactAddressService getContactAddressService() {
		return contactAddressService;
	}

	@Autowired
	public void setContactAddressService(IContactAddressService contactAddressService) {
		this.contactAddressService = contactAddressService;
	}

	public IBargainAppOrgService getBargainAppOrgService() {
		return bargainAppOrgService;
	}

	@Autowired
	public void setBargainAppOrgService(IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}

	public IPreferentialService getPreferentialService() {
		return preferentialService;
	}

	@Autowired
	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}

	public ICusContractTaxService getCusContractTaxService() {
		return cusContractTaxService;
	}

	@Autowired
	public void setCusContractTaxService(ICusContractTaxService cusContractTaxService) {
		this.cusContractTaxService = cusContractTaxService;
	}

}
