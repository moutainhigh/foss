package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.settlement.common.api.server.dao.IReverseSignSettleDao;
import com.deppon.foss.module.settlement.common.api.server.dao.ITruckArriveConfirmDao;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsSyncWaybillAndActualDao;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsOutvehicleFeeAdjustService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsSyncWaybillAndActualService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillStatusSyncToFossEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整车项目同步运单和实际承运表数据
 * 
 * @ClassName: IVtsCreateWaybillAndActual
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-5-5 下午6:42:38
 */
public class VtsSyncWaybillAndActualService implements
		IVtsSyncWaybillAndActualService {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(VtsSyncWaybillAndActualService.class);

	/**
	 * 注入Dao
	 */
	private IVtsSyncWaybillAndActualDao vtsSyncWaybillAndActualDao;
	
	/**
	 * 注入dao reverseSignSettleDao
	 */
	private IReverseSignSettleDao reverseSignSettleDao;
	
    /**
     * 注入 IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService
     * @param businessLockService
     */
    private IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService;
    
	/**
	 * 注入车辆到达确认Dao
	 */
	private ITruckArriveConfirmDao truckArriveConfirmDao;

	/**
	 * 同步运单数据
	 * 
	 * syncWayBillMessage
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:42:38
	 */
	@Transactional
	@Override
	public int syncWayBillMessage(WaybillEntity entity) {
		logger.info("***********同步运单数据开始***********");
		
		/**
		 * 运单修改时间处理,运单插入,不存在修改，则修改时间为创建时间，如果自动审核，则修改时间为2999年
		 * 开单和不是自动审核的话，修改时间都为创建时间
		 */
		String isWaybillRfc = "false";
		/**
		 * 整车存在自动受理，自动受理的时候传递久的运单ID过来，如果存在值，则判断为自动受理，直接修改老运单ID为无效
		 */
		if(StringUtils.isNotEmpty(entity.getOldVersionWaybillId())){
			isWaybillRfc = "true";
			//修改老的运单为无效
			int r = vtsSyncWaybillAndActualDao.cancelWaybillEntity(entity.getOldVersionWaybillId());
			if(r == 0){
				throw new SettlementException("整车自动审核的时候传入的ID在FOSS无对应数据,ID为： "+entity.getOldVersionWaybillId());
			}
		}
		//是否为运单更改,自动审核
		entity.setIsWaybillRfc(isWaybillRfc);
		/**
		 * 运单数据处理,假如存在一条有效的运单数据，则判断是运单更改， 处理方法：1、作废之前的运单数据重新插入新的数据
		 * 如果不存在有效的数据，则判断为插入数据
		 */
		validateParam(entity);
		// 插入运单信息
		int result = vtsSyncWaybillAndActualDao.buildWayBillMessage(entity);
		if(result == 0){
			throw new SettlementException("同步运单表,插入条数为0！");
		}
		logger.info("***********同步运单数据结束***********");
		return result;
	}

	/**
	 * 校验传入的字段值为数字的不能为空
	 * 
	 * @Title: validateParam
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-6-18 下午2:13:12
	 */
	private void validateParam(WaybillEntity entity) {
		logger.info("校验运单参数开始");
		if (entity.getValueAddFee() == null) {
			throw new SettlementException("VALUE_ADD_FEE字段值为null");
		}
		if (entity.getTransportFee() == null) {
			throw new SettlementException("TRANSPORT_FEE字段值为null");
		}
		if (entity.getPromotionsFee() == null) {
			throw new SettlementException("PROMOTIONS_FEE字段值为null");
		}
		if (entity.getPackageFee() == null) {
			throw new SettlementException("PACKAGE_FEE字段值为null");
		}
		if (entity.getOtherFee() == null) {
			throw new SettlementException("OTHER_FEE字段值为null");
		}
		if (entity.getPrePayAmount() == null) {
			throw new SettlementException("PRE_PAY_AMOUNT字段值为null");
		}
		if (entity.getToPayAmount() == null) {
			throw new SettlementException("TO_PAY_AMOUNT字段值为null");
		}
		if (entity.getGoodsQtyTotal() == null) {
			throw new SettlementException("GOODS_QTY_TOTAL字段值为null");
		}
		if (entity.getGoodsWeightTotal() == null) {
			throw new SettlementException("GOODS_WEIGHT_TOTAL字段值为null");
		}
		if (entity.getGoodsVolumeTotal() == null) {
			throw new SettlementException("GOODS_VOLUME_TOTAL字段值为null");
		}

		if (entity.getInsuranceAmount() == null) {
			throw new SettlementException("INSURANCE_AMOUNT字段值为null");
		}
		if (entity.getCodRate() == null) {
			throw new SettlementException("COD_RATE字段值为null");
		}
		if (entity.getCodFee() == null) {
			throw new SettlementException("COD_FEE字段值为null");
		}
		if (entity.getWholeVehicleAppfee() == null) {
			throw new SettlementException("WHOLE_VEHICLE_APPFEE字段值为null");
		}
		if (entity.getCodAmount() == null) {
			throw new SettlementException("COD_AMOUNT字段值为null");
		}
		if (entity.getMembraneNum() == null) {
			throw new SettlementException("MEMBRANE_NUM字段值为null");
		}
		if (entity.getSalverNum() == null) {
			throw new SettlementException("SALVER_NUM字段值为null");
		}
		if (entity.getFibreNum() == null) {
			throw new SettlementException("FIBRE_NUM字段值为null");
		}
		if (entity.getWoodNum() == null) {
			throw new SettlementException("WOOD_NUM字段值为null");
		}
		if (entity.getPaperNum() == null) {
			throw new SettlementException("PAPER_NUM字段值为null");
		}
		if (entity.getUnitPrice() == null) {
			throw new SettlementException("UNIT_PRICE字段值为null");
		}
		if (entity.getInsuranceFee() == null) {
			throw new SettlementException("INSURANCE_FEE字段值为null");
		}
		if (entity.getInsuranceRate() == null) {
			throw new SettlementException("INSURANCE_RATE字段值为null");
		}
		if (entity.getServiceFee() == null) {
			throw new SettlementException("SERVICE_FEE字段值为null");
		}
		if (entity.getBillWeight() == null) {
			throw new SettlementException("BILL_WEIGHT字段值为null");
		}
		if (entity.getWholeVehicleActualfee() == null) {
			throw new SettlementException("WHOLE_VEHICLE_ACTUALFEE字段值为null");
		}
		logger.info("校验运单参数结束");

	}

	/**
	 * 同步实际承运信息 存在则更新，不存在则插入 syncActualFreightMessage
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:42:38
	 */
	@Transactional 
	@Override
	public int syncActualFreightMessage(ActualFreightEntity entity) {
		logger.info("***********同步实际承运表数据开始***********");
		if(entity == null){
			throw new SettlementException("同步实际承运表传入的实体为null！");
		}

		/**
		 * 插入实际承运信息
		 */
		int result = vtsSyncWaybillAndActualDao
				.buildActualFreightMessage(entity);
		if(result == 0){
			throw new SettlementException("同步实际承运表,操作条数为0！");
		}
		
		/**
		 * 1.首先校验整车尾款应付单
		 */
		BillPayableConditionDto conDto = new BillPayableConditionDto();
		conDto.setWaybillNo(entity.getWaybillNo());//单号
		conDto.setActive("Y");//有效
		conDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST});//整车尾款TL2
		conDto.setIsRedBack("N");//非红单
		List<BillPayableEntity> list = vtsOutvehicleFeeAdjustService.queryTLBillPayableByWaybillNo(conDto);
		
		String firstPayableId = "";//整车首款应付单ID
		String lastPayableId = "";//整车尾款应付单ID
		String handleNo = "";//来源单号：整车运输合同编码
		if (CollectionUtils.isNotEmpty(list)) {
			for(BillPayableEntity billPayableEntity : list){
				if("TF2".equals(billPayableEntity.getBillType())){
					//获取整车首款应付单ID
					firstPayableId = billPayableEntity.getId();
					handleNo = billPayableEntity.getSourceBillNo();
				}else if("TL2".equals(billPayableEntity.getBillType())){
					//获取整车尾款应付单ID
					lastPayableId = billPayableEntity.getId();
					handleNo = billPayableEntity.getSourceBillNo();
				}
			}
			
			/**
			 * @author 218392 张永雪
			 * @2016-06-30 9:12:30
			 * 根据尾款ID查询凭证报表
			 * （1）如果没有新增一条；
			 * （2）如果有则不新增
			 * 加入这个判断目的是防止车辆到达之后，运单又发了更改,那么就造成多插入
			 */
			List<TruckArriveConfirmEntity> dtoList = new ArrayList<TruckArriveConfirmEntity>();
			TruckArriveConfirmEntity dto = new TruckArriveConfirmEntity();
			dto.setConertType("TAC");
			dto.setPayablelastId(lastPayableId);
			dtoList = truckArriveConfirmDao.queryTruckConfirmByEntity(dto);
			
			if(CollectionUtils.isEmpty(dtoList)){
				 TruckArriveConfirmEntity payableLastEntity = new TruckArriveConfirmEntity();
					/**
					 * @author 218392 张永雪
					 * 判断如果到达时间不为空，说明做了到达确认，那么要往“整车外请车到达确认凭证表”插入一条数据
					 */
					if( entity.getArriveTime() != null){
						 payableLastEntity.setPayablefirstId(firstPayableId);//整车首款应付单ID
						 payableLastEntity.setPayablelastId(lastPayableId);//整车尾款应付单ID
						 payableLastEntity.setConertType("TAC");//确认类型 TAC 到达确认  UAC 反到达确认
						 payableLastEntity.setConvertDate(entity.getArriveTime());//设置到达时间
						 payableLastEntity.setCreateTime(new Date());//设置创建时间
						 payableLastEntity.setHandleNo(handleNo);
						 payableLastEntity.setCreateDept("VTS");
						 truckArriveConfirmDao.insertSelective(payableLastEntity);
					}
			}
		}
		logger.info("***********同步实际承运表数据结束***********");
		return result;
	}

	/**
	 * 同步运单更改表信息
	 * 
	 * syncActualFreightMessage
	 * 
	 * @author &306579 |guoxinru
	 * @date 2016-5-17
	 */
	@Override
	@Transactional
	public int syncWayBillRfcMessage(WaybillRfcEntity entity) {
		logger.info("***********调用结算service同步运单更改表数据开始***********");
		int result = 0;
		// 查询是否存在此单号未审核、未受理的更改单；
		int queryNum = vtsSyncWaybillAndActualDao.queryExsitWaybillRfc(entity);
		if (queryNum > 1) {
			logger.error("存在不止一条未审核/未受理更改单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在不止一条未审核/未受理更改单！");
		} else {
			/*
			 * //查询该单号最后一条无效运单id String activeNid =
			 * queryLastActiveNWaybillNo(entity.getWaybillNo());
			 * if(StringUtils.isEmpty(activeNid)){
			 * logger.error("vts同步运单更改表时查询无效运单错误！"); throw new
			 * SettlementException("vts同步运单更改表时查询无效运单错误！"); } //查询该单号有效运单id
			 * String activeYid = queryActiveYWaybillNo(entity.getWaybillNo());
			 * if(StringUtils.isEmpty(activeYid)){
			 * logger.error("vts同步运单更改表时查询有效运单错误！"); throw new
			 * SettlementException("vts同步运单更改表时查询有效运单错误！"); } //运单更改表设置无效运单id
			 * entity.setOldVersionWaybillId(activeNid); //运单更改表设置有效运单id
			 * entity.setNewVersionWaybillId(activeYid);
			 */
			if (queryNum == 1) {
				// 该单号存在一条未审核、未受理的更改单，更新此数据；
				result = vtsSyncWaybillAndActualDao
						.updateWayBillRfcMessage(entity);
			} else {
				// 该单号不存在未审核、未受理的更改单，直接插入数据；
				result = vtsSyncWaybillAndActualDao
						.buildWayBillRfcMessage(entity);
			}
		}
		logger.info("***********调用结算service同步运单更改表数据结束***********");
		return result;
	}

	/**
	 * 查询该单号有效运单id
	 * 
	 * @param waybillNo
	 */
	private String queryActiveYWaybillNo(String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", "Y");
		return this.vtsSyncWaybillAndActualDao.queryActiveYWaybillNo(map);
	}

	/**
	 * 查询该单号最后一条无效运单id
	 * 
	 * @param waybillNo
	 */
	private String queryLastActiveNWaybillNo(String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", "N");
		return this.vtsSyncWaybillAndActualDao.queryLastActiveNWaybillNo(map);
	}

	/**
	 * 同步签收结果表信息数据
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-23
	 */
	@Override
	@Transactional
	public int syncWaybillSignResult(WaybillSignResultEntity entity) {
		// 1.同步签收结果表
		logger.info("调用结算service同步签收结果表数据开始");
		int num = vtsSyncWaybillAndActualDao.buildWaybillSignResult(entity);
		if (num == 0) {
			logger.error("插入签收结果表条数为0;运单号：" + entity.getWaybillNo());
			throw new SettlementException("插入签收结果表条数为0;运单号:"
					+ entity.getWaybillNo());
		}
		logger.info("调用结算service同步签收结果表数据成功");
		// 2.向财务签收表中插入数据
		logger.info("调用结算service财务签收表新增数据开始");
		int result = vtsSyncWaybillAndActualDao.insertPod(entity);
		if (result == 0) {
			logger.error("插入财务签收表条数为0;运单号：" + entity.getWaybillNo());
			throw new SettlementException("插入财务签收表条数为0;运单号:"
					+ entity.getWaybillNo());
		}
		logger.info("调用结算service同步签收结果表数据成功");
		// 3.代收货款、装卸费应付单插入签收时间；
		// 3.1根据运单号查询是否存在代收货款应付单；
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", entity.getWaybillNo());
		map.put("billType", "APC");
		map.put("active", "Y");
		int countApc = vtsSyncWaybillAndActualDao.queryApcOrRfPayable(map);
		if (countApc > 1) {
			logger.error("运单号：" + entity.getWaybillNo() + "存在多条代收货款应付单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在多条代收货款应付单！");
		} else if (countApc == 1) {
			int numApc = vtsSyncWaybillAndActualDao.updateApcPayable(entity);
			if (numApc != 1) {
				logger.error("运单号：" + entity.getWaybillNo()
						+ "更新代收货款应付单签收时间失败！");
				throw new SettlementException("运单号：" + entity.getWaybillNo()
						+ "更新代收货款应付单签收时间失败！");
			}
			logger.info("运单号：" + entity.getWaybillNo() + "更新代收货款应付单签收时间成功！");
		} else {
			logger.info("运单号：" + entity.getWaybillNo() + "无代收货款应付单！");
		}

		// 3.2根据运单号查询是否存在装卸费应付单；
		map.put("billType", "SF");
		int countSf = vtsSyncWaybillAndActualDao.queryApcOrRfPayable(map);
		if (countSf > 1) {
			logger.error("运单号：" + entity.getWaybillNo() + "存在多条装卸费应付单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在多条装卸费应付单！");
		} else if (countSf == 1) {
			int numApc = vtsSyncWaybillAndActualDao.updateSfPayable(entity);
			if (numApc != 1) {
				logger.error("运单号：" + entity.getWaybillNo() + "更新装卸费应付单签收时间失败！");
				throw new SettlementException("运单号：" + entity.getWaybillNo()
						+ "更新装卸费应付单签收时间失败！");
			}
			logger.info("运单号：" + entity.getWaybillNo() + "更新装卸费应付单签收时间成功！");
		} else {
			logger.info("运单号：" + entity.getWaybillNo() + "无装卸费应付单！");
		}

		// 4.现金收款单插入签收时间；
		Map<String, String> cashMap = new HashMap<String, String>();
		cashMap.put("waybillNo", entity.getWaybillNo());
		cashMap.put("active", "Y");
		int countCash = vtsSyncWaybillAndActualDao.queryCashColl(cashMap);
		if (countCash > 1) {
			logger.error("运单号：" + entity.getWaybillNo() + "存在多条现金收款单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在多条现金收款单！");
		} else if (countCash == 1) {
			int numCash = vtsSyncWaybillAndActualDao.updateCashColl(entity);
			if (numCash != 1) {
				logger.error("运单号：" + entity.getWaybillNo() + "更新现金收款单失败！");
				throw new SettlementException("运单号：" + entity.getWaybillNo()
						+ "更新现金收款单失败！");
			}
			logger.info("运单号：" + entity.getWaybillNo() + "更新现金收款单成功！");
		} else {
			logger.info("运单号：" + entity.getWaybillNo() + "无现金收款单！");
		}

		// 5.应收单插入签收时间；
		// 5.1根据运单号查询是否存在代收货款应收单；
		Map<String, String> recMap = new HashMap<String, String>();
		recMap.put("waybillNo", entity.getWaybillNo());
		recMap.put("active", "Y");
		recMap.put("billType", "CR");
		int countCr = vtsSyncWaybillAndActualDao.queryCrDrOrReceivable(recMap);
		if (countCr > 1) {
			logger.error("运单号：" + entity.getWaybillNo() + "存在多条代收货款应收单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在多条代收货款应收单！");
		} else if (countCr == 1) {
			int numCr = vtsSyncWaybillAndActualDao.updateCrReceivable(entity);
			if (numCr != 1) {
				logger.error("运单号：" + entity.getWaybillNo()
						+ "更新代收货款应收单签收时间失败！");
				throw new SettlementException("运单号：" + entity.getWaybillNo()
						+ "更新代收货款应收单签收时间失败！");
			}
			logger.info("运单号：" + entity.getWaybillNo() + "更新代收货款应收单签收时间成功！");
		} else {
			logger.info("运单号：" + entity.getWaybillNo() + "无代收货款应收单！");
		}

		// 3.2根据运单号查询是否存在到达应收单；
		recMap.put("billType", "DR");
		int countDr = vtsSyncWaybillAndActualDao.queryCrDrOrReceivable(recMap);
		if (countDr > 1) {
			logger.error("运单号：" + entity.getWaybillNo() + "存在多条到付运费应收单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在多条到付运费应收单！");
		} else if (countDr == 1) {
			int numDr = vtsSyncWaybillAndActualDao.updateDrReceivable(entity);
			if (numDr != 1) {
				logger.error("运单号：" + entity.getWaybillNo()
						+ "更新到付运费应收单签收时间失败！");
				throw new SettlementException("运单号：" + entity.getWaybillNo()
						+ "更新到付运费应收单签收时间失败！");
			}
			logger.info("运单号：" + entity.getWaybillNo() + "更新到付运费应收单签收时间成功！");
		} else {
			logger.info("运单号：" + entity.getWaybillNo() + "无到付运费应收单！");
		}

		// 3.2根据运单号查询是否存始发提成应收单；
		recMap.put("billType", "OR");
		int countOr = vtsSyncWaybillAndActualDao.queryCrDrOrReceivable(recMap);
		if (countOr > 1) {
			logger.error("运单号：" + entity.getWaybillNo() + "存在多条始发提成应收单！");
			throw new SettlementException("运单号：" + entity.getWaybillNo()
					+ "存在多条始发提成应收单！");
		} else if (countOr == 1) {
			int numOr = vtsSyncWaybillAndActualDao.updateOrReceivable(entity);
			if (numOr != 1) {
				logger.error("运单号：" + entity.getWaybillNo()
						+ "更新始发提成应收单签收时间失败！");
				throw new SettlementException("运单号：" + entity.getWaybillNo()
						+ "更新始发提成应收单签收时间失败！");
			}
			logger.info("运单号：" + entity.getWaybillNo() + "更新始发提成应收单签收时间成功！");
		} else {
			logger.info("运单号：" + entity.getWaybillNo() + "无始发提成应收单！");
		}
		return 0;

	}

	/**
	 * 修改运单状态开始
	 */
	@Override
	public int syncWaybillUpdateState(WaybillStatusSyncToFossEntity entity) {
		logger.info("VTS--修改运单状态开始");
		if (entity == null) {
			throw new SettlementException("要更改的运单为空......");
		}
		if (StringUtils.isEmpty(entity.getNewWaybillActive())) {
			throw new SettlementException("要更改的运单新状态为空...");
		}
		if (StringUtils.isEmpty(entity.getNewWaybillId())) {
			throw new SettlementException("要更改的运单新id为空...");
		}
		if (StringUtils.isEmpty(entity.getOldWaybillActive())) {
			throw new SettlementException("要更改的运单旧状态为空...");
		}
		if (StringUtils.isEmpty(entity.getOldWaybillId())) {
			throw new SettlementException("要更改的运单旧id为空...");
		}
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		/**
		 * 作废开单时候传递过来的运单信息,修改modify_time 审核的时候传递过来的运单信息则不用修改modify_time
		 * 审核之后的创建时间修改为作废时间
		 */
		map.put("active", entity.getNewWaybillActive().trim());
		map.put("id", entity.getNewWaybillId().trim());
		map.put("createTime","Y");
		count = count + vtsSyncWaybillAndActualDao.syncWaybillUpdateState(map);
		/**
		 * 
		 */
		map = new HashMap<String, String>();
		map.put("active", entity.getOldWaybillActive().trim());
		map.put("id", entity.getOldWaybillId().trim());
		map.put("modifyTime", "Y");
		count = count + vtsSyncWaybillAndActualDao.syncWaybillUpdateState(map);
		logger.info("VTS--修改运单状态开始");
		return count;
	}

	/* setter */
	public void setVtsSyncWaybillAndActualDao(
			IVtsSyncWaybillAndActualDao vtsSyncWaybillAndActualDao) {
		this.vtsSyncWaybillAndActualDao = vtsSyncWaybillAndActualDao;
	}

	public void setReverseSignSettleDao(IReverseSignSettleDao reverseSignSettleDao) {
		this.reverseSignSettleDao = reverseSignSettleDao;
	}

	/**
	 * 根据运单号+active为Y查询签收结果表信息
	 * @author 218392
	 * @date 2016-06-24 19:02:00
	 */
	@Override
	public VTSWaybillSignResultEntity queryWaybillSignResultByNo(
			VTSWaybillSignResultEntity entity) {
		if(entity == null){
			throw new SettlementException("签收的时候，查询签收结果表传入参数为空！");
		}
		logger.info("vts反签收校查询签收结果表开始!单号为:" + entity.getWaybillNo());
		VTSWaybillSignResultEntity signResultEntity = reverseSignSettleDao.queryWaybillSignResult(entity);
		return signResultEntity;
	}

	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-06-24 19:41:23
	 * 查询签收结果表不为空
	 */
	@Override
	public void handlerSignResultWaybill(VTSWaybillSignResultEntity querySignResultEntity,WaybillSignResultEntity entity) {
		
		//如果是部分签收:先把上一条置成N,同时把上一条的修改时间设置成当前时间,再插入这条新的数据为Y，同时把创建时间改成当前时间，修改时间为2999-12-31
		if("SIGN_STATUS_PARTIAL".equals(querySignResultEntity.getSignStatus())){
			//1.把有效的这条置成N
			logger.info("签收的时候把上调记录置成无效开始，单号为:" + querySignResultEntity.getWaybillNo());
			Date modifyTime = new Date();//修改时间
			String id = querySignResultEntity.getId();//获取ID
			VTSWaybillSignResultEntity updateSignResultEntity = new VTSWaybillSignResultEntity();
			updateSignResultEntity.setModifyTime(modifyTime);//set修改时间
			updateSignResultEntity.setActive("N");//set有效状态为N
			updateSignResultEntity.setId(id);//set主键ID，根据这个ID来修改签收结果表为N
			reverseSignSettleDao.updateWaybillSignResult(updateSignResultEntity);
			logger.info("签收的时候把上调记录置成无效结束，单号为:" + querySignResultEntity.getWaybillNo());
			
			//2.新增一条为Y的记录
			logger.info("插入签收结果表记录开始，单号为：" + querySignResultEntity.getWaybillNo());
			entity.setCreateTime(modifyTime);
			int num = vtsSyncWaybillAndActualDao.buildWaybillSignResult(entity);
			if (num == 0) {
				logger.error("插入签收结果表条数为0;运单号：" + entity.getWaybillNo());
				throw new SettlementException("插入签收结果表条数为0;运单号:"
						+ entity.getWaybillNo());
			}
			logger.info("插入签收结果表记录结束，单号为：" + querySignResultEntity.getWaybillNo());
			
		//如果这次签收状态为全部签收，那么只插入为N的记录:走这条记录的原因，异步接口最后一次全部签收的，FOSS这边是提前接收了
		}else if("SIGN_STATUS_ALL".equals(querySignResultEntity.getSignStatus())){
			logger.info("插入签收结果表记录开始，单号为：" + querySignResultEntity.getWaybillNo());
			Date date = new Date();
			entity.setActive("N");
			entity.setCreateTime(date);
			entity.setModifyTime(date);
			entity.setModifyUser("VTS_218392");
			int num = vtsSyncWaybillAndActualDao.buildWaybillSignResult(entity);
			if (num == 0) {
				logger.error("插入签收结果表条数为0;运单号：" + entity.getWaybillNo());
				throw new SettlementException("插入签收结果表条数为0;运单号:"
						+ entity.getWaybillNo());
			}
			logger.info("插入签收结果表记录结束，单号为：" + querySignResultEntity.getWaybillNo());
		}
	}

	public void setVtsOutvehicleFeeAdjustService(
			IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService) {
		this.vtsOutvehicleFeeAdjustService = vtsOutvehicleFeeAdjustService;
	}

	public void setTruckArriveConfirmDao(
			ITruckArriveConfirmDao truckArriveConfirmDao) {
		this.truckArriveConfirmDao = truckArriveConfirmDao;
	}
	
}
