package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IForkliftEfficientQueryDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftDriverEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftEfficientEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TransferFieldEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ForkliftEfficientVo;

public class ForkliftEfficientQueryDao extends iBatis3DaoImpl implements IForkliftEfficientQueryDao{
	
	/**命名空间常量*/
	private static final String NAMESPACE = "foss.unload.queryForkliftEfficient.";
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	@Override
	public List<ForkliftEfficientEntity> queryForkliftData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start){
		
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryForkliftData", forkliftEfficientVo, rowBounds);
	}
	
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return 数据总数 
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public Long queryForkliftDataCount(ForkliftEfficientVo forkliftEfficientVo){
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryForkliftDataCount", forkliftEfficientVo);
	}
	/**
	 *添加叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientEntity 需添加的数据实体
	 ***/
	@Override
	public void addForkliftData(ForkliftEfficientEntity forkliftEfficientEntity){
		
		 this.getSqlSession().insert(NAMESPACE+"addForkliftData", forkliftEfficientEntity);

	}
	/**
	 *修改叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientVo 前台页面传递参数 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	@Override
	public void updateForkliftData(ForkliftEfficientEntity forkliftEfficientEntity){
		
		 this.getSqlSession().update(NAMESPACE+"updateForkliftData", forkliftEfficientEntity);
		
	}
	/**
	 *查询该转运场是否存在 
	 * @author zenghaibin
	 * @date 2014-7-10 下上午9:50:53
	 * @return long
	 * @param transfFieldCode 转运场编码
	 ***/
	@Override
	public Long queryTransfFieldExist(String transfFieldCode){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryTransfFieldExist", transfFieldCode);
	}
	/**
	 *作废叉车数量信息 即添加有效截至日期
	 * @author zenghaibin
	 * @date 2014-7-10 下上午9:50:53
	 * @return void
	 * @param forkliftEfficientEntity 数据forkliftEfficientEntity
	 ***/
	@Override
	public void invalidForkliftData(ForkliftEfficientEntity forkliftEfficientEntity){
		
		this.getSqlSession().delete(NAMESPACE+"invalidForkliftData",forkliftEfficientEntity);
	}
	
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	@Override
	public List<ForkliftDriverEntity>  queryForkliftDriverData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start){
		
		//分页
				RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryForkliftDriverData",forkliftEfficientVo,rowBounds);
	}
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return long  叉车司机数据数， 分页用
	 **/
	@Override
	public Long queryForkliftDriverDataCount(ForkliftEfficientVo forkliftEfficientVo){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryForkliftDriverDataCount",forkliftEfficientVo);
				
	}
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	@Override
	public List<TransferFieldEntity> queryTransferFieldData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start){
		
		//分页
				RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryTransferFieldData",forkliftEfficientVo,rowBounds);
	}
	
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@param forkliftEfficientVo 参数数据
	 *@return Long 分页用
	 **/
	@Override
	public Long queryTransferFieldDataCount(ForkliftEfficientVo forkliftEfficientVo){
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryTransferFieldDataCount", forkliftEfficientVo);
	}
	/**
	 * @author niuly
	 * @date 2014-7-18下午1:40:10
	 * @function 统计叉车效率
	 * @return String
	 */
	@Override
	public Map<String,String> queryForkliftEfficiency(){
		Map<String,String> map = new HashMap<String,String>();
		this.getSqlSession().selectOne(NAMESPACE+"queryForkliftEfficiency",map);
		
		return map;
	}
}
