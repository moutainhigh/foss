/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ICalculateTransportPathService.java
 * 
 *  FILE NAME     :ICalculateTransportPathService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GeneralQueryDto;

/**
 * 计算&调整走货路径类
 * @author huyue
 * @date 2012-10-31 下午5:24:06
 */
public interface ICalculateTransportPathService extends IService{

	/**
	 * 开单新增走货路径
	 * @author huyue
	 * @date 2012-10-30 上午11:27:32
	 */
	void createTransportPath(TransportPathEntity transportPathEntity)throws TfrBusinessException;
	
	/**
	* @description 跟运单号waybillNo删除走货路径主表
	* @param waybillNo
	* @throws TfrBusinessException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2016年4月13日 下午5:42:33
	*/
	void deleteTransportPath(String waybillNo)throws TfrBusinessException;
	
	/**
	 * 修改走货路径及其明细的运单号
	 * @author huyue
	 * @date 2013-3-18 下午4:16:59
	 */
	void modifyWaybillNo(String oldNo, String newNo) throws TfrBusinessException;
	
	/**
	 * 更改单修改开单信息 , 增加了现所在部门 从现所在部门开始修改走货路径, nowOrgCode为当前部门,即本批货所在部门,将会根据本部门和Entity中的新到达部门计算新的走货路径
	 * @author huyue
	 * @date 2012-10-30 上午11:27:51
	 */
	void modifyTransportPath(TransportPathEntity transportPathEntity, List<String> serialNo, String nowOrgCode, String operatorCode, String operatorName)throws TfrBusinessException;
	
	
	/**
	 * 删除开单信息
	 * @author huyue
	 * @date 2012-10-30 上午11:31:53
	 */
	void deleteTransportPath(String waybillNo, String action)throws TfrBusinessException;
	
	/**
	 * 货物包装
	 * @author huyue
	 * @date 2012-10-30 上午11:33:52
	 */
	void packing(String waybillNo, String packingOrgCode,
			List<String> origSerialNo, List<String> objectiveSerialNo)throws TfrBusinessException;
	
	/**
	 * 装车/交接单
	 * @author huyue
	 * @date 2012-10-30 上午11:34:11
	 */
	void loadVehicle(String waybillNo, List<String> serialNoList, String action,
			int loadQTY, int ifJoinCar, String nowOrgCode,
			String destOrgCode, String vehicleNo)throws TfrBusinessException;
	/**
	 * <pre>
	 * 此方法逻辑同loadVehicle方法，区别为使用REQUIRES_NEW事务，对应于外围调用时，此方法出错抛出异常后，需继续外围其他业务的情况
	 * 可解决外围调用可能出现的事务问题(Transaction rolled back because it has been marked as rollback-only)
	 * </pre>
	 * 
	 * @author foss-wuyingjie
	 * @date 2013-2-2 下午1:50:41
	 */
	void loadVehicleRequiresNewTransactional(String waybillNo, List<String> serialNoList, String action,
			int loadQTY, int ifJoinCar, String nowOrgCode,
			String destOrgCode, String vehicleNo);
	
	/**
	 * 出发
	 * @author huyue
	 * @date 2012-10-30 上午11:34:31
	 */
	void depart(String waybillNo, List<String> serialNoList, String origOrgCode,
			Date startTime, String vehicleNo, String action, String truckDetailId)throws TfrBusinessException;
	
	/**
	 * 到达
	 * @author huyue
	 * @date 2012-10-30 上午11:34:46
	 */
	void arrive(String waybillNo, List<String> serialNoList, String arriveOrgCode,
			Date arriveTime, String action, String vehicleNo)throws TfrBusinessException;
	
	/**
	 * 入库  By 流水号list
	 * @author huyue
	 * @date 2012-10-30 上午11:35:05
	 */
	void inStorageList(String waybillNo, List<String> goodsNoList, String orgCode,
			String action)throws TfrBusinessException;
	
	/**
	 * 入库
	 * @author huyue
	 * @date 2012-10-30 上午11:35:05
	 */
	void inStorage(String waybillNo, String goodsNo, String orgCode,
			String action)throws TfrBusinessException;
	
	/**
	 * 签收
	 * @author huyue
	 * @date 2012-10-30 上午11:35:22
	 */
	void signIn(String waybillNo, String action)throws TfrBusinessException;
	
	/**
	 * 回退到上一状态 , action为现状态  By 流水号list
	 * @author huyue
	 * @date 2012-10-30 上午11:35:37
	 */
	void rollBackList(String waybillNo, List<String> goodsNoList, String vehicle,
			String action)throws TfrBusinessException;
	
	/**
	 * 回退到上一状态 , action为现状态
	 * @author huyue
	 * @date 2012-10-30 上午11:35:37
	 */
	void rollBack(String waybillNo, String serialNo, String vehicle,
			String action)throws TfrBusinessException;
	
	/**
	 * 根据运单号,货件号,查询下一部门及出发时间
	 * @author huyue
	 * @date 2012-10-30 上午11:36:05
	 */
	FeedbackDto getNextOrgAndTime(String waybillNo, String goodsNo,
			String correntOrgCode)throws TfrBusinessException;
	
	/**
	 * 多货修改走货路径, 带serialNO, 如果不是分批则进行分批
	 * @author huyue
	 * @date 2012-10-30 上午11:36:19
	 */
	void arriveMistake(String waybillNo, String serialNo,
			String arriveOrgCode, String beforeOrgCode)throws TfrBusinessException;
	
	/**
	 * 修改违禁品后序走货路径
	 * @author huyue
	 * @date 2012-12-5 下午4:00:54
	 */
	void contraband(String waybillNo, String serialNo,
			String nowOrgCode, String destOrgCode) throws TfrBusinessException;
	
	/**
	 * 调整路径明细路径
	 * @author huyue
	 * @date 2012-10-30 上午11:36:34
	 */
	void alterDetail(String waybillNo, String serialNo,
			String nowOrgCode, String destOrgCode, String billingOrgCode, String model)throws TfrBusinessException;
	
	/**
	 * 非合车调整走货路径
	 * @author huyue
	 * @date 2012-11-6 下午8:27:46
	 */
	void notJoinCarModify(String waybillNo, String serialNo, String origGoodsAreaCode, Date modifyTime,
			String nowOrgCode) throws TfrBusinessException;
	
	/**
	 * 拆分走货路径明细条目,用于各种类型的分批配载
	 * @author huyue
	 * @date 2012-10-30 上午11:37:00
	 */
	void seperatePathDetail(String waybillNo)throws TfrBusinessException, Exception;
	
	/**
	 * 获取分批配载后的主表path 拼接
	 * @author huyue
	 * @date 2012-11-7 下午7:34:33
	 */
	String getChangedPath(String waybillNo);
	
	/**
	 * 查询运单中货件号,以剩余线路段数排序
	 * @author huyue
	 * @date 2012-11-26 上午10:39:22
	 */
	List<String> listFastGoodsNo(String waybillNo) throws TfrBusinessException;
	
	/**
	 * 找到有效的第一条返回 可以找到上一部门
	 * @author huyue
	 * @date 2012-12-4 下午2:27:22
	 */
	PathDetailEntity findBeforeOrgCode(String waybillNo, String serialNo) throws TfrBusinessException;
	
	/**
	 * 查询有效的全部list
	 * @author huyue
	 * @date 2012-11-26 上午11:24:54
	 */
	PathDetailEntity queryEffect(String waybillNo, String serialNo) throws TfrBusinessException;
	
	/**
	 * 根据运单号,流水号,到达部门查询出发部门CODE
	 * @author huyue
	 * @date 2013-2-27 下午4:51:38
	 */
	String queryBeforeOrgCode(String waybillNo, String serialNo,
			String arriveOrgCode) throws TfrBusinessException;
	
	/**
	 * 查询货物全部路径
	 * @author huyue
	 * @date 2012-12-6 下午4:10:16
	 */
	List<PathDetailEntity> queryTotalPath(String waybillNo, String serialNo) throws TfrBusinessException;
	
	/**
	 * 提供给综合查询的接口,返回运单的状态明细, group by状态 ,出发部门,到达部门,出发时间,到达时间,下一到达时间
	 * @author huyue
	 * @date 2012-12-24 下午8:07:25
	 */
	List<GeneralQueryDto> queryWaybillStatusByWaybillNoForPkp(String waybillNo) throws TfrBusinessException;
	
	/**
	 * 新增流水号走货路径的初始化。
	 * 
	 * @author liangfuxiang
	 * @date 2013-03-13
	 * @throws TfrBusinessException
	 */
	void addNewTransportPath(String waybillNo, List<String> goodsNos, String currentOrgCode, String destOrgCode, String transportModel) throws TfrBusinessException;

	/**
	 * 移除选定货件的中转信息
	 * 
	 * @author liangfuxiang
	 * @date 2013-03-13
	 * @throws TfrBusinessException
	 */
	void deleteTransportPathByGoodsNos(String waybillNo, List<String> goodsNos) throws TfrBusinessException;

	/** 
	* @Title: alterDetailForRecreateTransportaionPath 
	* @Description: 修改走货路径
	* @param waybillNo
	* @param object
	* @param currentOrgCode
	* @param destOrgCode
	* @param billingOrgCode
	* @param transportModel  设定文件 
	* @return void    返回类型 
	* @see alterDetailForRecreateTransportaionPath
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-3-26 上午9:12:06   
	* @throws 
	*/ 
	void alterDetailForRecreateTransportaionPath(String waybillNo, String serialNo, String currentOrgCode, String destOrgCode, String billingOrgCode, String transportModel) throws TfrBusinessException;

	/**
	 * @throws TfrBusinessException  
	* @Title: migrateTransportPathData 
	* @Description:  对于已经签收的货物，走货路径需要迁移至备用表
	* @return void    返回类型 
	* @see migrateTransportPathData
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-27 下午6:32:32   
	* @throws 
	*/ 
	void migrateTransportPathData() throws TfrBusinessException;

	/**
	 * 
	* @Title: queryTransportPathMigration 
	* @Description: 查询走货路径数据迁移表，查看是否已经被数据迁移。
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return TransportPathEntity    返回类型 
	* @see queryTransportPathMigration
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-28 下午1:00:42   
	* @throws
	 */
	TransportPathEntity queryTransportPathMigration(String waybillNo) throws TfrBusinessException;
	

	// modify by liangfuxiang 2013-5-3下午4:53:31 begin BUG-8208
	/**
	 * TransportPathConstants.STATUS_WRONG（本部门不在货物走货路径中）拆分成两种状态： 
	 * 1.该票没有走路路径 
	 * 2.该票有走货路径但本部门不在走货路径上 
	 * @Title: getNextOrgAndTimeForDetailResult 
	 * @Description: 根据运单号,货件号,查询下一部门及出发时间
	 * @param waybillNo
	 * @param goodsNo
	 * @param correntOrgCode
	 * @return
	 * @throws TfrBusinessException 设定文件
	 * @return FeedbackDto 返回类型
	 * @see getNextOrgAndTimeForDetailResult
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-5-3 下午4:25:22
	 * @throws
	 */
	FeedbackDto getNextOrgAndTimeForDetailResult(String waybillNo, String goodsNo, String correntOrgCode) throws TfrBusinessException;
	
	/**
	 * 
	 * @Title: createTransportPathForStorage
	 * @Description: 中转对无走货路径货物加入处理
	 * @param transportPathEntity
	 * @throws TfrBusinessException 设定文件
	 * @return void 返回类型
	 * @see createTransportPathForStorage
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-5-3 下午5:37:50
	 * @throws
	 */
	void createTransportPathForStorage(String waybillNo,String currentOrgCode,String serialNo) throws TfrBusinessException;
	// modify by liangfuxiang 2013-5-3下午4:53:55 end;
	
	// modify by liangfuxiang 2013-5-20上午11:38:36 begin BUG-10466
	/**
	 * 
	* @Title: queryTotalPathForPrintLabel 
	* @Description: 打印标签查找走货路径接口
	* @param waybillNo
	* @param serialNo
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryTotalPathForPrintLabel
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-20 上午11:38:59   
	* @throws
	 */
	public List<PathDetailEntity> queryTotalPathForPrintLabel(String waybillNo, String serialNo) throws TfrBusinessException;
	// modify by liangfuxiang 2013-5-20上午11:38:42 end;
	/**
	 * 
	* @Title: getTransportModelNameByTransportModelCode 
	* @Description: 根据运输性质编码获取运输
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return String    返回类型 
	* @see getTransportModelNameByTransportModelCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-2 下午3:13:16   
	* @throws
	 */
	public String getTransportModelNameByTransportModelCode(String transportModelCode) throws TfrBusinessException;
	
	
	// modify by liangfuxiang 2013-6-8上午12:19:32 begin SUBBUG-940

	/**
	 * 
	 * @Title: queryTransportPath
	 * @Description: 根据运单号查询走货路径
	 * @return
	 * @throws TfrBusinessException 设定文件
	 * @return TransportPathEntity 返回类型
	 * @see queryTransportPath
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-8 上午12:19:10
	 * @throws
	 */
	public TransportPathEntity queryTransportPath(String waybillNo) throws TfrBusinessException;

	/**
	 * 
	 * @Title: listQueryGoodsNo
	 * @Description: 根据运单号查询流水号信息
	 * @param waybillNo
	 * @return
	 * @throws TfrBusinessException 设定文件
	 * @return List<String> 返回类型
	 * @see listQueryGoodsNo
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-8 上午12:20:22
	 * @throws
	 */
	public List<String> listQueryGoodsNo(String waybillNo) throws TfrBusinessException;
	// modify by liangfuxiang 2013-6-8上午12:19:39 end SUBBUG-940
	
	/**
	 * 
	 * @Title: queryActualTransportPath
	 * @Description: 获取实际的走货路径
	 * @return
	 * @throws TfrBusinessException 设定文件
	 * @return String 返回类型
	 * @see queryActualTransportPath
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-16 下午4:28:05
	 * @throws
	 */
	public String queryActualTransportPath(String waybillNo) throws TfrBusinessException;
	
	/**
	 * 
	 * @Title: departForAirHandover
	 * @Description: 出发
	 * @param waybillNo
	 * @param serialNoList
	 * @param origOrgCode
	 * @param startTime
	 * @param vehicleNo
	 * @param action
	 * @param truckDetailId
	 * @throws TfrBusinessException 设定文件
	 * @return void 返回类型
	 * @see departForAirHandover
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-17 下午3:06:41
	 * @throws
	 */
	public void departForAirHandover(String waybillNo, List<String> serialNoList, String origOrgCode, Date startTime, String vehicleNo, String action, String truckDetailId) throws TfrBusinessException;
	
	/**
	 * 
	* @Title: modifyTransportPathForAmendmentBill 
	* @Description: 更改单:调整走货路径.
	* @param transportPathEntity
	* @param serialNo
	* @param nowOrgCode
	* @param operatorCode
	* @param operatorName
	* @throws TfrBusinessException  设定文件 
	* @return void    返回类型 
	* @see modifyTransportPathForAmendmentBill
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-17 下午5:23:24   
	* @throws
	 */
	public void modifyTransportPathForAmendmentBill(TransportPathEntity transportPathEntity, List<String> serialNo, String nowOrgCode, String operatorCode, String operatorName) throws TfrBusinessException;
	
	
	/**
	* @description  更改单:调整走货路径. 和modifyTransportPathForAmendmentBill方法一样，增加了传入参数:入库类型(inStockType)
	* @param transportPathEntity
	* @param serialNo
	* @param nowOrgCode
	* @param operatorCode
	* @param operatorName
	* @param inStockType
	* @throws TfrBusinessException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月12日 下午2:36:10
	*/
	public void modifyTransportPathAmendmentBillType(TransportPathEntity transportPathEntity, List<String> serialNo, String nowOrgCode, String operatorCode, String operatorName,String inStockType) throws TfrBusinessException;
	
	/**
	 * 
	* @Title: updateTransportPathActionForInstore 
	* @Description: 更新走货路径的状态为入库。---INSTORE
	* @param waybillNo
	* @param goodsNo
	* @param currentOrgCode  设定文件 
	* @return void    返回类型 
	* @see updateTransportPathActionForInstore
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-26 上午9:41:49   
	* @throws
	 */
	public void updateTransportPathActionForInstore(String waybillNo,String goodsNo,String currentOrgCode);
	
	
	/**
	 * 
	* @Title: getOrgNameAndCode 
	* @Description: 获取组织名称和CODE
	* @return  设定文件 
	* @return String    返回类型 
	* @see getOrgNameAndCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-28 上午9:25:44   
	* @throws
	 */
	public String getOrgNameAndCode(String orgCode);
	
	/**
	 * 
	* @Title: getNextOrgAndTimeTransactionalNested 
	* @Description: 根据运单号,货件号,查询下一部门及出发时间
	* @param waybillNo
	* @param goodsNo
	* @param correntOrgCode
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return FeedbackDto    返回类型 
	* @see getNextOrgAndTimeTransactionalNested
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-2 下午10:01:07   
	* @throws
	 */
	public FeedbackDto getNextOrgAndTimeTransactionalNested(String waybillNo, String goodsNo,
			String correntOrgCode)throws TfrBusinessException;
	
	/**
	 * 
	* @Title: arriveMistake 
	* @Description: 多货修改走货路径, 带serialNO, 如果不是分批则进行分批
	* @param waybillNo
	* @param serialNo
	* @param arriveOrgCode
	* @param beforeOrgCode
	* @throws TfrBusinessException  设定文件 
	* @return void    返回类型 
	* @see arriveMistake
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-2 下午10:46:14   
	* @throws
	 */
	public void arriveMistakeTransactionalNested(String waybillNo, String serialNo, String arriveOrgCode, String beforeOrgCode) throws TfrBusinessException;
	
	/**
	 * 
	* @Title: notJoinCarModifyTransactionalNested 
	* @Description: 非合车调整走货路径
	* @param waybillNo
	* @param serialNo
	* @param origGoodsAreaCode
	* @param modifyTime
	* @param nowOrgCode
	* @throws TfrBusinessException  设定文件 
	* @return void    返回类型 
	* @see notJoinCarModifyTransactionalNested
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-2 下午10:57:18   
	* @throws
	 */
	public void notJoinCarModifyTransactionalNested(String waybillNo, String serialNo, String origGoodsAreaCode, Date modifyTime,
			String nowOrgCode) throws TfrBusinessException;
	/**
	 * 
	* @Title: inStorageTransactionalNested 
	* @Description: 卸车/入库
	* @param waybillNo
	* @param goodsNo
	* @param orgCode
	* @param action
	* @throws TfrBusinessException  设定文件 
	* @return void    返回类型 
	* @see inStorageTransactionalNested
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-3 下午4:21:19   
	* @throws
	 */
	public void inStorageTransactionalNested(String waybillNo, String goodsNo, String orgCode, String action) throws TfrBusinessException;
	
	/**
	 * 
	* @Title: updateTransportPathActionForInstore 
	* @Description: 更新走货路径的状态为入库。---INSTORE
	* @param waybillNo
	* @param goodsNo
	* @param currentOrgCode  设定文件 
	* @return void    返回类型 
	* @see updateTransportPathActionForInstore
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-3 下午4:27:05   
	* @throws
	 */
	public void updateTransportPathActionForInstoreTransactionalNested(String waybillNo,String goodsNo,String currentOrgCode);
	/**
	 * 
	* @Title: getNextOrgAndTimeForDetailResultInstoreTransactionalNested 
	* @Description: TransportPathConstants.STATUS_WRONG（本部门不在货物走货路径中）拆分成两种状态： 
	 * 1.该票没有走路路径 
	 * 2.该票有走货路径但本部门不在走货路径上 
	* @param waybillNo
	* @param goodsNo
	* @param correntOrgCode
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return FeedbackDto    返回类型 
	* @see getNextOrgAndTimeForDetailResultInstoreTransactionalNested
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-3 下午5:08:03   
	* @throws
	 */
	FeedbackDto getNextOrgAndTimeForDetailResultInstoreTransactionalNested(String waybillNo, String goodsNo, String correntOrgCode) throws TfrBusinessException;
	
	// modify by liangfuxiang 2013-7-10下午5:16:11 begin ISSUE-3095
	/**
	 * 
	 * @Title: queryPathDetailEntityListForPrintLabel
	 * @Description: 从综合查询走货路由信息 目的:打印条码
	 * @param origCode
	 * @param currentCode
	 * @param objCode
	 * @param transModel
	 * @return 设定文件
	 * @return List<PathDetailEntity> 返回类型
	 * @see queryPathDetailEntityListForPrintLabel
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-7-10 下午5:04:01
	 * @throws
	 */
	public List<PathDetailEntity> queryPathDetailEntityListForPrintLabel(String origCode, String currentCode, String objCode, String transModel);
	// modify by liangfuxiang 2013-7-10下午5:16:11 end ISSUE-3095
	
	/**
	 * @deprecated
	 * 用于小件 补码
	* @Title: changeDestinationPathForExpress 
	* @Description: 修改目的站!----补码需要修改走货路径
	* A.按票修改。
	* B.已知站与新的目的站之间确保有路径的逻辑由调用此接口方来保证。
	* C.已经与小件人员确认:不会出现A-B-C-D-X 当需要修改X的时候，货物在F的情况。
	* D.快递货库存统一都是快递货区..所以，不用涉及到库区的修改。
	* @param waybillNo
	* @param destOrgCode  设定文件 
	* @return void    返回类型 
	* @see changeDestinationPathForExpress
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-22 上午11:00:31   
	* @throws
	 */
	public void changeDestinationPathForExpress(String waybillNo,String destOrgCode);
	
	
	/**
	 * 用于小件 补码
	* @description
	* 与changeDestinationPathForExpress的区别是：不是直接修改目的站，而是根据当前最大路由的当前部门与目的部门(destOrgCode) 
	*  查询基础数据，调整走货路径
	* @param waybillNo
	* @param destOrgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年10月25日 下午3:18:10
	*/
	public void changeDestinationPathForAlterDetail(String waybillNo,String destOrgCode);
	
	/**
	 * 用于小件 补录 ISSUE-4480
	 *  删除老的走货路径，重新生成新的走货路径的功能
	 * @param waybillNo
	 * @param receiveOrgCode
	 * @param destOrgCode
	 * @throws TfrBusinessException
	 * @author 14022-foss-songjie
	 * @update 2013年11月19日 上午08:18:10
	 */
	public void changeDestinationPathForRecordAgain(String waybillNo,String destOrgCode) throws TfrBusinessException;
	

	// modify by liangfuxiang 2013-8-5上午10:36:47 begin BUG-49358 为满足读写分离，提供此接口----只读
	/**
	 * @Title: getNextOrgAndTimeForRWSplitting
	 * @Description: 根据运单号,货件号,查询下一部门及出发时间
	 * @param waybillNo
	 * @param goodsNo
	 * @param correntOrgCode
	 * @return
	 * @throws TfrBusinessException 设定文件
	 * @return FeedbackDto 返回类型
	 * @see getNextOrgAndTimeForRWSplitting
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-8-5 上午10:35:50
	 * @throws
	 */
	public FeedbackDto getNextOrgAndTimeForRWSplitting(String waybillNo, String goodsNo, String correntOrgCode) throws TfrBusinessException;
	// modify by liangfuxiang 2013-8-5上午10:37:17 end BUG-49358;
	/**
	* @description 更新出发时间
	* @param waybillNo
	* @param goodsNoList
	* @param currentOrgCode
	* @param destOrgCode
	* @param productType
	* @throws TfrBusinessException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年6月26日 下午5:21:22
	*/
	void changeModifyStartTime(String waybillNo, List<String> goodsNo, String currentOrgCode, String destOrgCode, String productType)throws TfrBusinessException;

	/**
	 * 不在库存时返回最近的交接单号，在库存时返回null
	 * @param waybillNo
	 * @param goodsNo
	 * @return
	 * @throws TfrBusinessException
	 */
	public String queryNewHandoverNoByWaybillNo(String waybillNo,String goodsNo) throws TfrBusinessException;
	
	/**
	 * 在途中修改对应的走货路径
	 * @param waybillNo 运单号
	 * @param serialNo 流水号
	 * @param nowOrgCode 出发部门 交接单的出发部门
	 * @param destOrgCode 到达部门 交接单的到达部门
	 * @param billingOrgCode 开单部门
	 * @param model 运输性质
	 * @throws TfrBusinessException
	 */
	public void alterPathDetailForNOStore(String waybillNo, String serialNo, String nowOrgCode, String destOrgCode, String billingOrgCode, String model) throws TfrBusinessException;
	
	/**
	* @description 根据运单号和当前部门重新调整走货路径(目的站是开单时对应的提货物网点虚拟营业部)
	* 1:综合同步crm数据后 第一时间调用该接口 修改走货路径 避免装车出现强装
	* 2:currentOrgCode是crm通过到综合的 到达部门编码()
	* @param waybillNo
	* @param nowOrgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月18日 下午4:33:05
	*/
	public void changePathForExpressToArrivalFirstOrg(String waybillNo,String nowOrgCode);
	/**
	 * @author nly
	 * @date 2015年5月9日 下午2:40:55
	 * @function
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 */
	List<String> queryPassDeptCodes(String waybillNo, List<String> serialNoList);
	/**
	 * @author nly
	 * @date 2015年5月9日 下午2:39:48
	 * @function 根据运单号或运单号流水号查询走货路径明细
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 */
	List<PathDetailEntity> queryPathDetailByNos(String waybillNo,List<String> serialNoList);

	/**
	 * @author 106162
	 * @date 2017年3月24日 下午2:39:48
	 * @function 根据运单号、运单号流水号、当前操作部门 三个参数查询走货路径明细对应的下一站部门
	 * @param waybillNo、goodsNo、origOrgCode
	 * @return nextOrgName
	 */
	String queryPathByWgoService(String waybillNo,String goodsNo,String origOrgCode);
}