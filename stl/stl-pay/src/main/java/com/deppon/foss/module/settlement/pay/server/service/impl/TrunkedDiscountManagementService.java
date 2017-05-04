package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.server.dao.ITrunkedDiscountManagementDao;
import com.deppon.foss.module.settlement.pay.api.server.service.ITrunkedDiscountManagementService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.TrunkDiscountManEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.TrunkedDiscountManagementDto;

/**
 * 
 * @ClassName: TrunkedDiscountManagementService
 * @Description: TODO(零担事后折折扣管理)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-19 下午1:54:56
 * 
 */
public class TrunkedDiscountManagementService implements
		ITrunkedDiscountManagementService {
	/**
	 * 折扣单Dao
	 */
	private ITrunkedDiscountManagementDao trunkedDiscountManagementDao;

	/**
	 * 按客户去查询
	 */
	@Override
	public TrunkedDiscountManagementDto queryTrunkedDiscountByCust(
			TrunkedDiscountManagementDto dto, int start, int limit) {
		//封装实体类集合
		//List<TrunkDiscountManEntity> lists = new ArrayList<TrunkDiscountManEntity>();
		List<TrunkDiscountManEntity> lists = trunkedDiscountManagementDao.queryTrunkedDiscountByCust(dto,
				start, limit);

		// 查询总行数
		long count = trunkedDiscountManagementDao.queryCountDiscountByCust(dto);
		TrunkedDiscountManagementDto tdmd = new TrunkedDiscountManagementDto();
		tdmd.setTrunkDiscountManagementDList(lists);
		// 设置总行数
		tdmd.setCount(count);
		return tdmd;
	}

	/**
	 * 按单号查折扣单
	 */
	@Override
	public TrunkedDiscountManagementDto queryTrunkedDiscountByNumber(
			TrunkedDiscountManagementDto dto, int start, int limit) {
		// 配置实体类集合
		//List<TrunkDiscountManEntity> entitys = new ArrayList<TrunkDiscountManEntity>();
		// 获取单号
		String waybillNo = dto.getWaybillNo();
		List<String> lists = new ArrayList<String>();
		String[] str = waybillNo.split(",");
		for (int i = 0; i < str.length; i++) {
			String s = str[i].trim();
			lists.add(s);
		}
		// 设置单号集合
		dto.setNumbers(lists);

		// 调用
		List<TrunkDiscountManEntity> entitys = trunkedDiscountManagementDao.queryTrunkedDiscountByNumber(
				dto, start, limit);

		// 查询总行数
		int count = trunkedDiscountManagementDao
				.queryCountDiscountByNumber(dto);

		TrunkedDiscountManagementDto tdmd = new TrunkedDiscountManagementDto();
		// 设置实体类集合
		tdmd.setTrunkDiscountManagementDList(entitys);
		// 设置总行数
		tdmd.setCount(count);
		return tdmd;
	}
	
	/*   getter setter */
	public ITrunkedDiscountManagementDao getTrunkedDiscountManagementDao() {
		return trunkedDiscountManagementDao;
	}

	public void setTrunkedDiscountManagementDao(
			ITrunkedDiscountManagementDao trunkedDiscountManagementDao) {
		this.trunkedDiscountManagementDao = trunkedDiscountManagementDao;
	}
}
