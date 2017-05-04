package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDuration;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationDispatch;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationTransfer;
import com.deppon.foss.module.transfer.platform.api.shared.dto.KeyValueDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StockDurationQcDto;

public interface IStockDurationService extends IService {
	/**
	 * @desc 查询提货方式
	 * @return 
	 * @date 2015年4月10日 上午11:36:03
	 * @author Ouyang
	 */
	List<KeyValueDto> findReceiveMethod();

	/**
	 * @desc 生成快递货库存时长数据
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年3月24日 下午5:53:06
	 * @author Ouyang
	 */
	void generateExp(Integer threadCount, Integer threadNo);

	/**
	 * @desc 生成零担货库存时长数据
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年3月24日 下午5:53:08
	 * @author Ouyang
	 */
	void generateLtc(Integer threadCount, Integer threadNo);

	/**
	 * @desc 查询快递数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:06
	 * @author Ouyang
	 */
	List<StockDuration> findExpDay(StockDurationQcDto parameter);

	/**
	 * @desc 查询快递数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:09
	 * @author Ouyang
	 */
	List<StockDuration> findExpMonth(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:11
	 * @author Ouyang
	 */
	List<StockDuration> findLtcDay(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:14
	 * @author Ouyang
	 */
	List<StockDuration> findLtcMonth(StockDurationQcDto parameter);

	/**
	 * @desc 查询快递中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationTransfer> findTfrExp(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询零担中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationTransfer> findTfrLtc(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询快递派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationDispatch> findDptExp(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询零担派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationDispatch> findDptLtc(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询快递中转数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findTfrExpCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担中转数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findTfrLtcCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询快递派送数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findDptExpCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担派送数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findDptLtcCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询本部门所属外场
	 * @param code
	 * @return
	 * @date 2015年3月26日 下午5:29:42
	 * @author Ouyang
	 */
	OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String orgCode);
}
