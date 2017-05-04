package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.dao.ITruckArriveConfirmDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IVtsWaybillFinanceBillService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupWriteBackDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author 218392 张永雪  FOSS结算开发组
 * @date 2016-05-20 08:03:20
 * FOSS结算配合VTS项目：开单生成财务单据（开单其他付款方式，不包含银行卡）
 *
 */
public class VtsWaybillFinanceBillService implements IVtsWaybillFinanceBillService{
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(WaybillPickupService.class);
	
	/**
	 * 运单开单服务Service
	 */
	private IWaybillPickupService waybillPickupService;
	
	/**
	 * 坏账服务
	 */
	private IBillBadAccountService billBadAccountService;
	
	/**
	 * 应付单Dao
	 */
	private IBillPayableEntityDao billPayableEntityDao;
	
	/**
	 * 现金收款单服务
	 */
	private IBillCashCollectionService billCashCollectionService;
	
	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 代收货款通用服务
	 */
	private ICodCommonService codCommonService;
	
	/**
	 * 代收货款服务
	 */
	private IBillPayCODService billPayCODService;
	
	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;
	
	/**
	 * 注入IVtsWaybillFinanceBillService
	 */
	private IVtsWaybillFinanceBillService vtsWaybillFinanceBillService;
	
	/**
	 * 注入车辆到达确认Dao
	 */
	private ITruckArriveConfirmDao truckArriveConfirmDao;
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-05-20 08:06:30
	 * 生成财务单据方法
	 */
	@Override
	public void addWaybillFinanceBill(WaybillPickupInfoDto waybillPickupInfoDto,CurrentInfo currentInfo) {
		if(waybillPickupInfoDto != null && currentInfo != null){
			logger.info("stl-consumer vts新增财务单据begin!单号为：" + waybillPickupInfoDto.getWaybillNo());
			waybillPickupService.addWaybill(waybillPickupInfoDto, currentInfo);
			logger.info("stl-consumer vts 开单新增财务单据成功！单号为：" + waybillPickupInfoDto.getWaybillNo());
		}else{
			throw new SettlementException("VTS开单传入实体为空，不能生成财务单据");
		}
	}
	
	/**
	 * @author 218392  张永雪
	 * @date 2016-05-31 22:16:30
	 * VTS运单发生更改：红冲财务单据，再次生成财务单据方法
	 * 运单状态--详细信息:EFFECTIVE已生效,OBSOLETE已作废,ABORTED已中止,UNACTIVE电子运单待激活
	 * 说明：
	 * （1）运单作废：实际承运表运单状态为-OBSOLETE 已作废
	 * （2）运单中止：实际承运表运单状态为-ABORTED  已中止
	 * （3）运单生成、修改：实际承运表运单状态为-EFFECTIVE 已生效
	 * 这个实际承运表是VTS单独走了另一个异步接口同步给FOSS的，不需要我这边再做
	 */
	@Override
	public void modifyWaybillFinanceBill(WaybillPickupInfoDto waybillPickupInfoDto,CurrentInfo currentInfo) {
		
		if(waybillPickupInfoDto != null && currentInfo != null){
			
			logger.info("stl-consumer vts更改单财务单据操作开始begin!单号为：" + waybillPickupInfoDto.getWaybillNo());
			
			//获取VTS操作类型：1.新增：add 2.更改：update 3.作废：disable 4.中止：stop;这里肯定没有add的
			String openBillType = waybillPickupInfoDto.getOpenBillType();
			
			if("disable".equals(openBillType) || "stop".equals(openBillType)){//如果是作废或者中止：红冲财务单据 如果是
				//设置运单状态为已作废-实际承运表中的状态 actualFreight.setStatus(WaybillConstants.OBSOLETE)
				String waybillNo = waybillPickupInfoDto.getWaybillNo();
				//红冲财务单据
				this.handleRedCancleFinanceBill(waybillPickupInfoDto,waybillNo, currentInfo);
				//标识业务完结
				WaybillTransactionEntity te = new WaybillTransactionEntity();
				te.setWaybillNo(waybillNo);
				//业务完结 businessOver='Y'
				waybillTransactionService.updateBusinessOver(te);
			}else if("update".equals(openBillType)){//如果是修改：红冲财务单据，重新生成新的财务单据
				//设置运单状态为已中止--实际承运表中的状态 actualFreight.setStatus(WaybillConstants.ABORTED)
				String waybillNo = waybillPickupInfoDto.getWaybillNo();
				//红冲财务单据
				this.handleRedCancleFinanceBill(waybillPickupInfoDto,waybillNo, currentInfo);
				//标识业务完结
				WaybillTransactionEntity te = new WaybillTransactionEntity();
				te.setWaybillNo(waybillNo);
				//业务完结 businessOver='Y'
				waybillTransactionService.updateBusinessOver(te);
				
				//新增财务单据
				vtsWaybillFinanceBillService.addWaybillFinanceBill(waybillPickupInfoDto, currentInfo);
				
			}
			
			logger.info("stl-consumer vts 更改单操作财务单据成功end！单号为：" + waybillPickupInfoDto.getWaybillNo());
			
		}else{
			
			throw new SettlementException("VTS更改单传入实体为空，不能生成财务单据!");
			
		}
	}

	
	/**
	 * @author 218392  张永雪
	 * @date 2016-06-01 13:32:30
	 * VTS运单发生更改：红冲财务单据，再次生成财务单据方法
	 */
	@Override
	public WaybillPickupWriteBackDto handleRedCancleFinanceBill(WaybillPickupInfoDto waybillPickupInfoDto,String waybillNo,CurrentInfo currentInfo) {
		// 坏账校验
		int i = billBadAccountService.queryByWaybillNO(waybillNo);
		if (i > 0) {
			throw new SettlementException("坏账申请审批完成，运单不允许更改");
		}
		
		//校验折扣单状态
/*		String status = waybillPickupService.querydiscountPayable(waybillNo);*/
		
		/**
		 * 1. 红冲现金收款单
		 */
		List<BillCashCollectionEntity> billCashes = billCashCollectionService
				.queryBySourceBillNOs(
						Arrays.asList(waybillNo),
						SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.ACTIVE);
		//校验现金收款单
		if(CollectionUtils.isNotEmpty(billCashes)){
			//list大小不能大于1
			if(billCashes.size()>1){
				throw new SettlementException("同一个运单存在多条现金收款单");
			}
		}
		// 红冲现金收款单
		if (CollectionUtils.isNotEmpty(billCashes)) {
			for (BillCashCollectionEntity entity : billCashes) {
				// 已签收，不能红冲
				if (entity.getConrevenDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲现金收款单操作");
				}
				billCashCollectionService.writeBackBillCashCollection(entity,currentInfo);
			}
		}
		
		/**
		 * 2.红冲应收单
		 */
		// 查询应收单（始发应收、到达应收）
		BillReceivableConditionDto billReceivableConditionDto = new BillReceivableConditionDto(waybillNo);
			billReceivableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
//				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
//				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
//				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,
//				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,
//				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
				});

		List<BillReceivableEntity> billReceives = billReceivableService.queryBillReceivableByCondition(billReceivableConditionDto);
		if (CollectionUtils.isNotEmpty(billReceives)) {
			int orig = 0;// 始发标记
			int dest = 0;// 到达标记
			int cod = 0;// 代收货款标记

			for (BillReceivableEntity entity : billReceives) {
				if (entity != null) {
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
							.equals(entity.getBillType())) {
						orig += 1;
						if (orig > 1) {
							throw new SettlementException("同一个运单，存在多条始发应收单");
						}
					} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
							.equals(entity.getBillType())// 专线到达运费应收单
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
									.equals(entity.getBillType())// 偏线到达运费应收单
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
									.equals(entity.getBillType())// 空运到达运费应收单
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
							.equals(entity.getBillType()) // 到达快递代理应收 ISSUE-3389 小件业务
					          ) {
						dest += 1;
						if (dest > 1) {
							throw new SettlementException("同一个运单，存在多条到达运费应收单");
						}
					} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
							.equals(entity.getBillType())
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
									.equals(entity.getBillType())
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
									.equals(entity.getBillType()) // 快递代理代收货款应收单 ISSUE-3389 小件业务
							  ) {
						cod += 1;
						if (cod > 1) {
							throw new SettlementException("同一个运单，存在多条代收货款应收单");
						}
					}
				}
			}
		}
		
		// 红冲应收单，包括代收货款
		if (CollectionUtils.isNotEmpty(billReceives)) {
			for (BillReceivableEntity entity : billReceives) {
				// 已签收，不能红冲
				if (entity.getConrevenDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲应收单操作");
				}
				//查询运单实体,是否是整车
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(entity.getWaybillId());
				// 是否整车：是-Y 否-N
				String isWholeVehicle = waybillEntity.getIsWholeVehicle();
                //判断是否整车
                if(FossConstants.YES.equals(isWholeVehicle)){
					billReceivableService.writeBackBillReceivable(entity,currentInfo);
				}else{
					if(!(entity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)
							||entity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE)))
					billReceivableService.writeBackBillReceivable(entity,currentInfo);
				}

			}
		}
		
		/**
		 * 3.红冲应付单
		 */
		// 查询应付单（装卸费应付）
		BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto(
				waybillNo);
		
		/**
		 * VTS如果是作废，是要红冲整车首尾款，如果是修改不作废整车首尾款
		 */
		if("disable".equals(waybillPickupInfoDto.getOpenBillType())){
			billPayableConditionDto
			.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST});
		}else{
			billPayableConditionDto
			.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,});
		}

		List<BillPayableEntity> billPayables = billPayableService
				.queryBillPayableByCondition(billPayableConditionDto);
		
		//校验装卸费应付单
		if (CollectionUtils.isNotEmpty(billPayables)) {
			//cod代表代收货款应付单个数
			int cod = 0;
			//sic代表装卸费应付单个数
			int sic = 0;
			for (BillPayableEntity entity : billPayables) {
				//应付单实体不为空时
				if (entity != null) {
					if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
							.equals(entity.getBillType())) {
						cod += 1;
						if (cod > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条代收货款应付单");
						}
					} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
							.equals(entity.getBillType())) {
						sic += 1;
						if (sic > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条装卸费应付单");
						}
					} 
				}
			}
		}
		
		// 红冲应付单，包括代收货款
		if (CollectionUtils.isNotEmpty(billPayables)) {
			for (BillPayableEntity entity : billPayables) {
				// 已签收，不能红冲
				if (entity.getSignDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲应付单操作");
				}

				billPayableService.writeBackBillPayable(entity, currentInfo);
				
			}
		}
		
		/**
		 * @author 218392 zhangyongxue
		 * 查询整车首尾款应付单，目的为了ID
		 */
		
		//如果是作废，那么就往凭证报表中插入UAC的记录
		if("disable".equals(waybillPickupInfoDto.getOpenBillType())){
			BillPayableConditionDto conditionDto = new BillPayableConditionDto(waybillNo);
			conditionDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST});
			// 非有效单据
			conditionDto.setActive("N");
			// 红单
			conditionDto.setIsRedBack("N");
			List<BillPayableEntity> payEntityList = billPayableEntityDao.queryBillPayableByCondition(conditionDto);
			
			String redFirstId = "";//红冲的应付单首款ID
			String redLastId = "";//红冲的应付单尾款ID
			if(CollectionUtils.isNotEmpty(payEntityList)){
				for(BillPayableEntity entity : payEntityList){
					
					if("TF2".equals(entity.getBillType())){
						redFirstId = entity.getId();
					}
					
					if("TL2".equals(entity.getBillType())){
						redLastId = entity.getId();
					}
					
				}
				//根据整车尾款应付单ID查询到达确认凭证表数据
				 String payableLastID = redLastId;//获取整车尾款应付单ID
				 TruckArriveConfirmEntity payableLastEntity = new TruckArriveConfirmEntity();
				 payableLastEntity.setPayablelastId(payableLastID);//整车尾款应付单ID
				 payableLastEntity.setConertType("TAC");//确认类型 TAC 到达确认  UAC 反到达确认
				 List<TruckArriveConfirmEntity> entityList = truckArriveConfirmDao.queryTruckConfirmByEntity(payableLastEntity);
				 
				 if(CollectionUtils.isNotEmpty(entityList)){//判断空
					 TruckArriveConfirmEntity truckArriveConfirmEntity = entityList.get(0);
					 /**
					  * 反确认：红单ID
					  */
					 TruckArriveConfirmEntity redTruckEntity = new TruckArriveConfirmEntity();
					 //VTS做过到达确认之后，先将确认凭证表的数据反确认
					 redTruckEntity.setId(UUIDUtils.getUUID());//ID
					 redTruckEntity.setConertType("UAC");//反确认
					 redTruckEntity.setConvertDate(truckArriveConfirmEntity.getConvertDate());//和原来到达确认时间一样
					 redTruckEntity.setCreateTime(new Date());//当前时间
					 redTruckEntity.setCreateUser("VTS");
					 redTruckEntity.setCreateDept("VTS");
					 redTruckEntity.setHandleNo(truckArriveConfirmEntity.getHandleNo());
					 redTruckEntity.setPayablelastId(truckArriveConfirmEntity.getPayablelastId());
					 redTruckEntity.setPayablefirstId(truckArriveConfirmEntity.getPayablefirstId());
					 truckArriveConfirmDao.insertSelective(redTruckEntity);
				 }

			}
			
		}
		
		
		// 作废代收货款单
		CODEntity codEntity = codCommonService.queryByWaybill(waybillNo);
		if (codEntity != null
				&& FossConstants.ACTIVE.equals(codEntity.getActive())) {
			this.billPayCODService.cancelBillCOD(codEntity, currentInfo);
		}

		// 红冲的结算单据信息
		WaybillPickupWriteBackDto dto = new WaybillPickupWriteBackDto();
		dto.setWriteBackBillCashCollections(billCashes); // 红冲的现金收款单
		dto.setWriteBackBillReceivables(billReceives); // 红冲的应收单
		dto.setWriteBackBillPayables(billPayables); // 红冲的应付单
		dto.setWriteBackCOD(codEntity); // 红冲的代收货款
		return dto;
	}
	
	
	
	
	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}
	
	public void setBillBadAccountService(
			IBillBadAccountService billBadAccountService) {
		this.billBadAccountService = billBadAccountService;
	}

	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	public void setBillPayCODService(IBillPayCODService billPayCODService) {
		this.billPayCODService = billPayCODService;
	}

	public void setWaybillTransactionService(
			IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}

	public void setVtsWaybillFinanceBillService(
			IVtsWaybillFinanceBillService vtsWaybillFinanceBillService) {
		this.vtsWaybillFinanceBillService = vtsWaybillFinanceBillService;
	}

	public void setBillPayableEntityDao(IBillPayableEntityDao billPayableEntityDao) {
		this.billPayableEntityDao = billPayableEntityDao;
	}

	public void setTruckArriveConfirmDao(
			ITruckArriveConfirmDao truckArriveConfirmDao) {
		this.truckArriveConfirmDao = truckArriveConfirmDao;
	}

}
