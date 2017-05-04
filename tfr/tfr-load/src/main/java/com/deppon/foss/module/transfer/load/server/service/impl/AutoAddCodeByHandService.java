/**  
 * Project Name:tfr-load  
 * File Name:AutoAddCodeByHandService.java  
 * Package Name:com.deppon.foss.module.transfer.load.server.service.impl  
 * Date:2015年6月16日上午1:51:56  
 *  
 */
package com.deppon.foss.module.transfer.load.server.service.impl;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeByHandDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: AutoAddCodeByHandService <br/>  
 * Function: 待人工补码记录service层. <br/>  
 * date: 2015年6月16日 上午1:51:56 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class AutoAddCodeByHandService implements IAutoAddCodeByHandService {
	
	private IAutoAddCodeByHandDao autoAddCodeByHandDao;
	
	
	/**
	 * 快递运单Service
	* @fields waybillExpressService
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午2:09:48
	* @version V1.0
	*/
	private IWaybillExpressService waybillExpressService;
	
	
	

	
	/**
	* @description 快递运单Service
	* @param waybillExpressService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午2:10:00
	*/
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**  
	 * autoAddCodeByHandDao.  
	 *  
	 * @param   autoAddCodeByHandDao    the autoAddCodeByHandDao to set  
	 * @since   JDK 1.6  
	 */
	public void setAutoAddCodeByHandDao(IAutoAddCodeByHandDao autoAddCodeByHandDao) {
		this.autoAddCodeByHandDao = autoAddCodeByHandDao;
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService#insertAddCodeByHand(com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity)  
	 */
	@Override
	public int insertAddCodeByHand(AutoAddCodeByHandEntity entity) {
		int backInt = 0;
		//快递到达退回直接转人工补码
		if(entity==null){
			throw new TfrBusinessException("entity为空"); 
		}
		if(entity!=null && entity.getReason()!=null && StringUtils.endsWith(AutoAddCodeConstants.RETURN_ARRIVE, entity.getReason())){
			backInt = autoAddCodeByHandDao.insertAddCodeByHand(entity);
		}else if(entity!=null && entity.getReason()!=null && StringUtils.endsWith(AutoAddCodeConstants.RETURN_FORWARD, entity.getReason())){
			//先判断此条记录在[自动补码-待手工补码表]是否存在,如果存在就只做update
			String waybillNo = autoAddCodeByHandDao.searchAddCodeByHandByWaybillNo(entity.getWaybillNo());
			if(waybillNo!=null){
				backInt = autoAddCodeByHandDao.updateAddCodeByHandByWaybillNo(entity);
			}else{
				backInt = autoAddCodeByHandDao.insertAddCodeByHand(entity);
			}
		}else{
			//没有补过码的转人工补码
			WaybillExpressEntity waybillDto = waybillExpressService.queryWaybillExpressByNo(entity.getWaybillNo());
			if(waybillDto!=null && StringUtils.endsWith(FossConstants.NO, waybillDto.getIsAddCode())){
//				AutoAddCodeByHandEntity dbpojo = autoAddCodeByHandDao.queryAddCodeByHand(entity.getWaybillNo(), null);
//				if(dbpojo!=null){
//					//数据库已有此数据不应重复添加
//				}else{
//					backInt = autoAddCodeByHandDao.insertAddCodeByHand(entity);
//				}
				//TODO
				backInt = autoAddCodeByHandDao.insertAddCodeByHand(entity);
			}
		}
		return backInt;
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService#deleteAddCodeByHand(java.lang.String)  
	 */
	@Override
	public int deleteAddCodeByHand(String waybillNo) {
		return autoAddCodeByHandDao.deleteAddCodeByHand(waybillNo);
	}


	/**
	* @description 根据运单号查询人工补码
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月27日 下午4:26:28
	*/
	@Override
	public AutoAddCodeByHandEntity queryAddCodeByHand(String waybillNo,
			String reason) {
		return autoAddCodeByHandDao.queryAddCodeByHand(waybillNo, reason);
	}

}
