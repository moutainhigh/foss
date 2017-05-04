package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.dao.ITruckArriveConfirmDao;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsOutvehicleFeeAdjustService;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-05-29 13:49:29
 * FOSS结算配合VTS整车项目：外请车费用调整IService
 */
public class VtsOutvehicleFeeAdjustService implements IVtsOutvehicleFeeAdjustService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(VtsOutvehicleFeeAdjustService.class);
	
	/**
	 * 注入应付单Dao
	 */
	private IBillPayableEntityDao billPayableEntityDao;
	
	/**
	 * 注入对账单Service
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
	 * 注入财务变更消息实体Service
	 */
	private IWaybillChangeMsgService waybillChangeMsgService;
	
	/**
	 * 注入车辆到达确认Dao
	 */
	private ITruckArriveConfirmDao truckArriveConfirmDao;
	
	/**
	 * 结算通用Common Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	//根据单号、单据类型：查询整车尾款应付单TL2
	@Override
	public List<BillPayableEntity> queryTLBillPayableByWaybillNo(
			BillPayableConditionDto conDto) {
		
		if(conDto == null){
			throw new BusinessException("VTS整车项目外请车费用调整同意之后传递参数为空！Service层中VtsOutvehicleFeeAdjustService");
		}else{
			return billPayableEntityDao.queryBillPayableByCondition(conDto);
		}
		
	}

	//核销整车尾款应付单+核销前的校验
	@Override
	public void writeBackBillPayable(BillPayableEntity entity, BillPayableEntity newPayableEntity, CurrentInfo currentInfo) {
		/**
		 *  1.输入参数校验
		 */
		if (entity == null || StringUtils.isEmpty(entity.getWaybillNo())) {
			throw new SettlementException("红冲应付单参数不能为空！");
		}
		LOGGER.info("entering service, waybillNo: "
				+ entity.getWaybillNo());

		/**
		 *  2.已核销,不能红冲
		 */
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销,不能红冲应付单");
		}
		
		/**
		 * 3. 校验对账单
		 */
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			//对账单号不为空时，需要查询对账单记录，
			//看对账单记录是否已经被确认了。
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isEmpty(list)) { 
				// 对账单未确认,则更新对账相关信息
				StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
				dto.setPayableEntityList(Arrays.asList(entity));
				statementOfAccountService.updateStatementAndDetailForRedBack(
						dto, currentInfo);
			} else { 
				// 对账单已确认则不允许红冲单据
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲");
			}
		}
		
		/**
		 * 4.红冲
		 */
		//获取一个时间
		Date now = Calendar.getInstance().getTime();
		BillPayableEntity updateEntity = new BillPayableEntity();
		// 作废旧单据 // ID
		updateEntity.setId(entity.getId()); 
		// 分区键
		updateEntity.setAccountDate(entity.getAccountDate()); 
		// 版本号
		updateEntity.setVersionNo(entity.getVersionNo()); 
		//是否有效-无效
		updateEntity.setActive(FossConstants.INACTIVE);
		//修改时间
		updateEntity.setModifyTime(now);
		// 修改人编码
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
		//修改人名称
		updateEntity.setModifyUserName(currentInfo.getEmpName());
		int i = billPayableEntityDao.updateByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("红冲应付单出错");
		}
		// 生成红冲单
		BillPayableEntity newEntity = new BillPayableEntity();
		//对象之间进行拷贝
		BeanUtils.copyProperties(entity, newEntity);
		//红单的ID
		String redId = UUIDUtils.getUUID();
		//ID
		newEntity.setId(redId);
	    //是否有效
		newEntity.setActive(FossConstants.INACTIVE);
		//版本号
		newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		//是否红单
		newEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		//设置记账日期
		newEntity.setAccountDate(now);
		//创建时间
		newEntity.setCreateTime(now);
		//修改时间
		newEntity.setModifyTime(now);
		//金额-.negate()是和原来金额相反
		newEntity.setAmount(newEntity.getAmount().negate());
		//已核销金额负数
		newEntity.setVerifyAmount(newEntity.getVerifyAmount().negate());
		//未核销金额设置为负数
		newEntity.setUnverifyAmount(newEntity.getUnverifyAmount().negate());
		// 修改人编码 
		newEntity.setModifyUserCode(currentInfo.getEmpCode());
		//修改人名称
		newEntity.setModifyUserName(currentInfo.getEmpName());
		this.add(newEntity);
		
		/**
		 * 5.生成蓝单 
		 */
		Date date = new Date();
		//蓝单ID
		String blueId = UUIDUtils.getUUID();
		newPayableEntity.setId(blueId);
		newPayableEntity.setAccountDate(date);
		newPayableEntity.setCreateDate(date);
		newPayableEntity.setCreateTime(date);
		
		// 应付单号
		newPayableEntity.setPayableNo(this.settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF62));//设置应付单号
		newPayableEntity.setCreateUserCode(currentInfo.getEmpCode());
		
		// FOSS生成的所有单据的初始化状态都为"N"
		newPayableEntity.setIsInit(FossConstants.NO);
		// 如果返回不等于1，则新增异常
	    billPayableEntityDao.add(newPayableEntity);
		
		/**
		 * 6.插入财务变更消息表
		 */
		// 单据的运单号和来源单号相同,红冲应付单时插入财务变更消息表 -1
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__EXCEPTION
						.equals(entity.getSourceBillType()))){

			// 调用插入财务变更消息方法
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getPayableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE, // 红冲代表业务完结-1
					now);
		}
		
		/**
		 * 7.费用调整后生成车辆到达确认，
		 * (1)反到达确认数据:插入UAC数据
		 * (2)整车外请车到达确认凭证表,插入TAC数据
		 */
		//根据整车尾款应付单ID查询到达确认凭证表数据
		 String payableLastID = entity.getId();//获取整车尾款应付单ID
		 TruckArriveConfirmEntity payableLastEntity = new TruckArriveConfirmEntity();
		 payableLastEntity.setPayablelastId(payableLastID);//整车尾款应付单ID
		 payableLastEntity.setConertType("TAC");//确认类型 TAC 到达确认  UAC 反到达确认
		 List<TruckArriveConfirmEntity> entityList = this.queryTruckConfirmByEntity(payableLastEntity);
		 
		 if(CollectionUtils.isNotEmpty(entityList)){//判断空
			 LOGGER.info("整车VTS做过达确认！");
			 TruckArriveConfirmEntity truckArriveConfirmEntity = entityList.get(0);
			 /**
			  * 01.反确认：红单ID
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
			 redTruckEntity.setPayablelastId(truckArriveConfirmEntity.getPayablelastId());//红单的尾款ID 和 原来的ID是一样的
			 truckArriveConfirmDao.insertSelective(redTruckEntity);
			 
			 /**
			  * 02.反确认：蓝单ID
			  */
			 TruckArriveConfirmEntity blueTruckEntity = new TruckArriveConfirmEntity();
			 //VTS做过到达确认之后，先将确认凭证表的数据反确认
			 blueTruckEntity.setId(UUIDUtils.getUUID());//ID
			 blueTruckEntity.setConertType("TAC");//反确认
			 blueTruckEntity.setConvertDate(truckArriveConfirmEntity.getConvertDate());//和原来到达确认时间一样
			 blueTruckEntity.setCreateTime(new Date());//当前时间
			 blueTruckEntity.setCreateUser("VTS");
			 blueTruckEntity.setCreateDept("VTS");
			 blueTruckEntity.setHandleNo(truckArriveConfirmEntity.getHandleNo());
			 blueTruckEntity.setPayablelastId(blueId);
			 truckArriveConfirmDao.insertSelective(blueTruckEntity);
			 
		 }
		LOGGER.info("successfully exit service, sourceBillNo: "
				+ entity.getSourceBillNo());

	}

	/**
	 * 生成新的整车尾款应付单
	 */
	@Override
	public int insertBillPayable(BillPayableEntity newPayableEntity, CurrentInfo currentInfo) {
		Date date = new Date();
		newPayableEntity.setId(UUIDUtils.getUUID());
		newPayableEntity.setAccountDate(date);
		newPayableEntity.setCreateDate(date);
		newPayableEntity.setCreateTime(date);
		
		// 应付单号
		newPayableEntity.setPayableNo(this.settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF62));//设置应付单号
		newPayableEntity.setCreateUserCode(currentInfo.getEmpCode());
		
		// FOSS生成的所有单据的初始化状态都为"N"
		newPayableEntity.setIsInit(FossConstants.NO);
		// 如果返回不等于1，则新增异常
		int i = billPayableEntityDao.add(newPayableEntity);
		if (i != 1) {
			throw new SettlementException("VTS发更改生成应付单出错");
		}
		return i;
	}
	
	/**
	 * 生成应付单
	 * @author 218392 zhangyongxue
	 * @date 2016-05-29 15:24:00
	 */
	@Override
	public void add(BillPayableEntity entity) {
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		// 如果返回不等于1，则新增异常
		int i = billPayableEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("生成应付单出错");
		}
		
	}
	

	/**
	 * 插入财务变更消息
	 * @author 218392 zhangyongxue
	 * @date 2016-05-29 15:24:00
	 */
	@Override
	public void addChangeMsg(String waybillNo, String payableNo,
			String msgAction, Date date) {
		WaybillChangeMsgEntity entity=new WaybillChangeMsgEntity();
		
		//ID
		entity.setId(UUIDUtils.getUUID());
		
		//运单号
		entity.setWaybillNo(waybillNo);
		
		//消息发生日期
		entity.setMsgDate(date);
		
		//消息动作
		entity.setMsgAction(msgAction);
		
		//来源单据类型-应付单
		entity.setSourceBillType(SettlementDictionaryConstants.
				WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE__PAYABLE);
		
		//来源单号-应付单号
		entity.setSourceBillNo(payableNo);
		this.waybillChangeMsgService.addChangeMsg(entity);		
	}
	
	
	/**
	 * 查询整车外请车到达确认凭证表
	 */
	@Override
	public List<TruckArriveConfirmEntity> queryTruckConfirmByEntity(TruckArriveConfirmEntity dto) {
		List<TruckArriveConfirmEntity> dtoList = new ArrayList<TruckArriveConfirmEntity>();
		dtoList = truckArriveConfirmDao.queryTruckConfirmByEntity(dto);
		return dtoList;
	}

	
	/**
	 * 重新到达 整车外请车到达确认凭证表
	 */
	@Override
	public void updateTruckConfirmByEntity(TruckArriveConfirmEntity dto) {
		// TODO Auto-generated method stub
		if(dto == null){
			LOGGER.info("整车外请车到达确认凭证表修改UAC的时候参数为空！");
		}
		truckArriveConfirmDao.updateTruckConfirmByEntity(dto);
		
		
	}
	

	public void setBillPayableEntityDao(IBillPayableEntityDao billPayableEntityDao) {
		this.billPayableEntityDao = billPayableEntityDao;
	}
	
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}
	

	public void setWaybillChangeMsgService(
			IWaybillChangeMsgService waybillChangeMsgService) {
		this.waybillChangeMsgService = waybillChangeMsgService;
	}

	
	public void setTruckArriveConfirmDao(
			ITruckArriveConfirmDao truckArriveConfirmDao) {
		this.truckArriveConfirmDao = truckArriveConfirmDao;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	
}
