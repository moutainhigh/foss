package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyEntity;

/**
 * 托盘绑定效率service
 * 
 * @author 200978 2015-2-2
 */
public interface ITrayscanEfficiencyService extends IService {

	void generateTrayBindingEff();

	List<TrayscanEfficiencyEntity> findTrayBindingEff(
			TrayscanEfficiencyEntity paramter);

	List<TrayscanEfficiencyDetailEntity> findTrayBindingDetailEff(
			TrayscanEfficiencyDetailEntity paramter);

}
