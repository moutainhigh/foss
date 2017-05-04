package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IWaybillEntityForEcsDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryIsVehicleassembleForEcs;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICodAuditDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.INonRefundCodLockService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author 218392 zyx
 * @date 2016-07-07- 19:49:00
 * 长期未退款代收货款冻结Service
 * 长期未退代收货款冻结需求,需求描述如下：
 * （1）90天≤N（运单签收时间）≤730天，系统每天23:00筛选符合条件的数据，并进行冻结,冻结后的数据进入代收货款支付审核界面并进行展示； 
 * （2）N（运单签收时间）＞730天，系统每天23:00筛选符合条件的数据，并进行永久冻结，永久冻结后数据进入“代收货款支付审核界面”并进行展示；
 * （3）长期冻结的运单，若需要支付代收货款，只能通过起草异常退代收工作流进行退款；
 * （4）审核状态为：冻结，此类运单不能进行运单更改（给予弹窗提示：该单已被冻结，联系资金安全控制组取消冻结）、不能进行代收货款支付、
 *      不能进行账号修改，资金部取消冻结后方可进行运单更改；审核状态为：永久冻结，此类运单不能进行任何付款操作，不能进行任何运单更改，不能进行账号修改。
 */
public class NonRefundCodLockService implements INonRefundCodLockService{
	
	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NonRefundCodLockService.class);
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;

	/** 
	 * 代收货款服务.
	 */
	private ICodCommonService codCommonService;
	
	/**
	 * 代收货款审核Service
	 */
	private ICodAuditService codAuditService;
	
    /**
     * 查询第一次开单时候的代收金额
     * @return
     */
    private IWaybillDao waybillDao;
    
	/** 
	 * 查询配载单记录的 
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/**
	 * 注入还款单公共接口
	 */
	private IBillRepaymentService billRepaymentService;
	
    /**
     * 待审核代收货款导出
     */
    ICodAuditDao codAuditDao;
    
    /**
	 * 注入查询配载单接口
	 */
	private IQueryIsVehicleassembleForEcs queryIsVehicleassembleForEcs;
	
	/**
	 * 注入运单dao
	 */
	private IWaybillEntityForEcsDao waybillEntityForEcsDao;
	
	/**
	 * 执行
	 */
	@Override
	public void process() {
		InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();
		try {

			LOGGER.info("process长期未退款代收货款冻结job开始!");
			String billType = "APC";
			List<BillPayableEntity> billPayableEntitieShort = null;
			List<BillPayableEntity> billPayableEntitieLong = null;
			int twelveHours = 12*3600000;
			/**
			 * 1.签收时间在90天≤N≤730天内；且已签收的运单含有代收货款，收货款付款状态为：未退款、待审核、收银员审核、营业部冻结，
			 * 	每天23:00系统自动对以上数据进行冻结
			 */
			billPayableEntitieShort = codCommonService.queryShortNonRefundCod(billType);
			
			if(CollectionUtils.isNotEmpty(billPayableEntitieShort)){
				LOGGER.info("第一个循环开始");
				for(BillPayableEntity billPayableEntity : billPayableEntitieShort){
					String unPayWaybillNo = billPayableEntity.getWaybillNo();//定义运单号
					LOGGER.info("process单号: " + unPayWaybillNo + "循环开始");
					List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByWaybillNo(unPayWaybillNo);
					LOGGER.info("process单号: " + unPayWaybillNo + "长期未退款代收货款冻结job开始!");
					if(CollectionUtils.isNotEmpty(codAuditEntityList)){
						LOGGER.info("if1");
						/**
						 * (1)如果是人工冻结的，变成RO的，修改人为sysJob，并且修改时间<24小时,这时候不动
						 */
						//查询人工修改的大于24小时的
						List<CodAuditEntity> codAuditEntitySys = codAuditDao.queryCodAuditBySysJob(unPayWaybillNo);
						if(CollectionUtils.isNotEmpty(codAuditEntitySys)){
							LOGGER.info("if2");
							/**
							 * (2)对于人工取消冻结的或者已经审核通过 RO，时间要大于24小时才可以再次冻结，就是修改成SSL
							 */
							CodAuditDto dto = new CodAuditDto();
							List<String> str = new ArrayList<String>();
							//批量更新
							for(CodAuditEntity en:codAuditEntitySys){
								
								str.add(en.getWaybillNo());
								
							}
							dto.setWaybillNos(str);
			            	dto.setLockStatus("SSL");
			            	dto.setModifyUerName("sysJob");
			            	dto.setModifyUserCode("sysJob");
			            	codAuditDao.updateAuditStatusBath(dto);
						}
						
					}else{

						LOGGER.info("else1");
						/**  1、单笔代收退款金额≥80000元   
						 * */
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						BigDecimal codeAmount= billPayableEntity.getAmount();//单笔代收退款金额
						BigDecimal amount1 = new BigDecimal(SettlementReportNumber.EIGHTY_THOUSAND);//定义80000元
						int result = codeAmount.compareTo(amount1);//1大于，-1小于，0是等于
						LOGGER.info("process长期未退款JOB单笔代收货款退款金额为：" + codeAmount + "...和80000比较的结果是：" + result);
						
						/**  2、单笔代收货款金额更改额绝对值≥50000元的  
						 * */
						String waybillNo = billPayableEntity.getWaybillNo();//运单号
						if(!StringUtils.isNotEmpty(waybillNo)){
							throw new SettlementException("process运单号为空！");
						}
						WaybillEntity entity= waybillDao.queryFirstWaybillByWaybillNo(waybillNo);//通过运单编号查询第一次开单时运单信息
						if(entity == null){
							//throw new SettlementException("process查询的运单实体为空！");
							LOGGER.info("process查询的运单实体为空！");
						}else{
							BigDecimal waybillCodAmount = entity.getCodAmount();//第一次开单时候的代收金额
							LOGGER.info("process长期未退款JOB通过运单编号查询第一次开单时的金额为：" + waybillCodAmount);
							BigDecimal amountCompare = codeAmount.subtract(waybillCodAmount);//差值
							BigDecimal amount2 = new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND);//定义80000元
							BigDecimal absAmount = amountCompare.abs();//绝对值
							int num = absAmount.compareTo(amount2);//1大于，-1小于，0是等于
							LOGGER.info("process长期未退款JOB单笔代收货款金额更改额绝对值为：" + absAmount + "...和50000比较的结果是：" + num);
							
							/**  3、出发部门=到达部门
							 *   */
							String  origOrgcode = billPayableEntity.getOrigOrgCode();//出发部门(取自应付单)
							String destOrgcode 	= billPayableEntity.getDestOrgCode();//到达部门(取自应付单)
							LOGGER.info("process长期未退款JOB出发部门编码为：" + origOrgcode +",到达部门编码为：" + destOrgcode);
							Boolean isEquals = false;
							if(StringUtils.isNotEmpty(origOrgcode) || StringUtils.isNotEmpty(destOrgcode)){
								isEquals = origOrgcode.equals(destOrgcode);
							}else{
								LOGGER.info("长期未退款JOB出发部门或到达部门为空！");
							}
							
							/**  4、签收时间-开单时间≤12小时  
							 * */
							Date signTime = billPayableEntity.getSignDate();//签收时间
							Date billTime = entity.getBillTime();//开单时间
							LOGGER.info("process长期未退款JOB签收的时间是：" + dateFormat.format(signTime));
							LOGGER.info("process长期未退款JOB开单的时间是：" + dateFormat.format(billTime));
							double subTime = Math.abs(signTime.getTime() - billTime.getTime());
							LOGGER.info("process长期未退款JOB开单的时间差毫秒数为：" + subTime);
							Boolean isOverTime = false;
							if(subTime < twelveHours){//签收时间是否小于12个小时
								isOverTime = true;
							}
							LOGGER.info("process签收时间是否小于12个小时：" + isOverTime);
							//签收开单时长单位小时，用于插入代收审核表中
							int signBillDiffer = (int) (subTime/3600000.0) ;
							LOGGER.info("process长期未退款JOB开单的时长是：" + signBillDiffer);
							
							/**  
							 * 5、线上BUG修复 2016-01-11 13:41:50 放到上线版本为0114
							 * 签收时间在签收当天8:00之前, 23：00之后的代收货款数据
							 */
							Calendar cal = Calendar.getInstance();
							cal.setTime(signTime);
							cal.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.TWENTY_THREE);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 000);
							Date date23 = cal.getTime();//获取当天23:00
							LOGGER.info("process长期未退款JOB当天23点00分000毫秒的时间是：" + dateFormat.format(date23));
							
							Calendar cal8 = Calendar.getInstance();
							cal8.setTime(signTime);
							cal8.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.EIGHT);
							cal8.set(Calendar.MINUTE, 0);
							cal8.set(Calendar.SECOND, 0);
							cal8.set(Calendar.MILLISECOND, 000);
							Date date8 = cal8.getTime();//获取次日08:00:00:000
							LOGGER.info("process长期未退款JOB当天8点00分00秒000毫秒的时间是：" + dateFormat.format(date8));
							LOGGER.info("process签收时间为：" + dateFormat.format(signTime));
							
							long signTimeLon = signTime.getTime();
							long date23Lon 	 = date23.getTime();
							long date8Lon	 = date8.getTime();
							
							Boolean timeIsBetween = false;
							if(signTimeLon >= date23Lon || signTimeLon <= date8Lon){
								timeIsBetween = true;
							}
							
							/**  6、存在代收款的运单，货物轨迹中无配载单记录的  
							 * */
							String isY = "";
							Boolean isExist = false;
						/*	isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
							if(StringUtils.equals("N", isY)){
								isExist = true;
							}*/
							
							/**
		    				 * @author 325369
		    				 * @date 2016-09-06
		    				 * 根据运单号查询运单表，判断是否是悟空运单，是则请求悟空接口查询配载单数，否则调用本地接口
		    				 */
							if (waybillEntityForEcsDao.queryWaybillIsEcsByWaybillNo(waybillNo) > 0) {
								isY = queryIsVehicleassembleForEcs.queryIsVehicleassembleForEcs(waybillNo);
								isExist = StringUtils.equals("N", isY) == true ? true : false;
							} else {
								isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
								isExist = StringUtils.equals("N", isY) == true ? true : false;
							}
							
							BillRepaymentConditionDto repaymentConDto = new BillRepaymentConditionDto();
							repaymentConDto.setWaybillNo(waybillNo);
							repaymentConDto.setActive(FossConstants.YES);
							// 来源单据类型：代收货款
							repaymentConDto
									.setSourceBillTypes(new String[] { SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD });
							List<BillRepaymentEntity> repayList = this.billRepaymentService
									.queryBillRepaymentByCondition(repaymentConDto);
							
							
							Date cashConfirmTime = null;//收银确认时间 
							String paymentType = null;
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
									cashConfirmTime = repayEntity.getCashConfirmTime();
									paymentType = repayEntity.getPaymentType();
								}
								
								
							}
								//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
								CodAuditEntity record = new CodAuditEntity();
								record.setId(UUIDUtils.getUUID());
								record.setWaybillNo(waybillNo);//单号
								record.setActive("Y");
								record.setCodAmount(codeAmount);//代收金额
								record.setLockStatus(SettlementDictionaryConstants.SETTLE_SHORT_LOCK);//短期锁定:针对签收时间在90天到730天未退款
								record.setSigTime(signTime);//签收时间
								record.setOrigOrgCode(origOrgcode);//出发部门编码
								record.setOrigOrgName(billPayableEntity.getOrigOrgName());//出发部门名称
								record.setDestOrgCode(destOrgcode);//到达部门编码
								record.setDestOrgName(billPayableEntity.getDestOrgName());//到达部门名称
								record.setHasTrack(isY);//是否有走货路径Y/N
								record.setCodType(billPayableEntity.getCodType());//代收货款类型
								record.setPaymentType(paymentType);//付款方式
								record.setBillTime(billTime);//开单时间
								if(cashConfirmTime != null){
									record.setComfirmTime(cashConfirmTime);//收银确定时间
								}else{
									LOGGER.info("收银时间为空！");
								}
								record.setAccountNo(billPayableEntity.getAccountNo());//银行行号
								record.setCustomerCode(billPayableEntity.getCustomerCode());//客户编码
								record.setCustomerName(billPayableEntity.getCustomerName());//客户名称
								record.setMobileNo(billPayableEntity.getCustomerPhone());//客户手机号码
								record.setCreateDate(new Date());//创建日期
								record.setCreateUser("sysJob");//创建人
								record.setBillSignDiffer(signBillDiffer);//签收开单时长
								record.setChangeAmount(absAmount);//代收更改金额
						        codAuditService.addShortOrLongLock(record);
						}
					}
					LOGGER.info("process单号: " + unPayWaybillNo + "循环结束");
				}
			}else{
				LOGGER.info("process长期未退款代收货款短期冻结查询结果为空！");
			}
			LOGGER.info("第一个循环终于跑完了");
			/**
			 * 2.运单签收时间N＞730天，且该运单含有代收货款，代收货款付款状态为：未退款、待审核、收银员审核、营业部冻结，
			 *  系统每天23:00点筛选出符合以上条件的数据，并进行永久冻结；
			 */
			billPayableEntitieLong = codCommonService.queryLongNonRefundCod(billType);
			
			if(CollectionUtils.isNotEmpty(billPayableEntitieLong)){
				LOGGER.info("大于730");
				LOGGER.info("第二个循环开始");
				for(BillPayableEntity billPayableEntity : billPayableEntitieLong){
					
					/**  1、单笔代收退款金额≥80000元   
					 * */
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					BigDecimal codeAmount= billPayableEntity.getAmount();//单笔代收退款金额
					BigDecimal amount1 = new BigDecimal(SettlementReportNumber.EIGHTY_THOUSAND);//定义80000元
					int result = codeAmount.compareTo(amount1);//1大于，-1小于，0是等于
					LOGGER.info("process长期未退款JOB单笔代收货款退款金额为：" + codeAmount + "...和80000比较的结果是：" + result);
					
					/**  2、单笔代收货款金额更改额绝对值≥50000元的  
					 * */
					String waybillNo = billPayableEntity.getWaybillNo();//运单号
					if(!StringUtils.isNotEmpty(waybillNo)){
						throw new SettlementException("process运单号为空！");
					}
					WaybillEntity entity= waybillDao.queryFirstWaybillByWaybillNo(waybillNo);//通过运单编号查询第一次开单时运单信息
					BigDecimal absAmount = BigDecimal.ZERO;
					if(entity != null){
						BigDecimal waybillCodAmount = entity.getCodAmount();//第一次开单时候的代收金额
						LOGGER.info("process长期未退款JOB通过运单编号查询第一次开单时的金额为：" + waybillCodAmount);
						BigDecimal amountCompare = codeAmount.subtract(waybillCodAmount);//差值
						BigDecimal amount2 = new BigDecimal(SettlementReportNumber.FIFTY_THOUSAND);//定义80000元
						absAmount = amountCompare.abs();//绝对值
						int num = absAmount.compareTo(amount2);//1大于，-1小于，0是等于
						LOGGER.info("process长期未退款JOB单笔代收货款金额更改额绝对值为：" + absAmount + "...和50000比较的结果是：" + num);
					}
					
					/**  3、出发部门=到达部门
					 *   */
					String  origOrgcode = billPayableEntity.getOrigOrgCode();//出发部门(取自应付单)
					String destOrgcode 	= billPayableEntity.getDestOrgCode();//到达部门(取自应付单)
					LOGGER.info("process长期未退款JOB出发部门编码为：" + origOrgcode +",到达部门编码为：" + destOrgcode);
					Boolean isEquals = false;
					if(StringUtils.isNotEmpty(origOrgcode) || StringUtils.isNotEmpty(destOrgcode)){
						isEquals = origOrgcode.equals(destOrgcode);
					}else{
						LOGGER.info("process长期未退款JOB出发部门或到达部门为空！");
					}
					int signBillDiffer = 0;
					Date signTime = billPayableEntity.getSignDate();//签收时间
					Date billTime = billPayableEntity.getBusinessDate();//开单时间(取应付单的业务时间)
					if(entity != null){
					/**  4、签收时间-开单时间≤12小时  
					 * */
					LOGGER.info("process长期未退款JOB签收的时间是：" + dateFormat.format(signTime));
					LOGGER.info("process长期未退款JOB开单的时间是：" + dateFormat.format(billTime));
					double subTime = Math.abs(signTime.getTime() - billTime.getTime());
					LOGGER.info("process长期未退款JOB开单的时间差毫秒数为：" + subTime);
					Boolean isOverTime = false;
					if(subTime < twelveHours){//签收时间是否小于12个小时
						isOverTime = true;
					}
					LOGGER.info("process签收时间是否小于12个小时：" + isOverTime);
					//签收开单时长单位小时，用于插入代收审核表中
					 signBillDiffer = (int) (subTime/3600000.0) ;
					LOGGER.info("process长期未退款JOB开单的时长是：" + signBillDiffer);
					}
					
					/**  
					 * 5、线上BUG修复 2016-01-11 13:41:50 放到上线版本为0114
					 * 签收时间在签收当天8:00之前, 23：00之后的代收货款数据
					 */
					Calendar cal = Calendar.getInstance();
					cal.setTime(signTime);
					cal.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.TWENTY_THREE);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 000);
					Date date23 = cal.getTime();//获取当天23:00
					LOGGER.info("process长期未退款JOB当天23点00分000毫秒的时间是：" + dateFormat.format(date23));
					
					Calendar cal8 = Calendar.getInstance();
					cal8.setTime(signTime);
					cal8.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.EIGHT);
					cal8.set(Calendar.MINUTE, 0);
					cal8.set(Calendar.SECOND, 0);
					cal8.set(Calendar.MILLISECOND, 000);
					Date date8 = cal8.getTime();//获取次日08:00:00:000
					LOGGER.info("process长期未退款JOB当天8点00分00秒000毫秒的时间是：" + dateFormat.format(date8));
					LOGGER.info("process签收时间为：" + dateFormat.format(signTime));
					
					long signTimeLon = signTime.getTime();
					long date23Lon 	 = date23.getTime();
					long date8Lon	 = date8.getTime();
					
					Boolean timeIsBetween = false;
					if(signTimeLon >= date23Lon || signTimeLon <= date8Lon){
						timeIsBetween = true;
					}
					
					/**  6、存在代收款的运单，货物轨迹中无配载单记录的  
					 * */
					String isY = "";
					Boolean isExist = false;
					/*isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
					if(StringUtils.equals("N", isY)){
						isExist = true;
					}*/
					/**
					 * @author 325369
					 * @date 2016-09-06
					 * 根据运单号查询运单表，判断是否是悟空运单，是则请求悟空接口查询配载单数，否则调用本地接口
					 */
					if (waybillEntityForEcsDao.queryWaybillIsEcsByWaybillNo(waybillNo) > 0) {
						isY = queryIsVehicleassembleForEcs.queryIsVehicleassembleForEcs(waybillNo);
						isExist = StringUtils.equals("N", isY) == true ? true : false;
					} else {
						isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
						isExist = StringUtils.equals("N", isY) == true ? true : false;
					}
					
					BillRepaymentConditionDto repaymentConDto = new BillRepaymentConditionDto();
					repaymentConDto.setWaybillNo(waybillNo);
					repaymentConDto.setActive(FossConstants.YES);
					// 来源单据类型：代收货款
					repaymentConDto
							.setSourceBillTypes(new String[] { SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD });
					List<BillRepaymentEntity> repayList = this.billRepaymentService
							.queryBillRepaymentByCondition(repaymentConDto);
					
					
					Date cashConfirmTime = null;//收银确认时间 
					String paymentType = null;
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
							cashConfirmTime = repayEntity.getCashConfirmTime();
							paymentType = repayEntity.getPaymentType();
						}
						
						
					}
						//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
						CodAuditEntity record = new CodAuditEntity();
						record.setId(UUIDUtils.getUUID());
						record.setWaybillNo(waybillNo);//单号
						record.setActive("Y");
						record.setCodAmount(codeAmount);//代收金额
						record.setLockStatus(SettlementDictionaryConstants.SETTLE_LONG_LOCK);//长期锁定:针对签收时间730天以上未退款
						record.setSigTime(signTime);//签收时间
						record.setOrigOrgCode(origOrgcode);//出发部门编码
						record.setOrigOrgName(billPayableEntity.getOrigOrgName());//出发部门名称
						record.setDestOrgCode(destOrgcode);//到达部门编码
						record.setDestOrgName(billPayableEntity.getDestOrgName());//到达部门名称
						record.setHasTrack(isY);//是否有走货路径Y/N
						record.setCodType(billPayableEntity.getCodType());//代收货款类型
						record.setPaymentType(paymentType);//付款方式
						record.setBillTime(billTime);//开单时间
						if(cashConfirmTime != null){
							record.setComfirmTime(cashConfirmTime);//收银确定时间
						}else{
							LOGGER.info("process收银时间为空！");
						}
						record.setAccountNo(billPayableEntity.getAccountNo());//银行行号
						record.setCustomerCode(billPayableEntity.getCustomerCode());//客户编码
						record.setCustomerName(billPayableEntity.getCustomerName());//客户名称
						record.setMobileNo(billPayableEntity.getCustomerPhone());//客户手机号码
						record.setCreateDate(new Date());//创建日期
						record.setCreateUser("sysJob");//创建人
						record.setBillSignDiffer(signBillDiffer);//签收开单时长
						record.setChangeAmount(absAmount);//代收更改金额
				        codAuditService.addShortOrLongLock(record);
				}
				LOGGER.info("第二个循环结束");
			
			}else{
				LOGGER.info("process长期未退款代收货款长期冻结查询结果为空！");
			}
			
		
		} catch (SettlementException se) {
			
			interfaceEntity.setIsSuccess("N");
			StringWriter writer = new StringWriter();
			se.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("长期未退款job冻结报错SettlementException：" + writer.toString());
			LOGGER.info("长期未退款job冻结报错SettlementException：" + writer.toString());
		}catch (BusinessException ex) {
			
			interfaceEntity.setIsSuccess("N");
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("长期未退款job冻结报错BusinessException：" + writer.toString());
			LOGGER.info("长期未退款job冻结报错BusinessException：" + writer.toString());
		} catch (Exception e) {
			
			interfaceEntity.setIsSuccess("N");
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("长期未退款job冻结报错Exception：" + writer.toString());
			LOGGER.info("长期未退款job冻结报错Exception：" + writer.toString());
			//记录失败日志
		}finally{
			/**
			 * 定义插入log日志
			 * stl.t_stl_bill_interfacelog
			 */
			//插入接口日志表
			interfaceEntity.setId(UUIDUtils.getUUID());
			interfaceEntity.setWaybillNo("job001");
			interfaceEntity.setEsbCode("jobcod");//服务端编码:开单更改单财务单据
			interfaceEntity.setSystemType("job");
			interfaceEntity.setSendContent("--");
			interfaceEntity.setCreateUser("218392");
			interfaceEntity.setModifyUser("job-ZYX");
			interfaceEntity.setCreateTime(new Date());
			LOGGER.info("addInterfaceLog代收货款长期未退款冻结开始插入日志!");
			/**
			 * 不需要记录日志
			 */
			esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			LOGGER.info("addInterfaceLog代收货款长期未退款冻结结束插入日志!");
		}
	}
	/**
	 * 插入日志表
	 */
	@Override
	public boolean addInterfaceLog(InterfaceLogEntity entity) {
		esbInterfaceLogService.addInterfaceLog(entity);
		return false;
	}
	
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}


	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setCodAuditDao(ICodAuditDao codAuditDao) {
		this.codAuditDao = codAuditDao;
	}

	public void setQueryIsVehicleassembleForEcs(
			IQueryIsVehicleassembleForEcs queryIsVehicleassembleForEcs) {
		this.queryIsVehicleassembleForEcs = queryIsVehicleassembleForEcs;
	}

	public void setWaybillEntityForEcsDao(
			IWaybillEntityForEcsDao waybillEntityForEcsDao) {
		this.waybillEntityForEcsDao = waybillEntityForEcsDao;
	}

	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}

}
