package com.deppon.foss.module.pickup.crm.server.service.impl;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcChangeDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.ProductCodeUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.fssc.inteface.domain.payment.DealComplainChangeFossRequest;
import com.deppon.fssc.inteface.domain.payment.DealComplainChangeFossResponse;

public class ComplChangeSignResultProcessor implements IProcess {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ComplChangeSignResultProcessor.class);
	//运单管理服务
	private IWaybillManagerService waybillManagerService;
	//运单签收结果
	private IWaybillSignResultDao waybillSignResultDao; 
	//落地配公司网点接口
	private ILdpAgencyDeptService ldpAgencyDeptService;
	//到达联管理接口
	private IArrivesheetDao arrivesheetDao;
	//运单签收结果接口
	private IWaybillSignResultService waybillSignResultService;
	//运单变更接口
	private ISignChangeService signChangeService;
	//签收变更主表Dao
	private ISignRfcDao signRfcDao;
	//签收变更详情Dao
	private ISignRfcChangeDetailDao signRfcChangeDetailDao;
	//到达联管理服务接口
	private IArriveSheetManngerService arriveSheetManngerService;
	//private IActualFreightDao actualFreightDao; //queryByWaybillNo
	//运单承运服务接口
	private IActualFreightService actualFreightService;
	//签收详情服务接口
	private ISignDetailService signDetailService;
	/**
	 * 业务斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * foss记录内物短少差错 
	 */
	private IRecordErrorWaybillService recordErrorWaybillService;
	/***
	 * 记录异常运单 上报QMS的Service
	 */
	private IRecordErrorWaybillDao recordErrorWaybillDao;
	/**
	 * 快递运单服务
	 */
	private IWaybillExpressService waybillExpressService;
	/**
	 * 变更类型-运单结果 变更明细表实体赋值时使用
	 */
	private static final String CHANGE_DETAIL_RFC_TYPE_R = SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT;
	/**
	 * 变更类型-到达联
	 */
	private static final String CHANGE_DETAIL_RFC_TYPE_A = SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_ARRIVESHEET;
	/**
	 * 签收情况-签收结果表
	 */
	private static final String CHANGE_DETAIL_SITUATION_R = SignConstants.SIGN_SITUATION;
	/**
	 * 签收情况-到达联表
	 */
	private static final String CHANGE_DETAIL_SITUATION_A = SignConstants.SITUATION;
	/**
	 * 变更结果-成功
	 */
	private static final String SUCCESS = "success";
	/**
	 * 变更结果-失败
	 */
	private static final String FAILED = "failed";
	/**
	 * 投诉类型-货物潮湿
	 */
	private static final String GOODS_DAMP = "货物潮湿";
	/**
	 * 投诉类型-货物污染
	 */
	private static final String GOODS_POLLUTION = "货物污染";
	/**
	 * 投诉类型-破损
	 */
	private static final String GOODS_BREAK = "破损";
	/**
	 * 投诉类型-内物短少
	 */
	private static final String GOODS_SHORT = "内物短少";
	/**
	 * @author foss-chenjunying
	 * @date 2014-12-15 上午10:53:10
	 * FOSS根据凭证号判断时需要满足以下条件才可以变更签收结果：
	 * A、传入的运单号不为空且运单号是FOSS中存在的； 
	 * B、此运单号签收情况为正常签收的；
	 * C、此运单号签收时间（以最后一次签收时间为准）为72小时以内的；
	 * D、此运单必须为整票签收完毕，若分批签收的，以最后一个签收的时间开始计算72小时，并将最后一个正常签收的签收情况改为异常签收；
	 * 
	 */
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		LOGGER.info("投诉变更签收结果开始....");
		DealComplainChangeFossRequest request = (DealComplainChangeFossRequest)obj;
		DealComplainChangeFossResponse response = new DealComplainChangeFossResponse();
		//验证传入的投诉参数
		try{
			//校验传入参数
			validateParams(request);
		//异常处理
		}catch(BusinessException be){
			//记录日志
			LOGGER.info("变更失败，"+be.getMessage());
			response.setChangeResult(FAILED);
			//记录变更结果信息
			response.setChangeResultInfo(be.getMessage());
			return response;
		}
		
		MutexElement mutexElement = new MutexElement(request.getWaybillNo(), "运单编号", MutexElementType.APPLICATION_SIGN_RFC);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){
			//如果没有得到锁
			LOGGER.info("当前运单操作中，请稍后再试");
			//记录日志
			response.setChangeResult(FAILED);
			//记录变更结果信息
			response.setChangeResultInfo(SignException.WAYBILL_LOCKED);//失败原因
			return response;
		}
		
		
		//获取投诉工单的运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(request.getWaybillNo());
		if (waybillEntity == null) {
			// 运单不存在
			LOGGER.info("变更失败，该运单号不存在");
			//解锁
			businessLockService.unlock(mutexElement);
			//记录变更结果
			response.setChangeResult(FAILED);
			//记录变更结果信息
			response.setChangeResultInfo("该运单号不存在");
			return response;
		}
		
		ActualFreightEntity actual = null;   //运单承运表,如果投诉变更为Y(已投诉成立的)，再次投诉不满足正常签收条件，不再变更
		//查询运单实际承运表记录
		actual = actualFreightService.queryByWaybillNo(request.getWaybillNo());
		if(null==actual){
			//记录日志
			LOGGER.info("变更失败,运单无实际承运表记录");
			//解锁
			businessLockService.unlock(mutexElement);
			//记录投诉变更结果
			response.setChangeResult(FAILED);
			//记录变更结果信息
			response.setChangeResultInfo("运单无实际承运表记录");
			return response;
		}
		//运单存在投诉变更
		if(SignConstants.YES.equals(actual.getComplainStatus())){ 
			//记录日志
			LOGGER.info("变更失败,运单已有投诉变更成立，不再变更签收结果");
			//解锁
			businessLockService.unlock(mutexElement);
			//记录投诉变更结果
			response.setChangeResult(FAILED);
			//记录变更结果信息
			response.setChangeResultInfo("运单已有投诉变更成立，不再变更签收结果");
			return response;
		}
		
		// 传入参数(运单号,当前运单号生效)
		WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(waybillEntity.getWaybillNo(), FossConstants.ACTIVE);
		//据运单号,active = 'Y' 查询运单签结果里的有效运单信息 
		WaybillSignResultEntity waybillSign = waybillSignResultDao.queryWaybillSignResult(wayEntity);
		if(waybillSign == null){
			
			/**
			 * DN201606120019	
			 * foss-xujie 330547
			 * 2016-6-29
			 */
			
			//如果投诉为内物短少，上报QMS内物短少差错
				if(StringUtils.contains(request.getComplaintBusinessType(), GOODS_SHORT)){
				//保存QMS内物短少差错信息(JOB上报)
				LOGGER.info("*************保存QMS内物短少差错信息***********start");
				
					RecordErrorWaybillDto recordErrorWaybillDto = new RecordErrorWaybillDto();
					//主键
					recordErrorWaybillDto.setId(UUIDUtils.getUUID());
					//是否上报(Y:未上报)
					recordErrorWaybillDto.setActive(FossConstants.YES);
					//运单号
					recordErrorWaybillDto.setWaybillNo(request.getWaybillNo());
					//短少量
					recordErrorWaybillDto.setAlittleShort(null);
					//外包装是否完好
					recordErrorWaybillDto.setPackingResult(null);
					//创建时间
					recordErrorWaybillDto.setCreateTime(new Date());
					//修改时间
					recordErrorWaybillDto.setModifyTime(new Date());
					//上报人名字
					recordErrorWaybillDto.setOperateName(waybillEntity.getCreateUser());
					//上报人工号
					recordErrorWaybillDto.setOperateCode(waybillEntity.getCreateUserCode());
					//上报人所在部门编码
					recordErrorWaybillDto.setOperateOrgCode(waybillEntity.getLastLoadOrgCode());
					//上报人所在部门名字
					recordErrorWaybillDto.setOperateOrgName(waybillEntity.getLastLoadOrgName());
					//流水号
					recordErrorWaybillDto.setSerialNo(null);
					recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
				
				LOGGER.info("*************保存QMS内物短少差错信息***********end");
			
			}
				//运单未签收
				LOGGER.info("变更失败,运单未签收");
				//解锁
				businessLockService.unlock(mutexElement);
				//记录变更结果
				response.setChangeResult(FAILED);
				//记录变更结果信息
				response.setChangeResultInfo("运单未签收");
			    return response; 
		}
		
		Date sysDate = request.getComplainSubTime();
		double num = (sysDate.getTime() - waybillSign.getSignTime().getTime())/NumberConstants.NUMBER_1000/(double)NumberConstants.NUMBER_3600; //当前时间-签收时间
		//86400000 = DateUtils.convert("2013-09-23 00:00:00").getTime() - DateUtils.convert("2013-09-22 00:00:00").getTime();
		//超出72小时，不予变更签收结果
		if(num>NumberConstants.NUMBER_72){
			//记录日志
			LOGGER.info("变更失败,运单签收时间超过72小时");
			//解锁
			businessLockService.unlock(mutexElement);
			//记录变更结果
			response.setChangeResult(FAILED);
			//记录变更结果信息
			response.setChangeResultInfo("运单签收时间超过72小时");
			return response;
		}
		//全部签收
		if(SignConstants.SIGN_STATUS_ALL.equals(waybillSign.getSignStatus())){
					//签收情况正常
			if(SignConstants.NORMAL_SIGN.equals(waybillSign.getSignSituation())){
				return processNormalSign(request, response, mutexElement, waybillEntity, waybillSign);
	
			}else{
				LOGGER.info("变更失败，运单签收情况为异常签");//记录日志
				businessLockService.unlock(mutexElement);
				//封装返回结果
				response.setChangeResult(FAILED);
				//封装返回结果
				response.setChangeResultInfo("运单签收情况为异常签收");
				return response;
			}
		}else{
			LOGGER.info("变更失败，运单未完全签收");//记录日志
			businessLockService.unlock(mutexElement);
			//封装返回结果
			response.setChangeResult(FAILED);
			//封装返回结果
			response.setChangeResultInfo("运单未完全签收");
			return response;
		}
	}

    /**
     * @param request
     * @param response
     * @param mutexElement
     * @param waybillEntity
     * @param waybillSign
     * @return
     */
    private Object processNormalSign(DealComplainChangeFossRequest request,
            DealComplainChangeFossResponse response, MutexElement mutexElement,
            WaybillEntity waybillEntity, WaybillSignResultEntity waybillSign) {
        ActualFreightEntity actual;
        //查询条件赋值
        SignRfcEntity checkApprovalin = new SignRfcEntity();
        checkApprovalin.setWaybillNo(request.getWaybillNo());
        checkApprovalin.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
        //判断手动提交的变更签收结果(审核中)
        if(signChangeService.isRfc(checkApprovalin)){
        	LOGGER.info("变更失败,运单有变更未审批");
        	businessLockService.unlock(mutexElement);
        	response.setChangeResult(FAILED);
        	//记录变更结果信息
        	response.setChangeResultInfo("运单有变更未审批");
        	return response;
        }
        //判断运输性质 (空运、偏线、落地配只有一条签收记录；分批签收需查询到达联签收)
        if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode()) || 
        				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode()) || 
        				checkWaybillIsLdp(waybillEntity)){    //运输方式(空运、偏线、落地配) 
        					    // 自动变更，起草变更一步到位
        	LOGGER.info("saveChangeAirliftPartialLine begin......");
        	//获得变更签收实体
        	SignRfcEntity record = new SignRfcEntity();
        	//获得变更明细数据项
        	List<SignRfcChangeDetailEntity> changeDetailentityList = new ArrayList<SignRfcChangeDetailEntity>();
        	try{
        		//变更记录主表，明细表实体赋值
        		setSignRfcAndChangeDetailR(record,waybillEntity,waybillSign,request,
        				changeDetailentityList,CHANGE_DETAIL_RFC_TYPE_R,CHANGE_DETAIL_SITUATION_R);
        		// 更新签收结果状态(sign_result)
        		updateSignResult(record,changeDetailentityList);
        	}catch(BusinessException e){
        		//记录日志
        		LOGGER.info("变更失败,"+e.getMessage());
        		//解锁
        		businessLockService.unlock(mutexElement);
        		//封装返回结果
        		response.setChangeResult(FAILED);
        		//封装返回结果
        		response.setChangeResultInfo(e.getMessage());
        		return response;
        	}
        	
        		
        }else{  //判断到达联签收表
        	// 获得到达联LIST,通过运单号
        	ArriveSheetDto ae = new ArriveSheetDto();
        	// 运单号
        	ae.setWaybillNo(waybillEntity.getWaybillNo());
        	// 已签收确认
        	List<String> arriveSheetStatusList = new ArrayList<String>();
        	arriveSheetStatusList.add(ArriveSheetConstants.STATUS_SIGN);
        	// 已签收状态
        	ae.setArriveSheetStatus(arriveSheetStatusList);
        	// 激活状态
        	ae.setActive(FossConstants.ACTIVE);
        	// 非销毁状态
        	ae.setDestroyed(FossConstants.NO);
        	//不在审批中
        	ae.setIsRfcing(FossConstants.NO);
        	//获得运单最新一次的有效到达联信息
        	ArriveSheetEntity arriveSheet = arriveSheetManngerService.queryArriveSheetBySignTimeDesc(ae);
        	if(null==arriveSheet){
        		LOGGER.info("变更失败，未查询到该运单的到达联信息");//记录日志
        		businessLockService.unlock(mutexElement);
        		//封装返回结果
        		response.setChangeResult(FAILED);
        		//封装返回结果
        		response.setChangeResultInfo("未查询到该运单的到达联信息");
        		return response;
        	}
        		
                  /* 错误的情况考虑（整车都有到达联，可能无货签表信息），这里考虑整车可能无到达联了，去掉					
        			if(arriveSheet==null){  //运单无到达联(空运偏线、落地配之外)
        				//获得变更签收实体
        				SignRfcEntity record = new SignRfcEntity();
        				//获得变更明细数据项
        				List<SignRfcChangeDetailEntity> changeDetailentityList = new ArrayList<SignRfcChangeDetailEntity>();
        				//变更记录主表，明细表实体赋值
        				setSignRfcAndChangeDetailR(record,waybillEntity,waybillSign,dto,
        								changeDetailentityList,changeDetailRfcTypeR,changeDetailSituationR);
        				// 更新签收结果状态(sign_result)
        				updateSignResult(record,changeDetailentityList);
        				//调用接口成功返回值
        				//return new ChangeSignResultDto(SUCCESS,null);
        			}else{
        			*/
        		//根据运单查询出到达联列表，按时间排序取最后一条，修改到达联签收状态（整票/分批）
        		SignRfcEntity record = new SignRfcEntity();
        				//获得变更明细数据项
        		List<SignRfcChangeDetailEntity> changeDetailentityList = new ArrayList<SignRfcChangeDetailEntity>();
        		//设置起草部门 (默认到达部门)
        		record.setDraftOrgName(waybillEntity.getLastLoadOrgName());
        		// 起草部门code
        		record.setDraftOrgCode(waybillEntity.getLastLoadOrgCode());
        				
        		//设置变更类型 --到达联联动修改签收结果主表，变更记录添加到达联即可
        		record.settSrvArrivesheetId(arriveSheet.getId());
        		setRecordPropertyArr(record,request);//添加变更实体数据
        		//添加变更 -sign_rfc表，状态为审核通过
        		signRfcDao.insertSelective(record);
        		SignRfcChangeDetailEntity detailEntity1 = new SignRfcChangeDetailEntity();//签收情况
        		try{
        			//给变更详情实体赋值
        			setChangeDetailSituation(detailEntity1,waybillSign,request,CHANGE_DETAIL_SITUATION_A);
        		}catch(BusinessException e){
        			//记录日志
        			LOGGER.info("变更失败,"+e.getMessage());
        			//结果参数赋值
        			response.setChangeResult(FAILED);
        			//返回错误信息
        			response.setChangeResultInfo(e.getMessage());
        			return response;
        		}
        		SignRfcChangeDetailEntity detailEntity2 = new SignRfcChangeDetailEntity();//签收备注
        		//给变更详情实体赋值
        		setChangeDetailNote(detailEntity2,waybillSign,request);
        		//如果投诉为内物短少，上报QMS内物短少差错
        		if(ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT.equals(detailEntity1.getAfterRfcinfo())){
        			//保存QMS内物短少差错信息(JOB上报)
        			this.saveRecordShortErrorInfo(waybillSign);
        		}else if(!ProductCodeUtils.isExpress(waybillEntity.getProductCode())){
        			//不为快递，不为内物短少，上报异常线上划责差错
        			//上报数据
        			RecordUnnormalSignWaybillDto unnormalDto = new RecordUnnormalSignWaybillDto();
        			//签收情况
        			unnormalDto.setSignSituation(detailEntity1.getAfterRfcinfo());
        			//签收备注
        			unnormalDto.setSignNote(detailEntity2.getAfterRfcinfo());
        			this.saveRecordUnnormalErrorInfo(unnormalDto, waybillSign);
        		}
        		changeDetailentityList.add(detailEntity1);
        		changeDetailentityList.add(detailEntity2);
        		if (CollectionUtils.isNotEmpty(changeDetailentityList)) {
        			//遍历变更到达联详细集合
        			for(SignRfcChangeDetailEntity changeDetailEntity:changeDetailentityList){
        				//设置变更主表id (关联id)
        				changeDetailEntity.settSrvSignRfcId(record.getId());
        				//变更类型
        				changeDetailEntity.setRfcType(CHANGE_DETAIL_RFC_TYPE_A);
        				//保存变更信息字段
        				signRfcChangeDetailDao.insertSelective(changeDetailEntity);
        			}
        		}
        		try{
        			//变更到达联签收记录，然后更新主表签收记录(联动变更签收结果表的签收记录)
        			updateArriveSheetResult(record, changeDetailentityList);
        		}catch(BusinessException e){
        			//记录日志
        			LOGGER.info("变更失败,"+e.getMessage());
        			//解锁
        			businessLockService.unlock(mutexElement);
        			//封装返回结果
        			response.setChangeResult(FAILED);
        			//封装返回结果
        			response.setChangeResultInfo(e.getMessage());
        			return response;
        		}
        		//将对应到达联下的流水号 更新为异常签收
        		SignDetailEntity detail = new SignDetailEntity();
        		detail.setArrivesheetNo(arriveSheet.getArrivesheetNo());
        		//查询运单到达连是否有货签表记录
        		List<SignDetailEntity> entityList = signDetailService.querySignDetailByParams(detail);
        		if(CollectionUtils.isNotEmpty(entityList)){ //查询是否有货签表记录存在，有才更新
        			//设置更新值 --流水号的签收详情 -- 异常签收
        			detail.setSituation(SignConstants.UNNORMAL_SIGN);
        			//调用更新签收详情接口
        			signDetailService.updateByArrivesheetNo(detail);
        		}
         }
         //更新实际承运表 记录投诉记录
        actual = new ActualFreightEntity();
        actual.setWaybillNo(request.getWaybillNo());
        actual.setComplainStatus(SignConstants.YES);
        actualFreightService.updateByWaybillNo(actual,request.getWaybillNo());
        //调用接口成功返回值

        //记录日志
        LOGGER.info("投诉变更签收结果成功");
        //返回变更结构
        response.setChangeResult(SUCCESS);
        //返回错误信息
        response.setChangeResultInfo(null);
        //业务锁解锁
        businessLockService.unlock(mutexElement);
        return response;
    }

	/**
	 * 	检查运单是否为落地配
	 * @param waybillEntity
	 * @return boolean
	 * @author foss-chenjunying
	 * @date 2014-12-15 下午16:41:57
	 * */
	/**
	 * @param waybillEntity
	 * @return
	 */
	public boolean checkWaybillIsLdp(WaybillEntity waybillEntity){
		//空运、偏线、快递
		if( (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())
				&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode()))
				&& !waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
			return false;
		}
		//运单对应的提货网点是否为：落地配网点
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {
			OuterBranchExpressEntity ldpOrg = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
					waybillEntity.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
				if(ldpOrg == null){
					return false;
				}
		}
		return true;
	}
	/**
	 * 给变更签收实体赋值(空运/偏线/落地配变更)
	 * @author foss-chenjunying
	 * */
	private void setRecordProperty(SignRfcEntity entity, DealComplainChangeFossRequest request){
		//运单号
		entity.setWaybillNo(request.getWaybillNo());
		//变更类型
		entity.setRfcType(SignConstants.SIGN_RFC_TYPE_WAYBILL);
		//起草人
		entity.setDrafter("营运品质服务部");
		//起草人编码
		entity.setDrafterCode("营运品质服务部");
		//起草时间
		entity.setDraftTime(request.getComplainSubTime());
		//起草原因
		entity.setReason("投诉工单自动变更签收结果");
		//备注
		entity.setNotes("客户投诉工单处理编号："+request.getWorkOrderHandleNo()+"，引起系统自动产生变更签收结果。");
		//申请状态 (自动通过)
		entity.setStatus(SignConstants.SIGN_RFC_SIGN_PASSED);
		//操作人
		entity.setOperator("system");
		//操作人编码
		entity.setOperatorCode("system");
		//操作人部门
		entity.setOperateOrgName("system");
		//操作人部门code
		entity.setOperateOrgCode("system");
		//操作时间
		entity.setOperateTime(new Date());
		//变更单号
		entity.setRfcNo(signChangeService.getRecNoInfo(request.getWaybillNo()));
	}
	/**
	 * 给变更签收实体赋值(到达联变更)
	 * @author foss-chenjunying
	 * */
	private void setRecordPropertyArr(SignRfcEntity entity,DealComplainChangeFossRequest request){
		//运单号
		entity.setWaybillNo(request.getWaybillNo());
		//变更类型
		entity.setRfcType(SignConstants.SIGN_RFC_TYPE_ARRIVESHEET);
		//起草人
		entity.setDrafter("营运品质服务部");
		//起草人编码
		entity.setDrafterCode("营运品质服务部");
		//起草时间
		entity.setDraftTime(request.getComplainSubTime());
		//起草原因
		entity.setReason("投诉工单自动变更签收结果");
		//备注
		entity.setNotes("客户投诉工单处理编号："+request.getWorkOrderHandleNo()+"，引起系统自动产生变更签收结果。");
		//申请状态 (自动通过)
		entity.setStatus(SignConstants.SIGN_RFC_SIGN_PASSED);
		//操作人
		entity.setOperator("system");
		//操作人编码
		entity.setOperatorCode("system");
		//操作人部门
		entity.setOperateOrgName("system");
		//操作人部门code
		entity.setOperateOrgCode("system");
		//操作时间
		entity.setOperateTime(new Date());
		//变更单号
		entity.setRfcNo(signChangeService.getRecNoInfo(request.getWaybillNo()));
	}
	
	/**
	 * 1.变更记录主表，详细表-记录实体赋值；
	 * 2.插入数据库
	 * @author foss-chenjunying
	 * @param record 
	 *   签收变更记录主表实体
	 * @param waybillEntity 
	 * 	 运单信息实体--用于获取默认到达部门和到达部门编号
	 * @param waybillSign
	 *   运单签收结果信息实体 
	 * @param dto
	 *   crm传值参数 Dto（投诉工单详情）
	 * @param changeDetailentityList
	 *   签收变更详情实体集合
	 * @param changeDetailRfcType 
	 *   变更类型 -签收结果
	 * @param changeDetailSituation
	 * 	 变更项code-系统映射时使用
	 * */
	private void setSignRfcAndChangeDetailR(SignRfcEntity record,WaybillEntity waybillEntity,
			WaybillSignResultEntity waybillSign,DealComplainChangeFossRequest request,
			List<SignRfcChangeDetailEntity> changeDetailentityList,
			String changeDetailRfcType,String changeDetailSituation){
		//设置起草部门 (默认到达部门)
		record.setDraftOrgName(waybillEntity.getLastLoadOrgName());
		// 起草部门code
		record.setDraftOrgCode(waybillEntity.getLastLoadOrgCode());
		//变更运单签收结果ID
		record.settSrvWaybillSignResultId(waybillSign.getId());
		setRecordProperty(record,request);//添加变更实体数据
		//添加变更 -sign_rfc表，状态为审核通过
		signRfcDao.insertSelective(record);
		
		SignRfcChangeDetailEntity detailEntity1 = new SignRfcChangeDetailEntity();//签收情况
		//给变更详情实体赋值
		setChangeDetailSituation(detailEntity1,waybillSign,request,changeDetailSituation);
		
		SignRfcChangeDetailEntity detailEntity2 = new SignRfcChangeDetailEntity();//签收备注
		//给变更详情实体赋值
		setChangeDetailNote(detailEntity2,waybillSign,request);
		//如果投诉为内物短少，上报QMS内物短少差错
		if(ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT.equals(detailEntity1.getAfterRfcinfo())){
			//保存QMS内物短少差错信息(JOB上报)
			this.saveRecordShortErrorInfo(waybillSign);
		}else if(!ProductCodeUtils.isExpress(waybillEntity.getProductCode())){
			//不为快递，不为内物短少，上报异常线上划责差错
			//上报数据
			RecordUnnormalSignWaybillDto unnormalDto = new RecordUnnormalSignWaybillDto();
			//签收情况
			unnormalDto.setSignSituation(detailEntity1.getAfterRfcinfo());
			//签收备注
			unnormalDto.setSignNote(detailEntity2.getAfterRfcinfo());
			//上报
			this.saveRecordUnnormalErrorInfo(unnormalDto, waybillSign);
		}
		changeDetailentityList.add(detailEntity1);
		changeDetailentityList.add(detailEntity2);
		if (CollectionUtils.isNotEmpty(changeDetailentityList)) {
			//遍历变更到达联详细集合
			for(SignRfcChangeDetailEntity changeDetailEntity:changeDetailentityList){
				//设置变更主表id (关联id)
				changeDetailEntity.settSrvSignRfcId(record.getId());
				//变更类型
				changeDetailEntity.setRfcType(changeDetailRfcType);
				//保存变更信息字段
				signRfcChangeDetailDao.insertSelective(changeDetailEntity);
			}
		}
	}
	
	/**
	 * 给变更详情实体赋值(签收情况-waybill)
	 * @author foss-chunjunying
	 * @param detailEntity 
	 * @param tSrvSignRfcId
	 * 变更记录id
	 * @param rfcItems
	 * 变更项
	 * @param rfcItemsCode
	 * 变更项code
	 * @param beforeRfcinfo
	 * 变更前信息
	 * @param afterRfcinfo
	 * 变更后信息
	 * @param rfcType
	 * 变更类型
	 * */
	private void setChangeDetailSituation(SignRfcChangeDetailEntity detailEntity,
			WaybillSignResultEntity waybillSign,DealComplainChangeFossRequest request,
			String changeDetailSituation){
		//变更项
		detailEntity.setRfcItems("签收情况");
		//变更项code 
		detailEntity.setRfcItemsCode(changeDetailSituation);
		//变更前信息
		detailEntity.setBeforeRfcinfo(waybillSign.getSignSituation());
		//变更后信息,细分异常类型
		if(StringUtils.contains(request.getComplaintBusinessType(), GOODS_DAMP)){
			//异常-潮湿
			detailEntity.setAfterRfcinfo(ArriveSheetConstants.SITUATION_UNNORMAL_DAMP);
		}else if(StringUtils.contains(request.getComplaintBusinessType(), GOODS_POLLUTION)){
			//异常-污染
			detailEntity.setAfterRfcinfo(ArriveSheetConstants.SITUATION_UNNORMAL_POLLUTION);
		}else if(StringUtils.contains(request.getComplaintBusinessType(), GOODS_BREAK)){
			//异常-破损
			detailEntity.setAfterRfcinfo(ArriveSheetConstants.SITUATION_UNNORMAL_BREAK);
		}else if(StringUtils.contains(request.getComplaintBusinessType(), GOODS_SHORT)){
			//异常-内物短少
			detailEntity.setAfterRfcinfo(ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT);
		}else{
			throw new BusinessException("无相应异常类型变更");
		}
	}
	
	/**
	 * 给变更详情实体赋值(备注)
	 * @author foss-chunjunying
	 * @param detailEntity 
	 * @param tSrvSignRfcId
	 * 变更记录id
	 * @param rfcItems
	 * 变更项
	 * @param rfcItemsCode
	 * 变更项code
	 * @param beforeRfcinfo
	 * 变更前信息
	 * @param afterRfcinfo
	 * 变更后信息
	 * @param rfcType
	 * 变更类型
	 * */
	private void setChangeDetailNote(SignRfcChangeDetailEntity detailEntity,WaybillSignResultEntity waybillSign,
			DealComplainChangeFossRequest request){
		//变更项
		detailEntity.setRfcItems("签收备注");
		//变更项code
		detailEntity.setRfcItemsCode(SignConstants.SIGN_NOTE);
		//变更前信息
		detailEntity.setBeforeRfcinfo(waybillSign.getSignNote());
		//变更后信息
		detailEntity.setAfterRfcinfo("客户投诉工单处理编号："+request.getWorkOrderHandleNo()+"，引起系统自动产生变更签收结果。");
	}
	/**
	 * 对需要更新的明细字段进行赋值<br />
	 * @author
	 * @version
	 * @param scdeList
	 * @param cls
	 * @param tSrvSignRfcId;
	 * 变更ID
	 * @param rfcItems;
	 * 变更项NAME
	 * @param rfcItemsCode;
	 * 变更项CODE
	 * @param beforeRfcinfo;
	 * 变更前
	 * @param afterRfcinfo;
	 * 变更后
	 * @param rfcType;
	 * 变更类型(0:付款,1:到达联,2:运单签收结果)
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object updateApplyData(List<SignRfcChangeDetailEntity> scdeList,
			Object oc) {
		if (CollectionUtils.isEmpty(scdeList)) {
			return oc;
		}
		// 对象类型
		Class cls = oc.getClass();
		// 得到对象中的字段
		Field[] fields = cls.getDeclaredFields();
		// 字段名
		String fieldName;
		// 字段类型
		String fieldType;
		// 字段首字母大写
		String firstLetter;
		// 字段的Set方法
		String setMethodName;
		// 字段值
		String value;
		try {
			// 针对对象的每一个字段循环
			for (Field field : fields) {
				// 字段NAME
				fieldName = field.getName();
				// 字段TYPE
				fieldType = field.getType().getSimpleName();
				// 字段首字母
				firstLetter = fieldName.substring(0, 1).toUpperCase();
				// SET NAME
				setMethodName = "set" + firstLetter + fieldName.substring(1);
				// 循环
				for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : scdeList) {
					// 字段名相等
					if (fieldName.equals(signRfcChangeDetailEntity
							.getRfcItemsCode())) {
						// GET METHOD
						Method fieldSetMet = cls.getMethod(setMethodName,
								new Class[] { field.getType() });
						// VALUE
						value = signRfcChangeDetailEntity.getAfterRfcinfo();
						setFieldValue(oc, fieldType, value, fieldSetMet);
					}
				}
			}
			return oc;
			// 技术异常
		} catch (Exception e) {
			LOGGER.info("error", e);
			throw new BusinessException("技术异常", e);
		}
	}

    /**
     * @param obj
     * @param fieldType
     * @param value
     * @param fieldSetMet
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NumberFormatException
     */
    private void setFieldValue(Object obj, String fieldType, String value,
            Method fieldSetMet) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NumberFormatException {
        // TYPE IS String
        if ("String".equals(fieldType)) {
        	fieldSetMet.invoke(obj, value);
        	// type is date
        } else if ("Date".equals(fieldType)) {
        	Date temp = parseDate(value);
        	fieldSetMet.invoke(obj, temp);
        	// type is integer or int
        } else if ("Integer".equals(fieldType)
        		|| "int".equals(fieldType)) {
        	Integer intval;
        	if (StringUtils.isEmpty(value)) {
        		intval = 0;
        	} else {
        		intval = Integer.parseInt(value);
        	}
        	fieldSetMet.invoke(obj, intval);
        	// type is Long or long
        } else if ("Long".equalsIgnoreCase(fieldType)) {
        	Long temp;
        	if (StringUtils.isEmpty(value)) {
        		temp = 0L;
        	} else {
        		temp = Long.parseLong(value);
        	}
        	fieldSetMet.invoke(obj, temp);
        	// type is double or Double
        } else if ("Double".equalsIgnoreCase(fieldType)) {
        	Double temp;
        	if (StringUtils.isEmpty(value)) {
        		temp = 0D;
        	} else {
        		temp = Double.parseDouble(value);
        	}
        	fieldSetMet.invoke(obj, temp);
        	// type is bigdecimal
        } else if ("BigDecimal".equalsIgnoreCase(fieldType)) {
        	BigDecimal temp;
        	if (StringUtils.isEmpty(value)) {
        		temp = BigDecimal.ZERO;
        	} else {
        		temp = new BigDecimal(value);
        	}
        	fieldSetMet.invoke(obj, temp);
        } else {
        	// other type nothing to work
        	LOGGER.info("not supper type" + fieldType);
        }
    }
    
	/**
	 * 
	 * <p>格式化时间<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param datestr
	 * @return
	 * Date
	 */
	private Date parseDate(String datestr) {
		// date string is null do nothing work
		if (StringUtils.isEmpty(datestr)) {
			return null;
		}
		try {
			String fmtstr = null;
			// format to yyyy-MM-dd HH:mm:ss
			if (datestr.indexOf(':') > 0) {
				fmtstr = "yyyy-MM-dd HH:mm:ss";
			} else {
				// format to yyyy-MM-dd
				fmtstr = "yyyy-MM-dd";
			}
			// create SimpleDateFormat
			SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);
			// get string
			return sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 更新签收结果表签收结果
	 * @param record
	 *   变更签收记录表实体，用于获取其签收结果Id
	 * @param changeDetailentityList
	 * 	  变更详情集合，用于给签收结果表中的相应更改字段赋值
	 * */
	private void updateSignResult(SignRfcEntity record,List<SignRfcChangeDetailEntity> changeDetailentityList) {
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置变更ID
		entity.setId(record.gettSrvWaybillSignResultId());
		// 变更前的签收结果
		WaybillSignResultEntity oldwaybillSignResultEntity = waybillSignResultService
				.queryWaybillSignResultById(entity.getId());
		if(null == oldwaybillSignResultEntity){
			LOGGER.info("未查询到该运单的签收记录");//记录日志
			throw new BusinessException("未查询到该运单的签收记录");//返回错误信息
		}
		//复制信息
		BeanUtils.copyProperties(oldwaybillSignResultEntity,entity);
		// 获得需要修改对象
		entity = (WaybillSignResultEntity) updateApplyData(changeDetailentityList, entity);
		try {
			// 调用正常签收变更为异常签收接口
			waybillSignResultService.changeExceptionSignResult(oldwaybillSignResultEntity,entity);
			//捕获异常
		}catch (BusinessException ex) {
			//businessLockService.unlock(mutexElement);//解锁
			LOGGER.info("接口调用异常，请核对", ex);
			//抛出异常
			throw new BusinessException(ex.getErrorCode(), ex);
		}
	}
	
	/**
	 * 更新到达联表签收结果 (会联动变更签收结果表的签收记录)
	 * @param record
	 *   变更签收记录表实体，用于获取其签收结果Id
	 * @param changeDetailentityList
	 * 	  变更详情集合，用于给签收结果表中的相应更改字段赋值
	 **/
	private void updateArriveSheetResult(SignRfcEntity record,List<SignRfcChangeDetailEntity> changeDetailentityList) {
		ArriveSheetEntity entity = new ArriveSheetEntity();
		//设置变更ID
		entity.setId(record.gettSrvArrivesheetId());
		//变更前的签收结果
		ArriveSheetEntity oldarriveSheet = arrivesheetDao
				.queryArriveSheetById(record.gettSrvArrivesheetId());
		//查询到达联签收记录
		if(null == oldarriveSheet){
			LOGGER.info("未查询到该运单的签收记录");//记录日志
			throw new BusinessException("未查询到该运单的签收记录");//抛出异常
		}
		//复制信息
		BeanUtils.copyProperties(oldarriveSheet,entity);
		//获得需要修改对象
		entity = (ArriveSheetEntity) updateApplyData(changeDetailentityList, entity);
		try {
			// 调用正常签收变更为异常签收接口
			arriveSheetManngerService.changeArriveSheet(oldarriveSheet, entity);
			//捕获异常
		}catch (BusinessException ex) {
			//businessLockService.unlock(mutexElement);//解锁
			LOGGER.info("接口调用异常，请核对", ex);
			//抛出异常
			throw new BusinessException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * 验证传入的参数 
	 * @author 231438 chenjunying
	 * @param  DealComplainChangeFossRequest request
	 * @return 
	 */
	private void validateParams(DealComplainChangeFossRequest request) {
		if(StringUtils.isEmpty(request.getWaybillNo())){
			//传入的运单号为空
			throw new BusinessException("传入的运单号为空");
		}
		if(StringUtils.isEmpty(request.getWorkOrderHandleNo())){
			//投诉工单处理编号为空
			throw new BusinessException("投诉工单处理编号为空");
		}
		if(StringUtils.isEmpty(request.getComplaintBusinessType())){
			//投诉业务类型为空
			throw new BusinessException("投诉业务类型为空");
		}
		if(null ==  request.getComplainSubTime()){
			//投诉上报时间为空
			throw new BusinessException("投诉上报时间为空");
		}
		if(StringUtils.isEmpty(request.getBussItem())){
			//参数：业务项为空
			throw new BusinessException("参数：业务项为空");
		}
		if(StringUtils.isEmpty(request.getBussScope())){
			//参数：业务范围为空
			throw new BusinessException("参数：业务项范围为空");
		}
	}
	
	@Override
	public Object errorHandler(Object arg0) throws ESBBusinessException {
		//记录异常
		LOGGER.info("处理工作流结果报错！");
		return null;
	}
	
  	/**
      * 保存QMS内物短少差错上报信息
	 */
	public void saveRecordShortErrorInfo(WaybillSignResultEntity waybillSign){
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********start");
		if(waybillSign != null){
			RecordErrorWaybillDto recordErrorWaybillDto = new RecordErrorWaybillDto();
			//主键
			recordErrorWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorWaybillDto.setActive(FossConstants.YES);
			//运单号
			recordErrorWaybillDto.setWaybillNo(waybillSign.getWaybillNo());
			//短少量
			recordErrorWaybillDto.setAlittleShort(null);
			//外包装是否完好
			recordErrorWaybillDto.setPackingResult(null);
			//创建时间
			recordErrorWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorWaybillDto.setModifyTime(new Date());
			//上报人名字
			recordErrorWaybillDto.setOperateName(waybillSign.getCreator());
			//上报人工号
			recordErrorWaybillDto.setOperateCode(waybillSign.getCreatorCode());
			//上报人所在部门编码
			recordErrorWaybillDto.setOperateOrgCode(waybillSign.getCreateOrgCode());
			//上报人所在部门名字
			recordErrorWaybillDto.setOperateOrgName(waybillSign.getCreateOrgName());
			//流水号
			recordErrorWaybillDto.setSerialNo(null);
			recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
		}
		LOGGER.info("*************保存QMS内物短少差错信息***********end");
	}
	
	/**
	 * @author: foss-231434-bieyexiong
	 * @description: foss记录异常 上报QMS
	 * @date:2016年02月18日 下午15:37:21
	 */
	private void saveRecordUnnormalErrorInfo(RecordUnnormalSignWaybillDto unnormalDto,WaybillSignResultEntity waybillSign){
		if(waybillSign != null){
			LOGGER.info("*************保存QMS异常签收线上划责信息***********start");
			//主键id
			unnormalDto.setId(UUIDUtils.getUUID());
			//运单号
			unnormalDto.setWaybillNo(waybillSign.getWaybillNo());
			//异常货物件数
			unnormalDto.setUnnormalNumber(NumberConstants.ONE);
			//签收时间
			unnormalDto.setSignTime(waybillSign.getSignTime());
			//是否已上报(默认为Y，未上报)
			unnormalDto.setActive("Y");
			//创建时间
			unnormalDto.setCreateTime(new Date());
			//备注
			unnormalDto.setNote("未上报");

			//保存异常划责信息
			recordErrorWaybillDao.insertUnnormalEntity(unnormalDto);

			LOGGER.info("*************保存QMS异常签收线上划责信息***********end");
		}
	}

	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param waybillManagerService
	 * void
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		//运单管理服务
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param waybillSignResultDao
	 * void
	 * */	
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		//运单签收结果Dao
		this.waybillSignResultDao = waybillSignResultDao;
	}
	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param ldpAgencyDeptService
	 * void
	 * */	
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}	
	
	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param arrivesheetDao
	 * void
	 */
	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}
	
	/**
	 * <p>注入<br /></p>
	 * @author foss-waybillSignResultService
	 * @version
	 * @param waybillManagerService
	 * void
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * <p>注入<br /></p>
	 * @author foss-waybillSignResultService
	 * @version
	 * @param waybillManagerService
	 * void
	 */
	public void setSignChangeService(
			ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param signRfcDao
	 * void
	 */
	public void setSignRfcDao(ISignRfcDao signRfcDao) {
		this.signRfcDao = signRfcDao;
	}
	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param actualFreightDao
	 * void
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	} */
	/**
	 * <p>注入<br /></p>
	 * @author foss-chenjunying
	 * @version
	 * @param businessLockService
	 * void
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/**
	 * <p>注入signDetailService</p>
	 * @author foss-chenjunying
	 * @version
	 * @param signDetailService
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	/**
	 * <p>注入signRfcChangeDetailDao</p>
	 * @author foss-chenjunying
	 * @version
	 * @param signRfcChangeDetailDao
	 * void
	 */
	public void setSignRfcChangeDetailDao(
			ISignRfcChangeDetailDao signRfcChangeDetailDao) {
		this.signRfcChangeDetailDao = signRfcChangeDetailDao;
	}
	/**
	 * <p>注入arriveSheetManngerService</p>
	 * @author foss-chenjunying
	 * @version
	 * @param arriveSheetManngerService
	 * void
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * <p>注入arriveSheetManngerService</p>
	 * @author foss-chenjunying
	 * @version
	 * @param arriveSheetManngerService
	 * void
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setRecordErrorWaybillDao(
			IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}
	
}
