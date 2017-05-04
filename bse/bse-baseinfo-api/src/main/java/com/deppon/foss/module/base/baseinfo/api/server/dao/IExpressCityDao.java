package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;

/**
 * 试点城市dao
 * @author foss-qiaolifeng
 * @date 2013-7-17 下午2:27:44
 */
public interface IExpressCityDao {

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
	List<ExpressCityEntity> queryExpressCityListByCondition(ExpressCityQueryDto expressCityQueryDto, int start, int limit);
	
	/**
	 * 根据ID查询一条快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 下午4:21:13
	 * @param id
	 * @return
	 */
	ExpressCityEntity queryOneExpressCityById(ExpressCityQueryDto expressCityQueryDto);
	
	/**
	 * 根据城市编码和有效状态查询快递代理/试点城市信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-19 上午10:30:40
	 * @param expressCityQueryDto
	 * @return
	 */
	List<ExpressCityEntity> queryOneExpressCityListByCodeAndType(ExpressCityQueryDto expressCityQueryDto);
	
	/**
	 * 新增快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午9:18:11
	 * @param expressCityEntity
	 */
	int addExpressCityEntity(ExpressCityEntity expressCityEntity);
	
	/**
	 * 修改快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午10:33:17
	 * @param expressCityEntity
	 * @return
	 */
	int updateExpressCityEntity(ExpressCityEntity expressCityEntity); 
	
	/**
	 * 根据营业部网点编码获取该营业网点所在城市的快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 下午1:40:56
	 * @param orgCode
	 * @return
	 */
	List<ExpressCityEntity> queryExpressCityByOrgCode(String orgCode,String active);

	/**
	 * 分页下载
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<ExpressCityEntity> queryExpressCityForDownloadByPage(
			ExpressCityEntity entity, int start, int limited);

	/**
	 * 下载
	 * @param entity
	 * @return
	 */
	List<ExpressCityEntity> queryExpressCityForDownload(ExpressCityEntity entity);
} 
