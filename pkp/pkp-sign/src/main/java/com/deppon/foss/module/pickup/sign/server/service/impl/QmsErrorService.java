package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProductDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IQmsErrorService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordImportantErrorQmsWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordQmsMainErrorDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordQmsSignInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordReverseErrorQmsWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordShortErrorQmsWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayableQueryForPkpService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * QMS差错上报信息
 * @author 231434-FOSS-bieyexiong
 * @date 2015-4-15 上午10:30:35
 */
public class QmsErrorService implements IQmsErrorService{
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QmsErrorService.class);
	
	/**
	 * 零担丢货管理组组织编码
	 */
	private static final String THROW_GOODS_ORG_CODE = "W01000301050203";
	
	/**
	 * 默认经手部门
	 */
	private static final String HANDED_DEPT = "**********";
	
	/**
	 * 运单签收结果接口
	 */
	private IWaybillSignResultDao  waybillSignResultDao;
	
	/**
	 * 部门服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 交接单服务
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 员工服务
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 走货路径服务
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 外发信息服务
	 */
	private ILdpExternalBillService ldpExternalBillService;
	
	/**
	 * 数据字典服务
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * 产品DAO
	 */
	private IProductDao bseProductDao;
	
	/**
	 * 偏线代理网点Dao
	 */
	private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
	
	/**
	 * 快递代理公司网点接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	/**
	 * 到达联
	 */
	private IArrivesheetDao arrivesheetDao;
	
	/**
	 * 结算获取理赔金额
	 */
	private IPayableQueryForPkpService payableQueryForPkpService;
	
	/**
	 * 签收明细service
	 */
	private ISignDetailService signDetailService;
	
	/**
	 * 运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * 手动上报，根据运单号，返回运单的部分签收信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-15 上午10:23:35
	 */
	@Override
	public String querySignInfo(String waybillNo,String businessType,String errorType) {
		String jsonStr = null;
		
		if(StringUtils.isBlank(waybillNo)){
			//记录异常日志
			LOGGER.error("运单号为空");
			throw new SignException("运单号为空");
		}
		//得到手动上报信息
		jsonStr = this.getSignInfo(waybillNo,errorType);
		
		return jsonStr;
	}

	/**
	 * 快递差错-查询QMS反签收差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-15 下午15:41:16
	 */
	@Override
	public Map<String,Object> getReverseErrorQmsInfo(WaybillEntity waybill,CurrentInfo currentInfo){
		//判断运单实体是否为空
		if(waybill == null){
			LOGGER.error("上报快递反签收差错失败，运单信息为空。");
			throw new SignException("运单信息为空。");
		}
		if(currentInfo == null){
			LOGGER.error("上报快递反签收差错失败，责任人信息为空。");
			throw new SignException("责任人信息为空。");
		}
		//上报主信息实体
		RecordQmsMainErrorDto mainErrorDto = new RecordQmsMainErrorDto();
		//反签收差错信息实体
		RecordReverseErrorQmsWaybillDto errorInfoDto = new RecordReverseErrorQmsWaybillDto();
		
		//运单号
		mainErrorDto.setWayBillNum(waybill.getWaybillNo());
		//文件标准id
		mainErrorDto.setDocStandarId(ReportConstants.REVERSE_DOCSTANDAR_ID);
		//收货部门(设置为标杆编码)
		mainErrorDto.setReceiveDeptCode(this.getOrgByCode(waybill.getReceiveOrgCode(),false));
		//收货部门名称
		mainErrorDto.setReceiveDeptName(waybill.getReceiveOrgName());
		//上报人工号
		mainErrorDto.setRepEmpcode(currentInfo.getEmpCode());
		//上报人名字
		mainErrorDto.setRepEmpName(currentInfo.getEmpName());
		//上报人职位
		mainErrorDto.setRepEmpJob(
						currentInfo.getUser() == null ? "" : currentInfo.getUser().getTitle());
		
		//收货人电话(优先给手机号)
		if(StringUtils.isNotBlank(waybill.getReceiveCustomerMobilephone())){
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerMobilephone());
		}else{
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerPhone());
		}
		//发货时间(开单时间)
		errorInfoDto.setDeliveryTime(DateUtils.convert(waybill.getBillTime()));
		//到达部门(即提货网点，设置为标杆编码)
		errorInfoDto.setArriveDeptCode(this.getOrgByCode(waybill.getCustomerPickupOrgCode(),false));
		//到达部门名称
		errorInfoDto.setArriveDeptName(waybill.getCustomerPickupOrgName());
		//收货人
		errorInfoDto.setConsignee(waybill.getReceiveCustomerContact());
		//开单部门(设置为标杆编码)
		errorInfoDto.setBillingDeptCode(this.getOrgByCode(waybill.getCreateOrgCode(),false));
		//开单部门名称
		errorInfoDto.setBillingDeptName(this.getOrgByCode(waybill.getCreateOrgCode(),true));
		
		//责任部门名称
		errorInfoDto.setRespDeptName(currentInfo.getCurrentDeptName());
		//根据部门编号查询部门信息
		OrgAdministrativeInfoEntity org= 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(currentInfo.getCurrentDeptCode());
		if(org != null){
			//责任部门(起草反签收人所在部门为责任部门，设置为标杆编码)
			errorInfoDto.setRespDeptCode(org.getUnifiedCode());
			if(StringUtils.isBlank(org.getPrincipalNo())){
				//查询责任人及其工号
				org = this.getResponsible(org);
			}
			//责任人工号
			errorInfoDto.setRespEmpCode(org.getPrincipalNo());
			EmployeeEntity emp = employeeService.querySimpleEmployeeByEmpCode(org.getPrincipalNo());
			if(emp != null){
				//责任人姓名
				errorInfoDto.setRespEmpName(emp.getEmpName());
			}
			//短消息通知对象(责任人+责任人上级)
			String shortMessageCodes = errorInfoDto.getRespEmpCode();
			String shortMessageNames = errorInfoDto.getRespEmpName();
			//如果责任部门是大区，则不再往上查责任人上级
			if(!FossConstants.YES.equals(org.getBigRegion())){
				//查询责任人的上级部门(即上级部门负责人),根据部门标杆编号查询部门信息
				OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByUnifiedCodeClean(org.getParentOrgUnifiedCode());
				if(parentOrg != null ){
					//短信通知对象工号(责任人、责任人上级)
					shortMessageCodes = shortMessageCodes + "," + parentOrg.getPrincipalNo();
					EmployeeEntity parentEmp = employeeService.querySimpleEmployeeByEmpCode(parentOrg.getPrincipalNo());
					if(parentEmp != null){
						//短信通知对象名字(责任人、责任人上级)
						shortMessageNames = shortMessageNames + "," + parentEmp.getEmpName();
					}
				}
			}
			errorInfoDto.setShortMessageCodes(shortMessageCodes);
			errorInfoDto.setShortMessageNames(shortMessageNames);
		}
		
		//将差错信息放进Map
		Map<String,Object> errorInfoMap = new HashMap<String,Object>();
		//差错类型编号
		errorInfoMap.put(ReportConstants.ERRORTYPEID,ReportConstants.QMS_FOSS_REVERSE);
		//业务类型
		errorInfoMap.put(ReportConstants.ERRCATEGOTY,ReportConstants.BUSINESSEXP);
		//主信息
		errorInfoMap.put(ReportConstants.MAININFO,mainErrorDto);
		//子信息
		errorInfoMap.put(ReportConstants.KDSUBHASINFO,errorInfoDto);
		
		return errorInfoMap;
	}

	/**
	 * 零担差错-查询QMS反签收差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-6-13 上午11:10:23
	 */
	@Override
	public Map<String,Object> getLDReverseErrorQmsInfo(WaybillEntity waybill,CurrentInfo currentInfo){
		//判断运单实体是否为空
		if(waybill == null){
			LOGGER.error("上报零担反签收差错失败，运单信息为空。");
			throw new SignException("运单信息为空。");
		}
		if(currentInfo == null){
			LOGGER.error("上报零担反签收差错失败，责任人信息为空。");
			throw new SignException("责任人信息为空。");
		}
		//上报主信息实体
		RecordQmsMainErrorDto mainErrorDto = new RecordQmsMainErrorDto();
		//反签收差错信息实体
		RecordReverseErrorQmsWaybillDto errorInfoDto = new RecordReverseErrorQmsWaybillDto();

		//运单号
		mainErrorDto.setWayBillNum(waybill.getWaybillNo());
		//文件标准id
		mainErrorDto.setDocStandarId(ReportConstants.LD_REVERSE_DOCSTANDAR_ID);
		//收货部门(设置为标杆编码)
		mainErrorDto.setReceiveDeptCode(this.getOrgByCode(waybill.getReceiveOrgCode(),false));
		//收货部门名称
		mainErrorDto.setReceiveDeptName(waybill.getReceiveOrgName());
		//上报人工号
		mainErrorDto.setRepEmpcode(currentInfo.getEmpCode());
		//上报人名字
		mainErrorDto.setRepEmpName(currentInfo.getEmpName());
		//上报人职位
		mainErrorDto.setRepEmpJob(
				currentInfo.getUser() == null ? "" : currentInfo.getUser().getTitle());

		//收货人电话(优先给手机号)
		if(StringUtils.isNotBlank(waybill.getReceiveCustomerMobilephone())){
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerMobilephone());
		}else{
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerPhone());
		}
		//发货时间(开单时间)
		errorInfoDto.setSendGoodsTime(DateUtils.convert(waybill.getBillTime()));
		//到达部门(即提货网点，设置为标杆编码)
		errorInfoDto.setArriveDeptCode(this.getOrgByCode(waybill.getCustomerPickupOrgCode(),false));
		//到达部门名称
		errorInfoDto.setArriveDeptName(waybill.getCustomerPickupOrgName());
		//收货人
		errorInfoDto.setReceiverName(waybill.getReceiveCustomerContact());
		//开单部门(设置为标杆编码)
		errorInfoDto.setBillingDeptCode(this.getOrgByCode(waybill.getCreateOrgCode(),false));
		//开单部门名称
		errorInfoDto.setBillingDeptName(this.getOrgByCode(waybill.getCreateOrgCode(),true));
		
		//责任部门名称
		errorInfoDto.setRespDeptName(currentInfo.getCurrentDeptName());
		//根据部门编号查询部门信息
		OrgAdministrativeInfoEntity org= 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(currentInfo.getCurrentDeptCode());
		if(org != null){
			//责任部门(起草反签收人所在部门为责任部门，设置为标杆编码)
			errorInfoDto.setRespDeptCode(org.getUnifiedCode());
			if(StringUtils.isBlank(org.getPrincipalNo())){
				//查询责任人及其工号
				org = this.getResponsible(org);
			}
			//责任人工号
			errorInfoDto.setRespEmpCode(org.getPrincipalNo());
			EmployeeEntity emp = employeeService.querySimpleEmployeeByEmpCode(org.getPrincipalNo());
			if(emp != null){
				//责任人姓名
				errorInfoDto.setRespEmpName(emp.getEmpName());
			}
			//短消息通知对象(责任人+责任人上级)
			String shortMessageCodes = errorInfoDto.getRespEmpCode();
			String shortMessageNames = errorInfoDto.getRespEmpName();
			//如果责任部门是大区，则不再往上查责任人上级
			if(!FossConstants.YES.equals(org.getBigRegion())){
				//查询责任人的上级部门(即上级部门负责人),根据部门标杆编号查询部门信息
				OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByUnifiedCodeClean(org.getParentOrgUnifiedCode());
				if(parentOrg != null ){
					//短信通知对象工号(责任人、责任人上级)
					shortMessageCodes = shortMessageCodes + "," + parentOrg.getPrincipalNo();
					EmployeeEntity parentEmp = employeeService.querySimpleEmployeeByEmpCode(parentOrg.getPrincipalNo());
					if(parentEmp != null){
						//短信通知对象名字(责任人、责任人上级)
						shortMessageNames = shortMessageNames + "," + parentEmp.getEmpName();
					}
				}
			}
			errorInfoDto.setShortMessageCodes(shortMessageCodes);
			errorInfoDto.setShortMessageNames(shortMessageNames);
		}
		
		//将差错信息放进Map
		Map<String,Object> errorInfoMap = new HashMap<String,Object>();
		//差错类型编号
		errorInfoMap.put(ReportConstants.ERRORTYPEID,ReportConstants.QMS_FOSS_REVERSE_LD);
		//业务类型
		errorInfoMap.put(ReportConstants.ERRCATEGOTY,ReportConstants.BUSINESSLTL);
		//主信息
		errorInfoMap.put(ReportConstants.MAININFO,mainErrorDto);
		//子信息
		errorInfoMap.put(ReportConstants.LDSUBHASINFO,errorInfoDto);

		return errorInfoMap;
	}

	/**
	 * 快递差错-得到内物短少差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-21 下午15:27:38
	 */
	@Override
	public Map<String,Object> getShortErrorQmsInfo(WaybillEntity waybill,
									RecordErrorWaybillResultDto recordErrorWaybillResultDto) {
		//判断运单实体是否为空
		if(waybill == null){
			LOGGER.error("上报快递内物短少差错失败，运单信息为空。");
			throw new SignException("运单信息为空。");
		}
		if(recordErrorWaybillResultDto == null){
			LOGGER.error("上报快递内物短少差错失败，上报信息为空。");
			throw new SignException("上报信息为空。");
		}
		//上报主信息实体
		RecordQmsMainErrorDto mainErrorDto = new RecordQmsMainErrorDto();
		//内物短少差错信息实体
		RecordShortErrorQmsWaybillDto errorInfoDto = new RecordShortErrorQmsWaybillDto();
		
		//运单号
		mainErrorDto.setWayBillNum(waybill.getWaybillNo());
		//文件标准id
		mainErrorDto.setDocStandarId(ReportConstants.SHORT_DOCSTANDAR_ID);
		//收货部门
		mainErrorDto.setReceiveDeptCode(this.getOrgByCode(waybill.getReceiveOrgCode(),false));
		//收货部门名字
		mainErrorDto.setReceiveDeptName(waybill.getReceiveOrgName());
		
		//查询上报人信息
		EmployeeEntity repEmp = employeeService.querySimpleEmployeeByEmpCode
				(recordErrorWaybillResultDto.getOperateCode());
		//上报部门
		OrgAdministrativeInfoEntity repDept= orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCodeClean(recordErrorWaybillResultDto.getOperateOrgCode());
		if(repEmp == null || repDept == null){
			LOGGER.error("上报快递内物短少差错失败，上报信息为空。");
			throw new SignException("上报人信息或上报部门信息为空。");
		}
		//上报人工号
		mainErrorDto.setRepEmpcode(repEmp.getEmpCode());
		//上报人姓名
		mainErrorDto.setRepEmpName(repEmp.getEmpName());
		//上报人职位
		mainErrorDto.setRepEmpJob(repEmp.getTitleName());
		//上报人部门
		mainErrorDto.setRepDeptCode(repDept.getUnifiedCode());
		//上报人部门名称
		mainErrorDto.setRepDeptName(repDept.getName());
		
		//短少量
		errorInfoDto.setShortAmount(recordErrorWaybillResultDto.getAlittleShort());
		//外包装是否完好
		errorInfoDto.setPackagingOk(recordErrorWaybillResultDto.getPackingResult());
		//短少流水号
		errorInfoDto.setInnerShortSerialCode(recordErrorWaybillResultDto.getSerialNo());
		//运输类型
		errorInfoDto.setTransportType(this.getDictionaryValue
							(ReportConstants.TRANS_TYPE,waybill.getTransportType()));
		//返单类型
		errorInfoDto.setBackBillingType(this.getDictionaryValue
							(ReportConstants.RETURNBILLTYPE,waybill.getReturnBillType()));
		//托运人(发货客户联系人)
		errorInfoDto.setConsignor(waybill.getDeliveryCustomerContact());
		//运输性质
		errorInfoDto.setTransportProperties(this.getProductName(waybill.getProductCode()));
		//收货人电话(优先给手机号)
		if(waybill.getReceiveCustomerMobilephone() != null){
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerMobilephone());
		}else{
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerPhone());
		}
		if(ReportConstants.TRANS_AIRCRAFT.equals(waybill.getTransportType())){
			//如果运输类型为空运
			//提货方式
			errorInfoDto.setDeliveryMethods(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSAIR,waybill.getReceiveMethod()));
		}else{
			//如果不为空运
			//提货方式
			errorInfoDto.setDeliveryMethods(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSHIGHWAYS,waybill.getReceiveMethod()));
		}
		//储运事项
		errorInfoDto.setStorageItems(waybill.getTransportationRemark());
		//重量
		errorInfoDto.setWeight(waybill.getGoodsWeightTotal());
		//体积
		errorInfoDto.setVolume(waybill.getGoodsVolumeTotal());
		//件数
		errorInfoDto.setFnumber(waybill.getGoodsQtyTotal());
		//货物名称
		errorInfoDto.setGoodsName(waybill.getGoodsName());
		//发货时间(开单时间)
		errorInfoDto.setDeliveryTime(DateUtils.convert(waybill.getBillTime()));
		//到达部门(提货网点,设置为标杆编码)
		errorInfoDto.setArriveDeptCode(this.getOrgByCode(waybill.getCustomerPickupOrgCode(),false));
		//到达部门名字
		errorInfoDto.setArriveDeptName(waybill.getCustomerPickupOrgName());
		//收货人(收货客户联系人)
		errorInfoDto.setConsignee(waybill.getReceiveCustomerContact());
		//(开单)付款方式
		errorInfoDto.setPaymentMethod(this.getDictionaryValue
							(ReportConstants.SETTLEMENT__PAYMENT_TYPE,waybill.getPaidMethod()));
		//保险金额(保价声明价值)
		errorInfoDto.setInsuranceAmount(waybill.getInsuranceAmount());
		//货物包装
		errorInfoDto.setGoodsPackage(waybill.getGoodsPackage());
		//运费总额
		errorInfoDto.setTotalFreight(waybill.getTotalFee());
		//开单部门
		errorInfoDto.setBillingDeptCode(this.getOrgByCode(waybill.getCreateOrgCode(),false));
		//开单部门名字
		errorInfoDto.setBillingDeptName(this.getOrgByCode(waybill.getCreateOrgCode(),true));
		
		//根据运单号查询交接单
		List<HandOverBillEntity> handOverBills = 
				handOverBillService.queryHandOveredRecordsByWaybillNo(waybill.getWaybillNo());
		String  vehicleNo = recordErrorWaybillResultDto.getVehicleNo();
		//取得最近的一次交接单
		if(CollectionUtils.isNotEmpty(handOverBills)){
			HandOverBillEntity handOverBill = 
					handOverBills.get(handOverBills.size() - NumberConstants.NUMBER_1);
			//交接单号
			errorInfoDto.setEir(handOverBill.getHandOverBillNo());
			if(StringUtils.isBlank(vehicleNo)){
				//设置车牌号
				vehicleNo = handOverBill.getVehicleNo();
			}
		}
		//车牌号
		errorInfoDto.setLicensePlateNumber(vehicleNo);
		
		//查询经手部门（即责任部门）
		List<String> serialNos = null;
		if(StringUtils.isNotBlank(recordErrorWaybillResultDto.getSerialNo())){
			serialNos = Arrays.asList(recordErrorWaybillResultDto.getSerialNo().split(","));
		}
		//查询是否落地配
		OuterBranchExpressEntity ldpOrg = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
							waybill.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
		//如果是快递代理签收
		if(ldpOrg != null){
			//查询外发单信息
			List<LdpExternalBillDto> dtos = 
					ldpExternalBillService.queryExternalBillListForLdpSign(waybill.getWaybillNo());
			if(CollectionUtils.isNotEmpty(dtos)){
				//外发员工号
				String externalUserCode = dtos.get(NumberConstants.NUMERAL_ZORE).getExternalUserCode();
				//查询员工信息
				EmployeeEntity emp = employeeService.querySimpleEmployeeByEmpCode(externalUserCode);
				if(emp == null){
					LOGGER.error("上报快递内物短少差错失败，外发员已离职。");
					throw new SignException("外发员已离职。"); 
				}
				//根据员工所在部门标杆部门查询部门信息
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByUnifiedCodeClean(emp.getUnifieldCode());
				//责任部门
				errorInfoDto.setRespDeptCode(org.getUnifiedCode());
				//责任部门名称
				errorInfoDto.setRespDeptName(org.getName());
				//如果责任部门负责人为空，则向上级部门查询
				if(StringUtils.isBlank(org.getPrincipalNo())){
					//查询责任部门负责人及其工号
					org = this.getResponsible(org);
				}
				//责任部门负责人工号
				errorInfoDto.setRespDeptResperCode(org.getPrincipalNo());
				emp = employeeService.querySimpleEmployeeByEmpCode(org.getPrincipalNo());
				if(emp != null){
					//责任部门负责人姓名
					errorInfoDto.setRespDeptResperName(emp.getEmpName());
				}
			}
		}else{//如果不是快递代理签收
			//获取实际走货路径的所有部门编码(路径明细表里有时候没有流水号，所以不传流水号)
			List<String> handedDeptCodes = 
					calculateTransportPathService.queryPassDeptCodes(waybill.getWaybillNo(),null);
			String[] handedDept = this.getHandedDept(handedDeptCodes,true);
			if(handedDept != null){
				//经手部门标杆编码
				errorInfoDto.setHandedDeptCode(handedDept[0]);
				//经手部门名称
				errorInfoDto.setHandedDeptName(handedDept[1]);
				//责任部门标杆编码
				errorInfoDto.setRespDeptCode(handedDept[0]);
				//责任部门名字
				errorInfoDto.setRespDeptName(handedDept[1]);
				//责任部门负责人
				errorInfoDto.setRespDeptResperCode(handedDept[2]);
				//责任部门负责人姓名
				errorInfoDto.setRespDeptResperName(handedDept[SettlementReportNumber.THREE]);
			}
			
		}
		
		//将差错信息放进Map
		Map<String,Object> errorInfoMap = new HashMap<String,Object>();
		//差错类型编号
		errorInfoMap.put(ReportConstants.ERRORTYPEID,ReportConstants.QMS_FOSS_SHORT);
		//业务类型
		errorInfoMap.put(ReportConstants.ERRCATEGOTY,ReportConstants.BUSINESSEXP);
		//主信息
		errorInfoMap.put(ReportConstants.MAININFO,mainErrorDto);
		//子信息
		errorInfoMap.put(ReportConstants.KDSUBHASINFO,errorInfoDto);
		
		return errorInfoMap;
	}
	
	/**
	 * 零担差错-查询QMS内物短少差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-6-13 下午15:13:11
	 */
	@Override
	public Map<String,Object> getLDShortErrorQmsInfo(WaybillEntity waybill,
									RecordErrorWaybillResultDto recordErrorWaybillResultDto){
		//判断运单实体是否为空
		if(waybill == null){
			LOGGER.error("上报零担内物短少差错失败，运单信息为空。");
			throw new SignException("运单信息为空。");
		}
		if(recordErrorWaybillResultDto == null){
			LOGGER.error("上报零担内物短少差错失败，上报信息为空。");
			throw new SignException("上报信息为空。");
		}
		//上报主信息实体
		RecordQmsMainErrorDto mainErrorDto = new RecordQmsMainErrorDto();
		//内物短少差错信息实体
		RecordShortErrorQmsWaybillDto errorInfoDto = new RecordShortErrorQmsWaybillDto();

		//运单号
		mainErrorDto.setWayBillNum(waybill.getWaybillNo());
		//文件标准id
		mainErrorDto.setDocStandarId(ReportConstants.LD_SHORT_DOCSTANDAR_ID);
		//收货部门
		mainErrorDto.setReceiveDeptCode(this.getOrgByCode(waybill.getReceiveOrgCode(),false));
		//收货部门名字
		mainErrorDto.setReceiveDeptName(waybill.getReceiveOrgName());
		//查询上报人信息
		EmployeeEntity repEmp = employeeService.querySimpleEmployeeByEmpCode
				(recordErrorWaybillResultDto.getOperateCode());
		//上报部门
		OrgAdministrativeInfoEntity repDept= orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCodeClean(recordErrorWaybillResultDto.getOperateOrgCode());
		if(repEmp == null || repDept == null){
			LOGGER.error("上报零担内物短少差错失败，上报信息为空。");
			throw new SignException("上报人信息或上报部门信息为空。");
		}
		//上报人工号
		mainErrorDto.setRepEmpcode(repEmp.getEmpCode());
		//上报人姓名
		mainErrorDto.setRepEmpName(repEmp.getEmpName());
		//上报人职位
		mainErrorDto.setRepEmpJob(repEmp.getTitleName());
		//上报人部门
		mainErrorDto.setRepDeptCode(repDept.getUnifiedCode());
		//上报人部门名称
		mainErrorDto.setRepDeptName(repDept.getName());

		//短少量
		errorInfoDto.setShortNum(recordErrorWaybillResultDto.getAlittleShort());
		//外包装是否完好
		errorInfoDto.setPackageIsGood(recordErrorWaybillResultDto.getPackingResult());
		//短少流水号
		errorInfoDto.setGoodsLoseFlowcode(recordErrorWaybillResultDto.getSerialNo());
		//运输类型
		errorInfoDto.setTransType(this.getDictionaryValue
							(ReportConstants.TRANS_TYPE,waybill.getTransportType()));
		//返单类型
		errorInfoDto.setReturnBillType(this.getDictionaryValue
							(ReportConstants.RETURNBILLTYPE,waybill.getReturnBillType()));
		//是否集中接货
		errorInfoDto.setIsConcentReceiving(waybill.getPickupCentralized());
		//托运人(发货客户联系人)
		errorInfoDto.setShipper(waybill.getDeliveryCustomerContact());
		//运输性质
		errorInfoDto.setTransNature(this.getProductName(waybill.getProductCode()));
		//收货人电话(优先给手机号)
		if(waybill.getReceiveCustomerMobilephone() != null){
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerMobilephone());
		}else{
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerPhone());
		}
		if(ReportConstants.TRANS_AIRCRAFT.equals(waybill.getTransportType())){
			//如果运输类型为空运
			//提货方式
			errorInfoDto.setPickUpType(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSAIR,waybill.getReceiveMethod()));
		}else{
			//如果不为空运
			//提货方式
			errorInfoDto.setPickUpType(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSHIGHWAYS,waybill.getReceiveMethod()));
		}
		//储运事项
		errorInfoDto.setStorageTransport(waybill.getTransportationRemark());
		//重量
		errorInfoDto.setSumWeight(waybill.getGoodsWeightTotal());
		//体积
		errorInfoDto.setSumVolume(waybill.getGoodsVolumeTotal());
		//件数
		errorInfoDto.setSumNumber(waybill.getGoodsQtyTotal());
		//货物名称
		errorInfoDto.setGoodsName(waybill.getGoodsName());
		//发货时间(开单时间)
		errorInfoDto.setSendGoodsTime(DateUtils.convert(waybill.getBillTime()));
		//到达部门(提货网点,设置为标杆编码)
		errorInfoDto.setArriveDeptCode(this.getOrgByCode(waybill.getCustomerPickupOrgCode(),false));
		//到达部门名字
		errorInfoDto.setArriveDeptName(waybill.getCustomerPickupOrgName());
		//收货人(收货客户联系人)
		errorInfoDto.setReceiverName(waybill.getReceiveCustomerContact());
		//(开单)付款方式
		errorInfoDto.setPayType(this.getDictionaryValue
							(ReportConstants.SETTLEMENT__PAYMENT_TYPE,waybill.getPaidMethod()));
		//保险金额(保价声明价值)
		errorInfoDto.setSafeMoney(waybill.getInsuranceAmount());
		//货物包装
		errorInfoDto.setGoodsPackage(waybill.getGoodsPackage());
		//运费总额
		errorInfoDto.setFreightSumFee(waybill.getTotalFee());
		//开单部门
		errorInfoDto.setBillingDeptCode(this.getOrgByCode(waybill.getCreateOrgCode(),false));
		//开单部门名字
		errorInfoDto.setBillingDeptName(this.getOrgByCode(waybill.getCreateOrgCode(),true));

		//根据运单号查询交接单
		List<HandOverBillEntity> handOverBills = 
				handOverBillService.queryHandOveredRecordsByWaybillNo(waybill.getWaybillNo());
		//车牌号
		String vehicleNo = recordErrorWaybillResultDto.getVehicleNo();
		//取得最近的一次交接单
		if(handOverBills != null && handOverBills.size()>NumberConstants.NUMERAL_ZORE){
			HandOverBillEntity handOverBill = 
					handOverBills.get(handOverBills.size() - NumberConstants.NUMBER_1);
			//交接单号
			errorInfoDto.setTransferBill(handOverBill.getHandOverBillNo());
			if(StringUtils.isBlank(vehicleNo)){
				//设置车牌号
				vehicleNo = handOverBill.getVehicleNo();
			}
		}
		//车牌号
		errorInfoDto.setCarCode(vehicleNo);

		//查询经手部门
		List<String> serialNos = null;
		if(StringUtils.isNotBlank(recordErrorWaybillResultDto.getSerialNo())){
			serialNos = Arrays.asList(recordErrorWaybillResultDto.getSerialNo().split(","));
		}
		//获取实际走货路径的所有部门编码(路径明细表里有时候没有流水号，所以不传流水号)
		List<String> handedDeptCodes = 
				calculateTransportPathService.queryPassDeptCodes(waybill.getWaybillNo(),null);
		//剔除“零担丢货管理组”及“出发部门”
		if(CollectionUtils.isNotEmpty(handedDeptCodes)){
			Iterator<String> iterator = handedDeptCodes.iterator();
			OuterBranchParamsDto branch = new OuterBranchParamsDto();
			List<OuterBranchEntity> list = null;
			while(iterator.hasNext()){
				String orgCode = iterator.next();
				//代理网点编码
				branch.setAgentDeptCode(orgCode);
				//查询空运/偏线代理网点
				list = vehicleAgencyDeptDao.queryOuterBranchs(branch);
				//剔除“零担丢货管理组”、“出发部门”、"空运/偏线代理网点"
				if(THROW_GOODS_ORG_CODE.equals(orgCode)//剔除“零担丢货管理组”
						|| StringUtils.equals(orgCode, waybill.getReceiveOrgCode())//剔除“出发部门”
						|| CollectionUtils.isNotEmpty(list)){//空运/偏线代理网点
					iterator.remove();
				}
				list = null;
			}
			//司机所属部门(派送时，送货司机所在部门)
			handedDeptCodes.add(recordErrorWaybillResultDto.getDriverOrgCode());
		}
		//获取经手部门标杆编码 和 经手部门名称
		String[] handedDept = this.getHandedDept(handedDeptCodes,true);
		if(handedDept != null){
			//经手部门标杆编码
			errorInfoDto.setGoodsHandleDeptCode(handedDept[0]);
			//经手部门名称
			errorInfoDto.setGoodsHandleDeptName(handedDept[1]);
			//责任部门标杆编码
			errorInfoDto.setRespDeptCode(handedDept[0]);
			//责任部门名字
			errorInfoDto.setRespDeptName(handedDept[1]);
			//责任部门负责人
			errorInfoDto.setRespDeptResperCode(handedDept[2]);
			//责任部门负责人姓名
			errorInfoDto.setRespDeptResperName(handedDept[SettlementReportNumber.THREE]);
			//责任人
			errorInfoDto.setRespEmpCode(handedDept[2]);
			//责任人姓名
			errorInfoDto.setRespEmpName(handedDept[SettlementReportNumber.THREE]);
		}
		
		//将差错信息放进Map
		Map<String,Object> errorInfoMap = new HashMap<String,Object>();
		//差错类型编号
		errorInfoMap.put(ReportConstants.ERRORTYPEID,ReportConstants.QMS_FOSS_SHORT_LD);
		//业务类型
		errorInfoMap.put(ReportConstants.ERRCATEGOTY,ReportConstants.BUSINESSLTL);
		//主信息
		errorInfoMap.put(ReportConstants.MAININFO,mainErrorDto);
		//子信息
		errorInfoMap.put(ReportConstants.LDSUBHASINFO,errorInfoDto);

		return errorInfoMap;
	}
	
	/**
	 * 零担差错-查询QMS异常签收线上划责自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2016-2-20 下午13:56:19
	 */
	@Override
	public String getLDUnnormalSignQmsInfo(WaybillEntity waybill,
			RecordUnnormalSignWaybillDto unnormalDto) {
		if(waybill == null){
			LOGGER.error("零担异常签收线上划责自动上报失败，运单信息为空！");
			throw new SignException("上报失败：运单信息为空！"); 
		}
		if(unnormalDto == null){
			LOGGER.error("零担异常签收线上划责自动上报失败，上报数据为空！");
			throw new SignException("上报失败：上报数据为空！");
		}
		//承运信息实体
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(unnormalDto.getWaybillNo());
		if(actual == null){
			LOGGER.error("零担异常签收线上划责自动上报失败，上报数据为空！");
			throw new SignException("上报失败：运单信息为空！");
		}
		//异常签收线上划责信息实体
		RecordUnnormalSignWaybillDto errorInfoDto = new RecordUnnormalSignWaybillDto();
		//运单号
		errorInfoDto.setWaybillNo(unnormalDto.getWaybillNo());

		//利用HashSet去重
		Set<String> signSituations = new HashSet<String>();
		//异常签收件数
		int unnormalNumber = 0;
		if(StringUtils.isBlank(unnormalDto.getSignSituation())){
			//异常货物件数 和 签收情况
			//查询异常签收明细
			List<SignDetailEntity> signDetailEntitys = 
					signDetailService.querySignDetailByWaybillNo(unnormalDto.getWaybillNo());
			if(CollectionUtils.isEmpty(signDetailEntitys)){
				LOGGER.error("零担异常签收线上划责自动上报失败，异常签收明细为空(该单号可能正常签收或者未签收)！");
				throw new SignException("上报失败：异常签收明细为空(该单号可能正常签收或者未签收)！");
			}
			for(SignDetailEntity signDetailEntity : signDetailEntitys){
				//当有签收情况为内物短少时
				if(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT
						.equals(signDetailEntity.getSituation())){
					LOGGER.error("零担异常签收线上划责自动上报失败，该运单已上报内物短少差错！");
					throw new SignException("上报失败：该运单已上报内物短少差错！");
				}
				signSituations.add(signDetailEntity.getSituation());
			}
			if(signSituations.size() == 1){
				//如果set的元素个数为1，则非同票多类异常
				//将HashSet转换成ArrayList
				List<String> situations = new ArrayList<String>(signSituations);
				//签收类型(将签收情况换成中文)
				errorInfoDto.setSignSituation(this.getDictionaryValue
						(ReportConstants.PKP_SIGN_SITUATION,situations.get(0)));
			}else{
				//不等于1，就是大于1(前面的过滤条件使之不可能为0)
				//签收类型(将签收情况换成中文)
				errorInfoDto.setSignSituation("同票多类异常");
			}
			//设置异常货物件数
			unnormalNumber = signDetailEntitys.size();
		}else{
			//签收类型(将签收情况换成中文)
			errorInfoDto.setSignSituation(this.getDictionaryValue
					(ReportConstants.PKP_SIGN_SITUATION,unnormalDto.getSignSituation()));
		}
		if(unnormalDto.getUnnormalNumber() == null){
			//异常货件数
			errorInfoDto.setUnnormalNumber(unnormalNumber);
		}else{
			//异常货件数
			errorInfoDto.setUnnormalNumber(unnormalDto.getUnnormalNumber());
		}
		//货物名称
		errorInfoDto.setGoodsName(waybill.getGoodsName());
		//保险金额(保价声明价值)
		errorInfoDto.setSafeMoney(waybill.getInsuranceAmount());
		//签收时间(异常签收时间)
		errorInfoDto.setUnnormalSignTime(DateUtils.convert(unnormalDto.getSignTime()));
		//开单部门
		errorInfoDto.setBillingDeptCode(this.getOrgByCode(waybill.getCreateOrgCode(),false));
		//开单部门名字
		errorInfoDto.setBillingDeptName(this.getOrgByCode(waybill.getCreateOrgCode(),true));
		//(总)重量
		errorInfoDto.setSumWeight(waybill.getGoodsWeightTotal());
		//(总)体积
		errorInfoDto.setSumVolume(waybill.getGoodsVolumeTotal());
		//(总)件数
		errorInfoDto.setSumNumber(waybill.getGoodsQtyTotal());
		//运费总额
		errorInfoDto.setFreightSumFee(waybill.getTotalFee() == null ? new BigDecimal(0) : waybill.getTotalFee());
		//货物包装
		errorInfoDto.setGoodsPackage(waybill.getGoodsPackage());
		//(开单)付款方式
		errorInfoDto.setPayType(this.getDictionaryValue
				(ReportConstants.SETTLEMENT__PAYMENT_TYPE,waybill.getPaidMethod()));
		//运输性质
		errorInfoDto.setTransNature(this.getProductName(waybill.getProductCode()));
		//派送方式(提货方式)
		if(ReportConstants.TRANS_AIRCRAFT.equals(waybill.getTransportType())){
			//如果运输类型为空运
			//派送方式(提货方式)
			errorInfoDto.setPickUpType(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSAIR,waybill.getReceiveMethod()));
		}else{
			//如果不为空运
			//提货方式
			String pickUpType = this.getDictionaryValue(ReportConstants.PICKUPGOODSHIGHWAYS,waybill.getReceiveMethod());
			if(StringUtils.isBlank(pickUpType)){
				//零担查不到时，可能为家装
				pickUpType = this.getDictionaryValue(ReportConstants.PICKUPGOODSSPECIALDELIVERYTYPE,waybill.getReceiveMethod());
			}
			//派送方式(提货方式)
			errorInfoDto.setPickUpType(StringUtils.isBlank(pickUpType) ? waybill.getReceiveMethod() : pickUpType);
		}
		//签收备注
		errorInfoDto.setSignNote(unnormalDto.getSignNote());
		//开单时间
		errorInfoDto.setSendGoodsTime(DateUtils.convert(waybill.getBillTime()));
		
		//查询经手部门
		//获取实际走货路径的所有部门编码(路径明细表里有时候没有流水号，所以不传流水号)
		List<String> handedDeptCodes = null;
		//如果为整车，责任部门取收货部门
		if(FossConstants.YES.equals(waybill.getIsWholeVehicle())){
			handedDeptCodes = new ArrayList<String>();
			handedDeptCodes.add(waybill.getReceiveOrgCode());
		}else{
			handedDeptCodes = calculateTransportPathService.queryPassDeptCodes(unnormalDto.getWaybillNo(),null);
		}
		//获取经手部门标杆编码 和 经手部门名称
		errorInfoDto.setGoodsHandleDepts(this.getHandedDepts(handedDeptCodes));
		
		//行业货源品类
		errorInfoDto.setGoodsSourceType(this.getDictionaryValue(ReportConstants.BSE_SOURCE_CATEGORY, actual.getIndustrySourceCategory()));

		String errorInfo = JSONObject.toJSONString(errorInfoDto);
		//返回json字符串
		return errorInfo;
	}
	
	/**
	 * 零担差错-查询QMS重大货物异常自动上报信息
	 * @author 306548-foss-honglujun
	 * 
	 */
	@Override
	public String getLDImportantErrorQmsInfo(WaybillEntity waybill,
			RecordErrorImportantWaybillResultDto recordErrorImportantWaybillResultDto){
		//判断运单实体是否为空
		if(waybill == null){
			LOGGER.error("上报零担重大货物异常失败，运单信息为空。");
			throw new SignException("运单信息为空。");
		}
		if(recordErrorImportantWaybillResultDto == null){
			LOGGER.error("上报零担重大货物异常失败，上报信息为空。");
			throw new SignException("上报信息为空。");
		}
		//重大货物异常信息实体
		RecordImportantErrorQmsWaybillDto errorInfoDto = new RecordImportantErrorQmsWaybillDto();

		//运单号
		errorInfoDto.setWayBillNum(waybill.getWaybillNo());
		
		//文件标准id
		errorInfoDto.setDocStandarId(null);
		//收货部门
		errorInfoDto.setReceiveDeptCode(this.getOrgByCode(waybill.getReceiveOrgCode(),false));
		//收货部门名字
		errorInfoDto.setReceiveDeptName(waybill.getReceiveOrgName());
		//查询上报人信息
		EmployeeEntity repEmp = employeeService.querySimpleEmployeeByEmpCode
				(recordErrorImportantWaybillResultDto.getOperateCode());
		//上报部门
		OrgAdministrativeInfoEntity repDept= orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCodeClean(recordErrorImportantWaybillResultDto.getOperateOrgCode());
		if(repEmp == null || repDept == null){
			LOGGER.error("上报零担重大货物异常差错失败，上报信息为空。");
			throw new SignException("上报人信息或上报部门信息为空。");
		}
		//上报人工号
		errorInfoDto.setRepEmpcode(repEmp.getEmpCode());
		//上报人姓名
		errorInfoDto.setRepEmpcode(repEmp.getEmpName());
		//上报人职位
		errorInfoDto.setRepEmpJob(repEmp.getTitleName());
		//上报人部门
		errorInfoDto.setRepDeptCode(repDept.getUnifiedCode());
		//上报人部门名称
		errorInfoDto.setRepDeptName(repDept.getName());
		//签收时间
		errorInfoDto.setSignTime(recordErrorImportantWaybillResultDto.getSignTime());

		//短少量
		/*errorInfoDto.setShortNum(recordErrorImportantWaybillResultDto.getAlittleShort());
		//外包装是否完好
		errorInfoDto.setPackageIsGood(recordErrorImportantWaybillResultDto.getPackingResult());
		//短少流水号
		errorInfoDto.setGoodsLoseFlowcode(recordErrorImportantWaybillResultDto.getSerialNo());*/
		//运输类型
		errorInfoDto.setTransType(this.getDictionaryValue
							(ReportConstants.TRANS_TYPE,waybill.getTransportType()));
		//返单类型
		errorInfoDto.setReturnBillType(this.getDictionaryValue
							(ReportConstants.RETURNBILLTYPE,waybill.getReturnBillType()));
		//是否集中接货
		errorInfoDto.setIsConcentReceiving(waybill.getPickupCentralized());
		//托运人(发货客户联系人)
		errorInfoDto.setShipper(waybill.getDeliveryCustomerContact());
		//运输性质
		errorInfoDto.setTransNature(this.getProductName(waybill.getProductCode()));
		//收货人电话(优先给手机号)
		if(waybill.getReceiveCustomerMobilephone() != null){
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerMobilephone());
		}else{
			errorInfoDto.setReceiverPhone(waybill.getReceiveCustomerPhone());
		}
		if(ReportConstants.TRANS_AIRCRAFT.equals(waybill.getTransportType())){
			//如果运输类型为空运
			//提货方式
			errorInfoDto.setPickUpType(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSAIR,waybill.getReceiveMethod()));
		}else{
			//如果不为空运
			//提货方式
			errorInfoDto.setPickUpType(this.getDictionaryValue
							(ReportConstants.PICKUPGOODSHIGHWAYS,waybill.getReceiveMethod()));
		}
		//储运事项
		errorInfoDto.setStorageTransport(waybill.getTransportationRemark());
		//重量
		errorInfoDto.setSumWeight(waybill.getGoodsWeightTotal());
		//体积
		errorInfoDto.setSumVolume(waybill.getGoodsVolumeTotal());
		//件数
		errorInfoDto.setSumNumber(waybill.getGoodsQtyTotal());
		//货物名称
		errorInfoDto.setGoodsName(waybill.getGoodsName());
		//发货时间(开单时间)
		errorInfoDto.setSendGoodsTime(DateUtils.convert(waybill.getBillTime()));
		//到达部门(提货网点,设置为标杆编码)
		errorInfoDto.setArriveDeptCode(this.getOrgByCode(waybill.getCustomerPickupOrgCode(),false));
		//到达部门名字
		errorInfoDto.setArriveDeptName(waybill.getCustomerPickupOrgName());
		//收货人(收货客户联系人)
		errorInfoDto.setReceiverName(waybill.getReceiveCustomerContact());
		//(开单)付款方式
		errorInfoDto.setPayType(this.getDictionaryValue
							(ReportConstants.SETTLEMENT__PAYMENT_TYPE,waybill.getPaidMethod()));
		//保险金额(保价声明价值)
		errorInfoDto.setSafeMoney(waybill.getInsuranceAmount());
		//签收时间(异常签收时间)
		errorInfoDto.setUnnormalSignTime(DateUtils.convert(recordErrorImportantWaybillResultDto.getSignTime()));
		//货物包装
		errorInfoDto.setGoodsPackage(waybill.getGoodsPackage());
		//运费总额
		errorInfoDto.setFreightSumFee(waybill.getTotalFee());
		//开单部门
		errorInfoDto.setBillingDeptCode(this.getOrgByCode(waybill.getCreateOrgCode(),false));
		//开单部门名字
		errorInfoDto.setBillingDeptName(this.getOrgByCode(waybill.getCreateOrgCode(),true));
		//错误类型
		errorInfoDto.setErrCategoty(ReportConstants.BUSINESSLTL);
		//差错类型编号
		errorInfoDto.setErrTypeId(ReportConstants.ERRORTYPEID);
		//事件经过
		errorInfoDto.setIncident(null);
		//根据运单号查询交接单
		List<HandOverBillEntity> handOverBills = 
				handOverBillService.queryHandOveredRecordsByWaybillNo(waybill.getWaybillNo());
		//车牌号
		String vehicleNo = recordErrorImportantWaybillResultDto.getVehicleNo();
		//取得最近的一次交接单
		if(handOverBills != null && handOverBills.size()>NumberConstants.NUMERAL_ZORE){
			HandOverBillEntity handOverBill = 
					handOverBills.get(handOverBills.size() - NumberConstants.NUMBER_1);
			//交接单号
			errorInfoDto.setTransferBill(handOverBill.getHandOverBillNo());
			if(StringUtils.isBlank(vehicleNo)){
				//设置车牌号
				vehicleNo = handOverBill.getVehicleNo();
			}
		}
		//车牌号
		errorInfoDto.setCarCode(vehicleNo);

		//查询经手部门
		List<String> serialNos = null;
		if(StringUtils.isNotBlank(recordErrorImportantWaybillResultDto.getSerialNo())){
			serialNos = Arrays.asList(recordErrorImportantWaybillResultDto.getSerialNo().split(","));
		}
		//获取实际走货路径的所有部门编码(路径明细表里有时候没有流水号，所以不传流水号)
		List<String> handedDeptCodes = 
				calculateTransportPathService.queryPassDeptCodes(waybill.getWaybillNo(),null);
		//剔除“零担丢货管理组”及“出发部门”
		if(CollectionUtils.isNotEmpty(handedDeptCodes)){
			Iterator<String> iterator = handedDeptCodes.iterator();
			OuterBranchParamsDto branch = new OuterBranchParamsDto();
			List<OuterBranchEntity> list = null;
			while(iterator.hasNext()){
				String orgCode = iterator.next();
				//代理网点编码
				branch.setAgentDeptCode(orgCode);
				//查询空运/偏线代理网点
				list = vehicleAgencyDeptDao.queryOuterBranchs(branch);
				//剔除“零担丢货管理组”、“出发部门”、"空运/偏线代理网点"
				if(THROW_GOODS_ORG_CODE.equals(orgCode)//剔除“零担丢货管理组”
						|| StringUtils.equals(orgCode, waybill.getReceiveOrgCode())//剔除“出发部门”
						|| CollectionUtils.isNotEmpty(list)){//空运/偏线代理网点
					iterator.remove();
				}
				list = null;
			}
			//司机所属部门(派送时，送货司机所在部门)
			handedDeptCodes.add(recordErrorImportantWaybillResultDto.getDriverOrgCode());
		}
		//获取经手部门标杆编码 和 经手部门名称
		String[] handedDept = this.getHandedDept(handedDeptCodes,true);
		if(handedDept != null){
			//经手部门标杆编码
			errorInfoDto.setGoodsHandleDeptCode(handedDept[0]);
			//经手部门名称
			errorInfoDto.setGoodsHandleDeptName(handedDept[1]);
			//责任部门标杆编码
			errorInfoDto.setRespDeptCode(handedDept[0]);
			//责任部门名字
			errorInfoDto.setRespDeptName(handedDept[1]);
			//责任部门负责人
			errorInfoDto.setRespDeptResperCode(handedDept[2]);
			//责任部门负责人姓名
			errorInfoDto.setRespDeptResperName(handedDept[SettlementReportNumber.THREE]);
			//责任人
			errorInfoDto.setRespEmpCode(handedDept[2]);
			//责任人姓名
			errorInfoDto.setRespEmpName(handedDept[SettlementReportNumber.THREE]);
		}
		String errorInfo = JSONObject.toJSONString(errorInfoDto);
		return errorInfo;
	}
	
	/**
	 * 得到手动上报差错信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-28 下午16:40:08
	 */
	public String getSignInfo(String waybillNo,String errorType){
		RecordQmsSignInfoDto dto = new RecordQmsSignInfoDto();
		
		//是否全部签收，默认为“N”
		dto.setIsSignStatusAll(FossConstants.NO);
		//是否正常签收，默认为“N”
		dto.setIsNormalSign(FossConstants.NO);
		//签收情况,默认为“未签收”
		dto.setSignSituation("未签收");
		//设置查询条件
		WaybillSignResultEntity resultEntity = 
				new WaybillSignResultEntity(waybillNo,FossConstants.ACTIVE);
		//返回签收结果信息
		resultEntity = waybillSignResultDao.queryWaybillSignResult(resultEntity);
		//如果签收结果不为空
		if(resultEntity != null){
			//签收时间
			dto.setSignTime(resultEntity.getSignTime());
			//签收状态为全部签收
			if(SignConstants.SIGN_STATUS_ALL.equals(resultEntity.getSignStatus())){
				//全部签收(“Y”)
				dto.setIsSignStatusAll(FossConstants.YES);
			}
			//如果为正常签收
			if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(resultEntity.getSignSituation())){
				//正常签收(“Y”)
				dto.setIsNormalSign(FossConstants.YES);
				//签收情况(将之换成中文)
				dto.setSignSituation("正常签收");
			}else{
				//查询到达联
				// 获得到达联LIST,通过运单号
				ArriveSheetDto ae = new ArriveSheetDto();
				// 运单号
				ae.setWaybillNo(waybillNo);
				// 已签收确认
				List<String> arriveSheetStatusList = new ArrayList<String>();
				arriveSheetStatusList.add(ArriveSheetConstants.STATUS_SIGN);
				// 已签收状态
				ae.setArriveSheetStatus(arriveSheetStatusList);
				//激活状态
				ae.setActive(FossConstants.ACTIVE);
				//非销毁状态
				ae.setDestroyed(FossConstants.NO);
				// 获得运单对应的有效到达联信息（有效的已签收到达联可能会有多个）
				List<ArriveSheetEntity> arriveSheetList = arrivesheetDao
															.queryArriveSheetByLifeCycle(ae);
				//用HashSet去除重复
				Set<String> signSituations = new HashSet<String>();
				//获取到达联的异常签收情况
				if(CollectionUtils.isNotEmpty(arriveSheetList)){
					for(ArriveSheetEntity arriveSheet : arriveSheetList){
						if(StringUtils.isNotBlank(arriveSheet.getSituation()) 
								&& !ArriveSheetConstants.SITUATION_NORMAL_SIGN.
														equals(arriveSheet.getSituation())){
							signSituations.add(arriveSheet.getSituation());
						}
					}
				}
				//到达联的异常签收情况不为空时
				if(CollectionUtils.isNotEmpty(signSituations)){
					if(signSituations.size() == 1){
						//将HashSet转换成ArrayList
						List<String> situations = new ArrayList<String>(signSituations);
						//签收情况(将之换成中文)
						dto.setSignSituation(this.getDictionaryValue
								(ReportConstants.PKP_SIGN_SITUATION,situations.get(0)));
					}else{
						//签收情况(将之换成中文)
						dto.setSignSituation("同票多类异常");
					}
				}else{
					//当到达联为空或到达联签收情况为空时，用签收结果表的签收情况
					dto.setSignSituation(this.getDictionaryValue
							(ReportConstants.PKP_SIGN_SITUATION,resultEntity.getSignSituation()));
				}
			}
		}
		try{
			//获取理赔应付单信息
			BigDecimal claimAmount = payableQueryForPkpService.queryClaimPayableBillByWaybill(waybillNo);
			//设置理赔金额
			dto.setClaimAmount(claimAmount);
		}catch(BusinessException e){
			LOGGER.error(e.getErrorCode());
			//未查询到数据或查询到多条数据时，理赔金额设为0
			dto.setClaimAmount(new BigDecimal(0));
		}
		//将dto转化成json字符串
		String dtoJson = JSONObject.toJSONString(dto);
		
		return dtoJson;
	}
	
	/**
	 * 根据组织编码，得到组织标杆编码或组织名称
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-4-16 上午9:31:26
	 * @return
	 */
	public String getOrgByCode(String code,boolean isResultName){
		if(code != null){
			//根据部门编号查询部门信息
			OrgAdministrativeInfoEntity org= 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			if(org != null){
				if(isResultName){
					return org.getName();
				}else{
					return org.getUnifiedCode();
				}
			}
		}
		return null;
	}
	
	/**
	 * 通过当前部门，查询负责人工号和名字
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-5-8 下午4:01:21
	 */
	public OrgAdministrativeInfoEntity getResponsible(OrgAdministrativeInfoEntity org){
		int i = 0;
		OrgAdministrativeInfoEntity orgParent = null;
		if(org != null){
			//当前部门负责人为空，且当前部门不为大区(为了防止死循环，限制查询6次)
			while(StringUtils.isBlank(org.getPrincipalNo()) && !FossConstants.YES.equals(org.getBigRegion())
					&& i<NumberConstants.NUMERAL_SIX){
				//根据上级组织标杆编码查询上级组织
				orgParent = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByUnifiedCodeClean(org.getParentOrgUnifiedCode());
				//上级组织为空
				if(orgParent == null){
					return org;
				}else if((orgParent != null && !StringUtils.isBlank(orgParent.getPrincipalNo()))
						|| FossConstants.YES.equals(orgParent.getBigRegion())){
					//上级组织不为空，且上级组织负责人不为空,或者上级组织是大区

					//将上级组织负责人工号填入当前组织
					org.setPrincipalNo(orgParent.getPrincipalNo());
					return org;
				}
				//将上级组织的上级标杆编码 填入 当前组织的上级标杆编码
				org.setParentOrgUnifiedCode(orgParent.getParentOrgUnifiedCode());
				i++;
			}
		}
		return org;
	}
	
	/**
	 * 获取经手部门标杆编码 和 经手部门名称 
	 * @param 231434-FOSS-bieyexiong
	 * @date 2016-6-22 下午9:08:23
	 */
	public List<Map<String,String>> getHandedDepts(List<String> handedDeptCodes){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(handedDeptCodes == null){
			Map<String,String> map = new HashMap<String, String>();
			map.put(ReportConstants.DEPT_STAND_CODE, HANDED_DEPT);
			map.put(ReportConstants.DEPT_NAME, HANDED_DEPT);
			list.add(map);
			return list;
		}
		for(String handedDeptCode : handedDeptCodes){
			//通过部门编号查询组织信息
			OrgAdministrativeInfoEntity org= 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(handedDeptCode);
			if(org != null){
				Map<String,String> map = new HashMap<String, String>();
				map.put(ReportConstants.DEPT_STAND_CODE, StringUtils.isBlank(org.getUnifiedCode())?HANDED_DEPT:org.getUnifiedCode());
				map.put(ReportConstants.DEPT_NAME, StringUtils.isBlank(org.getName())?HANDED_DEPT:org.getName());
				list.add(map);
			}
		}
		if(CollectionUtils.isEmpty(list)){
			Map<String,String> map = new HashMap<String, String>();
			map.put(ReportConstants.DEPT_STAND_CODE, HANDED_DEPT);
			map.put(ReportConstants.DEPT_NAME, HANDED_DEPT);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取经手部门标杆编码 和 经手部门名称 及 责任人工号 和 责任人名字
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-5-11 上午9:15:55
	 */
	public String[] getHandedDept(List<String> handedDeptCodes,boolean isQueryPrincipal) {
		if(handedDeptCodes == null){
			return null;
		}
		//经手部门
		StringBuilder handedCode = new StringBuilder();
		//经手部门名字
		StringBuilder handedName = new StringBuilder();
		//部门负责人
		StringBuilder principalNo = new StringBuilder();
		//部门负责人名字
		StringBuilder principalName = new StringBuilder();
		//经手部门数组
		String[] handedDept = new String[SettlementReportNumber.FOUR];
		
		for(String handedDeptCode : handedDeptCodes){
			//通过部门编号查询组织信息
			OrgAdministrativeInfoEntity org= 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(handedDeptCode);
			if(org != null){
				//将经手部门标杆编码通过“,”连接起来
				handedCode.append(org.getUnifiedCode()).append(",");
				//将经手部门名称通过“,”连接起来
				handedName.append(org.getName()).append(",");
				//如果要查责任人
				if(isQueryPrincipal){
					//如果部门负责人为空
					if(StringUtils.isBlank(org.getPrincipalNo())){
						//往上级部门查询部门负责人及其工号
						org = this.getResponsible(org);
					}
					//如果上级部门查到负责人，通过“,”连接起来
					if(StringUtils.isNotBlank(org.getPrincipalNo())){
						//部门负责人工号,通过“,”连接起来
						principalNo.append(org.getPrincipalNo()).append(",");
						//查到负责人工号时，继续查其名字
						EmployeeEntity emp = employeeService.querySimpleEmployeeByEmpCode(org.getPrincipalNo());
						if(emp != null){
							//部门负责人姓名,通过“，”连接起来
							principalName.append((emp.getEmpName())).append(",");
						}
					}
				}
			}
		}
		if(StringUtils.isNotBlank(handedCode) && StringUtils.isNotBlank(handedName)){
			//去掉最后一个逗号
			String handedDeptCode = handedCode.substring(0,handedCode.length() - 1);
			String handedDeptName = handedName.substring(0,handedName.length() - 1);
			//经手部门
			handedDept[0] = handedDeptCode;
			//经手部门名字
			handedDept[1] = handedDeptName;
		}
		if(StringUtils.isNotBlank(principalNo) && StringUtils.isNotBlank(principalName)){
			//去掉最后一个逗号
			String respDeptResperCode = principalNo.substring(0,principalNo.length() - 1);
			String respDeptResperName = principalName.substring(0,principalName.length() - 1);
			//责任部门负责人
			handedDept[2] = respDeptResperCode;
			//责任部门负责人名字
			handedDept[SettlementReportNumber.THREE] = respDeptResperName;
		}
		return handedDept;
	}
	
	/**
	 * 将数据字典内的值代码换成名称
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-4-23 上午11:34:13
	 * @return
	 */
	public String getDictionaryValue(String termsCode,String signSituation){
		//根据词条名和值编码，查询数据字典
		DataDictionaryValueEntity dictionary = 
				dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode
									(termsCode,signSituation);
		return dictionary == null ? null : dictionary.getValueName();
	}

	/**
	 * 根据产品编码 查询 产品名称
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-27 下午16:26:21
	 */
	public String getProductName(String productCode){
		//根据CODE查询产品实体集合
		List<ProductEntity> entitys = bseProductDao.queryProductsByCode(productCode);
		if(entitys != null && entitys.size()>ArriveSheetConstants.ZERO){
			ProductEntity entity = entitys.get(ArriveSheetConstants.ZERO);
			return entity == null ? null : entity.getName();
		}
		return null;
	}
	
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setBseProductDao(IProductDao bseProductDao) {
		this.bseProductDao = bseProductDao;
	}

	public void setVehicleAgencyDeptDao(IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
		this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}

	public void setPayableQueryForPkpService(
			IPayableQueryForPkpService payableQueryForPkpService) {
		this.payableQueryForPkpService = payableQueryForPkpService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
}
