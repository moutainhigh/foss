package com.deppon.foss.prt.pdapayinreportforpackage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 单个报表数据源
 * @author 045738-foss-maojianqiang
 * @date 2012-12-24 下午7:23:03
 */
public class PDAPayinReportForPackagePrtDataSource implements JasperDataSource{
	/**
	 * 签收单列表
	 */
	private List<DriverCollectionRptDEntity> singRptDList ;
	/**
	 * 返单列表
	 */
	private List<DriverCollectionRptDEntity> returnRptDList;
	
	/**
	 * 定义日期格式化
	 */
	public static final String FORMAT = "yyyy-MM-dd";
	/**
	 * 签收单总数
	 */
	public static final String SINGNAME = "签收单总数"; 
	/**
	 * 返单总数
	 */
	public static final String RETURNNAME = "返单总数";
	/**
	 * 实收返单数
	 * @author 218392 zhangyongxue
	 * @date 2015-07-08 16:50:05
	 */
	public static final String REALRETURNSINGULAR = "实收返单数";
	/**
	 * 声明导出表格列头
	 */
	public static final String TABLE_HEADER_NUM = "序号";
	public static final String TABLE_HEADER_WAYBILLNO = "运单号";
	public static final String TABLE_HEADER_WEIGHT = "重量(kg)";
	public static final String TABLE_HEADER_VOLUMN = "体积m³";
	public static final String TABLE_HEADER_PIECES = "件数";
	public static final String TABLE_HEADER_AMOUNT_CASH = "现付金额(元)";
	public static final String TABLE_HEADER_AMOUNT_ARR = "到付金额(元)";
	public static final String TABLE_HEADER_ISSIGNBILL = "是否签收单";
	public static final String TABLE_HEADER_ISRETURNBILL = "是否返单";
	public static final String TABLE_HEADER_VEHICLENO = "车牌号";
	public static final String TABLE_HEADER_Y = "是";
	public static final String TABLE_HEADER_N = "否";
	public static final String TABLE_HEADER_CARD_AMOUNT = "刷卡收入(元)";
	public static final String TABLE_HEADER_CASH_AMOUNT = "PDA现金收入(元)";
	//@author 218392 zhangyongxue  2015-07-08 16:27:00 付款方式
	public static final String TABLE_HEADER_PAY_TYPE = "付款方式";
	/**
	 * 声明表格类型
	 */
	public static final String TABLE_TYPE_RECVEIVE = "类型：接货";
	public static final String TABLE_TYPE_DELIVER =  "类型：派送";
	
	
	
	/** 
	 * p参数数据源
	 * @author 045738-foss-maojianqiang
	 * @param  打印提供context,可获取前台传递参数，service等
	 * @date 2012-12-24 下午7:24:50
	 * @return 
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		String driverCode = (String)jasperContext.get("driverCode");
		String driverName = (String)jasperContext.get("driverName");
		String beginDate = (String)jasperContext.get("reportBeginDate");
		String endDate = (String)jasperContext.get("reportEndDate");
		Date reportBeginDate = DateUtils.convert(beginDate, DateUtils.DATE_TIME_FORMAT);
		Date reportEndDate = DateUtils.convert(endDate,DateUtils.DATE_TIME_FORMAT);
		String amount = (String)jasperContext.get("receivedAmountTotal");
		String notes = (String)jasperContext.get("notes");
		//@author 218392 zhangyongxue 2015-07-11 16:28:30
		String returnSingular = (String)jasperContext.get("returnSingular");
		BigDecimal  receivedAmountTotal = BigDecimal.ZERO;
		if(!StringUtils.isBlank(amount)){
			receivedAmountTotal = new BigDecimal(amount);
		}

		//设置为下一天的00:00:00
		String currentUser = (String)jasperContext.get("currentUser");
		Date nowDate = new Date();
		
		//声明查询dto
		DriverCollectionRptDto queryDto = new DriverCollectionRptDto();
		queryDto.setDriverCode(driverCode);
		queryDto.setDriverName(driverName);
		queryDto.setReportBeginDate(reportBeginDate);
		queryDto.setReportEndDate(reportEndDate);
		
		//构建用户
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(currentUser);
		user.setEmployee(employee);
		//构建部门
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		//获取用户、部门信息
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		//获取service
		IPDAPayInReportBillCreateService service =(IPDAPayInReportBillCreateService) jasperContext.getApplicationContext().getBean("pDAPayInReportBillCreateService");
		//司机
		DriverCollectionRptDto dto =  service.queryReceiptSendGoodsInfo(queryDto, currentInfo);
		
		//如果实体为空，则抛出异常提示
		if(dto==null){
			throw new SettlementException("没有查询到数据！");
		}
		
		//接货列表
		List<DriverCollectionRptDEntity> singRptDListNew = dto.getReceivedList();
		//送货列表
		List<DriverCollectionRptDEntity> returnRptDListNew = dto.getDeliverList();
		//获取表头信息
		DriverCollectionRptEntity entity = dto.getEntity();
		
		//如果列表为空，则抛出异常
		if(CollectionUtils.isEmpty(singRptDListNew) && CollectionUtils.isEmpty(returnRptDListNew)){
			throw new SettlementException("没有查询到明细数据！");
		}
		//将值付给全局变量
		this.setSingRptDList(singRptDListNew);
		this.setReturnRptDList(returnRptDListNew);
		
		//声明打印map
		Map<String, Object> map = new HashMap<String, Object>();
		String reportNo = "SJ"+new SimpleDateFormat("yyMMddhhmmss").format(new Date());
		map.put("reportNo", reportNo);//报表编号
		//进行日期转化
		/**
		 * @author 218392 zhangyongxue  2015-07-08 17:10:11
		 * 报表日期，这里的日期做成的格式是 :查询的不是同一天显示2015-07-06到2015-07-08这种形式
		 * 日期的值取自查询form表单中起止结束日期，而且只取到年月日，时分秒不取（需求说的）
		 */
		String printBeginDate = new SimpleDateFormat(FORMAT).format(reportBeginDate);
		String printEndDate = new SimpleDateFormat(FORMAT).format(reportEndDate);
		if(printBeginDate.equals(printEndDate)){
			map.put("businessDate",printBeginDate);//当前台页面选择的查询时间是同一天
		}else{
			map.put("businessDate",printBeginDate+"到"+printEndDate);//查询时间不是同一天
		}
		//司机名称
		map.put("driverName", driverName);
		//创建人名称
		map.put("createUserCode", currentUser);
		//创建时间
		map.put("createTime", DateUtils.convert(nowDate,FORMAT));
		//总票数
		map.put("waybillQtyTotal", entity.getWaybillQtyTotal());
		//总重量
		map.put("weightTotal", entity.getWeightTotal());
		//总体积
		map.put("volumeTotal", entity.getVolumeTotal());
		//总件数
		map.put("totalPieces", entity.getPiecesTotal());
		//应收总额
		map.put("receiveAmountTotal", entity.getReceiveAmountTotal());
		//实收总额
		map.put("receivedAmountTotal", receivedAmountTotal);
		//刷卡总额
		map.put("cardTotalAmount", entity.getCardTotalAmount());
		//现金总额
		map.put("cashTotalAmount", entity.getCashTotalAmount());
		//备注
		map.put("notes", notes);
		//如果接货列表有值，则显示签收单条数
		if(singRptDListNew.size()>0){
			map.put("context1", SINGNAME);
			map.put("context2",entity.getSignwaybillTotal());
		}
		//如果送货列表有值，则显示返单条数
		if(returnRptDListNew.size()>0){
			map.put("context3", RETURNNAME);
			map.put("context4", entity.getReturnTicketTotal());
		}
		/**
		 * @author 218392 zhangyongxue 2015-07-08 16:51:16
		 * 实收返单数
		 */
		map.put("context5", REALRETURNSINGULAR);
		map.put("returnSingular", returnSingular);
		return map;
	}

	/** 
	 * list列表数据源
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2012-12-24 下午7:24:50
	 * @return 
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {
		//声明表格数据
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//接货列表
		List<DriverCollectionRptDEntity> singRptDListNew  = this.getSingRptDList();
		//送货列表
		List<DriverCollectionRptDEntity> returnRptDListNew  = this.getReturnRptDList();
		
		//获取接货列表
		if(singRptDListNew.size()>0){
			//声明现付金额
			BigDecimal amountCash = BigDecimal.ZERO;
			//刷卡总收入
			BigDecimal cardAmountTotal = BigDecimal.ZERO;
			//现金总收入
			BigDecimal cashAmountTotal = BigDecimal.ZERO;
			//声明签收单条数
			int singCount = 0;
			
			//声明表格类型
			Map<String, Object> delareMap = new HashMap<String, Object>();
			delareMap.put("totalInfo",TABLE_TYPE_RECVEIVE);
			list.add(delareMap);
			//声明表头
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("rownum", TABLE_HEADER_NUM);//声明序号
			headerMap.put("waybillNo",TABLE_HEADER_WAYBILLNO);
			headerMap.put("vehicleNo",TABLE_HEADER_VEHICLENO);
			headerMap.put("weight",TABLE_HEADER_WEIGHT);
			headerMap.put("volume", TABLE_HEADER_VOLUMN);
			headerMap.put("qty", TABLE_HEADER_PIECES);
			headerMap.put("payType", TABLE_HEADER_PAY_TYPE); //@author 218392 zhangyongxue 2015-07-08 16:28:30
			headerMap.put("amount", TABLE_HEADER_AMOUNT_CASH);
			headerMap.put("cardIncome", TABLE_HEADER_CARD_AMOUNT);
			headerMap.put("cashIncome", TABLE_HEADER_CASH_AMOUNT);
			headerMap.put("isFlag",TABLE_HEADER_ISSIGNBILL);
			list.add(headerMap);
			//循环获取打印数据
			for(int i=0;i<singRptDListNew.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				DriverCollectionRptDEntity entity = singRptDListNew.get(i);
				map.put("rownum", i+1);//声明序号
				map.put("waybillNo",entity.getWaybillNo());
				map.put("weight",entity.getWeight().toString());
				if(StringUtils.isBlank(entity.getVehicleNo())){
					map.put("vehicleNo", "&nbsp;");
				}else{
					map.put("vehicleNo", entity.getVehicleNo());
				}
				map.put("volume", entity.getVolume().toString());
				map.put("qty", entity.getQty().toString());
				//@author 218392 zhangyongxue 2015-07-08 16:29:50
				if(StringUtils.equals("CD",entity.getPayType())){
					map.put("payType", "银行卡");
				}else if(StringUtils.equals("NT",entity.getPayType())){
					map.put("payType", "支票");
				}else if(StringUtils.equals("CH",entity.getPayType())){
					map.put("payType", "现金");
				}else if(StringUtils.equals("TT",entity.getPayType())){
					map.put("payType", "电汇");
				}else if(StringUtils.equals("OL",entity.getPayType())){
					map.put("payType", "网上支付");
				}else if(StringUtils.equals("CT",entity.getPayType())){
					map.put("payType", "月结");
				}else if(StringUtils.equals("DT",entity.getPayType())){
					map.put("payType", "临时欠款");
				}else if(StringUtils.equals("FC",entity.getPayType())){
					map.put("payType", "到付");
				}
				if((StringUtils.equals("CT",entity.getPayType())) || (StringUtils.equals("FC",entity.getPayType())) 
						|| (StringUtils.equals("OL",entity.getPayType()))){
					entity.setAmount(BigDecimal.ZERO);//现付金额
					entity.setCardAmount(BigDecimal.ZERO);//刷卡收入
					entity.setCashAmount(BigDecimal.ZERO);//PDA现金收入
				}
				map.put("amount", entity.getAmount().toString());
				map.put("cardIncome", entity.getCardAmount().toString());
				map.put("cashIncome", entity.getCashAmount().toString());
				//现付金额累加
				amountCash = amountCash.add(entity.getAmount());
				//刷卡总收入
				cardAmountTotal = cardAmountTotal.add(entity.getCardAmount());
				//现金总收入
				cashAmountTotal = cashAmountTotal.add(entity.getCashAmount());
				//是否签收单按
				if(StringUtils.equals(FossConstants.ACTIVE, entity.getIsSignwaybill())){
					map.put("isFlag",TABLE_HEADER_Y);
					//签收单数+1
					singCount = singCount+1;
				}else{
					map.put("isFlag",TABLE_HEADER_N);
				}
				list.add(map);
			}
			//声明表格结尾
			Map<String, Object> sumInfo = new HashMap<String, Object>();
			sumInfo.put("totalInfo", "合计   "+"现付金额: "+amountCash.toString()+" 票数: "+singRptDListNew.size()+"  签收单数："+singCount
						+" 刷卡总收入: "+cardAmountTotal+"  PDA现金总收入："+cashAmountTotal);
			list.add(sumInfo);
		}
		
		//获取送货类表
		if(returnRptDListNew.size()>0){
			//声明现付金额
			BigDecimal amountArr = BigDecimal.ZERO;
			//刷卡总收入
			BigDecimal cardAmountTotal = BigDecimal.ZERO;
			//现金总收入
			BigDecimal cashAmountTotal = BigDecimal.ZERO;
			//声明签收单条数
			int returnCount = 0;
			
			//声明表格类型
			Map<String, Object> delareMap = new HashMap<String, Object>();
			delareMap.put("totalInfo",TABLE_TYPE_DELIVER);
			list.add(delareMap);
			//声明表头
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("rownum", TABLE_HEADER_NUM);//声明序号
			headerMap.put("waybillNo",TABLE_HEADER_WAYBILLNO);
			headerMap.put("vehicleNo",TABLE_HEADER_VEHICLENO);
			headerMap.put("weight",TABLE_HEADER_WEIGHT);
			headerMap.put("volume", TABLE_HEADER_VOLUMN);
			headerMap.put("qty", TABLE_HEADER_PIECES);
			headerMap.put("amount", TABLE_HEADER_AMOUNT_ARR);
			headerMap.put("payType", TABLE_HEADER_PAY_TYPE); //@author 218392 zhangyongxue 2015-07-08 16:53:30
			headerMap.put("cardIncome", TABLE_HEADER_CARD_AMOUNT);
			headerMap.put("cashIncome", TABLE_HEADER_CASH_AMOUNT);
			headerMap.put("isFlag",TABLE_HEADER_ISRETURNBILL);
			list.add(headerMap);
			//循环获取打印数据
			for(int i=0;i<returnRptDListNew.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				DriverCollectionRptDEntity entity = returnRptDListNew.get(i);
				map.put("rownum", i+1);//声明序号
				map.put("waybillNo",entity.getWaybillNo());
				if(StringUtils.isBlank(entity.getVehicleNo())){
					map.put("vehicleNo", "&nbsp;");
				}else{
					map.put("vehicleNo", entity.getVehicleNo());
				}
				map.put("payType", "到付");//@author 218392 zhangyongxue 2015-07-08 16:55:69
				map.put("weight",entity.getWeight().toString());
				map.put("volume", entity.getVolume().toString());
				map.put("qty", entity.getQty().toString());
				map.put("amount", entity.getAmount().toString());
				map.put("cardIncome", entity.getCardAmount().toString());
				map.put("cashIncome", entity.getCashAmount().toString());
				//现付金额累加
				amountArr = amountArr.add(entity.getAmount());
				//刷卡总收入
				cardAmountTotal = cardAmountTotal.add(entity.getCardAmount());
				//现金总收入
				cashAmountTotal = cashAmountTotal.add(entity.getCashAmount());
				//是否返单
				if(StringUtils.equals(FossConstants.ACTIVE, entity.getIsReturnTicket())){
					map.put("isFlag",TABLE_HEADER_Y);
					//返单数+1
					returnCount = returnCount+1;
				}else{
					map.put("isFlag",TABLE_HEADER_N);
				}
				list.add(map);
			}
			//声明表格结尾
			Map<String, Object> sumInfo = new HashMap<String, Object>();
			sumInfo.put("totalInfo", "合计   "+"到付金额: "+amountArr.toString()+"  票数: "+returnRptDListNew.size()+"  返单数："+returnCount
					+" 刷卡总收入: "+cardAmountTotal+" PDA现金总收入: "+cashAmountTotal);
			list.add(sumInfo);
		}
		return list;
	}

	
	/**
	 * @return singRptDList
	 */
	public List<DriverCollectionRptDEntity> getSingRptDList() {
		return singRptDList;
	}

	
	/**
	 * @param singRptDList
	 */
	public void setSingRptDList(List<DriverCollectionRptDEntity> singRptDList) {
		this.singRptDList = singRptDList;
	}

	
	/**
	 * @return returnRptDList
	 */
	public List<DriverCollectionRptDEntity> getReturnRptDList() {
		return returnRptDList;
	}

	
	/**
	 * @param returnRptDList
	 */
	public void setReturnRptDList(List<DriverCollectionRptDEntity> returnRptDList) {
		this.returnRptDList = returnRptDList;
	}

	
}
