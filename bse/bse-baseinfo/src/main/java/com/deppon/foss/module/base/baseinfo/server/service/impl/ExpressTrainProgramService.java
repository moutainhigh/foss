package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressTrainProgramDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressTrainProgramService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncProgramInfoToGisService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressTrainProgramDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SysToGisOrgDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressTrainScheduleException;
import com.deppon.foss.util.CollectionUtils;
/**
 *<p>Title: ExpressTrainProgramService</p>
 * <p>Description:快递支线班车方案Service </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-12
 */
public class ExpressTrainProgramService implements IExpressTrainProgramService {
	/**
	 * 日志打印类
	 */
	 private static final Logger log = Logger.getLogger(ExpressTrainProgramService.class);
	/**
	 * 快递支线班车方案dao
	 */
	private IExpressTrainProgramDao expressTrainProgramDao;
	/**
	 * 组织Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 营业部Service 
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 员工Service
	 */
	private IEmployeeService employeeService;
	/**
	 * 向GIS同步信息Service
	 */
	private ISyncProgramInfoToGisService syncProgramInfoToGisService;
	/**
	 * 快递线路时刻表service
	 */
	private IExpressLineScheduleService expressLineScheduleService;
	
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setExpressLineScheduleService(
			IExpressLineScheduleService expressLineScheduleService) {
		this.expressLineScheduleService = expressLineScheduleService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setExpressTrainProgramDao(
			IExpressTrainProgramDao expressTrainProgramDao) {
		this.expressTrainProgramDao = expressTrainProgramDao;
	}
	
	public void setSyncProgramInfoToGisService(
			ISyncProgramInfoToGisService syncProgramInfoToGisService) {
		this.syncProgramInfoToGisService = syncProgramInfoToGisService;
	}

	/**
	 *<p>Title: addExpressTrainProgram</p>
	 *<p>新增快递支线班车方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午2:26:11
	 * @param dto
	 * @return
	 */
	@Transactional
	@Override
	public ExpressTrainProgramEntity addExpressTrainProgram(
			ExpressTrainProgramDto dto) {
		//非空校验
		if(null==dto ||null==dto.getExpressTrainProgramEntity()||StringUtils.isBlank(dto.getExpressTrainProgramEntity().getProgramName())){
			throw new ExpressTrainScheduleException("参数为空");
		}
		//数据库中查询
		ExpressTrainProgramEntity entityResult =this.queryExpressTrainProgramByProgramName(dto.getExpressTrainProgramEntity().getProgramName());
		//若数据库中有该名称的方案，不能再新增
		if(null !=entityResult){
			throw new ExpressTrainScheduleException(dto.getExpressTrainProgramEntity().getProgramName()+"的班车方案系统已经存在，不能再生成");
		}
		//设置营业部数量，
		dto.getExpressTrainProgramEntity().setSalesDeptCount(dto.getSalesList().size());
		//执行新增操作
		ExpressTrainProgramEntity result =expressTrainProgramDao.addExpressTrainProgram(dto.getExpressTrainProgramEntity());
		///若新增成功，同步给GIS
		if(null !=result){
			boolean flag=syncToGis(dto);
			if(!flag){
				throw new ExpressTrainScheduleException("同步给GIS失败，请作废该方案，重新生成方案");
			}
		}
		return result;
	}
	/**
	 *<p>Title: syncToGis</p>
	 *<p>向GIS同步数据</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14上午10:06:04
	 * @param dto
	 */
	private boolean syncToGis(ExpressTrainProgramDto dto) {
		//校验非空
		if(null ==dto||null ==dto.getExpressTrainProgramEntity()||StringUtils.isBlank(dto.getExpressTrainProgramEntity().getOriginOutfieldCode())){
			return false;
		}
		//校验营业部集合
		if(CollectionUtils.isEmpty(dto.getSalesList())){
			return false;
		}
		boolean result =false;
		try {
			//封装dto
			dto=packagingDto(dto);
			result =syncProgramInfoToGisService.syncProgramInfo(dto);
		} catch(ExpressTrainScheduleException e){
			throw new ExpressTrainScheduleException(e.getMessage());
		}catch (Exception e) {
			log.error("发送到GiS出错", e);
			result =false;
		}
		return result;
	}
	/**
	 *<p>Title: packagingDto</p>
	 *<p>封装Dto</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14上午10:20:35
	 * @param dto
	 * @return
	 */
	private ExpressTrainProgramDto packagingDto(ExpressTrainProgramDto dto) {
		// 校验非空
		if (null == dto
				|| null == dto.getExpressTrainProgramEntity()
				|| StringUtils.isBlank(dto.getExpressTrainProgramEntity()
						.getOriginOutfieldCode())) {
			return null;
		}
		// 校验营业部集合
		if (CollectionUtils.isEmpty(dto.getSalesList())) {
			return null;
		}
		//获取出发外场的驻地营业部
		String  saleCode =saleDepartmentService.queryStationSaleCodeByTransferCenterCode(dto.getExpressTrainProgramEntity()
						.getOriginOutfieldCode());
		//获取外场的驻地营业部的服务坐标编码
		OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleCode);
		//出发外场
		SysToGisOrgDto outFieldDto = new SysToGisOrgDto();
		outFieldDto.setOrgCode(dto.getExpressTrainProgramEntity().getOriginOutfieldCode());
		outFieldDto.setOrgName(dto.getExpressTrainProgramEntity().getOriginOutfieldName());
		if(StringUtils.isBlank(org.getDepCoordinate())){
			throw new ExpressTrainScheduleException(dto.getExpressTrainProgramEntity().getOriginOutfieldName()+"对应的驻地部门"+org.getName() +"的部门服务坐标编码为空，无法解析经纬度");
		}
		outFieldDto.setOrgCoordinate(org.getDepCoordinate());
		dto.setOutFieldDto(outFieldDto);
		//设置营业部
		List<SysToGisOrgDto> gisOrgDtos =new ArrayList<SysToGisOrgDto>();
		for (SaleDepartmentEntity sales :dto.getSalesList()) {
			SysToGisOrgDto salesDto =new SysToGisOrgDto();
			OrgAdministrativeInfoEntity dept =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(sales.getCode());
			if(null !=dept){
				//r若GISID为空
				if(StringUtils.isBlank(dept.getDepCoordinate())){
					throw new ExpressTrainScheduleException(dept.getName() +"的部门服务坐标编码为空，无法解析经纬度");
				}
				salesDto.setOrgCode(dept.getCode());
				salesDto.setOrgName(dept.getName());
				salesDto.setOrgCoordinate(dept.getDepCoordinate());
				gisOrgDtos.add(salesDto);
			}
		}
		//设置营业部集合
		dto.setSalesDtoList(gisOrgDtos);
		return dto;
	}

	/**
	 *<p>Title: queryExpressTrainProgramList</p>
	 *<p>分页查询方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午2:26:31
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<ExpressTrainProgramEntity> queryExpressTrainProgramList(
			ExpressTrainProgramEntity entity, int start, int limit) {
		if(null ==entity){
			entity =new ExpressTrainProgramEntity();
		}
		List<ExpressTrainProgramEntity> resultList=expressTrainProgramDao.queryExpressTrainProgramList(entity, start, limit);
		return attahName(resultList);
	}
	/**
	 * 
	 *<p>Title: attahName</p>
	 *<p>添加名称</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-21上午9:54:12
	 * @param resultList
	 * @return
	 */
	private List<ExpressTrainProgramEntity> attahName(
			List<ExpressTrainProgramEntity> resultList) {
		if(CollectionUtils.isEmpty(resultList) ){
			return null;
		}
		List<ExpressTrainProgramEntity> list =new ArrayList<ExpressTrainProgramEntity>();
		for (ExpressTrainProgramEntity expressTrainProgramEntity : resultList) {
			list.add(attahName(expressTrainProgramEntity));
		}
		return list;
	}
	/**
	 * 
	 *<p>Title: attahName</p>
	 *<p>添加名称</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-21上午9:56:41
	 * @param expressTrainProgramEntity
	 * @return
	 */
	private ExpressTrainProgramEntity attahName(
			ExpressTrainProgramEntity expressTrainProgramEntity) {
		if(null ==expressTrainProgramEntity){
			return null;
		}
		if(StringUtils.isNotBlank(expressTrainProgramEntity.getModifyUser())&& (!StringUtils.equals("GIS",expressTrainProgramEntity.getModifyUser()))){
			expressTrainProgramEntity.setModifyUserName(employeeService.queryEmpNameByEmpCode(expressTrainProgramEntity.getModifyUser()));
		}else if(StringUtils.isNotBlank(expressTrainProgramEntity.getModifyUser())&& StringUtils.equals("GIS",expressTrainProgramEntity.getModifyUser())){
			expressTrainProgramEntity.setModifyUserName("GIS重新生成");
		}
		return expressTrainProgramEntity;
	}

	/**
	 *<p>Title: queryCount</p>
	 *<p>查询记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午2:27:01
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(ExpressTrainProgramEntity entity) {
		if(null ==entity){
			entity =new ExpressTrainProgramEntity();
		}
		return expressTrainProgramDao.queryCount(entity);
	}
	/**
	 *<p>Title: queryExpressTrainProgramByProgramName</p>
	 *<p>根据方案名称查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:11:09
	 * @param ProgramName
	 * @return
	 */
	@Override
	public ExpressTrainProgramEntity queryExpressTrainProgramByProgramName(
			String programName) {
		if(StringUtils.isBlank(programName)){
			throw new ExpressTrainScheduleException("方案名称为空");
		}
		return expressTrainProgramDao.queryExpressTrainProgramByProgramName(programName);
	}
	/**
	 *<p>Title: deleteProgramList</p>
	 *<p>批量作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:16:43
	 * @param programList
	 * @param modifyUser
	 * @return
	 */
	@Override
	public int deleteProgramList(List<ExpressTrainProgramEntity> programList,
			String modifyUser) {
		if(CollectionUtils.isEmpty(programList)||StringUtils.isBlank(modifyUser)){
			throw new ExpressTrainScheduleException("参数为空");
		}
		int count =0;
		//批量作废
		for (ExpressTrainProgramEntity entity : programList) {
			entity.setModifyUser(modifyUser);
			entity.setId("");
			//根据方案名称来作废，不要根据ID
			count +=deleteProgram(entity);
		}
		return count==programList.size()?NumberConstants.NUMBER_1:NumberConstants.ZERO;
	}
	/**
	 *<p>Title: deleteProgram</p>
	 *<p>作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:18:48
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int deleteProgram(ExpressTrainProgramEntity entity) {
		//非空校验
		if(null==entity||StringUtils.isBlank(entity.getProgramName())){
			throw new ExpressTrainScheduleException("参数为空，无法作废");
		}
		//先作废子表(快递班车线路时刻表信息)
		ExpressLineScheduleEntity expressLineScheduleEntity =new ExpressLineScheduleEntity();
		expressLineScheduleEntity.setModifyUser(entity.getModifyUser());
		expressLineScheduleEntity.setProgramName(entity.getProgramName());
		expressLineScheduleService.deleteExpressLineScheduleByProgramName(expressLineScheduleEntity);
		//然后再作废方案
		return expressTrainProgramDao.deleteExpressTrainProgram(entity);
	}
	/**
	 * 
	 *<p>Title: updateExpressTrainProgram</p>
	 *<p>更新方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:02:26
	 * @param programEntity
	 * @return
	 */
	@Transactional
	@Override
	public int updateExpressTrainProgram(ExpressTrainProgramEntity programEntity) {
		//非空校验
		if(null==programEntity||StringUtils.isBlank(programEntity.getId())||StringUtils.isBlank(programEntity.getProgramName())){
			throw new ExpressTrainScheduleException("参数为空");
		}
		return expressTrainProgramDao.updateExpressTrainProgram(programEntity);
	}

}
