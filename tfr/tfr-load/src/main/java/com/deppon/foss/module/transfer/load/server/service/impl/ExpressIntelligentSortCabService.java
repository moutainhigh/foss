package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressIntelligentSortCabDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPrintPackageLabelDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressThroughPackagePathService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.PackagePrintLogDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IExpressIntelligentSortCabService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.ExpressIntelligentSortDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestIntelligentSortParam;
import com.deppon.foss.module.transfer.pda.api.shared.domain.ExpressPathDetailEntiy;
import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestPacakgeNoParam;
import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestPathDetailParam;
import com.deppon.foss.module.transfer.pda.api.shared.domain.ResponseParameter;
import com.deppon.foss.module.transfer.pda.api.shared.domain.WaybillPathDetailEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
* @description 这里用一句话描述这个类的作用
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年4月7日 下午5:17:26
*/

public class ExpressIntelligentSortCabService implements IExpressIntelligentSortCabService{
	static final Logger LOGGER = LoggerFactory.getLogger(ExpressIntelligentSortCabService.class);
	private ITfrCommonService tfrCommonService;//生成包编号序列号的service
	
	private IPrintPackageLabelDao printPackageLabelDao;//打印服务
	
	private IPDAExpressPackageDao pdaExpressPackageDao;//生成包服务
	
	private IPDACommonService pdaCommonService;//查询部门服务
	
	private IEmployeeService employeeService;//人员服务
	
	private IPDALoadDao pdaLoadDao;//装车服务
	
	private IPDASortingDao pdaSortingDao;//分拣服务接口
	
	private IExpressThroughPackagePathService expressThroughPackagePathService;//生成包走货路由服务
	private IExpressIntelligentSortCabDao expressIntelligentSortCabDao;//分拣柜服务接口
	
	
	private IWaybillManagerService waybillManagerService;
	

	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	
	public void setExpressThroughPackagePathService(
			IExpressThroughPackagePathService expressThroughPackagePathService) {
		this.expressThroughPackagePathService = expressThroughPackagePathService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	

	public void setPrintPackageLabelDao(IPrintPackageLabelDao printPackageLabelDao) {
		this.printPackageLabelDao = printPackageLabelDao;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setPdaExpressPackageDao(IPDAExpressPackageDao pdaExpressPackageDao) {
		this.pdaExpressPackageDao = pdaExpressPackageDao;
	}
	
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public void setExpressIntelligentSortCabDao(
			IExpressIntelligentSortCabDao expressIntelligentSortCabDao) {
		this.expressIntelligentSortCabDao = expressIntelligentSortCabDao;
	}

	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	
	
	public void setPdaSortingDao(IPDASortingDao pdaSortingDao) {
		this.pdaSortingDao = pdaSortingDao;
	}

	/**
	* @description 智能分拣柜打印标签需要的包号接口
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressIntelligentSortCabService#makePacakgeNo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	* @author 105869-foss-heyongdong
	* @update 2015年4月11日 上午10:14:38
	* @version V1.0
	*/
	@Transactional
	@Override
	public @ResponseBody ResponseParameter makePacakgeNo(@RequestBody RequestPacakgeNoParam requestParam) {
		
		//返回实体
		ResponseParameter response = new ResponseParameter();
		//获取传入参数
		String userName = requestParam.getUserName();
		String userCode = requestParam.getUserCode();
		String deptName = requestParam.getDeptName();
		String deptCode = requestParam.getDeptCode();
		int printTimes = requestParam.getPrintTimes();
		LOGGER.error("==============开始  智能分拣柜获取包号，获取人:"+userName+"获取人工号："+userCode+"获取人部门："+deptName+"================");
		try{
		List<String> pacakgeNos =new ArrayList<String>();
		if(printTimes<=0){
			response.setBeSuccess(false);
			response.setReturnType("makePacakgeNo");
			response.setFailureReason("传入打印次数为空！");
			return response;
		}
		for(int i=0;i<printTimes;i++){
			String packageNo=tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.BH,"");
			if(!StringUtils.isBlank(packageNo)&&packageNo.length()==LoadConstants.SONAR_NUMBER_13){
				int length=packageNo.length();
				packageNo="B"+packageNo.substring(2, length);
			}
			if(!StringUtils.isBlank(packageNo)){
				PackagePrintLogDto dto=new PackagePrintLogDto();
				dto.setId(UUIDUtils.getUUID());//ID
				dto.setDeptCode(deptCode);//登录部门编码
				dto.setDeptName(deptName);//登录部门名称
				dto.setPackageNo(packageNo);//包号
				dto.setPrintTime(new Date());
				dto.setEmpCode(userCode);//登录人编码
				dto.setPrintPersonCode(userCode);//打打印人编码
				printPackageLabelDao.addPackagePrintLog(dto);
				pacakgeNos.add(packageNo);
			}
			
		}
		response.setBeSuccess(true);
		response.setReturnType("makePacakgeNo");
		response.setReturnEntity(pacakgeNos);
		LOGGER.error("==============结束  智能分拣柜获取包号，获取人:"+userName+"获取人工号："+userCode+"获取人部门："+deptName+"================");
		}catch(Exception e){
			LOGGER.error("==============异常  智能分拣柜获取包号，获取人:"+userName+"获取人工号："+userCode+"获取人部门："+deptName+"================");
			response.setBeSuccess(false);
			response.setReturnType("makePacakgeNo");
			response.setFailureReason(e.getMessage());
			e.printStackTrace();
			return response;
		}
		return response;
	}

	
	/**
	* @description 智能分拣柜建包
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressIntelligentSortCabService#createPacakge(com.deppon.foss.module.transfer.load.api.shared.domain.RequestIntelligentSortParam)
	* @author 105869-foss-heyongdong
	* @update 2015年4月11日 上午10:16:04
	* @version V1.0
	*/
	@Transactional
	@Override
	public @ResponseBody ResponseParameter createPacakge(@RequestBody RequestIntelligentSortParam entity) {
		
		
		
		//返回s
		ResponseParameter response =new ResponseParameter();
		try{
			if(entity==null){
				throw new TfrBusinessException("传递包信息为空!");
			}
			String pacakgeNo = entity.getPackageNo();
			
			LOGGER.error("==============开始  智能分拣柜建包，建包包号："+pacakgeNo+"================");
			
			if(StringUtil.isEmpty(pacakgeNo)){
				throw new TfrBusinessException("包号不能为空!");
			}
			
			String orgCode = entity.getOrgCode();
			if(StringUtil.isEmpty(orgCode)){
				throw new TfrBusinessException("出发部门不能为空!");
			}
			String destOrgCode = entity.getDestDeptCode();
			if(StringUtil.isEmpty(destOrgCode)){
				throw new TfrBusinessException("到达部门不能为空!");
			}
			
			String createUserCode = entity.getCreateUserCode();
			if(StringUtil.isEmpty(createUserCode)){
				throw new TfrBusinessException("创建人不能为空!");
			}
			ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(pacakgeNo);
			if(expressPackage != null){
				throw new TfrBusinessException("该包号已存在!");
			}
			//增加类型校验
			if(StringUtil.isEmpty(entity.getPkgType()))
			{
				throw new TfrBusinessException("快递包类型为空");
			}
			List<ExpressIntelligentSortDetailEntity> waybillInfos = entity.getWaybillInfos();
			if(CollectionUtils.isEmpty(waybillInfos)||waybillInfos.size()<=0){
				throw new TfrBusinessException("传递运单信息为空!");
			}
			//设备号
			String divceNo = waybillInfos.get(0).getDeviceNo();
			//包实体
			expressPackage = new ExpressPackageEntity();
			//查询到达部门
			OrgAdministrativeInfoEntity arriveOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(destOrgCode);
			if(arriveOrg == null){
				throw new TfrBusinessException("到达部门不存在!");
			}else{
				expressPackage.setArriveOrgCode(arriveOrg.getCode());
				expressPackage.setArriveOrgName(arriveOrg.getName());
			}
			//获取出发部门
			OrgAdministrativeInfoEntity origOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(orgCode);
			if(origOrg == null){
				throw new TfrBusinessException("出发部门不存在!");
			}else{
				expressPackage.setDepartOrgCode(origOrg.getCode());
				expressPackage.setDepartOrgName(origOrg.getName());
			}
			if(arriveOrg.getCode().equals(origOrg.getCode())){
				throw new TfrBusinessException("出发部门和到达部门不能为同一部门");
			}
			//查询建包人
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(createUserCode);
			expressPackage.setCreateUserCode(createUserCode);
			if(emp == null){
				throw new TfrBusinessException("创建人不存在!");
			}else{
				expressPackage.setCreateUserName(emp.getEmpName());
			}
			//设置主键id
			expressPackage.setId(UUIDUtils.getUUID());
			//设置包号
			expressPackage.setPackageNo(pacakgeNo);
			//设置创建时间
			expressPackage.setCreateTime(entity.getCreateTime());
			//包状态
			expressPackage.setStatus(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_FINISHED);
			//建包结束时间
			expressPackage.setEndTime(entity.getFinishTime());
			//运单set 集合
			Set<String> waybillNos = new HashSet<String>();
			//总件数
			BigDecimal totalGoodsQty = new BigDecimal(0);
			//总体积
			BigDecimal totalVolume = new BigDecimal(0);
			//总重量
			BigDecimal totalWeight = new BigDecimal(0);
			//总票数
			BigDecimal totalwaybillQty = new BigDecimal(0);
			//包明细集合
			//List<ExpressPackageDetailEntity> pacakgeDetail = new ArrayList<ExpressPackageDetailEntity>();
			
			for(ExpressIntelligentSortDetailEntity  waybillentity : waybillInfos){
				
				String waybillNo = waybillentity.getWaybillNo();//运单号
				
				String serialNo = waybillentity.getSerialNo();//流水号为空
				if(StringUtil.isBlank(serialNo)){
					throw new TfrBusinessException("运单号："+waybillNo+"的流水号为空！");
				}
				WaybillEntity waybillInfo =waybillManagerService.queryWaybillBasicByNo(waybillNo);
				
				
				if(waybillInfo!=null){
					//货物体积
					BigDecimal goodsVolumetotal = waybillInfo.getGoodsVolumeTotal()==null ? new BigDecimal(0):waybillInfo.getGoodsVolumeTotal();
					//货物重量
					BigDecimal goodsWeighttotal = waybillInfo.getGoodsWeightTotal()==null ? new BigDecimal(0):waybillInfo.getGoodsWeightTotal();
					//开单件数
					BigDecimal goodsQtytotal = waybillInfo.getGoodsQtyTotal()==null ? new BigDecimal(1):new BigDecimal(waybillInfo.getGoodsQtyTotal());
					
					//每件货物的重量体积
					BigDecimal goodsVolume = goodsVolumetotal.divide(goodsQtytotal, 2, BigDecimal.ROUND_HALF_UP);
					BigDecimal goodsWeight = goodsWeighttotal.divide(goodsQtytotal, 2, BigDecimal.ROUND_HALF_UP);
					if(!waybillNos.contains(waybillNo)){//不存在集合中则放入运单集合 
						waybillNos.add(waybillNo);
					}
					ExpressPackageDetailEntity goods = new ExpressPackageDetailEntity();
					goods.setCreateTime(new Date());
					goods.setDeviceNo(waybillentity.getDeviceNo()==null?"":waybillentity.getDeviceNo());//设备号
					goods.setGoodsName(waybillInfo.getGoodsName());//货物名称
					goods.setGoodsQty(waybillInfo.getGoodsQtyTotal());//开单件数
					goods.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);//扫描状态  待办
					goods.setId(UUIDUtils.getUUID());
					goods.setNotes("智能分拣柜建包");//备注
					goods.setPackageNo(pacakgeNo);//包号
					goods.setPack(waybillInfo.getGoodsPackage());//货物包装
					goods.setReachOrgName(waybillInfo.getCustomerPickupOrgName());//到达部门
					goods.setRecieveOrgName(waybillInfo.getReceiveOrgName());//收货部门
					goods.setScanState(TransferPDADictConstants.SCAN_STATE_SCANED);//扫描状态
					goods.setScanTime(waybillentity.getScanTime()==null?new Date():waybillentity.getScanTime());//扫描时间
					goods.setSerialNo(waybillentity.getSerialNo());//流水号
					goods.setTransportTypeCode(waybillInfo.getProductCode());//运输编码
					
					String transportName = expressIntelligentSortCabDao.queryWaybillTransportName(waybillInfo.getProductCode());
					goods.setTransportTypeName(transportName==null?"快递":transportName);//运输性质
					goods.setVolume(goodsVolume.doubleValue());//货物体积
					goods.setWayBillNo(waybillNo);//运单号
					goods.setWeight(goodsWeight.doubleValue());//货物重量
					
					//计算总件数，体积，重量
					totalGoodsQty=totalGoodsQty.add(new BigDecimal(1));
					totalVolume=totalVolume.add(goodsVolume);
					totalWeight=totalWeight.add(goodsWeight);
					pdaExpressPackageDao.insertExpressPackageDetail(goods);
					//添加明细中
					//pacakgeDetail.add(goods);
					
					//插入分拣扫描
					SortingScanEntity sortingScanEntity = new SortingScanEntity();
					sortingScanEntity.setDeviceNo(waybillentity.getDeviceNo()==null?"智能分拣柜":"waybillentity.getDeviceNo()");
					sortingScanEntity.setOperatorCode(createUserCode);
					sortingScanEntity.setOperatorName(emp.getEmpName());
					sortingScanEntity.setOrgName(origOrg.getName());
					sortingScanEntity.setOrgCode(origOrg.getCode());
					sortingScanEntity.setScanType(TransferPDADictConstants.SORTING_SCAN_TYPE_UP);
					sortingScanEntity.setScanTime(waybillentity.getScanTime()==null?new Date():waybillentity.getScanTime());
					sortingScanEntity.setSerialNo(waybillentity.getSerialNo());
					sortingScanEntity.setWayBillNo(waybillNo);
					sortingScanEntity.setCreateTime(new Date());
					sortingScanEntity.setId(UUIDUtils.getUUID());
					sortingScanEntity.setScanMode(UnloadConstants.SORT_SCAN_MODE_PDA);
					pdaSortingDao.insertSortingScan(sortingScanEntity);
				}
				
			}
			//总票数
			totalwaybillQty=new BigDecimal(waybillNos.size());
			expressPackage.setWaybillQty(totalwaybillQty);
			//总件数，体积，重量
			expressPackage.setGoodsQty(totalGoodsQty);
			expressPackage.setVolume(totalVolume);
			expressPackage.setWeight(totalWeight);
			//是否直达包
			String beThroughArrive=entity.getPkgType();
			if(StringUtil.equals("STRAIGHT_PACKAGE", beThroughArrive)){
				expressPackage.setExpressPackageType(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_THROUGH_ARRIVE);
			}else{
				expressPackage.setExpressPackageType(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_NORMAL_ARRIVE);
			}
			
			//插入tfr.t_opt_pda_task
			PDATaskEntity pdaEntity = new PDATaskEntity();
			pdaEntity.setTaskNo(entity.getPackageNo());
			pdaEntity.setDeviceNo(divceNo);
			pdaEntity.setId(UUIDUtils.getUUID());
			pdaEntity.setJoinTime(new Date());
			pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_PACKAGE);
			pdaEntity.setBeCreator(FossConstants.YES);
			LOGGER.error("插入快递包PDA"+pacakgeNo);
			pdaLoadDao.insertPDATask(pdaEntity);
			LOGGER.error("插入快递包"+pacakgeNo);
			//再次校验该包是否已存在
			int pcount = pdaExpressPackageDao.queryPackageCountByNo(pacakgeNo);
			if(pcount >= 1){
				throw new TfrBusinessException("该包号已存在!");
			}
			//建包
			pdaExpressPackageDao.insertExpressPackage(expressPackage);
			if(StringUtil.equals("STRAIGHT_PACKAGE", beThroughArrive)){
				LOGGER.error("新建快递直达包："+expressPackage.getPackageNo()+"走货路由开始！");
				expressThroughPackagePathService.createThroughPackagePath(pacakgeNo);
				LOGGER.error("新建快递直达包："+expressPackage.getPackageNo()+"走货路由结束！");
			}
			LOGGER.error("==============结束  智能分拣柜建包，建包包号："+pacakgeNo+"================");
	   }catch(Exception e){
		   LOGGER.error("==============异常  智能分拣柜建包，建包包号："+entity==null?"":entity.getPackageNo()+"================");
		   response.setBeSuccess(false);
		   response.setReturnType("createPacakge");
		   response.setFailureReason(e.getMessage());
		   e.printStackTrace();
		   return response;
	   }
		response.setBeSuccess(true);
		response.setReturnType("createPacakge");
		return response;
	}

	

	
	/**
	* @description 拉取走货路由
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressIntelligentSortCabService#queryWaybillPath()
	* @author 105869-foss-heyongdong
	* @update 2015年4月11日 上午10:16:47
	* @version V1.0
	*/
	@Override
	public @ResponseBody ResponseParameter queryWaybillPath(@RequestBody RequestPathDetailParam requestParam) {
		String depetCode = requestParam.getDepetCode();
		Date startTime = requestParam.getStartTime();
		Date endTime = requestParam.getEndTime();
		String strStartTime = DateUtils.convert(startTime==null?new Date():startTime, DateUtils.DATE_TIME_FORMAT) ;
		String strendTime = DateUtils.convert(endTime==null?new Date():endTime, DateUtils.DATE_TIME_FORMAT) ;
		LOGGER.error("==============开始    智能分拣柜拉取走货路由 ，拉取部门："+depetCode+"拉取起始时间："+strStartTime+"拉取截止时间："+strendTime+"=====================");
		ResponseParameter response = new ResponseParameter();
		try{
			
			if(depetCode==null){
				throw new TfrBusinessException("传入参数部门为空！");
			}
			
			if(startTime==null || endTime==null){
				throw new TfrBusinessException("传入参数起始截止时间为空！");
			}
			//int weightLimit = this.getWaybillWeightLimit();
			//int totalCounts = expressIntelligentSortCabDao.queryWaybillPathDetailCount( depetCode, startTime,endTime);
			int weightLimit = this.getWaybillWeightLimit();
			int totalCounts = expressIntelligentSortCabDao.queryWaybillPathDetailCount( depetCode, startTime,endTime,weightLimit);
			if(totalCounts>=LoadConstants.MAX_TOTAL_NUM){
				throw new TfrBusinessException("查询的数据量太大，请缩小查询时间段！");
			}
			//TODO 查询传递过来部门的顶级外场
			//根据车辆  到达部门 和 出发的起始截止时间 拉取 在途中 运单的走货路由 
			List<PathDetailEntity> pathDetails = expressIntelligentSortCabDao.queryWaybillPathDetail( depetCode, startTime,endTime,weightLimit);
			//运单走货路由集合
			List<WaybillPathDetailEntity> waybillPathDetails = new ArrayList<WaybillPathDetailEntity>();
			//路由明细
			List<ExpressPathDetailEntiy> expressPathdetails = new ArrayList<ExpressPathDetailEntiy>();
			//
			if(CollectionUtils.isEmpty(pathDetails) || pathDetails.size()<=0){
				response.setBeSuccess(false);
				response.setReturnType("queryWaybillPath");
				response.setFailureReason("该时间段内无运单路由信息！");
				return response;
			}
			String waybillNo = pathDetails.get(0).getWaybillNo();
			String goodsNo = pathDetails.get(0).getGoodsNo();
			float weight = pathDetails.get(0).getWeight()==null?0:pathDetails.get(0).getWeight().floatValue();
			float volumn = pathDetails.get(0).getVolumn()==null?0:pathDetails.get(0).getVolumn().floatValue();
			for(PathDetailEntity temp :pathDetails){
				
				//如果运单号 流水号相同 则 组合其 走货路由
				if(StringUtil.equals(waybillNo, temp.getWaybillNo()) && StringUtil.equals(goodsNo, temp.getGoodsNo())){
					ExpressPathDetailEntiy expressPathdetail = new ExpressPathDetailEntiy();
					expressPathdetail.setRouteNo(temp.getRouteNo());
					expressPathdetail.setOrigOrgCode(temp.getOrigOrgCode());
					expressPathdetail.setDestOrgCode(temp.getObjectiveOrgCode());
					//添加流水号路由明细
					expressPathdetails.add(expressPathdetail);
				}else{
					WaybillPathDetailEntity waybillPathDetail = new WaybillPathDetailEntity();
					waybillPathDetail.setWaybillNo(waybillNo);
					waybillPathDetail.setSerialNo(goodsNo);
					waybillPathDetail.setWeight(weight);
					waybillPathDetail.setSize(volumn);
					waybillPathDetail.setPathdetails(expressPathdetails);
					//添加路由到返回结果集中
					waybillPathDetails.add(waybillPathDetail);
					//初始化参数
					expressPathdetails = new ArrayList<ExpressPathDetailEntiy>();
					ExpressPathDetailEntiy expressPathdetail = new ExpressPathDetailEntiy();
					expressPathdetail.setRouteNo(temp.getRouteNo());
					expressPathdetail.setOrigOrgCode(temp.getOrigOrgCode());
					expressPathdetail.setDestOrgCode(temp.getObjectiveOrgCode());
					//添加流水号路由明细
					expressPathdetails.add(expressPathdetail);
					waybillNo = temp.getWaybillNo();
					goodsNo = temp.getGoodsNo();
					weight = temp.getWeight()==null?0:temp.getWeight().floatValue();
					volumn = temp.getVolumn()==null?0:temp.getVolumn().floatValue();
				}
			}
			//最后一个运单
			if(expressPathdetails!=null && expressPathdetails.size()>0){
				WaybillPathDetailEntity waybillPathDetail = new WaybillPathDetailEntity();
				waybillPathDetail.setWaybillNo(waybillNo);
				waybillPathDetail.setSerialNo(goodsNo);
				waybillPathDetail.setWeight(weight);
				waybillPathDetail.setSize(volumn);
				waybillPathDetail.setPathdetails(expressPathdetails);
				//添加
				waybillPathDetails.add(waybillPathDetail);
			}
			
			response.setBeSuccess(true);
			response.setReturnType("queryWaybillPath");
			response.setReturnEntity(waybillPathDetails);
			LOGGER.error("==============结束   智能分拣柜拉取走货路由 ，拉取部门："+depetCode+"拉取起始时间："+strStartTime+"拉取截止时间："+strendTime+"=====================");
		}catch(Exception e){
			LOGGER.error("==============异常   智能分拣柜拉取走货路由 ，拉取部门："+depetCode+"拉取起始时间："+strStartTime+"拉取截止时间："+strendTime+"=====================");
			response.setBeSuccess(false);
			response.setReturnType("queryWaybillPath");
			response.setFailureReason(e.getMessage());
			e.printStackTrace();
			return response;
		}
		return response;
	}
	
//	public int getWaybillWeightLimit() {
//		ConfigurationParamsEntity paramEntity;
//		int weightLimit =4;
//		//获取最大线程数
//		try {
//			paramEntity = configurationParamsService
//					.queryConfigurationParamsByOrgCode(
//							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
//							ConfigurationParamsConstants.TFR_PARM_BCM_QUERY_WAYBILL_WEIGHT_LIMIT,
//							FossConstants.ROOT_ORG_CODE);
//			if(paramEntity!= null){
//				String value = paramEntity.getConfValue();
//				//从数据字典获取线程数
//				weightLimit = Integer.parseInt(value);
//			}
//			
//		} catch (Exception e) {
//			LOGGER.info("从数据字典获取卸车入库线程数异常！"+e.toString());
//		}
//		return weightLimit;
//	}
public int getWaybillWeightLimit() {
		ConfigurationParamsEntity paramEntity;
		int weightLimit =LoadConstants.SONAR_NUMBER_4;
		//获取最大线程数
		try {
			paramEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM_BCM_QUERY_WAYBILL_WEIGHT_LIMIT,
							FossConstants.ROOT_ORG_CODE);
			if(paramEntity!= null){
				String value = paramEntity.getConfValue();
				//从数据字典获取线程数
				weightLimit = Integer.parseInt(value);
			}
			
		} catch (Exception e) {
			LOGGER.info("从数据字典获取卸车入库线程数异常！"+e.toString());
		}
		return weightLimit;
	}

}
