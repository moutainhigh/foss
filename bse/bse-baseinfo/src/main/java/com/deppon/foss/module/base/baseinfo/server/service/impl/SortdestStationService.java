package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISortdestStationDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISortdestStationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SortdestStationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SortdestStationException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class SortdestStationService implements ISortdestStationService {

	/**
	 * 
	 */
	private ISortdestStationDao sortdestStationDao;
	
	public void setSortdestStationDao(ISortdestStationDao sortdestStationDao) {
		this.sortdestStationDao = sortdestStationDao;
	}
	 
	@Override
	public List<SortdestStationEntity> queryAll(
			SortdestStationEntity sortdestStationEntity,int start,int limit) {
		 
		return sortdestStationDao.queryAll(sortdestStationEntity,start,limit);
	}

	/**
	 * 
	 */
	@Override
	public void sortDestStationInsert(
			SortdestStationEntity sortdestStationEntity) {
		SortdestStationEntity queryParam=new SortdestStationEntity();
		queryParam.setActive(FossConstants.ACTIVE);
		queryParam.setScheme(sortdestStationEntity.getScheme().trim());
		List<SortdestStationEntity> sortdestStations=sortdestStationDao.queryAll(queryParam);
		
		if(null!=sortdestStations&&sortdestStations.size()>0){
			
			 throw new SortdestStationException("该方案已经存在！","该方案已经存在！");
		}
		
		sortdestStationEntity.setId(UUIDUtils.getUUID());
		sortdestStationEntity.setStatus(FossConstants.INACTIVE);
		sortdestStationEntity.setActive(FossConstants.ACTIVE);
		sortdestStationEntity.setVirtualCode(UUIDUtils.getUUID());
		sortdestStationEntity.setCreateDate(new Date());
		sortdestStationEntity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		sortdestStationDao.sortDestStationInsert(sortdestStationEntity);
		
	}
	/**
	 * 
	 */
	@Override
	public void sortDestStationUpdate(
			SortdestStationEntity sortdestStationEntity) {
		
		//判断方案名称是否唯一
		SortdestStationEntity queryParam=new SortdestStationEntity();
		queryParam.setActive(FossConstants.ACTIVE);
		queryParam.setScheme(sortdestStationEntity.getScheme().trim());
		
		List<SortdestStationEntity> sortdestStations=sortdestStationDao.queryAll(queryParam);
		if(!CollectionUtils.isEmpty(sortdestStations)&&sortdestStations.size()>0){
			SortdestStationEntity compareEntity=sortdestStations.get(0);
			 if(sortdestStations.size()>1||
					 !StringUtils.equals(compareEntity.getId(), sortdestStationEntity.getId())){
				 throw new SortdestStationException("该方案已经存在！","该方案已经存在！");
			 } 
		}
		//作废原纪录
		SortdestStationEntity delEntiyt=new SortdestStationEntity();
		delEntiyt.setActive(FossConstants.INACTIVE);
		delEntiyt.setId(sortdestStationEntity.getId());
		delEntiyt.setModifyDate(new Date());
		delEntiyt.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		sortdestStationDao.sortDestStationUpdate(delEntiyt);
		
		//新增数据
		sortdestStationEntity.setId(UUIDUtils.getUUID());
//		sortdestStationEntity.setStatus("N");
		sortdestStationEntity.setActive(FossConstants.ACTIVE);
		sortdestStationEntity.setVirtualCode(sortdestStationEntity.getVirtualCode());
		sortdestStationEntity.setCreateDate(new Date());
		sortdestStationEntity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		sortdestStationDao.sortDestStationInsert(sortdestStationEntity);
		
		
	}



	@Override
	public Long countAll(SortdestStationEntity sortdestStationEntity) {
		 
		return sortdestStationDao.countAll(sortdestStationEntity);
	}

	/**
	 * 作废以及禁用启用
	 */
	@Override
	public void sortDestStationDelByCode(String codeStr,String active) {
		
		String [] codes=codeStr.split(",");
		
		if(codes.length<=0){
			
			 throw new SortdestStationException("请刷新页面再次删除！","请刷新页面再次删除！");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(active)){
			map.put("active", FossConstants.INACTIVE);
			map.put("status", FossConstants.INACTIVE);
			Map<String, Object> childmap = new HashMap<String, Object>();
			childmap.put("parentVirtualCodes", codes);
			childmap.put("active", FossConstants.INACTIVE);
			childmap.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
			childmap.put("modifyDate", new Date());
			sortdestStationDao.sortDestStationInfoDel(childmap);
		}
		if(StringUtils.equals(active, FossConstants.INACTIVE)){
			map.put("status", FossConstants.INACTIVE);
		}
		if(StringUtils.equals(active, FossConstants.ACTIVE)){
		/*	if(codes.length<2&&codes.length>0){
				SortdestStationEntity sortdestStationEntity=new SortdestStationEntity();
				sortdestStationEntity.setStatus(FossConstants.ACTIVE);
				sortdestStationEntity.setOrigOrgCode(codes[0]);
				Long l=sortdestStationDao.countAll(sortdestStationEntity);
				if(l>0){
					throw new SortdestStationException("一个外场只能启用一种方案！","一个外场只能启用一种方案！");
				}
			}*/
			map.put("status", FossConstants.ACTIVE);
		}
		map.put("codes", codes);
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		map.put("modifyDate", new Date());
		
		sortdestStationDao.sortDestStationDeleteByCode(map);
	}

	//-------------------------------------------------------------------------------------------------
	/**
	 * 
	 */
	@Override
	public List<SortdestStationDto> querySortChildAll(
			SortdestStationDto sortdestStationDto, int start, int limit) {
		 
		
		return sortdestStationDao.querySortChildAll(sortdestStationDto, start, limit);
	}

	/**
	 * 
	 */
	@Override
	public Long countAllSortChild(SortdestStationDto sortdestStationDto) {
		 
		return sortdestStationDao.countAllSortChild(sortdestStationDto);
	}
	/**
	 * 
	 */
	@Override
	public void sortDestStationInfoInserts(
			List<SortdestStationDto> sortdestStationDtos) {
		 
		
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public void sortDestStationInfoUpdate(SortdestStationDto sortdestStationDto) {
		if(StringUtils.isEmpty(sortdestStationDto.getParentVirtualCode())){
			 throw new SortdestStationException("请刷新页面重新操作！","请刷新页面重新操作！");
		}
		
		//---------------------
		SortdestStationDto delCondetion=new SortdestStationDto();
		delCondetion.setId(sortdestStationDto.getId());
		delCondetion.setActive(FossConstants.INACTIVE);
		delCondetion.setModifyDate(new Date());
		delCondetion.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		sortdestStationDao.sortDestStationInfoUpdate(delCondetion);
		//-------------------------
		SortdestStationDto queryCondetion=new SortdestStationDto();
		queryCondetion.setBoxNo(sortdestStationDto.getBoxNo());
		queryCondetion.setCellNo(sortdestStationDto.getCellNo());
		queryCondetion.setActive(FossConstants.ACTIVE);
		queryCondetion.setParentVirtualCode(sortdestStationDto.getParentVirtualCode());
		List<SortdestStationDto> sortdestStationDtos=sortdestStationDao.querySortChildAll(queryCondetion);
		
		if(null!=sortdestStationDtos&&sortdestStationDtos.size()>0){
			 throw new SortdestStationException("格口号已存在！","格口号已存在！");
		}
		sortdestStationDto.setId(UUIDUtils.getUUID());
		sortdestStationDto.setActive(FossConstants.ACTIVE);
		sortdestStationDto.setCreateDate(new Date());
		sortdestStationDto.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		sortdestStationDao.sortDestStationInfoInsert(sortdestStationDto);
	}

	/**
	 * 
	 */
	@Override
	public void sortDestStationInfoInsert(SortdestStationDto sortdestStationDto) {
		if(StringUtils.isEmpty(sortdestStationDto.getParentVirtualCode())){
			 throw new SortdestStationException("请刷新页面重新操作！","请刷新页面重新操作！");
		}
		SortdestStationDto queryCondetion=new SortdestStationDto();
		queryCondetion.setBoxNo(sortdestStationDto.getBoxNo());
		queryCondetion.setCellNo(sortdestStationDto.getCellNo());
		queryCondetion.setParentVirtualCode(sortdestStationDto.getParentVirtualCode());
		List<SortdestStationDto> sortdestStationDtos=sortdestStationDao.querySortChildAll(queryCondetion);
		
		if(null!=sortdestStationDtos&&sortdestStationDtos.size()>0){
			 throw new SortdestStationException("格口号已存在！","格口号已存在！");
		}
		sortdestStationDto.setActive(FossConstants.ACTIVE);
		sortdestStationDto.setId(UUIDUtils.getUUID());
		sortdestStationDto.setCreateDate(new Date());
		sortdestStationDto.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		sortdestStationDao.sortDestStationInfoInsert(sortdestStationDto);
	}

	/**
	 * 
	 */
	@Override
	public void sortDestStationInfoDel(String codeStr) {
		String [] codes=codeStr.split(",");
		
		if(codes.length<=0){
			
			 throw new SortdestStationException("请刷新页面再次删除！","请刷新页面再次删除！");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("active", FossConstants.INACTIVE);
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		map.put("modifyDate", new Date());
		
		sortdestStationDao.sortDestStationInfoDel(map);
		
	}
	

}
