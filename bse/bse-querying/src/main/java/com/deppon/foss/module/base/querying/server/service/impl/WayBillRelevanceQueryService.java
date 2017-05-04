package com.deppon.foss.module.base.querying.server.service.impl;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.querying.server.service.IIntegrativeQueryService;
import com.deppon.foss.module.base.querying.server.service.IMsgOnlineService;
import com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService;
import com.deppon.foss.module.base.querying.shared.domain.ArriveChanSignVo;
import com.deppon.foss.module.base.querying.shared.domain.HandlingCharges;
import com.deppon.foss.module.base.querying.shared.domain.ReturnShipping;
import com.deppon.foss.module.base.querying.shared.domain.ServiceRemedy;
import com.deppon.foss.module.base.querying.shared.exception.QueryingBussinessException;
import com.deppon.foss.module.base.querying.shared.vo.WaybillVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IReturnBillProcessService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillQueryForBseService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ChangeSignRfcDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverySituationDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillQueryException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISettlementInfoQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;


/**
 * 
 */
public class WayBillRelevanceQueryService implements IWayBillRelevanceQueryService{
    
    /**
     * 
     */
    private static final Logger log = Logger.getLogger(IntegrativeQueryService.class);
    /** 综合查询service接口 **/
    private IIntegrativeQueryService integrativeQueryService;
    /** 查询签收变更结果 **/
    private ISignChangeService signChangeService;
    /**  用于运单和更改单查询 **/
    private IWaybillQueryService waybillQueryService;
    /** 定义了标签打印信息的相关操作 **/
    private IPrintLabelService printLabelService;
    /** 签收单返单service 查询签收单返单 **/
    private IReturnBillProcessService returnSignBillProcessService;
    /** 货款结清Service **/
    private IRepaymentService repaymentService;
    /** 查询运单的到付金额、代收货款、装卸费、发票信息Service接口 [提供给综合管理使用] **/
    private ISettlementInfoQueryService settlementInfoQueryService;
    /** 查询运单的派送情况Service接口 [提供给综合管理使用] **/
    private IWaybillQueryForBseService waybillQueryForBseService;
    
    /**
     * 综合查询-在线消息查询*.
     */
    private IMsgOnlineService msgOnlineService;
    
    /** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	
    
    
    /**
     * 设置 查询派送情况 信息*.
     *
     * @param signChangeService the new 查询派送情况信息 *
     */
    public void setWaybillQueryForBseService(
			IWaybillQueryForBseService waybillQueryForBseService) {
		this.waybillQueryForBseService = waybillQueryForBseService;
	}


	/**
     * 设置 综合查询service接口 *.
     *
     * @param integrativeQueryService the new 综合查询service接口 *
     */
    public void setIntegrativeQueryService(
    	IIntegrativeQueryService integrativeQueryService) {
        this.integrativeQueryService = integrativeQueryService;
    }

    
    /**
     * 设置 查询签收变更结果 *.
     *
     * @param signChangeService the new 查询签收变更结果 *
     */
    public void setSignChangeService(ISignChangeService signChangeService) {
        this.signChangeService = signChangeService;
    }

    
    /**
     * 设置 用于运单和更改单查询 *.
     *
     * @param waybillQueryService the new 用于运单和更改单查询 *
     */
    public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
        this.waybillQueryService = waybillQueryService;
    }

    
    /**
     * 设置 定义了标签打印信息的相关操作 *.
     *
     * @param printLabelService the new 定义了标签打印信息的相关操作 *
     */
    public void setPrintLabelService(IPrintLabelService printLabelService) {
        this.printLabelService = printLabelService;
    }

    
    /**
     * 设置 签收单返单service 查询签收单返单 *.
     *
     * @param returnSignBillProcessService the new 签收单返单service 查询签收单返单 *
     */
    public void setReturnSignBillProcessService(
    	IReturnBillProcessService returnSignBillProcessService) {
        this.returnSignBillProcessService = returnSignBillProcessService;
    }

    
    /**
     * 设置 货款结清Service *.
     *
     * @param repaymentService the new 货款结清Service *
     */
    public void setRepaymentService(IRepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    
    /**
     * 设置 查询运单的到付金额、代收货款、装卸费、发票信息Service接口 [提供给综合管理使用] *.
     *
     * @param settlementInfoQueryService the new 查询运单的到付金额、代收货款、装卸费、发票信息Service接口 [提供给综合管理使用] *
     */
    public void setSettlementInfoQueryService(
    	ISettlementInfoQueryService settlementInfoQueryService) {
        this.settlementInfoQueryService = settlementInfoQueryService;
    }
    
    /**
     * 设置在线消息查询信息
     *
     * @param customerService the customerService to set
     */
	public void setMsgOnlineService(IMsgOnlineService msgOnlineService) {
		this.msgOnlineService = msgOnlineService;
	}
	
	
	/**
	 * 合并 跟踪记录和在线消息查询信息
	 * @author 132599-foss-shenweihua
	 * @date 2013-07-11 上午10:34:59
	 */
	private List<TrackRecordEntity> convertTrackRecordInfo(List<MsgOnlineEntity> msgOnlineEntitys){
		List<TrackRecordEntity> trackRecordEntitys = new ArrayList<TrackRecordEntity>();
		if (CollectionUtils.isNotEmpty(msgOnlineEntitys)) {
			for (MsgOnlineEntity msgOnlineEntity : msgOnlineEntitys) {
				TrackRecordEntity trackRecordEntity = new TrackRecordEntity();
				trackRecordEntity.setTrackContent(msgOnlineEntity.getContext());//设置消息内容
				trackRecordEntity.setTraceCategories(msgOnlineEntity.getTraceCategories());//设置跟踪类别
				trackRecordEntity.setCreateTime(msgOnlineEntity.getCreateTime());//设置起草时间
				trackRecordEntity.setCreateOrgName(msgOnlineEntity.getSendOrgName());//设置起草部门
				trackRecordEntity.setCreateUserName(msgOnlineEntity.getSendUserName());//设置起草人
				trackRecordEntity.setAcceptedTime(msgOnlineEntity.getModifyTime());//设置受理时间
				trackRecordEntity.setAcceptedOrgName(msgOnlineEntity.getReceiveOrgName());//设置受理部门   
				trackRecordEntity.setAcceptedMan(msgOnlineEntity.getModifyUserName());//设置受理人
				trackRecordEntity.setAcceptedRemark(msgOnlineEntity.getRemarks());//设置受理备注
				trackRecordEntitys.add(trackRecordEntity);
			}
		}
		
		return trackRecordEntitys;
	}
	
    /** 
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-07-11 上午11:35:25
     * @param entity
     * @return
     * @throws QueryingBussinessException 
     * @see com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService#queryTrackRecords(com.deppon.foss.module.base.querying.shared.domain.TrackRecordEntity)
     */
    @Override
    public WaybillVo queryTrackRecords(TrackRecordEntity entity)throws QueryingBussinessException{
	WaybillVo waybillVo = new WaybillVo();
	//获取跟踪记录信息
	List<TrackRecordEntity> trackRecordEntities= integrativeQueryService.queryTrackRecords(entity);
	for(TrackRecordEntity trackEntity:trackRecordEntities){
		if(trackEntity.getTraceCategories()==null||"".equals(trackEntity.getTraceCategories())){
			trackEntity.setTraceCategories(QueryingConstant.QUERYING_REMARK);
		}
	}
	 MsgOnlineEntity msgEntity = new MsgOnlineEntity();
	 msgEntity.setWaybillNo(entity.getWaybillNo());
	 //获取在线消息查询信息
	 List<MsgOnlineEntity> msgEntities = msgOnlineService.queryOnlineMsgByEntity(msgEntity, NumberConstants.NUMERAL_ZORE, NUMBER);
		for(MsgOnlineEntity msgEntitys:msgEntities){
			msgEntitys.setTraceCategories(QueryingConstant.ONLINE_TRACKING);
		}
		
	waybillVo.setTrackRecordList(trackRecordEntities);
	waybillVo.getTrackRecordList().addAll(convertTrackRecordInfo(msgEntities));
	return waybillVo;
    }

    /** 
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午12:35:25
     * @param waybillNo
     * @return
     * @throws QueryingBussinessException 
     * @see com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService#queryWayBilllChangeByWaybillNo(java.lang.String)
     */
    @Override
    public WaybillVo queryWayBilllChangeByWaybillNo(String waybillNo)throws QueryingBussinessException{
	WaybillVo waybillVo = new WaybillVo();
	// 出发更改
	List<LeaveChangeByWaybillNoResultDto> byWaybillNoResultDtos = new ArrayList<LeaveChangeByWaybillNoResultDto>();
	try {
	    byWaybillNoResultDtos = waybillQueryService.queryLeaveChangeByWaybillNo(waybillNo);
	} catch (WaybillQueryException e) {
	    log.error(e.getMessage(), e);
	    throw new QueryingBussinessException(e.getErrorCode(),
		    e.getMessage(), e);
	} catch (Exception e) {
	   log.error(e.getMessage(), e);
	}
	// 到达更改
	List<ChangeSignRfcDto> changeSignRfcDtos = new ArrayList<ChangeSignRfcDto>();
	try {
		changeSignRfcDtos = signChangeService.queryArriveChangeByWaybillNo(waybillNo);
	} catch (SignException e) {
		log.error(e.getMessage(), e);
		throw new QueryingBussinessException(e.getErrorCode(),
			e.getMessage(), e);
		} catch (Exception e) { 
		log.error(e.getMessage(), e);
	}
	// 到达构造信息--传给前台
	List<ArriveChanSignVo> arriveChangeSigns = geneArriveChanSigns(changeSignRfcDtos);
	waybillVo.setStartChangeSignRfcDtos(byWaybillNoResultDtos);
	waybillVo.setArriveChangeSignRfcDtos(arriveChangeSigns);
	
	return waybillVo;
    }

    /** 
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午12:35:25   
     * @param waybillNo
     * @param billTime
     * @param leaveDeptCode
     * @param arriveDeptCode
     * @return
     * @throws QueryingBussinessException 
     * @see com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService#queryFinanceConditionByWayBillNo(java.lang.String, java.util.Date, java.lang.String, java.lang.String)
     */
    @Override
    public WaybillVo queryFinanceConditionByWayBillNo(String waybillNo,Date billTime,String leaveDeptCode,String arriveDeptCode) throws QueryingBussinessException{
	WaybillVo waybillVo = new WaybillVo();
	// 运单到付费用信息     接送货提供接口
	List<RepaymentArriveDto> repaymentArriveDtos = repaymentService	.queryArriveFeeByWaybillNo(waybillNo);
	// 前端是取的list中第一个值，所以循环 把所有值封装到第一个值中 便于前端不修改代码
	if (CollectionUtils.isNotEmpty(repaymentArriveDtos)) {
	    List<RepaymentArriveDto> repaymentArriveDtoList = new ArrayList<RepaymentArriveDto>();
	    RepaymentArriveDto repaymentArrive = new RepaymentArriveDto();
	    repaymentArrive.setActualFreight(new BigDecimal(0));
	    repaymentArrive.setArriveAmount(new BigDecimal(0));
	    for (RepaymentArriveDto repaymentArriveDto : repaymentArriveDtos) {
		repaymentArrive.setActualFreight(repaymentArrive.getActualFreight().add(null == 
			repaymentArriveDto.getActualFreight() ? new BigDecimal(0)
		: repaymentArriveDto.getActualFreight()));
		repaymentArrive.setArriveAmount(repaymentArrive.getArriveAmount().add(null == 
			repaymentArriveDto.getArriveAmount() ? new BigDecimal(0): 
			    repaymentArriveDto.getArriveAmount()));
	    }
	    repaymentArrive.setPaymentTime(repaymentArriveDtos.get(0).getPaymentTime());
	    repaymentArrive.setPaymentType(repaymentArriveDtos.get(0).getPaymentType());
	    repaymentArriveDtoList.add(repaymentArrive);
	    repaymentArriveDtos = repaymentArriveDtoList;
	}
	
	// 代收货款信息    结算提供接口
	WaybillSettlementInfoDto waybillSettlementInfoDto = new WaybillSettlementInfoDto();
	try {
	    waybillSettlementInfoDto = settlementInfoQueryService.queryCodFeeByWaybillNO(waybillNo,billTime,new Date(billTime.getTime()+ QueryingConstant.THIRTYDAYAFATER_TIMEMILLIS));
	} catch (SettlementException e) {
	    log.error(e.getMessage(), e);
	    throw new QueryingBussinessException(e.getErrorCode(),e.getMessage(), e);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	// 发票信息     结算提供接口
	WaybillSettlementInfoDto settlementInfoDto;
	try {
	    settlementInfoDto = settlementInfoQueryService.queryInvoiceByWaybillNO(waybillNo,leaveDeptCode,arriveDeptCode,billTime,
		    new Date(billTime.getTime()+ QueryingConstant.THIRTYDAYAFATER_TIMEMILLIS));
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    throw new QueryingBussinessException(e.getMessage(),
	    e.getMessage(), e);
	}
	
	// 装卸费信息     结算提供接口
	WaybillSettlementInfoDto infoDto;
	try {
	    infoDto = settlementInfoQueryService.queryServiceFeeByWaybillNO(waybillNo, billTime, new Date(billTime.getTime()
			    + QueryingConstant.THIRTYDAYAFATER_TIMEMILLIS));
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    throw new QueryingBussinessException(e.getMessage(),
	    e.getMessage(), e);
	}
			
	
	WaybillSettlementInfoDto dto = new WaybillSettlementInfoDto();
	if (null != waybillSettlementInfoDto) {
		BeanUtils.copyProperties(waybillSettlementInfoDto, dto);
	}
	if (null != settlementInfoDto) {
		BeanUtils.copyProperties(settlementInfoDto, dto);
	}
	if (null != infoDto) {
		BeanUtils.copyProperties(infoDto, dto);
	}
	waybillVo.setRepaymentArriveDtos(repaymentArriveDtos);
	waybillVo.setWaybillSettlementInfoDto(dto);
	return waybillVo;
    }

    /** 
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午12:35:25
     * @param waybillNo
     * @return
     * @throws QueryingBussinessException 
     * @see com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService#querySignedBillByWaybillNo(java.lang.String)
     */
    @Override
    public WaybillVo querySignedBillByWaybillNo(String waybillNo)throws QueryingBussinessException{
	// 签收单信息     接送货提供接口
	WaybillVo waybillVo = new WaybillVo();
	List<ReturnBillProcessEntity> billProcessEntities = returnSignBillProcessService.querySignedBillByWaybillNo(waybillNo);
	waybillVo.setBillProcessEntities(billProcessEntities);
	return waybillVo;
    }

    /** 
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-7-06 下午10:35:25
     * @param waybillNo
     * @return
     * @throws QueryingBussinessException 
     * @see com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService#queryLabelPrintByWaybillNo(java.lang.String)
     */
    @Override
    public WaybillVo queryLabelPrintByWaybillNo(String waybillNo)throws QueryingBussinessException{
	// 打印记录信息
	WaybillVo waybillVo = new WaybillVo();
	List<GoodsLabelPrintDto> goodsLabelPrintDtos = printLabelService.queryLabelPrintByWaybillNoDetail(waybillNo);
	waybillVo.setGoodsLabelPrintDtos(goodsLabelPrintDtos);
	return waybillVo;
    }

    /**
     * 
     *
     * @param changeSignRfcDtos 
     * @return 
     */
    private List<ArriveChanSignVo> geneArriveChanSigns(
		List<ChangeSignRfcDto> changeSignRfcDtos) {
	List<ArriveChanSignVo> arriveChangeSigns = new ArrayList<ArriveChanSignVo>();
	if (CollectionUtils.isNotEmpty(changeSignRfcDtos)) {
		ArriveChanSignVo vo = null;
		for (int i = 0; i < changeSignRfcDtos.size(); i++) {
			vo = new ArriveChanSignVo();
			vo = copyProperties(changeSignRfcDtos.get(i), vo);
			List<SignRfcChangeDetailEntity> changeDetails = changeSignRfcDtos
					.get(i).getSignRfcChangeDetailList();
			// 构造更改信息
			StringBuffer detail = new StringBuffer();
			if (CollectionUtils.isNotEmpty(changeDetails)) {
				for (int j = 0; j < changeDetails.size(); j++) {
					
					//若变更前信息为空，
					if(StringUtil.isEmpty(changeDetails.get(j).getBeforeRfcinfo())){
						detail.append(changeDetails.get(j).getRfcItems());
						continue;
					}
					detail.append("由 ");
					detail.append(changeDetails.get(j).getBeforeRfcinfo());
					detail.append("改为 ");
					detail.append(changeDetails.get(j).getAfterRfcinfo());
					detail.append("; ");
				}
			}
			vo.setChangeMsg(detail.toString());
			arriveChangeSigns.add(vo);
		}
	}
	return arriveChangeSigns;
    }
    
    /**
     * 
     *
     * @param csDto 
     * @param vo 
     * @return 
     */
    private ArriveChanSignVo copyProperties(ChangeSignRfcDto csDto,ArriveChanSignVo vo) {
	// 单据编号
	vo.setRfcNo(csDto.getSignRfcEntity().getWaybillNo());
	// 起草人
	vo.setDrafter(csDto.getSignRfcEntity().getDrafter());
	// 起草时间
	vo.setDraftTime(csDto.getSignRfcEntity().getDraftTime());
	// 受理部门
	vo.setOperateOrgName(csDto.getSignRfcEntity().getOperateOrgName());
	// 受理时间
	vo.setOperateTime(csDto.getSignRfcEntity().getOperateTime());
	// 受理备注
	vo.setNotes(csDto.getSignRfcEntity().getNotes());
	// 变更原因
	vo.setReason(csDto.getSignRfcEntity().getReason());
	// 受理状态 数据字典
	vo.setStatus(csDto.getSignRfcEntity().getStatus());
	return vo;
    }

    /**
     * 
     * <p>派送情况</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-7-04 下午1:39:47
     * @param waybillNo
     * @return
     * @see
     */
	@Override
	public WaybillVo queryLabelDeliverySituationByWaybillNo(String waybillNo) throws QueryingBussinessException{
		WaybillVo waybillVo = new WaybillVo();
		DeliverySituationDto deliverySituationDto = new DeliverySituationDto();
		try {
			deliverySituationDto = waybillQueryForBseService.queryDeliverySituationByWaybill(waybillNo);
		} catch (WaybillQueryException e) {
		    log.error(e.getMessage(), e);
		    throw new QueryingBussinessException(e.getErrorCode(),
			    e.getMessage(), e);
		} catch (Exception e) {
		   log.error(e.getMessage(), e);
		}
		waybillVo.setDeliverySituationDto(deliverySituationDto);
		return waybillVo;
	}
	
	/**
	 * 
	 * <p>
	 * 标签打印记录（导出用）
	 * </p>
	 * 
	 * @author FOSS-132599-shenweihua
	 * @date 2013-07-06 下午5:17:17
	 * @param waybillNo
	 * @return 
	 * @see
	 */
	@Override
	public InputStream queryLablePrinting(String waybillNo) {
		List<GoodsLabelPrintDto> goodsLabelPrintDtos = printLabelService.queryLabelPrintByWaybillNoDetail(waybillNo);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(GoodsLabelPrintDto row : goodsLabelPrintDtos){
			List<String> columnList = new ArrayList<String>();
			// 流水号
			columnList.add(row.getSerialNo());
			// 关联流水号
			columnList.add(row.getOriginalSerialNo());
			// 时间
			columnList.add(DateUtils.convert(row.getPrintTime(), DateUtils.DATE_TIME_FORMAT));
			// 打印人
			columnList.add(row.getPrintUserName());
			// 所属部门
			columnList.add(row.getPrintUserOrgName());
			rowList.add(columnList);
		}
		// 列头
		String[] rowHeads = {"流水号","关联流水号","时间","打印人","所属部门"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(QueryingConstant.LABEL_PRINTING_NAME);
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}

	/**
	 * 
	 * <p>
	 * 财务情况
	 * </p>
	 * 
	 * @author FOSS-132599-shenweihua
	 * @date 2013-07-16 下午6:17:17
	 * @param waybillNo
	 * @return 
	 * @see
	 */
	@Override
	public WaybillVo queryWaybillFinanceInfo(String waybillNo) throws QueryingBussinessException{
		WaybillVo waybillVo = new WaybillVo();
		HandlingCharges handlingCharges = new HandlingCharges();//装卸费
		ServiceRemedy serviceRemedy = new ServiceRemedy();// 服务补救
		ReturnShipping returnShipping = new ReturnShipping();//退运费
		WaybillFinanceInfoDto waybillFinanceInfoDto = new WaybillFinanceInfoDto();
		try {
			waybillFinanceInfoDto = settlementInfoQueryService.queryWaybillFinanceInfo(waybillNo);
			if(CollectionUtils.isNotEmpty(waybillFinanceInfoDto.getOtherFeeInfos())){
				for(OtherFeeInfo otherFeeInfo:waybillFinanceInfoDto.getOtherFeeInfos()){
					if(otherFeeInfo.getPayableBillType().equals("SF")){//装卸费
						handlingCharges.setAmount(otherFeeInfo.getAmount());
						handlingCharges.setPaymentStatus(otherFeeInfo.getPaymentStatus());
						handlingCharges.setPaymentType(otherFeeInfo.getPaymentType());
					}else if(otherFeeInfo.getPayableBillType().equals("CP")){ //服务补救
						serviceRemedy.setAmount(otherFeeInfo.getAmount());
						serviceRemedy.setPaymentStatus(otherFeeInfo.getPaymentStatus());
						serviceRemedy.setPaymentType(otherFeeInfo.getPaymentType());
					}else{//退运费
						returnShipping.setAmount(otherFeeInfo.getAmount());
						returnShipping.setPaymentStatus(otherFeeInfo.getPaymentStatus());
						returnShipping.setPaymentType(otherFeeInfo.getPaymentType());
						returnShipping.setVerifyRcvAmount(otherFeeInfo.getVerifyRcvAmount());
						returnShipping.setReturnableAmount(otherFeeInfo.getReturnableAmount());
					}
				}
			}
		} catch (WaybillQueryException e) {
		    log.error(e.getMessage(), e);
		    throw new QueryingBussinessException(e.getErrorCode(),
			    e.getMessage(), e);
		} catch (Exception e) {
		   log.error(e.getMessage(), e);
		}
		waybillVo.setHandlingCharges(handlingCharges);
		waybillVo.setServiceRemedy(serviceRemedy);
		waybillVo.setReturnShipping(returnShipping);
		waybillVo.setWaybillFinanceInfoDto(waybillFinanceInfoDto);
		return waybillVo;
	}

	
	/**
	 * 
	 * <p>
	 * 跟踪记录（导出用）
	 * </p>
	 * 
	 * @author FOSS-132599-shenweihua
	 * @date 2013-08-01 上午11:34:17
	 * @param waybillNo
	 * @return 
	 * @see
	 */
	@Override
	public InputStream queryExportTrackRecords(String waybillNo) {
		TrackRecordEntity entity = new TrackRecordEntity();
		entity.setWaybillNo(waybillNo);
		MsgOnlineEntity msgEntity = new MsgOnlineEntity();
		 msgEntity.setWaybillNo(waybillNo);
		//获取跟踪记录信息
		List<TrackRecordEntity> trackRecordEntities= integrativeQueryService.queryTrackRecords(entity);
		for(TrackRecordEntity trackEntity:trackRecordEntities){
			if(trackEntity.getTraceCategories()==null||"".equals(trackEntity.getTraceCategories())){
				trackEntity.setTraceCategories(QueryingConstant.QUERYING_REMARK);
			}
		}
		//获取在线消息查询信息
		 List<MsgOnlineEntity> msgEntities = msgOnlineService.queryOnlineMsgByEntity(msgEntity, NumberConstants.NUMERAL_ZORE, NUMBER);
			for(MsgOnlineEntity msgEntitys:msgEntities){
				msgEntitys.setTraceCategories(QueryingConstant.ONLINE_TRACKING);
			}
		trackRecordEntities.addAll(convertTrackRecordInfo(msgEntities));
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(TrackRecordEntity row : trackRecordEntities){
			List<String> columnList = new ArrayList<String>();
			// 跟踪内容
			columnList.add(row.getTrackContent());
			// 联系人
			columnList.add(row.getContacts());
			// 跟踪类别
			columnList.add(row.getTraceCategories());
			// 跟踪/起草时间
			columnList.add(DateUtils.convert(row.getCreateTime(), DateUtils.DATE_TIME_FORMAT));
			// 跟踪/起草部门
			columnList.add(row.getCreateOrgName());
			// 跟踪/起草人
			columnList.add(row.getCreateUserName());
			//受理时间
			columnList.add(DateUtils.convert(row.getAcceptedTime(), DateUtils.DATE_TIME_FORMAT));
			//受理部门
			columnList.add(row.getAcceptedOrgName());
			//受理人
			columnList.add(row.getAcceptedMan());
			//受理备注
			columnList.add(row.getAcceptedRemark());
			rowList.add(columnList);
		}
		// 列头
		String[] rowHeads = {"跟踪内容","联系人","跟踪类别","跟踪/起草时间","跟踪/起草部门","跟踪/起草人","受理时间","受理部门","受理人","受理备注"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(QueryingConstant.TRACK_RECORDS_NAME);
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}
    
    

}
