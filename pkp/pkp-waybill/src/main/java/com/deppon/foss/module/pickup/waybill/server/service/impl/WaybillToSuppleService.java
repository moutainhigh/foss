package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillToSuppleDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPackBIService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillToSuppleService;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.impl.GrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillSupplementLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleCondtionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleResultDto;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 待补录运单作废SerVice
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-14 19:37:20
 */
public class WaybillToSuppleService implements IWaybillToSuppleService {
	
	/**
	 * 运单持久层
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * 待补录运单信息表
	 */
	private IWaybillPendingDao waybillPendingDao;
	
	/**
	 * 待补录日志插入表
	 */
	private IWaybillToSuppleDao waybillToSuppleDao;
	
	/**
	 * 删除库存
	 */
	private IStockService stockService;
	
	private IWaybillPackBIService waybillPackBIService;
	
	/**
	 * GUI消息提醒的删除
	 */
	private IPickupToDoMsgService pickupToDoMsgService;
	
	protected final static Logger LOG = LoggerFactory.getLogger(WaybillPackBIService.class.getName());
	@Autowired
	private GrayScaleService grayScaleService;
	/**
	 * 添加待补录日志记录表
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-11 15:13:42
	 * @param waybillSupplementLogEntity
	 */
	@Override
	public void addWaybillToSuppleRecord(WaybillSupplementLogEntity waybillSupplementLogEntity) {
		if(waybillSupplementLogEntity == null){
			throw new BusinessException("传入的参数不能为空");
		}
		//获得出库操作人信息
		String operatorCode = FossUserContext.getCurrentInfo().getEmpCode();//员工工号
		String operatorName = FossUserContext.getCurrentInfo().getEmpName();////员工姓名
		String currentOrgCode = FossUserContext.getCurrentDept().getCode();
		if("WAYBILL_INVALID".equals(waybillSupplementLogEntity.getInvalidType())){
			WaybillEntity waybill = waybillDao.queryWaybillByNo(waybillSupplementLogEntity.getOldWaybillNo());
			WaybillPendingEntity waybillPendingEntity = waybillPendingDao.getWaybillPendingEntityByWaybillNo(waybillSupplementLogEntity.getOldWaybillNo());
			if(waybill != null){
				if(StringUtils.isEmpty(waybill.getPendingType())){
					throw new BusinessException("运单对应的处理类型有误，请稍候重试");
				}
				if(waybill != null && waybill.getPendingType().endsWith("ACTIVE")){
					throw new BusinessException("运单信息已经被补录，请起草更改单进行作废中止");
				}
				if(waybill.getPendingType().endsWith("PENDING")){
					//获取对应运单数据ID
					waybillSupplementLogEntity.setWaybillId(waybill.getId());
					//作废运单数据
					WaybillEntity waybillTemp = new WaybillEntity();
					waybillTemp.setActive(FossConstants.NO);
					//设置对应的ID
					waybillTemp.setId(waybill.getId());
					//更新运单
					waybillDao.updateWaybill(waybillTemp);
					//需要将active设置为N
					waybill.setActive(FossConstants.NO);
					waybillPackBIService.addWaybillPackBIEntity(waybill);
				}else if(waybill.getPendingType().endsWith("ACTIVE")){
					throw new BusinessException("运单信息已经被补录，不能作废");
				}
			}
			if(waybillPendingEntity != null){
				//获取对应运单数据ID
				waybillSupplementLogEntity.setWaybillPendingId(waybillPendingEntity.getId());
				WaybillPendingEntity waybillPendingTemp = new WaybillPendingEntity();
				waybillPendingTemp.setActive(FossConstants.NO);
				waybillPendingTemp.setId(waybillPendingEntity.getId());
				waybillPendingDao.updateByPrimaryKeySelective(waybillPendingTemp);
			}
			//删除库存
			WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
			waybillStockEntity.setWaybillNO(waybillSupplementLogEntity.getOldWaybillNo());
			List<StockEntity> labelGoodList = stockService.queryStockByWaybillNo(waybillStockEntity);
			//是否存在，存在直接删除库存
			if(org.apache.commons.collections.CollectionUtils.isNotEmpty(labelGoodList)){
				for(StockEntity serialsNo : labelGoodList){
					InOutStockEntity inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setWaybillNO(serialsNo.getWaybillNO());
					inOutStockEntity.setSerialNO(serialsNo.getSerialNO());
					inOutStockEntity.setOrgCode(currentOrgCode);
					inOutStockEntity.setOperatorCode(operatorCode);
					inOutStockEntity.setOperatorName(operatorName);
					inOutStockEntity.setInOutStockType(StockConstants.INVALID_GOODS_OUT_STOCK_TYPE);
					stockService.outStockDelivery(inOutStockEntity);
				}
			}else{
				if(StringUtils.isEmpty(waybillSupplementLogEntity.getWaybillId()) && StringUtils.isEmpty(waybillSupplementLogEntity.getWaybillPendingId())){
					throw new BusinessException("没有查询到可作废的数据");
				}
			}
			/**
			 * 删除GUI待补录消息的提醒
			 */
			pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL,
					null, waybillSupplementLogEntity.getOldWaybillNo());
		}else if("WAYBILL_RENEW".equals(waybillSupplementLogEntity.getInvalidType())){
			WaybillEntity waybill = waybillDao.queryWaybillByNo(waybillSupplementLogEntity.getOldWaybillNo());
			
			if(waybill != null && waybill.getPendingType().endsWith("ACTIVE")){
				throw new BusinessException("运单信息存在，无需还原");
			}
			//TODO 建议：后期如果作废运单这个权限下放Dao一线，因为数据转储的不能上报差错，这是系统自动转储的，不能由一线承担
			//'CLAIM_PAY'; --申请理赔专用
			//灰度改造 （323098王鹏涛）
			//waybillToSuppleDao.renewWaybillByProcedure(waybillSupplementLogEntity.getOldWaybillNo() , DateUtils.convert("2011-01-01"), new Date(), "CLAIM_PAY");
			returnedGoodsWriteoffReceivableToGrayCubc(waybillSupplementLogEntity.getOldWaybillNo() , DateUtils.convert("2011-01-01"), new Date(), "CLAIM_PAY");
		}
		//设置主键
		waybillSupplementLogEntity.setId(UUIDUtils.getUUID());
		//创建时间
		waybillSupplementLogEntity.setCreateTime(new Date());
		//修改时间
		waybillSupplementLogEntity.setModifyTime(waybillSupplementLogEntity.getCreateTime());
		//作废人坐在组织
		waybillSupplementLogEntity.setInvalidOrgCode(currentOrgCode);
		//设置操作人
		waybillSupplementLogEntity.setOperateCode(operatorCode);
		//添加对应的记录
		waybillToSuppleDao.addWaybillToSuppleRecord(waybillSupplementLogEntity);
	}

	@Override
	public List<WaybillToSuppleResultDto> queryWaybillToSuppleAction(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto, int start, int limit) {
		if(waybillToSuppleCondtionDto == null){
			throw new BusinessException("传入的参数不能为空");
		}
		WaybillToSuppleCondtionDto waybillToSuppleCondtionDtoTem = new WaybillToSuppleCondtionDto();
		if(StringUtils.isNotEmpty(waybillToSuppleCondtionDto.getWaybillNo())){
			waybillToSuppleCondtionDtoTem.setWaybillNo(waybillToSuppleCondtionDto.getWaybillNo());
		}else if(StringUtils.isNotEmpty(waybillToSuppleCondtionDto.getOrderNo())){
			waybillToSuppleCondtionDtoTem.setOrderNo(waybillToSuppleCondtionDto.getOrderNo());
		}else{
			waybillToSuppleCondtionDto.setWaybillNo(null);
			waybillToSuppleCondtionDto.setOrderNo(null);
			waybillToSuppleCondtionDtoTem = waybillToSuppleCondtionDto;
		}
		List<WaybillToSuppleResultDto> list = waybillToSuppleDao.queryWaybillToSuppleAction(waybillToSuppleCondtionDtoTem, start, limit);
		return list;
	}
	
	
	public InputStream exportWaybillSuppleAction(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto) {
		if(waybillToSuppleCondtionDto == null){
			throw new BusinessException("传入的参数不能为空");
		}
		WaybillToSuppleCondtionDto waybillToSuppleCondtionDtoTem = new WaybillToSuppleCondtionDto();
		if(StringUtils.isNotEmpty(waybillToSuppleCondtionDto.getWaybillNo())){
			waybillToSuppleCondtionDtoTem.setWaybillNo(waybillToSuppleCondtionDto.getWaybillNo());
		}else if(StringUtils.isNotEmpty(waybillToSuppleCondtionDto.getOrderNo())){
			waybillToSuppleCondtionDtoTem.setOrderNo(waybillToSuppleCondtionDto.getOrderNo());
		}else{
			waybillToSuppleCondtionDto.setWaybillNo(null);
			waybillToSuppleCondtionDto.setOrderNo(null);
			waybillToSuppleCondtionDtoTem = waybillToSuppleCondtionDto;
		}
		//进行数据的导出
		List<WaybillToSuppleResultDto> oppDtos = waybillToSuppleDao.queryWaybillToSuppleActionAll(waybillToSuppleCondtionDtoTem);
		if(CollectionUtils.isEmpty(oppDtos)){
		    return null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (WaybillToSuppleResultDto dto : oppDtos) {
			List<String> result = new ArrayList<String>();	
			//待补录运单号
			result.add(dto.getOldWaybillNo());
			//新运单号
			result.add(dto.getNewWaybillNo());
			//订单号
			result.add(dto.getOrderNo());
			//工作流号
			result.add(dto.getWorkflowNo());
			//作废类型
			if("WAYBILL_INVALID".equals(dto.getInvalidType())){
				result.add("作废待补录运单");
			}else if ("WAYBILL_RENEW".equals(dto.getInvalidType())){
				result.add("恢复转储运单");
			}else if ("EWAYBILL_INVALID".equals(dto.getInvalidType())){
				result.add("作废电子运单");
			}else{
				result.add(dto.getInvalidType());
			}
			//作废原因
			result.add(dto.getInvalidReason());
			//作废人
			result.add(dto.getInvalidor());
			//作废人所在部门
			result.add(dto.getInvalidOrgName());
			//作废时间
			result.add(DateUtils.convert(dto.getInvalidTime(), DateUtils.DATE_TIME_FORMAT));
			rowList.add(result);
		}
		List<String> rowHeads = new ArrayList<String>();
		// 待补录运单号
		rowHeads.add("待补录运单号");
		// 新运单号
		rowHeads.add("新运单号");
		// 出发站
		rowHeads.add("订单号");
		// 目的站
		rowHeads.add("工作流号");
		//作废类型
		rowHeads.add("作废类型");
		//体积
		rowHeads.add("作废原因");
		//件数
		rowHeads.add("作废人");
		// 包装
		rowHeads.add("作废人所在部门");
		rowHeads.add("作废时间");
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads.toArray(new String[rowHeads.size()]));
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("作废待补录运单记录");
		exportSetting.setSize(NotifyCustomerConstants.EXPORT_NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}

	@Override
	public Long queryWaybillToSuppleActionCount(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto) {
		if(waybillToSuppleCondtionDto == null){
			throw new BusinessException("传入的参数不能为空");
		}
		WaybillToSuppleCondtionDto waybillToSuppleCondtionDtoTem = new WaybillToSuppleCondtionDto();
		if(StringUtils.isNotEmpty(waybillToSuppleCondtionDto.getWaybillNo())){
			waybillToSuppleCondtionDtoTem.setWaybillNo(waybillToSuppleCondtionDto.getWaybillNo());
		}else if(StringUtils.isNotEmpty(waybillToSuppleCondtionDto.getOrderNo())){
			waybillToSuppleCondtionDtoTem.setOrderNo(waybillToSuppleCondtionDto.getOrderNo());
		}else{
			waybillToSuppleCondtionDto.setWaybillNo(null);
			waybillToSuppleCondtionDto.setOrderNo(null);
			waybillToSuppleCondtionDtoTem = waybillToSuppleCondtionDto;
		}
		return waybillToSuppleDao.queryWaybillToSuppleActionCount(waybillToSuppleCondtionDtoTem);
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	public void setWaybillToSuppleDao(IWaybillToSuppleDao waybillToSuppleDao) {
		this.waybillToSuppleDao = waybillToSuppleDao;
	}
	
	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setWaybillPackBIService(IWaybillPackBIService waybillPackBIService) {
		this.waybillPackBIService = waybillPackBIService;
	}
	
	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}
	//waybillToSuppleDao.renewWaybillByProcedure(waybillSupplementLogEntity.getOldWaybillNo() , DateUtils.convert("2011-01-01"), new Date(), "CLAIM_PAY");
	private void returnedGoodsWriteoffReceivableToGrayCubc(String waybillNo, Date beginDate, Date endDate, String claimPay){
	    	RequestDO requestDO = new RequestDO();		
		requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".addWaybillToSuppleRecord");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		requestDO.setSourceBillNos(waybillNo);
		
		VestResponse response=null;
		try {
		    response = grayScaleService.vestAscription(requestDO);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    waybillToSuppleDao.renewWaybillByProcedure(waybillNo, beginDate, endDate, claimPay);
		    return;
		    //e.printStackTrace();
		}
		if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
			List<VestBatchResult> batchResults = response.getVestBatchResult();
			for (int i = 0; i < batchResults.size(); i++) {
				
			}
			VestBatchResult batchResult = batchResults.get(0);
			if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResult.getVestSystemCode())){
			    waybillToSuppleDao.renewWaybillByProcedure(waybillNo, beginDate, endDate, claimPay);
			}
		}
	}
}
