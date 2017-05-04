package com.deppon.foss.prt.billpayment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;

/**
 * 打印数据绑定
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-3 下午12:51:55
 */
public class BillPaymentPrtDataSource implements JasperDataSource {

	// 注入日志
	private static final Logger logger = LoggerFactory
			.getLogger(BillPaymentPrtDataSource.class);
	// 时分秒类型
	DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 日期类型
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");// 此处需要多出一个空格，不然打印日期最后一位出不来。
	// 界面传入带T，需要转化成date,然后再转成目标格式
	DateFormat stringFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * 设置form表单的值
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-3 下午12:53:19
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception{
		return null;
	}

	/**
	 * 设置grid的值
	 * @author 045738-foss-maojianqiang
	 * @throws Exception 
	 * @date 2012-11-3 下午12:53:40
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) throws Exception {
		logger.info("打印设值开始");
		//调用方法查询出要打印的付款单
		List<BillPaymentEntity> list = getQueryDto(jasperContext);
		//判断是否为空，如果为空，则抛出异常
		if(CollectionUtils.isEmpty(list)){
			throw new SettlementException("没有查询到要打印的数据！");
		}
		//获取要用到的数据字典转化
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.BILL_PAYMENT__AUDIT_STATUS);
		termCodes.add(DictionaryConstants.BILL_PAYMENT__REMIT_STATUS);
		termCodes.add("FOSS_BOOLEAN");
		Map<String,Map<String,Object>> dicMaps = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		//获取付款方式数据字典map
		Map<String,Object> paymentTypeMap = dicMaps.get(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		//获取付款方式数据字典map
		Map<String,Object> auditStatusMap = dicMaps.get(DictionaryConstants.BILL_PAYMENT__AUDIT_STATUS);
		//获取付款方式数据字典map
		Map<String,Object> remitStatusMap = dicMaps.get(DictionaryConstants.BILL_PAYMENT__REMIT_STATUS);
		//获取付款方式数据字典map
		Map<String,Object> activeMap = dicMaps.get("FOSS_BOOLEAN");
		
		//声明打印
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		//封装打印map
		for(BillPaymentEntity bill:list){
			//声明每一行的map
			Map<String,Object> map = new HashMap<String, Object>();
			//封装应付单号
			map.put("paymentNo",bill.getPaymentNo());
			//封装来源单号
			map.put("sourceBillNo", bill.getSourceBillNo());
			//付款部门
			map.put("paymentOrgName", bill.getPaymentOrgName());
			//付款金额
			map.put("amount", bill.getAmount());
			//业务日期
			if(bill.getBusinessDate()!=null){
				map.put("businessDate", DateUtils.convert(bill.getBusinessDate(), "yyyy-MM-dd "));
			}else{
				map.put("businessDate",null);
			}
			//付款方式
			map.put("paymentType", paymentTypeMap.get(bill.getPaymentType()));
			//审核状态
			map.put("auditStatus", auditStatusMap.get(bill.getAuditStatus()));
			map.put("remitStatus", remitStatusMap.get(bill.getRemitStatus()));
			//是否红单
			map.put("isRedBack", activeMap.get(bill.getIsRedBack()));
			//是否初始化
			map.put("isInit", activeMap.get(bill.getIsInit()));
			//版本号
			map.put("versionNo", bill.getVersionNo().toString());
			
			//将一行的map放到返回list中
			mapList.add(map);
		}
		logger.info("打印设值结束");
		return mapList;
	}
	
	/**
	 * 根据获取查询列表
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-11 上午11:52:00
	 */
	private List<BillPaymentEntity> getQueryDto(JasperContext jasperContext){
		//获取当前登录部门
		String paymentOrgCode = (String)jasperContext.get("paymentOrgCode");
		String empCode = (String)jasperContext.get("empCode");
		//判断当前登录部门
		if(StringUtils.isBlank(paymentOrgCode)){
			throw new SettlementException("当前登录部门为空，不能进行打印操作！");
		}
		//构建用户
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(empCode);
		user.setEmployee(employee);
		//构建部门
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(paymentOrgCode);
		
		//获取用户、部门信息
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		//获取查询service
		IPaymentQueryService queryService = (IPaymentQueryService) jasperContext.getApplicationContext().getBean("paymentQueryService");
		//按单号查询不会超过10个
		List<String> billNos = new ArrayList<String>();	
		//按单号查询不会超过10个
		List<String> sourceNoArray = new ArrayList<String>();	
		//工作流号
		List<String> workFlowNos = new ArrayList<String>();
		//声明查询dto
		BillPaymentQueryDto dto = new BillPaymentQueryDto();
				
		//运单、付款单号单据编号集合
		if(jasperContext.get("billPaymentVo.billPaymentQueryDto.billNoArray")!=null){
			if(jasperContext.get("billPaymentVo.billPaymentQueryDto.billNoArray") instanceof String[]){
				billNos = Arrays.asList((String[]) jasperContext.get("billPaymentVo.billPaymentQueryDto.billNoArray"));
			}else if(jasperContext.get("billPaymentVo.billPaymentQueryDto.billNoArray") instanceof String){
				billNos.add((String)jasperContext.get("billPaymentVo.billPaymentQueryDto.billNoArray"));
			}
			//当单号不等于空值，进行判断是付款单或者是运单			
			if (!CollectionUtils.isEmpty(billNos)) {
				// 付款单list
				List<String> paymentNoList = new ArrayList<String>();
				// 运单list
				List<String> waybillNoList = new ArrayList<String>();
				//进入循环，截取多个单号				
				for (String billNo : billNos) {
					//判断是否按照付款单查询
					if (billNo.trim().startsWith(SettlementConstants.BILL_PREFIX_FK)) {
					//清除空，并加入付款单结合													
						paymentNoList.add(billNo.trim());
					} else {
						//否则按照运单号查询						
						waybillNoList.add(billNo.trim());
					}
				}
				//将查询结果添加至付款单集合				
				dto.setPaymentNos(paymentNoList);
				//将查询结果添加至运单号集合				
				dto.setWaybillNos(waybillNoList);
			}
		}
		
		//来源单据编号集合
		if(jasperContext.get("billPaymentVo.billPaymentQueryDto.sourceBillNoArray")!=null){
			if(jasperContext.get("billPaymentVo.billPaymentQueryDto.sourceBillNoArray") instanceof String[]){
				sourceNoArray = Arrays.asList((String[]) jasperContext.get("billPaymentVo.billPaymentQueryDto.sourceBillNoArray"));
			}else if(jasperContext.get("billPaymentVo.billPaymentQueryDto.sourceBillNoArray") instanceof String){
				sourceNoArray.add((String)jasperContext.get("billPaymentVo.billPaymentQueryDto.sourceBillNoArray"));
			}
			//设置按来源单号查询
			dto.setSourceBillNos(sourceNoArray);
		}
		
		//工作流号集合
		if (jasperContext.get("billPaymentVo.billPaymentQueryDto.workFlowNosArray") != null) {
			if (jasperContext.get("billPaymentVo.billPaymentQueryDto.workFlowNosArray") instanceof String[]) {
				workFlowNos = Arrays.asList((String[]) jasperContext.get("billPaymentVo.billPaymentQueryDto.workFlowNosArray"));
			} else if (jasperContext.get("billPaymentVo.billPaymentQueryDto.workFlowNosArray") instanceof String) {
				workFlowNos.add((String) jasperContext.get("billPaymentVo.billPaymentQueryDto.workFlowNosArray"));
			}
			// 设置按工作流号查询
			dto.setWorkFlowNos(workFlowNos);
		}
		
		//起始日期
		String startAccountDate = (String) jasperContext.get("billPaymentVo.billPaymentQueryDto.startAccountDate");
		//结束日期
		String endAccountDate = (String) jasperContext.get("billPaymentVo.billPaymentQueryDto.endAccountDate");
		//付款方式
		String paymentType = (String) jasperContext.get("billPaymentVo.billPaymentQueryDto.paymentType");
		//客户编码
		String customerCode = (String) jasperContext.get("billPaymentVo.billPaymentQueryDto.customerCode");
		//付款状态
		String remitStatus = (String) jasperContext.get("billPaymentVo.billPaymentQueryDto.remitStatus");
		//是否初始化
		String isInit = (String) jasperContext.get("billPaymentVo.billPaymentQueryDto.isInit");
		
		//对日期进行转化
		if(StringUtils.isNotEmpty(startAccountDate)){
			dto.setStartAccountDate(DateUtils.convert(startAccountDate,"yyyy-MM-dd"));
		}
		//对日期进行转化  结束日期+1
		if(StringUtils.isNotEmpty(endAccountDate)){
			Date endDate = DateUtils.convert(DateUtils.addDay(DateUtils.convert(endAccountDate,"yyyy-MM-dd"), 1), DateUtils.DATE_FORMAT);
			dto.setEndAccountDate(endDate);
		}
		dto.setPaymentType(paymentType);
		dto.setCustomerCode(customerCode);
		dto.setRemitStatus(remitStatus);
		dto.setIsInit(isInit);
		//调用service,获取查询结果
		BillPaymentResultDto resultDto = queryService.queryPaymentBillNotPage(dto, currentInfo);
		//返回结果集
		return resultDto.getBillPaymentEntityList();
	}
	
}
