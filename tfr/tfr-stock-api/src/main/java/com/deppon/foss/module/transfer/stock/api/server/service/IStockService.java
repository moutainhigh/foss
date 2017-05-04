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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/server/service/IStockService.java
 *  
 *  FILE NAME          :IStockService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.StockOrgDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.UpdateStockPreHandOverStateDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDetailDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;

/**
* @description 本接口定义了对货物的
* 入库操作
* 出库曹操
* 库存查询
* 库存出入库历史记录的查询
* 必走货的查询
* @version 1.0
* @author 140022-foss-songjie
* @update 2013-8-5 上午9:27:57
*/

public interface IStockService extends IService{
	
	/**
	 * PC端操作出库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @return 操作成功返回 FossConstants.SUCCESS，库存中不存的货件记录则返回 FossConstants.FAILURE
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-6 上午8:34:45
	 */
	int outStockPC(InOutStockEntity inOutStockEntity);
	
	/**
	 * PC端操作入库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-6 上午9:17:13
	 */
	int inStockPC(InOutStockEntity inOutStockEntity) throws StockException;
	/**
	 * <pre>
	 * 此方法逻辑同inStockPC方法，区别为使用REQUIRES_NEW事务，对应于外围调用时，此方法出错抛出异常后，需继续外围其他业务的情况
	 * 可解决外围调用可能出现的事务问题(Transaction rolled back because it has been marked as rollback-only)
	 * </pre>
	 * 
	 * @param stockEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-18 下午3:25:41
	 */
	void inStockRequiresNewTransactional(InOutStockEntity stockEntity);
	
	/**
	 * 根据条件获取流水号库存实体
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午6:58:29
	 */
	StockEntity queryStockEntityByNos(String orgCode, String waybillNO, String serialNO, String goodsAreaCode);
	
	/**
	 * 根据条件获取流水号虚拟库存实体
	 * @author 332219-foss
	 * @date 2016-11-23
	 */
	StockSaleEntity querySaleStockEntityByNos(String orgCode, String waybillNO, String serialNO, String goodsAreaCode);
	
	
	/**
	 * PC端查询库存界面 单件入库 
	 * @param inOutStockEntity 入库动作信息
	 * @param serialNOs 入库的货件的流水号，多个流水号以逗号隔开
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午10:42:24
	 */
	void inStockSerialNOs(InOutStockEntity inOutStockEntity, String serialNOs, String confirmFlag, String inStockConfirmFlag) throws StockException;
	
	/**
	 * 页面操作批量出库
	 * @param outStockList
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-22 上午9:52:48
	 */
	void outStockList(List<InOutStockEntity> outStockList, String userCode, String userName);
	/**
	 * 
	 * 根据运单号、部门、货区查询货件库存
	 * @param waybillStockEntity.waybillNO  运单号
	 * @param waybillStockEntity.orgCode  部门编号
	 * @param waybillStockEntity.goodsAreaCode  货区编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午10:45:17
	 */
	List<StockEntity> queryStock(WaybillStockEntity waybillStockEntity);
	
	/**
	 * 根据运单号查询货件库存
	 * @param waybillStockEntity
	 * @return
	 */
	List<StockEntity> queryStockByWaybillNo(WaybillStockEntity waybillStockEntity);
	
	/**
	 * 根据运单号查询货件库存
	 * 按照入库时间排序
	 * 200968 2016-3-16
	 * @param waybillStockEntity
	 * @return
	 */
	List<StockEntity> queryStockByWaybillNoInStockTime(WaybillStockEntity waybillStockEntity);
	
	/**
	 * 查询运单库存
	 * @param waybillStockEntity 封装关于运单库存的查询条件
	 * @param beginInStockTime 运单入库的起始时间
	 * @param endInStockTime 运单入库的截止时间	
	 * @param limit start 分页参数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午10:45:23
	 */
	List<WaybillStockQueryDto> queryWaybillStock(WaybillStockEntity waybillStockEntity,
			Date beginInStockTime,Date endInStockTime, int limit, int start);
	/**
	 * 查询运单库存的总数用于分页
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午10:45:28
	 */
	Long queryCount(WaybillStockEntity waybillStockEntity,Date beginInStockTime,Date endInStockTime);
	
	/**
	 * 根据运单号分页查询货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-17 下午4:43:30
	 */
	List<StockEntity> queryGoods(InOutStockEntity inOutStockEntity, int limit, int start);
	
	/**
	 * 根据运单号查询货件总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-17 下午4:43:35
	 */
	Long queryGoodsCount(InOutStockEntity inOutStockEntity);
	
	/**
	 * 根据运单号查询货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-17 下午4:43:40
	 */
	List<StockEntity> queryGoods(InOutStockEntity inOutStockEntity,String orgCode);
	/**
	 * 登出特殊货区（贵重物品货区、异常货区、代包装货区）的货件到正常货区
	 * @param outStockList 出库货件list
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 下午1:45:41
	 */
	void logoutSpecialGoodsArea(List<InOutStockEntity> outStockList, String userCode, String userName);
	
	
	/**
	 * 导出货件库存到Excel
	 * @param ids 货件库存ID组成的以逗号分隔的字符串
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-26 上午10:47:27
	 */
	InputStream exportExcelStream(String ids,WaybillStockEntity waybillStockEntity,Date beginInStockTime,Date endInStockTime);
	/**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-26 下午2:01:05
	 */
	String encodeFileName(String fileName) throws StockException;
	/**
	 * 分页查询必走货
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午2:31:30
	 */
	List<WaybillStockQueryDto> queryPriorityGoods(WaybillStockQueryDto waybillStockQueryDto,int limit, int start);
	/**
	 * 查询必走货总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午4:01:32
	 */
	Long queryPriorityGoodsCount(WaybillStockQueryDto waybillStockQueryDto);
	/**
	 * 查询弃货入库时间
	 * @param waybillNO 运单号
	 * @param orgCode 部门编号
	 * @return 该票的最后一件货物的入库时间
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-31 下午2:31:34
	 */
	Date queryAbandonedGoodsInstockTime(String waybillNO, String orgCode);
	
	/**
 	 * 根据运单号s、部门CODE查询运单库存总件数
 	 * @author zyr
 	 * @date 2015-08-28 上午10:31:04
 	 */
	Map<String, Integer> querySumStockGoodsQtyByWaybillsOrgCode(String[] waybillNos, String orgCode);
	/**
	 * 单件入库
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-14 下午2:35:23
	 */
	int inStock(InOutStockEntity inOutStockEntity, String confirmFlag, String inStockConfirmFlag, boolean isPage);
	/**
	 * 单件出库
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-14 下午2:37:05
	 */
	int outStock(InOutStockEntity inOutStockEntity);
	/**
	 * 查询三级产品列表
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-16 下午2:35:34
	 */
	List<BaseDataDictDto> queryProductList();
	
	/**
	 * 根据运单号、流水号查询唯一的库存记录
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @return StockEntity or null
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-23 下午3:35:12
	 */
	StockEntity queryUniqueStock(String waybillNO, String serialNO);
	
	/**
	 * 批量传入运单号、流水号、库存部门code，更新流水号库存的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午6:46:31
	 */
	boolean updatePreHandOverState(List<UpdateStockPreHandOverStateDto> dtoList,String targetStatus)  throws StockException;
	
	/**
	 * 判断货件是否存在该部门库存
	 * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param goodsAreaCode 货区   （可以为空）
	 * @return true： 不存在    false： 存在
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-14 下午4:16:07
	 */
	boolean isNotExistStock(String orgCode, String waybillNO, String serialNO, String goodsAreaCode);
	
	/**
	 * 根据走货路径的调整，修改相应库存（从原货区出库，入库到调整后的新货区）
	 * @param waybillNo 运单号
	 * @param serialNO  流水号
	 * @param orgCode   当前部门编号
	 * @param userCode  操作人工号
	 * @param userName  操作人姓名
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 上午9:38:06
	 */
	int adjustGoodsAreaStock(String waybillNO, String serialNO, String orgCode, String userCode, String userName);
	
	/**
	 * 根据合车调整，增加合车库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 上午11:31:13
	 */
	int adjustTogetherTruckStock(List<String> waybillNOList, List<String> originalGoodsAreaCodeList, 
			String newGoodsAreaCode, String orgCode);
	
	/**
	 * 判断运单是否有出库记录
	 * @param waybillNO 运单号
	 * @param createBillTime 开单时间
	 * @return 有出库记录返回 true, 否则 false
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 上午9:05:06
	 */
	boolean isOutStock(String waybillNO, Date createBillTime);
	
	/**
	 * 根据用户当前部门获取相应大部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午2:47:54
	 */
	String getBigOrgCode(String currentOrgCode);
	
	/**
	 * 获取当前大部门的特殊货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午5:19:17
	 */
	List<BaseDataDictDto> querySpecialAreaList(String orgCode);
	
	/**
	 * 判断货件是否存在于该部门非某一货区
     * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param goodsAreaCode 货区
	 * @return true： 存在    false：不 存在
	 * @author foss-wuyingjie
	 * @date 2012-11-30 下午5:26:44
	 */
	boolean isExistOtherGoodsAreaStock(String orgCode, String waybillNO, String serialNO, String goodsAreaCode);
	/**
	 * 根据当前部门获取有库存的大部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-6 下午5:28:43
	 */
	OrgAdministrativeInfoEntity queryStockOrg(OrgAdministrativeInfoEntity currentOrg);
	
	/**
	 * 根据当前部门查询库存部门信息（是否是外场、驻地派送部、空运总调）
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-22 下午4:05:03
	 */
	StockOrgDto queryStockOrgInfo(OrgAdministrativeInfoEntity currentOrg);
	
	/**
	 * 判断部门类型是否是外场
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-7 上午11:00:41
	 */
	String isTransferCenter(String orgCode);
	
	/**
	 * 违禁品出库异常货区，入库到驻地派送部货区
	 * 修改走货路径，将驻地派送部作为终点站
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-17 上午10:56:47
	 */
	int handoverContrabandToStationStock(InOutStockEntity inOutStockEntity);
	
	/**
	 * 获取某一部门、货区中所有货件的快照
	 *
	 * @param deptCode
	 * @param goodsAreaCode
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-19 上午10:17:54
	 */
	List<StockEntity> queryStockByGoodsAreaCode(String deptCode, String goodsAreaCode);
	/**
	 * 根据部门查询库存件数
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午8:47:55
	 */
	Long queryStockGoodsQty(String orgCode);
	
	/**
	 * 查询库存统计栏
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-6 上午9:49:54
	 */
	WaybillStockStatisticsDto queryWaybillStockStatistics(WaybillStockEntity waybillStockEntity,
			Date beginInStockTime,Date endInStockTime);
	
	/**
	 * 查询必走货 统计栏
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-6 下午2:26:26
	 */
	WaybillStockStatisticsDto queryPriorityGoodsStatistics(WaybillStockQueryDto waybillStockQueryDto);
	
	/**
	 * 查询入库动作记录
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-17 下午2:31:46
	 */
	List<InOutStockEntity> queryInStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime);
	
	
	/**
	* @param waybillNo
	* @param serialNo
	* @param orgCode
	* @param createBillTime 
	* @return
	* @description 查询小于入库时间的入库动作记录
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-27 下午1:52:19
	*/
	List<InOutStockEntity> queryInStockInfoSmall(String waybillNo, String serialNo, String orgCode, Date createBillTime);
	/**
	 * 查询出库动作记录
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @param createBillTime
	 * @return
	 * 
	 * @author foss-wuyingjie
	 */
	List<InOutStockEntity> queryOutStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime);
	
	void addStock(InOutStockEntity inOutStockEntity);
	
	/**
	 * 开单批量入库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-6 上午9:17:13
	 */
	int inStockCreateBill(List<InOutStockEntity> inStockList) throws StockException;
	
	/**
	 * PC端操作批量出库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-6 上午9:17:13
	 */
	int outStockBatchPC(List<InOutStockEntity> outStockList) throws TfrBusinessException;
	
	/**
	 * PC端操作批量出库（按照类型）
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	* @return: int
	* @author: Xingmin , 2016-9-23 上午11:04:52
	 */
	int  outStockBatchPCByType(List<InOutStockEntity> outStockList, String type, String id) throws TfrBusinessException;
	
	/**
	 * 卸车入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-1 下午4:24:51
	 */
	int inStockUnload(InOutStockEntity inOutStockEntity) throws StockException;
	
	
	/**
	* @description 卸车入库优化
	* @param inOutStockEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午11:28:48
	*/
	void batchInStockUnload(List<InOutStockEntity> inOutStockEntityList);
	
	/**
	 * 散货货区货物入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-26 上午10:17:53
	 */
	void bulkGoodsInStock(List<InOutStockEntity> inStockList, String userCode, String userName) throws StockException;
	
	/**
	 * 调整外场库存到派送货区库存
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-9 下午2:00:04
	 */
	void adjustStockToStation(String waybillNo, String orgCode, List<String> serialNoList, String userCode, String userName);
	
	/**
	 * 反签收批量入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-13 下午2:00:14
	 */
	int inStockReverseSign(List<InOutStockEntity> inStockList);
	/**
	 * 移除货件
	 * 1.货件在库存中：出库、删除走货路径
	 * 2.货件不在库存中：不做任何操作
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-14 下午5:54:19
	 */
	int outStockInvalidGoods(String waybillNo, List<String> serialNosList,String orgCode,String operatorCode,String operatorName);
	/**
	 * 提供给结算模块：到付清查报表-查询库存状态
	 * 1：通过运单号，查询少货表（卸车／清仓少货）
	 *	     存在少货未找到，则：【库存少货】
	 * 2：通过运单号，查签收结果表 签收状态：
	 * 		正常签收：【正常签收】
	 *      其它状态：【异常签收】
	 * 3：通过运单号/到达部门，查库存表
	 *	          不存在 ：【未入库】
	 *	          存在：
	 *	              如果库存件数=开单件数：【库存中】
	 *	              如果库存件数！=开单件数：【库存异常】
	 * @param waybillNo 运单号
	 * @param createBillGoodsQty 开单件数
	 * @param arriveOrgCode 到达部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 上午11:56:52
	 */
	String queryWaybillStockStatus(String waybillNo, int createBillGoodsQty, String arriveOrgCode);
	
	/**
	 * 运单在出发部门库存，且未做交接单时，更改运单号
	 * 更改中转相关数据：
	 * 1.库存数据
	 * 2.走货路径数据
	 * 3.包装信息
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @param createBillTime 开单时间
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:25:42
	 */
	void modifyWaybillNo(String oldWaybillNo, String newWaybillNo, Date createBillTime); 
	
	
	/**
	 * DEFECT-2021
			整车运单更改运单号，在审核该更改单时提示“查询不到走货路径”  
	 * 运单在出发部门库存，且未做交接单时，更改运单号
	 * 更改中转相关数据：
	 * 1.库存数据
	 * 2.走货路径数据
	 * 3.包装信息
	* @description 用一句话说明这个方法做什么
	* @param oldWaybillNo 原运单号
	* @param newWaybillNo 新运单号
	* @param createBillTime 开单时间
	* @param ProductCode 运输性质
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月11日 下午4:43:01
	*/
	void modifyWaybillNo(String oldWaybillNo, String newWaybillNo, Date createBillTime,String productCode); 
	
	/**
	 * 查询运单库存明细
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-7 下午5:19:56
	 */
	List<WaybillStockDetailDto> queryWaybillStockDetail(String waybillNo);

	/**
	 * 导出必走货信息(一级)
	 * @author 097457-foss-wangqiang
	 * @param waybillStockQueryDto
	 * @return
	 */
	InputStream exportPriorityGoods(WaybillStockQueryDto waybillStockQueryDto);
	/**
	 * 根据库存信息查询运单是否分批
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-24 下午4:58:20
	 */
	String querySeparateStatusByStock(String waybillNo);
	
	/**
	* @param waybillNo 运单号
	* @param orgCode 当前部门编号
	* @return  有记录：Y  无记录：N
	* @description 根据运单号和当前部门编号查询库存
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-22 上午11:32:02
	*/
	String queryStockByWaybillNoOrgCode(String waybillNo,String orgCode);
	
	
	/**
	* @param inStockList 运单号、流水号、货区编码、部门编号、库位 不能为空
	* @param orgCode 库位编号
	* @param orgCode 修改库位的部门编号
	* @param userCode 修改人code
	* @param userName 修改人名称
	* @return
	* @throws StockException 修改库存表的库位
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-10 上午8:22:33
	*/
	void updateStockStockPosition(List<InOutStockEntity> inStockList,String position,String orgCode,String userCode,String userName)  throws StockException;
	
	/**
	* @param orgCode
	* @return
	* @description 根据外场编码查询对应的货区(驻地派送部)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-9 上午10:50:45
	*/
	List<BaseDataDictDto> queryGoodsAreaListByOrganizationCode(String orgCode);
	
	/**
	* @param orgCode 外场编号
	* @param goodsAreaCode 货区code
	* @return
	* @description 获取对应的库位集合
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午11:00:19
	*/
	List<BaseDataDictDto> queryStorageListByGoodsAreaFrom(String orgCode,String goodsAreaCode);
	
	/**
	* @param goodsAreaCode
	* @param orgCode
	* @return  true 货区是 驻地派送部的货区
	* @description 检验货区是否是驻地派送部的货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-10 下午3:10:04
	*/
	boolean checkGoodsArea(String goodsAreaCode,String orgCode);
	
	
	/**
	* @param inOutStockEntity
	* @param currentOrgCode
	* @param confirmFlag
	* @return
	* @description 走货路径调用PROPAGATION_NESTED事物
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-11 下午8:15:05
	*/
	public InOutStockEntity goodsWalkPathTransactional(InOutStockEntity inOutStockEntity,String currentOrgCode,String confirmFlag,String checkGoodsArea);
	
	
	/**
	 * 派送签收时调用出库动作
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @return 无返回 
	 * @description 
	 * @version 1.0
	 * @author 140022-foss-songjie
	 * @update 2013-7-12 上午11:10:39
	*/
	void outStockDelivery(InOutStockEntity inOutStockEntity);

	/**
	* @param currentOrg
	* @return
	* @description 根据当前部门获取对应的外场编码 (没有外场的返回当前部门的编码)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-13 下午4:26:50
	*/
	String transferCenterCodeByBigOrg(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity);
	
	/**
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @description 更新是否已经建包
	 */
	public void updateIsPackage(
			String waybillNo, String serialNo, String orgCode);
	
	
	
	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午3:40:23
	*/
	List<BaseDataDictDto> queryNextOrgByStock(String orgCode,String goodArea,String orgName,int start,int limit);
	
	
	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用 总记录数 
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午10:13:19
	*/
	Integer  queryNextOrgByStockCount(String orgCode,String goodArea,String orgName);
	
	/**
	 * @author niuly
	 * @date 2014-5-24上午8:48:34
	 * @function 根据运单号查询所有的入库部门编码
	 * @param waybillNo
	 * @return
	 */
	List<String> queryInDeptCodeByWaybillNo(String waybillNo);
	/**
	* @description 分页查询库存迁移记录
	* @param moveGoodsStockDto
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午5:24:51
	*/
	List<MoveGoodsStockQueryDto> queryMoveGoods(MoveGoodsStockDto moveGoodsStockDto,int limit,int start);
	

	/**
	* @description 撤销申请
	* @param moveGoodsEntityList
	* @param userCode
	* @param userName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-22 上午9:37:19
	*/
	void revocationStock(MoveGoodsEntity moveGoodsEntity, String userCode, String userName);

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
    * @param orgCode   部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、流水号、部门编号来获取运单号、流水号、定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-18 下午5:21:46
	*/
	public List<StockPositionNumberEntity> queryStockPositionNumber(String waybillNo,
			String orgCode);
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
    * @param orgCode   部门编号
	* @return 根据运单号、流水号、部门编号来获取库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-25 下午14:21:46
	*/
	public StockEntity queryStockByWSO(String waybillNo,String serialNo,String orgCode);

	

	/**
	* @description 审核申请
	* @param moveGoodsEntity
	* @param userCode
	* @param userName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午5:42:35
	*/
	void auditorStock(MoveGoodsEntity moveGoodsEntity, String userCode, String userName);

	
	/**
	* @description 作废申请
	* @param moveGoodsEntity
	* @param userCode
	* @param userName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:26:48
	*/
	void invalidateStock(MoveGoodsEntity moveGoodsEntity, String userCode, String userName);
	/**
	* @description 退回申请
	* @param moveGoodsEntity
	* @param userCode
	* @param userName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:38:40
	*/
	void returnStock(MoveGoodsEntity moveGoodsEntity, String userCode, String userName);
	
	/**
	* @description 根据id查询库存迁移明细
	* @param id
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-24 上午10:51:02
	*/
	MoveGoodsStockQueryDto viewMoveGoodsById(String id);
	
	
	/**
	* @description 根据部门code 获得货区code,name(如果是外场,返回库区name,code    如果是营业部返回,营业部name   其余返回空)
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-27 下午4:17:07
	*/
	List<BaseDataDictDto> queryAreaCodeAndAreaNameByOrgCode(String orgCode,String orgName);
	
	
	/**
	* @description 把要库存迁移的信息入库
	* @param moveGoodsEntity
	* @param moveGoodsDepartmentEntity
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-29 下午2:32:26
	*/
	void moveGoodsInStock(MoveGoodsEntity moveGoodsEntity,MoveGoodsDepartmentEntity moveGoodsDepartmentEntity);
	
	
	/**
	* @description 把修改的库存迁移信息入库
	* @param moveGoodsEntity
	* @param moveGoodsDepartmentEntity
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午10:34:34
	*/
	void moveGoodsModifyInStock(MoveGoodsEntity moveGoodsEntity,MoveGoodsDepartmentEntity moveGoodsDepartmentEntity);
	
	
	/**
	* @description 查询库存迁移总记录数
	* @param moveGoodsStockDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-4 上午9:20:51
	*/
	Long queryMoveGoodsCount(MoveGoodsStockDto moveGoodsStockDto);
	
	
	/**
	* @description 判断部门是否是外场,是外场才会显示页面信息
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-4 下午2:45:43
	*/
	String queryIsTransferCenter(String orgCode);
	
	
	/**
	* @description 根据员工工号查询员工角色信息
	* @param userCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-7 下午5:03:00
	*/
	List<UserOrgRoleEntity> queryOrgRoleByCode(String userCode);
	
	
	/**
	* @description 从库存表中根据部门code和库区code查询出库区的货物
	* @param OrgCode
	* @param GoodsAreaCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月27日 上午10:52:59
	*/
	List<StockEntity> queryGoodsByOrgAndGoodsArea(String orgCode,String goodsAreaCode);
	
	
	/**
	* @description 从运单库存表中根据部门code和库区code查询出库区的货物
	* @param OrgCode
	* @param GoodsAreaCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:12:12
	*/
	List<WaybillStockEntity> queryGoodsByOrgAndGoodsAreaFromWaybillStock(String orgCode,String goodsAreaCode);
	
	
	/**
	* @description 把库存表 中移出部门信息更新为移入部门
	* @param stockEntity
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:46:34
	*/
	void updateMoveInArea(StockEntity stockEntity,String moveout_code,String moveout_areacode);
	
	
	/**
	* @description 把运单库存表 中移出部门信息更新为移入部门   
	* @param waybillStockEntity
	* @param movein_code
	* @param movein_areacode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月28日 下午3:17:32
	*/
	void updateMoveInAreaFromWaybillStock(WaybillStockEntity waybillStockEntity,String moveout_code,String moveout_areacode);
	
	
	/**
	* @description 判断移出部门的库区的目的部门编码和移出部门的库区的目的部门编码是否一致
	* @param moveout_code
	* @param movein_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年3月6日 下午4:15:10
	*/
	void queryMoveOutCodeAndMoveInCodeIsEqual(String moveout_code,String moveout_areacode,String movein_code,String movein_areacode);
	
	/**
	* @description 从部门移出部门移入到部门移入部门
	* @param moveout_code
	* @param moveout_areacode
	* @param movein_code
	* @param movein_areacode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月29日 上午9:18:01
	*/
	void moveGoodsFromMoveOutToMoveInCode(String moveout_code,String moveout_areacode,String movein_code,String movein_areacode,String id,int goods_type);

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param orgCode 部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、流水号、部门编号来存储定位编号到库存表中
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-19 上午8:19:46
	*/
	void saveStockPositionNumber(List<StockPositionNumberEntity> stockPositionNumberEntityList);

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param stockPositionNumber  定位编号
	* @return 查找所有的运单号、流水号、定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午14:10:46
	*/
	public List<StockPositionNumberEntity> queryAllStockPositionNumber();
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param orgCode   部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、流水号、部门编号来更新定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午14:10:46
	*/
	void updateStockPositionNumber(String waybillNo,String serialNo,String orgCode,String stockPositionNumber);
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param stockPositionNumber 定位编号
	* @return 根据运单号、流水号来删除定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-21 上午9:10:46
	*/
	void deleteStockPositionNumber(String waybillNo,String serialNo,String orgCode);
    
	/**
	 * @author 200968
	 * 
	 */
	void stockPositionNumberJobRun();
	
	
	/**
	* @description 查询库区号变更记录
	* @param changeGoodsAreaQueryDto
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:39:34
	*/
	public List<ChangeGoodsAreaEntity> queryChangeGoodsArea(
			   ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto, int limit, int start);
	
	
	
	/**
	* @description 查询库区编号修改总记录数
	* @param changeGoodsAreaQueryDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 上午10:34:56
	*/
	Long queryChangeGoodsAreaCount(ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto);
	
	
	
	/**
	* @description 作废库区编号修改的申请
	* @param changeGoodsAreaEntity
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:23:44
	*/
	void invalidateChangeGoodsArea(ChangeGoodsAreaEntity changeGoodsAreaEntity);
	
	
	/**
	* @description 根据部门code查询库区编码
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:26:14
	*/
	List<NewAndOldGoodsAreaEntity> lookGoodsAreaByOrgcode(String orgCode);
	
	
	/**
	* @description 将库区编号变更的信息写入数据库表中
	* @param changeGoodsAreaEntity
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午3:26:52
	*/
	void changeGoodsAreaInStock(ChangeGoodsAreaEntity changeGoodsAreaEntity,List<NewAndOldGoodsAreaEntity> list) throws Exception;
	
	
	
	/**
	* @description 根据部门code和id查询库区编码对应关系
	* @param orgCode
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月11日 下午3:51:24
	*/
	List<NewAndOldGoodsAreaEntity> lookModifyGoodsAreaByOrgcode(String orgCode,String id);
	
	
	/**
	* @description 根据部门code和id查询库区编码对应关系
	* @param orgCode
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月16日 下午3:43:09
	*/
	List<NewAndOldGoodsAreaEntity> lookLookGoodsAreaByOrgcode(String orgCode,String id);
	/**
	* @description 将修改后的库区编号变更的信息写入数据库表中
	* @param list
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午10:33:37
	*/
	void updateGoodsAreaInStockById(List<NewAndOldGoodsAreaEntity> list,ChangeGoodsAreaEntity changeGoodsAreaEntity);
	
	
	/**
	* @description 根据id插入新旧库区编号数据且将新库区code改为新库区code_new
	* @param list
	* @param orgCode
	* @param id
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 上午9:53:46
	*/
	void insertDataById(List<NewAndOldGoodsAreaEntity> list,String orgCode, String id) throws StockException;



	 /**
	    * @param String  waybillNo   原单单号
	 	* @return 0:success   1:error
	 	* @description  返货开单 入库    改变原单的库存状态
	 	* @version 1.0
	 	* @author 216208
	 	* @update 2015-02-02 下午14:21:46    String waybillNo
	 	*/
	  public int returngoodsBills(String  waybillNo);
	  /**
	    * @param String  waybillNo   原单单号
	 	* @return 0:success   1:error
	 	* @description 返货开单 入库  恢复原单的库存状态
	 	* @version 1.0
	 	* @author 216208
	 	* @update 2015-02-05 下午14:21:46    String waybillNo
	 	*/
	  public int backreturngoodsBills(String  waybillNo);

	  
	  
	/**
	* @description 根据驻地部门code查询对应的驻地库区
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月8日 下午6:45:02
	*/
	public List<BaseDataDictDto> stationAreaByOrgcodeList (String orgCode);
	  
	  
	/**
	* @description 在丢货改善小组超过28天的运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月8日 下午4:16:10
	*/
	public void dayLoseGoodsForGuiji28();
	
	/**
	* @description 通过运单号判断是否在外场库存（3天外发类型时 有一票在外场库存，即入异常库存）
	* @return
	* @version 1.0
	* @author 218427-foss-hongwy
	* @update 2015年8月3日 下午4:16:10
	*/
	public void isInTransferCenter(String waybillNo);
    
     /**
	   * @author nly
	   * @date 2015年4月22日 上午8:51:14
	   * @function 根据入库类型查询入库记录
	   * @param waybillNo
	   * @param serialNo
	   * @param inStockTypeList
	   * @return
	   */
	 List<InOutStockEntity> queryInStockInfoByType(String waybillNo,String serialNo, List<String> inStockTypeList);



	 /**
	  * 判断是不是快递运单号
	  * author 268084
	  * @param waybillNo
	  * @return
	  */
	 public boolean ifIsExpressWaybill(String waybillNo);
	 
	 /**
	   * @description 根据原单号查询库存
	   * @author 273247
	   * @param waybillNO
	   * @return
	   */
	public boolean  queryStockByWaybillNo (String waybillNO);
	
	/**
	 * 根据CRM传过来的运单号查询货件库存
	 * @author 273247
	 * @return
	 */
	public List<StockEntity> queryStockByCrmWaybillNo(String waybillNo);
		
	/**
	 * 根据运单编号查询库存中的当前部门
	 * @author 336540
	 * @date 2016-10-16 11:20:50
	 */
	public String stockQueryOrgCodeByWaybillNo(String waybillNo);
	
	
	/**
	   * @author hwy
	   * @date 2015年9月8日 上午8:51:14
	   * @function 根据运单号 流水号查询是否少货找到
	   * @param waybillNo
	   * @param serialNo
	   * @return
	   */
	public String queryLostFindGoods(String waybillNo,String serialNo);

	/**
	 * 根据部门查询库存总件数和总票数
	 * @author 272681-foss-chenlei
	 * @date 2016-02-19 下午14:49:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockGoodsQtyAndWaybillQty(java.lang.String)
	 */
	WaybillStockStatisticsDto queryStockGoodsQtyAndWaybillQty(String orgCode);	
	
	/**
	 * @function 根据运单号,部门code查询运单件数
	 * @author 218381-foss-lijie
	 * @date 2016年03月15日 上午11:15:14
	 * @param waybillNo
	 * @param OrgCode
	 * @return
	 */
	public int queryCountByWaybillNoAndOrgCode(String waybillNo,String orgCode);
	
	/**营业部交接
	 * 增加库存
	 * 1.增加货件库存
	 * 2.更新运单库存
	 * 3.保存入库动作
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 22:20:39
	 */
	void addStockSale(InOutStockEntity inOutStockEntity);
	
	/**营业部交接
	 * PC端操作批量出库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @author 360903
	 * @date 2016年9月20日 15:40:29
	 */
	int outStockSaleBatchPC(List<InOutStockEntity> outStockList) throws TfrBusinessException;
	
	/**营业部交接
	 * 根据条件获取流水号库存实体
	 * @author 360903
	 * @date 2016年9月20日 17:09:51
	 */
	StockSaleEntity queryStockSaleEntityByNos(String orgCode, String waybillNO, String serialNO, String goodsAreaCode);
	
	/**营业部交接
	 * 批量传入运单号、流水号、库存部门code，更新流水号库存的预配状态
	 * @author 360903
	 * @date 2016年9月20日 17:24:43
	 */
	boolean updatePreHandOverStateSale(List<UpdateStockPreHandOverStateDto> dtoList,String targetStatus)  throws StockException;
	
	/**营业部交接
	 * 根据库存部门、运单号、流水号获取件库存
	 * @author 360903
	 * @date 2016年9月20日 16:57:49
	 */
	StockSaleEntity queryUniqueStockSale(InOutStockEntity outStock);
	
	/**营业部交接
	 * 根据出库集合查询是否存在虚拟库存，并出库
	 * @author 360903
	 * @date 2016年9月26日 22:30:38
	 */
	 List<InOutStockEntity> queryStockInfoSale(List<InOutStockEntity> outStockList) ;
	 
		/**合伙人需求
		 * 根据运单，判定是否经过第一外场
		 * @author 360903
		 * @date 2016年11月1日 14:43:47
		 */
	 int whetherPass(String waybillNo);
	 
		/** 
		 * @Title: queryHandOverBillByLoadTaskNo 
		 * @Description: 根据运单获取最后一次交接单信息
		 * @param waybillNo
		 * @return    
		 * @author: tfr-360903
		 * Date:2016年11月7日 11:04:30
		 */ 
	 boolean querystockOrgListByWaybillNo(String waybillNo,String orgCode); 
	
	 /**
	 * @param outStockList
	 * @Description: 根据扫描信息合伙人虚拟库存出库
	 * @return
	 */
	public void outStockSaleStockInfoSale(List<InOutStockEntity> outStockList);
}