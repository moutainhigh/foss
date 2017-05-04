package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 试点城市service
 * @author foss-qualifying
 * @date 2013-7-17 下午1:52:24
 */
public interface IExpressCityService extends IService {

	/**
	 * 查询总条数
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:19:33
	 * @param expressCityQueryDto
	 * @return
	 */
	long queryExpressCityCountByCondition(ExpressCityQueryDto expressCityQueryDto);
	
	/**
	 * 查询列表
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:19:36
	 * @param expressCityQueryDto
	 * @return
	 */
	List<ExpressCityResultDto> queryExpressCityListByCondition(ExpressCityQueryDto expressCityQueryDto, int start, int limit);
	 
	/**
	 * 根据条件查询一条快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 下午4:08:52
	 * @param id
	 * @return
	 */
	ExpressCityResultDto queryOneExpressCityByCondition(ExpressCityQueryDto expressCityQueryDto);
	
	
	/**
	 * 新增
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:19:40
	 * @param expressCityQueryDtoList
	 */
	void addExpressCityList(List<ExpressCityEntity> expressCityEntityList,CurrentInfo currentInfo);
	
	/**
	 * 作废
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:19:42
	 * @param expressCityQueryDtoList
	 */
	void disabledExpressCity(ExpressCityQueryDto expressCityQueryDto,CurrentInfo currentInfo);
	
	/**
	 * 修改
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:21:40
	 * @param oldExpressCityQueryDto
	 * @param newExpressCityQueryDtoList
	 */
	void updateExpressCity(ExpressCityEntity oldExpressCityEntity,List<ExpressCityEntity> newExpressCityEntityList,CurrentInfo currentInfo);
	
	
	/**
	 * 根据营业部网点编码获取该营业部所在城市的快递代理/试点城市类型(优先试点城市)
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 上午11:08:14
	 * @param orgCode
	 * @return
	 */
	ExpressCityResultDto queryExpressCityTypeByOrgCode(String orgCode);
	
	/**
	 * 根据营业部网点编码获取该营业部所在城市的快递代理/试点城市类型（优先快递代理城市）
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 上午11:08:14
	 * @param orgCode
	 * @return
	 */
	ExpressCityResultDto queryLdpExpressCityTypeByOrgCode(String orgCode);
	
	/**
	 * 根据营业部编码查询快递代理/试点城市映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-19 下午4:09:14
	 * @param orgCode
	 * @param active
	 * @return
	 */
	List<ExpressCityEntity> queryExpressCityByOrgCode(String orgCode,String active);
	
}
