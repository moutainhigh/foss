package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressLineScheduleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressTrainProgramService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgGisLongitudeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressTrainScheduleException;
import com.deppon.foss.util.CollectionUtils;
/**
 *<p>Title: ExpressLineScheduleService</p>
 * <p>Description:快递班车线路时刻表service实现类 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-13
 */
public class ExpressLineScheduleService implements IExpressLineScheduleService {
	/**
	 * 快递班车线路时刻表dao
	 */
	private IExpressLineScheduleDao expressLineScheduleDao;
	/**
	 * 组织
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 *快递班车方案Service 
	 */
	private IExpressTrainProgramService expressTrainProgramService;

	public void setExpressTrainProgramService(
			IExpressTrainProgramService expressTrainProgramService) {
		this.expressTrainProgramService = expressTrainProgramService;
	}
	
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setExpressLineScheduleDao(
			IExpressLineScheduleDao expressLineScheduleDao) {
		this.expressLineScheduleDao = expressLineScheduleDao;
	}
	/**
	 *<p>Title: addExpressLineSchedule</p>
	 *<p>新增快递班车线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午4:06:23
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int addExpressLineSchedule(
			ExpressLineScheduleEntity entity) {
		if(null ==entity ||StringUtils.isBlank(entity.getProgramName())){
			throw new ExpressTrainScheduleException("参数为空");
		}
		ExpressLineScheduleEntity result =expressLineScheduleDao.addExpressLineSchedule(entity);
		return result ==null?NumberConstants.ZERO:NumberConstants.NUMBER_1;
	}
	/**
	 *<p>Title: deleteExpressLineSchedule</p>
	 *<p>作废快递班车线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午4:07:40
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int deleteExpressLineSchedule(ExpressLineScheduleEntity entity) {
		if(null ==entity){
			throw new ExpressTrainScheduleException("参数为空");
		}
		return expressLineScheduleDao.deleteExpressLineSchedule(entity);
	}
	/**
	 *<p>Title: queryExpressLineScheduleList</p>
	 *<p>分页查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午4:08:32
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<ExpressLineScheduleEntity> queryExpressLineScheduleList(
			ExpressLineScheduleEntity entity, int start, int limit) {
		if(null == entity){
			entity =new ExpressLineScheduleEntity();
		}
		List<ExpressLineScheduleEntity> resultList =expressLineScheduleDao.queryExpressLineScheduleList(entity, start, limit);
		return attahName(resultList);
	}
	/**
	 *<p>Title: attahName</p>
	 *<p>工具方法：给部门添加名称</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午6:51:44
	 * @param queryExpressLineScheduleList
	 * @return
	 */
	private List<ExpressLineScheduleEntity> attahName(
			List<ExpressLineScheduleEntity> entityList) {
		if(CollectionUtils.isEmpty(entityList)){
			return null;
		}
		List<ExpressLineScheduleEntity> resultList =new ArrayList<ExpressLineScheduleEntity>();
		for (ExpressLineScheduleEntity entity : entityList) {
			entity=attahName(entity);
			resultList.add(entity);
		}
		return resultList;
	}
	/**
	 * 
	 *<p>Title: attahName</p>
	 *<p>工具方法：给部门添加名称</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午7:04:22
	 * @param entity
	 * @return
	 */
	private ExpressLineScheduleEntity attahName(ExpressLineScheduleEntity entity) {
		if(null!=entity){
			//若出发部门编码不为空，设置部门名称
			if(StringUtils.isNotBlank(entity.getOriginDeptCode())){
				entity.setOriginDeptName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(entity.getOriginDeptCode()));
			}
			//若到达部门编码不为空，设置到达部门名称
			if(StringUtils.isNotBlank(entity.getArriveDeptCode())){
				entity.setArriveDeptName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(entity.getArriveDeptCode()));
			}
		}
		return entity;
	}
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午4:10:05
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(ExpressLineScheduleEntity entity) {
		if(null ==entity){
			entity =new ExpressLineScheduleEntity();
		}
		return expressLineScheduleDao.queryCount(entity);
	}
	/**
	 *<p>Title: deleteExpressLineScheduleByProgramName</p>
	 *<p>根据方案名称作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:30:47
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int deleteExpressLineScheduleByProgramName(
			ExpressLineScheduleEntity entity) {
		//非空校验
		if(null==entity ||StringUtils.isBlank(entity.getProgramName())){
			throw new ExpressTrainScheduleException("参数为空");
		}
		return expressLineScheduleDao.deleteExpressLineSchedule(entity);
	}
	/**
	 * 
	 *<p>Title: addExpressLineScheduleMore</p>
	 *<p>批量新增线路信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:46:21
	 * @param lineScheduleList
	 * @return
	 */
	@Override
	public int addExpressLineScheduleMore(
			List<ExpressLineScheduleEntity> lineScheduleList) {
		if(CollectionUtils.isEmpty(lineScheduleList)){
			throw new ExpressTrainScheduleException("集合为空");
		}
		int count =0;
		//批量新增
		for (ExpressLineScheduleEntity expressLineScheduleEntity : lineScheduleList) {
			count += this.addExpressLineSchedule(expressLineScheduleEntity);
		}
		return count==lineScheduleList.size()?NumberConstants.NUMBER_1:NumberConstants.ZERO;
	}
	/** 
	 *<p>Title: deleteLineScheduleListByLineName</p>
	 *<p>根据线路名称批量作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-15下午3:47:45
	 * @param lineNameList
	 * @param empCode
	 * @return
	 */
	@Override
	public int deleteLineScheduleListByLineName(List<String> lineNameList,
			String programName,String modifyUser) {
		if(CollectionUtils.isEmpty(lineNameList)||StringUtils.isBlank(modifyUser)||StringUtils.isBlank(programName)){
			throw new ExpressTrainScheduleException("参数为空");
		}
		int count =0;
		//批量作废线路
		for (String lineName : lineNameList) {
			ExpressLineScheduleEntity entity =new ExpressLineScheduleEntity();
			entity.setLineName(lineName);
			entity.setProgramName(programName);
			entity.setModifyUser(modifyUser);
			count +=this.deleteExpressLineSchedule(entity);
		}
		//根据方案名称查询
		ExpressTrainProgramEntity programEntity =expressTrainProgramService.queryExpressTrainProgramByProgramName(programName);
		//获取作废线路后的线路条数
		programEntity.setLineCount(programEntity.getLineCount()-lineNameList.size());
		//获取作废线路后的车辆条数
		programEntity.setVehicleCount(programEntity.getVehicleCount()-lineNameList.size());
		//设置更新人
		programEntity.setModifyUser(modifyUser);
		//更新方案
		expressTrainProgramService.updateExpressTrainProgram(programEntity);
		return count>=lineNameList.size()?NumberConstants.NUMBER_1:NumberConstants.ZERO;
	}
	/**
	 *<p>Title: queryDeptGisIdsByLineName</p>
	 *<p>根据线路名称，查询该线路所有部门GisId集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17上午9:53:03
	 * @param lineName
	 * @return
	 */
	@Override
	public List<OrgGisLongitudeDto> queryDeptGisIdsByLineName(String lineName,String programName) {
		if(StringUtils.isBlank(lineName)){
			throw new ExpressTrainScheduleException("线路名称为空");
		}
		List<ExpressLineScheduleEntity> resultList=expressLineScheduleDao.queryExpressLineScheduleListByLineName(lineName,programName);
		
		List<OrgGisLongitudeDto> orgGisIds =new ArrayList<OrgGisLongitudeDto>();
		List<String> orgNameList =new ArrayList<String>();
		//非空校验
		if(CollectionUtils.isNotEmpty(resultList)){
			for (ExpressLineScheduleEntity entity : resultList) {
				//非空校验（出发部门）
				if(null!=entity){
					//校验出发部门
					if(StringUtils.isNotBlank(entity.getOriginDeptCode())&&StringUtils.isNotBlank(entity.getOriginDeptGisId())){
						OrgGisLongitudeDto dto =convertGisDto(entity.getOriginDeptCode(), entity.getOriginDeptGisId());
						//若部门名称集合不包含该部门
						if(!orgNameList.contains(dto.getOrgName())){
							//封装部门信息集合
							orgNameList.add(dto.getOrgName());
							orgGisIds.add(dto);
						}
					}
					//校验到达部门
					if(StringUtils.isNotBlank(entity.getArriveDeptCode())&&StringUtils.isNotBlank(entity.getArriveDeptGisId())){
						OrgGisLongitudeDto dto =convertGisDto(entity.getArriveDeptCode(), entity.getArriveDeptGisId());
						//若部门名称集合不包含该部门
						if(!orgNameList.contains(dto.getOrgName())){
							//封装部门信息集合
							orgNameList.add(dto.getOrgName());
							orgGisIds.add(dto);
						}
					}
				}
			}
		}
		return orgGisIds;
	}
	/**
	 *<p>Title: convertGisDto</p>
	 *<p>解析为GIS坐标对象</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17下午2:49:43
	 * @param originDeptCode
	 * @param originDeptGisId
	 * @return
	 */
	private OrgGisLongitudeDto convertGisDto(String orgCode,
			String deptGisId) {
		if(StringUtils.isBlank(orgCode)||StringUtils.isBlank(deptGisId)){
			throw new ExpressTrainScheduleException("参数为空");
		}
		//获取部门名称
		OrgGisLongitudeDto dto =new OrgGisLongitudeDto();
		String name =orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode);
		dto.setOrgName(name);
		//获取部门经度
		dto.setLatitude(deptGisId.substring(0,deptGisId.indexOf(',')));
		//获取部门纬度
		dto.setLongitude(deptGisId.substring(deptGisId.indexOf(',')+1));
		return dto;
	}
	/**
	 *<p>Title: updateExpressLineSchedule</p>
	 *<p>更新线路</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17下午7:29:13
	 * @param entity
	 */
	@Override
	public int updateExpressLineSchedule(ExpressLineScheduleEntity entity) {
		if(null ==entity ||StringUtils.isBlank(entity.getId())){
			throw new ExpressTrainScheduleException("参数为空");
		}
		ExpressLineScheduleEntity deleteEntity = new ExpressLineScheduleEntity();
		deleteEntity.setId(entity.getId());
		deleteEntity.setModifyUser(entity.getModifyUser());
		//先作废
		int result =this.deleteExpressLineSchedule(deleteEntity);
		if(result<1){
			throw new ExpressTrainScheduleException("更新失败");
		}
		entity.setCreateUser(entity.getModifyUser());
		//再新增
		 int addResult =this.addExpressLineSchedule(entity);
		 if(addResult<1){
			 throw new ExpressTrainScheduleException("更新失败");
		 }
		 //更新方案表的修改人
		//根据方案名称查询
		ExpressTrainProgramEntity programEntity =expressTrainProgramService.queryExpressTrainProgramByProgramName(entity.getProgramName());
		programEntity.setModifyUser(entity.getCreateUser());
		//更新方案
		expressTrainProgramService.updateExpressTrainProgram(programEntity);
		return addResult;
	}

	/**
	 *<p>Title: exportLeaseVehicle</p>
	 *<p>导出线路信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-21上午11:40:54
	 * @param entity
	 * @return
	 */
	@Override
	public InputStream exportLeaseVehicle(ExpressLineScheduleEntity entity) {
		//查询线路信息
		List<ExpressLineScheduleEntity> resultList =this.queryExpressLineScheduleList(entity, 0, NumberConstants.NUMBER_2000);
		//非空校验
		if (CollectionUtils.isNotEmpty(resultList)) {
			//行信息
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (ExpressLineScheduleEntity expressLineScheduleEntity : resultList) {
				//列信息
				List<String> columnList = new ArrayList<String>();
				
				columnList.add(expressLineScheduleEntity.getProgramName());//方案名称
				columnList.add(expressLineScheduleEntity.getLineName());//线路名称
				columnList.add(expressLineScheduleEntity.getCityName());//城市
				columnList.add(expressLineScheduleEntity.getOriginDeptName());//出发部门
				columnList.add(expressLineScheduleEntity.getArriveDeptName());//到达部门
				columnList.add(expressLineScheduleEntity.getTravelDistance()+"");//行政距离
				columnList.add(expressLineScheduleEntity.getTravelTime()); //行驶时效
				SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:ss");
				columnList.add(String.valueOf(sdf.format(expressLineScheduleEntity.getDepartTime())));//出发时间
				columnList.add(String.valueOf(sdf.format(expressLineScheduleEntity.getArriveTime())));//到达时间
				rowList.add(columnList);
			}
			ExportResource exportResource = new ExportResource();
			ExportSetting exportSetting = new ExportSetting();
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			String[] rowHeads ={"方案名称","线路名称","城市","出发部门","到达部门","行驶距离","行驶时效","出发时间","到达时间"};
			exportResource.setHeads(rowHeads);
			exportSetting.setSheetName("线路时刻表信息");
			exportResource.setRowList(rowList);
			exportSetting.setSize(NumberConstants.NUMBER_2000);
			return objExporterExecutor.exportSync(exportResource, exportSetting);
		}
		return null;
	}

}
