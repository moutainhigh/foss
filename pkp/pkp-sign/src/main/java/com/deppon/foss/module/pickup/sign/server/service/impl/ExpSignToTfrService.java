package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignToTfrService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISerialSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 提供给中转的营业部签收接口 快递100
 * @author 
 *		foss-meiying
 * @date 
 *      2015-3-3上午10:37:11
 * @since
 * @version
 */
public class ExpSignToTfrService implements IExpSignToTfrService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.ExpSignToTfrService";
	
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpSignToTfrService.class);

	/**
	 *  到达联service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;

	/**
	 *  签收明细service
	 */
	private ISignDetailService signDetailService;

	/**
	 *  运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;

	/**
	 *  结算签收Service
	 */
	private ILineSignService lineSignService;

	/**
	 * 结清货款Service
	 */
	private IRepaymentService repaymentService;
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	
	/**
	 *  运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * 签收变更结果接口
	 */
	private ISignChangeService signChangeService;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;
	private IRepaymentDao repaymentDao;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IEmployeeService employeeService;
	private ILabeledGoodService labeledGoodService;
	private IWaybillTransactionService waybillTransactionService;
	private ICalculateTransportPathService calculateTransportPathService;
	private IWeixinSendService weixinSendService;
	
	/**
	 * 按流水签收件数
	 */
	private static final int EXTERNAL_SIGN_NUM = 1;
	
	/**
	 * 轨迹推送接口（快递100,、菜鸟）
	 */
	private ISendWaybillTrackService sendWaybillTrackService; 
	/**
	 * 轨迹推送接口 注入
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 落地配service接口
	 */
	private ILdpExternalBillService ldpExternalBillService;
	/**
	 * Sets the 落地配service.
	 *
	 * @param ldpExternalBillService the new 落地配service
	 */
	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	/**
	 * 外发流水签收
	 * dao接口
	 */
	private ISerialSignResultService serialSignResultService;
	
	public void setSerialSignResultService(
			ISerialSignResultService serialSignResultService) {
		this.serialSignResultService = serialSignResultService;
	}
	
	/**
	 * Sets the 签收明细service.
	 *
	 * @param signDetailService the new 签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	/**
	 * Sets the 到达联service.
	 *
	 * @param arriveSheetManngerService the new 到达联service
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * Sets the 运单签收结果service.
	 *
	 * @param waybillSignResultService the new 运单签收结果service
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Sets the 结算签收Service.
	 *
	 * @param lineSignService the new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}	

	/**
	 * Sets the 结清货款Service.
	 *
	 * @param repaymentService the new 结清货款Service
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	/**
	 * Sets the 中转出库接口.
	 *
	 * @param stockService the new 中转出库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * Sets the 运单状态服务接口.
	 *
	 * @param actualFreightService the new 运单状态服务接口
	 */
	public void setActualFreightService(
			IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	private CurrentInfo getCurrentInfo(ArriveSheetEntity entity){
		//通过司机工号查询姓名
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(entity.getOperatorCode());
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		// 获取操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getOperateOrgCode());
		if(emp != null){
			user.setEmployee(emp);//得到员工信息
		}else {
			emp = new EmployeeEntity();
			emp.setEmpName("快递100自动签收");//员工名称
			emp.setEmpCode("快递100自动签收");//员工编码
			user.setEmployee(emp);//得到员工信息
		}
		if(org != null){
			dept.setName(org.getName());// 得到部门名称
			dept.setUnifiedCode(org.getUnifiedCode());//标杆编码
		}else {//其他
			if(StringUtils.isNotBlank(entity.getOperateOrgName())){
				dept.setName(entity.getOperateOrgName());
			}else{
				
				dept.setName("快递100自动签收");//默认的部门名称
			}
		}
		//部门编码
		dept.setCode(entity.getOperateOrgCode());
		return new CurrentInfo(user, dept);//返回信息
	}
	 /**
	 * 提供给中转的营业部签收接口 快递100
	 * 
	 * @author 159231-meiying
	 * @date 2015-2-4 
	 */
	@Override
	public String addExpressArrivesheetForTfr(ArriveSheetEntity entity){
		Date systemDate = new Date();
		// 返回信息  
		String resultMsg = "";
		LOGGER.info("提供给中转的营业部签收接口开始：" + ReflectionToStringBuilder.toString(entity));//记录日志
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
		if(waybill== null){
			throw new SignException(SignException.WAYBILLNO_NULL);//该运单号不存在
		}
		CurrentInfo currentInfo = this.getCurrentInfo(entity);
		this.autoRepayment(entity, systemDate,currentInfo);//自动结清货款
		//判断当前运单是否签收
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
		if(WaybillConstants.INNER_PICKUP.equals(waybill.getReceiveMethod())){//提货方式为汽运内部自提
			LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
		}else {
			try {
				ArriveSheetEntity arriveSheetEntity = new ArriveSheetEntity(entity.getWaybillNo(), ArriveSheetConstants.STATUS_SIGN,
						FossConstants.ACTIVE,FossConstants.NO);
				Long signCountLong = arriveSheetManngerService.queryArriveExistSign(arriveSheetEntity);
				// 系统调用财务接口完成财务出库 首先判断是否第一次签收。
				//如果是就调用，如果不是，就不调用。 根据运单号判断到达联表里是否存在签收。
				// 系统调用财务接口完成财务出库
				if (signCountLong == null || SignConstants.ZERO >= signCountLong) {
					LineSignDto dto=new LineSignDto();
					// 签收日期
					dto.setSignDate(systemDate);
					dto.setProductType(waybill.getProductCode());
					dto.setIsWholeVehicle(waybill.getIsWholeVehicle());
					// 运单号
					dto.setWaybillNo(entity.getWaybillNo());
					// 操作人名称
					dto.setOperatorName(currentInfo.getEmpName());
					// 操作人编码
					dto.setOperatorCode(currentInfo.getEmpCode());
					// 签收部门名称
					dto.setSignOrgName(currentInfo.getCurrentDeptName());
					// 签收部门编码
					dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
					// 签收类型为专线
					dto.setSignType(SettlementConstants.LINE_SIGN);
					// 不是PDA签收
					dto.setIsPdaSign(FossConstants.NO);
					LOGGER.info("--调用结算子系统“签收接口”开始传入参数：----"+ReflectionToStringBuilder.toString(dto));//记录日志
					//确认收入，子母件一套确认规则，正常运单一套确认规则
					this.confirmTaking(dto, currentInfo);
				}
			} catch (SettlementException se) {//捕获异常  
				LOGGER.error("用财务接口完成财务出库 异常",se);//记录日志
				// 异常处理
				throw new SignException(se.getErrorCode(),se);
			}
		}
		//初始化一条到达联数据
		initArriveSheet(entity, currentInfo, waybill);
		List<LabeledGoodEntity> labels=labeledGoodService.queryAllSerialByWaybillNo(entity.getWaybillNo());
		if(CollectionUtils.isNotEmpty(labels)){
			for (LabeledGoodEntity labeledGoodEntity : labels) {
				SignDetailEntity signDetail = new SignDetailEntity();
				signDetail.setArrivesheetNo(entity.getArrivesheetNo());
				signDetail.setSerialNo(labeledGoodEntity.getSerialNo());
				signDetail.setSituation(entity.getSituation());
				//添加签收明细信息
				signDetailService.addSignDetailInfo(signDetail);
				InOutStockEntity inOutStock = new InOutStockEntity();
				// 运单号
				inOutStock.setWaybillNO(entity.getWaybillNo());
				// 流水号
				inOutStock.setSerialNO(labeledGoodEntity.getSerialNo());
				inOutStock.setOperatorCode(entity.getOperatorCode());
				inOutStock.setOperatorName(entity.getOperator());
				// 部门编码
				inOutStock.setOrgCode(currentInfo.getCurrentDeptCode());
				// 出入库类型 签收出库
				inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
				// 进行出库操作
				try {
					stockService.outStockDelivery(inOutStock);
				} catch (StockException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
				}
			}
		}
		try {
			//做签收操作
			this.initWaybillSignResult(entity,systemDate,currentInfo,waybill);
		} catch (SignException e) {//捕获异常
			throw new SignException(e.getErrorCode(), e);//抛出异常
		}
		todoActionService.updateLabeledStatusByWaybillNum(waybill.getWaybillNo(),null);
		WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
		BeanUtils.copyProperties(entity,waybillSigndto);//复制代码
		waybillSigndto.setRecordReceiverMessageStop(true);
		//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门  需求要求只给发货人发
		resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
		//应用监控签收调用
		waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
		if(isSendInvoiceInfo){
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			waybillSignResultService.sendInvoiceInfo(waybill,actual);
		}
		return resultMsg;
	}
	private void autoRepayment(ArriveSheetEntity entity,Date systemDate,CurrentInfo currentInfo ){
		boolean flag = repaymentService.isPayment(entity.getWaybillNo());
		if(!flag){	// 付款方式为现金
			RepaymentEntity repaymentEntity=new RepaymentEntity();
			//得到结算应收单信息
			FinancialDto financialDto =  repaymentService.queryFinanceSign(entity.getWaybillNo());
			//应收代收款
			BigDecimal receiveableAmount = financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
			//应收到付款
			BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
			// 生成付款编号
			StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
			//拼接付款编号
			dateStr = dateStr.append(entity.getWaybillNo());
			//付款方式
			repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			//付款编号
			repaymentEntity.setRepaymentNo(dateStr.toString());
			//付款时间
			repaymentEntity.setPaymentTime(systemDate);
			//操作人
			repaymentEntity.setOperator(currentInfo.getEmpName());
			//操作人编码
			repaymentEntity.setOperatorCode(currentInfo.getEmpCode());
			//操作部门
			repaymentEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
			//操作部门编码
			repaymentEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//币种
			repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			repaymentEntity.setWaybillNo(entity.getWaybillNo());
			//当实付运费和代收货款同时为0时 设置为财务单据无需生成
			if(BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
				&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)){
				// 更新ActualFreight表中的结清状态为已结清
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
				//结清状态-已结清
				actualFreightEntity.setSettleStatus(FossConstants.YES);
				//结款日期
				actualFreightEntity.setSettleTime(systemDate);
				actualFreightEntity.setArriveNotoutGoodsQty(0);
				actualFreightEntity.setGenerateGoodsQty(entity.getSignGoodsQty());
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				//无需结清 将付款信息置0
				repaymentEntity.setActualFreight(BigDecimal.ZERO);
				//无需结清 将付款信息置0
				repaymentEntity.setCodAmount(BigDecimal.ZERO);
				//设置财务单据无需生成
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
			}else{
				if(receiveableAmount.compareTo(new BigDecimal(0))!= 0){
					LOGGER.info("提供给中转的营业部签收接口：运单"+entity.getWaybillNo()+"应收代收款不等于0");
					throw new SignException("该运单应收代收款不等于0");
				}else{
					LOGGER.info("提供给中转的营业部签收接口开始：" +entity.getWaybillNo()+"该运单应收到付款不等于0");
					throw new SignException("该运单应收到付款不等于0");
				}
			}
			// 生成付款信息
			repaymentDao.addPaymentInfo(repaymentEntity);
		}else{
			// 更新ActualFreight表中的结清状态为已结清
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//运单号
			actualFreightEntity.setWaybillNo(entity.getWaybillNo());
			actualFreightEntity.setGenerateGoodsQty(entity.getSignGoodsQty());
			actualFreightEntity.setArriveNotoutGoodsQty(0);
			//更新actualFreight表
			actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
		}
	}
	private void initWaybillSignResult(ArriveSheetEntity entity,Date systemDate,CurrentInfo currentInfo,WaybillEntity waybill){
				
		WeixinSendDto dto = new WeixinSendDto();
		SignRfcEntity checkEntity = new SignRfcEntity();
		// 运单号
		checkEntity.setWaybillNo(entity.getWaybillNo());
		// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
		checkEntity.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
	    if(signChangeService.isRfc(checkEntity)){
	    	throw new SignException(SignException.SIGN_RFC_SIGN_APPROVALIN); //该运单有到达更改还没有审批，请审批后再签收
	    }
	    
	    // 传入参数(运单号,当前运单号生效)
	    WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(entity.getWaybillNo(), FossConstants.ACTIVE);
	    // 根据运单号 查询运单签收结果
	    WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
	    if(null != waybillSign){
	    	if(waybill.getGoodsQtyTotal() <= waybillSign.getSignGoodsQty()){ //控制并发，
	    		LOGGER.error("当前运单已签收！");//记录日志
	    		throw new AirAgencySignException("当前运单已签收！");
	    	}
	    	if(waybill.getGoodsQtyTotal() < (waybillSign.getSignGoodsQty() + entity.getSignGoodsQty())){
	    		LOGGER.error("已签收件数+本次签收件数大于运单开单件数！");//记录日志
	    		throw new AirAgencySignException("已签收件数+本次签收件数大于运单开单件数！");
	    	}
	    }else{//开单件数和签收件数比较
	    	if(waybill.getGoodsQtyTotal() < entity.getSignGoodsQty()){
	    		LOGGER.error("运单："+entity.getWaybillNo()+"签收件数 >开单件数");//记录日志
	    		throw new AirAgencySignException("运单："+entity.getWaybillNo()+"签收件数 >开单件数");
	    	}
	    }
		//判断当前运单是否是一票多件
		List<LdpExternalBillDto> ldpExternalBillEntityList = ldpExternalBillService.queryByWaybillNo(entity.getWaybillNo());
		String isOneInMore = "";
		if(ldpExternalBillEntityList != null && ldpExternalBillEntityList.size() > 1){//是一票多件
			isOneInMore = FossConstants.YES;
		}
		// 如果当前运单不是第一次添加
		if (waybillSign != null && isOneInMore.equals(FossConstants.YES)) {
			// 运单签收结果里作废当前运单号
			waybillSignResultService.invalidWaybillSignResult(waybillSign.getId(), systemDate);
		}
		dto.setWaybillNo(entity.getWaybillNo());
		//本次签收件数
		dto.setCurrentPieces(entity.getSignGoodsQty());
		if(isOneInMore.equals(FossConstants.YES)){
			//已处理件数
			dto.setProcessedPieces(waybillSign.getSignGoodsQty());
		}else{
			//已处理件数
			dto.setProcessedPieces(entity.getSignGoodsQty());
		}
		//状态类型
		dto.setStateType(entity.getSituation());
		BeanUtils.copyProperties(entity,wayEntity);
		
		wayEntity.setCreator(entity.getOperator());//操作人
		wayEntity.setCreatorCode(entity.getOperatorCode());//操作人编码
		wayEntity.setDeliverymanName(null);
		//一票多件货物外发，可分批多次签收；
		if(isOneInMore.equals(FossConstants.YES)){
			if(entity.getSignGoodsQty() > EXTERNAL_SIGN_NUM){
				LOGGER.error("运单号："+entity.getWaybillNo()+"，按流水号签收，签收件数不能大于"+EXTERNAL_SIGN_NUM+"件！");//记录日志
				throw new SignException("运单号："+entity.getWaybillNo()+"，按流水号签收，签收件数不能大于"+EXTERNAL_SIGN_NUM+"件！");
			}
			if(waybillSign != null){
    			//签收情况
    			wayEntity.setSignSituation(SignConstants.NORMAL_SIGN.equals(waybillSign.getSignSituation()) ? entity.getSituation() : SignConstants.UNNORMAL_SIGN);
    			//签收件数
    			wayEntity.setSignGoodsQty(waybillSign.getSignGoodsQty() + entity.getSignGoodsQty());
    		}else{
    			//签收件数
    			wayEntity.setSignGoodsQty(entity.getSignGoodsQty());
    		}
			// 添加运单签收结果信息
			waybillSignResultService.addWaybillSignResult(wayEntity);
			//初始化外发流水签收实体
			SerialSignResultEntity serialSignResultEntity = new SerialSignResultEntity();
			
			serialSignResultEntity.setWaybillNo(entity.getWaybillNo());								//运单号
			serialSignResultEntity.setSerialNo(entity.getSerialNo());								//流水号
			serialSignResultEntity.setActive(FossConstants.YES);									//是否有效
			serialSignResultEntity.setExternalWaybillNo(entity.getExternalWaybillNo());				//外发单号
			serialSignResultEntity.setDeliverymanName(entity.getDeliverymanName());					//签收人
			serialSignResultEntity.setSignGoodsQty(entity.getSignGoodsQty());						//签收件数
			serialSignResultEntity.setSignSituation(entity.getSituation());							//流水号签收情况
			if(null != currentInfo){
				serialSignResultEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());		//签收部门编码
				serialSignResultEntity.setCreateOrgName(currentInfo.getCurrentDeptName());		//签收部门名称
				serialSignResultEntity.setCreatorCode(currentInfo.getEmpCode());				//操作人编码
				serialSignResultEntity.setCreator(currentInfo.getEmpName());					//操作人
			}else{
				serialSignResultEntity.setCreator(entity.getCreateUser());						//操作人
			}
			
			// 验证传的值
			this.validateSerialSignResultEntity(serialSignResultEntity);
			//同一运单号、流水号、是否有效，在外发流水签收表单中只能有一条数据
			SerialSignResultEntity serialEntity = serialSignResultService.queryByConditions(serialSignResultEntity);
			if(serialEntity == null){
				//生成外发流水签收信息
				serialSignResultService.addSerialSignResultInfo(serialSignResultEntity);
			}else{
				LOGGER.error("运单号："+entity.getWaybillNo()+"的"+entity.getSerialNo()+"流水号已签收。");//记录日志
				throw new SignException("运单号："+entity.getWaybillNo()+"的"+entity.getSerialNo()+"流水号已签收。");
			}
		}else{
			//签收情况
			wayEntity.setSignSituation(entity.getSituation());
			//签收件数
			wayEntity.setSignGoodsQty(entity.getSignGoodsQty());
			// 添加运单签收结果信息
			waybillSignResultService.addWaybillSignResult(wayEntity);
		}
		
		//DN201511090005 添加“签收人类型”字段  243921
		wayEntity.setDeliverymanType(entity.getDeliverymanType());				
		//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送
		sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
		//菜鸟轨迹 add by 231438
		//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
		sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
		waybillSignResultService.updateCrmOrder(waybill.getOrderNo(), currentInfo, wayEntity);
		//标识业务完结
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		//运单号
		waybillTransactionEntity.setWaybillNo(entity.getWaybillNo());
		waybillTransactionService.updateBusinessOver(waybillTransactionEntity);
			// 调中转的走货路径接口
		try {
			calculateTransportPathService.signIn(entity.getWaybillNo(), TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
		} catch (TfrBusinessException e) {//捕获异常
			LOGGER.error("调中转的走货路径接口有异常", e);//异常记录
			throw new SignException(e.getMessage(), e);
		}
		LOGGER.info("签收完毕，微信消息开发推送。参数类型:"+ReflectionToStringBuilder.toString(dto));
		ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(dto, WeixinConstants.WEIXIN_SIGN_TYPE);
		if(ResultDto.FAIL.equals(resultDto.getCode())){
			LOGGER.info("推送微信失败，错误详情："+resultDto.getMsg());
		}
		LOGGER.info("微信消息处理完毕,此处略去一万字");
	}
	
	/**
	 * 验证添加外发流水签收结果时的参数
	 * @author foss-sunyanfei
	 * @param serialSignResultEntity
	 */
	private void validateSerialSignResultEntity(SerialSignResultEntity serialSignResultEntity){
		if (null == serialSignResultEntity) {
			LOGGER.error("接口传入的数据为空");//记录日志
			throw new SignException("接口传入的数据为空");
		}else if(StringUtils.isBlank(serialSignResultEntity.getWaybillNo())) {//运单号为空
			LOGGER.error("运单号为空，不能添加外发流水签收结果");//记录日志
			throw new SignException("运单号为空，不能添加外发流水签收结果");//运单号为空
		}else if(StringUtils.isBlank(serialSignResultEntity.getSerialNo())) {//流水号为空
			LOGGER.error("流水号为空，不能添加外发流水签收结果");//记录日志
			throw new SignException("流水号为空，不能添加外发流水签收结果");//流水号为空
		}else if(StringUtils.isBlank(serialSignResultEntity.getSignSituation())) {//流水号签收情况为空
			LOGGER.error("流水号签收情况为空，不能添加外发流水签收结果");//记录日志
			throw new SignException("流水号签收情况为空，不能添加外发流水签收结果");//流水号签收情况为空
		}else if(serialSignResultEntity.getSignGoodsQty() == null || serialSignResultEntity.getSignGoodsQty().intValue() == 0) {//签收件数为空
			LOGGER.error("签收件数为空，不能添加外发流水签收结果");//记录日志
			throw new SignException("签收件数为空，不能添加外发流水签收结果");//签收件数为空
		}else if(StringUtils.isBlank(serialSignResultEntity.getActive())) {//是否有效为空
			LOGGER.error("是否有效为空，不能添加外发流水签收结果");//记录日志
			throw new SignException("是否有效为空，不能添加外发流水签收结果");//是否有效为空
		}
	}
	
	/**
	 * 初始化到达联
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.RookieService#initArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	public void initArriveSheet(ArriveSheetEntity arriveSheet,CurrentInfo currentInfo,WaybillEntity waybill) {
		//--判断是否存在到达联
		ArriveSheetEntity searchEntity = new ArriveSheetEntity();
		searchEntity.setActive(FossConstants.ACTIVE);//有效
		searchEntity.setDestroyed(FossConstants.NO);//未作废
		searchEntity.setWaybillNo(arriveSheet.getWaybillNo());
		List<ArriveSheetEntity> arriveList = arriveSheetManngerService.queryArriveSheetByWaybillNo(searchEntity);
		if(!CollectionUtils.isEmpty(arriveList)){
			for (ArriveSheetEntity arriveSheetEntit : arriveList) {
				if(arriveSheetEntit!= null && StringUtils.isNotBlank(arriveSheetEntit.getStatus()) && ArriveSheetConstants.STATUS_REFUSE.equals(arriveSheetEntit.getStatus())){
					continue;
				}
				arriveSheetEntit.setActive(FossConstants.NO);
				arriveSheetEntit.setModifyTime(new Date());
				arriveSheetManngerService.updateByPrimaryKeySelective(arriveSheetEntit);
			}
		}
		Date date = new Date();
		arriveSheet.setStatus(ArriveSheetConstants.STATUS_SIGN);//到达联的状态为“已签收”
		arriveSheet.setSignStatus(SignConstants.SIGN_STATUS_ALL);
		arriveSheet.setIsPdaSign(FossConstants.NO); //是否PDA签收=“否”
		arriveSheet.setCreateUserName(currentInfo.getEmpName());//创建人名称
		arriveSheet.setCreateUserCode(currentInfo.getEmpCode());//创建人编码
		arriveSheet.setCreateOrgName(currentInfo.getCurrentDeptName());//创建部门名称
		arriveSheet.setCreateOrgCode(currentInfo.getCurrentDeptCode());//创建部门编码
		arriveSheet.setOperateTime(date);//操作日期 
		arriveSheet.setOperator(currentInfo.getEmpName());
		arriveSheet.setCreateTime(date);//创建日期
		arriveSheet.setModifyTime(null);
		arriveSheet.setIsPrinted(FossConstants.NO);
		arriveSheet.setActive(FossConstants.YES);
		arriveSheet.setDestroyed(FossConstants.NO);
		//到达联件数=签收件数
		arriveSheet.setArriveSheetGoodsQty(arriveSheet.getSignGoodsQty());
		arriveSheet.setDeliverymanName(waybill.getReceiveCustomerContact());//签收人取收货客户联系人，已经同需求确认
		//生成到达联编号
		arriveSheet.setArrivesheetNo(arriveSheetManngerService.generateArriveSheetId(arriveSheet.getWaybillNo()));
		arriveSheetManngerService.addArriveSheetData(arriveSheet);
		LOGGER.info("--生成到达联完成"+ ReflectionToStringBuilder.toString(arriveSheet));//记录日志
	}
	/**
	 * 签收运单确认收入
	 * @param dto
	 * @param currentInfo
	 */
	private void confirmTaking(LineSignDto dto,CurrentInfo currentInfo){
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("waybillNo", dto.getWaybillNo());
		params.put("active", FossConstants.YES);
		//查询是否子母件，以及获取子母件运单列表
		TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
		if(null != oneDto && FossConstants.YES.equals(oneDto.getIsTwoInOne())){
		//判断当前运单是否子母件
			List<WaybillSignResultEntity> list = new ArrayList<WaybillSignResultEntity>();
			for(String signWaybillNo:oneDto.getWaybillNoList()){
				// 传入参数(运单号,当前运单号生效)
				WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(signWaybillNo, FossConstants.ACTIVE);
				// 根据运单号 查询运单签收结果：子母件都只有一件，签收结果表有且只能有一条签收数据(只能是全部签收)
				WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
				if( null != waybillSign){
					list.add(waybillSign);
				}
			}
			//当前签收件是母件
			if(oneDto.getMainWaybillNo().equals(dto.getWaybillNo())){
				if(oneDto.getWaybillNoList().size() - list.size()  > 1){ 
					//母件不是最后一件签收，不做确认收入
				}else{ //母件签收是最后一件
					//母件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());	
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}			
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				}
			}else{//是子件
				//当前子件不是子母件签收的最后一件
				if(oneDto.getWaybillNoList().size() - list.size()  > 1){
					//子件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());	
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}		
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				}else{//当前子件是子母件签收的最后一件
					//子件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());	
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}		
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
					LineSignDto newDto = new LineSignDto();
					BeanUtils.copyProperties(dto, newDto);
					newDto.setWaybillNo(oneDto.getMainWaybillNo());//设置运单号为母件单号
					//母件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode2 = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(newDto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode2 = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+ newDto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
						lineSignService.confirmTaking(newDto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
						FOSSSignOrRevSignRequestDto reqDto1 = new FOSSSignOrRevSignRequestDto();
						reqDto1.setCurrentInfo(currentInfo);
						reqDto1.setLineSignDto(newDto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto1);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());	
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}		
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				}
			}
		}else{ //不是子母件(正常运单)
			//CUBC签收   灰度改造    353654 ---------------------------start
			String vestSystemCode2 = null;
			try {
              	List<String> arrayList = new ArrayList<String>();
              	arrayList.add(dto.getWaybillNo());
              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
              			SettlementConstants.TYPE_FOSS);
              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
              	List<VestBatchResult> list1 = response.getVestBatchResult();
              	vestSystemCode2 = list1.get(0).getVestSystemCode();	
	  			} catch (Exception e) {
	  				LOGGER.info("灰度分流失败,"+"运单号："+ dto.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
	  			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
				lineSignService.confirmTaking(dto, currentInfo);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
				FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
				reqDto.setCurrentInfo(currentInfo);
				reqDto.setLineSignDto(dto);
				CUBCSignOrRevSignResultDto resultDto = cUBCSignService.sendSignReqToCUBC(reqDto);
				if(StringUtils.equals(resultDto.getResultMark(), FossConstants.NO)){
					if(StringUtils.isNotBlank(resultDto.getMeg())){
						LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto.getMeg());
						throw new CUBCSignException(resultDto.getMeg());	
					}else{
						throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
					}
				}		
			}
			//CUBC签收   灰度改造    353654 ---------------------------end
		}
	}
	/**
	 * Sets the 签收变更结果接口.
	 *
	 * @param signChangeService the new 签收变更结果接口
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	public void setRepaymentDao(IRepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	public void setWaybillTransactionService(
			IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}
	/**
	 * Sets the 运单service.
	 *
	 * @param waybillManagerService the new 运单service
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
/*	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}*/
	
}