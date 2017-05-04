package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICargoArrivedDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDetailDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CentralizePickupConifg;

public class CargoArrivedDao extends iBatis3DaoImpl implements ICargoArrivedDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ICargoArrivedDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralizePickupConifg> findPickupWeightConifg(String parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findPickupWeightConifg", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralizePickupConifg> findPickupVolumeConifg(String parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findPickupVolumeConifg", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDto> findTfrCtrStock(CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(NAMESPACE + "findTfrCtrStock",
				parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDto> findTfrCtrOnTheWay(CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findTfrCtrOnTheWay", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDetailDto> findTfrCtrOnTheWayDetails(
			CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findTfrCtrOnTheWayDetails", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDto> findSalesDeptNoArrived(
			CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findSalesDeptNoArrived", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDto> findPickupNoArrived(CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findPickupNoArrived", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDto> findPreSalesDeptNoArrived(
			CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findPreSalesDeptNoArrived", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoArrivedDto> findPrePickupNoArrived(
			CargoArrivedQcDto parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findPrePickupNoArrived", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findRelatedTfrCtr(String parameter) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findRelatedTfrCtr", parameter);
	}

}
