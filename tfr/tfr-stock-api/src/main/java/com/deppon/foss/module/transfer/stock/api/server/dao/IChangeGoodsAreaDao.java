package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto;


/**
* @description 本接口定义了对库存编号修改的增、删,查、改操作
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年4月3日 下午4:42:42
*/
public interface IChangeGoodsAreaDao {

	
	
	/**
	* @description 查询库区编号修改记录
	* @param changeGoodsAreaQueryDto
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 上午10:37:11
	*/
	List<ChangeGoodsAreaEntity> queryChangeGoodsArea(ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto,int limit,int start);
	
	
	/**
	* @description 查询库区变更总记录数
	* @param changeGoodsAreaQueryDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 上午10:38:59
	*/
	Long queryChangeGoodsAreaCount(ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto);
	
	
	/**
	* @description 根据id查询数据
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:28:07
	*/
	ChangeGoodsAreaEntity queryChangeGoodsAreaEntityById(String id);
	
	
	/**
	* @description 作废申请
	* @param changeGoodsAreaEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:30:00
	*/
	int invalidateChangeGoodsArea(ChangeGoodsAreaEntity changeGoodsAreaEntity);
	
	
	/**
	* @description 根据部门code查询库区号
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:36:34
	*/
	List<NewAndOldGoodsAreaEntity> lookGoodsAreaByOrgcode(String orgCode);
	
	
	/**
	* @description 将申请的库区编号修改信息写入主表
	* @param changeGoodsAreaEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午3:40:24
	*/
	public int changeGoodsAreaInStock(ChangeGoodsAreaEntity changeGoodsAreaEntity);
	
	
	/**
	* @description 将申请的库区编号修改的新旧库区信息写入主表
	* @param newAndOldGoodsAreaEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月10日 下午5:22:40
	*/
	public int changeGoodsAreaNewAndOldInStock(NewAndOldGoodsAreaEntity newAndOldGoodsAreaEntity);
	
	
	/**
	* @description 根据部门code和id查询库区编码对应关系
	* @param orgCode
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月11日 下午3:53:25
	*/
	List<NewAndOldGoodsAreaEntity> lookModifyGoodsAreaByOrgcode(String orgCode,String id);
	
	
	/**
	* @description 根据部门code和id查询库区编码对应关系(查询页面)
	* @param orgCode
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月16日 下午3:44:47
	*/
	List<NewAndOldGoodsAreaEntity> lookLookGoodsAreaByOrgcode(String orgCode,String id);
	
	/**
	* @description 根据id更新  新库区编号
	* @param newAndOldGoodsAreaEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午10:39:02
	*/
	public int updateNewCodeById(NewAndOldGoodsAreaEntity newAndOldGoodsAreaEntity);
	
	
	/**
	* @description 根据id修改备注信息
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午11:23:01
	*/
	public int updateGoodsAreaInStockById(ChangeGoodsAreaEntity changeGoodsAreaEntity);
	
	
	/**
	* @description 将确认修改的新旧库区对应关系写入数据库
	* @param id1
	* @param change_goodsarea_area_id
	* @param orgCode
	* @param n
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 上午11:01:52
	*/
	public int insertNewCodeById(NewAndOldGoodsAreaEntity n);
	
	
	/**
	* @description 把tfr.t_opt_stock 的库区code变为    新库区code_new
	* @param orgCode
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午3:38:53
	*/
	public int update_T_opt_stock_to_code_new(String orgCode,String id);
	
	
	/**
	* @description 把tfr.t_opt_waybill_stock 的库区code变为    新库区code_new
	* @param orgCode
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午3:50:52
	*/
	public int update_T_opt_waybill_stock_to_code_new(String orgCode,String id);
	
	
	/**
	* @description 将tfr.t_opt_stock库区编号中的'_new'去掉
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:02:30
	*/
	public int update_T_opt_stock_delete_new(String orgCode);
	
	
	/**
	* @description 将tfr.t_opt_waybill_stock库区编号中的'_new'去掉
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:23:48
	*/
	public int update_T_opt_waybill_stock_delete_new(String orgCode);
	
	
	/**
	* @description 将带'_new'的无法删除的库区里面的运单数量加到已存在的运单明细上
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:31:08
	*/
	public int update_T_opt_waybill_stock_add_new(String orgCode);
	
	
	/**
	* @description 将'_new'的数据物理删除
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 下午7:36:36
	*/
	public int delete_T_opt_waybill_stock_new(String orgCode);
	
	
	/**
	* @description 将数据的状态改为已修改
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月28日 上午9:37:00
	*/
	public int modifiedChangeGoodsArea(String id);
}
