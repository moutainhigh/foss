package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableRequest;
import com.deppon.esb.inteface.domain.payable.update.BillPayableChangedRequest;
import com.deppon.esb.inteface.domain.receivable.BillRecevivableChangedRequest;
import com.deppon.esb.inteface.domain.receivable.add.PtpAddBillReceivableRequest;
import com.deppon.foss.module.settlement.common.api.server.dao.IEsbInterfaceLogDao;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * 异步接口日志表service
 * @author foss结算-306579-guoxinru 
 * @date 2016-6-17 上午11:37:06    
 */
public class EsbInterfaceLogService implements IEsbInterfaceLogService {
	
	/**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EsbInterfaceLogService.class);
	
    /**
	 * 注入接口日志表DAO
	 */
	private IEsbInterfaceLogDao esbInterfaceLogDao;
	
	/**
	 * 接口日志表新增记录
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean addInterfaceLog(InterfaceLogEntity entity) {
		boolean flag = false;
		try {
			int count = esbInterfaceLogDao.addInterfaceLog(entity);
			if(count!=1){
				throw new SettlementException();
			}
			flag = true;
		} catch (SettlementException e) {
			LOGGER.error("插入接口日志表失败！运单号："+entity.getWaybillNo());
		}finally{
			return flag;
		}
	}

	public void setEsbInterfaceLogDao(IEsbInterfaceLogDao esbInterfaceLogDao) {
		this.esbInterfaceLogDao = esbInterfaceLogDao;
	}

	/* 
	 * 保存合伙人异步接口抛异常的错误日志
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService#savePtpInterfaceLog(java.lang.Object, java.lang.String)
	 */
	@Override
	public void savePtpInterfaceLog(Object obj, String exception) {
		//记录日志entity
		InterfaceLogEntity logEntity = new InterfaceLogEntity();
		//id
		logEntity.setId(UUIDUtils.getUUID());
		//创建日期
		logEntity.setCreateTime(new Date());
		//来源系统
		logEntity.setSystemType(SettlementESBDictionaryConstants.SOURCE_SYSTEM_PTP);
		//请求参数
		logEntity.setSendContent(JSON.toJSONString(obj));
		//异常信息
		logEntity.setErrorLog(exception);
		//失败
		logEntity.setIsSuccess(FossConstants.NO);
		
		//判断自来哪个接口
		if(obj instanceof BillPayableChangedRequest){//应付单更改
			//应付单更改请求参数
			BillPayableChangedRequest request = (BillPayableChangedRequest) obj;
			//创建人
			logEntity.setCreateUser("合伙人应付单更改接口");
			//运单号
			logEntity.setWaybillNo(request.getChangePayableBills().get(0).getWaybillNo());
			//保存单据子类型
			logEntity.setModifyUser(request.getChangePayableBills().get(0).getBillType());
			//esb服务编码
			logEntity.setEsbCode(SettlementESBDictionaryConstants.FOSS_ESB2FOSS_UPDATE_PAYABLE_BILL);
			
		}else if(obj instanceof PtpAddBillPayableRequest){//应付单新增
			//应付单新增请求参数 
			PtpAddBillPayableRequest request = (PtpAddBillPayableRequest) obj;
			//创建人
			logEntity.setCreateUser("合伙人应付单新增接口");
			//运单号
			logEntity.setWaybillNo(request.getPayableBills().get(0).getWaybillNo());
			//保存单据子类型
			logEntity.setModifyUser(request.getPayableBills().get(0).getBillType());
			//esb服务编码
			logEntity.setEsbCode(SettlementESBDictionaryConstants.FOSS_ESB2FOSS_ADD_PAYABLE_BILL);
			
		}else if(obj instanceof BillRecevivableChangedRequest){//应收单更改
			//应收单更改请求参数
			BillRecevivableChangedRequest request = (BillRecevivableChangedRequest) obj;
			//创建人
			logEntity.setCreateUser("合伙人应收单更改接口");
			//运单号
			logEntity.setWaybillNo(request.getWaybillNo());
			//保存单据子类型
			logEntity.setModifyUser(request.getBillType());
			//esb服务编码
			logEntity.setEsbCode(SettlementESBDictionaryConstants.FOSS_ESB2FOSS_UPDATE_RECEIVABLE);
			
		}else if(obj instanceof PtpAddBillReceivableRequest){
			//应收单新增请求参数
			PtpAddBillReceivableRequest request = (PtpAddBillReceivableRequest) obj;
			//创建人
			logEntity.setCreateUser("合伙人应收单新增接口");
			//运单号
			logEntity.setWaybillNo(request.getReceivableBills().get(0).getWaybillNo());
			//保存单据子类型
			logEntity.setModifyUser(request.getReceivableBills().get(0).getBillType());
			//esb服务编码
			logEntity.setEsbCode(SettlementESBDictionaryConstants.FOSS_ESB2FOSS_ADD_RECEIVABLEBILL);
		}
		
		//记录日志到数据库
		this.addInterfaceLog(logEntity);
	}
	
}
