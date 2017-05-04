package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillAdvancedPaymentEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillAdvancedPaymentEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预付单公共
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 下午1:12:37
 */
public class BillAdvancedPaymentService implements
		IBillAdvancedPaymentService {
	
	/**
	 * 预付单Dao
	 */
	private IBillAdvancedPaymentEntityDao billAdvancedPaymentEntityDao;
	
	/**
	 * 控制日志
	 */
	private static final Logger logger = LogManager.getLogger(BillAdvancedPaymentService.class);
	
	
	/**
	 * 修改预付单的对账单号及是否生成对账单字段值
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午7:30:36
	 * @param entity
	 *         预付单实体
	 * @param  currentInfo
	 *         当前操作者
	 */
	@Override
	public int updateBillAdvancedPaymentByMakeStatement(
			BillAdvancedPaymentEntity entity,CurrentInfo currentInfo) {
		return billAdvancedPaymentEntityDao.updateBillAdvancedPaymentByMakeStatement(entity);
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条预付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-18 上午11:52:05
	 * @param sourceBillNos
	 *            来源单号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	public List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentNos(
			List<String> advancesNos, String active){
		return billAdvancedPaymentEntityDao.queryBillAdvancedPaymentNos(advancesNos, active);
	}
	
	/**
	 * 核销空运预付的金额等字段信息
	 * @author foss-pengzhen
	 * @date 2012-10-22 下午1:54:20
	 * @param  entity
	 * @param  writeoffAmount
	 * @param  currentInfo
	 * @return
	 * @see
	 */
	public void writeoffAdvancedPayment(BillAdvancedPaymentEntity entity, 
			BigDecimal writeoffAmount,CurrentInfo currentInfo){
		//writeoffAmount核销金额必须大于0
		if(entity == null || BigDecimal.ZERO.equals(writeoffAmount)){
			throw new SettlementException("核销预付单的参数不能为空");
		}
		int i = billAdvancedPaymentEntityDao
			.writeoffAdvancedPayment(entity, writeoffAmount);
		if (i != 1) {
			throw new SettlementException(entity.getAdvancesNo()+":预付单金额已发生变化，请刷新后再试");
		}
		// 核销之后，其他地方还需要使用，为了保持java方法中的版本号和数据库中的版本号统一，在这里+1
		entity.setVersionNo((short) (entity.getVersionNo() + 1));
	}
	
	/**
	 * 根据单个单号查询空运预付单数据
	 * @author foss-pengzhen
	 * @date 2012-10-23 下午5:40:56
	 * @param advancesNo
	 * @param active
	 * @return
	 * @see
	 */
	public BillAdvancedPaymentEntity queryBillAdvancedPaymentNo(String advancesNo,String active){
		return billAdvancedPaymentEntityDao.queryBillAdvancedPaymentNo(advancesNo, active);
	}
	
	/**
	 * 新增预付单
	 * @author foss-pengzhen
	 * @date 2012-11-12 下午5:30:59
	 * @param entity
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public int addAdvancedPaymentEntity(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo){
		//实体不能为空
		if(entity != null){
			
			//新增预付单-验证必填项数据不能为空
			if(StringUtils.isEmpty(entity.getId())){
				throw new SettlementException("预付单id不能为空！");
			}
			
			//预付单号不能为空
			if(StringUtils.isEmpty(entity.getAdvancesNo())){
				throw new SettlementException("预付单号不能为空！");
			}
			
			//金额不能为空
			if(null==entity.getAmount()){
				throw new SettlementException("金额不能为空！");
			}
			
			if(null==entity.getUnverifyAmount()){
				throw new SettlementException("未核销金额不能为空！");
			}
			if(entity.getVerifyAmount() == null){
				throw new SettlementException("已核销金额不能为空！");
			}
			
			//应收单的客户信息不能为空
			if(StringUtils.isEmpty(entity.getCustomerCode())){
				throw new SettlementException("客户编码不能为空！");
			}
			if(StringUtils.isEmpty(entity.getCustomerName())){
				throw new SettlementException("客户名称不能为空！");
			}
			
			//申请部门信息不能为空
			if(StringUtils.isEmpty(entity.getApplyOrgCode())){
				throw new SettlementException("申请部门编码不能为空！");
			}
			if(StringUtils.isEmpty(entity.getApplyOrgName())){
				throw new SettlementException("申请部门名称不能为空！");
			}
			if(StringUtils.isEmpty(entity.getActive())){
				throw new SettlementException("版本不能为空！");
			}
			if(StringUtils.isEmpty(entity.getIsRedBack())){
				throw new SettlementException("红单不能为空");
			}
			
			//业务日期和记账日期为必填项
			if(entity.getBusinessDate() == null){
				throw new SettlementException("业务日期不能为空！");
			}
			if(entity.getAccountDate() == null ){
				throw new SettlementException("记账日期不能为空！");
			}
			if(entity.getCreateTime() == null){
				throw new SettlementException("创建时间不能为空");
			}
			
			//预收单
			if(StringUtils.isEmpty(entity.getBillType())){
				throw new SettlementException("单据类型不能为空！");
			}
			if(StringUtils.isEmpty(entity.getIsInit())){
				throw new SettlementException("初始化状态不能为空！");
			}
			if(entity.getVersionNo() == null){
				throw new SettlementException("版本号不能为空！");
			}
			return billAdvancedPaymentEntityDao.addAdvancedPaymentEntity(entity);
		}
		return 0;
	}
	
	/**
	 * 修改审批状态
	 * @author foss-pengzhen
	 * @date 2013-1-7 下午4:24:23
	 * @param entity
	 * @return
	 * @see
	 */
	public int updateAdvancePaymentAuditStatus(BillAdvancedPaymentEntity entity){
		
		//接口参数不能为空
		if(entity == null){
			throw new SettlementException("修改预付单传入的参数为空！");
		}
		
		//Id不能为空
		if(StringUtils.isEmpty(entity.getId())){
			throw new SettlementException("修改预付单传入的ID号为空！");
		}
		
		//审批状态不能为空
		if(StringUtils.isEmpty(entity.getAuditStatus())){
			throw new SettlementException("修改预付单传入的审批状态为空！");
		}
		
		//预付单的版本号不能为空
		if(StringUtils.isEmpty(entity.getVersionNo().toString())){
			throw new SettlementException("修改预付单传入的版本号为空！");
		}
		entity.setBusinessDate(new Date());
		
		return billAdvancedPaymentEntityDao.updateAdvancePaymentAuditStatus(entity);
	}
	
	/**
	 * 根据预付单，更新费控产生工作流号到Foss
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午4:14:12
	 */
	public int updatePaymentBillWorkFlow(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo){
		if(null==entity){
			throw new SettlementException("更新费控产生工作流号错误，传入实体为空！");
		}
		
		//版本号、预付单号
		if(null==entity.getVersionNo()){
			throw new SettlementException("版本号,不能为空！");
		}
		if(StringUtil.isEmpty(entity.getAdvancesNo())){
			throw new SettlementException("预付单号,不能为空!");
		}
		if(StringUtil.isEmpty(entity.getWorkflowNo())){
			throw new SettlementException("工作流号,不能为空!");
		}
		
		if(StringUtil.isEmpty(entity.getAuditStatus())){
			throw new SettlementException("审核状态,不能为空!");
		}
		
		if(StringUtil.isEmpty(entity.getId())){
			throw new SettlementException("预付单号ID,不能为空!");
		}
		
		//获取当前时间
		Date modifyTime = Calendar.getInstance().getTime();
		entity.setModifyTime(modifyTime);
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());
		return billAdvancedPaymentEntityDao.updatePaymentBillWorkFlow(entity);
	}
	
	/**
	 * 更新费控返回审批结果
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午8:07:40
	 */
	public int updatePaymentBillResult(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo){
		if(null==entity){
			throw new SettlementException("更新审批结果时，传入实体为空!");
		}
		if(null==entity.getVersionNo()){
			throw new SettlementException("版本号不能为空！");
		}
		if(StringUtil.isEmpty(entity.getAdvancesNo())){
			throw new SettlementException("预付单号不能为空!");
		}
		if(StringUtil.isEmpty(entity.getAuditStatus())){
			throw new SettlementException("审核状态不能为空!");
		}
		//获取当前时间
		Date modifyTime = Calendar.getInstance().getTime();
		entity.setModifyTime(modifyTime);
		//当前修改人
		entity.setModifyUserCode(currentInfo.getEmpCode());
		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());
		return billAdvancedPaymentEntityDao.updatePaymentBillResult(entity);
	}
	
	/**
	 * 作废预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午6:33:02
	 */
	public int writeBackAdvancePay(BillAdvancedPaymentEntity entity,
			CurrentInfo currentInfo){
		
		// 输入参数校验
		if(null==entity){
			throw new SettlementException("作废预付单实体时，传入实体为空!");
		}
		if(null==entity.getVersionNo()){
			throw new SettlementException("版本号不能为空！");
		}
		if(StringUtil.isEmpty(entity.getAdvancesNo())){
			throw new SettlementException("预付单号不能为空!");
		}
		if(StringUtil.isEmpty(entity.getActive())){
			throw new SettlementException("预付单，有效状态不能为空!");
		}
		if(StringUtil.isEmpty(entity.getId())){
			throw new SettlementException("红冲预付单ID，不能为空!");
		}
		
		logger.info("---Start 作废预付单，预付单号：" + entity.getAdvancesNo());
		
		//获取当前时间
		Date modifyTime = Calendar.getInstance().getTime();
    	entity.setModifyTime(modifyTime);
    	//获取操作人编号
		entity.setModifyUserCode(currentInfo.getEmpCode());
		//获取操作人名称
		entity.setModifyUserName(currentInfo.getEmpName());
		
		int i = billAdvancedPaymentEntityDao.writeBackAdvancePay(entity);
		if (i != 1) {
			throw new SettlementException("作废预付单出错！");
		}
		
		logger.info("---End 作废预付单，预付单号：" + entity.getAdvancesNo());
		
		return i;
	}
	
	/**
	 * 批量修改预付单的对账单单号
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-4 下午6:34:13
	 * @param dto
	 */
	@Override
	public void batchUpdateByMakeStatement(BillAdvancedPaymentEntityDto dto, CurrentInfo currentInfo) {
		
		// 输入参数校验
		if(dto==null||StringUtils.isEmpty(dto.getStatementBillNo())
				||CollectionUtils.isEmpty(dto.getBillAdvancedPayment())
				){
			throw new SettlementException("修改预付单对账单号传入参数不能为空！");
		}
		if (dto.getBillAdvancedPayment().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("预付单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "，不能进行修改操作");
		}
		
		logger.info("---Start 批量修改预付单的对账单单号");
		
		//是否有效
		dto.setActive(FossConstants.ACTIVE);//是否有效设置有效
		
		//修改时间
		dto.setModifyTime(new Date());
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());
		
		int i = this.billAdvancedPaymentEntityDao.updateBatchByMakeStatement(dto);
		if (i != dto.getBillAdvancedPayment().size()) {
			throw new SettlementException("批量修改预付单的对账单单号服务失败");
		}
		
		logger.info("---End 批量修改预付单的对账单单号");
	}

	
	
	
	/**
	 * 更新费控返回审批结果，审批失败
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午8:07:40
	 */
	@Override
	public int updatePaymentBillResultFail(BillAdvancedPaymentEntity entity,
			CurrentInfo currentInfo) {
		
		if(null==entity){
			throw new SettlementException("更新审批结果时，传入实体为空!");
		}
		if(null==entity.getVersionNo()){
			throw new SettlementException("版本号不能为空！");
		}
		if(StringUtil.isEmpty(entity.getAdvancesNo())){
			throw new SettlementException("预付单号不能为空!");
		}
		if(StringUtil.isEmpty(entity.getAuditStatus())){
			throw new SettlementException("审核状态不能为空!");
		}
		
		logger.info("---start修改预付单审批状态为失败和单据为无效，预付单号：" + entity.getAdvancesNo());
		//获取当前时间、当前修改人、修改人名称
		Date modifyTime = Calendar.getInstance().getTime();
		entity.setModifyTime(modifyTime);
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());
		
		int i = billAdvancedPaymentEntityDao.updatePaymentBillResultFail(entity);
		if (i != 1) {
			throw new SettlementException("修改预付单审批状态为失败和单据为无效，预付单出错。预付单号"+entity.getAdvancesNo());
		}
		logger.info("---End 作废预付单，预付单号：" + entity.getAdvancesNo());
		return i;				
	}

	
	/**
	 * @param billAdvancedPaymentEntityDao the billAdvancedPaymentEntityDao to set
	 */
	public void setBillAdvancedPaymentEntityDao(
			IBillAdvancedPaymentEntityDao billAdvancedPaymentEntityDao) {
		this.billAdvancedPaymentEntityDao = billAdvancedPaymentEntityDao;
	}
	
}
