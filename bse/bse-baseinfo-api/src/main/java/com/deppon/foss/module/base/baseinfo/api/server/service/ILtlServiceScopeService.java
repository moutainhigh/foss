package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LtlServiceScopeDto;

/**
 * 零担服务范围的Service接口
 * 
 * @ClassName: ILtlServiceScopeService
 * @author 200664-yangjinheng
 * @date 2014年9月30日 下午12:35:46
 */
public interface ILtlServiceScopeService extends IService {

	/**
	 * 精确查询 通过 行政区域编码CODE 查询行政区域信息
	 * 
	 * @Title: queryDistrictByCode
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:36:05
	 * @throws
	 */
	LtlServiceScopeDto queryDistrictByCode(String code);

	/**
	 * 根据name查询行政区域，迭代查询该行政区域的上级，直到上级为“中国”
	 * 
	 * @Title: queryDistrictByName
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:36:14
	 * @throws
	 */
	LtlServiceScopeDto queryDistrictByName(String name);

	/**
	 * 查询该行政区域的营业网点信息和派送范围信息
	 * 
	 * @Title: queryLtlServiceScopeInfo
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午3:52:28
	 * @throws
	 */
	List<LtlServiceScopeDto> queryLtlServiceScopeInfo(LtlServiceScopeDto dto);

}
