package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerCircleRelationDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.IsCustomerCircleDto;

/**
 * 
 * (为接送货准备)
 * @author 308861 
 * @date 2017-1-11 下午2:06:20
 * @since
 * @version
 */
public class CustomerCircleRelationNewService implements ICustomerCircleRelationNewService{

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CustomerCircleRelationNewService.class);

    /**
     * 注入customerCircleRelationDao
     */
    private ICustomerCircleRelationDao customerCircleRelationDao;
    //setter
    public void setCustomerCircleRelationDao(
			ICustomerCircleRelationDao customerCircleRelationDao) {
		this.customerCircleRelationDao = customerCircleRelationDao;
	}

    /**
     * 注入cusBargainDao
     */
    private ICusBargainDao cusBargainDao;
    //setter
	public void setCusBargainDao(ICusBargainDao cusBargainDao) {
		this.cusBargainDao = cusBargainDao;
	}
	
	/**
	 * 注入customerdao
	 */
	private ICustomerDao customerDao;
	//setter
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/** 
	 * 根据 客户编码  开单时间  开单更改单状态 查询客户圈信息  
	 * @author 308861 
	 * @date 2017-1-11 下午4:50:48
	 * @param custCode
	 * @param billDate
	 * @param isChange
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationService#getByCustCode(java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	public CustomerCircleNewDto getByCustCode(String custCode, Date billDate,
			String isChange) {
		CustomerCircleNewDto customerCircleNewDto = new CustomerCircleNewDto();
		//判断客户编码是否为空
		if(StringUtil.isBlank(custCode)){
			LOGGER.error("客户编码为空");
		    return null;
		}
		//实例化
		CustomerCircleEntity customerCircleEntity = new CustomerCircleEntity();
		//根据客户编码查询客户圈基础信息(如果更改单，按照开单时间查询客户在客户圈内的有效客户信息)
		customerCircleEntity=customerCircleRelationDao.getByCustCode(custCode,billDate);
		if(customerCircleEntity == null){
			customerCircleNewDto.setIsCustCircle("N");
			LOGGER.error("该客户不在客户圈内");
			return customerCircleNewDto;
		}
		//该客户在客户圈内
		customerCircleNewDto.setIsCustCircle("Y");
		//客户实体
		CustomerNewEntity customerNewEntity=new CustomerNewEntity(); 
		//合同实体
		CusBargainNewEntity cusBargainNewEntity = new CusBargainNewEntity();
		//该客户为：主客户
		if(customerCircleEntity.getIsMainCust().equals("Y")){
			//获取客户信息
			customerNewEntity=customerDao.customerByCusCode(custCode);
			//获取合同信息
			cusBargainNewEntity=selectCusBargainInfo(custCode,billDate,isChange);
			/**
			 * 找不到合同时规则：设置默认值
			 */
			//是否统一结算（主客户找不到合同，是否统一结算默认为：N）
			cusBargainNewEntity.setAsyntakegoodsCode(cusBargainNewEntity.getAsyntakegoodsCode() ==null ? "N" : cusBargainNewEntity.getAsyntakegoodsCode());
			//是否精准包裹（主客户找不到合同，是否精准包裹默认为：N）
			cusBargainNewEntity.setIsAccuratePackage(cusBargainNewEntity.getIsAccuratePackage() ==null ? "N" : cusBargainNewEntity.getIsAccuratePackage());
			//月结方式（主客户找不到合同，月结方式默认为：NOT_MONTH_END）
			cusBargainNewEntity.setChargeType(cusBargainNewEntity.getChargeType() ==null ? "NOT_MONTH_END" : cusBargainNewEntity.getChargeType());
			/**
			 * 实际开单客户编码是主客户
			 *
			 * 是否统一结算；
			 * 统一结算校验规则：
			 * 当主客户有效，取主客户所在客户圈主客户的当前有效合同中是否统一结算字段
			 * 和主客户在客户圈中的配置的是否统一结算字段，两者任意一个为是，则运单为统一结算
			 */
			if(customerCircleEntity.getIsFocusPay().equals("N") && cusBargainNewEntity.getAsyntakegoodsCode().equals("N")){
				customerCircleEntity.setIsFocusPay("N");
			}else{
				customerCircleEntity.setIsFocusPay("Y");
			}
		}else{//该客户为：从客户
			//客户圈编码
			String code=customerCircleEntity.getCustCircleCode();
			//是否统一结算（从客户）
			String slaveIsFocusPay=customerCircleEntity.getIsFocusPay();
			//根据客户圈编码查有效的该圈内主客户信息
			customerCircleEntity=customerCircleRelationDao.selectMainCust(code,billDate);
			if(customerCircleEntity == null){
				LOGGER.error("从客户找不到对应的主客户");
				return null;
			}
			//主客户编码
			String mainCode=customerCircleEntity.getCustCode();
			//获取主客户信息
			customerNewEntity=customerDao.customerByCusCode(mainCode);
			//获取合同信息
			cusBargainNewEntity=selectCusBargainInfo(mainCode,billDate,isChange);
			/**
			 * 找不到合同时规则：设置默认值
			 */
			//是否统一结算（主客户找不到合同，是否统一结算默认为：N）
			cusBargainNewEntity.setAsyntakegoodsCode(cusBargainNewEntity.getAsyntakegoodsCode() ==null ? "N" : cusBargainNewEntity.getAsyntakegoodsCode());
			//是否精准包裹（主客户找不到合同，是否精准包裹默认为：N）
			cusBargainNewEntity.setIsAccuratePackage(cusBargainNewEntity.getIsAccuratePackage() ==null ? "N" : cusBargainNewEntity.getIsAccuratePackage());
			//月结方式（主客户找不到合同，月结方式默认为：NOT_MONTH_END）
			cusBargainNewEntity.setChargeType(cusBargainNewEntity.getChargeType() ==null ? "NOT_MONTH_END" : cusBargainNewEntity.getChargeType());
			/**
			 * 实际开单客户编码是从客户
			 * 
			 * 是否统一结算；
			 * 统一结算校验规则：
			 * 当从客户有效，取从客户所在客户圈主客户的当前有效合同中是否统一结算字段
			 * 和从客户在客户圈中的配置的是否统一结算字段，两者任意一个为是，则运单为统一结算
			 */
			if(slaveIsFocusPay.equals("N") && cusBargainNewEntity.getAsyntakegoodsCode().equals("N")){
				customerCircleEntity.setIsFocusPay("N");
			}else{
				customerCircleEntity.setIsFocusPay("Y");
			}
		}
		//客户圈客户信息
		customerCircleNewDto.setCustomerCircleEntity(customerCircleEntity);
		//客户信息
		customerCircleNewDto.setCustomerNewEntity(customerNewEntity);
		//客户合同信息
		customerCircleNewDto.setCusBargainNewEntity(cusBargainNewEntity);
		return customerCircleNewDto;
	}


	/**
	 * 
	 * 根据 客户编码  开单时间  开单更改单状态  查询客户合同信息
	 * @author 308861 
	 * @date 2017-1-4 下午2:04:25
	 * @param custCode
	 * @param billDate
	 * @param isChange
	 * @return
	 * @see
	 */
	private CusBargainNewEntity selectCusBargainInfo(String custCode, Date billDate,
			String isChange) {
		CusBargainNewEntity cusBargainNewEntity=new CusBargainNewEntity();
		//是否精准包裹
		String isAccuratePackage=null;
		//根据开单时间查询
		if(isChange.equals("Y")){//开单状态
			//合同信息
			cusBargainNewEntity=cusBargainDao.queryCusBargainNewByCustCode(custCode, billDate,"Y");
			if(cusBargainNewEntity == null){
				LOGGER.error("开单状态时根据客户编码未查询到合同信息");
				return new CusBargainNewEntity();
			}
		}else{//更改单状态
			//合同信息
			cusBargainNewEntity=cusBargainDao.queryCusBargainNewByCustCode(custCode, billDate,null);
			if(cusBargainNewEntity == null){
				LOGGER.error("更改单状态时根据客户编码未查询到合同信息");
				return new CusBargainNewEntity();
			}
		}
		//根据合同的crmId查询优惠信息的是否精准包裹
		isAccuratePackage=cusBargainDao.iscpackBybargainByCrmId(cusBargainNewEntity.getCrmId(), billDate);
		//合同适用部门名称和编码
		OrgAdministrativeInfoEntity orgEntity=cusBargainDao.applicateOrgNameByCrmId(cusBargainNewEntity.getUnifiedCode());
		if(orgEntity !=null){
			//合同所适用部门名称字段封装
			cusBargainNewEntity.setApplicateOrgName(orgEntity.getName());
			//合同所适用部门编码字段封装
			cusBargainNewEntity.setApplicateOrgCode(orgEntity.getCode());
		}
		//是否精准包裹字段封装
		cusBargainNewEntity.setIsAccuratePackage(isAccuratePackage);
		return cusBargainNewEntity;
	}

	/**
	 * 
	 * 根据客户编码判断是否在客户圈内以及主客户编码 
	 * @author 308861 
	 * @date 2017-2-7 下午5:33:38
	 * @param custCode
	 * @param billDate
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService#isCustomerCircle(java.lang.String, java.util.Date)
	 */
	@Override
	public IsCustomerCircleDto isCustomerCircle(String custCode,Date billDate) {
		IsCustomerCircleDto isCustomerCircleDto=new IsCustomerCircleDto();
		//判断客户编码是否为空
		if(StringUtil.isBlank(custCode)){
			LOGGER.error("客户编码为空");
		    return null;
		}
		CustomerCircleEntity customerCircleEntity=new CustomerCircleEntity();
		//根据客户编码查询客户圈基础信息(如果更改单，按照开单时间查询客户在客户圈内的有效客户信息)
		customerCircleEntity=customerCircleRelationDao.getByCustCode(custCode,billDate);
		if(customerCircleEntity == null){
			isCustomerCircleDto.setIsCustCircle("N");
			LOGGER.error("该客户不在客户圈内");
			return isCustomerCircleDto;
		}
		//该客户在客户圈内
		isCustomerCircleDto.setIsCustCircle("Y");
		//该客户为：主客户
		if(customerCircleEntity.getIsMainCust().equals("Y")){
			isCustomerCircleDto.setMainCode(custCode);
		}else{//该客户为：从客户
			//客户圈编码
			String code=customerCircleEntity.getCustCircleCode();
			//根据客户圈编码查有效的该圈内主客户信息
			customerCircleEntity=customerCircleRelationDao.selectMainCust(code,billDate);
			if(customerCircleEntity == null){
				LOGGER.error("从客户找不到对应的主客户");
				return null;
			}
			isCustomerCircleDto.setMainCode(customerCircleEntity.getCustCode());
		}
		return isCustomerCircleDto;
	}
}
