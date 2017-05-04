package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionAreaPlanDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionAreaPlanService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionAreaPlanEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionAreaPlanException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
/**
 * 展馆区域规划Service
 * @author 187862
 * @date 2015-07-07 下午4:41:36
 *
 */
public class ExhibitionAreaPlanService implements IExhibitionAreaPlanService {

	/**
	 * 行政区域Service
	 */
	private IAdministrativeRegionsService administrativeRegionsService; 
	/**
	 * 展馆区域规划Dao
	 */
	private IExhibitionAreaPlanDao exhibitionAreaPlanDao;
	
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setExhibitionAreaPlanDao(
			IExhibitionAreaPlanDao exhibitionAreaPlanDao) {
		this.exhibitionAreaPlanDao = exhibitionAreaPlanDao;
	}

	/**
	 * 新增展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午4:53:26
	 */
	@Transactional
	@Override
	public int addExhibitionAreaPlan(ExhibitionAreaPlanEntity entity)
			throws ExhibitionAreaPlanException {
		if(entity==null){
			throw new ExhibitionAreaPlanException("新增展馆区域规划信息为空！");
		}
		if(StringUtil.isNotEmpty(entity.getExhibitionName())){//展馆名称去空格
			entity.setExhibitionName(entity.getExhibitionName().trim());
		}
		//展馆编码唯一
		ExhibitionAreaPlanEntity codeEntity=exhibitionAreaPlanDao.
				queryExhibitionAreaPlanByCode(entity.getExhibitionCode());
		if(codeEntity!=null){
			throw new ExhibitionAreaPlanException("该展馆编码已存在，请重新选择管理部门生成展馆编码！");
		}
		//展馆名称唯一
		ExhibitionAreaPlanEntity nameEntity=exhibitionAreaPlanDao.
				queryExhibitionAreaPlanByName(entity.getExhibitionName());
		if(nameEntity!=null){
			throw new ExhibitionAreaPlanException("该展馆名称已存在，不允许重复添加！");
		}
		//封装省市区名称
		this.convertDistrictNameByCode(entity);
		return exhibitionAreaPlanDao.addExhibitionAreaPlan(entity);
	}

	/**
	 * 更新展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-10 下午2:30:03
	 */
	@Transactional
	@Override
	public int updateExhibitionAreaPlanByCode(ExhibitionAreaPlanEntity entity) {
		if(entity==null){
			throw new ExhibitionAreaPlanException("修改展馆区域规划信息为空！");
		}
		if(StringUtil.isNotEmpty(entity.getExhibitionName())){//展馆名称去空格
			entity.setExhibitionName(entity.getExhibitionName().trim());
		}
		//判断展馆编码、展馆名称业务规则
		ExhibitionAreaPlanEntity codeEntity=exhibitionAreaPlanDao.
				queryExhibitionAreaPlanByCode(entity.getExhibitionCode());
		if(codeEntity!=null){//数据库中已存在
			if(!StringUtil.equals(entity.getVirtualCode(), codeEntity.getVirtualCode())){//判断该实体是否为本次修改数据
				if(StringUtil.equals(entity.getExhibitionCode(), codeEntity.getExhibitionCode())){
					throw new ExhibitionAreaPlanException("修改后的展馆编码已存在于系统中，请重新选择管理部门生成展馆编码！");
				}
			}
		}
		
		ExhibitionAreaPlanEntity nameEntity=exhibitionAreaPlanDao.
				queryExhibitionAreaPlanByName(entity.getExhibitionName());
		if(nameEntity!=null){//数据库中已存在
			if(!StringUtil.equals(entity.getVirtualCode(), nameEntity.getVirtualCode())){//判断该实体是否为本次修改数据
				if(StringUtil.equals(entity.getExhibitionName(), nameEntity.getExhibitionName())){
					throw new ExhibitionAreaPlanException("修改后的展馆名称已存在于系统中，不允许重复添加！");
				}
			}
		}
		
		//先作废，再新增
		String[] virCodes=new String[]{entity.getVirtualCode()};
		String modifyUser=FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		int i=exhibitionAreaPlanDao.deleteExhibitionAreaPlanByVirCode(virCodes, modifyUser);
		int j=0;
		if(i>0){
			//封装省市区名称
			this.convertDistrictNameByCode(entity);
			j=exhibitionAreaPlanDao.addExhibitionAreaPlan(entity);
		}
		
		return i>0 && j>0 ? NumberConstants.NUMERAL_ONE:NumberConstants.NUMERAL_ZORE;
	}
	
	/**
	 * 作废展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午4:54:12
	 */
	@Transactional
	@Override
	public int deleteExhibitionAreaPlanByCode(String[] codes, String modifyUser) {
		return exhibitionAreaPlanDao.deleteExhibitionAreaPlanByCode(codes, modifyUser);
	}

	/**
	 * 根据条件查询展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午4:55:36
	 */
	@Override
	public List<ExhibitionAreaPlanEntity> queryExhibitionAreaPlanByCondition(
			ExhibitionAreaPlanEntity entity, int limit, int start) {
		if(entity==null){
			entity=new ExhibitionAreaPlanEntity();
		}
		List<ExhibitionAreaPlanEntity> list=exhibitionAreaPlanDao.queryExhibitionAreaPlans(entity, limit, start);
		return CollectionUtils.isEmpty(list)?null:list;
	}

	/**
	 * 根据条件统计展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午4:58:24
	 */
	@Override
	public Long queryRecordCount(ExhibitionAreaPlanEntity entity) {
		return exhibitionAreaPlanDao.queryRecordCount(entity);
	}

	/**
	 * 根据展馆编码查询信息
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午5:02:57
	 */
	@Override
	public ExhibitionAreaPlanEntity queryExhibitionAreaPlanByCode(
			String exhibitionCode) {
		return exhibitionAreaPlanDao.queryExhibitionAreaPlanByCode(exhibitionCode);
	}

	/**
	 * 根据展馆名称查询信息
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午5:12:59
	 */
	@Override
	public ExhibitionAreaPlanEntity queryExhibitionAreaPlanByName(
			String exhibitionName,String virtualCode) {
		if(StringUtil.isNotEmpty(exhibitionName)){
			exhibitionName=exhibitionName.trim();
		}
		//根据viewState判断在更新时确保展馆名与非本条修改数据展馆名相同
		ExhibitionAreaPlanEntity nameEntity=exhibitionAreaPlanDao.
				queryExhibitionAreaPlanByName(exhibitionName);
		if(nameEntity!=null){//数据库中已存在
			if(StringUtil.isNotEmpty(virtualCode)){//虚拟编码不为空，即为修改操作时判断名称
				if(!StringUtil.equals(virtualCode, nameEntity.getVirtualCode())){//判断该实体是否为本次修改数据
					if(StringUtil.equals(exhibitionName, nameEntity.getExhibitionName())){
						throw new ExhibitionAreaPlanException("修改后的展馆名称已存在于系统中，不允许重复添加！");
					}
				}else{//修改操作为自身名称
					return null;
				}
			}else{//虚拟编码为空，即为新增操作时判断名称
				//暂不做处理
			}
		}
		
		return exhibitionAreaPlanDao.queryExhibitionAreaPlanByName(exhibitionName);
	}

	/**
	 * 根据管理部门查询展馆区域规划信息最大编码
	 */
	@Override
	public String queryCodeByManagement(ExhibitionAreaPlanEntity entity) {
		return exhibitionAreaPlanDao.queryCodeByManagement(entity);
	}
	
	/**
   	 * 根据管理部门编码自动生成展馆编码
   	 * @param entity
   	 * @return
   	 * @param @param entity
   	 * @param @return
   	 * @author 187862
   	 * @date 2015-7-9 上午10:46:38
   	 */
	@Override
	public String createCodeByManagement(ExhibitionAreaPlanEntity entity) {
		String regionCode=null;
		String tuncatStr="Y"+entity.getManagementCode();
		entity.setActive("Y");
		String maxCode=this.queryCodeByManagement(entity);
		if(!StringUtil.isEmpty(maxCode)){
			String truncatRegionCode=maxCode.substring(tuncatStr.length(), maxCode.length());
			int number=Integer.parseInt(truncatRegionCode)+1;
			String strCode = String.valueOf(number);
			if(strCode.length()==NumberConstants.NUMERAL_ONE){
				 strCode="00"+strCode;
			}else if(strCode.length()==NumberConstants.NUMERAL_TWO){
				 
				 strCode="0"+strCode;
			}
			regionCode=tuncatStr+strCode;
		}else{
			regionCode=tuncatStr+"001";
		}
		return regionCode;
	}
	
	/**
	 * 根据行政区域编码封装行政区域名称
	 * @author 187862-dujunhui
	 * @date 2015-7-10 下午5:03:22
	 */
	private void convertDistrictNameByCode(ExhibitionAreaPlanEntity entity){
		if(StringUtil.isNotEmpty(entity.getProvCode())){
			entity.setProvName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getProvCode()));
		}
		if(StringUtil.isNotEmpty(entity.getCityCode())){
			entity.setCityName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCityCode()));
		}
		if(StringUtil.isNotEmpty(entity.getCountyCode())){
			entity.setCountyName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCountyCode()));
		}
	}

}