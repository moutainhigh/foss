/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.crm.module.client.sync.domain.dto.CrmSyncRequest;
import com.deppon.crm.module.client.sync.domain.dto.CustAccount;
import com.deppon.crm.module.client.sync.domain.dto.CustContract;
import com.deppon.crm.module.client.sync.domain.dto.CustContractTax;
import com.deppon.crm.module.client.sync.domain.dto.CustContractdept;
import com.deppon.crm.module.client.sync.domain.dto.CustCustbasedata;
import com.deppon.crm.module.client.sync.domain.dto.CustCustlinkman;
import com.deppon.crm.module.client.sync.domain.dto.CustPreferenceaddress;
import com.deppon.crm.module.client.sync.domain.dto.CustPreferential;
import com.deppon.crm.module.client.sync.domain.dto.CustShuttleaddress;
import com.deppon.esb.inteface.domain.crm.OrigCustSyncRequestProcessDetail;
import com.deppon.esb.inteface.domain.crm.OrigCustSyncResponse;
import com.deppon.esb.inteface.domain.crm.ScatterCustBankInfo;
import com.deppon.esb.inteface.domain.crm.ScatterCustInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContractTaxService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosFromCrmService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BargainAppOrgException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactAddressException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusAccountException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusAddressException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusBargainException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusContactException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusContractTaxException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CustomerException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PreferentialException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.foss.ws.syncdata.domain.SyncResponse;


/**
 * 从CRM系统同步客户主数据业务处理接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-5-16 下午3:30:43 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-5-16 下午3:30:43
 * @since
 * @version
 */
public class SyncInfosFromCrmService implements ISyncInfosFromCrmService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncInfosFromCrmService.class);
    
    /**
     * 
     */
    private static final String USER_CODE = "CRM";
    
    /**
     * 客户信息Service接口.
     */
    private ICustomerService customerService;

    /**
     * 客户开户银行Service接口.
     */
    private ICusAccountService cusAccountService;

    /**
     * 客户接送货地址Service接口.
     */
    private ICusAddressService cusAddressService;

    /**
     * 客户合同信息Service接口.
     */
    private ICusBargainService cusBargainService;

    /**
     * 客户联系人信息Service接口.
     */
    private ICusContactService cusContactService;

    /**
     * 合同适用部门Service接口.
     */
    private IBargainAppOrgService bargainAppOrgService;

    /**
     * 客户优惠信息Service接口.
     */
    private IPreferentialService preferentialService;
    
    /**
     * 联系人接送货地址Service接口.
     */
    private IContactAddressService contactAddressService;
    
    /**
     * 客户发票标记信息Service接口
     */
    private ICusContractTaxService cusContractTaxService;
    
	/**
     * 散客信息Service接口.
     */
    private INonfixedCustomerService nonfixedCustomerService;

    /**
     * 临欠散客开户银行账户信息Service接口.
     */
    private INonfixedCusAccountService nonfixedCusAccountService;
    
    /**
     * 设置 散客信息Service接口.
     *
     * @param nonfixedCustomerService the nonfixedCustomerService to set
     */
    public void setNonfixedCustomerService(
    	INonfixedCustomerService nonfixedCustomerService) {
        this.nonfixedCustomerService = nonfixedCustomerService;
    }
    
    /**
     * 设置 临欠散客开户银行账户信息Service接口.
     *
     * @param nonfixedCusAccountService the nonfixedCusAccountService to set
     */
    public void setNonfixedCusAccountService(
    	INonfixedCusAccountService nonfixedCusAccountService) {
        this.nonfixedCusAccountService = nonfixedCusAccountService;
    }
    
    /**
     * 设置 联系人接送货地址Service接口.
     *
     * @param contactAddressService the contactAddressService to set
     */
    public void setContactAddressService(
    	IContactAddressService contactAddressService) {
        this.contactAddressService = contactAddressService;
    }
    
    /**
     * 设置 客户信息Service接口.
     *
     * @param customerService the customerService to set
     */
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }
    
    /**
     * 设置 客户开户银行Service接口.
     *
     * @param cusAccountService the cusAccountService to set
     */
    public void setCusAccountService(ICusAccountService cusAccountService) {
        this.cusAccountService = cusAccountService;
    }
    
    /**
     * 设置 客户接送货地址Service接口.
     *
     * @param cusAddressService the cusAddressService to set
     */
    public void setCusAddressService(ICusAddressService cusAddressService) {
        this.cusAddressService = cusAddressService;
    }
    
    /**
     * 设置 客户合同信息Service接口.
     *
     * @param cusBargainService the cusBargainService to set
     */
    public void setCusBargainService(ICusBargainService cusBargainService) {
        this.cusBargainService = cusBargainService;
    }
    
    /**
     * 设置 客户联系人信息Service接口.
     *
     * @param cusContactService the cusContactService to set
     */
    public void setCusContactService(ICusContactService cusContactService) {
        this.cusContactService = cusContactService;
    }
    
    /**
     * 设置 合同适用部门Service接口.
     *
     * @param bargainAppOrgService the bargainAppOrgService to set
     */
    public void setBargainAppOrgService(IBargainAppOrgService bargainAppOrgService) {
        this.bargainAppOrgService = bargainAppOrgService;
    }
    
    /**
     * 设置 客户优惠信息Service接口.
     *
     * @param preferentialService the preferentialService to set
     */
    public void setPreferentialService(IPreferentialService preferentialService) {
        this.preferentialService = preferentialService;
    }
    
    /**
     * 设置 客户发票标记信息Service接口.
     *
     * @param cusContractTaxService the cusContractTaxService to set
     */
    public void setCusContractTaxService(
			ICusContractTaxService cusContractTaxService) {
		this.cusContractTaxService = cusContractTaxService;
	}


    /**
     * <p>客户同步主数据处理</p>.
     *
     * @param crmSyncRequest 
     * @return 
     * @throws CommonException 
     * @author 094463-foss-xieyantao
     * @date 2013-5-16 下午3:30:43
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosFromCrmService#syncInfo(com.deppon.crm.module.client.sync.domain.dto.CrmSyncRequest, javax.xml.ws.Holder)
     */
    @Transactional
    @Override
	public SyncResponse syncInfo(CrmSyncRequest crmSyncRequest)
			throws CommonException {
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
			response.setReturn(false);
			return response;
		}
		// 获取客户基本信息
		CustCustbasedata custbasedata = crmSyncRequest.gettCustCustbasedata();
		if (null != custbasedata) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = customerService.queryCustomerByCrmId(custbasedata
					.getFid());

			LOGGER.info("客户信息CRM_ID :" + custbasedata.getFid());
			CustomerEntity entity = convertCustomer(custbasedata);
			if (StringUtils.isEmpty(custbasedata.getCustGroup())) {
				throw new BusinessException("客户类别为空！");
			}
			// String codeid = entity.getCusCode();
			// CustomerDto custDtoid =
			// customerService.queryCustInfoByCode(codeid);
			/*
			 * if (null != custDtoid) { convertid = custDtoid.getId(); }
			 */
			try {
				boolean isSaveOrUpdateCustomer=true;
				if (flag
						|| StringUtils.equals(custbasedata.getCustGroup(),
								DictionaryValueConstants.CUSTOMER_SC_CUSTOMER)) {
					if (!flag) {
						// 078816_wp_2014-03-20
						customerNature = custbasedata.getCustGroup();
						custFossId = custbasedata.getFossId();

					}

					isSaveOrUpdateCustomer = saveOrUpdateCustomer(response, custbasedata,
							entity);

				} else {

					isSaveOrUpdateCustomer=	saveOrUpdateCustomer(response, entity);

				}
				if(!isSaveOrUpdateCustomer){
					LOGGER.info("保存客户信息失败！");
					response.setReturn(false);
					return response;
				}
			} catch (CustomerException e) {
				LOGGER.error(e.getMessage(), e);
				throw new CommonException(e.getMessage(), e);
			}
		}
		// 客户开户银行信息列表
		List<CustAccount> custAccounts = crmSyncRequest.gettCustAccount();
		try {
			boolean isSaveOrUpdatecustAccounts=true;
		if (CollectionUtils.isNotEmpty(custAccounts)) {
			
			isSaveOrUpdatecustAccounts=saveOrUpdateCustAccount(response, custAccounts);
		}
		if(!isSaveOrUpdatecustAccounts){
			LOGGER.info("新增或修改客户开户银行信息失败！");
			response.setReturn(false);
			return response;
		}
		} catch (CusAccountException e) {
			LOGGER.error(e.getMessage(), e);
			throw new CommonException(e.getMessage(), e);
		}
		// 客户合同信息
		List<CustContract> contracts = crmSyncRequest.gettCustContract();
			boolean isSaveOrUpdateCustContract=true;
		if (CollectionUtils.isNotEmpty(contracts)) {
			
			isSaveOrUpdateCustContract=saveOrUpdateCustContract(response, contracts,
					isSaveOrUpdateCustContract);
		}
		if(!isSaveOrUpdateCustContract){
			LOGGER.info("新增或修改客户合同信息失败！");
			response.setReturn(false);
			return response;
		}
		// 客户联系人信息
		List<CustCustlinkman> linkmanList = crmSyncRequest
				.gettCustCustlinkman();
        boolean isSaveOrUpdateCustCustlinkman=true;
		if (CollectionUtils.isNotEmpty(linkmanList)) {
			isSaveOrUpdateCustCustlinkman=saveOrUpdateCustCustlinkman(response, customerNature, custFossId,
					linkmanList);
		}
		if(!isSaveOrUpdateCustCustlinkman){
			LOGGER.info("新增或修改客户联系人信息失败！");
			response.setReturn(false);
			return response;
		}

		// 接送货地址信息
		List<CustShuttleaddress> addressList = crmSyncRequest
				.gettCustShuttleaddress();
        boolean isSaveOrUpdateCustShuttleaddress=true;
		if (CollectionUtils.isNotEmpty(addressList)) {
			isSaveOrUpdateCustShuttleaddress=saveOrUpdateCustShuttleaddress(response, customerNature,
					custFossId, isSaveOrUpdateCustCustlinkman, addressList);
		}
		if(!isSaveOrUpdateCustShuttleaddress){
			LOGGER.info("新增或修改客户接送货地址信息失败！");
			response.setReturn(false);
			return response;
		}
		// 联系人-地址关系信息
		List<CustPreferenceaddress> contactAddressList = crmSyncRequest
				.gettCustPreferenceaddress();
		boolean isSaveOrUpdateCustPreferenceaddress=true;
		if (CollectionUtils.isNotEmpty(contactAddressList)) {
			isSaveOrUpdateCustPreferenceaddress=saveOrUpdateCustPreferenceaddress(response, contactAddressList);
		}
		if(!isSaveOrUpdateCustPreferenceaddress){
			LOGGER.info("新增或修改客户联系人-地址关系信息失败！");
			response.setReturn(false);
			return response;
		}
		// 合同-部门关系信息
		List<CustContractdept> contractDeptList = crmSyncRequest
				.gettCustContractdept();
		boolean isSaveOrUpdateCustContractdept=true;
		if (CollectionUtils.isNotEmpty(contractDeptList)) {
			isSaveOrUpdateCustContractdept=saveOrUpdateCustContractdept(response, contractDeptList);
		}
		if(!isSaveOrUpdateCustContractdept){
			LOGGER.info("新增或修改客户合同-部门关系信息失败！");
			response.setReturn(false);
			return response;
		}
		// 客户优惠信息
		List<CustPreferential> preferentialList = crmSyncRequest
				.gettCustPreferential();
		boolean isSaveOrUpdateCustPreferential=true;
		if (CollectionUtils.isNotEmpty(preferentialList)) {
			isSaveOrUpdateCustPreferential=saveOrUpdateCustPreferential(response, preferentialList);
		}
		if(!isSaveOrUpdateCustPreferential){
			LOGGER.info("新增或修改客户优惠信息失败！");
			response.setReturn(false);
			return response;
		}
		// 客户发票标记信息
		List<CustContractTax> cusContractTaxList = crmSyncRequest
				.gettCustContractTax();
		boolean isSaveOrUpdateCustContractTax=true;
		if (CollectionUtils.isNotEmpty(cusContractTaxList)) {
			isSaveOrUpdateCustContractTax=saveOrUpdateCustContractTax(response, cusContractTaxList);
		}
		if(!isSaveOrUpdateCustContractTax){
			LOGGER.info("新增或修改客户发票标记信息失败！");
			response.setReturn(false);
			return response;
		}
		LOGGER.info("同步客户主信息结束.......");
		response.setReturn(true);
		return response;

	}

	private boolean saveOrUpdateCustContractTax(SyncResponse response,
			List<CustContractTax> cusContractTaxList) throws CommonException {
		for (CustContractTax contractTax : cusContractTaxList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = cusContractTaxService
					.queryCusContractTaxByCrmId(contractTax.getFid(), null);
			LOGGER.info("客户发票标记信息CRM_ID :" + contractTax.getFid());
			// 信息转换封装
			CusContractTaxEntity entity = convertCusContractTax(contractTax);
			if (flag) {
				try {
					int result = cusContractTaxService
							.updateCusContractTax(entity);
					if (result > 0) {
						LOGGER.info("修改客户发票标记信息成功！");
					} else {
						return false;
					}
				} catch (CusContractTaxException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {
				try {
					int result = cusContractTaxService
							.addCusContractTax(entity);
					if (result > 0) {
						LOGGER.info("新增客户发票标记信息成功！");
					} else {
						return false;
					}
				} catch (CusContractTaxException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean saveOrUpdateCustPreferential(SyncResponse response,
			List<CustPreferential> preferentialList) throws CommonException {
		for (CustPreferential preferential : preferentialList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = preferentialService.queryPreferentialByCrmId(
					preferential.getFid(), null);
			LOGGER.info("客户优惠信息CRM_ID :" + preferential.getFid());
			// 信息转换封装
			PreferentialEntity entity = convertPreferential(preferential);
			if (flag) {
				try {
					int result = preferentialService
							.updatePreferential(entity);
					if (result > 0) {
						LOGGER.info("修改客户优惠信息成功！");
					} else {
						return false;
					}
				} catch (PreferentialException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {
				try {
					int result = preferentialService
							.addPreferential(entity);
					if (result > 0) {
						LOGGER.info("新增客户优惠信息成功！");
					} else {
						return false;
					}
				} catch (PreferentialException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean saveOrUpdateCustContractdept(SyncResponse response,
			List<CustContractdept> contractDeptList) throws CommonException {
		for (CustContractdept contractdept : contractDeptList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = bargainAppOrgService.queryBargainAppOrgByCrmId(
					contractdept.getFid(), null);
			LOGGER.info("合同-部门关系信息CRM_ID :" + contractdept.getFid());
			// 信息转换封装
			BargainAppOrgEntity entity = convertBargainAppOrg(contractdept);
			if (flag) {
				try {
					int result = bargainAppOrgService
							.updateBargainAppOrg(entity);
					if (result > 0) {
						LOGGER.info("修改客户合同-部门关系信息成功！");
					} else {
						return false;
					}
				} catch (BargainAppOrgException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {
				try {
					int result = bargainAppOrgService
							.addBargainAppOrg(entity);
					if (result > 0) {
						LOGGER.info("新增客户合同-部门关系信息成功！");
					} else {
						return false;
					}
				} catch (BargainAppOrgException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean saveOrUpdateCustPreferenceaddress(SyncResponse response,
			List<CustPreferenceaddress> contactAddressList)
			throws CommonException {
		for (CustPreferenceaddress preferenceAddress : contactAddressList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = contactAddressService
					.queryContactAddressByCrmId(preferenceAddress.getFid(),
							null);
			LOGGER.info("联系人-地址关系信息CRM_ID :" + preferenceAddress.getFid());
			// 信息转换封装
			ContactAddressEntity entity = convertContactAddress(preferenceAddress);
			if (flag) {
				try {
					int result = contactAddressService
							.updateContactAddress(entity);
					if (result > 0) {
						LOGGER.info("修改联系人-地址关系信息成功！");
					} else {
						return false;
					}
				} catch (ContactAddressException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {
				try {
					int result = contactAddressService
							.addContactAddress(entity);
					if (result > 0) {
						LOGGER.info("新增联系人-地址关系信息成功！");
					} else {
						return false;
					}
				} catch (ContactAddressException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean saveOrUpdateCustShuttleaddress(SyncResponse response,
			String customerNature, String custFossId,
			boolean isSaveOrUpdateCustCustlinkman,
			List<CustShuttleaddress> addressList) throws CommonException {
		for (CustShuttleaddress address : addressList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = cusAddressService.queryCusAddressByCrmId(
					address.getFid(), null);
			LOGGER.info("接送货地址信息CRM_ID :" + address.getFid());
			// 信息转换封装
			CusAddressEntity entity = convertCusAddress(address);
			if (flag
					|| StringUtils.equals(customerNature,
							DictionaryValueConstants.CUSTOMER_SC_CUSTOMER)) {
				try {
					if (!flag) {
						// 078816_wp_2014-03-20
						entity.setCustomserNature(customerNature);
						entity.setOwnCustId(custFossId);

					}
					int result = cusAddressService.updateCusAddress(entity);
					if (result > 0) {
						LOGGER.info("修改客户接送货地址信息成功！");
					} // wp_078816_20140818
					else if (result == 0) {
						cusAddressService.addCusAddress(entity);
					} else {
						return false;
					}
				} catch (CusAddressException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {
				try {

					int result = cusAddressService.addCusAddress(entity);
					if (result > 0) {
						LOGGER.info("新增客户接送货地址信息成功！");
					} else {
						return false;
					}
					// CusAddressEntity addressEntity =
					// cusAddressService.queryCusAddressByOwnCustId(convertid);
					// if(null==addressEntity){
					// int result = cusAddressService
					// .addCusAddress(entity);
					// if (result > 0) {
					// LOGGER.info("新增客户接送货地址信息成功！");
					// } else {
					// LOGGER.info("新增客户接送货地址信息失败！");
					// response.setReturn(false);
					// return response;
					// }
					// }else{
					// entity.setOwnCustId(convertid);//CRM不会传此散客生成ID CSS生产
					// int result =
					// cusAddressService.updateCusAddressByCusfossid(entity);
					// if (result > 0) {
					// LOGGER.info("修改客户接送货地址信息成功！");
					// }else {
					// LOGGER.info("修改客户接送货地址信息失败！");
					// response.setReturn(false);
					// return response;
					// }
					// }

				} catch (CusAddressException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
	
		return true;
	}

	private boolean saveOrUpdateCustCustlinkman(SyncResponse response,
			String customerNature, String custFossId,
			List<CustCustlinkman> linkmanList) throws CommonException {
		for (CustCustlinkman linkman : linkmanList) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = cusContactService.queryCusContactByCrmId(
					linkman.getFid(), null);
			LOGGER.info("客户联系人信息CRM_ID :" + linkman.getFid());
			// 信息转换封装
			ContactEntity entity = convertContact(linkman);
			if (flag
					|| StringUtils.equals(customerNature,
							DictionaryValueConstants.CUSTOMER_SC_CUSTOMER)) {// 修改
				try {
					if (!flag) {
						// 078816_wp_2014-03-20
						entity.setCustomserNature(customerNature);
						entity.setOwnCustId(custFossId);

					}
					int result = cusContactService.updateCusContact(entity);
					if (result > 0) {
						LOGGER.info("修改客户联系人信息成功！");
					} // wp_078816_20140818
					else if (result == 0) {
						cusContactService.addCusContact(entity);
					} else {
						return false;
					}
				} catch (CusContactException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {// 新增
				try {
					int result = cusContactService.addCusContact(entity);
					if (result > 0) {
						LOGGER.info("新增客户联系人信息成功！");
					} else {
						LOGGER.info("新增客户联系人信息失败！");
						response.setReturn(false);
						return false;
					}
					// ContactEntity contactEntity =
					// cusContactService.queryCusContactByOwnCustId(convertid);
					// if(null==contactEntity){
					// int result = cusContactService
					// .addCusContact(entity);
					// if (result > 0) {
					// LOGGER.info("新增客户联系人信息成功！");
					// } else {
					// LOGGER.info("新增客户联系人信息失败！");
					// response.setReturn(false);
					// return response;
					// }
					// }else{
					// entity.setOwnCustId(convertid);//CRM不会传此散客生成ID CSS生产
					// int result =
					// cusContactService.updateCusContactByCusfossid(entity);
					// if (result > 0) {
					// LOGGER.info("修改客户联系人信息成功！");
					// }else {
					// LOGGER.info("修改客户联系人信息失败！");
					// response.setReturn(false);
					// return response;
					// }
					// }

				} catch (CusContactException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean saveOrUpdateCustContract(SyncResponse response,
			List<CustContract> contracts, boolean isSaveOrUpdateCustContract)
			throws CommonException {
		for (CustContract custContract : contracts) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = cusBargainService.queryCusBargainByCrmId(
					custContract.getFid(), null);
			LOGGER.info("客户合同信息CRM_ID :" + custContract.getFid());
			// 信息转换封装
			CusBargainEntity entity = convertCusBargain(custContract);
			if (flag) {// 如果存在修改
				try {
					int result = cusBargainService.updateCusBargain(entity);
					if (result > 0) {
						LOGGER.info("修改客户合同信息成功！");
					} else {
						return false;
					}
				} catch (CusBargainException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			} else {
				try {
					int result = cusBargainService.addCusBargain(entity);
					if (result > 0) {
						LOGGER.info("新增客户合同信息成功！");
					} else {
						LOGGER.info("新增客户合同信息失败！");
						response.setReturn(false);
						return false;
					}
				} catch (CusBargainException e) {
					LOGGER.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean saveOrUpdateCustAccount(SyncResponse response,
			List<CustAccount> custAccounts) {
		for (CustAccount custAccount : custAccounts) {
			// 验证同步过来的信息在Foss系统中是否存在
			boolean flag = cusAccountService.queryCusAccountByCrmId(
					custAccount.getFid(), null);
			LOGGER.info("客户开户银行信息CRM_ID :" + custAccount.getFid());
			// 信息转换封装
			CusAccountEntity entity = convertCusAccount(custAccount);
			if (flag) {// 如果存在修改
				
					int result = cusAccountService.updateCusAccount(entity);
					if (result > 0) {
						LOGGER.info("修改客户开户银行信息成功！");
					} else {
						
						return false;
					}
				
			} else {// 新增
				
					int result = cusAccountService.addCusAccount(entity);
					if (result > 0) {
						LOGGER.info("新增客户开户银行信息成功！");
					} else {
						return false;
					}
				

			}
		}
		return true;
	}

	private boolean saveOrUpdateCustomer(SyncResponse response,
			CustomerEntity entity) {
		// int result = customerService.addCustomer(entity);
		int result = 0;

		// 解决散客信息自动升级成固定客户，上面条件不满足，但是FOSS中有相关客户信息，这样会造成2条信息 261997
		// css
		String code = entity.getCusCode();
		CustomerDto custDto = customerService
				.queryCustInfoByCode(code);
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

	private boolean saveOrUpdateCustomer(SyncResponse response,
			CustCustbasedata custbasedata, CustomerEntity entity) {
		entity.setCusCode(custbasedata.getFcustnumber());
		entity.setId(custbasedata.getFossId());
		int result = customerService.updateCustomer(entity);
		if (result > 0) {
			LOGGER.info("修改客户基本信息成功！");
		} // wp_078816_20140818
		else if (result == 0) {
			String code = entity.getCusCode();
			CustomerDto custDto = customerService
					.queryCustInfoByCode(code);
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

    /**
     * <p>
     * 把CRM同步过来的客户信息转化为FOSS客户信息实体
     * </p>.
     *
     * @param obj 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午5:09:17
     * @see
     */
    private CustomerEntity convertCustomer(CustCustbasedata obj) {
	//创建客户实体
	CustomerEntity entity = new CustomerEntity();
	// 客户公司地址
	entity.setAddress(obj.getFcompaddr()); 
	// 客户属性----------需要类型编码转换
	entity.setProperty(obj.getFcustnature());
	// 客户类型
	entity.setType(obj.getFcusttype()); 
	//信用额度-------是CRM中临欠额度
	entity.setCreditAmount(obj.getFprocredit());
	// 客户名称
	entity.setName(obj.getFcustname());
	// 税务营业执照号
	entity.setLicense(obj.getFtaxregnumber());
	//客户发票标记
	entity.setInvoiceType(obj.getFinvoiceType());
	
	//精准计价
	entity.setIsAccuratePrice(obj.getIsAccuratePrice());
	
	if(StringUtil.isNotBlank(obj.getFdeptidStandardcode())){
	    // 客户所属部门标杆编码
	    entity.setUnifiedCode(obj.getFdeptidStandardcode().trim());
	}else {
	    // 客户所属部门标杆编码
	    entity.setUnifiedCode(obj.getFdeptidStandardcode());
	}
	    //客户编码 
	    entity.setCusCode(obj.getFcustnumber()); 
	
	//客户是否有效---------------与Active字段重复
//	entity.setActiveCus(activeCus);
	//月结客户总欠款---------------------CRM中没有此字段
//	entity.setTotalArrears(obj.get);
	//结算方式---------------------CRM中没有此字段
	// entity.setChargeMode(obj); 
	// 客户等级
	entity.setDegree(obj.getFdegree());
	// CRM客户ID
	entity.setCrmCusId(obj.getFid());
	
	// CRM-状态：正常：0；  审批中：1  ；无效 ：2；
	if (StringUtil.equals(obj.getFcuststatus(), NumberConstants.NUMERAL_S_ZORE)) {
	    // CRM-状态：正常：0；  审批中：1  ；无效 ：2；
	    entity.setActive(FossConstants.ACTIVE);
	}else if (StringUtil.equals(obj.getFcuststatus(), NumberConstants.NUMERAL_S_TWO)) {// 2为无效
	    // CRM-状态：正常：0；  审批中：1  ；无效 ：2；
	    entity.setActive(FossConstants.INACTIVE);
	}else {
	    LOGGER.info("传入的状态有问题啊.........."+obj.getFcuststatus());
	    throw new CustomerException("传入的状态有问题啊.........."+obj.getFcuststatus());
	}
	//所在省份
	entity.setProvName(obj.getFprovince());
	//所在城市
	entity.setCityName(obj.getFcity());
	//客户简称
	entity.setSimpleName(obj.getFsimplename());
	//是：1   否：0
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFisimportcustor())){
	    //是否重要客户
	    entity.setIsImport(FossConstants.NO);
	}else {
	    //是否重要客户
	    entity.setIsImport(FossConstants.YES);
	}
	//是：1   否：0
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFisspecial())){
	    //是否特殊客户
	    entity.setIsSpecial(FossConstants.NO);
	}else {
	    //是否特殊客户
	    entity.setIsSpecial(FossConstants.YES);
	}
	//是：1   否：0
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFistrangoods())){
	    //是否异地调货
	    entity.setFistrangoods(FossConstants.NO);
	}else {
	    //是否异地调货
	    entity.setFistrangoods(FossConstants.YES);
	}
	//发票抬头
	entity.setBillTitle(obj.getFbilltitle());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//是否财务作废
	if(obj.isFinanceCancel()){
	    entity.setIsDelete(FossConstants.ACTIVE);
	}else {
	    entity.setIsDelete(FossConstants.INACTIVE);
	}
	//发件人短信
	entity.setShipperSms(obj.getFshipperSms());
	//收件人短信
	entity.setReceiverSms(obj.getFreceiverSms());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);
	//是否精准代收
	if(obj.isFisAccurateCollection()){
	    entity.setAccurateCollection(FossConstants.ACTIVE);
	}else {
	    entity.setAccurateCollection(FossConstants.INACTIVE);
	}
	//078816_wp_2014-03-20
	//是否大客户
	if(obj.isIfBigCustomer()){
		entity.setIsLargeCustomers(FossConstants.ACTIVE);
	}else{
		entity.setIsLargeCustomers(FossConstants.INACTIVE);
	}
	//是否快递大客户  132599—2014-10-28
	if(obj.isFisExpKeycustomer()){
		entity.setIsExpressBigCust(FossConstants.ACTIVE);
	}else{
		entity.setIsExpressBigCust(FossConstants.INACTIVE);
	}
	//业务类别
	String businessType = obj.getFcustcategory().trim();
	entity.setBusinessType(businessType);
	
	//客户类型
	String customerNature = obj.getCustGroup().trim();
	if(StringUtils.isEmpty(customerNature)){
		throw new BusinessException("客户CRM_ID为"+obj.getFcustnumber()+"的客户，客户类型为空！");
	}else{
		entity.setCustomserNature(customerNature);
	}
	//收货人固定手机号
	entity.setFixedReceiveMobile(obj.getFixedReceiveMobile());
	//一级客户
	entity.setOneLevelIndustry(obj.getFtradeid());
	//二级客户
	entity.setTwoLevelIndustry(obj.getSecondTrade());
	//是否电子运单大客户
	entity.setIsElecBillBigCust(obj.getIfElecBillBigCust());
	//客户地址备注
	entity.setCustAddrRemark(obj.getFcompaddrRemark());
	// 特安上限
	entity.setTeanLimit(obj.getFteanLimit());
	// 名单类别  2015-06-04 FOSS_261997 黑名单延期
	entity.setBlackListCategory(obj.getBlackListCategory());
	//客户分群
	entity.setFlabelleavemonth(obj.getFlabelleavemonth());
	//快递装卸费业务类型	
	entity.setExpHandChargeBusiType(obj.getExpHandChargeBusiType());
	//设置比例
	entity.setSetProportion(obj.getSetProportion());
	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的联系人-接送货地址信息转化为FOSS联系人接送货地址实体
     * </p>.
     *
     * @param obj 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午5:09:17
     * @see
     */
    private ContactAddressEntity convertContactAddress(CustPreferenceaddress obj) {
	//创建实体
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
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFhasstopcost())){
	 // 是否有停车费用
	    entity.setParkingFee(FossConstants.NO);
	}else {
	 // 是否有停车费用
	    entity.setParkingFee(FossConstants.YES);
	}
	//是：1   否：0
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFismainaddress())){
	 // 是否主地址
	    entity.setMainAddress(FossConstants.NO);
	}else {
	 // 是否主地址
	    entity.setMainAddress(FossConstants.YES);
	}
	//状态---------与Active重复
	// entity.setStatus(status);
	// 偏好起始时间
	entity.setBeginTime(obj.getFstarttime());
	// 偏好结束时间
	entity.setEndTime(obj.getFendtime());
	//是：1   否：0
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFissendtofloor())){
	 // 是否送货上楼
	    entity.setDeliveryUpstairs(FossConstants.NO);
	}else {
	 // 是否送货上楼
	    entity.setDeliveryUpstairs(FossConstants.YES);
	}
	// 0 为有效 2为无效
	if (StringUtil.equals(String.valueOf(obj.getFstatus()), NumberConstants.NUMERAL_S_ZORE)) {
	    // 为有效
	    entity.setActive(FossConstants.ACTIVE);
	}else if (StringUtil.equals(String.valueOf(obj.getFstatus()), NumberConstants.NUMERAL_S_TWO)) {
	    // 0 为有效 2为无效
	    entity.setActive(FossConstants.INACTIVE);
	}else {
	    LOGGER.info("联系人-接送货地址信息的状态有问题啊.........."+obj.getFstatus());
	    throw new CustomerException("联系人-接送货地址信息的状态有问题啊.........."+obj.getFstatus());
	}
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);

	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的客户联系人转化为FOSS联系人实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-26 下午5:09:17
     * @see
     */
    private ContactEntity convertContact(CustCustlinkman obj) {
	//创建客户联系人实体
	ContactEntity entity = new ContactEntity();
	// 性别:男
	if(StringUtil.equals("MAN", obj.getFsex())){
	 // 性别:男
	    entity.setGender(DictionaryValueConstants.CRM_SEX_MAN);
	}else {
	 // 性别:女
	    entity.setGender(DictionaryValueConstants.CRM_SEX_WOMAN);
	}
	
	// 办公电话
	entity.setContactPhone(obj.getFoffertel());
	// 移动电话
	entity.setMobilePhone(obj.getFmobiletel());
	//078816_wp_2014-03-21
	//传真
	entity.setFaxNo(obj.getFax());
	// entity.setFaxNo(obj.get);//传真---------------------CRM中没有此字段
	// 联系人地址
	entity.setAddress(obj.getFlinkmanaddress());
	// 电子邮箱
	entity.setEmail(obj.getFemail());
	// entity.setZipCode(obj.get);//邮编---------------------CRM中没有此字段
	// 生日
	entity.setBirthday(obj.getFborndate());
	// 身份证号
	entity.setIdCard(obj.getFidcard());
	// 个人爱好
	entity.setHobby(obj.getFpersonlove());
	//是否接收邮件---------------------CRM中没有此字段
	// entity.setReceiveEmail(obj.getFr);
	//是否接收短信---------------------CRM中没有此字段
	// entity.setReceiveMessage(obj);
	//是否接收信件---------------------CRM中没有此字段
	// entity.setReceiveLetter(obj.get);
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
	//备注---------------------CRM中没有此字段
	// entity.setNotes(obj.get);
	// 联系人类型
	entity.setContactType(obj.getFlinkmantype());
	// 是否主联系人
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFismainlinkman())){
	 // 是否主联系人
	    entity.setMainContract(FossConstants.NO);
	}else {
	 // 是否主联系人
	    entity.setMainContract(FossConstants.YES);
	}
	// CRM 0 为有效  2为无效
	if (StringUtil.equals(obj.getFstatus(), NumberConstants.NUMERAL_S_ZORE)) {
	    // CRM 0 为有效  2为无效
	    entity.setActive(FossConstants.ACTIVE);
	}else if (StringUtil.equals(obj.getFstatus(), NumberConstants.NUMERAL_S_TWO)) {
	 // CRM 0 为有效  2为无效
	    entity.setActive(FossConstants.INACTIVE);
	}else {
	    LOGGER.info("客户联系人的状态有问题啊.........."+obj.getFstatus());
	    throw new CustomerException("客户联系人的状态有问题啊.........."+obj.getFstatus());
	}
	// 客户编码
	entity.setCustomerCode(obj.getFcustid());
	//联系人编码
	entity.setCode(obj.getFnumber());
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);

	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的客户银行信息转化为FOSS客户开户银行实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-26 下午5:09:17
     * 
     * @see
     */
    private CusAccountEntity convertCusAccount(CustAccount obj) {
	//创建一个对象
	CusAccountEntity entity = new CusAccountEntity();
	// 其他支行名称
//	entity.setOtherBranchBankName(obj.getFsubbankname());
	// 支行名称
	entity.setBranchBankName(obj.getFsubbankname());
	if(StringUtil.isNotBlank(obj.getFsubbanknameid())){
	    //支行编码
	    entity.setBranchBankCode(obj.getFsubbanknameid().trim());
	}else {
	    //支行编码
	    entity.setBranchBankCode(obj.getFsubbanknameid());
	}
	
	// 开户账号
	entity.setAccountNo(obj.getFbankaccount());
	// 开户人姓名
	entity.setAccountName(obj.getFcountname());
	if(StringUtil.isNotBlank(obj.getFbankcityid())){
	    // 开户行城市编码
	    entity.setCityCode(obj.getFbankcityid().trim());
	}else {
	    // 开户行城市编码
	    entity.setCityCode(obj.getFbankcityid());
	}
	if(StringUtil.isNotBlank(obj.getFbankprovinceid())){
	    // 开户行省份编码
	    entity.setProvCode(obj.getFbankprovinceid().trim());
	}else {
	    // 开户行省份编码
	    entity.setProvCode(obj.getFbankprovinceid());
	}
	if(StringUtil.isNotBlank(obj.getFbankid())){
	    // 开户行编码
	    entity.setBankCode(obj.getFbankid().trim());
	}else {
	    // 开户行编码
	    entity.setBankCode(obj.getFbankid());
	}
	
	//开户行名称
	entity.setOpeningBankName(obj.getFbank());
	// 手机号码
	entity.setMobilePhone(obj.getFlinkmanmobile());
	// 账号与客户关系
	entity.setCustomer(obj.getFrelation());
	// 是否默认账号
	entity.setDefaultAccount(obj.getFisdefaultaccount());
	// entity.setNotes(obj.get);//备注
	if (StringUtil.equals(String.valueOf(obj.getFstatus()), NumberConstants.NUMERAL_S_ZORE)) {
	 // CRM 0有效
	    entity.setActive(FossConstants.ACTIVE);
	}else if (StringUtil.equals(String.valueOf(obj.getFstatus()), NumberConstants.NUMERAL_S_TWO)) {
	 // CRM 2 无效
	    entity.setActive(FossConstants.INACTIVE);
	}else {
	    LOGGER.info("客户银行账号的状态有问题啊.........."+obj.getFstatus());
	    throw new CustomerException("客户银行账号的状态有问题啊.........."+obj.getFstatus());
	}
	// 账户性质 对公；对私两种
	entity.setAccountNature(obj.getFaccountnature());
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	//开户省份名称
	entity.setProvinceName(obj.getFbankprovincename());
	//开户城市名称
	entity.setCityName(obj.getFbankcityname());
	//所属客户ID
	entity.setBelongCustom(obj.getFbelongcustom());
	//财务联系人名称
	entity.setFinanceLinkman(obj.getFfinancelinkman());
	//账户用途(代收货款、月发月送、代收货款和月发月送)
	entity.setAccountUse(obj.getFaccountuse());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);

	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的客户接送货地址转化为FOSS客户接送货地址实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-26 下午5:09:17
     * 
     * @see
     */
    private CusAddressEntity convertCusAddress(CustShuttleaddress obj) {
	//创建客户接送货地址对象
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
	// 地址类型(RECEIVE_GOODS 接货地址 SEND_GOODS 发货地址 RECEIVE_SEND_GOODS 接送货地址)---------需要类型编码转换
	entity.setAddressType(obj.getFaddresstype());
	// CRM 0为有效
	// 2为无效
	if (StringUtil.equals(String.valueOf(obj.getFstatus()), NumberConstants.NUMERAL_S_ZORE)) { 
	    // CRM 0为有效
	    // 2为无效
	    entity.setActive(FossConstants.ACTIVE);
	}else if (StringUtil.equals(String.valueOf(obj.getFstatus()), NumberConstants.NUMERAL_S_TWO)) {
	 // CRM 0为有效
	    // 2为无效
	    entity.setActive(FossConstants.INACTIVE);
	}else {
	    LOGGER.info("客户接送货地址的状态有问题啊.........."+obj.getFstatus());
	    throw new CustomerException("客户接送货地址的状态有问题啊.........."+obj.getFstatus());
	}
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	// 地址备注信息
	entity.setAddressRemark(obj.getFaddressRemark());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);

	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的客户合同信息转化为FOSS客户合同信息实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-26 下午5:09:17
     * 
     * @see
     */
    private CusBargainEntity convertCusBargain(CustContract obj) {
	//创建客户合同信息
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
	//对账日期
	entity.setStatementDate(obj.getFrecondate());
	//开发票日期
	entity.setInvoicingDate(obj.getFinvoicdate());
	//结款日期
	entity.setCheckoutDate(obj.getFresultdate());
	// 申请事由
	entity.setApplyReason(obj.getFapplication());
	if(StringUtil.isNotBlank(obj.getFdeptidStandardcode())){
	    // 创建部门标杆编码
	    entity.setUnifiedCode(obj.getFdeptidStandardcode().trim());
	}else {
	    // 创建部门标杆编码
	    entity.setUnifiedCode(obj.getFdeptidStandardcode());
	}
	
	// 创建部门ID
	entity.setApplicateOrgId(obj.getFdeptid().toString());
	// 是否折扣
	if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, obj.getFiddiscount())){
	 // 是否折扣
	    entity.setDiscount(FossConstants.NO);
	}else {
	 // 是否折扣
	    entity.setDiscount(FossConstants.YES);
	}
	// 合同状态---------------与active字段重复
//	entity.setStatus(obj.getFcontractstatus());
	// 合同主体
	entity.setBargainSubject(obj.getFcontractsubject());
	// 注册资金
	entity.setRegisterFunds(obj.getFregicapital());
	if(StringUtil.isNotBlank(obj.getFbeforecontractnum())){
	    // 原合同编号
	    entity.setLastBargain(obj.getFbeforecontractnum().trim());
	}else {
	    // 原合同编号
	    entity.setLastBargain(obj.getFbeforecontractnum());
	}
	if(StringUtil.isNotBlank(obj.getFcontractnum())){
	    // 合同编号
	    entity.setBargainCode(obj.getFcontractnum().trim());
	}else {
	    // 合同编号
	    entity.setBargainCode(obj.getFcontractnum());
	}
	
	// 走货名称
	entity.setTransName(obj.getFgoodsname());
	// 客户全称
	entity.setCustomerName(obj.getFcustcompany());
	// 协议联系人
	entity.setLinkmanId(obj.getFcontactid().toString());
	//结算方式 -----------------------多余字段
	// entity.setChargeMode(obj.get);
	// 优惠类型
	entity.setPreferentialType(obj.getFpreferentialtype());
	//已用额度----------------------CRM里面没有这个字段
	// entity.setUsedAmount();
	//是否超期---------------------CRM里面没有这个字段
	// entity.setOverdue(obj.get);
	//业务日期---------------------CRM里面没有这个字段
	// entity.setBizDate(obj.get);
	//月结合同天数
	entity.setDebtDays(obj.getDebtDays());
	// 合同起始日期
	entity.setBeginTime(obj.getFcontractbegindate());
	// 合同到期日期
	entity.setEndTime(obj.getFcontractenddate());
	// 客户ID
	entity.setCustomerCode(obj.getFcustid());
	// CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
	if (StringUtil.equals(obj.getFcontractstatus(), NumberConstants.NUMERAL_S_ONE)) {
	    // CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
	    entity.setActive(FossConstants.ACTIVE);
	 // CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
	}else if (StringUtil.equals(obj.getFcontractstatus(), NumberConstants.NUMERAL_S_TWO)) {
	 // CRM--合同状态： 0审批中1生效 2失效3待生效4不同意
	    entity.setActive(FossConstants.INACTIVE);
	}else {
	    LOGGER.info("客户合同的状态有问题啊.........."+obj.getFcontractstatus());
	    throw new CustomerException("客户合同的状态有问题啊.........."+obj.getFcontractstatus());
	}
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);
	//异地调货标志
	if(obj.getAsyntakegoodscode()){
	    entity.setAsyntakegoodsCode(FossConstants.YES);
	}else {
	    entity.setAsyntakegoodsCode(FossConstants.NO);
	}
	if(StringUtil.isNotBlank(obj.getHastenfunddeptcode())){
	    //设置 催款部门标杆编码.
	    entity.setHastenfunddeptCode(obj.getHastenfunddeptcode().trim());
	}else {
	    //设置 催款部门标杆编码.
	    entity.setHastenfunddeptCode(obj.getHastenfunddeptcode());
	}
	entity.setPriceVersionDate(obj.getPriceVersionDate());

	//快递结款方式
	entity.setExPayWay(obj.getExPayWay());
	//快递优惠类型
	entity.setExPreferentialType(obj.getExPreferentialType());
	//快递价格版本时间
	entity.setExPriceVersionDate(obj.getExPriceVersionDate());
	//是否单独报价 true false
	if(obj.isfIsSeparateQuote()){
		entity.setIsAloneQuotation(FossConstants.YES);
	}else {
		entity.setIsAloneQuotation(FossConstants.NO);
	}
	if(obj.isIfElecEnjoy()){
		entity.setIfElecEnjoy(FossConstants.YES);
	}else{
		entity.setIfElecEnjoy(FossConstants.NO);
	}
	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的合同-部门关系信息转化为FOSS合同适用部门实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-26 下午5:09:17
     * 
     * @see
     */
    private BargainAppOrgEntity convertBargainAppOrg(CustContractdept obj) {
	//创建一个客户合同适用部门实体
	BargainAppOrgEntity entity = new BargainAppOrgEntity();
	// 对应部门标杆编码
	entity.setUnifiedCode(obj.getFdeptidStandardcode());
	// 开始时间
	entity.setBeginTime(obj.getFbegintime());
	// 结束时间
	entity.setEndTime(obj.getFendtime());
	// 工作流号
	entity.setWorkflowNo(obj.getFworkflowid());
	// OA审批状态（同意、审批中、退回）----该字段CRM已经废弃
//	entity.setOaApproveStatus(obj.getFapprovalstate());
	// 审批人
	entity.setApprover(obj.getFapprovalman());
	// 工作流类型----该字段CRM已经废弃
//	entity.setWorkflowStatus(obj.getFworkflowtype());
	//状态 -------------与active字段重复
	// entity.setStatus(status);
	// 是否归属部门---------需要查清状态类型进行转换
	entity.setOrgBelonging(obj.getFisdept());
	// CRM--状态   1正常，0已作废
	if (StringUtil.equals(obj.getFstate(), NumberConstants.NUMERAL_S_ZORE)) {// CRM
	    // CRM--状态   1正常，0已作废
	    entity.setActive(FossConstants.INACTIVE);
	}else if(StringUtil.equals(obj.getFstate(), NumberConstants.NUMERAL_S_ONE)){
	 // CRM--状态   1正常，0已作废
	    entity.setActive(FossConstants.ACTIVE);
	}else {
	    LOGGER.info("客户合同适用部门的状态有问题啊.........."+obj.getFstate());
	    throw new CustomerException("客户合同适用部门的状态有问题啊.........."+obj.getFstate());
	}
	// 合同ID
	entity.setBargainId(obj.getFcontractid());
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//创建用户
	entity.setCreateUser(USER_CODE); 
	//修改用户
	entity.setModifyUser(USER_CODE);

	return entity;
    }

    /**
     * <p>
     * 把CRM同步过来的客户优惠信息转化为FOSS客户优惠信息实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-26 下午5:09:17
     * 
     * @see
     */
    private PreferentialEntity convertPreferential(CustPreferential obj) {
	//创建客户优惠信息对象
	PreferentialEntity entity = new PreferentialEntity();
	/**
	 * @author 218392 zhangyongxue
	 * 进仓派送费折扣
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
	//--------------CRM信息缺少是否启用
	// entity.setActive();
	// 在CRM中fid
	entity.setCrmId(obj.getFid());
	// CRM创建时间
	entity.setCreateDate(obj.getFcreatetime());
	// 修改时间
	entity.setModifyDate(obj.getFlastupdatetime());
	//设置 执行起始日期.
	entity.setEffectiveDate(obj.getEffectiveDate());
	//设置 执行到期日期.
	entity.setExpirationDate(obj.getExpirationDate());
	 //创建用户
	entity.setCreateUser(USER_CODE);
	//修改用户
	entity.setModifyUser(USER_CODE);
	//优惠所属类型(取值LTT或者EXPRESS,分别表示零担和快递)
	entity.setFtype(obj.getFtype());
	//超重货操作费折扣
	entity.setOverweightOperatDiscount(obj.getFoverWeightRate());
	//包装费折扣
	entity.setPackingChargesDiscount(obj.getFpackingRate());
	//签收单返单费折扣
	entity.setSingleSignDiscount(obj.getFsignBillRate());
	//代收最低手续费
	entity.setLowestCharge(obj.getFlowestCharge());
	//重泡比值
	entity.setWeightBubbleRate(obj.getFexpweightbubblerate());
	//大件上楼费折扣
	entity.setBigUprate(obj.getFbiguprate());
	//续重最低费率
	entity.setContinueHeavyLowestRate(obj.getConHeavyLowestRate());
	//续重折扣
	entity.setContinueHeavyDiscount(obj.getConHeavyDisCount());
	//快递返货保价折扣类型(原折扣  OLD_PREFE 新折扣  NEW_PREFE 单票固定价 FIXED_PRICE 原价 OLD_PRICE)
	entity.setExpBackCollPreType(obj.getExpBackCollPreType());
	//快递返货运费折扣类型(原折扣  OLD_PREFE 新折扣  NEW_PREFE 单票固定价 FIXED_PRICE 原价 OLD_PRICE)
	entity.setExpBackFreghtType(obj.getExpBackPreType());
	//返货运费固定值
	entity.setBackFreghtFixed(obj.getCarriageFixed());
	//返货保价固定值
	entity.setBackCollFixed(obj.getCollFixed());
	//单票代收手续费
	entity.setSinTicketCollCharge(obj.getOneTickCollCharge());
	//单票保价手续费
	entity.setSinTicketSurePriceCharge(obj.getEnsuredPriceCharge());
	//单票包装费
	entity.setSinTicketPackCharge(obj.getOneTickPackCharge());
	//是否精准包裹1 Y， 0 N
	if(obj.getIfAccurateElect()!=null&&"1".equals(obj.getIfAccurateElect())){
		entity.setIsAccuratePackage(FossConstants.YES);
	}else{
		entity.setIsAccuratePackage(FossConstants.NO);
	}
	//零担重泡比参数
	entity.setLttWeightBubbleRate(obj.getLttWeightBubbleRate());
	//是否代收退费  true 是 false 否
	if(obj.isCollReturnCharge()){
		entity.setCollReturnCharge(FossConstants.YES);
	}else {
		entity.setCollReturnCharge(FossConstants.NO);
	}
	return entity;
    }
    
    /**
     * <p>
     * 把CRM同步过来的客户发票标记信息转化为FOSS客户发票标记信息实体
     * </p>.
     *
     * @param obj 
     * 
     * @return 
     * 
     * @author 132599-foss-shenweihua
     * 
     * @date 2013-11-22 下午4:59:17
     * 
     * @see
     */
    private CusContractTaxEntity convertCusContractTax(CustContractTax obj){
    	// 创建客户发票标记信息对象
    	CusContractTaxEntity entity = new CusContractTaxEntity();
    	
    	// 发票标记信息对应的合同ID
    	entity.setBargainCrmId(obj.getFcontractid());
    	//在CRM中fid
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
    	 //创建用户
    	entity.setCreateUser(USER_CODE);
    	//修改用户
    	entity.setModifyUser(USER_CODE);
    	
    	return entity;
    }
    
    /**
     * <p>同步散客信息</p>.
     * 此方法由于CRM2期固定客户和散客合并，已经废弃不用
     * 
     * @param custInfo 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-6-6 下午9:05:38
     * @see
     */
    @Transactional
    public OrigCustSyncResponse syncNonfixedCustomer(ScatterCustInfo custInfo){
	// 定义响应对象
	OrigCustSyncResponse response = new OrigCustSyncResponse();

	// 同步信息处理结果集合
	List<OrigCustSyncRequestProcessDetail> detailList = new ArrayList<OrigCustSyncRequestProcessDetail>();
	// 同步成功总数
	int successCount = 0;
	// 同步失败总数
	int failedCount = 0;
	// 获取同步过来的数据
	// ScatterCustInfo custInfo = request.getScatterCustInfo();
	if (null == custInfo) {
		 return response;
	}
	    // 操作结果信息
	    OrigCustSyncRequestProcessDetail resultDetail = new OrigCustSyncRequestProcessDetail();
	    // 散客信息实体
	    NonfixedCustomerEntity entity = convertCustomer(custInfo);

	    // 获取开户银行信息集合
	    List<ScatterCustBankInfo> bankInfoList = custInfo.getFbank();
	    // 散客ID
	    resultDetail
		    .setFscatterid(String.valueOf(custInfo.getFscatterid()));
	    // 设置操作类型
	    resultDetail.setOperateType(custInfo.getOperateType());
	    // 操作类型验证
	    if (StringUtil.equals(NumberConstants.NUMERAL_S_ONE,
				custInfo.getOperateType())) {// 新增
			try {
				int result = nonfixedCustomerService
						.updateNonfixedCustomer(entity);
				if (result > 0) {
					successCount = saveScatterCustInfo(custInfo, successCount,
						resultDetail, bankInfoList);
				} else {
					// 失败记录数
					failedCount++;
					// 新增失败
					resultDetail.setResult(false);
					// 设置原因
					resultDetail.setReason(null);
				}
			} catch (CustomerException e) {
				// 异常日志
				LOGGER.error(e.getMessage(), e);
				// 失败记录数
				failedCount++;
				// 新增失败
				resultDetail.setResult(false);
				// 保存失败原因
				resultDetail.setReason(e.getMessage());
			}
			// 执行修改操作
		} else if (StringUtil.equals(NumberConstants.NUMERAL_S_TWO,
		    custInfo.getOperateType())) {// 修改
		try {
		    LOGGER.info("同步临欠散客信息开始，执行修改操作！");
		    int result = nonfixedCustomerService
			    .updateNonfixedCustomer(entity);
		    if (result > 0) {
		    	successCount = updateScatterCustInfo(custInfo, successCount,
					resultDetail, bankInfoList);
		    } else {
			// 失败条数
			failedCount++;
			// 失败
			resultDetail.setResult(false);
			// 设置原因
			resultDetail.setReason(null);
		    }
		} catch (CustomerException e) {
		    // 日志
		    LOGGER.error(e.getMessage(), e);
		    // 失败条数
		    failedCount++;
		    // 失败
		    resultDetail.setResult(false);
		    // 保存失败原因
		    resultDetail.setReason(e.getMessage());
		}
		// 删除   已废除  现在删除修改全部用修改      BUG-50862 1 新增2修改
	    } else if (StringUtil.equals(NumberConstants.NUMERAL_S_THREE,
			    custInfo.getOperateType())) {
		try {
		    // 根据开户账号作废散客信息
		    int result = nonfixedCustomerService
			    .deleteNonfixedCustomerByCode(
				    String.valueOf(entity.getCrmId()),
				    USER_CODE);
		    if (result > 0) {
		    	successCount = deleteScatterCustBankInfo(successCount,
					resultDetail, bankInfoList);
		    } else {
			// 失败条数
			failedCount++;
			// 作废失败
			resultDetail.setResult(false);
			// 设置原因
			resultDetail.setReason(null);
		    }
		} catch (CustomerException e) {
		    // 异常日志
		    LOGGER.error(e.getMessage(), e);
		    // 失败条数
		    failedCount++;
		    // 作废失败
		    resultDetail.setResult(false);
		    // 设置失败原因
		    resultDetail.setReason(e.getMessage());
		}
	    }
	    // 设置结果集合
	    detailList.add(resultDetail);
	
	// 添加所有结果
	response.getDetail().addAll((detailList));
	// 设置失败数
	response.setFailCount(failedCount);
	// 设置成功数
	response.setSuccessCount(successCount);
	// 记录日志
	LOGGER.info("同步散客信息和临欠散客开户银行账户信息结束");
	// 返回响应消息
	return response;
    }

	private int deleteScatterCustBankInfo(int successCount,
			OrigCustSyncRequestProcessDetail resultDetail,
			List<ScatterCustBankInfo> bankInfoList) {
		// 成功
		if (CollectionUtils.isNotEmpty(bankInfoList)) {
		    LOGGER.info("同步临欠散客开户银行账户信息开始，执行删除操作！");
		    for (ScatterCustBankInfo bankInfo : bankInfoList) {
			// 根据开户账号作废临欠散客开户银行账户
			nonfixedCusAccountService
				.deleteCusAccountByCode(
					bankInfo.getFid(), USER_CODE);
		    }
		}
		// 成功数
		successCount++;
		// 作废成功
		resultDetail.setResult(true);
		return successCount;
	}

	private int updateScatterCustInfo(ScatterCustInfo custInfo,
			int successCount, OrigCustSyncRequestProcessDetail resultDetail,
			List<ScatterCustBankInfo> bankInfoList) {
		// 成功
		if (CollectionUtils.isNotEmpty(bankInfoList)) {
		    LOGGER.info("同步临欠散客开户银行账户信息开始，执行修改操作！");
		    for (ScatterCustBankInfo bankInfo : bankInfoList) {
			NonfixedCusAccountEntity cusAccount = convertCusAccount(bankInfo);

			// 设置银行账号所属散客CRM_ID
			cusAccount.setNoncustCrmId(custInfo
				.getFscatterid());
			try {
			    // 修改信息
			    nonfixedCusAccountService
				    .updateCusAccount(cusAccount);
			} catch (CustomerException e) {
			    // 异常信息日志
			    LOGGER.error(e.getMessage(), e);
			}
		    }
		}
		// 成功条数
		successCount++;
		// 成功
		resultDetail.setResult(true);
		return successCount;
	}

	private int saveScatterCustInfo(ScatterCustInfo custInfo, int successCount,
			OrigCustSyncRequestProcessDetail resultDetail,
			List<ScatterCustBankInfo> bankInfoList) {
		// 新增成功
			if (CollectionUtils.isNotEmpty(bankInfoList)) {
				LOGGER.info("同步临欠散客开户银行账户信息开始，执行新增操作！");
				// 迭代循环
				for (ScatterCustBankInfo bankInfo : bankInfoList) {
					// 临欠散客的开户银行信息赋值
					NonfixedCusAccountEntity cusAccount = convertCusAccount(bankInfo);

					// 设置银行账号所属散客CRM_ID
					cusAccount
							.setNoncustCrmId(custInfo.getFscatterid());

					// 保存信息
					nonfixedCusAccountService
							.updateCusAccount(cusAccount);

				}
			}
			// 失败条数
			successCount++;
			// 新增成功
			resultDetail.setResult(true);
		return successCount;
	}
    
    /**
     * <p>散客信息转换</p>.
     *此方法由于CRM2期固定客户和散客合并，已经废弃不用
     *
     * @param custInfo 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-6-6 下午9:30:46
     * @see
     */
    private NonfixedCustomerEntity convertCustomer(ScatterCustInfo custInfo){
	
	// 散客信息实体
	NonfixedCustomerEntity entity = new NonfixedCustomerEntity();

	// 散客ID
	entity.setCrmId(custInfo.getFscatterid());
	// 散客类型
	entity.setType(custInfo.getFscattertype());
	// 临欠额度
	entity.setTempArrears(custInfo.getFprocredit());
	// 联系人地址
	entity.setContactAddress(custInfo.getContactAddress());
	if (StringUtil.isNotBlank(custInfo.getCustCode())) {
	    // 客户编码
	    entity.setCustCode(custInfo.getCustCode().trim());
	} else {
	    // 客户编码
	    entity.setCustCode(custInfo.getCustCode());
	}

	// 客户名称
	entity.setCustName(custInfo.getCustName());
	// 手机号码
	entity.setMobilePhone(custInfo.getMobilePhone());
	// 电话
	entity.setTelephone(custInfo.getTelephone());
	// 联系人名称
	entity.setLinkmanName(custInfo.getContactName());
	// 会员号
	entity.setMemberNum(custInfo.getCustNumber());
	if (StringUtil.isNotBlank(custInfo.getDeptStandardcode())) {
	    // 散客所属部门标杆编码
	    entity.setUnifiedCode(custInfo.getDeptStandardcode().trim());
	} else {
	    // 散客所属部门标杆编码
	    entity.setUnifiedCode(custInfo.getDeptStandardcode());
	}
	LOGGER.info("散客是否财务作废（转换前）： " + custInfo.isFinanceCancel());
	// 是否财务作废.
	if (custInfo.isFinanceCancel()) {
	    entity.setIsDelete(FossConstants.YES);
	} else {
	    entity.setIsDelete(FossConstants.NO);
	}
	LOGGER.info("散客是否财务作废(转换后)： " + entity.getIsDelete());
	// 设置修改人
	entity.setModifyUser(USER_CODE);
	// 设置创建人
	entity.setCreateUser(USER_CODE);
	//设置创建时间   散客是按同步的时间创建   固定客户是按ＣＲＭ那边的时间
	entity.setCreateDate(new Date());
	//设置修改时间
	entity.setModifyDate(new Date());
	// 状态转换:散客的状态：0表示有效 2为无效
	if (StringUtil.equals(NumberConstants.NUMERAL_S_ZORE,
		custInfo.getCustStatus())) {
	    // 有效
	    entity.setActive(FossConstants.ACTIVE);
	} else {
	    // 无效
	    entity.setActive(FossConstants.INACTIVE);
	}
	
	return entity;
    }
    
    /**
     * <p>
     * 临欠散客的开户银行信息赋值
     * </p>
     * 此方法由于CRM2期固定客户和散客合并，已经废弃不用
     * 
     * @param obj
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-28 下午7:12:08
     * @see
     */
    private NonfixedCusAccountEntity convertCusAccount(ScatterCustBankInfo obj) {
	// 临欠散客的开户银行账户信息
	NonfixedCusAccountEntity entity = new NonfixedCusAccountEntity();
	// 其他支行名称
	entity.setOtherBranchBankName(obj.getFsubbankname());
	// 开户账号
	entity.setAccountNo(obj.getFbankaccount());
	// 开户人姓名
	entity.setAccountName(obj.getFcountname());
	// 开户行城市编码
	entity.setCityCode(obj.getBankcityCode());
	// 开户行省份编码
	entity.setProvCode(obj.getBankProvinceCode());
	// 开户行编码
	entity.setBankCode(obj.getBankCode());
	// 手机号码
	entity.setMobilePhone(obj.getFlinkmanmobile());
	// 账号与客户关系
	entity.setCustomer(obj.getFrelation());
	// 描述
	entity.setNotes(obj.getFdesciption());
	// 支行编码
	entity.setBranchBankCode(obj.getSubbankCode());
	// 客户行省份名称
	entity.setProvinceName(obj.getBankProvinceName());
	// 客户行城市名称
	entity.setCityName(obj.getBankCityName());
	// 开户行名称
	entity.setOpeningBankName(obj.getBankName());
	// 支行名称
	entity.setBranchBankName(obj.getSubbankName());
	// 账户性质
	entity.setAccountNature(obj.getAccountType());
	//设置CRM_ID
	entity.setCrmId(obj.getFid());
	// 是否默认账号
	if (obj.isFisdefaultaccount()) {
	    // 是否默认账号
	    entity.setDefaultAccount(FossConstants.YES);
	} else {
	    // 是否默认账号
	    entity.setDefaultAccount(FossConstants.NO);
	}
	
	//散客银行账号的状态：0：有效；1：审批中；2：无效
	if (StringUtil.equals(NumberConstants.NUMERAL_S_ZERE, obj.getStatus())) {
	    // 设置账户有效
	    entity.setActive(FossConstants.ACTIVE);
	} else if (StringUtil.equals(NumberConstants.NUMERAL_S_TWO,
		obj.getStatus())) {
	    // 设置账户无效
	    entity.setActive(FossConstants.INACTIVE);
	} else {
	    LOGGER.info("传入的银行账户状态有问题啊。。。。。。。。。。。。。" + obj.getStatus());
	    throw new CustomerException("", "传入的银行账户状态有问题啊。。。。。。。。。。。。。"
		    + obj.getStatus());
	}
	//创建时间
	entity.setCreateDate(new Date());
	// 修改时间
	entity.setModifyDate(new Date()); 
	// 设置创建人
	entity.setCreateUser(USER_CODE);
	//设置修改人
	entity.setModifyUser(USER_CODE);
	//返回转换好的实体
	return entity;
    }


}
