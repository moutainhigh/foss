package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPaymentEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPmtAutoPayDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 付款单服务
 * @author ibm-zhuwei
 * @date 2012-10-24 上午9:41:40
 */
public class BillPaymentService implements IBillPaymentService {

	private static final Logger logger = LogManager.getLogger(BillPaymentService.class);
	
	/**
	 * 付款单Dao
	 */
	private IBillPaymentEntityDao billPaymentEntityDao;
	
	/**
	 * 付款单明细Service
	 */
	private IBillPaymentDService billPaymentDService;

	/**
	 * Set billPaymentEntityDao 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-18 下午5:39:25
	 * @param billPaymentEntityDao
	 */
	public void setBillPaymentEntityDao(
			IBillPaymentEntityDao billPaymentEntityDao) {
		this.billPaymentEntityDao = billPaymentEntityDao;
	}
	
	/**
	 * 生成付款单
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 4:09:58 PM
	 */
	private void add(BillPaymentEntity entity){
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		//保存数据后返回值
		int i = billPaymentEntityDao.add(entity);
		//i不等于1提示异常信息
		if (i != 1) {
			throw new SettlementException("生成付款单失败");
		}
	}

	/**
	 * 新增付款单
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午9:35:02
	 * @param entity
	 * @param currentInfo
	 */
	@Override
	public void addBillPayment(BillPaymentEntity entity, CurrentInfo currentInfo) {
		//实体不能为空或id不能为空，付款单号不能为空，业务日期和记账日期都不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getPaymentNo())
				|| entity.getBusinessDate() == null
				|| entity.getAccountDate() == null) {
			throw new SettlementException("新增付款单传入的参数不能为空！");
		}
		
		logger.info("新增付款单Start, 付款单号：" + entity.getPaymentNo());
		
		//设置操作者信息
		Date date = new Date();
		
		//设置创建时间
		entity.setCreateTime(date);
		
		//设置创建人编码
		entity.setCreateUserCode(currentInfo.getEmpCode());
		
		//创建人名称
		entity.setCreateUserName(currentInfo.getEmpName());
		
		//设置修改时间
		entity.setModifyTime(date);
		
		//设置修改人编码
		entity.setModifyUserCode(currentInfo.getEmpCode());
		
		//设置修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());

		// 业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate = entity.getBusinessDate();
		
		//记账日期格式化到秒，之后进行比较
		Date accountDate = entity.getAccountDate();
		
		//业务日期如果在记账日期之后，提示异常信息
		if (bussinessDate.after(accountDate)) {
			throw new SettlementException("记账日期小于业务日期，不能进行新增付款单操作！");
		}
		
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		
		this.add(entity);
		
		logger.info("新增付款单End, 付款单号：" + entity.getPaymentNo());
	}

	/**
	 * 红冲付款单
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午11:30:15
	 * @param entity
	 * 			付款单实体
	 * @param currentInfo
	 *          当前操作者
	 */
	@Override
	public void writeBackBillPayment(BillPaymentEntity entity,CurrentInfo currentInfo) {
		//判断，实体和ID，付款单号、记账日期、业务日期不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getPaymentNo())
				|| entity.getBusinessDate() == null
				|| entity.getAccountDate() == null) {
			throw new SettlementException("红冲付款单传入的参数不能为空！");
		}
		
		logger.info("entering service..., paymentNo: " + entity.getPaymentNo());

		Date now = new Date();
		
		//new一个修改实体
		BillPaymentEntity updateEntity = new BillPaymentEntity();
		
		// 作废旧单据
		updateEntity.setId(entity.getId());
		
		//失败原因
		updateEntity.setDisableOpinion(entity.getDisableOpinion());
		
		//版本号
		updateEntity.setVersionNo(entity.getVersionNo());
		
		//是否有效-无效
		updateEntity.setActive(FossConstants.INACTIVE);
		
		//修改时间
		updateEntity.setModifyTime(now);
		
		// 操作者信息
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人编码
		updateEntity.setModifyUserName(currentInfo.getEmpName());
		
		int i = billPaymentEntityDao.updateByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("红冲付款单失败");
		}
		
		// 生成红冲单
		BillPaymentEntity newEntity = new BillPaymentEntity();
		BeanUtils.copyProperties(entity, newEntity);
		
		//设置红单ID
		newEntity.setId(UUIDUtils.getUUID());
		
		//是否有效
		newEntity.setActive(FossConstants.INACTIVE);
		
		//版本号
		newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		//是否红单
		newEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		
		//记账日期
		newEntity.setAccountDate(now);
		
		//创建时间
		newEntity.setCreateTime(now);
		
		//修改时间
		newEntity.setModifyTime(now);
		
		//操作者信息
		newEntity.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		newEntity.setModifyUserName(currentInfo.getEmpName());
		
		//设置金额为负数
		newEntity.setAmount(NumberUtils.multiply(newEntity.getAmount(), -1));
		
		this.add(newEntity);
		
		logger.info("exit service..., paymentNo: " + entity.getPaymentNo());
	}

	/**
	 * 批量审核付款单
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午10:41:51
	 * @param dto
	 * 			存放付款单集合dto
	 * @param currentInfo
	 *          当前操作者
	 */
	@Override
	public void batchAuditBillPayment(BillPaymentDto dto,
			CurrentInfo currentInfo) {
		
		//存放的应付单实体集合不能为空
		if(dto==null||CollectionUtils.isEmpty(dto.getBillPayments())){
			throw new SettlementException("批量审核付款单的参数不能为空！");
		}
		logger.info("批量 批量审核付款单 Start ");
		
		//未审核
		dto.setConAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);
		
		//审核状态：已审核
		dto.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
		
		//审核人编码
		dto.setAuditUserCode(currentInfo.getEmpCode());
		
		//审核人名称
		dto.setAuditUserName(currentInfo.getEmpName());
		
		//只审核状态为有效的数据
		dto.setActive(FossConstants.ACTIVE);//有效
		
		//修改时间
		dto.setModifyTime(new Date());
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());
		
		//dao层返回值不为1，提示保存失败异常
		int i=this.billPaymentEntityDao.updateBillPaymentByBatchAudit(dto);
		if(i!=dto.getBillPayments().size()){
			throw new SettlementException("批量审核付款单失败！");
		}
		logger.info("批量 批量审核付款单 end ");
	}

	/**
	 * 批量反审核付款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午10:42:09
	 * @param dto
	 *        存放付款单集合dto
	 * @param currentInfo
	 *        当前操作者
	 */
	@Override
	public void batchReverseAuditBillPayment(BillPaymentDto dto,
			CurrentInfo currentInfo) {
		//调用接口参数不能为空，且dto中的付款单集合不能为空
		if(dto==null||CollectionUtils.isEmpty(dto.getBillPayments())){
			throw new SettlementException("批量反审核付款单的参数不能为空！");
		}
		logger.info("批量反审核付款单 Start ");
		
		//已审核
		dto.setConAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
		
		//设置审核状态：未审核
		dto.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);
		
		//有效
		dto.setActive(FossConstants.ACTIVE);
		
		//审核人编码设置为空
		dto.setAuditUserCode(currentInfo.getEmpCode());
		
		//审核人名称设置为空
		dto.setAuditUserName(currentInfo.getEmpName());
		
		//有效
		dto.setActive(FossConstants.ACTIVE);
		
		//修改时间
		dto.setModifyTime(new Date());
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());
		int i=this.billPaymentEntityDao.updateBillPaymentByBatchAudit(dto);
		if(i!=dto.getBillPayments().size()){
			throw new SettlementException("批量反审核付款单失败！");
		}
		logger.info("批量反审核付款单 End ");
	}
	
	/**
	 * 调用费控接口，反写付款工作流
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午10:42:09
	 * @param dto
	 *        参数dto
	 * @param currentInfo
	 *        当前操作者信息
	 */
	@Override
	public void batchReverseWorkFlowNoBillPayment(BillPaymentDto dto,
			CurrentInfo currentInfo) {
		
		//参数和付款集合不能为空
		if(dto==null||CollectionUtils.isEmpty(dto.getBillPayments())){			
			throw new SettlementException("批量更新付款单工作流号的参数不能为空！");
		}
		logger.info("批量反写费控工作流 Start ");
		
		//付款工作流号不能为空
		if(StringUtils.isBlank(dto.getWorkflowNo())){
			throw new SettlementException("付款工作流号不能为空！");
		}
		
		//有效
		dto.setActive(FossConstants.ACTIVE);
		
		//修改时间
		dto.setModifyTime(new Date());
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());
		int i=this.billPaymentEntityDao.updateBillPaymentByBatchWorkflow(dto);
		
		//判断更新行数
		if(i!=dto.getBillPayments().size()){
			throw new SettlementException("批量更新工作流号失败！");
		}
		logger.info("批量反写费控工作流 End ");
	}
	
	/**
	 * 调用费控接口，反写备用金工作流
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午10:42:09
	 * @param dto
	 * 		  
	 * @param currentInfo  
	 *        当前操作者
	 */
	@Override
	public void batchReverseApplyWorkflowBillPayment(BillPaymentDto dto,
			CurrentInfo currentInfo) {
		logger.info("批量反写备用金工作流 Start ");
		//参数和参数中包含的付款单集合不能为空
		if(dto==null||CollectionUtils.isEmpty(dto.getBillPayments())){	
			throw new SettlementException("批量更新备用金工作流号的参数不能为空！");
		}
		
		//备用金工作流号，不能为空
		if(StringUtils.isBlank(dto.getApplyWorkFlowNo())){
			throw new SettlementException("备用金工作流号不能为空！");
		}
		
		//有效
		dto.setActive(FossConstants.ACTIVE);
		
		//修改时间
		dto.setModifyTime(new Date());
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());
		int i=this.billPaymentEntityDao.updateBillPaymentByBatchApplyWorkflow(dto);
		
		//判断更新行数
		if(i!=dto.getBillPayments().size()){
			throw new SettlementException("批量更新备用金工作流号失败！");
		}
		
		logger.info("批量反写备用金工作流 End ");
	}
	
	/**
	 * 根据一到多个付款单号，获取一到多条付款单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午11:47:42
	 * @param paymentNos
	 * 			付款单号集合
	 * @param active
	 *        是否有效
	 * @return 
	 */
	@Override
	public List<BillPaymentEntity> queryBillPaymentByPaymentNOs(
			List<String> paymentNos, String active) {
		//付款单号集合不能为空
		if(CollectionUtils.isEmpty(paymentNos)){
			throw new SettlementException("查询付款单，输入的付款单号不能为空！");
		}
		return this.billPaymentEntityDao.queryBillPaymentByPaymentNOs(paymentNos, active);
	}

	 /** 
	  * 根据一到多个付款单id，获取一到多条付款单记录
	  * @author 045738-foss-maojianqiang
	  * @date 2012-12-1 上午8:59:50
	  * @param ids
	  *       付款单id集合
	  * @param active
	  *       是否有效
	  */
	@Override
	public List<BillPaymentEntity> queryBillPaymentByPaymentIds(
			List<String> ids, String active) {
		//ID集合不能为空
		if(CollectionUtils.isEmpty(ids)){
			throw new SettlementException("查询付款单，输入的Id集合不能为空！");
		}
		return this.billPaymentEntityDao.queryBillPaymentByPaymentIds(ids, active);
	}
	
	/**
	 * 根据一到多个来源单号，获取一到多条付款单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午11:50:48
	 * @param sourceBillNos  来源单号集合
	 * @param sourceBillType  来源单据类型
	 * @param active 是否有效
	 * @Deprecated 现在必须通过付款单明细上的来源号 关联查询到付款单            
	 * @return  List<BillPaymentEntity>
	 */
	@Override
	@Deprecated 
	public List<BillPaymentEntity> queryBillPaymentBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType,String active) {
		//来源单号集合不能为空
		if(CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("查询付款单，输入的来源单号集合不能为空！");
		}
		return this.billPaymentEntityDao.queryBillPaymentBySourceBillNOs(sourceBillNos,sourceBillType, active);

	}

	/**
	 *根据付款单号，或来源单号、
	 *单据类型等查询条件查询付款单信息 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午11:51:04
	 * @param dto
	 *       存放付款单号和付款单的来源单号
	 * @return 
	 */
	@Override
	public List<BillPaymentEntity> queryBillPaymentByCondition(
			BillPaymentConditionDto dto) {
		
		//付款单号或来源单号不为空
		if(dto!=null&&(StringUtils.isNotEmpty(dto.getPaymentNo())||
				StringUtils.isNotEmpty(dto.getSourceBillNo())
				)){
			
			//有效
			dto.setActive(FossConstants.ACTIVE);
			
			//非红单
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			return this.billPaymentEntityDao.queryBillPaymentByCondition(dto);
		}else{
			throw new SettlementException("查询付款单，输入的单据号不能为空！");
		}
	}

	/**
	 * 根据一到多个付款单号，获取一到多条付款单的汇款状态
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 下午1:48:09
	 * @param paymentNos
	 *        付款单号集合
	 * @param active
	 *        是否有效
	 * @return 
	 */
	@Override
	public List<BillPaymentEntity> queryPaymentRemitStatusByPaymentNOs(
			List<String> paymentNos, String active) {
		//付款单号集合不能为空
		if(CollectionUtils.isEmpty(paymentNos)){
			throw new SettlementException("查询付款单，输入的付款单号不能为空！");
		}
		List<BillPaymentEntity> list=this.billPaymentEntityDao.queryPaymentRemitStatusByPaymentNOs(paymentNos, active);
		return list;
	}

	/**
	 * 根据外发单号和外发代理编码是否已存在有效的付款单信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午7:34:01
	 * @param dto
	 *        存放外放单号和外发代理编码
	 * @return
	 */
	@Override
	public int queryPaymentByExternalBillNo(BillPaymentConditionDto dto) {
		//查询接口参数不能为空
		//来源单号不能为空
		//代理编码不能为空
		if(dto==null||StringUtils.isEmpty(dto.getSourceBillNo())
				||StringUtils.isEmpty(dto.getPartailLineAgentCode())
				){
			throw new SettlementException("查询偏线付款单，输入的参数信息不能为空！");
		}
		//只查询有效数据
		dto.setActive(FossConstants.ACTIVE);
		
		//查询非红单数据
		dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		return this.billPaymentEntityDao.queryPaymentByExternalBillNo(dto);
	}

	/**
	 * 批量更付款单的付款状态  dto--需要封装 汇款状态、付款单集合
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:08:18
	 * @param  dto
	 *      存放付款单集合
	 */
	@Override
	public void batchReverseRemitStatusBillPayment(BillPaymentDto dto,
			CurrentInfo currentInfo) {
		logger.info("批量 更新付款单汇款状态 Start ");
		
		//参数不能为空
		//付款单集合不能为空
		if(dto==null||CollectionUtils.isEmpty(dto.getBillPayments())){
			throw new SettlementException("批量更付款单的付款状态参数不能为空！");
		}
		
		//判断汇款状态，汇款状态不能为空
		if(StringUtils.isBlank(dto.getRemitStatus())){
			throw new SettlementException("汇款状态不能为空！");
		}
		
		//有效
		dto.setActive(FossConstants.ACTIVE);
		
		//设置修改时间
		dto.setModifyTime(new Date());
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());
		
		//调用dao层，修改方法，返回结果
		int i=this.billPaymentEntityDao.batchReverseRemitStatusBillPayment(dto);
		
		//dao层，保存修改数据之后，返回值和传入的付款单集合进行匹对
		if(i!=dto.getBillPayments().size()){
			throw new SettlementException("批量审核付款单失败！");
		}
		logger.info("批量 更新付款单汇款状态 end ");
	}
	
	/** 
	 * 根据付款单号集合，批量更新付款状态
	 * @author 302346-foss-Jaing Xun
	 * @date 2016-06-02 上午11:39:00
	 * @param dto 付款单DTO
     * @return
	 */
	@Override
	public void updateRemitStatusByPmtNos(BillPmtAutoPayDto dto) {
		logger.info("根据付款单号集合，批量更新付款状态 begin");
		int i=this.billPaymentEntityDao.updateRemitStatusByPmtNos(dto);
		if(i!=dto.getPaymentNoList().size()){
			throw new SettlementException("根据付款单号集合，批量更新付款状态失败！");
		}
		logger.info("根据付款单号集合，批量更新付款状态 end。更新行数:"+i+",总数量："+dto.getPaymentNoList().size());
	}

	/** 
	 * 根据批次号（即付款编号）来查询付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午5:08:42
	 * @param batchNos
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService#queryPaymentByBatchNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BillPaymentEntity> queryPaymentByBatchNos(String payNos,
			String active) {
		//付款编号为空，则抛出异常
		if(StringUtils.isBlank(payNos)){
			throw new SettlementException("查询付款单，输入的付款编号不能为空！");
		}
		
		//传入付款单编号，调用Dao层方法返回付款单集合
		List<BillPaymentEntity> list=this.billPaymentEntityDao.queryPaymentByBatchNos(payNos, active);
		return list;
	}

	/**
	 * 根据来源单号集合和部门编码集合，查询付款单信息 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:36:34
	 * @param sourceBillNos 来源单号
	 * @param sourceBillType 来源单据类型
	 * @param orgCodes 部门编码集合，可以为空
	 * @param active 是否有效
	 * @return List<BillPaymentEntity>
	 */
	@Override
	public List<BillPaymentEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, String sourceBillType,
			List<String> orgCodes, String active,CurrentInfo currentInfo) {
		//来源单号集合不能为空
		if(CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("根据来源单号集合和部门编码集合，查询付款单信息参数不能为空！");
		}
		
		//调用dao层查询方法
		return this.billPaymentEntityDao.queryBySourceBillNOsAndOrgCodes
				(sourceBillNos, sourceBillType, orgCodes, active,currentInfo);
	}

	/**
	 * 根据单个付款单号，查询付款单的汇款状态
	 * 付款单号不能为空，
	 * 是否有效可以为空
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-7 下午4:59:49
	 * @param paymentNo
	 * 			付款单号
	 * @param active
	 *          是否有效
	 * @return
	 */
	@Override
	public String queryPaymentRemitStatusByPaymentNO(String paymentNo,
			String active) {
		//付款单号为空，不能进行后续的查询操作
		if(StringUtils.isEmpty(paymentNo)){
			throw new SettlementException("根据来付款单号查询付款单状态，参数不能为空！");
		}
		
		//调用dao层方法进行查询
		return this.billPaymentEntityDao.queryPaymentRemitStatusByPaymentNO(paymentNo, active);
	}
	/**
	 * 新增代打木架对账单
	 * @author ddw
	 * @date 2014-06-17
	 */
	@Override
	public void addBillWoodenPayment(BillPaymentEntity entity){
		this.add(entity);
	}
	
	/**
	 * 新增家装付款单
	 * @author zya
	 * @date 2014-06-17
	 */
	@Override
	public void addBillDopPayment(BillPaymentEntity entity){
		this.add(entity);
	}
	
	/**
	 * 新增合伙人奖罚付款单
	 * @author gxr
	 * @date 2016-02-26
	 */
	@Override
	public void addBillPAwardPayment(BillPaymentEntity entity) {
		this.add(entity);
		
	}
	
	/**
	 * 新增自动付款付款单
	 * @author Jiang Xun
	 * @date 2016-05-18
	 */
	public void addBilAutoPayPayment(BillPaymentEntity entity){
		this.add(entity);
	}
	
	/**
	 * 保存付款单和付款单明细记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-17 下午3:44:54
	 * @param entity
	 * 			付款单不能为空
	 * @param paymentDetails
	 *         付款单明细数据不能为空
	 * @param currentInfo
	 *        当前操作者信息
	 */
	@Override
	public void addBillPaymentAndDetails(BillPaymentEntity entity,
			List<BillPaymentDEntity> paymentDetails, CurrentInfo currentInfo) {
		
		logger.info("start  保存付款单和保存付款单明细");
		
		//一个付款单，最少有一条付款单明细记录
		if(CollectionUtils.isEmpty(paymentDetails)){
			throw new SettlementException("新增付款单明细不能为空！");
		}
		
		//调用上面的保存单条付款单方法
		addBillPayment(entity,currentInfo);
		
		//添加付款单明细
		for(BillPaymentDEntity dEntity:paymentDetails){
			
			//ID主键
			dEntity.setId(UUIDUtils.getUUID());
			
			//付款单号
			dEntity.setPaymentNo(entity.getPaymentNo());
			
			//付款单的记账日期
			dEntity.setPaymentAccountDate(entity.getAccountDate());
			
			//创建时间
			dEntity.setCreateTime(new Date());
			
			//调用付款单明细Service方法进行保存
			billPaymentDService.addBillPaymentD(dEntity);
		}
		
		logger.info("start  保存付款单和保存付款单明细");
		
	}

	@Override
	public void updatePaymentBatchNoBack(BillPaymentDto billPaymentDto,
			CurrentInfo currentInfo) {

		logger.info("批量 还原付款单汇款状态 Start ");
		
		//参数不能为空
		//付款单集合不能为空
		if(billPaymentDto==null||CollectionUtils.isEmpty(billPaymentDto.getBillPayments())){
			throw new SettlementException("批量更付款单的付款状态参数不能为空！");
		}
		
		//判断汇款状态，汇款状态不能为空
		if(StringUtils.isBlank(billPaymentDto.getRemitStatus())){
			throw new SettlementException("汇款状态不能为空！");
		}
		
		//有效
		billPaymentDto.setActive(FossConstants.ACTIVE);
		
		//设置修改时间
		billPaymentDto.setModifyTime(new Date());
		
		//修改人编码
		billPaymentDto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		billPaymentDto.setModifyUserName(currentInfo.getEmpName());
		
		//调用dao层，修改方法，返回结果
		int i=this.billPaymentEntityDao.updatePaymentBatchNoBack(billPaymentDto);
		
		//dao层，保存修改数据之后，返回值和传入的付款单集合进行匹对
		if(i!=billPaymentDto.getBillPayments().size()){
			throw new SettlementException("批量审核付款单失败！");
		}
		logger.info("批量还原付款单汇款状态 end ");
	}
	
	/**
	 * 修改付款单转报销工作流号
	 * @param map
	 * @return
	 */
	@Override
	public int updatePaymentByBatchNo(Map<Object, String> map) {
		int rownom = billPaymentEntityDao.updatePaymentByBatchNo(map);
		return rownom;
	}
	
	/** 
	 * 根据汇款状态、付款类型，查询应付单
	 * @author 231438
	 * @date 2016-06-06 上午:47:00
	 * @param entity 付款单ENTITY
     * @return 付款单集合
	 */
	@Override
	public List<BillPaymentEntity> queryBillPaymentByPmtType(BillPaymentEntity entity){
		if(entity == null){
			throw new SettlementException("合伙人自动付款重推查询付款单：查询参数实体不能为空！");
		}
		if(CollectionUtils.isEmpty(entity.getPaymentTypes())){
			throw new SettlementException("合伙人自动付款重推查询付款单：付款单类型 不能为空");
		}
		if(StringUtils.isEmpty(entity.getRemitStatus())){
			throw new SettlementException("合伙人自动付款重推查询付款单：汇款状态不能为空");
		}
		entity.setActive("Y");
		return billPaymentEntityDao.queryBillPaymentByPmtType(entity);
	}
	
	/**
	 * 合伙人到付运费自动付款重推更新付款单信息
	 * @author 231438
	 * @param billPaymentDto
	 * @return int
	 */
	@Override
    public int updateFcusPaymentByPaymentNos(BillPmtAutoPayDto billPaymentDto){
		if(null == billPaymentDto){
			throw new SettlementException("合伙人自动付款重推更新付款单：参数实体不能为空！");
		}
		if(StringUtils.isEmpty(billPaymentDto.getRemitStatus())){
			throw new SettlementException("合伙人自动付款重推更新付款单：汇款状态不能为空");
		}
		if(null == billPaymentDto.getModifyTime()){
			throw new SettlementException("合伙人自动付款重推更新付款单：更新时间不能为空");
		}
    	return billPaymentEntityDao.updateFcusPaymentByPaymentNos(billPaymentDto);
    }
    
	/**
	 * @param billPaymentDService the billPaymentDService to set
	 */
	public void setBillPaymentDService(IBillPaymentDService billPaymentDService) {
		this.billPaymentDService = billPaymentDService;
	}



}