package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.domain.foss2oms.LTLECancelOrderStatusRequest;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOmsOrderDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaAppInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.UUIDUtils;

/**
 * TODO 记得提交Mapper及,WaybillProcessLog,及WaybillContants
 * @description Oms系统订单处理类
 * @author 297064
 *
 */
public class OmsOrderService implements IOmsOrderService {
	
	/**
	 * Logger日志
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderService.class);
	
	private IOmsOrderDao omsOrderDao;
	
	private IWaybillManagerService waybillManagerService;
	
	private IPdaAppInfoDao pdaAppInfoDao;
	
	private ICrmOrderJMSService crmOrderJMSService;
	
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	
	private IWaybillProcessLogDao waybillProcessLogDao;
	
	private ILabelPushProcessService labelPushProcessService;
	
	private static final Integer ZERO = 0;
	
	
	
	/**
	 * 同步OMS订单,生成处理代补录运单
	 */
	@Override
	@Transactional
	public void asynOmsOrder(OmsOrderEntity entity){
		/**
		 * 校验订运单关系是否存在一对多
		 */
		if(validateOWRelation(entity.getOrderNo(),entity.getWaybillNo())){
			generateOmsOrder(entity);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void cancelOrder(OmsOrderEntity entity){
		boolean isSuccess = false;
		/**
		 * 校验订运单关系是否存在一对多
		 */
		String orderNo = entity.getOrderNo();
		String waybillNo = entity.getWaybillNo();
		if(validateOWRelation(orderNo,waybillNo)){
			PdaAppInfoEntity painfo = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(entity.getWaybillNo());
			/**
			 * 未扫描数据的时候进行更新订单
			 */
			if(painfo!=null&&!WaybillConstants.LTLEWAYBILL_SCAN.equals(painfo.getScan())){
				OmsOrderEntity updateEntity = new OmsOrderEntity();
				updateEntity.setOrderNo(entity.getOrderNo());
				updateEntity.setWaybillStatus(WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL);
				omsOrderDao.updateOmsOrderByOrderNoSelective(updateEntity);
				isSuccess = true;
			}
		}
		sendCancelOrderResult(isSuccess,orderNo,waybillNo);
	}
	
	private void sendCancelOrderResult(boolean isSuccess,String orderNo,String waybillNo){
		AccessHeader header = new AccessHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_CANCEL_LTLEWAYBILL");
		header.setVersion("0.1");
		header.setBusinessId(orderNo);

		LTLECancelOrderStatusRequest req = new LTLECancelOrderStatusRequest();
		req.setOrderNo(orderNo);
		req.setWaybillNo(waybillNo);
		if(isSuccess){
			req.setResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS);
		}else{
			req.setResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
		}
		LOGGER.info("OMS订单取消JMS服务推送: " + ReflectionToStringBuilder.toString(req));
		// 发送请求
		String sendResult = WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS;
		String exceptionMessage = "";
		String content = "";
		try {
			ESBJMSAccessor.asynReqeust(header, req);
		} catch (Exception e) {
			// 对异常进行处理
			LOGGER.error("订单状态修改失败：", e);
			sendResult = WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE;
			exceptionMessage = "JMS请求发送失败：" + e.getMessage();
		}
		content = ReflectionToStringBuilder.toString(req);  
		waybillProcessLogDao.saveLog(content, orderNo, waybillNo, new Date(), WaybillConstants.LTLEWAYBILL_OPERATION_TYPE_CANCEL_ORDER_LOG, sendResult, exceptionMessage);
	}
	
	/**
	 * 校验订运单号,orderNo,waybillNo
	 * 如果抛出Exception的话,基本可以确认是selectOne(),这样的话Foss是无法接受订运单交错绑定
	 */
	private boolean validateOWRelation(String orderNo,String waybillNo){
		boolean result = true;
		String failReason = null;
		try{
			OmsOrderEntity o = omsOrderDao.queryOmsOrderByOrderNo(orderNo);
			if(null != o &&!(orderNo.equals(o.getOrderNo())&&waybillNo.equals(o.getWaybillNo()))){
				result = false;
			}
			if(result){
				OmsOrderEntity w= omsOrderDao.queryOmsOrderByWaybillNo(waybillNo);
				if(null != w &&!(orderNo.equals(w.getOrderNo())&&waybillNo.equals(w.getWaybillNo()))){
					result = false;
				}
			}
		}catch(Exception e){
			failReason = ExceptionUtils.getFullStackTrace(e);
			LOGGER.error("OMS订单同步至FOSS,订单号："+orderNo+",运单号:"+waybillNo+",异常信息:"+ failReason);
			result = false;
		}finally{
			if(!result){
				crmOrderJMSService.pushOmsOrderInfoStatust(orderNo , waybillNo , WaybillConstants.LTLEWAYBILL_GENERATE_WAYBILL_PENDING_LOG , null,WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN, null, WaybillValidateException.ORDERNOORWAYBILLNO_ONE_TO_MANY,failReason);
			}
		}
		return result;
	}
	
	
	/**
	 * 判断异常信息是否需要修改大件上楼,其基于LTLEWaybillService.judgeBeanForLDU中所扣出来的代码
	 * 如大件上楼判断规则增加时候请在此方法加上相关的异常匹配信息
	 * 零担电子电子会每次从PdaAppInfo信息中读取isBigUp来设置大件上楼,如设置成大件上楼后抛出了大件上楼的异常
	 * 将会把PdaAppInfo的isBigUp设置成N,当OMS再次下单流入FOSS时候将不会强制设置成大件上楼
	 */
	public boolean isBigUpException(String errorCode){
		boolean isBigUpException = false;
		if(StringUtils.isNotEmpty(errorCode)){
			errorCode = errorCode.trim();
			if(WaybillValidateException.LARGE_DELIVER_UP_SINGLETON_WEIGHT_LES_50.contains(errorCode)
			||WaybillValidateException.LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_130.contains(errorCode)
			||WaybillValidateException.WOODYOKE_LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_170.contains(errorCode)){
				isBigUpException = true;
				return isBigUpException;
			}
		}
		return isBigUpException;
	}
	
	/**
	 * 处理同步的Oms订单信息,异常AysnOmsAddOrder2FossProcessor处进行日志处理
	 * @param entity
	 * @return
	 */
	@Transactional
	public void generateOmsOrder(OmsOrderEntity entity){
		/**
		 * 查询订单
		 */
		OmsOrderEntity queryResult = omsOrderDao.queryOmsOrderByOrderNo(entity.getOrderNo());
		if(queryResult == null){
			/**
			 * 保存OmsOrderEntity
			 */
			entity.setId(UUIDUtils.getUUID());
			omsOrderDao.save(entity);
			/**
			 * 当OMS订单至FOSS时候预先在FOSS保存一条未扫描记录
			 * 但零担电子运单管理时候,只需要查询OMS订单如已存在就可以直接更新PdaAppInfo
			 */
			PdaAppInfoEntity pdaAppInfoEntity = new PdaAppInfoEntity();
			pdaAppInfoEntity.setId(UUIDUtils.getUUID());
			pdaAppInfoEntity.setActive(WaybillConstants.YES);
			pdaAppInfoEntity.setScan(WaybillConstants.LTLEWAYBILL_NOT_SCAN);
			pdaAppInfoEntity.setCreateTime(entity.getCreateTime());
			pdaAppInfoEntity.setModifyTime(entity.getCreateTime());
			pdaAppInfoEntity.setWaybillNo(entity.getWaybillNo());
			pdaAppInfoDao.savePdaAppInfo(pdaAppInfoEntity);
		}else{
			OmsOrderEntity updateOmsOrderEntity = new OmsOrderEntity();
			BeanUtils.copyProperties(entity, updateOmsOrderEntity);
			/**
			 * 固定不允许修改字段,OMS下单时间，订单同步时间,运单号
			 * 当waybillEntity != null 下面字段也不再允许修改(因为部门会和标签信息有关系，此时标签信息已经发送给客户)
			 * 件数包装，收入部门，到达部门，受理部门，
			 */
			updateOmsOrderEntity.setWaybillNo(queryResult.getWaybillNo());
			updateOmsOrderEntity.setModifyTime(queryResult.getModifyTime());
			List<String> waybillNoList=new ArrayList<String>();
			/**
			 * 查询是否存在有效运单
			 */
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
			if(waybillEntity!=null){
				updateOmsOrderEntity.setOrderAcceptOrgCode(queryResult.getOrderAcceptOrgCode());
				updateOmsOrderEntity.setOrderAcceptOrgName(queryResult.getOrderAcceptOrgName());
				//暂时去除件数限制
				//updateOmsOrderEntity.setGoodsQtyTotal(queryResult.getGoodsQtyTotal());
				updateOmsOrderEntity.setIncomeOrgCode(queryResult.getIncomeOrgCode());
				updateOmsOrderEntity.setIncomeOrgName(queryResult.getIncomeOrgName());
				updateOmsOrderEntity.setCustomerPickupOrgCode(queryResult.getCustomerPickupOrgCode());
				waybillNoList.add(queryResult.getWaybillNo());
			}
			/**
			 * 更新订单
			 */
			omsOrderDao.updateOmsOrderByOrderNoSelective(updateOmsOrderEntity);
			if (waybillNoList.size() > ZERO) {
				//调用标签推送服务
				labelPushProcessService.retryFailedJob(waybillNoList);
			}
		}
		int result = lTLEWaybillProcessService.addWaybillProcessEntity(entity.getWaybillNo(), entity.getOrderNo() , WaybillConstants.LTLEWAYBILL_PDA_PENDING_PROCESS);
		if(result == 0){
			throw new WaybillValidateException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		
	}
	
	
	
	
	/**
	 * @description 根据订单号查询
	 */
	@Override
	public OmsOrderEntity queryOmsOrderByOrderNo(String orderNo) {
		LOGGER.info("订单查询根据订单号："+orderNo);
		if(StringUtils.isEmpty(orderNo)){
			return null;
		}
		return omsOrderDao.queryOmsOrderByOrderNo(orderNo);
	}

	
	/**
	 * @description 根据运单号查询
	 */
	@Override
	public OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo) {
		LOGGER.info("订单查询根据运单号："+waybillNo);
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		return omsOrderDao.queryOmsOrderByWaybillNo(waybillNo);
	}
	
	/**
	 * 根据订单号更新
	 */
	@Override
	public int updateOmsOrderByOrderNoSelective(OmsOrderEntity entity) {
		if(null==entity.getOrderNo()){
			return 0;
		}
		return omsOrderDao.updateOmsOrderByOrderNoSelective(entity);
	}
	
	/**
	 * 根据ID更新
	 * @param entity
	 * @return
	 */
	@Override
	public int updateOmsOrderByPrimaryKey(OmsOrderEntity entity){
		if(null==entity.getOrderNo()){
			return 0;
		}
		return omsOrderDao.updateOmsOrderByPrimaryKey(entity);
	}


	@Override
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(
			LTLEWaybillQueryResultDto ltleWaybillQueryResultDto) {
		return omsOrderDao.queryOmsOrderOrLabelStatusByWaybillNo(ltleWaybillQueryResultDto);
	}

	
	public void setOmsOrderDao(IOmsOrderDao omsOrderDao) {
		this.omsOrderDao = omsOrderDao;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setPdaAppInfoDao(IPdaAppInfoDao pdaAppInfoDao) {
		this.pdaAppInfoDao = pdaAppInfoDao;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}

	public void setLabelPushProcessService(
			ILabelPushProcessService labelPushProcessService) {
		this.labelPushProcessService = labelPushProcessService;
	}

	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}
	

}
