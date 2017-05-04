package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayForTfrService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseToCubcCallBack;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackageMainPriceDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageAssistPriceService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageMainPriceService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackagePriceToCubcService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackingToCubcEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.exception.PackagingException;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;


/**
 * @desc 主要包装金额service
 * @author foss-105795-wqh
 * @date   2014-04-29
 * */
public class PackageMainPriceService implements IPackageMainPriceService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(PackageMainPriceService.class);
	
	//获取开单信息
	private IWaybillManagerService waybillManagerService;
	//主包装接口
	private IPackageMainPriceDao packageMainPriceDao;
	//综合包装供应商接口
	private IPackagingSupplierService packagingSupplierService;
	//职员信息
	private IEmployeeService employeeService;
	//辅助包装service
	private IPackageAssistPriceService packageAssistPriceService;
	private IPackingRecAndPayForTfrService packingRecAndPayForTfrService;
	/**
	 * 获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//营业部代打木架信息
	private IWoodenRequirementsService woodenRequirementsService;
	//部门组织
	private IOrgAdministrativeInfoService OrgAdministrativeInfoService;
	//异步至CUBC
	private IPackagePriceToCubcService packagePriceToCubcService;
	//同步至CUBC
	private IFossToCubcService fossToCubcService;

	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/**
	 * @desc PackingRecAndPayTfrDto类型转换为PackingToCubcEntity类型 TODO
	 * @author 316759-RuipengWang-foss
	 * @date 2016-11-10 3:28:40 PM
	 * @see typeConversion
	 */
	private PackingToCubcEntity typeConversion(PackageMainPriceEntity packingRecAndPayTfrDto, String type, CurrentInfo currentInfo) {
		PackingToCubcEntity cubcEntity = null;
		if (packingRecAndPayTfrDto != null) {
			cubcEntity = new PackingToCubcEntity();
			// 运单号
			cubcEntity.setWaybillNo(packingRecAndPayTfrDto.getWaybillNo());
			// 开单部门code
			cubcEntity.setBillOrgCode(packingRecAndPayTfrDto.getBillOrgCode());
			// 开单部门名称
			cubcEntity.setBillOrgName(packingRecAndPayTfrDto.getBillOrgName());
			// 包装部门code
			cubcEntity.setPackageOrgCode(packingRecAndPayTfrDto.getPackageOrgCode());
			// 包装部门名称
			cubcEntity.setPackageOrgName(packingRecAndPayTfrDto.getPackageOrgName());
			// 应付金额
			cubcEntity.setPackagePayableMoney(packingRecAndPayTfrDto.getPackagePayableMoney());
			// 操作
			cubcEntity.setStatus(type);
			// 当前操作人工号
			cubcEntity.setCurentEmpCode(currentInfo.getEmpCode());
			// 当前登录人姓名
			cubcEntity.setCurentEmpName(currentInfo.getEmpName());
			// 当前登录人部门编码
			cubcEntity.setCurentEmpDeptCode(currentInfo.getCurrentDeptCode());
			// 当前登录人部门名称
			cubcEntity.setCurentEmpDeptName(currentInfo.getCurrentDeptName());
			
			//木条长度woodenBarLong
			cubcEntity.setWoodenBarLong(new BigDecimal("0"));
			//气泡膜体积	bubbVelamenVolume
			cubcEntity.setBubbVelamenVolume(new BigDecimal("0"));
			//缠绕膜体积	bindVelamenVolume
			cubcEntity.setBindVelamenVolume(new BigDecimal("0"));
			//包带根数	bagBeltNum
			cubcEntity.setBagBeltNum(0);
			//是否激活	active
			cubcEntity.setActive("");
			//审核人姓名	auditorName
			cubcEntity.setAuditorName("");
			//审核人code	auditorCode
			cubcEntity.setAuditorCode("");
			//审核日期	auditDate
			cubcEntity.setAuditDate(null);
			//反审核人姓名	deauditorName
			cubcEntity.setDeauditorName("");
			//反审核人code	deauditorCode
			cubcEntity.setDeauditorCode("");
			//反审核日期	deauditDate
			cubcEntity.setDeauditDate(null);
			//理论打木架体积	theoryFrameVolume
			cubcEntity.setTheoryFrameVolume(packingRecAndPayTfrDto.getTheoryFrameVolume());
			//实际打木架体积	actualFrameVolume
			cubcEntity.setActualFrameVolume(packingRecAndPayTfrDto.getActualFrameVolume());
			//打木架体积	packageFrameVolume
			cubcEntity.setPackageFrameVolume(packingRecAndPayTfrDto.getPackageFrameVolume());
			//理论打木箱体积	theoryWoodenVolume
			cubcEntity.setTheoryWoodenVolume(packingRecAndPayTfrDto.getTheoryWoodenVolume());
			//实际打木箱体积	actualWoodenVolume
			cubcEntity.setActualWoodenVolume(packingRecAndPayTfrDto.getActualWoodenVolume());
			//打木箱体积	packageWoodenVolume
			cubcEntity.setPackageWoodenVolume(packingRecAndPayTfrDto.getPackageWoodenVolume());
			//实际打木托个数	theoryMaskNumber
			cubcEntity.setTheoryMaskNumber(packingRecAndPayTfrDto.getTheoryMaskNumber());
			//打木托个数	actualMaskNumber
			cubcEntity.setActualMaskNumber(packingRecAndPayTfrDto.getActualMaskNumber());
			//包装供应商code	packageSupplierCode
			cubcEntity.setPackageSupplierCode(packingRecAndPayTfrDto.getPackageSupplierCode());
			//包装供应商	packageSupplierName
			cubcEntity.setPackageSupplierName(packingRecAndPayTfrDto.getPackageSupplierName());
			//包装材料
			cubcEntity.setPackageMaterial(packingRecAndPayTfrDto.getPackageMaterial());
			//创建部门
			cubcEntity.setCreateOrgName(packingRecAndPayTfrDto.getCreateOrgName());
			//创建部门Code
			cubcEntity.setCreateOrgCode(packingRecAndPayTfrDto.getCreateOrgCode());
			//创建人code
			cubcEntity.setCreateUserCode(packingRecAndPayTfrDto.getCreateUserCode());
			//创建人
			cubcEntity.setCreateUserName(packingRecAndPayTfrDto.getCreateUserName());
			//创建时间
			cubcEntity.setCreateTime(packingRecAndPayTfrDto.getCreateTime());
			//修改人code
			cubcEntity.setModifyUserCode(packingRecAndPayTfrDto.getModifyUserCode());
			//修改人
			cubcEntity.setModifyUserName(packingRecAndPayTfrDto.getModifyUserName());
			//精确到毫秒的修改时间
			cubcEntity.setModifyTimeMs(packingRecAndPayTfrDto.getModifyTimeMs());
			//修改时间
			cubcEntity.setModifyTime(packingRecAndPayTfrDto.getModifyTime());
			//是否当月单据
			cubcEntity.setIsNowMonth(packingRecAndPayTfrDto.getIsNowMonth());
			//审核状态
			cubcEntity.setAuditStatus(packingRecAndPayTfrDto.getAuditStatus());
		}
		return cubcEntity;
	}
	
	/**
	 * @desc 增加主要包装金额
	 * @author foss-105795-wqh
	 * @param  pdaPackagingInfoEntity
	 * @return null
	 * @date   2014-05-04
	 * */
	@Transactional
	public void addPackageMainPrice(PDAPackagingInfoEntity pdaPackagingInfoEntity)
	{
		LOGGER.info("开始添加主要包装金额信息");
		
		List<String> listMate=new ArrayList<String>();
		listMate.add(TransferPDADictConstants.PACKAGING_MATE_WOODEN_FRAME);
		listMate.add(TransferPDADictConstants.PACKAGING_MATE_WOOED_BOX);

		OrgAdministrativeInfoEntity orgOutField= getOutFieldCode(pdaPackagingInfoEntity.getOrgCode());
		/**
		 * 由于存在一些外场没有对应的包装供应商，故这里需要放开限制，如果PDA端没有上传包装供应商，需再次去查询该外场是否真的不存在包装供应商，
		 * 如果存在，返回信息给PDA，提示用户有包装供应商，请选择后再提交
		 * 
		 * 
		 * */
		if(StringUtil.isEmpty(pdaPackagingInfoEntity.getPackageSupplierCode()))
		{
			//需再次去外场查询包装供应商是否存在
			PackagingSupplierEntity entity=new PackagingSupplierEntity();
			entity.setOrgCodeCode(orgOutField.getCode());
			entity.setActive("Y");
			List<PackagingSupplierEntity> result=packagingSupplierService.queryPackagingSupplierListByOrgCode(entity, PackagingConstants.SONAR_NUMBER_10, 1);
			if(result!=null&&result.size()>0)
			{
				//将所有的包装供应商拼接
				StringBuffer strSupplier=new StringBuffer();
				for(int i=0;i<result.size();i++)
				{
					strSupplier.append(result.get(i).getPackagingSupplier()+" ");
					if(i<result.size()-1)
					{
						strSupplier.append(",");
					}
					
				}
				
				LOGGER.error(orgOutField.getName()+" 存在包装供应商："+strSupplier.toString());
				throw new PackagingException(orgOutField.getName()+" 存在包装供应商："+strSupplier.toString());
			}else{
				//不产生包装金额相关数据
				LOGGER.error("=============="+orgOutField.getName()+  "不存在包装供应商,不产生包装金额相关数据===============");
				return;
			}
			
		}
		
		//如果包装材料中没包含打木架，打木箱不生成包装金额信息
		if(!listMate.contains(pdaPackagingInfoEntity.getPackedMate()))
		{
			return;
		}
		//开单基本信息
		WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(pdaPackagingInfoEntity.getWayBillNumber());
		if(waybillEntity==null)
		{
			throw new TfrBusinessException("扫描运单号："+pdaPackagingInfoEntity.getWayBillNumber()+" 不存在！");
		}
		
		//已经生成的主包装信息
		List<PackageMainPriceEntity> mainList=null;
		//是否已经存在
		boolean isExist=false;
		//保存entity
		PackageMainPriceEntity packageMainPriceEntity=new PackageMainPriceEntity();
		//初始化主包装信息
		//set ID
		packageMainPriceEntity.setId(UUIDUtils.getUUID());
		//运单号
		packageMainPriceEntity.setWaybillNo(pdaPackagingInfoEntity.getWayBillNumber());
		//包装材料
		packageMainPriceEntity.setPackageMaterial(pdaPackagingInfoEntity.getPackedMate());
		//开单部门
		packageMainPriceEntity.setBillOrgCode(waybillEntity.getCreateOrgCode());
		//开单部门名称
		String billOrgName=OrgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getCreateOrgCode());
		packageMainPriceEntity.setBillOrgName(billOrgName);
		//外场code
		packageMainPriceEntity.setPackageOrgCode(orgOutField.getCode());
		//外场
		packageMainPriceEntity.setPackageOrgName(orgOutField.getName());
		//审核状态
		packageMainPriceEntity.setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT);
		
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(pdaPackagingInfoEntity.getOperatorCode());
		if (employee == null) {
			LOGGER.error("请校验创建人工号是否存在");
			throw new PackagingException("请校验创建人工号是否存在");
		}
		String userName = employee.getEmpName();
		//创建人code
		packageMainPriceEntity.setCreateUserCode(pdaPackagingInfoEntity.getOperatorCode());
		//创建人
		packageMainPriceEntity.setCreateUserName(userName);
		//创建日期
		packageMainPriceEntity.setCreateTime(new Date());
		//修改人code
		packageMainPriceEntity.setModifyUserCode(pdaPackagingInfoEntity.getOperatorCode());
		//修改人
		packageMainPriceEntity.setModifyUserName(userName);
		//修改日期
		packageMainPriceEntity.setModifyTime(new Date());
		//创建部门code
		packageMainPriceEntity.setCreateOrgCode(orgOutField.getCode());
		//创建部门
		packageMainPriceEntity.setCreateOrgName(orgOutField.getName());
		//接送货理论打木架信息
		WoodenRequirementsEntity woodenRequirePendingEntity=woodenRequirementsService.queryWoodenRequirements(packageMainPriceEntity.getWaybillNo());
		
		if(woodenRequirePendingEntity==null)
		{
			LOGGER.error("运单：{"+pdaPackagingInfoEntity.getWayBillNumber()+"} 不存在包装需求");
			throw new PackagingException("运单：{"+pdaPackagingInfoEntity.getWayBillNumber()+"} 不存在包装需求");
		}
		if(woodenRequirePendingEntity.getStandGoodsVolume()==null)
		{
			woodenRequirePendingEntity.setStandGoodsVolume(BigDecimal.ZERO);
		}
		if(woodenRequirePendingEntity.getBoxGoodsVolume()==null)
		{
			woodenRequirePendingEntity.setBoxGoodsVolume(BigDecimal.ZERO);
		}
		if(woodenRequirePendingEntity.getSalverGoodsNum()==null)
		{
			woodenRequirePendingEntity.setSalverGoodsNum(0);
		}
		
		
		//理论打木架体积
		packageMainPriceEntity.setTheoryFrameVolume(woodenRequirePendingEntity.getStandGoodsVolume());
		//理论打木箱体积
		packageMainPriceEntity.setTheoryWoodenVolume(woodenRequirePendingEntity.getBoxGoodsVolume());

	    //理论打木托
		packageMainPriceEntity.setPackageMaskNumber(new BigDecimal(woodenRequirePendingEntity.getSalverGoodsNum()));
		
		//初始化打木箱、打木架体积
	    if(pdaPackagingInfoEntity.getPackedMate().equals(TransferPDADictConstants.PACKAGING_MATE_WOODEN_FRAME))
		{
	    	packageMainPriceEntity.setActualFrameVolume(pdaPackagingInfoEntity.getPackedVolume());
		}else{
			packageMainPriceEntity.setActualWoodenVolume(pdaPackagingInfoEntity.getPackedVolume());
		}
		
	    //处理体积为空的情况
  		if(packageMainPriceEntity.getActualFrameVolume()==null)
  		{
  			packageMainPriceEntity.setActualFrameVolume(BigDecimal.ZERO);
  		}
  		if(packageMainPriceEntity.getActualWoodenVolume()==null)
  		{
  			packageMainPriceEntity.setActualWoodenVolume(BigDecimal.ZERO);
  		}
	    
		PackagingSupplierEntity resultEntity=new PackagingSupplierEntity();
		if(StringUtil.isNotEmpty(pdaPackagingInfoEntity.getPackageSupplierCode()))
		{
			//调综合接口获取包装供应商信息 todo
			PackagingSupplierEntity queryEntity=new PackagingSupplierEntity();

			queryEntity.setPackagingSupplierCode(pdaPackagingInfoEntity.getPackageSupplierCode());
			queryEntity.setOrgCode(pdaPackagingInfoEntity.getOrgCode());
			try {
				resultEntity= packagingSupplierService.queryPackagingSupplierByEntity(queryEntity.getOrgCode(),queryEntity.getPackagingSupplierCode());
				if(resultEntity==null)
				{
					LOGGER.error("包装供应商不存在");
					throw new TfrBusinessException("包装供应商不存在");
				}
			} catch (BusinessException e) {
				LOGGER.error("获取包装供应商时，调综合接口异常：{"+e.getErrorCode()+"}");
				throw new TfrBusinessException("获取包装供应商时，调综合接口异常：{"+e.getErrorCode()+"}");
			}
		}
		
		//包装供应商
		packageMainPriceEntity.setPackageSupplierCode(pdaPackagingInfoEntity.getPackageSupplierCode());
		
		packageMainPriceEntity.setPackageSupplierName(resultEntity.getPackagingSupplier());
		
		
	    //由于PDA不能扫描打木托个数，设置为0
	    packageMainPriceEntity.setActualMaskNumber(BigDecimal.ZERO);
	    packageMainPriceEntity.setPackageMaskNumber(BigDecimal.ZERO);
	    
		//判断之前是否包装过，如果包装过则需修改之前的记录
		if(StringUtil.isNotEmpty(pdaPackagingInfoEntity.getWayBillNumber())
		      &&StringUtil.isNotEmpty(pdaPackagingInfoEntity.getPackedMate())
				   &&StringUtil.isNotEmpty(pdaPackagingInfoEntity.getOrgCode()))
		{
			 mainList=queryMainPackagePriceByWaybillNoAndSupplierCode(pdaPackagingInfoEntity.getWayBillNumber(),
					 pdaPackagingInfoEntity.getOrgCode(),pdaPackagingInfoEntity.getPackageSupplierCode());
		}
		
	   //主要包装是否审核
		boolean isAudited=false;
		
		
		
		//如果已经存在主包装信息且上一次的包装金额未审核,更新之前的信息
		if(mainList!=null&&mainList.size()>0)
		{
			//调结算接口判断是否已经审核
			if(mainList.get(0).getAuditStatus().equals(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED))
			{
				isAudited=true;
			}else{
				isExist=true;
				PackageMainPriceEntity existMainEntity=mainList.get(0);
				//修改已经存在的主要包装信息
				packageMainPriceEntity=modifyPackedMainPrice(packageMainPriceEntity,existMainEntity);
			}
			
			
		}
		//计算打木架、打木箱体积
		calculateFrameAndBoxVolume(packageMainPriceEntity);
		//计算应付金额
		calculatePackagePayableMoney(packageMainPriceEntity,resultEntity);
		//处理保留两位小数
		handlePoint(packageMainPriceEntity);
		
		//如果已经审核记录包装金额异常表结束
		if(isAudited)
		{
			addPackagePriceException(packageMainPriceEntity);
			return;
		}
		
		PackageAssistPriceEntity packageAssistPriceEntity=new PackageAssistPriceEntity();
		//判断该运单是否已经在辅助包装表中录入，如果已经录入则修改辅助包装材料 
		packageAssistPriceEntity.setWaybillNo(pdaPackagingInfoEntity.getWayBillNumber());
		packageAssistPriceEntity.setPackageOrgCode(pdaPackagingInfoEntity.getOrgCode());
		packageAssistPriceEntity.setPackageSupplierCode(pdaPackagingInfoEntity.getPackageSupplierCode());
		List<PackageAssistPriceEntity> packageAssistPriceList=
				packageAssistPriceService.queryAssistPackagePriceByWaybillNoAndSupplierCode(packageAssistPriceEntity);
		if(packageAssistPriceList!=null&&packageAssistPriceList.size()>0)
		{
			//判断是否审核,如果已经审核本条记录进入包装异常表
			if(packageAssistPriceList.get(0).getAuditStatus().equals(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED))
			{
				addPackagePriceException(packageMainPriceEntity);
				return;
			}else{
			
			//作废辅助包装中的打木架或打木箱体积
			doPackageMainPriceToAssist(packageMainPriceEntity,packageAssistPriceEntity);
			//employee
			packageAssistPriceEntity.setModifyTime(new Date());
			packageAssistPriceEntity.setId(packageAssistPriceList.get(0).getId());
			packageAssistPriceEntity.setModifyUserCode(employee.getEmpCode());
			packageAssistPriceEntity.setModifyUserName(employee.getEmpName());
			packageAssistPriceEntity.setPackageOrgCode(pdaPackagingInfoEntity.getOrgCode());
			packageAssistPriceService.updatePackageAssistPriceByMain(packageAssistPriceEntity);
		  }
		}
		
		//判断是否再次扫描，如果再次扫描修改之前的记录，则新增
		try {
			if(isExist)
			{
				//如果当前包装材料为打木架
				/*if(pdaPackagingInfoEntity.getPackedMate().equals(TransferPDADictConstants.PACKAGING_MATE_WOODEN_FRAME))
				{
					//将打木箱相关体积设置为空
					packageMainPriceEntity.setActualWoodenVolume(null);
					packageMainPriceEntity.setPackageWoodenVolume(null);
				}else{
					
					//将打木架相关体积设置为空
					packageMainPriceEntity.setActualFrameVolume(null);
					packageMainPriceEntity.setPackageFrameVolume(null);
				}*/
				
				//如果是修改，假如打木托信息
				updatePackageMainPrice(packageMainPriceEntity);
				
				
			}else{
				packageMainPriceDao.addPackageMainPrice(packageMainPriceEntity);
				
				//调结算接口 新增应付单
				List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
				PackingRecAndPayTfrDto packingRecAndPayTfrDto =this.typeConversion(packageMainPriceEntity);
				packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);
				//封装currentInfo
				UserEntity user = new UserEntity();
				user.setEmployee(employee);
				CurrentInfo currentInfo = new CurrentInfo(user, orgOutField);
				
				
				////cubcgray 335284-316759
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType("W");
				parDto.setSourceBillNos(new String[] { packingRecAndPayTfrDto.getWaybillNo() });
				//调用灰度
				VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
				boolean flag = true;//stl, not cubc
				if (!CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponseDto.getVestBatchResult().get(0).getVestSystemCode())) {
					flag = false;
				}
				LOGGER.info("PC端辅助包装金额管理cubc flag=" + flag);
				if (flag) {
					//STL CUBC调用标识
					try {
						packingRecAndPayForTfrService.add(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING, currentInfo);
					} catch (SettlementException e) {
						LOGGER.error("调结算接口异常，信息为：{"+e.getErrorCode()+"}");
						throw new TfrBusinessException("调结算接口异常，信息为：{"+e.getErrorCode()+"}");
					}
				} else {
					/**
					 * 同步信息至CUBC
					 * @author 316759-RuipengWang-foss
					 * @date 2016-11-09 16:51:21
					 */
					if (packageMainPriceEntity != null) {
						List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
						PackingToCubcEntity cubcEntity = this.typeConversion(packageMainPriceEntity, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING, currentInfo);
						packingToCubcEntitys.add(cubcEntity);
					
						try {
							LOGGER.info("推送给CUBC的参数 packingToCubcEntitys = " + packingToCubcEntitys);
							packagePriceToCubcService.pushAddPackingRecAndPay(packingToCubcEntitys);
						} catch (Exception e) {
							LOGGER.error("同步至CUBC接口异常，信息为：{" + e + "}");
							throw new TfrBusinessException("同步至CUBC接口异常，信息为：{" + e + "}");
						}
					}
				}
				//end cubcgray 335284-316759
			}
		} catch (TfrBusinessException e) {
			LOGGER.error("系统异常，信息为：{"+e.getErrorCode()+"}");
			throw new TfrBusinessException("系统异常，信息为：{"+e.getErrorCode()+"}");
		}
		LOGGER.info("添加主要包装信息结束");
	}
	
	
	/**
	 * 修改已经存在的主要包装信息
	 * */
	private PackageMainPriceEntity modifyPackedMainPrice(PackageMainPriceEntity newPackageMainPriceEntity,
			PackageMainPriceEntity oldPackageMainPriceEntity)
	{
		newPackageMainPriceEntity.setId(oldPackageMainPriceEntity.getId());
		/**
		 * 打木架
		 * */
	       //实际体积
	      BigDecimal actFVoume=(newPackageMainPriceEntity.getActualFrameVolume()).add(
	    		  oldPackageMainPriceEntity.getActualFrameVolume());
	       //最后体积
	    /*  BigDecimal VoumeF=(newPackageMainPriceEntity.getPackageFrameVolume()).add(
	    		  oldPackageMainPriceEntity.getPackageFrameVolume());*/
	      
	      newPackageMainPriceEntity.setActualFrameVolume(actFVoume);
	    //  oldPackageMainPriceEntity.setPackageFrameVolume(VoumeF);
		
		/**
		 * 打木箱
		 * */
	       //实际体积
	      BigDecimal actBVoume=(newPackageMainPriceEntity.getActualWoodenVolume()).add(
	    		  oldPackageMainPriceEntity.getActualWoodenVolume());
	       //最后体积
	    /*  BigDecimal VoumeB=(newPackageMainPriceEntity.getPackageWoodenVolume()).add(
	    		  oldPackageMainPriceEntity.getTheoryWoodenVolume());*/
	      
	      newPackageMainPriceEntity.setActualWoodenVolume(actBVoume);
	      //oldPackageMainPriceEntity.setPackageWoodenVolume(VoumeB);
	     /**
	      * 包装材料
	      * */
	     //修改包装材料,判断当前包装材料是否已经存，如果存在不修改，否则拼接
	      if(!oldPackageMainPriceEntity.getPackageMaterial().contains(
	    		  newPackageMainPriceEntity.getPackageMaterial()))
	      {
	    	  String meterial=oldPackageMainPriceEntity.getPackageMaterial()+","+newPackageMainPriceEntity.getPackageMaterial();
	    	  newPackageMainPriceEntity.setPackageMaterial(meterial);
	      }
	      
	      
	      return newPackageMainPriceEntity;
	      
	}
	
	/**
	 * 
	 * 计算应付金额：
	 * 
	 * 1.	应付金额=打木架体积*打木架单价+打木箱体积*打木箱单价；
     * 2.	当打木架体积小于或等于起步体积时：应付金额=打木架最低一票费用 +打木箱体积*打木箱单价；
     * 3.	当打木箱体积小于或等于起步体积时：应付金额=打木箱最低一票费用 +打木架体积*打木架单价；
     * 4.	当打木架体积小于或等于起步体积时且当打木箱体积小于或等于起步体积时： 应付金额=打木架最低一票费用 +打木箱最低一票费用；
     * 
	 * */
	private void calculatePackagePayableMoney(PackageMainPriceEntity packageMainPriceEntity,
			PackagingSupplierEntity packagingSupplierEntity)
	{
		
		//应付金额
		BigDecimal packagePayableMoney=BigDecimal.ZERO;
		//打木架起步体积
		BigDecimal frameStartVolume=null;
		//打木架最低一票费用 
		BigDecimal frameMinPrice=null;
		//打木架单价(方)
		BigDecimal framePirce=null;
		//打木箱起步体积
		BigDecimal boxStartVolume=null;
		//打木箱最低一票费用
		BigDecimal boxMinPrice=null;
		//打木箱单价(方)
		BigDecimal boxPirce=null;
		/**
		 * 当打木架体积不为0时，基础中需存在 打木架起步体积、打木架最低一票费用 
		 * */
		if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0)
		{
			if(packagingSupplierEntity.getWoodenFrameStartVolume()==null)
			{
				LOGGER.error("该包装供应商不存在打木架起步体积,请维护包装供应商基础资料");
				throw new TfrBusinessException("该包装供应商不存在打木架起步体积,请维护包装供应商基础资料");
			}
			frameStartVolume=new BigDecimal(packagingSupplierEntity.getWoodenFrameStartVolume());
			//打木架最低一票费用
			
			if(packagingSupplierEntity.getWoodenFrameMin()==null)
			{
				LOGGER.error("该包装供应商不存在打木架最低一票费用,请维护包装供应商基础资料");
				throw new TfrBusinessException("该包装供应商不存在打木架最低一票费用,请维护包装供应商基础资料");
			}
			frameMinPrice=new BigDecimal(packagingSupplierEntity.getWoodenFrameMin());
			if(packagingSupplierEntity.getWoodenFrame()==null)
			{
				LOGGER.error("该包装供应商不存在打木架单价,请维护包装供应商基础资料");
				throw new TfrBusinessException("该包装供应商不存在打木架单价,请维护包装供应商基础资料");
			}
			framePirce=new BigDecimal(packagingSupplierEntity.getWoodenFrame());
		}
		
		/**
		 * 当打木箱体积不为0时，包装供应商基础资料需存在打木箱起步价体积、打木箱最低一票费用
		 * **/
		if(packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0)
		{
			if(packagingSupplierEntity.getWoodBoxStartVolume()==null)
			{
				LOGGER.error("该包装供应商不存在打木箱起步体积,请维护包装供应商基础资料");
				throw new TfrBusinessException("该包装供应商不存在打木箱起步体积,请维护包装供应商基础资料");
			}
			boxStartVolume=new BigDecimal(packagingSupplierEntity.getWoodBoxStartVolume());
			
			if(packagingSupplierEntity.getWoodBoxMin()==null)
			{
				LOGGER.error("该包装供应商不存在打木箱最低一票费用,请维护包装供应商基础资料");
				throw new TfrBusinessException("该包装供应商不存在打木架最低一票费用,请维护包装供应商基础资料");
			}
			boxMinPrice=new BigDecimal(packagingSupplierEntity.getWoodBoxMin());
			
			if(packagingSupplierEntity.getWoodBox()==null)
			{
				LOGGER.error("该包装供应商不存在打木箱单价,请维护包装供应商基础资料");
				throw new TfrBusinessException("该包装供应商不存在打木箱单价,请维护包装供应商基础资料");
			}
			boxPirce=new BigDecimal(packagingSupplierEntity.getWoodBox());
		}
		
		//1 当打木架体积小于或等于起步体积且打木箱架体积大于起步价体积：应付金额=打木架最低一票费用 +打木箱体积*打木箱单价；
		
		if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(frameStartVolume)<=0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(boxStartVolume)>0)
		{
			packagePayableMoney=boxPirce.multiply(packageMainPriceEntity.getPackageWoodenVolume()).add(frameMinPrice);
		}
		//2 当打木架体积小于或等于起步体积且打木箱体积为0：应付金额=打木架最低一票费用 
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(frameStartVolume)<=0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)==0)
		{
			packagePayableMoney=frameMinPrice;
		}
		//3 当打木箱体积小于或等于起步体积且打木架体积大于起步价体积：应付金额=打木箱最低一票费用 +打木架体积*打木架单价；
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(frameStartVolume)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(boxStartVolume)<=0)
		{
			packagePayableMoney=packageMainPriceEntity.getPackageFrameVolume().multiply(framePirce).add(boxMinPrice);
		}
		//4当打木箱体积小于或等于起步体积且打木架体积为0：应付金额=打木箱最低一票费用
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)==0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(boxStartVolume)<=0)
		{
			packagePayableMoney=boxMinPrice;
		}
		//5当打木架体积小于或等于起步体积时且当打木箱体积小于或等于起步体积时： 应付金额=打木架最低一票费用 +打木箱最低一票费用；
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(frameStartVolume)<=0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(boxStartVolume)<=0)
		{
			packagePayableMoney=frameMinPrice.add(boxMinPrice);
		}
		//6当打木架体积大于起步体积，打木箱体积大于起步体积：应付金额=打木架体积*打木架单价+打木箱体积*打木箱单价；
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(frameStartVolume)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(boxStartVolume)>0){
			
			packagePayableMoney=boxPirce.multiply(packageMainPriceEntity.getPackageWoodenVolume()).add(
					packageMainPriceEntity.getPackageFrameVolume().multiply(framePirce));
		}
		//7当打木架体积大于起步体积，打木箱体积=0：应付金额=打木架体积*打木架单价；
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(frameStartVolume)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)==0){
			
			packagePayableMoney=packageMainPriceEntity.getPackageFrameVolume().multiply(framePirce);
		}
		//8 打木箱体积大于起步体积，打木架体积=0：应付金额=打木箱体积*打木箱单价；
		else if(packageMainPriceEntity.getPackageFrameVolume()!=null
				&&packageMainPriceEntity.getPackageWoodenVolume()!=null
				&&packageMainPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)==0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageMainPriceEntity.getPackageWoodenVolume().compareTo(boxStartVolume)>0){
			
			packagePayableMoney=packageMainPriceEntity.getPackageWoodenVolume().multiply(boxPirce);
		}
		
		//保留两位小数
    	//DecimalFormat df = new DecimalFormat("#.00");
		
		packageMainPriceEntity.setPackagePayableMoney(packagePayableMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		
	}
	
	/**
	 * @desc 一个运单分批扫描，如果是同一部门、同一包装材料默认为 修改已经录入的主包装信息
	 * @author foss-105795-wqh
	 * @date   2014-05-07
	 * */
	@Transactional
	public void updatePackageMainPrice(PackageMainPriceEntity packageMainPriceEntity)
	{
		if(packageMainPriceEntity==null)
		{
			throw new TfrBusinessException("信息不存在");
		}
		//查询出修改之前的信息
		List<PackageMainPriceEntity> oldEntityList=queryMainPackagePriceByWaybillNoAndSupplierCode(
				packageMainPriceEntity.getWaybillNo(),packageMainPriceEntity.getPackageOrgCode(),
				packageMainPriceEntity.getPackageSupplierCode());
		PackageMainPriceEntity oldEntity=oldEntityList.get(0);
		//保存新修改的信息
		packageMainPriceDao.updatePackageMainPrice(packageMainPriceEntity);
		//调结算接口修改相关财务单据
		List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
		PackingRecAndPayTfrDto packingRecAndPayTfrDto =this.typeConversion(packageMainPriceEntity);
		packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);
		UserEntity user = new UserEntity();
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(packageMainPriceEntity.getCreateUserCode());
		user.setEmployee(employee);
		CurrentInfo currentInfo = new CurrentInfo(user, getOutFieldCode(packageMainPriceEntity.getPackageOrgCode()));
		//CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//封装灰度实体，类型是运单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
		parDto.setSourceBillNos(new String[] {packingRecAndPayTfrDtoList.get(0).getWaybillNo()});
		//调用灰度
		VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
		if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
			try {
				//JSYH-135 测试代打打木架变更需求时，在进行修改操作时，修改项不引起应付金额变化的时候，应付单也进行了红冲，此功能需要优化；
				//如果包装金额不发生变化，不调结算接口
				BigDecimal result=oldEntity.getPackagePayableMoney().subtract(packageMainPriceEntity.getPackagePayableMoney());
				if(result.compareTo(BigDecimal.ZERO)!=0)
				{
					packingRecAndPayForTfrService.update(packingRecAndPayTfrDtoList, 
							SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING, currentInfo);
					
				}
				
			} catch (SettlementException e) {
				LOGGER.error("调结算接口异常，信息为：{"+e.getErrorCode()+"}");
				throw new TfrBusinessException("调结算接口异常，信息为：{"+e.getErrorCode()+"}");
			}
		} else {
			/**
			 * 修改打木架信息同步至CUBC
			 * @author 316759-RuipengWang-foss
			 * @date 2016-11-10 10:51:57 AM
			 * TODO
			 */
			if (packageMainPriceEntity != null) {
				List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
				PackingToCubcEntity cubcEntity = this.typeConversion(packageMainPriceEntity, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING, currentInfo);
				packingToCubcEntitys.add(cubcEntity);
				String requestStr = JSONObject.toJSONString(packingToCubcEntitys);
				try {
					LOGGER.info("推送给CUBC的参数 requestStr = " + requestStr);
					ResponseToCubcCallBack responseDto = fossToCubcService.pushUpdatePacking(requestStr);

					if (null == responseDto) {
						throw new TfrBusinessException("同步信息至CUBC异常：返回参数为null");
					} else if (StringUtils.equals(responseDto.getResult(), AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
						throw new TfrBusinessException("同步信息至CUBC异常：" + responseDto.getReason());
					}
				} catch (Exception e) {
					LOGGER.error("同步至CUBC接口异常", e);
					throw new TfrBusinessException("同步至CUBC接口异常，信息为：{" + e + "}", e.getMessage());
				}
			}
		}
	}
	/**
	 * 
	     *
		 * @desc  PackageMainPriceEntity类型转换为PackingRecAndPayTfrDto类型
		 * @author 042795
		 * @date 2014-6-26 下午3:28:40
		 * @see typeConversion
	 */
	private PackingRecAndPayTfrDto typeConversion(PackageMainPriceEntity packageMainPriceEntity){
		PackingRecAndPayTfrDto packingRecAndPayTfrDto = null;
		if(packageMainPriceEntity != null){
			packingRecAndPayTfrDto = new PackingRecAndPayTfrDto();
			packingRecAndPayTfrDto.setBillOrgCode(packageMainPriceEntity.getPackageOrgCode());
			packingRecAndPayTfrDto.setBillOrgName(packageMainPriceEntity.getPackageOrgName());
			packingRecAndPayTfrDto.setPackageOrgCode(packageMainPriceEntity.getPackageSupplierCode());
			packingRecAndPayTfrDto.setPackageOrgName(packageMainPriceEntity.getPackageSupplierName());
			packingRecAndPayTfrDto.setPackagePayableMoney(packageMainPriceEntity.getPackagePayableMoney());
			packingRecAndPayTfrDto.setWaybillNo(packageMainPriceEntity.getWaybillNo());
		}
		return packingRecAndPayTfrDto;
	}
	/**
	 * @desc 根据运单号、包装供应商、包装部门查询已经生成
	 * 
	 * 的主包装金额信息
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	public List<PackageMainPriceEntity> queryMainPackagePriceByWaybillNoAndSupplierCode(String waybillNo,
			String packageOrgCode,String supplierCode)
	{
		return packageMainPriceDao.queryMainPackagePriceByWaybillNoAndSupplierCode(waybillNo, packageOrgCode, supplierCode);
	}
	
	
	/**
	 *作废对应给辅助包装中的打木架、打木箱体积
	 * **/
	
	private void doPackageMainPriceToAssist(PackageMainPriceEntity mainPackage,PackageAssistPriceEntity assistPackage)
	{
		//打木架
		if(mainPackage.getPackageMaterial().equals(TransferPDADictConstants.PACKAGING_MATE_WOODEN_FRAME))
		{
			//实际体积
			assistPackage.setActualFrameVolume(BigDecimal.ZERO);
			//打木架体积
			assistPackage.setPackageFrameVolume(BigDecimal.ZERO);
			
			assistPackage.setActualWoodenVolume(null);
			//打木箱体积
			assistPackage.setPackageWoodenVolume(null);
		}else{
			//打木箱体积
			//实际体积
			assistPackage.setActualWoodenVolume(BigDecimal.ZERO);
			//打木箱体积
			assistPackage.setPackageWoodenVolume(BigDecimal.ZERO);
			//实际体积
			assistPackage.setActualFrameVolume(null);
			//打木架体积
			assistPackage.setPackageFrameVolume(null);
			
		}
		
	}
	
	
	/*
	 * @desc 获取当前外场部门的code
	 * @author foss-105795-wqh
	 * @param 
	 * @ date 2014-04-11
	 * 
	 * */
	private OrgAdministrativeInfoEntity getOutFieldCode(String orgCode){
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//组织对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(orgCode,
							bizTypes);
	   if(org==null)
	   {
		   throw new TfrBusinessException("当前部门不存在");
		   
	   }
	   return org;
     }
    
    
    /**
	 * 
	 * 处理保留小数位数
	 * 
	 * **/
	private void handlePoint(PackageMainPriceEntity packageMainPriceEntity)
    {
		//四舍五入处理
    	//DecimalFormat df = new DecimalFormat("#.00");
		//实际打木架体积
		if(packageMainPriceEntity.getActualFrameVolume()!=null)
		{
			packageMainPriceEntity.setActualFrameVolume(packageMainPriceEntity.getActualFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

			/*packageMainPriceEntity.setActualFrameVolume(new BigDecimal(
					df.format(packageMainPriceEntity.getActualFrameVolume().doubleValue())));*/
		}
		//实际打木箱体积
		if(packageMainPriceEntity.getActualWoodenVolume()!=null)
		{
			packageMainPriceEntity.setPackageFrameVolume(packageMainPriceEntity.getPackageFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

			/*packageMainPriceEntity.setActualWoodenVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getActualWoodenVolume().doubleValue())));*/
		}
		//理论打木架体积
		if(packageMainPriceEntity.getTheoryFrameVolume()!=null)
		{
			packageMainPriceEntity.setTheoryFrameVolume(packageMainPriceEntity.getTheoryFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

			/*packageMainPriceEntity.setTheoryFrameVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getTheoryFrameVolume().doubleValue())));*/
		}
		//理论打木箱体积
		if(packageMainPriceEntity.getTheoryWoodenVolume()!=null)
		{
			packageMainPriceEntity.setTheoryWoodenVolume(packageMainPriceEntity.getTheoryWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

			/*packageMainPriceEntity.setTheoryWoodenVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getTheoryWoodenVolume().doubleValue())));*/
		}
		//打木架体积
		if(packageMainPriceEntity.getPackageFrameVolume()!=null)
		{
			packageMainPriceEntity.setPackageFrameVolume(packageMainPriceEntity.getPackageFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

			/*packageMainPriceEntity.setPackageFrameVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getPackageFrameVolume().doubleValue())));*/
		}
		//打木箱体积
		if(packageMainPriceEntity.getPackageWoodenVolume()!=null)
		{
			packageMainPriceEntity.setPackageWoodenVolume(packageMainPriceEntity.getPackageWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

			/*packageMainPriceEntity.setPackageWoodenVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getPackageWoodenVolume().doubleValue())));*/
		}
    	
    }
    
	/**
	 * 计算打木架、打木箱体积体积
	 * ***/
	
	private void calculateFrameAndBoxVolume(PackageMainPriceEntity packageMainPriceEntity)
	{
		//关系常量
		BigDecimal relationConstant=new BigDecimal(PackagingConstants.PACKAGE_W_F_THEORY_ACTUAL_RELATION_CONSTANT);
		//比较常量
		BigDecimal compareConstant=new BigDecimal(PackagingConstants.PACKAGE_W_F_COMPARE_CONSTANT);
		
	
		/*
		 * 计算打木架体积:
		 * 1.	当（实际打木架―理论打木架）∕理论打木架大于1%时，打木架体积=（理论打木架体积*1.01）； 
		 * 2.	当（实际打木架―理论打木架）∕理论打木架小于等于1%时，打木架体积=理论打木架体积；
		 * */
	    if(packageMainPriceEntity.getActualFrameVolume()!=null
	    		&&packageMainPriceEntity.getActualFrameVolume().compareTo(BigDecimal.ZERO)>0
	    		&&packageMainPriceEntity.getTheoryFrameVolume().compareTo(BigDecimal.ZERO)>0)
	    {
	    	//理论体积
		      BigDecimal throeyFVoume=packageMainPriceEntity.getTheoryFrameVolume();
		       //实际体积
		      BigDecimal actFVoume=packageMainPriceEntity.getActualFrameVolume();
		       //最后体积
		      BigDecimal voume=null;
		    
		      if(throeyFVoume.compareTo(BigDecimal.ZERO)==0)
		      {
		    	  throw new TfrBusinessException("该运单不存在打木架的的需求");
		      }  
		      BigDecimal resultF=(actFVoume.subtract(throeyFVoume)).divide(throeyFVoume,2,BigDecimal.ROUND_HALF_UP);
		     
		     if(resultF.compareTo(compareConstant)>0){
		    	 voume=throeyFVoume.multiply(relationConstant);
		    	 
		     }else{
		    	 voume=throeyFVoume;
		    	 
		     }
		     
		     //实际打木架体积
		   //  packageMainPriceEntity.setActualFrameVolume(actFVoume);
		     //打木架体积
		     packageMainPriceEntity.setPackageFrameVolume(voume);
		      
		     
	    }else{
	    	 packageMainPriceEntity.setPackageFrameVolume(packageMainPriceEntity.getActualFrameVolume());
	    }
	  /**
	   * 打木箱体积计算规则：
	   * 1.当（实际打木箱―理论打木箱）∕理论打木箱大于1%时，打木箱体积=（理论打木箱体积*1.01）； 
	   * 2.当（实际打木箱―理论打木箱）∕理论打木箱小于等于1%时，打木箱体积=理论打木箱体积； 
	   * */
		
	    if(packageMainPriceEntity.getActualWoodenVolume()!=null
	    		&&packageMainPriceEntity.getActualWoodenVolume().compareTo(BigDecimal.ZERO)>0
	    		&&packageMainPriceEntity.getTheoryWoodenVolume().compareTo(BigDecimal.ZERO)>0)
	    {
	    	   //理论体积
		      BigDecimal throeyBVoume=packageMainPriceEntity.getTheoryWoodenVolume();
		       //实际体积
		      BigDecimal actBVoume=packageMainPriceEntity.getActualWoodenVolume();
		       //最后体积
		      BigDecimal voume=null;
		    
		      if(throeyBVoume.compareTo(BigDecimal.ZERO)==0)
		      {
		    	  throw new TfrBusinessException("该运单不存在打木箱的的需求");
		      }
		      BigDecimal resultB=(actBVoume.subtract(throeyBVoume)).divide(throeyBVoume,2,BigDecimal.ROUND_HALF_UP);
		     
		     if(resultB.compareTo(compareConstant)>0){
		    	 voume=throeyBVoume.multiply(relationConstant);
		    	 
		     }else{
		    	 voume=throeyBVoume;
		    	 
		     }
		     //理论打木箱体积
		     //packageMainPriceEntity.setTheoryWoodenVolume(throeyBVoume);
		     //实际打木箱体积
		     // packageMainPriceEntity.setActualWoodenVolume(actBVoume);
		     //打木箱体积
		     packageMainPriceEntity.setPackageWoodenVolume(voume);
		     
	    }else{
	    	packageMainPriceEntity.setPackageWoodenVolume(packageMainPriceEntity.getActualWoodenVolume());
	    }
	    
	   /* if(packageMainPriceEntity.getPackageFrameVolume()==null
	    		||packageMainPriceEntity.getActualFrameVolume().compareTo(BigDecimal.ZERO)==0)
	    {
	    	packageMainPriceEntity.setPackageFrameVolume(BigDecimal.ZERO);
	    }

	    if(packageMainPriceEntity.getActualWoodenVolume()==null
	    		||packageMainPriceEntity.getActualWoodenVolume().compareTo(BigDecimal.ZERO)==0)
	    {
	    	packageMainPriceEntity.setPackageWoodenVolume(BigDecimal.ZERO);
	    }
	    */
		
		
	}
	
	
    
    /**
	 * @desc 添加包装异常信息
	 * @author foss-105795-wqh
	 * @date   2014-05-28
	 * */
	public void addPackagePriceException(PackageMainPriceEntity packageMainPriceEntity){
		
		packageMainPriceDao.addPackagePriceException(packageMainPriceEntity);
	}

	/**
	 * @desc 根据运单号、包装材料、包装部门查询已经生成的主包装金额信息
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	public List<PackageMainPriceEntity> queryMainPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode)
	{
		return packageMainPriceDao.queryMainPackagePriceListByWaybillNo(waybillNoList, packageOrgCode);
	}
	

	/**
	 * @desc 根据运单号查询有哪些转运场有该运单的主要包装信息
	 * @author foss-205109-zenghaibin
	 * @date   2014-12-09 14:35:35
	 * @param String wayBillNo
	 * @param List<PackageMainPriceEntity>
	 * */
	private  List<PackageMainPriceEntity> queryPriceListByWaybillNo(String wayBillNo){
		
		return  packageMainPriceDao.queryPriceListByWaybillNo(wayBillNo);
		
	}
	
	/**
	 * @desc 修改主要包装信息
	 * @author foss-205109-zenghaibin
	 * @date   2014-12-09 14:35:35
	 * @param String wayBillNo
	 * */
	public void updateMainPriceByWayBillNo(String wayBillNo){
		List<PackageMainPriceEntity> packageMainPriceList=this.queryPriceListByWaybillNo(wayBillNo);
		CurrentInfo user=FossUserContext.getCurrentInfo();
		if(CollectionUtils.isNotEmpty(packageMainPriceList)){
			for(PackageMainPriceEntity entity:packageMainPriceList){
				
				if(StringUtil.equals(entity.getAuditStatus(), PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED)){
					throw new TfrBusinessException("该运单在部门"+entity.getPackageOrgCode()+"的主要包装信息已经审核通过，不允许修改");
				}
				PDAPackagingInfoEntity pdaPackagingInfoEntity=new PDAPackagingInfoEntity();
				//设置运单号
				pdaPackagingInfoEntity.setWayBillNumber(entity.getWaybillNo());
				//设置包装材料
				pdaPackagingInfoEntity.setPackedMate(entity.getPackageMaterial());
				//设置包装体积
				pdaPackagingInfoEntity.setPackedVolume(new BigDecimal("0"));
				//设置加托个数
				pdaPackagingInfoEntity.setPlusNum(entity.getPackageMaskNumber().intValue());
				//包装部门
				pdaPackagingInfoEntity.setOrgCode(entity.getPackageOrgCode());
				//操作人编码
				pdaPackagingInfoEntity.setOperatorCode(user.getEmpCode());
				//包装供应商编码
				pdaPackagingInfoEntity.setPackageSupplierCode(entity.getPackageSupplierCode());
				
				this.addPackageMainPrice(pdaPackagingInfoEntity);
				
			}
		}
		
	}
	
	public void setPackageMainPriceDao(IPackageMainPriceDao packageMainPriceDao) {
		this.packageMainPriceDao = packageMainPriceDao;
	}

	public void setPackagingSupplierService(
			IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setPackageAssistPriceService(
			IPackageAssistPriceService packageAssistPriceService) {
		this.packageAssistPriceService = packageAssistPriceService;
	}

	public void setWoodenRequirementsService(
			IWoodenRequirementsService woodenRequirementsService) {
		this.woodenRequirementsService = woodenRequirementsService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		OrgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPackingRecAndPayForTfrService(
			IPackingRecAndPayForTfrService packingRecAndPayForTfrService) {
		this.packingRecAndPayForTfrService = packingRecAndPayForTfrService;
	}

	public void setPackagePriceToCubcService(
			IPackagePriceToCubcService packagePriceToCubcService) {
		this.packagePriceToCubcService = packagePriceToCubcService;
	}

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}

}
