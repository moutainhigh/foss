package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftDriverEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftEfficientEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TransferFieldEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ForkliftEfficientVo;

public interface IForkliftEfficientQueryService extends IService{
	/**
	 * 获取当前部门编号
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return String
	 */
	public String queryOrgCode();		
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public List<ForkliftEfficientEntity> queryForkliftData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start);
	
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return 数据总数 
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public Long queryForkliftDataCount(ForkliftEfficientVo forkliftEfficientVo);
	/**
	 *添加叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return void
	 * @param forkliftEfficientVo 前台页面传递参数 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	
	public void addForkliftData(ForkliftEfficientVo forkliftEfficientVo);
	
	/**
	 *修改叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-10 上午9:50:53
	 * @return void
	 * @param forkliftEfficientVo 前台页面传递参数 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public void updateForkliftData(ForkliftEfficientVo forkliftEfficientVo);

	/**
	 *电叉司机数据信息
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	public List<ForkliftDriverEntity>  queryForkliftDriverData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start);
	
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return long  叉车司机数据数， 分页用
	 **/
	public Long queryForkliftDriverDataCount(ForkliftEfficientVo forkliftEfficientVo);
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	public List<TransferFieldEntity> queryTransferFieldData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start);
	
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@param forkliftEfficientVo 参数数据
	 *@return Long 分页用
	 **/
	public Long queryTransferFieldDataCount(ForkliftEfficientVo forkliftEfficientVo);
	/**
	 * @author niuly
	 * @date 2014-7-18下午3:16:36
	 * @function 查询叉车效率
	 * @return
	 * @throws Exception 
	 */
	String queryForkliftEffiency() throws Exception;
}

