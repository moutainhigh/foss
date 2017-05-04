package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.inteface.domain.crm.CreateScatterRequest;
import com.deppon.esb.inteface.domain.crm.ScatterInfo;
import com.deppon.esb.pojo.transformer.jaxb.CreateScatterRequestTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFailNonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISynNonfixedCustomerToCrmSerivce;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FailNonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerAssociatedInformationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CustomerException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
//import com.deppon.esb.api.ESBJMSAccessor;
//import com.deppon.esb.api.domain.AccessHeader;
//import com.deppon.esb.core.exception.ESBException;

/**
 * 配合CRM2期，FOSS需要实现快递开单，散客数据在FOSS系统中创建 然后同步给CRM系统，CRM系统对数据进行拆分加工处理后再回传给FOSS
 * 
 * @author 078816
 * @date 2014-03-19
 * 
 */
public class SynNonfixedCustomerToCrmSerivce implements
		ISynNonfixedCustomerToCrmSerivce {
	/**
	 * 日志信息
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SynNonfixedCustomerToCrmSerivce.class);
	/**
	 * 服务编码
	 */
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECIEVE_NONFIXEDCUSTOMER";
	
	
	/**
	 * 数字9
	 */
	private static final int NUMBER_ONE=1;
	
	/**
	 * 数字9
	 */
	private static final int NUMBER_TWO=2;
	
	/**
	 * 数字9
	 */
	private static final int NUMBER_EIGHT=8;
	
	/**
	 * 数字9
	 */
	private static final int NUMBER_NINE=9;
	/**
	 * 数字9
	 */
	private static final int NUMBER_TEN=10;

	// 客户主信息接口
	private ICustomerService customerService;

	// 客户联系人接口
	private ICusContactService cusContactService;

	// 客户接送货地址接口
	private ICusAddressService cusAddressService;

	// 联系人接送货地址偏好接口
	private IContactAddressService contactAddressService;
	
	//推送失败散客接口
	IFailNonfixedCustomerService failNonfixedCustomerService;

	/**
	 * 如果客户数据不存在，就在FOSS系统创建对应的散客信息
	 * 
	 * auther:wangpeng_078816 date:2014-3-19
	 * 
	 */
	@Override
	@Transactional
	public NonfixedCustomerAssociatedInformationEntity createNonfixedCustomer(
			NonfixedCustomerAssociatedInformationEntity entity) {
		//313353 sonar
		this.sonarSplitOne(entity);
		
		// 手机号码
		String mobilePhone = entity.getCellPhone(); // 去掉trim()方法，如果为空会报错，isEmpty已经判断了空格的情况
		// 固定电话
//		String phone = entity.getMobile(); // 去掉trim()方法，如果为空会报错，isEmpty已经判断了空格的情况
		
		//联系人名称
//		String conName=entity.getCustomerName();
		// 客户属性
		String custAtt = entity.getCustType();

		// 记录插入客户数据的行数
		int custNum = 0;
		// 记录插入客户联系数据的行数
		int linkManNum = 0;
	

		if (StringUtils.equals(custAtt,
				DictionaryValueConstants.CUSTOMER_SC_CUSTOMER)) {
//			if (StringUtils.isEmpty(mobilePhone) && StringUtils.isEmpty(phone)) {
//				throw new CustomerException("手机号码或者固定电话至少有一个存在");
//			}else if(StringUtils.isNotEmpty(mobilePhone)&&(mobilePhone.contains("*")||mobilePhone.contains("-"))){
//				throw new CustomerException("手机号码格式错误");
//			}
//
//			//根据手机号或者固话查找联系人
//			ContactEntity dto=cusContactService.queryCusContactByMobileOrPhone(mobilePhone,phone,conName);
//			if (null != dto) {
//				throw new CustomerException(
//						"已经存在于系统中有效联系人：" + dto.getContactName()
//						+ "，不能重复使用，手机号或者固定电话必须唯一");
//
//			}
			if (StringUtils.isNotEmpty(mobilePhone)) {
				ContactEntity dto = cusContactService
						.queryCusContactByMobile(mobilePhone);
				if (null != dto) {
					throw new CustomerException("手机号码" + mobilePhone
							+ "已经存在于系统中有效联系人：" + dto.getContactName()
							+ "下，不能重复使用，手机号码必须唯一");
				}
			}
			// 生成散客客户编码
			String nonFixedCode = this.createNonFixedCustomerNum();
			// while(StringUtils.isEmpty(nonFixedCode)){
			// nonFixedCode = this.createNonFixedCustomerNum();
			// if(!StringUtils.isEmpty(nonFixedCode)){
			// CustomerDto dto =
			// customerService.queryCustomerDtoByCustCode(nonFixedCode);
			// if(null != dto){
			// nonFixedCode = "";
			// continue;
			// }else{
			// break;
			// }
			// }
			// }
			entity.setCustomerCode(nonFixedCode);
			// 封装FOSS自身系统的散客对象
			CustomerEntity custEntity = this.convertCustomer(entity);
			// while(null == custEntity){
			// custEntity = this.convertCustomer(entity);
			// if(null != custEntity){
			// break;
			// }
			// }
			// 获得客户的fossId
			String custFossId = custEntity.getId();
			custEntity.setCusCode(nonFixedCode);

			// 封装FOSS自身系统的联系人对象
			ContactEntity linkManEntity = this.convertContact(entity);
			// while(null == linkManEntity){
			// linkManEntity = this.convertContact(entity);
			// if(null != linkManEntity){
			// break;
			// }
			// }
			linkManEntity.setOwnCustId(custFossId);

			// 封装FOSS自身接送货地址信息对象
			CusAddressEntity cusAddEntity = this.convertCusAddress(entity);
			// while(null == cusAddEntity){
			// cusAddEntity = this.convertCusAddress(entity);
			// if(null != cusAddEntity){
			// break;
			// }
			// }
			cusAddEntity.setOwnCustId(custFossId);

			// 将创建的FOSS自身的散客信息插入到相应的表里
			// 插入新增散客信息
			try {
				// int i = 0;
				custNum = customerService.addNonfixedCustomer(custEntity);
				// while(custNum == 0){
				// custNum = customerService.addNonfixedCustomer(custEntity);
				// if(custNum > 0){
				// break;
				// }
				// i++;
				// if(i == 5){
				// break;
				// }
				// }

				// int j = 0;
				// 插入新增散客联系人信息
				linkManNum = cusContactService
						.addNonfixedCusContact(linkManEntity);
				// while(linkManNum == 0){
				// linkManNum =
				// cusContactService.addNonfixedCusContact(linkManEntity);
				// if(linkManNum > 0){
				// break;
				// }
				// j++;
				// if(j == 5){
				// break;
				// }
				// }
			} catch (CustomerException e) {
				logger.error("插入散客数据异常：" + e.getMessage());
				throw new CustomerException(e.getMessage());
			}

			// 插入新增散客接送货地址信息
			if (StringUtils.isNotEmpty(cusAddEntity.getAddress())
					|| StringUtils.isNotEmpty(cusAddEntity.getProvinceName())) {
				cusAddressService.addNonfixedCusAddress(cusAddEntity);
			}

			// 将创FOSS建的散客信息通过接口同步给CRM系统
			entity.setId(custFossId);
			entity.setCreateDate(new Date());
			entity.setCreateUser(FossUserContext.getCurrentInfo().getUserName());
		}

		 if(custNum >0  && linkManNum >0
		 && StringUtils.equals(custAtt,
		 DictionaryValueConstants.CUSTOMER_SC_CUSTOMER)){
		 //调用同步数据接口
		 List<NonfixedCustomerAssociatedInformationEntity> list = new
		 ArrayList<NonfixedCustomerAssociatedInformationEntity>();
		 list.add(entity);
		 this.syncNonfixedCustomerDataToCrm(list);
		 return entity;
		 }
		 if(custNum == 0 && linkManNum == 0
		 && StringUtils.equals(custAtt,
		 DictionaryValueConstants.CUSTOMER_SC_CUSTOMER)){
		 return null;
		 }
		 if(StringUtils.equals(custAtt,
		 DictionaryValueConstants.CUSTOMER_PC_CUSTOMER)){
		
		 //调用同步数据接口
		 List<NonfixedCustomerAssociatedInformationEntity> list = new
		 ArrayList<NonfixedCustomerAssociatedInformationEntity>();
		 list.add(entity);
		 this.syncNonfixedCustomerDataToCrm(list);
		 return entity;
		 }
//		if (!StringUtils.equals(custAtt,
//				DictionaryValueConstants.CUSTOMER_PC_CUSTOMER)
//				|| (custNum == 0 && linkManNum == 0)) {
//			return null;
//		} else {
//			List<NonfixedCustomerAssociatedInformationEntity> list = new ArrayList<NonfixedCustomerAssociatedInformationEntity>();
//			list.add(entity);
//			this.syncNonfixedCustomerDataToCrm(list);
//			return entity;
//		}
		 return null;

	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(NonfixedCustomerAssociatedInformationEntity entity) {
		if (null == entity) {
			throw new CustomerException("开单传入的客户对象为空！");
		}
	}
	

	/**
	 * 将创建的散客信息同步给CRM系统
	 * 
	 * auther:wangpeng_078816 date:2014-3-19
	 * 
	 */
	@Override
	public void syncNonfixedCustomerDataToCrm(
			List<NonfixedCustomerAssociatedInformationEntity> list) {
		// 创建传递散客的请求对象
		CreateScatterRequest request = new CreateScatterRequest();
		List<ScatterInfo> infoList = request.getScatterInfos();
		StringBuffer codes = new StringBuffer();
		String scCodes="";
		// 封装转化散客实体对象
		for (NonfixedCustomerAssociatedInformationEntity entity : list) {
			ScatterInfo info = new ScatterInfo();
			info.setFossId(entity.getId());
			info.setCustName(entity.getCustomerName());
			info.setCustNumber(entity.getCustomerCode());
			info.setLinkmanName(entity.getLinkManName());
			// 手机号码
			info.setMobilephone(entity.getCellPhone());
			// 固定电话
			info.setTelephone(entity.getMobile());
			info.setLinkmanAdress(entity.getAddress());
			info.setCustAttribute(entity.getCustomerAttributes());
			info.setCreateUser(entity.getCreateUser());
			info.setCreateTime(entity.getCreateDate());
			info.setBusinessType(entity.getBusinessType());
			info.setStandardCode(entity.getUnifiedCode());
			info.setProvinceCode(entity.getProCode());
			info.setCityCode(entity.getCityCode());
			info.setAreaCode(entity.getCountyCode());
			info.setLinkmanAdressRemark(entity.getCustAddrRemark());
			infoList.add(info);
			scCodes=scCodes+entity.getCustomerCode()+",";
			codes.append(entity.getCustomerCode()).append(
					SymbolConstants.EN_COMMA);
		}
		String businessCodes = codes.toString();
		String businessId = businessCodes.substring(0,
				businessCodes.lastIndexOf(","));
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(businessId);
		accessHeader.setBusinessDesc1("发送开单创建的散客数据到CRM系统");
		accessHeader.setBusinessDesc2(UUIDUtils.getUUID());
		accessHeader.setVersion("0.1");
		try {
			CreateScatterRequestTrans trans = new CreateScatterRequestTrans();
			logger.info("开始进行发送散客数据到CRM系统的操作：\n" + trans.fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			logger.info("结束发送散客数据到CRM系统的操作：\n" + trans.fromMessage(request));
		} catch (Exception e) {
			String errorMessage ="";
			if(e!=null){
				errorMessage += e.toString()+",";
			}
			if(e.getCause()!=null){
				errorMessage+=e.getCause().toString()+",";
			}
			if(e.getStackTrace()!=null){
				errorMessage+= e.getStackTrace().toString();
			}
			logger.info("推送散客失败异常：" + errorMessage);
			if(errorMessage.length()>3000){
				errorMessage=errorMessage.substring(0,3000);
			}
			for (NonfixedCustomerAssociatedInformationEntity entity : list) {
				FailNonfixedCustomerEntity dto=new FailNonfixedCustomerEntity
						(entity.getCustomerCode(), entity.getCustomerName(), entity.getLinkManCode(), entity.getLinkManName(), 
								entity.getAddress(),  entity.getCellPhone(),entity.getMobile(), entity.getProCode(), entity.getCityCode(), 
								entity.getCountyCode(), entity.getUnifiedCode(), entity.getBusinessType(), 
								entity.getCustomerAttributes(), entity.getOneLevelIndustry(), 
								entity.getTwoLevelIndustry(), entity.getOwnCustId(), 
								entity.getCustAddrRemark(), entity.getCustType(), new Date(), 0);
				dto.setErrorInfo(errorMessage);
				failNonfixedCustomerService.insert(dto);
			
			}
		}
	}

	/**
	 * 封装创建的FOSS散客信息实体类
	 * 
	 * auther:wangpeng_078816 date:2014-3-20
	 * 
	 */
	private CustomerEntity convertCustomer(
			NonfixedCustomerAssociatedInformationEntity entity) {
		if (null == entity) {
			throw new BusinessException("传入的对象为空");
		}

		UserEntity user = FossUserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();

		CustomerEntity custEntity = new CustomerEntity();
		custEntity.setId(UUIDUtils.getUUID());
		custEntity.setVirtualCode(custEntity.getId());
		custEntity.setCusCode(entity.getCustomerCode());
		custEntity.setName(entity.getCustomerName());
		custEntity.setUnifiedCode(entity.getUnifiedCode());
		custEntity
				.setCustomserNature(DictionaryValueConstants.CUSTOMER_SC_CUSTOMER);
		custEntity.setActive(FossConstants.ACTIVE);
		custEntity.setCreateDate(new Date());
		custEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		custEntity.setCreateUser(userCode);// FossUserContext.getCurrentUser().getUserName()
		custEntity.setModifyUser(userCode);// FossUserContext.getCurrentUser().getUserName()
		custEntity.setIsDelete(FossConstants.INACTIVE);
		custEntity.setBusinessType(entity.getBusinessType());
		custEntity.setAddress(entity.getAddress());
		custEntity.setCustAddrRemark(entity.getCustAddrRemark());
		return custEntity;
	}

	/**
	 * 封装创建的FOSS散客联系人信息实体类
	 * 
	 * auther:wangpeng_078816 date:2014-3-20
	 * 
	 */
	private ContactEntity convertContact(
			NonfixedCustomerAssociatedInformationEntity entity) {
		if (null == entity) {
			throw new BusinessException("传入的对象为空");
		}
		ContactEntity contactEntity = new ContactEntity();
		contactEntity.setId(UUIDUtils.getUUID());
		contactEntity.setMobilePhone(entity.getCellPhone());
		contactEntity.setContactPhone(entity.getMobile());
		contactEntity.setContactName(entity.getLinkManName());
		contactEntity.setActive(FossConstants.ACTIVE);
		contactEntity.setCreateDate(new Date());
		contactEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		contactEntity.setCreateUser(FossUserContext.getCurrentUser()
				.getUserName());
		contactEntity.setModifyUser(FossUserContext.getCurrentUser()
				.getUserName());
		return contactEntity;
	}

	/**
	 * 封装创建的FOSS散客接送货地址信息实体类
	 * 
	 * auther:wangpeng_078816 date:2014-3-20
	 * 
	 */
	private CusAddressEntity convertCusAddress(
			NonfixedCustomerAssociatedInformationEntity entity) {
		if (null == entity) {
			throw new BusinessException("传入的对象为空");
		}
		CusAddressEntity cusAddressEntity = new CusAddressEntity();
		cusAddressEntity.setId(UUIDUtils.getUUID());
		cusAddressEntity.setAddress(entity.getAddress());
		cusAddressEntity.setProvinceName(entity.getProCode());
		cusAddressEntity.setCityCode(entity.getCityCode());
		cusAddressEntity.setCountyCode(entity.getCountyCode());
		cusAddressEntity.setActive(FossConstants.ACTIVE);
		cusAddressEntity.setCreateDate(new Date());
		cusAddressEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		cusAddressEntity.setCreateUser(FossUserContext.getCurrentUser()
				.getUserName());
		cusAddressEntity.setModifyUser(FossUserContext.getCurrentUser()
				.getUserName());
		return cusAddressEntity;
	}

	/**
	 * 开单客户如果不存在，就现在FOSS系统中新增一个散客,生产散客编码
	 * 
	 * @author wangpeng_078816
	 * @date 2014-03-12
	 * @param entity
	 * 
	 */
	private String createNonFixedCustomerNum() {
		StringBuffer str = new StringBuffer();
		str.append("F");
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
		str.append(dt.format(new Date()));

		str.append(this.createRandomData());

		String randomCode = str.toString();

		//CustomerEntity entity = null;
		// entity = customerService.queryCustInfoByCode(randomCode);
		//验证散客是否存在
		boolean checkCustomer=customerService.queryExistsCustInfoByCode(randomCode);
		while (checkCustomer) {
			randomCode = randomCode.substring(0, NUMBER_NINE) + this.createRandomData();
			//entity = customerService.queryCustInfoByCode(randomCode);
			checkCustomer= customerService.queryExistsCustInfoByCode(randomCode);
		}
		return randomCode;

	}
	

	/**
	 * 生成一个八位的随机数
	 * 
	 * @author wangpeng_078816
	 * @date 2014-03-12
	 * @param entity
	 * 
	 */
	private String createRandomData() {
		Random rm = new Random();
		// 获得随机数
		double pross = (NUMBER_ONE + rm.nextDouble()) * Math.pow(NUMBER_TEN, NUMBER_EIGHT);
		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);
		// 返回固定的长度的随机数
		String randomData = fixLenthString.substring(NUMBER_TWO, NUMBER_TEN);

		return randomData;
	}
	

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public ICusContactService getCusContactService() {
		return cusContactService;
	}

	public void setCusContactService(ICusContactService cusContactService) {
		this.cusContactService = cusContactService;
	}

	public ICusAddressService getCusAddressService() {
		return cusAddressService;
	}

	public void setCusAddressService(ICusAddressService cusAddressService) {
		this.cusAddressService = cusAddressService;
	}

	public IContactAddressService getContactAddressService() {
		return contactAddressService;
	}

	public void setContactAddressService(
			IContactAddressService contactAddressService) {
		this.contactAddressService = contactAddressService;
	}

	public void setFailNonfixedCustomerService(
			IFailNonfixedCustomerService failNonfixedCustomerService) {
		this.failNonfixedCustomerService = failNonfixedCustomerService;
	}
	
	
}
