/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description:
 * @author : panshiqi (309613)
 * @date : 2016年2月18日 下午8:07:37
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.api.server.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;

/**
 * 
* @description: 待刷卡运单管理数据层接口
* @className: IWSCWayBillManageDao
* 
* @authorCode 309613
* @date 2016年2月18日 下午8:08:07 
*
 */
public interface IWSCWayBillManageDao {

	//-----------------------------------------待刷卡相关操作-----------------------------------------

	/**
	 * 
	* @description: 运单开单时添加代刷卡运单数据
	* @title: addWSCWayBill
	* @authorCode 309613
	* @date 2016年2月18日 下午8:09:24 
	* @param params 参数对象,添加的待刷卡运单数据
	* @return int 受影响行数
	* @throws Exception
	 */
	public int addWSCWayBill(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 运单更改时更新待刷卡运单数据, 注:sql中并非全字段更新,当前仅根据业务更新部分字段.
	* @title: updateWSCWayBill
	* @author panshiqi 309613
	* @date 2016年4月5日 上午9:24:58 
	* @param params 
	* @return
	* @throws Exception
	 */
	public int updateWSCWayBillByItemID(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 根据运单号查询有效的待刷卡数据
	* @title: getWayBillsByWayBillNo
	* @authorCode 309613
	* @date 2016年2月24日 上午9:09:26 
	* @param wayBillNo 运单编号
	* @param wayBillSource 运单来源{1-运单开单 2-运单更改}
	* @return List<WSCWayBillEntity> 待刷卡运单信息集合
	* @throws Exception
	 */
	public List<WSCWayBillEntity> getWayBillsByWayBillNo(String wayBillNo, String wayBillSource) throws Exception;

	/**
	 * 
	* @description: 获取运单号下最新的有效待刷卡运单数据
	* @title: getLastActiveDataByWayBillNo
	* @authorCode 309613
	* @date 2016年2月24日 下午8:54:13 
	* @param wayBillNo 运单编号
	* @return WSCWayBillEntity 待刷卡运单数据
	* @throws Exception
	 */
	public WSCWayBillEntity getLastActiveDataByWayBillNo(String wayBillNo) throws Exception;

	/**
	 * 
	* @description: 根据部门编号查询部门下有效&未支付待刷卡运单数据
	* @title: getWayBillListByOrgCode
	* @authorCode 309613
	* @date 2016年2月21日 下午2:33:07 
	* @param orgCode 开单部门编号
	* @param wayBillNo 运单编号(可选)
	* @return List<WSCWayBillEntity> 待刷卡运单数据集合
	* @throws Exception
	 */
	public List<WSCWayBillEntity> getWayBillListByOrgCode(String orgCode, String... wayBillNo) throws Exception;

	/**
	 * 
	* @description: 根据待刷卡数据条目编号查询待刷卡运单数据
	* @title: getWayBillInfoByID
	* @authorCode 309613
	* @date 2016年2月18日 下午8:49:29 
	* @param id 待刷卡运单数据GUID标识
	* @return WSCWayBillEntity 待刷卡运单数据
	 */
	public WSCWayBillEntity getWayBillInfoByWSCItemId(String id) throws Exception;

	/**
	 * 
	* @description: 根据ItemId修改待刷卡运单数据为无效状态
	* @title: updateWSCWayBill
	* @authorCode 309613
	* @date 2016年2月19日 上午8:38:06 
	* @param params 参数对象,仅需传入待刷卡运单数据GUID标识(wscItemId)
	* @return int 受影响行数
	 */
	public int invalidWSCWayBillByItemId(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 根据运单号修改待刷卡运单数据为无效状态
	* @title: invalidWSCWayBillByWayBillNo
	* @authorCode 309613
	* @date 2016年2月24日 下午10:24:17 
	* @param params 参数对象,仅需传入运单编号
	* @return int 受影响行数
	* @throws Exception
	 */
	public int invalidWSCWayBillByWayBillNo(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 更新运单下未支付待刷卡数据为无效
	* @title: invalidUnPayMentWSCDataByWayBillNo
	* @author panshiqi 309613
	* @date 2016年4月10日 上午10:32:33 
	* @param wayBillNo
	* @return
	* @throws Exception
	 */
	public int invalidUnPayMentWSCDataByWayBillNo(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 记录刷卡结果
	* @title: recordSwipeCardResult
	* @authorCode 309613
	* @date 2016年2月18日 下午8:09:56 
	* @param params 参数对象,刷卡结果数据
	* @return int 受影响行数
	 */
	public int recordSwipeCardResult(WSCWayBillEntity params) throws Exception;

	/**
	 * 
	* @description: 同步运单字段变化到待刷卡
	* @title: synchBillInfo2Wsc
	* @author panshiqi 309613
	* @date 2016年4月11日 下午3:31:06 
	* @param params
	* @return
	* @throws Exception
	 */
	public int synchBillInfo2Wsc(WSCWayBillParamEntity params) throws Exception;

	/**
	 * 
	* @description: 根据运单号获取运单有效刷卡记录次数
	* @title: getSwipedCountByWayBillNo
	* @author panshiqi 309613
	* @date 2016年4月18日 上午10:23:33 
	* @param wayBillNo 运单号
	* @return
	* @throws Exception
	 */
	public int getSwipedCountByWayBillNo(String wayBillNo) throws Exception;

	//-----------------------------------------T+0相关操作-----------------------------------------

	/**
	 * 
	* @description: 已刷卡运单更改，金额增加时，修改运单的T+0明细数据
	* @title: updateT0DetailWhenBillAmountAdd
	* @author panshiqi 309613
	* @date 2016年3月17日 上午9:33:46 
	* @param params 参数实体,必要参数{wayBillNo 运单号，addAmount 运单更改增加金额}
	* @return 更新成功行数
	* @throws Exception
	 */
	public int updateT0DetailWhenBillAmountAdd(PosCardDetailEntity params) throws Exception;

	/**
	 * 
	* @description: 根据运单号获取所有T+0明细数据
	* @title: queryT0DetailByWayBillNo
	* @author panshiqi 309613
	* @date 2016年3月17日 上午8:42:25 
	* @param wayBillNo 运单号
	* @param invoiceType 单据类型
	* @return T+0明细实体集合
	* @throws Exception
	 */
	public List<PosCardDetailEntity> queryT0DetailByWayBillNo(String wayBillNo, String invoiceType) throws Exception;

	/**
	 * 
	* @description: 根据运单号和交易流水号获取T+0明细数据
	* @title: queryT0DetailByWayBillNoAndSerialNo
	* @author panshiqi 309613
	* @date 2016年3月24日 上午10:50:23 
	* @param wayBillNo
	* @param serialNo
	* @param invoiceType
	* @return
	* @throws Exception
	 */
	public PosCardDetailEntity queryT0DetailByWayBillNoAndSerialNo(String wayBillNo, String serialNo, String invoiceType) throws Exception;

	/**
	 * 
	* @description: 根据交易流水号获取T+0数据
	* @title: queryT0BySerialNo
	* @author panshiqi 309613
	* @date 2016年3月17日 上午8:44:52 
	* @param serialNo 交易流水号
	* @return 单个T+0数据实体
	* @throws Exception
	 */
	public PosCardEntity queryT0BySerialNo(String serialNo) throws Exception;

	/**
	 * 
	* @description: 根据ID置T0明细数据为无效
	* @title: invalidT0DetailByID
	* @author panshiqi 309613
	* @date 2016年3月17日 下午3:19:55 
	* @param id T0明细数据ID
	* @return 更新数据库行数
	* @throws Exception
	 */
	public int invalidT0DetailByID(String id) throws Exception;

	/**
	 * 
	* @description: 释放T0明细表刷卡金额到T0报表
	* @title: releaseDetailAmount2T0
	* @author panshiqi 309613
	* @date 2016年3月17日 下午3:21:54 
	* @param params T0数据实体,实体中字段赋值则会更新到数据库,调用时确保参数实体中不需要更新的字段为空或空白
	* @return 更新数据库行数
	* @throws Exception
	 */
	public int releaseDetailAmount2T0(PosCardEntity params) throws Exception;

	/**
	 * 
	* @description: 根据ID置T0明细数据为无效(批量)
	* @title: invalidT0DetailByIDBatch
	* @author panshiqi 309613
	* @date 2016年3月17日 下午10:58:43 
	* @param idList 待置无效的明细ID集合
	* @return
	* @throws Exception
	 */
	public int invalidT0DetailByIDBatch(List<String> idList) throws Exception;

	/**
	 * 
	* @description: 根据ID置T0明细数据本次刷卡金额为0(批量)
	* @title: resetT0DetailOccAmountByIDBatch
	* @author panshiqi 309613
	* @date 2016年4月9日 上午11:05:10 
	* @param idList
	* @return
	* @throws Exception
	 */
	public int resetT0DetailOccAmountByIDBatch(List<String> idList) throws Exception;

	/**
	 * 
	* @description: 释放T0明细表刷卡金额到T0报表(批量)
	* @title: releaseDetailAmount2T0Batch
	* @author panshiqi 309613
	* @date 2016年3月17日 下午11:10:14 
	* @param params Map<String, PosCardEntity> {key:交易流水号, value:T+0报表数据}
	* @return 更新数据库行数
	* @throws Exception
	 */
	public int releaseDetailAmount2T0Batch(Map<String, PosCardEntity> params) throws Exception;

	/**
	 * 
	* @description: 更新T+0明细数据(批量)
	* @title: updateT0DetailBatch
	* @author panshiqi 309613
	* @date 2016年3月18日 上午2:48:03 
	* @param t0DetailEntityList
	* @return
	* @throws Exception
	 */
	public int updateT0DetailBatch(List<PosCardDetailEntity> t0DetailEntityList) throws Exception;

	/**
	 * 
	* @description: 更新T+0报表数据的相关金额
	* @title: updateT0Amount
	* @author panshiqi 309613
	* @date 2016年3月24日 上午9:16:53 
	* @param t0
	* @return
	* @throws Exception
	 */
	public int updateT0Amount(PosCardEntity t0) throws Exception;

	/**
	 * 
	* @description: 更新T+0明细数据的相关金额
	* @title: updateT0DetailAmount
	* @author panshiqi 309613
	* @date 2016年3月24日 上午9:17:11 
	* @param t0Detail
	* @return
	* @throws Exception
	 */
	public int updateT0DetailAmount(PosCardDetailEntity t0Detail) throws Exception;

	/**
	 * 
	* @description: 获取T+0明细表中运单有效已刷卡金额总和 & 运单总金额
	* @title: getBillAmountAndSumOccupateAmount
	* @author panshiqi 309613
	* @date 2016年3月30日 下午9:21:42 
	* @param wayBillNo 运单号
	* @param invoiceType 单据类型
	* @return
	* @throws Exception
	 */
	public Map<String, BigDecimal> getBillAmountAndSumOccupateAmount(String wayBillNo, String invoiceType) throws Exception;

	/**
	 * 
	* @description: 同步运单字段变化到T+0明细
	* @title: synchBillInfo2T0
	* @author panshiqi 309613
	* @date 2016年4月11日 下午4:04:01 
	* @param t0Detail
	* @return
	* @throws Exception
	 */
	public int synchBillInfo2T0(PosCardDetailEntity t0Detail) throws Exception;

}
