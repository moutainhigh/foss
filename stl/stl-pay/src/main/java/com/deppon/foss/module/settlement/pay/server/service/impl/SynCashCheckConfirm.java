package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.inteface.finmanager.CommonException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.ICashCheckConfirmService;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.server.service.ISynCashCheckConfirm;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCheckConfirmEntityRequest;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCheckConfirmEntityResponse;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInResultDto;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * @author 218392 张永雪
 * @date 2015-12-08 09:21:20
 * 盘点以及未收银确认时在盘点时提示 
 *
 */
public class SynCashCheckConfirm implements ISynCashCheckConfirm{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynCashCheckConfirm.class);

	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	@Override
	public String queryCashCheckConfirm(String cashCheckConfirmEntityRequest){
		
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_NO_CONFIRM_AMOUNT");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		//声明变量:返回给财务盘点的实体
		CashCheckConfirmEntityResponse response = new CashCheckConfirmEntityResponse();
		
		JSONObject object = JSONObject.fromObject(cashCheckConfirmEntityRequest);
		CashCheckConfirmEntityRequest request = (CashCheckConfirmEntityRequest) JSONObject.toBean(object, CashCheckConfirmEntityRequest.class);
		String unifiedCode = request.getDeptBenchmarkCode(); 
		//记录日志
		LOGGER.info("部门标杆编号:" + unifiedCode);
		try{
			if(StringUtils.isNotEmpty(unifiedCode)){
				/**
				 * 1.查询部门累计未交账金额前，先重新按部门生成现金收入（缴款）报表 2.现金收入（缴款）报表生成完后，查询部门累计未交帐营业款、预收款
				 */
				// 根据财务自助标杆部门编码综合的部门编码编码查询
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(unifiedCode);
				//判空
				if (null == orgEntity) {
					//异常处理
					LOGGER.error("根据标杆编码："+unifiedCode + "查找Foss中的部门编码未找到！");
					throw new CommonException("根据标杆编码："+unifiedCode + "查找Foss中的部门编码未找到！");
				}
				// 根据标杆部门获取当前部门
				String deptNo =orgEntity.getCode();
				//记录日志
				LOGGER.info("根据标杆编码："+unifiedCode + "查找Foss中的部门编码:"+deptNo);
				
				/**
				 * 1、根据单个网点，调用存储过程生成现金收入（缴款）报表
				 */
				// 当前日期
				Date now = new Date();
				// 获取当前时间精确时间
				Date currentTime = getNowTime();
				
				//添加互斥锁收集数据
				List<MutexElement> mutexElements = new ArrayList<MutexElement>();
				// 业务互锁运单号
				MutexElement mutexElement = new MutexElement(deptNo, "按部门生成缴款报表时,锁定部门", MutexElementType.CASH_IN_COME_LOCK_DEPT_CODE);
				//加入互斥对象集合
				mutexElements.add(mutexElement);
				//添加互斥锁
				if(CollectionUtils.isNotEmpty(mutexElements)){
					//锁定
					if(!businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH)) {
						throw new SettlementException("资源已被占用，请稍后再操作！");
					}
				}
				
				
				//记录日志
				LOGGER.info("调用存储过程，按部门生成现金收入（缴款）金额");
				//调用接口处理
				reportCashRecPayInService.createOneReportCashRecPayIn(
						SettlementConstants.CASH_BEGIN_DATE, now, deptNo,
					    currentTime);
				//记录日志
				LOGGER.info("结束调用");
				
				
				//去除互斥锁
				if(CollectionUtils.isNotEmpty(mutexElements)){
					//解锁
					businessLockService.unlock(mutexElements);
				}
				
				
				
				/**
				 * 2、查询部门累计未交款营业款预收款
				 */
				CashCollectionRptEntity entity = new CashCollectionRptEntity();
				// 设置查询编码为组织编码编号
				entity.setOrgCode(deptNo);
				//调用接口查询
				BillCashRecPayInResultDto dto = reportCashRecPayInService.queryCashinComerptDebtAmount(entity);
				//记录日志
				LOGGER.info("累计现金营业款金额:" + dto.getTotalClerksAmount() + "累计预收款总金额:"+ dto.getTotalPrecollectedAmount());

				// 累计现金营业款金额、累计预收款总金额
				//设置属性
				response.setAdvanceMoney(dto.getTotalPrecollectedAmount());
				//设置属性
				response.setBusinessMoney(dto.getTotalClerksAmount());
				//记录日志
				LOGGER.info("盘点以及未收银确认的代收货款查询提示:cashCheckConfirmRequest success exit...");
				
				/**
				 * 3、查询代收货款为收银确认的单号
				 */
				LOGGER.info("开始查询代收货款未收银确认的单号...");
				//List<String> waybillNoList = new ArrayList<String>();
				List<String> waybillNoList = cashCheckConfirmService.queryCashUnconfirmCod(deptNo);
				LOGGER.info("查询代收货款未收银确认的单号结束!");
				//生命一个StringBuffer变量，用来拼接查出来的单号
				String waybillStr = "";
				//非空判断
				if(!waybillNoList.isEmpty()){
					LOGGER.info("查询结果不为空...");
					int n = 0;//次数
					for(String waybillNo : waybillNoList){
						if(n < 1){
							waybillStr = waybillStr + waybillNo;
							n++;
						}else{
							waybillStr = waybillStr +","+ waybillNo;
							n++;
						}
						
					}
					response.setCashUnconfirmedNo(waybillStr);
					LOGGER.info("查询的单号为："+response.getCashUnconfirmedNo());
					
				}else{
					LOGGER.info("查询结果为空...");
					response.setCashUnconfirmedNo("");
				}
				resp.setHeader("ESB-ResultCode", "1");
				response.setIfSuccess(true);
				
			}else{
				response.setIfSuccess(false);
				resp.setHeader("ESB-ResultCode", "1");
				response.setErrorMsg("财务传入的部门编号为空");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("数据异常..."+response.getErrorMsg());
		}
		LOGGER.info("返回响应...");
		String responseJson = JSONObject.fromObject(response).toString();
		
		return responseJson;
	}
	
	/**
	 * 获取当前时间.
	 */
	private Date getNowTime() {
		// 获取当前精确时间
		return org.apache.commons.lang.time.DateUtils.truncate(Calendar
				.getInstance().getTime(), Calendar.SECOND);
	}
	
	/**
	 * 注入盘点以及查询未收银确认接口service
	 */
	private ICashCheckConfirmService cashCheckConfirmService;

	/**
	 * 注入业务互斥锁
	 */
	private IBusinessLockService businessLockService;
	
	/** 
	 * 注入组织管理服务.
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/** 现金收入缴款报表管理service. */
	private IReportCashRecPayInService reportCashRecPayInService;
	
	public void setCashCheckConfirmService(
			ICashCheckConfirmService cashCheckConfirmService) {
		this.cashCheckConfirmService = cashCheckConfirmService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setReportCashRecPayInService(
			IReportCashRecPayInService reportCashRecPayInService) {
		this.reportCashRecPayInService = reportCashRecPayInService;
	}
	

}
