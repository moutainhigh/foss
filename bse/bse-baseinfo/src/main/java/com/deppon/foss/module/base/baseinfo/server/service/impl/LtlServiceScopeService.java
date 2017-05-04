package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILtlServiceScopeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILtlServiceScopeService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LtlServiceScopeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LtlServiceScopeInfoException;

public class LtlServiceScopeService implements ILtlServiceScopeService {

	/**
	 * “中国”的编码CODE
	 */
	private final String chinaCode = "100000";
	/**
	 * “行政区域”的编码CODE
	 */
	private final String districtCode = "01";

	private ILtlServiceScopeDao ltlServiceScopeDao;

	public void setLtlServiceScopeDao(ILtlServiceScopeDao ltlServiceScopeDao) {
		this.ltlServiceScopeDao = ltlServiceScopeDao;
	}

	/**
	 * 精确查询 通过 行政区域编码CODE 查询行政区域信息
	 * 
	 * @Title: queryDistrictByCode
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:37:12
	 * @throws
	 */
	public LtlServiceScopeDto queryDistrictByCode(String code) {
		if (StringUtils.isBlank(code)) {
			throw new LtlServiceScopeInfoException("传入的参数值不能为空");
		}
		LtlServiceScopeDto entity = ltlServiceScopeDao.queryDistrictByCode(code);
		if (entity != null) {
			HashMap<String, String> fullPath = loadEntityFullPath(entity);
			String fullCodePath = fullPath.get("fullCodePath");
			String fullNamePath = fullPath.get("fullNamePath");
			entity.setFullCodePath(fullCodePath);
			entity.setFullNamePath(fullNamePath);
		}
		return entity;
	}

	/**
	 * 根据name查询行政区域，迭代查询该行政区域的上级，直到上级为“中国”
	 * 
	 * @Title: queryDistrictByName
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:37:37
	 * @throws
	 */
	public LtlServiceScopeDto queryDistrictByName(String name) {
		LtlServiceScopeDto entity = ltlServiceScopeDao.queryDistrictByName(name);
		if (entity != null) {
			HashMap<String, String> fullPath = loadEntityFullPath(entity);
			String fullCodePath = fullPath.get("fullCodePath");
			String fullNamePath = fullPath.get("fullNamePath");
			entity.setFullCodePath(fullCodePath);
			entity.setFullNamePath(fullNamePath);
		}
		return entity;
	}

	/**
	 * 遍历查询各城市的上级部门，当上级部门等于的编码CODE是100000（中国）或者 01 （行政区域）的时候，不再查询
	 * 
	 * @Title: loadEntityFullPath
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:37:48
	 * @throws
	 */
	private HashMap<String, String> loadEntityFullPath(LtlServiceScopeDto entity) {
		StringBuffer fullCodePath = new StringBuffer(entity.getCode());// 根据NAME查询到的城市CODE
		StringBuffer fullNamePath = new StringBuffer(entity.getName());// 根据NAME查询到的城市NAME
		LtlServiceScopeDto parentEntity = ltlServiceScopeDao//
				.queryDistrictByCode(entity.getParentDistrictCode());// 查询当前城市的上级城市
		String parentCode = entity.getParentDistrictCode();// 当前城市的上级CODE
		String parentName = entity.getParentDistrictName();// 当前城市的上级NAME
		// 上级不为空，进行拼接
		if (null != parentCode) {
			fullCodePath.insert(0, parentCode + ".");// 拼接上级CODE
			fullNamePath.insert(0, parentName + "-");// 拼接上级NAME
			// 当上级CODE不等于100000 “中国” 或者 01 “行政区域”
			while (!parentCode.equals(chinaCode) && !parentCode.equals(districtCode)) {
				parentEntity = ltlServiceScopeDao//
						.queryDistrictByCode(parentEntity.getParentDistrictCode());// 查询上级城市的上级
				parentCode = parentEntity.getCode();
				parentName = parentEntity.getName();
				fullCodePath.insert(0, parentCode + ".");
				if (!parentName.equals("中国")) {// 不等于“中国”才拼接
					fullNamePath.insert(0, parentName + "-");
				}
			}
		}
		fullCodePath.insert(0, ".");
		HashMap<String, String> fullPath = new HashMap<String, String>();
		fullPath.put("fullCodePath", fullCodePath.toString());
		fullPath.put("fullNamePath", fullNamePath.toString());
		return fullPath;
	}

	/**
	 * 查询该行政区域的营业网点信息和派送范围信息
	 * 
	 * @Title: queryLtlServiceScopeInfo
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午3:52:28
	 * @throws
	 */
	public List<LtlServiceScopeDto> queryLtlServiceScopeInfo(LtlServiceScopeDto dto) {
		if (dto == null) {
			throw new LtlServiceScopeInfoException("查询无此行政区域");
		}
		return ltlServiceScopeDao.queryLtlServiceScopeInfo(dto);
	}

}
