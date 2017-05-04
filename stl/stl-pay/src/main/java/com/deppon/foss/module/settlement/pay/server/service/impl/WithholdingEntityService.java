/**
 * 
 */
package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWithholdingEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IWithholdingEntityDetailService;
import com.deppon.foss.module.settlement.pay.api.server.service.IWithholdingEntityService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;

/**
 * @author 045738
 * 预提状态
 */
public class WithholdingEntityService implements IWithholdingEntityService {

	//打印日志使用
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WithholdingEntityService.class);
	
	/**
	 * 注入dao
	 */
	private IWithholdingEntityDao withholdingEntityDao;
	
	/**
	 * 注入预提sercie
	 */
	private IWithholdingEntityDetailService withholdingEntityDetailService;
	
	/**
	 * 注入临时租车service
	 */
	private ITemprentalMarkService temprentalMarkService;
		
	/**
	 * 功能：根据传入的单号，类型，和预提状态来查询。
	 * 		单号、类型为必填项，预提状态为非必填
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public List<WithholdingEntity> selectByNosAndType(List<String> billNos,
			String billType, List<String> status, String active) {
		//校验查询单号
		if(CollectionUtils.isEmpty(billNos)){
			throw new SettlementException("单号不能为空！");
		}
		//校验单号类型
		if(StringUtils.isEmpty(billType)){
			throw new SettlementException("查询单号的类型不能为空");
		}
		return withholdingEntityDao.selectByNosAndType(billNos, billType, status, active);
	}
	

	/**
	 * 功能：根据传入的单号，类型，和预提状态来查询。
	 * 		单号、类型为必填项，预提状态为非必填
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public void add(WithholdingEntity entity, CurrentInfo currentInfo) {
		//预提记录
		if(entity == null){
			throw new SettlementException("新增预提单参数不能为空！");
		}
		Date nowDate = new Date();
		LOGGER.info("successfully entering service, withholdingNo: "+entity.getWithholdingNo());
		//校验传入参数
		if(StringUtils.isBlank(entity.getWithholdingNo())){
			throw new SettlementException("预提单号不能为空");
		}
		if(StringUtils.isBlank(entity.getCostDeptCode())){
			throw new SettlementException("费用承担部门不能为空");
		}
		if(StringUtils.isBlank(entity.getCostType())){
			throw new SettlementException("费用类型不能为空");
		}
		if(entity.getCostDate()==null){
			throw new SettlementException("费用所属月份不能为空");
		}
		validaExtracted(entity);
		//创建时间
		entity.setCreateTime(nowDate);
		//创建人
		entity.setCreateUserCode(currentInfo.getEmpCode());
		entity.setCreateUserName(currentInfo.getEmpName());
		//最后修改时间
		entity.setModifyTime(nowDate);
		//创建时间
		entity.setModifyUserCode(currentInfo.getEmpCode());
		//创建时间
		entity.setModifyUserName(currentInfo.getEmpName());
		withholdingEntityDao.insert(entity);	
		
		LOGGER.info("successfully exit service: "+entity.getWithholdingNo());
	}


	private void validaExtracted(WithholdingEntity entity) {
		if(StringUtils.isBlank(entity.getInvoiceCode())){
			throw new SettlementException("发票抬头不能为空");
		}
		if(entity.getAmount()==null){
			throw new SettlementException("预提金额不能为空");
		}
		if(StringUtils.isBlank(entity.getNotes())){
			throw new SettlementException("申请事由及说明");
		}
		if(StringUtils.isBlank(entity.getWithholdingStatus())){
			throw new SettlementException("预提状态不能为空");
		}
		if(StringUtils.isBlank(entity.getCreateOrgCode())){
			throw new SettlementException("预提部门不能为空");
		}
		if(StringUtils.isBlank(entity.getActive())){
			throw new SettlementException("有效标志不能为空");
		}
	}

	/** 
	 * <p>反写预提状态到预提单实现方法</p> 
	 * @author 105762
	 * @date 2014-7-10 下午3:22:59
	 * @param esbHeader
	 * @param parameters
	 * @return
	 * @see com.deppon.foss.withholdingservice.IWithholdingService#rewriteWithholdingState(javax.xml.ws.Holder, com.deppon.foss.inteface.domain.stl.WithholdingRequest)
	 */
	@Override
	public boolean rewriteWithholdingState(WithholdingDto dto) {
		// 非空校验
		SettlementUtil.valideIsNull(dto, "传入参数dto为空");
		SettlementUtil.valideIsNull(dto.getWorkflowNo(), "传入参数 预提工作流号 为空");
		SettlementUtil.valideIsNull(dto.isSuccess(), "传入参数 是否成功 为空");

		// 校验失败原因
		if (!dto.isSuccess()) {
			SettlementUtil.valideIsNull(dto.getReason(), "预提失败时失败原因不能为空");
		}
		//查询预提明细
		List<String> rentCarNos = withholdingEntityDetailService.selectRentCarNoByWorkFlowNo(dto.getWorkflowNo());
		//判断是否为空
		if(CollectionUtils.isEmpty(rentCarNos)){
			 throw new SettlementException(dto.getWorkflowNo()+"未找到对应的临时租车记录！");
		}
		 
		 /*
		 * 处理预提结果：成功
		 * 1. 更改预提单 预提状态为“已预提”
		 */
		boolean isSuccessful = true;
		if (dto.isSuccess()) {
			//声明信息
			RentalMarkDto rentMarkDto = new RentalMarkDto();
			rentMarkDto.setAccruedState(SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERED);
			rentMarkDto.setAccruedWorkNo(dto.getWorkflowNo());
			rentMarkDto.setRentCarNos(rentCarNos);
			try{
				//更新租车记录预提信息
				temprentalMarkService.updateTemprentalMarkAccrued(rentMarkDto);
			}catch(BusinessException e){
				throw new SettlementException(e);
			}
			//更新预提单号和应付单信息
			WithholdingEntity en = new WithholdingEntity();
			en.setWorkflowNo(dto.getWorkflowNo());//成功要把工作号写上去，否则会变成空。
			en.setModifyUserName(SettlementUtil.getFKCurrentInfo().getUserName());
			en.setWithholdingStatus(SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERED);
			isSuccessful = withholdingEntityDao.updateWithholdingSuccessByWorkflowNo(en) > 0 ? true : false;
		} else {
			/*
			 * 处理预提结果：失败 
			 * 0. 更新中转临时租车上的预提记录
			 * 1. 清除对应应付单上的工作流号（实际为预提工作流号）
			 * 2. 更改预提单鱼体状态为“未预提”，清空工作流号
			 */
			//声明信息
			RentalMarkDto rentMarkDto = new RentalMarkDto();
			rentMarkDto.setAccruedState(SettlementDictionaryConstants.WITHHOLDING_STATUS_NOT_TRANSFER);//
			rentMarkDto.setAccruedWorkResult(dto.getReason());
			rentMarkDto.setRentCarNos(rentCarNos);
			try{
				//更新租车记录预提信息
				temprentalMarkService.updateTemprentalMarkAccrued(rentMarkDto);
			}catch(BusinessException e){
				throw new SettlementException(e);
			}
			
			// 1. 清除对应应付单上的工作流号（实际为预提工作流号）
			isSuccessful = withholdingEntityDao.wipePayableWorkflowNo(dto) > 0 ? true : false;

			// 2. 更改预提单预提状态为“未预提”，清空工作流号
			WithholdingEntity en = new WithholdingEntity();
			en.setWorkflowNo(dto.getWorkflowNo());
			en.setWithholdingStatus(SettlementDictionaryConstants.WITHHOLDING_STATUS_NOT_TRANSFER);
			en.setModifyUserName(SettlementUtil.getFKCurrentInfo().getUserName());
			en.setFailReasion(dto.getReason());
			if(isSuccessful){
				isSuccessful = withholdingEntityDao.updateWithholdingFailedByWorkflowNo(en) > 0 ? true : false;
			}
		}
		if (!isSuccessful) {
			throw new SettlementException("未找到预提工作流号对应的预提单或应付单");
		}
		return isSuccessful;
	}
	
	public IWithholdingEntityDao getWithholdingEntityDao() {
		return withholdingEntityDao;
	}

	public void setWithholdingEntityDao(IWithholdingEntityDao withholdingEntityDao) {
		this.withholdingEntityDao = withholdingEntityDao;
	}

	public void setWithholdingEntityDetailService(
			IWithholdingEntityDetailService withholdingEntityDetailService) {
		this.withholdingEntityDetailService = withholdingEntityDetailService;
	}

	public void setTemprentalMarkService(
			ITemprentalMarkService temprentalMarkService) {
		this.temprentalMarkService = temprentalMarkService;
	}
	
}
