/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description:
 * @author : panshiqi (309613)
 * @date : 2016年2月18日 上午11:10:21
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCRecordEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillReturnEntity;

/**
 * @description: 待刷卡运单管理接口
 * @className: IWSCWayBillManageService
 * 
 * @authorCode 309613
 * @date 2016年2月18日 上午11:10:45
 * 
 */
public interface IWSCWayBillManageService {

	/**
	* @description: 运单开单时添加待刷卡运单数据
	* @title: addWSCWayBill
	* @authorCode 309613
	* @date 2016年2月18日 下午5:47:54 
	* @param params 参数对象,添加的待刷卡运单数据
	* @throws Exception
	 */
	public void addWSCWayBill(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 修改待刷卡运单为无效
	* @title: invalidWSCWayBill
	* @authorCode 309613
	* @date 2016年2月24日 下午4:04:16 
	* @param params 参数对象,仅传运单编号即可
	* @throws Exception
	 */
	public void invalidWSCWayBillByWayBillNo(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 运单更改时修改待刷卡运单金额：包括更新原数据为无效 & 新增一条待刷卡运单数据
	* @title: invalidAndInsertWSCWayBill
	* @authorCode 309613
	* @date 2016年2月23日 下午5:48:01 
	* @param params 参数对象,修改后的待刷卡运单数据
	* @return WSCWayBillReturnEntity 修改(更新&插入)结果
	* @throws Exception
	 */
	public void invalidAndInsertWSCWayBill(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 根据运单号获取(最新&未支付&有效)待刷卡运单数据
	* @title: getLastActiveDataByWayBillNo
	* @authorCode 309613
	* @date 2016年2月24日 下午4:28:57 
	* @param params  参数对象,仅传运单编号即可
	* @return WSCWayBillReturnEntity 运单号下最新的待刷卡运单数据
	* @throws Exception
	 */
	public WSCWayBillReturnEntity getLastActiveDataByWayBillNo(WSCWayBillParamEntity params) throws Exception;

	/**
	* @description: 根据部门编号获取部门下所有有效&未支付待刷卡运单数据
	* @title: getWayBillListByOrgCode
	* @authorCode 309613
	* @date 2016年2月18日 下午5:53:56 
	* @param params 参数对象,仅传部门编号即可
	* @return List<WSCWayBillEntity> 待刷卡数据集合
	* @throws Exception
	 */
	public List<WSCWayBillEntity> getWayBillListByOrgCode(WSCWayBillParamEntity params) throws Exception;

	/**
	* @description: 记录刷卡结果
	* @title: recordSwipeCardResult
	* @authorCode 309613
	* @date 2016年2月18日 下午5:48:56 
	* @param params 参数对象,刷卡结果数据
	* @return int 受影响行数
	* @throws Exception
	 */
	public int recordSwipeCardResult(WSCWayBillEntity params) throws Exception;

	/**
	 * 
	* @description: 记录刷卡结果(批量)
	* @title: recordSwipeCardResult
	* @authorCode 309613
	* @date 2016年2月26日 上午9:09:40 
	* @param recordList 参数对象,刷卡结果数据集合
	* @return List<WSCWayBillReturnEntity> 刷卡信息记录结果集合
	* @throws Exception
	 */
	public WSCWayBillReturnEntity recordSwipeCardResultBatch(List<WSCRecordEntity> recordList) throws Exception;

	/**
	 * 
	* @description: 已刷卡运单金额减少
	* @title: swipedBillAmountReduce
	* @author panshiqi 309613
	* @date 2016年3月11日 下午2:51:53 
	* @return
	* @throws Exception
	 */
	public void swipedBillAmountReduce(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 更新运单下未支付待刷卡数据为无效
	* @title: invalidUnPayMentWSCDataByWayBillNo
	* @author panshiqi 309613
	* @date 2016年4月10日 下午2:46:12 
	* @param wayBillNo 
	* @throws Exception
	 */
	public void invalidUnPayMentWSCDataByWayBillNo(String wayBillNo) throws Exception;

	/**
	 * 
	* @description: 查询运单已刷卡金额总和
	* @title: getTotalSwipeAmountByWayBillNo
	* @author panshiqi 309613
	* @date 2016年4月10日 下午3:05:02 
	* @param wayBillNo
	* @return
	* @throws Exception
	 */
	public BigDecimal getTotalSwipedAmountByWayBillNo(String wayBillNo) throws Exception;

	/**
	 * 
	* @description: 判断运单是否已刷过卡
	* @title: wayBillIsAlreadySwiped
	* @author panshiqi 309613
	* @date 2016年4月18日 上午10:08:31 
	* @param wayBillNo 运单号
	* @return
	* @throws Exception
	 */
	public boolean wayBillIsAlreadySwiped(String wayBillNo) throws Exception;

	/**
	 * 
	* @description: 同步运单字段变化到待刷卡和T+0明细表
	* @title: syncBillInfo2WscAndT0
	* @author panshiqi 309613
	* @date 2016年4月11日 下午3:08:03 
	* @param params
	* @throws Exception
	 */
	public void syncBillInfo2WscAndT0(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 释放T+0明细金额到T+0报表  [事务：1.T+0明细置为无效  2.T+0报表已使用金额、未使用金额修改]
	* @title: releaseAmountWhenT0DetailInvalid
	* @author panshiqi 309613
	* @date 2016年3月18日 上午12:10:14 
	* @param t0Detailentity T+0明细实体
	* @param t0Entity T+0实体
	* @return
	* @throws Exception
	 */
	public int releaseAmountWhenT0DetailInvalid(PosCardDetailEntity t0Detailentity, PosCardEntity t0Entity) throws Exception;

	/**
	 * 
	* @description:  释放T+0明细金额到T+0报表(批量) [事务：1.T+0明细置为无效or(批量)  2.T+0报表已使用金额、未使用金额修改(批量)]
	* @title: releaseAmountWhenT0DetailInvalidBatch
	* @author panshiqi 309613
	* @date 2016年4月9日 上午11:12:56 
	* @param idList 批量处理的T+0明细数据的ID集合
	* @param params 需要批量更新的T+0数据集合
	* @param operateType 对T+0明细数据的操作类型 {1-删除   2-重置本次刷卡金额字段为0}
	* @throws Exception
	 */
	public void releaseAmountWhenT0DetailInvalidBatch(List<String> idList, Map<String, PosCardEntity> params, String... operateType)
			throws Exception;

	/**
	 * 
	* @description: 部分释放T+0明细金额到T+0报表(批量) [事务:1.T+0明细释放金额(批量) 2.T+0报表已使用金额、未使用金额修改(批量)]
	* @title: releaseAmountWhenSwipedBillReduceAmountBatch
	* @author panshiqi 309613
	* @date 2016年3月18日 上午2:38:04 
	* @param t0DetailEntityList
	* @param t0EntityList
	* @return
	* @throws Exception
	 */
	public void releaseAmountWhenSwipedBillReduceAmountBatch(List<PosCardDetailEntity> t0DetailEntityList,
			Map<String, PosCardEntity> t0EntityMap) throws Exception;

	/**
	 * 
	* @description: 更新T+0报表 & T+0明细相关金额字段 (事务)
	* @title: updateT0AndT0DetailAmount
	* @author panshiqi 309613
	* @date 2016年3月24日 上午9:08:18 
	* @param t0	待更新的T+0报表数据实体
	* @param t0Detail 待更新的T+0明细数据实体
	* @return 数据库受影响行数
	* @throws Exception
	 */
	public int updateT0AndT0DetailAmount(PosCardEntity t0, PosCardDetailEntity t0Detail) throws Exception;
}