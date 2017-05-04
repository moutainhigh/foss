package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
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
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRookieService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.BtachExecPramsDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
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
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 菜鸟服务service接口
 * @author foss-yuting
 * @date 2015-2-5 下午16:08:00
 * @since
 * @version
 */
public class RookieService implements IRookieService {
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.RookieService";
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}
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
	private static final Logger LOGGER = LoggerFactory.getLogger(RookieService.class);
	/**
	 *  运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * 推送微信服务类
	 */
	private IWeixinSendService weixinSendService;
	
	/**
	 * 货签服务类
	 */
	private ILabeledGoodService labeledGoodService;
	
	/**
	 * 运单完结状态操作Service接口
	 */
	private IWaybillTransactionService waybillTransactionService;
	
	/**
	 *  签收明细service
	 */
	private ISignDetailService signDetailService;
	
	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 计算&调整走货路径类
	 * 接口
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 快递代理服务接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;
	
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	
	/**
	 *  运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	
	/**
	 * 结清货款Service
	 */
	private IRepaymentService repaymentService;
	
	/**
	 *  到达联service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	
	/**
	 *  更改单service
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 *  结算签收
	 *  Service接口
	 */
	private ILineSignService lineSignService;

	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	
	/**
	 * 运单
	 * service
	 * 	接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 运单service接口注入
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 运单快递相关服务类接口
	 */
	private IWaybillExpressService waybillExpressService;
	
	/**
	 * 运单快递相关服务类接口 注入
	 * @param waybillExpressService
	 */
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**
	 * 处理部分签收和未签收的逻辑
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.RookieService#exceuteSignStatusPart(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public void exceuteSignStatusPart(WaybillSignResultEntity resultWEntity,WaybillEntity waybill, 
			WaybillSignResultEntity newresultWEntity,PdaDeliverSignDto pdaDeliverSignDto,CurrentInfo currentInfo,FinancialDto financialDto,boolean isDeliver) {
		Date systemDate=new Date();	
		ArriveSheetEntity entity=new ArriveSheetEntity();
		//1.判断是不是快递代理
		OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
		if (companyDto != null) {
			validaWaybillSignResult(resultWEntity, newresultWEntity, systemDate);
			//--调用封装好的快递代理接口
			try {
				addExpressAgentSignResult(newresultWEntity, currentInfo, waybill);
			} catch (BusinessException e) {//捕获异常
				LOGGER.error(e.getMessage(), e);//记录日志
				throw new SignException(e.getErrorCode(),e);//抛出异常
			}
		}else{
			//结清（pda派送，foss派送，自提）
			this.expReturnPayment(waybill,newresultWEntity,pdaDeliverSignDto,currentInfo,financialDto,isDeliver);

			//2.已结清
			//--判断是否存在未签收的到达联
			List<ArriveSheetEntity> entityList = arriveSheetManngerService.queryArriveSheetListNoSign(waybill.getWaybillNo());
			int signGty =0;//签收件数
			if(null == resultWEntity){
				signGty =0;
			}else{
				signGty = resultWEntity.getSignGoodsQty()==null ? 0 :resultWEntity.getSignGoodsQty();
			}
			//开单件数-签收件数(签收结果表的);全部签收 num=0，部分签收num>0
			int num = waybill.getGoodsQtyTotal()- signGty;
			if(num > 0){//有部分签收和未签收；--重新生成到达联 
				validaDate(systemDate, entityList);
				//1.有未签收的到达联，签收结果部分签收;2.有未签收到达联，无签收结果；3无签收结果。初始化一条到达联数据
				entity.setSituation(newresultWEntity.getSignSituation());//签收情况
				entity.setDeliverymanName(newresultWEntity.getDeliverymanName());//签收人
				//DN201511090005  添加“签收人类型”字段
				entity.setDeliverymanType(newresultWEntity.getDeliverymanType());
				entity.setIsPdaSign(newresultWEntity.getIsPdaSign());
				entity.setWaybillNo(waybill.getWaybillNo());//运单号
				entity.setActive(FossConstants.ACTIVE);//有效
				entity.setDestroyed(FossConstants.NO);//未作废
				entity.setSignGoodsQty(num); //签收件数：
				entity.setReceiveMethod(newresultWEntity.getReceiveMethod()); //
				entity.setArriveSheetGoodsQty(num);//到达联件数
				entity.setSignTime(newresultWEntity.getSignTime()); //签收时间
				initArriveSheet(entity, currentInfo,waybill); //初始化到达联的其他信息
			 	signOutStock(entity, waybill, currentInfo);
				
		 	}
		}
	}

	/**
	 * 原运单签收出库逻辑
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.RookieService#signOutStock(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public void signOutStock(ArriveSheetEntity entity, WaybillEntity waybill,CurrentInfo currentInfo) {
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
		entity.setWaybillNo(waybill.getWaybillNo());
		//调用中转接口获取流水号
		List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(waybill.getWaybillNo());
		List<String> serials = null;
		//如果流水号集合不为空
		if(!CollectionUtils.isEmpty(labeledGoodEntityList)){
			serials =new ArrayList<String>();
			for (LabeledGoodEntity labeledGoodEntity : labeledGoodEntityList) {
				serials.add(labeledGoodEntity.getSerialNo());
				SignDetailEntity signDetail = new SignDetailEntity();
				signDetail.setSerialNo(labeledGoodEntity.getSerialNo());//流水号
				// 到达联编号
				signDetail.setArrivesheetNo(entity.getArrivesheetNo());
				// 签收情况
				signDetail.setSituation(entity.getSituation());
				signDetailService.addSignDetailInfo(signDetail);//添加签收明细。
				InOutStockEntity inOutStock = new InOutStockEntity();
				// 运单号
				inOutStock.setWaybillNO(waybill.getWaybillNo());
				// 流水号
				inOutStock.setSerialNO(labeledGoodEntity.getSerialNo());
				// 部门编码
				inOutStock.setOrgCode(currentInfo.getCurrentDeptCode());
				// 操作人姓名
				inOutStock.setOperatorName(currentInfo.getEmpName());
				// 出入库类型 签收出库
				inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
				inOutStock.setOperatorCode(currentInfo.getEmpCode());
				// 进行出库操作
				try {
					stockService.outStockDelivery(inOutStock);
				} catch (StockException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new DeliverHandlerException(e.getErrorCode(),e);//抛出异常
				}
			}
		}
		//如果签收件数小于运单开单件数
		if(entity.getSignGoodsQty() != null &&entity.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
			todoActionService.updateLabeledStatusByWaybillNum(entity.getWaybillNo(),serials);
		}else {
			todoActionService.updateLabeledStatusByWaybillNum(entity.getWaybillNo(),null);
		}
		try {
			// 传入参数实体(运单号,当前运单号生效)
			WaybillSignResultEntity waybEntity = new WaybillSignResultEntity(entity.getWaybillNo(), FossConstants.ACTIVE);
			// 根据运单号 查询运单签收结果
			WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybEntity);
			WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,new Date(),currentInfo);
			wayEntity.setIsPdaSign(entity.getIsPdaSign());
			wayEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
			validaArriveSheetEntity(currentInfo, waybill, entity, waybillSign);
			// 添加运单签收结果信息
			waybillSignResultService.addWaybillSignResult(wayEntity);
			//DN201511090005 添加“签收人类型”字段  243921
			wayEntity.setDeliverymanType(entity.getDeliverymanType());
			//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送 
			sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
			if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
				//如果是东方购物的客户编码，则原单签收时不需要发送签收状态至CRM DN201602220004 --by 243921 
				if(!SignConstants.CRM_ORDER_CUSTOMER_CODE.equals(waybill.getDeliveryCustomerCode())){
					waybillSignResultService.updateCrmOrder(waybill.getOrderNo(), currentInfo, wayEntity);
				}
			}
		} catch (SignException e) {//捕获异常
			throw new DeliverHandlerException(e.getErrorCode(), e);//抛出异常
		}
		//应用监控签收调用
		waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
		WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
		BeanUtils.copyProperties(entity,waybillSigndto);//复制代码
		//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
		waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
		//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
		if(isSendInvoiceInfo){
			waybillSignResultService.sendInvoiceInfo(waybill,actual);
		}
	}

	/**
	 * 返货子母件确认收入
	 * @author foss-sunyanfei
	 * @date  2015-9-11 上午 11:50:12
	 * @param dto
	 * @param currentInfo
	 * @param signDto
	 */
	private void confirmTakingCheck(ArriveSheetEntity arriveSheet,WaybillEntity waybill,CurrentInfo currentInfo){
		try {
			//判断当前运单是否子母件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("waybillNo", arriveSheet.getWaybillNo());
			params.put("active", FossConstants.YES);
			TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);

			if(oneDto != null && oneDto.getIsTwoInOne().equals(FossConstants.YES)){
				List<WaybillSignResultEntity> list = new ArrayList<WaybillSignResultEntity>();
				for(String waybillNo:oneDto.getWaybillNoList()){
					// 传入参数(运单号,当前运单号生效)
					WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(waybillNo, FossConstants.ACTIVE);
					// 根据运单号 查询运单签收结果
					WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
					if( null != waybillSign){
						list.add(waybillSign);
					}
				}
				
				//判断子母件是母件
				if(oneDto.getMainWaybillNo().equals(arriveSheet.getWaybillNo())){
					if(oneDto.getWaybillNoList().size() - list.size()  > 1){
						//母件不是最后一件签收，不做确认收入
					}else{ 
						//母件签收是最后一件，确认收入
						this.confirmTaking(arriveSheet,waybill,currentInfo);
					}
				}else{//子件
					//当前子件不是子母件签收的最后一件
					if(oneDto.getWaybillNoList().size() - list.size()  > 1){
						//子件确认收入
						this.confirmTaking(arriveSheet,waybill,currentInfo);
					}else{//当前子件是子母件签收的最后一件
						//子件确认收入
						this.confirmTaking(arriveSheet,waybill,currentInfo);
						ArriveSheetEntity newArr = new ArriveSheetEntity();
						BeanUtils.copyProperties(arriveSheet, newArr); //复制信息到新的到达联信息 母件确认收入视同
						newArr.setWaybillNo(oneDto.getMainWaybillNo()); //设置母件单号
						//母件确认收入
						this.confirmTaking(newArr,waybill,currentInfo);
					}
				}
			}else{
				//不是子母件，正常调用
				this.confirmTaking(arriveSheet,waybill,currentInfo);
			}
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("子母件调用财务接口抛出异常",se);//记录日志
			throw new PdaProcessException(se.getErrorCode(),se);//抛出异常
		}
		LOGGER.info("子母件调用结算签收接口结束");//记录日志
	}
	
	/**
	 * 初始化到达联
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.RookieService#initArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public void initArriveSheet(ArriveSheetEntity arriveSheet,CurrentInfo currentInfo,WaybillEntity waybill) {
		Date date = new Date();
		LOGGER.info("--生成到达联开始"+ ReflectionToStringBuilder.toString(arriveSheet));//记录日志
		//手工生成到达联由于是违禁品，手工生成到达联，到达联件数=签收出库件数，到达联的状态为“已签收”，到达联active=’Y’，destroyed=’N’,是否PDA签收=“否”
		arriveSheet.setStatus(ArriveSheetConstants.STATUS_SIGN);//到达联的状态为“已签收”
		arriveSheet.setIsPdaSign(arriveSheet.getIsPdaSign()); //是否PDA签收=“否”
		arriveSheet.setIsPrinted("N");  //是否已打印到达联；默认值未N
		arriveSheet.setCreateUserName(currentInfo.getEmpName());//创建人名称
		arriveSheet.setCreateUserCode(currentInfo.getEmpCode());//创建人编码
		arriveSheet.setCreateOrgName(currentInfo.getCurrentDeptName());//创建部门名称
		arriveSheet.setCreateOrgCode(currentInfo.getCurrentDeptCode());//创建部门编码
		arriveSheet.setOperateOrgCode(currentInfo.getCurrentDeptCode());//操作部门编码
		arriveSheet.setOperateOrgName(currentInfo.getCurrentDeptName());////操作部门名称
		arriveSheet.setOperator(currentInfo.getEmpName());//操作人名称
		arriveSheet.setOperatorCode(currentInfo.getEmpCode());//操作人编码
		arriveSheet.setOperateTime(date);//操作日期 
		arriveSheet.setCreateTime(date);//创建日期
		//到达联件数=签收件数
		//arriveSheet.setArriveSheetGoodsQty(contrabandInfoDto.getSignGoodsQty());
		//签收件数=签收件数
		//arriveSheet.setSignGoodsQty(contrabandInfoDto.getSignGoodsQty());
		//生成到达联编号
		arriveSheet.setArrivesheetNo(arriveSheetManngerService.generateArriveSheetId(waybill.getWaybillNo()));
		arriveSheetManngerService.addArriveSheetData(arriveSheet);
		LOGGER.info("--生成到达联完成"+ ReflectionToStringBuilder.toString(arriveSheet));//记录日志
	}

	 /**
	 * 菜鸟快递代理签收
	 * @date 2015-2-5 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.RookieService#addExpressAgentSignResult(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public void addExpressAgentSignResult(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, WaybillEntity waybill) {
		// 记录日志
		LOGGER.info("提交签收信息开始");
		//校验未受理的更改单
		if(waybillRfcService.isExsitsWayBillRfc(waybill.getWaybillNo())){
			// 抛出业务异常
			throw new AirAgencySignException(RepaymentException.EXIST_WAYBILLRFC);
		}
		WeixinSendDto sendDto = new WeixinSendDto();
		Integer result = 0;//默认值
		Date modifyTime = new Date();
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
		try {
			// 调用结算出库接口
			if(SignConstants.SIGN_STATUS_PARTIAL.equals(wayEntity.getSignStatus())){//如果签收状态为部分签收
				//不操作
			}else{
				LineSignDto dto=new LineSignDto();
				if(null != currentInfo){
					// 操作人名称
					dto.setOperatorName(currentInfo.getEmpName());
					// 操作人编码
					dto.setOperatorCode(currentInfo.getEmpCode());
					// 签收部门名称
					dto.setSignOrgName(currentInfo.getCurrentDeptName());
					// 签收部门编码
					dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
				}else{
					// 操作人名称
					dto.setOperatorCode(wayEntity.getCreateUser());
				}
				// 签收时间
				dto.setSignDate(modifyTime);
				dto.setSignType(SettlementConstants.LAND_STOWAGE_SIGN);
				// 运输性质
				dto.setProductType(waybill.getProductCode());
				// 是否整车
				dto.setIsWholeVehicle(waybill.getIsWholeVehicle());
				// 不是PDA签收
				dto.setIsPdaSign(wayEntity.getIsPdaSign());
				
				//子母件确认收入：判断当前运单是否是子母件
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("waybillNo", waybill.getWaybillNo());
				params.put("active", FossConstants.YES);
				TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
				//判断当前运单是否子母件
				if(oneDto !=null && oneDto.getIsTwoInOne().equals(FossConstants.YES)){
					//判断母件单号是否为空
					if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
						throw new PdaProcessException("子母件获取母件单号失败！");
					}
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(oneDto.getMainWaybillNo());
					if(waybillEntity==null){
						throw new PdaProcessException("母件单号"+oneDto.getMainWaybillNo()+"不存在");
					}
					
					List<WaybillSignResultEntity> list = new ArrayList<WaybillSignResultEntity>();
					for(String waybillNo:oneDto.getWaybillNoList()){
						// 传入参数(运单号,当前运单号生效)
						WaybillSignResultEntity resultEntity = new WaybillSignResultEntity(waybillNo, FossConstants.ACTIVE);
						// 根据运单号 查询运单签收结果
						WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
						if( null != waybillSign){
							list.add(waybillSign);
						}
					}
					//判断当前签收运单是子母件的母件
					if(oneDto.getMainWaybillNo().equals(waybill.getWaybillNo())){
						if(oneDto.getWaybillNoList().size() - list.size()  > 1){
							//母件不是最后一件签收，不做确认收入
						}else{ 
							//母件签收是最后一件，确认收入
							dto.setWaybillNo(oneDto.getMainWaybillNo());
							//CUBC签收   灰度改造    353654 ---------------------------start
							String vestSystemCode = null;
							try {
				              	List<String> arrayList = new ArrayList<String>();
				              	arrayList.add(dto.getWaybillNo());
				              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addExpressAgentSignResult",
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
					}else{//子件
						//当前签收件不是子母件签收的最后一件
						if(oneDto.getWaybillNoList().size() - list.size()  > 1){
							//子件确认收入
							dto.setWaybillNo(waybill.getWaybillNo());
							//CUBC签收   灰度改造    353654 ---------------------------start
							String vestSystemCode = null;
							try {
				              	List<String> arrayList = new ArrayList<String>();
				              	arrayList.add(dto.getWaybillNo());
				              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addExpressAgentSignResult",
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
							dto.setWaybillNo(waybill.getWaybillNo());
							//CUBC签收   灰度改造    353654 ---------------------------start
							String vestSystemCode = null;
							try {
				              	List<String> arrayList = new ArrayList<String>();
				              	arrayList.add(dto.getWaybillNo());
				              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addExpressAgentSignResult",
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
							//母件确认收入
							LineSignDto newDto = new LineSignDto();
							BeanUtils.copyProperties(dto, newDto);//复制信息给新的newDto
							newDto.setWaybillNo(oneDto.getMainWaybillNo()); //设置单号为母件单号
							//CUBC签收   灰度改造    353654 ---------------------------start
							String vestSystemCode1 = null;
							try {
				              	List<String> arrayList = new ArrayList<String>();
				              	arrayList.add(newDto.getWaybillNo());
				              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addExpressAgentSignResult",
				              			SettlementConstants.TYPE_FOSS);
				              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
				              	List<VestBatchResult> list1 = response.getVestBatchResult();
				              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
					  			} catch (Exception e) {
					  				LOGGER.info("灰度分流失败,"+"运单号："+newDto.getWaybillNo());
					  				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
					  			}
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
								lineSignService.confirmTaking(newDto, currentInfo);
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
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
				}else{ //不是子母件
					dto.setWaybillNo(waybill.getWaybillNo());
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode1 = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addExpressAgentSignResult",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
			  				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
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
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("调用结算出库接口抛异常", se);//记录日志
			// 处理异常
			throw new AirAgencySignException(se.getErrorCode(), se);//抛出异常
		}
		// 代理公司编码
		wayEntity.setAgentCode(StringUtil.defaultIfNull(wayEntity.getAgentCode()));		
		wayEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());//签收部门编码
		wayEntity.setCreateOrgName(currentInfo.getCurrentDeptName());//签收部门名称
		wayEntity.setCreator(currentInfo.getEmpName());//操作人
		wayEntity.setCreatorCode(currentInfo.getEmpCode());//操作人编码
		// 生效时间为当前时间
		wayEntity.setCreateTime(modifyTime);
		wayEntity.setActive(FossConstants.YES);
		// 时效时间为空，添加时采用默认值
		wayEntity.setModifyTime(null);
		// 运单号
		wayEntity.setWaybillNo(waybill.getWaybillNo());
		//根据签收情况设置签收状态
    	// 签收状态--部分签收
		//设置为正常签收
		wayEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
		//如果是东方购物的客户，则不推送原单签收状态至CRM DN201602220004 243921
		if(!SignConstants.CRM_ORDER_CUSTOMER_CODE.equals(waybill.getDeliveryCustomerCode())){
			waybillSignResultService.updateCrmOrder(waybill.getOrderNo(),currentInfo,wayEntity);//更新订单状态
		}
		//标识业务完结
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		//运单号
		waybillTransactionEntity.setWaybillNo(waybill.getWaybillNo());
		waybillTransactionService.updateBusinessOver(waybillTransactionEntity);//修改业务完结
		sendDto.setStateType(wayEntity.getSignSituation());
    	sendDto.setWaybillNo(wayEntity.getWaybillNo());
    	//确认过，正常签收和部分签收只能被签收一次
		sendDto.setCurrentPieces(wayEntity.getSignGoodsQty());
		sendDto.setProcessedPieces(wayEntity.getSignGoodsQty());
		//签收时间
		sendDto.setCreateTime(wayEntity.getSignTime()==null ? modifyTime:wayEntity.getSignTime());
		sendDto.setWaybillNo(wayEntity.getWaybillNo());
		//签收人
		sendDto.setSignName(wayEntity.getDeliverymanName());
		result = waybillSignResultService.addWaybillSignResult(wayEntity);
		if(result >= 0){//如果添加成功
			//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送
			sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
			ActualFreightEntity record=new ActualFreightEntity();
			record.setArriveNotoutGoodsQty(0);
			actualFreightService.updateByWaybillNo(record, waybill.getWaybillNo());
			todoActionService.updateLabeledStatusByWaybillNum(waybill.getWaybillNo(),null);
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
				// 调中转的走货路径接口
				try {
					calculateTransportPathService.signIn(waybill.getWaybillNo(), TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
				} catch (TfrBusinessException e) {//捕获异常
					LOGGER.error("--调中转的走货路径接口有异常", e);//记录日志
					throw new AirAgencySignException(e.getMessage(), e);//抛出异常
				}
				LOGGER.info("--该 运单不是整车运单，调用中转走货路径接口完成,运单号"+waybill.getWaybillNo());//记录日志
		}else {
			LOGGER.error("添加运单签收结果失败，签收操作不成功");//记录日志
			//签收失败，抛出异常信息
			throw new AirAgencySignException(AirAgencySignException.WAYBILL_SIGN_RESULT_ADD_FAILED);//抛出异常
		}	
		LOGGER.info("偏线开始处理微信消息,单号为："+sendDto.getWaybillNo());
		ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(sendDto, WeixinConstants.WEIXIN_SIGN_TYPE);
		if(ResultDto.FAIL.equals(resultDto.getCode())){
			LOGGER.info("偏线微信消息推送失败。错误详情："+resultDto.getMsg());
		}
		LOGGER.info("微信消息处理完毕!");
		//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
		if(isSendInvoiceInfo){
			waybillSignResultService.sendInvoiceInfo(waybill,actual);
		}
	}
	private void confirmTaking(ArriveSheetEntity arriveSheet,WaybillEntity waybill,CurrentInfo currentInfo){
		try {
			// 系统调用财务接口完成财务出库 首先判断是否第一次签收。
			//如果是就调用，如果不是，就不调用。 根据运单号判断到达联表里是否存在签收。
			LineSignDto dto = new LineSignDto();
			// 操作人编码
			dto.setOperatorName(currentInfo.getEmpName());
			// 操作人名称
			dto.setOperatorCode(currentInfo.getEmpCode());
			// 签收部门名称
			dto.setSignOrgName(currentInfo.getCurrentDeptName());
			// 签收部门编码
			dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
			// 运单号
			dto.setWaybillNo(arriveSheet.getWaybillNo());
			// 签收时间
			dto.setSignDate(arriveSheet.getOperateTime());
			dto.setSignType(SettlementConstants.LINE_SIGN);
			// 是否整车运单
			dto.setIsWholeVehicle(waybill.getIsWholeVehicle());
			// 是否PDA签收
			dto.setIsPdaSign(arriveSheet.getIsPdaSign());
			// 运输性质
			dto.setProductType(waybill.getProductCode());
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
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("--调用财务接口出现异常");//记录日志
			// 捕捉结算抛出的异常
			throw new BusinessException(se.getErrorCode(), se);
		}
	}
	
	/**
	 * 返货关联签子母件，手动进行结清货款
	 * @author foss-sunyanfei
	 * @date 2015-9-14 上午 11:11:09
	 * @param dto
	 * @param signDto
	 * @param currentInfo
	 */
	private void expReturnPayment(WaybillEntity waybill,WaybillSignResultEntity newresultWEntity,PdaDeliverSignDto pdaDeliverSignDto,CurrentInfo currentInfo,FinancialDto financialDto,boolean isDeliver){
		//非快递代理
		//--是否已经结清
		if (!repaymentService.isPayment(waybill.getWaybillNo())) {
			if(isDeliver){  //是否派送
				//判断是否是pda派送签收
				if(StringUtils.isNotEmpty(newresultWEntity.getIsPdaSign())&&newresultWEntity.getIsPdaSign().equals(FossConstants.YES)){
					RepaymentEntity repayment = new RepaymentEntity();
					// 运单号
					repayment.setWaybillNo(waybill.getWaybillNo());
					//PDA结清     243921
					repayment.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE);
					// 付款方式
					repayment.setPaymentType(pdaDeliverSignDto.getPaymentType());
					BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybill.getWaybillNo());
					//财务单据查询，灰度改造   353654 ---------------------------start 
					List<BillReceivableEntity> billReceivableEntities = null;
					String vestSystemCode = null;
		            try {
		            	List<String> arrayList = new ArrayList<String>();
                    	arrayList.add(waybill.getWaybillNo());
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".expReturnPayment",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		            	List<VestBatchResult> list = response.getVestBatchResult();
		            	vestSystemCode = list.get(0).getVestSystemCode();		
					} catch (Exception e) {
						LOGGER.info("灰度分流失败,"+"运单号："+waybill.getWaybillNo());
						throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
					}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						LOGGER.info("FOSS查询财务单据开始!运单号："+ waybill.getWaybillNo());
						billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
						LOGGER.info("FOSS查询财务单据完成!运单号："+ waybill.getWaybillNo());
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						try {
							billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybill.getWaybillNo());			
						} catch (Exception e) {
							LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
							throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
						}
					}
					//财务单据查询，灰度改造   353654 ---------------------------end
					if(CollectionUtils.isEmpty(billReceivableEntities)){
						// 实付运费
						repayment.setActualFreight(BigDecimal.ZERO);
						repayment.setCodAmount(BigDecimal.ZERO);// 代收货款
						repayment.setActualFreight(repayment.getActualFreight() == null ? BigDecimal.ZERO:repayment.getActualFreight());
						repayment.setCodAmount(repayment.getCodAmount() == null? BigDecimal.ZERO :repayment.getCodAmount());
						//付款时间
						repayment.setPaymentTime(new Date());
						//串号
						repayment.setPdaSerial(pdaDeliverSignDto.getPdaSerial());
						try {
							// 调结清货款进行结清
							repaymentService.deliverOperate(repayment, currentInfo);
						} catch (RepaymentException e) {//捕获异常
							LOGGER.error("--调用结清货款抛出异常:"+e.getErrorCode());//记录异常信息
							throw new PdaProcessException(e.getErrorCode(),e);//抛出捕获的异常
						}
						LOGGER.info("调用结清货款完成");//记录日志
					}else{
						for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
							//合并金额
							BigDecimal totalAmount = (pdaDeliverSignDto.getTotalAmount() == null ? BigDecimal.ZERO : pdaDeliverSignDto.getTotalAmount());
							// 到达应收单
							if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
								// 实付运费
								repayment.setActualFreight(billReceivableEntity.getUnverifyAmount());
								// 代收货款
								repayment.setCodAmount(BigDecimal.ZERO);
								//银行交易流水号--到付流水号
								repayment.setBankTradeSerail(pdaDeliverSignDto.getBankTradeSerail());
								//判断是否合并，若为合并，则全部使用合并付款方式
								if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
									//合并金额--付款方式
									repayment.setPaymentType(pdaDeliverSignDto.getTotalPaymentType());
								}else{
									//到付金额--付款方式
									repayment.setPaymentType(pdaDeliverSignDto.getPaymentType());
								}
							}
							// 代收货款应收单
							else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
								// 实付运费
								repayment.setActualFreight(BigDecimal.ZERO);
								// 代收货款
								repayment.setCodAmount(billReceivableEntity.getUnverifyAmount());
								//银行交易流水号--代收货款流水号
								repayment.setBankTradeSerail(pdaDeliverSignDto.getCodBankTradeSerail());
								validaPdaDeliverSignDto(pdaDeliverSignDto,
										repayment, totalAmount);
							}else{
								continue;
							}

							//若付款方式为现金，则流水号必须为空
							if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(repayment.getPaymentType())){
								//银行交易流水号--代收货款流水号
								repayment.setBankTradeSerail("");
								//串号
								repayment.setPdaSerial("");
							}else{
								//串号
								repayment.setPdaSerial(pdaDeliverSignDto.getPdaSerial());
							}

							repayment.setActualFreight(repayment.getActualFreight() == null ? BigDecimal.ZERO:repayment.getActualFreight());
							repayment.setCodAmount(repayment.getCodAmount() == null? BigDecimal.ZERO :repayment.getCodAmount());
							//付款时间
							repayment.setPaymentTime(new Date());

							try {
								// 调结清货款进行结清
								repaymentService.deliverOperate(repayment, currentInfo);
							} catch (RepaymentException e) {//捕获异常
								LOGGER.error("--调用结清货款抛出异常:"+e.getErrorCode());//记录异常信息
								throw new PdaProcessException(e.getErrorCode(),e);//抛出捕获的异常
							}
							LOGGER.info("调用结清货款完成");//记录日志
						}
					}
				}else{
					//foss快递派送处理
					FinancialDto rstFinancialDto = repaymentService.queryFinanceSign(waybill.getWaybillNo());
					validaWaybillSignResultEntity(waybill, newresultWEntity,
							currentInfo, financialDto, rstFinancialDto);
				}
			  }else{  //自提和快递代理 如原单未结清，直接抛出异常
				throw new SignException("当前原单号未结清!");//抛出异常
				 }
			}
		}

	private void validaPdaDeliverSignDto(PdaDeliverSignDto pdaDeliverSignDto,
			RepaymentEntity repayment, BigDecimal totalAmount) {
		if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
			//合并金额--付款方式
			repayment.setPaymentType(pdaDeliverSignDto.getTotalPaymentType());
		}else{
			//代收货款--付款方式
			repayment.setPaymentType(pdaDeliverSignDto.getCodPaymentType());
		}
	}

	private void validaWaybillSignResultEntity(WaybillEntity waybill,
			WaybillSignResultEntity newresultWEntity, CurrentInfo currentInfo,
			FinancialDto financialDto, FinancialDto rstFinancialDto) {
		if(rstFinancialDto!=null){
			RepaymentEntity repayment = new RepaymentEntity();
			// 运单号
			repayment.setWaybillNo(waybill.getWaybillNo());
			//付款时间
			repayment.setPaymentTime(new Date());
			// 提货人
			repayment.setDeliverymanName(newresultWEntity.getDeliverymanName());
			// 实付运费
			repayment.setActualFreight(rstFinancialDto.getReceiveablePayAmoout()== null ?BigDecimal.ZERO : rstFinancialDto.getReceiveablePayAmoout());
			// 代收货款
			repayment.setCodAmount(new BigDecimal(0));
			// 付款方式
			repayment.setPaymentType(financialDto.getPaymentType());
			repayment.setConsigneeName(financialDto.getConsigneeName());// 客户名称
			repayment.setConsigneeCode(financialDto.getConsigneeCode());//客户编码
			repayment.setClaimNo(financialDto.getClaimNo());//款项认领编号
			// 调结清货款进行结清
			try {
				repaymentService.deliverOperate(repayment, currentInfo);
				LOGGER.info("--调结清货款进行结清成功");//记录日志
			} catch (RepaymentException se) {//捕获异常
				LOGGER.error("--调用货款结清接口异常",se);//记录日志
				// 捕捉结清货款抛出的异常
				throw new DeliverHandlerException(se.getErrorCode(), se);
			}
		}
	}
	
	/**
	 * Sets the 运单状态服务接口.
	 *
	 * @param actualFreightService the new 运单状态服务接口
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * Sets the 快递代理服务接口.
	 *
	 * @param ldpAgencyDeptService the new 快递代理服务接口
	 */
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}
	
	/**
	 * Sets the 运单签收结果service.
	 *
	 * @param waybillSignResultService the new 运单签收结果service
	 */
	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
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
	 * Sets the 到达联service.
	 *
	 * @param arriveSheetManngerService the new 到达联service
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
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
	 * Sets the 中转出库接口.
	 *
	 * @param stockService the new 中转出库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	/**
	 * Sets the 代办事项服务类接口.
	 *
	 * @param todoActionService the new 代办事项服务类接口
	 */
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
	/**
	 * Sets the 更改单服务类接口.
	 *
	 * @param waybillRfcService the new 更改单服务类接口
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	
	/**
	 * Sets the 结算签收Service.
	 *
	 * @param lineSignService 
	 * 		the 
	 * 		new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}
	/**
	 * Sets the 运单完结状态操作Service.
	 *
	 * @param waybillTransactionService 
	 * 		the new 运单完结状态操作Service
	 */
	public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}
	
	/**
	 * Sets the 计算&调整走货路径类.
	 *
	 * @param calculateTransportPathService
	 * 		 the new 计算&调整走货路径类
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	/**
	 * Sets the 推送微信服务类.
	 *
	 * @param weixinSendService
	 * 		 the new 推送微信服务类
	 */
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}

	/**
	 * Sets the bill receivable service.
	 *
	 * @param labeledGoodService the new bill receivable service
	 */
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	/**
	 * 应收账服务
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	
	/**
	 * 返货子母件结清-签收(PDA返货原单专用)
	 * @author foss-sunyanfei
	 * @date 2015-9-14 下午 15:24:19
	 * @param entity
	 * @param currentInfo
	 * @param oldWaybillNo 母单号/子单号 
	 */
	@Override
	public void returnTwoInOneSign(PdaDeliverSignDto entity,CurrentInfo currentInfo,TwoInOneWaybillDto oneDto) {
		//母件结清
		WaybillEntity wayMainbill = waybillManagerService.queryWaybillBasicByNo(oneDto.getMainWaybillNo());
		if(wayMainbill!= null){ //返货原单子母件 母单不为空
			WaybillSignResultEntity wEntity = new WaybillSignResultEntity();
			wEntity.setWaybillNo(oneDto.getMainWaybillNo());
			wEntity.setActive(FossConstants.YES);
			WaybillSignResultEntity oldresultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wEntity);
			if(oldresultWEntity!=null&&SignConstants.SIGN_STATUS_ALL.equals(oldresultWEntity.getSignStatus())){		
			}else{
				WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
				resultWEntity.setDeliverymanName(entity.getDeliverymanName());
				resultWEntity.setSignSituation(entity.getSituation());
				resultWEntity.setSignTime(entity.getSignTime());
				resultWEntity.setSignNote(entity.getSignNote());
				resultWEntity.setWaybillNo(oneDto.getMainWaybillNo());
				resultWEntity.setSignGoodsQty(wayMainbill.getGoodsQtyTotal());
				resultWEntity.setIsPdaSign(FossConstants.YES);
				resultWEntity.setReceiveMethod(wayMainbill.getReceiveMethod());
				expReturnPayment(wayMainbill,resultWEntity,entity,currentInfo,null,true);
			}
		}else{
			throw new SignException("返货原单为子母件，母件运单信息为空！");
		}
		
		//子件结清
		//获取返货子母件list
		List<WaybillExpressEntity> twoInOneList = waybillExpressService.queryWaybillListByWaybillNo(entity.getWaybillNo());
		if(CollectionUtils.isEmpty(twoInOneList)){
			throw new SignException("返货原单子母件列表为空！");
		}
		//存储批量执行异步方法需要的参数
		List<BtachExecPramsDto> batchList = new ArrayList<BtachExecPramsDto>();
		//循环结清子母件并签收
		if(twoInOneList.size() > 0){
			for(WaybillExpressEntity waybillExpress:twoInOneList){
				//查询当前结清子母件运单的运单信息
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillExpress.getOriginalWaybillNo());
				if(waybill!= null){
					WaybillSignResultEntity wEntity = new WaybillSignResultEntity();
					wEntity.setWaybillNo(waybillExpress.getOriginalWaybillNo()); //查询原单签收信息
					wEntity.setActive(FossConstants.YES);
					//查询运单是否有签收状态为全部签收的有效记录
					WaybillSignResultEntity oldresultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wEntity);
					if(oldresultWEntity!=null && SignConstants.SIGN_STATUS_ALL.equals(oldresultWEntity.getSignStatus())){
						//返货原单已签收，则不再往下走签收
					}else{
						WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
						resultWEntity.setDeliverymanName(entity.getDeliverymanName());
						resultWEntity.setSignSituation(entity.getSituation());
						resultWEntity.setSignTime(entity.getSignTime());
						resultWEntity.setSignNote(entity.getSignNote());
						resultWEntity.setWaybillNo(waybillExpress.getOriginalWaybillNo());//赋值为关联的原单号
						resultWEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
						resultWEntity.setIsPdaSign(FossConstants.YES);
						resultWEntity.setReceiveMethod(waybill.getReceiveMethod());
						expReturnPayment(waybill,resultWEntity,entity,currentInfo,null,true);//结清
						
						//返货原单未签收，做签收
						Date systemDate=new Date();	
						ArriveSheetEntity arriveEntity=new ArriveSheetEntity();
						//1.判断是不是快递代理
						OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
						if (companyDto != null) {
							//快递代理
							validaWaybillSignResult(oldresultWEntity,
									resultWEntity, systemDate);
							//--快递代理签收
							try {
								addExpressAgentSignResult(resultWEntity, currentInfo, waybill);
								
							} catch (BusinessException e) {//捕获异常
								LOGGER.error(e.getMessage(), e);//记录日志
								throw new SignException(e.getErrorCode(),e);//抛出异常
							}
						}else{
						
							//--判断是否存在未签收的到达联
							List<ArriveSheetEntity> entityList = arriveSheetManngerService.queryArriveSheetListNoSign(waybill.getWaybillNo());
							int signGty =0;//签收件数
							if(null == oldresultWEntity){
								signGty =0;
							}else{
								signGty = oldresultWEntity.getSignGoodsQty()==null ? 0 :oldresultWEntity.getSignGoodsQty();
							}
							//开单件数-签收件数(签收结果表的);全部签收 num=0，部分签收num>0
							int num = waybill.getGoodsQtyTotal()- signGty;
							if(num > 0){//有部分签收和未签收；--重新生成到达联 
								validaDate(systemDate, entityList);
								//1.有未签收的到达联，签收结果部分签收;2.有未签收到达联，无签收结果；3无签收结果。初始化一条到达联数据
								arriveEntity.setSituation(resultWEntity.getSignSituation());//签收情况
								arriveEntity.setDeliverymanName(resultWEntity.getDeliverymanName());//签收人
								//DN201511090005 添加“签收人类型”字段  243921
								arriveEntity.setDeliverymanType(resultWEntity.getDeliverymanType());
								arriveEntity.setIsPdaSign(resultWEntity.getIsPdaSign());
								arriveEntity.setWaybillNo(waybill.getWaybillNo());//运单号
								arriveEntity.setActive(FossConstants.ACTIVE);//有效
								arriveEntity.setDestroyed(FossConstants.NO);//未作废
								arriveEntity.setSignGoodsQty(num); //签收件数：
								arriveEntity.setReceiveMethod(resultWEntity.getReceiveMethod()); //
								arriveEntity.setArriveSheetGoodsQty(num);//到达联件数
								arriveEntity.setSignTime(resultWEntity.getSignTime()); //签收时间
								initArriveSheet(arriveEntity, currentInfo,waybill); //初始化到达联的其他信息
							 	//signOutStock(arriveEntity, waybill, currentInfo);
								
							 	//原运单签收出库逻辑
							 	ActualFreightEntity actual = actualFreightService.queryByWaybillNo(arriveEntity.getWaybillNo());
								boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
								arriveEntity.setWaybillNo(waybill.getWaybillNo());
								//调用中转接口获取流水号
								List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(waybill.getWaybillNo());
								List<String> serials = null;
								serials = validaCurrentInfo(currentInfo,
										waybill, arriveEntity,
										labeledGoodEntityList, serials);
								//如果签收件数小于运单开单件数
								if(arriveEntity.getSignGoodsQty() != null && arriveEntity.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
									todoActionService.updateLabeledStatusByWaybillNum(arriveEntity.getWaybillNo(),serials);
								}else {
									todoActionService.updateLabeledStatusByWaybillNum(arriveEntity.getWaybillNo(),null);
								}
								try {
									/** @author 231438
									 *  解决签收原单时STL.T_STL_POD中为插入一条签收状态为已签收（POD）的记录
									 */
									// 传入参数实体(运单号,当前运单号生效)
									WaybillSignResultEntity waybEntity = new WaybillSignResultEntity(arriveEntity.getWaybillNo(), FossConstants.ACTIVE);
									// 根据运单号 查询运单签收结果
									WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybEntity);
									validaArriveSheetEntity(currentInfo,
											waybill, arriveEntity, waybillSign);
									
									WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(arriveEntity,waybill,new Date(),currentInfo);
									wayEntity.setIsPdaSign(arriveEntity.getIsPdaSign());
									wayEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
									
									// 添加运单签收结果信息
									waybillSignResultService.addWaybillSignResult(wayEntity);
									//DN201511090005 添加“签收人类型”字段  243921
									wayEntity.setDeliverymanType(resultWEntity.getDeliverymanType());
									//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送 
									sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
									if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
										//如果是东方购物的客户编码，则原单签收时不需要发送签收状态至CRM DN201602220004 --by 243921 
										if(!SignConstants.CRM_ORDER_CUSTOMER_CODE.equals(waybill.getDeliveryCustomerCode())){
											waybillSignResultService.updateCrmOrder(waybill.getOrderNo(), currentInfo, wayEntity);
										}
									}
								} catch (SignException e) {//捕获异常
									throw new SignException(e.getErrorCode(), e);//抛出异常
								}
								//应用监控签收调用
								waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
								WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
								BeanUtils.copyProperties(arriveEntity,waybillSigndto);//复制代码
								/*//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
								waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
								//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
								if(isSendInvoiceInfo){
									waybillSignResultService.sendInvoiceInfo(waybill,actual);
								}*/
								
								BtachExecPramsDto btachExecPramsDto = new BtachExecPramsDto();
								btachExecPramsDto.setActual(actual);
								btachExecPramsDto.setSendInvoiceInfo(isSendInvoiceInfo);
								btachExecPramsDto.setWaybill(waybill);
								btachExecPramsDto.setWayEntity(wEntity);
								btachExecPramsDto.setWaybillSignResultDto(waybillSigndto);
								batchList.add(btachExecPramsDto);
						 	}
						}
					}
				}	
			}
		}
		// 循环调用签收要执行的异步方法(非快递代理)
		if(CollectionUtils.isNotEmpty(batchList) && batchList.size()>0){
			for(BtachExecPramsDto btachDto:batchList){
				OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
						btachDto.getWaybill().getCustomerPickupOrgCode(),FossConstants.ACTIVE);
				if (companyDto == null) { //非快递代理才批量执行异步方法
					/*if(SignConstants.SIGN_STATUS_ALL.equals(btachDto.getWayEntity().getSignStatus())){//如果签收状态为全部签收
					//调用CRM修改订单接口更新订单状态.
					waybillSignResultService.updateCrmOrder(btachDto.getWaybill().getOrderNo(), currentInfo, btachDto.getWayEntity());
				}*/
					
					//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
					waybillSignResultService.sendMessNotice(btachDto.getWaybill(), currentInfo,btachDto.getWaybillSignResultDto());
					//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
					if(btachDto.isSendInvoiceInfo()){
						waybillSignResultService.sendInvoiceInfo(btachDto.getWaybill(),btachDto.getActual());
					}
				}
			}
		}
	}

	private void validaWaybillSignResult(
			WaybillSignResultEntity oldresultWEntity,
			WaybillSignResultEntity resultWEntity, Date systemDate) {
		if(oldresultWEntity!=null){//如果签收状态为部分签收
			//--作废原来签收结果表
			if(SignConstants.SIGN_STATUS_PARTIAL.equals(oldresultWEntity.getSignStatus())){
				resultWEntity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
				// 运单签收结果里作废当前运单号
				waybillSignResultService.invalidWaybillSignResult(oldresultWEntity.getId(), systemDate);
			}
		}
	}
	/**
	 * 返货子母件结清-签收(自提签收、快递代理 签返货原单)
	 * @author foss-sunyanfei
	 * @date 2015-9-14 下午 15:24:10
	 * @param entity
	 * @param currentInfo
	 * @param oldWaybillNo
	 * @param oneDto
	 * @param newWsrEntity
	 */
	@Override
	public void returnTwoInOneSign(ArriveSheetEntity entity,CurrentInfo currentInfo,TwoInOneWaybillDto oneDto,WaybillSignResultEntity newWsrEntity){
		//母件运单信息
		WaybillEntity wayMainbill = waybillManagerService.queryWaybillBasicByNo(oneDto.getMainWaybillNo());
		if(wayMainbill == null){
			throw new SignException("返货原单为子母件，母件运单信息为空！");
		}
		//查母单提货网点是否落地配外发网点
		OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(wayMainbill.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
		//返货原单子母件 母单不为空 母件结清
		WaybillSignResultEntity signEntity = new WaybillSignResultEntity();
		signEntity.setWaybillNo(oneDto.getMainWaybillNo());
		signEntity.setActive(FossConstants.YES);
		WaybillSignResultEntity oldSignResultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signEntity);
		if(oldSignResultWEntity!=null&&SignConstants.SIGN_STATUS_ALL.equals(oldSignResultWEntity.getSignStatus())){		
			//母件已经签收了
		}else{
			WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
			resultWEntity.setDeliverymanName(entity.getDeliverymanName());
			resultWEntity.setSignSituation(entity.getSituation());
			resultWEntity.setSignTime(entity.getSignTime());
			resultWEntity.setSignNote(entity.getSignNote());
			resultWEntity.setWaybillNo(oneDto.getMainWaybillNo());
			resultWEntity.setSignGoodsQty(wayMainbill.getGoodsQtyTotal());
			resultWEntity.setIsPdaSign(FossConstants.NO);
			resultWEntity.setReceiveMethod(wayMainbill.getReceiveMethod());
			if(null == companyDto){ //返货原单为自有--结清母件
				expReturnPayment(wayMainbill,resultWEntity,null,currentInfo,null,false);
			}
		}
		
		//子件结清
		//获取返货子母件list
		List<WaybillExpressEntity> twoInOneList = null;
		if(null != entity){ //快递自提签收
			twoInOneList = waybillExpressService.queryWaybillListByWaybillNo(entity.getWaybillNo());
		}else if(null != newWsrEntity){ //快递代理签收
			twoInOneList = waybillExpressService.queryWaybillListByWaybillNo(newWsrEntity.getWaybillNo());
		}
		if(CollectionUtils.isEmpty(twoInOneList)){
			throw new SignException("返货原单子母件列表为空！");
		}
		//存储批量执行异步方法需要的参数
		List<BtachExecPramsDto> batchList = new ArrayList<BtachExecPramsDto>();
		//循环结清子母件并签收(快递代理不需要结清)
		if(twoInOneList.size() > 0 && CollectionUtils.isNotEmpty(twoInOneList)){
			for(WaybillExpressEntity waybillExpress:twoInOneList){
				//查询当前结清子母件运单的运单信息
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillExpress.getOriginalWaybillNo());
				if(waybill!= null){
					WaybillSignResultEntity wEntity = new WaybillSignResultEntity();
					wEntity.setWaybillNo(waybillExpress.getOriginalWaybillNo());//查询 原单的签收结果
					wEntity.setActive(FossConstants.YES);
					//查询运单是否有签收状态为全部签收的有效记录
					WaybillSignResultEntity oldresultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wEntity);
					if(oldresultWEntity!=null && SignConstants.SIGN_STATUS_ALL.equals(oldresultWEntity.getSignStatus())){
						//返货原单已签收，则不再往下走签收
					}else{
						WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
						resultWEntity.setDeliverymanName(entity.getDeliverymanName());
						resultWEntity.setSignSituation(entity.getSituation());
						resultWEntity.setSignTime(entity.getSignTime());
						resultWEntity.setSignNote(entity.getSignNote());
						resultWEntity.setWaybillNo(waybillExpress.getOriginalWaybillNo());
						resultWEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
						resultWEntity.setIsPdaSign(FossConstants.NO);
						resultWEntity.setReceiveMethod(waybill.getReceiveMethod());
						//判断是否快递代理
						if(null == companyDto){ //母件不是快递代理外发，其子件也不是（母件为快递代理外发那么子件必定是外发，外发签收不需要做结清）做结清(子件)
							expReturnPayment(waybill,resultWEntity,null,currentInfo,null,false);//结清
						}
						//返货原单未签收，做签收
						Date systemDate=new Date();	
						ArriveSheetEntity arriveEntity=new ArriveSheetEntity();
						//1.判断是不是快递代理
						//OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
						if (companyDto != null) {
							validaWaybillSignResult(oldresultWEntity,
									resultWEntity, systemDate);
							//--快递代理签收
							try {
								addExpressAgentSignResult(resultWEntity, currentInfo, waybill);
								
							} catch (BusinessException e) {//捕获异常
								LOGGER.error(e.getMessage(), e);//记录日志
								throw new SignException(e.getErrorCode(),e);//抛出异常
							}
						}else{
						
							//--判断是否存在未签收的到达联
							List<ArriveSheetEntity> entityList = arriveSheetManngerService.queryArriveSheetListNoSign(waybill.getWaybillNo());
							int signGty =0;//签收件数
							if(null == oldresultWEntity){
								signGty =0;
							}else{
								signGty = oldresultWEntity.getSignGoodsQty()==null ? 0 :oldresultWEntity.getSignGoodsQty();
							}
							//开单件数-签收件数(签收结果表的);全部签收 num=0，部分签收num>0
							int num = waybill.getGoodsQtyTotal()- signGty;
							if(num > 0){//有部分签收和未签收；--重新生成到达联 
								validaDate(systemDate, entityList);
								//1.有未签收的到达联，签收结果部分签收;2.有未签收到达联，无签收结果；3无签收结果。初始化一条到达联数据
								arriveEntity.setSituation(resultWEntity.getSignSituation());//签收情况
								arriveEntity.setDeliverymanName(resultWEntity.getDeliverymanName());//签收人
								//DN201511090005  添加“签收人类型”字段
								arriveEntity.setDeliverymanType(resultWEntity.getDeliverymanType());
								arriveEntity.setIsPdaSign(resultWEntity.getIsPdaSign());
								arriveEntity.setWaybillNo(waybill.getWaybillNo());//运单号
								arriveEntity.setActive(FossConstants.ACTIVE);//有效
								arriveEntity.setDestroyed(FossConstants.NO);//未作废
								arriveEntity.setSignGoodsQty(num); //签收件数：
								arriveEntity.setReceiveMethod(resultWEntity.getReceiveMethod()); //
								arriveEntity.setArriveSheetGoodsQty(num);//到达联件数
								arriveEntity.setSignTime(resultWEntity.getSignTime()); //签收时间
								initArriveSheet(arriveEntity, currentInfo,waybill); //初始化到达联的其他信息
							 	//signOutStock(arriveEntity, waybill, currentInfo);
								
							 	//原运单签收出库逻辑
							 	ActualFreightEntity actual = actualFreightService.queryByWaybillNo(arriveEntity.getWaybillNo());
								boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
								arriveEntity.setWaybillNo(waybill.getWaybillNo());
								//调用中转接口获取流水号
								List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(waybill.getWaybillNo());
								List<String> serials = null;
								//如果流水号集合不为空
								serials = validaCurrentInfo(currentInfo,
										waybill, arriveEntity,
										labeledGoodEntityList, serials);
								//如果签收件数小于运单开单件数
								if(arriveEntity.getSignGoodsQty() != null && arriveEntity.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
									todoActionService.updateLabeledStatusByWaybillNum(arriveEntity.getWaybillNo(),serials);
								}else {
									todoActionService.updateLabeledStatusByWaybillNum(arriveEntity.getWaybillNo(),null);
								}
								try {
									WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(arriveEntity,waybill,new Date(),currentInfo);
									wayEntity.setIsPdaSign(arriveEntity.getIsPdaSign());
									wayEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
									/** @author 231438
									 *  解决签收原单时STL.T_STL_POD中为插入一条签收状态为已签收（POD）的记录
									 */
									// 传入参数实体(运单号,当前运单号生效)
									WaybillSignResultEntity waybEntity = new WaybillSignResultEntity(arriveEntity.getWaybillNo(), FossConstants.ACTIVE);
									// 根据运单号 查询运单签收结果
									WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybEntity);
									validaArriveSheetEntity(currentInfo,
											waybill, arriveEntity, waybillSign);
									// 添加运单签收结果信息
									waybillSignResultService.addWaybillSignResult(wayEntity);
									
									//DN201511090005 添加“签收人类型”字段  243921
									wayEntity.setDeliverymanType(resultWEntity.getDeliverymanType());									
									//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送 
									sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
									if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
										//如果是东方购物的客户编码，则原单签收时不需要发送签收状态至CRM DN201602220004 --by 243921 
										if(!SignConstants.CRM_ORDER_CUSTOMER_CODE.equals(waybill.getDeliveryCustomerCode())){
											waybillSignResultService.updateCrmOrder(waybill.getOrderNo(), currentInfo, wayEntity);
										}
									}
								} catch (SignException e) {//捕获异常
									throw new SignException(e.getErrorCode(), e);//抛出异常
								}
								//应用监控签收调用
								waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
								WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
								BeanUtils.copyProperties(arriveEntity,waybillSigndto);//复制代码
								
								BtachExecPramsDto btachExecPramsDto = new BtachExecPramsDto();
								btachExecPramsDto.setActual(actual);
								btachExecPramsDto.setSendInvoiceInfo(isSendInvoiceInfo);
								btachExecPramsDto.setWaybill(waybill);
								btachExecPramsDto.setWayEntity(wEntity);
								btachExecPramsDto.setWaybillSignResultDto(waybillSigndto);
								batchList.add(btachExecPramsDto);
						 	}
						}
					}
				}	
			}
		}
		// 循环调用签收要执行的异步方法(非快递代理)
		if(CollectionUtils.isNotEmpty(batchList) && batchList.size()>0){
			for(BtachExecPramsDto btachDto:batchList){
				if (companyDto == null) { //非快递代理才批量执行异步方法
					//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
					waybillSignResultService.sendMessNotice(btachDto.getWaybill(), currentInfo, btachDto.getWaybillSignResultDto());
					//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
					if(btachDto.isSendInvoiceInfo()){
						waybillSignResultService.sendInvoiceInfo(btachDto.getWaybill(),btachDto.getActual());
					}
				}
			}
		}
	}

	private void validaArriveSheetEntity(CurrentInfo currentInfo,
			WaybillEntity waybill, ArriveSheetEntity arriveEntity,
			WaybillSignResultEntity waybillSign) {
		if(null != waybillSign){//不为空，不是第一次添加签收结果信息
			//不错处理，已插入记录到POD表
		}else{//是第一次添加签收结果信息 
			if(WaybillConstants.INNER_PICKUP.equals(waybill.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				//调用结算签收接口,stl表中添加一条数据
				this.confirmTakingCheck(arriveEntity, waybill, currentInfo);
			}
		}
	}

	private List<String> validaCurrentInfo(CurrentInfo currentInfo,
			WaybillEntity waybill, ArriveSheetEntity arriveEntity,
			List<LabeledGoodEntity> labeledGoodEntityList, List<String> serials) {
		if(!CollectionUtils.isEmpty(labeledGoodEntityList)){
			serials =new ArrayList<String>();
			for (LabeledGoodEntity labeledGoodEntity : labeledGoodEntityList) {
				serials.add(labeledGoodEntity.getSerialNo());
				
				SignDetailEntity signDetail = new SignDetailEntity();
				signDetail.setSerialNo(labeledGoodEntity.getSerialNo());//流水号
				// 到达联编号
				signDetail.setArrivesheetNo(arriveEntity.getArrivesheetNo());
				// 签收情况
				signDetail.setSituation(arriveEntity.getSituation());
				signDetailService.addSignDetailInfo(signDetail);//添加签收明细。
				
				InOutStockEntity inOutStock = new InOutStockEntity();
				// 运单号
				inOutStock.setWaybillNO(waybill.getWaybillNo());
				// 流水号
				inOutStock.setSerialNO(labeledGoodEntity.getSerialNo());
				// 部门编码
				inOutStock.setOrgCode(currentInfo.getCurrentDeptCode());
				// 操作人姓名
				inOutStock.setOperatorName(currentInfo.getEmpName());
				// 出入库类型 签收出库
				inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
				inOutStock.setOperatorCode(currentInfo.getEmpCode());
				// 进行出库操作
				try {
					stockService.outStockDelivery(inOutStock);
				} catch (StockException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new SignException(e.getErrorCode(),e);//抛出异常
				}
			}
		}
		return serials;
	}

	private void validaDate(Date systemDate, List<ArriveSheetEntity> entityList) {
		if(CollectionUtils.isNotEmpty(entityList)){ //有未签收到达联
			for (ArriveSheetEntity arrEntity : entityList) {  //作废到达联
				//到达联为NEW状态
				arrEntity.setActive(FossConstants.NO);
				arrEntity.setModifyTime(systemDate);
				//作废未签收的到达联
				arriveSheetManngerService.updateByPrimaryKeySelective(arrEntity);
			 }
		}
	}
}
