package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyEntity;

/**
 * 托盘绑定效率dao
 * 
 * @author 200978 2015-2-2
 */
public interface ITrayscanEfficiencyDao {

	void generateTrayBindingEff(Date staDate, int staMonth);

	List<TrayscanEfficiencyEntity> findTrayBindingEff(
			TrayscanEfficiencyEntity paramter);

	List<TrayscanEfficiencyDetailEntity> findTrayBindingDetailEff(
			TrayscanEfficiencyDetailEntity paramter);

}
