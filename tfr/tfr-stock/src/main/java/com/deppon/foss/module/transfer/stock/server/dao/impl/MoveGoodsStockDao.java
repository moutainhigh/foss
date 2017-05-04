package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockQueryDto;

public class MoveGoodsStockDao extends iBatis3DaoImpl implements IMoveGoodsStockDao{
		
	
	/**
	* @description  分页查询库存迁移
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#queryMoveGoodsStock(com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto, int, int)
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午5:51:50
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<MoveGoodsStockQueryDto> queryMoveGoodsStock(
			MoveGoodsStockDto moveGoodsStockDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryMoveGoodsStock", moveGoodsStockDto,rowBounds);
	}

	
	/**
	* @description 撤销申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#revocationStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午6:10:02
	* @version V1.0
	*/
	@Override
	public int revocationStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "revocationStock", moveGoodsEntity);
	}


	
	/**
	* @description 根据Id查询数据库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#queryMoveGoodsEntityById(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午2:18:00
	* @version V1.0
	*/
	@Override
	public MoveGoodsEntity queryMoveGoodsEntityById(String id) {
		return (MoveGoodsEntity)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryMoveGoodsEntityById",id);
	}


	
	/**
	* @description 审核申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#auditorStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午5:45:07
	* @version V1.0
	*/
	@Override
	public int auditorStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "auditorStock", moveGoodsEntity);
	}

	/**
	* @description 作废申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#invalidateStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:28:35
	* @version V1.0
	*/
	@Override
	public int invalidateStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "invalidateStock", moveGoodsEntity);
	}


	
	/**
	* @description 退回申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#returnStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:41:38
	* @version V1.0
	*/
	@Override
	public int returnStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "returnStock", moveGoodsEntity);
	}


	
	/**
	* @description  根据id查询库存迁移明细
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#viewMoveGoodsById(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2014-12-24 上午11:10:36
	* @version V1.0
	*/
	@Override
	public MoveGoodsStockQueryDto viewMoveGoodsById(String id) {
		return (MoveGoodsStockQueryDto)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "viewMoveGoodsById",id);
	}


	
	/**
	* @description 将moveGoodsEntity入库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#moveGoodsInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-29 下午3:17:57
	* @version V1.0
	*/
	@Override
	public int moveGoodsInStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().insert(StockConstants.NAME_SPACE + "moveGoodsInStock", moveGoodsEntity);
	}


	
	/**
	* @description 将moveGoodsDepartmentEntity入库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#moveGoodsDepartmentInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-29 下午3:18:00
	* @version V1.0
	*/
	@Override
	public int moveGoodsDepartmentInStock(
			MoveGoodsDepartmentEntity moveGoodsDepartmentEntity) {
		return this.getSqlSession().insert(StockConstants.NAME_SPACE + "moveGoodsDepartmentInStock", moveGoodsDepartmentEntity);
	}


	
	/**
	* @description 将修改人的信息,及修改的信息入库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#moveGoodsModifyInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午10:39:52
	* @version V1.0
	*/
	@Override
	public int moveGoodsModifyInStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "moveGoodsModifyInStock", moveGoodsEntity);
	}


	
	/**
	* @description 将修改的MoveGoodsDepartmentEntity入库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#moveGoodsModifyInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午10:39:54
	* @version V1.0
	*/
	@Override
	public int moveGoodsModifyDepartmentInStock(
			MoveGoodsDepartmentEntity moveGoodsDepartmentEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "moveGoodsModifyDepartmentInStock", moveGoodsDepartmentEntity);
	}


	
	/**
	* @description 查询库存迁移总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#queryMoveGoodsCount(com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto)
	* @author 218381-foss-lijie
	* @update 2015-1-4 上午9:29:34
	* @version V1.0
	*/
	@Override
	public Long queryMoveGoodsCount(MoveGoodsStockDto moveGoodsStockDto) {
		return (Long)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryMoveGoodsCount",moveGoodsStockDto);
	}


	
	/**
	* @description 从库存表中根据部门code和库区code查询出库区的货物
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#queryGoodsByOrgAndGoodsArea(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月27日 上午11:08:20
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockEntity> queryGoodsByOrgAndGoodsArea(String orgCode,String goodsAreaCode) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("OrgCode", orgCode);
		paramsMap.put("GoodsAreaCode", goodsAreaCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryGoodsByOrgAndGoodsArea",paramsMap);
	}


	
	/**
	* @description 从运单库存表中根据部门code和库区code查询出库区的货物
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#queryGoodsByOrgAndGoodsAreaFromWaybillStock(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:23:43
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillStockEntity> queryGoodsByOrgAndGoodsAreaFromWaybillStock(
			String orgCode, String goodsAreaCode) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("OrgCode", orgCode);
		paramsMap.put("GoodsAreaCode", goodsAreaCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryGoodsByOrgAndGoodsAreaFromWaybillStock",paramsMap);
	}

	/**
	* @description 把移出部门的货物     从库存表     入库到移入部门库存
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#insertintoMoveInArea(com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity)
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:53:13
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int updateMoveInArea(StockEntity stockEntity,String moveoutCode,String moveoutAreacode) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("stockEntity", stockEntity);
		paramsMap.put("moveout_code", moveoutCode);
		paramsMap.put("moveout_areacode", moveoutAreacode);
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "updateMoveInArea", paramsMap);
	}

	/**
	* @description 把移出部门的货物     从运单库存表     入库到移入部门库存
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#insertintoMoveInAreaFromWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity, java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月28日 下午3:21:38
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int updateMoveInAreaFromWaybillStock(
			WaybillStockEntity waybillStockEntity,String moveoutCode,String moveoutAreacode) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillStockEntity", waybillStockEntity);
		paramsMap.put("moveout_code", moveoutCode);
		paramsMap.put("moveout_areacode", moveoutAreacode);
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "updateMoveInAreaFromWaybillStock", paramsMap);
	}


	
	/**
	* @description 确认迁移申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#confirmStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity)
	* @author 218381-foss-lijie
	* @update 2015年1月29日 上午10:09:11
	* @version V1.0
	*/
	@Override
	public int confirmStock(MoveGoodsEntity moveGoodsEntity) {
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "confirmStock", moveGoodsEntity);
	}


	
	/**
	* @description 根据部门编码查询目的站部门编码
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao#queryArriveCode(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年3月6日 下午4:25:22
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public String queryArriveCode(String orgcode , String areacode) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("orgcode", orgcode);
		paramsMap.put("areacode", areacode);
		return (String)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryArriveCode",paramsMap);
	}


	
	
	
	
	
}
