package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.PathdetailExtensionEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughExpressPathDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughExpressPathEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughPackageScanWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.PathdetailExtensionDto;
/**
 * @author wqh
 * @desc:直达包走货路由
 * @date：2014-09-18
 */
public interface IExpressThroughPackagePathDao {

	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:新增直达包走货路由主信息
	 * @parm:throughExpressPathEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:30:37
	 */
	public void addThroughExpressPackagePath(ThroughExpressPathEntity throughExpressPathEntity);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:添加快递直达包走货路由明细信息 
	 * @parm:pathDetailList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:35:23
	 */
	public void addThroughExpressPackagePathDetailList(List<ThroughExpressPathDetailEntity> pathDetailList);
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:修改快递直达包走货路由主表信息
	 * @parm:throughExpressPathEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:39:02
	 */
	public void updateMainThroughPackagePath(ThroughExpressPathEntity throughExpressPathEntity);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:
	 * @parm:
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:40:22
	 */
	public void updateThroughPackagePathDetail(ThroughExpressPathDetailEntity throughExpressPathDetailEntity);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询主表走货路由信息
	 * @parm:throughExpressPathEntity
	 * @return:List<ThroughExpressPathEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:42:21
	 */
	public List<ThroughExpressPathEntity> queryMainPathList(ThroughExpressPathEntity throughExpressPathEntity);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号查询走货路由主表信息
	 * @parm:map
	 * @return:List<ThroughExpressPathEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:44:17
	 */
	public List<ThroughExpressPathEntity> queryMainPathByPackagekNoList(List<String> packageNoList);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询走货路由明细信息
	 * @parm:throughExpressPathDetailEntity
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:47:36
	 */
	public List<ThroughExpressPathDetailEntity> queryPathDetilList(ThroughExpressPathDetailEntity throughExpressPathDetailEntity);
	
	
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号查询走货路由明细
	 * @parm:packageNoList
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:49:17
	 */
	public  List<ThroughExpressPathDetailEntity> queryPathDetilByPackageNoList(List<String> packageNoList);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号删除走货路主表
	 * @parm:packageNoList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:50:49
	 */
	public void deleteMainPathByPackageNoList(List<String> packageNoList);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据ID删除走货路主表
	 * @parm:idList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:52:56
	 */
	public void deleteMainPathByIdList(List<String> idList);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号删除走货路明细表
	 * @parm:packageNoList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:54:16
	 */
	public void deletePathDetailByPackageNoList(List<String> packageNoList);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据ID删除走货路主表
	 * @parm:idList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:55:12
	 */
	public void deletePathDetailByIdList(List<String> idList);
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询为 出发部门最小路段
	 * @parm:PathdetailExtensionEntity
	 * @return:PathdetailExtensionEntity
	 * @author:wqh
	 * @date:2014年9月22日上午9:35:06
	 */
	public PathdetailExtensionEntity queryDepartMinRoutePathDetail( PathdetailExtensionEntity pathdetailExtensionEntity);

	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询为 到达部门最大路段
	 * @parm:PathdetailExtensionEntity
	 * @return:PathdetailExtensionEntity
	 * @author:wqh
	 * @date:2014年9月22日上午9:36:34
	 */
	public PathdetailExtensionEntity queryArriveMaxRoutePathDetail( PathdetailExtensionEntity pathdetailExtensionEntity);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据路由号查询所有路段
	 * @parm:map
	 * @return:List<PathdetailExtensionEntity>
	 * @author:wqh
	 * @date:2014年9月22日上午10:09:43
	 */
	public List<PathdetailExtensionEntity> queryAllPathDetailList(String waybillNo,String goodsNo,int minRouteNo,int maxRouteNo);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询直达包当前部门为到达部门最大端路径
	 * @parm:PathdetailExtensionEntity
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月24日上午11:17:20
	 */
	public ThroughExpressPathDetailEntity queryThroughPackageArriveMaxRoutePathDetail(ThroughExpressPathDetailEntity pathdetailExtensionEntity);
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:将所有路段置为 DEPART_PACKAGE状态
	 * @parm:PathdetailExtensionEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月24日下午4:41:50
	 */
	public void updatePathDetailForDepart(ThroughExpressPathDetailEntity pathdetailExtensionEntity);
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据运单号查询大于当前路段号的所有路段的到达部门
	 * @parm:PathdetailExtensionEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月24日下午4:41:50
	 */
	public List<PathdetailExtensionEntity> queryAllArriveOrigCodeByWaybillNo(PathdetailExtensionEntity pathdetailExtensionEntity);
	
	/** 保存快递直达包扫描运单记录*/
	public void addThroughPackageScanWaybill(ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity);
	
	/** 修改快递直达包扫描运单记录*/
	public void updateThroughPackageScanWaybill(ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity);
	
	/**  查询快递直达包扫描记录*/
	public List<ThroughPackageScanWaybillEntity> queryThroughPackageScanWaybil(ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity);
	
	/**根据运单集合查询所有的走货路径明细 */
	public List<PathdetailExtensionEntity> queryPathDetailList(List<String> waybillNoList);
	/**根据运单集合查询所有的走货路径明细 (通过车辆任务明细表关联单据明细表以及交接明细表做查询)*/
	public List<PathdetailExtensionDto> queryPathDetailLists(List<String> waybillNoList);
	
	/**查询当前部门运单所有的出发部门与到达部门*/
	public List<PathdetailExtensionEntity> queryDepartAndArriveOrigCodeByWaybillNos(List<String> waybillNoList,String departOrgCode,String arriveOrgCode);
}
