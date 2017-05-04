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
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.NormVoteSignScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;

/**
 * 
  * @ClassName NormSignByVoteService 
  * @Description 按票正常签收 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class NormSignByVoteService implements IBusinessService<Void, NormVoteSignScanEntity> {
	
	private static final Log LOG = LogFactory.getLog(NormSignByVoteService.class);
	
	private IPdaDeliverSignService pdaDeliverSignService;
	private IDeliveryDao deliveryDao;
	
	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	/**
	 * 解析包体
	 */
	@Override
	public NormVoteSignScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		NormVoteSignScanEntity normVoteSignScan = JsonUtil.parseJsonToObject(NormVoteSignScanEntity.class, asyncMsg.getContent());
		 // 部门编号
		normVoteSignScan.setDeptCode(asyncMsg.getDeptCode());
		// PDA编号
		normVoteSignScan.setPdaCode(asyncMsg.getPdaCode());   
		// user编号
		normVoteSignScan.setScanUser(asyncMsg.getUserCode()); 
		// 操作类型
		normVoteSignScan.setScanType(asyncMsg.getOperType()); 
		// ID
		normVoteSignScan.setId(asyncMsg.getId());			  
		// 上传时间
		normVoteSignScan.setUploadTime(asyncMsg.getUploadTime()); 
		// 同步状态
		normVoteSignScan.setSyncStatus(asyncMsg.getAsyncStatus()); 
		// 同步次数
		normVoteSignScan.setCount(asyncMsg.getSyncCount());		   
		return normVoteSignScan;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, NormVoteSignScanEntity normVoteSignScan) throws PdaBusiException {
		LOG.info(normVoteSignScan);
		PdaDeliverSignDto pdaDeliverSignDto = null;
		//验证数据有效性
		this.validate(asyncMsg, normVoteSignScan);
		try {
			//封装数据
			pdaDeliverSignDto = wrapPdaDeliverSignDto(asyncMsg, normVoteSignScan);
			long startTime = System.currentTimeMillis();
			//pda签收出库操作
			pdaDeliverSignService.pdaSign(pdaDeliverSignDto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]按票正常签收接口消耗时间:"+(endTime-startTime)+"ms");
			//保存扫描信息,签收信息,流水号信息表
			saveScanMsgAndSignAndSerilnumber(normVoteSignScan);
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
	private void saveScanMsgAndSignAndSerilnumber(NormVoteSignScanEntity normVoteSignScan){
		//保存扫描信息
		deliveryDao.saveNormSignByVoteScanMsg(normVoteSignScan);
		//保存签收信息
		deliveryDao.saveNormSignByVoteSign(normVoteSignScan);
		List<PdaSignDetailEntity> pdaSignDetailEntityList = normVoteSignScan.getLabelCodes();
		if(pdaSignDetailEntityList != null){
			for (int i = 0; i < pdaSignDetailEntityList.size(); i++) {
				PdaSignDetailEntity detailEntity = pdaSignDetailEntityList.get(i);
				detailEntity.setWblCode(normVoteSignScan.getWblCode());
				detailEntity.setId(UUIDUtils.getUUID());
				//保存流水号信息
				deliveryDao.saveNormSignByVoteSerilnumber(detailEntity);
			}
		}
	}
	
	/**
	 * 拼装实体
	 * @param asyncMsg
	 * @param normVoteSignScan
	 * @return
	 */
	private PdaDeliverSignDto wrapPdaDeliverSignDto(AsyncMsg asyncMsg, NormVoteSignScanEntity normVoteSignScan){
		PdaDeliverSignDto pdaDeliverSignDto = new PdaDeliverSignDto();
		//运单号
		pdaDeliverSignDto.setWaybillNo(normVoteSignScan.getWblCode());
		//到达联编号
		pdaDeliverSignDto.setArrivesheetNo(normVoteSignScan.getArrInfoCode());
		//用户编号
		pdaDeliverSignDto.setDriverCode(asyncMsg.getUserCode());
		//支付方式
		pdaDeliverSignDto.setPaymentType(normVoteSignScan.getPayType());
		
		List<PdaSignDetailEntity> labelCodes = normVoteSignScan.getLabelCodes();
		if(labelCodes != null){
			List<PdaSignDetailDto> pdaSignDetailDtos = new ArrayList<PdaSignDetailDto>();
			PdaSignDetailDto pdaSignDetailDto = null;
			for (PdaSignDetailEntity pdaSignDetailEntity : labelCodes) {
				pdaSignDetailDto = new PdaSignDetailDto();
				//签收情况
				pdaSignDetailDto.setSituation(pdaSignDetailEntity.getSignStatus());
				//流水号
				pdaSignDetailDto.setSerialNo(pdaSignDetailEntity.getLabelCode());
				pdaSignDetailDtos.add(pdaSignDetailDto);
			}
			pdaDeliverSignDto.setPdaSignDetailDtos(pdaSignDetailDtos);
		}
		//部门编号
		pdaDeliverSignDto.setSignDeptCode(asyncMsg.getDeptCode());
		//签收件数
		pdaDeliverSignDto.setSignGoodsQty(normVoteSignScan.getPieces());
		//扫描时间
		pdaDeliverSignDto.setSignTime(normVoteSignScan.getScanTime());
		//车牌号
		pdaDeliverSignDto.setVehicleNo(normVoteSignScan.getTruckCode());
		//运单号
		pdaDeliverSignDto.setWaybillNo(normVoteSignScan.getWblCode());
		//签收情况
		pdaDeliverSignDto.setSituation(normVoteSignScan.getSignStatus());
		//是否提供定额发票
		pdaDeliverSignDto.setIsofferInvoice(normVoteSignScan.getIsofferInvoice());
        //银行交易流水号--代收货款流水号
      	pdaDeliverSignDto.setCodBankTradeSerail(normVoteSignScan.getCodBankTradeSerail());
    	// 银行交易流水号--到付流水号
      	pdaDeliverSignDto.setBankTradeSerail(normVoteSignScan.getBankTradeSerail());
      	//签收人
      	pdaDeliverSignDto.setDeliverymanName(normVoteSignScan.getSignPerson());
      
		
		return pdaDeliverSignDto;
	}
	
	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param normVoteSignScan
	 */
	private void validate(AsyncMsg asyncMsg, NormVoteSignScanEntity normVoteSignScan){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(normVoteSignScan, "NormVoteSignScanEntity");
		
		//扫描数据UUID
		Argument.hasText(normVoteSignScan.getId(), "NormVoteSignScanEntity.id");
		//运单号
		Argument.hasText(normVoteSignScan.getWblCode(), "NormVoteSignScanEntity.wblCode");
		//流水号
//		Argument.notEmptyElements(normVoteSignScan.getLabelCodes(), "NormVoteSignScanEntity.labelCodes");
		//扫描标识
		Argument.hasText(normVoteSignScan.getScanFlag(), "NormVoteSignScanEntity.scanFlag");
		//扫描时间
		Argument.notNull(normVoteSignScan.getScanTime(), "NormVoteSignScanEntity.scanTime");
		//车牌号
		Argument.hasText(normVoteSignScan.getTruckCode(), "NormVoteSignScanEntity.truckCode");
		//到达联编号
		Argument.hasText(normVoteSignScan.getArrInfoCode(), "NormVoteSignScanEntity.arrInfoCode");
		//签收人
		Argument.hasText(normVoteSignScan.getSignPerson(), "NormVoteSignScanEntity.signPerson");
		//付款方式
//		Argument.hasText(normVoteSignScan.getPayType(), "NormVoteSignScanEntity.payType");
		//件数
		Argument.isPositiveNum(normVoteSignScan.getPieces(), "NormVoteSignScanEntity.pieces");
		//签收情况
		Argument.hasText(normVoteSignScan.getSignStatus(), "NormVoteSignScanEntity.signStatus");
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_VOTE_SIGN_NORM.VERSION;
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
