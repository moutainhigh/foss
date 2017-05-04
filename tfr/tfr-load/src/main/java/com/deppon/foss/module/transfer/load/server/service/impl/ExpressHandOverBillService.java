/**
 * 1、	【查询条件】模块中的【包号】输入只能输入字母（不必区分大小写）和阿拉伯数字，包号是具有一个区别于单号、交接单号的特定字段，比如以“Ｂ＂开头；
2、	【包号】的默认值为空。
1、	【查询条件】模块中的默认查询范围为当前转运场或营业部所属工号建立的包，不能查询其他部门建立的包；若要查询其他部门的包需要切换FOSS系统到相应部门；
【查询条件】模块中的【到达部门】选择范围为全国所有的快件发货城市。
【查询条件】模块中的【建包人工号】可以人工输入，也可以从下拉菜单中选择。
1、	【查询条件】模块中的【建包时间】是必选项；
2、	【建包时间】必须精确到“秒“，查询的时间段最大为7天，截止时间不得超过当前系统时间；
3、	【建包时间】的默认值为登陆时间的前一天0:00:00到当天23:59:59。
1、	系统支持多选择项查询，使用包号查询为排他条件
1、	默认状态下，包列表为空；
2、	输入查询条件，点击【查询】后显示相应查询结果。
【包列表】中的各条信息是按照建包时间的先后顺序排列的。最早建包的信息，排在列表的最前面。
1、	【包列表】中的【生成交接单】是一个按钮，点击某一个包号的【生成交接单】后，则链接至【新增交接单】；
2、	如果交接单已经生成，再点击【生成交接单】按钮，则提示“已生成交接单，不能再次生成”。
新增交接单页面可以从管理包信息的【生成交接单】链接过来。
如果从管理包信息的【生成交接单】链接过来，默认勾选【是否包交接】，【是否包交接】后文本框中显示该包号，并出现【导入】按钮。
如果直接点击新增交接单菜单进入，【是否包交接】默认不勾选。当勾选【是否包交接】后出现文本框，文本框为空；并出现【导入】按钮。
在包交接模式下，【装车完成时间】呈激活状态，默认为建包完成时间，可修改，【是否预配交接单】被隐藏。
在保存时，交接类型按照出发部门和到达部门进行验证。
在保存时，对包里快件的票和件进行逐一审查，不在当前部门库存的，提示不能生成交接单；
交接单保存成功后货物出库。
新业务规则
查询待配载交接单页面中，在确认时，对添加的交接单进行校验，所选交接单必须全部为包的交接单，或全部为非包的交接单。否则弹窗提示。
新增交接单返回配载单页面时，进行校验，新增的交接单必须与原有的交接单类型一致，全部为包的交接单，或全部为非包的交接单。否则弹窗提示。
全为包的配载单的“配载车次号”的生成规则为：
①“KD-”+始发外场简称+到达外场简称+出发日期（格式yymmdd）+班次；
②如果“配载类型”为“整车”，配载车次号编码规则为：“KD-”+出发站缩写+目的站缩写+出发日期（格式yymmdd）+Z+班次(此班次和“卡车班次”无关，为本部门该日在该路线上所发车次数的累加)，班次为两位数字，从1依次往上递加，例如“出发日期”为2012年4月28日广州到上海的班次为1的全部为包的配载单，则该配载单的配载车次号为：KD-GZSH12042801，不可人工填写与修改；
 */
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.transfer.common.api.shared.define.ExpressConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LdpHandOverDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LdpHandOverDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: ExpressHandOverBillService
 * @author: ShiWei shiwei@outlook.com
 * @description: 包交接单service
 * @date: 2013-7-26 下午2:18:37
 * 
 */
public class ExpressHandOverBillService implements IExpressHandOverBillService {
	
	/**
	 * 本模块dao
	 */
	private IExpressHandOverBillDao expressHandOverBillDao;
	
	/**
	 * 包dao，用于获取包基本信息
	 */
	private IExpressPackageDao expressPackageDao;
	
	/**
	 *交接单dao
	 */
	private IHandOverBillDao handOverBillDao;
	
	/**
	 * 用于获取小部门所属的大部门
	 */
	private ILoadService loadService;
	
	/**
	 * 综合线路service，根据外场编码获取其辐射的营业部
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	/**
	 * 综合部门service，传入“到达部门code”，判断该部门是否为营业部、外场
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 库存service，用来出库、更改流水号库存预配状态
	 */
	private IStockService stockService;
	
	/**
	 * 任务车辆service，用于修改、作废交接单时调用接口更新任务车辆明细信息
	 */
	private ITruckTaskService truckTaskService;
	
	/**
	 * 发车计划service
	 */
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	
	/**
	 * 车辆服务，用户查询车辆所有权
	 */
	private IVehicleService vehicleService;
	
	/**
	 * 自有司机service
	 */
	private IOwnDriverService ownDriverService;
	
	/**
	 * 外请司机service
	 */
	private ILeasedDriverService leasedDriverService;
	
	/**
	 * 记录日志
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 重泡比 zwd
	 */
    private IHeavyBubbleRatioService heavyBubbleRatioService;
    
	/**
	 * 重泡比 zwd
	 */
    //产品接口
    @Resource
    private IProductService  productService4Dubbo;
    
    
/*	public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/
	public void setHeavyBubbleRatioService(
			IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setHandOverBillDao(IHandOverBillDao handOverBillDao) {
		this.handOverBillDao = handOverBillDao;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public void setTruckDepartPlanDetailService(
			ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}

	public void setExpressHandOverBillDao(
			IExpressHandOverBillDao expressHandOverBillDao) {
		this.expressHandOverBillDao = expressHandOverBillDao;
	}

	public void setExpressPackageDao(IExpressPackageDao expressPackageDao) {
		this.expressPackageDao = expressPackageDao;
	}

	/**
	 * 根据包号获取运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:05:10
	 */
	@Override
	public List<HandOverBillDetailEntity> queryWaybillListByPackageNo(String packageNo){
		return expressHandOverBillDao.queryWaybillListByPackageNo(packageNo);
	}
	
	/**
	 * 根据包号和运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:06:03
	 */
	@Override
	public List<SerialNoStockEntity> querySerialNoListByPackageNoAndWaybillNo(String packageNo,String waybillNo){
		return expressHandOverBillDao.querySerialNoListByPackageNoAndWaybillNo(packageNo, waybillNo);
	}

	/**
	 * 根据包号获取包信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午3:24:59
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService#queryExpressPackageByPackageNo(java.lang.String)
	 */
	@Override
	public ExpressPackageEntity queryExpressPackageByPackageNo(String packageNo) {
		return expressPackageDao.queryExpressPackageByPackageNo(packageNo);
	}
	
	/**
	 * 根据包号加载包信息，用于包生成交接单时的数据加载
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午3:50:48
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService#loadExpressPackageInfo(java.lang.String)
	 */
	@Override
	public List<Object> loadExpressPackageInfo(String packageNo){
		ExpressPackageEntity pEntity = this.queryExpressPackageByPackageNo(packageNo);
		//如果状态不是未生成交接单，试抛异常
		if(null != pEntity && StringUtils.isNotEmpty(pEntity.getStatus()) && StringUtils.equals(pEntity.getStatus(), ExpressConstants.PACKAGE_STATUS_ALREADY_CANCLED)){
			throw new TfrBusinessException("该包已撤销，无法生成交接单！");
		}
		if(null != pEntity && StringUtils.isNotEmpty(pEntity.getStatus()) && StringUtils.equals(pEntity.getStatus(), ExpressConstants.PACKAGE_STATUS_CREATED_HANDOVER_BILL)){
			throw new TfrBusinessException("该包已生成交接单，无法重复生成！");
		}
		/*
		 * 构造交接单基本信息
		 */
		HandOverBillEntity hEntity = new HandOverBillEntity();
		//交接单号-包号
		/**
		 * @desc 判断是否快递空运，装B的叫法==商务专递
		 * by @author wqh
		 * @date 2015-08-15
		 * */
		String handoverBillNo=packageNo;
		//拿到包类型，如果为快递空运则交接单号在之前的基础上建B替换成BY
		String exPackageType=pEntity.getExpressPackageType();
		if(StringUtils.isNotEmpty(exPackageType)&&exPackageType.equals(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_KD_AIR)){
			handoverBillNo="KY"+handoverBillNo; 
		}
		hEntity.setHandOverBillNo(handoverBillNo);
		//出发部门code、name
		hEntity.setDepartDept(pEntity.getDepartOrgName());
		hEntity.setDepartDeptCode(pEntity.getDepartOrgCode());
		//到达部门code、name
		hEntity.setArriveDept(pEntity.getArriveOrgName());
		hEntity.setArriveDeptCode(pEntity.getArriveOrgCode());
		//装车完成时间-建包结束时间
		hEntity.setLoadEndTime(pEntity.getEndTime());
		
		/*
		 * 构造运单信息
		 */
		List<HandOverBillDetailEntity> wList = this.queryWaybillListByPackageNo(packageNo);
		for(HandOverBillDetailEntity wEntity : wList){
			String waybillNo = wEntity.getWaybillNo();
			//获取运单的流水号list
			List<SerialNoStockEntity> sList = this.querySerialNoListByPackageNoAndWaybillNo(packageNo, waybillNo);
			wEntity.setSerialNoStockList(sList);
		}
		
		//定义返回值
		List<Object> rList = new ArrayList<Object>();
		rList.add(hEntity);
		rList.add(wList);
		
		return rList;
	}
	
	/**
	 * 保存包交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-7-30 下午3:19:29
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService#saveHandOverBill(com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto)
	 */
	@Override
	@Transactional
	public String saveExpressHandOverBill(NewHandOverBillDto newHandOverBillDto) {
		/**
		 *获取dto中的交接单基本信息，补充前台未设置的属性
		 */
		//获取当前用户工号、姓名
		UserEntity user = FossUserContext.getCurrentUser();
		//用户code
		String userCode = user.getEmployee().getEmpCode();
		//用户name
		String userName = user.getEmployee().getEmpName();
		//获取当前时间
		Date nowDate = new Date();
		//获取传入的交接单基本信息实体
		HandOverBillEntity handOverBillEntity = newHandOverBillDto.getHandOverBillEntity();
		String handOverBillNo = handOverBillEntity.getHandOverBillNo();
		/**
		 * 快递空运的包需要处理 KY的字符串
		 * by wqh
		 * time:2015-10-12
		 * **/
		String packageNo=handOverBillNo;
		if(StringUtil.isNotEmpty(packageNo)&&packageNo.contains("KYB")){
			packageNo=packageNo.replace("KYB", "B");
		}else if(StringUtil.isNotEmpty(packageNo)&&packageNo.contains("kyB")){
			packageNo=packageNo.replace("kyB", "B");
		}
		//校验包状态
		ExpressPackageEntity pEntity = this.queryExpressPackageByPackageNo(packageNo);
		//如果状态不是未生成交接单，试抛异常
		if(StringUtils.equals(pEntity.getStatus(), ExpressConstants.PACKAGE_STATUS_ALREADY_CANCLED)){
			throw new TfrBusinessException("该包已撤销，无法生成交接单！");
		}
		if(StringUtils.equals(pEntity.getStatus(), ExpressConstants.PACKAGE_STATUS_CREATED_HANDOVER_BILL)){
			throw new TfrBusinessException("该包已生成交接单，无法重复生成！");
		}

		// 补充基本信息属性
		handOverBillEntity.setCreateDate(nowDate);
		//交接时间 
		
		handOverBillEntity.setHandOverTime(nowDate);
		// 创建人姓名、工号、币种
		handOverBillEntity.setCreateUserName(userName);
		//创建人code
		handOverBillEntity.setCreateUserCode(userCode);
		//币种
		handOverBillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//主键
		handOverBillEntity.setId(UUIDUtils.getUUID());
		//是否包交接单
		handOverBillEntity.setBePackage(FossConstants.YES);
		
		/**
		 * 校验车辆、司机所有权：自有车、自有司机；外请车、外请车司机，不混搭
		 */
		log.error("交接单号：" + handOverBillNo + "开始校验车辆、司机所有权");
		String truckOwner=this.validateVehicleAndDriverOwnerShip(handOverBillEntity.getVehicleNo(), handOverBillEntity.getDriver());
		log.error("交接单号：" + handOverBillNo + "校验车辆、司机所有权结束");
		
		
		//设置是否为PDA扫描生成：否
		handOverBillEntity.setIsCreatedByPDA(FossConstants.NO);
		//设置修改人修改日期
		handOverBillEntity.setModifyDate(nowDate);
		//新增时，修改人等于创建人
		handOverBillEntity.setModifyUser(handOverBillEntity.getCreateUser());
		//name
		handOverBillEntity.setModifyUserName(handOverBillEntity.getCreateUserName());
		//code
		handOverBillEntity.setModifyUserCode(handOverBillEntity.getCreateUserCode());
		//获取当前部门的上级部门的名称、编码
		OrgAdministrativeInfoEntity superOrg = loadService.querySuperiorOrgByOrgCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
		String orgName = superOrg.getName();
		// 出发部门
		handOverBillEntity.setDepartDept(orgName);
		//出发部门code
		handOverBillEntity.setDepartDeptCode(superOrg.getCode());
		//校验车牌号是否可用
		if(StringUtils.isNotBlank(handOverBillEntity.getVehicleNo())&&!(StringUtils.equals(handOverBillEntity.getBeTrailerVehicleNo(), FossConstants.YES)
				&&StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)
				&&!StringUtils.equals(truckOwner, ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED))){
			log.error("交接单号：" + handOverBillNo + "开始校验车牌号");
			//是否存在有未校验封签的记录
			loadService.validateVehicleNoCanBeUsed(handOverBillEntity.getVehicleNo());
			//校验其他部门是否有使用该车辆并且未出发的记录
			loadService.queryUndepartRecByVehicleNo(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getVehicleNo(), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
			log.error("交接单号：" + handOverBillNo + "校验车牌号结束");
			handOverBillEntity.setBeTrailerVehicleNo(null);
		}
		
		/**
		 * 遍历dot中的运单list获取Vo中的运单列表，补充前台未设置的属性，遍历时同时计算总票数，总件数，总重量，总体积
		 */
		List<HandOverBillDetailEntity> waybillList = newHandOverBillDto.getWaybillStockList();
		//是否整车交接单
		handOverBillEntity.setIsCarLoad(FossConstants.NO);
		//如果不是外发交接单，则读取发车计划
		if(!StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, handOverBillEntity.getHandOverType())){
			log.error("交接单号：" + handOverBillNo + "开始读取发车计划");
			//获取发车计划信息
			TruckDepartPlanDetailDto planDetail = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(superOrg.getCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getVehicleNo(),null);
			if(planDetail != null){
				// 发车计划明细ID
				handOverBillEntity.setTruckDepartPlanDetailId(planDetail.getId());
				// 线路名称
				handOverBillEntity.setLineName(planDetail.getLineName());
				//线路虚拟编码
				handOverBillEntity.setLineCode(planDetail.getLineNo());
				//班次
				handOverBillEntity.setFrequencyNo(planDetail.getFrequencyNo());
			}
			log.error("交接单号：" + handOverBillNo + "读取发车计划结束");
		}
		
		log.error("交接单号：" + handOverBillNo + "开始校验线路");
		//校验线路
		DepartureStandardDto departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getCreateDate());
		//如果两部门之间线路不存在，则中断，不允许交接
		if(departureStandard == null || StringUtils.isBlank(departureStandard.getLineVirtualCode())){
			//判断两部门之间快递线路是否存在，若不存在，不允许交接
			DepartureStandardDto  departureStandard2 =expresslineService.queryDepartureStandardListBySourceTargetDirectly(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getCreateDate()); 
			if(departureStandard2 == null || StringUtils.isBlank(departureStandard2.getLineVirtualCode())){
				//业务异常信息
				throw new TfrBusinessException("本部门至“" + handOverBillEntity.getArriveDept() + "”之间的线路不存在，无法交接！");
			}
		}
		log.error("交接单号：" + handOverBillNo + "校验线路结束");
		
		/**
		 * 校验交接类型
		 */
		log.error("交接单号：" + handOverBillNo + "开始校验交接类型");
		OrgAdministrativeInfoEntity departOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(handOverBillEntity.getDepartDeptCode());
		OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(handOverBillEntity.getArriveDeptCode());
		/**
		 * 只有外场到分部才能做分部交接单
		 */
		if(StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_DIVISION)
				&& departOrg != null
				&& destOrg != null
				&& !StringUtils.equals(departOrg.getTransferCenter(), FossConstants.YES)
				&& !StringUtils.equals(destOrg.getExpressBranch(), FossConstants.YES)){
			throw new TfrBusinessException("转运到分部之间进行交接时，“交接类型”才能选择“分部交接单”！");
		}
		/**
		 * 到达部门、出发部门均为外场，则必须为集配交接单，否则只能为短配,如果到达部门是分部，则必须是分部交接单
		 */
		if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_PARTIALLINE)){
			if(departOrg != null
					&& destOrg != null
					&& StringUtils.equals(departOrg.getTransferCenter(), FossConstants.YES)
					&& StringUtils.equals(destOrg.getTransferCenter(), FossConstants.YES)){
				if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)){
					if(!StringUtils.equals(destOrg.getExpressBranch(), FossConstants.YES)){
						//业务异常信息
						throw new TfrBusinessException("转运中心之间进行交接时，“交接类型”必须为“集配交接单”！");
					}
				}
			}else if(!StringUtils.equals(handOverBillEntity.getIsCarLoad(), FossConstants.YES)){
				if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)
						&&!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_DIVISION)){
					//业务异常信息
					throw new TfrBusinessException("非整车交接时，“交接类型”必须为“短配交接单”或者“分部交接单”！");
				}
			}
		}
		log.error("交接单号：" + handOverBillNo + "校验交接类型结束");
		//构造待保存的运单list
		List<HandOverBillDetailEntity> unSavedWaybillStockList = new ArrayList<HandOverBillDetailEntity>();
		// 定义总票数、总件数、总重量、总体积、代收货款总额
		Integer totalWaybill = 0, totalPieces = 0, fastWaybillCount = 0;
		//定义精准卡航票数、重量、体积；精准空运票数、重量、体积；精准城运票数、重量、体积
		Integer waybillQtyFLF = 0, waybillQtyAF = 0, waybillQtyFSF = 0;
		BigDecimal weightFLF = BigDecimal.ZERO,weightAF = BigDecimal.ZERO,weightFSF = BigDecimal.ZERO,
				volumeFLF = BigDecimal.ZERO,volumeAF = BigDecimal.ZERO,volumeFSF = BigDecimal.ZERO;
		//总体积
		BigDecimal totalVolume = BigDecimal.ZERO, 
				//总重量
				totalWeight = BigDecimal.ZERO, 
				//总金额
				totalMoney = BigDecimal.ZERO,
				//代收货款总额
				totalCodAmount = BigDecimal.ZERO,
				//快递货总重量
				fastTotalWeight=BigDecimal.ZERO,
				//非快递货体积
				normalTotalVolume=BigDecimal.ZERO;
				//未转换体积
//				unconvertTotalVolume=BigDecimal.ZERO;
		for (int i = 0; i < waybillList.size(); i++) {
			HandOverBillDetailEntity waybillEntity = waybillList.get(i);
			//如果为优先货
			if(StringUtils.equals(waybillEntity.getIsFastGoods(), FossConstants.YES)){
				//优先货累加
				fastWaybillCount += 1;
			}
			//如果为精准卡航
			if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_FLF)){
				waybillQtyFLF += 1;
				weightFLF = weightFLF.add(waybillEntity.getWeight());
				volumeFLF = volumeFLF.add(waybillEntity.getCubage());
			//如果为精准空运
			}else if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_AF)){
				waybillQtyAF += 1;
				weightAF = weightAF.add(waybillEntity.getWeight());
				volumeAF = volumeAF.add(waybillEntity.getCubage());
			//如果为精准城运
			}else if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_FSF)){
				waybillQtyFSF += 1;
				weightFSF = weightFSF.add(waybillEntity.getWeight());
				volumeFSF = volumeFSF.add(waybillEntity.getCubage());
			}
			
			//币种
			waybillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			//交接单号
			waybillEntity.setHandOverBillNo(handOverBillEntity.getHandOverBillNo());
			//出发部门编码
			waybillEntity.setOrigOrgCode(handOverBillEntity.getDepartDeptCode());
			//交接类型
			waybillEntity.setHandOverType(handOverBillEntity.getHandOverType());
			//设置ID
			waybillEntity.setId(UUIDUtils.getUUID());
			//设置交接时间
			waybillEntity.setCreateDate(handOverBillEntity.getHandOverTime());
			//设置修改时间
			waybillEntity.setModifyDate(handOverBillEntity.getHandOverTime());
			//保险价值，乘以100
			waybillEntity.setInsuranceValue(waybillEntity.getInsuranceValue().multiply(new BigDecimal(LoadConstants.SONAR_NUMBER_100)));
			//运单金额
			waybillEntity.setWaybillFee(waybillEntity.getWaybillFee().multiply(new BigDecimal(LoadConstants.SONAR_NUMBER_100)));//存储总金额的时候以分为单位，乘以100
			unSavedWaybillStockList.add(waybillEntity);
			//票数+1
			totalWaybill += 1;
			//件数累加
			totalPieces += waybillEntity.getPieces().intValue();
			//总体积累加
			totalVolume = totalVolume.add(waybillEntity.getCubageAc());
			//总重量累加
			totalWeight = totalWeight.add(waybillEntity.getWeightAc());
			//总金额累加
			totalMoney = totalMoney.add(waybillEntity.getWaybillFee());
			//代收货款总额累加
			totalCodAmount = totalCodAmount.add(waybillEntity.getCodAmount());
			
			//查询所有的快递3级产品
			List<String> productCodes=productService4Dubbo.getAllLevels3ExpressProductCode();
			if(CollectionUtils.isNotEmpty(productCodes)&&productCodes.size()>0){
				/*//在判断产品中是否都是快递
				for(String productCode:productCodes){
					if(!productService.onlineDetermineIsExpressByProductCode(productCode,new Date())){
						throw new TfrBusinessException("调接送接口：getAllLevels3ExpressProductCode 获取所有快递3级产品异常");
					}
					
				}*/
				//快递转换体积
				if(productCodes.contains(waybillEntity.getTransPropertyCode())){
					fastTotalWeight=fastTotalWeight.add(waybillEntity.getWeightAc());
				}
				
				
			}else{
				normalTotalVolume=normalTotalVolume.add(waybillEntity.getCubageAc());
				//快递转换体积
				/*if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_PACKAGE)
						||StringUtils.equals(waybillEntity.getTransPropertyCode(), "RCP")
						||StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_EPEP)){
					fastTotalWeight=fastTotalWeight.add(waybillEntity.getWeightAc());
				}else{
					normalTotalVolume=normalTotalVolume.add(waybillEntity.getCubageAc());
				}*/
				
			}
			
			
			
		}
		//计算快递转换体积
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		totalVolume=(normalTotalVolume.add(fastTotalWeight.multiply(queryExpressConverParameter(handOverBillEntity.getDepartDeptCode())).divide(new BigDecimal(LoadConstants.VOLUME_SIZE)))).setScale(2,BigDecimal.ROUND_HALF_UP);
		// 总重量，总体积，总金额、代收货款总额
		handOverBillEntity.setVolumeTotal(totalVolume);
		//重量
		handOverBillEntity.setWeightTotal(totalWeight);
		//总金额
		handOverBillEntity.setMoneyTotal(totalMoney);
		//代收货款总额
		handOverBillEntity.setCodAmountTotal(totalCodAmount);
		// 优先货票数
		handOverBillEntity.setFastWaybillQty(fastWaybillCount);
		// 总件数，总票数
		handOverBillEntity.setGoodsQtyTotal(totalPieces);
		handOverBillEntity.setWaybillQtyTotal(totalWaybill);
		//卡航、空运、城运票数、重量、体积
		handOverBillEntity.setWaybillQtyAF(waybillQtyAF);
		handOverBillEntity.setGoodsWeightAF(weightAF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeAF(volumeAF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setWaybillQtyFLF(waybillQtyFLF);
		handOverBillEntity.setGoodsWeightFLF(weightFLF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeFLF(volumeFLF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setWaybillQtyFSF(waybillQtyFSF);
		handOverBillEntity.setGoodsWeightFSF(weightFSF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeFSF(volumeFSF.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		/**
		 * 遍历dto中的流水号list获取流水号列表，补充前台未设置的属性
		 */
		List<HandOverBillSerialNoDetailEntity> serialNoList = newHandOverBillDto.getSerialNoStockList();
		// 循环流水号列表，设置其他属性
		List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList = new ArrayList<HandOverBillSerialNoDetailEntity>();
		//出库所有流水号
		List<InOutStockEntity> inStorageSerialNoList = new ArrayList<InOutStockEntity>();
		for (int i = 0; i < serialNoList.size(); i++) {
			HandOverBillSerialNoDetailEntity serialNo = serialNoList.get(i);
			InOutStockEntity entity = new InOutStockEntity();
			//运单号
			entity.setWaybillNO(serialNo.getWaybillNo());
			//流水号
			entity.setSerialNO(serialNo.getSerialNo());
			//部门code
			entity.setOrgCode(superOrg.getCode());
			//出库类型，手动交接出库
			entity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
			//操作人code
			entity.setOperatorCode(userCode);
			//操作人name
			entity.setOperatorName(userName);
			//交接单号
			entity.setInOutStockBillNO(handOverBillNo);
			inStorageSerialNoList.add(entity);

			// 设置流水号之交接单编号
			serialNo.setHandOverBillNo(handOverBillEntity.getHandOverBillNo());
			// 设置流水号之交接时间
			serialNo.setHandOverTime(handOverBillEntity.getHandOverTime());
			// 设置流水号之出发部门编码
			serialNo.setOrigOrgCode(handOverBillEntity.getDepartDeptCode());
			// 流水号生成主键
			serialNo.setId(UUIDUtils.getUUID());
			unsavedSerialNoStockList.add(serialNo);
		}
		
		//更新包状态为已生成交接单
		expressPackageDao.updateExpressPackageState(packageNo, ExpressConstants.PACKAGE_STATUS_CREATED_HANDOVER_BILL);
		
		//交接单状态
		handOverBillEntity.setHandOverBillState(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
		//调用dao，传入交接单基本信息、运单列表、流水号列表，分三级insert数据
		handOverBillDao.saveHandOverBill(handOverBillEntity,unSavedWaybillStockList, unsavedSerialNoStockList);
		log.error("交接单号：" + handOverBillNo + "生成结束！");
		
		
		//记录批量出库日志
		log.error("交接单号：" + handOverBillNo + "流水号批量出库开始");
		//批量出库
		//stockService.outStockBatchPC(inStorageSerialNoList);
		
		//按照类型批量出库
		stockService.outStockBatchPCByType(inStorageSerialNoList, StockConstants.OUT_STOCK_TYPE_HANDOVER, handOverBillNo);
		
		log.error("交接单号：" + handOverBillNo + "流水号批量出库结束");
		
		
		log.error("交接单号：" + handOverBillNo + "生成任务车辆开始！");
		truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
		log.error("交接单号：" + handOverBillNo + "生成任务车辆结束！");
		
		//返回前台重新生成的交接单号
		return handOverBillNo;
	}
	
	/**
	 * 校验车牌号和司机的所有权，两者必须一致
	 * @author 045923-foss-shiwei
	 * @date 2013-5-16 下午4:23:10
	 */
	private String validateVehicleAndDriverOwnerShip(String vehicleNo,String driverCode){
		if(StringUtils.isNotBlank(vehicleNo) && StringUtils.isNotBlank(driverCode)){
			VehicleAssociationDto vehicleInfo = vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
			if(vehicleInfo != null){
				String vehicleType = vehicleInfo.getVehicleOwnershipType();
				//如果为外请车
				if(StringUtils.equals(vehicleType, ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)){
					LeasedDriverEntity queryCon = new LeasedDriverEntity();
					queryCon.setIdCard(driverCode);
					LeasedDriverEntity driver = this.leasedDriverService.queryLeasedDriverBySelective(queryCon);
					if(driver == null){
						throw new TfrBusinessException("外请车必须使用外请司机！");
					}
					//如果为公司车
				}else if(StringUtils.equals(vehicleType, ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)){
					DriverAssociationDto driver = this.ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
					if(driver == null){
						throw new TfrBusinessException("公司车必须使用公司司机！");
					}
				}
				return vehicleType;
			}else{
				throw new TfrBusinessException("选择的车辆不存在！");
			}
		}
		return null;
	}

	/**
	 * 查询落地配交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-8-05 上午9:05:08
	 */
	@Override
	public List<LdpHandOverDto> queryLDPHandOverBill(String handOverBillNo,String agencyCode, Date handOverDate) {
		//查询条件
		QueryHandOverBillConditionDto queryDto = new QueryHandOverBillConditionDto();
		//交接单号
		queryDto.setHandOverBillNo(handOverBillNo);
		//到达部门code
		queryDto.setArriveDept(agencyCode);
		if(handOverDate != null){
			//交接时间
			Date startTime = DateUtils.getStartDatetime(handOverDate);
			Date endTime = DateUtils.getStartDatetime(handOverDate, 1);
			queryDto.setBeginHandOverTime(startTime);
			queryDto.setEndHandOverTime(endTime);
		}
		//交接类型
		queryDto.setHandOverType(LoadConstants.HANDOVER_TYPE_LDP);
		List<QueryHandOverBillDto> billList = handOverBillDao.queryHandOverBillListNoPaging(queryDto);
		//构造返回的list
		List<LdpHandOverDto> dtoList = new ArrayList<LdpHandOverDto>();
		for(QueryHandOverBillDto dto : billList){
			LdpHandOverDto rDto = new LdpHandOverDto();
			//交接单号
			rDto.setHandoverNo(dto.getHandOverBillNo());
			//落地配公司code
			rDto.setAgentCompanyCode(dto.getArriveDeptCode());
			//落地配公司name
			rDto.setAgentCompanyName(dto.getArriveDept());
			//德邦部门code
			rDto.setDpOrgCode(dto.getDepartDeptCode());
			//德邦部门name
			rDto.setDpOrgName(dto.getDepartDept());
			//司机code
			rDto.setDriverCode(dto.getDriver());
			//司机name
			rDto.setDriverName(dto.getDriverName());
			//司机电话
			rDto.setDriverPhoneNo(dto.getDriverTel());
			//件数
			rDto.setGoodsQTYTotal(dto.getGoodsQtyTotal());
			//装车完成时间
			rDto.setLoadFinishTime(dto.getLoadEndTime());
			//修改时间
			rDto.setModifyTime(dto.getModifyDate());
			//备注
			rDto.setNotes(dto.getNote());
			//创建人code
			rDto.setUserCode(dto.getCreateUserCode());
			//创建人姓名
			rDto.setUserName(dto.getCreateUserName());
			//车牌号
			rDto.setVehicleNo(dto.getVehicleNo());
			//总体积
			rDto.setVolumeTotal(dto.getVolumeTotal());
			//总票数
			rDto.setWaybillQTYTotal(dto.getWaybillQtyTotal());
			//总重量
			rDto.setWeightTotal(dto.getWeightTotal());
			
			List<HandOverBillDetailEntity> detailList = handOverBillDao.queryHandOverBillDetailByNo(dto.getHandOverBillNo(), null);
			//构造返回的明细List
			List<LdpHandOverDetailDto> rdDtoList = new ArrayList<LdpHandOverDetailDto>();
			for(HandOverBillDetailEntity dDto : detailList){
				LdpHandOverDetailDto rdDto = new LdpHandOverDetailDto();
				//提货网点code
				rdDto.setAgentOrgCode(dDto.getPkpOrgCode());
				//提货网点name
				rdDto.setAgentOrgName(dDto.getPkpOrgName());
				//声明价值
				rdDto.setDeclarationValue(dDto.getInsuranceValue());
				//货物品名
				rdDto.setGoodsName(dDto.getGoodsName());
				//件数
				rdDto.setGoodsQTY(dDto.getPieces().intValue());
				//货物种类
				rdDto.setGoodsType(dDto.getGoodsType());
				//交接时间
				rdDto.setHandoverTime(dto.getHandOverTime());
				//备注
				rdDto.setNotes(dDto.getNote());
				//体积
				rdDto.setVolume(dDto.getCubage());
				//运单号
				rdDto.setWaybillNo(dDto.getWaybillNo());
				//重量
				rdDto.setWeight(dDto.getWeight());
				rdDtoList.add(rdDto);
			}
			
			rDto.setDetails(rdDtoList);
			dtoList.add(rDto);
		}
		return dtoList;
	}
	/**
	 * 查询快递转换体积参数
	 * @author 105869
	 * @return Bigdecimal
	 * @date 2014年7月26日 16:57:45
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService#queryExpressConverParameter()
	 */
	public BigDecimal queryExpressConverParameter(String orgCode){
		
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter=BigDecimal.ZERO;
		String  stringValue = "";
		try{
			if(StringUtils.isNotEmpty(orgCode)){
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
			}

		}catch(Exception e){
			throw new TfrBusinessException("调综合接口根据外场编码来查询重泡比参数异常"+e.toString());
		}

		if(stringValue!=null && StringUtils.isNotEmpty(stringValue)){
			double doubleValue = Double.valueOf(stringValue.toString());
			converParameter = new BigDecimal(doubleValue);
			BigDecimal a =new BigDecimal("1.000");
			//重泡比为重量体积转换参数分之一
			 converParameter = a.divide(converParameter,LoadConstants.SONAR_NUMBER_3);
			 return converParameter;
		}else{
			ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
					ConfigurationParamsConstants.TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS, 
					"DIP");
			if(paramEntity!=null){
				String value=paramEntity.getConfValue();
				try {
					double dvalue = Double.parseDouble(value);
					converParameter = new BigDecimal(dvalue);
				} catch (Exception e) {
					throw new TfrBusinessException("快递转换体积参数转换错误："+e.toString());
				}
				return converParameter;
				
			}else{
				throw new TfrBusinessException("请配置快递转换体积参数！");
			}
		}

	} 
}
