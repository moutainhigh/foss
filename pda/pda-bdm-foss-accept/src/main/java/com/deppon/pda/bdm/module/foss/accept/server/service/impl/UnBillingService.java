package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.UnBillingEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file UnBillingService.java 
 * @description 未开单录入服务类
 * @author ChenLiang
 * @created 2013-1-8 下午3:39:15    
 * @version 1.0
 */
public class UnBillingService implements IBusinessService<Void, UnBillingEntity> {
	
	private Logger log = Logger.getLogger(getClass());

	private IAcctDao acctDao;
	
	private IPdaWaybillService pdaWaybillService;

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UnBillingEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		UnBillingEntity unBilling = JsonUtil.parseJsonToObject(UnBillingEntity.class, asyncMsg.getContent());
		//pda编号
		unBilling.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		unBilling.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		unBilling.setScanType(asyncMsg.getOperType());
		//用户编号
		unBilling.setScanUser(asyncMsg.getUserCode());
		
		unBilling.setUploadTime(asyncMsg.getUploadTime());
		return unBilling;
	}
	
	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param unBilling
	 * @throws PdaBusiException
	 */
	private void validate(AsyncMsg asyncMsg, UnBillingEntity unBilling) throws PdaBusiException {
		//验证用户和部门信息是否为空
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//验证实体字段内容
		Argument.notNull(unBilling, "UnBillingEntity");
		//流水号
		Argument.hasText(unBilling.getId(), "UnBillingEntity.id");
		//扫描时间
		Argument.notNull(unBilling.getScanTime(), "UnBillingEntity.scanTime");
		//扫描标识
		Argument.hasText(unBilling.getScanFlag(), "UnBillingEntity.scanFlag");
		//运单号
		Argument.hasText(unBilling.getWblCode(), "UnBillingEntity.wblCode");
		//类型
		Argument.hasText(unBilling.getTransType(), "UnBillingEntity.transType");
		Argument.hasText(unBilling.getTruckCode(), "UnBillingEntity.truckCode");
		Argument.isPositiveNum(unBilling.getPieces(), "UnBillingEntity.pieces");
		//Argument.isPositiveNum(unBilling.getAmount(), "UnBillingEntity.amount");
	}

	/**
	 * 
	 * @description 未开单录入服务
	 * @param asyncMsg
	 * @param unBilling
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, UnBillingEntity unBilling) throws PdaBusiException {
		this.validate(asyncMsg, unBilling);
		unBilling.setSyncStatus(Constant.SYNC_STATUS_INIT);
		
		// 保存未开单录入扫描数据
		log.debug("---保存未开单录入扫描数据开始---");
		acctDao.saveUnBillingScan(unBilling);
		log.debug("---保存未开单录入扫描数据开始---");
		
		// 保存未开单录入数据
		log.debug("---保存未开单录入数据开始---");
		acctDao.saveUnBilling(unBilling);
		log.debug("---保存未开单录入数据结束---");
		
		WaybillPdaDto waybillPdaDto = new WaybillPdaDto();
		waybillPdaDto.setOrderNo(unBilling.getLabelCode());// 订单(转车任务)号
		waybillPdaDto.setProductCode(unBilling.getTransType());// 运输性质
		waybillPdaDto.setWaybillNo(unBilling.getWblCode());// 运单号
		waybillPdaDto.setGoodsQty(unBilling.getPieces());// 开单件数
		waybillPdaDto.setActualFee(new BigDecimal(unBilling.getAmount()));// 收款金额
		waybillPdaDto.setBillUserNo(unBilling.getScanUser());// 开单人工号
		waybillPdaDto.setPdaNo(unBilling.getPdaCode());// PDA设备号
		waybillPdaDto.setLicensePlateNum(unBilling.getTruckCode());// 车牌号
		waybillPdaDto.setBillStart(unBilling.getScanTime());// 开单时间
		waybillPdaDto.setCreateUserCode(asyncMsg.getUserCode());
		ResultDto dto = null;
		try {
			log.debug("---调用FOSS未开单录入接口开始---");
			long startTime = System.currentTimeMillis();
			dto = pdaWaybillService.submitWaybillByPDAForAirAndVehicle(waybillPdaDto);
			log.debug("---调用FOSS未开单录入接口返回结果："+dto.getCode()+"---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]未开单录入接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS未开单录入接口结束---");
		
		return null;
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_UNBILLING.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
