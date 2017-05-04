package com.deppon.foss.service.impl;

import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.common.api.shared.domain.WKResponseParameterEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService;
import com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.service.IITFWKTfrBillService;
import com.deppon.foss.util.define.ESBHeaderConstant;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONObject;


/**
* @description 同步结算单信息
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月11日 上午10:05:43
*/
public class ITFWKTfrBillService implements IITFWKTfrBillService {

	private IWKTfrBillService wKTfrBillService;
	
	private ITruckTaskForWkService truckTaskForWkService;
	
	private ITruckTaskDao truckTaskDao;
	
	
	public void setTruckTaskDao(ITruckTaskDao truckTaskDao) {
		this.truckTaskDao = truckTaskDao;
	}


	private static Logger LOGGER = LogManager.getLogger(ITFWKTfrBillService.class);

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;


	/**
	 * @description 添加悟空交接单 (non-Javadoc)
	 * @see com.deppon.foss.service.IITFWKTfrBillService#addBill(java.lang.String)
	 * @author 328864-foss-xieyang
	 * @update 2016年4月25日 上午10:07:04
	 * @version V1.0 http://localhost:8080/foss/services/wktfrbill/addBill
	 */
	@Override
	public Object addBill(String reqMsg) {
		// esb通用设置相应header
		//ECS系统需求走ESB服务改成直连
//		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
//		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_CREATE_HANDOVERBILL_FROMWK");
//		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
//		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
//		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
//		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
//		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
//		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
//		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
//		//统一让ESB响应快递信息
//		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		
		WKResponseParameterEntity resp = new WKResponseParameterEntity();
		resp.setResultFlag(false);
		
		WKBillEntity wkBillEntity = null;
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("悟空传过来添加交接单数据是:" + reqMsg);
		}

		try {
			wkBillEntity  = JSON.parseObject(reqMsg, WKBillEntity.class);
		} catch (Exception e) {
			LOGGER.error("接收对象字段有问题,请检查传来的字段...",e);
			resp.setFailureReason("接收对象字段有问题,详细信息:" + e.getMessage());
		}

		// 判断wkBillEntity 是否为null, 不是为null 插入数据
		if (wkBillEntity != null) {
			 // 数据插入结果 成功:1 失败:0
			Map<String, String> result = wKTfrBillService.insertWKBill(wkBillEntity);
			if("1".equals(result.get("result"))) {
				resp.setResultFlag(true);
			} else {
				resp.setFailureReason(result.get("errMsg"));
			}

		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("新增悟空结算单结束");
		}
		
		return JSONObject.fromObject(resp).toString();
	}

	/**
	 * @description 更新悟空交接单 (non-Javadoc)
	 * @see com.deppon.foss.service.IITFWKTfrBillService#updateBill(java.lang.String)
	 * @author 328864-foss-xieyang
	 * @update 2016年4月25日 上午10:06:43
	 * @version V1.0 // http://localhost:8080/foss/services/wktfrbill/updateBill
	 */
	@Override
//	@Transactional
	public Object updateBill(String reqMsg) {
		// esb通用设置相应header
		//ECS系统需求走ESB服务改成直连
//		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
//		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_UPDATE_HANDOVERBILL_FROMWK");
//		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
//		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
//		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
//		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
//		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
//		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
//		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
//		//统一让ESB响应快递信息
//		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");

		WKResponseParameterEntity resp = new WKResponseParameterEntity();
		resp.setResultFlag(false);

		WKBillEntity wkBillEntity = null;
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("悟空传过来更新交接单数据是:" + reqMsg);
		}
		
		try {
			wkBillEntity  = JSON.parseObject(reqMsg, WKBillEntity.class);
		} catch (Exception e) {
			LOGGER.error("接收对象字段有问题,请检查传来的字段..." , e);
			resp.setFailureReason("接收对象字段有问题,详细信息:" + e.getMessage());
			
		}
		WKTfrBillEntity wkTfrBillEntity = null;
		
		if(wkBillEntity != null) {
			wkTfrBillEntity = wkBillEntity.getLoadHandoverBillEntity();
		}
		
		// 判断wkTfrBillEntity 是否为null, 不是为null 更新数据
		if (wkTfrBillEntity == null || StringUtils.isEmpty(wkTfrBillEntity.getVehicleNo())) {
			LOGGER.error("接收对象字段有问题,请检查车牌号是否为空..." );
			resp.setFailureReason("车牌号不能为空");
			return JSONObject.fromObject(resp).toString();
		}
		
		WKTfrBillEntity checkEtity=new WKTfrBillEntity();
		// 校验车牌号是否改变  
		// 校验车牌号
		if(StringUtils.isNotEmpty(wkTfrBillEntity.getHandoverBillNo())) {
			checkEtity.setHandoverBillNo(wkTfrBillEntity.getHandoverBillNo());
			checkEtity = wKTfrBillService.getWKTfrBillEntity(checkEtity);
			if(checkEtity == null || checkEtity.getVehicleNo() == null) {
				resp.setFailureReason("校验车牌号失败");
				return JSONObject.fromObject(resp).toString();
			}
		}
		//取到老车辆任务，用于插入到临时表中，推送给ECS系统
		TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(checkEtity.getHandoverBillNo());
		
		// 数据库更新是否成功 成功:1 失败:0
		Map<String, String> result = wKTfrBillService.updateWKBill(wkBillEntity);
		if("1".equals(result.get("result"))) {
			//只针对长短度才更新、作废车辆任务信息
			if(LoadConstants.WKHANDOVER_TYPE_LONG_DISTANCE.equals(checkEtity.getHandoverType())||
					LoadConstants.WKHANDOVER_TYPE_SHORT_DISTANCE.equals(checkEtity.getHandoverType())){
				//作废交接单
				if(LoadConstants.WKHANDOVERBILL_STATE_ALREADY_CANCEL.equals(wkTfrBillEntity.getHandoverState())) {
					LOGGER.error("快递发起作废交接单，并作废车辆任务信息:"+wkTfrBillEntity.getHandoverBillNo());
					truckTaskForWkService.deleteTruckTaskByWkHandOverBill(wkTfrBillEntity.getHandoverBillNo());
				}else if(!wkTfrBillEntity.getVehicleNo().equals(checkEtity.getVehicleNo())) {
					LOGGER.error("快递发起更新交接单,并更新车辆任务:"+wkTfrBillEntity.getHandoverBillNo());
					//  车牌号改变
					truckTaskForWkService.updateTruckTaskByWkHandOverBill(wkTfrBillEntity.getHandoverBillNo(),truckTask);
				}
			}
			resp.setResultFlag(true);
		} else {
			resp.setFailureReason(result.get("errMsg"));
		}
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("修改悟空结算单结束");
		}

		return JSONObject.fromObject(resp).toString();
	}


	/*************         setting       ***********************/
	public void setwKTfrBillService(IWKTfrBillService wKTfrBillService) {
		this.wKTfrBillService = wKTfrBillService;
	}
	

	public void setTruckTaskForWkService(ITruckTaskForWkService truckTaskForWkService) {
		this.truckTaskForWkService = truckTaskForWkService;
	}

}
