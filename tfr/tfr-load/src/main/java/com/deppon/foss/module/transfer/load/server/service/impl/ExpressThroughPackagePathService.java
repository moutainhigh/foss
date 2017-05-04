package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressThroughPackagePathDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressThroughPackagePathService;
import com.deppon.foss.module.transfer.load.api.shared.define.ExpressThroughPackageConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PathdetailExtensionEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughExpressPathDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughExpressPathEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PathdetailExtensionDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author wqh
 * @desc:直达包走货路由
 * @date：2014-09-18
 */
public class ExpressThroughPackagePathService implements IExpressThroughPackagePathService {

	static final Logger LOGGER = LoggerFactory
			.getLogger(ExpressThroughPackagePathService.class);

	private IPDAExpressPackageDao pdaExpressPackageDao;
	
	private ITransportationPathDao transportationPathDao;//走货路径dao
	

	private IExpressThroughPackagePathDao expressThroughPackagePathDao;
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.service
	 * @desc:创建直达包走货路由
	 * @parm:packageNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月18日上午10:48:28
	 */ 
	@Transactional
	public void createThroughPackagePath(String packageNo) {
		LOGGER.info("===============开始创建快递直达包走货路由================");
		if (StringUtil.isEmpty(packageNo)) {
			LOGGER.error("包号为空");
			throw new TfrBusinessException("包号为空");
		}
		// 检验
		ExpressPackageEntity expressPackage = checkThroughPackage(packageNo);

		//判断是否为直达包或者空运直达包
		if (!TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_THROUGH_ARRIVE.equals(expressPackage.getExpressPackageType())&&
				!TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_AIR_THROUGH_ARRIVE.equals(expressPackage.getExpressPackageType())) 
		{

			LOGGER.error("包号:{" + packageNo + "}  不是快递直达包或空运直达包，不能创建走货路由");
			throw new TfrBusinessException("包号:{" + packageNo + "}  不是快递直达包或空运直达包，不能创建走货路由");
			
		}
		//判断直达包走货路由是否存在，如果存在需将之前记录找出来删除
		List<String> packageNoList=new ArrayList<String>();
		packageNoList.add(packageNo);
		List<ThroughExpressPathEntity> throughExpressPathEntityList=expressThroughPackagePathDao.queryMainPathByPackagekNoList(packageNoList);
		if(throughExpressPathEntityList!=null&&throughExpressPathEntityList.size()>0){
			expressThroughPackagePathDao.deleteMainPathByPackageNoList(packageNoList);//
		}
		
		ThroughExpressPathDetailEntity pathDetail=new ThroughExpressPathDetailEntity();
		pathDetail.setPackageNo(packageNo);
		List<ThroughExpressPathDetailEntity> pathPackageDetailList= expressThroughPackagePathDao.queryPathDetilList(pathDetail);
		
		if(pathPackageDetailList!=null&&pathPackageDetailList.size()>0){
			expressThroughPackagePathDao.deletePathDetailByPackageNoList(packageNoList);
		}
		
		//查直达包中的运单
   	     List<ExpressPackageGoodsDto> packScanSerialGoods = pdaExpressPackageDao.queryScanPackageGoods(packageNo);
         if(packScanSerialGoods==null||packScanSerialGoods.size()==0)
         {
        	 LOGGER.error("包号:{" + packageNo + "}  不存在运单明细,不能提交");
 			throw new TfrBusinessException("包号:{" + packageNo + "}  不存在运单明细,不能提交");
         }
         String waybillNo=packScanSerialGoods.get(0).getWayBillNo(); 
         
         //查询明细中的某一个运单 ，根据运单查询走货路径主表
         TransportPathEntity transportPathEntity=new TransportPathEntity();
         
         transportPathEntity.setWaybillNo(waybillNo);
         List<TransportPathEntity> transportPathList=transportationPathDao.queryTransportPathList(transportPathEntity);
         if(transportPathList==null||transportPathList.size()==0){
        	 LOGGER.error(waybillNo+"  ：走货路径不存在");
  			throw new TfrBusinessException(waybillNo+"  ：走货路径不存在");
         }  
		//如果存在多条 
         if(transportPathList.size()>1){
        	 LOGGER.error(waybillNo+"  ：存在多条走货路径");
   			throw new TfrBusinessException(waybillNo+"  ：存在多条走货路径");
         }
         //是否为分批配载
         boolean isOptBatch=false;
         transportPathEntity=transportPathList.get(0);
         if(FossConstants.YES.equals(transportPathEntity.getIfPartialStowage()))
         {
        	 isOptBatch=true;
         }
         PathdetailExtensionEntity pathdetailExtensionEntity=new PathdetailExtensionEntity();
         pathdetailExtensionEntity.setWaybillNo(waybillNo);
         
         //如果是分批配载 ，传入流水号
         if(isOptBatch){
        	
        	 pathdetailExtensionEntity.setGoodsNo(packScanSerialGoods.get(0).getSeiralNo());
         }
         //当前部门
         pathdetailExtensionEntity.setOrigOrgCode(expressPackage.getDepartOrgCode());
         //查询直达包出发部门为当走货路径的最小路段
         PathdetailExtensionEntity minPathDetailEntity=expressThroughPackagePathDao.queryDepartMinRoutePathDetail(pathdetailExtensionEntity);
         
         if(minPathDetailEntity==null){
        	 LOGGER.error("【直达包生成走货路径】--> 运单{ "+waybillNo+" } 没有经过 部门 ："+expressPackage.getDepartOrgName());
   			throw new TfrBusinessException("【直达包生成走货路径】--> 运单{ "+waybillNo+" } 没有经过 部门 ："+expressPackage.getDepartOrgName());
        	 
         }
         
         int minRouteNo=Integer.parseInt(minPathDetailEntity.getRouteNo());
         //最终到达部门
         pathdetailExtensionEntity.setObjectiveOrgCode(expressPackage.getArriveOrgCode());
         
         //查询直达包到达部门为走货路径到达部门的最大路段
         PathdetailExtensionEntity maxPathDetailEntity=expressThroughPackagePathDao.queryArriveMaxRoutePathDetail(pathdetailExtensionEntity);
         if(maxPathDetailEntity==null){
        	 LOGGER.error("【直达包生成走货路径】--> 运单{ "+waybillNo+" } 没有经过 部门 ："+expressPackage.getDepartOrgName()+"-----");
    			throw new TfrBusinessException("【直达包生成走货路径】--> 运单{ "+waybillNo+" } 没有经过 部门 ："+expressPackage.getDepartOrgName()+"-----");
         }
         
         int maxRouteNo=Integer.parseInt(maxPathDetailEntity.getRouteNo());
         
         //存在 出发部门为当前部门的最小路段，直达包到达部门为走货路径到达部门的最大路段 ，查询出两者之间的所有走货路径明细段
         
         String goodsNo=null;
         if(pathdetailExtensionEntity.getGoodsNo()!=null){
        	 goodsNo=pathdetailExtensionEntity.getGoodsNo();
         }
         
         List<PathdetailExtensionEntity> allPathDetailList=expressThroughPackagePathDao.queryAllPathDetailList(waybillNo, goodsNo, minRouteNo, maxRouteNo);
         if(allPathDetailList==null||allPathDetailList.size()==0){
        	 LOGGER.error(waybillNo+"  ：走货路径 { "+minRouteNo+" } 至 "+maxRouteNo+" 路径不存在！");
 			throw new TfrBusinessException(waybillNo+"  ：走货路径 { "+minRouteNo+" } 至 "+maxRouteNo+" 路径不存在 ！");
         }
        //创建直达包走货路由
         addPackagePathAndPathDetail(expressPackage,allPathDetailList);

	}
	
	/**
	 * 创建直达包走货路由
	 * */
	private void addPackagePathAndPathDetail(ExpressPackageEntity expressPackage,List<PathdetailExtensionEntity> pathDetailList)
	{
		ThroughExpressPathEntity throughExpressPathEntity=new ThroughExpressPathEntity();
		//初始化路由主表
		//id
		throughExpressPathEntity.setId(UUIDUtils.getUUID());
		//直达包号
		throughExpressPathEntity.setPackageNo(expressPackage.getPackageNo());
		//建包部门code
		throughExpressPathEntity.setCreateOrgCode(expressPackage.getDepartOrgCode());
		//建包部门名称
		throughExpressPathEntity.setCreateOrgName(expressPackage.getDepartOrgName());
		//重量
		throughExpressPathEntity.setWeight(expressPackage.getWeight());
		//体积
		throughExpressPathEntity.setVolume(expressPackage.getVolume());
		//票数
		throughExpressPathEntity.setWaybillQty(expressPackage.getWaybillQty());
		//件数
		throughExpressPathEntity.setSerialQty(expressPackage.getGoodsQty().intValue());
		//状态
		throughExpressPathEntity.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS_CREATE_PACKAGE);
		//路由描述
		throughExpressPathEntity.setPackagePath(pathNameJoinDetail(pathDetailList));
		//创建人code
		throughExpressPathEntity.setCreateUserCode(expressPackage.getCreateUserCode());
		//创建人
		throughExpressPathEntity.setCreateUser(expressPackage.getCreateUserName());
		//创建日期
		throughExpressPathEntity.setCreateDate(new Date());
		//修改人code
		throughExpressPathEntity.setModifyUserCode(expressPackage.getCreateUserCode());
		//修改人
		throughExpressPathEntity.setModifyUser(expressPackage.getCreateUserName());
		//修改日期
		throughExpressPathEntity.setModifyDate(new Date());
		//添加主表路段
		try {
			expressThroughPackagePathDao.addThroughExpressPackagePath(throughExpressPathEntity);
			
		} catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
		/**
		 * 初始化走货路由明细
		 * */
		List<ThroughExpressPathDetailEntity> throughPathDetailList=new ArrayList<ThroughExpressPathDetailEntity>();
		for(int i=0;i<pathDetailList.size();i++){
			PathdetailExtensionEntity pathdetailExtensionEntity=pathDetailList.get(i);
			ThroughExpressPathDetailEntity pathDetail=new ThroughExpressPathDetailEntity();
			//id
			pathDetail.setId(UUIDUtils.getUUID());
			//包号
			pathDetail.setPackageNo(expressPackage.getPackageNo());
			//package_path_id
			pathDetail.setPackagePathId(throughExpressPathEntity.getId());
			//建包部门code
			pathDetail.setCreateOrgCode(expressPackage.getDepartOrgCode());
			//建包部门名称
			pathDetail.setCreateOrgName(expressPackage.getDepartOrgName());
			//当前部门code
			pathDetail.setOrigOrgCode(pathdetailExtensionEntity.getOrigOrgCode());
			//当前部门名称
			pathDetail.setOrigOrgName(pathdetailExtensionEntity.getOrigOrgName());
			//目的部门部门code
			pathDetail.setObjectiveOrgCode(pathdetailExtensionEntity.getObjectiveOrgCode());
			//目的部门名称
			pathDetail.setObjectiveOrgName(pathdetailExtensionEntity.getObjectiveOrgName());
			//状态
			pathDetail.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS_CREATE_PACKAGE);
			//路段号
			pathDetail.setRouteNo(i+1);
			//车牌号
			pathDetail.setVehicleNo(null);
			//到达时间
			pathDetail.setArriveTime(null); 
			//出发时间
			pathDetail.setDepartTime(null);
			//创建人code
			pathDetail.setCreateUserCode(expressPackage.getCreateUserCode());
			//创建人
			pathDetail.setCreateUser(expressPackage.getCreateUserName());
			//修改人code
			pathDetail.setModifyUserCode(expressPackage.getCreateUserCode());
			//创建人
			pathDetail.setModifyUser(expressPackage.getCreateUserName());
			//创建时间
			pathDetail.setCreateDate(new Date());
			//修改时间
			pathDetail.setModifyDate(new Date());
			throughPathDetailList.add(pathDetail);
		}
		//添加明细路段
		try {
			expressThroughPackagePathDao.addThroughExpressPackagePathDetailList(throughPathDetailList);
			
		} catch (Exception e) {

			throw new TfrBusinessException(e.getMessage());
		}
	
		
	}

	/**
	 * 路由拼接
	 * */
	private String pathNameJoinDetail(List<PathdetailExtensionEntity> pathDetailList){
		StringBuffer pathName=new StringBuffer();
		
		for(int i=0;i<pathDetailList.size();i++)
		{
			if(i==0){
				pathName.append(pathDetailList.get(i).getOrigOrgName()+"—"+pathDetailList.get(i).getObjectiveOrgName());
				
			}else{
				pathName.append("—"+pathDetailList.get(i).getObjectiveOrgName());
			}
		}
		
		return pathName.toString(); 
	}
	
	/**
	 * 参数校验 快递直达包
	 * */
	private ExpressPackageEntity checkThroughPackage(String packageNo)
	{
		if (StringUtil.isEmpty(packageNo)) {
			LOGGER.error("包号为空");
			throw new TfrBusinessException("包号为空");
		}
		// 查包号是否存在
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);

		if (expressPackage == null) {
			LOGGER.error("包号:{" + packageNo + "}  不存在");
			throw new TfrBusinessException("包号:{" + packageNo + "}  不存在");
		}
		return expressPackage;
	}
	
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.service
	 * @desc:直达包出发
	 * @parm:packageNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月18日上午11:00:42
	 */
	@Transactional
	public void depart(String packageNo,String vehicleNo, String currentOrgCode, String userCode,String userName)
	{
		//参数校验
		checkThroughPackage(packageNo);
		//修改路由主表
	    //修改走货路由主表
	    ThroughExpressPathEntity throughExpressPathEntity=checkPackagePath(packageNo);
	    //将状态更改为出发
	    throughExpressPathEntity.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS__DEPART_PACKAGE);
	    //修改信息
	    throughExpressPathEntity.setModifyUserCode(userCode);
	    throughExpressPathEntity.setModifyUser(userName);
	    throughExpressPathEntity.setModifyDate(new Date());
	    expressThroughPackagePathDao.updateMainThroughPackagePath(throughExpressPathEntity);
	    /**
	     * 修改明细信息
	     * */
	    //找到始发部门为当前部门的路段
	    ThroughExpressPathDetailEntity pathDetail=new ThroughExpressPathDetailEntity();
	    pathDetail.setPackageNo(packageNo);
	    pathDetail.setOrigOrgCode(currentOrgCode);
	    //pathDetail.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS_CREATE_PACKAGE);
	    List<ThroughExpressPathDetailEntity> pathDetailList=expressThroughPackagePathDao.queryPathDetilList(pathDetail);
	    if(pathDetailList==null||pathDetailList.size()==0)
	    {
	    	LOGGER.error("始发部门为当前部门的路段不存在");
			throw new TfrBusinessException("始发部门为当前部门的路段不存在");
	    }
	    if(pathDetailList.size()>1)
	    {
	    	LOGGER.error("始发部门为当前部门的路段存在多段");
			throw new TfrBusinessException("始发部门为当前部门的路段存在多段");
	    }
	    //更新当前路段
	    pathDetail=pathDetailList.get(0);
	    pathDetail.setDepartTime(new Date());//出发时间
	    pathDetail.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS__LEAVE_PACKAGE);//修改未出发状态
	    pathDetail.setModifyDate(new Date());
	    pathDetail.setModifyUserCode(userCode);
	    pathDetail.setModifyUser(userName);
	    pathDetail.setVehicleNo(vehicleNo);
	    expressThroughPackagePathDao.updateThroughPackagePathDetail(pathDetail);
	}
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.service
	 * @desc:直达包到达
	 * @parm:packageNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月18日上午11:02:00
	 */
	@Transactional
	public void arrive(String packageNo,String vehicleNo, String currentOrgCode, String userCode,String userName) {
	
		//参数校验
	    checkThroughPackage(packageNo);	
	    //修改走货路由主表
	    ThroughExpressPathEntity throughExpressPathEntity=checkPackagePath(packageNo);
	    //状态为达到
	    throughExpressPathEntity.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS__ARRIVE_PACKAGE);
	    //修改信息
	    throughExpressPathEntity.setModifyUserCode(userCode);
	    throughExpressPathEntity.setModifyUser(userName);
	    throughExpressPathEntity.setModifyDate(new Date());
	    expressThroughPackagePathDao.updateMainThroughPackagePath(throughExpressPathEntity);
	    /**
	     * 修改明细表信息
	     * 
	     * */
	    //找出本段状态，修改为 ARRIVE_PACKAGE
	    ThroughExpressPathDetailEntity pathDetail=new ThroughExpressPathDetailEntity();
	    pathDetail.setPackageNo(packageNo);
	    pathDetail.setObjectiveOrgCode(currentOrgCode);
	    //找到到达部门为当前部门的最大路段
	    ThroughExpressPathDetailEntity pathdetailExtensionEntity=expressThroughPackagePathDao.queryThroughPackageArriveMaxRoutePathDetail(pathDetail);
	    if(pathdetailExtensionEntity==null){
	    	LOGGER.error("到达部门为当前部门的路段不存在");
			throw new TfrBusinessException("到达部门为当前部门的路段不存在");
	    }
	    //到达时间
	    pathdetailExtensionEntity.setDepartTime(new Date());
	    //状态
	    pathdetailExtensionEntity.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS__ARRIVE_PACKAGE);
	    //车牌号
	    if(StringUtil.isNotEmpty(vehicleNo)){
	    	pathdetailExtensionEntity.setVehicleNo(vehicleNo);
	    }
	    //修改时间
	    pathdetailExtensionEntity.setModifyDate(new Date());
	    //修改人
	    pathdetailExtensionEntity.setModifyUser(userName);
	    //修改人code
	    pathdetailExtensionEntity.setModifyUserCode(userCode);
	    //调dao
	    expressThroughPackagePathDao.updateThroughPackagePathDetail(pathdetailExtensionEntity);
	    /**
	     * 将之前所有路段置为  LEAVE_PACKAGE
	     * 
	     * */
	    expressThroughPackagePathDao.updatePathDetailForDepart(pathdetailExtensionEntity);
	    
	    
	
	}

	/**
	 * 验证直达包走货路由
	 * 
	 * */
	
	private ThroughExpressPathEntity checkPackagePath(String packageNo){
		
	  List<String> packageNoList=new ArrayList<String>();
		packageNoList.add(packageNo);
		List<ThroughExpressPathEntity> throughExpressPathList=expressThroughPackagePathDao.queryMainPathByPackagekNoList(packageNoList);
	    if(throughExpressPathList==null||throughExpressPathList.size()<1){
	    	LOGGER.error("快递直达包号:{" + packageNo + "}  走货路由主信息不存在");
			throw new TfrBusinessException("快递直达包号:{" + packageNo + "}  走货路由主信息不存在");
	    }
		//主表信息存在多条
	    if(throughExpressPathList.size()>1){
	    	LOGGER.error("快递直达包号:{" + packageNo + "}  存在多条走货路由主信息");
			throw new TfrBusinessException("快递直达包号:{" + packageNo + "}  存在多条走货路由主信息");
	    }
	    
	  return throughExpressPathList.get(0);
	  }
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.service
	 * @desc:解包
	 * @parm:packageNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月18日上午11:03:22
	 */
	@Transactional
	public void unbindPackage(String packageNo,String currentOrgCode, String userCode,String userName) {
		
		//参数校验
	    checkThroughPackage(packageNo);	
	    //修改走货路由主表
	    ThroughExpressPathEntity throughExpressPathEntity=checkPackagePath(packageNo);
	    //状态为达到
	    throughExpressPathEntity.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS_UNBIND_PACKAGE);
	    //修改信息
	    throughExpressPathEntity.setModifyUserCode(userCode);
	    throughExpressPathEntity.setModifyUser(userName);
	    throughExpressPathEntity.setModifyDate(new Date());
	    throughExpressPathEntity.setUnbindOrgCode(currentOrgCode);//解包部门
	    expressThroughPackagePathDao.updateMainThroughPackagePath(throughExpressPathEntity);
		
	    /**
	     * 修改明细表信息
	     * 
	     * */
	    //找出本段状态，修改为 ARRIVE_PACKAGE
	    ThroughExpressPathDetailEntity pathDetail=new ThroughExpressPathDetailEntity();
	    pathDetail.setPackageNo(packageNo);
	    pathDetail.setObjectiveOrgCode(currentOrgCode);
	    //找到到达部门为当前部门的最大路段
	    ThroughExpressPathDetailEntity pathdetailExtensionEntity=expressThroughPackagePathDao.queryThroughPackageArriveMaxRoutePathDetail(pathDetail);
	    if(pathdetailExtensionEntity==null){
	    	LOGGER.error("到达部门为当前部门的路段不存在");
			throw new TfrBusinessException("到达部门为当前部门的路段不存在");
	    }
	    
	    //状态
	    pathdetailExtensionEntity.setStatus(ExpressThroughPackageConstants.EXPRESS_THROUGH_STATUS_UNBIND_PACKAGE);
	    
	    //修改时间
	    pathdetailExtensionEntity.setModifyDate(new Date());
	    //修改人
	    pathdetailExtensionEntity.setModifyUser(userName);
	    //修改人code
	    pathdetailExtensionEntity.setModifyUserCode(userCode);
	    //调dao
	    expressThroughPackagePathDao.updateThroughPackagePathDetail(pathdetailExtensionEntity);
	    /**
	     * 将之前所有路段置为  LEAVE_PACKAGE
	     * 
	     * */
	    expressThroughPackagePathDao.updatePathDetailForDepart(pathdetailExtensionEntity);
	    
	    
		
	}

	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:新增直达包走货路由主信息
	 * @parm:throughExpressPathEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月20日下午2:30:37
	 */
	public void addThroughExpressPackagePath(ThroughExpressPathEntity throughExpressPathEntity){
		expressThroughPackagePathDao.addThroughExpressPackagePath(throughExpressPathEntity);
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
		expressThroughPackagePathDao.addThroughExpressPackagePathDetailList(pathDetailList);
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
		expressThroughPackagePathDao.updateMainThroughPackagePath(throughExpressPathEntity);
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
		expressThroughPackagePathDao.updateThroughPackagePathDetail(throughExpressPathDetailEntity);
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
	public List<ThroughExpressPathEntity> queryMainPathList(ThroughExpressPathEntity throughExpressPathEntity){
		return expressThroughPackagePathDao.queryMainPathList(throughExpressPathEntity);
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
	public List<ThroughExpressPathEntity> queryMainPathByPackagekNoList(List<String> packageNoList){
		return expressThroughPackagePathDao.queryMainPathByPackagekNoList(packageNoList);
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
	public List<ThroughExpressPathDetailEntity> queryPathDetilList(ThroughExpressPathDetailEntity throughExpressPathDetailEntity){
		return expressThroughPackagePathDao.queryPathDetilList(throughExpressPathDetailEntity);
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
	public  List<ThroughExpressPathDetailEntity> queryPathDetilByPackageNoList(List<String> packageNoList){
		return expressThroughPackagePathDao.queryPathDetilByPackageNoList(packageNoList);
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
		expressThroughPackagePathDao.deleteMainPathByPackageNoList(packageNoList);
	}
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询为 到达部门最大路段
	 * @parm:PathdetailExtensionEntity
	 * @return:PathdetailExtensionEntity
	 * @author:wqh
	 * @date:2014年9月22日上午9:36:34
	 */
	public PathdetailExtensionEntity queryArriveMaxRoutePathDetail( PathdetailExtensionEntity pathdetailExtensionEntity)
	{
		return 	expressThroughPackagePathDao.queryArriveMaxRoutePathDetail(pathdetailExtensionEntity);
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
		expressThroughPackagePathDao.deleteMainPathByIdList(idList);
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
		expressThroughPackagePathDao.deletePathDetailByPackageNoList(packageNoList);
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
		
		expressThroughPackagePathDao.deletePathDetailByIdList(idList);
	}
	
	//
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:查询直达包当前部门为到达部门最大端路径
	 * @parm:PathdetailExtensionEntity
	 * @return:List<ThroughExpressPathDetailEntity>
	 * @author:wqh
	 * @date:2014年9月24日上午11:17:20
	 */
	public ThroughExpressPathDetailEntity queryThroughPackageArriveMaxRoutePathDetail(ThroughExpressPathDetailEntity pathdetailExtensionEntity){
		//wwb sonar 2016年12月21日08:40:23 返回值未使用
		/*ThroughExpressPathDetailEntity pathdetailExtensionEntity2=*/
		expressThroughPackagePathDao.queryThroughPackageArriveMaxRoutePathDetail(pathdetailExtensionEntity);
		if(pathdetailExtensionEntity==null){
			LOGGER.error("查询直达包当前部门为到达部门路由明细为空");
			throw new TfrBusinessException("查询直达包当前部门为到达部门路由明细为空");
		}
	
		return pathdetailExtensionEntity;
	}
	
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据运单号查询大于当前路段号的所有路段的到达部门
	 * @parm:PathdetailExtensionEntity
	 * @return:void
	 * @author:wqh
	 * @date:2014年9月24日下午4:41:50
	 */
	public List<PathdetailExtensionEntity> queryAllArriveOrigCodeByWaybillNo(PathdetailExtensionEntity pathdetailExtensionEntity)
	{
		if(pathdetailExtensionEntity==null){
			throw new TfrBusinessException("参数不能为空");
		}
		return expressThroughPackagePathDao.queryAllArriveOrigCodeByWaybillNo(pathdetailExtensionEntity);
	}
	
	
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据运单集合查询所有的走货路径明细
	 * @parm:waybillNoList
	 * @return:List<PathdetailExtensionEntity>
	 * @author:wqh
	 * @date:2014年9月24日下午4:41:50
	 */
	public List<PathdetailExtensionEntity> queryPathDetailList(List<String> waybillNoList){
		
		if(waybillNoList==null||waybillNoList.size()==0){
			
			throw new TfrBusinessException("参数不能为空");
		}
		
		return expressThroughPackagePathDao.queryPathDetailList(waybillNoList);
	}
	/**
	 * @com.deppon.foss.module.transfer.load.api.server.dao
	 * @desc:根据运单集合查询所有的走货路径明细(通过车辆任务明细表关联单据明细表以及交接明细表做查询)
	 * @parm:waybillNoList
	 * @return:List<PathdetailExtensionDto>
	 * @author:336785
	 * 	
	 */
	public List<PathdetailExtensionDto> queryPathDetailLists(List<String> waybillNoList){
		
		if(waybillNoList==null||waybillNoList.size()==0){
			
			throw new TfrBusinessException("参数不能为空");
		}
		
		return expressThroughPackagePathDao.queryPathDetailLists(waybillNoList);
	}
	/**查询当前部门运单所有的出发部门与到达部门*/
	public List<PathdetailExtensionEntity> queryDepartAndArriveOrigCodeByWaybillNos(List<String> waybillNoList,String departOrgCode,String arriveOrgCode)
	{
       if(waybillNoList==null||waybillNoList.size()==0){
			
			throw new TfrBusinessException("运单不能为空");
		}
       if(StringUtil.isEmpty(departOrgCode))
       {
    	   throw new TfrBusinessException("出发部门不能为空");
       }
       if(StringUtil.isEmpty(arriveOrgCode))
       {
    	   throw new TfrBusinessException("目的部门不能为空");
       }
		return expressThroughPackagePathDao.queryDepartAndArriveOrigCodeByWaybillNos(waybillNoList,departOrgCode,arriveOrgCode);

	}
	
	
	/**
	 * set and get
	 * */

	public void setPdaExpressPackageDao(
			IPDAExpressPackageDao pdaExpressPackageDao) {
		this.pdaExpressPackageDao = pdaExpressPackageDao;
	}

	public void setTransportationPathDao(
			ITransportationPathDao transportationPathDao) {
		this.transportationPathDao = transportationPathDao;
	}

	public void setExpressThroughPackagePathDao(
			IExpressThroughPackagePathDao expressThroughPackagePathDao) {
		this.expressThroughPackagePathDao = expressThroughPackagePathDao;
	}
	

	
}
