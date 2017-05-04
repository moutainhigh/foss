package com.deppon.foss.prt.ofvpaymentreport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.util.CollectionUtils;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IOfvPaymentReportQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


/**
 * 外请车付款报表打印
 * @author foss-qiaolifeng
 * @date 2013-3-29 下午2:39:04
 */
public class OfvPaymentReportSimplePrtDataSource implements JasperDataSource {


	
	/**
	 * 首款列表
	 */
	private List<OfvPaymentReportResultDto> preOfvList; 
	
	/**
	 * 尾款列表
	 */
	private List<OfvPaymentReportResultDto> arrOfvList;
	 
	/**
	 * 定义日期格式化
	 */
	public static final String FORMAT = "yyyy-MM-dd";
	
	/**
	 * 声明导出表格列头
	 */
	public static final String PAYABLE_TYPE_F = "付款类型: 首款";
	public static final String PAYABLE_TYPE_L = "付款类型: 尾款";
	
	public static final String TABLE_HEADER_VEHICLE_ASSEMBLE_BILL_NO = "车次号";
	public static final String TABLE_HEADER_VEHICLE_NO = "车牌号";
	public static final String TABLE_HEADER_DRIVER_NAME = "司机";
	public static final String TABLE_HEADER_ORIG_ORG = "出发部门";
	public static final String TABLE_HEADER_DEST_ORG = "到达部门";
	public static final String TABLE_HEADER_FEE_TOTAL = "运费总额";
	public static final String TABLE_HEADER_PRE_PAID_FEE = "出发实付";
	public static final String TABLE_HEADER_ARR_PAID_FEE = "到达实付";
	public static final String TABLE_HEADER_ADJUST_PAID_FEE = "增减变化";
	public static final String TABLE_HEADER_AUTUAL_FEE = "实付总运费";
	public static final String TABLE_HEADER_BE_RETURN_RECEIPT = "是否押回单";
	
	/** 
	 * P参数数据源
	 * @author foss-qiaolifeng
	 * @date 2013-3-29 下午4:47:11
	 * @param jasperContext
	 * @return
	 * @throws Exception
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		
		//获取当前工作流号
		String workFlowNo = (String)jasperContext.get("workFlowNo");
		//获取当前员工编码
		String empCode = (String)jasperContext.get("empCode");
		
		//工作流号不能为空
		if(StringUtils.isEmpty(workFlowNo)){
			throw new SettlementException("传入的工作流号为空，不能进行操作");
		}
		
		// 员工号不能为空
		if (StringUtils.isEmpty(empCode)) {
			throw new SettlementException("传入的员工号为空，请重新登录并操作");
		}
		
		//获取外请车报表service
		IOfvPaymentReportQueryService ofvPaymentReportQueryService = (IOfvPaymentReportQueryService)jasperContext.getApplicationContext().getBean("ofvPaymentReportQueryService");
		
		//生成查询数据dto
		OfvPaymentReportQueryDto dto = new OfvPaymentReportQueryDto();
		dto.setWorkFlowNo(workFlowNo);//设置工作流号
		dto.setEmpCode(empCode);//设置员工编码
		//查询外请车付款信息
		List<OfvPaymentReportResultDto> resultDtoList = ofvPaymentReportQueryService.queryOfvPaymentReportByWorkFlowNo(dto);
		
		//没有查询数据，不允许打印
		if(CollectionUtils.isEmpty(resultDtoList)){
			throw new SettlementException("打印时,没有查询到外请车付款数据");
		}
		
		//出发付款总金额
		BigDecimal prePaidFeeTotal = BigDecimal.ZERO;
		//到达付款总金额
		BigDecimal arrPaidFeeTotal = BigDecimal.ZERO;
		//调整费用总金额
		BigDecimal adjustPaidFeeTotal = BigDecimal.ZERO;
		//出发实付总金额
		BigDecimal prePaidFeeSum =  BigDecimal.ZERO;
		//到达实付总金额
		BigDecimal arrPaidFeeSum = BigDecimal.ZERO;
		
		//首款列表
		List<OfvPaymentReportResultDto> preOfvListNew = new ArrayList<OfvPaymentReportResultDto>(); 
		//尾款列表
		List<OfvPaymentReportResultDto> arrOfvListNew= new ArrayList<OfvPaymentReportResultDto>();
		//循环处理
		for(OfvPaymentReportResultDto resultdto : resultDtoList){
			
			if(resultdto.getPrePaidFee()==null){
				resultdto.setPrePaidFee(BigDecimal.ZERO);
			}
			
			if(resultdto.getArrivePaidFee()==null){
				resultdto.setArrivePaidFee(BigDecimal.ZERO);
			}
			
			if(resultdto.getAdjustPaidFee()==null){
				resultdto.setAdjustPaidFee(BigDecimal.ZERO);
			}
			
			//出发付款累加
			prePaidFeeTotal = prePaidFeeTotal.add(resultdto.getPrePaidFee());
			//到达付款累加
			arrPaidFeeTotal = arrPaidFeeTotal.add(resultdto.getArrivePaidFee());
			//调整费用累加
			adjustPaidFeeTotal = adjustPaidFeeTotal.add(resultdto.getAdjustPaidFee());
			
			//首款
			if(resultdto.getPrePaidFee().compareTo(BigDecimal.ZERO)>0){
				//加入到首款列表
				preOfvListNew.add(resultdto);
			//尾款	
			}else if(resultdto.getArrivePaidFee().compareTo(BigDecimal.ZERO)>0){
				//加入到尾款列表
				arrOfvListNew.add(resultdto);
			}
		}
		//出发实付
		prePaidFeeSum = prePaidFeeTotal;
		//到达实付
		arrPaidFeeSum = arrPaidFeeTotal;
	
		
		//设置全局首款、尾款列表
		this.setPreOfvList(preOfvListNew);//首款
		this.setArrOfvList(arrOfvListNew);//尾款
		
		//声明打印map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workFlowNo", workFlowNo);//工作流号
		map.put("totalRecordsInDB", resultDtoList.size());//合计票数
		map.put("prePaidFeeSum", prePaidFeeSum.toString());//出发实付
		map.put("arrivePaidFeeSum", arrPaidFeeSum.toString());//到达实付
		map.put("actualFeeTotal", prePaidFeeSum.add(arrPaidFeeSum).toString());//实付总额
		
		return map;
	}

	@Override
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {

		// 声明表格数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 首款列表
		List<OfvPaymentReportResultDto> preOfvListNew = this.getPreOfvList();
		// 尾款列表
		List<OfvPaymentReportResultDto> arrOfvListNew = this.getArrOfvList();
		
		//处理打印数据
		if(!CollectionUtils.isEmpty(preOfvListNew)){
			
			//声明表格类型
			Map<String, Object> delareMap = new HashMap<String, Object>();
			delareMap.put("totalInfo", PAYABLE_TYPE_F);//付款类型：首款
			list.add(delareMap);//加入打印列表
			
			//声明表头
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("vehicleAssembleBillNo", TABLE_HEADER_VEHICLE_ASSEMBLE_BILL_NO);
			headerMap.put("vehicleNo",TABLE_HEADER_VEHICLE_NO);
			headerMap.put("driverName",TABLE_HEADER_DRIVER_NAME);
			headerMap.put("origOrgName",TABLE_HEADER_ORIG_ORG);
			headerMap.put("destOrgName", TABLE_HEADER_DEST_ORG);
			headerMap.put("feeTotal", TABLE_HEADER_FEE_TOTAL);
			headerMap.put("prePaidFee", TABLE_HEADER_PRE_PAID_FEE);
			headerMap.put("arrivePaidFee",TABLE_HEADER_ARR_PAID_FEE);
			headerMap.put("adjustPaidFee",TABLE_HEADER_ADJUST_PAID_FEE);
			headerMap.put("actualFee",TABLE_HEADER_AUTUAL_FEE);
			headerMap.put("beReturnReceipt",TABLE_HEADER_BE_RETURN_RECEIPT);
			list.add(headerMap);
			
			//出发付款总金额
			BigDecimal prePaidFeeTotal = BigDecimal.ZERO;
			//到达付款总金额
			BigDecimal arrPaidFeeTotal = BigDecimal.ZERO;
			//实际付款总金额
			BigDecimal actualFeeTotal = BigDecimal.ZERO;
			//循环处理首款
			for(OfvPaymentReportResultDto dto:preOfvListNew){
				//出发付款累加
				prePaidFeeTotal = prePaidFeeTotal.add(dto.getPrePaidFee());
				//到达付款累加
				arrPaidFeeTotal = arrPaidFeeTotal.add(dto.getArrivePaidFee());
				//数据列
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vehicleAssembleBillNo", dto.getVehicleAssembleBillNo());
				map.put("vehicleNo", dto.getVehicleNo());
				map.put("driverName", dto.getDriverName());
				map.put("origOrgName", dto.getOrigOrgName());
				map.put("destOrgName", dto.getDestOrgName());
				map.put("feeTotal", dto.getFeeTotal().toString());
				map.put("prePaidFee", dto.getPrePaidFee().toPlainString());
				map.put("arrivePaidFee", dto.getArrivePaidFee().toString());
				map.put("adjustPaidFee", dto.getAdjustPaidFee().toString());
				map.put("actualFee", dto.getPrePaidFee().toString());
				map.put("beReturnReceipt", dto.getBeReturnReceipt());
				//加入到打印列表
				list.add(map);
			}
			//实际付款
			actualFeeTotal = prePaidFeeTotal.add(arrPaidFeeTotal);
			
			//声明表格结尾
			Map<String, Object> sumInfo = new HashMap<String, Object>();
			sumInfo.put("totalInfo", "小计: "+preOfvListNew.size()
					+"  		出发付款："+prePaidFeeTotal.toString()
					+"  		到达付款："+arrPaidFeeTotal.toString()
					+"  		实付："+actualFeeTotal.toString());
			list.add(sumInfo);
			
		}
		
		// 处理打印数据
		if (!CollectionUtils.isEmpty(arrOfvListNew)) {

			// 声明表格类型
			Map<String, Object> delareMap = new HashMap<String, Object>();
			delareMap.put("totalInfo", PAYABLE_TYPE_L);// 付款类型：尾款
			list.add(delareMap);// 加入打印列表

			// 声明表头
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("vehicleAssembleBillNo",TABLE_HEADER_VEHICLE_ASSEMBLE_BILL_NO);
			headerMap.put("vehicleNo", TABLE_HEADER_VEHICLE_NO);
			headerMap.put("driverName", TABLE_HEADER_DRIVER_NAME);
			headerMap.put("origOrgName", TABLE_HEADER_ORIG_ORG);
			headerMap.put("destOrgName", TABLE_HEADER_DEST_ORG);
			headerMap.put("feeTotal", TABLE_HEADER_FEE_TOTAL);
			headerMap.put("prePaidFee", TABLE_HEADER_PRE_PAID_FEE);
			headerMap.put("arrivePaidFee", TABLE_HEADER_ARR_PAID_FEE);
			headerMap.put("adjustPaidFee", TABLE_HEADER_ADJUST_PAID_FEE);
			headerMap.put("actualFee", TABLE_HEADER_AUTUAL_FEE);
			headerMap.put("beReturnReceipt", TABLE_HEADER_BE_RETURN_RECEIPT);
			list.add(headerMap);

			// 出发付款总金额
			BigDecimal prePaidFeeTotal = BigDecimal.ZERO;
			// 到达付款总金额
			BigDecimal arrPaidFeeTotal = BigDecimal.ZERO;
			// 实际付款总金额
			BigDecimal actualFeeTotal = BigDecimal.ZERO;
			// 调整变化总金额
			BigDecimal adjustPaidFeeTotal = BigDecimal.ZERO;
			
			// 循环处理首款
			for (OfvPaymentReportResultDto dto : arrOfvListNew) {
				// 出发付款累加
				prePaidFeeTotal = prePaidFeeTotal.add(dto.getPrePaidFee());
				// 到达付款累加
				arrPaidFeeTotal = arrPaidFeeTotal.add(dto.getArrivePaidFee());
				// 调整变化付款累加
				adjustPaidFeeTotal = adjustPaidFeeTotal.add(dto.getAdjustPaidFee());
				
				// 数据列
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vehicleAssembleBillNo", dto.getVehicleAssembleBillNo());
				map.put("vehicleNo", dto.getVehicleNo());
				map.put("driverName", dto.getDriverName());
				map.put("origOrgName", dto.getOrigOrgName());
				map.put("destOrgName", dto.getDestOrgName());
				map.put("feeTotal", dto.getFeeTotal().toString());
				map.put("prePaidFee", dto.getPrePaidFee().toPlainString());
				map.put("arrivePaidFee", dto.getArrivePaidFee().toString());
				map.put("adjustPaidFee", dto.getAdjustPaidFee().toString());
				map.put("actualFee", dto.getArrivePaidFee());
				map.put("beReturnReceipt", dto.getBeReturnReceipt());
				// 加入到打印列表
				list.add(map);
			}
			// 实际付款
			actualFeeTotal = prePaidFeeTotal.add(arrPaidFeeTotal);

			// 声明表格结尾
			Map<String, Object> sumInfo = new HashMap<String, Object>();
			sumInfo.put("totalInfo","小计: " + arrOfvListNew.size() 
					+ "  		出发付款："+ prePaidFeeTotal.toString() 
					+ "  		到达付款："+ arrPaidFeeTotal.toString() 
					+ "  		实付："+ actualFeeTotal.toString());
			list.add(sumInfo);

		}
		
		return list;
	}

	
	
	/**
	 * @get
	 * @return preOfvList
	 */
	public List<OfvPaymentReportResultDto> getPreOfvList() {
		/*
		 * @get
		 * @return preOfvList
		 */
		return preOfvList;
	}

	/**
	 * @set
	 * @param preOfvList
	 */
	public void setPreOfvList(List<OfvPaymentReportResultDto> preOfvList) {
		/*
		 *@set
		 *@this.preOfvList = preOfvList
		 */
		this.preOfvList = preOfvList;
	}

	
	/**
	 * @get
	 * @return arrOfvList
	 */
	public List<OfvPaymentReportResultDto> getArrOfvList() {
		/*
		 * @get
		 * @return arrOfvList
		 */
		return arrOfvList;
	}

	
	/**
	 * @set
	 * @param arrOfvList
	 */
	public void setArrOfvList(List<OfvPaymentReportResultDto> arrOfvList) {
		/*
		 *@set
		 *@this.arrOfvList = arrOfvList
		 */
		this.arrOfvList = arrOfvList;
	}

}
