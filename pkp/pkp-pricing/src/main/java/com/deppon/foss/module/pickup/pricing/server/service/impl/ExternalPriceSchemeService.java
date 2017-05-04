package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExternalPriceSchemeDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExternalPriceSchemeService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ExternalPriceSchemeException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Service
 */
public class ExternalPriceSchemeService implements IExternalPriceSchemeService {
	
	private static final int THREE = 3;
	private static final int FOUR =4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN = 10;
	
	private static final Logger logger = Logger.getLogger(ExternalPriceSchemeService.class);
	/**
	 * 偏线外发价格方案Dao
	 * */
	private IExternalPriceSchemeDao externalPriceSchemeDao;
	/**
	 * 偏线代理网点service
	 * */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	/**
	 * 外场 Service
	 * */
	
	@Inject
	private IEmployeeService employeeService;
	private IAreaAddressService areaAddressService;
	public IAreaAddressService getAreaAddressService() {
		return areaAddressService;
	}
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}
	/**
	 * 设置 职员service.
	 *
	 * @param employeeService the new 职员service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线代理送货费方案
	 * @param entity
	 * @return String
	 */
	private IOutfieldService outfieldService;
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	public void setExternalPriceSchemeDao(
			IExternalPriceSchemeDao externalPriceSchemeDao) {
		this.externalPriceSchemeDao = externalPriceSchemeDao;
	}
	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	@Override
	public List<ExternalPriceSchemeEntity> queryExternalPriceSchemeByParam(
			ExternalPriceSchemeEntity entity, int limit, int start) {
		List<ExternalPriceSchemeEntity> entityList=null;
		if(null==entity){
			entity=new ExternalPriceSchemeEntity();
		}
		entityList=externalPriceSchemeDao.queryExternalPriceSchemeByParam(entity, limit, start);
		
		if(CollectionUtils.isNotEmpty(entityList)){
			for(ExternalPriceSchemeEntity externalPriceSchemeEntity:entityList){
				if(StringUtil.isNotBlank(externalPriceSchemeEntity.getModifyUser())){
					EmployeeEntity employeeEntity= employeeService.queryEmployeeByEmpCode(externalPriceSchemeEntity.getModifyUser());//查询修改人信息
					if(employeeEntity!=null){
						//设置修改人姓名
						externalPriceSchemeEntity.setModifyUser(employeeEntity.getEmpName());
					}
				}
			}
		}
		return entityList;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案总量
	 * @param entity
	 * @return Long
	 */
	@Override
	public Long queryRecordCount(ExternalPriceSchemeEntity entity) {
		Long result=null;
		if(null!=entity){
			result=externalPriceSchemeDao.queryRecordCount(entity);
		}
		return result;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	@Override
	public ExternalPriceSchemeEntity queryExternalePriceSchemeById(
			String schemeId) {
		ExternalPriceSchemeEntity entity=null;
		if(StringUtil.isNotBlank(schemeId)){
			entity=externalPriceSchemeDao.queryExternalePriceSchemeById(schemeId);
			entity.setCurrentUsedVersion(entity.getCurrentUsedVersion());
		}
		return entity;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	@Transactional
	@Override
	public int addExternalPriceScheme(ExternalPriceSchemeEntity entity) {
		if(null==entity){
			return FossConstants.FAILURE;
		}else{
			StringBuffer tempBuffer = new StringBuffer();
			if (StringUtils.isEmpty(entity.getSchemeName())) {
				tempBuffer.append("方案名称  ");
			}
			if (StringUtils.isEmpty(entity.getTransportType())) {
				tempBuffer.append(" 运输类型 ");
			}
			if (StringUtils.isEmpty(entity.getAgentDeptCode())) {
				tempBuffer.append(" 目的站  ");
			}
			if (StringUtils.isEmpty(entity.getExternalOrgCode())) {
				tempBuffer.append(" 外发外场 ");
			}
			if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
				throw new ExternalPriceSchemeException("",tempBuffer.toString() + " 不能为空！");
			}
			Date now=new Date();
			if(entity.getBeginTime().before(now)){
				throw new ExternalPriceSchemeException("","生效日期不能小于当前日期！");
			}
			if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001_ZH, entity.getTransportType())){
				entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
			}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002_ZH, entity.getTransportType())){
				entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
			}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003_ZH, entity.getTransportType())){
				entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
			}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE_ZHENGCHE, entity.getTransportType())){
				entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE_ZHENGCHE);
			}
			entity.setId(UUIDUtils.getUUID());
			entity.setVersionNo(System.currentTimeMillis());
			
			Date createDate=new Date();					
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			// 当前登录用户empcode
			String userCode = user.getEmployee().getEmpCode();
			//修改人、修改时间为展示内容，新增时，修改人即新增人
			entity.setCreateDate(createDate);
			entity.setCreateUser(userCode);
			entity.setModifyDate(createDate);
			entity.setModifyUser(userCode);			
			entity.setEndTime(DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT));
			entity.setActive(FossConstants.INACTIVE);
			return externalPriceSchemeDao.addExternalPriceScheme(entity);
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @description 修改偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	@Transactional
	@Override
	public int updateExternalPriceScheme(ExternalPriceSchemeEntity entity) {
		if(null==entity || StringUtil.isBlank(entity.getId())){
			return FossConstants.FAILURE;
		}else{
			StringBuffer tempBuffer = new StringBuffer();
			if (StringUtils.isEmpty(entity.getSchemeName())) {
				tempBuffer.append("方案名称  ");
			}
			if (StringUtils.isEmpty(entity.getTransportType())) {
				tempBuffer.append(" 运输类型 ");
			}
			if (StringUtils.isEmpty(entity.getAgentDeptCode())) {
				tempBuffer.append(" 目的站  ");
			}
			if (StringUtils.isEmpty(entity.getExternalOrgCode())) {
				tempBuffer.append(" 外发外场 ");
			}
			if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
				throw new ExternalPriceSchemeException("",tempBuffer.toString() + " 不能为空！");
			}
			Date now=new Date();
			if(null==entity.getBeginTime() || entity.getBeginTime().before(now)){
				throw new ExternalPriceSchemeException("","生效日期不能小于当前日期！");
			}
			entity.setTransportType(convertTransportTypeToEnum(entity.getTransportType()));
			entity.setModifyDate(now);
			//吴涛 修改： 修改人信息没有正确显示
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			// 当前登录用户empcode
			String userCode = user.getEmployee().getEmpCode();
			entity.setModifyUser(userCode);
			return externalPriceSchemeDao.updateExternalPriceScheme(entity);
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @description 删除偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	@Transactional
	@Override
	public int deleteExternalPriceSchemeById(List<String> idList) {
		if(null==idList||idList.size()==0){
			return FossConstants.FAILURE;
		}else{
			List<String> list=new ArrayList<String>();
			int result=0;
			//分批处理，每200条处理一次
			for(int i=0;i<idList.size();i++){
				list.add(idList.get(i));
				if(i%NumberConstants.NUMBER_200==0||i==idList.size()-1){
					//查询待删除的数据中，是否有已激活的内容
					long activeResult=externalPriceSchemeDao.queryExternalPriceSchemeByIdAndState(list, FossConstants.ACTIVE);
					if(activeResult>0){
						throw new ExternalPriceSchemeException("","请选择未激活数据进行删除！");
					}
					result+=externalPriceSchemeDao.deleteExternalPriceSchemeById(list);
					list.clear();
				}
			}
			return result;
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @description 激活偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	@Transactional
	@Override
	public int activateExternalPriceSchemeById(List<ExternalPriceSchemeEntity> entityList) {
		Date now=new Date();
		int result=0;
		for(ExternalPriceSchemeEntity entity:entityList){
			checkAndupdateOldExternalPriceScheme(entity,now);//修改已激活方案的截止日期
			//激活方案不修改起止时间，只修改激活的状态
			entity.setBeginTime(null);
			entity.setEndTime(null);
			entity.setActive(FossConstants.ACTIVE);
			result+=externalPriceSchemeDao.updateSchemeStateById(entity);
		}
		return result;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 立即激活偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	@Transactional
	@Override
	public int immediatelyActivateSchemeById(ExternalPriceSchemeEntity entity) {
		Date now =new Date();
		checkAndupdateOldExternalPriceScheme(entity,now);//修改已激活方案的截止日期
		//立即激活，修改起始时间、激活状态。
		entity.setEndTime(null);
		entity.setActive(FossConstants.ACTIVE);
		return externalPriceSchemeDao.updateSchemeStateById(entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 复制偏线外发价格方案
	 * @param entity
	 * @return ExternalPriceSchemeEntity
	 */
	@Transactional
	@Override
	public ExternalPriceSchemeEntity copyExternalPriceScheme(String id) {
		if(StringUtil.isBlank(id)){
			throw new ExternalPriceSchemeException("请选择需要复制的方案！","请选择需要复制的方案！");
		}else{
			ExternalPriceSchemeEntity copyEntity=new ExternalPriceSchemeEntity();
			Date now=new Date();
			copyEntity.setId(UUIDUtils.getUUID());
			copyEntity.setCreateDate(now);
			copyEntity.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
			copyEntity.setModifyDate(now);
			//Allen：modify start...
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			// 当前登录用户empcode
			String userCode = user.getEmployee().getEmpCode();
			copyEntity.setModifyUser(userCode);
			//modify  end...
			copyEntity.setVersionNo(System.currentTimeMillis());
			copyEntity.setActive(FossConstants.INACTIVE);
			externalPriceSchemeDao.copyExternalPriceScheme(copyEntity,id);
			return copyEntity;
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @description 立即中止偏线外发价格方案
	 * @param id
	 * @return Integer
	 */
	@Transactional
	@Override
	public int immediatelySuspendSchemeById(ExternalPriceSchemeEntity entity) {
		if(null==entity||StringUtil.isBlank(entity.getId())){
			throw new ExternalPriceSchemeException("请选择需要中止的方案！","请选择需要中止的方案！");
		}
		if(new Date().after(entity.getEndTime())){
			throw new ExternalPriceSchemeException("中止日期不能小于当前日期！","中止日期不能小于当前日期！");
		}
		List<String> idList=new ArrayList<String>();
		idList.add(entity.getId());
		long activeResult=externalPriceSchemeDao.queryExternalPriceSchemeByIdAndState(idList, FossConstants.ACTIVE);
		if(activeResult==0){
			throw new ExternalPriceSchemeException("请选择已激活数据进行操作！","请选择已激活数据进行操作！");
		}
		entity.setBeginTime(null);
		entity.setActive(FossConstants.ACTIVE);
		return externalPriceSchemeDao.updateSchemeStateById(entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 导入偏线外发价格方案
	 * @param workbook
	 * @return Integer
	 */
	@Override
	public int importExternalPriceScheme(Workbook workbook) {
		List<ExternalPriceSchemeEntity> allEntityList=analysisExcel(workbook);//解析EXCEL
		List<ExternalPriceSchemeEntity> entityList=new ArrayList<ExternalPriceSchemeEntity>();
		int result=0;
		//分批处理，每50条处理一次
		for(int i=0;i<allEntityList.size();i++){
			entityList.add(allEntityList.get(i));
			if(i%NumberConstants.NUMBER_50==0||i==allEntityList.size()-1){
				result +=externalPriceSchemeDao.batchAddExternalPriceScheme(entityList);
				entityList.clear();
			}
		}
		return result;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 导出偏线外发价格方案 参考 EffectiveExpressPlanService
	 * @param workbook
	 * @return Integer
	 */

	@Override
	public ExportResource exportExternalPriceSchemeByParams(ExternalPriceSchemeEntity entity){
		List<ExternalPriceSchemeEntity> oppDtos = externalPriceSchemeDao.queryExternalPriceSchemeByParam(entity, Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
		if(CollectionUtils.isEmpty(oppDtos)){
		    return null;
		}
		ExportResource exportResource = new ExportResource();
	
		List<List<String>> rowList = new ArrayList<List<String>>();
		String temp = null;
    	Date nowDate = new Date();
		for (ExternalPriceSchemeEntity opp : oppDtos) {
		     rowList.add(exportPlatform(opp, temp, nowDate));
		}
		exportResource.setHeads(PricingColumnConstants.EXPORT_EXTERNAL_PRICE_TITLE);	    
		exportResource.setRowList(rowList);
		return exportResource;
	}
	
	 private List<String> exportPlatform(ExternalPriceSchemeEntity entity, String temp, Date nowDate){
			List<String> result = new ArrayList<String>();	

			//{"方案名称","目的站","外发外场","运输类型","省份","城市","区县","重货价格","轻货价格","最低一票","数据状态","生效日期","截止日期","修改时间","修改人","是否当前版本"};
		    result.add(entity.getSchemeName());
			result.add(entity.getAgentDeptName());
			result.add(entity.getExternalOrgName());
			if ("TRANS_VEHICLE".equals(entity.getTransportType())) {
				result.add("汽运");
			} else {
				result.add("空运");
			}
			temp = areaAddressService.queryRegionByCode(entity.getProvCode()).getName();
		    result.add(temp);
	    	temp = areaAddressService.queryRegionByCode(entity.getCityCode()).getName();
		    result.add(temp);
	    	temp = areaAddressService.queryRegionByCode(entity.getCountyCode()).getName();
		    result.add(temp);
		    result.add(entity.getHeavyPrice().toString());
		    result.add(entity.getLightPrice().toString());
		    result.add(entity.getLowestPrice().toString());
		    
			if(FossConstants.ACTIVE.equals(entity.getActive())){
			    result.add("已激活");
			}else{
			    result.add("未激活");
			}

			//方案开始时间
			result.add(DateUtils.convert(entity.getBeginTime(), DateUtils.DATE_TIME_FORMAT));
			//方案结束时间
			result.add(DateUtils.convert(entity.getEndTime(), DateUtils.DATE_TIME_FORMAT));
			//修改时间
			result.add(DateUtils.convert(entity.getModifyDate(), DateUtils.DATE_TIME_FORMAT));
			//吴涛修改 :查询修改人信息未显示
			temp = employeeService.queryEmployeeByEmpCode(entity.getModifyUser()).getEmpName();
			result.add(temp);
			
	    	if (FossConstants.ACTIVE.equals(entity.getActive()) 
	    			&& nowDate.after(entity.getBeginTime()) && nowDate.before(entity.getEndTime())) {
	    		result.add("是");
			} else {
	    		result.add("否");
			}

			return result;
		}

	/**
	 * @author 092020-lipengfei
	 * @description 激活方案
	 * @param workbook
	 * @return Integer
	 */
	private void checkAndupdateOldExternalPriceScheme(ExternalPriceSchemeEntity entity,Date now){
		if(null==entity){
			throw new ExternalPriceSchemeException("请选择一条记录进行操作！","请选择一条记录进行操作！");
		}
		if(entity.getBeginTime().before(now)){
			throw new ExternalPriceSchemeException("生效日期不能小于当前日期!","生效日期不能小于当前日期！");
		}
		List<String> idList=new ArrayList<String>();
		idList.add(entity.getId());
		long activeResult=externalPriceSchemeDao.queryExternalPriceSchemeByIdAndState(idList, FossConstants.INACTIVE);
		if(activeResult==0){
			throw new ExternalPriceSchemeException("请选择未激活数据进行激活！","请选择未激活数据进行激活！");
		}
		ExternalPriceSchemeEntity oldActiveEntity=new ExternalPriceSchemeEntity();
		oldActiveEntity.setEndTime(DateUtils.getDateAdd(entity.getBeginTime(), -NumberConstants.NUMBER_1000));
		oldActiveEntity.setAgentDeptCode(entity.getAgentDeptCode());
		oldActiveEntity.setExternalOrgCode(entity.getExternalOrgCode());
		oldActiveEntity.setTransportType(entity.getTransportType());
		oldActiveEntity.setActive(FossConstants.ACTIVE);
		oldActiveEntity.setModifyDate(now);
		oldActiveEntity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
		externalPriceSchemeDao.updateExternalPriceSchemeEndTime(oldActiveEntity);//更新已存在的方案的截止日期
	}
	
	/**
	 * @author 092020-lipengfei
	 * @description 解析EXCEL
	 * @param workbook
	 * @return List<ExternalPriceSchemeEntity>
	 */
	private List<ExternalPriceSchemeEntity> analysisExcel(Workbook workbook){
		List<ExternalPriceSchemeEntity> entityList=new ArrayList<ExternalPriceSchemeEntity>();
		Date now = new Date();
		//默认第一个sheet
		if(null!=workbook && null!=workbook.getSheetAt(0)){
			Sheet sheet=workbook.getSheetAt(0);
			int index = 1;
			int col = 0;
			Iterator<Row> rows = sheet.rowIterator();
			rows.next();// 第一行标题不读
			if(rows.hasNext()){
			 for (;rows.hasNext();) {
					ExternalPriceSchemeEntity entity = new ExternalPriceSchemeEntity();
					Row row = rows.next();
					col = 0;
					// 读取一行的数据，传入的参数：单元内容，实体，行数，列数
					for (Iterator<Cell> cells = row.cellIterator(); cells.hasNext();) {
						Cell cell = cells.next();
						convertCellInfo(cell, entity, index, col);// 读取单元格数据
						col++;
					}
					entity.setId(UUIDUtils.getUUID());
					entity.setVersionNo(System.currentTimeMillis());
					// 修改人、修改时间为展示内容，新增时，修改人即新增人
					entity.setCreateDate(now);
					UserEntity user = (UserEntity) UserContext.getCurrentUser();
					entity.setCreateUser(user.getEmployee().getEmpCode());
					entity.setModifyDate(now);
					entity.setModifyUser(user.getEmployee().getEmpCode());
					entity.setActive(FossConstants.INACTIVE);
					entityList.add(entity);
					index ++;
			   }
			}
		}
		return entityList;
	}
	
	/**
	 * @author 092020-lipengfei
	 * @description 转换单元格中的数据
	 * @param cell
	 * @param entity
	 * @param index
	 * @param col
	 */
	private void convertCellInfo(Cell cell,ExternalPriceSchemeEntity entity,int index,int col){
		//"序号","方案名称","目的站","运输类型","外发外场","重货费率","轻货费率","最低一票","生效日期","截止日期"
		String value=ExcelHandleUtil.obtainStringVal(cell);
		switch (cell.getColumnIndex()) {
			case 0:
				//序号
				break;
			case 1:
				//方案名称
				if(StringUtils.isNotBlank(value)) {
					entity.setSchemeName(value);
				}else{
					throw new ExternalPriceSchemeException("BUS_SCHEEEMENAME_NULL","第"+index+"行，第"+col+"列未填写方案名称！");
				}
				break;
			case 2:
				//目的站
				if(StringUtils.isNotBlank(value)) {
                	OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
                	outerBranchEntity.setAgentDeptName(value.trim());
                	List<OuterBranchEntity> obe = vehicleAgencyDeptService.queryVehicleAgencyDepts(outerBranchEntity, TEN, 0);
                	if (obe != null && obe.size() > 0 ) {
                		entity.setAgentDeptName(obe.get(0).getAgentDeptName());//目的站名称
                		entity.setAgentDeptCode(obe.get(0).getAgentDeptCode());//目的站编码
                		entity.setProvCode(obe.get(0).getProvCode());//省份编码
                		entity.setProvName(obe.get(0).getProvName());//省份名称
                		entity.setCityCode(obe.get(0).getCityCode());//城市编码
                		entity.setCityName(obe.get(0).getCityName());//城市名称
                		entity.setCountyCode(obe.get(0).getCountyCode());//区县名称
                		entity.setCountyName(obe.get(0).getCountyName());//区县名称
					} else {
                    	throw new ExternalPriceSchemeException("BUS_AGENCYDEPTNAME_NOT_EXIST","第"+index+"行，第"+col+"列的目的站（“" + value + "”）在系统中不存在！");
					}
                } else {
                	throw new ExternalPriceSchemeException("BUS_AGENCYDEPTNAME_NULL","第"+index+"行，第"+col+"列未填写目的站！");
				}
				break;
			case THREE:
				//外发外场
				if(StringUtils.isNotBlank(value)) {
					OutfieldEntity outfieldEntity = new OutfieldEntity();
					outfieldEntity.setName(value.trim());
                	List<OutfieldEntity> oe = outfieldService.queryOutfieldByEntity(outfieldEntity, 0, TEN);
                	if (oe != null && oe.size() > 0) {
                		entity.setExternalOrgName(oe.get(0).getName());//外场名称
                		entity.setExternalOrgCode(oe.get(0).getOrgCode());//外场编码
					} else {
                    	throw new ExternalPriceSchemeException("BUS_OUTFIELDNAME_NOT_EXIST","第"+index+"行，第"+col+"列的外发外场（“" + value + "”）在系统中不存在！");
					}
				}else{
					throw new ExternalPriceSchemeException("BUS_OUTFIELDNAME_NULL","第"+index+"行，第"+col+"列未填写外发外场！");
				}
				break;
			case FOUR:
				//运输性质
				if(StringUtils.isNotBlank(value)) {
						entity.setTransportType(convertTransportTypeToEnum(value));
				}else{
					throw new ExternalPriceSchemeException("BUS_TRANTYPE_NULL","第"+index+"行，第"+col+"列未填写运输性质！");
				}
				break;
		
			case FIVE:
				//重货费率
				if(StringUtils.isNotBlank(value)) {
                	try {
                		entity.setHeavyPrice(new BigDecimal(value.trim()));
					} catch (Exception e) {
						throw new ExternalPriceSchemeException("BUS_HEAVYPRICE_ERROR","第"+index+"行，第"+col+"列填写的轻货费率（“" + value + "”）有误！");
					}
				}else{
					throw new ExternalPriceSchemeException("BUS_HEAVYPRICE_NULL","第"+index+"行，第"+col+"列未填写重货费率！");
				}
				break;
			case SIX:
				//轻货费率
				if(StringUtils.isNotBlank(value)) {
                	try {
                		entity.setLightPrice(new BigDecimal(value.trim()));
					} catch (Exception e) {
						throw new ExternalPriceSchemeException("BUS_LIGHTPRICE_ERROR","第"+index+"行，第"+col+"列填写的轻货费率（“" + value + "”）有误！");
					}
				}else{
					throw new ExternalPriceSchemeException("BUS_LIGHTPRICE_NULL","第"+index+"行，第"+col+"列未填写轻货费率！");
				}
				break;
			case SEVEN: //数据库中的中没有数据
				//最低一票
				if(StringUtils.isNotBlank(value)) {
                	try {
						int a = value.trim().lastIndexOf(".");
						if (a > 0) {
							value = value.substring(0, a);  
							entity.setLowestPrice(new BigDecimal(value));
						}
                	} catch (Exception e) {
						throw new ExternalPriceSchemeException("BUS_LOWESTPRICE_ERROR","第"+index+"行，第"+col+"列填写的最低一票（“" + value + "”）有误！");
					}
				}else{
					throw new ExternalPriceSchemeException("BUS_LOWESTPRICE_NULL","第"+index+"行，第"+col+"列未填写最低一票！");
				}
				break;
			case EIGHT:
				//生效日期
				if(StringUtils.isNotBlank(value)) {
					try {
						entity.setBeginTime(DateUtils.convert(value.trim()));
					} catch (Exception e) {
						throw new ExternalPriceSchemeException("BUS_BEGINTIME_ERROR","第"+index+"行，第"+col+"列填写的生效日期（“" + value + "”）有误！");
					}
				}else{
					throw new ExternalPriceSchemeException("BUS_BEGINTIME_NULL","第"+index+"行，第"+col+"列未填写生效日期！");
				}
				break;
			case NINE:
				//截止日期
				if(StringUtils.isNotBlank(value)) {
					try {
						entity.setEndTime(DateUtils.convert(value.trim()));
					} catch (Exception e) {
						throw new ExternalPriceSchemeException("BUS_ENTTIME_ERROR","第"+index+"行，第"+col+"列填写的截止日期（“" + value + "”）有误！");
					}
				}else{
					entity.setEndTime(DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT));
				}
				break;
			default :
				logger.info("第"+index+"行，第"+col+"列数据超出预期范围！");
		}
	}

	/**
	 * 运输方式中文转换为枚举
	 * @param transportCH
	 * @return String
	 */
	private String convertTransportTypeToEnum(String transportCH){
		String transportEnum=null;
		if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001_ZH, transportCH)){
			transportEnum=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001;
		}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002_ZH, transportCH)){
			transportEnum=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002;
		}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003_ZH, transportCH)){
			transportEnum=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003;
		}else{
			transportEnum=transportCH;
		}
		return transportEnum;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站，外发外场，运输类型，外发单生成时间 查询偏线外发价格方案
	 * @param targetOrgCode
	 * @param outOrgCode
	 * @param transportType
	 * @param externalBillCreateTime
	 * @return ExternalPriceSchemeEntity
	 */
	@Override
	public ExternalPriceSchemeEntity queryAgentOutPriceInfo(
			String targetOrgCode, String outOrgCode, String transportType,
			Date externalBillCreateTime) {
		StringBuffer tempBuffer = new StringBuffer();
		if (StringUtils.isEmpty(targetOrgCode)) {
			tempBuffer.append("targetOrgCode ");
		}
		if (StringUtils.isEmpty(outOrgCode)) {
			tempBuffer.append(" outOrgCode ");
		}
		if (StringUtils.isEmpty(transportType)) {
			tempBuffer.append(" transportType  ");
		}
		if (null==externalBillCreateTime) {
			tempBuffer.append(" externalBillCreateTime ");
		}
		if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
			throw new ExternalPriceSchemeException("",tempBuffer.toString() + " 不能为空！");
		}
		//根据参数查询方案
		return externalPriceSchemeDao.queryAgentOutPriceInfo(targetOrgCode, outOrgCode, transportType, externalBillCreateTime);
	}
	
	
	
}
