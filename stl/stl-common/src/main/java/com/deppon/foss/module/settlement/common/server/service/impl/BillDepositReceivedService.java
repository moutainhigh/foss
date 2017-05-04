package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillDepositReceivedEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预收单服务
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-23 上午9:10:51
 */
public class BillDepositReceivedService implements IBillDepositReceivedService {

	private static final Logger logger = LogManager
			.getLogger(BillDepositReceivedService.class);

	/**
	 * 预收单dao
	 */
	private IBillDepositReceivedEntityDao billDepositReceivedEntityDao;

	/**
	 * 对账单service
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
     * NCI ：service
     */
    private IPdaPosManageService pdaPosManageService;
    
	public IBillDepositReceivedEntityDao getBillDepositReceivedEntityDao() {
		return billDepositReceivedEntityDao;
	}

	public void setBillDepositReceivedEntityDao(
			IBillDepositReceivedEntityDao billDepositReceivedEntityDao) {
		this.billDepositReceivedEntityDao = billDepositReceivedEntityDao;
	}

	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	/**
	 * 批量确认收银
	 * @author foss-pengzhen
	 * @date 2012-12-17 上午11:17:46
	 */
	public void confirmCashierBillDepositReceived(BillDepositReceivedEntityDto dto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillDepositReceivedEntityList())) {
			throw new SettlementException("确认收银，预收单参数不能为空");
		}
		if (dto.getBillDepositReceivedEntityList().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("确认收银预收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行操作。");
		}
		
		logger.info("entering service, ids: " + dto.getBillDepositReceivedEntityList());
		
		Date now = new Date();

		// 操作者信息
		dto.setModifyTime(now);
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		// 收银人信息
		// 收银人信息
		dto.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__CONFIRM);
		dto.setCashConfirmTime(now);
		dto.setCashConfirmUserCode(currentInfo.getEmpCode());
		dto.setCashConfirmUserName(currentInfo.getEmpName());
		
		int i = this.billDepositReceivedEntityDao.updateByConfirmCashier(dto);
		
		if (i != dto.getBillDepositReceivedEntityList().size()) {
			throw new SettlementException("确认收银预收单失败");
		}
		
		logger.info("successfully exit service, ids: " + dto.getBillDepositReceivedEntityList());
	}
	
	/**
	 * 修改预收单的对账单号及是否生成对账单字段值
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午7:30:36
	 */
	@Override
	public int updateBillDepositReceivedByMakeStatement(
			BillDepositReceivedEntity entity, CurrentInfo info) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("修改预收单参数不能为空！");
		}

		logger.info("修改预收单的对账单号开始,预收单号：" + entity.getDepositReceivedNo());

		// 设置创建者名称、编码、创建者部门名称、编码
		entity.setCreateUserCode(info.getEmpCode());
		entity.setCreateUserName(info.getEmpName());
		//entity.setCreateOrgCode(info.getCurrentDeptCode());
		//entity.setCreateOrgName(info.getCurrentDeptName());

		int i = billDepositReceivedEntityDao
				.updateBillDepositReceivedByMakeStatement(entity);

		if (i != 1) {
			throw new SettlementException("修改预收单的对账单号出错");
		}
		logger.info("修改预收单的对账单号结束,预收单号：" + entity.getDepositReceivedNo());
		return i;
	}

	/**
	 * 批量修改应收单的对账单号及是否生成对账单字段值
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-5 上午9:26:15
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#batchUpdateBillDepositReceivedByMakeStatement(java.util.List,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void batchUpdateBillDepositReceivedByMakeStatement(
			BillDepositReceivedEntityDto dto, CurrentInfo info) {
		
		// 输入参数校验
		if (dto == null
				|| StringUtils.isEmpty(dto.getStatementBillNo())
				|| CollectionUtils.isEmpty(dto
						.getBillDepositReceivedEntityList())) {
			throw new SettlementException("批量修改预收单参数不能为空！");
		}
		if (dto.getBillDepositReceivedEntityList().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("预收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "，不能进行修改操作");
		}

		logger.info("批量修改预收单的对账单号开始，对账单号：" + dto.getStatementBillNo());
		// 是否有效设置有效
		dto.setActive(FossConstants.ACTIVE);
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(info.getEmpCode());
		dto.setModifyUserName(info.getEmpName());

		int i = billDepositReceivedEntityDao
				.batchUpdateBillDepositReceivedByMakeStatement(dto);

		if (i != dto.getBillDepositReceivedEntityList().size()) {
			throw new SettlementException("批量修改预收单的对账单单号服务失败");
		}
		logger.info("批量修改预收单的对账单号结束，对账单号：" + dto.getStatementBillNo());
	}

	/**
	 * 核销时修改预收单的金额等字段信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-19 上午11:21:40
	 */
	@Override
	public void writeoffBillDepositReceived(BillDepositReceivedEntity entity,
			BigDecimal writeoffAmount, CurrentInfo info) {
		if(entity==null||writeoffAmount==null){
       	 	throw new SettlementException("核销预收单的参数不能为空");
        } 
		int rows = billDepositReceivedEntityDao.writeOffBillDepositReceived(
				entity, writeoffAmount, info);
		if (rows != 1) {
			throw new SettlementException(entity.getDepositReceivedNo()+":预收单金额已发生变化，请刷新后再试");
		}

		// 核销之后，其他地方还需要使用，为了保持java方法中的版本号和数据库中的版本号统一，在这里+1
		entity.setVersionNo((short) (entity.getVersionNo() + 1));
	}

	/**
	 * 根据传入的多个预收单id号，获取预收单列表信息
     * @author foss-pengzhen
     * @date 2012-10-19 下午1:40:42
     * @param depositReceivedIds 预收单ID号集合
     * @param active 是否有效
     * @return
	 */
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedIds(
			List<String> depositReceivedIds, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(depositReceivedIds)) {
			throw new SettlementException("查询预收单，预收单ID号集合不能为空");
		}
		
		logger.debug("active:" + active);
		
		return billDepositReceivedEntityDao.queryByDepositReceivedIds(
				depositReceivedIds, active);
	}
	
	/**
	 * 根据传入的多个预收单号，获取预收单列表信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-19 下午1:40:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedNOs(
			List<String> depositReceivedNos, String active) {
		
		// 输入参数校验
		if (CollectionUtils.isEmpty(depositReceivedNos)) {
			throw new SettlementException("查询预收单，预收单号集合不能为空");
		}
		List<String> listDepNos = new ArrayList<String>();
		List<BillDepositReceivedEntity> depEntitysList = new ArrayList<BillDepositReceivedEntity>();
		for(int i = 0; i < depositReceivedNos.size();i++){
				
			listDepNos.add(depositReceivedNos.get(i));
			if(listDepNos.size() == SettlementConstants.MAX_LIST_SIZE){
				List<BillDepositReceivedEntity> list = billDepositReceivedEntityDao.queryByDepositReceivedNOs(listDepNos, active);
				if(null != list){
					depEntitysList = (List<BillDepositReceivedEntity>) CollectionUtils.union(depEntitysList,list);
				}
				listDepNos.clear();
			}
		}
		if(0 < listDepNos.size() && listDepNos.size() < SettlementConstants.MAX_LIST_SIZE){
			List<BillDepositReceivedEntity> list = billDepositReceivedEntityDao.queryByDepositReceivedNOs(listDepNos, active);
			if(null != list){
				depEntitysList = (List<BillDepositReceivedEntity>) CollectionUtils.union(depEntitysList,list);
			}
			listDepNos.clear();
		}
		logger.debug("active:" + active);
		
		return depEntitysList;
	}

	/**
	 * 根据传入的一个预收单号，获取一条预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-22 下午2:07:57
	 */
	@Override
	public BillDepositReceivedEntity queryByDepositReceivedNo(
			String depositReceivedNo, String active) {
		
		// 输入参数校验
		if (StringUtils.isEmpty(depositReceivedNo)) {
			throw new SettlementException("查询预收单，预收单号不能为空");
		}
		
		// 必须有值，否则可能返回多个预收单
		if (StringUtils.isEmpty(active)) {
			throw new SettlementException("查询预收单，预收单是否有效不能为空");
		}
		
		logger.debug("depositReceivedNo" + depositReceivedNo + ",  active:"
				+ active);
		
		return billDepositReceivedEntityDao.queryByDepositReceivedNo(
				depositReceivedNo, active);
	}

	/**
	 * 新增预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 上午11:37:59
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#addBillDepositReceived(com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity)
	 */
	@Override
	public int addBillDepositReceived(BillDepositReceivedEntity entity,
			CurrentInfo info) {
		if(entity==null
				||entity.getId()==null
				||null==entity.getAccountDate()){
			throw new SettlementException("新增预收单参数不能为空！");
		}
		logger.info("id:" + entity.getId() + ",  depositReceivedNo:"
				+ entity.getDepositReceivedNo());

		// 设置创建者名称、编码、创建者部门名称、编码、状态为已提交
		entity.setCreateUserCode(info.getEmpCode());
		entity.setCreateUserName(info.getEmpName());
		entity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		entity.setModifyUserCode(info.getCurrentDeptCode());
		entity.setModifyUserName(info.getCurrentDeptName());
		
		//收银状态改为提交 --不管是新增还是红冲，新生成单据 收银确认人、时间是空，收银状态为提交
		entity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		entity.setCashConfirmTime(null);
		entity.setCashConfirmUserCode(null);
		entity.setCashConfirmUserName(null);
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);

		return billDepositReceivedEntityDao.add(entity);
	}

	/**
	 * 作废预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 上午9:09:51
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#disableBillDepositReceived(com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void disableBillDepositReceived(BillDepositReceivedEntity entity,
			CurrentInfo info) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("失效预收单参数不能为空！");
		}

		logger.info("entering service, depositReceivedNo: "
				+ entity.getDepositReceivedNo());
		
		List<String> statementBillNoList = new ArrayList<String>();
		
		Boolean isNotConfirm=false;

		// 1、校验已核销或部分核销的预收单不能作废
		validaReceived(entity, info, statementBillNoList, isNotConfirm);
		
		
		Date now = Calendar.getInstance().getTime();

		// 作废原有版本
		BillDepositReceivedEntity updateEntity = new BillDepositReceivedEntity();

		// 作废原应收单
		 // ID
		updateEntity.setId(entity.getId());
		// 分区键
		updateEntity.setAccountDate(entity.getAccountDate()); 
		// 版本号
		updateEntity.setVersionNo(entity.getVersionNo()); 
		updateEntity.setActive(FossConstants.INACTIVE);
		updateEntity.setModifyTime(now);
		updateEntity.setModifyUserCode(info.getEmpCode());
		updateEntity.setModifyUserName(info.getEmpName());
		
		int i = billDepositReceivedEntityDao.updateByTakeEffect(updateEntity);

		if (i != 1) {
			throw new SettlementException("失效预收单出错");
		}

		// 生成红冲单
		validaBill(entity, info, now);
		
		logger.info("successfully exit service, id: " + entity.getId());

	}

	private void validaReceived(BillDepositReceivedEntity entity,
			CurrentInfo info, List<String> statementBillNoList,
			Boolean isNotConfirm) {
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("该预收单已核销，不允许作废");
			// 2、 校验对账单已经确认对账单不能作废
		} else if (StringUtils.isNotEmpty(entity.getStatementBillNo())
				&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			
			statementBillNoList.add(entity.getStatementBillNo());

			// 调用对账单确认验证接口预收单是否已经确认对账单
			statementBillNoList = statementOfAccountService
					.queryConfirmStatmentOfAccount(statementBillNoList);
			if (CollectionUtils.isNotEmpty(statementBillNoList)) {
				throw new SettlementException("预收单对应的对账单已确认，不允许作废");
			}else{
				isNotConfirm=true;
			}
			// 3、已处于退款中的预收单不能作废
		} else if (!SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND
				.equals(entity.getRefundStatus())) {
			throw new SettlementException("该预收单处于已退款或退款中状态，不允许作废");
			// 4、红单或无效版本不能作废
		} else if (SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES
				.equals(entity.getIsRedBack())
				|| FossConstants.INACTIVE.equals(entity.getActive())) {
			throw new SettlementException("该预收单是红单或者无效版本，不允许作废");
			// 5、当前登陆用户不能作废制单人是自己的预收单
		} else if (entity.getCreateUserCode().equals(info.getEmpCode())) {
			throw new SettlementException("当前登陆用户不能作废制单人是自己的预收单");
		}

		if(isNotConfirm){
			
				//预收单对应的对账单未确认，则允许作废			
				
				//更新对应的对账单和对账单明细
				List<BillDepositReceivedEntity>  depositReceivedEntityList=
						new ArrayList<BillDepositReceivedEntity>();
				
				depositReceivedEntityList.add(entity);
								
				//声明实例对账单dto
				StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
				//把相应的数据设置到对账单中
				statementOfAccountUpdateDto.setDepositReceivedEntityList(depositReceivedEntityList);
				// 作废完成时，通知修改对账单及对账单明细信息
				statementOfAccountService.updateStatementAndDetailForRedBack(
						statementOfAccountUpdateDto, info);								
		}
	}

	private void validaBill(BillDepositReceivedEntity entity, CurrentInfo info,
			Date now) {
		BillDepositReceivedEntity redBackEntity = new BillDepositReceivedEntity();
		BeanUtils.copyProperties(entity, redBackEntity);

		redBackEntity.setId(UUIDUtils.getUUID());
		// 设置为无效状态
		redBackEntity.setActive(FossConstants.INACTIVE); 
		redBackEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 红单
		redBackEntity
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		// 设置记账日期
		redBackEntity.setAccountDate(now); 
		redBackEntity.setCreateTime(now);
		redBackEntity.setModifyTime(now);

		redBackEntity.setAmount(NumberUtils.multiply(entity.getAmount(), -1));
		redBackEntity.setVerifyAmount(NumberUtils.multiply(
				entity.getVerifyAmount(), -1));
		redBackEntity.setUnverifyAmount(NumberUtils.multiply(
				entity.getUnverifyAmount(), -1));

		//redBackEntity.setCreateOrgCode(info.getCurrentDeptCode());
		//redBackEntity.setCreateOrgName(info.getCurrentDeptName());
		
		//收银状态改为提交
		redBackEntity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);

		int iAdd = addBillDepositReceived(redBackEntity, info);

		if (iAdd != 1) {
			throw new SettlementException("失效预收单出错");
		}
		
		/**
		 * NCI项目，假如作废预收单成功之后，释放T+0报表的已使用金额，删除明细数据
		 * @author 269052 zhouyuan008@deppon.com
		 * @date 2016-03-16
		 */
		/**
		 * 1、判断是否为银行卡
		 */
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(entity.getPaymentType())){
			/**
			 * 确定要作废的是哪个单据
			 * 
			 * 2、根据预收单号和交易流水号去查询明细
			 */
			// 判断单号是否来自于NCI项目
			PosCardDetailEntity detail = new PosCardDetailEntity();
			detail.setInvoiceNo(entity.getDepositReceivedNo());
			// 交易流水号
			detail.setTradeSerialNo(entity.getRemitNo());
			
			//工号
			detail.setModifyUserCode(info.getEmpCode());
			//更新人
			detail.setModifyUser(info.getEmpName());
			
			/**
			 * 根据单据号去查询是否属于NCI项目中的数据
			 */
			List<PosCardDetailEntity> list = pdaPosManageService.queryPosDetailsByNo(detail);
			if(CollectionUtils.isNotEmpty(list)){
				// 释放T+0报表的金额和删除明细数据
				pdaPosManageService.updatePosAndDeleteDetail(detail);
			}	
		}
	}

	/**
	 * 修改预收单的支付状态、付款单号、付款金额等信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午5:35:27
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#payForBillDepositReceived(com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity,
	 *      java.lang.String,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void payForBillDepositReceived(BillDepositReceivedEntity entity,
			CurrentInfo info) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getVersionNo() == null
				|| StringUtils.isEmpty(entity.getPaymentNo())) {
			throw new SettlementException("修改预收单支付状态出错");
		}

		logger.info("修改预收单支付状态开始, id: " + entity.getId()+ " ,付款单号:"+entity.getPaymentNo());
		
		Date now = Calendar.getInstance().getTime();
		// 已退款
		entity.setRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__REFUNDED);
		// 修改时间
		entity.setModifyTime(now);
		// 修改人编码
		entity.setModifyUserCode(info.getEmpCode());
		// 修改人名称
		entity.setModifyUserName(info.getEmpName());

		int i = billDepositReceivedEntityDao.updateByPaymentStatus(entity);

		if (i != 1) {
			throw new SettlementException("修改预收单的支付状态、付款单号、付款金额等信息失败");
		}
		logger.info("修改预收单支付状态结束");
	}

	/**
	 * 取消预收单的支付状态、付款单号、付款金额等信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午5:35:47
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#cancelPayForBillDepositReceived(com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity,
	 *      java.lang.String,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void cancelPayForBillDepositReceived(
			BillDepositReceivedEntity entity, CurrentInfo info) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getVersionNo() == null
				|| StringUtils.isEmpty(entity.getPaymentNo())) {
			throw new SettlementException("取消预收单支付状态出错");
		}

		logger.info("取消修改预收单支付状态开始, id: " + entity.getId()+ " ,付款单号:"+entity.getPaymentNo());
		
		Date now = Calendar.getInstance().getTime();

		entity.setPaymentAmount(BigDecimal.ZERO);
		entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
		entity.setPaymentNotes(null);
		// 未退款
		entity.setRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND);
		// 修改时间
		entity.setModifyTime(now);
		// 修改人编码
		entity.setModifyUserCode(info.getEmpCode());
		// 修改人名称
		entity.setModifyUserName(info.getEmpName());

		int i = billDepositReceivedEntityDao.updateByPaymentStatus(entity);

		if (i != 1) {
			throw new SettlementException("取消预收单的支付状态、付款单号、付款金额等信息失败");
		}
		logger.info("修改预收单支付状态结束");
	}

	/**
	 * 根据付款单号查询预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-4 下午3:23:30
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#queryByPaymentNo(java.lang.String)
	 */
	@Override
	public List<BillDepositReceivedEntity> queryByPaymentNo(String paymentNo) {

		if (StringUtils.isEmpty(paymentNo)) {
			throw new SettlementException("查询预收单，付款单号不能为空");
		}

		// 设置有效非红单
		String active = FossConstants.ACTIVE;
		String isRedBack = SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO;

		return billDepositReceivedEntityDao.queryByPaymentNo(paymentNo, active,
				isRedBack);
	}
	
	 /**
     * 根据付款单号查询预收单
     * @author foss-pengzhen
     * @date 2012-12-4 下午3:25:12
     * @param paymentNo 支付单号
     * @param active 是否有效
     * @param isRedBack 是否红冲
     * @return
     */
	@Override
    public List<BillDepositReceivedEntity> queryByPaymentNos(
    		List<String> paymentNos, String active,String isRedBack){
    	if (CollectionUtils.isEmpty(paymentNos)) {
			throw new SettlementException("查询预收单，付款单号不能为空");
		}
    	
		return billDepositReceivedEntityDao.queryByPaymentNos(paymentNos, active,
				isRedBack);
    }

	/* 
	 * 根据汇款编号查询合伙人预收单是否存在
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService#queryByRemitNo(java.lang.String, java.lang.String)
	 */
	@Override
	public BillDepositReceivedEntity queryByRemitNo(String remitNo,
			String active) {
		if(StringUtils.isBlank(remitNo)){
			throw new SettlementException("查询预收单，汇款编号不能为空");
		}
		
		//根据汇款编号查询合伙人预收单
		List<BillDepositReceivedEntity> depositList = billDepositReceivedEntityDao
				.queryByRemitNo(remitNo, active);

		return CollectionUtils.isEmpty(depositList) ? null : depositList.get(0);
	}
}
