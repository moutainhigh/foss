package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEamService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpressAutoMakeupService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpressAutoMakeupValidateService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IFossWithOthersForEmaService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmPaymentTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressAutoMakeupService implements IExpressAutoMakeupService {
	
	private static final int NUMBER_490 = 490;
	
	private static final int NUMBER_4000 = 4000;
	
	private static final double POINT_001 = 0.01;
	
	/**
	 * 运单管理接口
	 */
	IWaybillManagerService waybillManagerService;
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * 运单dao
	 */
	private IWaybillDao waybillDao;
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/**
	 * 运单开单服务
	 */
	IWaybillPickupService waybillPickupService;

	public void setWaybillPickupService(
			IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExpressAutoMakeupService.class);
	/**
	 * 快递自动补录校验Service
	 */
	IExpressAutoMakeupValidateService expressAutoMakeupValidateService;

	public void setExpressAutoMakeupValidateService(
			IExpressAutoMakeupValidateService expressAutoMakeupValidateService) {
		this.expressAutoMakeupValidateService = expressAutoMakeupValidateService;
	}

	/**
	 * 组织信息 Service接口
	 */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 人员 Service接口
	 */
	IEmployeeService employeeService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * 消息的服务接口
	 */
	private IMessageService  messageService;
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 用来操作交互"用户信息"的数据库对应数据访问Service接口
	 */
	IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * 快递车辆Service接口
	 */
	IExpressVehiclesService expressVehiclesService;

	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	private IEamDao eamDao;
	public void setEamDao(IEamDao eamDao) {
		this.eamDao = eamDao;
	}
	/**
	 * 客户合同信息Service接口
	 */
	ICusBargainService cusBargainService;

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	/**
	 * 营业部 Service接口
	 */
	ISaleDepartmentService saleDepartmentService;

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * 快递代理置服务类
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;

	public void setLdpAgencyDeptService(
			ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	/**
	 * 外场服务类
	 */
	private IOutfieldService outfieldService;

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	/**
	 * 库区服务类
	 */
	private IGoodsAreaService goodsAreaService;

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	/**
	 * 待处理运单主表
	 */
	private IWaybillPendingService waybillPendingService;

	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}

	/**
	 * 删除暂存表数据与插入日志记录的服务类
	 */
	private IEamService eamService;

	public void setEamService(IEamService eamService) {
		this.eamService = eamService;
	}

	/**
	 * 获取相关配置参数
	 */
	private ISysConfigService pkpsysConfigService;

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	/**
	 * 客户信息查询服务
	 * */
	private IQueryCustomerService queryCustomerService; 
	
    public void setQueryCustomerService(IQueryCustomerService queryCustomerService) {
		this.queryCustomerService = queryCustomerService;
	}
	/**
	 * 根据供应商补录的省市区信息查询对应的省市区Code
	 * */
	private IAdministrativeRegionsService  administrativeRegionsService; 
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 产品定义Service接口
	 */
	IProductService productService;
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	/**
	 * 客户信息Dao接口
	 */
	ICustomerDao customerDao;
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	/**
	 * 回传补录运单成功与否给录单中心
	 */
	IFossWithOthersForEmaService fossWithOthersForEmaService;
	
	public void setFossWithOthersForEmaService(
			IFossWithOthersForEmaService fossWithOthersForEmaService) {
		this.fossWithOthersForEmaService = fossWithOthersForEmaService;
	}
	/**
	 * 快递运单Service
	 */
	IWaybillExpressService waybillExpressService;	
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	
	//查询走货路径
	private IFreightRouteService freightRouteService ;
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}
	
	/**
	 * GUI查询走货路径相关服务接口
	 */
	IWaybillFreightRouteService waybillFreightRouteService;
	public void setWaybillFreightRouteService(
			IWaybillFreightRouteService waybillFreightRouteService) {
		this.waybillFreightRouteService = waybillFreightRouteService;
	}
	private ICusAccountService cusAccountService;
	
	public void setCusAccountService(ICusAccountService cusAccountService) {
		this.cusAccountService = cusAccountService;
	}
	/**
	 *  供应商补录失败消息
	 */
	public static final String MSG_TYPE__SUPPLIER_FAIL_PATCH = "SUPPLIER_FAIL_PATCH";
	/**
	 * 定时任务生成运单
	 * @author YangxiaoLong
	 * @date
	 */
	// 要查询的JobId
	public static final String queryJobId = WaybillConstants.UNKNOWN;
	@Override
	public void batchGenerateExpressWaybillJobs(){
		// 初始化时首先设置批处理条数为100条
		String queryNum = "500";
		ExpressAutoMakeupVo vo = new ExpressAutoMakeupVo();
		// 后台设置当前每个周期要处理的订单数，后期通过数据字典配置
		ConfigurationParamsEntity configQueryNum = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.PKP_EXPRESS_GENERATE_CYCLE,
						FossConstants.ROOT_ORG_CODE);
		if(configQueryNum != null && StringUtils.isNotBlank(configQueryNum.getConfValue())){
			queryNum = configQueryNum.getConfValue();
		}
		vo.setResultNum(Integer.parseInt(queryNum));
		while (vo.getResultNum() == Integer.parseInt(queryNum)) {
			String jobId = UUIDUtils.getUUID();
			// 更新一定数量的JobId
			vo = updateGenearateUnActiveEWaybillForJobTopNum(jobId, queryNum);
			// 根据JobId查询待处理信息
			// 此处注意更新一定数量的jobId然后根据Jobid查询待处理信息,如何将Jobid与待处理信息联系到一起的
			List<EamDto> list = eamService.queryGenerateUnActiveEamByJobID(vo.getJobId());
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					// 执行补录
					expressWaybillAutoMakeup(list.get(i));
				}
			}
		}
	}

	/**
	 * 每次更新一定数量待补录的运单的任务编号 JOBID
	 * @author YangxiaoLong
	 * @date 2015-07-08
	 */
	@Transactional
	public ExpressAutoMakeupVo updateGenearateUnActiveEWaybillForJobTopNum(
			String jobId, String queryNum) {
		ExpressAutoMakeupVo vo = new ExpressAutoMakeupVo();
		vo.setJobId(jobId);
		vo.setQueryNum(queryNum);
		vo.setQueryJobId(queryJobId);
		int result = eamService.updateJobIDTopByRowNum(vo);
		vo.setResultNum(result);
		return vo;
	}
	/**
	 * 快递自动补录
	 * @author 220125 YangXiaolong
	 * @date 2015-05-20
	 * @return 
	 *业务说明：原先PDA开完单后，在FOSS里的运单信息的补录由营业部营业员手动补录，这样营业员会花费大量的时间进行运单补录，耗费人力，
	 *物力，财力， 快递自动补录项目在业务上要实现的是，建立合作商家，将原先有营业员补录的信息对接给其他商家，由其他合作商家补录，
	 *补录后将补录的信息通过录单系统WICS传给FOSS， FOSS采用跑JOB的方法，实现定时自动补录商家推送的运单的信息。 补录流程说明：
	 *整个快递自动补录的流程采用和电子运单模块相同的逻辑，主要分以下三个模块：
	 *1.封装可校验的运单实体并计算相关费用 相关逻辑封装在generatePrePdaDto()方法 
	 *2.校验运单相关逻辑在validateExpressWaybill() 方法
	 *3.提交运单相关逻辑在submitExpressWaybill() 方法
	 * @throws Exception
	 */
	@Override
	public ResultDto expressWaybillAutoMakeup(EamDto eamDto){
		//信息返回结果实体
		ResultDto resultDto = new ResultDto();
		try {
			// 生成可以校验的运单实体,校验，计费
			WaybillDto waybillDto = generatePrePdaDto(eamDto);
			// 提交运单
			waybillManagerService.submitExpressWaybill(waybillDto);
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("自动补录成功！");
			eamService.handleOrderAndLog(eamDto,WaybillConstants.YES);
		} catch (BusinessException e) {
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("自动补录失败！");
			String message = "快递自动补录业务异常:";
			// 为了异常记录完全拼接以下异常信息
			String messagee = e.getMessage();
			message = message + messagee;
			String content="单号："+eamDto.getWaybillNo()+"，"+message+"....,请手动补录!";
			String reg=".*null.*";  
			//当捕获为null的业务异常时，为了站内消息显示做的处理
			if(message.matches(reg)){
				content="单号："+eamDto.getWaybillNo()+"，"+"系统异常,请手动补录!";
			}
			sendInnerMess(eamDto,content);
			String messageee="   栈信息："+ ExceptionUtils.getFullStackTrace(e);
			message=message+messageee;
			if (StringUtils.isNotEmpty(message) && message.length() > NUMBER_490) {
				eamDto.setRemarks("异常："+ message.substring(0, NUMBER_490));
			} else {
				eamDto.setRemarks("异常：" + message);
			}
			eamService.handleOrderAndLog(eamDto,WaybillConstants.NO);
			LOGGER.error("快递自动补录业务异常:" + "栈信息："
					+ ExceptionUtils.getFullStackTrace(e));
		} catch (Exception ee) {
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("自动补录失败！");
			String message = "快递自动补录系统异常Exception:";
			String excepMessage = ExceptionUtils.getFullStackTrace(ee);
			if (StringUtil.isNotBlank(excepMessage)) {
				message = message + excepMessage;
			}
			if (StringUtils.isNotEmpty(message) && message.length() > NUMBER_490) {
				eamDto.setRemarks("异常："+ message.substring(0, NUMBER_490));
			} else {
				eamDto.setRemarks("异常：" + message);
			}
			eamService.handleOrderAndLog(eamDto,WaybillConstants.NO);
			String content="单号："+eamDto.getWaybillNo()+"，"+"系统异常,请手动补录!";
			sendInnerMess(eamDto,content);
			LOGGER.error("快递自动补录系统异常Exception:" + ee.getMessage() + "栈信息："
					+ ExceptionUtils.getFullStackTrace(ee));
		} catch (Throwable able) {
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("自动补录失败！");
			String message = "快递自动补录系统异常Throwable:";
			String throwable = ExceptionUtils.getFullStackTrace(able);
			if (StringUtil.isNotBlank(throwable)) {
				message = message + throwable;
			}
			if (StringUtils.isNotEmpty(message) && message.length() > NUMBER_490) {
				eamDto.setRemarks("异常："+ message.substring(0, NUMBER_490));
			} else {
				eamDto.setRemarks("异常：" + message);
			}
			eamService.handleOrderAndLog(eamDto,WaybillConstants.NO);
			String content="单号："+eamDto.getWaybillNo()+"，"+"系统异常,请手动补录!";
			sendInnerMess(eamDto,content);
			LOGGER.error("快递自动补录系统异常Throwable:" + able.getMessage() + "栈信息："+ ExceptionUtils.getFullStackTrace(able));
		}
		return resultDto;
	}

	/**
	 * 将供应商补录的信息与暂存表的信息结合转化为可校验的运单
	 * @author liuCuoTao
	 * @date 2015-05-15
	 * @return
	 */
	private WaybillDto generatePrePdaDto(
			EamDto eamDto) {
		WaybillDto waybillDto = new WaybillDto();
		WaybillPendingEntity pendingEntity = null;
		WaybillEntity waybillEntity =waybillDao.queryWaybillByNo(eamDto.getWaybillNo());
		if(null!=waybillEntity 
			&& (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType()) 
		   || WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntity.getPendingType()))){
			throw new WaybillValidateException("运单已开单！");
		}
		if (null != eamDto.getWaybillNo()) {
			pendingEntity = waybillPendingService.queryPendingByNo(eamDto.getWaybillNo());
			if (pendingEntity == null) { 
				throw new WaybillValidateException("PDA未开单！");
			}
			if (!WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingEntity.getPendingType())) { 
				throw new WaybillValidateException("不是PDA开的运单！");
			}
		} else {
			throw new WaybillValidateException("供应商补录信息不能为空！");
		}
		if(null!=pendingEntity.getOrderNo()){
			WaybillEntity entity  = waybillDao.queryWaybillByOrderNo(pendingEntity.getOrderNo());
			if(entity!=null 
					&& 
			   (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(entity.getPendingType())
					 ||
			    WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(entity.getPendingType()))
			){
				throw new WaybillValidateException("该订单号已开单");
			}
		}
		//本项目不做返单
		if(pendingEntity!=null && pendingEntity.getReturnType()!=null 
			&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(pendingEntity.getReturnType())){
			throw new WaybillValidateException("运单为返单，请手动补录");
		}
		//本项目不做返货
		if(pendingEntity!=null && pendingEntity.getReturnType()!=null 
			&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(pendingEntity.getReturnType())){
			throw new WaybillValidateException("运单为返货，请手动补录");
		}
		//本项目不做电商尊享
		if(pendingEntity!=null && pendingEntity.getProductCode()!=null 
				&& WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(pendingEntity.getProductCode())){
			throw new WaybillValidateException("运单运输性质为电商尊享，请手动补录");
			}
		// 原件签收单返回
		if (pendingEntity!=null && pendingEntity.getReturnBillType()!=null 
				&&WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(pendingEntity.getReturnBillType())) {
			if(StringUtils.isEmpty(eamDto.getDeliveryCustomerProvCode()) || StringUtils.isEmpty(eamDto.getDeliveryCustomerCityCode())
					|| StringUtils.isEmpty(eamDto.getDeliveryCustomerAddress())){			
				throw new WaybillValidateException("签收单原件返回发货人地址不能为空");
			}
		}
		//一票货不允许超过4000件
		if(pendingEntity!=null && pendingEntity.getGoodsQtyTotal()!=null 
						&& pendingEntity.getGoodsQtyTotal() > NUMBER_4000){
					throw new WaybillValidateException("一票货不允许超过4000件");
		}
		//如果是内部带货，供应商传入的金额清0
		 if(null!=pendingEntity.getDeliveryCustomerCode() &&WaybillConstants.DEPPON_CUSTOMER.equals(pendingEntity.getDeliveryCustomerCode())){
			 eamDto.setCodAmount(new BigInteger("0"));
			 eamDto.setInsuranceAmount(new BigInteger("0"));
			 eamDto.setPackageFeeCanvas(new BigInteger("0"));
		 }
		//初始化运单entity
		waybillEntity=generateWaybillEntity(eamDto,pendingEntity);
		waybillDto.setWaybillEntity(waybillEntity);
		
		//将值赋给WaybillExpressEntity
		waybillDto.setWaybillExpressEntity(getWaybillExpressEntity(waybillEntity,pendingEntity));
		
		//currentInfo当前用户(根据用户工号查询currentInfo)
		waybillDto.setCurrentInfo(getWaybilCurrentInfo(waybillEntity));
		waybillDto.setOldWaybillNo(eamDto.getWaybillNo());
		// 校验运单实体===该方法加在下面的计费中
		// 计算费用，并填充waybillChargeDtlEntity、waybillDisDtlEntity信息；
		// woodenRequirementsEntity快递没有，不予处理
		expressAutoMakeupValidateService.calculateExpressAllFee(waybillDto,
				FossConstants.ACTIVE,eamDto);
		// actualFreightEntity运单冗余明细
		waybillDto.setActualFreightEntity(getActualFreightEntity(waybillEntity,pendingEntity,eamDto));
		
		//当有代收货款时设置开户行信息
		if(waybillEntity.getCodAmount() != null && waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
			CusAccountEntity cusAccountEntity=	expressAutoMakeupValidateService.queryEWaybillCusAccountInfo(waybillEntity);
			waybillDto.setOpenBank(cusAccountEntity);
			waybillDto.getWaybillEntity().setAccountBank(cusAccountEntity.getOpeningBankName());
		}
		return waybillDto;
	}
	/**
	 * 初始化运单实体
	 * @author LiuCuoTao搓
	 * @date 2015-07-15
	 */
	private WaybillEntity generateWaybillEntity(EamDto eamDto,WaybillPendingEntity pendingEntity){
		WaybillEntity waybillEntity=new WaybillEntity();
		//收货客户联系人 
		if(eamDto.getReceiveCustomerName()==null && StringUtils.isBlank(eamDto.getReceiveCustomerName())){
		   throw new WaybillValidateException("收货客户名称为必填项！");
		}
		//发货客户联系人
		if(eamDto.getDeliveryCustomerName()==null && StringUtils.isBlank(eamDto.getDeliveryCustomerName())){
			throw new WaybillValidateException("发货客户名称为必填项！");
		}
		//收货具体地址
		if(eamDto.getReceiveCustomerAddress()==null && StringUtils.isBlank(eamDto.getReceiveCustomerAddress())){
				throw new WaybillValidateException("收货具体地址为必填项！");
		}
		//托寄物内容
		if(eamDto.getGoodsName()==null && StringUtils.isBlank(eamDto.getGoodsName())){
				throw new WaybillValidateException("货物名称为必填项！");
		}
		
		//WaybillEntity基本信息
		setWaybillEntityBasicInfo(waybillEntity,eamDto,pendingEntity);
		//设置发货人信息
		setDeliveryCustomerInfo(waybillEntity,eamDto,pendingEntity);
		
		//设置收货人信息
		setReceiveCustomerInfo(waybillEntity,eamDto,pendingEntity);
		
		//设置收货部门
		waybillEntity.setReceiveOrgCode(pendingEntity.getReceiveOrgCode());
		
		//设置运输信息
		setTransportInfo(waybillEntity,eamDto,pendingEntity);
		
		//设置货物信息
		setGoodsInfo(waybillEntity,eamDto,pendingEntity);
		//设置产品类型
	   if(null!=pendingEntity.getProductCode() && StringUtil.isNotBlank(pendingEntity.getProductCode())){
		 if("RCP".equals(pendingEntity.getProductCode())||"PACKAGE".equals(pendingEntity.getProductCode())
				   ||"EPEP".equals(pendingEntity.getProductCode())){
			  waybillEntity.setProductCode(pendingEntity.getProductCode());
			  waybillEntity.setProductId(productService.getLevel3ProductInfo(waybillEntity.getProductCode()).getId()); 
		 }else{
			 throw new WaybillValidateException("运单不是快递产品！");
		 }
	   }else{
		   throw new WaybillValidateException("产品类型为空！");
	   }
		//设置目的站和走货路径
		setTargetOrgCode(waybillEntity,eamDto,pendingEntity);
			
		//设置配载信息
		setLoadInfo(waybillEntity,eamDto,pendingEntity);

		//设置增值服务及费用基础信息
		setFeeRelatedInfo(waybillEntity,eamDto,pendingEntity);
		//产品性质为电商尊享时的信息校验与封装
		//validateEpep(pendingEntity);
		return waybillEntity;
	}
	/**
	 * 校验是否可以享受电商尊享件
	 * @author 220125
	 * @date 2015-09-15
	 * @return
	 */
	private void validateEpep(WaybillPendingEntity pendingEntity){
	//判定是否可以享受电商尊享件
	if(StringUtils.isNotEmpty(pendingEntity.getProductCode()) && 
		WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(pendingEntity.getProductCode())){
		//付款方式是否为月结
		boolean isMonthEnd = false;
		if(StringUtils.isNotEmpty(pendingEntity.getPaidMethod()) && 
			CrmPaymentTypeEnum.MONTHPAY.getCrmCode().equals(pendingEntity.getPaidMethod())){
			isMonthEnd = true;
		}
		//如果不是月结，则抛出异常
		if(!isMonthEnd){
			//此处为打印，没得必要判空！
			LOGGER.info("运输性质为电商尊享件,但开单方式不为月结或到付，该订单号为:" + pendingEntity.getOrderNo());
			throw new WaybillValidateException("产品为电商尊享件时，只能开月结单和到付!");
		}
		//判定客户是否电商尊享客户
		boolean isNotSpecialCustomerEnjoy = false;
		if(StringUtils.isEmpty(pendingEntity.getDeliveryCustomerCode())){
			isNotSpecialCustomerEnjoy = true;
		}else{
			//默认是没有开的权利的
			isNotSpecialCustomerEnjoy = true;
			//获取合同实体
			CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(pendingEntity.getDeliveryCustomerCode());
			//合同为空,自然不是月结客户
			if(cusBargainEntity != null && DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(cusBargainEntity.getExPayWay())){
				isNotSpecialCustomerEnjoy = false;
			}else{
				//非月结客户不能开月结
				throw new WaybillValidateException("非月结客户付款方式不能为月结!");
			}
			//根据客户编码去查询是否有开电商尊享的权限
			if(cusBargainEntity != null && FossConstants.YES.equals(cusBargainEntity.getIfElecEnjoy())){
				isNotSpecialCustomerEnjoy = false;
			}else{
				LOGGER.info("运输性质为电商尊享件,开单方式为月结，但客户不为电商尊享客户，该订单号为:" + pendingEntity.getOrderNo());
				throw new WaybillValidateException("该客户不是电商尊享客户，不能开电商尊享单!");
			}
		}
		//校验是否有开电商尊享的权利
		if(isNotSpecialCustomerEnjoy){
			LOGGER.info("运输性质为电商尊享件,但客户没有电商尊享的权利，该订单号为:" + pendingEntity.getOrderNo());
			throw new WaybillValidateException("发货客户不是电商尊享客户，不能享受电商尊享优惠!");
		   }
	   }
	}
	
	/**
	 * 填充快递实体
	 * 
	 * @author 065340-LiuTao
	 * @date 2015-05-15
	 */
	private WaybillExpressEntity getWaybillExpressEntity(
			WaybillEntity waybillEntity,WaybillPendingEntity pendingEntity) {
		if (waybillEntity != null) {
			WaybillExpressEntity waybillExpressEntity = new WaybillExpressEntity();
			if (StringUtils.isNotBlank(waybillEntity.getWaybillNo())) {
				waybillExpressEntity.setWaybillNo(waybillEntity.getWaybillNo());
			}
			waybillExpressEntity.setDeliveryCustomerProvCode(waybillEntity.getDeliveryCustomerProvCode());
			waybillExpressEntity.setDeliveryCustomerCityCode(waybillEntity.getDeliveryCustomerCityCode());
			waybillExpressEntity.setDeliveryCustomerDistCode(waybillEntity.getDeliveryCustomerDistCode());
			waybillExpressEntity.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
			waybillExpressEntity.setReceiveCustomerName(waybillEntity.getReceiveCustomerName());
			waybillExpressEntity.setReceiveCustomerPhone(waybillEntity.getReceiveCustomerPhone());
			waybillExpressEntity.setReceiveCustomerContact(waybillEntity.getReceiveCustomerContact());
			waybillExpressEntity.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
			waybillExpressEntity.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
			waybillExpressEntity.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerDistCode());
			waybillExpressEntity.setVolumeWeight(waybillEntity.getBillWeight());
			waybillExpressEntity.setReceiveCustomerAddress(StringUtils
					.substring(waybillEntity.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
			waybillExpressEntity.setReceiveCustomerAddress(StringUtils
					.substring(waybillEntity.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
			waybillExpressEntity.setReceiveMethod(waybillEntity.getReceiveMethod());
			waybillExpressEntity.setTargetOrgCode(waybillEntity.getTargetOrgCode());
			waybillExpressEntity.setBillTime(waybillEntity.getBillTime());
			waybillExpressEntity.setCreateOrgCode(waybillEntity.getCreateOrgCode());
			waybillExpressEntity.setCustomerPickupOrgCode(waybillEntity.getCustomerPickupOrgCode());
			waybillExpressEntity.setCustomerPickupOrgName(waybillEntity.getCustomerPickupOrgName());
			
			//快递开单默认支持所有的都可以做返货 
			waybillExpressEntity.setCanReturnCargo(FossConstants.YES);
			// 是否补码
			waybillExpressEntity.setIsAddCode(FossConstants.NO);

			waybillExpressEntity.setExpressEmpCode(waybillEntity.getDriverCode());
			EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(waybillEntity.getDriverCode());
			if (employeeEntity != null) {
				waybillExpressEntity.setExpressEmpName(employeeEntity.getEmpName());
				waybillExpressEntity.setExpressOrgCode(employeeEntity.getOrgCode());
				waybillExpressEntity.setExpressOrgName(employeeEntity.getOrgName());
			}
			waybillExpressEntity.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
			waybillExpressEntity.setReceiveOrgName(waybillEntity.getReceiveOrgName());
			waybillExpressEntity.setReturnType(pendingEntity.getReturnType());
			waybillExpressEntity.setOriginalWaybillNo(pendingEntity.getOriginalWaybillNo());
			//内部带货发货人工号和费用承担部门
			 if(null!=pendingEntity.getDeliveryCustomerCode() &&WaybillConstants.DEPPON_CUSTOMER.equals(pendingEntity.getDeliveryCustomerCode())
					 &&null!=pendingEntity.getInnerNotes()){
				 String sendEmployeeCode="";
				 try {
					   //存内部带货发货人工号时格式为发货人工号：172388;
					   sendEmployeeCode=pendingEntity.getInnerNotes().substring(pendingEntity.getInnerNotes().indexOf("：")+1,  pendingEntity.getInnerNotes().indexOf(";"));
				} catch (Exception e) {
					throw new WaybillValidateException("未获取到内部发货人工号");
				}
				EmployeeEntity entitys = employeeService.queryEmployeeByEmpCode(sendEmployeeCode);
				waybillExpressEntity.setDeliveryEmployeeCode(sendEmployeeCode);
				if(entitys!=null && entitys.getOrgCode()!=null){
					waybillExpressEntity.setInnerPickupFeeBurdenDept(entitys.getOrgCode());
				}
			}
			return waybillExpressEntity;
		}
		return null;
	}
	/**
	 * 获得当前用户信息
	 * 
	 * @author YangXiaolong
	 * @date 2015-05-15
	 */
	public CurrentInfo getWaybilCurrentInfo(WaybillEntity entity) {

		CurrentInfo currentInfo = null;

		// 创建人（快递员）
		UserEntity user = userService
				.queryUserByEmpCode(entity.getDriverCode());

		// 必须通过快递员来查其车辆，进而匹配组织部门，不能通过车牌号，因为在激活时，有可能快递员更改，但是车牌号没更改的情况
		ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
		expressVehiclesEntityPara.setEmpCode(entity.getDriverCode());
		expressVehiclesEntityPara.setActive(WaybillConstants.YES);
		List<ExpressVehiclesEntity> expressVehiclesEntityList = expressVehiclesService
				.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
		// ExpressVehiclesEntityList!=nul
		if (expressVehiclesEntityList.size() > 0) {
			ExpressVehiclesEntity expressVehiclesEntityResult = expressVehiclesEntityList.get(0);
			if (StringUtils.isNotBlank(expressVehiclesEntityResult.getOrgCode())) {
				// 获取组织
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(expressVehiclesEntityResult.getOrgCode());
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(entity.getDriverCode());

				user.setEmployee(employee);
				currentInfo = new CurrentInfo(user, org);
			}
		}
		return currentInfo;
	}

	/**
	 * 填充实际承运信息
	 * @author 065340-LiuTao
	 * @date 2015-05-15
	 */
	private ActualFreightEntity getActualFreightEntity(WaybillEntity waybillEntity, WaybillPendingEntity waybillPendingEntity,EamDto eamDto) {
		if (waybillEntity != null) {
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			// 是否快递集中录单
			actualFreightEntity.setIsExpressFocus("Y");
			// 开始开单时间
			actualFreightEntity.setStartBillTime(waybillEntity.getCreateTime());
			// 运单号
			actualFreightEntity.setWaybillNo(waybillEntity.getWaybillNo());
			// 货物名称
			if (StringUtils.isNotEmpty(waybillEntity.getGoodsName())) {
				actualFreightEntity.setGoodsName(waybillEntity.getGoodsName());
			}
			// 重量
			actualFreightEntity.setWeight(waybillEntity.getBillWeight());
			// 体积
			if (waybillEntity.getGoodsVolumeTotal() != null && waybillEntity.getGoodsVolumeTotal().doubleValue() > 0) {
				actualFreightEntity.setVolume(waybillEntity.getGoodsVolumeTotal());
			} else {
				actualFreightEntity.setVolume(BigDecimal.valueOf(POINT_001));
			}
			// 件数
			actualFreightEntity.setGoodsQty(waybillEntity.getGoodsQtyTotal());
			// 尺寸
			actualFreightEntity.setDimension(StringUtils.isNotEmpty(waybillEntity.getGoodsSize()) ? waybillEntity
					.getGoodsSize() : "1*1*1*1");
			// 保险声明价值
			actualFreightEntity.setInsuranceValue(waybillEntity.getInsuranceAmount());
			// 包装费
			actualFreightEntity.setPackingFee(waybillEntity.getPackageFee() != null ? waybillEntity
							.getPackageFee() : BigDecimal.valueOf(0));
			// 送货费
			actualFreightEntity.setDeliverFee(waybillEntity.getDeliveryGoodsFee() != null ? waybillEntity
					.getDeliveryGoodsFee() : BigDecimal.valueOf(0));
			// 装卸费
			actualFreightEntity.setLaborFee(waybillEntity.getServiceFee() != null ? waybillEntity
							.getServiceFee() : BigDecimal.valueOf(0));
			// 代收货款
			actualFreightEntity.setCodAmount(waybillEntity.getCodAmount() != null ? waybillEntity
							.getCodAmount() : BigDecimal.valueOf(0));
			// 增值费
			actualFreightEntity.setValueaddFee(waybillEntity.getValueAddFee() != null ? waybillEntity
							.getValueAddFee() : BigDecimal.valueOf(0));
			// 公布价运费
			actualFreightEntity.setFreight(waybillEntity.getTransportFee() != null ? waybillEntity
							.getTransportFee() : BigDecimal.valueOf(0));
			// 结清状态
			actualFreightEntity.setSettleStatus(FossConstants.NO);
			// 结清时间
			actualFreightEntity.setSettleTime(new Date());
			// 实际送货方式
			actualFreightEntity.setActualDeliverType(waybillEntity.getReceiveMethod());
			// 运单状态
			actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);

			// 设置出发部门
			actualFreightEntity.setStartStockOrgCode(waybillEntity.getCreateOrgCode());
			// 获取最终库存部门和货区
			actualFreightEntity = queryEndStocksDepartmentService(waybillEntity, actualFreightEntity);

			// 整车开单的时候 对actual Freight表的字段做如下调整
			if (waybillEntity.getIsWholeVehicle() != null && WaybillConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
				// 1 ARRIVE_GOODS_QTY = 开单件数
				actualFreightEntity.setArriveGoodsQty(waybillEntity.getGoodsQtyTotal());
				// 2 ARRIVE_NOTOUT_GOODS_QTY 到达未签收件数 = 开单件数
				actualFreightEntity.setArriveNotoutGoodsQty(waybillEntity.getGoodsQtyTotal());
				// 3 若预计到达时间 不为空则为预计到达时间，否则为当前时间
				// 预计到达时间
				Date preArrivedTime = waybillEntity.getPreCustomerPickupTime();
				if (null != preArrivedTime) {
					actualFreightEntity.setArriveTime(preArrivedTime);
				} else {
					actualFreightEntity.setArriveTime(new Date());
				}
			}
			// 短信标识
			actualFreightEntity.setIsSMS(WaybillConstants.NO);
			// 快递优惠类型
			if (StringUtil.isNotEmpty(waybillEntity.getSpecialOffer())) {
				actualFreightEntity.setSpecialOffer(waybillEntity.getSpecialOffer());
			}
			// 发票标记
			actualFreightEntity.setInvoice(WaybillConstants.INVOICE_02);
			// 设置是否统一结算标志
			// 发货人客户编码
			if (StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCode())) {
				CusBargainEntity deliveryrCustBargaon = cusBargainService
						.queryCusBargainByCustCode(waybillEntity
								.getDeliveryCustomerCode());
				if (deliveryrCustBargaon != null) {
						actualFreightEntity.setStartCentralizedSettlement(deliveryrCustBargaon.getAsyntakegoodsCode());
						actualFreightEntity.setStartContractOrgCode(deliveryrCustBargaon.getUnifiedCode());
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(deliveryrCustBargaon.getUnifiedCode());
						actualFreightEntity.setStartContractOrgName(orgAdministrativeInfoEntity.getName());
						actualFreightEntity.setStartReminderOrgCode(deliveryrCustBargaon.getHastenfunddeptCode());
					   if(!"CT".equals(waybillPendingEntity.getPaidMethod()) 
					    && !"FC".equals(waybillPendingEntity.getPaidMethod()) && "Y".equals(deliveryrCustBargaon.getAsyntakegoodsCode())){
						   throw new WaybillValidateException("发货客户是统一结算时，付款方式只能为月结或到付！");
					   }
				 }else {
						actualFreightEntity.setStartCentralizedSettlement("");
						actualFreightEntity.setStartContractOrgCode("");
						actualFreightEntity.setStartContractOrgName("");
						actualFreightEntity.setStartReminderOrgCode("");
				}
			}
			// 收货人客户编码
			if (StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCode())) {
				CusBargainEntity receiverCustBargaon = cusBargainService
						.queryCusBargainByCustCode(waybillEntity
								.getReceiveCustomerCode());
				if (receiverCustBargaon != null) {
						actualFreightEntity.setArriveCentralizedSettlement(receiverCustBargaon.getAsyntakegoodsCode());
						actualFreightEntity.setArriveContractOrgCode(receiverCustBargaon.getUnifiedCode());
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(receiverCustBargaon.getUnifiedCode());
						actualFreightEntity.setArriveContractOrgName(orgAdministrativeInfoEntity.getName());
						actualFreightEntity.setArriveReminderOrgCode(receiverCustBargaon.getHastenfunddeptCode());
				} else {
						actualFreightEntity.setArriveCentralizedSettlement("");
						actualFreightEntity.setArriveContractOrgCode("");
						actualFreightEntity.setArriveContractOrgName("");
						actualFreightEntity.setArriveReminderOrgCode("");
				}
			}
			//收货镇编码设置    
			List<AdministrativeRegionsEntity> administrativeRegionsEntity1=null;
			if(null!=eamDto.getReceiveCustomerTownCode()){
				try {
					//如果录单中心传过来的是数字，则是编码
					Integer.parseInt(eamDto.getReceiveCustomerTownCode());
					actualFreightEntity.setReceiveCustomerVillageCode(eamDto.getReceiveCustomerTownCode());
				} catch (Exception e) {
					////如果录单中心传过来的不是数字，则是名字
					administrativeRegionsEntity1=administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(waybillEntity.getReceiveCustomerDistCode());
					if(administrativeRegionsEntity1!=null){
						for( AdministrativeRegionsEntity a :administrativeRegionsEntity1){
							if(a.getName().equals(eamDto.getReceiveCustomerTownCode())){
								actualFreightEntity.setReceiveCustomerVillageCode(a.getCode());
								waybillEntity.setReceiveCustomerVillageCode(a.getCode());
								break;
							}
						}
					}
					e.printStackTrace();
				}
			}
			//设置是否为次日补录到实际承运表中
			setIsNextPending(actualFreightEntity,waybillPendingEntity);
			return actualFreightEntity;
		}
		return null;
	}

	/**
	 * 获取提货网点组织
	 * @author 065340-LiuTao
	 * @date 2015-05-15
	 * @return
	 */
	public ActualFreightEntity queryEndStocksDepartmentService(
			WaybillEntity waybillEntity, ActualFreightEntity actualFreightEntity) {
		String productCode = waybillEntity.getProductCode();
		/**
		 * 获取最终配载部门
		 */
		String lastLoadOrgCode = waybillEntity.getLastLoadOrgCode();

		String goodsAreaCode = null;
		String endStockOrgCode = null;
		String errorMessage = "";
		if (lastLoadOrgCode == null) {
			throw new WaybillValidateException("最终配载部门不能为空！");
		}

		// 如果不是偏线或者不是空运时：如果最终配载部门是驻地部门
		if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
				.equals(productCode)
				&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
						.equals(productCode)
				&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE
						.equals(productCode)
				&& !ExpWaybillConstants.ROUND_COUPON_PACKAGE
						.equals(productCode)) {
			SaleDepartmentEntity saleDepartment = saleDepartmentService
					.querySaleDepartmentByCode(
							waybillEntity.getLastLoadOrgCode(),
							waybillEntity.getBillTime());
			if (saleDepartment == null) {
				throw new WaybillStoreException(
	        WaybillStoreException.DEPT_NULL, new Object[] { "【"+ waybillEntity.getLastLoadOrgCode() + "】" });
			}
			// 是否驻地部门
			if (saleDepartment.checkStation()) {
				endStockOrgCode = saleDepartment.getTransferCenter();
				errorMessage = "驻地部门" + saleDepartment.getName() + "对应的外场";
				goodsAreaCode = getGoodsAreaCode(endStockOrgCode,
				DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION,errorMessage);
			} else {
				endStockOrgCode = lastLoadOrgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
			}
		}
		// 如果是经济快递
		else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE
				.equals(productCode)
				|| ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(productCode)) {
			// 快递最终外场
			String lastOutFieldCode = waybillManagerService.queryLastOutFieldCode(waybillEntity);

			// 快递代理信息
			OuterBranchExpressEntity entity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
			waybillEntity.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
			// 判断是不是快递代理
			if (null != entity) {
				// 是快递代理，则最终库存部门是最终外场
				endStockOrgCode = lastOutFieldCode;
				errorMessage = "快递代理对应的外场";
				if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE
						.equals(productCode)) {
					goodsAreaCode = getExpressGoodsAreaCode(endStockOrgCode,
							ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,errorMessage);

				} else {
					goodsAreaCode = getExpressGoodsAreaCode(endStockOrgCode,
	                       ExpWaybillConstants.ROUND_COUPON_PACKAGE,errorMessage);
				}
			} else {
				// 提货网点所属组织
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
				if (null == org) {
					throw new WaybillStoreException(
				              WaybillStoreException.DEPT_NULL,
				new Object[] { "【"+ waybillEntity.getLastLoadOrgCode() + "】" });
				}

				// 判断是否为虚拟营业部
				if (FossConstants.YES.equals(org.getExpressSalesDepartment())) {
					// 虚拟营业部对应的库存部门为最终外场
					endStockOrgCode = lastOutFieldCode;
				} else {
					// 是否为营业部
					SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(
									waybillEntity.getLastLoadOrgCode(),waybillEntity.getBillTime());
					// 不是营业部
					if (saleDepartment == null) {
						throw new WaybillStoreException("不是快递代理，也不是营业部，数据有问题！");
					}
					// 是营业部
					else {
						// 是否驻地部门
						if (saleDepartment.checkStation()) {
							endStockOrgCode = saleDepartment.getTransferCenter();
							errorMessage = "驻地部门" + saleDepartment.getName()+ "对应的外场";
							goodsAreaCode = getGoodsAreaCode(endStockOrgCode,
							DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION,errorMessage);
						} else {
							endStockOrgCode = lastLoadOrgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
						}
					}
				}
			}
		}
		// 如果是偏线
		else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
				.equals(productCode)) {
			endStockOrgCode = lastLoadOrgCode;// 最终库存部门是最终配载部门
			errorMessage = "偏线对应的外场";
			goodsAreaCode = getGoodsAreaCode(endStockOrgCode,
			DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER,errorMessage);// 偏线货区编码

		}
		// 如果是空运
		else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
				.equals(productCode)) {
			endStockOrgCode = outfieldService.queryTransferCenterByAirDispatchCode(lastLoadOrgCode);// 获取空运对应的外场
			errorMessage = "空运总调对应的外场";
			goodsAreaCode = getGoodsAreaCode(endStockOrgCode,
			DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT,errorMessage);// 获取空运库区
		}

		actualFreightEntity.setGoodsAreaCode(goodsAreaCode);// 设置最终库存部门
		actualFreightEntity.setEndStockOrgCode(endStockOrgCode);// 设置对应库区

		if (actualFreightEntity.getStartStockOrgCode() == null
				|| "".equals(actualFreightEntity.getStartStockOrgCode())) {
			throw new WaybillStoreException("生成库存时，传入的始发库存部门为空");
		}
		return actualFreightEntity;
	}

	/**
	 * 获取库区编码
	 * @author 065340-LiuTao
	 * @date 2015-05-15
	 */
	private String getGoodsAreaCode(String transferCenter, String type,
			String errorMessage) {
		List<GoodsAreaEntity> goodsAreas = goodsAreaService
				.queryGoodsAreaListByType(transferCenter, type);
		if (goodsAreas == null || goodsAreas.isEmpty()) {
			throw new WaybillValidateException(
					WaybillValidateException.GOODS_AREAS_NULL,
					new Object[] { errorMessage });
		} else {
			GoodsAreaEntity goodsAreaEntity = goodsAreas.get(0);
			if (goodsAreaEntity.getGoodsAreaCode() == null) {
				throw new WaybillValidateException("对应库区编码为空！");
			} else {
				return goodsAreaEntity.getGoodsAreaCode();
			}
		}
	}

	/**
	 * 获取库区编码
	 * @author 065340-LiuTao
	 * @date 2015-05-15
	 */
	private String getExpressGoodsAreaCode(String transferCenter, String type,
			String errorMessage) {
		/**
		 * 通过获取服务，获取货区
		 */
		GoodsAreaEntity goodsArea = goodsAreaService
				.queryExpressGoodsAreaByTransCenterCode(transferCenter, type);
		if (goodsArea == null) {
			throw new WaybillValidateException(
					WaybillValidateException.GOODS_AREAS_NULL,
					new Object[] { errorMessage });
		} else {
			/**
			 * 获取货区编码
			 */
			if (StringUtils.isEmpty(goodsArea.getGoodsAreaCode())) {
				throw new WaybillValidateException("对应库区编码为空！");
			} else {
				/**
				 * 返回获取编码
				 */
				return goodsArea.getGoodsAreaCode();
			}
		}
	}
	/**
 	 * 根据发货人手机/电话号码查询客户编码
 	 * @author Foss-065340-LiuTao
	 * @date 2015-07-10
 	 * 
 	 * */
 	private  List<CustomerQueryConditionDto> queryDeliveryCustomer(EamDto expressAutomaticMakeup) {
 		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 手机
		String mobilePhone = StringUtil.defaultIfNull(expressAutomaticMakeup.getDeliveryCustomerMobilePhone());
		// 电话
		String telePhone = StringUtil.defaultIfNull(expressAutomaticMakeup.getDeliveryCustomerPhone());
		// 解析多个电话号码的查询
		Map<String, List<String>> map = null;
		// 将电话号码中的手机号码解析出来
		List<String> mobiles = null;
		// 电话集合
		List<String> phones = null;
		// 判断手机是否为空
		if (StringUtils.isNotEmpty(mobilePhone)) {
			mobiles = new ArrayList<String>();
			mobiles.add(mobilePhone);

			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setMobilePhone(mobilePhone);
			dto.setExactQuery(true);
			dtoList.add(dto);
		}
		// 判断电话是否为空
		else if (StringUtils.isNotEmpty(telePhone)) {
			map = resolveMobileAndPhone(telePhone);
			mobiles = map.get("mobiles");
			phones = map.get("phones");

			CustomerQueryConditionDto dto = null;
			// 手机不为空时
			if (CollectionUtils.isNotEmpty(mobiles)) {
				for (String mobile : mobiles) {
					dto = new CustomerQueryConditionDto();
					dto.setMobilePhone(mobile);
					dto.setExactQuery(true);
					dtoList.add(dto);
				}
			}
			// 电话不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				for (String phone : phones) {
					dto = new CustomerQueryConditionDto();
					dto.setContactPhone(phone);
					dto.setExactQuery(true);
					dtoList.add(dto);
				}
			}
		}
		
		List<CustomerQueryConditionDto> contactList = queryCustomerService.queryCustomerByConditionList(dtoList);
//		List<CustomerQueryConditionDto> contactLists=new ArrayList<CustomerQueryConditionDto>();
		// 若CRM中查询无果，则从历史客户中查询
		if (CollectionUtils.isEmpty(contactList)) {
			if (CollectionUtils.isNotEmpty(mobiles)) {
				// 将手机中的号码加入集合进行查询
				mobiles.add(mobilePhone);
				List<HisDeliveryCusEntity> custHisMobile = queryCustomerService.queryHisDeliveryCusByMobileList(mobiles);
				List<CustomerQueryConditionDto> custMobile=convertDeliveryCustToConditionDto(custHisMobile);
				if (CollectionUtils.isNotEmpty(custMobile)) {
					if (CollectionUtils.isEmpty(contactList)) {
						contactList = custMobile;
					} else {
						contactList.addAll(custMobile);
					}
				}
			}
			// 电话号码不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				List<HisDeliveryCusEntity> custHisPhone = queryCustomerService.queryHisDeliveryCusByPhoneList(phones);
				List<CustomerQueryConditionDto> custPhone=convertDeliveryCustToConditionDto(custHisPhone);
				if (CollectionUtils.isNotEmpty(custPhone)) {
					if (CollectionUtils.isEmpty(contactList)) {
						contactList = custPhone;
					} else {
						contactList.addAll(custPhone);
					}
				}
			}
		}
//		else{
//			for(CustomerQueryConditionDto contact:contactList){
//				if(null!=contact.getCustCode()){
//					contactLists.add(contact);
//					break;
//				}
//			}
//			contactLists=contactList;
//		}
		return contactList;
 	}
 	/**
 	 * 根据收货人手机/电话号码查询客户编码
 	 * @author Foss-065340-LiuTao
	 * @date 2015-07-10
 	 * 
 	 * */
 	private  List<CustomerQueryConditionDto> queryReceiveCustomer(EamDto expressAutomaticMakeup) {
 		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 手机
		String mobilePhone = StringUtil.defaultIfNull(expressAutomaticMakeup.getReceiveCustomerMobilePhone());
		// 电话
		String telePhone = StringUtil.defaultIfNull(expressAutomaticMakeup.getReceiveCustomerPhone());
		// 解析多个电话号码的查询
		Map<String, List<String>> map = null;
		// 将电话号码中的手机号码解析出来
		List<String> mobiles = null;
		// 电话集合
		List<String> phones = null;
		// 判断手机是否为空
		if (StringUtils.isNotEmpty(mobilePhone)) {
			mobiles = new ArrayList<String>();
			mobiles.add(mobilePhone);

			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setMobilePhone(mobilePhone);
			dto.setExactQuery(true);
			dtoList.add(dto);
		}
		// 判断电话是否为空
		else if (StringUtils.isNotEmpty(telePhone)) {
			map = resolveMobileAndPhone(telePhone);
			mobiles = map.get("mobiles");
			phones = map.get("phones");

			CustomerQueryConditionDto dto = null;
			// 手机不为空时
			if (CollectionUtils.isNotEmpty(mobiles)) {
				for (String mobile : mobiles) {
					dto = new CustomerQueryConditionDto();
					dto.setMobilePhone(mobile);
					dto.setExactQuery(true);
					dtoList.add(dto);
				}
			}
			// 电话不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				for (String phone : phones) {
					dto = new CustomerQueryConditionDto();
					dto.setContactPhone(phone);
					dto.setExactQuery(true);
					dtoList.add(dto);
				}
			}
		}
		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = queryCustomerService.queryCustomerByConditionList(dtoList);
		List<CustomerQueryConditionDto> contactLists=new ArrayList<CustomerQueryConditionDto>();
		// 若CRM中查询无果，则从历史客户中查询
		if (CollectionUtils.isEmpty(contactList)) {
			if (CollectionUtils.isNotEmpty(mobiles)) {
				// 将手机中的号码加入集合进行查询
				mobiles.add(mobilePhone);
				List<HisReceiveCusEntity> custHisMobile = queryCustomerService.queryHisReceiveCusByMobileList(mobiles);
				List<CustomerQueryConditionDto> custMobile=convertReceiveCustToConditionDto(custHisMobile);
				if (CollectionUtils.isNotEmpty(custMobile)) {
					for(CustomerQueryConditionDto cust:custMobile){
						if(null!=cust.getCustCode()){
							contactLists.add(cust);
							break;
						}
					}
				}
			}
			// 电话号码不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				List<HisReceiveCusEntity> custHisPhone = queryCustomerService.queryHisReceiveCusByPhoneList(phones);
				List<CustomerQueryConditionDto> custPhone=convertReceiveCustToConditionDto(custHisPhone);
				if (CollectionUtils.isNotEmpty(custPhone)) {
						for(CustomerQueryConditionDto List:contactList){
							if(null!=List.getCustCode()){
								contactLists.add(List);
								break;
							}
						}
				}
			}
		}else{
			for(CustomerQueryConditionDto contact:contactList){
				if(null!=contact.getCustCode()){
					contactLists.add(contact);
					break;
				}
			}
		}
		return contactLists;
 	}
 	/**
	 * 解析字符中的电话号码和手机号码
	 * @author Foss-065340-LiuTao
	 * @date 2015-07-10
	 */
	private Map<String, List<String>> resolveMobileAndPhone(String phone) {
		// 存放解析后的手机和电话号码
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		// 分隔字符串
		List<String> strList = resolvePhone(phone);
		// 电话号码
		List<String> phoneList = new ArrayList<String>();
		// 手机号码
		List<String> mobileList = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(strList)) {
			// 将解析出的字符串进行分类
			for (String str : strList) {
				if (isMobileNO(str)) {
					mobileList.add(str);
				} else {
					phoneList.add(str);
				}
			}
		}

		map.put("mobiles", mobileList);
		map.put("phones", phoneList);
		return map;
	}
    /**
     * 对传入的电话号码根据“、”、“/”、“,”进行解析
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-16 下午8:36:57
     */
	private List<String> resolvePhone(String phone) {
		if (phone == null || "".equals(phone)) {
		    return null;
		}
		phone = phone.replace('，', ',');
		String[] str = phone.split("、|,|/");
		return Arrays.asList(str);
    }
    /**
    * 判断是否为手机号码
    * @author Foss-065340-LiuTao
	* @date 2015-07-10
	*/
	private boolean isMobileNO(String mobiles) {
		String mobile = "1[0-9]{10}";
		Pattern p = Pattern.compile(mobile);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	 * 将HisDeliveryCusEntity集合转换为CustomerQueryConditionDto集合
	 * @author Foss-065340-LiuTao
	 * @date 2015-07-10
	 */
	private List<CustomerQueryConditionDto> convertDeliveryCustToConditionDto(List<HisDeliveryCusEntity> entityList) {
		List<CustomerQueryConditionDto> dtoList = null;
		// 判断是否为空
		if (CollectionUtils.isNotEmpty(entityList)) {
			dtoList = new ArrayList<CustomerQueryConditionDto>();
			// 转换对象
			for (HisDeliveryCusEntity entity : entityList) {
				CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
				// 客户地址
				dto.setAddress(entity.getDeliveryCustomerAddress());
				// 联系人
				dto.setContactName(entity.getDeliveryCustomerContact());
				// 手机
				dto.setMobilePhone(entity.getDeliveryCustomerMobilephone());
				// 电话
				dto.setContactPhone(entity.getDeliveryCustomerPhone());
				// 客户类型
				dto.setCustomerType(WaybillConstants.CUSTOMER_TYPE_HISTORY);
				//地址备注
				dto.setAddressRemark(entity.getDeliveryCustomerAddressNote());
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	/**
	 * 将HisReceiveCusEntity集合转换为CustomerQueryConditionDto集合
 * @author Foss-065340-LiuTao
	 * @date 2015-07-10
	 */
	private List<CustomerQueryConditionDto> convertReceiveCustToConditionDto(List<HisReceiveCusEntity> entityList) {
		List<CustomerQueryConditionDto> dtoList = null;
		// 判断是否为空
		if (CollectionUtils.isNotEmpty(entityList)) {
			dtoList = new ArrayList<CustomerQueryConditionDto>();
			// 转换对象
			for (HisReceiveCusEntity entity : entityList) {
				CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
				// 客户地址
				dto.setAddress(entity.getReceiveCustomerAddress());
				// 联系人
				dto.setContactName(entity.getReceiveCustomerContact());
				// 手机
				dto.setMobilePhone(entity.getReceiveCustomerMobilephone());
				// 电话
				dto.setContactPhone(entity.getReceiveCustomerPhone());
				// 客户类型
				dto.setCustomerType(WaybillConstants.CUSTOMER_TYPE_HISTORY);
				//地址备注
				dto.setAddressRemark(entity.getReceiveCustomerAddressNote());
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	/**
	 * 运单基本信息设置
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */
	private void setWaybillEntityBasicInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){
		//运单号
		if(StringUtils.isNotBlank(eamDto.getWaybillNo())){
			waybillEntity.setWaybillNo(eamDto.getWaybillNo());
		}
		//订单号   
		if(StringUtils.isNotBlank(pendingEntity.getOrderNo())){
		    waybillEntity.setOrderNo(pendingEntity.getOrderNo());
		}
		//订单来源
		if(StringUtils.isNotBlank(pendingEntity.getOrderChannel())){
			waybillEntity.setOrderChannel(pendingEntity.getOrderChannel());
		}
		//订单付款方式
		if(StringUtils.isNotBlank(pendingEntity.getOrderPaidMethod())){
			waybillEntity.setOrderPaidMethod(pendingEntity.getOrderPaidMethod());
		}
		//创建人工号
		if(StringUtils.isNotBlank(pendingEntity.getDriverCode())){
		    waybillEntity.setCreateUserCode(pendingEntity.getDriverCode());
		    EmployeeEntity employee=employeeService.queryEmployeeByEmpCode(pendingEntity.getDriverCode());
		     //创建人姓名
			if(employee!=null && employee.getEmpName()!=null){			
				waybillEntity.setCreateUserName(employee.getEmpName());
			}
		}
		waybillEntity.setCreateTime(new Date());
		waybillEntity.setBillTime(pendingEntity.getBillTime());
		waybillEntity.setAddTime(new Date());
		
		//根据快递员车牌号所在的营业部设置创建营业部
		if(StringUtils.isNotBlank(pendingEntity.getLicensePlateNum())){
			SaleDepartmentEntity sales=querySaleDepartmentByVehicleNo(pendingEntity.getLicensePlateNum());
			if(sales!=null){
				//开单部门所在组织
				waybillEntity.setCreateOrgCode(sales.getCode());//创建部门编码
				waybillEntity.setCreateUserDeptName(sales.getName());//创建部门名称
				//1014 结算用到该字段，必须不能为空，否则空指针
				if(null!=sales.getCode()){
					waybillEntity.setModifyOrgCode(sales.getCode());//创建时，修改部门即为创建部门
				}else{
					waybillEntity.setModifyOrgCode(pendingEntity.getCreateOrgCode());
				}
			}
		}
		//是否快递
		if(StringUtils.isNotEmpty(waybillEntity.getProductCode())){
			if(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybillEntity.getProductCode())||
					ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(waybillEntity.getProductCode())){
				waybillEntity.setIsExpress(FossConstants.YES);
			}else{
				waybillEntity.setIsExpress(FossConstants.NO);
			}
		}
		//设置是否经过本部门,该字段为PDA传递过来的数据，默认设置为NO吧
		waybillEntity.setIsPassOwnDepartment(FossConstants.NO);
		waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		waybillEntity.setActive(FossConstants.YES);
		LOGGER.info("运单基本信息设置完毕.....................订单号为" + waybillEntity.getWaybillNo());
	}
	/**
	 * 查询快递员车辆所在营业部
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */
	public SaleDepartmentEntity querySaleDepartmentByVehicleNo(String vehicleNo){
		//IExpressVehiclesService
		String saleDeptCode = expressVehiclesService.queryOrgCodeByVehicleNo(vehicleNo);
		SaleDepartmentEntity saleDepartmentEntityquery = saleDepartmentService.querySaleDepartmentByCode(saleDeptCode);

		return saleDepartmentEntityquery;
	}
	/**
	 * 设置发货客户信息
	 *  
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */	
	private void setDeliveryCustomerInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){
		/*
		 * 发货客户信息
		 */
	 if(!WaybillConstants.DEPPON_CUSTOMER.equals(pendingEntity.getDeliveryCustomerCode())){
			//业务需求：当付款方式为月结，并且传入的客户编码不为空，同时该客户编码是月结的客户编码，才按传入的客户编码录,否则通过电话带出且带出的编码必须为月结，负责失败！
		 if(WaybillConstants.MONTH_PAYMENT.equals(pendingEntity.getPaidMethod())){
			 if((null!=eamDto.getDeliveryCustomerCode()&& StringUtil.isNotBlank(eamDto.getDeliveryCustomerCode()))
						&&isMonth(pendingEntity,eamDto)){
							 CustomerQueryConditionDto condition=new CustomerQueryConditionDto();
							 condition.setCustCode(eamDto.getDeliveryCustomerCode().trim().toUpperCase());
							 condition.setActive("Y");
							 waybillEntity.setDeliveryCustomerCode(eamDto.getDeliveryCustomerCode().trim().toUpperCase());
							 List<CustomerQueryConditionDto> deliveryCustomer= waybillExpressService.queryCustomerByCondition(condition);
							 if(deliveryCustomer!=null&&deliveryCustomer.size()>0){
								waybillEntity.setDeliveryCustomerName(deliveryCustomer.get(0).getCustName());//发货客户名称
								waybillEntity.setDeliveryCustomerId(deliveryCustomer.get(0).getCustId());//发货客户ID
								waybillEntity.setDeliverCustContactId(deliveryCustomer.get(0).getLinkmanId());//发货客户联系人ID
							}else{
								throw new WaybillValidateException("该月结客户编码在系统中不存在！");
							}
				 }else{
					//根据发货人手机号码/电话号码查询客户编码
						List<CustomerQueryConditionDto> deliveryCustomer1=queryDeliveryCustomer(eamDto);
						List<CustomerQueryConditionDto> deliveryCustomer2=new ArrayList<CustomerQueryConditionDto>();
						StringBuilder remarks = new StringBuilder("");
						if (CollectionUtils.isNotEmpty(deliveryCustomer1)) {
							for(CustomerQueryConditionDto cust:deliveryCustomer1){
								if(null!=cust.getCustCode()){
									remarks.append(cust.getCustCode()).append(",");
									deliveryCustomer2.add(cust);
								}
							}
						}
						if(deliveryCustomer2!=null && deliveryCustomer2.size()>0){
							for(CustomerQueryConditionDto cust1:deliveryCustomer2){
								if(isMonthTodo(cust1.getCustCode(),pendingEntity.getReceiveOrgCode())){
							waybillEntity.setDeliveryCustomerCode(cust1.getCustCode());//发货客户编码
							waybillEntity.setDeliveryCustomerName(cust1.getCustName());//发货客户名称
							waybillEntity.setDeliveryCustomerId(cust1.getCustId());//发货客户ID
							waybillEntity.setDeliverCustContactId(cust1.getLinkmanId());//发货客户联系人ID
							 break;
								}
							}
							Map<String,Object> map=new HashMap<String,Object>();
							map.put("waybillNo", waybillEntity.getWaybillNo());
							map.put("remarks", remarks.toString());
							eamDao.eamLogeUpdate(map);
						}else{
							waybillEntity.setDeliveryCustomerCode(pendingEntity.getDeliveryCustomerCode());//发货客户编码
							waybillEntity.setDeliveryCustomerName(pendingEntity.getDeliveryCustomerName());//发货客户名称
							waybillEntity.setDeliveryCustomerId(pendingEntity.getDeliveryCustomerId());//发货客户ID
							waybillEntity.setDeliverCustContactId(pendingEntity.getDeliveryCustomerContactId());//发货客户联系人ID
						}
				 }
		 }else{//下面这种方法是正对非月结客户的!根据业务的需求：当开单为非月结时，要打折必须要有客户编码 ，客户编码只校验存在性，若编码不存在则校验失败
			 if((null!=eamDto.getDeliveryCustomerCode()&& StringUtil.isNotBlank(eamDto.getDeliveryCustomerCode()))){
							 CustomerQueryConditionDto condition=new CustomerQueryConditionDto();
							 condition.setCustCode(eamDto.getDeliveryCustomerCode().trim().toUpperCase());
							 condition.setActive("Y");
							 waybillEntity.setDeliveryCustomerCode(eamDto.getDeliveryCustomerCode().trim().toUpperCase());
							 //查询客户信息
							 List<CustomerQueryConditionDto> deliveryCustomer= waybillExpressService.queryCustomerByCondition(condition);
							 if(deliveryCustomer!=null&&deliveryCustomer.size()>0){
								waybillEntity.setDeliveryCustomerName(deliveryCustomer.get(0).getCustName());//发货客户名称
								waybillEntity.setDeliveryCustomerId(deliveryCustomer.get(0).getCustId());//发货客户ID
								waybillEntity.setDeliverCustContactId(deliveryCustomer.get(0).getLinkmanId());//发货客户联系人ID
							}else{
								throw new WaybillValidateException("该客户编码在系统中不存在！");
							}
				 }else{
						//根据发货人手机号码/电话号码查询客户编码
						List<CustomerQueryConditionDto> deliveryCustomer1=queryDeliveryCustomer(eamDto);
						List<CustomerQueryConditionDto> deliveryCustomer2=new ArrayList<CustomerQueryConditionDto>();
						StringBuilder remarks = new StringBuilder("");
						if (CollectionUtils.isNotEmpty(deliveryCustomer1)) {
							for(CustomerQueryConditionDto cust:deliveryCustomer1){
								if(null!=cust.getCustCode()){
									remarks.append(cust.getCustCode()).append(",");
									deliveryCustomer2.add(cust);
								}
							}
						}
						if(deliveryCustomer2!=null && deliveryCustomer2.size()>0){
							waybillEntity.setDeliveryCustomerCode(deliveryCustomer2.get(0).getCustCode());//发货客户编码
							waybillEntity.setDeliveryCustomerName(deliveryCustomer2.get(0).getCustName());//发货客户名称
							waybillEntity.setDeliveryCustomerId(deliveryCustomer2.get(0).getCustId());//发货客户ID
							waybillEntity.setDeliverCustContactId(deliveryCustomer2.get(0).getLinkmanId());//发货客户联系人ID
							Map<String,Object> map=new HashMap<String,Object>();
							map.put("waybillNo", waybillEntity.getWaybillNo());
							map.put("remarks", remarks);
							eamDao.eamLogeUpdate(map);
						}else{
							waybillEntity.setDeliveryCustomerCode(pendingEntity.getDeliveryCustomerCode());//发货客户编码
							waybillEntity.setDeliveryCustomerName(pendingEntity.getDeliveryCustomerName());//发货客户名称
							waybillEntity.setDeliveryCustomerId(pendingEntity.getDeliveryCustomerId());//发货客户ID
							waybillEntity.setDeliverCustContactId(pendingEntity.getDeliveryCustomerContactId());//发货客户联系人ID
						}
				 }
		 }
		//判定客户编码是否为空
		if(StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCode())){
			CustomerDto customerDto = customerDao.queryCustInfoByCode(waybillEntity.getDeliveryCustomerCode());
			if(customerDto!=null){
				//是否是大客户
				waybillEntity.setDeliveryBigCustomer(customerDto.getIsLargeCustomers());
			}
		}
		waybillEntity.setDeliveryCustomerId(pendingEntity.getDeliveryCustomerId());
		//发货客户手机
		if(eamDto.getDeliveryCustomerMobilePhone()!=null && StringUtils.isNotBlank(eamDto.getDeliveryCustomerMobilePhone())){
			waybillEntity.setDeliveryCustomerMobilephone(eamDto.getDeliveryCustomerMobilePhone());
			}else{
				waybillEntity.setDeliveryCustomerMobilephone(pendingEntity.getDeliveryCustomerMobilephone());
			}
		//发货客户电话
		if(eamDto.getDeliveryCustomerPhone()!=null && StringUtils.isNotBlank(eamDto.getDeliveryCustomerPhone())){
			waybillEntity.setDeliveryCustomerPhone(eamDto.getDeliveryCustomerPhone());
			}else{
				waybillEntity.setDeliveryCustomerPhone(pendingEntity.getDeliveryCustomerPhone());
			}
		//发货客户联系人	
		waybillEntity.setDeliveryCustomerContact(eamDto.getDeliveryCustomerName());
			//地址省编码设置       此处的方法根据名称查Code为精确查询，前期先不做修改
			AdministrativeRegionsEntity administrativeRegionsEntity=null;
			try {
				if(eamDto.getDeliveryCustomerProvCode()!=null && StringUtils.isNotBlank(eamDto.getDeliveryCustomerProvCode())){
				    //如果录单中心传过来是数字，则是编码
					Integer.parseInt(eamDto.getDeliveryCustomerProvCode());
					waybillEntity.setDeliveryCustomerProvCode(eamDto.getDeliveryCustomerProvCode());
					waybillEntity.setDeliveryCustomerCityCode(eamDto.getDeliveryCustomerCityCode());
					waybillEntity.setDeliveryCustomerDistCode(eamDto.getDeliveryCustomerDistCode());
				}else{
					waybillEntity.setDeliveryCustomerProvCode(pendingEntity.getDeliveryCustomerProvCode());
					waybillEntity.setDeliveryCustomerCityCode(pendingEntity.getDeliveryCustomerCityCode());
					waybillEntity.setDeliveryCustomerDistCode(pendingEntity.getDeliveryCustomerDistCode());
				}
			} catch (Exception e) {
				  //如果录单中心传过来不是数字，则是名字
				if(eamDto.getDeliveryCustomerProvCode()!=null && StringUtils.isNotBlank(eamDto.getDeliveryCustomerProvCode())){
					administrativeRegionsEntity=administrativeRegionsService.queryAdministrativeRegionsByName(eamDto.getDeliveryCustomerProvCode());
					if(administrativeRegionsEntity!=null && StringUtils.isNotBlank(administrativeRegionsEntity.getCode())){
						waybillEntity.setDeliveryCustomerProvCode(administrativeRegionsEntity.getCode());
					}
					else{
						waybillEntity.setDeliveryCustomerProvCode(pendingEntity.getDeliveryCustomerProvCode());
					}
				}
				//地址市编码设置       此处的方法根据名称查Code为精确查询，前期先不做修改
				List<AdministrativeRegionsEntity> administrativeRegionsEntity1=null;
				if(eamDto.getDeliveryCustomerCityCode()!=null && StringUtils.isNotBlank(eamDto.getDeliveryCustomerCityCode())){
					administrativeRegionsEntity1=administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(waybillEntity.getDeliveryCustomerProvCode());
					if(administrativeRegionsEntity1!=null){
						for( AdministrativeRegionsEntity a :administrativeRegionsEntity1 ){
							if(a.getName().equals(eamDto.getDeliveryCustomerCityCode())){
								waybillEntity.setDeliveryCustomerCityCode(a.getCode());
								break;
							}
						}
					}else{
						waybillEntity.setDeliveryCustomerCityCode(pendingEntity.getDeliveryCustomerCityCode());
					}
				}
				//地址区编码设置       此处的方法根据名称查Code为精确查询，前期先不做修改
				List<AdministrativeRegionsEntity> administrativeRegionsEntity2=null;
				if(pendingEntity.getDeliveryCustomerDistCode()!=null && StringUtils.isNotBlank
						(pendingEntity.getDeliveryCustomerDistCode())){
					waybillEntity.setDeliveryCustomerDistCode(pendingEntity.getDeliveryCustomerDistCode());
				}else{
					administrativeRegionsEntity2=administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(waybillEntity.getDeliveryCustomerCityCode());
					if(administrativeRegionsEntity2!=null){
						for( AdministrativeRegionsEntity a :administrativeRegionsEntity2 ){
							if(a.getName().equals(eamDto.getDeliveryCustomerDistCode())){
								waybillEntity.setDeliveryCustomerDistCode(a.getCode());
								break;
							}
						}
						if(waybillEntity.getDeliveryCustomerDistCode()==null){
							waybillEntity.setDeliveryCustomerDistCode(waybillEntity.getDeliveryCustomerCityCode());
						}
					}
					else{
						waybillEntity.setDeliveryCustomerDistCode(waybillEntity.getDeliveryCustomerCityCode());
					   }			
					}
			}
			//具体地址  (这一块必须写在try块外面)设置发货具体地址与省市区设置无关
			if(eamDto.getDeliveryCustomerAddress()!=null && StringUtils.isNotBlank(eamDto.getDeliveryCustomerAddress())){
				waybillEntity.setDeliveryCustomerAddress(eamDto.getDeliveryCustomerAddress());
			}else{
				waybillEntity.setDeliveryCustomerAddress(pendingEntity.getDeliveryCustomerAddress());
			}
		LOGGER.info("发货客户基本信息设置完毕.....................发货人客户编码为" + waybillEntity.getDeliveryCustomerCode()) ;
	}else{
		//内部带货
		waybillEntity.setDeliveryCustomerCode(WaybillConstants.DEPPON_CUSTOMER);
		waybillEntity.setDeliveryCustomerMobilephone(pendingEntity.getDeliveryCustomerMobilephone());
		waybillEntity.setDeliveryCustomerName(pendingEntity.getDeliveryCustomerName());
		waybillEntity.setDeliveryCustomerPhone(pendingEntity.getDeliveryCustomerPhone());
		waybillEntity.setDeliveryCustomerContact(pendingEntity.getDeliveryCustomerContact());
	}
   }
	
	/**
	 * 设置收货客户信息
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */	
	private void setReceiveCustomerInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){
		/*
		 * 收货客户信息
		 */
		//根据收货人手机号码/电话号码查询客户编码
		List<CustomerQueryConditionDto> receiveCustomer=queryReceiveCustomer(eamDto);
		if(receiveCustomer!=null && receiveCustomer.size()>0){
			waybillEntity.setReceiveCustomerCode(receiveCustomer.get(0).getCustCode());//收货客户编码
			waybillEntity.setReceiveCustomerName(receiveCustomer.get(0).getCustName());//收货客户名称
			waybillEntity.setReceiveCustomerId(receiveCustomer.get(0).getCustId());//收货客户ID
			waybillEntity.setReceiverCustContactId(receiveCustomer.get(0).getLinkmanId());//收货客户联系人ID
		}else{
			waybillEntity.setReceiveCustomerCode(pendingEntity.getReceiveCustomerCode());//收货客户编码
			waybillEntity.setReceiveCustomerName(pendingEntity.getReceiveCustomerName());//收货客户名称
			waybillEntity.setReceiveCustomerId(pendingEntity.getReceiveCustomerId());//收货客户ID
			waybillEntity.setReceiverCustContactId(pendingEntity.getReceiveCustomerContactId());//收货客户联系人ID
		}
		if(waybillEntity.getReceiveCustomerCode()!=null && !"".equals(waybillEntity.getReceiveCustomerCode()) ){
			CustomerDto customerDto = customerDao.queryCustInfoByCode(waybillEntity.getReceiveCustomerCode());
			if(customerDto!=null){
				//判断收货客户是否是大客户
				waybillEntity.setReceiveBigCustomer(customerDto.getIsLargeCustomers());
			}
		}
		//收货客户手机
		if(eamDto.getReceiveCustomerMobilePhone()!=null && StringUtils.isNotBlank(eamDto.getReceiveCustomerMobilePhone())){
			waybillEntity.setReceiveCustomerMobilephone(eamDto.getReceiveCustomerMobilePhone());
			}else{
				waybillEntity.setReceiveCustomerMobilephone(pendingEntity.getReceiveCustomerMobilephone());
			}
		//收货客户电话
		if(eamDto.getReceiveCustomerPhone()!=null && StringUtils.isNotBlank(eamDto.getReceiveCustomerPhone())){
			waybillEntity.setReceiveCustomerPhone(eamDto.getReceiveCustomerPhone());
			}else{
				waybillEntity.setReceiveCustomerPhone(pendingEntity.getReceiveCustomerPhone());
			}
		waybillEntity.setReceiveCustomerContact(eamDto.getReceiveCustomerName());//收货客户联系人
		//收货地址省编码设置       此处的方法根据名称查Code为精确查询，前期先不做修改
		try {
			if(eamDto.getReceiveCustomerProvCode()!=null && StringUtils.isNotBlank(eamDto.getReceiveCustomerProvCode())){
			    //如果录单中心传过来是数字，则是编码
				Integer.parseInt(eamDto.getReceiveCustomerProvCode());
				waybillEntity.setReceiveCustomerProvCode(eamDto.getReceiveCustomerProvCode());
				waybillEntity.setReceiveCustomerCityCode(eamDto.getReceiveCustomerCityCode());
				waybillEntity.setReceiveCustomerDistCode(eamDto.getReceiveCustomerDistCode());
			}
		}catch(Exception e){
			 //如果录单中心传过来不是数字，则是名字
			AdministrativeRegionsEntity administrativeRegionsEntity=null;
			if(eamDto.getReceiveCustomerProvCode()!=null && StringUtils.isNotBlank(eamDto.getReceiveCustomerProvCode())){
				administrativeRegionsEntity=administrativeRegionsService.queryAdministrativeRegionsByName(eamDto.getReceiveCustomerProvCode());
				if(administrativeRegionsEntity!=null && StringUtils.isNotBlank(administrativeRegionsEntity.getCode())){
					waybillEntity.setReceiveCustomerProvCode(administrativeRegionsEntity.getCode());
				}
				else{
					waybillEntity.setReceiveCustomerProvCode(pendingEntity.getReceiveCustomerProvCode());
				}
			}else{
					throw new WaybillValidateException("收货地址省为必填项！");
				}
			//收货地址市编码设置       此处的方法根据名称查Code为精确查询，前期先不做修改
			List<AdministrativeRegionsEntity> administrativeRegionsEntity1=null;
			if(eamDto.getReceiveCustomerCityCode()!=null && StringUtils.isNotBlank(eamDto.getReceiveCustomerCityCode())){
				administrativeRegionsEntity1=administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(waybillEntity.getReceiveCustomerProvCode());
				if(administrativeRegionsEntity1!=null){
					for( AdministrativeRegionsEntity a :administrativeRegionsEntity1 ){
						if(a.getName().equals(eamDto.getReceiveCustomerCityCode())){
							waybillEntity.setReceiveCustomerCityCode(a.getCode());
							break;
						}
					}
				}else{
					waybillEntity.setReceiveCustomerCityCode(pendingEntity.getReceiveCustomerCityCode());
				}
			}
			else{
					throw new WaybillValidateException("收货地址市为必填项！");
				}
			//收货地址区编码设置       此处的方法根据名称查Code为精确查询，前期先不做修改
			List<AdministrativeRegionsEntity> administrativeRegionsEntity2=null;
			if(pendingEntity.getReceiveCustomerDistCode()!=null && StringUtils.isNotBlank
					(pendingEntity.getReceiveCustomerDistCode())){
				waybillEntity.setReceiveCustomerDistCode(pendingEntity.getReceiveCustomerDistCode());
			}else{
				administrativeRegionsEntity2=administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(waybillEntity.getReceiveCustomerCityCode());
				if(administrativeRegionsEntity2!=null){
					for( AdministrativeRegionsEntity a :administrativeRegionsEntity2 ){
						if(a.getName().equals(eamDto.getReceiveCustomerDistCode())){
							waybillEntity.setReceiveCustomerDistCode(a.getCode());
							break;
						}
					}
					if(waybillEntity.getReceiveCustomerDistCode()==null){
						waybillEntity.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerCityCode());
					}
				}
				else{
					waybillEntity.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerCityCode());
				   }			
				}
			}
		//收货具体地址
		if(eamDto.getReceiveCustomerAddress()!=null && StringUtils.isNotBlank(eamDto.getReceiveCustomerAddress())){
			waybillEntity.setReceiveCustomerAddress(eamDto.getReceiveCustomerAddress());
			}else{
				throw new WaybillValidateException("收货具体地址为必填项！");
			}
		LOGGER.info("收货客户基本信息设置完毕.....................发货人客户编码为" + waybillEntity.getReceiveCustomerName()) ;
	}
	/**
	 * 设置运输信息
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */	
	private void  setTransportInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){

		waybillEntity.setDriverCode(pendingEntity.getDriverCode());//司机工号
		waybillEntity.setLicensePlateNum(pendingEntity.getLicensePlateNum());//车牌号
		waybillEntity.setPickupCentralized(WaybillConstants.NO);//是否集中接货，全部置N
		waybillEntity.setIsWholeVehicle(WaybillConstants.NO);
		
		if(StringUtils.isNotEmpty(pendingEntity.getReceiveMethod())){
			if(pendingEntity.getReceiveMethod().indexOf("PICKUP") >= 0){
				waybillEntity.setReceiveMethod(WaybillConstants.SELF_PICKUP);//汽运自提
				waybillEntity.setPickupToDoor(WaybillConstants.NO);
			}else {
				waybillEntity.setReceiveMethod(WaybillConstants.DELIVER_UP);//快递默认汽运送货（上楼）
				waybillEntity.setPickupToDoor(WaybillConstants.YES);
			}
		}
		LOGGER.info("运输信息设置完毕.....................快递员工号为" + waybillEntity.getDriverCode()) ;
	}
	/**
	 * 设置货物信息
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */
	private void setGoodsInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){
		//托寄物内容
		if(eamDto.getGoodsName()!=null && StringUtils.isNotBlank(eamDto.getGoodsName())){
			    waybillEntity.setGoodsName(eamDto.getGoodsName());//货物名称
			}else{
					throw new WaybillValidateException("货物名称为必填项！");
			}
		waybillEntity.setGoodsQtyTotal(pendingEntity.getGoodsQtyTotal());//货物总件数
		waybillEntity.setGoodsWeightTotal(pendingEntity.getGoodsWeightTotal());//货物总重量
		waybillEntity.setGoodsVolumeTotal(pendingEntity.getGoodsVolumeTotal());//货物总体积
		if(pendingEntity.getGoodsVolumeTotal()== null){
			waybillEntity.setGoodsSize(BigDecimal.ZERO.toString());
		}else{
			waybillEntity.setGoodsSize(pendingEntity.getGoodsSize());//货物尺寸
		}
		//设置包装类型详情
		waybillEntity.setGoodsPackage(waybillEntity.getGoodsQtyTotal()+ExpWaybillConstants.GOOD_PACKAGE_DEFAULT);		
		waybillEntity.setPaperNum(waybillEntity.getGoodsQtyTotal());
		waybillEntity.setWoodNum(BigDecimal.ZERO.intValue());
		waybillEntity.setFibreNum(BigDecimal.ZERO.intValue());
		waybillEntity.setSalverNum(BigDecimal.ZERO.intValue());
		waybillEntity.setMembraneNum(BigDecimal.ZERO.intValue());
		waybillEntity.setOtherPackage(null);
		
		waybillEntity.setInnerNotes(pendingEntity.getInnerNotes());//对内备注
		waybillEntity.setTransportationRemark(pendingEntity.getOuterNotes());
		waybillEntity.setGoodsTypeCode(WaybillConstants.GOODS_TYPE_A);//货物类型
		waybillEntity.setPreciousGoods(WaybillConstants.NO);//是否贵重物品
		waybillEntity.setSpecialShapedGoods(WaybillConstants.NO);//是否异形物品
		
		//private String outerNotes;//对外备注不处理
		
		LOGGER.info("货物信息设置完毕.....................货物信息为" + waybillEntity.getGoodsName()) ;
	}
	/**
	 * 设置目的站
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */	
	private	void setTargetOrgCode(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){

		//根据收货省市区查询快递营业网点，优先取快递营业网点
		//目的站,订单有则取订单,没有则取快递营业点部
		if(StringUtils.isNotBlank(pendingEntity.getCustomerPickupOrgCode())){
			//判定提货网点是否为空
			SaleDepartmentEntity salesDept = saleDepartmentService.querySimpleSaleDepartmentByCode(pendingEntity.getCustomerPickupOrgCode());
			if(salesDept == null){
				throw new WaybillValidateException("未找到提货网点，不能开快递单");
			}
			//如果该提货网点不能开代收货款，则报错不能开提货网点单
			if(eamDto.getCodAmount() !=null && new BigDecimal(eamDto.getCodAmount()).compareTo(BigDecimal.ZERO)>0){
				if(!FossConstants.YES.equals(salesDept.getCanAgentCollected())){
					throw new WaybillValidateException("目的站所选网点不能开代收货款");
				}
			}
			
			//是否支持开多件--废除到达部门一票多件的校验：与罗益文确认
			/*if(waybillEntity.getGoodsQtyTotal() != null && waybillEntity.getGoodsQtyTotal() > 1){
				//如果不支持开多件，则抛出异常
				if(!FossConstants.YES.equals(salesDept.getCanExpressOneMany())){
					throw new WaybillValidateException(WaybillValidateException.CANNOT_CREATE_ONETOMANY_EXP);
				}
			}*/
			
			//营业部是否支持签收单返单
			if(pendingEntity.getReturnBillType() != null && StringUtils.isNotBlank(pendingEntity.getReturnBillType())
					&& !pendingEntity.getReturnBillType().equals(WaybillConstants.NOT_RETURN_BILL)){
				if(!FossConstants.YES.equals(salesDept.getCanExpressReturnSignBill())){
					throw new WaybillValidateException("提货网点不能做快递返单，请配置基础资料");
				}
			}
			//否则进行赋值
			waybillEntity.setCustomerPickupOrgCode(pendingEntity.getCustomerPickupOrgCode());//提货网点CODE
			waybillEntity.setCustomerPickupOrgName(salesDept.getName());//提货网点名称
			//提货网点部门简称
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingEntity.getCustomerPickupOrgCode());
			if(orgInfo != null){
				waybillEntity.setTargetOrgCode(orgInfo.getOrgSimpleName());
			}
		}else{
			//进行统一化处理
			SaleDepartmentEntity saleDeprtTemp =waybillExpressService.getTargetOrgCode(waybillEntity);
			if(saleDeprtTemp == null){
				throw new WaybillValidateException("目的站为空");
			}
			LOGGER.info("目的站快递营业部为" + saleDeprtTemp.getCode()+saleDeprtTemp.getName()) ;
			//是否支持开多件--废除到达部门一票多件的校验：与罗益文确认
			/*if(waybillEntity.getGoodsQtyTotal() != null && waybillEntity.getGoodsQtyTotal() > 1){
				//如果不支持开多件，则抛出异常
				if(!FossConstants.YES.equals(saleDeprtTemp.getCanExpressOneMany())){
					throw new WaybillValidateException(WaybillValidateException.CANNOT_CREATE_ONETOMANY_EXP);
				}
			}*/
			//判断是否能开代收货款
			if(eamDto.getCodAmount() !=null && new BigDecimal(eamDto.getCodAmount()).compareTo(BigDecimal.ZERO)>0){
				if(!FossConstants.YES.equals(saleDeprtTemp.getCanAgentCollected())){
					throw new WaybillValidateException("目的站所选网点不能开代收货款");
				}
			}

			//营业部是否支持签收单返单
			if(pendingEntity.getReturnBillType() != null && StringUtils.isNotBlank(pendingEntity.getReturnBillType())
					&& !pendingEntity.getReturnBillType().equals(WaybillConstants.NOT_RETURN_BILL)){
				if(!FossConstants.YES.equals(saleDeprtTemp.getCanExpressReturnSignBill())){
					throw new WaybillValidateException("提货网点不能做快递返单，请配置基础资料");
				}
			}
			
			waybillEntity.setCustomerPickupOrgName(saleDeprtTemp.getName());
			waybillEntity.setCustomerPickupOrgCode(saleDeprtTemp.getCode());
			//提货网点部门简称
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDeprtTemp.getCode());
			if(orgInfo != null){
				waybillEntity.setTargetOrgCode(orgInfo.getOrgSimpleName());
			}			
		} 
		if(StringUtils.isNotEmpty(pendingEntity.getReceiveOrgCode())){
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingEntity.getReceiveOrgCode());
			if (null != orgEntity) {
				waybillEntity.setReceiveOrgName(orgEntity.getName());
			            }
			}
			if(StringUtils.isNotEmpty(pendingEntity.getLastLoadOrgCode())){
			OrgAdministrativeInfoEntity loadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingEntity.getLastLoadOrgCode());
			if (null != loadOrg) {
				waybillEntity.setLastLoadOrgName(loadOrg.getName());
			            }
			}
		LOGGER.info("目的站设置完毕.....................目的站编码为" + waybillEntity.getTargetOrgCode()) ;
	}
	
	
	public SaleDepartmentEntity getBestSalesDepartment(List<SaleDepartmentEntity> saleDepartmentEntityList){
		SaleDepartmentEntity saleDeprtTemp = null;
		//如果是普通快递营业部，直接赋值不解释
		for(SaleDepartmentEntity entity : saleDepartmentEntityList){
			//判定该部门是否为虚拟营业部
	    	OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getCode());
	    	if(orgInfo != null && !FossConstants.YES.equals(orgInfo.getExpressSalesDepartment())){
	    		continue;
	    	}
			if(!(entity.getName().contains("出发")||entity.getName().contains("外发") || (entity.getName().contains("远郊")))){
				//普通营业部
				saleDeprtTemp = entity;
				break;
			}
		}
		//如果没有找到普通快递营业部，先判定是否出发快递营业部，没有再次选择外发的
		if(saleDeprtTemp == null){
			for(SaleDepartmentEntity entity:saleDepartmentEntityList){
				//判定该部门是否为虚拟营业部
		    	OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getCode());
		    	if(orgInfo != null && !FossConstants.YES.equals(orgInfo.getExpressSalesDepartment())){
		    		continue;
		    	}
				//再次选择出发快递营业部，如果还没有，就只能选择外发了
				if(entity.getName().contains("出发")||entity.getName().contains("外发")){
					//出发营业部
					saleDeprtTemp = entity;
					break;
				}else {
					//远郊营业部 
					if(saleDeprtTemp!=null && saleDeprtTemp.getName().contains("出发")||entity.getName().contains("外发")){
						continue;
					}else{
						saleDeprtTemp = entity;
					}
				}
			}
		}
		return saleDeprtTemp;
	}
	/**
	 * 设置配载信息
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */	
	private void setLoadInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){
		//配载部门、配载部门名称、最终配载部门、最终配置部门名称
		queryLodeDepartmentInfo(waybillEntity,pendingEntity);
		waybillEntity.setCarDirectDelivery(WaybillConstants.NO);//是否大车直送	
		waybillEntity.setForbiddenLine(WaybillConstants.YES);//禁行
		//预计出发时间
		waybillEntity.setPreDepartureTime(waybillExpressService.getLeaveTime(waybillEntity));
		//预计派送/提货时间
		EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(waybillEntity.getCreateOrgCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getProductCode(), waybillEntity.getPreDepartureTime(), new Date());
		//如果是自提货物的话
		if(effectiveDto != null){
			if(isPickup(waybillEntity, pendingEntity)){
				waybillEntity.setPreArriveTime(effectiveDto.getDeliveryDate());
				waybillEntity.setPreCustomerPickupTime(effectiveDto.getDeliveryDate());
			}else{
				waybillEntity.setPreArriveTime(effectiveDto.getSelfPickupTime());
				waybillEntity.setPreCustomerPickupTime(effectiveDto.getSelfPickupTime());
			}
		}
		//waybillEntity.setKilometer();//公里数	
		//waybillEntity.setLoadMethod();//配载类型
		//waybillEntity.setTransferStartOrgCode();//中转 返货的时候的中转起点外场
		//waybillEntity.setTransferStartOrgName();//中转 返货的时候的中转起点外场名称
		//private String freightMethod;//合票方式
		//private String arriveType;//到达类型
		//private String flightShift;//航班时间
		
		LOGGER.info("配置信息设置完毕.....................配置线路编码为" + waybillEntity.getLoadLineCode()) ;
	}
	/**
	 * 查询并设置始发配载部门、最终配载部门以及线路
	 * 
     * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */
	private void queryLodeDepartmentInfo(WaybillEntity waybillEntity,WaybillPendingEntity pendingEntity){
		//没有找到走路路径的，应该进行补录；
		try {
			//运输性质非空判断
			if(null == pendingEntity.getProductCode()){
				throw new WaybillValidateException("产品类型为空");
			}
			
			Boolean isPickupCentralized = WaybillConstants.YES.equals(pendingEntity.getPickupCentralized()) ? true : false;
			
			OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(
					isPickupCentralized,pendingEntity.getCreateOrgCode(),pendingEntity.getCustomerPickupOrgCode(),
					pendingEntity.getProductCode());
			if (orgInfoDto == null || orgInfoDto.getFreightRoute() == null) {
				throw new WaybillValidateException("走货线路查询不到");
				//补录操作
			} else {
				FreightRouteEntity freightRoute = orgInfoDto.getFreightRoute();

				//waybillEntity.setLoadLineName(orgInfoDto.getRouteLineName());// 配载线路名称
	
				if(freightRoute!=null){
					waybillEntity.setLoadLineCode(freightRoute.getVirtualCode());// 配载线路编码
					//waybillEntity.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码	
					//waybillEntity.setPackingOrganizationName(freightRoute.getPackingOrganizationName());// 代打木架部门名称
					//waybillEntity.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
				}else{
					waybillEntity.setLoadLineCode("");// 配载线路编码
					//waybillEntity.setPackageOrgCode("");// 代打木架部门编码	
					//waybillEntity.setPackingOrganizationName("");// 代打木架部门名称		
					//waybillEntity.setDoPacking("");// 是否可以打木架	
				}			
				waybillEntity.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());// 配载部门编号
				waybillEntity.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());// 配载部门名称
				waybillEntity.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());// 最终配载部门编号
				waybillEntity.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());// 最终配载部门名称
				//waybillEntity.setLastOutLoadOrgCode(orgInfoDto.getLastOutLoadOrgCode());//最终外场编码
				//waybillEntity.setGoodsTypeIsAB(orgInfoDto.getGoodsTypeIsAB());//是否AB货
		
			}
		} catch(BaseInfoInvokeException e) {
			throw new WaybillValidateException("走货线路查询不到"); 
			
		} catch(BusinessException w){
			//waybillEntity.setLoadLineName("");// 配载线路名称
			waybillEntity.setLoadLineCode("");// 配载线路编码
			waybillEntity.setLoadOrgCode("");// 配载部门编号
			waybillEntity.setLoadOrgName("");// 配载部门名称
			waybillEntity.setLastLoadOrgCode("");// 最终配载部门编号
			waybillEntity.setLastLoadOrgName("");// 最终配载部门名称
			//waybillEntity.setPackageOrgCode("");// 代打木架部门编码
			//waybillEntity.setPackingOrganizationName("");// 代打木架部门名称
			//waybillEntity.setDoPacking("");// 是否可以打木架
			//waybillEntity.setLastOutLoadOrgCode("");//最终配置外场	
			throw new WaybillValidateException("走货线路查询不到"); 
		}
	
	}
	/**
	 * 判定是否自提货物
	 * @param waybillEntity
	 * @param order
	 * @return
	 */
	private Boolean isPickup(WaybillEntity waybillEntity,WaybillPendingEntity pendingEntity) {
		String code = pendingEntity.getReceiveMethod();
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code))

		{
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 设置增值服务及费用基础信息
	 * 
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */
	private void setFeeRelatedInfo(WaybillEntity waybillEntity,EamDto eamDto,WaybillPendingEntity pendingEntity){
	
		//保价声明价值
		if(eamDto.getInsuranceAmount()==null){
			waybillEntity.setInsuranceAmount(BigDecimal.ZERO);
		}else{
			waybillEntity.setInsuranceAmount(new BigDecimal(eamDto.getInsuranceAmount()));
		}
		
		//private BigDecimal insuranceRate;//保价费率
		//private BigDecimal insuranceFee;//保价费
		
		//代收货款
		if(eamDto.getCodAmount()==null){
			//代收货款
			waybillEntity.setCodAmount(BigDecimal.ZERO);
			//代收货款手续费
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}else{
			//代收货款
			waybillEntity.setCodAmount(new BigDecimal(eamDto.getCodAmount()));
			
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}

		//退款类型(如果代收为0，则退款类型置空)
		if(eamDto.getCodAmount() !=null && new BigDecimal(eamDto.getCodAmount()).compareTo(BigDecimal.ZERO)>0){
				//代收货款退款类型
				if(eamDto.getRefundType()!=null && StringUtils.isNotBlank(eamDto.getRefundType())){
					if(StringUtil.equals(eamDto.getRefundType(), "1")){
						waybillEntity.setRefundType("R1");
					}else if(StringUtil.equals(eamDto.getRefundType(), "2")){
					    waybillEntity.setRefundType("R3");
				}else{
					throw new WaybillValidateException("无效的退款类型");
				}
			}else{
				waybillEntity.setRefundType(null);
			}
		}
		if(StringUtil.isNotBlank(pendingEntity.getReturnBillType())){
			waybillEntity.setReturnBillType(pendingEntity.getReturnBillType());//返单类别  
		}else{
			waybillEntity.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);//返单类别  
		}
		waybillEntity.setSecretPrepaid(WaybillConstants.NO);//预付费保密
		waybillEntity.setCurrencyCode("RMB");//付款币种
		waybillEntity.setWholeVehicleAppfee(BigDecimal.ZERO);//非整车，费用置0
		waybillEntity.setServiceFee(BigDecimal.ZERO);//无装卸费，费用置0
		
		//private BigDecimal deliveryGoodsFee;//送货费
		//private BigDecimal otherFee;//其他费用
		//private BigDecimal packageFee;//包装手续费
		//private BigDecimal promotionsFee;//优惠费用
		waybillEntity.setIsEconomyGoods(WaybillConstants.NO);
		
		//包装手续费
		if(eamDto.getPackageFeeCanvas()!=null && eamDto.getPackageFeeCanvas().compareTo(new BigInteger("0"))>0){
			 waybillEntity.setPackageFee(new BigDecimal(eamDto.getPackageFeeCanvas()));
		}else{
			 waybillEntity.setPackageFee(BigDecimal.ZERO);
		}
		//送货费
		waybillEntity.setDeliveryGoodsFee(BigDecimal.ZERO);
		//其他费用
		waybillEntity.setOtherFee(BigDecimal.ZERO);

		waybillEntity.setPromotionsCode(pendingEntity.getPromotionsCode());//优惠编码
		if(StringUtils.isNotEmpty(pendingEntity.getPaidMethod())){
			//现金
			if("CH".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.CASH_PAYMENT);//开单付款方式
			//月结
			}else if("CT".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.MONTH_PAYMENT);//开单付款方式
			//到付
			}else if("FC".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.ARRIVE_PAYMENT);//开单付款方式
			//网上支付
			}else if("OL".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.ONLINE_PAYMENT);//开单付款方式
			}
			//银行卡
			else if("CD".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.CREDIT_CARD_PAYMENT);//开单付款方式
			}
			//支票
			else if("NT".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.CHECK);//开单付款方式
			}
			//电汇
			else if("TT".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.TELEGRAPHIC_TRANSFER);//开单付款方式
			}
		    //临时欠款
			else if("DT".equals(pendingEntity.getPaidMethod())){
				waybillEntity.setPaidMethod(WaybillConstants.TEMPORARY_DEBT);//开单付款方式
			}
		}
		
		//只有在有代收货款的情况下才会设置对应的账号信息
		if(eamDto.getCodAmount() !=null && new BigDecimal(eamDto.getCodAmount()).compareTo(BigDecimal.ZERO)>0){
			if(eamDto.getAccountName()!=null){
			   waybillEntity.setAccountName(eamDto.getAccountName());//返款帐户开户名称
			}else{
				throw new WaybillValidateException("开户名不能为空");
			}
			if(eamDto.getCollectionAccount()!=null){
			   waybillEntity.setAccountCode(eamDto.getCollectionAccount());//返款帐户开户账户
			}else{
				throw new WaybillValidateException("开户账号不能为空");
			}
//			if(eamDto.getAccountBank()!=null){
//				waybillEntity.setAccountBank(eamDto.getAccountBank());
//		   }else{
//				throw new WaybillValidateException("开户银行不能为空");
//			}
		}
		waybillEntity.setOrderPaidMethod(pendingEntity.getPaidMethod());//支付方式
		
		LOGGER.info("费用相关信息设置完毕.....................货物信息为" + waybillEntity.getInsuranceAmount()) ;
	}	
	/**
	 * @author 065340-foss-liutao
	 * @date 2015-07-15
	 */
	public CusAccountEntity queryEWaybillCusAccountInfo(WaybillEntity entity){
		CusAccountEntity cusAccountEntity = null;
		if(StringUtils.isBlank(entity.getDeliveryCustomerCode())){
			throw new  WaybillValidateException("客户编码不能为空");
		}
		List<CusAccountEntity> cusAccountList = cusAccountService.
				queryAccountLatestNewInfoByCustCode(entity.getDeliveryCustomerCode());
		
		if(cusAccountList == null){
			//throw new WaybillValidateException(WaybillValidateException.CUSTOMER_ACCOUNT_NULL);
			return null;
		}else{
			for(CusAccountEntity cusAccountTemp: cusAccountList){
				if(cusAccountTemp.getAccountNo().equals(entity.getAccountCode())){
					cusAccountEntity = cusAccountTemp;
					break;
				}
			}
		}
		if(cusAccountEntity == null){
			return null;
		}else{
			return cusAccountEntity; 
		}
	}
	/**
	 * 补录失败消息保存到消息表
	 */
	private ResultDto sendInnerMess(EamDto eamDto,String content) {
		ResultDto dto = new ResultDto();
		//结果返回对象
		try{
		   WaybillPendingEntity pendingEntity = waybillPendingService.queryPendingByNo(eamDto.getWaybillNo());
			  if(null!=pendingEntity){
				//平台内部发给营业员的消息 保留
				InstationJobMsgEntity entity = new InstationJobMsgEntity();
				//发送人员编码
				entity.setSenderCode("N/A");
				//发送人员
				entity.setSenderName("系统");
				//发送组织编码
				entity.setSenderOrgCode("N/A");
				//发送组织
				entity.setSenderOrgName("/");
				//接收方组织编码
				if(null!=pendingEntity.getReceiveOrgCode()){
					entity.setReceiveOrgCode(pendingEntity.getReceiveOrgCode());
				}else{
					entity.setReceiveOrgCode(pendingEntity.getCreateOrgCode());
				}
				//补录失败
				entity.setMsgType(MSG_TYPE__SUPPLIER_FAIL_PATCH);
				//content
				entity.setContext(content);
				//接收方类型 MSG__RECEIVE_TYPE__ORG  组织 
				entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
				//id
				entity.setId(UUIDUtils.getUUID());
				// 未处理
				entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
				//发送时间
				entity.setPostDate(new Date());
				//创建时间
				messageService.createBatchInstationMsg(entity);
				//成功标志
				dto.setCode(ResultDto.SUCCESS);
				dto.setMsg("发送成功");
				return dto;
			}else{
				return null;
			}
		}catch(MessageException e){
			//出现异常
			LOGGER.error("error", e);
			//发送失败
			throw new WaybillValidateException("发送消息失败！");
		}
	}
	private  boolean isMonth(WaybillPendingEntity pendingEntity,EamDto eamDto){
		CusBargainVo vo=new CusBargainVo();
		vo.setExpayway(WaybillConstants.MONTH_END);
		vo.setCustomerCode(eamDto.getDeliveryCustomerCode());
		vo.setBillDate(new Date());
		vo.setBillOrgCode(pendingEntity.getReceiveOrgCode());
		CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoExp(vo);
		if(cusBargain == null){
			return false;
		}else{
			return true;
		}
	}
	
	
	//当付款方式为月结，补录的编码校验不通过，对于电话带出的多个编码校验是否有月结的编码，只有月结编码采用
	private  boolean isMonthTodo(String deliveryCustomerCode,String receiveOrgCode){
		CusBargainVo vo=new CusBargainVo();
		vo.setExpayway(WaybillConstants.MONTH_END);
		vo.setCustomerCode(deliveryCustomerCode);
		vo.setBillDate(new Date());
		vo.setBillOrgCode(receiveOrgCode);
		CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoExp(vo);
		if(cusBargain == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 查询时效
	 * 220125
	 */
	private void setIsNextPending(ActualFreightEntity actualFreightEntity,WaybillPendingEntity waybillPendingEntity){
		//出发部门
		String rcode =waybillPendingEntity.getReceiveOrgCode();
		//收货部门
		String ccode =waybillPendingEntity.getCustomerPickupOrgCode();
		// 产品
		String pcode = waybillPendingEntity.getProductCode();
		//开单时间
		Date bdate = waybillPendingEntity.getBillTime();
		//查找走货路径				
		List<FreightRouteLineDto> lines=null;
		try{
			lines=freightRouteService.queryEnhanceFreightRouteBySourceTarget(rcode,ccode,pcode,bdate);	
		}catch(Exception e){
			System.out.println("获取走货路径失败！");
		}
		if(CollectionUtils.isNotEmpty(lines)){
			//出发外场
		    String  oneTargetCode =null;
		    //最终外场
		    String lastSourceCode=null;
			if(lines.size()>=2){
				for(int i=0;i<=lines.size();i++){
				  if(i==0){
					  oneTargetCode=  lines.get(i).getTargetCode();
				  }
//				  if(i==1){
				  if(i==lines.size()-1){
//					  twoTargetCode=  lines.get(i).getTargetCode();
					  lastSourceCode= lines.get(i).getSourceCode();
					  FreightRouteEntity freightRoute = new FreightRouteEntity();
					  freightRoute.setOrginalOrganizationCode(oneTargetCode);
//					  freightRoute.setDestinationOrganizationCode(twoTargetCode);
					  freightRoute.setDestinationOrganizationCode(lastSourceCode);
					  freightRoute.setTransType(pcode);
					  freightRoute.setActive("Y");
					  //查询时效
					  List<FreightRouteEntity> fys= freightRouteService.querySimpleFreightRouteListByCondition(freightRoute,0, NumberConstants.NUMBER_100);
					  
					  if(CollectionUtils.isNotEmpty(fys)){
						  //时效大于13个小时
						  FreightRouteEntity route = fys.get(0);
						  Long aging=route.getAging();
						  if(aging!=null && aging / NumberConstants.NUMBER_1000 > NumberConstants.NUMBER_13){
							  actualFreightEntity.setIsNextDayPending("次日补录");
						  }else{
							  actualFreightEntity.setIsNextDayPending("当日补录");
						  }
					  }
				  }
				 
				}
		 }
	 }
}
	
}