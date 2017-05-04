package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;

/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Dao
 */
public interface IExternalPriceSchemeDao {
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	List<ExternalPriceSchemeEntity> queryExternalPriceSchemeByParam(ExternalPriceSchemeEntity entity,int limit, int start);
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案总量
	 * @param entity
	 * @return Long
	 */
	Long queryRecordCount(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	ExternalPriceSchemeEntity queryExternalePriceSchemeById(String schemeId);
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	int addExternalPriceScheme(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 批量新增偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	int batchAddExternalPriceScheme(List<ExternalPriceSchemeEntity> entity);
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案名称查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	List<ExternalPriceSchemeEntity> queryExternalePriceSchemeByName(String schemeName);
	/**
	 * @author 092020-lipengfei
	 * @description 修改偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	int updateExternalPriceScheme(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 删除偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	int deleteExternalPriceSchemeById(List<String> idList);
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线外发价格方案（激活、中止）
	 * @param id
	 * @param state
	 * @return Integer
	 */
	int updateSchemeStateById(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID和状态查询偏线外发价格方案
	 * @param idList
	 * @param state
	 * @return Integer
	 */
	Long queryExternalPriceSchemeByIdAndState(List<String> idList,String state);
	/**
	 * @author 092020-lipengfei
	 * @description 复制偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	int copyExternalPriceScheme(ExternalPriceSchemeEntity entity,String copyId);
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线外发价格方案截止日期
	 * @param entity
	 * @return Integer
	 */
	int updateExternalPriceSchemeEndTime(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站，外发外场，运输类型，外发单生成时间 查询偏线外发价格方案
	 * @param targetOrgCode
	 * @param outOrgCode
	 * @param transportType
	 * @param externalBillCreateTime
	 * @return ExternalPriceSchemeEntity
	 */
	ExternalPriceSchemeEntity queryAgentOutPriceInfo(String targetOrgCode,String outOrgCode,String transportType,Date externalBillCreateTime);
}
