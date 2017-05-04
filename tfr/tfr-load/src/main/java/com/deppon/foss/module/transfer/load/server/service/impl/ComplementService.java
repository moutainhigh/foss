/**
 * 1、	系统根据登
 * 录者的角色部门
 * 所管辖的补码区
 * 域展示到
 * 达该区域的快件
 * 信息。（即
 * 不选择目的站时，
 * 查询出当前登
 * 录人所属快递大区
 * 下所有的虚拟营
 * 业部待补码运单）
根据开单的【运输性
质】，仅对快
递类型的货物进行补
码操作。
1、	【查询条件】
模块中的【运
单号】输入只能输入
阿拉伯数字；
2、	【运单号】的默认
值为空。
1、	【查询条件】
模块
中的【出发城市】
需要包
括全国所有的发货
城市
2、	【出发城市】
的默
认值为空。
1、	【查询条件】
模块
中的【开单时间】包
括日期和时间
（精确到秒）两
个部分；
2、	开单时间
段的
选择最大不超过7天；
3、	时间只能选择
，不能输入；
4、	默认时间段
为：登陆
日期的前一天00:00
到当天结束时间。
1、	支持多选择项
查询，使用运单号查询
为排他条件。
2、	已补码的快件
及自提件均默认不
显示出来，按运
单号查询时可显示
1、	如果没有输
入任何条件，点击【
查询】，补码
列表显示截
止系统当前
时间, 本登陆
人角色可查
看的，所有未
补码的快件信息。
1、	默认状态下
，补码列表为空；
2、	输入查询条件
，点击【查询】后显
示补码列表查询结果；
1、补码后目的站栏位
显示的内容：
①默认仅显示选择
条目的目的站对应
城市所辖试点网点
或落地配代理名称；
②可通过该公共选
择器选择其他大区
下所有做包裹送货
的营业部或落地配
代理网点
2、补码完成以后，
该虚拟营业部的名
称变为实际的目的
营业部或外发落地
配代理网点；
3、只有目的站一致
的条目可以同时勾
选进行批量补码
4、补码完成以后
，生成新的走货路径。
5、记录当次补码的
变动。包括运单号
、补码前目的站编
码/名称、补码后
目的站编码/名称、
补码时间、操作人、
操作部门。
1、	如果未补码
的快件是同城件，
那么该信息排序优
先级提高；
2、	如果都是同城
件，按照开单时间
置顶，越早开单，
越置顶。
同城件是指运单的
出发城市、目的城
市相同的快件
1、	点击GIS图标
，弹出地图页面，
页面显示目的地地
址在地图的位置，
并根据该地址标记
附近到达网点的位置
。
1、	修改补码可重
复操作，修改补码的
方法和补码操作的方
法一样。
2、	支持并发操作，
当已经完成补码的运
单，再补码时提示“
该运单已补码，是否
确定修改”。
1、	补码功能财务
接口调用：如果运单
存在应收虚拟网点的
到付运费或者代收货
款费用，且最终网点
信息为自有网点，则
需要调用结算接口，
红冲虚拟网点的应收
到付运费或代收货款
费用，生成应收最新
提货网点的到付运费
或代收货款费用。
1、	查询条件中的
【目的站】支持多选
目的站可选范围限于
当前登录人所属快递
大区下属的虚拟营业
部
 */
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillToPartnersService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDestinationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressDto;
import com.deppon.foss.module.settlement.agency.api.server.service.IComplementFunctionService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;
import com.deppon.foss.module.transfer.common.api.shared.define.ExpressConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IComplementOptimizedDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.server.service.IComplementService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AsyncComplementFailedQcDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: ComplementService
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码service类
 * @date: 2013-7-23 下午1:56:52
 * 
 */
public class ComplementService implements IComplementService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ComplementService.class);
	
	/**
	 * 营业部 Service实现
	 */
	private ISaleDepartmentService SaleDepartmentService;
	 
	
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		SaleDepartmentService = saleDepartmentService;
	}

	/**
	 * 记录日志
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 交接单Service
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 补码dao
	 */
	private IComplementDao complementDao;
	/**
	 * 补码优化dao
	 */
	private IComplementOptimizedDao complementOptimizedDao;
	
	public void setComplementOptimizedDao(IComplementOptimizedDao complementOptimizedDao) {
		this.complementOptimizedDao = complementOptimizedDao;
	}

	/**
	 * 小件运单Service
	 */
	private IWaybillExpressService waybillExpressService;
	
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 走货路径Service，用于补码后更新走货路径信息
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 结算补码service，用于更改提货网点后，更改应收单信息
	 */
	private IComplementFunctionService complementFunctionService;
	
	/**
	 * 行政组织Service，用于判断提货网点是否为自有网点
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 用于获取用户所属快递大区
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 用于获取用户所属转运场
	 */
	private ILoadService loadService;
	
	/**
	 * 落地配外发单服务，查询是否已录入外发单
	 */
	private ILdpExternalBillService ldpExternalBillService;
	
	/**
	 * 判断运单是否已结清货款
	 */
	private IRepaymentService repaymentService;
	
	/**
	 * 落地配网点service
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	private IAutoAddCodeByHandService autoAddCodeByHandService;
	
	/**  
	 * autoAddCodeByHandService.  
	 *  
	 * @param   autoAddCodeByHandService    the autoAddCodeByHandService to set  
	 * @since   JDK 1.6  
	 */
	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}

	/**
	 * 自动补码时记录日志使用
	 */
	private static final String FOSS_AUTO = "FOSS_AUTO";

	/**
	 * 200968 2015-07-10
	 * 调用综合接口获取配置参数:快递外发货物保价金额
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 200968 2015-07-11
	 * 调用综合接口获取员工信息:如果是有特安上限,则为特安客户.
	 */
	private ICustomerService customerService;
	public void setWaybillDao(IWaybillDao waybillDao) {
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setComplementFunctionService(
			IComplementFunctionService complementFunctionService) {
		this.complementFunctionService = complementFunctionService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setComplementDao(IComplementDao complementDao) {
		this.complementDao = complementDao;
	}

	/**
	 * 查询补码信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:57:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IComplementService#queryComplementList(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto, int, int)
	 */
	@Override
	public List<ComplementQueryDto> queryComplementList(ComplementQueryDto queryDto, int start, int limit) {
		// 在action里面进行过设置
		// configComplementQueryInfo(queryDto);
 
		// 272681 让目的地址与综合查询的收货地址一致
		 List<ComplementQueryDto> complementQueryDtoList = new ArrayList<ComplementQueryDto>();
		// complementQueryDtoList = complementDao.queryComplementList(queryDto, start, limit);
		 
		////////////// 335284 start//使用优化dao查询列表/////////////////
		if (StringUtils.isEmpty(queryDto.getWaybillNo())) {
			complementQueryDtoList = complementOptimizedDao.queryComplementList(queryDto, start, limit);
		}else{
			complementQueryDtoList = complementOptimizedDao.queryComplementByWaybillno(queryDto);
		}
		////////////// 335284 end///////////////////

		for (ComplementQueryDto complementQuery : complementQueryDtoList) {
			////////////// 335284 start//使用优化dao设置列表元素相关地址信息/////////////////
			attachComplementInfo(complementQuery);
			////////////// 335284 end///////////////////
			
			
		}
		 return complementQueryDtoList;
		
	}
	
	@Override
	public ComplementQueryDto configComplementQueryInfo(ComplementQueryDto queryDto) {
		if(StringUtils.isBlank(queryDto.getWaybillNo())){
			List<String> pkpOrgCodeList = new ArrayList<String>();
			pkpOrgCodeList.add(ExpressConstants.UNREAL_SALES_DEPARTMENT_CODE);
			List<String> arriveDistCodeList = new ArrayList<String>();
			// 获得到达区县
			String arriveDistCode = queryDto.getArriveDistCode();
			if(StringUtils.isNotBlank(arriveDistCode)){
				// 以逗号和空格来分割字符串
				String[] arriveDistCodeArr=arriveDistCode.split(", ");
				arriveDistCodeList=Arrays.asList(arriveDistCodeArr);
			}
			String pkpOrgCode = queryDto.getPkpOrgCode();
			if(StringUtils.isNotBlank(pkpOrgCode)){
				pkpOrgCodeList.add(pkpOrgCode);
				//xiaobc 改start
				String currentTransferCenterCode = this.queryTransferCenterCode();
				queryDto.setCurrentTransferCenterCode(currentTransferCenterCode);//将当前登录用户所在外场存入查询条件中
				//xiaobc 改end
			}else{
				List<String> transferCenterCodeList = new ArrayList<String>();
				//xiaobc 改start
				String currentTransferCenterCode = this.queryTransferCenterCode();
				queryDto.setCurrentTransferCenterCode(currentTransferCenterCode);//将当前登录用户所在外场存入查询条件中
				//xiaobc 改end
				transferCenterCodeList.add(this.queryTransferCenterCode());
				//获取当前转运场下的所有虚拟营业部
				List<OrgAdministrativeInfoEntity> expressOrgList = orgAdministrativeInfoComplexService.queryExpressSalesDepartmentByTransCenterCode(transferCenterCodeList);
				for(OrgAdministrativeInfoEntity expressOrg : expressOrgList){
					pkpOrgCodeList.add(expressOrg.getCode());
				}
			}
			queryDto.setArriveDistCodeList(arriveDistCodeList);
			queryDto.setPkpOrgCodeList(pkpOrgCodeList);
		}
		return queryDto;
	}
	
	
	private void attachComplementInfo(ComplementQueryDto info){
		ComplementQueryDto addressDto = complementOptimizedDao.queryComplementAddressInfo(info);
		info.setDepartCityCode(addressDto.getDepartCityCode());
		info.setDepartCityName(addressDto.getDepartCityName());
		info.setArriveCityCode(addressDto.getArriveCityCode());
		info.setArriveCityName(addressDto.getArriveCityName());
		info.setArriveProName(addressDto.getArriveProName());
		info.setBillingOrgName(addressDto.getBillingOrgName());
		info.setAddress(addressDto.getAddress());
		StringBuilder address = new StringBuilder(info.getAddress());
		if (StringUtils.isNotBlank(info.getReceiveCustomerAddressNote())) {
			address.append("(").append(info.getReceiveCustomerAddressNote()).append(")");
		}
		info.setAddress(address.toString());
	}

	/**
	 * 查询退回补码信息
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:57:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IComplementService#queryComplementBackList(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto, int, int)
	 */
	@Override
	public List<ComplementQueryDto> queryComplementBackList(ComplementQueryDto queryDto, int start, int limit) {
		// configComplementBackQueryInfo(queryDto);已在action中配置
		 
		// 272681 让目的地址与综合查询的收货地址一致
		 List<ComplementQueryDto> complementQueryDtoList = new ArrayList<ComplementQueryDto>();
//		 complementQueryDtoList = complementDao.queryComplementBackList(queryDto, start, limit);
		 
		////////////// 335284 start//使用优化dao查询列表/////////////////
		if (StringUtils.isEmpty(queryDto.getWaybillNo())) {
			complementQueryDtoList = complementOptimizedDao.queryComplementBackList(queryDto, start, limit);
		} else {
			complementQueryDtoList = complementOptimizedDao.queryComplementBackByWaybillno(queryDto);
		}
		////////////// 335284 end///////////////////
		  
		for (ComplementQueryDto complementQuery : complementQueryDtoList) {
			////////////// 335284 start//使用优化dao设置列表元素相关地址信息/////////////////
			attachComplementInfo(complementQuery);
			////////////// 335284 end///////////////////
		}
		return complementQueryDtoList;
		
	}
	
	@Override
	public ComplementQueryDto configComplementBackQueryInfo(ComplementQueryDto queryDto) {
		if(StringUtils.isBlank(queryDto.getWaybillNo())){
			List<String> pkpOrgCodeList = new ArrayList<String>();
			//pkpOrgCodeList.add(ExpressConstants.UNREAL_SALES_DEPARTMENT_CODE);
			List<String> arriveDistCodeList = new ArrayList<String>();
			// 获得到达区县
			String arriveDistCode = queryDto.getArriveDistCode();
			if(StringUtils.isNotBlank(arriveDistCode)){
				// 以逗号和空格来分割字符串
				String[] arriveDistCodeArr=arriveDistCode.split(", ");
				arriveDistCodeList=Arrays.asList(arriveDistCodeArr);
			}
			String pkpOrgCode = queryDto.getPkpOrgCode();
			if(StringUtils.isNotBlank(pkpOrgCode)){
				//提货网点不为空，提货网点和当前登录外场（就是走货路径里面最后一个外场）都作为查询条件
				pkpOrgCodeList.add(pkpOrgCode);
				//xiaobc 改start
				String currentTransferCenterCode = this.queryTransferCenterCode();
				queryDto.setCurrentTransferCenterCode(currentTransferCenterCode);//将当前登录用户所在外场存入查询条件中
				//xiaobc 改end
			}else{
				//提货网点不为空时，将当前登录外场（就是走货路径里面最后一个外场）作为查询条件
				//xiaobc 改start
				String currentTransferCenterCode = this.queryTransferCenterCode();
				queryDto.setCurrentTransferCenterCode(currentTransferCenterCode);//将当前登录用户所在外场存入查询条件中
				//xiaobc 改end
			}
			queryDto.setArriveDistCodeList(arriveDistCodeList);
			queryDto.setPkpOrgCodeList(pkpOrgCodeList);
		}
		return queryDto;
	}

	/**
	 * 获取补码退回总记录数
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:57:30
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IComplementService#queryComplementBackCount(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto)
	 */
	@Override
	public Long queryComplementBackCount(ComplementQueryDto queryDto) {
		// configComplementBackQueryInfo(queryDto);已在action中配置

		// Long count = complementDao.queryComplementBackCount(queryDto);
		if (StringUtils.isEmpty(queryDto.getWaybillNo())) {
			return complementOptimizedDao.queryComplementBackCount(queryDto);
		} else {
			return complementOptimizedDao.queryComplementBackCountByWaybillno(queryDto);
		}
	}
	
	/**
	 * 获取补码总记录数
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:57:30
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IComplementService#queryComplementCount(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto)
	 */
	@Override
	public Long queryComplementCount(ComplementQueryDto queryDto) {
		// 在action里面进行过设置
		// configComplementQueryInfo(queryDto);

		//		Long queryComplementCount = complementDao.queryComplementCount(queryDto);
		if (StringUtils.isEmpty(queryDto.getWaybillNo())) {
			return complementOptimizedDao.queryComplementCount(queryDto);
		}else{
			return complementOptimizedDao.queryComplementCountByWaybillno(queryDto);
		}
	}

	/**
	 * 根据登录用户获取所属快递大区
	 */
	@Override
	public String queryTransferCenterCode(){
		String cOrgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		log.error("获取转运场，当前登录部门：" + cOrgCode);
		OrgAdministrativeInfoEntity superOrg = loadService.querySuperiorOrgByOrgCode(cOrgCode);
		if(superOrg == null){
			log.error("获取转运场，当前登录部门：" + cOrgCode);
			return null;
		}
		log.error("获取转运场，当前登录部门：" + cOrgCode + "，获取到的快递大区为：" + superOrg.getCode());
		return superOrg.getCode();
	}

	@Override
	public List<ComplementLogEntity> queryComplementLogList(
			ComplementLogDto complementLogDto, int start, int limit) {
		//将来快递和零担分离后 请注释掉此处的代码 即可 begin
		if(complementLogDto!=null && complementLogDto.getOperationOrgCode()!=null){
			String currentDeptCode = complementLogDto.getOperationOrgCode();
	    	OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = querySupTfrCtr(currentDeptCode);
	    	if(orgAdministrativeInfoEntity!=null){
	    		complementLogDto.setOperationOrgCode(orgAdministrativeInfoEntity.getCode());
	    	}
		}
		//将来快递和零担分离后 请注释掉此处的代码 即可 end
		
		List<ComplementLogEntity> logList = complementDao.queryComplementLogList(complementLogDto, start, limit);
		if(logList!=null && logList.size()>0){
			for (ComplementLogEntity complementLogEntity : logList) {
				if(complementLogEntity!=null && complementLogEntity.getWaybillNo()!=null){
					String address = complementDao.queryComplementLogAddress(complementLogEntity.getWaybillNo());
					complementLogEntity.setAddress(address);
				}
			}
		}
		return logList;
	}

	@Override
	public Long queryComplementLogCount(ComplementLogDto complementLogDto) {
		return complementDao.queryComplementLogCount(complementLogDto);
	}

	/*
	 * 补码标签打印 
	 */
	@Override
	public Map<String, String> printComplementLabel(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			throw new TfrBusinessException("补码标签打印运单号不能为空!");
		}
		
		//根据运单号，查询快递运单信息
		WaybillExpressEntity waybill = waybillExpressService.queryWaybillExpressByNo(waybillNo);
		
		if(waybill == null){
			throw new TfrBusinessException("运单[" + waybillNo + "]不是快递货!");
		}
		
		Map<String, String> map = new HashMap<String, String>();
		
		//获取运单提货网点编码
		String pickupOrgCode = waybill.getCustomerPickupOrgCode();
		//提货网点名称
		String pickupOrgName = waybill.getCustomerPickupOrgName();
		
		//根据运单提货网点编码查询补码简称
		//String complementAbbr = orgInfo.getComplementSimpleName();
		String complementAbbr = orgAdministrativeInfoService.queryComplementSimpleNameInfoByCode(pickupOrgCode);
		
		if(StringUtils.isBlank(complementAbbr)){
			throw new TfrBusinessException("运单[" + waybillNo +"],提货网点[" + pickupOrgName + "],编码["
					+ pickupOrgCode + "]无补码简称,请上报异常!");
		}
		
		//提货方式
		String receiveMethod = waybill.getReceiveMethod();
		//自提货无需判断补码
		if (!StringUtils.equals(WaybillConstants.SELF_PICKUP, receiveMethod)
				&& !StringUtils.equals(WaybillConstants.INNER_PICKUP,
						receiveMethod)) {
			ComplementLogDto complementLogDto = new ComplementLogDto();
			complementLogDto.setWaybillNo(waybillNo);
			Long queryComplementLogCount = queryComplementLogCount(complementLogDto);
			if(queryComplementLogCount == 0){
				throw new TfrBusinessException("快递货[" + waybillNo + "]未补码,请立即补码!");
			}
		} 
		
		map.put("complementAbbr", complementAbbr);
		map.put("pickupOrgName", pickupOrgName);
		
		return map;
	}
	
	/**
	 * @author nly
	 * @date 2015年4月21日 下午4:49:34
	 * @function 根据运单号查询最晚补码日志
	 * @param waybillNo
	 * @return
	 */
	@Override
	public ComplementLogEntity queryLastComplementLog(String waybillNo) {
		ComplementLogEntity entity = null;
		List<ComplementLogEntity> logList = complementDao.queryComplementLogListByWaybillNo(waybillNo);
		if(!CollectionUtils.isEmpty(logList)) {
			entity = logList.get(0);
		}
		return entity;
	}
	
	/**
	 * @desc 查询部门所属外场
	 * @param orgCode
	 * @return
	 * @date 2015年4月7日 下午12:02:56
	 */
	@Override
	public OrgAdministrativeInfoEntity querySupTfrCtr(String orgCode){
		if (StringUtils.isEmpty(orgCode)) {
			return new OrgAdministrativeInfoEntity();
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,
						bizTypesList);

		return orgEntity;
	}
	
	/** 
	 * @desc 补码日志
	 * @param info
	 * @return
	 * @date 2015年12月1日 下午8:45:36
	 */
	@Override
	public void createComplementLog(AsyncComplementEntity info) {
		// 日志对象
		ComplementLogEntity log = new ComplementLogEntity();
		
		log.setId(UUIDUtils.getUUID());
		log.setWaybillNo(info.getWaybillNo());
		log.setBeforeOrgCode(info.getBeforePkpOrgCode());
		log.setBeforeOrgName(info.getBeforePkpOrgName());
		log.setAfterOrgCode(info.getPkpOrgCode());
		log.setAfterOrgName(info.getPkpOrgName());
		log.setOperatorCode(info.getEmpCode());
		log.setOperatorName(info.getEmpName());
		log.setOperationOrgCode(info.getTfrCtrCode());
		log.setOperationOrgName(info.getTfrCtrName());
		log.setOperationTime(new Date());
		//人工补码对应的code为5   326027
		log.setMatchType("5");
		complementDao.addComplementLog(log);
	}
	
	private ComplementLogEntity createComplementLog(String waybillNo, String beforeOrgCode,String beforeOrgName,String afterOrgCode,
				String afterOrgName,CurrentInfo currentInfo, boolean isAuto, String matchType){
		ComplementLogEntity log = new ComplementLogEntity();
		
		log.setId(UUIDUtils.getUUID());
		log.setWaybillNo(waybillNo);
		log.setBeforeOrgCode(beforeOrgCode);
		log.setBeforeOrgName(beforeOrgName);
		log.setAfterOrgCode(afterOrgCode);
		log.setAfterOrgName(afterOrgName);
		log.setOperatorCode(currentInfo.getEmpCode());
		log.setOperatorName(currentInfo.getEmpName());
		
		String operationOrgCode = currentInfo.getCurrentDeptCode();
		String operationOrgName = currentInfo.getCurrentDeptName();
		
		//若是自动补码，则操作部门取走货路径的最终外场
		//否则，操作部门取当前用户所在部门所属外场
		if(isAuto){
			List<ComplementLogEntity> logListOrg = complementDao.queryOperationOrg(waybillNo);
		    if(CollectionUtils.isNotEmpty(logListOrg)){
		    	operationOrgCode = logListOrg.get(0).getOperationOrgCode();
		    	operationOrgName = logListOrg.get(0).getOperationOrgName();
		    }
		    
		    log.setOperatorCode(FOSS_AUTO);
			log.setOperatorName(FOSS_AUTO);
			log.setMatchType(matchType);
		    
		}else{
			OrgAdministrativeInfoEntity orgInfo = querySupTfrCtr(operationOrgCode);
			if(orgInfo != null){
				operationOrgCode = orgInfo.getCode();
				operationOrgName = orgInfo.getName();
				//人工补码对应的code为5   218381
				log.setMatchType("5");
			}
		}
		
		log.setOperationOrgCode(operationOrgCode);
		log.setOperationOrgName(operationOrgName);
		log.setOperationTime(new Date());
		
		complementDao.addComplementLog(log);
		return log; 
	}
	
	/**
	 * @desc 特安运单客户校验
	 * @param isLdp
	 * @param waybill
	 * @date 2015年12月2日 下午5:40:05
	 */
	private void checkCust(boolean isLdp, WaybillEntity waybill) {
		String waybillNo = waybill.getWaybillNo();

		// 保价声明价值
		BigDecimal insuranceAmount = waybill.getInsuranceAmount();

		if (insuranceAmount == null) {
			return;
		}

		CustomerDto cust = customerService.queryCustomerDtoByCustCode(waybill.getDeliveryCustomerCode());

		if (cust != null && cust.getTeanLimit() != null) {
			Integer teanLimit = cust.getTeanLimit();
			if (insuranceAmount.compareTo(new BigDecimal(teanLimit)) > 0) {
				throw new TfrBusinessException("运单【" + waybillNo + "】保价费为" + insuranceAmount + "元,特安客户保价上限值不允许超过"
						+ teanLimit + "元");
			}
		} else {
			String configCode = !isLdp ? "1104" : "1105";

			ConfigurationParamsEntity config = configurationParamsService.queryConfigurationParamsOneByCode(configCode);
			if (config != null) {
				BigDecimal limit = null;
				try {
					limit = new BigDecimal(config.getConfValue());
				} catch (Exception e) {
					LOGGER.error("ComplementService.checkCust报错:" + StringUtils.substring(e.getMessage(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
				}

				if (limit != null && insuranceAmount.compareTo(limit) > 0) {
					throw new TfrBusinessException("运单【" + waybillNo + "】保价费为" + insuranceAmount + "元,快递货物保价金额不允许超过"
							+ limit + "元");
				}
			}
		}
	}
	
	/**
	 * @desc 代收货款校验
	 * @param waybill
	 * @param canAgentCollected
	 * @param agentCollectedUpperLimit
	 * @date 2015年12月2日 下午6:05:00
	 */
	private void checkCodAmont(WaybillEntity waybill, String canAgentCollected, String agentCollectedUpperLimit) {
		BigDecimal codAmount = waybill.getCodAmount();
		String waybillNo = waybill.getWaybillNo();

		if (codAmount == null || codAmount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!FossConstants.YES.equals(canAgentCollected)) {
			throw new TfrBusinessException("运单【" + waybillNo + "】代收货款为" + codAmount + "元，但该网点不支持代收货款业务，补码失败");
		}

		BigDecimal limit = null;
		try {
			limit = new BigDecimal(agentCollectedUpperLimit);
		} catch (Exception e) {
			LOGGER.error("ComplementService.checkCodAmont报错:" + StringUtils.substring(e.getMessage(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
		}
		if (limit != null && codAmount.compareTo(limit) > 0) {
			throw new TfrBusinessException("运单【" + waybillNo + "】" + "超过该网点代收上限，补码失败");
		}
	}
	
	/**
	 * @desc 补码前校验
	 * @param waybillNo
	 * @param pkpOrgCode
	 * @param pkpOrgName
	 * @return pkpOrgCode是否落地配网点，运单原提货网点编码和名称
	 * @date 2015年12月1日 下午3:59:23
	 */
	private Map<String, String> check(String waybillNo, String pkpOrgCode, String pkpOrgName) {
		// 如果这个单已经有生效的落地配交接单，则不允许补码
		if (handOverBillService.queryBeLdpHandOveredByWaybillNo(waybillNo)) {
			throw new TfrBusinessException("运单【" + waybillNo + "】已经录入快递代理交接单，不允许补码！");
		}

		// 判断是否已录入外发单.
		boolean beExistLdpExternalBill = ldpExternalBillService.checkExistLdpExternalBillByWaybillNo(waybillNo);
		if (beExistLdpExternalBill) {
			throw new TfrBusinessException("运单【" + waybillNo + "】已经录入快递代理外发单，不允许补码！");
		}
		// 判断是否已经结清货款
		boolean bePayment = repaymentService.isPayment(waybillNo);
		// 通过单号查询是否有子母件单号 是子母件单号的话 签收后可以进行补码 218427--hongwy-foss
		String waybillNoCZM = complementDao.queryWaybillNoCZM(waybillNo);
		if (bePayment && waybillNoCZM == null) {
			throw new TfrBusinessException("运单【" + waybillNo + "】已经结清货款，不允许补码！");
		}

		// 获取运单信息
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);

		if (waybill == null) {
			throw new TfrBusinessException("运单【" + waybillNo + "】,运单信息有异常或者不存在");
		}

		// 下面分补码提货网点是自由网点还是落地配网点进行相关校验
		SaleDepartmentEntity salesDept = SaleDepartmentService.querySaleDepartmentByCodeNoCache(pkpOrgCode);
		boolean isLdp = salesDept == null;

		// 特安运单客户校验
		checkCust(isLdp, waybill);

		if (!isLdp) {
			// 提货网点是否有代收货款权限和代收货款上限
			String canAgentCollected = salesDept.getCanAgentCollected();
			String agentCollectedUpperLimit = salesDept.getAgentCollectedUpperLimit();

			// 代收货款校验
			checkCodAmont(waybill, canAgentCollected, agentCollectedUpperLimit);
		} else {
			// 如果运单为自提， 则补码不可选择落地配网点
			if (StringUtils.equals(waybill.getReceiveMethod(), WaybillConstants.SELF_PICKUP)
					|| StringUtils.equals(waybill.getReceiveMethod(), WaybillConstants.INNER_PICKUP)) {
				throw new TfrBusinessException("运单【" + waybillNo + "】为自提货物，不可选择快递代理网点，请更改提货网点后重新补码！");
			}

			// 获取提货网点信息
			OuterBranchExpressEntity outerBranch = ldpAgencyDeptService.queryLdpAgencyDeptByCode(pkpOrgCode,
					FossConstants.YES);
			
			if(outerBranch != null){
				// 提货网点是否有代收货款权限和代收货款上限
				String canAgentCollected = outerBranch.getHelpCharge();
				String agentCollectedUpperLimit = outerBranch.getAgentCollectedUpperLimit();
				
				// 代收货款校验
				checkCodAmont(waybill, canAgentCollected, agentCollectedUpperLimit);
				
				// 如果有保价
				if (waybill.getInsuranceFee() != null && waybill.getInsuranceFee().compareTo(BigDecimal.ZERO) != 0
						&& !StringUtils.equals(outerBranch.getInsured(), FossConstants.YES)) {
					throw new TfrBusinessException("运单【" + waybillNo + "】保价费为" + waybill.getInsuranceFee() + "元，但“"
							+ pkpOrgName + "”不支持保价，请更改提货网点后重新补码！");
				}
				
				// 如果要返单
				if (StringUtils.equals(waybill.getReturnBillType(), WaybillConstants.RETURNBILLTYPE_ORIGINAL)
						&& !StringUtils.equals(outerBranch.getReturnBill(), FossConstants.YES)) {
					throw new TfrBusinessException("运单【" + waybillNo + "】要求签收单原件返回，但“" + pkpOrgName
							+ "”不支持该业务，请更改提货网点后重新补码！");
				}
				
				// 如果付款方式为到付
				if (StringUtils.equals(waybill.getPaidMethod(), WaybillConstants.ARRIVE_PAYMENT)
						&& !StringUtils.equals(outerBranch.getArriveCharge(), FossConstants.YES)) {
					throw new TfrBusinessException("运单【" + waybillNo + "】付款方式为到付，但“" + pkpOrgName + "”不支持该业务，请更改提货网点后重新补码！");
				}
			}
		}

		Map<String, String> result = new HashMap<String, String>(LoadConstants.SONAR_NUMBER_4);
		String ldpFlag = isLdp ? FossConstants.YES : FossConstants.NO;
		result.put("ldpFlag", ldpFlag);
		result.put("beforePkpOrgCode", waybill.getCustomerPickupOrgCode());
		result.put("beforePkpOrgName", waybill.getCustomerPickupOrgName());
		if(salesDept != null){
			result.put("pkpOrgName", salesDept.getName());
		}

		return result;
	}
	
	/**
	 * @desc 补码调用其他接口
	 * @param waybillNo 运单号
	 * @param pkpOrgCode 补码提货网点
	 * @param complementTime 补码时间
	 * @param ldpFlag 补码提货网点是否落地配Y/N
	 * @param currentInfo 补码用户当前对象
	 * @date 2015年11月30日 下午7:49:55
	 */
	private void invokeOtherInterface(String waybillNo, String pkpOrgCode, String pkpOrgName, Date complementTime, String ldpFlag,
			CurrentInfo currentInfo) {
		// 下面先调走货路径接口，走货路径最终外场有可能变更，
		// 再调接送货接口，接送货会去查走货路径的最终外场，并修改运单的最终外场
		// 最后调结算接口，如果是落地配网点则取得运单的最终外场，传给结算
		// 上述3接口调用顺序不能更改

		// 调用走货路径接口
		log.error("补码调用走货路径接口开始，waybillNo=" + waybillNo + "，pkpOrgCode=" + pkpOrgCode);
		calculateTransportPathService.changeDestinationPathForAlterDetail(waybillNo, pkpOrgCode);
		log.error("补码调用走货路径接口结束，waybillNo=" + waybillNo + "，pkpOrgCode=" + pkpOrgCode);

		// 调用PKP接口
		WaybillExpressDto pkpDto = new WaybillExpressDto();
		pkpDto.setWaybillNo(waybillNo);
		pkpDto.setCustomerPickupOrgCode(pkpOrgCode);
		pkpDto.setAddCodeTime(complementTime);
		
		log.error("补码调用接送货接口开始，waybillNo=" + waybillNo + "，pkpOrgCode=" + pkpOrgCode);
		WaybillDestinationDto waybillDestinationDto=waybillExpressService.addWaybillExpressCode(pkpDto);
		log.error("补码调用接送货接口结束，waybillNo=" + waybillNo + "，pkpOrgCode=" + pkpOrgCode);

		// 调用结算接口
		SettlementComplementBillDto stlDto = new SettlementComplementBillDto();
		stlDto.setWaybillNo(waybillNo);

		// ISSUE-4336 去掉只在org != null时，调用结算借口限制,并在stlDto set isFreeSite[结算]属性
		// 如果为落地配网点，则stlDto中的destOrgName,destOrgCode取运单的最终到达外场
		String isFreeSite = FossConstants.YES;
		String destOrgName = pkpOrgName;
		String destOrgCode = pkpOrgCode;

		if (FossConstants.YES.equals(ldpFlag)) {
			isFreeSite = FossConstants.NO;

			WaybillExpressEntity entity = waybillExpressService.queryWaybillExpressByNo(waybillNo);
			if (entity != null) {
				destOrgCode = entity.getLastOutFieldCode();
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(destOrgCode);
				if (orgInfo != null) {
					destOrgName = orgInfo.getName();
				}
			}
		}
		stlDto.setDestOrgName(destOrgName);
		stlDto.setDestOrgCode(destOrgCode);
		stlDto.setIsFreeSite(isFreeSite);

		log.error("补码调用结算接口开始，waybillNo=" + waybillNo + ",destOrgCode=" + destOrgCode + ",destOrgName=" + destOrgName
				+ ",isFreeSite=" + isFreeSite);
		complementFunctionService.complementFunctionWriteBackAndCreateReceivable(stlDto, currentInfo);
		log.error("补码调用结算接口结束，waybillNo=" + waybillNo + ",destOrgCode=" + destOrgCode + ",destOrgName=" + destOrgName
				+ ",isFreeSite=" + isFreeSite);
		//如果上面的接送货接口返回的waybillDestinationDto不为空 说明为合伙人补码  需要掉接送货接口推送给PTP
       if(waybillDestinationDto!=null){
            log.error("补码调用接送货接口推信息给PTP开始，waybillNo=" + waybillNo + "," +
           		      "destOrgCode=" + destOrgCode + ",destOrgName=" + destOrgName
                        + ",waybillDestinationDto=" + waybillDestinationDto);
		    waybillToPartnersService.sendDestinationInfo(waybillDestinationDto);
            log.error("补码调用接送货接口推信息给PTP结束，waybillNo=" + waybillNo + "," +
		    	  	"destOrgCode=" + destOrgCode + ",destOrgName=" + destOrgName+ "," +
		    	  	"waybillDestinationDto=" + waybillDestinationDto);
		    }
	}
	
	/**
	 * * 接送货推送运单信息给PTP Service
	 * */
	private IWaybillToPartnersService waybillToPartnersService ;
	
	/**
	 * 接送货推送运单信息给PTP Service
	 * @param waybillToPartnersService the waybillToPartnersService to set
	 */
	public void setWaybillToPartnersService(
			IWaybillToPartnersService waybillToPartnersService) {
		this.waybillToPartnersService = waybillToPartnersService;
	}
	/**
	 * @desc 手动补码
	 * @param waybillNo
	 * @param pkpOrgCode
	 * @param pkpOrgName
	 * @date 2015年11月30日 下午7:37:50
	 */
	@Transactional
	@Override
	public void complementByHand(String waybillNo, String pkpOrgCode, String pkpOrgName) {
		//补码前校验
		Map<String, String> map = check(waybillNo, pkpOrgCode, pkpOrgName);
		
		String ldpFlag = map.get("ldpFlag");
		String beforePkpOrgCode = map.get("beforePkpOrgCode");
		String beforePkpOrgName = map.get("beforePkpOrgName");

		//在需手动补码表里删除
		autoAddCodeByHandService.deleteAddCodeByHand(waybillNo);

		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String empCode = currentInfo.getEmpCode();
		String empName = currentInfo.getEmpName();
		String empDeptCode = currentInfo.getCurrentDeptCode();
		String empDeptName = currentInfo.getCurrentDeptName();
		
		String tfrCtrCode = empDeptCode;
		String tfrCtrName = empDeptName;
		
		//获取当前用户所属外场
		OrgAdministrativeInfoEntity tfrCtrInfo = this.querySupTfrCtr(empDeptCode);
		if(tfrCtrInfo != null){
			tfrCtrCode = tfrCtrInfo.getCode();
			tfrCtrName = tfrCtrInfo.getName();
		}
		
		AsyncComplementEntity info = new AsyncComplementEntity();
		info.setId(UUIDUtils.getUUID());
		info.setWaybillNo(waybillNo);
		info.setPkpOrgCode(pkpOrgCode);
		info.setPkpOrgName(pkpOrgName);
		info.setLdpFlag(ldpFlag);
		info.setEmpCode(empCode);
		info.setEmpName(empName);
		info.setEmpDeptCode(empDeptCode);
		info.setEmpDeptName(empDeptName);
		info.setTfrCtrCode(tfrCtrCode);
		info.setTfrCtrName(tfrCtrName);
		Date date = new Date();
		info.setCreateTime(date);
		info.setModifyTime(date);
		info.setFailed(FossConstants.NO);
		info.setBeforePkpOrgCode(beforePkpOrgCode);
		info.setBeforePkpOrgName(beforePkpOrgName);
		
		if(!StringUtils.equals(pkpOrgCode, beforePkpOrgCode)){
			//记录需要异步补码的运单信息
			complementDao.insertAsyncComplement(info);
		}
		
	}
	
	/**
	 * @desc 异步补码真正写数据的部分，返回补码失败的异常信息
	 * @param info
	 * @date 2015年11月30日 下午6:58:01
	 */
	@Transactional
	@Override
	public void complementAsync(AsyncComplementEntity info) {
		String waybillNo = info.getWaybillNo();
		String pkpOrgCode = info.getPkpOrgCode();
		String pkpOrgName = info.getPkpOrgName();
		String ldpFlag = info.getLdpFlag();
		String empCode = info.getEmpCode();
		String empName = info.getEmpName();
		String empDeptCode = info.getEmpDeptCode();
		String empDeptName = info.getEmpDeptName();

		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(empCode);
		employee.setEmpName(empName);
		
		UserEntity user = new UserEntity();
		user.setEmployee(employee);
		
		user.setEmpCode(empCode);
		user.setEmpName(empName);
		
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(empDeptCode);
		dept.setName(empDeptName);
		
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		// 补码调用其他接口
		invokeOtherInterface(waybillNo, pkpOrgCode, pkpOrgName, new Date(), ldpFlag, currentInfo);
	}
	
	/**
	 * @desc 查询异步补码失败的运单
	 * @param info
	 * @param start
	 * @param limit
	 * @return
	 * @date 2015年12月1日 上午11:23:06
	 */
	@Override
	public List<AsyncComplementEntity> findAsyncComplementFailed(AsyncComplementFailedQcDto info, int start, int limit) {
		return complementDao.findAsyncComplementFailed(info, start, limit);
	}
	
	/**
	 * @desc 查询异步补码失败运单数量
	 * @param info
	 * @return
	 * @date 2015年12月1日 上午11:23:09
	 */
	@Override
	public Long cntAsyncComplementFailed(AsyncComplementFailedQcDto info) {
		return complementDao.cntAsyncComplementFailed(info);
	}
	
	/**
	 * @desc 补码方法封装-补码校验-记录补码日志-各种接口调用
	 * @param waybillNo运单号
	 * @param pkpOrgCode补码提货网点编码
	 * @param pkpOrgName补码提货网点名称
	 * @param currentInfo当前用户对象，有可能是自动补码时虚拟的当前对象
	 * @param isAuto是否自动补码
	 * @date 2015年12月1日 下午2:17:39
	 */
	private void complement(String waybillNo, String pkpOrgCode, String pkpOrgName, CurrentInfo currentInfo, boolean isAuto, String matchType) {
		// 补码校验
		Map<String, String> map = check(waybillNo, pkpOrgCode, pkpOrgName);
		// 补码提货网点是否落地配网点以及运单原提货网点编码、名称
		String ldpFlag = map.get("ldpFlag");
		String beforePkpOrgCode = map.get("beforePkpOrgCode");
		String beforePkpOrgName = map.get("beforePkpOrgName");
		
		if(StringUtils.isEmpty(pkpOrgName)){
			pkpOrgName = map.get("pkpOrgName");
		}
		
		if(StringUtils.isEmpty(pkpOrgName)){
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pkpOrgCode);
			if(org != null){
				pkpOrgName = org.getName();
			}
		}
		
		if(!StringUtils.equals(pkpOrgCode, beforePkpOrgCode)){
			// 记录补码日志
			ComplementLogEntity log = createComplementLog(waybillNo, beforePkpOrgCode, beforePkpOrgName, pkpOrgCode,
					pkpOrgName, currentInfo, isAuto, matchType);
			// 调用接口
			invokeOtherInterface(waybillNo, pkpOrgCode, pkpOrgName, log.getOperationTime(), ldpFlag, currentInfo);
		}
	}
	
	/**
	 * @desc 对异步补码失败的运单进行补码
	 * @param waybillNo;
	 * @param pkpOrgCode
	 * @param pkpOrgName
	 * @date 2015年12月1日 上午11:23:12
	 */
	@Transactional
	@Override
	public void complement4Failed(String waybillNo, String pkpOrgCode, String pkpOrgName) {
		//补码
		complement(waybillNo, pkpOrgCode, pkpOrgName, FossUserContext.getCurrentInfo(),false, null);
		// 补码成功后，删除对应记录
		AsyncComplementEntity info = new AsyncComplementEntity();
		info.setWaybillNo(waybillNo);
		info.setFailed(FossConstants.YES);
		complementDao.deleteAsyncComplementFailed(info);
	}
	
	/**
	 * @desc 自动补码，为防止其他地方调用，保留原来是否自动补码参数以及相关代码
	 * @param waybillNo
	 * @param pkpOrgCode
	 * @param pkpOrgName
	 * @param beAutoComplement
	 * @return
	 * @date 2015年12月1日 下午2:43:14
	 */
	@Transactional
    @Override
	public int complementPkpOrg(String waybillNo, String pkpOrgCode, String pkpOrgName,String beAutoComplement, String matchType) {
		//是否自动补
		boolean isAuto = FossConstants.YES.equals(beAutoComplement);
		
		CurrentInfo currentInfo = null;
		//自动补码时给当前用户一个默认值
		if(isAuto){
			UserEntity user = new UserEntity();
			user.setEmpCode(FOSS_AUTO);
			user.setEmpName(FOSS_AUTO);
			
			EmployeeEntity employee = new EmployeeEntity();
			employee.setEmpCode(FOSS_AUTO);
			employee.setEmpName(FOSS_AUTO);
			
			user.setEmployee(employee);
			
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			dept.setCode(FOSS_AUTO);
			
			
			dept.setName(FOSS_AUTO);
			currentInfo = new CurrentInfo(user,dept);
		}else{
			currentInfo = FossUserContext.getCurrentInfo();
		}
		
		//补码
		complement(waybillNo, pkpOrgCode, pkpOrgName, currentInfo, isAuto, matchType);
		
		//删除待人工补码的记录
		if(!isAuto){
			autoAddCodeByHandService.deleteAddCodeByHand(waybillNo);
		}
		
		return FossConstants.SUCCESS;
	}
	
	@Override
	public List<AsyncComplementEntity> findAsyncComplement() {
		return complementDao.findAsyncComplement();
	}
	
	@Override
	public int deleteAsyncComplement(AsyncComplementEntity info) {
		return complementDao.deleteAsyncComplement(info);
	}
	
	@Override
	public void updateAsyncFailed(AsyncComplementEntity info) {
		complementDao.updateAsyncFailed(info);
	}

}
