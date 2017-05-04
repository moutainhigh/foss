package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDetailDto;
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
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.NormPcSignScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;

/**
 * 
  * @ClassName NormSignByPcService 
  * @Description 按件正常签收 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class NormSignByPcService implements IBusinessService<Void, NormPcSignScanEntity> {
	
	private static final Log LOG = LogFactory.getLog(NormSignByPcService.class);
	
	private IPdaDeliverSignService pdaDeliverSignService;
	private IDeliveryDao deliveryDao;
	
	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}
	
	/**
	 * 解析包体
	 */
	@Override
	public NormPcSignScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		NormPcSignScanEntity normPcSignScan = JsonUtil.parseJsonToObject(NormPcSignScanEntity.class, asyncMsg.getContent());
		// 部门
		normPcSignScan.setDeptCode(asyncMsg.getDeptCode());
		// PDA编号
		normPcSignScan.setPdaCode(asyncMsg.getPdaCode());   
		// user编号
		normPcSignScan.setScanUser(asyncMsg.getUserCode()); 
		// 操作类型
		normPcSignScan.setScanType(asyncMsg.getOperType());
		// ID
		normPcSignScan.setId(asyncMsg.getId());			  
		// 上传时间
		normPcSignScan.setUploadTime(asyncMsg.getUploadTime()); 
		// 同步状态
		normPcSignScan.setSyncStatus(asyncMsg.getAsyncStatus()); 
		// 同步次数
		normPcSignScan.setCount(asyncMsg.getSyncCount());		   
		return normPcSignScan;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, NormPcSignScanEntity normPcSignScan) throws PdaBusiException {
		LOG.info(normPcSignScan);
		PdaDeliverSignDto pdaDeliverSignDto = null;
		try {
			//验证数据有效性
			this.validate(asyncMsg,normPcSignScan);
			//封装实体
			pdaDeliverSignDto = wrapPdaDeliverSignDto(asyncMsg, normPcSignScan);
			long startTime = System.currentTimeMillis();
			//pda签收出库操作
			pdaDeliverSignService.pdaSign(pdaDeliverSignDto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]按件正常签收接口消耗时间:"+(endTime-startTime)+"ms");
			//保存扫描信息,签收信息,流水号信息表
			saveScanMsgAndSignAndSerilnumber(normPcSignScan);
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
	private void saveScanMsgAndSignAndSerilnumber(NormPcSignScanEntity normPcSignScan){
		//扫描表
		deliveryDao.saveNormPcSignScanScanMsg(normPcSignScan);
		//签收信息表
		deliveryDao.saveNormPcSignScanSign(normPcSignScan);
		List<PdaSignDetailEntity> pdaSignDetailEntityList = normPcSignScan.getLabelCodes();
		for (int i = 0; i < pdaSignDetailEntityList.size(); i++) {
			PdaSignDetailEntity detailEntity = pdaSignDetailEntityList.get(i);
			//运单号
			detailEntity.setWblCode(normPcSignScan.getWblCode());
			detailEntity.setId(UUIDUtils.getUUID());
			deliveryDao.saveNormPcSignScanSerilnumber(detailEntity);
		}
	}
	
	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param normPcSignScan
	 * @return
	 */
	private PdaDeliverSignDto wrapPdaDeliverSignDto(AsyncMsg asyncMsg, NormPcSignScanEntity normPcSignScan){
		PdaDeliverSignDto pdaDeliverSignDto = new PdaDeliverSignDto();
		//运单号
		pdaDeliverSignDto.setWaybillNo(normPcSignScan.getWblCode());
		//到达联编号
		pdaDeliverSignDto.setArrivesheetNo(normPcSignScan.getArrInfoCode());
		//用户编号
		pdaDeliverSignDto.setDriverCode(asyncMsg.getUserCode());
		//支付方式
		pdaDeliverSignDto.setPaymentType(normPcSignScan.getPayType());
		
		List<PdaSignDetailEntity> labelCodes = normPcSignScan.getLabelCodes();
		List<PdaSignDetailDto> pdaSignDetailDtos = new ArrayList<PdaSignDetailDto>();
		PdaSignDetailDto pdaSignDetailDto = null;
		for (PdaSignDetailEntity pdaSignDetailEntity : labelCodes) {
			pdaSignDetailDto = new PdaSignDetailDto();
			//签收情况.
			pdaSignDetailDto.setSituation(pdaSignDetailEntity.getSignStatus());
			//流水号
			pdaSignDetailDto.setSerialNo(pdaSignDetailEntity.getLabelCode());
			pdaSignDetailDtos.add(pdaSignDetailDto);
		}
		//签收流水号列表
		pdaDeliverSignDto.setPdaSignDetailDtos(pdaSignDetailDtos);
		//部门编号
		pdaDeliverSignDto.setSignDeptCode(asyncMsg.getDeptCode());
		//签收件数
		pdaDeliverSignDto.setSignGoodsQty(normPcSignScan.getPieces());
		//扫描时间
		pdaDeliverSignDto.setSignTime(normPcSignScan.getScanTime());
		//车牌号
		pdaDeliverSignDto.setVehicleNo(normPcSignScan.getTruckCode());
		//运单号
		pdaDeliverSignDto.setWaybillNo(normPcSignScan.getWblCode());
		//签收情况
		pdaDeliverSignDto.setSituation(normPcSignScan.getSignStatus());
		//是否提供定额发票
      	pdaDeliverSignDto.setIsofferInvoice(normPcSignScan.getIsofferInvoice());
        //银行交易流水号--代收货款流水号
      	pdaDeliverSignDto.setCodBankTradeSerail(normPcSignScan.getCodBankTradeSerail());
    	// 银行交易流水号--到付流水号
      	pdaDeliverSignDto.setBankTradeSerail(normPcSignScan.getBankTradeSerail());
      	//签收人
      	pdaDeliverSignDto.setDeliverymanName(normPcSignScan.getSignPerson());
      	
		return pdaDeliverSignDto;
	}

	/**
	 * 验证数据
	 * @param asyncMsg
	 * @param normPcSignScan
	 */
	private void validate(AsyncMsg asyncMsg, NormPcSignScanEntity normPcSignScan){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(normPcSignScan, "NormPcSignScanEntity");
		
		//扫描数据UUID
		Argument.hasText(normPcSignScan.getId(), "NormPcSignScanEntity.id");
		//运单号
		Argument.hasText(normPcSignScan.getWblCode(), "NormPcSignScanEntity.wblCode");
		//流水号
		Argument.notEmptyElements(normPcSignScan.getLabelCodes(), "NormPcSignScanEntity.labelCodes");
		//扫描标识
		Argument.hasText(normPcSignScan.getScanFlag(), "NormPcSignScanEntity.scanFlag");
		//扫描时间
		Argument.notNull(normPcSignScan.getScanTime(), "NormPcSignScanEntity.scanTime");
		//车牌号
		Argument.hasText(normPcSignScan.getTruckCode(), "NormPcSignScanEntity.truckCode");
		//到达联编号
		Argument.hasText(normPcSignScan.getArrInfoCode(), "NormPcSignScanEntity.arrInfoCode");
		//签收人
		Argument.hasText(normPcSignScan.getSignPerson(), "NormPcSignScanEntity.signPerson");
		//付款方式
//		Argument.hasText(normPcSignScan.getPayType(), "NormPcSignScanEntity.payType");
		//签收情况
		Argument.hasText(normPcSignScan.getSignStatus(), "NormPcSignScanEntity.signStatus");
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_PC_SIGN_NORM.VERSION;
	}
	
	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaDeliverSignService(IPdaDeliverSignService pdaDeliverSignService) {
		this.pdaDeliverSignService = pdaDeliverSignService;
	}

}
