package com.deppon.foss.module.settlement.consumer.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IStatementCreatService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.InvoiceManagementAllVo;
import com.deppon.foss.util.DateUtils;

/**
 *@项目：合伙人项目
 *@功能：定义“发票管理”的action
 *@author:218371-foss-zhaoyanjun
 *@date:2016-01-28上午08:41
 */
public class InvoiceStatementManagerAction extends AbstractAction{
	//发票对账单新增页面action
	private static final Logger logger = LoggerFactory.getLogger(InvoiceStatementManagerAction.class);
	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 7827213280759899255L;
	/**
	 * 前台查询参数
	 */
	private InvoiceManagementAllVo invoiceManagementAllVo = new InvoiceManagementAllVo();
	
	/**
	 * 制作对账单查询结果（对账单及对账单明细单据）
	 */
	private InvoiceManagementAddDto invoiceManagementAddDto = new InvoiceManagementAddDto();
	
	/**
	 * 制作对账单服务类接口
	 */
	private IStatementCreatService statementCreatService;
	
	/**
	 * 要删除的对账单单号
	 */
	
	private String statementNoForDelete;
	
	/**
	 * 制作发票对账单返回的应收单
	 * 
	 * @author 218371-foss-zhaoyanjun
	 * @date 2016-02-26 下午16:40:05
	 */
	@JSON
	public String queryForStatementMake() {
		// 获取查询DTO
		InvoiceManagementAddDto queryDto = invoiceManagementAllVo.getInvoiceManagementAddDto();
		// 获取当前登录信息
		CurrentInfo info = FossUserContext.getCurrentInfo();
		
		/*
		 * 首先校验前台传入的非空参数是否为空 
		 * 
		 * 1、当按客户制作时，客户编码、账期不能为空；
		 *    单据明细子类型不能为空单据子类型不能同时为预收和预付单，不能只为预收或预付单 
		 * 2、当按单号制作时，传入的明细单号不能为空
		 */
		try {
			// 判断培训会务应收单不能合其他两个一起出现 
			List<String> manychecks=queryDto.getManycheck();
			List<String> manycheck =new ArrayList<String>();
			
			if(manychecks==null){
				logger.error("按单制作对账单时，单据子类型不能为空");
				throw new SettlementException("按单制作对账单时，单据子类型不能为空");
			}
			for(int i =0;i<manychecks.size();i++){
				if(!("").equals(manychecks.get(i))){
					manycheck.add(manychecks.get(i));
				}
			}
			
				if (manycheck.size()>=2){
					for(int i=0;i<manycheck.size();i++){
						if(("PTF").equals(manycheck.get(i)) || manycheck.get(i)=="PTF"){
								throw new SettlementException("选择错误(培训会务应收单只能单独选择)");
						}
					}
				}
			//如果选择培训会务应收单的话登录只能是  事业合伙人业务培训小组下的人
			if(("PTF").equals(manycheck.get(0)) || manycheck.get(0)=="PTF"){
				if(!("事业合伙人业务培训小组").equals(info.getCurrentDeptName()) && info.getCurrentDeptName()!="事业合伙人业务培训小组"){
					throw new SettlementException("当前登录人不是事业合伙人培训小组，不能制作培训会务应收单");
				}
			}
			//按日期查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
				//客户编码不能为空
				if (StringUtils.isEmpty(queryDto.getCustomerCode())) {
					//为空，提示客户编码为空，不能制作对账单
					throw new SettlementException("客户编码为空，不能制作对账单！");
				}
				//账期的开始或结束日期不能为空
				if (queryDto.getPeriodBeginDate() == null|| queryDto.getPeriodEndDate() == null) {
					//为空，提示账期的开始或结束日期为空，不能制作对账单
					throw new SettlementException("账期的开始或结束日期为空，不能制作对账单！");
				}
				// 给结束日期加1天
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1),DateUtils.DATE_FORMAT));
			//按单号查询
			} else if(SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto.getQueryPage())){
				//单号不能为空
				if (queryDto.getBillDetailNos() == null|| queryDto.getBillDetailNos().length == 0) {
					//为空，提示单号为空，不能制作对账单
					throw new SettlementException("单号为空，不能制作对账单！");
				}
			} 
			//将更改过后的值塞入里面
			queryDto.setManycheck(manycheck);
			// 返回对账单明细及对账单结果集
			InvoiceManagementAddDto resultDto = statementCreatService.queryForInvoiceStatementAddStatementMake(queryDto, info);
			//设置对账单返回结果
			invoiceManagementAllVo.setInvoiceManagementAddDto(resultDto);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 制作对账单保存操作
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-19 上午10:48:46
	 */
	@JSON
	public String addStatement() {
		//记录日志
		logger.info("制作对账单action开始");
		// 获取当前登录信息
		CurrentInfo info = FossUserContext.getCurrentInfo();
		// 获取前台保存时传入的dto
		InvoiceManagementAddDto resultDto = invoiceManagementAllVo.getInvoiceManagementAddDto();
		try {
			logger.info("制作对账单service进入");
			// 调用保存对账单接口
			InvoiceManagementAddDto dto = statementCreatService.addStatement(resultDto, info);
			//如果返回结果为空，新建对账单实体
			if (dto == null) {
				//新建对账单制作dto
				dto = new InvoiceManagementAddDto();
			}
			//返回制作对账单结果
			invoiceManagementAllVo.setInvoiceManagementAddDto(dto);
			//记录日志
			logger.info("制作对账单action结束");
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 查询已制作好的合伙人对账单
	 * 
	 * @author 218371-foss-zhaoyanjun
	 * @date 2016-02-26 下午16:40:05
	 */
	@JSON
	public String queryStatementForIma() {
		// 获取查询DTO
		InvoiceManagementAddDto queryDto = invoiceManagementAllVo.getInvoiceManagementAddDto();
		// 获取当前登录信息
		//CurrentInfo info = FossUserContext.getCurrentInfo();

		try {
			//账期的开始或结束日期不能为空
			if (queryDto.getCurrentDeptNo() == null|| "".equals(queryDto.getCurrentDeptNo())) {
				//为空，提示账期的开始或结束日期为空，不能制作对账单
				throw new SettlementException("当前部门为空，不能查询对账单！");
			}
			//按日期查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
				//客户编码不能为空
				if (StringUtils.isEmpty(queryDto.getCustomerCode())) {
					//为空，提示客户编码为空，不能制作对账单
					throw new SettlementException("客户编码为空，不能查询对账单！");
				}
				//账期的开始或结束日期不能为空
				if (queryDto.getPeriodBeginDate() == null|| queryDto.getPeriodEndDate() == null) {
					//为空，提示账期的开始或结束日期为空，不能制作对账单
					throw new SettlementException("账期的开始或结束日期为空，不能查询对账单！");
				}
				// 给结束日期加1天
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1),DateUtils.DATE_FORMAT));
			//按单号查询
			} else if(SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto.getQueryPage())){
				//单号不能为空
				if (queryDto.getBillDetailNos() == null|| queryDto.getBillDetailNos().length == 0) {
					//为空，提示单号为空，不能制作对账单
					throw new SettlementException("单号为空，不能查询对账单！");
				}
			} else if(SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryDto.getQueryPage())){
				//单号不能为空
				if (queryDto.getStatementNos() == null|| queryDto.getStatementNos().length == 0) {
					//为空，提示单号为空，不能制作对账单
					throw new SettlementException("单号为空，不能查询对账单！");
				}
			}
			List<InvoiceManagementAddDto> list=statementCreatService.queryStatements(queryDto);
			invoiceManagementAllVo.setList(list);
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 修改的合伙人对账单
	 * 
	 * @author 218371-foss-zhaoyanjun
	 * @date 2016-02-26 下午16:40:05
	 */
	@JSON
	public String deleteStatement() {
		// 获取查询DTO
		InvoiceManagementAddDto queryDto = invoiceManagementAllVo.getInvoiceManagementAddDto();
		// 获取当前登录信息
		//CurrentInfo info = FossUserContext.getCurrentInfo();
		try {
			if(queryDto.getStatementNos()==null||queryDto.getStatementNos().length==0){
				throw new SettlementException("对账单号为空，不能删除对账单");
			}
			statementCreatService.deleteStatementNo(queryDto.getStatementNos());
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	//按对账单号查询应收单
	@JSON
	public String queryReceivableDetailByStatementNo(){
		// 获取查询DTO
		InvoiceManagementAddDto queryDto = invoiceManagementAllVo
				.getInvoiceManagementAddDto();
		// 获取当前登录信息
		//CurrentInfo info = FossUserContext.getCurrentInfo();
		/*
		 * 首先校验前台传入的非空参数是否为空
		 * 
		 * 1、当按客户制作时，客户编码、账期不能为空； 单据明细子类型不能为空单据子类型不能同时为预收和预付单，不能只为预收或预付单
		 * 2、当按单号制作时，传入的明细单号不能为空
		 */
		try {
			// 客户编码不能为空
			if (StringUtils.isEmpty(queryDto.getStatementBillNo())) {
				// 为空，提示客户编码为空，不能制作对账单
				throw new SettlementException("对账单号为空，不能制作对账单！");
			}
			// 返回对账单明细及对账单结果集
			InvoiceManagementAddDto resultDto = statementCreatService
					.queryReceivablesByStatementBillNo(queryDto.getStatementBillNo());
			// 设置对账单返回结果
			invoiceManagementAllVo.setInvoiceManagementAddDto(resultDto);
			// 正常返回
			return returnSuccess();
			// 异常处理
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 异常返回
			return returnError(e);
		}
	}

	public InvoiceManagementAllVo getInvoiceManagementAllVo() {
		return invoiceManagementAllVo;
	}

	public InvoiceManagementAddDto getInvoiceManagementAddDto() {
		return invoiceManagementAddDto;
	}

	public String getStatementNoForDelete() {
		return statementNoForDelete;
	}

	public void setStatementNoForDelete(String statementNoForDelete) {
		this.statementNoForDelete = statementNoForDelete;
	}

	public void setInvoiceManagementAllVo(
			InvoiceManagementAllVo invoiceManagementAllVo) {
		this.invoiceManagementAllVo = invoiceManagementAllVo;
	}

	public void setInvoiceManagementAddDto(
			InvoiceManagementAddDto invoiceManagementAddDto) {
		this.invoiceManagementAddDto = invoiceManagementAddDto;
	}

	public void setStatementCreatService(IStatementCreatService statementCreatService) {
		this.statementCreatService = statementCreatService;
	}

}
