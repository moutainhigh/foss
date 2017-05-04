package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;

public interface IldpCompanyValueAddDao {

	/**
	 *  根据条件查询快递代理理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:47:22
	 * @param   entity
	 * @param   start
	 * @param   limit
	 * @return  List<PartbussValueAddEntity>
	 * 
	 *
	 */
	List<PartbussValueAddEntity> queryEntityByParams(PartbussValueAddEntity entity,int start,int limit);
	
	/**
	 * 新增一快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:48:48
	 * @param   entity
	 * @return  int 1:成功 -1：失败
	 *
	 */
	int addNewPartbussValueAddEntity(PartbussValueAddEntity entity);
	
	/**
	 * 根据id去快递代理递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:50:40
	 * @param   ids
	 * @return  int 1:成功 -1：失败
	 *
	 */
	int deletePartbussValueAddEntity(String[] ids);
	
	/**
	 * 根据id快递代理快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:50:40
	 * @param   ids
	 * @return  int 1:成功 -1：失败
	 *
	 */
	int activeValueAddedServices(String[] ids);
	
	/**
	 快递代理新快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:53:40
	 * @param   entity
	 * @return  int 1:成功 -1：失败
	 *
	 */
	int updatePartbussValueAddEntity(PartbussValueAddEntity entity);
	
	/**
	 *快递代理激活快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:56:58
	 * @param   id
	 * @return  int 1:成功 -1：失败
	 *
	 */
	int activeImmediatelyValueAddedServices(PartbussValueAddEntity entity);
	
	/**
	 快递代理即终止快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:57:04
	 * @param   id
	 * @return  int 1:成功 -1：失败
	 *
	 */
	int inActiveImmediatelyValueAddedServices(PartbussValueAddEntity entity);
	
	/**
	 * 快递代理条件查询快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:47:22
	 * @param   entity
	 * @return  List<PartbussValueAddEntity>
	 * 
	 *
	 */
	List<PartbussValueAddEntity> queryEntityByParams(PartbussValueAddEntity entity);
	
	/**
	 * 统计查询的行数
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 下午1:51:08
	 * @param   entity
	 * @return  Long
	 *
	 */
	Long countQueryRecord(PartbussValueAddEntity entity);
	
	/**
	 * 根据id去查询同一个方案是否有重复
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-20 上午11:36:25
	 * @return  List<PartbussValueAddEntity>
	 *
	 */
	List<PartbussValueAddEntity> queryEntityByIds(String id);
}
