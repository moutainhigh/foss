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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IHandOverBillDao.java
 *  
 *  FILE NAME          :IHandOverBillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandoverBillPrintRecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.HoldingStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SerialNoLoadExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo;

/** 
 * @className: IHandOverBillDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 交接单模板dao接口
 * @date: 2012-10-11 上午10:42:21
 * 
 */
public interface IHandOverBillDao {
	
	/**
	 * @author 045923-foss-shiwei
	 * @return List<WaybillStockEntity>
	 * @date 2012-10-11
	 * @description 查询交接运单，获取部门库存运单dao
	 */
	List<HandOverBillDetailEntity> queryWaybillStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start);
	
	/**
	 * 交接单新增、修改界面，快速添加通过运单号获取库存运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-3-25 下午6:39:48
	 */
	List<HandOverBillDetailEntity> queryWaybillStockByWaybillNo(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 根据运单号获取运单库存、去除“下一部门”过滤
	 * @author 045923-foss-shiwei
	 * @date 2013-6-10 下午4:05:15
	 */
	List<HandOverBillDetailEntity> queryWaybillStockByWaybillNoWithoutNextOrg(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 查询交接运单，获取部门在途运单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午2:56:46
	 */
	List<HandOverBillDetailEntity> queryEnRouteWaybillList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start);
	
	/**
	 * 根据运单号获取流水号库存信息
	 * @author 045923-foss-shiwei
	 * @date 2012-10-11 下午3:36:13
	 * @param waybillNo运单号，storageDept库存部门，下一到达部门
	 */
	List<SerialNoStockEntity> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * @author 045923-foss-shiwei
	 * @date 2013-03-23 上午3:36:13
	 * @param waybillNo运单号，storageDept库存部门，下一到达部门
	 */
	List<SerialNoStockEntity> queryJoinCarSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * 查询交接运单界面，分页时获取总条数
	 * @author 045923-dp-shiwei
	 * @date 2012-10-14 下午3:42:56
	 */
	Long getWaybillStockCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 查询交接运单界面，在途运单分页时获取总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午3:03:44
	 */
	Long getEnRouteWaybillCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 新增时，保存交接单数据
	 * @author 045923-dp-shiwei
	 * @date 2012-10-19 上午10:46:48
	 * @param handOverBillEntity交接单基本信息实体
	 				   unSavedWaybillStockList运单库存实体list
	 				   unsavedSerialNoStockList流水号库存实体list
	 */
	boolean saveHandOverBill(HandOverBillEntity handOverBillEntity,List<HandOverBillDetailEntity> unSavedWaybillStockList,List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList);
	
	/**
	 * 查询交接单dao方法
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:17:47
	 * @param queryHandOverBillConditionDto查询条件dto
	 */
	List<QueryHandOverBillDto> queryHandOverBillList(QueryHandOverBillConditionDto queryHandOverBillConditionDto,int limit,int start);
	
	/**
	 * 无分页查询交接单，用于导出交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:24:09
	 */
	List<QueryHandOverBillDto> queryHandOverBillListNoPaging(QueryHandOverBillConditionDto queryHandOverBillConditionDto);
	
	/**
	 * 获取交接单的总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 上午9:58:15
	 * @param queryHandOverBillConditionDto查询条件dto
	 */
	Long getHandOverBillListCount(QueryHandOverBillConditionDto queryHandOverBillConditionDto);

	/**
	 * 根据交接单号获取交接单中运单list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:18:26
	 * @param handOverBillNo交接单号
	 */
	List<HandOverBillDetailEntity> queryHandOverBillDetailByNo(String handOverBillNo,String waybillNo);
	/**
	 * 根据交接单号获取交接单中运单list
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午3:29:22
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryAirHandOverBillDetailByNo(java.lang.String)
	 */
	 List<HandOverBillDetailEntity> queryAirHandOverBillDetailByNo(String handOverBillNo,String waybillNo);
	/**
	 * 根据交接单号、运单号获取流水号list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午6:26:56
	 * @param handOverBillNo交接单号
	 */
	List<SerialNoLoadExceptionDto> queryWaybillDetailByNos(String handOverBillNo,String waybillNo);
	
	/**
	 * 根据交接单号，获取交接单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午10:15:34
	 * @param handOverBillNo交接单号
	 */
	List<HandOverBillOptLogEntity> queryHandOverBillOptLogByNo(String handOverBillNo,int limit,int start);
	
	/**
	 * 根据交接单号，获取修改日志条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午11:11:08
	 * @param handOverBillNo交接单号
	 */
	Long getHandOverBillOptLogCount(String handOverBillNo);
	
	/**
	 * 更新交接单状态，批量处理
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 下午4:44:04
	 * @param dtoList，属性：handOverBillNo交接单号，targetState目标状态(10：已预配，20：已交接，30：已出发，40：已到达，50：已入库，90：已作废)
	 */
	boolean updateHandOverBillState(List<UpdateHandOverBillStateDto> list);
	
	/**
	 * 根据交接单号获取交接单实体
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 下午1:53:01
	 * @param handOverBillNo交接单号
	 */
	HandOverBillEntity queryHandOverBillByNo(String handOverBillNo);
	/**
	 * 根据交接单号获取交接单实体
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午6:36:48
	 * @param handOverBillNo交接单号
	 */
	HandOverBillEntity queryAirHandOverBillByNo(String handOverBillNo);
	/**
	 * 修改交接单后保存数据方法
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 上午10:04:14
	 * @param handOverBillEntity 修改后的交接单基本信息实体
	 				   deletedWaybillList 被删除的交接单内运单list
	 				   addedWaybillList 新增的交接单内运单list
	 				   updatedWaybillList 更新的交接单内运单list
	 				   deletedSerialNoList 被删除的流水号list
	 				   addedSerialNoList 新增的流水号list
	 				   handOverBillOptLogList 交接单修改日志list   
	 */
	boolean updateHandOverBill(HandOverBillEntity handOverBillEntity,
			List<HandOverBillDetailEntity> deletedWaybillList,
			List<HandOverBillDetailEntity> addedWaybillList,
			List<HandOverBillDetailEntity> updatedWaybillList,
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList,
			List<HandOverBillSerialNoDetailEntity> addedSerialNoList,
			List<HandOverBillOptLogEntity> handOverBillOptLogList
			);
	
	/**
	 * 查询交接运单，查询在途运单时根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午5:08:01
	 */
	List<HandOverBillSerialNoDetailEntity> queryEnRouteSerialNoListByNos(String wayBillNo, String handOverBillNo);

	/**
	 * 为配载单模块返回待配载交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:28:47
	 */
	List<QueryHandOverBillDto> queryHandOverBillListForVehicleAssembleBill(QueryHandOverBillConditionDto queryHandOverBillConditionDto);
	
	/**
	 * 更新交接单中某流水号的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午7:45:42
	 */
	boolean updateHandOverBillSerialNoPreHandOverState(UpdateHandOverBillSerialNoPreHandOverStateDto updateHandOverBillSerialNoPreHandOverStateDto);
	
	/**
	 * 更新交接单中某流水号的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午7:45:42
	 */
	List<HandOverBillSerialNoDetailEntity> querySerialNoFromHandOverBill(UpdateHandOverBillSerialNoPreHandOverStateDto updateHandOverBillSerialNoPreHandOverStateDto);
	
	List<HandOverBillSerialNoDetailEntity> getHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);

	int addHandoverbillPrintrecord(
			HandoverBillPrintRecordEntity handoverBillPrintRecord);

	List<HandOverBillDetailDto> queryPrintHandOverBillDataByNo(
			String handOverBillNo);
	/**
	 * 
	 * <p>根据交接单号查询出打印（空运）（正）交接单中需要的数据 </p> 
	 * @author 189284 
	 * @date 2015-10-15 上午11:30:31
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	List<HandOverBillDetailDto> queryPrintAirHandOverBillDataByNo(
			String handOverBillNo);
	List<HandOverBillDetailDto> queryPrintPartialLineDataByNo(
			String handOverBillNo);

	Long checkPrintHandOverBill(HandOverBillVo handOverBillVo);

	String getVehicleassembleNoByHandoverNo(String handOverBillNo);
	
	/**
	 * 根据部门查询货件所在的交接单类型
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-12 下午4:20:40
	 */
	List<String> queryHandoverType(String waybillNo, String serialNo, String orgCode);
	
	/**
	 * 修改配载单车牌号时，批量更新配载单中交接单中的车牌及司机信息
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 下午8:26:40
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateVehicleNoAndDriverInfoFromVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto)
	 */
	int updateVehicleNoAndDriverInfoFromVehicleAssembleBill(UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto dto);

	/**
	 * 批量插入交接单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 下午8:48:00
	 */
	int addHandOverBillModifyLogList(List<HandOverBillOptLogEntity> logList);
	
	/**
	 * 上报OA少货已找到时，传入到达部门code、运单号、流水号，获取出发部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-1-14 下午4:35:42
	 */
	List<String> querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo(HandOverBillSerialNoDetailEntity queryEntity);

	/**
	 * 查询运单是否需要代打木架
	 * 	返回值大于0说明需要代打木架
	 * @author ibm-zhangyixin
	 * @date 2013-1-23 下午3:52:40
	 */
	Long queryWayBillPacked(String waybillNo);
	
	/**
	 * 根据到达部门code、出发部门code、运单号、指定的交接单号list中匹配出交接单号，用于上报OA少货时获取交接单号
	 * @author 045923-foss-shiwei
	 * @date 2013-2-28 下午4:53:35
	 */
	List<String> queryHandOverBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto);
	
	/**
	 * 根据运单号、到达部门code获取运单号被交接过的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:50:33
	 */
	List<SerialNoStockEntity> queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(
			String waybillNo, String destOrgCode);
	
	/**
	 * 根据运单号，查询该运单号在途的交接记录
	 * @author 045923-foss-shiwei
	 * @date 2013-5-24 下午5:14:56
	 */
	List<HandOverBillDetailEntity> queryGoodsOnTheWayRecordsByWaybillNo(String waybillNo);
	
	/**
	 * 通过任务车辆校验交接单状态
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 下午4:45:53
	 */
	List<String> validateHandOverBillStateFromTruckTask(String handOverBillNo);
	
	/**
	 * 对交接单待办事项进行漂移
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午10:46:19
	 */
	List<QueryHandOverBillDto> queryToDoUnDriftedHandOverBillList(Date endCreateTime);
	
	/**
	 * 对交接单的“是否已漂移待办”字段进行更新
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午10:48:23
	 */
	int updateHandOverBillDriftedToDo(String handOverBillNo);
	
	/**
	 * 根据运单号查询该运单所有的被交接记录，并按交接时间排序
	 * @author 045923-foss-shiwei
	 * @date 2013-6-14 下午7:49:11
	 */
	List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo);

	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对一的关系 
	 * @param taskNo
	 * @return    
	 * @return HandOverBillEntity    返回类型 
	 * queryHandOverBillByLoadTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-18下午3:30:19
	 */ 
	HandOverBillEntity queryHandOverBillByLoadTaskNo(String taskNo);
	
	/**
	* @Title: addHandOveredSerialNo 
	* @Description: 新增一条交接单流水号明细
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-6 上午10:31:13 
	* @param @param serialNo
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	int addHandOveredSerialNo(HandOverBillSerialNoDetailEntity serialNo);
	
	/**
	 * 根据交接单号查询运单信息（导出运单明细）
	 * @author 105869-foss-heyongdong
	 * @date 2013年10月22日 18:56:22
	 */
	List<HandOverBillDetailDto> queryHandOverBillDetailByHandbillNo(
			String handOverBillNo,String handOverType);
	/**
	 * 运单补录时更新交接单相关信息
	 * @param updatehandoverbills
	 * @param updateHandoverbillDetails
	 * @param logs
	 * @return
	 */
	boolean updateHandOverbillWeigthAndVolumn(
			List<HandOverBillEntity> updatehandoverbills,
			List<HandOverBillDetailEntity> updateHandoverbillDetails,
			List<HandOverBillOptLogEntity> logs);
	/**
	 * @author niuly
	 * @date 2014-3-6上午11:07:59
	 * @function 根据车辆明细ids查询交接单明细信息
	 * @param detailIds
	 * @return
	 */
	List<HandOverBillDetailEntity> queryHandOverBillDetailByIds(List<String> detailIdList);

	/**
	 * @author niuly
	 * @date 2014-3-6下午2:33:47
	 * @function 根据运单号和到达部门编码查询到达总件数
	 * @param waybillNo
	 * @param destOrgCode
	 * @return
	 */
	int queryWaybillCountByNoAndDept(String waybillNo, String destOrgCode);
	
	/**
	 * @function 根据运单号查询是否补码
	 * @param waybillNo
	 * @return
	 */
	int queryWaybillComplement(String waybillNo);
	
	/**
	 * 检查交接单在租车标记表的预提状态，如果为预提中或者已预提则不允许修改
	 * @author 205109-foss-zenghaibin
	 * @date 2014-08-14 上午08:27:39
	 * @param handOverBillNo
	 */
	public List<HoldingStateDto> queryHoldingState(String handOverBillNo);
	
	/**
	 * 根据运单号查询代理单号
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-27
	 * @param waybillNo
	 * @return 代理单号
	 * 一票多件 需求--根据运单号和流水号 查询代理单号
	 * @update-author 269701--lln--2015/11/03
	 * @update-param waybillNo 运单号 serialNo 流水号
	 */
	public String getAgentWaybillNoByWaybillNo(String waybillNo,String serialNo);
	/**
	 * 根据运单号查询该运单所有的被交接记录（废单），并按交接时间排序
	 * @author 271297-foss-zhangpeng
	 * @date 2015-7-27下午9:02:11
	 */

	List<HandOverBillEntity> queryHandOveredAllRecordsByWaybillNo(
			String waybillNo);
//	/**
//	 * 根据code 查询对应库存的异常库区
//	 * @author 218427-foss-hongwy
//	 * @date 2015-8-4  上午8:37:11
//	 */
//	public String queryGoodsExceptionArea(String code);
//	
//	/**
//	 * 根据code 查询对应库存的更新为异常库区code
//	 * @author 218427-foss-hongwy
//	 * @return 
//	 * @date 2015-8-4  上午8:37:11
//	 */
//    public int updateAreaCode(String waybillNo, String areacode, String code);
	
	/**
	  * 根据运单号、出发部门查询交接单明细表
	  * @author 273247
	  * @param waybillNo
	  * @param origOrgCode
	  * @return
	  */
	List<HandOverBillDetailEntity> queryHandoverBillDetailByWaybillNoAndOrgCord(String waybillNo, String origOrgCode);
	/**
	 * 
	 * <p>快递空运交接单 查询运单下的流水号</p> 
	 * @author 189284 
	 * @date 2015-9-22 上午10:12:05
	 * @param wayBillNo
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	List<HandOverBillSerialNoDetailEntity> getAirHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);
    //根据商务专递交接单号获取交接单中运单list 272681
	List<HandOverBillDetailEntity> queryBusAirHandOverBillDetailByNo(
			String handOverBillNo, String waybillNo);
   //根据商务专递交接单号、运单号获取流水号list 272681
	List<HandOverBillSerialNoDetailEntity> getBusAirHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);
  
	/** 
	 * @Title: queryHandOverBillNoByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对一的关系 
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillNoByLoadTaskNo(java.lang.String)
	 * @author: 272681
	 * @throws 
	 * Date:2015-11-24
	 */
	String queryHandOverBillNoByLoadTaskNo(String taskNo);

	
	/**
	* @description 根据交接单号单条更新交接单状态
	* @param dto
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年6月1日 下午1:55:06
	*/
	void updateWKHandOverBillStateByNo(Map<String,String> param);
	
	/**
	* @description 查询非本部门库存
	* @version 1.0
	* @author 332219
	* @update 2016年9月19日
	*/
	List<HandOverBillDetailEntity> querySaleWaybillStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit, int start);
	
	/**
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 上午3:36:13
	 * @param waybillNo运单号，storageDept库存部门，下一到达部门
	 */
	List<SerialNoStockEntity> querySaleJoinCarSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * 根据运单号获取流水号库存信息
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 下午3:36:13
	 * @param waybillNo运单号，storageDept库存部门，下一到达部门
	 */
	List<SerialNoStockEntity> querysaleSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * 查询非本部门交接运单界面，分页时获取总条数
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 上午3:36:13
	 */
	Long getSaleWaybillStockCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 交接单新增、修改界面，快速添加通过运单号获取非本部门库存运单信息
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-10-2 下午3:39:48
	 */
	List<HandOverBillDetailEntity> queryWaybillStockSaleByWaybillNo(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);

	/**
	 * 根据运单号获取运单在非本部库存、去除“下一部门”过滤
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-10-2 下午3:39:48
	 */
	List<HandOverBillDetailEntity> queryWaybillStockByWaybillSaleNoWithoutNextOrg(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
				
	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据运单获取最后一次交接单信息
	 * @param waybillNo
	 * @return    
	 * @author: tfr-360903
	 * Date:2016年11月7日 11:04:30
	 */ 
	HandOverBillEntity queryHandOverBillByLast(String waybillNo);
	
}