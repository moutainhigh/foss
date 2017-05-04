package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICargoArrivedDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ICargoArrivedService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDetailDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CentralizePickupConifg;
import com.deppon.foss.module.transfer.platform.api.shared.utils.CargoArrivedList;

public class CargoArrivedService implements ICargoArrivedService {

	/**
	 * 货物状态: 上一外场短途未到;上一外场库存;长途在途;营业部未到;集中接货未到
	 */
	public final static String PRE_SHORT_DIS_NO_ARRIVED = "PRE_SHORT_DIS_NO_ARRIVED";
	public final static String TFR_CTR_STOCK = "TFR_CTR_STOCK";
	public final static String TFR_CTR_ON_THE_WAY = "TFR_CTR_ON_THE_WAY";
	public final static String SALES_DEPT_NO_ARRIVED = "SALES_DEPT_NO_ARRIVED";
	public final static String PICKUP_NO_ARRIVED = "PICKUP_NO_ARRIVED";

	private ICargoArrivedDao cargoArrivedDao;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setCargoArrivedDao(ICargoArrivedDao cargoArrivedDao) {
		this.cargoArrivedDao = cargoArrivedDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @desc 根据子部门编码查询所属上级外场
	 * @param currentDeptCode
	 * @return
	 * @date 2015年1月19日 上午9:52:15
	 * @author Ouyang
	 */
	@Override
	public OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return new OrgAdministrativeInfoEntity();
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);

		return orgEntity;
	}

	/**
	 * @desc 查询长途在途明细
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 下午7:53:25
	 * @author Ouyang
	 */
	@Override
	public List<CargoArrivedDetailDto> findDetails(CargoArrivedQcDto parameter) {
		if (TFR_CTR_ON_THE_WAY.equals(parameter.getStatus())) {
			return cargoArrivedDao.findTfrCtrOnTheWayDetails(parameter);
		}
		return new ArrayList<CargoArrivedDetailDto>();
	}

	/**
	 * @desc 查询到达货量
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 下午7:54:05
	 * @author Ouyang
	 */
	@Override
	public List<CargoArrivedDto> findCargoArrived(CargoArrivedQcDto parameter) {
		// 货物状态
		String status = parameter.getStatus();

		// 上一外场库存
		if (TFR_CTR_STOCK.equals(status)) {
			return cargoArrivedDao.findTfrCtrStock(parameter);
		}
		// 长途在途
		if (TFR_CTR_ON_THE_WAY.equals(status)) {
			return cargoArrivedDao.findTfrCtrOnTheWay(parameter);
		}
		// 营业部未到
		if (SALES_DEPT_NO_ARRIVED.equals(status)) {
			return cargoArrivedDao.findSalesDeptNoArrived(parameter);
		}
		// 集中接货未到
		if (PICKUP_NO_ARRIVED.equals(status)) {
			return findPickupNoArrived(parameter);
		}
		// 上一外场短途未到
		if (PRE_SHORT_DIS_NO_ARRIVED.equals(status)) {
			return findPreShortDisNoArrived(parameter);
		}

		return new ArrayList<CargoArrivedDto>();
	}

	/**
	 * @desc 集中接货未到
	 * @param parameter
	 * @return
	 * @date 2015年1月16日 上午8:54:21
	 * @author Ouyang
	 */
	private List<CargoArrivedDto> findPickupNoArrived(
			CargoArrivedQcDto parameter) {
		// 当前外场
		String orgCode = parameter.getDestDeptCode();

		// 获取当前外场集中接货重量、体积配置参数
		Map<String, BigDecimal> pickupConifg = getPickupConifg(orgCode);

		BigDecimal weightConifg = pickupConifg.get("weightConifg");
		BigDecimal volumeConifg = pickupConifg.get("volumeConifg");

		parameter.setWeightConfig(weightConifg);
		parameter.setVolumeConfig(volumeConifg);

		return cargoArrivedDao.findPickupNoArrived(parameter);
	}

	/**
	 * @desc 上一外场短途未到； 营业部a->外场b->本外场c; 1+2
	 *       <p>
	 *       1：营业部a未到;a>：货物在a库存 + b>： 货物从a出发未到b
	 *       </p>
	 *       <p>
	 *       2: 外场b集中接货未到;a>:外场b集中接货未完成接货 + b>外场b集中接货完成接货未卸车
	 *       </p>
	 * @return
	 * @date 2015年1月16日 上午9:33:07
	 * @author Ouyang
	 */
	private List<CargoArrivedDto> findPreShortDisNoArrived(
			CargoArrivedQcDto parameter) {
		// 上一外场编码集合
		List<Map<String, String>> maps = cargoArrivedDao
				.findRelatedTfrCtr(parameter.getDestDeptCode());

		CargoArrivedList result = new CargoArrivedList();
		
		for (Map<String, String> map : maps) {
			String preDeptCode = map.get("CODE");
			String preDeptName = map.get("NAME");
			parameter.setPreDeptCode(preDeptCode);
			parameter.setPreDeptName(preDeptName);

			// 获取上一外场集中接货重量、体积配置参数
			Map<String, BigDecimal> pickupConifg = getPickupConifg(preDeptCode);

			BigDecimal weightConifg = pickupConifg.get("weightConifg");
			BigDecimal volumeConifg = pickupConifg.get("volumeConifg");

			parameter.setWeightConfig(weightConifg);
			parameter.setVolumeConfig(volumeConifg);

			// 上一外场短途未到:货物流a(营业部)->b(外场)->c(本外场),库存在a或a到b在途
			List<CargoArrivedDto> preSalesDeptNoArrived = cargoArrivedDao
					.findPreSalesDeptNoArrived(parameter);

			// 上一外场集中接货未到(未卸车)；货物量a(外场)->b(本外场)，a集中接货未到
			List<CargoArrivedDto> findPrePickupNoArrived = cargoArrivedDao
					.findPrePickupNoArrived(parameter);

			// 将各种类型的结果合并
			for (CargoArrivedDto item : preSalesDeptNoArrived) {
				result.add(item);
			}

			for (CargoArrivedDto item : findPrePickupNoArrived) {
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * @desc 集中接货外场重量、体积配置
	 * @param type
	 *            0表示重量 1表示体积
	 * @param orgCode外场编码
	 * @return
	 * @date 2015年1月15日 下午7:28:37
	 * @author Ouyang
	 */
	private String findPickupConifg(int type, String orgCode) {

		List<CentralizePickupConifg> conifgs = null;
		if (type == 0) {
			conifgs = cargoArrivedDao.findPickupWeightConifg(orgCode);
		} else {
			conifgs = cargoArrivedDao.findPickupVolumeConifg(orgCode);
		}

		if (CollectionUtils.isNotEmpty(conifgs)) {
			for (int i = 0, size = conifgs.size(); i < size; i++) {
				CentralizePickupConifg item = conifgs.get(i);
				String value = item.getValue();

				if (StringUtils.equals(orgCode, item.getOrgCode())) {
					return value;
				}

				if (i == size - 1) {
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * @desc 集中接货重量、体积配置
	 * @param orgCode
	 * @return
	 * @date 2015年1月16日 上午9:08:47
	 * @author Ouyang
	 */
	private Map<String, BigDecimal> getPickupConifg(String orgCode) {

		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>(2);

		BigDecimal weightConifg = null;
		BigDecimal volumeConifg = null;

		try {
			weightConifg = new BigDecimal(findPickupConifg(0, orgCode));
			volumeConifg = new BigDecimal(findPickupConifg(1, orgCode));
		} catch (Exception e) {
			throw new TfrBusinessException(orgCode + "外场集中接货重量货体积配置错误。");
		}

		result.put("weightConifg", weightConifg);
		result.put("volumeConifg", volumeConifg);
		return result;
	}
}