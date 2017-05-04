package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.transfer.common.api.shared.define.ExpressConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSaveConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSerialNoStockDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: ExpressPackageService
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包Service
 * @date: 2013-7-17 下午5:24:31
 * 
 */
public class ExpressPackageService implements IExpressPackageService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * PC端新增快递包，设备号 统一为PC
	 */
	private static final String DEVICENOBYPC = "PC"; 
	
	/**
	 * PC端模拟扫描状态
	 */
	private static final String SCANSTATE = "SCANED";
	
	/**
	 * 包状态
	 */
	private static final String PACKAGESTATE_FINISHED = "FINISHED";
	/**
	 * 货物类型
	 */
	private static final String GOODSSTATE = "NORMAL";
	
	/**
	 * 保留两位小数
	 */
	private static final int two = 2;
	/**
	 * 保留三位小数
	 */
	private static final int three = 3;
	
	/**
	 * 本模块Dao
	 */
	private IExpressPackageDao expressPackageDao;
	
	/**
	 * 装车共用service
	 */
	private ILoadService loadService;
	
	/**
	 * 系统配置参数   (PC是否忽略走货路径)
	 */
//	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * pda包服务，用于获取包内流水号
	 */
	private IPDAExpressPackageDao pdaExpressPackageDao;
	
	/**
	 * 库存服务，用于修改库存流水号的建包状态
	 */
	private IStockService stockService;
	/**
	 * 综合线路service，根据营业部码获取其关联的外场
	 */
	private ILineService lineService;
	private IExpressLineService expresslineService ;
	
	
	  //产品接口
	@Resource
    private IProductService  productService4Dubbo;
    
    

/*	public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/

	/*public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}*/

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setPdaExpressPackageDao(IPDAExpressPackageDao pdaExpressPackageDao) {
		this.pdaExpressPackageDao = pdaExpressPackageDao;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setExpressPackageDao(IExpressPackageDao expressPackageDao) {
		this.expressPackageDao = expressPackageDao;
	}

	/**
	 * 查询包
	 * @author 045923-foss-shiwei
	 * @date 2013-7-17 下午5:25:24
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService#queryExpressPackageList(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity, int, int)
	 */
	@Override
	public List<ExpressPackageEntity> queryExpressPackageList(ExpressPackageQueryDto entity, int start, int limit) {
		if(null==entity){
			return null;
		}
		//获取当前大部门code
		String superOrgCode = this.querySuperiorOrgCode();
		entity.setDepartOrgCode(superOrgCode);
		return expressPackageDao.queryExpressPackageList(entity, start, limit);
	}

	/**
	 * 获取包总量
	 * @author 045923-foss-shiwei
	 * @date 2013-7-17 下午5:25:42
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService#queryExpressPackageCount(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity)
	 */
	@Override
	public Long queryExpressPackageCount(ExpressPackageQueryDto entity) {
		if (entity == null) {
			return null;
		}
		//获取当前大部门code
		String superOrgCode = this.querySuperiorOrgCode();
		entity.setDepartOrgCode(superOrgCode);
		return expressPackageDao.queryExpressPackageCount(entity);
	}

	/**
	 * 更新包状态
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 上午9:16:00
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService#updateExpressPackageState(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateExpressPackageState(String packageNo, String targetState) {
		return expressPackageDao.updateExpressPackageState(packageNo, targetState);
	}

	/**
	 * PC界面，批量撤销包
	 * @author 045923-foss-shiwei
	 * @date 2013-7-25 下午2:16:04
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService#cancelExpressPackage(java.util.List)
	 */
	@Override
	@Transactional
	public int cancelExpressPackage(String packageNo) {
		if(StringUtils.isBlank(packageNo)){
			return FossConstants.FAILURE;
		}
		
		//已撤销的不允许重复撤销，已生成交接单的不允许撤销
		ExpressPackageEntity ePackage = expressPackageDao.queryExpressPackageByPackageNo(packageNo);
		if(StringUtils.equals(ePackage.getStatus(), ExpressConstants.PACKAGE_STATUS_ALREADY_CANCLED)){
			throw new TfrBusinessException("已经被撤销的包无法再次撤销，包号：" + packageNo + "。");
		}
		if(StringUtils.equals(ePackage.getStatus(), ExpressConstants.PACKAGE_STATUS_CREATED_HANDOVER_BILL)){
			throw new TfrBusinessException("已经生成交接单的包无法被撤销，包号：" + packageNo + "。");
		}
		//更新包状态为已撤销
		this.updateExpressPackageState(packageNo, ExpressConstants.PACKAGE_STATUS_ALREADY_CANCLED);
		//获取包内流水号，修改流水号库存的建包状态	
		List<ExpressPackageDetailEntity> detailList = pdaExpressPackageDao.queryScanPackageDetails(packageNo);
		for(ExpressPackageDetailEntity detail : detailList){
			stockService.updateIsPackage(detail.getWayBillNo(), detail.getSerialNo(), ePackage.getDepartOrgCode());
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * private method 获取大部门orgCode
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 上午8:06:48
	 */
	private String querySuperiorOrgCode(){
		//获取当前大部门code
		OrgAdministrativeInfoEntity org = loadService.querySuperiorOrgByOrgCode(FossUserContext.getCurrentDeptCode());
		if(org == null){
			return null;
		}
		return org.getCode();
	}

	/**
	 * 根据包号获取包信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 上午8:06:48
	 */
	@Override
	public ExpressPackageEntity queryExpressPackageByPackageNo(String packageNo) {
		if(StringUtils.isBlank(packageNo)){
			return null;
		}
		return expressPackageDao.queryExpressPackageByPackageNo(packageNo);
	}
	/**
	 * 更新包目的站
	 * @author 105869
	 * @2014年7月7日 13:38:05
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService#modifyPackageInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public int modifyPackageInfo(String packageNo, String destOrgName, String destOrgCode) {
		if(StringUtils.isEmpty(destOrgCode)){
			throw new TfrBusinessException("请输入目的站！");
		}
		//根据包号查询包基本信息
		ExpressPackageEntity expPackage= expressPackageDao.queryExpressPackageByPackageNo(packageNo);
		if(expPackage==null){
			throw new TfrBusinessException("该包不存在！");
		}
		if(StringUtils.endsWith(ExpressConstants.PACKAGE_STATUS_FINISHED, expPackage.getStatus())){
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			String orgCode = expPackage.getDepartOrgCode();
			LineEntity lineEntity = new LineEntity();
			lineEntity.setOrginalOrganizationCode(orgCode);
			lineEntity.setDestinationOrganizationCode(destOrgCode);
			lineEntity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			
			ExpressLineEntity explineEntity = new ExpressLineEntity();
			explineEntity.setOrginalOrganizationCode(orgCode);
			explineEntity.setDestinationOrganizationCode(destOrgCode);
			explineEntity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			//判断是否存在该始发线路
			List<LineEntity> lines =lineService.querySimpleLineListByCondition(lineEntity);
			if(!(CollectionUtils.isNotEmpty(lines)&&lines.size()>0)){
				//判断快递是否存在该始发线路
				List<ExpressLineEntity> explines = expresslineService.querySimpleLineListByCondition(explineEntity);
				if(!(CollectionUtils.isNotEmpty(explines)&&explines.size()>0)){
					throw new TfrBusinessException("不存在："+expPackage.getDepartOrgName()+"-"+destOrgName+"的始发线路！");
				}
			}
			//需要跟新的包实体
			ExpressPackageEntity updateEntity = new ExpressPackageEntity();
			updateEntity.setPackageNo(packageNo);
			updateEntity.setArriveOrgName(destOrgName);
			updateEntity.setArriveOrgCode(destOrgCode);
			updateEntity.setModifyTime(new Date());
			updateEntity.setModifyUserName(currentInfo.getEmpName());
			updateEntity.setModifyUserCode(currentInfo.getEmpCode());
			
			expressPackageDao.updatePackageInfo(updateEntity);
		}else{
			throw new TfrBusinessException("包已撤销或已生成交接单不能修改到达部门！");
		}
		return 0;
	}
	
	/**
	 * 包新增界面，快速添加通过运单号获取库存运单信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-20
	 * @param queyWaybillForExpressPackageDto
	 * @return
	 */
	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> queryWaybillStockByWaybillNo(QueryWaybillForExpressPackageConditionDto queyWaybillForExpressPackageDto){
		//定义返回值
		Map map = new HashMap<String,Object>();
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		queyWaybillForExpressPackageDto.setCurrentDeptCode(currentDeptCode);
		
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queyWaybillForExpressPackageDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		
		//获取运单库存
		List<ExpressPackageWayBillDetailDto> waybillStockList = new ArrayList<ExpressPackageWayBillDetailDto>();
		waybillStockList = expressPackageDao.queryWaybillStockByWaybillNo(queyWaybillForExpressPackageDto);
				
		//是否为修改交接单界面快速添加
		if(CollectionUtils.isEmpty(waybillStockList)){
			throw new TfrBusinessException("该运单在本部门没有库存或与交接单“到达部门”不匹配！");
		}
		ExpressPackageWayBillDetailDto waybillStock = waybillStockList.get(0);
		
		map.put("waybillStock",waybillStock);
		//根据运单号获取流水号
		QuerySerialNoListForWaybillConditionDto querySerialNoDto = new QuerySerialNoListForWaybillConditionDto();
		//运单号
		querySerialNoDto.setWaybillNo(waybillStock.getWaybillNo());
		//下一部门
		List<String> arriveDeptList = new ArrayList<String>();
		arriveDeptList = queyWaybillForExpressPackageDto.getArriveDeptList();//获得下一部门
		querySerialNoDto.setNextDeptCodeList(arriveDeptList);
		//是否合车(快递不考虑合车)
	//	querySerialNoDto.setIsJoinCar(waybillStock.getIsJoinCar());
		//是否通过运单号快速添加
	//	querySerialNoDto.setIsQuickAdd(FossConstants.YES);
		List<ExpressPackageSerialNoStockDto> serialNoList = this.querySerialNoStockList(querySerialNoDto);
		
		//判断该运单中的流水号是否已建包，如果全部建包，则不让再建包，如果还有部分没建包，则查询出没建包的流水号
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add(waybillStock.getWaybillNo());
		List<ExpressPackageDetailEntity> packageDetailList = this.queryPackageDetailByWaybills(waybillNoList);
		if(!CollectionUtils.isEmpty(packageDetailList)){
			for(int i =0;i<serialNoList.size();i++){
				for (ExpressPackageDetailEntity expressPackageDetailEntity : packageDetailList) {
					if(StringUtils.equals(serialNoList.get(i).getSerialNo(), expressPackageDetailEntity.getSerialNo())){
						//该流水号已建包，从list中删除
						serialNoList.remove(i);
						break;
					}
				}
			}
		}
		
		if(CollectionUtils.isEmpty(serialNoList)){
			throw new TfrBusinessException("该运单下的所有流水号已被建包！");
		}
		
		map.put("serialNoList",serialNoList);
		return map;
	}
	
	/**
	 * 传入运单号、库存部门，获取流水号list，用于查询包交接运单界面
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-21
	 * @param QuerySerialNoListForWaybillConditionDto
	 * @return
	 */
	@Transactional
	public List<ExpressPackageSerialNoStockDto> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {	
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		//设置当前部门code
		querySerialNoListForWaybillConditionDto.setCurrentDeptCode(currentDeptCode);
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		querySerialNoListForWaybillConditionDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//返回查询结果(快递不考虑合车)
		return expressPackageDao.querySerialNoStockList(querySerialNoListForWaybillConditionDto);
	}
	
	/**
	 * 根据出发部门和到达部门查询是否存在线路
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	@Transactional
	public boolean queryExpressLineIsExist(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto){
		boolean isExistPackageLine = false;
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		//设置当前部门code
		queryWaybillForExpressPackageDto.setCurrentDeptCode(currentDeptCode);
		//查询始发线路 是否存在
		long count = 0L;
		count = expressPackageDao.queryExpressSourceLineByOrgCount(queryWaybillForExpressPackageDto);
		//存在路线
		if(count>0){
			isExistPackageLine = true;
		}
		return isExistPackageLine;
	}
	
	/**
	 * 分页查询库存运单  和流水号
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @param start
	 * @param limit
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService#queryWaybillStockListForPackage(com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto, int, int)
	 */
	@Transactional
	public List<ExpressPackageWayBillDetailDto> queryWaybillStockListForPackage(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto,int start,int limit){
		List<ExpressPackageWayBillDetailDto> waybillStockList = new ArrayList<ExpressPackageWayBillDetailDto>();
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		if(null == queryWaybillForExpressPackageDto){
			return null;
		}
		//设置当前部门code
		queryWaybillForExpressPackageDto.setCurrentDeptCode(currentDeptCode);
		//只查询快递的运单
		List<String> transPropertyList = new ArrayList<String>();
		//拿到前台传过来的运输性质
		transPropertyList = queryWaybillForExpressPackageDto.getTransPropertyList();
		if(transPropertyList.size()>0){
			if(StringUtils.equals(transPropertyList.get(0),"ALL")){
				
				/**
				 * 查询所有的快递产品
				 * by wqh
				 * date 2015-08-15
				 * */
				transPropertyList.remove(0);
				transPropertyList=productService4Dubbo.getAllLevels3ExpressProductCode();
				/*transPropertyList.add("PACKAGE");
				transPropertyList.add("RCP");
				transPropertyList.add("EPEP");
				//添加商务专递
				transPropertyList.add(TransferConstants.PRODUCT_EXPRESS_AIR);*/

			}
		}
		//设置运输性质
		queryWaybillForExpressPackageDto.setTransPropertyList(transPropertyList);
		
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queryWaybillForExpressPackageDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//获得库存运单list
		waybillStockList = expressPackageDao.queryWaybillStockListForPackage(queryWaybillForExpressPackageDto,start,limit);
		//获取运单下的库存流水号
		if(waybillStockList.size()>0){
			for (ExpressPackageWayBillDetailDto waybillStock : waybillStockList) {
				//根据运单号获取流水号
				QuerySerialNoListForWaybillConditionDto querySerialNoDto = new QuerySerialNoListForWaybillConditionDto();
				//运单号
				querySerialNoDto.setWaybillNo(waybillStock.getWaybillNo());
				//下一部门
				List<String> arriveDeptList = new ArrayList<String>();
				arriveDeptList = queryWaybillForExpressPackageDto.getArriveDeptList();//获得下一部门
				querySerialNoDto.setNextDeptCodeList(arriveDeptList);
				List<ExpressPackageSerialNoStockDto> serialNoStockList = this.querySerialNoStockList(querySerialNoDto);//获取运单下的库存流水号
				waybillStock.setSerialNoStockList(serialNoStockList);
			}
		}
		if(!CollectionUtils.isEmpty(waybillStockList)){
			//将库存运单中已经建包的流水号设置   isCreatedPackage = 'Y'
			this.checkPackageWaybillIsCreatedPackage(waybillStockList);
		}
		return waybillStockList;
	}
	
	/**
	 * 查询库存运单总数
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	@Transactional
	public Long queryWaybillStockListForPackageCount(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto){
		Long toltalCount = 0L;
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		if (null == queryWaybillForExpressPackageDto) {
			return null;
		}
		//设置当前部门code
		queryWaybillForExpressPackageDto.setCurrentDeptCode(currentDeptCode);
		//只查询快递的运单
		List<String> transPropertyList = new ArrayList<String>();
		//拿到前台传过来的运输性质
		transPropertyList = queryWaybillForExpressPackageDto.getTransPropertyList();
		if(transPropertyList.size()>0){
			if(StringUtils.equals(transPropertyList.get(0),"ALL")){
				/**
				 * 查询所有的快递产品
				 * by wqh
				 * date 2015-08-15
				 * */
				transPropertyList.remove(0);
				transPropertyList=productService4Dubbo.getAllLevels3ExpressProductCode();
				/*transPropertyList.remove(0);
				transPropertyList.add("PACKAGE");
				transPropertyList.add("RCP");
				transPropertyList.add("EPEP");*/
			}
		}
		queryWaybillForExpressPackageDto.setTransPropertyList(transPropertyList);
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queryWaybillForExpressPackageDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		
		toltalCount = expressPackageDao.queryWaybillStockListForPackageCount(queryWaybillForExpressPackageDto);
		return toltalCount;
	}
	
	/**
	 * 查询该包号是否存在
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param packageNo
	 * @return
	 */
	@Transactional
	public boolean queryIsExistPackageNo(String packageNo){
		boolean flag = false;
		ExpressPackageEntity packageEntity = expressPackageDao.queryExpressPackageByPackageNo(packageNo);
		if(packageEntity!=null){
			flag = true;//包号已经存在
		}
		return flag;
	}
	
	/**
	 * PC端新增快递包(类似于扫描所有货物，然后存包)
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param expressPackageSaveConditionDto
	 * @return
	 */
	@Transactional
	@SuppressWarnings("static-access")
	public boolean saveExpressPackage(ExpressPackageSaveConditionDto expressPackageSaveConditionDto){
		//获取运单list和流水号list，前台传过来的数据是有顺序的，在两个list中运单对应了流水号。
		List<ExpressPackageWayBillDetailDto> waybillStockList = expressPackageSaveConditionDto.getWaybillStockList();
		List<ExpressPackageSerialNoStockDto> serialNoStockList = expressPackageSaveConditionDto.getSerialNoStockList();
		ExpressPackageEntity packageEntity = expressPackageSaveConditionDto.getPackageEntity();//拿到包详情
		
		/**
		 * desc 判断是否快递空运 因为商务专递不能与其他产品建包，所以快递空运包中所有的运单均为商务专递
		 * by wqh
		 * 2015-08-15
		 */
		//默认为普通包，在pc端不能建立直达包，只能建普通包一快递空运包
		String packageTpye=TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_NORMAL_ARRIVE;
		if(TransferConstants.PRODUCT_EXPRESS_AIR.equals(waybillStockList.get(0).getTransPropertyCode())){
			packageTpye=TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_KD_AIR;
		}
		
		
		String packageNo = packageEntity.getPackageNo();
		if(packageNo.length() != LoadConstants.SONAR_NUMBER_10 && packageNo.length() != LoadConstants.SONAR_NUMBER_12){
			throw new TfrBusinessException("包号只能是B后面加9位或者11位的数字，请重新输入！");
		}
		//如果已经存在包号
		if(queryIsExistPackageNo(packageNo)){
			throw new TfrBusinessException("包号已经存在，不能再添加！");
		}
		
		int waybillFlag = 0;
		int serialFlag = 0;
		BigDecimal totalWeight = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal totalVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		
		for(;waybillFlag<waybillStockList.size();waybillFlag++){
			//拿到运单Dto
			ExpressPackageWayBillDetailDto waybill = waybillStockList.get(waybillFlag);
			totalWeight = totalWeight.add(waybill.getWeight());
			totalVolume = totalVolume.add(waybill.getCubage());
			//判断包重量超过30KG 抛出异常提示
			if(totalWeight.compareTo(new BigDecimal("30"))>0){
				throw new TfrBusinessException("建包总重量大于30KG，不可添加该件！");
			}
			for(;serialFlag < serialNoStockList.size();){
				//流水号实体
				ExpressPackageSerialNoStockDto serialNo = serialNoStockList.get(serialFlag);
				//如果流水号的运单号和父循环的运单号不相等了，就break
				if(!StringUtils.equals(serialNo.getWaybillNo(), waybill.getWaybillNo())){
					break;
				}
				//包明细
				ExpressPackageDetailEntity goods = new ExpressPackageDetailEntity();
				//构造新增快递包明细实体
				goods.setCreateTime(new Date());
				goods.setDeviceNo(this.DEVICENOBYPC);
				goods.setGoodsName(waybill.getGoodsName());
				goods.setGoodsQty(waybill.getWaybillPieces().intValue());//开单件数
				goods.setGoodsState(waybill.getGoodsType()==null?this.GOODSSTATE:waybill.getGoodsType());//货物类型
				goods.setId(UUIDUtils.getUUID());
				goods.setNotes(waybill.getNote());
				goods.setPackageNo(packageEntity.getPackageNo());//包号
				goods.setPack(waybill.getPacking());
				goods.setReachOrgName(packageEntity.getArriveOrgName());//收货部门名称(到达部门)
				goods.setRecieveOrgName(waybill.getReceiveOrgName());//发货部门名称(出发部门)
				goods.setScanState(this.SCANSTATE);
				goods.setScanTime(new Date());
				goods.setSerialNo(serialNo.getSerialNo());
				goods.setTransportTypeCode(waybill.getTransPropertyCode());
				goods.setTransportTypeName(waybill.getTransProperty());
				goods.setVolume(waybill.getCubage().divide(waybill.getPieces(),three).setScale(two,BigDecimal.ROUND_HALF_DOWN).doubleValue());//保留两位小数
				goods.setWayBillNo(waybill.getWaybillNo());
				goods.setWeight(waybill.getWeight().divide(waybill.getPieces(),three).setScale(two,BigDecimal.ROUND_HALF_DOWN).doubleValue());
			
				
				//构造结束
				//插入包明细
				expressPackageDao.insertExpressPackageDetail(goods);
				
				serialFlag++;
			}
		}
		
		//构造包实体
		ExpressPackageEntity expressPackage = new ExpressPackageEntity();
		expressPackage.setId(UUIDUtils.getUUID());
		expressPackage.setPackageNo(packageEntity.getPackageNo());
		expressPackage.setDepartOrgCode(packageEntity.getDepartOrgCode());
		expressPackage.setDepartOrgName(packageEntity.getDepartOrgName());
		expressPackage.setArriveOrgCode(packageEntity.getArriveOrgCode());
		expressPackage.setArriveOrgName(packageEntity.getArriveOrgName());
		expressPackage.setWeight(totalWeight);
		expressPackage.setVolume(totalVolume);
		expressPackage.setWaybillQty(new BigDecimal(waybillStockList.size()));
		expressPackage.setGoodsQty(new BigDecimal(serialNoStockList.size()));
		expressPackage.setStatus(this.PACKAGESTATE_FINISHED);
		expressPackage.setCreateTime(new Date());
		expressPackage.setCreateUserName(packageEntity.getCreateUserName());
		expressPackage.setCreateUserCode(packageEntity.getCreateUserCode());
		expressPackage.setEndTime(new Date());
		expressPackage.setModifyTime(new Date());
		expressPackage.setModifyUserName(packageEntity.getCreateUserName());
		expressPackage.setModifyUserCode(packageEntity.getCreateUserCode());
		
		//添加包类型
		expressPackage.setExpressPackageType(packageTpye);
		
		//构造结束
		//插入快递包
		expressPackageDao.insertExpressPackage(expressPackage);
		
		return true;
	}
	
	/**
	 * 根据传入的库存运单，查询哪些运单中的流水号已经建包，并设置流水号中isCreatedPackage字段为Ｙ。用于快速添加快递运单和添加运单
	 * 包状态不在（CANCELED','CREATED_BILL','ALREADY_CANCELED）中的所有包明细
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-23
	 * @param waybillStockList
	 * @return
	 */
	public void checkPackageWaybillIsCreatedPackage(List<ExpressPackageWayBillDetailDto> waybillStockList){
		//提取运单列表中的运单号列表
		List<String> waybillNoList = new ArrayList<String>();
		for (ExpressPackageWayBillDetailDto expressPackageWayBillDetailDto : waybillStockList) {
			waybillNoList.add(expressPackageWayBillDetailDto.getWaybillNo());
		}
		List<ExpressPackageDetailEntity> pacakgeDetailList = null;
		//所选运单单中，所有已经建包的包明细
		pacakgeDetailList = this.queryPackageDetailByWaybills(waybillNoList);
		
		//检查库存中的运单是否部分在包明细中
		for (ExpressPackageWayBillDetailDto packageWayBillDetailDto : waybillStockList) {
			int serailCount = 0;//标识
			int totalSerail = packageWayBillDetailDto.getPieces().intValue();//总件数
			//拿到流水号list
			List<ExpressPackageSerialNoStockDto> serailNoList =  packageWayBillDetailDto.getSerialNoStockList();
			for (ExpressPackageDetailEntity expressPackageDetailEntity : pacakgeDetailList) {
				//如果运单号相同
				if(StringUtils.equals(expressPackageDetailEntity.getWayBillNo(),packageWayBillDetailDto.getWaybillNo())){
					//循环流水号
					for (ExpressPackageSerialNoStockDto packageSerialNoDto : serailNoList) {
						//如果流水号相同
						if(StringUtils.equals(packageSerialNoDto.getSerialNo(), expressPackageDetailEntity.getSerialNo())){
							serailCount++;//流水号数量累加
							packageSerialNoDto.setIsCreatedPackage(FossConstants.YES);//设置该运单中的流水号已建包
						}
						//减少循环次数
						if(serailCount>=totalSerail){
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据运单号列表，查询包明细中，运单号在这些运单中的明细列表
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-23
	 * @param waybillNoList
	 * @return
	 */
	private List<ExpressPackageDetailEntity> queryPackageDetailByWaybills(List<String> waybillNoList){
		List<ExpressPackageDetailEntity> pacakgeDetailList = new ArrayList<ExpressPackageDetailEntity>();
		pacakgeDetailList = expressPackageDao.queryPackageDetailByWaybills(waybillNoList);
		return pacakgeDetailList;
	}
	
}
