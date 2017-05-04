package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;

/**
 * @author 218392 zyx
 * @date 2016-07-07- 19:49:00
 * 长期未退款代收货款冻结Service
 * 长期未退代收货款冻结需求,需求描述如下：
 * （1）90天≤N≤730天，系统每天23:00筛选符合条件的数据，并进行冻结,冻结后的数据进入代收货款支付审核界面并进行展示； 
 * （2）N＞730天，系统每天23:00筛选符合条件的数据，并进行永久冻结，永久冻结后数据进入“代收货款支付审核界面”并进行展示；
 * （3）长期冻结的运单，若需要支付代收货款，只能通过起草异常退代收工作流进行退款；
 * （4）审核状态为：冻结，此类运单不能进行运单更改（给予弹窗提示：该单已被冻结，联系资金安全控制组取消冻结）、不能进行代收货款支付、
 *      不能进行账号修改，资金部取消冻结后方可进行运单更改；审核状态为：永久冻结，此类运单不能进行任何付款操作，不能进行任何运单更改，不能进行账号修改。
 */
public interface INonRefundCodLockService {

	/*
	 * 处理
	 */
	void process();
/**
 * 插入日志
 */
	boolean addInterfaceLog(InterfaceLogEntity entity); 
}
