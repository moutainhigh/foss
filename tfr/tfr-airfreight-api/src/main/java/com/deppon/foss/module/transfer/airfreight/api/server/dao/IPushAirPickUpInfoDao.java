package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;

/**
 * 
* @description FOSS推送合大票信息至OPP系统
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午11:19:18
 */
public interface IPushAirPickUpInfoDao extends IService{
	
	/**
	 * 
	* @description FOSS新增合大票清单信息时候 保存临时表状态为推送中以及操作状态为INSERT
	* @param temEntity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月11日 下午2:36:17
	 */
	 void addAirPickToTemp(AirWaybillTempEntity temEntity);
	 /**
	  * 
	 * @description 查询 临时表
	 * @param paramMap
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:36:27
	  */
	 List<AirWaybillTempEntity> queryAirPickUpTemInfo();
	 /**
	  * 
	 * @description 查询合大票主表
	 * @param airWayBillNo
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:36:38
	  */
	 AirPickUpInfoRequest queryAirPickUpInfo(String airPickUpId,String airWayBillNo);
	 /**
	  * 
	 * @description 查询合大票明细表
	 * @param airPickUpID
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:36:50
	  */
	 List<AirPickUpDetialInfoEntity> queryAirPickUpDetialInfo(String airPickUpID);
	 /**
	  * 
	 * @description 查询合大票流水号表
	 * @param detialId
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:36:59
	  */
	 List<AirPickUpSerialInfoEntity> queryAirPickUpSerialInfo(String detialId);
	 /**
	  * 
	 * @description 更新临时表状态
	 * @param paramMap
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:37:12
	  */
	void updateAirPickUpTemStatus(Map<String,String> paramMap);

	 /**
	  * 
	 * @description 校验临时表是否存在该合大票清单信息
	 * @return booean true 存在 false 不存在
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午3:43:37
	  */
	 boolean checkDataIsExist(AirWaybillTempEntity entity);
	 /**
	  * 
	 * @description 根据合大票明细id 修改合大票明细送货费
	 * @param params
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:57:47
	  */
	int updateAirPickDetialFeeData(Map<String, Object> params);
	/**
	 * 
	* @description 根据主表id 修改合大票总送货费
	* @param params
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午4:59:21
	 */
	int updateAirPickFeeData(Map<String, Object> params);
	/**
	 * 
	* @description 根据合大票清单明细id 以及运单号 删除合大票流水数据
	* @param waybillNo
	* @param airPickDetialId
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:31:42
	 */
	void deleteAirPickSerial(String waybillNo, String airPickDetialId);
	/**
	 * 
	* @description 根据合大票清单明细id 以及运单号 删除合大票明细数据
	* @param waybillNo
	* @param airPickDetialId
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:32:02
	 */
	void deleteAirPickDetial(String waybillNo, String airPickDetialId);
	/**
	 * 
	* @description 根据清单主表id 修改清单主表 总票数 总件数的值
	* @param param
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:45:10
	 */
	int updateAirPickData(Map<String, Object> param);
	/**
	 * 
	* @description 根据运单号 以及流水号 删除该流水数据
	* @param paramSerial
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:54:46
	 */
	void deleteAirPickSerialPart(Map<String, Object> paramSerial);
	/**
	 * 
	* @description 根据运单号 清单明id 修改明细件数
	* @param paramDetial
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午7:05:00
	 */
	int updateAirPickDetialData(Map<String, Object> paramDetial);
	/**
	 * 
	* @description 新增合大票明细数据
	* @param airpickDetialEntity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午7:20:08
	 */
	void addAirPickUpDetial(AirPickUpDetialInfoEntity airpickDetialEntity);
	/**
	 * 
	* @description 新增流水信息
	* @param entity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午7:30:40
	 */
	void addAirPickUpSerial(AirPickUpSerialEntity entity);
	/**
	 * 根据航空正单ID 查询航空正单信息
	 * @param airWayBillDto
	 * @return
	 */
	AirWaybillEntity queryAirWaybillInfoById(AirWayBillDto airWayBillDto);
	
	/**
	 * 根据合大票主表id 修改临时表状态
	 * @param airPickUpId
	 */
	void updateAirPickUpTemById();
}
