package com.deppon.foss.module.pickup.predeliver.server.process;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProductDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IAbandonGoodsApplicationQmsService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWorkFlowService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsQmsRequest;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsQmsWaybill;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.WorkFlowException;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.PickupWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * FOSS对接QMS，弃货应用服务（弃货运单信息查询、弃货签收）
 * @author 231434-FOSS-bieyexiong
 * @date 2015-5-18 上午08:59:35
 */
public class AbandonGoodsApplicationQmsService implements IAbandonGoodsApplicationQmsService{
	
	/**
	 * 日志
	 */
	private final static Logger LOGGER = 
			LoggerFactory.getLogger(AbandonGoodsApplicationQmsService.class);
	
	/**
	 * 数字0
	 */
	private final static int ZERO = 0;
	
	/**
	 * 数字1
	 */
	private final static int ONE = 1;
	
	/**
	 * 返回值（成功）
	 */
	private final static String SUCCESS = "1";
	
	/**
	 * 返回值（失败:运单信息查询失败）
	 */
	private final static String FAILURE = "0";
	
	/**
	 * 返回值（弃货签收失败:客户已提货，弃货工作流无效化）
	 */
	private final static String FAILURE_INVALID = "0";
	
	/**
	 * 返回值（弃货签收失败：qms数据回滚，弃货工作流可重新点击审核）
	 */
	private final static String FAILURE_BACK = "2";
	
	/**
	 * qms系统
	 */
	private final static String QMS = "QMS";
	
	/**
	 * 未签收（签收情况）
	 */
	private final static String STATUS = "未签收";
	
	/**
	 * 签收状态词条
	 */
	private final static String PKP_SIGN_STATUS = "PKP_SIGN_STATUS";
	
	/**
	 * 业务互斥服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 运单服务
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 运单签收结果服务
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 * 运单签收结果dao
	 */
	private IWaybillSignResultDao waybillSignResultDao;
	
	/**
	 * 员工信息服务
	 */
	private IEmployeeService  employeeService;
	
	/**
	 * 组织信息服务
	 */
	 private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	 
	 /**
	  * 运单状态
	  */
	 private IActualFreightService actualFreightService;
	 
	 /**
	  * 工作流服务
	  */
	 private IWorkFlowService workFlowService;
	 
	 /**
	  * 快递业务
	  */
	 private IWaybillExpressService waybillExpressService;
	 
	 /**
	  * 部门复杂service
	  */
	 private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	 
	 /**
	  * 运单库存dao
	  */
	 private IWaybillStockDao waybillStockDao;
	 
	 /**
	  * 产品DAO
	  */
	 private IProductDao bseProductDao;
	 
	 /**
	  * 数据字典服务
	  */
	 private IDataDictionaryValueService dataDictionaryValueService;
	 /**
	  * 货款结清(合伙人代收货款冲销)服务
	  */
	 private IRepaymentService repaymentService;
	/**
	 *  营业部 Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * waybillService 接口
	 */
	private IWaybillService waybillService;
	
	 /**
	  * QMS系统对接FOSS弃货签收
	  * @author 231434-FOSS-bieyexiong
 	  * @date 2015-5-18 上午09:26:51
	  */
	@Transactional
	public String signAbandonGoods(String requestJson){
		LOGGER.info("************弃货签收开始************");
		//返回值
		String response = "";
		
		//解析请求参数
		AbandonGoodsQmsRequest req = 
				JSONObject.parseObject(requestJson,AbandonGoodsQmsRequest.class);
		//若有请求参数为空，则无法签收
		if(req == null || StringUtils.isBlank(req.getWaybillNo()) 
				|| StringUtils.isBlank(req.getCurrentEmpCode())
				|| StringUtils.isBlank(req.getCurrentDeptCode())){
			//日志
			LOGGER.info("签收失败：请求参数为空！");
			//获取返回参数
			response = this.getResponseJson(null,FAILURE_BACK,"签收失败：请求参数为空！");
			LOGGER.info("************弃货签收结束************");
			return response;
		}
		MutexElement mutexElement = new MutexElement
				(req.getWaybillNo(),"运单编号",MutexElementType.RFC_SIGN);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){
			//如果没有得到锁
			LOGGER.info("签收失败：当前运单操作中，请稍后再试！");
			//记录日志
			response = this.getResponseJson(null,FAILURE_BACK,"签收失败：当前运单操作中，请稍后再试");
			LOGGER.info("************弃货签收结束************");
			return response;
		}
		//查询运单信息
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(req.getWaybillNo());
		if(waybill == null){
			//日志
			LOGGER.info("签收失败：该运单号无效！");
			//获取返回参数(运单号无效)
			response = this.getResponseJson(null,FAILURE_INVALID,"签收失败：该运单号无效！");
			LOGGER.info("************弃货签收结束************");
			//解锁
			businessLockService.unlock(mutexElement);
			return response;
		}
		//运单签收结果查询条件
		WaybillSignResultEntity signResultEntity = new WaybillSignResultEntity();
		signResultEntity.setWaybillNo(req.getWaybillNo());
		signResultEntity.setActive(FossConstants.YES);
		//运单签收结果
		signResultEntity = waybillSignResultDao.queryWaybillSignResult(signResultEntity);
		//如果有签收记录，并且是全部签收
		if(signResultEntity != null 
				&& SignConstants.SIGN_STATUS_ALL.equals(signResultEntity.getSignStatus())){
			//日志
			LOGGER.info("签收失败：该运单已全部签收！");
			response = this.getResponseJson(null,FAILURE_INVALID,"签收失败：该运单已全部签收！");
			LOGGER.info("************弃货签收结束************");
			//解锁
			businessLockService.unlock(mutexElement);
			return response;
		}
		try {
			//获取当前人员、部门信息
			CurrentInfo currentInfo = 
					this.getCurrentInfo(req.getCurrentEmpCode(),req.getCurrentDeptCode());
			//原单号
			boolean isSendInvoiceInfoOrg=false;
			WaybillEntity waybillOrg=null;
			ActualFreightEntity afOrg=null;
			
			//快递
			if(waybillExpressService.onlineDetermineIsExpressByProductCode
								(waybill.getProductCode(),waybill.getBillTime())){
				//查询是否是快递返单的原单号
				WaybillExpressEntity waybillExpressEntity = 
						waybillExpressService.queryWaybillByOriginalWaybillNo
							(req.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				//如果是快递返单的原单号，不让签收
				if(waybillExpressEntity != null){
					//日志
					LOGGER.info("签收失败：该运单已经返货新开单,请使用新单号进行操作!");
					throw new BusinessException("该运单已经返货新开单,请使用新单号进行操作!","");
				}
				//弃货返货签收
				List<WaybillExpressEntity> WaybillExpressEntityList = waybillExpressService.queryWaybillListByWaybillNo(req.getWaybillNo());
				WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(WaybillExpressEntityList) ? WaybillExpressEntityList.get(0) : null;
				if(newWaybillNoEntity!=null && StringUtils.isNotEmpty(newWaybillNoEntity.getOriginalWaybillNo())){//是新单号
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("waybillNo", newWaybillNoEntity.getOriginalWaybillNo());//返货原运单号
					params.put("active", FossConstants.ACTIVE);
					//根据运单号，active查询返货原单是否是子母件
					TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
					try{
						//不是子母件
						if(StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.NO)){
							//如果是快递返单的新单号，则将原单号签收
							if(newWaybillNoEntity != null && 
									StringUtils.isNotBlank(newWaybillNoEntity.getOriginalWaybillNo())){
								this.dealOriginalWaybillNo(newWaybillNoEntity.getOriginalWaybillNo(),
										currentInfo,waybillOrg,afOrg,isSendInvoiceInfoOrg);
							}
						}else{//是子母件
							for(WaybillExpressEntity expressEntity : WaybillExpressEntityList){
								//如果是快递返单的新单号，则将原单号签收
								if(expressEntity != null && 
										StringUtils.isNotBlank(expressEntity.getOriginalWaybillNo())){
									this.dealOriginalWaybillNo(expressEntity.getOriginalWaybillNo(),
											currentInfo,waybillOrg,afOrg,isSendInvoiceInfoOrg);
								}
							}
						}
					}catch(BusinessException e){
						LOGGER.error("签收失败：" + e.getMessage());
						throw new BusinessException("返货原单号签收异常:" + e.getErrorCode() , "");
					}
				}
			}
			
			// 中转出库
			workFlowService.outStock(req.getWaybillNo(), currentInfo); 
			//【合伙人的弃货签收，在workFlowService.outStock中调用开单接口进行运单终止-239284】

			//合伙人的弃货签收，在workFlowService.outStock中调用开单接口进行运单终止-239284-----------start-----------------------------------------------------------------------------------
			//到达部门
			SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(waybill.getLastLoadOrgCode());
			//出发部门
			SaleDepartmentEntity startSaleDept = saleDepartmentService.querySaleDepartmentInfoByCode(waybill.getReceiveOrgCode());
			//配置参数-合伙人结清4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人结清流程
			WaybillEntity entity = waybillManagerService.queryPartWaybillByNo(req.getWaybillNo());
			if (null == entity) {
				throw new WorkFlowException("运单不存在请核实!");
			}
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(req.getWaybillNo());
			if (null == actual) {
				throw new WorkFlowException("运单实际承运信息不存在请核实!");
			}
			
			String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
			if (StringUtils.isNotBlank(configString)) {
			    	try {
			    		SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
						long intTime = sdf.parse(configString).getTime();
						long destTime = entity.getBillTime().getTime();
						LOGGER.info("合伙人弃货签收===运单号：" +actual.getWaybillNo() + "开单日期：" + sdf.format(entity.getBillTime()) +  "，切割日期:" + configString.trim());
						//如果开单日期大于初始化日期，则走合伙人结清流程
						if (destTime >= intTime) {
							LOGGER.info("合伙人弃货参数===运单号：" +actual.getWaybillNo() + "，类型-" + PickupWaybillConstants.DERELICT_GOODS + "，出发部门:" + entity.getReceiveOrgCode() + ", 到达部门:" + entity.getLastLoadOrgCode() +
									"，当前部门:" + currentInfo.getCurrentDeptCode());
							// 合伙人丢货签收-通知PTP干掉流水日志-239284-异步接口---start
							if (null != saleDept && null != startSaleDept) {
								if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept()) || FossConstants.YES.equals(startSaleDept.getIsLeagueSaleDept())) {
									LOGGER.info("合伙人弃货参数===waybillService.syncLostGoodsToPtp运单号：" + actual.getWaybillNo() + ",  类型：" + PickupWaybillConstants.DERELICT_GOODS);
									// PTP作废流水-红冲金额-调结算红冲-异步接口-黄伟
									waybillService.syncLostGoodsToPtp(actual.getWaybillNo(), PickupWaybillConstants.DERELICT_GOODS);
								}
							} /*else {
								throw new WorkFlowException("到达部门或出发部门(合伙人)组织信息不存在！");
							}*/
							//modify by 353654
						}
					} catch (ParseException e) {
						
						throw new WorkFlowException("合伙人弃货初始化日期开关解析错误！");
					}
			} 
			//合伙人的弃货签收，在workFlowService.outStock中调用开单接口进行运单终止-239284-----------end-----------------------------------------------------------------------------------
			
			//根据运单号，查询运单状态
			ActualFreightEntity actualFreightEntity = 
					actualFreightService.queryByWaybillNo(req.getWaybillNo());
			//是否将发票信息传给发票系统
			boolean isSendInvoiceInfo = 
					waybillSignResultService.isNeedSendInvoiceInfo(waybill,actualFreightEntity);
			//新单号
			if(isSendInvoiceInfo){
				//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(waybill,actualFreightEntity);
			}
			if(isSendInvoiceInfoOrg){
				//如果原单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(waybillOrg,afOrg);
			}
			// 更新ActualFreight表中的结清状态为已结清
			workFlowService.updateActualFreight(actualFreightEntity);
			// 弃货签收接口
			waybillSignResultService.addAbandonGoodsSign(req.getWaybillNo(), currentInfo);
			// 需要调用业务完结接口
			workFlowService.overWaybillTransaction(req.getWaybillNo());
			
			LOGGER.info("签收成功！");
			response = this.getResponseJson(null,SUCCESS,"签收成功！");
			return response;
		} catch (BusinessException e) {
			LOGGER.error("签收失败：" + e.getMessage());
			throw e;
		} finally{
			LOGGER.info("************弃货签收结束************");
			//解锁
			businessLockService.unlock(mutexElement);
		}
	}
	
	/**
	 * QMS对接FOSS，根据运单号，获取异常货运单信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-22 下午15:02:15
	 */
	public String queryUnnormalWaybill(String requestJson){
		LOGGER.info("************运单信息查询开始************");
		//返回值
		String response = "";
		
		//解析请求参数
		AbandonGoodsQmsRequest req = 
				JSONObject.parseObject(requestJson,AbandonGoodsQmsRequest.class);
		if(req == null || StringUtils.isBlank(req.getWaybillNo())){
			//日志
			LOGGER.info("查询失败：请求参数为空！");
			//获取返回参数
			response = this.getResponseJson(req.getWaybillNo(),FAILURE,"查询失败：请求参数为空！");
			LOGGER.info("************运单信息查询结束************");
			return response;
		}
		AbandonGoodsQmsWaybill abandonWaybill = new AbandonGoodsQmsWaybill();
		try{
			//查询运单信息
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(req.getWaybillNo());
			if(waybill == null){
				//日志
				LOGGER.info("查询失败：该运单号无效！");
				//获取返回参数(运单号无效)
				response = this.getResponseJson
						(req.getWaybillNo(),FAILURE,"查询失败：该运单号无效！");
				LOGGER.info("************运单信息查询结束************");
				return response;
			}
			//运单号
			abandonWaybill.setWaybillNo(req.getWaybillNo());
			//开单时间
			abandonWaybill.setBillTime(waybill.getBillTime());
			//开单金额(总费用)
			abandonWaybill.setTotalFee(waybill.getTotalFee());
			//代收货款金额
			abandonWaybill.setCodAmount(waybill.getCodAmount());
			//预付金额
			abandonWaybill.setPrePayAmount(waybill.getPrePayAmount());
			//到付金额
			abandonWaybill.setToPayAmount(waybill.getToPayAmount());
			//开单品名(货物名称)
			abandonWaybill.setGoodsName(waybill.getGoodsName());
			//收货部门(转换成标杆编码)
			abandonWaybill.setReceiveOrgCode(this.getOrgCode(waybill.getReceiveOrgCode()));
			//收货部门名称
			abandonWaybill.setReceiveOrgName(waybill.getReceiveOrgName());
			//到达部门(提货网点,转换成标杆编码)
			abandonWaybill.setCustomerPickupOrgCode(this.getOrgCode(waybill.getCustomerPickupOrgCode()));
			//到达部门名称
			abandonWaybill.setCustomerPickupOrgName(waybill.getCustomerPickupOrgName());
			//开单件数
			abandonWaybill.setGoodsQtyTotal(waybill.getGoodsQtyTotal());
			//开单重量
			abandonWaybill.setGoodsWeightTotal(waybill.getGoodsWeightTotal());
			//开单体积
			abandonWaybill.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal());
			//保险价值
			abandonWaybill.setInsuranceAmount(waybill.getInsuranceAmount());
			//运输性质
			abandonWaybill.setProductCode(this.getProductName(waybill.getProductCode()));

			//查询库存部门
			String[] stockOrgCodes = this.queryStockOrgCodes(waybill.getWaybillNo());
			//库存部门(标杆编码)
			abandonWaybill.setStockOrgCode(stockOrgCodes[0]);
			//库存部门名称
			abandonWaybill.setStockOrgName(stockOrgCodes[1]);

			//************查询大区、事业部************Start
			//判断到达部门（提货网点）是否是内部网点
			boolean flag = this.checkCustomerPickupOrgCode(waybill.getCustomerPickupOrgCode());
			//大区
			OrgAdministrativeInfoEntity orgBigEntity = null;
			//事业部
			OrgAdministrativeInfoEntity orgDivisionEntity = null;
			//如果为内部网点
			if(flag){
				//查询大区
				orgBigEntity = this.querySuperiorOrg
						(waybill.getCustomerPickupOrgCode(),BizTypeConstants.ORG_BIG_REGION);
				//查询事业部
				orgDivisionEntity = this.querySuperiorOrg
						(waybill.getCustomerPickupOrgCode(),BizTypeConstants.ORG_DIVISION);
			}else{
				//查询大区(如果不是内部网点，则查询最终配载部门所在大区)
				orgBigEntity = this.querySuperiorOrg
						(waybill.getLastLoadOrgCode(),BizTypeConstants.ORG_BIG_REGION);
				//查询事业部
				orgDivisionEntity = this.querySuperiorOrg
						(waybill.getLastLoadOrgCode(),BizTypeConstants.ORG_DIVISION);
			}
			if(orgBigEntity != null){
				//到达大区(标杆编码)
				abandonWaybill.setBigRegionOrgCode(orgBigEntity.getUnifiedCode());
				//到达大区名称
				abandonWaybill.setBigRegionOrgName(orgBigEntity.getName());
			}
			if(orgDivisionEntity != null){
				//到达事业部(标杆编码)
				abandonWaybill.setDivisionOrgCode(orgDivisionEntity.getUnifiedCode());
				//到达事业部名称
				abandonWaybill.setDivisionOrgName(orgDivisionEntity.getName());
			}
			//************查询大区、事业部************End
			
			//************查询签收结果信息************Start
			//设置查询条件
			WaybillSignResultEntity resultEntity = 
					new WaybillSignResultEntity(req.getWaybillNo(),FossConstants.ACTIVE);
			//返回签收结果信息
			resultEntity = waybillSignResultDao.queryWaybillSignResult(resultEntity);
			if(resultEntity != null){
				//签收结果(签收状态,将编码转化成汉字)
				//2015-11-17  配合QMS的异常货类型，将签收结果变为签收状态
				String signSituation = this.getSignStatus(resultEntity.getSignStatus());
				abandonWaybill.setSignSituation(signSituation);
			}else{
				abandonWaybill.setSignSituation(STATUS);
			}
			//************查询签收结果信息************End
			//成功或失败的标识(0、失败；1、成功)
			abandonWaybill.setResult(SUCCESS);

			LOGGER.info("查询成功！");
			response = JSONObject.toJSONString(abandonWaybill);
			LOGGER.info("************运单信息查询结束************");
			return response;
		}catch(Exception e) {
			LOGGER.error("查询失败：" + e.getMessage());
			response = this.getResponseJson(req.getWaybillNo(),FAILURE,"查询失败：查询失败，请重新操作！");
			LOGGER.info("************运单信息查询结束************");
			return response;
		}
	}
	
	/**
	 * 获取返回信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-18 上午10:30:35
 	 */
	public String getResponseJson(String waybillNo,String result,String message){
		Map<String,Object> resMap = new HashMap<String,Object>();
		//设置返回信息
		if(StringUtils.isNotBlank(waybillNo)){
			resMap.put("waybillNo",waybillNo);
		}
		resMap.put("result",result);
		resMap.put("message",message);
		//将返回信息转成Json字符串
		String response = JSONObject.toJSONString(resMap);
		return response;
	}
	
	/**
	 * 将签收情况换成中文
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-4-23 上午11:34:13
	 * @return
	 */
	public String getSignStatus(String signStatus){
		//根据词条名和值编码，查询数据字典
		DataDictionaryValueEntity dictionary = 
				dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode
									(PKP_SIGN_STATUS,signStatus);
		return dictionary == null ? "" : dictionary.getValueName();
	}
	
	/**
	 * 根据运单号查询运单库存部门（编号及名称）
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-5-26 上午10:18:10
	 */
	public String[] queryStockOrgCodes(String waybillNo){
		//查询运单库存部门
		List<String> stockOrgCodes = waybillStockDao.queryStockOrgCodeByWaybillNo(waybillNo);
		//组织信息
		OrgAdministrativeInfoEntity org = null;
		//返回值
		String[] orgCodes = new String[2];
		//库存部门编码（填标杆编码）
		StringBuffer orgCode = new StringBuffer();
		//库存部门名称
		StringBuffer orgName = new StringBuffer();
		if(stockOrgCodes != null && stockOrgCodes.size()>0){
			for(String stockOrgCode : stockOrgCodes){
				//根据部门编号查询部门信息
				org= orgAdministrativeInfoService.
										queryOrgAdministrativeInfoByCodeClean(stockOrgCode);
				if(org != null){
					//用分号拼接部门
					orgCode.append(org.getUnifiedCode()).append(";");
					orgName.append(org.getName()).append(";");
				}
			}
		}
		//如果部门编号不为空串
		if(orgCode.length() > ONE){
			//截掉最后一个分号
			orgCodes[0] = orgCode.substring(ZERO,orgCode.length()-ONE);
		}
		//如果部门名字不为空串
		if(orgName.length() > ONE){
			//截掉最后一个分号
			orgCodes[1] = orgName.substring(ZERO,orgName.length()-ONE);
		}
		return orgCodes;
	}
	
	/**
	 * 判断到达部门（提货网点）是否是外部网点
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-5-22 下午16:44:23
	 */
	public boolean checkCustomerPickupOrgCode(String code){
		//通过部门编号查询组织信息
		OrgAdministrativeInfoEntity org= 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
		if(org == null){
			//如果查询不到组织信息，则为外部网点
			return false;
		}
		return true;
	}
	
	/**
	 * 根据部门编号、上级部门类型，查询指定上级部门
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-5-25 上午8:47:36
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrg(String code,String bizType){
		//设置查询上级类型
		List<String> orgBigRegionTypeLst = new ArrayList<String>();
		orgBigRegionTypeLst.add(bizType);
		OrgAdministrativeInfoEntity orgBigEntity =orgAdministrativeInfoComplexService
								.queryOrgAdministrativeInfoByCode(code,orgBigRegionTypeLst);
		
		return orgBigEntity;
	}
	
	/**
	 * 获取当前信息
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-18 上午11:29:40
	 */
	public CurrentInfo getCurrentInfo(String currentEmpCode,String currentDeptCode){
		//通过员工工号查询员工信息
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(currentEmpCode);
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		//通过部门标杆编号获取部门信息
		OrgAdministrativeInfoEntity org = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(currentDeptCode);
		if(emp != null){
			user.setEmployee(emp);//得到员工信息
		}else {
			emp = new EmployeeEntity();
			emp.setEmpName(QMS);//员工名称
			emp.setEmpCode(currentEmpCode);//员工编码
			user.setEmployee(emp);//得到员工信息
		}
		if(org != null){
			dept.setName(org.getName());// 得到部门名称
			dept.setUnifiedCode(currentDeptCode);//标杆编码
			//部门组织编码
			dept.setCode(org.getCode());
		}else {//其他
			dept.setName(QMS);//默认的部门名称
			//部门组织编码（当查不到部门时，把当前部门放进去）
			dept.setCode(currentDeptCode);
		}
		//返回信息
		return new CurrentInfo(user, dept);
	}

	/**
	 * 根据传入部门组织编码查询部门标杆编码
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-27 上午11:28:40
	 */
	public String getOrgCode(String orgCode){
		OrgAdministrativeInfoEntity org = null;
		//根据部门组织编码查询部门信息
		org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(org != null){
			orgCode = org.getUnifiedCode();
		}
		//返回标杆编码
		return orgCode;
	}
	
	/**
	 * 根据产品编码 查询 产品名称
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-27 下午16:26:21
	 */
	public String getProductName(String productCode){
		//根据CODE查询产品实体集合
		List<ProductEntity> entitys = bseProductDao.queryProductsByCode(productCode);
		if(entitys != null && entitys.size()>ZERO){
			ProductEntity entity = entitys.get(ZERO);
			return entity == null ? null : entity.getName();
		}
		return null;
	}
	
	/**
	 * 返回原单号签收出库
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-9-01 上午09:35:16
	 **/
	public void dealOriginalWaybillNo(String waybillNo,CurrentInfo currentInfo,
			WaybillEntity waybillOrg,ActualFreightEntity afOrg,boolean isSendInvoiceInfoOrg){
		// 中转出库
		workFlowService.outStock(waybillNo, currentInfo);
		//根据运单号，查询运单状态
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		//根据运单号查询 查询运单部分信息
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(waybillNo);
		if(null == waybillEntity){
			LOGGER.error("--该运单号不存在");//记录日志
			throw new BusinessException(SignException.WAYBILLNO_NULL);
		}
		//是否将发票信息传给发票系统
		isSendInvoiceInfoOrg = waybillSignResultService.isNeedSendInvoiceInfo
									(waybillEntity,actualFreightEntity);
		// 更新ActualFreight表中的结清状态为已结清
		workFlowService.updateActualFreight(actualFreightEntity);
		// 弃货签收接口
		waybillSignResultService.addAbandonGoodsSign(waybillNo, currentInfo);
		// 需要调用业务完结接口
		workFlowService.overWaybillTransaction(waybillNo);
		waybillOrg = waybillEntity;
		afOrg = actualFreightEntity;
	}
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setWaybillStockDao(IWaybillStockDao waybillStockDao) {
		this.waybillStockDao = waybillStockDao;
	}

	public void setBseProductDao(IProductDao bseProductDao) {
		this.bseProductDao = bseProductDao;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}
	
}
