package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.CargoDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CargoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstResultDto;

public interface ICargoFcstDao {

	/**
	 * @desc 查询指定日期是否节假日
	 * @param parameter
	 * @return
	 * @date 2015年3月17日 下午4:26:17
	 * @author Ouyang
	 */
	Long findHoliday(Date date);

	/**
	 * @desc 查询外场的到达线路
	 * @param tfrCtrCode
	 * @return
	 * @date 2015年3月19日 下午2:38:16
	 * @author Ouyang
	 */
	List<Map<String, String>> findArrLines(String tfrCtrCode);

	/**
	 * @desc 查询外场的出发线路
	 * @param tfrCtrCode
	 * @return
	 * @date 2015年3月19日 下午2:38:26
	 * @author Ouyang
	 */
	List<Map<String, String>> findDepartLines(String tfrCtrCode);

	/**
	 * @desc 查询公司所有外场(非分部)
	 * @return
	 * @date 2015年3月19日 上午9:25:06
	 * @author Ouyang
	 */
	List<String> findTfrCtrs();

	/**
	 * @desc 获取货量预测参数配置
	 * @return
	 * @date 2015年3月19日 上午10:43:17
	 * @author Ouyang
	 */
	String findFcstConfig(Map<String, String> map);

	/**
	 * @desc 实际长途到达货量
	 * @date 2015年3月17日 下午4:01:48
	 * @author Ouyang
	 */
	void insertActualArrLng(CargoFcstDto parameter);

	/**
	 * @desc 实际短途到达货量
	 * @date 2015年3月17日 下午4:01:48
	 * @author Ouyang
	 */
	void insertActualArrSht(CargoFcstDto parameter);

	/**
	 * @desc 实际集中接货货量
	 * @date 2015年3月17日 下午4:01:48
	 * @author Ouyang
	 */
	void insertActualArrPickup(CargoFcstDto parameter);

	/**
	 * @desc 实际长途出发货量
	 * @date 2015年3月17日 下午4:01:48
	 * @author Ouyang
	 */
	void insertActualDptLng(CargoFcstDto parameter);

	/**
	 * @desc 实际短途出发货量
	 * @date 2015年3月17日 下午4:01:48
	 * @author Ouyang
	 */
	void insertActualDptSht(CargoFcstDto parameter);

	/**
	 * @desc 实际派送出发货量
	 * @date 2015年3月17日 下午4:01:48
	 * @author Ouyang
	 */
	void insertActualDptDispatch(CargoFcstDto parameter);

	/**
	 * @desc 实际货量汇总
	 * @param parameter
	 * @date 2015年3月17日 下午4:10:37
	 * @author Ouyang
	 */
	void insertActual(CargoFcstDto parameter);

	/**
	 * @desc 查询外场某天某条线实际总货量
	 * @param tfrCtrCode
	 * @param staDate
	 * @return
	 * @date 2015年3月18日 下午5:18:18
	 * @author Ouyang
	 */
	CargoEntity findActual(String tfrCtrCode, Date staDate, String lineCode);

	/**
	 * @desc 查询外场某天某条线某类型实际总货量
	 * @param parameter
	 * @return
	 * @date 2015年3月17日 下午4:14:02
	 * @author Ouyang
	 */
	CargoDetailEntity findActualDetail(String tfrCtrCode, Date staDate,
			String lineCode, String goodsType);

	/**
	 * @desc 生成预测总货量
	 * @return
	 * @date 2015年3月17日 下午4:20:42
	 * @author Ouyang
	 */
	int insertFcst(CargoEntity parameter);

	/**
	 * @desc 生成各类型预测货量
	 * @param parameter
	 * @date 2015年3月17日 下午4:27:34
	 * @author Ouyang
	 */
	void insertFcstDetail(CargoDetailEntity parameter);

	/**
	 * @desc 界面查询
	 * @param parameter
	 * @return
	 * @date 2015年3月17日 下午5:12:04
	 * @author Ouyang
	 */
	List<CargoFcstResultDto> findFcstResult(CargoFcstDto parameter);

	/**
	 * @desc 界面查询,各条线汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月17日 下午5:12:04
	 * @author Ouyang
	 */
	List<CargoFcstResultDto> findFcstResultTotal(CargoFcstDto parameter);

	int cntFcst(String tfrCtrCode, String lineCode, Date staDate);
}
