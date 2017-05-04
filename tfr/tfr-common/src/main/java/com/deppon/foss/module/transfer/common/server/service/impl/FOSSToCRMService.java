package com.deppon.foss.module.transfer.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LineException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.transfer.common.api.server.dao.ICrmToFossDao;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToCRMService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.OrgDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressThroughPackagePathService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.PathdetailExtensionDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.deppon.foss.util.define.FossConstants;

/**
 * FOSS-TFR与CRM系统交互
 * @author 105795-foss-wqh
 * @date 2014-10-29 上午11:00:05
 */
public class FOSSToCRMService implements IFOSSToCRMService{

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(FOSSToCRMService.class);
	
	/**
	 * 组织相关service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 接送货获取运单信息接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 走货路径
	 * */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 更改单
	 * */
	private IWaybillRfcVarificationService waybillRfcVarificationService;
	
	
	/**
	 * 直达包
	 * 
	 * */
	private IExpressThroughPackagePathService expressThroughPackagePathService;
	
	/**
	 * 库存
	 * */
	private IStockService stockService;
	
	
	/**
	 * 综合生成走货路径service
	 */
	private IFreightRouteService freightRouteService;
	
	
	/**
	 * 交接单 IHandOverBillService
	 * */
	
	private IHandOverBillService handOverBillService;
	
	/**
	 * 配载单
	 * */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/**
	 * 公司车
	 * 
	 * */
	private IVehicleService vehicleService;
	
	/**
	 * 
	 * 员工
	 * */
	private IEmployeeService employeeService;
	
	
	/**
	 * dao
	 * */
	private ICrmToFossDao crmToFossDao;
	
	/**
	 * 营业部
	 * */
	private ISaleDepartmentService saleDepartmentService;
	
	/**卸车任务*/
	private IUnloadTaskService unloadTaskService;
	
	
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	/**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:匹配责任部门 
	 * @parm:waybillNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年10月29日上午10:10:26
	 */

	public List<OrgDto> matchResponbilityOrg(String waybillNo){
		// 设置相应header
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ESB_CRM2ESB_MATCH_RESPONSIBLE");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");

		List<OrgDto> result=new ArrayList<OrgDto>();
		List<OrgDto> responbilityOrgCode=null;
		OrgDto dto=new OrgDto();
		//处理结果
		try {
			responbilityOrgCode=handleResult(waybillNo);
			//最后出来结果
			if(CollectionUtils.isEmpty(responbilityOrgCode)){
				dto=new OrgDto();
				dto.setMessage("没有找到责任部门");
				result.add(dto);
			}else{
				for(OrgDto orgDto:responbilityOrgCode){
					OrgAdministrativeInfoEntity org=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgDto.getOrgCode());	
					//标杆编码
					orgDto.setOrgUnifiedCode(org.getUnifiedCode());
					//是否外场
					if(FossConstants.YES.equals(org.getTransferCenter())){
						orgDto.setIsTransferCenter(FossConstants.YES);
					}else{
						orgDto.setIsTransferCenter(FossConstants.NO);
					}
					//部门编码
					orgDto.setOrgCode(org.getCode());
					//任务部门名称
					orgDto.setOrgName(org.getName());
					//添加集合
					result.add(orgDto);
				}
				
			}
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return result;
		} catch (TfrBusinessException e) {
			 dto=new OrgDto();
			 dto.setMessage(e.getMessage());
		     result.add(dto);
		     response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			 return result;
		}
		
	}

	
	/**
	 * RULE-A2	根据发车时间匹配责任部门：
		1、	收货部门发车时间晚于准点发车时间，则匹配“收货部门”为责任部门（精准汽运（长）、精准汽运（短）可拉货48小时）。
		2、	准点发车时间取开单最近日期的最后一个班次，如果一个部门存在多个班次发车时间，则取最后班次的发车时间
	  RULE-A3	根据运行时效匹配责任部门：
		1、收货部门到外场的运行时效大于规定运行时效，则司机所属车队为责任部门。
		2、外场到外场的运行时效大于规定的运行时效，则司机所属车队为责任部门。
		3、外场到到达部门的运行时效大于规定的运行时效，则司机所属车队为责任部门。
		4、基于以上3点，如果存在多个运行时效有责的部门，则取运行时效超时最长的部门为责任部门。
		注：①如果为外请车，则外请车所属车队为责任部门。②运行时效取综合基础资料的时间。③公司车运行时效取卡航的运行时效，外请车取普货的运行时效。
	 * 
	 * */
	private List<OrgDto> findResponbilityOrgDepartOrOntheway(WaybillEntity waybillEntity,
			List<PathdetailExtensionDto> pathDeatilVaidList,Map productMap){
		   List<OrgDto> findResult=new ArrayList<OrgDto>();
		   OrgDto org=null;
		  //定义 精准汽运（长）、精准汽运（短）拉货时间，48小时
			long overtime=2L*TransferConstants.SONAR_NUMBER_24*TransferConstants.SONAR_NUMBER_3600*TransferConstants.SONAR_NUMBER_1000;
			
			List<OrgDto> resultOrgCode=new ArrayList<OrgDto>();
			
			PathdetailExtensionDto pathdetail=pathDeatilVaidList.get(0);
			/**
			 * 根据发车时间匹配责任部门：
			 * 1、	收货部门发车时间晚于准点发车时间，则匹配“收货部门”为责任部门（精准汽运（长）、精准汽运（短）可拉货48小时）。
			 * 2、	准点发车时间取开单最近日期的最后一个班次，如果一个部门存在多个班次发车时间，则取最后班次的发车时间
			 * */
			
			// 走货路径线路
			FreightRouteLineDto freightRouteLine = handleFreightRouteLineDto(pathdetail.getOrigOrgCode(),pathdetail.getObjectiveOrgCode(),  waybillEntity.getProductCode(),productMap);
			
			
		    //从出发部门到到达部门同一种产品存在多条线路
	    	//实际发车时间
			Date actDepartTime=pathdetail.getActualStartTime();
	    	if(actDepartTime==null){
	    		org=new OrgDto();
	    		org.setOrgCode(pathdetail.getOrigOrgCode());
	    		org.setMessage("没有进行发车!");
	    		findResult.add(org);
	    	}
	    	
	    	
	    	//基础资料标准发车时间
	    	Date provisionDepartTime=freightRouteLine.getLeaveDate();
	    	
	    	if(provisionDepartTime==null){
		    	logger.error(getNameByCode("{"+pathdetail.getOrigOrgCode())+"->"+getNameByCode(pathdetail.getObjectiveOrgCode())+"}"+productMap.get(waybillEntity.getProductCode())+" 没有配置基础资料标准发车时间!");
				throw new TfrBusinessException(getNameByCode("{"+pathdetail.getOrigOrgCode())+"->"+getNameByCode(pathdetail.getObjectiveOrgCode())+"}"+productMap.get(waybillEntity.getProductCode())+" 没有配置基础资料标准发车时间!");	
	
	    	}
	    	//判断是否 精准汽运（长）、精准汽运（短）可拉货48小时
	    	if("LRF".equalsIgnoreCase(waybillEntity.getProductCode())
	    			||"SRF".equalsIgnoreCase(waybillEntity.getProductCode())){
	    		
	    		provisionDepartTime=new Date(provisionDepartTime.getTime()+overtime);
	    	
	    	}
	    	//判断大小
	    	if(actDepartTime!=null&&actDepartTime.compareTo(provisionDepartTime)>0){
	    		//出发部门为责任部门
	    		org=new OrgDto();
	    		org.setOrgCode(pathdetail.getOrigOrgCode());
	    		org.setMessage("实际发车时间大于规定发车时间!");
	    		findResult.add(org);
	    		//resultOrgCode.add(pathdetail.getOrigOrgCode());
	    		
	    	}
		 //查询该运单做过的所有交接单（时间先后排序）
	     List<HandOverBillEntity> handOverBillList=handOverBillService.queryHandOveredRecordsByWaybillNo(waybillEntity.getWaybillNo());
	     /*if(CollectionUtils.isEmpty(handOverBillList))
	     {
	    	 logger.error("运单："+waybillEntity.getWaybillNo()+" 没有做过交接记录！");
			 throw new TfrBusinessException("运单："+waybillEntity.getWaybillNo()+" 没有做过交接记录！");	
	     }*/
	    
	    
	    /**
		 * 根据运行时效匹配责任部门：
			1、收货部门到外场的运行时效大于规定运行时效，则司机所属车队为责任部门，运行时间=准点到达时间-准点发车时间,如果交接单中的司机为空，则不匹配责任部门。
			2、外场到外场的运行时效大于规定的运行时效，则司机所属车队为责任部门，则司机所属车队为责任部门（取配载单中的司机去找责任部门。
			3、外场到到达部门的运行时效大于规定的运行时效，则司机所属车队为责任部门，运行时间=准点到达时间-准点发车时间，如果交接单中的司机为空，则不匹配责任部门。
			4、基于以上3点，如果存在多个运行时效有责的部门，则取运行时效超时最长的部门为责任部门。
			5 外请车，通过车牌去找对应的所属车队
			注：①如果为外请车，则外请车所属车队为责任部门。②运行时效取综合基础资料的时间。③公司车运行时效取卡航的运行时效，外请车取普货的运行时效。
		 * */	
	     boolean isOwn=true;
	     //默认为普货时效
	     String productCode="FSF";
	     //运行时效
	     long aging=0;
	     //最大实际时效 
	     long maxAgins=0;
	     //保留前一段时效差值
	     long temAgins=0;
	     //时间运行时效
	     long actAgins=0;
	    //是否外场到外场
	     boolean isFoToFo=true;
	     //上一个卸车部门时效
	     long lastAgin=0;
	     
	     if(CollectionUtils.isNotEmpty(handOverBillList)&&handOverBillList.size()>0){
	    	 for(int i=0;i<pathDeatilVaidList.size();i++){
	    		 //收货部门到外场的运行时效大于规定运行时效，则司机所属车队为责任部门
	    		 pathdetail=pathDeatilVaidList.get(i);
	    		 
	    		 //先判断运单是否在本段到达部门有过入库记录，如果没有则本段不做任何操作
	    		 List<InOutStockEntity>  inOutStockList=
	    				 stockService.queryInStockInfo(waybillEntity.getWaybillNo(), "0001", 
	    						 pathDeatilVaidList.get(i).getObjectiveOrgCode(), waybillEntity.getBillTime()) ;
	    		 if(CollectionUtils.isEmpty(inOutStockList)||pathdetail.getActualStartTime()==null||
	    				 pathdetail.getActualArriveTime()==null){
	    			 continue;
	    		 }
	    		 
	    		 if(StringUtil.isEmpty(pathDeatilVaidList.get(i).getVehicleNo())){
	    			 continue;
	    		 }

	    		 
	    		 //根据车牌判断是否外请车  OUTER ：外请车   OWN：公司车
	    		 List<String> vehicleTypeList= queryBelongVehicleByVehicleNo(pathdetail.getVehicleNo());
	    		 String vehicleType=null;
	    		 if(CollectionUtils.isNotEmpty(vehicleTypeList)||vehicleTypeList.size()>1){
	    			//如果该车牌既是公司车又是外请车按公司车算
	    			 vehicleType=vehicleTypeList.get(0);
	    		 }
	    		 if("OUTER".equalsIgnoreCase(vehicleType)){
	    			 isOwn=false;
	    		 }else{
	    			 productCode="FLF";
	    		 }
	    		
	    		 //取综合的基础资料
	    		 freightRouteLine=handleFreightRouteLineDto(pathdetail.getOrigOrgCode(),
	    				 pathdetail.getObjectiveOrgCode(),productCode,productMap);
	    		 //综合基础资料为分钟，这里需要将分钟转为毫秒
	    		 aging=freightRouteLine.getAging()*TransferConstants.SONAR_NUMBER_60*TransferConstants.SONAR_NUMBER_1000;
	    		 //如果不存在时效，则为始发或到达，取 运行时间=准点到达时间-准点发车时间
	    		 if(aging==0){
	    			 aging=freightRouteLine.getArriveDate().getTime()-freightRouteLine.getLeaveDate().getTime();
	    			 //营业部到外场或者外场到营业部
	    			 isFoToFo=false;
	    		 }
	    		 actAgins=pathdetail.getActualArriveTime().getTime()-pathdetail.getActualStartTime().getTime();
	    		 
	    		 //比较实际运行时效与规定运行时效,如果大于，存在责任部门
	    		 if(actAgins>aging){
	    			 temAgins=actAgins-aging;
	    			 
	    			 //比较本段时效差值一上一段时效差值，如果小于上一段，本段不在找责任部门
	    			 if(temAgins<maxAgins){
	    				 continue;
	    			 }else{
	    				 maxAgins=temAgins;
	    			 }
	    			 
	    			 if(isOwn){
	    				 //通过交接单找出对应的司机
	    				 for(HandOverBillEntity handOverBill :handOverBillList){
	    					 if(handOverBill.getDepartDeptCode().equalsIgnoreCase(pathdetail.getOrigOrgCode())&&
	    							 handOverBill.getArriveDeptCode().equalsIgnoreCase(pathdetail.getObjectiveOrgCode())){
	    						 //通过司机找对应的车队
	    						 //如果为外场到外场，取配载单中的司机编码
	    						 if(isFoToFo){
	    							 //通过交单找配载单
	    							 String vehiclessembleNo= handOverBillService.getVehicleassembleNoByHandoverNo(handOverBill.getHandOverBillNo());
	    							 //通过配载单查询配载单信息
	    							 List<VehicleAssembleBillEntity> vehicleAssembleBillList=vehicleAssembleBillService.queryVehicleAssembleBillByNo(vehiclessembleNo);
	    							 //取司机
	    							 String driverCode=vehicleAssembleBillList.get(0).getDriverCode();
	    							 //通过司机找所属部门
	    							 EmployeeEntity employeeEntity=employeeService.queryEmployeeByEmpCode(driverCode);
	    							 if(employeeEntity!=null){
	    								 //获取责任部门编码
	    								 org=new OrgDto();
	    								 org.setOrgCode(employeeEntity.getOrgCode());
	    								 org.setMessage("实际运行时效大于规定运行时效!");
	    								 findResult.add(org);
	    								 // resultOrgCode.add(employeeEntity.getOrgCode());
	    							 }
	    							 
	    							 break;
	    						 }else{
	    							 //取交接单中的编码
	    							 //取司机
	    							 String driverCode=handOverBill.getDriver();
	    							 if(StringUtil.isNotEmpty(driverCode)){
	    								 //通过司机找所属部门
	    								 EmployeeEntity employeeEntity=employeeService.queryEmployeeByEmpCode(driverCode);
	    								 if(employeeEntity!=null){
	    									 //获取责任部门编码
	    									 org=new OrgDto();
	    									 org.setOrgCode(employeeEntity.getOrgCode());
	    									 org.setMessage("实际运行时效大于规定运行时效!");
	    									 findResult.add(org);
	    									 //resultOrgCode.add(employeeEntity.getOrgCode());
	    								 }
	    							 }
	    							 break;
	    							 
	    						 }
	    					 }
	    				 }
	    			 }else{
	    				 //外请车，通过车牌去找对应的所属车队
	    				 String vehicleNo= pathdetail.getVehicleNo();
	    				 if(StringUtil.isNotEmpty(vehicleNo)){
	    					 VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
	    					 if(vehicleAssociationDto!=null){
	    						 //车队编码
	    						 String motorCadeCode= vehicleAssociationDto.getVehicleMotorcadeCode();
	    						 if(StringUtil.isNotEmpty(motorCadeCode)){
	    							 org=new OrgDto();
	    							 org.setOrgCode(motorCadeCode);
	    							 org.setMessage("实际运行时效大于规定运行时效!");
	    							 findResult.add(org);
	    							 //resultOrgCode.add(motorCadeCode);
	    						 }
	    						 
	    					 }
	    				 }
	    			 }
	    			 
	    		 }
	    		 /**
	    		  * 根据卸车时效找责任部门
	    		  * */	
	    		 
	    		 lastAgin=findResonbilityByUnloadAgeing(resultOrgCode,lastAgin,waybillEntity.getWaybillNo(),pathdetail.getObjectiveOrgCode(),pathdetail.getActualArriveTime());
	    		 
	    	 }
	    	 
	     }
		findResult.addAll(resultOrgCode);
		
		//去除重复责任部门 且不能多于3个责任部门
		resultOrgCode=handleResponbilityOrg(findResult);
		
		return findResult;
	}
	
	
	

	/**
	 * 根据卸货时效匹配责任部门：
		1   在FOSS中可以查到卸车开始时间及卸车结束时间，若外场未录卸车时间，则外场有责；
		2   货物未到最终目的站：
		a.	卸货晚点，则卸货晚点的外场为责任部门，如果多个外场均卸货晚点，则卸货晚点最久的外场为责任部门。（注：实际卸货时间大于正常卸货时间，则卸货晚点）。
		b.  正常卸货时间=正常卸货所需时间（12分钟/吨计算）+0.5小时卸货等待时间）。
		c.  实际卸货时间=当票货最后一件卸货扫描时间-到达时间。
		3  货物已经到最终目的站：
		a	驻地部门卸货晚点，责任部门为驻地外场。
		b.	非驻地部门卸货晚点，责任部门为非驻地部门。
		c.	到达部门为驻地部门时，正常卸货时间=正常卸货所需时间（12分钟/吨计算）+0.5小时卸货等待时间）。
		d.  到达部门为非驻地部门时，正常卸货时间=正常卸货所需时间（20分钟/吨计算）+0.5小时卸货等待时间）。
	 * 
	 * */
	private long findResonbilityByUnloadAgeing(List<OrgDto> responbilityOrgList,long lastAgin,
			String waybillNo,String unloadOrgCode,Date arriveTime){
		long tempAgin=0;
		OrgDto orgDto=null;
	    //参数校验 
		if(StringUtil.isEmpty(waybillNo)){
			logger.error(waybillNo+" 运单号为空");
			throw new TfrBusinessException(waybillNo+" 运单号为空");	
		}
		if(StringUtil.isEmpty(unloadOrgCode)){
			logger.error(unloadOrgCode+" 卸车部门为空");
			throw new TfrBusinessException(unloadOrgCode+" 卸车部门为空");	
		}
		if(arriveTime==null){
			logger.error(waybillNo+" "+getNameByCode(unloadOrgCode)+"到达时间为空");
			throw new TfrBusinessException(waybillNo+" "+getNameByCode(unloadOrgCode)+"到达时间为空");	
		}
		//查找组织信息
		OrgAdministrativeInfoEntity org=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(unloadOrgCode);	
		if(org==null){
			logger.error(unloadOrgCode+" 部门组织不存在");
			throw new TfrBusinessException(unloadOrgCode+" 部门组织不存在");	
		}
		
		//查询运单在unloadOrgCode下面的卸车任务 非分批配载的运单在一个部门可能会出现在多个卸车任务中
		List<String> unloadTaskIdList=queryUnloadTaskNoByWaybillNo(waybillNo,unloadOrgCode);
		if(CollectionUtils.isNotEmpty(unloadTaskIdList)){
			for(int i=0;i<unloadTaskIdList.size();i++){
				//查询该卸车任务中的总重量
				BigDecimal totalWeight=queryTotalWeightByUnloadTaskNo(unloadTaskIdList.get(i));
				//根据卸车任务id查询卸车任务实体
				UnloadTaskEntity unloadTaskEntity=unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskIdList.get(i));
				//最后一件卸货时间
				Date lastUnloadTime=queryUnloadLastScanTimeByWaybillNo(waybillNo,unloadTaskEntity.getId(),unloadOrgCode);
			    if(lastUnloadTime==null){
					logger.error(waybillNo+"  在  "+unloadOrgCode+" 部门不存在扫描记录");
					continue;

			    }
				//实际卸货时间 =当票货最后一件卸货扫描时间-到达时间
				long actUnloadTime=lastUnloadTime.getTime()-arriveTime.getTime();
				//规定卸货时间
				long provisionUnloadTime=0;
				//判断是否录入卸车开始、结束时间
				if(unloadTaskEntity.getUnloadStartTime()==null||unloadTaskEntity.getUnloadEndTime()==null){
					//没有录入卸车开始、结束时间,当前部门有责
					
					//判断部门组织性质（外场还是营业部或者驻地部门）
					if(FossConstants.YES.equalsIgnoreCase(org.getSalesDepartment())){
						//查询营运部信息
						SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(unloadOrgCode);
						
						//判断是否驻地部门
						if(FossConstants.YES.equalsIgnoreCase(saleDetp.getStation())){
							orgDto=new OrgDto();
							orgDto.setOrgCode(saleDetp.getTransferCenter());
							orgDto.setMessage("没有录入实际卸车开始时间或实际卸车结束时间!");
							responbilityOrgList.add(orgDto);
							//responbilityOrgList.add(saleDetp.getTransferCenter());
						}else{
						//非驻地部门
							orgDto=new OrgDto();
							orgDto.setOrgCode(unloadOrgCode);
							orgDto.setMessage("没有录入实际卸车开始时间或实际卸车结束时间!");
							responbilityOrgList.add(orgDto);
							//responbilityOrgList.add(unloadOrgCode);
						}
						
					}else{
					  //卸车部门是外场	
						orgDto=new OrgDto();
						orgDto.setOrgCode(unloadOrgCode);
						orgDto.setMessage("没有录入实际卸车开始时间或实际卸车结束时间!");
						responbilityOrgList.add(orgDto);
						//responbilityOrgList.add(unloadOrgCode);
					}
					
				}else{
					//如果录入 卸车开始、结束时间 
					
					//判断部门组织性质（外场还是营业部或者驻地部门）
					if(FossConstants.YES.equalsIgnoreCase(org.getSalesDepartment())){
						//查询营运部信息
						SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(unloadOrgCode);
						
						//判断是否驻地部门
						if(FossConstants.YES.equalsIgnoreCase(saleDetp.getStation())){
							//规定卸货时间(毫秒)
							 provisionUnloadTime=calculuteUnloadTime(true,totalWeight)*TransferConstants.SONAR_NUMBER_60*TransferConstants.SONAR_NUMBER_1000;
							//实际卸货时间与规定卸货时间比较
							 tempAgin=actUnloadTime-provisionUnloadTime;
							//责任部门判断
							 judgeResponbilityOrg(responbilityOrgList,tempAgin,lastAgin,saleDetp.getTransferCenter());
							
							
						}else{
						//非驻地部门,营业部
						   //规定卸货时间(毫秒)
							provisionUnloadTime=calculuteUnloadTime(false,totalWeight)*TransferConstants.SONAR_NUMBER_60*TransferConstants.SONAR_NUMBER_1000;
							//实际卸货时间与规定卸货时间比较
							tempAgin=actUnloadTime-provisionUnloadTime;
							//责任部门判断
							judgeResponbilityOrg(responbilityOrgList,tempAgin,lastAgin,unloadOrgCode);
							
						}
						
						
						
					}else{
					  //卸车部门是外场	
						//规定卸货时间(毫秒)
						provisionUnloadTime=calculuteUnloadTime(true,totalWeight)*TransferConstants.SONAR_NUMBER_60*TransferConstants.SONAR_NUMBER_1000;
						//实际卸货时间与规定卸货时间比较
						tempAgin=actUnloadTime-provisionUnloadTime;
						//责任部门判断
						judgeResponbilityOrg(responbilityOrgList,tempAgin,lastAgin,unloadOrgCode);
						
					}
				}
				
			}
			
		}
		
		return tempAgin;
	}
	
	/**
	 * 责任部门判断
	 * 
	 * */
	private void judgeResponbilityOrg(List<OrgDto> responbilityOrgList,long tempAgin,long lastAgin,String unloadOrgCode){
		OrgDto orgDto =null;
		if(tempAgin>0){
			//实际卸货时间大于规定卸货时间，驻地部门的外场有责
			//与上一部门的实际卸货时间比较，如果大于上一部门实际卸车时间，将上一部门的删除，加入本次卸车部门为责任部门
			if(tempAgin>lastAgin){
				//第一个卸车部门时 集合里面为空，需做下面处理
				if(CollectionUtils.isNotEmpty(responbilityOrgList)||responbilityOrgList.size()>0){
					responbilityOrgList.remove(0);
				}
				orgDto=new OrgDto();
				orgDto.setOrgCode(unloadOrgCode);
				orgDto.setMessage("实际卸货时间大于规定卸货时间!");
				responbilityOrgList.add(orgDto);
				//responbilityOrgList.add(unloadOrgCode);
			}else if(tempAgin==lastAgin){
				//如果两次时效一致，则本次卸车部门也为责任部门
				orgDto=new OrgDto();
				orgDto.setOrgCode(unloadOrgCode);
				orgDto.setMessage("实际卸货时间大于规定卸货时间!");
				responbilityOrgList.add(orgDto);
				//responbilityOrgList.add(unloadOrgCode);
			}
		}
	}
	
	
	/**
	 * 计算卸货时间(分钟)：
	 * 外场及驻地部门：正常卸货时间=正常卸货所需时间（12分钟/吨计算）+0.5小时卸货等待时间）
	 * 营业部：正常卸货时间=正常卸货所需时间（20分钟/吨计算）+0.5小时卸货等待时间）
	 * */
	private long calculuteUnloadTime(boolean isOutfiled,BigDecimal totalWeight){
		long result=0;
		if(isOutfiled){
			result=(new BigDecimal(TransferConstants.SONAR_NUMBER_12).multiply(totalWeight)).longValue()+TransferConstants.SONAR_NUMBER_30;
		}else{
			result=(new BigDecimal(TransferConstants.SONAR_NUMBER_20).multiply(totalWeight)).longValue()+TransferConstants.SONAR_NUMBER_30;
		}
		
		return result;
	}
	
	
	/**
	 * 去除重复的责任部门，如果超过3个以上，则保留3个责任部门
	 * */
	private List<OrgDto> handleResponbilityOrg(List<OrgDto> responbilityOrgList){
		List<OrgDto> result=new ArrayList<OrgDto>();
		Set<String> set=new HashSet<String>();
		for(OrgDto orgCode:responbilityOrgList){
			if(set.size()>TransferConstants.SONAR_NUMBER_3){
				break;
			}
			set.add(orgCode.getOrgCode());
		}
		Iterator<String> ite=set.iterator();
		while(ite.hasNext()){
			for(OrgDto orgCode:responbilityOrgList){
				if(ite.next().equals(orgCode.getOrgCode())){
					result.add(orgCode);
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * 包装运输性质
	 * */
	private Map pushMapProductCode(){
		Map<String,String> productMap=new HashMap<String,String>(); 
		productMap.put("FLF", "精准卡航");//精准卡航
		productMap.put("FSF","精准城运");//精准城运
		productMap.put("LRF","精准汽运（长）");//精准汽运（长）
		productMap.put("SRF","精准汽运（短）");//精准汽运（短）
		productMap.put("BGFLF","精准大票卡航");//精准大票卡航
		productMap.put("BGLRF","精准大票汽运(长)");//精准大票汽运(长)
		productMap.put("BGFSF","精准大票城运");//精准大票城运
		productMap.put("BGSRF","精准大票汽运(短)");//精准大票汽运(短)
		
		return productMap;
	}

	/**
	 * 根据部门code获取部门名称
	 * */
	private String getNameByCode(String orgCode) {
		// 根据部门代码获取部门名称
		String orgName = orgAdministrativeInfoService.queryCommonNameByCommonCodeFromCache(orgCode);
		// 名称为空
		if (StringUtils.isEmpty(orgName)) {
			// 返回代码
			return orgCode;
		}else {
			// 返回名称
			return orgName;
		}
	}
	
	/**
	 * 包装综合走货路径基础资料
	 * */
	private FreightRouteLineDto handleFreightRouteLineDto(String origOrgCode,String destOrgCode, String productCode,Map productMap){
		// 走货路径线路
		List<FreightRouteLineDto> freightRouteLineList = null;
		
		// 调用基础资料接口得到路径detail 从现部门之后
		try {
			freightRouteLineList = freightRouteService.queryFreightRouteBySourceTarget(origOrgCode,destOrgCode,  productCode, new Date());

		} catch (LineException e) {
			logger.error("{ 调综合线路配置接口异常：}"+e.getMessage());
			throw new TfrBusinessException("{ 调综合线路配置接口异常：}"+e.getMessage());	
		}
	    if(CollectionUtils.isEmpty(freightRouteLineList)){
	    	//将部门找出来，提示用户
	    	logger.error(getNameByCode("{"+origOrgCode)+"->"+getNameByCode(destOrgCode)+"}"+productMap.get(productCode)+" 没有配置走货路径!");
			throw new TfrBusinessException(getNameByCode("{"+origOrgCode)+"->"+getNameByCode(destOrgCode)+"}"+productMap.get(productCode)+" 没有配置走货路径!");	
	    }
	    if(freightRouteLineList.size()>1){
	    	logger.error(getNameByCode("{"+origOrgCode)+"->"+getNameByCode(destOrgCode)+"}"+productMap.get(productCode)+" 存在多条基础线路配置!");
			throw new TfrBusinessException(getNameByCode("{"+origOrgCode)+"->"+getNameByCode(destOrgCode)+"}"+productMap.get(productCode)+" 存在多条基础线路配置!");	
	    }
	    
	   return  freightRouteLineList.get(0);
	}
	
	
	/**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:通过运单号，卸车部门找所在的 卸车任务编号
	 * @parm:waybillNo
	 * @param:unloadOrgCode 
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
	public List<String> queryUnloadTaskNoByWaybillNo(String waybillNo,String unloadOrgCode){
		if(StringUtil.isEmpty(waybillNo)){
			 logger.error("运单号不能为空！");
			 throw new TfrBusinessException("运单号不能为空！");	
		}
		if(StringUtil.isEmpty(unloadOrgCode)){
			 logger.error("卸车部门不能为空！");
			 throw new TfrBusinessException("卸车部门不能为空！");	
		}
		
		return crmToFossDao.queryUnloadTaskNoByWaybillNo(waybillNo, unloadOrgCode);
	}

	/**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:通过卸车任务统计卸车任务下的总重量 （顿）
	 * @parm:unloadTaskNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
    public BigDecimal queryTotalWeightByUnloadTaskNo(String unloadTaskId){
    	if(StringUtil.isEmpty(unloadTaskId)){
			 logger.error("卸车编号ID不能为空！");
			 throw new TfrBusinessException("卸车编码ID不能为空！");	
		}
    	return crmToFossDao.queryTotalWeightByUnloadTaskNo(unloadTaskId);
    }
	
	
    /**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:查询运单最后一件货的扫描时间 
	 * @parm:waybillNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
    public Date queryUnloadLastScanTimeByWaybillNo(String waybillNo,String unloadTaskId,String unloadOrgCode){
    	if(StringUtil.isEmpty(waybillNo)){
			 logger.error("运单号不能为空！");
			 throw new TfrBusinessException("运单号不能为空！");	
		}
		if(StringUtil.isEmpty(unloadOrgCode)){
			 logger.error("卸车部门不能为空！");
			 throw new TfrBusinessException("卸车部门不能为空！");	
		}
		
		return crmToFossDao.queryUnloadLastScanTimeByWaybillNo(waybillNo,unloadTaskId,unloadOrgCode);
    }
    
    
    
    /**
     * 对所有的处理过程进行包装，反馈异常信息给调用者
     * */
	/**
	 * ULE-A1	责任部门匹配基于以下规则：
		1运单的运输性质为“精准卡航”、“精准城运”、“精准汽运（长）”、“精准汽运（短）”时，FOSS才匹配责任部门；
		2运单如果有更改过目的站、运输性质或进行了分批配载，则FOSS不需要匹配责任部门；
		3 货物已经到最终目的站，且承诺时效已兑现（货物实际到达目的站时间在FOSS-网点价格查询-营运时效最后一天的取货时间之前），则无责任部门；
		4  下面所有业务都基于本规则；
	  RULE-A2	根据发车时间匹配责任部门：
		1、	收货部门发车时间晚于准点发车时间，则匹配“收货部门”为责任部门（精准汽运（长）、精准汽运（短）可拉货48小时）。准点发车时间取综合基础资料时间（运单预计送货时间）。
		2、	准点发车时间取开单最近日期的最后一个班次，如果一个部门存在多个班次发车时间，则取最后班次的发车时间
	  RULE-A3	根据运行时效匹配责任部门：
		1、收货部门到外场的运行时效大于规定运行时效，则司机所属车队为责任部门。
		2、外场到外场的运行时效大于规定的运行时效，则司机所属车队为责任部门。
		3、外场到到达部门的运行时效大于规定的运行时效，则司机所属车队为责任部门。
		4、基于以上3点，如果存在多个运行时效有责的部门，则取运行时效超时最长的部门为责任部门。
		注：①如果为外请车，则外请车所属车队为责任部门。②运行时效取综合基础资料的时间。③公司车运行时效取卡航的运行时效，外请车取普货的运行时效。
	  RULE-A4	根据卸货时效匹配责任部门：
		1   在FOSS中可以查到卸车开始时间及卸车结束时间，若外场未录卸车时间，则外场有责；
		2   货物未到最终目的站：
		a.	卸货晚点，则卸货晚点的外场为责任部门，如果多个外场均卸货晚点，则卸货晚点最久的外场为责任部门。（注：实际卸货时间大于正常卸货时间，则卸货晚点）。
		b.  正常卸货时间=正常卸货所需时间（12分钟/吨计算）+0.5小时卸货等待时间）。
		c.  实际卸货时间=当票货最后一件卸货扫描时间-到达时间。
		3  货物已经到最终目的站：
		a	驻地部门卸货晚点，责任部门为驻地外场。
		b.	非驻地部门卸货晚点，责任部门为非驻地部门。
		c.	到达部门为驻地部门时，正常卸货时间=正常卸货所需时间（12分钟/吨计算）+0.5小时卸货等待时间）。
		d.  到达部门为非驻地部门时，正常卸货时间=正常卸货所需时间（20分钟/吨计算）+0.5小时卸货等待时间）。
	  RULE-A5	相同责任部门只取其中一个:
		FOSS匹配出的任务部门为2个及以上，且这些责任部门中存在相同部门时，则只取这些相同部门中其中任何一个为责任部门，并将该责任部门通过接口传给CRM。
	  RULE-A6	FOSS返回0至3个部门给CRM
		
	 * */
    private List<OrgDto> handleResult(String waybillNo){
    	
    	       //责任部门
    	  		List<OrgDto> responbilityOrgCode=new ArrayList<OrgDto>();
    			//运单号为空
    			if(StringUtil.isBlank(waybillNo)){
    			  logger.error("运单号为空");
    			  throw new TfrBusinessException("运单号为空");	
    			}
    			//运输性质为“精准卡航” FLF 、“精准城运” FSF、“精准汽运（长）” LRF、“精准汽运（短）” SRF
    			List<String> productCodeList=new ArrayList<String>();
    			productCodeList.add("FLF");//精准卡航
    			productCodeList.add("FSF");//精准城运
    			productCodeList.add("LRF");//精准汽运（长）
    			productCodeList.add("SRF");//精准汽运（短）
    			productCodeList.add("BGFLF");//精准大票卡航
    			productCodeList.add("BGLRF");//精准大票汽运(长)
    			productCodeList.add("BGFSF");//精准大票城运
    			productCodeList.add("BGSRF");//精准大票汽运(短)
    			
    			Map productMap=pushMapProductCode();
    			
    			WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(waybillNo);
    			if(waybillEntity==null){
    				  logger.error("运单号: {"+waybillNo+"} 不存在");
    				  throw new TfrBusinessException("运单号: {"+waybillNo+"} 不存在");	
    			}
    			//判断运单产品类型
    			String productcode= waybillEntity.getProductCode();
    			if(!productCodeList.contains(productcode)){
    				 logger.error("运单号: {"+waybillNo+"} 不是 【精准卡航】 或 【精准城运】 或【精准汽运（长）】或【精准汽运（短）】或【精准大票卡航】或【精准大票汽运(长)】或【精准大票城运】或【精准大票汽运(短)】，不能匹配责任部门");
    				  throw new TfrBusinessException("运单号: {"+waybillNo+"} 不是 【精准卡航】 或 【精准城运】 或【精准汽运（长）】或【精准汽运（短）】或【精准大票卡航】或【精准大票汽运(长)】或【精准大票城运】或【精准大票汽运(短)】，不能匹配责任部门");	
    			}
    			//判断是否有分批配载
    			TransportPathEntity queryTransportEntity=calculateTransportPathService.queryTransportPath(waybillNo);
    			if("Y".equals(queryTransportEntity.getIfPartialStowage())){
    				  logger.error("运单号: {"+waybillNo+"} 进行了分批配载，不匹配责任部门");
    				  throw new TfrBusinessException("运单号: {"+waybillNo+"} 进行了分批配载，不匹配责任部门");	
    			}
    			//运单如果有更改过目的站、运输性质
    			boolean isRfc=false;
    			WaybillRfcPrintDto waybillRfcPrintDto=waybillRfcVarificationService.queryWaybillRfcPrintDto(waybillNo);
    			if(waybillRfcPrintDto!=null){
    			
    				List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailList=waybillRfcPrintDto.getChangeList();
    				if(!CollectionUtils.isEmpty(waybillRfcChangeDetailList)){
    					for(WaybillRfcChangeDetailEntity changeDetail:waybillRfcChangeDetailList){
    						//更改过目的站、运输性质
    						if(TransferConstants.CRM_RESPONBILITY_WAYBILL_TARGETORGCODE.equalsIgnoreCase(changeDetail.getRfcItems())
    								||TransferConstants.CRM_RESPONBILITY_WAYBILL_PRODUCTCODE.equalsIgnoreCase(changeDetail.getRfcItems())){
    							isRfc=true;
    						}
    					}
    					
    					
    				}
    				
    			}
    			//有更改过目的站、运输性质，不匹配责任部门
    			if(isRfc){
    				logger.error("运单号: {"+waybillNo+"} 有【更改过目的站】或【运输性质】不匹配责任部门");
    				 throw new TfrBusinessException("运单号: {"+waybillNo+"} 有【更改过目的站】或【运输性质】不匹配责任部门");	
    			}
    			
    			//判断是否已经到达目的站,如果运单在目的站有入库记录，说明运单已经到达，由于运单没有分批配载，这里流水号直接用0001来代替
    			List<InOutStockEntity>  inOutStockList=
    					stockService.queryInStockInfo(waybillNo, "0001", waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getBillTime()) ;
    			
    			//如果入库记录不为空，则运单已经到达目的
    			if(CollectionUtils.isNotEmpty(inOutStockList)){
    				//判断入库时间与预计提货时间,这里
    				Date inStockTime=(inOutStockList.get(inOutStockList.size()-1)).getInOutStockTime();
    				
    				//预计提货时间
    				Date prePickTime=waybillEntity.getPreCustomerPickupTime();
    				//如果预计提货时间>=入库时间//不用匹配责任编码
    				if((prePickTime.getTime()-inStockTime.getTime())>0){
    					logger.error("运单号: {"+waybillNo+"} 在承诺时间内到达，无需匹配责任编码");

    					//return null;
    				}
    				
    			}
    			
    			List<String> waybillNoList=new ArrayList<String>();
    			waybillNoList.add(waybillNo);
    			//获取该运单的所有走货路径
    			/*List<PathdetailExtensionEntity> pathDetailList=expressThroughPackagePathService.queryPathDetailList(waybillNoList);*/
    			List<PathdetailExtensionDto> pathDetailList = expressThroughPackagePathService.queryPathDetailLists(waybillNoList);
    			if(CollectionUtils.isEmpty(pathDetailList)){
    				logger.error("运单号: {"+waybillNo+"} 走货路径不存在");
    				throw new TfrBusinessException("运单号: {"+waybillNo+"} 走货路径不存在");	
    			}
    			/*//有效路由号
    			int validRouteNo=1;
    			//找出有效路由号
    			for(int i=0;i<pathDetailList.size();i++){
    				if(waybillEntity.getCreateOrgCode().equalsIgnoreCase(pathDetailList.get(i).getOrigOrgCode())){
    					validRouteNo=Integer.parseInt(pathDetailList.get(i).getRouteNo());
    					break;
    				}
    			}*/
    			
    			/**
    			 * 找出从有效路由号validRouteNo开始后的路段
    			 * */
    		/*	List<PathdetailExtensionEntity> pathDeatilVaidList=new ArrayList<PathdetailExtensionEntity>();
    			for(int i=validRouteNo-1;i<pathDetailList.size();i++){
    				pathDeatilVaidList.add(pathDetailList.get(i));
    			}*/
    			//根据发车、运行时效匹配责任部门
    			responbilityOrgCode=findResponbilityOrgDepartOrOntheway(waybillEntity,pathDetailList, productMap);
    	   return responbilityOrgCode;
    	
    }
    
    
    
    /**
   	 * @com.deppon.foss.module.transfer.common.api.server.service
   	 * @desc:判断车辆归属性
   	 * @parm:waybillNo
   	 * @return:void
   	 * @author:wqh
   	 * @date:2014年11月10日上午10:10:26
   	 */
    public List<String> queryBelongVehicleByVehicleNo(String vehicleNo){
    	if(StringUtil.isEmpty(vehicleNo)){
    		throw new TfrBusinessException("请输入车牌号");
    	}
       return	crmToFossDao.queryBelongVehicleByVehicleNo(vehicleNo);
    	
    }
    
    
	//set 
	
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}


	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}


	public void setWaybillRfcVarificationService(
			IWaybillRfcVarificationService waybillRfcVarificationService) {
		this.waybillRfcVarificationService = waybillRfcVarificationService;
	}


	public void setExpressThroughPackagePathService(
			IExpressThroughPackagePathService expressThroughPackagePathService) {
		this.expressThroughPackagePathService = expressThroughPackagePathService;
	}


	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}


	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}


	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}


	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}


	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setCrmToFossDao(ICrmToFossDao crmToFossDao) {
		this.crmToFossDao = crmToFossDao;
	}


	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}


	
}
