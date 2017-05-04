package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto;


public class ChangeGoodsAreaDao extends iBatis3DaoImpl implements IChangeGoodsAreaDao{

	
	/**
	* @description 查询库区编号修改记录
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#queryChangeGoodsArea(com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto, int, int)
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:46:38
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeGoodsAreaEntity> queryChangeGoodsArea(
			ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto, int limit,int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryChangeGoodsArea", changeGoodsAreaQueryDto,rowBounds);
	}

	
	/**
	* @description 查询库区修改总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#queryChangeGoodsAreaCount(com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年4月7日 上午10:38:14
	* @version V1.0
	*/
	@Override
	public Long queryChangeGoodsAreaCount(
			ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto) {
		return (Long)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryChangeGoodsAreaCount",changeGoodsAreaQueryDto);
	}


	
	/**
	* @description 根据id查询数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#queryChangeGoodsAreaEntityById(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:31:04
	* @version V1.0
	*/
	@Override
	public ChangeGoodsAreaEntity queryChangeGoodsAreaEntityById(String id) {
		return (ChangeGoodsAreaEntity)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryChangeGoodsAreaEntityById",id);
	}


	
	/**
	* @description 作废申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#invalidateChangeGoodsArea(com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:31:08
	* @version V1.0
	*/
	@Override
	public int invalidateChangeGoodsArea(
			ChangeGoodsAreaEntity changeGoodsAreaEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "invalidateChangeGoodsArea", changeGoodsAreaEntity);
	}


	
	/**
	* @description 根据部门code查询库区号
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#lookGoodsAreaByOrgcode(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:37:03
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<NewAndOldGoodsAreaEntity> lookGoodsAreaByOrgcode(String orgCode) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "lookGoodsAreaByOrgcode", orgCode);
	}


	
	/**
	* @description 将申请的库区编号修改信息写入主表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#changeGoodsAreaInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午3:41:33
	* @version V1.0
	*/
	@Override
	public int changeGoodsAreaInStock(
			ChangeGoodsAreaEntity changeGoodsAreaEntity) {
		return this.getSqlSession().insert(StockConstants.NAME_SPACE + "changeGoodsAreaInStock", changeGoodsAreaEntity);
	}


	
	/**
	* @description 将申请的库区编号修改的新旧库区信息写入主表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#changeGoodsAreaNewAndOldInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月10日 下午5:23:26
	* @version V1.0
	*/
	@Override
	public int changeGoodsAreaNewAndOldInStock(
			NewAndOldGoodsAreaEntity newAndOldGoodsAreaEntity) {
		return this.getSqlSession().insert(StockConstants.NAME_SPACE + "changeGoodsAreaNewAndOldInStock", newAndOldGoodsAreaEntity);
	}

	
	/**
	* @description 根据部门code和id查询库区编码对应关系
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#lookModifyGoodsAreaByOrgcode(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月11日 下午3:53:51
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<NewAndOldGoodsAreaEntity> lookModifyGoodsAreaByOrgcode(
			String orgCode, String id) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("id", id);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "lookModifyGoodsAreaByOrgcode", paramsMap);
	}

	
	/**
	* @description 根据部门code和id查询库区编码对应关系(查询页面)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#lookLookGoodsAreaByOrgcode(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月16日 下午3:45:33
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<NewAndOldGoodsAreaEntity> lookLookGoodsAreaByOrgcode(
			String orgCode, String id) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("id", id);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "lookLookGoodsAreaByOrgcode", paramsMap);
	}
	
	/**
	* @description 根据id更新  新库区编号
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#updateNewCodeById(com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午10:40:31
	* @version V1.0
	*/
	@Override
	public int updateNewCodeById(NewAndOldGoodsAreaEntity newAndOldGoodsAreaEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "updateNewCodeById", newAndOldGoodsAreaEntity);
	}


	
	/**
	* @description 根据id修改备注信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#updateGoodsAreaInStockById()
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午11:23:33
	* @version V1.0
	*/
	@Override
	public int updateGoodsAreaInStockById(ChangeGoodsAreaEntity changeGoodsAreaEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "updateGoodsAreaInStockById", changeGoodsAreaEntity);
	}

	
	/**
	* @description 将确认修改的新旧库区对应关系写入数据库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#insertNewCodeById(java.lang.String, java.lang.String, java.lang.String, com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 上午11:02:46
	* @version V1.0
	*/
	@Override
	public int insertNewCodeById(NewAndOldGoodsAreaEntity n) {
		return this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertNewCodeById", n);
	}


	
	/**
	* @description 把tfr.t_opt_stock 的库区code变为    新库区code_new
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#update_T_opt_stock_to_code_new(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午3:39:16
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int update_T_opt_stock_to_code_new(String orgCode, String id) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("id", id);
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "update_T_opt_stock_to_code_new", paramsMap);
	}

	
	/**
	* @description 把tfr.t_opt_waybill_stock 的库区code变为    新库区code_new
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#update_T_opt_waybill_stock_to_code_new(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午3:51:45
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int update_T_opt_waybill_stock_to_code_new(String orgCode, String id) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("id", id);
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "update_T_opt_waybill_stock_to_code_new", paramsMap);
	}


	
	/**
	* @description 将tfr.t_opt_stock库区编号中的'_new'去掉
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#update_T_opt_stock_delete_new(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:03:14
	* @version V1.0
	*/
	@Override
	public int update_T_opt_stock_delete_new(String orgCode) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "update_T_opt_stock_delete_new", orgCode);
	}


	
	/**
	* @description 将tfr.t_opt_waybill_stock库区编号中的'_new'去掉
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#update_T_opt_waybill_stock_delete_new(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:24:54
	* @version V1.0
	*/
	@Override
	public int update_T_opt_waybill_stock_delete_new(String orgCode) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "update_T_opt_waybill_stock_delete_new", orgCode);
	}

	/**
	* @description 将带'_new'的无法删除的库区里面的运单数量加到已存在的运单明细上
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#update_T_opt_waybill_stock_add_new(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:32:55
	* @version V1.0
	*/
	@Override
	public int update_T_opt_waybill_stock_add_new(String orgCode) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "update_T_opt_waybill_stock_add_new", orgCode);
	}

	/**
	* @description 将'_new'的数据物理删除
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#delete_T_opt_waybill_stock_new(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:37:17
	* @version V1.0
	*/
	@Override
	public int delete_T_opt_waybill_stock_new(String orgCode) {
		return this.getSqlSession().delete(StockConstants.NAME_SPACE + "delete_T_opt_waybill_stock_new", orgCode);
	}


	
	/**
	* @description 将数据的状态改为已修改
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao#modifiedChangeGoodsArea(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月28日 上午9:37:40
	* @version V1.0
	*/
	@Override
	public int modifiedChangeGoodsArea(String id) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "modifiedChangeGoodsArea", id);
	}


	


	
	

}
