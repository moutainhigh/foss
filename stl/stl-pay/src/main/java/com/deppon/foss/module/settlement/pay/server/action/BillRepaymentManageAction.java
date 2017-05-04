package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentApplyDisableService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillRepaymentDisableResultVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillRepaymentDisableVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillRepaymentManageResultVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillRepaymentManageVo;
import com.deppon.foss.util.DateUtils;

/**
 * 查询还款单Action
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-08 下午2:09:42
 */
public class BillRepaymentManageAction extends AbstractAction {

	/**
	 * Logger 查询还款单logger
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillRepaymentManageAction.class);

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 3003559619424852872L;
	
	
	/**
	 * 业务日期常量
	 */
	private static final String QUERY_BUSINESSDATE_FLAG = "1";

	/**
	 * 记账日期常量
	 */
	private static final String QUERY_ACCOUNTDATE_FLAG = "2";


	/**
	 * 查询还款单service
	 */
	private IBillRepaymentManageService billRepaymentManageService;
	
	/**
	 * 申请作废还款单service
	 */
	private IBillRepaymentApplyDisableService billRepaymentApplyDisableService;

	/**
	 * 还款单vo
	 */
	private BillRepaymentManageVo billRepaymentManageVo;

	/**
	 * 还款单ResultVo
	 */
	private BillRepaymentManageResultVo billRepaymentManageResultVo;
	
	/**
	 * 还款单申请作废vo
	 */
	private BillRepaymentDisableVo billRepaymentDisableVo;
	
	/**
	 * 还款单申请作废resultvo
	 */
	private BillRepaymentDisableResultVo billRepaymentDisableResultVo;
	
	/**
	 * 导出excel的文件名称
	 */
	private  String fileName;

	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream stream;
	public String exportBillRepayPaymentApply() {
		// 输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			LOGGER.debug("导出还款单申请begin....");
			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取界面 输入条件、当前登录用户信息不为空
			BillRepaymentDisableDto repayDto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			// 获取该用户所管辖的部门
			Set<String> orgCodes = currentInfo.getUser().getOrgids();
			if(CollectionUtils.isEmpty(orgCodes)) {
				throw new SettlementException("当前用户没有任何部门的权限");
			}
			
			//判空
			if (null == repayDto) {
				//抛出异常
				throw new SettlementException("导出时，传入条件不能为空!", "");
			}
			repayDto.setEmpCode(currentInfo.getEmpCode());
			// 结束日期加1天
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(repayDto.getQueryType())) {
				LOGGER.debug("导出还款单按日期查询....");
				if(repayDto.getApplyDateStart() == null || repayDto.getApplyDateEnd() == null) {
					throw new SettlementException("起止日期不能为空！");
				}
				repayDto.setApplyDateEnd(DateUtils.addDayToDate(repayDto.getApplyDateEnd(), 1));
			} else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO.equals(repayDto.getQueryType())) {
				LOGGER.debug("导出还款单按还款单查询.....");
				if (CollectionUtils.isEmpty(repayDto.getRepaymentNos())) {
					//抛出异常
					throw new SettlementException("还款单号,不能为空!");
				}
			} else{
				LOGGER.debug("导出还款单不存在的类型.....");
				//抛出异常
				throw new SettlementException("作废申请查询，错误不存在类型!");
			}
			// 设置excel名称
			try {
				this.setFileName(new String(("还款单作废申请").getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				//导出文件名编码转化错误
				LOGGER.error("导出还款单作废申请，文件编码转化异常"+e1.getMessage(), e1);
			}
			//执行还款单导出时 查询方法			
			HSSFWorkbook wookBook = billRepaymentApplyDisableService.queryExportBillRepaymentApply(repayDto, currentInfo);
			try {
				//写入输出流				
				wookBook.write(baos);
				//生成输入流
				stream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				//导出还款单异常				
				LOGGER.error("导出还款单作废申请输入流异常"+e.getMessage(),e);
			}
			//返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			//返回失败
			return returnError(e);

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					LOGGER.error("导出还款单作废申请关闭流异常"+e.getMessage(),e);
				}
			}
		}
	}
	
	/**作废明细查看*/
	@JSON
	public String applyDetailQuery() {
		try {
			LOGGER.info("开始新增还款单作废申请ACTION...");
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 登陆用户不能为空		
			if(null == cInfo){
				throw new SettlementException("作废还款单获取用户名为空", "");
			}
			BillRepaymentDisableDto dto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
	
			List<RepaymentDisableDetailEntity> detailEntities = billRepaymentApplyDisableService.queryDisableDetailByDto(dto);
			BigDecimal amountSum = BigDecimal.ZERO;
			for(RepaymentDisableDetailEntity entity:detailEntities) {
				amountSum = amountSum.add(entity.getAmount());
			}

			dto = new BillRepaymentDisableDto();
			dto.setDetails(detailEntities);
			resultDto.setBillRepaymentDisableDto(dto);
			resultDto.setTotalVerifyAmount(amountSum);
			resultDto.setTotalCount(detailEntities.size());
			billRepaymentDisableResultVo = new BillRepaymentDisableResultVo();
			billRepaymentDisableResultVo.setBillRepaymentDisableResultDto(resultDto);
			
			return returnSuccess();
		} catch (SettlementException e) {
			LOGGER.info("新增申请作废异常"+e.getErrorCode());
			return returnError(e.getErrorCode());
			
		}
	}
	/**
	 * 
	 *新增作废申请
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-27 下午12:28:41 
	 * @return
	 */
	@JSON
	public String addRepaymentApply() {
		try {
			LOGGER.info("开始新增还款单作废申请ACTION...");
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 登陆用户不能为空		
			if(null == cInfo){
				throw new SettlementException("作废还款单获取用户名为空", "");
			}
			BillRepaymentDisableDto dto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			//设置其他属性
			dto.setApplyOrgCode(cInfo.getCurrentDeptCode());
			dto.setApplyOrgName(cInfo.getCurrentDeptName());
			dto.setApplyUserCode(cInfo.getEmpCode());
			dto.setApplyUserName(cInfo.getEmpName());
			dto.setEmpCode(cInfo.getEmpCode());
			
			billRepaymentApplyDisableService.addDisableApplication(dto);
			
			return returnSuccess();
		} catch (SettlementException e) {
			LOGGER.info("新增申请作废异常"+e.getErrorCode());
			return returnError(e.getErrorCode());
			
		}
	}
	
	/**
	 * 
	 *申请作废（查询还款单）
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午4:00:19 
	 * @return
	 */
	@JSON
	public String disableBillRepaymentApply() {
		try {
			LOGGER.info("开始申请作废还款单ACTION ..");
			// 获取界面选中的还款单号
			String selectBillRepayNo = billRepaymentDisableVo.getBillRepaymentDisableDto().getRepaymentNo();
			// 还款单号不能为空
			if(StringUtils.isEmpty(selectBillRepayNo)) {
				throw new SettlementException("没有选择申请作废的还款单!");
			}
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 登陆用户不能为空		
			if(null == cInfo){
				throw new SettlementException("作废还款单获取用户名为空", "");
			}
			
			BillRepaymentDisableDto dto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			dto.setEmpCode(cInfo.getEmpCode());
			
			//BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
			BillRepaymentDisableResultDto resultDto = billRepaymentApplyDisableService.queryRepayment(dto);
			resultDto.getBillRepaymentDisableDto().setApplyOrgName(cInfo.getCurrentDeptName());
			
			billRepaymentDisableResultVo = new BillRepaymentDisableResultVo();
			billRepaymentDisableResultVo.setBillRepaymentDisableResultDto(resultDto);
			
			return returnSuccess();
		} catch (SettlementException e) {
			LOGGER.error("申请作废还款单异常"+e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 
	 *申请作废（查询还款单明细）
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午4:00:19 
	 * @return
	 */
	@JSON
	public String disableBillRepaymentApplyDetail() {
		try {
			LOGGER.info("开始申请作废还款单ACTION ..");
			// 获取界面选中的还款单号
			String selectBillRepayNo = billRepaymentDisableVo.getBillRepaymentDisableDto().getRepaymentNo();
			// 还款单号不能为空
			if(StringUtils.isEmpty(selectBillRepayNo)) {
				throw new SettlementException("没有选择申请作废的还款单!");
			}
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 登陆用户不能为空		
			if(null == cInfo){
				throw new SettlementException("作废还款单获取用户名为空");
			}
			
			BillRepaymentDisableDto dto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			
			//BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
			BillRepaymentDisableResultDto resultDto = billRepaymentApplyDisableService.queryRepaymentWriteoff(dto ,getStart(), getLimit());
		
			billRepaymentDisableResultVo = new BillRepaymentDisableResultVo();
			billRepaymentDisableResultVo.setBillRepaymentDisableResultDto(resultDto);
			setTotalCount((long) resultDto.getTotalCount());
			
			return returnSuccess();
		} catch (SettlementException e) {
			LOGGER.error("申请作废还款单异常"+e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 *还款单申请作废查询 
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-25 下午6:15:38 
	 * @return
	 */
	@JSON
	public String queryBillRepaymentApply() {
		try {
			LOGGER.info("开始查询申请作废还款单ACTION ..");
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 登陆用户不能为空		
			if(null == cInfo){
				throw new SettlementException("获取用户名为空");
			}
			
			// 获取该用户所管辖的部门
			Set<String> orgCodes = cInfo.getUser().getOrgids();
			//声明当前的用户所管理的权限			
			List<String> orgCodeList = new ArrayList<String>(orgCodes);
			//判断用户管辖的部门
			if(CollectionUtils.isEmpty(orgCodeList)){
				//抛出异常
				throw new SettlementException("当前登录用户没有任何部门的查询权限！");
			}
			BillRepaymentDisableDto dto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			dto.setEmpCode(cInfo.getEmpCode());
			//校验参数按照日期查询 
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(dto
					.getQueryType())) {
					//业务开始日期是否为空				
					if (null == dto.getApplyDateStart()) {
						throw new SettlementException("开始申请日期,不能为空!");
					}
					//业务结束日期是否为空				
					if (null == dto.getApplyDateEnd()) {
						throw new SettlementException("结束申请日期,不能为空!");
					}
					//结束日期+1
					dto.setApplyDateEnd(DateUtils.addDayToDate(dto.getApplyDateEnd(), 1));
			} else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO.equals(dto.getQueryType())) {
				if(CollectionUtils.isEmpty(dto.getRepaymentNos())) {
					throw new SettlementException("还款单号不能为空！");
				}
			}
			
			//BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
			//查询申请作废的还款单
			BillRepaymentDisableResultDto resultDto = billRepaymentApplyDisableService.queryDisableApplicationByDto(dto, getStart(), getLimit());
			setTotalCount((long) resultDto.getTotalCount());
			
			billRepaymentDisableResultVo = new BillRepaymentDisableResultVo();
			billRepaymentDisableResultVo.setBillRepaymentDisableResultDto(resultDto);
			
			return returnSuccess();
		} catch (SettlementException e) {
			LOGGER.error("查询还款单申请作废异常"+e.getErrorCode(),e);
			return returnError(e);
		} 
	}
	
	
	/**
	 * 
	 *审批 还款单作废申请
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-26 上午8:12:37 
	 * @return
	 */
	@JSON
	public String aprroveRepayApply() {
		try {
			LOGGER.info("作废审批ACTION START ...");
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 登陆用户不能为空		
			if(null == cInfo){
				throw new SettlementException("获取用户名为空");
			}
			
			BillRepaymentDisableDto dto = billRepaymentDisableVo.getBillRepaymentDisableDto();
			//设置其他参数
			dto.setApproveUserCode(cInfo.getEmpCode());
			dto.setApproveUserName(cInfo.getEmpName());
			dto.setApproveOrgCode(cInfo.getCurrentDeptCode());
			dto.setApproveOrgName(cInfo.getCurrentDeptName());
			dto.setEmpCode(cInfo.getEmpCode());
			
			billRepaymentApplyDisableService.approveApplication(dto);
			return returnSuccess();
		} catch (SettlementException e) {
			LOGGER.error("审批还款单作废申请异常" + e.getErrorCode(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午1:02:09
	 */
	@JSON
	public String queryBillRepayment() {
		try {
			//记录日志
			LOGGER.info("查询还款单into...");
			//还款单的查询时，先判断界面输入实体是否为空
			if(null==billRepaymentManageVo.getBillRepaymentManageDto()){
				//抛出异常
				throw new SettlementException("查询参数，不能全部为空", "");
			}
			//获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 还款单界面输入条件Dto
			BillRepaymentManageDto billRepayDto = billRepaymentManageVo.getBillRepaymentManageDto();
			// 校验日期是否已填
			checkQueryParams(billRepayDto);
			//查询还款单并分页
			BillRepaymentManageResultDto billRepaymentManageResultDto = billRepaymentManageService.queryBillRepayment(billRepayDto, getStart(), getLimit(),currentInfo);
			
			//声明还款单返回界面VO实体 
			billRepaymentManageResultVo = new BillRepaymentManageResultVo();
			//查询还款单返回界面
			billRepaymentManageResultVo.setBillRepaymentManageResultDto(billRepaymentManageResultDto);
			// 设置总条数
			this.setTotalCount((long) billRepaymentManageResultVo.getBillRepaymentManageResultDto().getTotalCount());
			//查询还款单条数，logger打印输出
			LOGGER.info("查询还款单条数"+ billRepaymentManageResultVo.getBillRepaymentManageResultDto().getTotalCount());
			//返回成功
			return returnSuccess();
		} catch (SettlementException e) {
			//还款单异常处理，logger打印
			LOGGER.error("查询还款单异常"+e.getMessage(), e);
			//返回失败
			return returnError(e);
		}
	}

	/**
	 * 审核还款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 下午6:54:09
	 */
	@JSON
	public String aduitBillRepayment() {
		try {
			//记录日志
			LOGGER.debug("审核还款单开始...");
			// 检验还款单号是否为空
			if(StringUtils.isEmpty(billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos())){
				//抛出异常
				throw new SettlementException("审核还款单操作，还款单号不能为空", "");
			}
			//获取界面输入的还款单号集合
			String selectBillRepayNos = billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos();
			//logger输出审核还款单的条数			
			LOGGER.debug("审核还款单"+selectBillRepayNos);
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//获取当前登录用户，用于每个用户的数据权限			
			if(null==cInfo.getEmpName()){
				//抛出异常
				throw new SettlementException("审核还款单获取用户名为空", "");
			}
			//打印审核单还款单用户名称
			LOGGER.debug("审核还款单登录用户:"+cInfo.getUserName());
			/**
			 * 审核还款单 调用后台代码
			 */
			billRepaymentManageService.auditBillRepayment(billRepaymentManageVo.getBillRepaymentManageDto(), cInfo);
			
			//审核还款单完毕，用户调试logger
			LOGGER.debug("审核还款单sucess");
			return returnSuccess();
		} catch (SettlementException e) {
			//审核还款单异常logger打印和返回后端异常
			LOGGER.error("审核还款单异常！"+e.getMessage(), e);
			return returnError(e);
		}

	}

	/**
	 * 反审核还款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 下午6:54:20
	 */
	@JSON
	public String revAduitBillRepayment() {
		try {

			LOGGER.debug("反审核还款单开始...");
			//获取界面上还款单的实体
			if(StringUtils.isEmpty(billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos())){
				//抛出异常
				throw new SettlementException("没有选择反审核还款单!");
			}
			// 获取界面选中的还款单号
			String selectBillRepayNos = billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos();
			//logger 调试打印反审核还款单编号
			LOGGER.debug("反审核还款单号"+selectBillRepayNos);
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//控制权限时，用于获取当前登录用户
			if(null==cInfo.getEmpName()){
				//抛出异常
				throw new SettlementException("反审核还款单获取用户名为空", "");
			}
			//打印调试的debug 还款单的用户
			LOGGER.debug("反审核还款单登录用户:"+cInfo.getUserName());
			/**
			 * 反审核还款单调用业务控制逻辑
			 */
			billRepaymentManageService.auditBackBillRepayment(billRepaymentManageVo.getBillRepaymentManageDto(), cInfo);
			//反审核还款单用户结束
			LOGGER.debug("反审核还款单success......");
			return returnSuccess();
		} catch (SettlementException e) {
			//打印还款单的logger用于返回异常消息和异常
			LOGGER.error("反审核还款单"+e.getMessage(), e);
			return returnError(e);
		}

	}

	/**
	 * 作废还款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 下午6:54:53
	 */
	@JSON
	public String disabledBillRepayment() {
		try {
			LOGGER.debug("作废还款单开始...");
			//获取作废还款单时的实体            
			if(StringUtils.isEmpty(billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos())){
				//抛出异常
				throw new SettlementException("没有选择待作废还款单!");
			}
			// 获取界面选中的还款单号
			String selectBillRepayNos = billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos();
			LOGGER.debug("选择作废的还款单号"+selectBillRepayNos);
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//获取当前登录用户的用户			
			if(null==cInfo.getEmpName()){
				//抛出异常
				throw new SettlementException("作废还款单获取用户名为空", "");
			}
			LOGGER.debug("作废还款单登录用户:"+cInfo.getUserName());
			//作废还款单结束			
			/**
			 * 作废还款单
			 */
			billRepaymentManageService.disableBillRepayment(billRepaymentManageVo.getBillRepaymentManageDto(), cInfo);
			//结算还款单作废的调用			
			LOGGER.debug("作废还款单 action"+billRepaymentManageVo.getBillRepaymentManageDto().getRepaymentNo()+"success..");
			//返回界面			
			return returnSuccess();
		} catch (SettlementException e) {
			//logger返回,还款单作废的异常			
			LOGGER.error("作废还款单异常"+e.getMessage(),e);
			return returnError(e);
		}
	}
	
	
	/**
	 * 还款/批量还款
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-23 下午2:25:43
	 */
	@JSON
	public String toRepayment() {

		try {
			LOGGER.debug("还款/批量还款开始...");
			//获取当前登录用户的信息			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//获取批量作废还款单的 当前用户			
			if(null==cInfo.getEmpName()){
				//抛出异常
				throw new SettlementException("还款/批量还款获取用户名为空", "");
			}
			LOGGER.debug("还款/批量还款登录用户:"+cInfo.getUserName());
			
			// 批量还款
			billRepaymentManageService.repaymentForStatement(billRepaymentManageVo.getBillStatementToPaymentQueryDto(),cInfo);
			//批量作废还款单的结束
			LOGGER.debug("还款/批量还款success...");
			return returnSuccess();
		} catch (SettlementException e) {
			//批量作废还款单logger
			LOGGER.error("还款、批量还款异常"+e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 根据汇款单号查询汇款信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-5 下午1:53:02
	 */
	@JSON
	public String queryRemittanceDetail() {

		try {
			LOGGER.debug("查询汇款单信息开始...");
			//获取当前汇款参数不能为空			
			if(null==billRepaymentManageVo.getBillStatementToPaymentQueryDto()){
				//抛出异常
				throw new SettlementException("查询汇款单参数不能为空");
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 根据汇款单号查询汇款信息
			BillStatementToPaymentResultDto billStatementToPaymentResultDto = billRepaymentManageService.queryRemittanceDetail(billRepaymentManageVo.getBillStatementToPaymentQueryDto(),cInfo);
			//返回汇款用户信息
			billRepaymentManageVo.setBillStatementToPaymentResultDto(billStatementToPaymentResultDto);
			//查询汇款单信息结束			
			LOGGER.debug("查询汇款单信息结束...");
			return returnSuccess();
		} catch (SettlementException e) {
			//根据汇款单号查询汇款单异常信息处理
			LOGGER.error("根据汇款单号查询汇款信息异常"+e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 还款单的核销明细
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-13 上午9:44:44
	 */
	@JSON
	public String detailBillRepayment() {

		try {
			LOGGER.debug("查看还款单明细开始...");
			//还款单用户实体			
			if(null==billRepaymentManageVo||null == billRepaymentManageVo.getBillRepaymentManageDto()||
					StringUtils.isEmpty(billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos())){
				//抛出异常
				throw new SettlementException("没有选择待核销还款单!");
			}
			
			// 获取界面选中的还款单号
			String selectBillRepayNos = billRepaymentManageVo.getBillRepaymentManageDto().getSelectBillRepayNos();
			LOGGER.debug("待核销还款单号..."+selectBillRepayNos);
			// 根据还款单号，查询还款单明细查询
			List<BillWriteoffEntity> entityList = billRepaymentManageService.queryBillWriteoffEntityDetail(selectBillRepayNos);
			//判空
			if(CollectionUtils.isNotEmpty(entityList)){
				//打印日志
				LOGGER.debug("查询还款单明细条数..."+entityList.size());
			}
			
			// 核销明细总金额
			BigDecimal totalWriteoffAmount = BigDecimal.ZERO;
			//循环处理
			for (BillWriteoffEntity entity : entityList) {
				//计算总金额
				totalWriteoffAmount = totalWriteoffAmount.add(entity.getAmount());
			}
			
			// 设置还款单号返回值
			billRepaymentManageResultVo = new BillRepaymentManageResultVo();
			BillRepaymentManageResultDto billRepaymentManageResultDto = new BillRepaymentManageResultDto();
			//查询还款单明细List
			billRepaymentManageResultDto.setBillWriteoffEntityList(entityList);
			//核销金额
			billRepaymentManageResultDto.setTotalWriteoffAmount(totalWriteoffAmount);
			LOGGER.debug("还款单的核销明细  核销金额..."+totalWriteoffAmount);
			//总条数
			billRepaymentManageResultDto.setTotalWriteoffCount(Long.valueOf(entityList.size()));
			//返回到界面
			billRepaymentManageResultVo.setBillRepaymentManageResultDto(billRepaymentManageResultDto);
			LOGGER.debug("查看还款单明细结束...");
			return returnSuccess();
		} catch (SettlementException e) {
			//还款单异常信息			
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 检查查询参数是否正确
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-7 上午10:47:40
	 */
	private void checkQueryParams(BillRepaymentManageDto billRepayDto) {
		//判断查询还款单实体使用为空		
		if (billRepayDto == null) {
			//抛出异常
			throw new SettlementException("查询参数，不能全部为空", "");
		}
		// 按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billRepayDto
				.getQueryType())) {
			// 判断还款单业务日期日期是否为空
			if (QUERY_BUSINESSDATE_FLAG.equals(billRepayDto.getQueryDateFlag())) {
				//业务开始日期是否为空				
				if (null == billRepayDto.getBusinessStartDate()) {
					//抛出异常
					throw new SettlementException("业务开始日期,不能为空!", "");
				}
				//业务结束日期是否为空				
				if (null == billRepayDto.getBusinessEndDate()) {
					//抛出异常
					throw new SettlementException("业务结束日期,不能为空!", "");
				}
				//是否按照记账日期查询				
			} else if (QUERY_ACCOUNTDATE_FLAG.equals(billRepayDto.getQueryDateFlag())) {
				//记账开始日期和记账结束日期不能为空				
				if (null == billRepayDto.getAccountStartDate()) {
					//抛出异常
					throw new SettlementException("记账开始日期,不能为空!", "");
				}
				//记账结束日期不能为空				
				if (null == billRepayDto.getAccountEndDate()) {
					//抛出异常
					throw new SettlementException("记账结束日期,不能为空!", "");
				}
			} else {
				//否则日期为空				
				throw new SettlementException("日期查询,不能都为空!", "");
			}
			// 如果按照还款单号查询 还款单号不能为空
		} else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO.equals(billRepayDto.getQueryType())) {
			//判空
			if (CollectionUtils.isEmpty(billRepayDto.getRepaymentNos())) {
				//抛出异常
				throw new SettlementException("还款单按还款号查询异常,还款单号不能为空!", "");
			}
			// 如果按照对账单号查询，对账单号不能为空
		} else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO
				.equals(billRepayDto.getQueryType())) {
			//判空
			if (CollectionUtils.isEmpty(billRepayDto.getStatementBillNos())) {
				//抛出异常
				throw new SettlementException("还款单按对账号查询异常,对账单不能为空!", "");
			}
			// 如果按照运单号查询还款单，运单单号不能为空
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(billRepayDto.getQueryType())) {
			//判空
			if (CollectionUtils.isEmpty(billRepayDto.getWayBillNos())) {
				//抛出异常
				throw new SettlementException("还款单按运单号查询异常,运单号不能为空!", "");
			}
			
		//如果根据来源单号查询还款单，来源单号不能为空
		}else if(SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO.equals(billRepayDto.getQueryType())){
			//判空
			if (CollectionUtils.isEmpty(billRepayDto.getSourceBillNos())) {
				throw new SettlementException("还款单按来源单号查询异常,来源单号不能为空!", "");
			}
		}else if(SettlementConstants.TAB_QUERY_BY_LANDSTOWAGE_NO.equals(billRepayDto.getQueryType())){
			//导出还款单时，按位置的查询条件查询
			if (CollectionUtils.isEmpty(billRepayDto.getBatchNos())) {
				//记录日志
				LOGGER.debug("导出还款单按银联交易流水号查询.....");
				//抛出异常
				throw new SettlementException("流水号,不能为空!");
			}
		} else {
			//抛出异常
			throw new SettlementException("查询时，参数错误!", "");
		}
	}

	/**
	 * 导出还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午6:27:23
	 */
	public String exportBillRepayPayment() {
		// 输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			LOGGER.debug("导出还款单begin....");
			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取界面 输入条件、当前登录用户信息不为空
			BillRepaymentManageDto repayDto = billRepaymentManageVo.getBillRepaymentManageDto();
			//判空
			if (null == repayDto) {
				//抛出异常
				throw new SettlementException("导出时，传入条件不能为空!", "");
			}
			// 结束日期加1天
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(repayDto.getQueryType())) {
				//导出还款单业务日期不为空				
				LOGGER.debug("导出还款单按日期查询....");
			}// 对账单
			else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(repayDto.getQueryType())) {
				LOGGER.debug("导出还款单按对账单查询.....");
				if (CollectionUtils.isEmpty(repayDto.getStatementBillNos())) {
					//抛出异常
					throw new SettlementException("对账单号,不能为空!");
				}
				// 还款单
			} else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO.equals(repayDto.getQueryType())) {
				LOGGER.debug("导出还款单按还款单查询.....");
				if (CollectionUtils.isEmpty(repayDto.getRepaymentNos())) {
					//抛出异常
					throw new SettlementException("还款单号,不能为空!");
				}
				// 按运单号查询
			} else if(SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(repayDto.getQueryType())){
				//导出还款单时，按位置的查询条件查询
				if (CollectionUtils.isEmpty(repayDto.getWayBillNos())) {
					//记录日志
					LOGGER.debug("导出还款单按运单查询.....");
					//抛出异常
					throw new SettlementException("运单号,不能为空!");
				}
				//来源单号查询				
			}else if (SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO.equals(repayDto.getQueryType())) {
				//导出还款单时，按位置的查询条件查询
				if (CollectionUtils.isEmpty(repayDto.getSourceBillNos())) {
					//记录日志
					LOGGER.debug("导出还款单按运单查询.....");
					//抛出异常
					throw new SettlementException("运单号,不能为空!");
				}
			}else if(SettlementConstants.TAB_QUERY_BY_LANDSTOWAGE_NO.equals(repayDto.getQueryType())){
				//导出还款单时，按位置的查询条件查询
				if (CollectionUtils.isEmpty(repayDto.getBatchNos())) {
					//记录日志
					LOGGER.debug("导出还款单按银联交易流水号查询.....");
					//抛出异常
					throw new SettlementException("流水号,不能为空!");
				}
			}else{
				LOGGER.debug("导出还款单不存在的类型.....");
				//抛出异常
				throw new SettlementException("还款单查询，错误不存在类型!");
			}
			// 设置excel名称
			try {
				this.setFileName(new String(("还款单").getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				//导出文件名编码转化错误
				LOGGER.error("导出还款单，文件编码转化异常"+e1.getMessage(), e1);
			}
			//执行还款单导出时 查询方法			
			HSSFWorkbook wookBook = billRepaymentManageService.queryExportBillRepayment(repayDto, currentInfo);
			try {
				//写入输出流				
				wookBook.write(baos);
				//生成输入流
				stream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				//导出还款单异常				
				LOGGER.error("导出还款单输入流异常"+e.getMessage(),e);
			}
			//返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			//返回失败
			return returnError(e);

		} finally {
			// 关闭流
			if (baos != null) {
				try {
					//关闭流					
					baos.close();
				} catch (IOException e) {
					//关闭还款单流异常					
					LOGGER.error("导出还款单关闭流异常"+e.getMessage(),e);
				}
			}
		}
	}

	/**
	 * @GET
	 * @return billRepaymentManageVo
	 */
	public BillRepaymentManageVo getBillRepaymentManageVo() {
		/*
		 *@get
		 *@ return billRepaymentManageVo
		 */
		return billRepaymentManageVo;
	}

	/**
	 * @SET
	 * @param billRepaymentManageVo
	 */
	public void setBillRepaymentManageVo(BillRepaymentManageVo billRepaymentManageVo) {
		/*
		 *@set
		 *@this.billRepaymentManageVo = billRepaymentManageVo
		 */
		this.billRepaymentManageVo = billRepaymentManageVo;
	}

	/**
	 * @GET
	 * @return billRepaymentManageResultVo
	 */
	public BillRepaymentManageResultVo getBillRepaymentManageResultVo() {
		/*
		 *@get
		 *@ return billRepaymentManageResultVo
		 */
		return billRepaymentManageResultVo;
	}

	/**
	 * @SET
	 * @param billRepaymentManageResultVo
	 */
	public void setBillRepaymentManageResultVo(
			BillRepaymentManageResultVo billRepaymentManageResultVo) {
		/*
		 *@set
		 *@this.billRepaymentManageResultVo = billRepaymentManageResultVo
		 */
		this.billRepaymentManageResultVo = billRepaymentManageResultVo;
	}

	/**
	 * @GET
	 * @return fileName
	 */
	public String getFileName() {
		/*
		 *@get
		 *@ return fileName
		 */
		return fileName;
	}

	/**
	 * @SET
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		/*
		 *@set
		 *@this.fileName = fileName
		 */
		this.fileName = fileName;
	}

	/**
	 * @GET
	 * @return stream
	 */
	public ByteArrayInputStream getStream() {
		/*
		 *@get
		 *@ return stream
		 */
		return stream;
	}

	/**
	 * @SET
	 * @param stream
	 */
	public void setStream(ByteArrayInputStream stream) {
		/*
		 *@set
		 *@this.stream = stream
		 */
		this.stream = stream;
	}

	/**
	 * @SET
	 * @param billRepaymentManageService
	 */
	public void setBillRepaymentManageService(
			IBillRepaymentManageService billRepaymentManageService) {
		/*
		 *@set
		 *@this.billRepaymentManageService = billRepaymentManageService
		 */
		this.billRepaymentManageService = billRepaymentManageService;
	}

	/**
	 * @param billRepaymentApplyDisableService the billRepaymentApplyDisableService to set
	 */
	public void setBillRepaymentApplyDisableService(
			IBillRepaymentApplyDisableService billRepaymentApplyDisableService) {
		this.billRepaymentApplyDisableService = billRepaymentApplyDisableService;
	}

	/**
	 * @return the billRepaymentDisableVo
	 */
	public BillRepaymentDisableVo getBillRepaymentDisableVo() {
		return billRepaymentDisableVo;
	}

	/**
	 * @param billRepaymentDisableVo the billRepaymentDisableVo to set
	 */
	public void setBillRepaymentDisableVo(
			BillRepaymentDisableVo billRepaymentDisableVo) {
		this.billRepaymentDisableVo = billRepaymentDisableVo;
	}

	/**
	 * @return the billRepaymentDisableResultVo
	 */
	public BillRepaymentDisableResultVo getBillRepaymentDisableResultVo() {
		return billRepaymentDisableResultVo;
	}

	/**
	 * @param billRepaymentDisableResultVo the billRepaymentDisableResultVo to set
	 */
	public void setBillRepaymentDisableResultVo(
			BillRepaymentDisableResultVo billRepaymentDisableResultVo) {
		this.billRepaymentDisableResultVo = billRepaymentDisableResultVo;
	}
	
}
