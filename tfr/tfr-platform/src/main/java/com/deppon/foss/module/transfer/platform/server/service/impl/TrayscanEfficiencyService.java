package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.server.dao.ITrayscanEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ITrayscanEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.DateUtils;

/**
 * 托盘绑定效率
 * 
 * @author 200978 2015-2-2
 */
public class TrayscanEfficiencyService implements ITrayscanEfficiencyService {

	private ITrayscanEfficiencyDao trayscanEfficiencyDao;

	public void setTrayscanEfficiencyDao(
			ITrayscanEfficiencyDao trayscanEfficiencyDao) {
		this.trayscanEfficiencyDao = trayscanEfficiencyDao;
	}

	@Override
	public void generateTrayBindingEff() {
		Date staDate = DateUtils.getStartDatetime(DateUtils.addDayToDate(
				new Date(), -1));

		int staMonth = Integer.valueOf(PlatformUtils.formatDate2String(staDate,
				"yyyyMM"));

		trayscanEfficiencyDao.generateTrayBindingEff(staDate, staMonth);
	}

	@Override
	public List<TrayscanEfficiencyEntity> findTrayBindingEff(
			TrayscanEfficiencyEntity paramter) {
		return trayscanEfficiencyDao.findTrayBindingEff(paramter);
	}

	@Override
	public List<TrayscanEfficiencyDetailEntity> findTrayBindingDetailEff(
			TrayscanEfficiencyDetailEntity paramter) {
		return trayscanEfficiencyDao.findTrayBindingDetailEff(paramter);
	}

}
