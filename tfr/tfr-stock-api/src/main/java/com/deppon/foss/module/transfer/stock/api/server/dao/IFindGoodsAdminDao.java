package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;


/**
 * 
* @ClassName: IFindGoodsAdminDao
* @Description: 找货管理Dao
* @author 189284--ZX
* @date 2015-7-10 下午4:42:52
*
 */
public interface IFindGoodsAdminDao {
    /**
     * 
    * @Title: queryFindGoodsAdminQuery
    * @Description: 分页查询 找货管理信息
    * @author 189284--ZX
    * @param @param findGoodsAdminEntity
    * @param @param limit
    * @param @param start
    * @param @return    设定文件
    * @return List<FindGoodsAdminEntity>    返回类型
    * @throws
     */
	public List<FindGoodsAdminEntity> queryFindGoodsAdmin(
			FindGoodsAdminEntity findGoodsAdminEntity, int start, int limit);
	/**
	 * 
	* @Title: queryFindGoodsAdminQueryCount
	* @Description: 查询 找货管理信息 总数
	* @author 189284--ZX
	* @param @param findGoodsAdminEntity
	* @param @return    设定文件
	* @return Long    返回类型
	* @throws
	 */
	Long queryFindGoodsAdminQueryCount(FindGoodsAdminEntity findGoodsAdminEntity);
	/**
	  * 
	 * @Title: queryFindGoodsAdminDetail
	 * @Description: 根据 运单 流水 查询找货管理明细
	 * @author 189284--ZX
	 * @param  findGoodsAdminDetailEntity.waybillNo 运单号
	 * @param  findGoodsAdminDetailEntity.serialNo 流水号
	 * @param @return    设定文件
	 * @return List<FindGoodsAdminDetailEntity>    返回类型
	 * @throws
	  */
	List<FindGoodsAdminDetailEntity> queryFindGoodsAdminDetail(FindGoodsAdminDetailEntity findGoodsAdminDetailEntity);
	 /**
	  * 
	 * @Title: commitFindGoodsAdmin
	 * @Description: 根据 任务编号 提交找货管理信息
	 * @author 189284--ZX
	 * @param @param findGoodsAdminEntity    设定文件
	 * @return void    返回类型
	 * @throws
	  */
	void commitFindGoodsAdmin(FindGoodsAdminEntity findGoodsAdminEntity);
	 /**
	  * 
	 * @Title: findGoodsAdminInsert
	 * @Description:批量新增找货管理信息明细
	 * @author 189284--ZX
	 * @param @param findGoodsAdminEntity
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	int findGoodsAdminDetailInsert(
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys);
	 /**
	  * 
	 * @Title: findGoodsAdminInsert
	 * @Description: 新增找货管理信息
	 * @author 189284--ZX
	 * @param @param findGoodsAdminEntity
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	int findGoodsAdminInsert(FindGoodsAdminEntity findGoodsAdminEntity);
	/**
	  * 
	 * @Title: queryFindGoodsAdminDeatil
	 * @Description: pda  创建找货任务的时间 查明细
	 * @author 189284--ZX
	 * @param  findGoodsAdminEntity.goodsAreaCode 货区Code'
	 * @param  findGoodsAdminEntity.orgCode 货区所属外场Code
	 * @param  findGoodsAdminEntity.taskCreateDate 上报开始时间
	 * @param  findGoodsAdminEntity.taskEndDate  上报结束时间
	 * @param @return    设定文件
	 * @return List<FindGoodsAdminDetailEntity>    返回类型
	 * @throws
	  */
	List<FindGoodsAdminDetailEntity> queryFindGoodsAdminDeatil(
			FindGoodsAdminEntity findGoodsAdminEntity);
	/**
	  * 
	 * @Title: insertFGoodsDetailMapper
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author 189284--ZX
	 * @param  taskNo任务编号
	 * @param  findGoodsAdminDetailEntitys.waybillNo运单号
	 * @param  findGoodsAdminDetailEntitys.serialNo 流水
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	int insertFGoodsDetailMapper(
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys);
	/***
	  * 
	 * @Title: scanFGoodsfgoodsDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author 189284--ZX
	 * @param @param waybillNo运单号
	 * @param @param serialNo流水
	 * @param @param user修改人
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	int scanFGoodsfgoodsDetail(String waybillNo, String serialNo, String user);
	 /**
	  * 
	 * @Title: qureyFGoodsDetailByTaskNo
	 * @Description: 根据任务号 查询明细
	 * @author 189284--ZX
	 * @param @param taskNo
	 * @param @return    设定文件
	 * @return List<FindGoodsAdminDetailEntity>    返回类型
	 * @throws
	  */
	List<FindGoodsAdminDetailEntity> qureyFGoodsDetailByTaskNo(String taskNo);

}
