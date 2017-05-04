/**
 * @company : com.deppon
 * @project : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description:
 * @author : panshiqi(309613)
 * @date : 2016年2月18日 下午8:12:37
 * @version : v1.0
 */

package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.settlement.common.api.server.dao.IWSCWayBillManageDao;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCRecordEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillReturnEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
* @description: 待刷卡运单管理接口实现
* @className: WSCWayBillManageService
* 
* @authorCode 309613
* @date 2016年2月18日 下午8:12:41 
*
 */
public class WSCWayBillManageService implements IWSCWayBillManageService {

	/**
	 * 注入Dao对象
	 */
	private IWSCWayBillManageDao wscWayBillManageDao;

	/**
	 * 注入service对象
	 */
	private IWSCWayBillManageService wscWayBillManageService;
    private IWSCManageService wscManageService;

	/**  
	 * 设置 注入Dao对象  
	 * @param wscWayBillManageDao 注入Dao对象  
	 */
	public void setWscWayBillManageDao(IWSCWayBillManageDao wscWayBillManageDao) {
		this.wscWayBillManageDao = wscWayBillManageDao;
	}

    /**
	 * 设置 注入service对象  
	 * @param wscWayBillManageService 注入service对象  
	 */
	public void setWscWayBillManageService(IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}

    public void setWscManageService(IWSCManageService wscManageService) {
        this.wscManageService = wscManageService;
    }

    /**
	 * 获取日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WSCWayBillManageService.class);

	//-----------------------------------------------------------------------------
	// 描述: 添加代刷卡运单数据
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	  1.运单开单
	//    2.运单发更改: 已支付运单"现付金额"增加 (即:现付金额 > 运单已刷卡金额总和)
	//    3.运单发更改: 非银行卡运单付款方式变更为银行卡
	// 修改记录:
	// 	  v1.0 - 2016-4-9 14:22:43 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public void addWSCWayBill(WSCWayBillParamEntity params) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-添加代刷卡运单数据-begin-".concat(logGuidStr));

		// 结果信息
		String returnMsg = "";

		// 验证必要参数
		if (params == null) {
			returnMsg = "WSC-添加代刷卡运单数据-参数对象不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证运单号
		if (StringUtil.isNUllOrWhiteSpace(params.getWayBillNo())) {
			returnMsg = "WSC-添加代刷卡运单数据-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证运单来源 {1-运单开单 2-运单更改}
		if (StringUtil.isNUllOrWhiteSpace(params.getWayBillSource())) {
			returnMsg = "WSC-添加代刷卡运单数据-数据来源不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-添加代刷卡运单数据-processing-".concat(logGuidStr).concat("{运单号:").concat(params.getWayBillNo()).concat(",运单来源:")
				.concat(params.getWayBillSource()).concat("}"));

		// 发货联系人名称验证
		if (StringUtil.isNUllOrWhiteSpace(params.getSendCustomerName())) {
			returnMsg = "WSC-添加代刷卡运单数据-发货联系人名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 开单部门编码验证
		if (StringUtil.isNUllOrWhiteSpace(params.getCreateBillOrgCode())) {
			returnMsg = "WSC-添加代刷卡运单数据-开单部门编码不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 开单部门名称验证
		if (StringUtil.isNUllOrWhiteSpace(params.getCreateBillOrgName())) {
			returnMsg = "WSC-添加代刷卡运单数据-开单部门名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 开单时间验证
		if (params.getCreateBillTime() == null) {
			returnMsg = "WSC-添加代刷卡运单数据-开单时间不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 待刷卡金额验证
		if (params.getWaitSwipeAmount() <= 0) {
			returnMsg = "WSC-添加代刷卡运单数据-待刷卡金额不能小于等于0！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 运单总金额验证
		if (params.getWayBillAmount() <= 0) {
			returnMsg = "WSC-添加代刷卡运单数据-运单总金额不能小于等于0！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 创建人编号验证
		if (StringUtil.isNUllOrWhiteSpace(params.getCreateUserCode())) {
			returnMsg = "WSC-添加代刷卡运单数据-创建人编号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 创建人名称验证
		if (StringUtil.isNUllOrWhiteSpace(params.getCreateUserName())) {
			returnMsg = "WSC-添加代刷卡运单数据-创建人名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 区分不同数据来源,根据对应的条件查询待刷卡数据, 条件: A.运单开单{1.运单号 2.有效} B.运单更改{1.运单号 2.有效 3.未刷卡(未支付&已刷卡金额为0)}
		List<WSCWayBillEntity> wayBills = wscWayBillManageDao.getWayBillsByWayBillNo(params.getWayBillNo(), params.getWayBillSource());

		// 已存在待刷卡数据,不允许添加待刷卡数据
		if (wayBills != null && wayBills.size() > 0) {
			returnMsg = "WSC-添加待刷卡运单数据失败:已存在运单号为" + params.getWayBillNo() + "的有效待刷卡数据.";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 添加备注
		params.setNotes("WSC-运单开单,新增待刷卡数据.");

		// 运单发更改
		if ("2".equals(params.getWayBillSource())) {

			// 添加备注
			params.setNotes("WSC-已支付运单更改,现付金额增加or改为银行卡,新增待刷卡数据.".concat(params.getPointParamJson()));
		}

		// 插入运单数据
		int lines = wscWayBillManageDao.addWSCWayBill(params);
		if (lines <= 0) {
			returnMsg = "WSC-添加待刷卡运单数据失败,数据库影响行数为0.";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 数据插入成功
		LOGGER.info("WSC-添加待刷卡运单数据成功");

		// 记录日志
		LOGGER.info("WSC-添加代刷卡运单数据-end-".concat(logGuidStr));

	}

	//-----------------------------------------------------------------------------
	// 描述: 根据运单号获取(最新&有效)待刷卡运单数据
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景:
	//	  1.银行卡运单发更改时, 接送货查询运单最新的待刷卡数据判断运单是否为已支付.
	//      概念:
	//			1.已支付: 运单预付金额已全部刷卡
	//          2.未支付: 运单预付金额部分刷卡或未刷卡
	// 修改记录:
	//	  v1.0 - 2016-4-9 14:22:43 - 309613
	//-----------------------------------------------------------------------------
	@Override
	public WSCWayBillReturnEntity getLastActiveDataByWayBillNo(WSCWayBillParamEntity params) throws Exception {

		// 日志的guid标识
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-根据运单号获取(最新&有效)待刷卡运单数据-begin-".concat(logGuidStr));

		// 声明返回对象
		WSCWayBillReturnEntity result = new WSCWayBillReturnEntity();

		// 返回信息
		String returnMsg = "";

		// 验证必要参数
		if (params == null) {
			returnMsg = "WSC-根据运单号获取(最新&有效)待刷卡运单数据-参数对象不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 验证运单号
		String wayBillNo = params.getWayBillNo();
		if (StringUtil.isNUllOrWhiteSpace(wayBillNo)) {
			returnMsg = "WSC-根据运单号获取(最新&有效)刷卡运单数据-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-根据运单号获取(最新&有效)待刷卡运单数据-processing-".concat(logGuidStr).concat("{运单号:").concat(params.getWayBillNo()).concat("}"));

		// 查询数据
		WSCWayBillEntity wayBillInfo = wscWayBillManageDao.getLastActiveDataByWayBillNo(wayBillNo);
		result.setWscWayBillInfo(wayBillInfo);

		// 返回结果
		if (wayBillInfo == null) {
			returnMsg = "WSC-根据运单号获取(最新&有效)待刷卡运单数据-未查询到该运单号待刷卡运单数据.";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		} else {
			returnMsg = "WSC-根据运单号获取(最新&有效)待刷卡运单数据-查询数据成功.";
			LOGGER.info(returnMsg);
			result.setResultCode("1");
			result.setResultDesc(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-根据运单号获取(最新&有效)待刷卡运单数据-end-".concat(logGuidStr));

		return result;
	}

	//-----------------------------------------------------------------------------
	// 描述: 根据运单号修改待刷卡运单数据为无效
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	  1.运单作废: a.查询T+0明细数据,如存在,则释放明细已刷卡金额到T+0报表
	//                 释放逻辑:将明细已刷卡金额追加到T+0报表未使用金额中,T+0报表中已
	//                         使用金额相应减少
	//               b.更新对应的T+0明细数据为已删除
	//               c.更新运单有效的待刷卡数据为无效
	//    2.运单修改为非银行卡:
	//      |- 已支付: a.查询T+0明细数据,如存在释放金额,同上
	//                b.更新对应的T+0明细本次刷卡为0, 但不删除.
	//                c.不更新待刷卡数据
	//      |- 未支付: a.查询T+0明细数据,如存在释放金额,同上
	//                b.更新对应的T+0明细本次刷卡为0, 但不删除.
	//                c.更新运单有效的未支付数据为无效
	// 修改记录:
	// 	  v1.0 - 2016-4-9 14:22:43 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public void invalidWSCWayBillByWayBillNo(WSCWayBillParamEntity params) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-根据运单号修改待刷卡运单数据为无效-begin-".concat(logGuidStr));

		// 返回信息
		String returnMsg = "";

		// 验证必要参数
		if (params == null) {
			returnMsg = "WSC-根据运单号修改待刷卡运单数据为无效-参数对象不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证运单号
		if (StringUtil.isNUllOrWhiteSpace(params.getWayBillNo())) {
			returnMsg = "WSC-根据运单号修改待刷卡运单数据为无效-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-根据运单号修改待刷卡运单数据为无效-processing-".concat(logGuidStr).concat("{运单号:").concat(params.getWayBillNo()).concat("}"));

		// 验证修改人编号
		if (StringUtil.isNUllOrWhiteSpace(params.getModifyUserCode())) {
			returnMsg = "WSC-根据运单号修改待刷卡运单数据为无效-修改人编号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证修改人名称
		if (StringUtil.isNUllOrWhiteSpace(params.getModifyUserName())) {
			returnMsg = "WSC-根据运单号修改待刷卡运单数据为无效-修改人名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		//---------------------------------------------------------------------------------
		//	释放 {1.作废运单  2.付款由银行卡更改为非银行卡的运单} 所占刷卡交易流水号金额
		//---------------------------------------------------------------------------------
		//	NOTE:
		//		a.查询运单号是否有T+0明细数据, 若存在明细数据, 则将明细中的占用流水号金额释放到T+0中
		//        1.运单作废时,T+0明细数据删除
		//        2.运单付款方式改为非银行卡时, T+0明细数据不删除, 仅修改本次刷卡金额字段为0
		//---------------------------------------------------------------------------------
		try {

			// 根据运单号查询T+0明细数据, T+0明细表中 "INVOICE_TYPE" 字段使用 W1、W2 区分待刷卡数据和结清货款数据.{W1:待刷卡  W2:结清货款}
			List<PosCardDetailEntity> t0DetailList = wscWayBillManageDao.queryT0DetailByWayBillNo(params.getWayBillNo(), "W1");

			// 如果查询到T+0明细数据, 则释放明细占用流水号金额
			if (t0DetailList != null && t0DetailList.size() > 0) {

				// 声明HashMap保存明细集合中所有的交易流水号，及对应的待更新T0数据实体
				HashMap<String, PosCardEntity> updateT0Map = new HashMap<String, PosCardEntity>();

				// 声明List保存待释放的明细ID
				List<String> idList = new ArrayList<String>();

				// 声明List保存处理中发生异常的明细数据编号
				List<String> failedReleaseDetailId = new ArrayList<String>();

				// 循环处理T+0明细数据
				for (PosCardDetailEntity detailEntity : t0DetailList) {

					try {

						// 将明细编号添加到list, 删除明细数据时使用
						idList.add(detailEntity.getId());

						// 判断明细所属交易流水号是否已保存到map中, 如未保存则添加
						if (!updateT0Map.containsKey(detailEntity.getTradeSerialNo())) {

							// 根据交易流水号获取T+0明细所属的T+0报表数据
							PosCardEntity t0Entity = wscWayBillManageDao.queryT0BySerialNo(detailEntity.getTradeSerialNo());

							// 声明T+0报表数据实体, 将需要更新的字段添加到实体属性中
							PosCardEntity t0EntityParam = new PosCardEntity();

							// 追减流水号已使用金额, PS: 新已使用金额 = 旧已使用金额 - 明细已占用流水号金额
							t0EntityParam.setUsedAmount(t0Entity.getUsedAmount().subtract(detailEntity.getOccupateAmount()));

							// 追加流水号未使用金额, PS: 新未使用金额 = 旧未使用金额 + 明细已占用流水号金额
							t0EntityParam.setUnUsedAmount(t0Entity.getUnUsedAmount().add(detailEntity.getOccupateAmount()));

							// 设置交易流水号, 作为更新T+0报表数据的过滤条件
							t0EntityParam.setTradeSerialNo(detailEntity.getTradeSerialNo());

							// 设置修改人编号
							t0EntityParam.setModifyUserCode(params.getModifyUserCode());

							// 设置修改人名称
							t0EntityParam.setModifyUser(params.getModifyUserName());

							// 设置备注
							t0EntityParam.setNotes("WSC-已刷卡运单作废,删除明细,释放明细刷卡金额.");

							// 将交易流水号和待更新的T+0实体添加到map中
							updateT0Map.put(detailEntity.getTradeSerialNo(), t0EntityParam);

						}
						// 如交易流水号以保存在Map中, 先从map中取出待更新T+0数据实体, 再将明细已占用流水号金额追加到待更新T+0数据实体中
						else {

							// 从map中取出待更新T+0实体
							PosCardEntity newT0EntityParam = updateT0Map.get(detailEntity.getTradeSerialNo());

							// 将明细释放金额追加到T+0实体
							if (newT0EntityParam != null) {

								// 追减流水号已使用金额
								newT0EntityParam.setUsedAmount(newT0EntityParam.getUsedAmount().subtract(detailEntity.getOccupateAmount()));

								// 追加流水号未使用金额
								newT0EntityParam.setUnUsedAmount(newT0EntityParam.getUnUsedAmount().add(detailEntity.getOccupateAmount()));
							}

							// 将交易流水号和待更新的T+0实体添加到map中
							updateT0Map.put(detailEntity.getTradeSerialNo(), newT0EntityParam);
						}

					} catch (Exception e) {

						// 将释放金额失败的明细id保存到list集合中, 便于查询
						failedReleaseDetailId.add(detailEntity.getId());
					}
				}

				// T+0明细数据操作类型 {1-修改状态为删除  2-重置本次刷卡金额字段为0}
				String operateType = params.isSwipedBillPayWayChange() ? "2" : "1";

				// 【事务】 批量更新T+0明细数据  & 批量更新T+0报表已使用、未使用金额
				wscWayBillManageService.releaseAmountWhenT0DetailInvalidBatch(idList, updateT0Map, operateType);

				// 更新T+0数据结果处理
				returnMsg = failedReleaseDetailId.size() > 0 ? "WSC-运单作废or改为非银行卡,释放T+0明细金额失败,失败明细ID:".concat(failedReleaseDetailId
						.toString()) : "WSC-运单作废or改为非银行卡,释放T+0明细金额,更新数据成功.";
				LOGGER.info(returnMsg);
				params.setNotes(returnMsg);

			}
			// 未查询到T+0明细数据, 记录到备注字段中
			else {

				// 设置备注
				returnMsg = "WSC-运单作废or改为非银行卡,释放T+0明细金额时未查询到明细数据.";
				LOGGER.info(returnMsg);
				params.setNotes(returnMsg);
			}

		} catch (Exception e) {

			// 获取打屏信息的标识, 方便日后查询异常
			String logFlag = StringUtil.getLogFlag();

			// 打印异常信息
			System.out.println("WSC-运单作废or改为非银行卡,释放T+0明细金额异常:".concat(logFlag).concat(StringUtil.getExceptionStackTrace(e)));
			params.setNotes("WSC-运单作废or改为非银行卡,释放T+0明细金额异常-".concat(logFlag));
		}

		//---------------------------------------------------------------------------------
		//  更新运单号的未支付待刷卡数据为无效
		//---------------------------------------------------------------------------------
		//	NOTE:
		//		a.避免运单作废或已为非银行卡单仍存在未刷卡数据
		//---------------------------------------------------------------------------------
		// 声明待刷卡数据对象, 将待修改字段更新到对象中
		WSCWayBillParamEntity deleteParams = new WSCWayBillParamEntity();

		// 设置运单号
		deleteParams.setWayBillNo(params.getWayBillNo());

		// 设置修改人编号
		deleteParams.setModifyUserCode(params.getModifyUserCode());

		// 设置修改人名称
		deleteParams.setModifyUserName(params.getModifyUserName());

		// 设置备注
		returnMsg = "WSC-运单作废or改为非银行卡,更新运单号的未支付待刷卡数据为无效.";
		deleteParams.setNotes(StringUtil.isNUllOrWhiteSpace(params.getNotes()) ? returnMsg : params.getNotes().concat(returnMsg));

		// 更新数据
		wscWayBillManageDao.invalidUnPayMentWSCDataByWayBillNo(deleteParams);

		//---------------------------------------------------------------------------------
		//  更改运单号对应的待刷卡数据为"无效"
		//---------------------------------------------------------------------------------
		//	NOTE:
		//		a.运单作废时, 修改待刷卡表中对应运单号有效数据为"无效"
		//	    b.非银行卡运单修改为银行卡时, 不修改待刷卡运单数据为"无效"
		//---------------------------------------------------------------------------------
		if (!params.isSwipedBillPayWayChange()) {

			// 声明待刷卡数据对象, 将待修改字段更新到对象中
			WSCWayBillParamEntity modifyParams = new WSCWayBillParamEntity();

			// 设置运单号
			modifyParams.setWayBillNo(params.getWayBillNo());

			// 设置修改人编号
			modifyParams.setModifyUserCode(params.getModifyUserCode());

			// 设置修改人名称
			modifyParams.setModifyUserName(params.getModifyUserName());

			// 设置备注
			returnMsg = "WSC-运单作废,修改运单号下有效数据为无效.";
			modifyParams.setNotes(StringUtil.isNUllOrWhiteSpace(params.getNotes()) ? returnMsg : params.getNotes().concat(returnMsg));

			// 修改待刷卡运单数据为"无效"
			int lines = wscWayBillManageDao.invalidWSCWayBillByWayBillNo(modifyParams);
			if (lines <= 0) {
				returnMsg = "WSC-运单作废,修改运单号下已刷卡有效数据为无效失败,影响行数为0.";
				LOGGER.info(returnMsg);
				//throw new SettlementException(returnMsg); // 当运单开单未刷卡即被作废, 前面已经将这条记录置为无效, 此处抛异常则会导致运单作废失败, 这里打印信息即可.
			}

			// 返回结果
			returnMsg = "WSC-运单作废,修改运单号下有效数据为无效成功.";
			LOGGER.info(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-根据运单号修改待刷卡运单数据为无效-end-".concat(logGuidStr));

	}

	//-----------------------------------------------------------------------------
	// 描述: 修改待刷卡运单金额
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	  1.运单未刷卡运单,"现付金额"增加 (即:现付金额 > 运单已刷卡金额总和)
	//      操作: 更新待刷卡运单数据的总金额和待刷卡金额
	// 
	// 修改记录:
	// 	  v1.0 - 2016-4-9 14:22:43 - 309613  
	// PS: 
	//    吐槽一下该接口名：v1.0  时未刷卡运单发更改,更改逻辑是先置为无效再插入新数据,所以就有
	//    了 invalidAndInsert,v2.0  时突然修改为直接更新,然后就...
	//-----------------------------------------------------------------------------
	@Override
	public void invalidAndInsertWSCWayBill(WSCWayBillParamEntity params) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-begin-".concat(logGuidStr));

		// 返回消息
		String returnMsg = "";

		// 必要参数验证
		if (params == null) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-参数对象不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证待刷卡数据ID
		if (StringUtil.isNUllOrWhiteSpace(params.getWscItemId())) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-ID不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-processing-".concat(logGuidStr).concat("{数据编号:").concat(params.getWscItemId()).concat("}"));

		// 验证修改人编号
		if (StringUtil.isNUllOrWhiteSpace(params.getWscItemId())) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-修改人编号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证修改人名称
		if (StringUtil.isNUllOrWhiteSpace(params.getWscItemId())) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-修改人名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 运单总金额验证
		if (params.getWayBillAmount() <= 0) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-运单总金额不能小于等于0！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证修改后的待刷卡金额
		if (params.getWaitSwipeAmount() <= 0) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-更改后的待刷卡金额不能小于等于0！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		//-----------------------------------------------------------------------------
		//	PS: 1. 待刷卡里面的开单部门编号和名称, 实际为收货部门编号和名称, 原因是A部门能帮B部门
		//		      开单, 并且单据归属于B部门, 且仅B部门快递员能刷卡.
		//      2. 已刷过卡的运单是不允许修改收货部门的
		//-----------------------------------------------------------------------------
		// 开单部门编码验证, 实际传入的值为收货部门编号
		if (StringUtil.isNUllOrWhiteSpace(params.getCreateBillOrgCode())) {
			returnMsg = "WSC-添加代刷卡运单数据-开单部门编码不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 开单部门名称验证, 实际传入的值为收货部门名称
		if (StringUtil.isNUllOrWhiteSpace(params.getCreateBillOrgName())) {
			returnMsg = "WSC-添加代刷卡运单数据-开单部门名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 验证待更改数据是否存在
		WSCWayBillEntity wscWayBillInfo = wscWayBillManageDao.getWayBillInfoByWSCItemId(params.getWscItemId());
		if (wscWayBillInfo == null) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-待刷卡数据不存在或已无效,数据条目编号：" + params.getWscItemId();
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 更新待刷卡数据
		// 声明待刷卡数据对象, 将待更新字段更新到对象属性中
		WSCWayBillParamEntity modifyParams = new WSCWayBillParamEntity();

		// 设置待刷卡数据编号
		modifyParams.setId(params.getWscItemId());

		// 设置修改人编号
		modifyParams.setModifyUserCode(params.getModifyUserCode());

		// 设置修改人名称
		modifyParams.setModifyUserName(params.getModifyUserName());

		// 设置修改时间
		modifyParams.setModifyTime(params.getModifyTime());

		// 设置备注
		modifyParams.setNotes("WSC-未刷卡运单更改".concat(params.getPointParamJson()));

		// 设置运单总金额
		modifyParams.setWayBillAmount(params.getWayBillAmount());

		// 设置新待刷卡金额
		modifyParams.setWaitSwipeAmount(params.getWaitSwipeAmount());

		// 设置数据来源为运单更改 {1-运单开单  2-运单更改}
		modifyParams.setWayBillSource("2");

		// 设置开单部门编号
		modifyParams.setCreateBillOrgCode(params.getCreateBillOrgCode());

		// 设置开单部门名称
		modifyParams.setCreateBillOrgName(params.getCreateBillOrgName());

		// 更新待刷卡运单数据
		int lines = wscWayBillManageDao.updateWSCWayBillByItemID(modifyParams);
		if (lines <= 0) {
			returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)失败,数据库影响行数为0.";
			LOGGER.info(returnMsg);
			//throw new SettlementException(returnMsg);
		}

		// 更新成功
		returnMsg = "WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-更改成功.";
		LOGGER.info(returnMsg);

		// 记录日志
		LOGGER.info("WSC-修改待刷卡运单金额(未刷卡运单现付金额增加)-end-".concat(logGuidStr));
	}

	//-----------------------------------------------------------------------------
	// 描述: 根据部门编号查询部门下待刷卡运单数据
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	  1.PDA调用该接口查询部门下待刷卡数据,传递给POS进行刷卡操作
	// 
	// 修改记录:
	// 	  v1.0 - 2016-4-9 14:22:43 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public List<WSCWayBillEntity> getWayBillListByOrgCode(WSCWayBillParamEntity params) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-根据部门编号查询部门下待刷卡运单数据-begin-".concat(logGuidStr));

		// 返回信息
		String returnMsg = "";

		// 必要参数验证
		if (params == null) {
			returnMsg = "WSC-根据部门编号查询部门下待刷卡运单数据-参数对象不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证部门编号
		if (StringUtil.isNullOrEmpty(params.getOrgCode())) {
			returnMsg = "WSC-根据部门编号查询部门下待刷卡运单数据-查询数据时传入部门编号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-根据部门编号查询部门下待刷卡运单数据-processing-".concat(logGuidStr).concat("{部门编号:").concat(params.getOrgCode()).concat("}"));

		// 查询数据
		List<WSCWayBillEntity> billList = new ArrayList<WSCWayBillEntity>();
		billList = wscWayBillManageDao.getWayBillListByOrgCode(params.getOrgCode(), params.getWayBillNo());

		// 记录日志
		LOGGER.info("WSC-根据部门编号查询部门下待刷卡运单数据-end-".concat(logGuidStr));

		// 返回结果
		return billList;
	}

	//-----------------------------------------------------------------------------
	// 描述: 记录刷卡结果(批量)
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	  1.PDA上传刷卡记录, PDA传入的刷卡记录数据结构：
	//      List
	//         |-List
	//              |-wayBillSwipedResult
	//              |-...
	//              |-wayBillSwipedResult
	//         |-...
	//         |-List
	// 		
	// 修改记录:
	// 	  v1.0 - 2016-4-9 14:22:43 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public WSCWayBillReturnEntity recordSwipeCardResultBatch(List<WSCRecordEntity> paramsList) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-记录刷卡结果(批量)-begin-".concat(logGuidStr));

		// 声明返回结果对象集合
		WSCWayBillReturnEntity result = new WSCWayBillReturnEntity();

		// 判断参数
		if (paramsList == null || paramsList.size() == 0) {
			throw new SettlementException("WSC-记录刷卡结果(批量)-刷卡结果数据集合不能为空！");
		}

		// 声明待刷卡运单记录数据集合
		List<WSCWayBillEntity> wscRecordList = null;

		// 刷卡结果记录失败的运单号集合
		List<String> failRecordWayBillNoList = new ArrayList<String>();

		// 刷卡结果记录失败运单对应的失败原因集合
		Map<String, String> failRecordMsgMap = new HashMap<String, String>();

		// 刷卡结果记录成功的运单号集合
		List<String> succeedRecordWayBillNoList = new ArrayList<String>();

		// 循环处理多笔刷卡结果数据
		for (WSCRecordEntity wscRecordEntity : paramsList) {
			// 清空对象
			wscRecordList = null;

			// 验证刷卡记录
			if (wscRecordEntity != null) {

				// 获取单笔刷卡记录集合
				wscRecordList = wscRecordEntity.getWscRecordList();

				// 一笔刷卡运单集合对象不能为空 & 运单集合长度大于0
				if (wscRecordList != null && wscRecordList.size() > 0) {

					// 循环记录单条运单刷卡数据
					for (WSCWayBillEntity record : wscRecordList) {
						if (record != null) {
                            WSCWayBillEntity wscWayBillInfo = wscWayBillManageDao.getWayBillInfoByWSCItemId(record.getWscItemId());
                            if (wscWayBillInfo.getWaitSwipeAmount() != record.getAlreadySwipeAmount()){
                                WSCEntity entity = new WSCEntity();
                                BeanUtils.copyProperties(record,entity);
                                entity.setAlreadySwipeAmount(record.getAlreadySwipeAmount());
                                entity.setWaitSwipeAmount(record.getAlreadySwipeAmount());
                                wscManageService.changeWayBill(entity);
                                continue;
                            }
							// 1.更新T+0报表及其明细   注：PDA刷卡成功后调用T+0接口仅插入T+0&T+0明细初始数据,T+0明细本次刷卡金额、T+0已使用金额、T+0未使用金额需要此处更新 。
							try {

								//根据运单号和交易流水号获取T+0明细数据 , T+0明细表中 "INVOICE_TYPE" 字段使用 W1、W2 区分待刷卡数据和结清货款数据.{W1:待刷卡  W2:结清货款}
								PosCardDetailEntity t0Detail = wscWayBillManageDao.queryT0DetailByWayBillNoAndSerialNo(
										record.getWayBillNo(), record.getSerialNo(), "W1");

								//根据交易流水号获取T+0数据
								PosCardEntity t0 = wscWayBillManageDao.queryT0BySerialNo(record.getSerialNo());

								// 验证数据
								if (t0Detail == null || t0 == null) {
									// 设置备注
									String errorMsg = "WSC-记录刷卡结果时更新T+0失败,未查询到对应的明细数据或T+0数据,{运单号:".concat(record.getWayBillNo())
											.concat(",交易流水号:").concat(record.getSerialNo()).concat("}");
									record.setNotes(StringUtil.isNUllOrWhiteSpace(record.getNotes()) ? errorMsg : record.getNotes().concat(
											errorMsg));
								} else {

									//----------------------------------------------------------------------------------------------
									// NOTE：
									//      1.更新T+0明细及T+0数据时，先查询待刷卡数据并判断待刷卡数据的支付状态，主要是防止重复更新T+0明细数据
									//----------------------------------------------------------------------------------------------

									// 查询被刷卡的待刷卡运单数据
									//WSCWayBillEntity wscWayBillInfo = wscWayBillManageDao.getWayBillInfoByWSCItemId(record.getWscItemId());

									// 未查询到待刷卡数据
									if (wscWayBillInfo == null) {
										throw new SettlementException("WSC-记录刷卡结果,原待刷卡运单数据不存在,运单号：" + record.getWayBillNo() + ",数据条目编号："
												+ record.getWscItemId());
									}
									// 查询到待刷卡数据
									else {

										// 待刷卡数据未支付
										if ("N".equals(wscWayBillInfo.getPaymentStatus()) && wscWayBillInfo.getAlreadySwipeAmount() == 0) {

											// 更新本次刷卡金额
											t0Detail.setOccupateAmount(new BigDecimal(record.getAlreadySwipeAmount()));

											// 更新已使用金额,未使用金额sql中计算
											t0.setUsedAmount(t0.getUsedAmount().add(new BigDecimal(record.getAlreadySwipeAmount())));

											// 更新T+0数据(事务)
											wscWayBillManageService.updateT0AndT0DetailAmount(t0, t0Detail);
										}
										// 待刷卡已支付
										else {

											// 设置备注
											String errorMsg = "WSC-记录刷卡结果时更新T+0失败,待刷卡数据已支付,不能重复记录刷卡数据,{运单号:".concat(record.getWayBillNo())
													.concat(",交易流水号:").concat(record.getSerialNo()).concat("}");
											record.setNotes(StringUtil.isNUllOrWhiteSpace(record.getNotes()) ? errorMsg : record.getNotes()
													.concat(errorMsg));
										}
									}

								}

							} catch (SettlementException e) {

								// 设置备注
								String errorMsg = "WSC-记录刷卡结果时更新T+0异常,异常信息:".concat(e.getErrorCode()).concat("{运单号:")
										.concat(record.getWayBillNo()).concat(",交易流水号:").concat(record.getSerialNo()).concat("}");
								record.setNotes(StringUtil.isNUllOrWhiteSpace(record.getNotes()) ? errorMsg : record.getNotes().concat(
										errorMsg));

								// 打印异常信息
								System.out.println("WSC-接口recordSwipeCardResultBatch异常:".concat(e.getErrorCode()));

							} catch (Exception e) {

								// 设置备注
								String errorMsg = "WSC-记录刷卡结果时更新T+0异常.{运单号:".concat(record.getWayBillNo()).concat(",交易流水号:")
										.concat(record.getSerialNo()).concat("}");
								record.setNotes(StringUtil.isNUllOrWhiteSpace(record.getNotes()) ? errorMsg : record.getNotes().concat(
										errorMsg));

								// 打印异常信息
								System.out.println("WSC-接口recordSwipeCardResultBatch异常:".concat(StringUtil.getExceptionStackTrace(e)));
							}

							// 2.更新待刷卡运单数据
							try {

								// 记录单条运单刷卡信息
								wscWayBillManageService.recordSwipeCardResult(record);

								// 记录数据成功，保存记录成功的运单号
								succeedRecordWayBillNoList.add(record.getWayBillNo());

							} catch (SettlementException e) {

								// 记录失败运单号及失败原因
								failRecordWayBillNoList.add(record.getWayBillNo());
								failRecordMsgMap.put(record.getWayBillNo(), e.getErrorCode());

								// 打印异常信息
								System.out.println("WSC-接口recordSwipeCardResultBatch异常:".concat(e.getErrorCode()));

							} catch (Exception e) {

								// 记录失败运单号及失败原因
								failRecordWayBillNoList.add(record.getWayBillNo());
								failRecordMsgMap.put(record.getWayBillNo(), "WSC-运单刷卡结果记录异常,异常信息:" + e.getMessage());

								// 打印异常信息
								System.out.println("WSC-接口recordSwipeCardResultBatch异常:".concat(StringUtil.getExceptionStackTrace(e)));
							}
						}
					}
				}
			}
		}

		// 返回结果
		result.setFailRecordWayBillNoList(failRecordWayBillNoList);
		result.setFailRecordMsgMap(failRecordMsgMap);
		result.setSucceedRecordWayBillNoList(succeedRecordWayBillNoList);

		if (failRecordWayBillNoList != null && failRecordWayBillNoList.size() > 0) {
			result.setResultCode("0");
			result.setResultDesc("WSC-部分运单刷卡信息记录失败，失败运单号及失败原因见failRecordWayBillNoList和failRecordMsgMap");
			System.out.println("WSC-部分运单刷卡信息记录失败,失败运单号及原因:".concat(failRecordMsgMap.toString())); // 输出失败运单及原因到控制台
		} else {
			result.setResultCode("1");
			result.setResultDesc("WSC-刷卡信息记录成功.");
		}

		// 记录日志
		LOGGER.info("WSC-记录刷卡结果(批量)-end-".concat(logGuidStr));

		return result;
	}

	//-----------------------------------------------------------------------------
	// 描述: 运单现付金额减少
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	   1.运单现付金额更改,"现付金额"减少 (现付金额 < 运单已刷卡金额总和) 
	//       操作:
	//         a.按刷卡时间先后顺序,从T+0明细中释放减少的现付金额,直到减少的现付金额释放完
	//           为止,释放的金额添加到T+0报表的未使用金额中,同时T+0报表的已使用金额相应减少.
	//         b.更新运单号的未支付待刷卡数据为无效
	// 修改记录:
	// 	  v1.0 - 2016-4-9 14:22:43 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public void swipedBillAmountReduce(WSCWayBillParamEntity params) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-运单现付金额减少-begin-".concat(logGuidStr));

		// 返回信息
		String returnMsg = "";

		try {
			// 参数对象验证
			if (params == null) {
				returnMsg = "WSC-运单现付金额减少-参数对象不能为空！";
				throw new SettlementException(returnMsg);
			}
			// 验证运单号
			if (StringUtil.isNUllOrWhiteSpace(params.getWayBillNo())) {
				returnMsg = "WSC-运单现付金额减少-运单号不能为空！";
				throw new SettlementException(returnMsg);
			}
			// 验证减少金额
			if (params.getBillReduceAmount() <= 0) {
				returnMsg = "WSC-运单现付金额减少-运单减少金额取绝对值,必须大于0！";
				throw new SettlementException(returnMsg);
			}

			//---------------------------------------------------------------------------------
			//  更新运单号的未支付待刷卡数据为无效
			//---------------------------------------------------------------------------------
			//	NOTE:
			//		a.避免运单作废或已为非银行卡单仍存在未刷卡数据
			//      b.未支付的待刷卡数据可能存在、也可能不存在, 直接更新即可.
			//---------------------------------------------------------------------------------
			try {

				// 声明待刷卡数据对象, 将待修改字段更新到对象中
				WSCWayBillParamEntity deleteParams = new WSCWayBillParamEntity();

				// 设置运单号
				deleteParams.setWayBillNo(params.getWayBillNo());

				// 设置修改人编号
				deleteParams.setModifyUserCode(params.getModifyUserCode());

				// 设置修改人名称
				deleteParams.setModifyUserName(params.getModifyUserName());

				// 设置备注
				returnMsg = "WSC-运单现付金额减少时,更新运单号的未支付待刷卡数据为无效.";
				deleteParams.setNotes(StringUtil.isNUllOrWhiteSpace(params.getNotes()) ? returnMsg : params.getNotes().concat(returnMsg));

				// 更新数据
				wscWayBillManageDao.invalidUnPayMentWSCDataByWayBillNo(deleteParams);

			} catch (Exception e) {

				// 打印&抛出异常
				returnMsg = "WSC-运单现付金额减少,新运单号的未支付待刷卡数据为无效异常.";
				LOGGER.info(returnMsg);
				e.printStackTrace();
				throw new SettlementException(returnMsg);
			}

			//---------------------------------------------------------------------------------
			//  从T+0明细中释放减少的预付金额
			//---------------------------------------------------------------------------------
			//	NOTE:
			//		a.未查询到T+0明细数据,也属正常的业务场景,开单运单未支付即发更改,且预付金额减少
			//---------------------------------------------------------------------------------
			// 运单类型,T+0明细表中 "INVOICE_TYPE" 字段使用 W1、W2 区分待刷卡数据和结清货款数据.{W1:待刷卡  W2:结清货款}
			String invoiceType = "W1";

			// 根据运单号查询所有有效T+0明细数据,并且数据按创建时间正序排序
			List<PosCardDetailEntity> t0DetailList = wscWayBillManageDao.queryT0DetailByWayBillNo(params.getWayBillNo(), invoiceType);

			// 未查询到运单号对应的T+0明细信息
			if (t0DetailList == null || t0DetailList.size() <= 0) {
				returnMsg = "WSC-运单现付金额减少-未查询到运单号对应的T+0明细信息！";
				throw new SettlementException(returnMsg);
			}

			// 运单减少金额
			BigDecimal reduceAmount = new BigDecimal(params.getBillReduceAmount());

			// 获取运单已刷卡金额总和, T+0明细表中 "INVOICE_TYPE" 字段使用 W1、W2 区分待刷卡数据和结清货款数据.{W1:待刷卡  W2:结清货款}
			Map<String, BigDecimal> amountMap = wscWayBillManageDao.getBillAmountAndSumOccupateAmount(params.getWayBillNo(), "W1");

			// 判断运单减少金额是否大于运单已刷卡金额
			if (amountMap == null || amountMap.size() <= 0) {
				returnMsg = "WSC-运单现付金额减少-未查询到运单已刷卡金额总和！";
				throw new SettlementException(returnMsg);
			}
			int compareResult = amountMap.get("SUMOCCUPATEAMOUNT").compareTo(reduceAmount);
			if (compareResult == -1) {
				returnMsg = "WSC-运单现付金额减少-运单减少金额不能大于运单已刷卡金额！";
				throw new SettlementException(returnMsg);
			}

			// 待释放金额 = 运单减少金额 - 已释放金额, 待释放金额初始值 = 运单减少金额
			BigDecimal waitReleaseAmount = reduceAmount;

			// 声明HashMap保存明细集合中所有的交易流水号，及对应的更新后T0实体
			HashMap<String, PosCardEntity> updateT0Map = new HashMap<String, PosCardEntity>();

			// 声明List保存待更新T0明细数据实体集合
			List<PosCardDetailEntity> detailEntityParamList = new ArrayList<PosCardDetailEntity>();

			// 声明List保存处理中发生异常的明细编号
			List<String> failedReleaseDetailId = new ArrayList<String>();

			// 循环处理T+0明细数据
			for (PosCardDetailEntity detailEntity : t0DetailList) {
				try {

					// 交易流水号在Map中不存在
					if (!updateT0Map.containsKey(detailEntity.getTradeSerialNo())) {

						// 根据明细数据的交易流水号获取T0报表数据
						PosCardEntity t0Entity = wscWayBillManageDao.queryT0BySerialNo(detailEntity.getTradeSerialNo());

						// 整合update参数
						PosCardEntity t0EntityParam = new PosCardEntity();
						// 设置已使用流水号金额、未使用流水号金额
						t0EntityParam.setUsedAmount(t0Entity.getUsedAmount());
						t0EntityParam.setUnUsedAmount(t0Entity.getUnUsedAmount());
						// 设置修改人编号、名称
						t0EntityParam.setModifyUserCode(params.getModifyUserCode());
						t0EntityParam.setModifyUser(params.getModifyUserName());
						// 设置备注
						t0EntityParam.setNotes("WSC-释放明细金额.");
						// 设置交易流水号
						t0EntityParam.setTradeSerialNo(detailEntity.getTradeSerialNo());

						// 将交易流水号和初始的待更新T0实体装载入Map
						updateT0Map.put(detailEntity.getTradeSerialNo(), t0EntityParam);
					}

					// ***T0明细释放金额***
					// 待更新的明细实体
					PosCardDetailEntity detailEntityParam = new PosCardDetailEntity();

					// 复制明细ID,用作更新数据的where条件
					detailEntityParam.setId(detailEntity.getId());

					// 设置明细总金额 = 明细总金额 - 运单减少金额, PS: 运单总金额变化时, 由接送货统一更新
					// detailEntityParam.setAmount(detailEntity.getAmount().subtract(reduceAmount));

					// 待释放金额与0的比较结果
					int comResult = waitReleaseAmount.compareTo(BigDecimal.ZERO);

					// 本次释放金额
					BigDecimal releaseAmount = new BigDecimal(0);

					// 待释放金额<=0, 仅需修改T0明细的总金额
					if (comResult == 0 || comResult == -1) {

						// 将明细中原 本次刷卡、未核销 金额保存到待更新T0明细实体中
						detailEntityParam.setOccupateAmount(detailEntity.getOccupateAmount());

						// 未核销金额字段后续会删除, 不再做更新
						//detailEntityParam.setUnVerifyAmount(detailEntity.getUnVerifyAmount());

						// 将待更新T0明细实体填充到List中
						detailEntityParamList.add(detailEntityParam);
					}
					// 待释放金额>0, 需要修改T0明细的总金额、已刷卡金额、未核销金额
					else {
						// a.待释放金额 > 明细已刷卡金额
						if (detailEntity.getOccupateAmount().compareTo(waitReleaseAmount) == -1) {

							// 设置已刷卡金额为0
							detailEntityParam.setOccupateAmount(new BigDecimal(0));

							// 待释放金额 > 明细已刷卡金额, 设置 待释放金额 = 待释放金额 - 明细已刷卡金额 
							waitReleaseAmount = waitReleaseAmount.subtract(detailEntity.getOccupateAmount());

							// 设置本次释放金额为明细刷卡金额
							releaseAmount = detailEntity.getOccupateAmount();
						}
						// b.待释放金额 <= 明细已刷卡金额
						else {

							// 设置明细已刷卡金额 = 原明细刷卡金额 - 待释放金额
							detailEntityParam.setOccupateAmount(detailEntity.getOccupateAmount().subtract(waitReleaseAmount));

							// 设置本次释放金额为待释放金额
							releaseAmount = waitReleaseAmount;

							// 重置待释放金额,明细能完全释放运单减少金额,设置待释放金额为0
							waitReleaseAmount = new BigDecimal(0);
						}

						// 设置未核销金额 = 总金额 - 已刷卡金额
						// detailEntityParam.setUnVerifyAmount(detailEntityParam.getAmount().subtract(detailEntityParam.getOccupateAmount()));

						// 将待更新T0明细实体填充到List中
						detailEntityParamList.add(detailEntityParam);
					}

					// ***更新释放金额到T0更新实体中***
					// 从Map中取出待更新T0实体,将明细释放金额追加进去
					PosCardEntity newT0EntityParam = updateT0Map.get(detailEntity.getTradeSerialNo());
					if (newT0EntityParam != null) {
						// 更新使用金额 = 原使用金额 - 本次释放金额
						newT0EntityParam.setUsedAmount(newT0EntityParam.getUsedAmount().subtract(releaseAmount));
						// 更新未使用金额 = 原未使用金额 + 本次释放金额
						newT0EntityParam.setUnUsedAmount(newT0EntityParam.getUnUsedAmount().add(releaseAmount));
					}

					// 将更新后的待更新T0实体覆盖到Map中
					updateT0Map.put(detailEntity.getTradeSerialNo(), newT0EntityParam);

				} catch (Exception e) {
					// 将释放金额失败的明细id保存到集合
					failedReleaseDetailId.add(detailEntity.getId());
				}
			}

			//------------------------------------------------------------------------------
			// 事务: T0明细修改运单总金额&释放运单减少金额(批量) & 明细释放金额更新到T0数据中(批量)
			//------------------------------------------------------------------------------
			// NOTE:
			// 	 已刷卡运单金额减少时,需要对T+0做以下几个操作:
			//  	1.运单对应的T+0明细数据按刷卡时间升序排列,然后按此顺序对运单减少金额进行释放.
			//		2.T+0明细释放的金额更新到对应的(交易流水号关联)T+0报表数据中.
			//------------------------------------------------------------------------------
			wscWayBillManageService.releaseAmountWhenSwipedBillReduceAmountBatch(detailEntityParamList, updateT0Map);

			// 判断处理结果并返回
			returnMsg = failedReleaseDetailId.size() > 0 ? "WSC-运单现付金额减少时,释放T+0明细金额失败,失败明细条数:".concat(Integer
					.toString(failedReleaseDetailId.size())) : "WSC-运单现付金额减少时,释放T+0明细金额成功.";
			LOGGER.info(returnMsg);

		} catch (SettlementException e) {

			// 打印&抛出异常
			returnMsg = "WSC-运单现付金额减少,释放T+0明细金额失败,失败原因:" + e.getErrorCode();
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);

		} catch (Exception e) {

			// 打印&抛出异常
			returnMsg = "WSC-已刷卡运单金额减少,释放T+0明细金额异常.";
			LOGGER.info(returnMsg);
			System.out.println("WSC-接口swipedBillAmountReduce异常:".concat(StringUtil.getExceptionStackTrace(e)));
			throw e;
		}

		// 记录日志
		LOGGER.info("WSC-已刷卡运单金额减少-end-".concat(logGuidStr));
	}

	//-----------------------------------------------------------------------------
	// 描述: 更新运单下未支付待刷卡数据为无效
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	   1.未支付运单发更改, 预付金额减少
	// 
	// 修改记录:
	// 	  v1.0 - 2016-4-10 14:42:35 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public void invalidUnPayMentWSCDataByWayBillNo(String wayBillNo) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-运单更改,预付金额不变,更新运单下未支付待刷卡数据为无效-begin-".concat(logGuidStr));

		// 返回消息
		String returnMsg = "";

		// 验证运单号
		if (StringUtil.isNUllOrWhiteSpace(wayBillNo)) {
			returnMsg = "WSC-运单更改,预付金额不变,更新运单下未支付待刷卡数据为无效-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-运单更改,预付金额不变,更新运单下未支付待刷卡数据为无效-processing-".concat(logGuidStr).concat("{运单号:").concat(wayBillNo).concat("}"));

		// 声明待刷卡数据对象, 将待修改字段更新到对象中
		WSCWayBillParamEntity deleteParams = new WSCWayBillParamEntity();

		// 设置运单号
		deleteParams.setWayBillNo(wayBillNo);

		// 设置备注
		returnMsg = "WSC-运单更改,预付金额不变,更新运单下未支付待刷卡数据为无效.";
		deleteParams.setNotes(returnMsg);

		// 更新数据
		wscWayBillManageDao.invalidUnPayMentWSCDataByWayBillNo(deleteParams);

		// 记录日志
		LOGGER.info("WSC-运单更改,预付金额不变,更新运单下未支付待刷卡数据为无效-end-".concat(logGuidStr));
	}

	//-----------------------------------------------------------------------------
	// 描述: 查询运单已刷卡金额总和
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	   1.运单发更改,接送货据此判断预付金额是增加、减少、还是不变, 逻辑如下:
	//       |-预付增加: 预付金额 > 运单已刷卡总金额
	//       |-预付减少: 预付金额 < 运单已刷卡总金额
	//       |-预付不变: 预付金额 = 运单已刷卡总金额
	// 
	// 修改记录:
	// 	  v1.0 - 2016-4-10 14:42:35 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public BigDecimal getTotalSwipedAmountByWayBillNo(String wayBillNo) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-查询运单已刷卡金额总和-begin-".concat(logGuidStr));

		// 返回消息
		String returnMsg = "";

		// 验证运单号
		if (StringUtil.isNUllOrWhiteSpace(wayBillNo)) {
			returnMsg = "WSC-查询运单已刷卡金额总和-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-查询运单已刷卡金额总和-processing-".concat(logGuidStr).concat("{运单号:").concat(wayBillNo).concat("}"));

		// 查询运单已刷卡金额总和 & 运单总金额  {刷卡总金额key:SUMOCCUPATEAMOUNT ,运单总金额key:BILLAMOUNT}
		// T+0明细表中 "INVOICE_TYPE" 字段使用 W1、W2 区分待刷卡数据和结清货款数据.{W1:待刷卡  W2:结清货款}
		Map<String, BigDecimal> amountMap = wscWayBillManageDao.getBillAmountAndSumOccupateAmount(wayBillNo, "W1");
		if (amountMap == null || amountMap.size() <= 0) {
			returnMsg = "WSC-未查询运单已刷卡金额总和！";
			LOGGER.info(returnMsg);
			return new BigDecimal(0);
		}

		// 运单已刷卡金额总和
		BigDecimal totalSwipedAmount = amountMap.get("SUMOCCUPATEAMOUNT");

		// 未查询到数据
		if (totalSwipedAmount == null) {
			returnMsg = "WSC-未查询运单已刷卡金额总和！";
			LOGGER.info(returnMsg);
			return new BigDecimal(0);
		}

		// 已刷卡金额不能小于0
		int compareResult = totalSwipedAmount.compareTo(BigDecimal.ZERO);
		if (compareResult == -1) {
			returnMsg = "WSC-已刷卡金额总和不能小于0！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-查询运单已刷卡金额总和-end-".concat(logGuidStr));

		// 返回结果
		return totalSwipedAmount;
	}

	//-----------------------------------------------------------------------------
	// 描述: 同步运单更改字段到待刷卡和T+0明细表
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	   1.运单发更改,"运单总金额"、"发货人编号"、"发货人名称"改变时调用,接口操作如下:
	//       a.更新运单号下所有有效待刷卡数据的"运单总金额"、"发货人编号"、"发货人名称"字段
	// 		 b.更新运单号下所有T+0明细数据的"运单总金额"字段
	//       
	// 修改记录:
	// 	  v1.0 - 2016-4-11 15:09:42 - 309613  
	//-----------------------------------------------------------------------------
	@Override
	public void syncBillInfo2WscAndT0(WSCWayBillParamEntity params) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-查询运单已刷卡金额总和-begin-".concat(logGuidStr));

		// 返回消息
		String returnMsg = "";

		// 验证参数对象
		if (params == null) {
			returnMsg = "WSC-同步运单更改字段到待刷卡和T+0明细表-参数对象不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证运单号
		if (StringUtil.isNUllOrWhiteSpace(params.getWayBillNo())) {
			returnMsg = "WSC-同步运单更改字段到待刷卡和T+0明细表-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		// 验证运单总金额
		if (params.getWayBillAmount() < 0) {
			returnMsg = "WSC-同步运单更改字段到待刷卡和T+0明细表-运单总金额不能小于0！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		/*
		// 验证发货联系人编号
		if (StringUtil.isNUllOrWhiteSpace(params.getSendCustomerCode())) {
			returnMsg = "WSC-同步运单更改字段到待刷卡和T+0明细表-发货人编号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}
		*/
		// 验证发货联系人名称
		if (StringUtil.isNUllOrWhiteSpace(params.getSendCustomerName())) {
			returnMsg = "WSC-同步运单更改字段到待刷卡和T+0明细表-发货联系人名称不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 声明待刷卡数据对象, 将待修改字段更新到对象中
		PosCardDetailEntity syncParamOfT0Detail = new PosCardDetailEntity();
		// 设置单据编号
		syncParamOfT0Detail.setInvoiceNo(params.getWayBillNo());
		// 设置单据类型, T+0明细表中 "INVOICE_TYPE" 字段使用 W1、W2 区分待刷卡数据和结清货款数据.{W1:待刷卡  W2:结清货款}
		syncParamOfT0Detail.setInvoiceType("W1");
		// 设置运单总金额
		syncParamOfT0Detail.setAmount(new BigDecimal(params.getWayBillAmount()));

		// 声明待刷卡数据对象, 将待修改字段更新到对象中
		WSCWayBillParamEntity syncParamOfWsc = new WSCWayBillParamEntity();
		// 设置运单号
		syncParamOfWsc.setWayBillNo(params.getWayBillNo());
		// 设置运单总金额
		syncParamOfWsc.setWayBillAmount(params.getWayBillAmount());
		// 设置发货人编号
		syncParamOfWsc.setSendCustomerCode(params.getSendCustomerCode());
		// 设置发货人名称
		syncParamOfWsc.setSendCustomerName(params.getSendCustomerName());

		//---------------------------------------------
		// 1.更新T+0明细
		//---------------------------------------------
		try {
			wscWayBillManageDao.synchBillInfo2T0(syncParamOfT0Detail);
		} catch (Exception e) {

			// 获取打屏信息的标识, 方便日后查询异常
			String logFlag = StringUtil.getLogFlag();

			// 打印异常信息
			System.out.println("WSC-同步运单更改字段到待刷卡和T+0明细表-更新T+0明细数据异常:".concat(logFlag).concat(StringUtil.getExceptionStackTrace(e)));
			syncParamOfWsc.setNotes("WSC-同步运单更改字段到待刷卡和T+0明细表-更新T+0明细数据异常-".concat(logFlag));
		}

		//---------------------------------------------
		// 2.更新待刷卡数据
		//---------------------------------------------
		// 更新数据
		wscWayBillManageDao.synchBillInfo2Wsc(syncParamOfWsc);

		// 记录日志
		LOGGER.info("WSC-运单更改,预付金额不变,更新运单下未支付待刷卡数据为无效-end-".concat(logGuidStr));

	}

	//-----------------------------------------------------------------------------
	// 描述: 判断运单是否已刷过卡
	// 类型: 外部调用
	// 作者: panshiqi 309613
	// 业务场景：
	//	   1.图片开单、集中开单有收入部门,已刷过卡的运单收入部门不能改变,接送货运单发更改时,
	//		 调用此接口查询发更改运单是否已刷过卡.
	//       
	// 修改记录:
	// 	  v1.0 - 2016-4-18 10:10:13 - 309613 
	//-----------------------------------------------------------------------------
	@Override
	public boolean wayBillIsAlreadySwiped(String wayBillNo) throws Exception {

		// 标识日志的guid
		String logGuidStr = StringUtil.getLogGuid();

		// 记录日志
		LOGGER.info("WSC-判断运单是否已刷过卡-begin-".concat(logGuidStr));

		// 返回消息
		String returnMsg = "";

		// 验证运单号
		if (StringUtil.isNUllOrWhiteSpace(wayBillNo)) {
			returnMsg = "WSC-判断运单是否已刷过卡-运单号不能为空！";
			LOGGER.info(returnMsg);
			throw new SettlementException(returnMsg);
		}

		// 记录日志
		LOGGER.info("WSC-判断运单是否已刷过卡-processing-".concat(logGuidStr).concat("{运单号:").concat(wayBillNo).concat("}"));

		// 根据运单号获取运单有效刷卡记录次数
		int swipedCount = wscWayBillManageDao.getSwipedCountByWayBillNo(wayBillNo);

		// 处理查询结果
		if (swipedCount <= 0) {
			returnMsg = "WSC-判断运单是否已刷过卡-运单未刷过卡！";
			LOGGER.info(returnMsg);
			return false;
		} else {
			returnMsg = "WSC-判断运单是否已刷过卡-运单" + wayBillNo + "已刷过" + swipedCount + "次卡.";
			LOGGER.info(returnMsg);
			return true;
		}
	}

	/**
	 * In - 记录刷卡结果,更新待刷卡已刷卡金额
	 */
	@Override
	public int recordSwipeCardResult(WSCWayBillEntity params) throws Exception {

		// 验证参数对象
		if (params == null) {
			throw new SettlementException("WSC-记录刷卡结果-刷卡结果数据不能为空！");
		}
		// 待刷卡运单数据编号验证
		if (StringUtil.isNUllOrWhiteSpace(params.getWscItemId())) {
			throw new SettlementException("WSC-记录刷卡结果-数据编号不能为空！");
		}
		// 刷卡部门编号验证
		if (StringUtil.isNUllOrWhiteSpace(params.getSwipeCardOrgCode())) {
			throw new SettlementException("WSC-记录刷卡结果-刷卡部门编号不能为空！");
		}
		// 刷卡部门名称验证
		if (StringUtil.isNUllOrWhiteSpace(params.getSwipeCardOrgName())) {
			throw new SettlementException("WSC-记录刷卡结果-刷卡部门名称不能为空！");
		}
		// 刷卡操作人编号验证
		if (StringUtil.isNUllOrWhiteSpace(params.getSwipeCardUserCode())) {
			throw new SettlementException("WSC-记录刷卡结果-刷卡操作人编号不能为空！");
		}
		// 刷卡操作人名称验证
		if (StringUtil.isNUllOrWhiteSpace(params.getSwipeCardUserName())) {
			throw new SettlementException("WSC-记录刷卡结果-刷卡操作人名称不能为空！");
		}
		// 交易流水号验证
		if (StringUtil.isNUllOrWhiteSpace(params.getSerialNo())) {
			throw new SettlementException("WSC-记录刷卡结果-交易流水号不能为空！");
		}
		// 刷卡金额验证
		if (params.getAlreadySwipeAmount() <= 0) {
			throw new SettlementException("WSC-记录刷卡结果-刷卡金额不能小于等于0！");
		}

		// 查询被刷卡的待刷卡运单数据
		WSCWayBillEntity wscWayBillInfo = wscWayBillManageDao.getWayBillInfoByWSCItemId(params.getWscItemId());

		// 未查询到待刷卡数据
		if (wscWayBillInfo == null) {

			throw new SettlementException("WSC-记录刷卡结果,原待刷卡运单数据不存在,运单号：" + params.getWayBillNo() + ",数据条目编号：" + params.getWscItemId());
		}
		// 查询到待刷卡数据
		else {

			// 待刷卡数据已支付
			if ("Y".equals(wscWayBillInfo.getPaymentStatus())) {
				throw new SettlementException("WSC-记录刷卡结果,原待刷卡运单数据已支付,运单号：" + params.getWayBillNo() + ",数据条目编号：" + params.getWscItemId());
			}

			// 待刷卡数据未支付, 代码才会执行下去, 进而更新代刷卡数据
		}

		// 更新待刷卡运单数据,记录刷卡信息
		WSCWayBillParamEntity modifyParams = new WSCWayBillParamEntity();

		// 数据标识
		modifyParams.setId(params.getWscItemId());

		// 设置修改人编号
		modifyParams.setModifyUserCode(params.getSwipeCardUserCode());

		// 设置修改人名称
		modifyParams.setModifyUserName(params.getSwipeCardUserName());

		// 设置备注
		String notesMsg = StringUtil.isNUllOrWhiteSpace(params.getNotes()) ? "WSC-记录刷卡结果时更新待刷卡运单数据.".concat(params.getPointParamJson())
				: "WSC-记录刷卡结果时更新待刷卡运单数据.".concat(params.getPointParamJson()).concat(params.getNotes());
		modifyParams.setNotes(notesMsg);

		// 设置交易流水号
		modifyParams.setSerialNo(params.getSerialNo());

		// 设置已刷卡金额
		modifyParams.setAlreadySwipeAmount(params.getAlreadySwipeAmount());

		// 设置刷卡部门编号
		modifyParams.setSwipeCardOrgCode(params.getSwipeCardOrgCode());

		// 设置刷卡部门名称
		modifyParams.setSwipeCardOrgName(params.getSwipeCardOrgName());

		// 设置刷卡时间
		modifyParams.setSwipeCardTime(params.getSwipeCardTime());

		// 设置刷卡人编号
		modifyParams.setSwipeCardUserCode(params.getSwipeCardUserCode());

		// 设置刷卡人名称
		modifyParams.setSwipeCardUserName(params.getSwipeCardUserName());

		// 计算支付状态：{待刷卡金额-已刷卡金额=0时为已支付，否则为未支付}
		double waitSwipeAmount = wscWayBillInfo.getWaitSwipeAmount();
		if (params.getAlreadySwipeAmount() >= waitSwipeAmount) {
			modifyParams.setPaymentStatus("Y");
		} else {
			modifyParams.setPaymentStatus("N");
		}

		// 更新待刷卡数据
		int lines = wscWayBillManageDao.updateWSCWayBillByItemID(modifyParams);
		if (lines <= 0) {
			throw new SettlementException("WSC-记录刷卡结果时更新待刷卡数据失败,数据库影响行数为0.");
		}

		// 返回结果
		return lines;
	}

	/**
	 * In - 释放T+0明细金额到T+0报表(单条)(事务)-[暂未使用]
	 * 步骤：
	 * 	   1.置T+0明细数据为无效
	 *     2.释放置无效的T+0明细本次刷卡金额到T+0报表中
	 */
	@Transactional
	@Override
	public int releaseAmountWhenT0DetailInvalid(PosCardDetailEntity t0Detailentity, PosCardEntity t0Entity) throws Exception {

		try {

			// 验证参数对象
			if (t0Detailentity == null || t0Entity == null) {
				throw new SettlementException("WSC-参数对象不能为空！");
			}
			// T+0明细数据ID验证
			if (StringUtil.isNUllOrWhiteSpace(t0Detailentity.getId())) {
				throw new SettlementException("WSC-明细ID不能为空！");
			}
			// T+0已使用金额验证,已使用金额不为空且>=0
			if (t0Entity.getUsedAmount() == null || t0Entity.getUsedAmount().compareTo(BigDecimal.ZERO) == -1) {
				throw new SettlementException("WSC-已使用金额为空或小于0！");
			}
			// T+0未使用金额验证
			if (t0Entity.getUnUsedAmount() == null || t0Entity.getUnUsedAmount().compareTo(BigDecimal.ZERO) == -1) {
				throw new SettlementException("WSC-未使用金额不能为空或小于0！");
			}

			// 1.置T+0明细数据为无效
			int lines = wscWayBillManageDao.invalidT0DetailByID(t0Detailentity.getId());
			if (lines <= 0) {
				throw new SettlementException("WSC-已刷卡运单作废时,设置T+0明细数据无效失败,更新明细数据条数为0.");
			}

			// 2.释放置无效的T+0明细本次刷卡金额到T+0报表中
			// 重新计算T+0使用金额、未使用金额
			BigDecimal usedAmount = t0Entity.getUsedAmount();
			BigDecimal unUsedAmount = t0Entity.getUnUsedAmount();
			BigDecimal occupateAmount = t0Detailentity.getOccupateAmount();

			// 新T0使用流水号金额 = T0使用流水号金额 - T0明细本次已刷卡金额
			t0Entity.setUsedAmount(usedAmount.subtract(occupateAmount));

			// 新T0未使用流水号金额 = T0未使用流水号金额 + T0明细本次已刷卡金额
			t0Entity.setUnUsedAmount(unUsedAmount.add(occupateAmount));

			// 更新T+0数据
			lines = wscWayBillManageDao.releaseDetailAmount2T0(t0Entity);
			if (lines <= 0) {
				throw new SettlementException("WSC-已刷卡运单作废时,更新T+0流水号使用金额和未使用失败,更新T+0数据条数为0.");
			}

			// 返回更新数据行数
			return lines;

		} catch (SettlementException e) {
			throw e;
		} catch (Exception e) {
			throw new SettlementException("WSC-已刷卡运单作废时更新T+0及其明细数据异常,异常信息：" + e.getMessage());
		}
	}

	/**
	 * In - 释放T+0明细金额到T+0报表(批量)(事务)
	 * 步骤：
	 * 	   1.批量修改T+0明细数据
	 *     2.批量更新T+0数据的已使用金额、未使用金额
	 */
	@Transactional
	@Override
	public void releaseAmountWhenT0DetailInvalidBatch(List<String> idList, Map<String, PosCardEntity> paramsMap, String... operateType)
			throws Exception {

		try {

			// 验证参数对象
			if (idList == null || idList.size() <= 0 || paramsMap == null || paramsMap.size() <= 0) {
				throw new SettlementException("FOSS-WSC-参数对象不能为空！");
			}

			//-------------------------------------------------------------------
			// 1.批量修改T+0明细数据
			//   a.运单作废: 删除运单对应的T+0明细数据为删除
			//   b.付款方式更改为非银行卡: 重置运单对应的T+0明细本次数据金额为0,但不删除
			//-------------------------------------------------------------------
			if (operateType != null && "2".equals(operateType)) {

				// 1.批量更新T+0明细数据本次刷卡金额为0
				wscWayBillManageDao.resetT0DetailOccAmountByIDBatch(idList);
			} else {

				// 1.批量更新T+0明细数据状态为删除
				wscWayBillManageDao.invalidT0DetailByIDBatch(idList);
			}

			// 2.批量更新T+0数据的已使用金额、未使用金额
			wscWayBillManageDao.releaseDetailAmount2T0Batch(paramsMap);

		} catch (SettlementException e) {
			throw e;
		} catch (Exception e) {
			throw new SettlementException("FOSS-WSC-已刷卡运单作废时更新T+0及其明细数据异常,异常信息：" + e.getMessage());
		}
	}

	/**
	 * In - 部分释放T+0明细金额到T+0报表(批量)(事务)
	 * 步骤：
	 * 	   1.T+0明细释放金额(批量)
	 *     2.T+0报表已使用金额、未使用金额修改(批量)
	 */
	@Transactional
	@Override
	public void releaseAmountWhenSwipedBillReduceAmountBatch(List<PosCardDetailEntity> t0DetailEntityList,
			Map<String, PosCardEntity> t0EntityMap) throws Exception {

		try {

			// 验证参数对象
			if (t0DetailEntityList == null || t0DetailEntityList.size() <= 0 || t0EntityMap == null || t0EntityMap.size() <= 0) {
				throw new SettlementException("FOSS-WSC-参数对象不能为空！");
			}

			// 1.T+0明细释放金额(批量)
			wscWayBillManageDao.updateT0DetailBatch(t0DetailEntityList);

			// 2.T+0报表已使用金额、未使用金额修改(批量)
			wscWayBillManageDao.releaseDetailAmount2T0Batch(t0EntityMap);

		} catch (SettlementException e) {
			throw e;
		} catch (Exception e) {
			throw new SettlementException("FOSS-WSC-已刷卡运单金额减少时更新T+0及其明细数据异常,异常信息：" + e.getMessage());
		}
	}

	/**
	 * In - 更新T+0报表&T+0明细相关金额字段(单条)(事务)
	 * 步骤：
	 * 	   1.更新T+0明细本次刷卡金额
	 *     2.更新T+0已使用金额、未使用金额
	 */
	@Transactional
	@Override
	public int updateT0AndT0DetailAmount(PosCardEntity t0, PosCardDetailEntity t0Detail) throws Exception {
		try {
			// 验证参数对象
			if (t0 == null || t0Detail == null) {
				throw new SettlementException("FOSS-WSC-参数对象不能为空！");
			}

			// 1.更新T+0明细本次刷卡金额
			int lines = wscWayBillManageDao.updateT0DetailAmount(t0Detail);
			if (lines <= 0) {
				throw new SettlementException("FOSS-WSC-记录刷卡结果时,更新T+0明细的本次刷卡金额和未核销金额失败,更新T+0明细数据条数为0.");
			}

			// 2.更新T+0已使用金额、未使用金额
			lines = wscWayBillManageDao.updateT0Amount(t0);
			if (lines <= 0) {
				throw new SettlementException("FOSS-WSC-记录刷卡结果时,更新T+0已使用金额和未使用金额,更新T+0数据条数为0.");
			}

			// 返回更新数据行数
			return lines;

		} catch (SettlementException e) {
			throw e;
		} catch (Exception e) {
			throw new SettlementException("FOSS-WSC-记录刷卡结果时,更新T+0及其明细异常,异常信息：" + e.getMessage());
		}
	}

}

/**
 * 
* @description: 字符串工具类
* @className: StringUtil
* 
* @authorCode 309613
* @date 2016年2月19日 上午10:37:05 
*
 */
class StringUtil {

	/**
	 * @description: 判断字符串是否为null或空串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (null == str || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * @description: 判断字符串是否为null或空字符串或空格字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNUllOrWhiteSpace(String str) {
		if (null == str || str.equals("") || str.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	* @description: 判断多个字符串是否为null或空字符串或空格字符串
	* @title: isNUllOrWhiteSpace
	* @authorCode 309613
	* @date 2016年2月19日 上午10:34:53 
	* @param str
	* @return
	 */
	public static boolean isNUllOrWhiteSpace(List<String> strs) {
		for (String str : strs) {
			if (isNUllOrWhiteSpace(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description: 获取Exception堆栈信息
	 * 
	 * @param e 异常对象
	 * @return 异常堆栈信息字符串
	 */
	public static String getExceptionStackTrace(Throwable e) {
		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			return str;
		} catch (Exception e2) {
			return e.getMessage();
		}
	}

	/**
	 * 
	* @description: 获取记录日志的GUID
	* @title: getLogGuid
	* @author panshiqi 309613
	* @date 2016年3月22日 下午10:32:15 
	* @return
	 */
	public static String getLogGuid() {
		try {
			UUID uuid = UUID.randomUUID();
			return uuid.toString();
		} catch (Exception e) {
			System.out.println("待刷卡模块,打印日志时生成GUID异常.");
		}
		return "";
	}

	/**
	 * 
	* @description: 获取系统当前时间字符串
	* @title: getNowTime
	* @author panshiqi 309613
	* @date 2016年4月9日 上午8:29:02 
	* @return
	 */
	public static String getNowTimeStr() {
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	* @description: 获取打屏日志标识
	* @title: getLogFlag
	* @author panshiqi 309613
	* @date 2016年4月9日 上午8:40:05 
	* @return
	 */
	public static String getLogFlag() {
		try {
			return "[打屏时间:".concat(StringUtil.getNowTimeStr()).concat(",打屏标识:").concat(StringUtil.getLogGuid()).concat("]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[打屏时间:,打屏标识:]";
	}
}
