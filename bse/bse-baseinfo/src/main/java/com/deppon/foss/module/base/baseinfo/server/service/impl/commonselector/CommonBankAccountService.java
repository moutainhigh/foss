package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonBankAccountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonBankAccountEntity;
import com.deppon.foss.util.CollectionUtils;
/**
 * 公共查询选择器--（对公，对私）账号Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 上午8:49:42
 */
public class CommonBankAccountService implements ICommonBankAccountService {
	/**
	 * 组织信息 Service接口
	 */
	//private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 公共选择器---组织公共账户Dao
	 */
	private ICommonBankAccountDao commonBankAccountDao;
	/** The bank service. */
	private IBankService bankService;
	/** The area address service. */
	private IAreaAddressService areaAddressService;
	/**
	 * 查询组织对公账号信息
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
	@Override
	public List<CommonBankAccountEntity> queryBankAccountByDto(
			CommonBankAccountEntity commonBankAccountEntity, int start,
			int limit) {
		//如果输入名字则只根据名字模糊查询
		if(StringUtils.isNotBlank(commonBankAccountEntity.getBankAccName())){
			CommonBankAccountEntity result = new CommonBankAccountEntity();
			List<CommonBankAccountEntity> entityList = new ArrayList<CommonBankAccountEntity>();
			result.setBankAccName(commonBankAccountEntity.getBankAccName());
			result.setActive(commonBankAccountEntity.getActive());
			if(CollectionUtils.isNotEmpty(commonBankAccountEntity.getAccountTypes())){
				result.setAccountTypes(commonBankAccountEntity.getAccountTypes());
			}
			entityList = commonBankAccountDao.queryBankAccountByDto(result, start, limit);
			//313353 sonar
			this.sonarSplitOne(entityList);
			return entityList;
		}else{
		//	List<CommonBankAccountEntity> entityList = new ArrayList<CommonBankAccountEntity>();
			List<CommonBankAccountEntity> entityList = commonBankAccountDao.queryBankAccountByDto(commonBankAccountEntity, start, limit);
			for (int i = 0; i < entityList.size(); i++){
				if(StringUtils.isBlank(entityList.get(i).getCityName())){
				// 设置银行name
				BankEntity entity = new BankEntity();
				if(StringUtils.isNotBlank(entityList.get(i).getBankCd())){
					entity = bankService.queryBankInfoByCode(entityList.get(i).getBankCd());
				}
				if (null !=entity) {
					entityList.get(i).setBankName(entity.getName());
				}
				// 设置支行name
				entity = new BankEntity();
				if(StringUtils.isNotBlank(entityList.get(i).getSubbranchCd())){
					entity = bankService.queryBankInfoByCode(entityList.get(i).getSubbranchCd());
				}
				if (null!=entity) {
					entityList.get(i).setSubbranchName(
							entity.getName());
				}
				// 设置省名称
				AdministrativeRegionsEntity city = areaAddressService.queryRegionByCode(
						entityList.get(i).getProvCd());
				if (null!=city) {
					entityList.get(i).setProvName(city.getName());
				}
				// 设置城市名称
				AdministrativeRegionsEntity pro =  areaAddressService.queryRegionByCode(
						entityList.get(i).getCityCd());
				if (null!=pro) {
					entityList.get(i).setCityName(pro.getName());
				}
				}
				
			}
			return entityList;
		}
    }
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(List<CommonBankAccountEntity> entityList) {
		for (int i = 0; i < entityList.size(); i++){
			if(StringUtils.isBlank(entityList.get(i).getCityName())){
			// 设置银行name
			BankEntity entity = new BankEntity();
			if(StringUtils.isNotBlank(entityList.get(i).getBankCd())){
				entity = bankService.queryBankInfoByCode(entityList.get(i).getBankCd());
			}
			if (null !=entity) {
				entityList.get(i).setBankName(entity.getName());
			}
			// 设置支行name
			entity = new BankEntity();
			if(StringUtils.isNotBlank(entityList.get(i).getSubbranchCd())){
				entity = bankService.queryBankInfoByCode(entityList.get(i).getSubbranchCd());
			}
			if (null!=entity) {
				entityList.get(i).setSubbranchName(
						entity.getName());
			}
			// 设置省名称
			AdministrativeRegionsEntity city = areaAddressService.queryRegionByCode(
					entityList.get(i).getProvCd());
			if (null!=city) {
				entityList.get(i).setProvName(city.getName());
			}
			// 设置城市名称
			AdministrativeRegionsEntity pro =  areaAddressService.queryRegionByCode(
					entityList.get(i).getCityCd());
			if (null!=pro) {
				entityList.get(i).setCityName(pro.getName());
			}
			}
			
		}
	}
	
	 /**
		 * 查询组织对公账号信息 总数
		 * 
		 * @author 130346-foss-lifanghong
		 * @date 2013-08-22 下午2:35:52
		 * @return
		 */
	@Override
	public long countQueryBankAccountByDto(
			CommonBankAccountEntity commonBankAccountEntity) {
//    	if(StringUtils.isNotBlank(commonBankAccountEntity.getDeptCode())){
//    		OrgAdministrativeInfoEntity orgInfo=this.getOrgInfo(commonBankAccountEntity.getDeptCode());
//    		if(null != orgInfo){
//    			commonBankAccountEntity.setDeptCd(orgInfo.getUnifiedCode());
//    		}
//    	}
		
		//如果输入名字则只根据名字模糊查询
				if(StringUtils.isNotBlank(commonBankAccountEntity.getBankAccName())){
					CommonBankAccountEntity entity = new CommonBankAccountEntity();
			//		List<CommonBankAccountEntity> entityList = new ArrayList<CommonBankAccountEntity>();
					entity.setBankAccName(commonBankAccountEntity.getBankAccName());
					entity.setActive(commonBankAccountEntity.getActive());
					if(CollectionUtils.isNotEmpty(commonBankAccountEntity.getAccountTypes())){
						entity.setAccountTypes(commonBankAccountEntity.getAccountTypes());
					}
					return commonBankAccountDao.queryRecordCount(entity);
				}else{
					return commonBankAccountDao.queryRecordCount(commonBankAccountEntity);
				}
    }
	 /**
     * 根据部门编码获取部门标杆编码
     * @author 101911-foss-zhouChunlai
     * @param 
     * @date 2013-1-11 上午11:18:36
     * @return 
     */
    /*private OrgAdministrativeInfoEntity getOrgInfo(String deptCode){
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
    }*/
	public ICommonBankAccountDao getCommonBankAccountDao() {
		return commonBankAccountDao;
	}
	public void setCommonBankAccountDao(ICommonBankAccountDao commonBankAccountDao) {
		this.commonBankAccountDao = commonBankAccountDao;
	}
	/*public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}*/
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}
    
}
