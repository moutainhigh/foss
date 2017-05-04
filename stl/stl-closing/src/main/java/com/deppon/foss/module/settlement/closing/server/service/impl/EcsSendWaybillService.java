package com.deppon.foss.module.settlement.closing.server.service.impl;


import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.ecs.EcsFossComplementRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcExceptionType;
import com.deppon.foss.module.settlement.closing.api.server.dao.IWaybillCommonDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsSendWaybillService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.WayBillRequest;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 同步运单信息接口
 * @author ECS-326181
 * @date 2016-5-9 上午9:30:00
 *
 */
public class EcsSendWaybillService implements IEcsSendWaybillService {

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	IWaybillCommonDao waybillCommonDao;
	
	//组织信息 Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	//快递代理公司网点接口
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	private IWaybillTransactionService waybillTransactionService;
	
	private IEcsSendWaybillService ecsSendWaybillService;
	/**
	 * 定义常量值：0 1、不可继承 2、避免魔法数字
	 */
	private static final String ZEROSTR = "0";
	
	/**
	 * 同步运单信息
	 * @author ECS-326181
	 * @param wayBillRequest 同步数据
	 */
	@Transactional
	@Override
	public void doSynchroWaybill (WayBillRequest wayBillRequest, boolean flag) {
		Map<Integer, String> waybillIdMap = null;
		WaybillEntity waybillEntity = wayBillRequest.getWaybillEntity();
		ActualFreightEntity actualFreight = wayBillRequest.getActualFreightEntity();
		WaybillExpressEntity waybillExpress = wayBillRequest.getWaybillExpressEntity();
		WaybillRfcEntity waybillRfc = wayBillRequest.getWaybillRfcEntity();
		//操作类型
		String operationType = wayBillRequest.getOperationType();
		//类型为丢弃，并且子件集合为空后者子件集合第一个值为空
		if ("DISCARD".equals(operationType) &&(CollectionUtils.isEmpty(waybillEntity.getWaybillNos()) || (CollectionUtils.isNotEmpty(waybillEntity.getWaybillNos()) 
				&& waybillEntity.getWaybillNos().get(0) == null))) {
			return;
		}
		// 记录日志
		if (waybillRfc != null) {
			checkWayBillNoIsEmpty(waybillRfc.getWaybillNo(), "运单更改信息");
			saveOrUpdateWaybillRfcEntity(waybillRfc, operationType);
			if (WaybillRfcConstants.PRE_ACCECPT.equals(waybillRfc.getStatus()) 
					|| WaybillRfcConstants.ACCECPT_DENY.equals(waybillRfc.getStatus())) {
				return;
			}
		}
		if (waybillEntity != null) {
			checkWayBillNoIsEmpty(waybillEntity.getWaybillNo(), "运单信息");
			waybillIdMap = saveOrUpdateWaybillEntity(waybillEntity, flag, operationType);
		}
		if (actualFreight != null) {
			checkWayBillNoIsEmpty(actualFreight.getWaybillNo(), "实际承运信息");
			saveOrUpdateActualFreightEntity(actualFreight, waybillEntity, operationType);
		}
		if (waybillExpress != null) {
			checkWayBillNoIsEmpty(waybillExpress.getWaybillNo(), "其他快递信息");
			saveOrUpdateWaybillExpressEntity(waybillExpress, waybillEntity, waybillIdMap, operationType);
		}
	}
	
	private void checkWayBillNoIsEmpty (String waybillNo, String name){
		if (waybillNo == null || "".equals(waybillNo)) {
			throw new BusinessException(MessageFormat.format("{0}的运单号不能为空！", name));
		}
	}
	
	/**
	 * 操作运单信息
	 * @param waybillEntity
	 * @param isAdd 
	 * 			false 补码
	 * 			true 正常同步运单 
	 * @param operationType 操作类型
	 */
	public Map<Integer, String> saveOrUpdateWaybillEntity(WaybillEntity waybillEntity, boolean isAdd, String operationType) {
		//是否有效
		boolean isActive=true;
		//正常同步运单 才取比较赋值
		WaybillEntity oldWaybill = waybillCommonDao.findWaybillByWaybillNo(waybillEntity.getWaybillNo());
		checkIsCanComplement(isAdd, oldWaybill);
		//是否修改运单
		boolean flag = oldWaybill != null;
		//是否新增运单
		boolean ifInsert = true;
		if (isAdd) {
			if (flag) {
				ifInsert = !oldWaybill.getId().equals(waybillEntity.getId());
				try {
					doDestInvokeValue(waybillEntity, oldWaybill);
				} catch (Exception e) {//出现异常不影响下边逻辑运行。
					logger.error("赋值错误："+e.getMessage());
					e.printStackTrace();
				}
			}else{
				//当第一次开单时,插入业务数据,记录该单开过单
				WaybillTransactionEntity entity = new WaybillTransactionEntity();
				entity.setWaybillNo(waybillEntity.getWaybillNo());
				entity.setBusinessOver(ZEROSTR);
				entity.setFinanceOver(ZEROSTR);
				entity.setCreateDate(new Date());
				waybillTransactionService.addWaybillTransaction(entity);
			}
		}
		isActive =  ifInsert ? isAdd : false;
		//修修改是并且未找到值
		if ("UPDATE".equals(operationType) && oldWaybill != null) {
			//修改运单 通过运单号码更新运单记
			waybillCommonDao.updateWaybillEntityByWaybillNo(waybillEntity, waybillEntity.getWaybillNo(), isActive);
			boolean isInsert  = doAddWaybillEntity(waybillEntity, isAdd, isActive);
			return saveWaybillEntityByWayBillNos(waybillEntity, isInsert, isActive);
		} else if (operationType == null) {
			//老运单ID与新运单id相等则只做update , 正常同步运单以及有效才新增
				//设置返回成功 1
				doAddWaybillEntity(waybillEntity, isAdd, isActive);
		} else if ("DISCARD".equals(operationType)) {
			return saveWaybillEntityByWayBillNos(waybillEntity, true, isActive);
		}
		return null;
	}
	
	private boolean doAddWaybillEntity(WaybillEntity waybillEntity, boolean isAdd, boolean ifInsert){
		boolean flag = false;
		if (FossConstants.YES.equals(waybillEntity.getActive()) && isAdd && ifInsert) {
			waybillEntity.setProductId(waybillEntity.getProductCode());
			waybillEntity.setIsWholeVehicle(FossConstants.NO);//是否整车
			waybillEntity.setFreePickupGoods(FossConstants.YES);//是否免费接货
			waybillEntity.setForbiddenLine(FossConstants.YES);//是否禁行
			waybillEntity.setSpecialShapedGoods(FossConstants.NO);//是否异形
			waybillEntity.setPickupToDoor(FossConstants.YES);//是否上门接货
			waybillEntity.setCarDirectDelivery(FossConstants.NO);//是否大车
			waybillEntity.setCreateTime(new Date());
			waybillEntity.setModifyTime(waybillEntity.getCreateTime());
			waybillCommonDao.insertWaybillEntity(waybillEntity);
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 校验是否可以补码
	 * @param isAdd false 补码
	 * @param oldWaybill 运单对象
	 */
	private void checkIsCanComplement(boolean isAdd, WaybillEntity oldWaybill){
		//为补码时，oldWaybill为空说明开单为同步，无法做修改操作。
		if (!isAdd && oldWaybill == null) {
			throw new SettlementException("无法在开单前做补码同步运单操作！");
		}
	}
	
	/**
	 * 保存运单信息表子母件
	 * @param waybillEntity
	 * @param flag 
	 *        true 新增
	 *        false 修改
	 */
	public Map<Integer, String> saveWaybillEntityByWayBillNos(WaybillEntity waybillEntity, boolean flag, boolean isActive) {
		Map<Integer, String> waybillIdMap = null;
		int i = 0;
		if (CollectionUtils.isNotEmpty(waybillEntity.getWaybillNos()) 
				&& waybillEntity.getWaybillNos().get(0) != null && !"".equals(waybillEntity.getWaybillNos().get(0))) {
			logger.info("****************size*************"+waybillEntity.getWaybillNos());
			waybillIdMap = new HashMap<Integer, String>();
			waybillEntity.setProductId(waybillEntity.getProductCode());
			waybillEntity.setIsWholeVehicle(FossConstants.NO);//是否整车
			waybillEntity.setFreePickupGoods(FossConstants.YES);//是否免费接货
			waybillEntity.setForbiddenLine(FossConstants.YES);//是否禁行
			waybillEntity.setSpecialShapedGoods(FossConstants.NO);//是否异形
			waybillEntity.setPickupToDoor(FossConstants.YES);//是否上门接货
			waybillEntity.setCarDirectDelivery(FossConstants.NO);//是否大车
			waybillEntity.setCreateTime(new Date());
			waybillEntity.setModifyTime(waybillEntity.getCreateTime());
			waybillEntity.setTransportFee(BigDecimal.ZERO);
			waybillEntity.setPrePayAmount(BigDecimal.ZERO);
			waybillEntity.setToPayAmount(BigDecimal.ZERO);
			waybillEntity.setCodAmount(BigDecimal.ZERO);
			waybillEntity.setDeliveryGoodsFee(BigDecimal.ZERO);
			waybillEntity.setPickupFee(BigDecimal.ZERO);
			waybillEntity.setInsuranceFee(BigDecimal.ZERO);
			waybillEntity.setOtherFee(BigDecimal.ZERO);
			waybillEntity.setInsuranceAmount(BigDecimal.ZERO);
			waybillEntity.setTotalFee(BigDecimal.ZERO);
			waybillEntity.setPackageFee(BigDecimal.ZERO);
			waybillEntity.setValueAddFee(BigDecimal.ZERO);
			waybillEntity.setPromotionsFee(BigDecimal.ZERO);
			waybillEntity.setServiceFee(BigDecimal.ZERO);
			waybillEntity.setWholeVehicleAppfee(BigDecimal.ZERO);
			List<WaybillEntity> result = doCreateEntity(waybillEntity, waybillEntity.getWaybillNos());
			for (WaybillEntity billEntity : result) {
				waybillCommonDao.updateWaybillEntityByWaybillNo(billEntity, billEntity.getWaybillNo(), isActive);
				if (flag) {
					billEntity.setId(UUIDUtils.getUUID());
					waybillCommonDao.insertWaybillEntity(billEntity);
					waybillIdMap.put(i,billEntity.getId());
					i++;
				}
			}
		}
		return waybillIdMap;
	}
	
	/**
	 * 操作实际承运信息
	 * @param waybillEntity
	 */
	public void saveOrUpdateActualFreightEntity(ActualFreightEntity actualFreight, WaybillEntity waybillEntity, String operationType) {
		Boolean flag = null;
		//根据id去获取是否已存在当前运单
		List<String> result = waybillCommonDao.findActualFreightByWaybillNo(actualFreight.getWaybillNo());
		int size = result.size();
		if (size > 1) return;
		//TODO 必填项由悟空传
		if (!FossConstants.YES.equals(actualFreight.getStartCentralizedSettlement())) {
			//始发客户合同部门编码
			actualFreight.setStartContractOrgCode(null);
			//始发客户合同上催款部门编码
			actualFreight.setStartReminderOrgCode(null);
		}
		if (!FossConstants.YES.equals(actualFreight.getArriveCentralizedSettlement())) {
			//到达客户合同部门编码
			actualFreight.setArriveContractOrgCode(null);
			//到达客户合同上催款部门编码
			actualFreight.setArriveReminderOrgCode(null);
		}
		//存在修改，否则新增
		 if (operationType == null) {
			actualFreight.setCreateTime(null);
			waybillCommonDao.insertActualFreightEntity(actualFreight);
		} else if ("UPDATE".equals(operationType) && size == 1) {
			flag = false;
			actualFreight.setId(result.get(0));
			actualFreight.setModifyTime(new Date());
			waybillCommonDao.updateActualFreightEntityById(actualFreight);
		} else if ("DISCARD".equals(operationType)){
			flag = true;
			actualFreight.setCreateTime(null);
		} 
		if (flag != null) {
			saveActualFreightByWayBillNos(actualFreight, waybillEntity, flag);
		}
	}
	
	/**
	 * 保存实际承运信息表子母件
	 * @param waybillEntity
	 * @param flag 
	 *        true 新增
	 *        false 修改
	 */
	public void saveActualFreightByWayBillNos(ActualFreightEntity actualFreightEntity, WaybillEntity waybillEntity, boolean flag) {
		if (CollectionUtils.isNotEmpty(waybillEntity.getWaybillNos())
				&& waybillEntity.getWaybillNos().get(0) != null && !"".equals(waybillEntity.getWaybillNos().get(0))) {
			List<ActualFreightEntity> result = doCreateEntity(actualFreightEntity, waybillEntity.getWaybillNos());
			for (ActualFreightEntity actualFreight : result) {
				actualFreight.setInsuranceValue(BigDecimal.ZERO);
				actualFreight.setPackingFee(BigDecimal.ZERO);
				actualFreight.setDeliverFee(BigDecimal.ZERO);
				actualFreight.setLaborFee(BigDecimal.ZERO);
				actualFreight.setCodAmount(BigDecimal.ZERO);
				actualFreight.setValueaddFee(BigDecimal.ZERO);
				actualFreight.setFreight(BigDecimal.ZERO);
				if (flag) {
					waybillCommonDao.insertActualFreightEntity(actualFreight);
				} else {
					waybillCommonDao.updateActualFreightEntityByWaybillNo(actualFreight);
				}
			}
		}
	}
	
	/**
	 * 根据子母件集合生成对应运单
	 * @param obj
	 * @param waybillNos
	 * @return
	 */
	public <T> List<T> doCreateEntity (T obj, List<String> waybillNos) {
		Class<T> cls = (Class<T>) obj.getClass();
		String[] waybillNoArrays = waybillNos.get(0).split(",");
		List<T> list = new ArrayList<T>(waybillNoArrays.length);
		T dest;
		try {
			Constructor<T> ctr = cls.getConstructor(null);
			for (int i=0,j = waybillNoArrays.length; i < j; i++) {
				logger.info("****************子母件waybillNo*************"+waybillNoArrays[i]);
				dest = ctr.newInstance(null);
				PropertyUtils.copyProperties(dest, obj);
				Method setter = cls.getMethod("setWaybillNo", String.class);
				setter.invoke(dest, waybillNoArrays[i]);
				list.add(dest);
			}
		} catch (Exception e) {
			System.out.println("赋值失败");
		} 
		return list;
	}
	
	/**
	 * 操作快递信息
	 * @param waybillEntity
	 */
	public void saveOrUpdateWaybillExpressEntity(WaybillExpressEntity waybillExpress, WaybillEntity waybillEntity, Map<Integer, String> waybillIdMap, String operationType) {
		Boolean flag = false;
		//根据id去获取是否已存在当前运单
		List<String> result = waybillCommonDao.findWaybillExpressByWaybillNo(waybillEntity.getWaybillNo());
		int size = result.size();
		if (size > 1) return;
		waybillExpress.setWaybillNo(waybillEntity.getWaybillNo());
		waybillExpress.setBillTime(waybillEntity.getBillTime());
		waybillExpress.setCreateOrgCode(waybillEntity.getCreateOrgCode());
		waybillExpress.setModifyDate(new Date());
		//存在修改，否则新增
		if (operationType == null) {
			waybillExpress.setWaybillId(waybillEntity.getId());
			waybillExpress.setCreateTime(new Date());
			waybillCommonDao.insertWaybillExpressEntity(waybillExpress);
		} else if ("UPDATE".equals(operationType) && size == 1) {
			flag = false;
			waybillCommonDao.updateWaybillExpressByWaybillNo(waybillExpress);
		} else if ("DISCARD".equals(operationType)) {
			flag = true;
			waybillExpress.setWaybillId(waybillEntity.getId());
			waybillExpress.setCreateTime(new Date());
		}
		if (flag != null) {
			saveWaybillExpressByWayBillNos(waybillExpress, waybillEntity, flag, waybillIdMap);
		}
	}
	
	/**
	 * 保存运单快递冗余表子母件
	 * @param waybillExpress
	 * @param waybillEntity
	 * @param flag 
	 *        true 新增
	 *        false 修改
	 * @param waybillIdMap
	 */
	public void saveWaybillExpressByWayBillNos(WaybillExpressEntity waybillExpress, WaybillEntity waybillEntity, 
			boolean flag, Map<Integer, String> waybillIdMap) {
		if (CollectionUtils.isNotEmpty(waybillEntity.getWaybillNos())
				&& waybillEntity.getWaybillNos().get(0) != null && !"".equals(waybillEntity.getWaybillNos().get(0))) {
			List<WaybillExpressEntity> result = doCreateEntity(waybillExpress, waybillEntity.getWaybillNos());
			for (int i=0,j = result.size(); i < j; i++) {
				if (flag) {
					result.get(i).setWaybillId(waybillIdMap.get(i));
					waybillCommonDao.insertWaybillExpressEntity(result.get(i));
				} else {
					waybillCommonDao.updateWaybillExpressByWaybillNo(result.get(i));
				}
			}
		}
	}
	
	/**
	 * 操作运单更改信息
	 * @param waybillEntity
	 */
	public void saveOrUpdateWaybillRfcEntity(WaybillRfcEntity waybillRfc, String operationType) {
		//根据id去获取是否已存在当前运单
		waybillRfc.setNeedWriteOff(waybillRfc.getIsFinanceChange());
		waybillRfc.setWriteOffStatus(WaybillRfcConstants.NO_WRITE_OFF);
		//快递变更类型为EXPBILLCHANGE_TYPE_UPDATE ||EXPBILLCHANGE_TYPE_REMOVE ，需转成结算这边的类型
		if ("EXPBILLCHANGE_TYPE_UPDATE".equals(waybillRfc.getRfcType())) {//变更
			waybillRfc.setRfcType(WaybillRfcConstants.CUSTOMER_CHANGE);
		} else if ("EXPBILLCHANGE_TYPE_REMOVE".equals(waybillRfc.getRfcType())) {//作废
			waybillRfc.setRfcType(WaybillRfcConstants.INVALID);
		}
		//变更来源--详细信息:CUSTOMER_REQUIRE客户要求,INSIDE_REQUIRE内部要求
		if ("1".equals(waybillRfc.getRfcSource())) {//变更
			waybillRfc.setRfcSource(WaybillRfcConstants.CUSTOMER_REQUIRE);
		} else if ("2".equals(waybillRfc.getRfcSource())) {//作废
			waybillRfc.setRfcSource(WaybillRfcConstants.INSIDE_REQUIRE);
		}
		//存在修改，否则新增
		waybillRfc.setCreateDate(new Date());
		//根据运单号，起草时间获取此运单号上一条数据
		WaybillRfcEntity oldWaybillRfc = waybillCommonDao.findWaybillRfcByWaybillNo(waybillRfc.getWaybillNo());
		if (oldWaybillRfc != null) {
			try {
				doDestInvokeValue(waybillRfc, oldWaybillRfc);
			} catch (Exception e) {//出现异常不影响下边逻辑运行。
				logger.error("赋值错误："+e.getMessage());
				e.printStackTrace();
			}
		}
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(waybillRfc.getWaybillNo());
		waybillNos = this.isExsitsWayBillRfcs(waybillNos);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(waybillNos)) {
			//做修改
			waybillCommonDao.updateWaybillRfcEntityById(waybillRfc);
		} else {
			waybillCommonDao.insertWaybillRfcEntity(waybillRfc);
		}
	}

	/**
	 * ECS补码更新运信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-07-20 15:20
	 */
	@Override
	public void updateWaybillByComplement(EcsFossComplementRequest req) {
		WayBillRequest request = new WayBillRequest();
		
		WaybillEntity waybillEntity = new WaybillEntity();
		waybillEntity.setWaybillNo(req.getWaybillNo());
		//当补码到自有网点时
		if(FossConstants.YES.equals(req.getIsFreeSite())){
			//查询到达部门
			OrgAdministrativeInfoEntity orgAdmin=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(req.getPkpOrgCode());
			if(orgAdmin == null){
				throw new SettlementException("悟空补码传入的到达部门在FOSS不存在!");
			}
			//提货网点
			waybillEntity.setCustomerPickupOrgCode(orgAdmin.getCode());
			//提货网点名称
			waybillEntity.setCustomerPickupOrgName(orgAdmin.getName());
			//目的站
			waybillEntity.setTargetOrgCode(orgAdmin.getOrgSimpleName());
		}else{
			//为非自有网点时
			OuterBranchExpressEntity bpe = ldpAgencyDeptService.queryLdpAgencyDeptByCode(req.getPkpOrgCode(), FossConstants.YES);
			if(bpe == null){
				throw new SettlementException("悟空补码传入的代理网点在FOSS不存在!");
			}
			//提货网点
			waybillEntity.setCustomerPickupOrgCode(bpe.getAgentDeptCode());
			//提货网点名称
			waybillEntity.setCustomerPickupOrgName(bpe.getAgentDeptName());
			//目的站
			waybillEntity.setTargetOrgCode(bpe.getSimplename());
		}
		//最终配载部门
		waybillEntity.setLastLoadOrgCode(req.getDestOrgCode());
		//最终配载部门名称
		waybillEntity.setLastLoadOrgName(req.getDestOrgName());
		
		WaybillExpressEntity waybillExpress = new WaybillExpressEntity();
		//运单号
		waybillExpress.setWaybillNo(req.getWaybillNo());
		//是补码
		waybillExpress.setIsAddCode(FossConstants.YES);
		
		request.setWaybillEntity(waybillEntity);
		request.setOperationType("UPDATE");
		request.setWaybillExpressEntity(waybillExpress);
		//更新运单信息
		ecsSendWaybillService.doSynchroWaybill(request, false);
	}
	
	/**
	 * 为避免开单同步运单新增时必填项有值，更改同步运单新增必填项又出现null的情况。
	 * 	 	假如更改时有属性为空，老对象属性有值，则将值赋给新对象
	 * 
	 * @author 326181
	 * @param dest 新对象
	 * @param src  老对象
	 * @throws Exception
	 */
	private void doDestInvokeValue(Object dest, Object src) throws Exception {
		Class<Object> clsDest = (Class<Object>) dest.getClass();
		Class<Object> clsSrc = (Class<Object>) src.getClass();
		//通过内省获取属性描述器
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clsDest).getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			//获取dest的属性值
			Object destVal = propertyDescriptors[i].getReadMethod().invoke(dest, null);
			//destVal有值则跳过
			if (destVal != null && !"".equals(destVal)) {
				continue;
			}
			//获取当前getter的名称
			String getterName = propertyDescriptors[i].getReadMethod().getName();
			//获取getter的返回值
			Object value = clsSrc.getMethod(getterName).invoke(src);
			Method setter = propertyDescriptors[i].getWriteMethod();
			// value不为空，则覆盖原目标对象对应值
			if (value != null && !"".equals(value) && setter != null) {
				setter.invoke(dest, value);
			}
		}
	}
	
	/**
	 * 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 * 
	 * @param waybillNo
	 * @return
	 */
	private List<String> isExsitsWayBillRfcs(List<String> waybillNoList) throws WaybillRfcException {
		// 如果单号列表大于1000个，返回查询量太大异常
		if (waybillNoList != null && waybillNoList.size() > 1000) {
			throw new WaybillRfcException(WaybillRfcExceptionType.QUERY_NUMBER_TOO_GARGE_ERROR_CODE);
		}

		// 如果运单列表大小为空，返回查询运单号为空异常
		if (waybillNoList == null || waybillNoList.size() == 0) {
			throw new WaybillRfcException(WaybillRfcExceptionType.QUERY_NUMBER_NULL_ERROR_CODE);

		}

		WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto = new WaybillFRcQueryByWaybillNosDto();//创建对象
		waybillFRcQueryByWaybillNosDto.setWaybillNos(waybillNoList);
		waybillFRcQueryByWaybillNosDto.setPreAccecpt(WaybillRfcConstants.PRE_ACCECPT);
		waybillFRcQueryByWaybillNosDto.setPreAudit(WaybillRfcConstants.PRE_AUDIT);

		return waybillCommonDao.queryWaybillRfcByWaybillNos(waybillFRcQueryByWaybillNosDto);
	}
	
	public void setWaybillCommonDao(IWaybillCommonDao waybillCommonDao) {
		this.waybillCommonDao = waybillCommonDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setWaybillTransactionService(
			IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}

	public void setEcsSendWaybillService(
			IEcsSendWaybillService ecsSendWaybillService) {
		this.ecsSendWaybillService = ecsSendWaybillService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

}
