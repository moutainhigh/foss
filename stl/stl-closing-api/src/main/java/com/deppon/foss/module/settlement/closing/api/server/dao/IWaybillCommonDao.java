package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNosDto;

/**
 * 同步运单/签收信息 dao
 * 
 * @author 326181
 * 
 */
public interface IWaybillCommonDao {

	/**
	 * 通过该运单号查询对应有效的运单数据
	 * 
	 * @author ECS-326181
	 * @param waybillNo
	 *            运单号
	 * @return
	 */
	WaybillEntity findWaybillByWaybillNo(String waybillNo);

	/**
	 * 查询此运单号是否存在
	 * 
	 * @author ECS-326181 2016-5-9 11点
	 * @param waybillNo
	 *            运单号
	 * @return
	 */
	List<String> findActualFreightByWaybillNo(String waybillNo);

	/**
	 * 通过运单号查询id
	 * 
	 * @author ECS-326181 2016-5-9 11点
	 * @param waybillNo
	 *            运单号
	 * @return
	 */
	List<String> findWaybillExpressByWaybillNo(String waybillNo);

	/**
	 * 通过运单号查询id
	 * 
	 * @author ECS-326181 2016-5-9 11点
	 * @param id
	 * @return
	 */
	WaybillRfcEntity findWaybillRfcByWaybillNo(String waybillNo);

	/**
	 * 创建运单
	 * 
	 * @author ECS - 326181
	 * @date 2016-5-9
	 * @param waybill
	 */
	int insertWaybillEntity(WaybillEntity waybill);
  
  /**
	 * 查询运单有没有
	 * 
	 * @author ECS-327090-2016-05-09
	 * @date 
	 * @param entity
	 * @return
	 * @see
	 */
	Integer selectWaybillSignResult(WaybillSignResultEntity entity);
	
	/**
	 * 根据运单号更新运单信息
	 * 
	 * @author ECS-327090-2016-05-09
	 * @date 
	 * @param entity
	 * @return
	 * @see
	 */
	int updateWaybill(WaybillSignResultEntity entity);
	
	/**
	 * 根据运单号更新运单信息
	 * 
	 * @author ECS-327090-2016-05-09
	 * @date 
	 * @param entity
	 * @return
	 * @see
	 */
	int addWaybillSignResult(WaybillSignResultEntity entity);
	
	/**
	 * 修改运单
	 * 
	 * 通过运单号码更新运单记录
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param waybill
	 * @return
	 */
	int updateWaybillEntityByWaybillNo(WaybillEntity waybill,String oldWaybillNo, boolean isAdd);
	
	/**
	 * 
	 * <p>
	 * 根据主键更新
	 * </p>
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param record
	 * @return
	 * @see
	 */
	int updateActualFreightEntityById(ActualFreightEntity record);
	
	/**
	 * 根据运单号进行数据的更新
	 * @author Foss-326181
	 * @date 2016-07-21
	 * @param actualFreightEntity
	 * @return
	 */
	int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity);
	
	/**
	 * 
	 * <p>
	 * 新增实际承运表
	 * </p>
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param record
	 * @return
	 */
	int insertActualFreightEntity(ActualFreightEntity record);
	
	/**
	 *通过运单编号修改运单
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param waybillExpress
	 * @return
	 */
	int updateWaybillExpressByWaybillNo(WaybillExpressEntity waybillExpress);
	
	/**
	 * 提交时新增运单快递
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 */
	String insertWaybillExpressEntity(WaybillExpressEntity waybillExpress);
	
	/**
	 * 
	 * <p>
	 * 更新更改单实体<br />
	 * </p>
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param waybillRfcEntity
	 *            void
	 */
	void updateWaybillRfcEntityById(WaybillRfcEntity waybillRfcEntity);
	
	/**
	 * 
	 * 新增更改单
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 */
	void insertWaybillRfcEntity(WaybillRfcEntity rfcEntity);
	
	/**
	 * 通过运单号集合拿到待处理的更改单
	 * 
	 * @param WaybillFRcQueryByWaybillNosDto
	 * @return
	 */
	List<String> queryWaybillRfcByWaybillNos(WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto);
}
