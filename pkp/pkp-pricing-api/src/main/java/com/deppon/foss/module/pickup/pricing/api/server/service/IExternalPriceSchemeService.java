package com.deppon.foss.module.pickup.pricing.api.server.service;

//import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;
/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Service
 */
public interface IExternalPriceSchemeService extends IService {
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
	 * @description 修改偏线外发价格方案
	 * @param entityList
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
	 * @description 激活偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	int activateExternalPriceSchemeById(List<ExternalPriceSchemeEntity> entityList);
	/**
	 * @author 092020-lipengfei
	 * @description 立即激活偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	int immediatelyActivateSchemeById(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 复制偏线外发价格方案
	 * @param entity
	 * @return ExternalPriceSchemeEntity
	 */
	ExternalPriceSchemeEntity copyExternalPriceScheme(String id);
	/**
	 * @author 092020-lipengfei
	 * @description 立即中止偏线外发价格方案
	 * @param id
	 * @return Integer
	 */
	int immediatelySuspendSchemeById(ExternalPriceSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 导入偏线外发价格方案
	 * @param workbook
	 * @return Integer
	 */
	int importExternalPriceScheme(Workbook workbook);
	/**
	 * @author 092020-lipengfei
	 * @description 导出偏线外发价格方案 参考EffectiveExpressPlanService
	 * @param workbook
	 * @return Integer
	 */
	public ExportResource exportExternalPriceSchemeByParams(ExternalPriceSchemeEntity entity);
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
