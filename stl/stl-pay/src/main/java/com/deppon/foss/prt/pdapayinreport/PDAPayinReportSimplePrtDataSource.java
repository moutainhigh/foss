package com.deppon.foss.prt.pdapayinreport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 单个报表数据源
 * @author 045738-foss-maojianqiang
 * @date 2012-12-24 下午7:23:03
 */
public class PDAPayinReportSimplePrtDataSource implements JasperDataSource{
	/**
	 * 报表编号
	 */
	private String reportNo = null;
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
	/**
	 * 声明表格类型
	 */
	public static final String TABLE_TYPE_RECVEIVE = "类型：接货";
	public static final String TABLE_TYPE_DELIVER =  "类型：派送";
	
	
	
	
	/**
	 * 构造方法
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-24 下午7:29:59
	 */
	public PDAPayinReportSimplePrtDataSource(String reportNo){
		this.reportNo = reportNo;
	}
	
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
		//校验报表编号
		if(StringUtils.isBlank(reportNo)){
			throw new SettlementException("传入的报表编号为空，不能进行打印操作！");
		}
		//获取service
		IPDAPayInReportBillCreateService service =(IPDAPayInReportBillCreateService) jasperContext.getApplicationContext().getBean("pDAPayInReportBillCreateService");
		//司机
		DriverCollectionRptEntity entity = 	service.queryReceiveReportByReportNo(reportNo);
		//如果实体为空，则抛出异常提示
		if(entity==null){
			throw new SettlementException("没有查询到数据！");
		}
		//实体明细
		List<DriverCollectionRptDEntity> rptDList = service.queryRptDEntityByReportNo(reportNo,entity.getBusinessDate());
		//如果列表为空，则抛出异常
		if(CollectionUtils.isEmpty(rptDList)){
			throw new SettlementException("没有查询到明细数据！");
		}
		//接货列表
		List<DriverCollectionRptDEntity> singRptDListNew  = new ArrayList<DriverCollectionRptDEntity>();
		//送货列表
		List<DriverCollectionRptDEntity> returnRptDListNew   = new ArrayList<DriverCollectionRptDEntity>();
		
		//循环遍历明细列表，获取接货和送货列表
		for(DriverCollectionRptDEntity d:rptDList){
			//接货
			if(StringUtils.equals(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP, d.getType())){
				singRptDListNew.add(d);
			}else{
				returnRptDListNew.add(d);
			}
		}
		//将值付给全局变量
		this.setSingRptDList(singRptDListNew);
		this.setReturnRptDList(returnRptDListNew);
		
		//声明打印map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportNo", reportNo);//报表编号
		//进行日期转化
		if(entity.getBusinessDate()!=null){
			map.put("businessDate",DateUtils.convert(entity.getBusinessDate(),FORMAT));
		}else{
			map.put("businessDate",null);
		}
		//司机名称
		map.put("driverName", entity.getDriverName());
		//车牌号
		//map.put("vehicleNo", entity.getVehicleNo());
		//创建人名称
		map.put("createUserCode", entity.getCreateUserName());
		//创建时间
		if(entity.getCreateTime()!=null){
			map.put("createTime", DateUtils.convert(entity.getCreateTime(),FORMAT));
		}else{
			map.put("createTime", null);
		}
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
		//备注
		map.put("receivedAmountTotal", entity.getReceivedAmountTotal());
		map.put("notes", entity.getNotes());
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
			headerMap.put("amount", TABLE_HEADER_AMOUNT_CASH);
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
				map.put("amount", entity.getAmount().toString());
				//现付金额累加
				amountCash = amountCash.add(entity.getAmount());
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
			sumInfo.put("totalInfo", "合计   "+"现付金额："+amountCash.toString()+"  票数："+singRptDListNew.size()+"  签收单数："+singCount);
			list.add(sumInfo);
		}
		
		//获取送货类表
		if(returnRptDListNew.size()>0){
			//声明现付金额
			BigDecimal amountArr = BigDecimal.ZERO;
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
				
				map.put("weight",entity.getWeight().toString());
				map.put("volume", entity.getVolume().toString());
				map.put("qty", entity.getQty().toString());
				map.put("amount", entity.getAmount().toString());
				//现付金额累加
				amountArr = amountArr.add(entity.getAmount());
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
			sumInfo.put("totalInfo", "合计   "+"到付金额："+amountArr.toString()+"  票数："+returnRptDListNew.size()+"  返单数："+returnCount);
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
