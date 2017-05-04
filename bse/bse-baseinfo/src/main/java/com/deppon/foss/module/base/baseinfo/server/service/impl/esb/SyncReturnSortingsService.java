package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISortdestStationDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncReturnSortingsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SortdestStationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDataDicDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDicResponse;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationResponse;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationRestFulDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationRestFulInfoDto;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.define.FossConstants;

/**
* @ClassName:  
* @Description:   
* @author 130134
* @date 2015-4-28 上午9:42:28 
*
 */
public class SyncReturnSortingsService implements ISyncReturnSortingsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncReturnSortingsService.class);
	
	private ISortdestStationDao sortdestStationDao; 

	private IDataDictionaryValueDao dataDictionaryValueDao;

	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;

	public void setSortdestStationDao(ISortdestStationDao sortdestStationDao) {
		this.sortdestStationDao = sortdestStationDao;
	}

	public void setDataDictionaryValueDao(
			IDataDictionaryValueDao dataDictionaryValueDao) {
		this.dataDictionaryValueDao = dataDictionaryValueDao;
	}
 
	/**
	 * 
	* @Title: sortDestStationInsert 
	* @Description: 根据外场编码查询智能分拣柜基础资料接口
	* @param @param deptCode    
	* @return SortdestStationRestFulDto    返回类型 
	 */
	@Override
	public String querySortings(String deptCode) {
		LOGGER.info("------------------------智能分拣柜基础资料查询接口begin----------------------------");
		resp.setCharacterEncoding("utf-8");
		SortdestStationResponse response=new SortdestStationResponse();
		
		JSONObject obj = JSONObject.fromObject(deptCode);
		String orgCode =obj.getString("deptCode");
		if(StringUtils.isEmpty(deptCode)){
			response.setSuccess(false);
			response.setMessage("传入的外场编码为空！");
			return JSON.toJSONString(response);
		}
		SortdestStationEntity sortdestStationEntity=new SortdestStationEntity();
		sortdestStationEntity.setOrigOrgCode(orgCode);
		sortdestStationEntity.setStatus(FossConstants.ACTIVE);
		List<SortdestStationEntity> sortdestStationEntitys=sortdestStationDao.queryAll(sortdestStationEntity);
		
		if(CollectionUtils.isEmpty(sortdestStationEntitys)){
			response.setSuccess(false);
			response.setMessage("该外场没用可用的方案！");
			return JSON.toJSONString(response);
		}
		List<SortdestStationRestFulDto> dtos=new ArrayList<SortdestStationRestFulDto>();
		 for(SortdestStationEntity entity:sortdestStationEntitys){
			 
			    SortdestStationRestFulDto dto=new SortdestStationRestFulDto();
				dto.setOrigOrgCode(entity.getOrigOrgCode());
				dto.setOrigOrgName(entity.getOrigOrgName());
				dto.setSchemeCode(entity.getSchemeCode());
				dto.setSchemeName(entity.getScheme());
				
			 List<SortdestStationDto> sortdestStationDtos= entity.getSortdestStationDtos();
			 if(null==sortdestStationDtos){
				 continue;
			 }
			 List<SortdestStationRestFulInfoDto> sortdestStationRestFulInfoDtos=new ArrayList<SortdestStationRestFulInfoDto>();
			 for(SortdestStationDto sortdestStationDto:sortdestStationDtos){
					SortdestStationRestFulInfoDto sortdestStationRestFulInfoDto=new SortdestStationRestFulInfoDto();
					sortdestStationRestFulInfoDto.setBoxNo(sortdestStationDto.getBoxNo());
					sortdestStationRestFulInfoDto.setCellNo(sortdestStationDto.getCellNo());
					sortdestStationRestFulInfoDto.setDestOrgCode(sortdestStationDto.getDestOrgCode());
					sortdestStationRestFulInfoDto.setDestOrgName(sortdestStationDto.getDestOrgName());
					sortdestStationRestFulInfoDto.setPackAgeType(sortdestStationDto.getPackAgeType());
					sortdestStationRestFulInfoDtos.add(sortdestStationRestFulInfoDto);
				}
			 
			 dto.setSortdestStationRestFulInfoDtos(sortdestStationRestFulInfoDtos);
			 dtos.add(dto);
		 }
		 response.setSuccess(true);
		 response.setSortdestStationRestFulDtos(dtos);
		 String res=JSON.toJSONString(response);
		return res;
	}

	/**
	 * 
	* @Title: sortDestStationInsert 
	* @Description: 数据字典接口
	* @param @param      
	* @return List<SortdestStationDataDictionaryValueDto>    返回类型 
	 */
	@Override
	public String queryDataDictionarys() {
		LOGGER.info("------------------------智能分拣柜数据字典查询接口begin----------------------------");
		resp.setCharacterEncoding("utf-8");
		SortdestStationDicResponse response=new SortdestStationDicResponse();
		List<DataDictionaryValueEntity> dataDictionaryEntitys = dataDictionaryValueDao
				.queryDataDictionaryValueByTermsCode("SORTING_PARAMETER_TYPE");

		if(CollectionUtils.isEmpty(dataDictionaryEntitys)){
			response.setSuccess(false);
			response.setMessage("没有找到有效的数据,请联系业务组！");
			return JSON.toJSONString(response);
		}
		
		List<SortdestStationDataDicDto> dataDics = new ArrayList<SortdestStationDataDicDto>();
		for (DataDictionaryValueEntity entity : dataDictionaryEntitys) {
			SortdestStationDataDicDto dto = new SortdestStationDataDicDto();
			dto.setValueCode(entity.getValueCode());
			dto.setValueName(entity.getValueName());
			dto.setValue(entity.getExtAttribute1());
			dataDics.add(dto);
		}
		LOGGER.info("------------------------智能分拣柜数据字典查询接口end----------------------------");
		
		response.setSuccess(true);
		response.setSortdestStationDataDicDtos(dataDics);
		String res=JSON.toJSONString(response);
		return res;
	}

}
