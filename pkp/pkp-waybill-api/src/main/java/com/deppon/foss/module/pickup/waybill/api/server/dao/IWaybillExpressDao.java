/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryCityProvinceDto;

/**
 * 运单快递dao
 * @author 026123-foss-lifengteng
 *
 */
public interface IWaybillExpressDao {

	/**
	 * 提交时新增运单快递
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	String addWaybillExpressEntity(WaybillExpressEntity waybillExpress);
	
	/**
	 *通过运单编号修改运单
	 * 
	 * @param waybill
	 */
	int updateWaybillExpressByWaybillNo(WaybillExpressEntity waybillExpress);
	
	/**
	 * 修改运单
	 * 
	 * @param waybill
	 */
	int updateWaybillExpressById(WaybillExpressEntity waybillExpress);
	
	/**
	 * 通过运单编号查询运单快递
	 * 
	 * @param waybill
	 */
	WaybillExpressEntity queryWaybillExpressByNo(String waybillNo);
	
	/**
	 * 通过运单Id查询运单快递
	 * 
	 * @param waybill
	 */
	WaybillExpressEntity queryWaybillExpressByWaybillId(String waybillId);
	
	/**
	 * 通过id询运单快递
	 * 
	 * @param waybill
	 */
	WaybillExpressEntity queryWaybillExpressById(String id);
	
	/**
	 * 查询城市
	 * @param orgCode
	 * @return
	 */
	WaybillQueryCityProvinceDto queryCityProvince(String orgCode);
	
	/**
	 * 通过原单编号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(String waybillNo);
	/**
	 * 返单开单
	 * @param waybillExpress
	 */
	void addWaybillExpressEntityReturn(WaybillExpressEntity waybillExpress);
	
	/**
	 * 返单开单
	 * @param waybillExpress
	 */
	void addWaybillExpressEntityReturn(List<WaybillExpressEntity> entitys);


	/**
	 * 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	List<WaybillExpressEntity> queryWaybillByWaybillNo(String waybillNo);
	
	/**
	 * 通过返单号查出返货原单号
	 * @param waybillNo
	 * @return
	 */
	List<WaybillExpressEntity> queryWaybillReturnByWaybillNo(WaybillExpressEntity waybillExpress);
	/**
	 * 通过原单号查询快递表中的信息
	 * 232608
	 * @param waybillNo
	 * @return
	 */
   WaybillExpressEntity queryExpressWaybillByOriginalWaybillNo(String waybillNo);
}
