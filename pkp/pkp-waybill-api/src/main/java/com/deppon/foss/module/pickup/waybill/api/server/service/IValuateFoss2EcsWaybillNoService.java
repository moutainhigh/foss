package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Map;


/**
 * 
 * @ClassName: IValuateFoss2EscWaybillNoService 
 * @Description: 校验运单号是否存在
 * @author 351326
 * @date 2016-8-3 上午9:59:01 
 *
 */
public interface IValuateFoss2EcsWaybillNoService {
	/**
	 * 
	 * <p>校验运单是否存在</p> 
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-12 下午2:32:11
	 * @param waybillNo
	 * @param operator
	 * @return
	 * @see
	 */
	boolean valuateFoss2EcsWaybillNo(String waybillNo,String operator);
	
	/**
	 * FOSS校验悟空快递单号地址
	 * @param map
	 * @return true:校验正常 可以开单，false：校验异常，不可开单
	 * @author 272311- sangwenhao
	 */
	boolean validateWaybillNoIsCorrect(Map<String, String> map) ;
}
