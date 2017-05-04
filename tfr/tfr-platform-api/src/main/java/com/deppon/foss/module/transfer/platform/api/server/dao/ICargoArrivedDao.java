package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDetailDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CentralizePickupConifg;

public interface ICargoArrivedDao {

	/**
	 * @desc 外场集中接送货体积配置
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午9:21:31
	 * @author Ouyang
	 */
	List<CentralizePickupConifg> findPickupWeightConifg(String parameter);

	/**
	 * @desc 外场集中接送货体积配置
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午9:21:31
	 * @author Ouyang
	 */
	List<CentralizePickupConifg> findPickupVolumeConifg(String parameter);

	/**
	 * @desc 外场库存；货物流a(外场)->b(本外场),库存在a
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午8:54:57
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findTfrCtrStock(CargoArrivedQcDto parameter);

	/**
	 * @desc 外场在途；货物流a(外场)->b(本外场),从a出发未到b
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午8:54:57
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findTfrCtrOnTheWay(CargoArrivedQcDto parameter);

	/**
	 * @desc 外场在途明细；货物流a(外场)->b(本外场),从a出发未到b
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 下午2:44:15
	 * @author Ouyang
	 */
	List<CargoArrivedDetailDto> findTfrCtrOnTheWayDetails(
			CargoArrivedQcDto parameter);

	/**
	 * @desc 营业部库存；1:货物流a(营业部)->b(本外场),库存在a + 2:货物流a(营业部)->b(本外场),从a出发未到b
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午8:54:57
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findSalesDeptNoArrived(CargoArrivedQcDto parameter);

	/**
	 * @desc 本外场集中接货未完到(未卸车)
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午9:22:39
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findPickupNoArrived(CargoArrivedQcDto parameter);

	/**
	 * @desc 上一外场短途未到 1:上一外场营业部库存，货物流a(营业部)->b(外场)->c(本外场)，库存在a + 2:上一外场营业部在途，a到b在途
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午8:54:57
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findPreSalesDeptNoArrived(CargoArrivedQcDto parameter);

	/**
	 * @desc 上一外场集中接货未到(未卸车)；货物量a(外场)->b(本外场)，a集中接货未到
	 * @param parameter
	 * @return
	 * @date 2015年1月15日 上午8:54:57
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findPrePickupNoArrived(CargoArrivedQcDto parameter);
	
	/**
	 * @desc 查询本外场的相关外场
	 * @param parameter
	 * @return
	 * @date 2015年1月20日 上午10:47:29
	 * @author Ouyang
	 */
	List<Map<String,String>> findRelatedTfrCtr(String parameter);
}
