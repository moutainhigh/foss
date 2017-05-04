package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillPayQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 应收冲应付服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午2:13:56
 */
public class BillRecWriteoffBillPayService implements IBillRecWriteoffBillPayService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(BillRecWriteoffBillPayService.class);

	/**
	 * 注入应收冲应付DAO
	 */
	private IBillWriteoffBillRecQueryDao billWriteoffBillRecQueryDao;

	/**
	 * 注入应收Dao
	 */
	private IBillWriteoffBillPayQueryDao billWriteoffBillPayQueryDao;

	/**
	 * 获取批次号
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入核销应收冲应付服务
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 对账单公共service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 调用接送货更改单服务接口
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;
	


	/**
	 * 查询待核销应收单、应付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-17 下午4:41:07
	 * @param billReceivableDto
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService#queryReceivableList(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public BillRecWriteoffBillPayResultDto queryRecWriteoffPayList(BillRecWriteoffBillPayDto billRecWriteOffPayDto, CurrentInfo cInfo)throws BusinessException {
		//查询dto不能为空
		if (billRecWriteOffPayDto == null) {
			//提示内部错误，待核销的应付单和应收单为空
			throw new SettlementException("内部错误，待核销的应付单和应收单为空!");
		}
		
		/*BUG-57417 092036-BOCHENLONG
		 * 
		 *COPY DTO属性，给应收应付实用，避免DTO属性发生变化 
		 */
		BillRecWriteoffBillPayDto billRecWriteoffBillPayDtoRec = new BillRecWriteoffBillPayDto();
		BeanUtils.copyProperties(billRecWriteOffPayDto, billRecWriteoffBillPayDtoRec);
		
		BillRecWriteoffBillPayDto billRecWriteoffBillPayDtoPay = new BillRecWriteoffBillPayDto();
		BeanUtils.copyProperties(billRecWriteOffPayDto, billRecWriteoffBillPayDtoPay);
		
		// 设置应付单是否可以为红单、是否有效等状态,并返回查询列表
		List<BillPayableEntity> payableList = queryBillPayList(billRecWriteoffBillPayDtoPay, cInfo);
		// 设置应收单查询条件非红单、是否有效等状态,并返回查询列表
		List<BillReceivableEntity> receivableList = queryBillRecList(billRecWriteoffBillPayDtoRec, cInfo);
		//记录日志
		LOGGER.info("应收冲应付服务" + "应收单总条数size："+ (receivableList != null ? receivableList.size() : 0)+ "应付单总条数：" + (payableList != null ? payableList.size() : 0));
		
		//生成应收冲应付返回dto
		BillRecWriteoffBillPayResultDto billRecWriteoffBillPayResultDto = new BillRecWriteoffBillPayResultDto();
		// 统计应收单总条数、总金额、总核销条数、总未核销金额、
		//如果应收单列表
		if (CollectionUtils.isNotEmpty(receivableList)) {
			int recTotalNum = receivableList.size();// 应收单总条数
			BigDecimal recTotalAmount = BigDecimal.ZERO;// 应收单总金额
			BigDecimal recUnverifyTotalAmount = BigDecimal.ZERO;// 应收单未核销的金额
			BigDecimal recVerifyTotalAmount = BigDecimal.ZERO;//应收单核销的总金额
			// 计算应收总条数、总金额、总核销条数、总未核销条数
			//循环处理
			for (BillReceivableEntity entity : receivableList) {
				//总金额
				recTotalAmount = recTotalAmount.add(entity.getAmount());
				//未核销总金额
				recUnverifyTotalAmount = recUnverifyTotalAmount.add(entity.getUnverifyAmount());
				//已核销总金额
				recVerifyTotalAmount = recVerifyTotalAmount.add(entity.getVerifyAmount());
			}
			// 设置应收总条数
			billRecWriteoffBillPayResultDto.setRecTotalNum(recTotalNum);
			// 设置应收应收总金额
			billRecWriteoffBillPayResultDto.setRecTotalAmount(recTotalAmount);
			// 设置应收应收总核销金额
			billRecWriteoffBillPayResultDto.setRecUnverifyTotalAmount(recUnverifyTotalAmount);
			// 设置应收应收总未核销金额
			billRecWriteoffBillPayResultDto.setRecVerifyTotalAmount(recVerifyTotalAmount);
			// 将查询的应收单结果集在DTO返回到action
			billRecWriteoffBillPayResultDto.setBillReceivableEntityList(receivableList);
		}

		// 统计应付单的总条数、应付总金额、应付总核销金额、应付未核销金额
		//如果应付单列表不为空
		if (CollectionUtils.isNotEmpty(payableList)) {
			//总条
			int payTotalNum = payableList.size();
			//总金额
			BigDecimal payTotalAmount = BigDecimal.ZERO;
			//总未核销金额
			BigDecimal payUnverifyTotalAmount = BigDecimal.ZERO;
			//总核销金额
			BigDecimal payVerifyTotalAmount = BigDecimal.ZERO;

			// 统计应付的应付总条数、应付未核销金额、应付已核销金额、应付总金额
			//循环处理
			for (BillPayableEntity entity : payableList) {
				//总金额
				payTotalAmount = payTotalAmount.add(entity.getAmount());
				//总未核销金额
				payUnverifyTotalAmount = payUnverifyTotalAmount.add(entity.getUnverifyAmount());
				//总核销金额
				payVerifyTotalAmount = payVerifyTotalAmount.add(entity.getVerifyAmount());
			}
			// 应付总条数
			billRecWriteoffBillPayResultDto.setPayTotalNum(payTotalNum);
			// 应付总金额
			billRecWriteoffBillPayResultDto.setPayTotalAmount(payTotalAmount);
			// 应付未核销金额
			billRecWriteoffBillPayResultDto.setPayUnverifyTotalAmount(payUnverifyTotalAmount);
			// 应付已核销金额
			billRecWriteoffBillPayResultDto.setPayVerifyTotalAmount(payVerifyTotalAmount);

			
			// 将查询的结果集应付单在DTO返回到action
			billRecWriteoffBillPayResultDto.setBillPayableEntityList(payableList);
		}
		//记录日志
		LOGGER.info("按日期查询应收单总条数size："+ billRecWriteoffBillPayResultDto.getBillPayableEntityList().size()+ "按日期查询应付单总条数："+ billRecWriteoffBillPayResultDto.getBillPayableEntityList().size());
		//返回应收冲应付返回dto
		return billRecWriteoffBillPayResultDto;
	}

	
	
	/**
	 * 按单号查询应收单运单号查询、应付单或者运单查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-29 下午2:38:18
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService#queryBillReceivableOrBillPayableOrWaybillForNumbers(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@Override
	public BillRecWriteoffBillPayResultDto queryBillRecOrBillPayNums(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,CurrentInfo cInfo) {

		// 校验输入参数不能为空
		if (billRecWriteoffBillPayDto == null) {
			//提示内部错误，查询应收单应付单条件为空
			throw new SettlementException("内部错误，查询应收单应付单条件为空!");
		}
		
		/*BUG-57417 092036-BOCHENLONG
		 * 
		 *COPY DTO属性，给应收应付实用，避免DTO属性发生变化 
		 */
		BillRecWriteoffBillPayDto billRecWriteoffBillPayDtoRec = new BillRecWriteoffBillPayDto();
		BeanUtils.copyProperties(billRecWriteoffBillPayDto, billRecWriteoffBillPayDtoRec);
		
		BillRecWriteoffBillPayDto billRecWriteoffBillPayDtoPay = new BillRecWriteoffBillPayDto();
		BeanUtils.copyProperties(billRecWriteoffBillPayDto, billRecWriteoffBillPayDtoPay);
		
		
		// 根据应收单号获取用于可用于核销的应收
		List<BillReceivableEntity> queryReceivableNos = queryRecForNs(billRecWriteoffBillPayDtoRec, cInfo);
		// 根据应付单号获取用于可用于核销应付单信息
		List<BillPayableEntity> queryPayableNos = queryPayForNs(billRecWriteoffBillPayDtoPay, cInfo);

		// 用于service 和action之间参数传递
		BillRecWriteoffBillPayResultDto billRecWriteoffBillPayResultDto = new BillRecWriteoffBillPayResultDto();
		// 设置查询到的应收单的总条数、总金额、未核销金额、已核销金额
		//如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(queryReceivableNos)) {
			// 统计总条数、总金额、总未核销金额、总已核销金额
			// 应收单总条数
			int recTotalNum = queryReceivableNos.size();
			// 应收总金额
			BigDecimal recTotalAmount = BigDecimal.ZERO;
			// 应收未核销金额
			BigDecimal recUnverifyTotalAmount = BigDecimal.ZERO;
			// 应收已核销金额
			BigDecimal recVerifyTotalAmount = BigDecimal.ZERO;
			//循环处理
			for (BillReceivableEntity entity : queryReceivableNos) {
				//总金额
				recTotalAmount = recTotalAmount.add(entity.getAmount());
				//总未核销金额
				recUnverifyTotalAmount = recUnverifyTotalAmount.add(entity.getUnverifyAmount());
				//总已核销金额
				recVerifyTotalAmount = recVerifyTotalAmount.add(entity.getVerifyAmount());
			}
			// 返回action 条数
			billRecWriteoffBillPayResultDto.setRecTotalNum(recTotalNum);
			// 返回action 金额
			billRecWriteoffBillPayResultDto.setRecTotalAmount(recTotalAmount);
			// 返回action 未核销总金额
			billRecWriteoffBillPayResultDto.setRecUnverifyTotalAmount(recUnverifyTotalAmount);
			// 返回action 核销总金额 
			billRecWriteoffBillPayResultDto.setRecVerifyTotalAmount(recVerifyTotalAmount);
		}

		// 设置查询到的应付单的总条数、总金额、未核销金额、已核销金额
		//如果应付单列表不为空
		if (CollectionUtils.isNotEmpty(queryPayableNos)) {
			//总条数
			int payTotalNum = queryPayableNos.size();
			//总条数
			billRecWriteoffBillPayResultDto.setPayTotalNum(payTotalNum);
			//总金额
			BigDecimal payTotalAmount = BigDecimal.ZERO;
			//未核销金额
			BigDecimal payUnverifyTotalAmount = BigDecimal.ZERO;
			//已核销金额
			BigDecimal payVerifyTotalAmount = BigDecimal.ZERO;
			//循环处理
			for (BillPayableEntity entity : queryPayableNos) {
				//总金额
				payTotalAmount = payTotalAmount.add(entity.getAmount());
				//未核销金额
				payUnverifyTotalAmount = payUnverifyTotalAmount.add(entity.getUnverifyAmount());
				//已核销金额
				payVerifyTotalAmount = payVerifyTotalAmount.add(entity.getVerifyAmount());
			}
			// 返回action 总金额
			billRecWriteoffBillPayResultDto.setPayTotalAmount(payTotalAmount);
			// 返回action 核销总金额
			billRecWriteoffBillPayResultDto.setPayVerifyTotalAmount(payVerifyTotalAmount);
			// 返回action 未核销总金额
			billRecWriteoffBillPayResultDto.setPayUnverifyTotalAmount(payUnverifyTotalAmount);
		}

		// 将查询的应收单结果集在DTO返回到action
		billRecWriteoffBillPayResultDto.setBillReceivableEntityList(queryReceivableNos);
		// 将查询的结果集应付单在DTO返回到action
		billRecWriteoffBillPayResultDto.setBillPayableEntityList(queryPayableNos);
		//返回应收冲应付返回dto
		return billRecWriteoffBillPayResultDto;
	}

	
	
	/**
	 * <p>
	 * 核销应收单应付单
	 * </p>
	 * @author 095793-foss-LiQin
	 * @date 2012-10-24 下午1:51:33
	 * @param billPayableEntityList
	 * @param billReceivableEntityList
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService#writeoffBillRecivableBillPaybable(java.util.List,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public BillRecWriteoffBillPayResultDto writeoffBillRecivableBillPaybable(BillRecWriteoffBillPayDto billRecPayDto, CurrentInfo cInfo)throws SettlementException {

		//查询参数dto不能为空
		if (billRecPayDto == null) {
			//提示内部错误，核销应收应付单Dto为空
			throw new SettlementException("内部错误，核销应收应付单Dto为空！");
		}

		//记录日志
		LOGGER.info(this.getClass().getName() + "into...");

		// 获取界面待核销应付单号
		List<BillPayableEntity> payNosFormList = billRecPayDto.getBillPayableEntityList();
		// 获取界面的待核销的应收单号
		List<BillReceivableEntity> receivableNosFormList = billRecPayDto.getBillReceivableEntityList();
		
		/**
		 * 根据界面传入参数，重新从服务器获取待核销的应收单
		 */
		List<BillReceivableEntity> recQueryAllList;
		/**
		 * 根据界面传入参数，重新从服务器获取待核销的应付单列表
		 */
		List<BillPayableEntity> payQueryAllList;

		// 根据传入的dto，查询应收单号或者应付单号
		// 重新从查询最新的应收单号，应付单号，防止单据发生变化
		// 按上一次查询条件执行查询
		if (null != billRecPayDto.getRecBusinessStartDate()&& null != billRecPayDto.getRecBusinessEndDate()&&null!=billRecPayDto.getPayBusinessStartDate()&&null!=billRecPayDto.getPayBusinessEndDate()) {
			// 查询应收单列表
			// 设置应付单是否可以为红单、是否有效等状态,并返回查询列表
			payQueryAllList = queryBillPayList(billRecPayDto, cInfo);
			// 查询应付单列表
			// 设置应收单查询条件非红单、是否有效等状态,并返回查询列表
			recQueryAllList = queryBillRecList(billRecPayDto, cInfo);
			//记录日志
			LOGGER.info("应收冲应付核销，按日期查询" + "应收单总条数size："+ recQueryAllList.size() + "应付单总条数："+ payQueryAllList.size());
		} else {
			// 根据单号，查询应收单列表
			recQueryAllList = queryRecForNs(billRecPayDto, cInfo);
			// 根据单号，查询应付单列表
			payQueryAllList = queryPayForNs(billRecPayDto, cInfo);
			//记录日志
			LOGGER.info("应收冲应付核销按单号查询:" + "应收单总条数size：" + recQueryAllList.size()+ "应付单总条数：" + payQueryAllList.size());
		}
		
		//应收冲应付核销时,查询应付单不能为空
		if(payQueryAllList.size()<=0){
			//提示应收冲应付核销时,查询应付单为空，不能核销核销
			throw new SettlementException("应收冲应付核销时,查询应付单为空，不能核销核销！");
		}
		//应收冲应付核销时,查询应收单不能为空
		if(recQueryAllList.size()<=0){
			//提示应收冲应付核销时,查询应收单为空，不能参加核销
			throw new SettlementException("应收冲应付核销时,查询应收单为空，不能参加核销！");
		}
		//获取客户编码
		String recCusCode = receivableNosFormList.get(0).getCustomerCode();
		
		//modify by 099995-foss-wujiangtao存放验证合格后的应收单信息，核销时需要传入完整的应收单信息，页面传过来的参数信息不够
		Map<String,BillReceivableEntity> recMap=new HashMap<String,BillReceivableEntity>();
		//modify by 099995-foss-wujiangtao存放验证合格后的应付单信息，核销时需要传入完整的应付单信息，页面传过来的参数信息不够
		Map<String,BillPayableEntity>payMap=new HashMap<String,BillPayableEntity>();
		
		//添加互斥锁收集数据
		List<MutexElement> mutexElements = new ArrayList<MutexElement>();

		// 判断界面选择的应收单集合数据是否和服务器端的一致，如果发生金额发生变化，返回界面提醒客户重新选择核销应收或者应付单据
		//如果应收单列表
		if (CollectionUtils.isNotEmpty(recQueryAllList)) {
			//循环处理选择的应收单列表
			for (BillReceivableEntity recEntityForm : receivableNosFormList) {
				//循环处理load的应收单列表
				for (BillReceivableEntity receivableEntity : recQueryAllList) {

					// 界面获取应收单的ID和后台或者应收单ID相同 并且客户编号相同
					if (receivableEntity.getId().equals(recEntityForm.getId())&& receivableEntity.getCustomerCode().equals(recEntityForm.getCustomerCode())) {
						
						//modify by 099995-foss-wujiangtao map添加数据
						recMap.put(receivableEntity.getReceivableNo(), receivableEntity);//modify by 099995-foss-wujiangtao

						// 应收单不能为红单
						if (receivableEntity.getIsRedBack().equals(FossConstants.YES)) {
							//提示应收单号:XXXX为红单，不能核销
							throw new SettlementException("应收单号:,"+ receivableEntity.getReceivableNo()+ "为红单，不能核销");
						}

						// 待核销的应收单未核销金额不能小于0
						if (receivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) != 1) {
							//提示应收单号:XXXX的未核销金额,不能小于或者等于0
							throw new SettlementException("应收单"+ receivableEntity.getReceivableNo()+ "的未核销金额,不能小于或者等于0！");
						}

						// 应收单未核销金额发生了变化
						if (!receivableEntity.getUnverifyAmount().equals(recEntityForm.getUnverifyAmount())) {
							//提示应收单号:XXXX应收单未核销金额发生变化，请重新选择后核销
							throw new SettlementException("应收单号："+ receivableEntity.getReceivableNo()+ "应收单未核销金额发生变化，请重新选择后核销！");
						}

						// 应收单是否被网上锁定
						if (null != receivableEntity.getUnlockDateTime()&&receivableEntity.getUnlockDateTime().after(new Date())) {
							//提示应收单号:XXXX被网上支付锁定,请重新选择
							throw new SettlementException("应收单号:"+ receivableEntity.getReceivableNo()+ "被网上支付锁定,请重新选择");
						}

						// 判断是否存在未受理的更改单据
						// 选中应收单存在未受理的更改单不能直接核销
						validaEntity(recEntityForm);
						
						// 如果应收单中存在运单号
						if (StringUtils.isNotEmpty(receivableEntity.getWaybillNo())) {
							// 业务互锁运单号
							MutexElement mutexElement = new MutexElement(receivableEntity.getWaybillNo(), "应收冲应付核销操作", MutexElementType.WAYBILL_NO);
							//加入互斥对象集合
							mutexElements.add(mutexElement);
						}
						
						// 剔除已界面和后台发生变化的应收单信息
						recQueryAllList.remove(receivableEntity);//
						break;
						
					}
				}
			}
		}

		/**
		 * 判断界面选择应付单集合是否和服务器端的一致，是否发生金额变化，如果有返回到界面
		 *  1、界面传入应付单号、客户编号和服务器是否保持一致
		 *  2、应付单的未核销金额，发生变化，不能核销
		 *  3、判断应付单是否为红单（前端可校验） 
		 *  4、待核销应收单和应付单，客户编码不能相同
		 *  5、选中应收单存在未受理的更改单不能直接核销 
		 *  6、剔除已删除的应付单信息 
		 *  7、 获取核销批次号 
		 *  8、调用应收应付服务
		 *  9、核销完成，通知修改对账单及对账单明细信息(调用对账信息)
		 */
		//如果应付单列表不为空
		if (CollectionUtils.isNotEmpty(payQueryAllList)) {
			//循环处理选择的应付单列表
			for (BillPayableEntity payNosFormEntity : payNosFormList) {
				//循环处理load的应付单列表
				for (BillPayableEntity billPayableEntity : payQueryAllList) {
					
					// 界面传入应付单号、客户编号和服务器是否保持一致
					if (billPayableEntity.getId().equals(payNosFormEntity.getId())&& billPayableEntity.getCustomerCode().equals(payNosFormEntity.getCustomerCode())) {
						
						//modify by 099995-foss-wujiangtao map添加数据
						payMap.put(billPayableEntity.getPayableNo(), billPayableEntity);//modify by 099995-foss-wujiangtao

						
						
						// 应付单的支付类别不是不限，且是否核销标志为否，不能核销
						if (StringUtils.isNotEmpty(billPayableEntity.getIsWriteoff())&&FossConstants.NO.equals(billPayableEntity.getIsWriteoff())) {
							throw new SettlementException("应付单："+ billPayableEntity.getPayableNo()+ "的支付类别为现金或电汇，无法核销");
						}
				
						// 应付单的未核销金额，发生变化，不能核销
						if (!billPayableEntity.getUnverifyAmount().equals(payNosFormEntity.getUnverifyAmount())) {
							//提示应付单号：XXXX未核销金额发生变化，请重新选择后核销！
							throw new SettlementException("应付单号："+ billPayableEntity.getPayableNo()+ "未核销金额发生变化，请重新选择后核销！");

						}
						// 判断应付单是否为红单（前端可校验）
						if (billPayableEntity.getIsRedBack().equals(FossConstants.YES)) {
							//提示应付单号XXXX是红单，不能核销
							throw new SettlementException("应付单号"+ payNosFormEntity.getPayableNo()+ "是红单，不能核销！");
						}
						// 应付单付款状态为否
//						if (!billPayableEntity.getPayStatus().equals(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO)) {
//							//提示应付单号XXXX已经付款，不能核销
//							throw new SettlementException("应付单号"+ payNosFormEntity.getPayableNo()+ "已经付款，不能核销！");
//						}
						// 待核销应收单和应付单，客户编码不能相同
						if (billPayableEntity.getCustomerCode().compareTo(recCusCode) != 0) {
							//提示应付单号XXXX待核销应收单和应付单，客户编码不能不相同
							throw new SettlementException("应付单"+ payNosFormEntity.getPayableNo()+ "待核销应收单和应付单，客户编码不能不相同！");
						}

						// 选中应收单存在未受理的更改单不能直接核销
						validaBillPayable(payNosFormEntity);
						
						//如果单据类型为理赔应付单，且理赔应付单的支付类别为：现金和电汇的不能参与核销
						if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(payNosFormEntity.getBillType())&&(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__CASH.equals(payNosFormEntity.getPaymentCategories())||SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER.equals(payNosFormEntity.getPaymentCategories()))){
							//提示应付单号XXXX支付类别为现金或电汇的理赔应付单不能参与核销
							throw new SettlementException(payNosFormEntity.getPayableNo()+"支付类别为现金或电汇的理赔应付单不能参与核销！");
						}
						
						// 如果应付单中存在运单号
						if (StringUtils.isNotEmpty(billPayableEntity.getWaybillNo())) {
							// 业务互锁运单号
							MutexElement mutexElement = new MutexElement(billPayableEntity.getWaybillNo(), "应收冲应付核销操作", MutexElementType.WAYBILL_NO);
							//加入互斥对象集合
							mutexElements.add(mutexElement);
						}
						// 剔除已删除的应付单信息
						payQueryAllList.remove(billPayableEntity);
						break;
					}
				}
			}
		}
		
		//添加互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//锁定
			businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH);
		}
		
		
		// 获取核销批次号
		String writeoffBillBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		
		// 调用common公共的核销的应收冲应付service
		//生成核销操作dto
		BillWriteoffOperationDto billWriteoffDto = new BillWriteoffOperationDto();
		//设置核销操作dto的应付单参数
		billWriteoffDto.setBillPayableEntitys(payNosFormList);
		
		
		
		//modify by 099995-foss-wujiangtao
		List<BillReceivableEntity> recList=new ArrayList<BillReceivableEntity>();
		//如果map不为空
		if(!recMap.isEmpty()){
			//遍历map
			Iterator<String> iter=recMap.keySet().iterator();
			while(iter.hasNext()){
				//遍历数据加入list
				recList.add(recMap.get(iter.next()));
			}
		}
		
		//modify by 099995-foss-wujiangtao
		List<BillPayableEntity> paylist=new ArrayList<BillPayableEntity>();
		//如果map不为空
		if(!payMap.isEmpty()){
			//遍历map
			Iterator<String> iter=payMap.keySet().iterator();
			while(iter.hasNext()){
				//遍历数据加入list
				paylist.add(payMap.get(iter.next()));
			}
		}
		
		//当待核销应收、应付单列均不为空才执行赋值并核销
		if(!CollectionUtils.isEmpty(recList)&&!CollectionUtils.isEmpty(paylist)){
			//这里的应收单集合，用后台查询出来的数据，不能用前台的数据
			billWriteoffDto.setBillReceivableEntitys(recList);//modify by 099995-foss-wujiangtao
			//这里的应付单集合，用后台查询出来的数据，不能用前台的数据
			billWriteoffDto.setBillPayableEntitys(paylist);
		
		//应收单为空，应付单不为空	
		}else if(CollectionUtils.isEmpty(recList)&&!CollectionUtils.isEmpty(paylist)){
			//提示已核销或确认对账的应收单不能进行核销操作,请重新选择核销
			throw new SettlementException("已核销、确认对账单或存在更改单的应收单不能进行核销操作,请重新选择核销!");
		
		//应付单为空，应收单不为空	
		}else if(!CollectionUtils.isEmpty(recList)&&CollectionUtils.isEmpty(paylist)){
			//提示已核销或确认对账的应付单不能进行核销操作,请重新选择核销
			throw new SettlementException("已核销、确认对账单或存在更改单的应付单不能进行核销操作,请重新选择核销!");
		//应收、应付单均为空
		}else{
			//提示已核销或确认对账的应付单不能进行核销操作,请重新选择核销
			throw new SettlementException("已核销、确认对账单或存在更改单的应收应付单不能进行核销操作,请重新选择核销");
		}
		
		
		
		//设置核销操作dto的核销批次号
		billWriteoffDto.setWriteoffBatchNo(writeoffBillBatchNo);
		//设置核销操作dto的生成方式
		billWriteoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 生成核销操作返回dto
		BillWriteoffOperationDto billWriteoffOperationDtoResult;
		// 调用应收应付服务
		billWriteoffOperationDtoResult = billWriteoffService.writeoffReceibableAndPayable(billWriteoffDto, cInfo);

		// 添加核销完成的应收单的实体
		recQueryAllList.addAll(billWriteoffOperationDtoResult.getBillReceivableEntitys());

		// 添加核销完成的应付单的实体
		payQueryAllList.addAll(billWriteoffOperationDtoResult.getBillPayableEntitys());

		// 核销完成，通知修改对账单及对账单明细信息(调用对账信息)
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//设置对账单修改dto核销应收单列表参数
		statementOfAccountUpdateDto.setReceivableEntityList(billWriteoffOperationDtoResult.getBillReceivableEntitys());
		//设置对账单修改dto核销应付单列表参数
		statementOfAccountUpdateDto.setPayableEntityList(billWriteoffOperationDtoResult.getBillPayableEntitys());
		//修改对账单及对账单明细信息
		statementOfAccountService.updateStatementAndDetailForWriteOff(statementOfAccountUpdateDto, cInfo);

		// 声明应收单的总金额
		BigDecimal recTotalAmount = BigDecimal.ZERO;
		// 声明应收单的未核销总金额
		BigDecimal recUnverifyTotalAmount = BigDecimal.ZERO;
		// 声明应收单的已核销总金额
		BigDecimal recVerifyTotalAmount = BigDecimal.ZERO;
		// 声明应付单的总金额
		BigDecimal payTotalAmount = BigDecimal.ZERO;
		// 声明应付单的未核销总金额
		BigDecimal payUnverifyTotalAmount = BigDecimal.ZERO;
		// 声明应付单的已核销总金额
		BigDecimal payVerifyTotalAmount = BigDecimal.ZERO;

		// 计算应收单、应付单列表的总条数、总金额、未核销总金额、已核销总金额
		//循环处理应收单列表
		for (BillReceivableEntity entity : recQueryAllList) {
			//总金额
			recTotalAmount = recTotalAmount.add(entity.getAmount());
			//未核销总金额
			recUnverifyTotalAmount = recUnverifyTotalAmount.add(entity.getUnverifyAmount());
			//已核销总金额
			recVerifyTotalAmount = recVerifyTotalAmount.add(entity.getVerifyAmount());
		}
		//循环处理应付单列表
		for (BillPayableEntity entity : payQueryAllList) {
			//总金额
			payTotalAmount = payTotalAmount.add(entity.getAmount());
			//未核销总金额
			payUnverifyTotalAmount = payUnverifyTotalAmount.add(entity.getUnverifyAmount());
			//已核销总金额
			payVerifyTotalAmount = payVerifyTotalAmount.add(entity.getVerifyAmount());
		}

		// 生成应收冲应付dto
		BillRecWriteoffBillPayResultDto billRecPayResultDto = new BillRecWriteoffBillPayResultDto();
		//设置应收冲应付dto应付单列表
		billRecPayResultDto.setBillPayableEntityList(payQueryAllList);
		//设置应收冲应付dto应收单列表
		billRecPayResultDto.setBillReceivableEntityList(recQueryAllList);

		// 应收单总金额 、总条数、总核销金额、总未核销金额
		//设置应收冲应付dto应收条数
		billRecPayResultDto.setRecTotalNum(recQueryAllList.size());
		//设置应收冲应付dto应收总金额
		billRecPayResultDto.setRecTotalAmount(recTotalAmount);
		//设置应收冲应付dto应收未核销总金额
		billRecPayResultDto.setRecUnverifyTotalAmount(recUnverifyTotalAmount);
		//设置应收冲应付dto应收核销总金额
		billRecPayResultDto.setRecVerifyTotalAmount(recVerifyTotalAmount);

		// 应付单总金额 、总条数、总核销金额、总未核销金额
		//设置应收冲应付dto应付总条数
		billRecPayResultDto.setPayTotalNum(payQueryAllList.size());
		//设置应收冲应付dto应付总金额
		billRecPayResultDto.setPayUnverifyTotalAmount(payUnverifyTotalAmount);
		//设置应收冲应付dto应付未核销总金额
		billRecPayResultDto.setPayVerifyTotalAmount(payVerifyTotalAmount);
		//设置应收冲应付dto应付核销总金额
		billRecPayResultDto.setPayTotalAmount(payTotalAmount);

		//去除互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//解锁
			businessLockService.unlock(mutexElements);
		}
		
		//start 269044-zhurongrong 2016-05-09
		//发生核销时，先判断该客户是否在灰名单中，在的话需判断是否拉出来，不在的话，直接pass
		GrayCustomerEntity entity = grayCustomerService.queryGrayCustomerByCustomerCode(recCusCode);
		//存放待处理客户编码集合
		List<String> customerCodeList = new ArrayList<String>();
		//将客户编码添加到集合中
		customerCodeList.add(recCusCode);
		if(entity!=null) {
			try{
				//调用判断时候修改灰名单接口
				grayCustomerService.updateGrayCustomerToECS(customerCodeList);
			} catch (Exception e) {
				//打印异常
				LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
			}
		}
		//end
		// 调用通用的应收冲应付核销接口
		return billRecPayResultDto;
	}



	private void validaEntity(BillReceivableEntity recEntityForm) {
		if (StringUtils.isNotEmpty(recEntityForm.getWaybillNo())) {
			//判断运单号是否存在未受理的更改单
			boolean isExsitsWayBillRfc = waybillRfcService.isExsitsWayBillRfc(recEntityForm.getWaybillNo());

			// 存在的话返回异常提示
			if (isExsitsWayBillRfc) {
				//提示存在未受理更改单的应收单: XXXX不能进行核销核销
				throw new SettlementException("存在未受理更改单的应收单: "+ recEntityForm.getReceivableNo()+ " 不能进行核销核销!");
			}
		}
	}



	private void validaBillPayable(BillPayableEntity payNosFormEntity) {
		if (StringUtils.isNotEmpty(payNosFormEntity.getWaybillNo())) {
			//检测应付单对应运单是否存在未受理的更改单
			boolean isExsitsWayBillRfc = waybillRfcService.isExsitsWayBillRfc(payNosFormEntity.getWaybillNo());

			// 存在的话返回异常提示
			if (isExsitsWayBillRfc) {
				//提示存在未受理更改单的应收单:XXXX不能进行核销核销
				throw new SettlementException("存在未受理更改单的应收单: "+ payNosFormEntity.getPayableNo()+ " 不能进行核销核销!");
			}
		}
	}

	
	
	
	

	/**
	 * 查询应收单设置输入条件
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午12:46:54
	 */
	public List<BillReceivableEntity> queryBillRecList(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,CurrentInfo cInfo) {

		//查询dto不能为空
		if (billRecWriteoffBillPayDto == null) {
			//提示内部错误，查询应的应收单为空
			throw new SettlementException("内部错误，查询应的应收单为空!");
		}

		// 设置可用于核销的应收单的状态：有效
		billRecWriteoffBillPayDto.setRecActive(FossConstants.YES);
		// 设置可用于核销的应收单的状态：非红单
		billRecWriteoffBillPayDto.setRecIsRedBack(FossConstants.NO);
		//应收单审核状态必须为已经审核
		billRecWriteoffBillPayDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);
		// 应收单签收状态为“是”
		// 应收单的退款状态为未退款
		billRecWriteoffBillPayDto.setRecRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND);
		// 应收单的状态
		List<String> statusList = new ArrayList<String>();
		// 应收单的状态：已提交
		statusList.add(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		// 应收单的状态：已确认
		statusList.add(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__CONFIRM);
		//设置应收单状态查询参数
		billRecWriteoffBillPayDto.setRecStatusList(statusList);

		// 设置可用于核销应收单
		List<String> billTypeList = new ArrayList<String>();
		//应收单单据子类型：代收货款应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		// TODO 合伙人应收单不能为某些应收单:
		//始发提成应收单（H）、委托派费应收单（H）、到付运费应收单（H）、代收货款应收单（H）不允许在应收冲应付界面查询出来；
		//始发提成应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE);
		//委托派费应收单(H)
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE );
		//到付运费应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);
		//设置应收单单据子类型
		billRecWriteoffBillPayDto.setBillRecTypeList(billTypeList);
		
		
		//应收单必须为：到达偏线代理应收单
		List<String> recBillTypeList=new ArrayList<String>();
		//应收单类型：到达偏线代理应收单
//		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);
		//应收单类型： 到达应收
		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		
		//家裝應收
		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__HOME_IMPROVEMENT);
		
		//应收单类型：空运到达代理应收
//		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		//设置应收单类型参数
		billRecWriteoffBillPayDto.setRecBillTypeList(recBillTypeList);
		
		//如果应收单已锁，设置解锁时间小于当前时间
	    billRecWriteoffBillPayDto.setNowTime(new Date());

		// 获取当前登录部门编码
		billRecWriteoffBillPayDto.setGeneratingOrgCode(cInfo.getCurrentDeptCode());
		// 获取当前登录部门名称
		billRecWriteoffBillPayDto.setGeneratingOrgName(cInfo.getCurrentDeptName());

		// 应收单列表
		List<BillReceivableEntity> queryRecList = billWriteoffBillRecQueryDao.queryReceivableList(billRecWriteoffBillPayDto);
		
		// 应收单的对账单
		// 1、调用对账单接口过滤预收单，根据单号过滤掉已经生成对账单的应收单
		// 1.1 、获取对账单号列表/更改单
		
		List<String> statementBillNoList = null;
		List<String> rfcBillNoList = null;
		
		//生成待处理应收单列表
		Collection<BillReceivableEntity> toDoList=new ArrayList<BillReceivableEntity>();
		//如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(queryRecList)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//生成运单号列表
			rfcBillNoList = new ArrayList<String>();
			
			// 循环处理应收单列表
			for (BillReceivableEntity entity : queryRecList) {
				//如果对账单号存在
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//将对账单号加入对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
				//添加应收单对应的运单号
				if (entity.getWaybillNo() != null) {
					rfcBillNoList.add(entity.getWaybillNo());	
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		// add调用更改单服务接口，返回存在更改单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单号是否已确认，并返回已确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}
		
		if(CollectionUtils.isNotEmpty(rfcBillNoList)){
			// 返回存在更改单的单号
			rfcBillNoList = waybillRfcService.isExsitsWayBillRfcs(rfcBillNoList);
		}

		// 1.3、从应收单列表中除去已经确认对账单的数据
		// add除去变更的单号数据
		//如果已确认对账单号不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//循环处理对账单号
			for (String statementBillNo : statementBillNoList) {
				//循环处理应收单
				for (BillReceivableEntity entity : queryRecList) {
					//对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						//将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(rfcBillNoList)) {
			//循环处理更改单单号
			for (String rfcBillNo : rfcBillNoList) {
				//循环应收单
				for (BillReceivableEntity entity : queryRecList) {
					//单号相等
					if(rfcBillNo.equals(entity.getWaybillNo())) {
						//将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
		
		//如果待处理应收单列表不为空
		if(CollectionUtils.isNotEmpty(toDoList)){
			//从应收单列表中删除待处理应收单列表中相同的数据
			queryRecList.removeAll(toDoList);
			//清空待处理应收单列表
			toDoList.clear();
		}
		
		//返回处理后的应收单列表
		return queryRecList;
	}
	
	
	

	/**
	 * 对应付单进行初始化,并返回查询结果
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午1:25:48
	 */
	private List<BillPayableEntity> queryBillPayList(BillRecWriteoffBillPayDto billRecWriteOffPayDto, CurrentInfo cInfo) {

		//查询dto不能为空
		if (billRecWriteOffPayDto == null) {
			//提示内部错误，查询应付单DTO为空
			throw new SettlementException("内部错误，查询应付单DTO为空!");
		}

		// 应付单审核状态为已审核
		billRecWriteOffPayDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE); 
		// 应付单审核状态为有效
		billRecWriteOffPayDto.setPayActive(FossConstants.YES);
		// 应付单审核状态为非红单
		billRecWriteOffPayDto.setPayIsRedBack(FossConstants.NO);
		
		//应付单的单据类型1.不能为装卸费和服务补救费
		List<String> billPayTypeList = new ArrayList<String>();
		//应付单的单据类型:装卸费
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		//应付单的单据类型:服务补救
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
		// TODO 不能为合伙人某些应付单:到达提成应付单、委托到达提成应付单、到付运费应付单，不允许在应收冲应付界面查询出来；
		// 合伙人到达提成应付单
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE);
		// 合伙人到付运费应付单
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE);
		// 合伙人委托派费应付单
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE);
		//设置应付单类型参数
		billRecWriteOffPayDto.setBillPayTypeList(billPayTypeList);

		//应付单支付状态(未支付、当应付单已支付 付款单对应汇款状态为已支付)和应付单付款状态等于已支付，付款单对应汇款状态为已支付
		//应付单支付状态：未支付
		billRecWriteOffPayDto.setPayStatusNo(FossConstants.NO);
		//应付单支付状态：已支付
		billRecWriteOffPayDto.setPayStatusYes(FossConstants.YES);
		//设置应付单汇款状态：已汇款
		billRecWriteOffPayDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);
		
		// 获取当前登录部门编码
		billRecWriteOffPayDto.setGeneratingOrgCode(cInfo.getCurrentDeptCode());
		// 获取当前登录部门名称
		billRecWriteOffPayDto.setGeneratingOrgName(cInfo.getCurrentDeptName());

		
		
		//当为应付单为代收货款类型时，代收货款类型必须为审核退，并应付单已生效
		List<String> payableTypeList=new ArrayList<String>();
		//应付单类型：应付代收货款
		payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		//设置应付单的类型参数
		billRecWriteOffPayDto.setPayableBillTypeList(payableTypeList);
		//应付单的生效状态已生效
		billRecWriteOffPayDto.setEffectiveStatus(FossConstants.YES);
		
		/* 设置代收货款需要用到的状态
		 * 
		 * 杨书硕 2013-7-31 上午9:23:42
		 */
		//代收货款已退款
		billRecWriteOffPayDto.setCodStatusRD(SettlementDictionaryConstants.COD__STATUS__RETURNED);
		//代收货款资金部冻结状态
		billRecWriteOffPayDto.setBillFrozenStaFrozen(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN);
		//设置应付单、代收货款类型必须为审核退审核退
		List<String> codTypeList=new ArrayList<String>();
		//应付单的代收货款类型： 审核退
		codTypeList.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE);
		//设置应付单的代收货款类型
		billRecWriteOffPayDto.setCodTypeList(codTypeList);
		
		/***
		 * 应付单核销时，增加校验：如果为理赔应付单，应付单的支付类别为：现金和电汇的，不允许参与核销		
		 */
		//应付单为理赔类型
		billRecWriteOffPayDto.setClaimsPaybleType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		
		//应付单支付类型为现金和电汇
		List<String> paymentTypeList=new ArrayList<String>();
		//应付单支付类型:现金
		paymentTypeList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		//应付单支付类型:电汇
		paymentTypeList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
		//设置应付单的支付类型
		billRecWriteOffPayDto.setPaymentTypeList(paymentTypeList);
		
		//未冻结状态应付单的应付单才能核销
		billRecWriteOffPayDto.setNoFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		
		//查询应付单
		List<BillPayableEntity> queryPayList = billWriteoffBillPayQueryDao.queryPayableList(billRecWriteOffPayDto);

		// 应付单的对账单
		// 1、调用对账单接口过滤预收单，根据单号过滤掉已经生成对账单的应付单
		// 1.1 、获取对账单号列表
		// add更改过的运单号集合
		List<String> statementBillNoList = null;
		
		List<String> rfcBillNoList = new ArrayList<String>();
		
		//生成待处理应付单列表
		Collection<BillPayableEntity> toDoList=new ArrayList<BillPayableEntity>(); 
		//如果应付单不为空
		if (CollectionUtils.isNotEmpty(queryPayList)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//运单号列表
			rfcBillNoList = new ArrayList<String>();
			//循环处理应付单列表
			for (BillPayableEntity entity : queryPayList) {
				//如果对账单号存在
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//加入到对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
				//如果运单号不为空则加入集合
				if (entity.getWaybillNo() != null) {
					rfcBillNoList.add(entity.getWaybillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		// add 调用更改单服务接口，返回存在更改单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单是否已确认，并返回已确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}
		
		if(CollectionUtils.isNotEmpty(rfcBillNoList)){
			// 返回存在更改单的单号
			rfcBillNoList = waybillRfcService.isExsitsWayBillRfcs(rfcBillNoList);
		}

		// 1.3、从应付单列表中除去已经确认对账单的数
		// add除去更改状态的
		//如果已确认的对账单号不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			// 循环处理已确认对账单号列表
			for (String statementBillNo : statementBillNoList) {
				// 循环处理应收单列表
				for (BillPayableEntity entity : queryPayList) {
					// 如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						// 将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(rfcBillNoList)) {
			// 循环处理更改单号列表
			for (String rfcBillNo : rfcBillNoList) {
				// 循环处理应收单列表
				for (BillPayableEntity entity : queryPayList) {
					// 如果单号相等
					if (rfcBillNo.equals(entity.getWaybillNo())) {
						toDoList.add(entity);
					}
				}
			}
		}
		//如果待处理应收单列表不为空
		if(CollectionUtils.isNotEmpty(toDoList)){
			//从应收单列表中删除待处理应收单列表相同的数据
			queryPayList.removeAll(toDoList);
			//清空待处理应收单列表
			toDoList.clear();
		}
		//返回处理后的应收单列表
		return queryPayList;
	}

	/**
	 * 按核销单查询应收单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-25 下午3:14:04
	 * @param billRecWriteoffBillPayDto
	 * @param cInfo
	 * @return
	 */
	private List<BillReceivableEntity> queryRecForNs(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,CurrentInfo cInfo) {

		//查询dto不能为空
		if (billRecWriteoffBillPayDto == null) {
			//提示内部错误，来源核销单为空
			throw new SettlementException("内部错误，来源核销单为空!");
		}

		// 设置可用于核销的应收单的状态：有效
		billRecWriteoffBillPayDto.setRecActive(FossConstants.YES);
		// 设置可用于核销的应收单的状态：非红单
		billRecWriteoffBillPayDto.setRecIsRedBack(FossConstants.NO);
		//应收单未已审核状态
		billRecWriteoffBillPayDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);	
		// 应收单签收状态为“是”
		// 应收单的退款状态为未退款
		billRecWriteoffBillPayDto.setRecRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND);
		// 应收单的状态
		List<String> statusList = new ArrayList<String>();
		//已提交
		statusList.add(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		//已确认
		statusList.add(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__CONFIRM);
		//设置状态
		billRecWriteoffBillPayDto.setRecStatusList(statusList);
		// 获取当前登录部门和名称
		billRecWriteoffBillPayDto.setGeneratingOrgCode(cInfo.getCurrentDeptCode());
		// 获取当前登录部门和名称
		billRecWriteoffBillPayDto.setGeneratingOrgName(cInfo.getCurrentDeptName());
		// 设置可用于核销应收单，应收单单据子类型不为：代收货款
		List<String> billTypeList = new ArrayList<String>();
		//代收货款
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		//TODO 合伙人应收单不能是 某些类型
		//始发提成应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE);
		//委托派费应收单(H)
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE );
		//到付运费应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);
		//设置状态
		billRecWriteoffBillPayDto.setBillRecTypeList(billTypeList);
		
		//应收单必须为到达应收（到达偏线应收、到达应收、空运到达应收），必须已签收
		List<String>recBillTypeList=new ArrayList<String>();
		//应收单必须为到达应收（到达偏线应收、到达应收、空运到达应收），必须已签收
//		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);
		//应收单必须为到达应收（到达偏线应收、到达应收、空运到达应收），必须已签收
		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		//应收单必须为到达应收（到达偏线应收、到达应收、空运到达应收），必须已签收
//		recBillTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		//设置状态
		billRecWriteoffBillPayDto.setRecBillTypeList(recBillTypeList);
		
		//如果应收单已锁，设置解锁时间小于当前时间
		billRecWriteoffBillPayDto.setNowTime(new Date());
		

		// 界面传入应收单或者运单号集合，如果不为应收单号或者运单号不为空进行分割
		if (StringUtils.isNotEmpty(billRecWriteoffBillPayDto.getReceivableNosOrWaybillNos())) {
			//获取单号，并分割
			String[] numbers = billRecWriteoffBillPayDto.getReceivableNosOrWaybillNos().split(",");

			// 界面传入的应收单号、运单号或者其他类票据进行分割，如果存在运单放入运单集合、如果存在应收单放入应收单集合
			Map<String, List<String>> resultMap = SettlementUtil.convertBillNos(numbers);

			// 判断map中是否有应收单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YS))) {
				//设置应收单类型
				billRecWriteoffBillPayDto.setReceivableNos(resultMap.get(SettlementConstants.BILL_PREFIX_YS));
			}

			// 判断map中是否有运单号或小票单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_LY))) {
				//设置来源单号类型
				billRecWriteoffBillPayDto.setSourceBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_LY));
			}
		}

		// 如果用户输入的有应收单和运单或者小票单据，返回查询所有的
		List<BillReceivableEntity> queryRecWaybillAll = new ArrayList<BillReceivableEntity>();
		// 如果用户输入的有应收单和运单或者小票单据，返回查询所有的
		List<BillReceivableEntity> queryReceivableNos = new ArrayList<BillReceivableEntity>();
		//应收单号不为空
		if (CollectionUtils.isNotEmpty(billRecWriteoffBillPayDto.getReceivableNos())) {
			//按应收单号查询
			queryReceivableNos = billWriteoffBillRecQueryDao.queryReceivableNos(billRecWriteoffBillPayDto);
		}
		//来源单号不为空
		if (CollectionUtils.isNotEmpty(billRecWriteoffBillPayDto.getSourceBillNosList())) {
			//按来源单号查询
			queryRecWaybillAll = billWriteoffBillRecQueryDao.queryRecByWayBill(billRecWriteoffBillPayDto);
		}
		// 去除重复的记录
		@SuppressWarnings("unchecked")
		List<BillReceivableEntity> queryRecResultList = (List<BillReceivableEntity>) CollectionUtils.union(queryRecWaybillAll, queryReceivableNos);
		
		// 应收单的对账单
		// 1、调用对账单接口过滤预收单，根据单号过滤掉已经生成对账单的应收单
		// 1.1 、获取对账单号列表
		// add获取更改单号列表
		List<String> statementBillNoList = null;
		List<String> rfcBillNoList = null;
		
		// 生成待处理应收单列表
		Collection<BillReceivableEntity> toDoList = new ArrayList<BillReceivableEntity>();
		// 如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(queryRecResultList)) {
			// 生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			// 更改单号列表
			rfcBillNoList = new ArrayList<String>();
			
			// 循环处理应收单列表
			for (BillReceivableEntity entity : queryRecResultList) {
				// 如果对账单号存在
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					// 将对账单号加入对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
				
				// 如果运单号不为空
				if (entity.getWaybillNo() != null) {
					rfcBillNoList.add(entity.getWaybillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		// add调用更改服务接口，返回存在更改单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			// 检测对账单号是否已确认，并返回已确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}
		
		if(CollectionUtils.isNotEmpty(rfcBillNoList)){
			// 返回存在更改单的单号
			rfcBillNoList = waybillRfcService.isExsitsWayBillRfcs(rfcBillNoList);
		}

		// 1.3、从应收单列表中除去已经确认对账单的数据
		// 如果已确认对账单号不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			// 循环处理对账单号
			for (String statementBillNo : statementBillNoList) {
				// 循环处理应收单
				for (BillReceivableEntity entity : queryRecResultList) {
					// 对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						// 将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
		// add从应收单列表中除去存在更改单的数据
		if (CollectionUtils.isNotEmpty(rfcBillNoList)) {
			//循环更改单列表
			for (String rfcBillNo :rfcBillNoList) {
				//循环处理应收单
				for (BillReceivableEntity entity : queryRecResultList) {
					// 运单号相等
					if (rfcBillNo.equals(entity.getWaybillNo())) {
						// 将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
		
		// 如果待处理应收单列表不为空
		if (CollectionUtils.isNotEmpty(toDoList)) {
			// 从应收单列表中删除待处理应收单列表中相同的数据
			queryRecResultList.removeAll(toDoList);
			// 清空待处理应收单列表
			toDoList.clear();
		}
		// 返回处理后的应收单列表
		return queryRecResultList;
	}
	
	
	

	/**
	 * 初始化应付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-25 下午3:10:38
	 * @param billRecWriteoffBillPayDto
	 * @param cInfo
	 * @return
	 */
	private List<BillPayableEntity> queryPayForNs(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,CurrentInfo cInfo) {

		//查询dto不能为空
		if (billRecWriteoffBillPayDto == null) {
			//提示内部错误，核销应收应付单Dto为空
			throw new SettlementException("内部错误，核销应收应付单Dto为空！");
		}

		// 审核状态为已审核
		billRecWriteoffBillPayDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 应付单有效
		billRecWriteoffBillPayDto.setPayActive(FossConstants.YES);
		// 应付单非红单
		billRecWriteoffBillPayDto.setPayIsRedBack(FossConstants.NO);
		// 应付单类型
		List<String> billPayTypeList = new ArrayList<String>();

		// 应付单不能为装卸费
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		//应付单不能为服务补救
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
		// TODO 不能为合伙人某些应付单:到达提成应付单、委托到达提成应付单、到付运费应付单，不允许在应收冲应付界面查询出来；
		// 合伙人到达提成应付单
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE);
		// 合伙人到付运费应付单
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE);
		// 合伙人委托派费应付单
		billPayTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE);
		//应付单类型
		billRecWriteoffBillPayDto.setBillPayTypeList(billPayTypeList);

		// 获取当前登录部门编码
		billRecWriteoffBillPayDto.setGeneratingOrgCode(cInfo.getCurrentDeptCode());
		// 获取当前登录部门名称
		billRecWriteoffBillPayDto.setGeneratingOrgName(cInfo.getCurrentDeptName());

		// 应付单支付状态、应付单付款状态等于已支付，付款单对应汇款状态为已支付
		billRecWriteoffBillPayDto.setPayStatusNo(FossConstants.NO);
		// 应付单支付状态、应付单付款状态等于已支付，付款单对应汇款状态为已支付
		billRecWriteoffBillPayDto.setPayStatusYes(FossConstants.YES);
		// 应付单支付状态、应付单付款状态等于已支付，付款单对应汇款状态为已支付
		billRecWriteoffBillPayDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);

		// 当为应付单为代收货款类型时，代收货款类型必须为审核退，并应付单已生效
		List<String> payableTypeList = new ArrayList<String>();
		//应付单类型：代收货款
		payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		//设置应付单类型
		billRecWriteoffBillPayDto.setPayableBillTypeList(payableTypeList);
		//设置生效状态
		billRecWriteoffBillPayDto.setEffectiveStatus(FossConstants.YES);

		/* 设置代收货款需要用到的状态
		 * 
		 * 杨书硕 2013-7-31 上午9:23:42
		 */
		//代收货款已退款
		billRecWriteoffBillPayDto.setCodStatusRD(SettlementDictionaryConstants.COD__STATUS__RETURNED);
		//代收货款资金部冻结状态
		billRecWriteoffBillPayDto.setBillFrozenStaFrozen(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN);
		// 设置应付单、代收货款类型必须为审核退审核退
		List<String> codTypeList = new ArrayList<String>();
		//应付单代收货款类型：审核退
		codTypeList.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE);
		//设置代收货款类型
		billRecWriteoffBillPayDto.setCodTypeList(codTypeList);
		
		/***
		 * 应付单核销时，增加校验：如果为理赔应付单，应付单的支付类别为：现金和电汇的，不允许参与核销		
		 */
		//应付单为理赔类型
		billRecWriteoffBillPayDto.setClaimsPaybleType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		
		//应付单支付类型为现金和电汇
		List<String> paymentTypeList=new ArrayList<String>();
		//应付单支付类型为现金
		paymentTypeList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		//应付单支付类型为电汇
		paymentTypeList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
		//设置应付单支付类型参数
		billRecWriteoffBillPayDto.setPaymentTypeList(paymentTypeList);
			
		//未冻结状态应付单的应付单才能核销
		billRecWriteoffBillPayDto.setNoFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

		// 获取应付单号或者运单号并分割处理
		if (StringUtils.isNotEmpty(billRecWriteoffBillPayDto.getPayNosOrWaybillNos())) {
			//获取单号并分割
			String[] numbers = billRecWriteoffBillPayDto.getPayNosOrWaybillNos().split(",");
			// 调用公共的类，进行判断
			// 根据应付单号长度或者运单号判断是应付单还是运单
			Map<String, List<String>> resultMap = SettlementUtil.convertBillNos(numbers);

			// 判断map中是否有应付单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YF))) {
				//应付单类型
				billRecWriteoffBillPayDto.setPayableNos(resultMap.get(SettlementConstants.BILL_PREFIX_YF));
			}

			// 判断map中是否有运单号或小票单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_LY))) {
				//来源单号类型
				billRecWriteoffBillPayDto.setSourceBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_LY));
			}
		}

		// 如果应付单不为空，根据应付单查询
		List<BillPayableEntity> queryPayWayBillAll = new ArrayList<BillPayableEntity>();
		// 如果应付单不为空，根据应付单查询
		List<BillPayableEntity> queryPayableNos = new ArrayList<BillPayableEntity>();

		//应付单号不为空
		if (CollectionUtils.isNotEmpty(billRecWriteoffBillPayDto.getPayableNos())) {
			//按应付单号查询
			queryPayableNos = billWriteoffBillPayQueryDao.queryPayableNos(billRecWriteoffBillPayDto);
		}
		// 如果运单不为空
		if (CollectionUtils.isNotEmpty(billRecWriteoffBillPayDto.getSourceBillNosList())) {
			//根据运单或者其他票据查询
			queryPayWayBillAll = billWriteoffBillPayQueryDao.queryPayByWayBill(billRecWriteoffBillPayDto);
		}

		// 去除重复的 记录
		@SuppressWarnings("unchecked")
		List<BillPayableEntity> queryPayResultList = (List<BillPayableEntity>) CollectionUtils.union(queryPayWayBillAll, queryPayableNos);
		
		// 应付单的对账单
		// 1、调用对账单接口过滤预收单，根据单号过滤掉已经生成对账单的应付单
		// 1.1 、获取对账单号列表
		// add获取变更单号列表
		List<String> statementBillNoList = null;
		List<String> rfcBillNoList = null;
		
		// 生成待处理应付单列表
		Collection<BillPayableEntity> toDoList = new ArrayList<BillPayableEntity>();
		// 如果应付单不为空
		if (CollectionUtils.isNotEmpty(queryPayResultList)) {
			// 生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			// 生成运单号列表
			rfcBillNoList = new ArrayList<String>();
			
			// 循环处理应付单列表
			for (BillPayableEntity entity : queryPayResultList) {
				// 如果对账单号存在
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					// 加入到对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
				// 如果运单号不为空
				if (entity.getWaybillNo() != null) {
					rfcBillNoList.add(entity.getWaybillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		// add调用更改单服务接口，返回存在更改单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			// 检测对账单是否已确认，并返回已确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}
		
		if(CollectionUtils.isNotEmpty(rfcBillNoList)){
			// 返回存在更改单的单号
			rfcBillNoList = waybillRfcService.isExsitsWayBillRfcs(rfcBillNoList);
		}

		// 1.3、从应付单列表中除去已经确认对账单的数据
		// 如果已确认的对账单号不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			// 循环处理已确认对账单号列表
			for (String statementBillNo : statementBillNoList) {
				// 循环处理应收单列表
				for (BillPayableEntity entity : queryPayResultList) {
					// 如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						// 将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
		// add从应付单列表中除去已经确认对账单的数据
		if (CollectionUtils.isNotEmpty(rfcBillNoList)) {
			// 循环变更单列表
			for(String rfcBillNo : rfcBillNoList) {
				// 循环应收单列表
				for (BillPayableEntity entity : queryPayResultList) {
					// 如果运单号相同
					if(rfcBillNo.equals(entity.getWaybillNo())) {
						// 将应收单加入到待处理应收单列表
						toDoList.add(entity);
					}
				}
			}
		}
			
		// 如果待处理应收单列表不为空
		if (CollectionUtils.isNotEmpty(toDoList)) {
			// 从应收单列表中删除待处理应收单列表相同的数据
			queryPayResultList.removeAll(toDoList);
			// 清空待处理应收单列表
			toDoList.clear();
		}
		// 返回处理后的应收单列表
		return queryPayResultList;
	}



	/**
	 * 导出应收单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午4:59:51
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService#exportWriteoffBillRec(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public HSSFWorkbook exportWriteoffBillRec(BillRecWriteoffBillPayDto billRecPayDto, CurrentInfo cInfo) {
		
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if ( null== billRecPayDto|| null==billRecPayDto.getArrayColumnNames()|| billRecPayDto.getArrayColumnNames().length == 0) {
			//提示导出Excel的列头名称不存在，请至少存在一列
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		
		// 调用执行方法，获取结果集
		List <BillReceivableEntity> queryRecList = queryExportWriteoffBillRec(billRecPayDto,cInfo);
		
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (queryRecList == null ||queryRecList.size() == 0) {
			//提示没有要导出的数据
			throw new SettlementException("没有要导出的数据!");
		}
		
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormRecEntity(queryRecList, billRecPayDto.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(billRecPayDto.getArrayColumnNames());
		//设置导出列数据
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		return export.exportExcel(data, "sheet",SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
	}

	
	
	/**
	 * 应收单list的实体转化成list<list<String>
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-21 下午12:49:19
	 */
	private List<List<String>> convertListFormRecEntity(List<BillReceivableEntity> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);
		termCodes.add(DictionaryConstants.FOSS_ACTIVE);
		termCodes.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);
		
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		// 循环进行封装
		for (BillReceivableEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			//循环处理
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(BillReceivableEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					//生成object
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日前格式化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						// 如果为单据子类型，则需要转化
						if (columnName.equals("billType")) {
							// 核销状态
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE, fieldValue.toString());
						}else if(columnName.equals("productCode")){
							fieldValue = productService.getProductByCache(fieldValue.toString(),new Date()).getName();
						}
						//是否有效不为空
						if (columnName.equals("active")) {
							// 是否有效
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.FOSS_ACTIVE, fieldValue.toString());
						}
						//是否红单不为空
						if (columnName.equals("isRedBack")) {
							// 是否红单
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__IS_RED_BACK, fieldValue.toString());
						}
						//加入的列数据中
						rowList.add(fieldValue.toString());
					} else {
						//加入null到列数据中
						rowList.add(null);
					}
				}
			}
			//列数据加入到excel表单中
			sheetList.add(rowList);
		}
		//返回excel表单
		return sheetList;
	}
	
	/**
	 * 应收单导出查询
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午5:21:33
	 */
	private List<BillReceivableEntity> queryExportWriteoffBillRec(BillRecWriteoffBillPayDto billRecPayDto,CurrentInfo cInfo){
		//生成应收单列表
		List<BillReceivableEntity> receivableList=null;
		//应收单按日期查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(billRecPayDto.getQueryType())){
			//设置最大条数
			billRecPayDto.setMaxShowNum(Integer.MAX_VALUE);
			// 设置应收单查询条件非红单、是否有效等状态,并返回查询列表
			receivableList = queryBillRecList(billRecPayDto, cInfo);
			//记录日志
			LOGGER.info("应收冲应付服务" + "导出应收单总条数size"+ (receivableList != null ? receivableList.size() : 0));
			
		//应收单按应收单号和运单号查询
		}else if(SettlementConstants.TAB_QUERY_BY_REC_PAY_NO.equals(billRecPayDto.getQueryType())){
			//记录日志
			LOGGER.info("应收冲应付服务 按应收单号查询导出 查询应收单单号"+billRecPayDto.getReceivableNosOrWaybillNos());
			receivableList = queryRecForNs(billRecPayDto, cInfo);
			//记录日志
			LOGGER.info("应收冲应付，按应收单号查询 SIZE"+receivableList.size());
		}else{
			//提示应收冲应付导出应收单时，查询tab未知的类型
			throw new SettlementException("应收冲应付导出应收单时，查询tab未知的类型!");
		}
		//返回应收单列表
		return receivableList;
	}
	
	
	
	/**
	 * 导出应付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午4:59:51
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService#exportWriteoffBillPay(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public HSSFWorkbook exportWriteoffBillPay(BillRecWriteoffBillPayDto billRecPayDto, CurrentInfo cInfo) {
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (null == billRecPayDto|| null == billRecPayDto.getArrayColumnNames()|| billRecPayDto.getArrayColumnNames().length == 0) {
			//提示导出Excel的列头名称不存在，请至少存在一列
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}

		// 调用执行方法，获取结果集
		List<BillPayableEntity> queryPayList = queryExportWriteoffBillPay(billRecPayDto, cInfo);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (queryPayList == null || queryPayList.size() == 0) {
			//提示没有要导出的数据
			throw new SettlementException("没有要导出的数据!");
		}

		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormPayEntity(queryPayList,billRecPayDto.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(billRecPayDto.getArrayColumnNames());
		//设置列数据
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		return export.exportExcel(data, "sheet",SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
	}

	/**
	 * 应付单list的实体转化成list<list<String>
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-21 下午12:49:19
	 */
	private List<List<String>> convertListFormPayEntity(List<BillPayableEntity> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		//添加单据类型数据字典到集合中
		termCodes.add(DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
		termCodes.add(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS);
		termCodes.add(DictionaryConstants.FOSS_ACTIVE);
		termCodes.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);
		termCodes.add(DictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS);
		termCodes.add(DictionaryConstants.COD__COD_TYPE);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		// 循环进行封装
		for (BillPayableEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			//循环处理
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(BillPayableEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					//生成object对象
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日期格式化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}
					// 将字段封装到list中
					if (fieldValue != null) {

						// 单据类型不为空
						if (columnName.equals("billType")) {
							// 单据类型
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.BILL_PAYABLE__BILL_TYPE, fieldValue.toString());
						}
						//审核状态不为空
						if (columnName.equals("approveStatus")) {
							// 审核状态
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS, fieldValue.toString());
						}
						//是否有效不为空
						if (columnName.equals("active")) {
							// 是否有效
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.FOSS_ACTIVE, fieldValue.toString());
						}
						//是否红单不为空
						if (columnName.equals("isRedBack")) {
							// 是否红单
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__IS_RED_BACK, fieldValue.toString());
						}
						//时效状态不为空
						if (columnName.equals("effectiveStatus")) {
							// 是否生效
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS, fieldValue.toString());
						}
						//代收货款类型不为空
						if (columnName.equals("codType")) {
							// 代收货款类型
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.COD__COD_TYPE, fieldValue.toString());
						}else if(columnName.equals("productCode")){
							fieldValue = productService.getProductByCache(fieldValue.toString(),new Date()).getName();
						}
						//加入列数据
						rowList.add(fieldValue.toString());
					} else {
						//加入努力了到列数据
						rowList.add(null);
					}
				}
			}
			//将列数据加入excel表单
			sheetList.add(rowList);
		}
		//返回excel表单
		return sheetList;
	}

	/**
	 * 查询需导出应付单
	 * @author 095793-foss-LiQin
	 * @date 2013-1-4 上午10:42:58
	 */
	private List<BillPayableEntity> queryExportWriteoffBillPay(BillRecWriteoffBillPayDto billRecPayDto, CurrentInfo cInfo) {
		//生成应付单列表
		List<BillPayableEntity> payableList = null;
		// 应付单按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billRecPayDto.getQueryType())) {
			// 设置最大条数
			billRecPayDto.setMaxShowNum(Integer.MAX_VALUE);
			// 设置应付单查询条件非红单、是否有效等状态,并返回查询列表
			payableList=queryBillPayList(billRecPayDto, cInfo);
			//记录日志
			LOGGER.info("应收冲应付服务" + "导出应付单总条数size"+ (payableList!=null ? payableList.size():0));
			// 应收单按应收单号和运单号查询
		} else if (SettlementConstants.TAB_QUERY_BY_REC_PAY_NO.equals(billRecPayDto.getQueryType())) {
			//记录日志
			LOGGER.info("应收冲应付服务 按应收单号查询导出 查询应收单单号"+ billRecPayDto.getPayNosOrWaybillNos());
			//初始化应付单
			payableList=queryPayForNs(billRecPayDto, cInfo);
		} else {
			//提示应收冲应付导出应收单时，查询tab未知的类型
			throw new SettlementException("应收冲应付导出应收单时，查询tab未知的类型!");
		}
		//返回应付单列表
		return payableList;
	}
	
	/**
	 * @return billWriteoffBillRecQueryDao
	 */
	public IBillWriteoffBillRecQueryDao getBillWriteoffBillRecQueryDao() {
		return billWriteoffBillRecQueryDao;
	}

	/**
	 * @param billWriteoffBillRecQueryDao
	 */
	public void setBillWriteoffBillRecQueryDao(IBillWriteoffBillRecQueryDao billWriteoffBillRecQueryDao) {
		this.billWriteoffBillRecQueryDao = billWriteoffBillRecQueryDao;
	}

	/**
	 * @return billWriteoffBillPayQueryDao
	 */
	public IBillWriteoffBillPayQueryDao getBillWriteoffBillPayQueryDao() {
		return billWriteoffBillPayQueryDao;
	}

	/**
	 * @param billWriteoffBillPayQueryDao
	 */
	public void setBillWriteoffBillPayQueryDao(IBillWriteoffBillPayQueryDao billWriteoffBillPayQueryDao) {
		this.billWriteoffBillPayQueryDao = billWriteoffBillPayQueryDao;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	
	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}



	/**
	 * @GET
	 * @return productService
	 */
	public IProductService getProductService() {
		/*
		 *@get
		 *@ return productService
		 */
		return productService;
	}



	/**
	 * @SET
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		/*
		 *@set
		 *@this.productService = productService
		 */
		this.productService = productService;
	}


	/**
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}
	
}
