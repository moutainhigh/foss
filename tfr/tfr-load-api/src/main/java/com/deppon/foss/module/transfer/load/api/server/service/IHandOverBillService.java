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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IHandOverBillService.java
 *  
 *  FILE NAME          :IHandOverBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandoverBillPrintRecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SerialNoLoadExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo;
/** 
 * @className: IHandOverBillService
 * @author: ShiWei shiwei@outlook.com
 * @description: 交接单模块service接口
 * @date: 2012-10-11 上午11:00:31
 * 
 */
public interface IHandOverBillService extends IService {
		
	/**
	 * @author shiwei
	 * @return List<WaybillStockEntity>
	 * @date 2012-10-11
	 * @description 查询交接运单，获取部门库存运单service
	 */
	List<HandOverBillDetailEntity> queryWaybillStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start);
	
	/**
	 * 交接单新增、修改界面，快速添加通过运单号获取库存运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-3-25 下午6:39:48
	 */
	Map<String,Object> queryWaybillStockByWaybillNo(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 交接单新增、修改界面，一步查询运单、流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-20 上午11:06:14
	 */
	List<HandOverBillDetailEntity> queryWaybillStockListAndSerialNoStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start);
	
	/**
	 * 查询交接运单，获取部门在途运单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午2:55:13
	 */
	List<HandOverBillDetailEntity> queryEnRouteWaybillList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start);
	/**
	 * 传入运单号、库存部门，下一到达部门，获取流水号list，用于查询交接运单界面
	 * @author 045923-foss-shiwei
	 * @date 2012-11-3 上午9:30:04
	 */
	List<SerialNoStockEntity> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * 获取运单库存的总记录数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-3 上午9:31:09
	 */
	Long getWaybillStockCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 查询交接运单，获取在途运单总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午3:06:57
	 */
	Long getEnRouteWaybillCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto);
	
	/**
	 * 查询交接运单，查询在途运单时根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午5:08:01
	 */
	List<HandOverBillSerialNoDetailEntity> queryEnRouteSerialNoListByNos(String wayBillNo, String handOverBillNo);
	
	/**
	 * 新增时，保存交接单数据
	 * @author 045923-foss-shiwei
	 * @date 2012-10-20 下午3:31:43
	 * @param newHandOverBillDto交接单信息dto，返回保存后的交接单号
	 */
	String saveHandOverBill(NewHandOverBillDto newHandOverBillVo);
	
	/**
	 * 查询交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:19:05
	 * @param queryHandOverBillConditionDto 查询条件dto
	 */
	List<QueryHandOverBillDto> queryHandOverBillList(QueryHandOverBillConditionDto queryHandOverBillConditionDto,int limit,int start);
	
	/**
	 * 获取交接单条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 上午10:00:31
	 * @param queryHandOverBillConditionDto 查询条件dto
	 */
	Long getHandOverBillListCount(QueryHandOverBillConditionDto queryHandOverBillConditionDto);
	
	/**
	 * 根据交接单号获取交接单运单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:19:25
	 * @param handOverBillNo 交接单号
	 */
	List<HandOverBillDetailEntity> queryHandOverBillDetailByNo(String handOverBillNo);
	/**
	 * 根据交接单号获取正交接单运单列表
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午3:25:17
	 * @param handOverBillNo 交接单号
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryAirHandOverBillDetailByNo(java.lang.String)
	 */
	List<HandOverBillDetailEntity> queryAirHandOverBillDetailByNo( String handOverBillNo);
	/**
	 * 根据交接单号、运单号获取流水号list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午6:27:44
	 * @param handOverBillNo交接单号，waybillNo运单号
	 */
	List<SerialNoLoadExceptionDto> queryWaybillDetailByNos(String handOverBillNo,String waybillNo);
	
	/**
	 * 根据交接单号，获取交接单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午10:16:51
	 * @param handOverBillNo交接单号
	 */
	List<HandOverBillOptLogEntity> queryHandOverBillOptLogByNo(String handOverBillNo,int limit,int start);
	
	/**
	 * 根据交接单号，获取修改日志条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午11:13:25
	 * @param handOverBillNo交接单号
	 */
	Long getHandOverBillOptLogCount(String handOverBillNo);
	
	/**
	 * 批量更新交接单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 下午4:45:03
	 * @param dtoList，属性handOverBillNo交接单号，targetState目标状态(10：已预配，20：已交接，30：已出发，40：已到达，50：已入库，90：已作废)
	 */
	boolean updateHandOverBillState(List<UpdateHandOverBillStateDto> list) throws BusinessException;
	
	/**
	 * 根据交接单号更新交接单状态，单条处理
	 * @author 045923-foss-shiwei
	 * @date 2012-11-12 下午4:04:30
	 */
	boolean updateHandOverBillStateByNo(String handOverBillNo,int targetState);
	
	/**
	 * 根据交接单号得到交接单实体
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 下午1:56:49
	 * @param handOverBillNo交接单号
	 */
	HandOverBillEntity queryHandOverBillByNo(String handOverBillNo);
	/**
	 * 根据交接单号得到交接单实体
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午6:36:48
	 * @param handOverBillNo交接单号
	 * @return HandOverBillEntity
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryAirHandOverBillByNo(java.lang.String)
	 * 
	 */
	HandOverBillEntity queryAirHandOverBillByNo(String handOverBillNo);
	/**
	 * 用于交接单详情界面，导出运单列表，返回excel文件的inputstream
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 下午2:33:44
	 * @param handOverBillNo交接单号
	 */
	@SuppressWarnings("rawtypes")
	public List getWayBillExcelInputStream(String handOverBillNo,String handOverType) throws TfrBusinessException;
	
	/**
	 * 修改交接单保存数据service
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 上午9:53:52
	 * @param updateHandOverBillDto交接单修改数据dto
	 */
	boolean updateHandOverBill(UpdateHandOverBillDto updateHandOverBillDto);
	
	/**
	 * 作废交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-29 下午3:51:49
	 */
	boolean cancelHandOverBill(String handOverBillNo);
	
	/**
	 * 为配载单模块返回待配载交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:28:47
	 */
	List<QueryHandOverBillDto> queryHandOverBillListForVehicleAssembleBill(QueryHandOverBillConditionDto queryHandOverBillConditionDto);

	/**
	 * 获取交接单流水
	 * @author ibm-zhangyixin
	 * @date 2013-3-15 下午3:09:42
	 */
	List<HandOverBillSerialNoDetailEntity> getHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);

	/**
	 * 插入打印记录
	 * @author ibm-zhangyixin
	 * @date 2013-3-15 下午3:09:57
	 */
	int addHandoverbillPrintrecord(
			HandoverBillPrintRecordEntity handoverBillPrintRecord);

	/**
	 * 查询打印交接单中需要的数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-2 下午2:21:02
	 */
	List<HandOverBillDetailDto> queryPrintHandOverBillDataByNo(
			String handOverBillNo);
	/**
	 * 
	 * <p>根据交接单号查询出打印（空运）（正）交接单中需要的数据</p> 
	 * @author 189284 
	 * @date 2015-10-15 上午11:37:34
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	List<HandOverBillDetailDto> queryPrintAirHandOverBillDataByNo(
			String handOverBillNo);

	/**
	 * 查询打印外发清单中需要的数据 
	 * @author ibm-zhangyixin
	 * @date 2012-11-2 下午2:21:28
	 */
	List<HandOverBillDetailDto> queryPrintPartialLineDataByNo(
			String handOverBillNo);

	/**
	 * 打印交接单时校验是否已选中改车牌下所有的交接单 
	 * @author ibm-zhangyixin
	 * @date 2012-11-2 下午2:23:49
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#checkPrintHandOverBill(com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo)
	 */
	Long checkPrintHandOverBill(HandOverBillVo handOverBillVo);
	
	/**
	 * 显示交接单号
	 * @author 045923-foss-shiwei
	 * @date 2012-11-21 上午10:40:59
	 */
	String[] showHandOverBillNo();
	
	/**
	 * 更新交接单中流水号的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午8:28:50
	 */
	boolean updateHandOverBillSerialNoPreHandOverState(List<UpdateHandOverBillSerialNoPreHandOverStateDto> dtoList) throws TfrBusinessException;
	
	/**
	 * 传入到达部门code，返回外场实体，如果不是外场，则返回null
	 * @author 045923-foss-shiwei
	 * @date 2012-12-5 下午9:18:50
	 */
	OutfieldEntity queryOutfieldByOrgCode(String arriveDeptCode);
	/**
	 * 根据交接单号查询出配置单号 
	 * @author ibm-zhangyixin
	 * @date 2012-11-22 下午3:14:41
	 */
	String getVehicleassembleNoByHandoverNo(String handOverBillNo);
	
	/**
	 * 根据部门查询货件所在的交接单类型
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-12 下午4:31:45
	 */
	List<String> queryHandoverType(String waybillNo, String serialNo, String orgCode);
	
	/**
	 * 修改配载单车牌号时，批量更新配载单中交接单中的车牌及司机信息
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 下午8:18:43
	 */
	int updateVehicleNoAndDriverInfoFromVehicleAssembleBill(UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto dto);

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
	 * 根据车牌号获取司机信息
	 * @author 045923-foss-shiwei
	 * @date 2013-1-24 上午11:42:27
	 */
	DriverBaseDto queryDriverInfoByVehicleNo(String vehicleNo);
	
	/**
	 * 根据车牌号获取虚拟车牌司机信息
	 * @author 205109-foss-zenghaibin
	 * @date 2014-9-30 上午11:42:27
	 */
	ExpressVehiclesEntity queryDivisionDriverInfo(String vehicleNo);
	
	/**
	 * 根据到达部门code、出发部门code、运单号、指定的交接单号list中匹配出交接单号，用于上报OA少货时获取交接单号
	 * @author 045923-foss-shiwei
	 * @date 2013-2-28 下午4:53:35
	 */
	List<String> queryHandOverBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto);
	
	/**
	 * 获取上级组织code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-3 下午4:19:20
	 */
	String querySuperiorOrgCode();
	
	/**
	 * 根据运单号和到达部门code获取该运单被交接过的流水号list
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:37:22
	 */
	List<SerialNoStockEntity> queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(String waybillNo,String destOrgCode);

	/**
	 * 判断部门是否为营业部
	 * @author 045923-foss-shiwei
	 * @date 2013-5-23 上午9:49:25
	 */
	String queryOrgBeSalesDepartment(String orgCode);
	
	/**
	 * 查询某运单是否有在途的、分批交接的记录
	 * @return null，表示没有在途的交接记录
	 * 				  "Y"，表示分批交接
	 * 				  "N"，表示没有 分批交接
	 * @author 045923-foss-shiwei
	 * @date 2013-5-24 下午4:02:24
	 */
	String queryGoodsBeSeparatedFromHandOverBillByWaybillNo(String waybillNo);
	
	/**
	 * 获取三级产品类型
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 上午9:59:35
	 */
	List<BaseDataDictDto> queryProductList();
	
	/**
	 * 导出交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:07:46
	 */
	@SuppressWarnings("rawtypes")
	List getHandOverBillExcelInputStream(QueryHandOverBillConditionDto queryHandOverBillConditionDto);
	
	/**
	 * 交接单生成后，漂移未处理待办，用于job调用
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午10:55:24
	 */
	int driftedToDoMsgAfterHandOvered();
	
	/**
	 * 根据运单号查询该运单所有的被交接记录，并按交接时间排序
	 * @author 045923-foss-shiwei
	 * @date 2013-6-14 下午7:55:06
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
	 * Date:2013-7-18下午3:29:30
	 */ 
	HandOverBillEntity queryHandOverBillByLoadTaskNo(String taskNo);
	
	/**
	* @Title: queryBeLdpHandOveredByWaybillNo 
	* @Description: 根据运单号查询某运单是否有落地配交接记录
	* @param @param waybillNo 运单号
	* @return boolean  true表示有落地配交接记录，反之则反之
	* @throws
	 */
	boolean queryBeLdpHandOveredByWaybillNo(String waybillNo);
	
	/**
	 * @param queryWaybillForHandOverBillDto
	 * @return 达部门、配载部门所辐射的营业部的list
	 * @date 2013-10-17 下午3:58:46
	 */
	List<String> getArrivedDeptListByDto(
			QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto);
	/**
	 * 补录运单时更新交接单信息
	 * @param makeUpWaybillEntity
	 * @date 2014年2月18日 11:27:55
	 * @return
	 */
	String updateHandOverbillforMakeUpWaybill(MakeUpWaybillEntity makeUpWaybillEntity);
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
	 * @date 2014-3-6下午2:30:02
	 * @function 根据运单号和到达部门编码查询到达总件数
	 * @param waybillNo
	 * @param destOrgCode
	 * @return
	 */
	int queryWaybillCountByNoAndDept(String waybillNo, String destOrgCode);

	BigDecimal queryExpressConverParameter(String orgCode);
	
	/**
	 * 检查交接单在租车标记表的预提状态，如果为预提中或者已预提则不允许修改
	 * @author 205109-foss-zenghaibin
	 * @date 2014-08-14 上午08:27:39
	 */
	public String queryHoldingState(String handOverBillNo);
	
	/**
	 * 根据运单号查询代理运单号
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-27
	 * @param waybillNo
	 * @return
	 * @update-author 269701--lln
	 * @update-param waybillNo 运单号 serialNo 流水号 
	 */
	 String getAgentWaybillNoByWaybillNo(String waybillNo,String serialNo);
	 
	 /**
		 * 此方法根据运单号查询所有的交接单（包括废单）
		 * @Author:271297  zhangpeng
		 * 2015-7-27
		 * @param waybillNo
		 * @return
		 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryBeLdpHandOveredAllByWaybillNo(java.lang.String) 
		 */
	boolean queryBeLdpHandOveredAllByWaybillNo(String waybillNo);
	
	 
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
	 * @date 2015-9-22 上午9:52:32
	 * @param wayBillNo
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	List<HandOverBillSerialNoDetailEntity> getAirHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);
	List<HandOverBillDetailEntity> queryHandOverBillDetailByHandNo(String handOverBillNo);
    //2015/9/1 商务专递 272681
	List<HandOverBillSerialNoDetailEntity> getBusAirHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);
   //根据商务专递交接单号获取交接单运单列表 272681
	List<HandOverBillDetailEntity> queryBusAirHandOverBillDetailByNo(
			String handOverBillNo);
		
	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对多的关系
	 * @param taskNo
	 * @return    
	 * @return HandOverBillEntity    返回类型 
	 * queryHandOverBillNoByLoadTaskNo
	 * @author: 272681
	 * @throws 
	 * Date:2015-11-24
	 */ 
	String queryHandOverBillNoByLoadTaskNo(String taskNo);
	
	
	/** 
	 * @Title: queryArriveDeptList 
	 * @Description: 根据交接单VO信息，查询出他所有的到达部门
	 * @param handOverBillVo
	 * @return    
	 * @return List    返回类型 
	 * queryArriveDeptList
	 * @author: 332219
	 * @throws 
	 * Date:2016-9-10
	 */ 
	List<String> queryArriveDeptList(HandOverBillVo handOverBillVo);

	/**
	* @description 更新快递交接单状态
	* @param handoverNo
	* @param handoverStates
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年6月1日 上午11:12:44
	*/
	void updateWKHandOverBillStateByNo(String handoverNo,String handoverStates);
	
	/**
	 * 传入运单号、库存部门，下一到达部门，获取流水号list，用于查询交接运单界面
	 * @author 332219-foss-xuehaoyang
	 * @date 20126-10-2 上午9:30:04
	 */
	List<SerialNoStockEntity> querySaleSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据运单获取最后一次交接单信息
	 * @param waybillNo
	 * @return    
	 * @author: tfr-360903
	 * Date:2016年11月7日 11:04:30
	 */ 
	int  queryHandOverBillByLast(String waybillNo,String orgCode) ;
	
	/** 
	 * @Title: queryHandOverBillByWaybillNo 
	 * @Description: 根据运单获取最后一次交接单信息
	 * @param waybillNo
	 * @return    
	 * @author: tfr-332219
	 * Date:2016年12月27日
	 */ 
	public HandOverBillEntity queryHandOverBillByWaybillNo(String waybillNo);
	
}