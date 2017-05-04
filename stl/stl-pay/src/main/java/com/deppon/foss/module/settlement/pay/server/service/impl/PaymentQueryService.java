package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;

/**
 * 付款单查询service接口实现
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 下午12:37:21
 */
public class PaymentQueryService implements IPaymentQueryService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(PaymentQueryService.class);

	/**
	 * 查询付款单dao
	 */
	private IPaymentQueryDao paymentQueryDao;

	/**
	 * 付款单服务service
	 */
	private IBillPaymentManageService billPaymentManageService;

	/**
	 * 对公银行账号 Service
	 */
	private IPublicBankAccountService publicBankAccountService;

	/**
	 * 注入组织SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 分页查询付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	@Override
	public BillPaymentResultDto queryPaymentBill(BillPaymentQueryDto dto,int start, int limit, CurrentInfo cInfo) {

		logger.debug("分页查询付款单开始...");

		if (dto == null) {
			throw new SettlementException("输入参数为空,查询付款单失败!");
		}

		// 使用登录用户过滤数据，
		if (cInfo != null) {
			//只查询和登录用户相同部门的付款单，该条件以不用
			//dto.setPaymentOrgCode(cInfo.getCurrentDeptCode());
			//设置当前登录用户编号			
			dto.setEmpCode(cInfo.getEmpCode());
		}
		
		// 设置返回dto
		BillPaymentResultDto rtnDto = new BillPaymentResultDto();

		// 按参数查询总条数
		BillPaymentResultDto paramDto = null;

		// 按参数分页查询付款单
		List<BillPaymentEntity> listPageByParam = null;

		// 按单号查询（不分页）
		List<BillPaymentEntity> listByNos = new ArrayList<BillPaymentEntity>();

		// 如果付款单号、运单号、来源单号、工作流号都为空，则按参数查询
		if (CollectionUtils.isEmpty(dto.getPaymentNos())
				&& CollectionUtils.isEmpty(dto.getWaybillNos())
				&& CollectionUtils.isEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isEmpty(dto.getWorkFlowNos())) {

			// 查询付款单总条数、总金额
			paramDto = paymentQueryDao.queryPaymentTotalByParams(dto);
			if (paramDto != null && paramDto.getPaymentTotalRows() > 0) {
				// 分页查询付款单
				listPageByParam = paymentQueryDao.queryPaymentByPageAndParams(dto, start, limit);
			}
			
			//如果付款单号和运单号之一不为空，且来源单号、工作流号为空，按付款单号或运单号查询
		} else if((CollectionUtils.isNotEmpty(dto.getPaymentNos())|| CollectionUtils.isNotEmpty(dto.getWaybillNos()))
					&& CollectionUtils.isEmpty(dto.getSourceBillNos())
					&& CollectionUtils.isEmpty(dto.getWorkFlowNos())
					){

			// 如果付款单号 不为空，按付款单号查询
			if (CollectionUtils.isNotEmpty(dto.getPaymentNos())) {

				// 按付款单号查询（不分页），并将查询结果放入
				List<BillPaymentEntity> listPageByPaymentNos = paymentQueryDao.queryPaymentByPaymentNos(dto);
				listByNos.addAll(listPageByPaymentNos);
			}
			// 如果运单号 不为空，按运单号查询
			if (CollectionUtils.isNotEmpty(dto.getWaybillNos())) {

				// 按付款单号查询（不分页），并将查询结果放入
				List<BillPaymentEntity> listPageByWaybillNos = paymentQueryDao
						.queryPaymentByWaybillNos(dto);
				listByNos.addAll(listPageByWaybillNos);
			}
		//如果付款单号、运单号、工作流号为空，且来源单号不为空，按来源单号查询
		}else if(CollectionUtils.isEmpty(dto.getPaymentNos())
				&& CollectionUtils.isEmpty(dto.getWaybillNos())
				&& CollectionUtils.isNotEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isEmpty(dto.getWorkFlowNos())
				){
			
			listByNos = paymentQueryDao.queryPaymentBysourceBillNos(dto);
			
			//如果付款单号、运单号、来源单号为空，且工作流号不为空，按来源单号查询	
		}else if(CollectionUtils.isEmpty(dto.getPaymentNos())
				&& CollectionUtils.isEmpty(dto.getWaybillNos())
				&& CollectionUtils.isEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isNotEmpty(dto.getWorkFlowNos())
				){
			
			listByNos = paymentQueryDao.queryPaymentByWorkFlowNos(dto);
		}

		// 如果按参数有数据，设置返回数据
		if (paramDto != null && CollectionUtils.isNotEmpty(listPageByParam)) {

			rtnDto.setBillPaymentEntityList(listPageByParam);
			rtnDto.setPaymentTotalAmount(paramDto.getPaymentTotalAmount());
			rtnDto.setPaymentTotalRows(paramDto.getPaymentTotalRows());

			// 如果按单号查询数据，设置返回数据
		} else if (CollectionUtils.isNotEmpty(listByNos)) {

			rtnDto.setBillPaymentEntityList(listByNos);
			rtnDto.setPaymentTotalRows(Long.valueOf(listByNos.size()));

			// 设置总金额
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (BillPaymentEntity entity : listByNos) {
				if (entity.getAmount() != null) {
					totalAmount = totalAmount.add(entity.getAmount());
				}
			}
			rtnDto.setPaymentTotalAmount(totalAmount);

		}
		logger.debug("分页查询付款单结束...");
		return rtnDto;
	}

	/**
	 * 不分页查询付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-6 上午10:38:20
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#queryPaymentBillNotPage(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public BillPaymentResultDto queryPaymentBillNotPage(BillPaymentQueryDto dto, CurrentInfo cInfo) {

		logger.debug("不分页查询付款单开始...");

		if (dto == null) {
			throw new SettlementException("输入参数为空,查询付款单失败!");
		}

		// 使用登录用户过滤数据，
		if (cInfo != null) {
			// 只查询和登录用户相同部门的付款单，该条件以不用
			// dto.setPaymentOrgCode(cInfo.getCurrentDeptCode());
			// 设置当前登录用户编号
			dto.setEmpCode(cInfo.getEmpCode());
		}

		// 设置返回dto
		BillPaymentResultDto rtnDto = new BillPaymentResultDto();

		// 按参数查询付款单
		List<BillPaymentEntity> listByParam = null;

		// 按运单号查询
		List<BillPaymentEntity> listByNos = new ArrayList<BillPaymentEntity>();

		// 如果付款单号、运单号、来源单号、工作流号都为空，则按参数查询
		if (CollectionUtils.isEmpty(dto.getPaymentNos())
				&& CollectionUtils.isEmpty(dto.getWaybillNos())
				&& CollectionUtils.isEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isEmpty(dto.getWorkFlowNos())) {

			listByParam = paymentQueryDao.queryPaymentByParams(dto);

			// 如果付款单号和运单号之一不为空，且来源单号、工作流号为空，按付款单号或运单号查询
		} else if ((CollectionUtils.isNotEmpty(dto.getPaymentNos()) || CollectionUtils
				.isNotEmpty(dto.getWaybillNos()))
				&& CollectionUtils.isEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isEmpty(dto.getWorkFlowNos())) {

			// 如果付款单号 不为空，按付款单号查询
			if (CollectionUtils.isNotEmpty(dto.getPaymentNos())) {

				// 按付款单号查询（不分页），并将查询结果放入
				List<BillPaymentEntity> listPageByPaymentNos = paymentQueryDao
						.queryPaymentByPaymentNos(dto);
				listByNos.addAll(listPageByPaymentNos);
			}
			// 如果运单号 不为空，按运单号查询
			if (CollectionUtils.isNotEmpty(dto.getWaybillNos())) {

				// 按付款单号查询（不分页），并将查询结果放入
				List<BillPaymentEntity> listPageByWaybillNos = paymentQueryDao
						.queryPaymentByWaybillNos(dto);
				listByNos.addAll(listPageByWaybillNos);
			}
			// 如果付款单号、运单号、工作流号为空，且来源单号不为空，按来源单号查询
		} else if (CollectionUtils.isEmpty(dto.getPaymentNos())
				&& CollectionUtils.isEmpty(dto.getWaybillNos())
				&& CollectionUtils.isNotEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isEmpty(dto.getWorkFlowNos())) {

			listByNos = paymentQueryDao.queryPaymentBysourceBillNos(dto);

			// 如果付款单号、运单号、来源单号为空，且工作流号不为空，按来源单号查询
		} else if (CollectionUtils.isEmpty(dto.getPaymentNos())
				&& CollectionUtils.isEmpty(dto.getWaybillNos())
				&& CollectionUtils.isEmpty(dto.getSourceBillNos())
				&& CollectionUtils.isNotEmpty(dto.getWorkFlowNos())) {

			listByNos = paymentQueryDao.queryPaymentByWorkFlowNos(dto);
		}
		
		
		// 如果按参数有数据，设置返回数据
		if (CollectionUtils.isNotEmpty(listByParam)) {

			rtnDto.setBillPaymentEntityList(listByParam);
			// 如果按单号查询数据，设置返回数据
		} else if (CollectionUtils.isNotEmpty(listByNos)) {

			rtnDto.setBillPaymentEntityList(listByNos);
		}
		logger.debug("不分页查询付款单结束...");
		return rtnDto;
	}

	/**
	 * 根据来源单号查付款单
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:12
	 */
	@Override
	public List<BillPaymentEntity> queryBillPaymentListBySourceBillNo(BillPaymentQueryDto dto){
		
		return paymentQueryDao.queryPaymentBysourceNoAsPayable(dto);
		
	}
	
	/**
	 * 根据付款单号查询付款单信息及其关联应付单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-4 下午2:35:45
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#queryPaymentBillDetial(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@Override
	public BillPaymentResultDto queryPaymentBillDetial(BillPaymentQueryDto dto, int start, int limit,CurrentInfo cInfo) {

		// 由于界面上可能有红单和无效数据存在，所有由单号、是否有效、是否红单共同查询
		if (dto == null || StringUtils.isEmpty(dto.getSelectBillPaymentNos())
				|| StringUtils.isEmpty(dto.getActive())
				|| StringUtils.isEmpty(dto.getIsRedBack())) {
			throw new SettlementException("查看付款单明细时输入参数异常");
		}
		// 处理付款单号
		validatePaymentDto(dto);

		// 使用登录用户过滤数据，
		if (cInfo != null) {
			// 只查询和登录用户相同部门的付款单，该条件以不用
			// dto.setPaymentOrgCode(cInfo.getCurrentDeptCode());
			// 设置当前登录用户编号
			dto.setEmpCode(cInfo.getEmpCode());
		}
		
		// 根据付款单号、是否有效、是否红单条件,查询付款单
		List<BillPaymentEntity> paymentList = paymentQueryDao
				.queryPaymentByPaymentNos(dto);
		if (CollectionUtils.isEmpty(paymentList)) {
			throw new SettlementException("确定申请备用金所选付款单不存在");
		}
		BillPaymentEntity billPaymentEntity = paymentList.get(0);

		// 根据付款单号，查询其相关的应付单明细
		/*BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto();
		billPayableConditionDto.setPaymentNo(billPaymentEntity.getPaymentNo());*/

		/*// 生成存储应付单、预收单列表
		List<BillPayableEntity> billPayableEntityList = null;
		List<BillDepositReceivedEntity> billDepositReceivedEntityList = null;*/
		
		//生成应付单查询dto
		BillPayableManageDto queryPayableDto = new BillPayableManageDto();
		//设置付款单号
		queryPayableDto.setPaymentNo(billPaymentEntity.getPaymentNo());
		//设置付款单明细来源单号类型
		queryPayableDto.setSourceBillTypeFkd(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE);
		//设置只查询非红单
		queryPayableDto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		//生成预收单查询dto
		BillDepositReceivedPayDto queryDepositReceivedDto = new BillDepositReceivedPayDto();
		//设置付款单号
		queryDepositReceivedDto.setPaymentNo(billPaymentEntity.getPaymentNo());
		//设置付款单明细来源单号类型
		queryDepositReceivedDto.setSourceBillTypeFkd(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_DEPOSIT_RECEIVED);
		//设置只查询非红单
		queryDepositReceivedDto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		Long paymentDetialSize = paymentQueryDao.queryPaymentDetialByPageAndParamsSize(dto, queryPayableDto, queryDepositReceivedDto);
		List<BillPaymentAddDto> billPaymentAddDtoList = new ArrayList<BillPaymentAddDto>();
		if(paymentDetialSize.compareTo(Long.valueOf(0L)) > 0){
			// 分页查询
			billPaymentAddDtoList = paymentQueryDao.queryPaymentDetialByPageAndParams(dto,queryPayableDto,queryDepositReceivedDto, start, limit);
		}
		
		/*//查询付款单明细
		List<BillPaymentDEntity> paymentDList = null;
		
		// 如果来源单号类型为应付单类型，只查询应付明细
		if (SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT
				.equals(billPaymentEntity.getSourceBillType())) {

			billPayableEntityList = billPayableQueryManageservice.queryListByPaymentNo(queryPayableDto);

			// 如果来源单号类型为预收单类型，只查询预收单明细
		} else if (SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
				.equals(billPaymentEntity.getSourceBillType())) {
			billDepositReceivedEntityList = billDepositReceivedPayService.queryListByPaymentNo(queryDepositReceivedDto);

			// 否则，应付、预收两种数据明细都查询
		} else {
			billPayableEntityList = billPayableQueryManageservice.queryListByPaymentNo(queryPayableDto);
			billDepositReceivedEntityList = billDepositReceivedPayService.queryListByPaymentNo(queryDepositReceivedDto);
		}
		
		List<BillPaymentAddDto> billPaymentAddDtoList = new ArrayList<BillPaymentAddDto>();
		
		// 代打木架应付单，用分页查询
		if (CollectionUtils.isNotEmpty(billPayableEntityList)) {
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE.equals(billPayableEntityList.get(0).getBillType())){
				// 分页查询
				billPaymentAddDtoList = paymentQueryDao.queryPaymentDetialByPageAndParams(queryPayableDto, start, limit);
			}else{
				// 查询所有
				paymentDList = billPaymentDService.queryPaymentDEntityListByPaymentNo(billPaymentEntity.getPaymentNo());
				
				// 如果应付单list不为空，转化为付款单明细数据
				for (BillPayableEntity entity : billPayableEntityList) {
					BillPaymentAddDto billPaymentAddDto = new BillPaymentAddDto();
					billPaymentAddDto
							.setPaymentNo(billPaymentEntity.getPaymentNo());
					billPaymentAddDto.setPayableNo(entity.getPayableNo());
					billPaymentAddDto.setBillType(entity.getBillType());
					billPaymentAddDto.setBusinessDate(entity.getBusinessDate());
					billPaymentAddDto.setAmount(entity.getAmount());
					billPaymentAddDto.setUnverifyAmount(entity.getUnverifyAmount());
					billPaymentAddDto.setVerifyAmount(entity.getVerifyAmount());
					billPaymentAddDto.setCurrentPaymentAmount(entity
							.getPaymentAmount());
					billPaymentAddDto.setNotes(entity.getNotes());
					billPaymentAddDto.setWaybillNo(entity.getWaybillNo());
					billPaymentAddDto.setSourceBillNo(entity.getSourceBillNo());
					//如果付款单明细不为空
					if(!CollectionUtils.isEmpty(paymentDList)){
						//循环处理付款单明细
						for(BillPaymentDEntity billPaymentDEntity:paymentDList){
							//应付单号等于付款单明细的来源单号
							if(entity.getPayableNo().equals(billPaymentDEntity.getSourceBillNo())){
								if(billPaymentDEntity.getPayAmount()!=null&&billPaymentDEntity.getPayAmount().compareTo(BigDecimal.ZERO)==1){
									//重设本次付款金额为付款单明细的付款金额
									billPaymentAddDto.setCurrentPaymentAmount(billPaymentDEntity.getPayAmount());
								}
							}
						}
					}
					billPaymentAddDtoList.add(billPaymentAddDto);
					
					
				}
				
				// 如果预收单list不为空，转化为付款单明细数据
				if (CollectionUtils.isNotEmpty(billDepositReceivedEntityList)) {
					for (BillDepositReceivedEntity entity : billDepositReceivedEntityList) {
						BillPaymentAddDto billPaymentAddDto = new BillPaymentAddDto();
						billPaymentAddDto.setPaymentNo(billPaymentEntity.getPaymentNo());
						billPaymentAddDto.setPayableNo(entity.getDepositReceivedNo());
						billPaymentAddDto.setBillType(entity.getBillType());
						billPaymentAddDto.setBusinessDate(entity.getBusinessDate());
						billPaymentAddDto.setAmount(entity.getAmount());
						billPaymentAddDto.setUnverifyAmount(entity.getUnverifyAmount());
						billPaymentAddDto.setVerifyAmount(entity.getVerifyAmount());
						billPaymentAddDto.setCurrentPaymentAmount(entity.getPaymentAmount());
						billPaymentAddDto.setNotes(entity.getNotes());
						billPaymentAddDto.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);
						//如果付款单明细不为空
						if(!CollectionUtils.isEmpty(paymentDList)){
							//循环处理付款单明细
							for(BillPaymentDEntity billPaymentDEntity:paymentDList){
								//预收单号等于付款单明细的来源单号
								if(entity.getDepositReceivedNo().equals(billPaymentDEntity.getSourceBillNo())){
									if(billPaymentDEntity.getPayAmount()!=null&&billPaymentDEntity.getPayAmount().compareTo(BigDecimal.ZERO)==1){
										//重设本次付款金额为付款单明细的付款金额
										billPaymentAddDto.setCurrentPaymentAmount(billPaymentDEntity.getPayAmount());
									}
								}
							}
						}
						billPaymentAddDtoList.add(billPaymentAddDto);
						
						
					}
				}
			}
			
		}
		*/
		
		// 生产返回dto
		BillPaymentResultDto billPaymentResultDto = new BillPaymentResultDto();
		billPaymentResultDto.setBillPaymentEntity(billPaymentEntity);
		billPaymentResultDto.setBillPaymentAddDtoList(billPaymentAddDtoList);
		billPaymentResultDto.setPaymentDetialTotal(paymentDetialSize);
		return billPaymentResultDto;
	}

	/**
	 * 申请备用金
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:07:13
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#applySpareMoney(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public BillPaymentResultDto applySpareMoney(BillPaymentQueryDto dto,
			CurrentInfo cInfo) {

		if (dto == null || StringUtils.isEmpty(dto.getPayBillBankNo())) {
			throw new SettlementException("申请备用金输入帐号异常");
		}

		// 根据登录用户部门编码获取公司组织标杆编码
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(cInfo.getCurrentDeptCode());
		if (orgAdministrativeInfoEntity == null) {
			throw new SettlementException("当前用户组织信息不存在");
		}

		//调用综合service获取登录用户（营业员）的银行账户详细信息
		PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
		publicBankAccountEntity.setDeptCd(orgAdministrativeInfoEntity.getUnifiedCode());
		List<PublicBankAccountEntity> bankAcountList = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);

		// 用于存储登录用户（营业员）的银行账户详细信息
		BillPaymentEntity billPaymentEntity = new BillPaymentEntity();

		if (CollectionUtils.isNotEmpty(bankAcountList)) {
			// 如果输入的银行帐号等于前台输入的银行帐号，返回该帐号的信息
			for (PublicBankAccountEntity entity : bankAcountList) {
				if (dto.getPayBillBankNo().equals(entity.getBankAcc())) {
					billPaymentEntity.setAccountNo(entity.getBankAcc());
					billPaymentEntity.setProvinceName(entity.getProvName());
					billPaymentEntity.setProvinceCode(entity.getProvCd());
					billPaymentEntity.setCityName(entity.getCityName());
					billPaymentEntity.setCityCode(entity.getCityCd());
					billPaymentEntity.setBankHqName(entity.getBankName());
					billPaymentEntity.setBankHqCode(entity.getBankName());
					billPaymentEntity.setBankBranchName(entity.getSubbranchName());
					billPaymentEntity.setBankBranchCode(entity.getSubbranchCd());
					billPaymentEntity.setAccountType(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
					billPaymentEntity.setPayeeName(entity.getBankAccName());
				}
			}
		}

		// 返回值
		BillPaymentResultDto resultDto = new BillPaymentResultDto();
		resultDto.setBillPaymentEntity(billPaymentEntity);

		return resultDto;
	}

	/**
	 * 保存申请备用金
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-4 上午10:47:56
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#saveApplySpareMoney(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void saveApplySpareMoney(BillPaymentQueryDto dto, CurrentInfo cInfo) {

		if (dto == null || StringUtils.isEmpty(dto.getSelectBillPaymentNos())|| StringUtils.isEmpty(dto.getPayBillBankNo())|| StringUtils.isEmpty(dto.getPayBillPayeeName())|| dto.getApplyAmount() == null) {
			throw new SettlementException("确定申请备用金时银行账户信息异常");
		}
		// 处理付款单号
		validatePaymentDto(dto);

		//数据权限
		if(cInfo!=null){
			dto.setEmpCode(cInfo.getEmpCode());
		}
		
		// 根据付款单号查询付款单
		List<BillPaymentEntity> paymentList = paymentQueryDao.queryPaymentByPaymentNos(dto);
		if (CollectionUtils.isEmpty(paymentList)) {
			throw new SettlementException("确定申请备用金所选付款单不存在");
		}

		// 申请备用金服务参数
		PayToCostcontrolDto payToCostcontrolDto = new PayToCostcontrolDto();
		payToCostcontrolDto.setPaymentNos(dto.getPaymentNos());// 付款单号
		payToCostcontrolDto.setPayBillCelephone(dto.getMobilePhone());// 联系方式
		payToCostcontrolDto.setPayBillAmount(dto.getApplyAmount());// 申请金额
		payToCostcontrolDto.setPayBillBankNo(dto.getPayBillBankNo());// 银行账号
		payToCostcontrolDto.setPayBillPayeeName(dto.getPayBillPayeeName());// 收款方姓名
		payToCostcontrolDto.setPayBillPayeeCode(dto.getPayBillPayeeCode());//收款方code
		payToCostcontrolDto.setAccountType(dto.getAccountType());// 账号类型
		payToCostcontrolDto.setProvinceCode(dto.getProvinceCode());//开户行省份编码
		payToCostcontrolDto.setProvinceName(dto.getProvinceName());
		payToCostcontrolDto.setCityCode(dto.getCityCode());//开户行城市编码
		payToCostcontrolDto.setCityName(dto.getCityName());//开户行城市名称
		payToCostcontrolDto.setBankHqCode(dto.getBankHqCode());
		payToCostcontrolDto.setBankHqName(dto.getBankHqName());//开户行名称
		payToCostcontrolDto.setBankBranchCode(dto.getBankBranchCode());
		payToCostcontrolDto.setBankBranchName(dto.getBankBranchName());//开户行支行名称
		payToCostcontrolDto.setPayBillComNo(dto.getInvoiceHeadCode());//发票抬头编码
		payToCostcontrolDto.setPayBillComName(dto.getInvoiceHeadName());//发票抬头名称
		payToCostcontrolDto.setIsAutoAbatementLoan(dto.getIsAutoAbatementLoan());//是否自动冲借支
		payToCostcontrolDto.setBatchNo(dto.getBatchNo());
		//设置事由说明
		if(!StringUtils.isEmpty(dto.getNotes())){
			//备注
			String notes = dto.getNotes();
			//判断超过300行截取
			final int numberOfThreeHundred = 300;
			final int numberOfTwoNinetyNine = 299;
			if(dto.getNotes().length()>numberOfThreeHundred){
				//截取299个字
				notes = dto.getNotes().substring(0,numberOfTwoNinetyNine);
			}
			payToCostcontrolDto.setPayBillDiscription(notes);
		}
		// 调用申请备用金工作流
		billPaymentManageService.applyPettyCash(payToCostcontrolDto, cInfo);

	}

	/**
	 * 审核付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:07:23
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#aduitPayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void aduitPayment(BillPaymentQueryDto dto, CurrentInfo cInfo) {

		if (dto == null || StringUtils.isEmpty(dto.getSelectBillPaymentNos())) {
			throw new SettlementException("审核付款单时输入参数异常");
		}
		// 处理输入单号
		validatePaymentDto(dto);

		// 审核付款单
		billPaymentManageService.auditPaymentOrderBill(dto.getPaymentNos(),
				dto.getOpinionNotes(), cInfo);
	}

	/**
	 * 反审核付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:07:33
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#revAduitPayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void revAduitPayment(BillPaymentQueryDto dto, CurrentInfo cInfo) {

		if (dto == null || StringUtils.isEmpty(dto.getSelectBillPaymentNos())) {
			throw new SettlementException("反审核付款单时输入参数异常");
		}
		// 处理输入单号
		validatePaymentDto(dto);

		// 反审核付款单
		billPaymentManageService.reverseAuditPaymentOrder(dto.getPaymentNos(),
				dto.getOpinionNotes(), cInfo);
	}

	/**
	 * 作废付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:07:47
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService#disabledPayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void disabledPayment(BillPaymentQueryDto dto, CurrentInfo cInfo) {

		if (dto == null || StringUtils.isEmpty(dto.getSelectBillPaymentNos())) {
			throw new SettlementException("作废付款单时输入参数异常");
		}
		// 处理输入单号
		validatePaymentDto(dto);

		// 作废付款单
		billPaymentManageService.invalidPaymentOrder(dto.getPaymentNos(),
				dto.getOpinionNotes(), cInfo);
	}

	/**
	 * 检查并处理dto数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:10:41
	 */
	private void validatePaymentDto(BillPaymentQueryDto dto) {

		// 如果dto或者选择的单号为空，提示输入参数错误
		if (dto == null || StringUtils.isEmpty(dto.getSelectBillPaymentNos())) {
			throw new SettlementException("没有选中的付款单");
		}

		// 按“,”分割还款单字串，生成付款单号list
		String[] paymentNos = dto.getSelectBillPaymentNos().split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < paymentNos.length; i++) {
			list.add(paymentNos[i].trim());
		}
		dto.setPaymentNos(list);

	}

	/**
	 * @param paymentQueryDao
	 */
	public void setPaymentQueryDao(IPaymentQueryDao paymentQueryDao) {
		this.paymentQueryDao = paymentQueryDao;
	}

	/**
	 * @param billPaymentManageService
	 */
	public void setBillPaymentManageService(
			IBillPaymentManageService billPaymentManageService) {
		this.billPaymentManageService = billPaymentManageService;
	}

	/**
	 * @param publicBankAccountService
	 */
	public void setPublicBankAccountService(
			IPublicBankAccountService publicBankAccountService) {
		this.publicBankAccountService = publicBankAccountService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}
