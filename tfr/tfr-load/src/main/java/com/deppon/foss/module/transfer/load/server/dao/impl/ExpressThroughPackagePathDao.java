package com.deppon.foss.module.transfer.load.server.dao.impl;
/**
 * @author wqh
 * @desc:直达包走货路由
 * @date：2014-09-18
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressThroughPackagePathDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.PathdetailExtensionEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughExpressPathDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughExpressPathEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughPackageScanWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.PathdetailExtensionDto;
public class ExpressThroughPackagePathDao extends iBatis3DaoImpl implements IExpressThroughPackagePathDao {
	
	private static final String NAMESPACE = "foss.load.express.throughPackage.";
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:新增直达包走货路由主信息
	 * @parm:throughExpressPathEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:30:37
	 */
	public void addThroughExpressPackagePath(ThroughExpressPathEntity throughExpressPathEntity){
		
		this.getSqlSession().insert(NAMESPACE+"addThroughExpressPackagePath", throughExpressPathEntity);
		
	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:添加快递直达包走货路由明细信息 
	 * @parm:pathDetailList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:35:23
	 */
	public void addThroughExpressPackagePathDetailList(List<ThroughExpressPathDetailEntity> pathDetailList){
		
		this.getSqlSession().insert(NAMESPACE+"addThroughExpressPackagePathDetailList", pathDetailList);

	}
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:修改快递直达包走货路由主表信息
	 * @parm:throughExpressPathEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:39:02
	 */
	public void updateMainThroughPackagePath(ThroughExpressPathEntity throughExpressPathEntity){
		
		this.getSqlSession().insert(NAMESPACE+"updateMainThroughPackagePath", throughExpressPathEntity);

	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:
	 * @parm:
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:40:22
	 */
	public void updateThroughPackagePathDetail(ThroughExpressPathDetailEntity throughExpressPathDetailEntity){
		
		this.getSqlSession().insert(NAMESPACE+"updateThroughPackagePathDetail", throughExpressPathDetailEntity);

	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询主表走货路由信息
	 * @parm:throughExpressPathEntity
	 * @return:List<ThroughExpressPathEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:42:21
	 */
	@SuppressWarnings("unchecked")
	public List<ThroughExpressPathEntity> queryMainPathList(ThroughExpressPathEntity throughExpressPathEntity){
		return this.getSqlSession().selectList(NAMESPACE+"queryMainPathList", throughExpressPathEntity);
	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号查询走货路由主表信息
	 * @parm:map
	 * @return:List<ThroughExpressPathEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:44:17
	 */
	@SuppressWarnings("unchecked")
	public List<ThroughExpressPathEntity> queryMainPathByPackagekNoList(List<String> packageNoList){
		return this.getSqlSession().selectList(NAMESPACE+"queryMainPathByPackagekNoList", packageNoList);
	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询走货路由明细信息
	 * @parm:throughExpressPathDetailEntity
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:47:36
	 */
	@SuppressWarnings("unchecked")
	public List<ThroughExpressPathDetailEntity> queryPathDetilList(ThroughExpressPathDetailEntity throughExpressPathDetailEntity){
		return this.getSqlSession().selectList(NAMESPACE+"queryPathDetilList", throughExpressPathDetailEntity);
	}
	

	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号查询走货路由明细
	 * @parm:packageNoList
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月20日下午2:49:17
	 */
	@SuppressWarnings("unchecked")
	public  List<ThroughExpressPathDetailEntity> queryPathDetilByPackageNoList(List<String> packageNoList){
		return this.getSqlSession().selectList(NAMESPACE+"queryPathDetilByPackageNoList", packageNoList);
	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号删除走货路主表
	 * @parm:packageNoList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:50:49
	 */
	public void deleteMainPathByPackageNoList(List<String> packageNoList){
		 this.getSqlSession().delete(NAMESPACE+"deleteMainPathByPackageNoList", packageNoList);

	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据ID删除走货路主表
	 * @parm:idList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:52:56
	 */
	public void deleteMainPathByIdList(List<String> idList){
		 this.getSqlSession().delete(NAMESPACE+"deleteMainPathByIdList", idList);

	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据包号删除走货路明细表
	 * @parm:packageNoList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:54:16
	 */
	public void deletePathDetailByPackageNoList(List<String> packageNoList){
		 this.getSqlSession().delete(NAMESPACE+"deletePathDetailByPackageNoList", packageNoList);

	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据ID删除走货路主表
	 * @parm:idList
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:55:12
	 */
	public void deletePathDetailByIdList(List<String> idList){
		 this.getSqlSession().delete(NAMESPACE+"deletePathDetailByIdList", idList);

	}
	
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询为 出发部门最小路段
	 * @parm:PathdetailExtensionEntity
	 * @return:PathdetailExtensionEntity
	 * @author:wqh
	 * @date:2014年9月22日上午9:35:06
	 */
	@SuppressWarnings("unchecked")
	public PathdetailExtensionEntity queryDepartMinRoutePathDetail( PathdetailExtensionEntity pathdetailExtensionEntity){
	
		List<PathdetailExtensionEntity> pathDetailList=this.getSqlSession().selectList(NAMESPACE+"queryDepartMinRoutePathDetail", pathdetailExtensionEntity);
	    if(pathDetailList!=null&&pathDetailList.size()==1){
	    	return pathDetailList.get(0);
	    }else{
	    	return null;
	    }
	    
	}

	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询为 到达部门最大路段
	 * @parm:PathdetailExtensionEntity
	 * @return:PathdetailExtensionEntity
	 * @author:wqh
	 * @date:2014年9月22日上午9:36:34
	 */
	@SuppressWarnings("unchecked")
	public PathdetailExtensionEntity queryArriveMaxRoutePathDetail( PathdetailExtensionEntity pathdetailExtensionEntity){
		
		List<PathdetailExtensionEntity> pathDetailList=this.getSqlSession().selectList(NAMESPACE+"queryArriveMaxRoutePathDetail", pathdetailExtensionEntity);
	    if(pathDetailList!=null&&pathDetailList.size()==1){
	    	return pathDetailList.get(0);
	    }else{
	    	return null;
	    }
	}
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据路由号查询所有路段
	 * @parm:waybillNo
	 * @return:List<PathdetailExtensionEntity>
	 * @author:wqh 
	 * @param:goodsNo
	 * @date:2014年9月22日上午10:09:43
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PathdetailExtensionEntity> queryAllPathDetailList(String waybillNo,String goodsNo,int minRouteNo,int maxRouteNo ){
		
		Map dataMap=new HashMap();
		dataMap.put("waybillNo", waybillNo);
		if(goodsNo==null){
			dataMap.put("goodsNo", goodsNo);
			
		}
		dataMap.put("minRouteNo", minRouteNo);
		dataMap.put("maxRouteNo", maxRouteNo);
        
		return this.getSqlSession().selectList(NAMESPACE+"queryAllPathDetailList", dataMap);
		
		
	}

	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询直达包当前部门为到达部门最大端路径
	 * @parm:PathdetailExtensionEntity
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月24日上午11:17:20
	 */
	@SuppressWarnings("unchecked")
	public ThroughExpressPathDetailEntity queryThroughPackageArriveMaxRoutePathDetail(ThroughExpressPathDetailEntity pathdetailExtensionEntity){
		
		List<ThroughExpressPathDetailEntity> detailList= this.getSqlSession().selectList(NAMESPACE+"queryThroughPackageArriveMaxRoutePathDetail", pathdetailExtensionEntity);
       //foss-218427 sonar优化  去除错误逻辑
		if(detailList.size()>0){
        	return detailList.get(0);
        }else{
        	return null;
        }
		
		
	}
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:将所有路段置为 DEPART_PACKAGE状态
	 * @parm:PathdetailExtensionEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月24日下午4:41:50
	 */
	public void updatePathDetailForDepart(ThroughExpressPathDetailEntity pathdetailExtensionEntity){
		
		this.getSqlSession().update(NAMESPACE+"updatePathDetailForDepart", pathdetailExtensionEntity);
	}
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据运单号查询大于当前路段号的所有路段的到达部门
	 * @parm:PathdetailExtensionEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月24日下午4:41:50
	 */
	@SuppressWarnings("unchecked")
	public List<PathdetailExtensionEntity> queryAllArriveOrigCodeByWaybillNo(PathdetailExtensionEntity pathdetailExtensionEntity){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryAllArriveOrigCodeByWaybillNo", pathdetailExtensionEntity);
	}
	
	/** 保存快递直达包扫描运单记录*/
	public void addThroughPackageScanWaybill(ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity){
		
		 this.getSqlSession().insert(NAMESPACE+"addThroughPackageScanWaybill",throughPackageScanWaybillEntity);

	}
	
	/** 修改快递直达包扫描运单记录*/
	public void updateThroughPackageScanWaybill(ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity){
		this.getSqlSession().update(NAMESPACE+"updateThroughPackageScanWaybill", throughPackageScanWaybillEntity);
	}
	
	/**  查询快递直达包扫描记录*/
	@SuppressWarnings("unchecked")
	public List<ThroughPackageScanWaybillEntity> queryThroughPackageScanWaybil(ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryThroughPackageScanWaybil", throughPackageScanWaybillEntity);
	}
	
	
	/**根据运单集合查询所有的走货路径明细 */
	@SuppressWarnings("unchecked")
	public List<PathdetailExtensionEntity> queryPathDetailList(List<String> waybillNoList)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryPathDetailList", waybillNoList);

	}
	/**根据运单集合查询所有的走货路径明细 (车辆任务明细表关联单据明细表以及交接明细表做查询)*/
	@SuppressWarnings("unchecked")
	public List<PathdetailExtensionDto> queryPathDetailLists(List<String> waybillNoList)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryPathDetailLists", waybillNoList);

	}
	/**查询当前部门运单所有的出发部门与到达部门*/
	@SuppressWarnings("unchecked")
	public List<PathdetailExtensionEntity> queryDepartAndArriveOrigCodeByWaybillNos(List<String> waybillNoList,String departOrgCode,String arriveOrgCode)
	{
		Map dataMap=new HashMap();
		dataMap.put("waybillNoList", waybillNoList);
		dataMap.put("departOrgCode", departOrgCode);
		dataMap.put("arriveOrgCode", arriveOrgCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryDepartAndArriveOrigCodeByWaybillNos", dataMap);
		
		
	}
}
