package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SelfDeryScanEntity;

/**
 * 
  * @ClassName SelfDeryService 
  * @Description 客户自提 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class SelfDeryService implements IBusinessService<Void, SelfDeryScanEntity>{
	
	private static final Log LOG = LogFactory.getLog(SelfDeryService.class);
	public final static int  MINUTE =3600 ;
	public final static int  MILLISECOND =1000 ;   
	public final static int  HOUR =24 ;
	public final static int  DAY =7 ;
	private IPdaSignService pdaSignService;
	private IDeliveryDao deliveryDao;
	
	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	/**
	 * 解析包体
	 */
	@Override
	public SelfDeryScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SelfDeryScanEntity selfDeryScan = JsonUtil.parseJsonToObject(SelfDeryScanEntity.class, asyncMsg.getContent());
		//部门编号
		selfDeryScan.setDeptCode(asyncMsg.getDeptCode());
		//pda编号
		selfDeryScan.setPdaCode(asyncMsg.getPdaCode());
		//用户编号
		selfDeryScan.setScanUser(asyncMsg.getUserCode());
		//操作类型
		selfDeryScan.setScanType(asyncMsg.getOperType());
		
		selfDeryScan.setUploadTime(asyncMsg.getUploadTime());
		return selfDeryScan;
	}
	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, SelfDeryScanEntity selfDeryScan) throws PdaBusiException {
		LOG.info(selfDeryScan);
		PdaSignDto dto = null;
		try {
			//验证数据有效性
			this.validate(asyncMsg,selfDeryScan);
			//封装实体
			dto = wrapPdaSignDto(asyncMsg, selfDeryScan);
			long startTime = System.currentTimeMillis();
			//pda签收出库操作
			pdaSignService.pdaSign(dto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]客户自提接口消耗时间:"+(endTime-startTime)+"ms");
			//保存扫描信息,签收信息,流水号信息表
			saveScanMsgAndSignAndSerilnumber(selfDeryScan);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	
	/**
	 * 保存扫描信息,签收信息,流水号信息表
	 * @param normPcSignScan
	 */
	@Transactional
	private void saveScanMsgAndSignAndSerilnumber(SelfDeryScanEntity selfDeryScan){
		//保存扫描信息
		deliveryDao.saveSelfDeryScanScanMsg(selfDeryScan);
		//保存签收信息
		deliveryDao.saveSelfDeryScanSign(selfDeryScan);
		List<String> labelCodeList = selfDeryScan.getLabelCodes();
		for (int i = 0; i < labelCodeList.size(); i++) {
			String labelCode = labelCodeList.get(i);
			PdaSignDetailEntity detailEntity = new PdaSignDetailEntity();
			detailEntity.setId(UUIDUtils.getUUID());
			//运单号
			detailEntity.setWblCode(selfDeryScan.getWblCode());
			//流水号
			detailEntity.setLabelCode(labelCode);
			deliveryDao.saveSelfDeryScanSerilnumber(detailEntity);
		}
	}
	
	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param selfDeryScan
	 * @return
	 */
	private PdaSignDto wrapPdaSignDto(AsyncMsg asyncMsg, SelfDeryScanEntity selfDeryScan){
		PdaSignDto dto = new PdaSignDto();
		//运单号
		dto.setWaybillNo(selfDeryScan.getWblCode());
		//到达联编号
		dto.setArrivesheetNo(selfDeryScan.getArrInfoCode());
		//用户编号
		dto.setOperatorCode(asyncMsg.getUserCode());
		//流水号列表
		dto.setSerialNos(selfDeryScan.getLabelCodes());
		//部门编号
		dto.setSignDeptCode(asyncMsg.getDeptCode());
		//件数
		dto.setSignGoodsQty(selfDeryScan.getPieces());
		//签收备注
		dto.setSignNote(selfDeryScan.getExcpReason());
		//扫描时间
		dto.setSignTime(selfDeryScan.getScanTime());
		//签收状态
		dto.setSituation(selfDeryScan.getSignStatus());
		//运单号
		dto.setWaybillNo(selfDeryScan.getWblCode());
		return dto;
	}

	/**
	 * 验证数据
	 * @param asyncMsg
	 * @param selfDeryScan
	 */
	private void validate(AsyncMsg asyncMsg, SelfDeryScanEntity selfDeryScan){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(selfDeryScan, "SelfDeryScanEntity");
		
		//扫描数据UUID
		Argument.hasText(selfDeryScan.getId(), "SelfDeryScanEntity.id");
		//运单号
		Argument.hasText(selfDeryScan.getWblCode(), "SelfDeryScanEntity.wblCode");
		//流水号
		Argument.notEmptyElements(selfDeryScan.getLabelCodes(), "SelfDeryScanEntity.labelCodes");
		//扫描标识
		Argument.hasText(selfDeryScan.getScanFlag(), "SelfDeryScanEntity.scanFlag");
		//扫描时间
		Argument.notNull(selfDeryScan.getScanTime(), "SelfDeryScanEntity.scanTime");
		/*
		 * PDA客户端扫描时间与服务器时间相差7天以上以上抛出异常
		 * 2014-02-21 gaojia 
		 */
		long scantime = selfDeryScan.getScanTime().getTime();
		long cuurenttime = new Date().getTime();
		if(scantime<cuurenttime&&cuurenttime-scantime>(MINUTE*MILLISECOND*HOUR*DAY)){
			throw new FossInterfaceException(null,"自提签收信息扫描时间比当前系统时间相差7天以上");
		}else if(scantime>cuurenttime&&scantime-cuurenttime>(MINUTE*MILLISECOND*HOUR*DAY)){
			throw new FossInterfaceException(null,"自提签收信息扫描时间比当前系统时间相差7天以上");
		}
		//到达联编号
		Argument.hasText(selfDeryScan.getArrInfoCode(), "SelfDeryScanEntity.arrInfoCode");
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_SFDEL.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaSignService(IPdaSignService pdaSignService) {
		this.pdaSignService = pdaSignService;
	}
	
}
