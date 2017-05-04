package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustExpressPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;

public class AdjustExpressPathService implements IAdjustExpressPathService {

	//日志
	private static final Logger logger = LogManager.getLogger(AdjustTransportationPathService.class);
	
	/**
	 * 组织相关service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 数据字典service
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * 货区service
	 */
	private IGoodsAreaService goodsAreaService;
	
	/**
	 * 计算走货路径service
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	//常量 保留3位小数字
	private static final int three = 3;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	//adjustExpressPathDao
	private IAdjustExpressPathDao adjustExpressPathDao;
	
	/**
	 * 调整走货路径dao
	 * 
	 */
	private IAdjustTransportationPathDao adjustTransportationPathDao;
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao#queryExpressByParamMap(java.util.Map, int, int)
	 * @desc 查询快递修改的走货路径集合
	 * @wqh
	 * @date 2014-12-08
	 */
	public List<AdjustEntity> queryExpressWaybills(AdjustEntity adjustEntity,int limit, int start)
	{
	    
		
		BigDecimal countW;
		BigDecimal countV;

		reInitAdjustEntity(adjustEntity);
		// 获取获取集合
		List<String> goodsAreaCodesList = getGoodsAreaCodesByAdjustEntity(adjustEntity);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", adjustEntity.getOrgCode());
		paramMap.put("goodsAreaCodesList", goodsAreaCodesList);
		if(StringUtil.isNotEmpty(adjustEntity.getPackageNextOrgCode()))
		{
			paramMap.put("objectiveOrgCode",adjustEntity.getPackageNextOrgCode());
			
		}
		// 根据组织号获取库区list 并且分页
		List<AdjustEntity> adjust = adjustExpressPathDao.queryExpressByParamMap(paramMap, limit, start);
		// 根据组织号获取运单号list
		List<AdjustEntity> waybillList = adjustExpressPathDao.queryExpressWaybillListByParamMap(paramMap);
		// modify by liangfuxiang 2013-6-3下午10:32:42 end BUG-19817;
		// 将waybill合并为库区数据
		for (int i = 0; i < adjust.size(); i++) {
			// 如果组织code不为空
			if (null != adjustEntity && StringUtils.isNotEmpty(adjustEntity.getOrgCode())) {
				// 设置组织code和组织名称
				adjust.get(i).setOrgCode(adjustEntity.getOrgCode());
				adjust.get(i).setOrgName(getNameByCode(adjustEntity.getOrgCode()));
			}
			// 调用基础资料查询库区对应的库区线路信息
			GoodsAreaEntity line = goodsAreaService.queryGoodsAreaByCode(adjust.get(i).getOrgCode(), adjust.get(i).getGoodsAreaCode());

			
			// 给重量和体积赋0值
			countW = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			countV = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			// 循环查询到的运单list
			for (int j = 0; j < waybillList.size(); j++) {
				// 如果运单的货区为当前货区
				if (StringUtils.equals(waybillList.get(j).getGoodsAreaCode(), adjust.get(i).getGoodsAreaCode()) && null != waybillList.get(j).getGoodsQtyTotal()
						&& null != waybillList.get(j).getGoodsWeightTotal() && null != waybillList.get(j).getGoodsVolumeTotal()) {
					BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
					// 获取在库件数和总件数的比
					bd = BigDecimal.valueOf((double) waybillList.get(j).getStockGoodsQTY() / (double) waybillList.get(j).getGoodsQtyTotal());
					// 根据比值乘总票重量&体积,累加重量和体积
					countW = countW.add(waybillList.get(j).getGoodsWeightTotal().multiply(bd));
					countV = countV.add(waybillList.get(j).getGoodsVolumeTotal().multiply(bd));
				}
			}
			// 设置货区重量和体积
			adjust.get(i).setAreaWeightTotal(countW);
			adjust.get(i).setAreaVolumeTotal(countV);

			if (null == line) {

				logger.warn("AdjustTransportationPathService[queryLevel1()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + adjust.get(i).getOrgCode() + " GoodsAreaCode : "
						+ adjust.get(i).getGoodsAreaCode());
				// 设置库区编码
				adjust.get(i).setGoodsAreaName(adjust.get(i).getGoodsAreaCode());
				// 不可调整货区
				adjust.get(i).setIfDisable(0);
				// 设置线路
				adjust.get(i).setAreaLine(
						TransportPathConstants.QUERYGOODSAREABYCODE_NULL + calculateTransportPathService.getOrgNameAndCode(adjust.get(i).getOrgCode()) + TransportPathConstants.ORIGGOODSAREACODE
								+ adjust.get(i).getGoodsAreaCode());
			}
			else {

				// 如果库区线路信息中有库区名
				if (StringUtils.isNotEmpty(line.getGoodsAreaName())) {
					// // 设置库区名
					adjust.get(i).setGoodsAreaName(line.getGoodsAreaName());
				}
				// 判断是否不可调整路径货区
				if (StringUtils.isEmpty(line.getArriveRegionCode()) && !"BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())) {
					// 根据TYPE获取数据字典中的TYPE名称
					if (StringUtils.isNotEmpty(line.getGoodsAreaType())) {
						// 根据 TERMS_CODE,VALUE_CODE 查询值对象：
						DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE,
								line.getGoodsAreaType());
						if (null != dataDictionaryValueEntity && StringUtils.isNotEmpty(dataDictionaryValueEntity.getValueName())) {
							// 不为空则设置名称
							adjust.get(i).setAreaLine(dataDictionaryValueEntity.getValueName());
						}
						else {
							// 否则设置type
							adjust.get(i).setAreaLine(line.getGoodsAreaType());
						}
					}
					// 不可调整货区则设置为0
					adjust.get(i).setIfDisable(1);
				}
				else {
					//允许快递货区修改
					if("BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())){
						// 根据 TERMS_CODE,VALUE_CODE 查询值对象：
						DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE,
								line.getGoodsAreaType());
						// 不为空则设置名称
						adjust.get(i).setAreaLine(dataDictionaryValueEntity.getValueName());
					}else{
						// 设置线路 根据出发和到达部门进行拼接
						adjust.get(i).setAreaLine(getNameByCode(adjust.get(i).getOrgCode()) + "-" + line.getArriveRegionName());
					}
					
				}
			}
			// modify by liangfuxiang 2013-7-17下午3:05:15 end BUG-45551 深圳枢纽中心 外场调整走货路由时，打开页面即报错，请解决。
		}
		return adjust;
	     
	     
	}

	

	/**
	 * 
	 * @Title: reInitAdjustEntity
	 * @Description: 重新初始化<修改货物走货路径>
	 * @param adjustEntity 设定文件
	 * @return void 返回类型
	 * @see reInitAdjustEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-5-1 上午10:23:40
	 * @throws
	 */
	private void reInitAdjustEntity(AdjustEntity adjustEntity) {
		if (null == adjustEntity) {
			logger.error(TransportPathConstants.QUERY_LEVEL1_ADJUSTENTITY_NULL);
			throw new TfrBusinessException(TransportPathConstants.QUERY_LEVEL1_ADJUSTENTITY_GLOB);
		}
		// 获取当前组织
		String initOrgCode = adjustEntity.getOrgCode();
		// 当前组织为空
		if (StringUtil.isEmpty(initOrgCode)) {
			logger.info(TransportPathConstants.QUERY_LEVEL1_ORGCODE_NULL);
			//重新获取当前部门编码
			initOrgCode=FossUserContext.getCurrentDeptCode();
		}
		// 根据当前组织编码获取组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = querySuperiorOrgByOrgCode(initOrgCode);
		// 重新配置当前组织编码
		adjustEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
	}

	/**
	 * 
	 * @Title: querySuperiorOrgByOrgCode
	 * @Description: 根据当前部门获取查找顶级组织部门
	 * @param orgCode
	 * @return 设定文件
	 * @return OrgAdministrativeInfoEntity 返回类型
	 * @see querySuperiorOrgByOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-5-1 上午9:40:28
	 * @throws
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) throws TfrBusinessException {

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		// 营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		}
		else {
			// 获取上级部门失败
			logger.error("AdjustTransportationPathService[querySuperiorOrgByOrgCode()]:" + TransportPathConstants.QUERY_TOP_ORG_BY_CURRENT_ORG_FAIL + orgCode);
			throw new TfrBusinessException(TransportPathConstants.QUERY_TOP_ORG_BY_CURRENT_ORG_FAIL_GLOB, new Object[] { orgCode });
		}
	}
	
	
	/**
	 * 根据组织code查询缓存获取name
	 * 
	 * @author huyue
	 * @date 2013-1-22 下午3:42:47
	 */
	public String getNameByCode(String orgCode) {
		//通过编码在缓存中查名称(包括德邦自有部门和代理网点)
		String orgName = orgAdministrativeInfoService.queryCommonNameByCommonCodeFromCache(orgCode);
		if (StringUtils.isEmpty(orgName)) {
			//如果为空,则返回code
			return orgCode;
		} else {
			//不为空则返回名字
			return orgName;
		}
	}
	

	/**
	 * @Title: getGoodsAreaCodesByAdjustEntity
	 * @Description: 获取组织编码
	 * @param adjustEntity
	 * @return 设定文件
	 * @return List<String> 返回类型
	 * @see getOrgCodesByAdjustEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-3 下午10:33:59
	 * @throws
	 */
	private List<String> getGoodsAreaCodesByAdjustEntity(AdjustEntity adjustEntity) {

		// 库区编码集合
		List<String> initGoodsAreaCodesList = new ArrayList<String>();

		// 获取当前组织
		String initGoodsAreaCodes = adjustEntity.getGoodsAreaCode();
		// 当前组织为空
		if (!StringUtil.isEmpty(initGoodsAreaCodes) && StringUtils.length(StringUtils.trim(initGoodsAreaCodes)) > 0) {
			// 拆分多个orgCode
			String[] inputOrgCodes = initGoodsAreaCodes.split(",");
			// 保存在列表中
			for (int i = 0; i < inputOrgCodes.length; i++) {
				initGoodsAreaCodesList.add(StringUtils.trim(inputOrgCodes[i]));
			}
		}
		return initGoodsAreaCodesList;
	}

  /**
   * 查询总条数
   * */
  public long getCount(AdjustEntity adjustEntity)
  {
		reInitAdjustEntity(adjustEntity);
		// 获取获取集合
		List<String> goodsAreaCodesList = getGoodsAreaCodesByAdjustEntity(adjustEntity);
		
	    Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", adjustEntity.getOrgCode());
		paramMap.put("goodsAreaCodesList", goodsAreaCodesList);
		if(StringUtil.isNotEmpty(adjustEntity.getPackageNextOrgCode()))
		{
			paramMap.put("objectiveOrgCode",adjustEntity.getPackageNextOrgCode());
			
		}
		// 根据组织号获取库区list 并且分页
		return adjustExpressPathDao.getCount(paramMap);
  }
	
  
  /* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao#queryExpressByParamMap(java.util.Map, int, int)
	 * @desc 查询快递修改的走货路径集合
	 * @wqh
	 * @date 2014-12-08
	 */
   public List<AdjustEntity> queryExpressWaybillList(AdjustEntity adjustEntity)
   {
	 //设置原路径为空
	 		String origPath = "";
	 		int ifDisable = 0;
	 		// 调用基础资料查询运单对应的修改前线路
	 		GoodsAreaEntity line = goodsAreaService.queryGoodsAreaByCode(adjustEntity.getOrgCode(), adjustEntity.getGoodsAreaCode());


	 		if (null == line) {
	 			logger.error("AdjustTransportationPathService[queryLevel2()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + adjustEntity.getOrgCode() + " GoodsAreaCode : "
	 					+ adjustEntity.getGoodsAreaCode());
	 			origPath = TransportPathConstants.QUERYGOODSAREABYCODE_NULL + calculateTransportPathService.getOrgNameAndCode(adjustEntity.getOrgCode()) + TransportPathConstants.ORIGGOODSAREACODE
	 					+ adjustEntity.getGoodsAreaCode();
	 			ifDisable = 0;
	 		}
	 		else {
	 			// 判断是否不可调整路径货区
	 			if (StringUtils.isEmpty(line.getArriveRegionCode()) && !"BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())) {
	 				// 根据TYPE获取数据字典中的TYPE名称
	 				if (StringUtils.isNotEmpty(line.getGoodsAreaType())) {
	 					//根据 TERMS_CODE,VALUE_CODE 查询值对象
	 					DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE, line.getGoodsAreaType());
	 					if (null != dataDictionaryValueEntity && StringUtils.isNotEmpty(dataDictionaryValueEntity.getValueName())) {
	 						//如果不为空则设置原路径
	 						origPath = dataDictionaryValueEntity.getValueName();
	 					} else {
	 						//否则设置为库区类型
	 						origPath = line.getGoodsAreaType();
	 					}
	 				}
	 				// 不可调整货区则设置为0
	 				ifDisable = 1;
	 			} else {
	 				//允许快递货区修改
	 				if("BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())){
	 					// 根据 TERMS_CODE,VALUE_CODE 查询值对象：
	 					DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE,
	 							line.getGoodsAreaType());
	 					// 不为空则设置名称
	 					origPath = dataDictionaryValueEntity.getValueName();
	 				}else{
	 					//非快递库区时，快递的目的站部门code置为空
	 					adjustEntity.setPackageNextOrgCode("");
	 				//设置线路 根据出发和到达部门进行拼接
	 				origPath = line.getOrganizationName() + "-" + line.getArriveRegionName();
	 				}
	 			}
	 		}
	 		
	 		List<String> goodsAreaCodesList = getGoodsAreaCodesByAdjustEntity(adjustEntity);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orgCode", adjustEntity.getOrgCode());
			paramMap.put("goodsAreaCodesList", goodsAreaCodesList);
			if(StringUtil.isNotEmpty(adjustEntity.getPackageNextOrgCode()))
			{
				paramMap.put("objectiveOrgCode",adjustEntity.getPackageNextOrgCode());
				
			}

	 		// 获取所有原部门,库区为当前的数据
	 		List<AdjustEntity> adjustList = adjustExpressPathDao.queryExpressWaybillList(paramMap);
	 		if(CollectionUtils.isNotEmpty(adjustList)){
	 			// 得到运单号的list 调用运单接口得到对应的重量,体积,件数
	 			for (int i = 0; i < adjustList.size(); i++) {
	 				if (null != adjustList.get(i).getOrgCode()) {
	 					if(null!=line){
	 						adjustList.get(i).setOrgName(line.getOrganizationName());
	 					}
	 					else{
	 						adjustList.get(i).setOrgName(adjustEntity.getOrgCode());
	 					}
	 				}
	 				// 如果不为空.计算实际库区内的重量体积件数
	 				if (null != adjustList.get(i).getGoodsQtyTotal() && null != adjustList.get(i).getGoodsWeightTotal() && null != adjustList.get(i).getGoodsVolumeTotal()) {
	 					BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
	 					//获取在库件数和总件数的比
	 					bd = BigDecimal.valueOf((double) adjustList.get(i).getStockGoodsQTY() / (double) adjustList.get(i).getGoodsQtyTotal());
	 					//根据比值乘总票重量&体积,累加重量和体积
	 					adjustList.get(i).setGoodsWeightTotal(adjustList.get(i).getGoodsWeightTotal().multiply(bd));
	 					adjustList.get(i).setGoodsVolumeTotal(adjustList.get(i).getGoodsVolumeTotal().multiply(bd));
	 				} else {
	 					// 为空则设置为0
	 					adjustList.get(i).setGoodsWeightTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
	 					adjustList.get(i).setGoodsVolumeTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
	 				}
	 				//设置原路径
	 				adjustList.get(i).setBeforeLine(origPath);
	 				//设置是否不能修改
	 				adjustList.get(i).setIfDisable(ifDisable);
	 				// 根据运单号,本部门,修改前库区,查库存改变表,得到修改后库存编号list
	 				Map<String, String> changeMap = new HashMap<String, String>();
	 				changeMap.put("waybillNo", adjustList.get(i).getWaybillNo());
	 				changeMap.put("orgCode", adjustEntity.getOrgCode());
	 				changeMap.put("origGoodsAreaCode", adjustEntity.getGoodsAreaCode());
	 				//根据运单号,部门号,原库区编号查询改变后的库区list
	 				List<String> newGoodsAreaCodeList = adjustTransportationPathDao.queryNewGoodsAreaCodeList(changeMap);
	 				StringBuffer path = new StringBuffer();
	 				// 如果为0 则表示没有调整后路径
	 				if (newGoodsAreaCodeList.size() == 0) {
	 					//设置为空
	 					path.append("");
	 				} else {
	 					//快递除外 PRICING_PRODUCT_EXPRESS_PACKAGE
	 					//goodsAreaService.queryCodeByArriveRegionCode(organizationCode, arriveRegionCode, productCode)
	 					for (int j = 0; j < newGoodsAreaCodeList.size(); j++) {
	 						// 调用基础资料方法查询调整后目的地
	 						GoodsAreaEntity afterLine = goodsAreaService.queryGoodsAreaByCode(adjustList.get(i).getOrgCode(), newGoodsAreaCodeList.get(j));
	 						if(StringUtils.isNotBlank(afterLine.getGoodsAreaName())){
	 							if(StringUtils.contains(afterLine.getGoodsAreaName(), "快递库区")){
	 								path.append("快递库区");
	 							}
	 						}else{
	 							String arriveOrgName = afterLine.getArriveRegionName();
	 							// 拼接线路
	 							if (j != 0) {
	 								path.append("/");
	 							}
	 							path.append(afterLine.getOrganizationName());
	 							path.append("-");
	 							path.append(arriveOrgName);
	 						}
	 					}
	 				}
	 				//设置调整后的线路
	 				adjustList.get(i).setAfterLine(path.toString());
	 			}
	 		}
	 		
	 		// 获取所有修改后货区为当前的条目
	 		// 查询修改后为本线路的运单信息
	 		Map<String, String> map = new HashMap<String, String>();
	 		map.put("orgCode", adjustEntity.getOrgCode());
	 		map.put("goodsAreaCode", adjustEntity.getGoodsAreaCode());
	 		// 获取所有修改后部门,库区为当前要求的数据
	 		List<AdjustEntity> changedAdjustList = adjustTransportationPathDao.queryChangedWaybillList(map);
	 		
	 		if(CollectionUtils.isNotEmpty(changedAdjustList)){
	 			// 更新其他字段
	 			for (int i = 0; i < changedAdjustList.size(); i++) {
	 				// 如果不为空,计算实际库区内的重量体积件数
	 				if (null != changedAdjustList.get(i).getGoodsQtyTotal() && changedAdjustList.get(i).getGoodsQtyTotal() != 0 && null != changedAdjustList.get(i).getGoodsWeightTotal() && !changedAdjustList.get(i).getGoodsWeightTotal().equals(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN)) && null != changedAdjustList.get(i).getGoodsVolumeTotal() && !changedAdjustList.get(i).getGoodsVolumeTotal().equals(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN))) {
	 					BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
	 					//获取在库件数和总件数的比
	 					bd = BigDecimal.valueOf((double) changedAdjustList.get(i).getStockGoodsQTY() / (double) changedAdjustList.get(i).getGoodsQtyTotal());
	 					//根据比值乘总票重量&体积,累加重量和体积
	 					changedAdjustList.get(i).setGoodsWeightTotal(changedAdjustList.get(i).getGoodsWeightTotal().multiply(bd));
	 					changedAdjustList.get(i).setGoodsVolumeTotal(changedAdjustList.get(i).getGoodsVolumeTotal().multiply(bd));
	 				} else {
	 					// 如果为空则设置为0
	 					changedAdjustList.get(i).setGoodsWeightTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
	 					changedAdjustList.get(i).setGoodsVolumeTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
	 				}
	 				// 调用基础资料接口获取修改前的线路
	 				GoodsAreaEntity beforeLine = goodsAreaService.queryGoodsAreaByCode(changedAdjustList.get(i).getOrgCode(), changedAdjustList.get(i).getGoodsAreaCode());
	 				
	 				if(null!=beforeLine){
	 					//有快递货区 合车过来的运单
	 					if(WaybillConstants.PACKAGE.equals(changedAdjustList.get(i).getProductCode())
	 							||"RCP".equals(changedAdjustList.get(i).getProductCode())
	 							||"EPEP".equals(changedAdjustList.get(i).getProductCode())
	 							){
	 						changedAdjustList.get(i).setBeforeLine("快递库区");
	 					}else{
	 						changedAdjustList.get(i).setBeforeLine(beforeLine.getOrganizationName() + "-" + beforeLine.getArriveRegionName());
	 					}
	 					
	 					// 不可调整设置为0
	 					changedAdjustList.get(i).setIfDisable(1);
	 				}
	 				else{
	 					
	 					logger.error("AdjustTransportationPathService[queryLevel2()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + changedAdjustList.get(i).getOrgCode() + " GoodsAreaCode : "
	 							+ changedAdjustList.get(i).getGoodsAreaCode());
	 					changedAdjustList.get(i).setBeforeLine(TransportPathConstants.QUERYGOODSAREABYCODE_NULL + calculateTransportPathService.getOrgNameAndCode(changedAdjustList.get(i).getOrgCode()) + TransportPathConstants.ORIGGOODSAREACODE
	 							+ changedAdjustList.get(i).getGoodsAreaCode());
	 					// 不可调整设置为0
	 					changedAdjustList.get(i).setIfDisable(0);
	 				}
	 				// 设置修改后线路为本线路
	 				changedAdjustList.get(i).setAfterLine(origPath);
	 			}
	 		}
	 		
	 		// 添加修改后为本库区的进原List
	 		adjustList.addAll(changedAdjustList);
	 		// modify by liangfuxiang 2013-7-17下午7:13:28 end BUG-45551
	 		return adjustList;
   }
	
	//set

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}



	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}



	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}



	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}



	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}



	public void setAdjustExpressPathDao(IAdjustExpressPathDao adjustExpressPathDao) {
		this.adjustExpressPathDao = adjustExpressPathDao;
	}



	public void setAdjustTransportationPathDao(
			IAdjustTransportationPathDao adjustTransportationPathDao) {
		this.adjustTransportationPathDao = adjustTransportationPathDao;
	}

	
	
}
