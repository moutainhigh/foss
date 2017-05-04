package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerAssociatedInformationEntity;

/**
 * 配合CRM2期，FOSS需要实现快递开单，散客数据在FOSS系统中创建
 * 然后同步给CRM系统，CRM系统对数据进行拆分加工处理后再回传给FOSS
 * @author 078816
 * @date   2014-03-19
 *
 */
public interface ISynNonfixedCustomerToCrmSerivce extends IService { 
	
	/**
	 * 如果客户数据不存在，就在FOSS系统创建对应的散客信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-3-19
	 *
	 */
	NonfixedCustomerAssociatedInformationEntity createNonfixedCustomer(NonfixedCustomerAssociatedInformationEntity entity);

	/**
	 * 将创建的散客信息同步给CRM系统
	 *
	 * auther:wangpeng_078816
	 * date:2014-3-19
	 *
	 */
	void syncNonfixedCustomerDataToCrm(List<NonfixedCustomerAssociatedInformationEntity> entity);
}
