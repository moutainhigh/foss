package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;


/**
 * 
 * 菜鸟服务service接口
 * @author foss-yuting
 * @date 2015-2-5 下午16:08:00
 * @since
 * @version
 */
public interface IRookieService {
	
	/**
	 * 处理部分签收和未签收的逻辑
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRookieService#exceuteSignStatusPart(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	 void exceuteSignStatusPart(WaybillSignResultEntity resultWEntity,WaybillEntity waybill, WaybillSignResultEntity newresultWEntity,PdaDeliverSignDto entity,CurrentInfo currentInfo,FinancialDto financialDto,boolean isDeliver);
	 
	/**
	 * 原运单签收出库逻辑
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRookieService#signOutStock(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	 void signOutStock(ArriveSheetEntity entity,WaybillEntity waybill,CurrentInfo currentInfo);
	 
	/**
	 * 初始化到达联
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRookieService#initArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	 void initArriveSheet(ArriveSheetEntity arriveSheet,CurrentInfo currentInfo,WaybillEntity waybill);
	 
	 /**
	 * 菜鸟快递代理签收
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRookieService#addExpressAgentSignResult(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	void addExpressAgentSignResult(WaybillSignResultEntity wayEntity, CurrentInfo currentInfo, WaybillEntity waybill);
	
	/**
	 * 返货子母件结清-签收(PDA签子母件返货原单)
	 * @author foss-sunyanfei
	 * @date 2015-9-14 下午 15:24:10
	 * @param entity
	 * @param currentInfo
	 * @param oldWaybillNo
	 * @param oneDto
	 */
	void returnTwoInOneSign(PdaDeliverSignDto entity,CurrentInfo currentInfo,TwoInOneWaybillDto oneDto);
	/**
	 * 返货子母件结清-签收(自提签收、快递代理签收返货原单)
	 * @author foss-sunyanfei
	 * @date 2015-9-14 下午 15:24:10
	 * @param entity
	 * @param currentInfo
	 * @param oldWaybillNo
	 * @param oneDto
	 */
	void returnTwoInOneSign(ArriveSheetEntity entity,CurrentInfo currentInfo,TwoInOneWaybillDto oneDto,WaybillSignResultEntity newWsrEntity);
}
