package com.deppon.foss.module.settlement.financeitf.server.esb;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.finance.CashIncomeProcessType;
import com.deppon.esb.inteface.domain.finance.CashIncomeRptType;
import com.deppon.esb.inteface.domain.finance.UpdateCashincomerpRequestType;
import com.deppon.esb.inteface.domain.finance.UpdateCashincomerpResponseType;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.UpdateCashIncomerptDto;

/**
 * 更新缴款信息（已缴款和未缴款金额）
 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
 * @author 095793-foss-LiQin
 * @date 2012-12-14 下午2:30:21
 */
public class UpdateCashIncomerptProcessor implements IProcess {

	/**
	 * 现金缴款管理service
	 */
	private IReportCashRecPayInService reportCashRecPayInService;
	
	/**
	 * 查询部门组织service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 更新现金缴款信息logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(UpdateCashIncomerptProcessor.class);

	/**
	 * 更新缴款信息接口
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-18 上午9:38:16
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object obj) throws ESBBusinessException {

		LOGGER.info("开始更新缴款信息。");
		// 获取接口参数
		UpdateCashincomerpRequestType request = (UpdateCashincomerpRequestType) obj;
		// 接口返回信息
		UpdateCashincomerpResponseType response = new UpdateCashincomerpResponseType();
		// 合计变量
		int totalNum = 0;
		// 流水号变量
		String serialNO = request.getSerialNO();
		
		// 部门标杆标杆编码存放
		Map<String ,String> deptCode = new HashMap<String ,String>();
		
		// -----------------如果流水号为空，则默认全部失败不作处理------------------------------
		// -----------------如果流水号重复，则默认全部失败不作处理------------------------------
		// -----------------否则，进行缴款处理-------------------------------------------
		if (StringUtil.isEmpty(serialNO)) {
			LOGGER.info("serialNO 为空，不能进行缴款操作。");
			response.setSuccessCount(0);
		} /*
			// 去掉交账反交账流水号重复验证
			else 	if (reportCashRecPayInService.isExistSerino(serialNO)) {
			LOGGER.info(serialNO + "已经缴款，不能再次缴款。");
			response.setSuccessCount(0);
			response.setSerialNO(serialNO);
		}*/ else {		
			for (CashIncomeRptType cashType : request.getCashIncomeRpts()) {
				
				// 处理明细实例化
				CashIncomeProcessType processType = new CashIncomeProcessType();
				try {
					// 校验参数
					checkCashRecReportParm(cashType, serialNO);
					
					// 打印日志
					LOGGER.info("更新缴款信息流水号:" + serialNO);
					LOGGER.info("更新缴款信息金额:" + cashType.getPayAmount());
					LOGGER.info("更新缴款信息部门:" + cashType.getDeptCd());
					LOGGER.info("更新缴款信息付款方式" + cashType.getPayMethod());
					
					// 如果MAP中不存在此编码，则查询放入
					if (deptCode.get(cashType.getDeptCd()) == null) {
						// 转换标杆编码
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(cashType.getDeptCd());
						deptCode.put(orgAdministrativeInfoEntity.getUnifiedCode(),orgAdministrativeInfoEntity.getCode());
					}
					
					// 封装参数传入后台进行业务处理
					UpdateCashIncomerptDto dto = new UpdateCashIncomerptDto();
					dto.setSerialNO(serialNO);
					dto.setPayAmount(cashType.getPayAmount());
					dto.setDeptCd(deptCode.get(cashType.getDeptCd()));
					dto.setPayMethod(cashType.getPayMethod());
					dto.setUnifiedCode(cashType.getDeptCd());
					
					// 调用业务接口进行业务处理
					// 返回参数map封装成功条数和核销金额
					Map<String, Object> map = reportCashRecPayInService.updateCashIncomerptForProcessor(dto);
					// 总数+1
					totalNum = totalNum + (Integer) map.get("num");
					// 封装明细参数成功、部门、核销金额
					processType.setWritteOff((BigDecimal) map.get("amountUse"));
					processType.setIsSuccess(true);
					processType.setDeptCd(cashType.getDeptCd());
					processType.setCodeNum(cashType.getCodeNum());
					
					response.getProcessDetails().add(processType);
				} catch (BusinessException ex) {
					LOGGER.error("缴款交账错误，" + ex.getErrorCode(), ex);
					
					String str = "";
					if(cashType.getPayAmount().compareTo(BigDecimal.ZERO) == -1) {
						str = "取消交账 ";
					}
					
					processType.setFailedReason(str + ex.getErrorCode());
					processType.setIsSuccess(false);
					processType.setDeptCd(cashType.getDeptCd());
					processType.setCodeNum(cashType.getCodeNum());
					
					// 添加返回明细
					response.getProcessDetails().add(processType);
				} catch (Exception e) {
					LOGGER.error("缴款交账发生未知错误，" + e.getMessage(), e);
					
					String str = "";
					if(cashType.getPayAmount().compareTo(BigDecimal.ZERO) == -1) {
						str = "取消交账 ";
					}
					
					processType.setFailedReason(str + e.getMessage());
					processType.setIsSuccess(false);
					processType.setDeptCd(cashType.getDeptCd());
					processType.setCodeNum(cashType.getCodeNum());
					
					// 添加返回明细
					response.getProcessDetails().add(processType);
				}
			}
			// 封装返回参数
			response.setSerialNO(serialNO);
			response.setSuccessCount(totalNum);
			LOGGER.info("结束更新缴款信息" + "成功条数:" + response.getSuccessCount()+ "，流水号:" + serialNO);
		}
		return response;

	}
	
	/**
	 * 校验，财务自助传递的参数
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin`
	 * @date 2012-12-14 下午2:42:32
	 */
	private void checkCashRecReportParm(CashIncomeRptType cashType,
			String serialNO) {
		
		/*MANA- 092036-BOCHENLONG 负数时，反核销*/
		// 缴款金额
		if (cashType.getPayAmount().compareTo(BigDecimal.ZERO) == 0) {
			throw new SettlementException("流水号：" + serialNO + "更新缴款明细，缴款金额不能为0");
		}  
		

		// 缴款部门编码
		if (StringUtil.isEmpty(cashType.getDeptCd())) {
			throw new SettlementException("流水号：" + serialNO + "更新现金明细,部门编码不能为空");
		}

		// 付款方式 ISSUE-3360 ADD
		if (StringUtil.isEmpty(cashType.getPayMethod())) {
			throw new SettlementException("流水号：" + serialNO + "更新缴款明细，付款方式不能为空");
		}
		
		if (StringUtil.isEmpty(cashType.getCodeNum())) {
			throw new SettlementException("流水号：" + serialNO + "更新缴款明细，汇款编号不能为空");
		}
	}
	

	/**
	 * 错误处理
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-14 下午2:30:01
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// 错误日志
		LOGGER.error(req.getClass().getName() + "更新盘点缴款报表错误！");
		return null;
	}

	/**
	 * 现金收入（缴款）报表服务set
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-18 上午9:43:45
	 */
	public void setReportCashRecPayInService(
			IReportCashRecPayInService reportCashRecPayInService) {
		this.reportCashRecPayInService = reportCashRecPayInService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	

}
