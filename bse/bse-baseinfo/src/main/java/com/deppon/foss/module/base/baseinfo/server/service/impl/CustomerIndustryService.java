package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerIndustryDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerIndustryService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CustomerIndustryException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户行业信息Service接口实现
 * @author dujunhui-187862
 * @date 2014-9-25 下午4:01:27
 * @since
 * @version
 */
public class CustomerIndustryService implements ICustomerIndustryService{

    /**
     * 客户行业信息DAO接口.
     */
    private ICustomerIndustryDao cusProfessionDao;
    
    /**
     * 设置客户行业信息Dao接口
     * @param cusProfessionDao
     */
	public void setCusProfessionDao(ICustomerIndustryDao cusProfessionDao) {
		this.cusProfessionDao = cusProfessionDao;
	}
	
	/**
	 * 新增客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:06:22
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addCusProfession(CustomerIndustryEntity entity) throws CustomerIndustryException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		// 先验证在数据库是否存在
		CustomerIndustryEntity isFlag = cusProfessionDao.queryCusCusProfessionBySecCode(entity.getProfessionCode());
		
		if (isFlag!=null) {// 存在就修改
			cusProfessionDao.updateCusProfession(entity);
		} else {//不存在则新增
		    entity.setId(UUIDUtils.getUUID());
		    cusProfessionDao.addCusProfession(entity);
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 修改客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:06:22
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int updateCusProfession(CustomerIndustryEntity entity) throws CustomerIndustryException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		return cusProfessionDao.updateCusProfession(entity);
	}
	
	/**
	 * <p>根据二级客户编码查询客户行业信息</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:06:22
	 * @param secDegreeProfessionCode
	 * @return
	 * @see
	 */
	@Override
	public CustomerIndustryEntity queryCusProfessionBySecCode(String secDegreeProfessionCode) 
			throws CustomerIndustryException{
		return cusProfessionDao.queryCusCusProfessionBySecCode(secDegreeProfessionCode);
	}

	/** 
	 * <p>查询全部二级行业信息</p> 
	 * @author 187862
	 * @date 2014-11-6 下午3:37:07
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerIndustryService#queryAllSecProfession()
	 */
	@Override
	public List<CustomerIndustryEntity> queryAllSecProfession() {
		return cusProfessionDao.queryAllSecProfession();
	}

	/** 
	 * <p>根据Code数组查询对应主键的二级行业数据</p> 
	 * @author 187862
	 * @date 2014-11-6 下午3:37:08
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerIndustryService#querySecProfessionByCodes(java.lang.String[])
	 */
	@Override
	public List<CustomerIndustryEntity> querySecProfessionByCodes(List<String> codes) {
		if(CollectionUtils.isNotEmpty(codes)){
			return cusProfessionDao.querySecProfessionByCodes(codes);
		}else{
			return null;
		}
	}
	
	/** 
	 * <p>根据客户编码查询对应主键的二级行业数据</p> 
	 * @author 187862
	 * @date 2014-12-16 下午5:32:08
	 * @param id
	 * @return 
	 */
	@Override
	public CustomerIndustryEntity querySecProfessionByCusCode(String customerCode) {
		if(StringUtils.isEmpty(customerCode)){
		   return null;
		}
		return cusProfessionDao.queryCusProfessionByCusCode(customerCode);
	}

}