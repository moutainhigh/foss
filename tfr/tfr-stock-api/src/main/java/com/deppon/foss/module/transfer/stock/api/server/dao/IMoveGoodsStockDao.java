package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockQueryDto;

/**
* @description 本接口定义了对库存迁移的增、删,查、改操作
* @version 1.0
* @author 218381-foss-lijie
* @update 2014-12-12 下午5:35:44
*/
public interface IMoveGoodsStockDao {
	/**
	* @description 分页查询库存迁移
	* @param moveGoodsStockDto
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午5:52:52
	*/
	List<MoveGoodsStockQueryDto> queryMoveGoodsStock(MoveGoodsStockDto moveGoodsStockDto,int limit, int start);
	
	
	/**
	* @description 查询库存迁移总记录数
	* @param moveGoodsStockDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-4 上午9:28:25
	*/
	Long queryMoveGoodsCount(MoveGoodsStockDto moveGoodsStockDto);
	/**
	* @description 撤销申请
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午6:08:53
	*/
	int revocationStock(MoveGoodsEntity moveGoodsEntity);
	
	/**
	* @description 审核申请
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午5:43:51
	*/
	int auditorStock(MoveGoodsEntity moveGoodsEntity);
	
	/**
	* @description 作废申请
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:27:59
	*/
	int invalidateStock(MoveGoodsEntity moveGoodsEntity);
	
	
	/**
	* @description 退回申请
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:41:07
	*/
	int returnStock(MoveGoodsEntity moveGoodsEntity);
	/**
	* @description 根据Id查询数据库
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午2:15:06
	*/
	MoveGoodsEntity queryMoveGoodsEntityById(String id);
	
	
	/**
	* @description 确认迁移申请
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月29日 上午10:08:25
	*/
	int confirmStock(MoveGoodsEntity moveGoodsEntity);
	/**
	* @description  根据id查询库存迁移明细
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-24 上午11:09:50
	*/
	MoveGoodsStockQueryDto viewMoveGoodsById(String id);
	
	
	/**
	* @description 将moveGoodsEntity入库
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-29 下午3:14:36
	*/
	public int moveGoodsInStock(MoveGoodsEntity moveGoodsEntity);
	
	
	/**
	* @description 将moveGoodsDepartmentEntity入库
	* @param moveGoodsDepartmentEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-29 下午3:15:49
	*/
	public int moveGoodsDepartmentInStock(MoveGoodsDepartmentEntity moveGoodsDepartmentEntity);
	
	
	/**
	* @description 将修改人的信息,及修改的信息入库
	* @param moveGoodsEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午10:38:31
	*/
	public int moveGoodsModifyInStock(MoveGoodsEntity moveGoodsEntity);
	
	
	/**
	* @description 将修改的MoveGoodsDepartmentEntity入库
	* @param moveGoodsDepartmentEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午10:38:35
	*/
	public int moveGoodsModifyDepartmentInStock(MoveGoodsDepartmentEntity moveGoodsDepartmentEntity);
	
	
	/**
	* @description 从库存表中根据部门code和库区code查询出库区的货物
	* @param OrgCode
	* @param GoodsAreaCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月27日 上午10:59:34
	*/
	List<StockEntity> queryGoodsByOrgAndGoodsArea(String orgCode,String goodsAreaCode);
	
	
	/**
	* @description 从运单库存表中根据部门code和库区code查询出库区的货物
	* @param OrgCode
	* @param GoodsAreaCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:17:43
	*/
	List<WaybillStockEntity> queryGoodsByOrgAndGoodsAreaFromWaybillStock(String orgCode,String goodsAreaCode);
	
	
	/**
	* @description 把库存表 中移出部门信息更新为移入部门
	* @param stockEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:52:27
	*/
	public int updateMoveInArea(StockEntity stockEntity,String moveoutCode,String moveoutAreacode);
	
	
	/**
	* @description 把运单库存表 中移出部门信息更新为移入部门
	* @param waybillStockEntity
	* @param movein_code
	* @param movein_areacode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月28日 下午3:20:46
	*/
	public int updateMoveInAreaFromWaybillStock(WaybillStockEntity waybillStockEntity,String moveoutCode,String moveoutAreacode);
	
	
	
	
	/**
	* @description 根据部门编码和库区编码查询目的站部门编码
	* @param orgcode
	* @param areacode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年3月6日 下午4:39:35
	*/
	public String queryArriveCode(String orgcode ,String areacode);
}
















