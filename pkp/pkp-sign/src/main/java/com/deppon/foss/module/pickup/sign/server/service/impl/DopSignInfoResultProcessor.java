package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.inteface.domain.dopsigninfo.DealDopSignInfoRequest;
import com.deppon.foss.esb.inteface.domain.dopsigninfo.DealDopSignInfoResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDopSignDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;


public class DopSignInfoResultProcessor implements IProcess{
	/**
	 * 变更结果-失败
	 */
	private static final String FAILED = "fail";
	/**
	 * 插入数据库成功
	 */
	private static final String SUCCESS="success";
	private static final String WARN_EMPTY="传输数据内容有误";
	/**
	 * 插入数据库失败
	 */
	private static final String DB_FAILD="数据插入数据库失败";
	/**
	 * 插入数据库异常
	 */
	private static final String DB_EXCEPTION="数据插入数据库异常";
	/**
	 * 业务斥锁服务
	 */
	private IBusinessLockService businessLockService;

	/**
	 *  签收出库Dao
	 */
	private ISignDao signDao;
	/**
	 * dop 签收出库Dao
	 */
	private IDopSignDao dopSignDao;
	// 运单管理接口
	private IWaybillManagerService waybillManagerService;
	//快递运单接口
	private IWaybillExpressService waybillExpressService;


	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DopSignInfoResultProcessor.class);
	@Override
	@Transactional
	public Object process(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		LOGGER.info("DOP传过来的家装签收信息保存操作开始....");
		DealDopSignInfoRequest request=(DealDopSignInfoRequest)req;
		DealDopSignInfoResponse response=new DealDopSignInfoResponse();
		try{
			//校验传入参数
			validateParams(request);
			//异常处理
		}catch(BusinessException be){
			//记录日志
			LOGGER.info("运单验证异常");
			response.setChangeResult(FAILED);
			response.setWayBillNo(request.getWaybillNo());
			//记录变更结果信息
			response.setChangeResultInfo(be.getMessage());
			return response;
		}
		/**
		 * 判断是否已经保存此运单的签收信息
		 */
		SignDto signdto=new SignDto();
		signdto.setWaybillNo(request.getWaybillNo());
		try{
			List<DopSignEntity> dtos=dopSignDao.queryDopListByWaybillNo(signdto);
			if(!dtos.isEmpty()){
				LOGGER.info("运单： "+request.getWaybillNo()+" 已经保存过！");
				response.setChangeResult(FAILED);
				response.setWayBillNo(request.getWaybillNo());
				response.setChangeResultInfo("运单： "+request.getWaybillNo()+" 已经保存过！");
				return response;
			}
		}catch (SignException e) {//捕获异常
			throw new SignException(e.getErrorCode(),e);//抛出异常
		}
		
		
		MutexElement mutexElement = new MutexElement(request.getWaybillNo(), "运单编号", MutexElementType.APPLICATION_SIGN_RFC);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);	
		if(!isLocked){
			//如果没有得到锁
			LOGGER.info("当前运单操作中，请稍后再试");
			//记录日志
			response.setChangeResult(FAILED);
			response.setWayBillNo(request.getWaybillNo());
			//失败原因
			response.setChangeResultInfo(SignException.WAYBILL_LOCKED);//失败原因
			return response;
		}
		//将接收到的签收信息封装
		DopSignEntity dopEntity=new DopSignEntity();
		dopEntity.setWaybillNo(request.getWaybillNo());
		dopEntity.setSignTime(request.getSignTime());
		dopEntity.setSupplierCode(request.getSupplierCode());
		dopEntity.setSupplierName(request.getSupplierName());
		dopEntity.setPayType(request.getPayType());
		dopEntity.setReceiveOrgName(request.getReceiveOrgName());
		dopEntity.setReceiveOrgCode(request.getReceiveOrgCode());
		dopEntity.setReceiverName(request.getReceiverName());
		dopEntity.setReceiverPhone(request.getReceiverPhone());
		dopEntity.setDeliveryOrgCode(request.getDeliveryOrgCode());
		dopEntity.setDeliveryOrgName(request.getDeliveryOrgName());
		dopEntity.setSignStatus(request.getSignStatus());
		dopEntity.setSignNote(request.getSignNote());
		dopEntity.setSignGoodsQty(request.getSignGoodsQty());
		dopEntity.setCreateTime(new Date());
		if(StringUtils.isNotEmpty(request.getWaybillNo())){
			try{
				//插入到数据库
				int warn=dopSignDao.insertDopCacheWaybillInfo(dopEntity);
				if(warn>0){
					//返回变更结构
					response.setChangeResult(SUCCESS);
					response.setWayBillNo(request.getWaybillNo());
					//返回错误信息
					response.setChangeResultInfo(null);
					//业务锁解锁
					businessLockService.unlock(mutexElement);
					LOGGER.info("DOP传过来的家装运单号："+request.getWaybillNo()+"的签收信息保存操作--成功.");
					return response;
				}else{
					LOGGER.info("DOP传过来的家装运单号："+request.getWaybillNo()+"的签收信息插入数据库--未成功");
					businessLockService.unlock(mutexElement);
					response.setChangeResult(FAILED);
					response.setWayBillNo(request.getWaybillNo());
					//记录变更结果信息
					response.setChangeResultInfo(DB_FAILD);//失败原因
					return response;
				}
			}catch(Exception e){
				LOGGER.info("DOP传过来的家装运单号："+request.getWaybillNo()+"的签收信息插入数据库--异常");
				businessLockService.unlock(mutexElement);
				response.setChangeResult(FAILED);
				response.setWayBillNo(request.getWaybillNo());
				//记录变更结果信息
				response.setChangeResultInfo(DB_EXCEPTION);//失败原因
				return response;
			}
		}else{
			LOGGER.info("运单号接收到的数据为空");
			businessLockService.unlock(mutexElement);
			//记录日志
			response.setChangeResult(FAILED);
			response.setWayBillNo(request.getWaybillNo());
			//记录变更结果信息
			response.setChangeResultInfo(WARN_EMPTY);//失败原因
			return response;
		}
	}

public void setDopSignDao(IDopSignDao dopSignDao) {
		this.dopSignDao = dopSignDao;
	}

/**
 * SignDao注入
 * @param signDao
 * @author 269871 foss-zhuliangzhi
 */
	public void setSignDao(ISignDao signDao) {
		this.signDao = signDao;
	}

	/**
	 * 验证传入的参数 
	 * @author 269871 foss-zhuliangzhi
	 * @param 
	 * @return 
	 */
	private void validateParams(DealDopSignInfoRequest request) {
		if(StringUtils.isEmpty(request.getWaybillNo())){
			//传入的运单号为空
			LOGGER.info("传入的运单号为空");
			throw new BusinessException("传入的运单号为空");
		}
		// 查询运单
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(request.getWaybillNo());
		if (waybillEntity != null) {
			//是否为快递
			Boolean flag=waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode().trim(),waybillEntity.getBillTime());
			//如果运输性质为空运偏线或快递
			if(flag || (waybillEntity.getProductCode().trim()).equals(ReportConstants.PICKUPGOODSAIR)||(waybillEntity.getProductCode().trim()).equals(ReportConstants.PRICING_PRODUCT_AIR_FREIGHT)){
				LOGGER.info("运单: "+request.getWaybillNo()+"不能为空运偏线或快递");
				throw new BusinessException("运单不能为空运偏线或快递");
			}
		}
		
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}
