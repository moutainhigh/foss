package com.deppon.foss.module.settlement.common.server.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsEffectPayableDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsCodAuditLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsCodAuditService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsEffectPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsTakingService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.CodAuditForVtsSignEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VtsCodAuditLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditForVtsSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsWaybillFRcQueryByWaybillNoDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


public class VtsEffectPayableService  implements IVtsEffectPayableService{
	/**
	 *VTSs生效装卸费跟代收货款服务
	 *@author 310970
	 *@Date 2016-6-13
	 *@param  waybillSignResultEntity entity
	 */
	/** 返回值. */
	private static final int RESULT_VALUE = 1;

	private static final String VALIDATE_PARAM_MES_NOT = "反";
	/**
	 * 注入日志 
	 */
	private static final Logger LOGGER = LogManager.getLogger(VtsEffectPayableService.class);
	/**
	 * 注入Dao 
	 */
	private IVtsEffectPayableDao vtsEffectPayableDao;
	/** 应付单公共Service. */
	private IBillPayableService billPayableService;
	
	private IVtsTakingService vtsTakingService;
	/** 查询配置参数表. */
	private IConfigurationParamsService configurationParamsService;
	/** 还款单Service. */
	private IBillRepaymentService billRepaymentService;
	/**在线通知*/
    //private IMessageService messageService;
    /** 查询配载单记录的 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	/** VTS代收货款审核service */
	private IVtsCodAuditService vtsCodAuditService;
	/** VTS代收货款审核日志 */
	private IVtsCodAuditLogService vtsCodAuditLogService;
	
	@Override
	public void EffectPayableByVtsInfo(WaybillSignResultEntity entity,
			CurrentInfo currentInfo) {
		//判空
		if(entity==null){
			throw new SettlementException("接口参数不能为空！");
		}
		//对参数进行校验
		this.validateParams(entity,true);
		//构造签收的DTO
		LineSignDto dto = new LineSignDto();
		//设置单号属性
		dto.setWaybillNo(entity.getWaybillNo());
		//设置签收的时间
		dto.setSignDate(entity.getSignTime());
		//设置签收
		dto.setSignType(SettlementConstants.LINE_SIGN);
		// 应收单、现金收款单确认收入
    	vtsTakingService.confirmIncome(dto, currentInfo);
		// 生效应付单
		this.effectBillPayable(dto,currentInfo);
	}

	private int effectBillPayable(LineSignDto dto,
			CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}

		LOGGER.info(" 确认签收-开始生效应付单，运单号：" + dto.getWaybillNo());

		// 查询是否存在代收货款应付单,装卸费应付单
		// 更新装卸费应付单的签收日期，便于生效装卸费应付单时使用
		BillPayableConditionDto payableConditionDto = new BillPayableConditionDto();
		payableConditionDto.setWaybillNo(dto.getWaybillNo());
		payableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE});
		List<BillPayableEntity> billPayables = billPayableService
				.queryBillPayableByCondition(payableConditionDto);
		if (CollectionUtils.isEmpty(billPayables)) {
			return RESULT_VALUE;
		}

		// 设置存在结算单据标记
		dto.setStlBillCounts(1);

		// 验证代收货款应付单
		List<BillPayableEntity> lists = checkBillPayableEntity(dto, billPayables);
		BillPayableEntity codBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		// 签收时，更新装卸费应付单的签收日期
		BillPayableEntity sfBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		
		if (codBillPayable != null && StringUtils.isNotEmpty(codBillPayable.getId())) {

			// 已经应收单确认收入时，查询出了代收货款应收单了
			BillReceivableEntity billReceivableEntity = dto.getCodReceivableEntity();
			if (billReceivableEntity == null || StringUtils.isEmpty(billReceivableEntity.getId())) {
				throw new SettlementException("不存在代收货款应收单");
			}
			// 专线和空运有代收货款业务，偏线没有代收货款业务（空运审核空运代收货款后，生效代收货款应付单）
			// 整车只有专线
			if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {

				// 当未核销金额大于0时，提示异常信息
				if (billReceivableEntity.getUnverifyAmount() != null
						&& billReceivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					throw new SettlementException("代收货款应收单存在未核销金额，不能进行签收操作");
				}
								
				this.enableBillPayableByPdaSign(billReceivableEntity, codBillPayable, dto,
						currentInfo);
			} 
		}
		// 生效装卸费是定时任务，在签收接口中只更新装卸费应付单的签收日期
		if (sfBillPayable != null && StringUtils.isNotEmpty(sfBillPayable.getId())) {
			sfBillPayable.setSignDate(dto.getSignDate());
			this.billPayableService.updateBillPayableSignDateByConfirmIncome(sfBillPayable,
					currentInfo);
		}
		LOGGER.info("vts确认签收-生效应付单成功！");

		return RESULT_VALUE;
	}
	/**
	 * 代收货款应收单的应收金额小于等于综合管理配置参数中设定的代收货款金额， 生效应付单
	 * @param billReceivableEntity
	 *            the bill receivable entity
	 * @param codBillPayable
	 *            the cod bill payable
	 * @param dto
	 *            the dto
	 * @param currentInfo
	 *            the current info
	 * @author 310970 caopeng
	 * @date 2016-6-14
	 */
	
	private void enableBillPayableByPdaSign(BillReceivableEntity billReceivableEntity,
			BillPayableEntity codBillPayable, LineSignDto dto,CurrentInfo currentInfo) {	
		
		// 签收时，代收货款应收单的应收金额小于等于综合管理配置参数中设定的代收货款金额， 生效应付单
		BigDecimal maxAmount = null;
		Date cashConfirmTime = null;//收银确认时间 
		String paymentType = null;
		String confValue = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.STL_COD_PDA_CONFIRM_AMOUNT);// 调用综合管理接口进行查询
		if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {
			maxAmount = NumberUtils.createBigDecimal(confValue);
		}

		// 代收货款应收单的应收金额小于等于maxAmount，生效应付单
		if (billReceivableEntity.getAmount() != null
				&& billReceivableEntity.getAmount().compareTo(maxAmount) < 1) {

			// 生效代收货款应付单
			this.billPayableService.enableBillPayable(codBillPayable, dto.getSignDate(),
					currentInfo);
		} else {

			/*
			 * 2013-01-21日防止签收前（代收货款还款单已经收银确认）变更， 根据运单号查询出来的实收货款还款单（来源单据类型：代收货款）
			 * 如果有数据且收银确认状态：已确认，直接生效代收货款应付单
			 */
			BillRepaymentConditionDto repaymentConDto = new BillRepaymentConditionDto();
			repaymentConDto.setWaybillNo(dto.getWaybillNo());
			repaymentConDto.setActive(FossConstants.YES);

			// 来源单据类型：代收货款
			repaymentConDto
					.setSourceBillTypes(new String[] { SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD });
			List<BillRepaymentEntity> repayList = this.billRepaymentService
					.queryBillRepaymentByCondition(repaymentConDto);

			// 代表对应的实收代收货款还款单是否已经收银确认
			boolean bl = false;

			// 还款单集合不为空
			if (CollectionUtils.isNotEmpty(repayList)) {

				// 对查询到得还款单repayEntity进行排序，按照记账日期accountDate从前往后进行排序
				ListComparator orderComparator = new ListComparator(
						SettlementConstants.ACCOUNT_DATE);
				Collections.sort(repayList, orderComparator);

				// 获取最后一条还款单记录，判断是否已经收银确认
				BillRepaymentEntity repayEntity = repayList.get((repayList.size() - 1));

				// 还款单状态为已经收银确认
				if (repayEntity != null
						&& SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__CONFIRM
								.equals(repayEntity.getStatus())) {
					bl = true;
					cashConfirmTime = repayEntity.getCashConfirmTime();
					paymentType = repayEntity.getPaymentType();
				}
				//ISSUE-3250 如果还款单未收银确认PDA派送签收的运单，金额超过X元时（目前系统配置的X=0），需要提醒到达部门做收银确认
				else{
									
					InstationJobMsgEntity entiy = new InstationJobMsgEntity();
					//发送人和发送部门信息
					entiy.setSenderCode(currentInfo.getEmpCode());
					entiy.setSenderName(currentInfo.getEmpName());
					entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
					entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
					//设置为代收货款消息
					entiy.setMsgType(MessageConstants.MSG_TYPE__COLLECTION);						
					//接受方式为组织
					entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					//设置接收部门信息
					entiy.setReceiveOrgCode(repayEntity.getCollectionOrgCode());
					entiy.setReceiveOrgName(repayEntity.getCollectionOrgName());
					//设置
					entiy.setContext("你部门签收的还款单："+codBillPayable.getWaybillNo()+"含有代收货款应收已经签收，请及时做收银确认操作");
				//	messageService.createBatchInstationMsg(entiy);
					
				}
			}
			if (bl) {
				// bl为true最后一条代收货款还款单记录已经收银确认，生效代收货款应付单
				this.billPayableService.enableBillPayable(codBillPayable, dto.getSignDate(),
						currentInfo);
				/**
				 * @author 218392 张永雪
				 * @date 2015-12-15 09:45:27
				 * 针对有效的代收数据，审批筛选条件如下（如下条件满足任意一个则为需审核数据）： 
				 *	1、	单笔代收退款金额≥80000元
				 *	2、	单笔代收货款金额更改额绝对值≥50000元的
				 *	3、	出发部门=到达部门
				 *	4、	签收时间-开单时间≤12小时
				 *	5、	23：00-次日8：00之间签收的代收货款数据
				 *	6、	存在代收款的运单，货物轨迹中无配载单记录的。
				 *
				 *筛选的数据必须同时满足以下条件：
				 *	1、	此次改动，不涉及快递代理代收流程、空运代收流程。只针对专线单据。
				 *	2、	单据类型必须为有代收货款的单据
				 *	3、	单据状态必须为已经正常签收
				 *	4、	单据状态必须为已经收银确认
				 *		备注：单据为满足以上条件的零担、快递单据
				 *
				 * @author 310970 曹朋
				 * @date 2016-3-21 10:06:28
				 * 针对有效的代收数据，新增三个审核筛选条件（条件如下。若需筛选的数据满足三个筛选条件任意一个，则直接流至资金复核小组进行审核。）
				 * 1.单笔代收即日退≥30000，单笔代收三日退（审核退）≥50000
				 * 2.付款方式为“电汇”的所有单据
				 * 3.代收货款更改受理时间与签收时间之差小于1小时的
				 * 筛选的数据必须同时满足以下条件：
				 *	1、	此次改动，不涉及快递代理代收流程、空运代收流程。只针对专线单据。
				 *	2、	单据类型必须为有代收货款的单据
				 *	3、	单据状态必须为已经正常签收
				 *	4、	单据状态必须为已经收银确认
				 *	5、  单据	必须经过上述已有的6个筛选条件之后，再进行新增的3个条件的 筛选
				 */
				
				/**  1、单笔代收退款金额≥80000元   
				 * */
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				BigDecimal codeAmount= codBillPayable.getAmount();//单笔代收退款金额
				BigDecimal amount1 = new BigDecimal(SettlementReportNumber.EIGHTY_THOUSAND);//定义80000元
				int result = codeAmount.compareTo(amount1);//1大于，-1小于，0是等于
				LOGGER.info("单笔代收货款退款金额为：" + codeAmount + "...和80000比较的结果是：" + result);
				
				/**  2、单笔代收货款金额更改额绝对值≥50000元的  
				 * */
				String waybillNo = codBillPayable.getWaybillNo();//运单号
				if(!StringUtils.isNotEmpty(waybillNo)){
					throw new SettlementException("运单号为空！");
				}
				WaybillEntity entity= vtsEffectPayableDao.queryFirstWaybillByWaybillNo(waybillNo);//通过运单编号查询第一次开单时运单信息
				BigDecimal waybillCodAmount = entity.getCodAmount();//第一次开单时候的代收金额
				LOGGER.info("通过运单编号查询第一次开单时的金额为：" + waybillCodAmount);
				BigDecimal amountCompare = codeAmount.subtract(waybillCodAmount);//差值
				BigDecimal amount2 = new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND);//定义80000元
				//代收更改金额
				BigDecimal absAmount = amountCompare.abs();//绝对值
				int num = absAmount.compareTo(amount2);//1大于，-1小于，0是等于
				LOGGER.info("单笔代收货款金额更改额绝对值为：" + absAmount + "...和50000比较的结果是：" + num);
				
				/**  3、出发部门=到达部门
				 *   */
				String  origOrgcode = codBillPayable.getOrigOrgCode();//出发部门(取自应付单)
				String destOrgcode 	= codBillPayable.getDestOrgCode();//到达部门(取自应付单)
				LOGGER.info("出发部门编码为：" + origOrgcode +",到达部门编码为：" + destOrgcode);
				Boolean isEquals = false;
				if(StringUtils.isNotEmpty(origOrgcode) || StringUtils.isNotEmpty(destOrgcode)){
					isEquals = origOrgcode.equals(destOrgcode);
				}else{
					throw new SettlementException("出发部门或到达部门为空！");
				}
				
				/**  4、签收时间-开单时间≤12小时  
				 * */
				Date signTime = dto.getSignDate();//签收时间
				Date billTime = entity.getBillTime();//开单时间
				LOGGER.info("签收的时间是：" + dateFormat.format(signTime));
				LOGGER.info("开单的时间是：" + dateFormat.format(billTime));
				double subTime = Math.abs(signTime.getTime() - billTime.getTime());
				LOGGER.info("(收银确认操作)开单的时间差毫秒数为：" + subTime);
				Boolean isOverTime = false;
				if(subTime < (SettlementReportNumber.TWELVE*SettlementReportNumber.NUMBER_3600000)){//签收时间是否小于12个小时
					isOverTime = true;
				}
				//签收开单时长单位小时，用于插入代收审核表中
				int signBillDiffer = (int) (subTime/3600000.0) ;
				
				/**  
				 * 5、线上BUG修复 2016-01-11 13:41:50 放到上线版本为0114
				 * 签收时间在签收当天8:00之前, 23：00之后的代收货款数据
				 */
				Calendar cal = Calendar.getInstance();
				cal.setTime(signTime);
				cal.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.TWENTY_THREE);
				cal.set(Calendar.MINUTE, SettlementReportNumber.ZERO);
				cal.set(Calendar.SECOND, SettlementReportNumber.ZERO);
				cal.set(Calendar.MILLISECOND, 000);
				Date date23 = cal.getTime();//获取当天23:00
				LOGGER.info("(收银确认操作)当天23点00分000毫秒的时间是：" + dateFormat.format(date23));
				
				Calendar cal8 = Calendar.getInstance();
				cal8.setTime(signTime);
				cal8.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.EIGHT);
				cal8.set(Calendar.MINUTE, SettlementReportNumber.ZERO);
				cal8.set(Calendar.SECOND, SettlementReportNumber.ZERO);
				cal8.set(Calendar.MILLISECOND, 000);
				Date date8 = cal8.getTime();//获取次日08:00:00:000
				LOGGER.info("(收银确认操作)当天8点00分00秒000毫秒的时间是：" + dateFormat.format(date8));
				LOGGER.info("签收时间为：" + dateFormat.format(signTime));
				
				long signTimeLon = signTime.getTime();
				long date23Lon 	 = date23.getTime();
				long date8Lon	 = date8.getTime();
				
				Boolean timeIsBetween = false;
				if(signTimeLon >= date23Lon || signTimeLon <= date8Lon){
					timeIsBetween = true;
				}
				
				/**  6、存在代收款的运单，货物轨迹中无交接单记录的  
				 * */
				String isY = "";
				Boolean isExist = false;
				isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
				if(StringUtils.equals("N", isY)){
					isExist = true;
				}
				/**
				 * 新增的三个筛选条件1：单笔代收即日退≥30000，单笔代收三日退（审核退）≥50000
				 * */
				//R1, 单笔代收即日退≥30000
				boolean codconditionR1=false;
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(codBillPayable.getCodType())&&
						codeAmount.compareTo(new BigDecimal(SettlementReportNumber.THIRY_THOUSAND))>=0){
					codconditionR1=true;
					LOGGER.info("单笔代收即日退退款金额为：" + codeAmount + "...和30000比较的结果是：" + codeAmount.compareTo(new BigDecimal(SettlementReportNumber.THIRY_THOUSAND)));
				}
				//R3,单笔代收三日退≥50000  SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
				boolean codconditionR3=false;
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(codBillPayable.getCodType())&&
						codeAmount.compareTo(new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND))>=0){
					codconditionR3=true;
					LOGGER.info("单笔代收三日退退款金额为：" + codeAmount + "...和50000比较的结果是：" + codeAmount.compareTo(new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND)));
				}
				//RA,单笔代收审核退≥50000  SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
				boolean codconditionRA=false;
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(codBillPayable.getCodType())&&
						codeAmount.compareTo(new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND))>=0){
					codconditionRA=true;
					LOGGER.info("单笔代收审核退退款金额为：" + codeAmount + "...和50000比较的结果是：" + codeAmount.compareTo(new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND)));
				}
				
				/**新增的三个筛选条件2:付款方式为“电汇”的所有单据
				 * */
				boolean codpayType = false;
				//获取应收单里的付款类型
				String  payType = billReceivableEntity.getPaymentType();
				//做判断，是否是电汇
				if(payType.equals(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER)){
					codpayType = true;
				}
				
				/**新增的三个筛选条件3.代收货款更改受理时间与签收时间之差小于1小时的
				 * */
				boolean isChangeOverTime = false;
				//定义Dto存放单号
				CodAuditForVtsSignDto codAuditDto = new CodAuditForVtsSignDto();
				codAuditDto.setWaybillNo(waybillNo);
				//获取更改时间
				List<CodAuditForVtsSignEntity>  codAuditEntityList= vtsCodAuditService.queryCodChangeTime(codAuditDto);
				if(CollectionUtils.isNotEmpty(codAuditEntityList)){
					//排序,按照
					ListComparator codChangeEntity = new ListComparator("changeTime");
					Collections.sort(codAuditEntityList, codChangeEntity);
					//获取最后一条更改记录
					CodAuditForVtsSignEntity   codAuditEntity = codAuditEntityList.get((codAuditEntityList.size() - 1));
					//签收跟更改时间差
					double subChangeTime = Math.abs(signTime.getTime()-codAuditEntity.getChangeTime().getTime());
					LOGGER.info("(收银确认操作)运单更改的时间差毫秒数为：" + subChangeTime);
					if(subChangeTime<(1*SettlementReportNumber.NUMBER_3600000)){
						isChangeOverTime = true;
					}
				}
				/** 判断:满足任意一个则为需审核数据
				 *  */
				if((result>-1) || (num>-1) || (isEquals) || (isOverTime) || (timeIsBetween) || (isExist)){
					//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
					CodAuditForVtsSignEntity record = new CodAuditForVtsSignEntity();
					record.setId(UUIDUtils.getUUID());
					record.setWaybillNo(waybillNo);//单号
					record.setActive("Y");
					record.setCodAmount(codeAmount);//代收金额
					record.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT);//资金部待审核状态
					record.setSigTime(signTime);//签收时间
					record.setOrigOrgCode(origOrgcode);//出发部门编码
					record.setOrigOrgName(codBillPayable.getOrigOrgName());//出发部门名称
					record.setDestOrgCode(destOrgcode);//到达部门编码
					record.setDestOrgName(codBillPayable.getDestOrgName());//到达部门名称
					record.setHasTrack(isY);//是否有走货路径Y/N 
					record.setCodType(codBillPayable.getCodType());//代收货款类型
					record.setPaymentType(paymentType);//付款方式
					record.setBillTime(billTime);//开单时间
					if(cashConfirmTime != null){
						record.setComfirmTime(cashConfirmTime);//收银确定时间
					}else{
						throw new SettlementException("收银时间为空！");
					}
					record.setAccountNo(codBillPayable.getAccountNo());//银行行号
					record.setCustomerCode(codBillPayable.getCustomerCode());//客户编码
					record.setCustomerName(codBillPayable.getCustomerName());//客户名称
					record.setMobileNo(codBillPayable.getCustomerPhone());//客户手机号码
					record.setCreateDate(new Date());//创建日期
			        UserEntity user =  FossUserContext.getCurrentUser();
					String userCode = user != null ?user.getUserName():"";
					record.setCreateUser(userCode);//创建人
					record.setBillSignDiffer(signBillDiffer);//签收开单时长
					record.setChangeAmount(absAmount);//代收更改金额
					
					vtsCodAuditService.addCodAudit(record);
					
					//2.同时还要往stl.t_stl_cod_audit_log日志表中插入,记录时效的时候把这表里的是否有效改成N 
					CodAuditForVtsSignDto audiDto = new CodAuditForVtsSignDto();
	    			List<String> waybillListNo = new ArrayList<String>();
					List<VtsCodAuditLogEntity> logList = new ArrayList<VtsCodAuditLogEntity>();
					waybillListNo.add(waybillNo);
					audiDto.setWaybillNos(waybillListNo);
					logList = buildCodAuditLogs(audiDto.getWaybillNos(),logList,"资金部待审核");
					if(logList != null && logList.size() > 0){
						vtsCodAuditLogService.insertBath(logList);//还是调用的批量插入
					}else{
						LOGGER.info("生效应付单的时候，插入日志的实体信息为空！");
					}
				}else{
					if(codconditionR1||codconditionR3||codconditionRA||codpayType||isChangeOverTime){
						//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
						CodAuditForVtsSignEntity record = new CodAuditForVtsSignEntity();
						record.setId(UUIDUtils.getUUID());
						//单号
						record.setWaybillNo(waybillNo);
						record.setActive("Y");
						//代收金额
						record.setCodAmount(codeAmount);
						//SETTLE_CODAUDIT_REVIEWAUDIT复合会计待审核
						record.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT);
						//签收时间
						record.setSigTime(signTime);
						//出发部门编码
						record.setOrigOrgCode(origOrgcode);
						//出发部门名称
						record.setOrigOrgName(codBillPayable.getOrigOrgName());
						//到达部门编码
						record.setDestOrgCode(destOrgcode);
						//到达部门名称
						record.setDestOrgName(codBillPayable.getDestOrgName());
						//是否有走货路径Y/N
						record.setHasTrack(isY); 
						//代收货款类型
						record.setCodType(codBillPayable.getCodType());
						//付款方式
						record.setPaymentType(paymentType);
						//开单时间
						record.setBillTime(billTime);
						if(cashConfirmTime != null){
							//收银确定时间	
							record.setComfirmTime(cashConfirmTime);
						}else{
							throw new SettlementException("收银时间为空！");
						}
						//银行行号
						record.setAccountNo(codBillPayable.getAccountNo());
						//客户编码
						record.setCustomerCode(codBillPayable.getCustomerCode());
						//客户名称
						record.setCustomerName(codBillPayable.getCustomerName());
						//客户手机号码
						record.setMobileNo(codBillPayable.getCustomerPhone());
						//创建日期
						record.setCreateDate(new Date());
						UserEntity user =  FossUserContext.getCurrentUser();
						String userCode = user != null ?user.getUserName():"";
						//创建人
						record.setCreateUser(userCode);
						//签收开单时长
						record.setBillSignDiffer(signBillDiffer);
						//代收更改金额
						record.setChangeAmount(absAmount);
						//插入代收货款
						vtsCodAuditService.addCodAudit(record);
						
						//2.同时还要往stl.t_stl_cod_audit_log日志表中插入,记录时效的时候把这表里的是否有效改成N 
		    			CodAuditForVtsSignDto audiDto = new CodAuditForVtsSignDto();
		    			List<String> waybillListNo = new ArrayList<String>();
						List<VtsCodAuditLogEntity> logList = new ArrayList<VtsCodAuditLogEntity>();
						waybillListNo.add(waybillNo);
						audiDto.setWaybillNos(waybillListNo);
						logList = buildCodAuditLogs(audiDto.getWaybillNos(),logList,"复合会计组待审核");
						if(logList != null && logList.size() > 0){
							//还是调用的批量插入
							vtsCodAuditLogService.insertBath(logList);
						}else{
							LOGGER.info("生效应付单的时候，插入日志的实体信息为空！");
					
						}
					}	
				}
				
			}else {
				// 更新代收货款应付单的签收日期
				codBillPayable.setSignDate(dto.getSignDate());
				this.billPayableService.updateBillPayableSignDateByConfirmIncome(codBillPayable,
						currentInfo);
			}
		}// 2013-01-21 需求变更结束
		
	}

	/**
	 * 根据一个单据类型获取应付单实体.
	 * 
	 * @param lists
	 *            the lists
	 * @param billType
	 *            the bill type
	 * @return the bill payable entity
	 * @author 310970 caopeng
	 * @date 2016-6-14
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private BillPayableEntity getBillPayableEntity(List<BillPayableEntity> lists,String billType) {
		if (CollectionUtils.isEmpty(lists)) {
			return null;
		}
		Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
				billType);
		
		List<BillPayableEntity> list = (List<BillPayableEntity>) CollectionUtils.select(lists,
				predicate);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	private List<BillPayableEntity> checkBillPayableEntity(LineSignDto dto,
			List<BillPayableEntity> billPayables) {
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}
		if (CollectionUtils.isEmpty(billPayables)) {
			return null;
		}
		
		// 验证同一个运单相同类型的应付单是否存在多条记录
		this.billPayableService.validateWaybillForBillPayable(billPayables);
		
		List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();
		boolean isTL = true;
		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity payableEntity = billPayables.get(i);

			// 应付单类型:代收货款应付单
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
					.equals(payableEntity.getBillType())) {
				list.add(payableEntity);
				isTL = false;
			}
			// 应付单类型：装卸费应付单
			else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(payableEntity.getBillType())) {
				list.add(payableEntity);
				isTL = false;
			}
			else if((SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(payableEntity.getBillType())
					&&FossConstants.YES.equals(payableEntity.getEffectiveStatus()))
					    &&(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(payableEntity.getBillType())
							&&FossConstants.YES.equals(payableEntity.getEffectiveStatus()))){
				throw new SettlementException("签收确认时,装卸费应付单和代收货款应付单没有未生效的状态!");
			}
		}
		if(isTL){
			throw new SettlementException("签收确认时，FOSS应付单表中没有未生效的装卸费或代收货款应付单！");
		}
		return list;
	}

	private void validateParams(WaybillSignResultEntity dto, boolean isSign) {

		String meg = isSign ? "" : VALIDATE_PARAM_MES_NOT;
		
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号为空");
		}
		if (!StringUtils.isNumeric(dto.getWaybillNo())) {
			throw new SettlementException("输入的运单号不合法！");
		}
		if (StringUtils.isEmpty(dto.getCreateOrgCode())) {
			throw new SettlementException(String.format("%s签收部门编码为空", meg));
		}

		LOGGER.info(" 开始调用接送货接口验证规则 ");
		
		VtsWaybillFRcQueryByWaybillNoDto waybillFRcQueryByWaybillNoDto = new VtsWaybillFRcQueryByWaybillNoDto();//创建对象
		waybillFRcQueryByWaybillNoDto.setPreAccecpt(WaybillRfcConstants.PRE_ACCECPT);
		waybillFRcQueryByWaybillNoDto.setPreAudit(WaybillRfcConstants.PRE_AUDIT);
		waybillFRcQueryByWaybillNoDto.setWaybillNo(dto.getWaybillNo());
		// 判断是否存在未受理的更改单
		boolean b = vtsEffectPayableDao.isExsitsWayBillRfc(waybillFRcQueryByWaybillNoDto);
		if (b) {
			// 存在未受理的更改单
			throw new SettlementException("运单" + dto.getWaybillNo() + "存在未受理的更改单");
		}
		if (dto.getSignTime() == null) {
			throw new SettlementException(String.format("%s签收日期不能为空", meg));
		}

		LOGGER.info(" 调用校验规则成功！ ");
	}
	 /**
     * 创建日志集合
     * @param waybillNos
     * @param logEntities
     * @param operatContext
     */
    public List<VtsCodAuditLogEntity> buildCodAuditLogs(List<String> waybillNos ,
                                   List<VtsCodAuditLogEntity> logEntities,
                                   String operatContext){
        UserEntity user =  FossUserContext.getCurrentUser();
        String userCode = user != null ?user.getUserName():"";
        String deptCode = FossUserContext.getCurrentDeptCode();
        String deptName = FossUserContext.getCurrentDeptName();
        //遍历运单号，进行封装日志信息
        if(CollectionUtils.isNotEmpty(waybillNos)){
            for(String waybillNo:waybillNos){
                VtsCodAuditLogEntity entity = new VtsCodAuditLogEntity();
                entity.setId(UUID.randomUUID().toString());
                entity.setWaybillNo(waybillNo);
                entity.setModifyUser(userCode);
                entity.setModifyDate(new Date());
                entity.setCreateUser(userCode);
                entity.setOperateContent(operatContext);
                entity.setOperateDeptcode(deptCode);
                entity.setOperateDeptname(deptName);
                entity.setOperateTime(new Date());
                entity.setCreateDate(new Date());
                logEntities.add(entity);
            }
        }
        return logEntities;
    }
	public void setVtsEffectPayableDao(IVtsEffectPayableDao vtsEffectPayableDao) {
		this.vtsEffectPayableDao = vtsEffectPayableDao;
	}
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	public void setVtsTakingService(IVtsTakingService vtsTakingService) {
		this.vtsTakingService = vtsTakingService;
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}
	/*public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}*/
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}
	public void setVtsCodAuditService(IVtsCodAuditService vtsCodAuditService) {
		this.vtsCodAuditService = vtsCodAuditService;
	}
	public void setVtsCodAuditLogService(
			IVtsCodAuditLogService vtsCodAuditLogService) {
		this.vtsCodAuditLogService = vtsCodAuditLogService;
	}

}
