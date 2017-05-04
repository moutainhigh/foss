package com.deppon.foss.module.settlement.closing.server.action;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.closing.api.server.service.IBalanceService;
import com.deppon.foss.module.settlement.closing.api.shared.vo.BalanceVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 
 * 期末结账批次处理层
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午4:07:05
 */
public class BalanceAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6012592666647643660L;

	/**
	 * 日志
	 */
	public static final Logger LOG = LogManager.getLogger(BalanceAction.class);

	/**
	 * 结账类型
	 */
	public static final String BALANCE_TYPE_BALANCE = "BALANCE";
	public static final String BALANCE_TYPE_UNBALANCE = "UNBALANCE";

	/**
	 * 结账值对象
	 */
	private BalanceVo balanceVo;

	/**
	 * 期末结账批次服务
	 */
	private IBalanceService balanceService;

	/**
	 * 期末结账
	 * 
	 * @return
	 */
	@JSON
	public String closing() {

		try {

			if (balanceVo == null) {
				throw new SettlementException("参数值对象为空");
			}

			Date endDate = balanceVo.getEndDate();

			if (endDate == null) {
				throw new SettlementException("截止日期不能为空");
			}

			// 结账
			balanceService.balance(endDate);

			return returnSuccess("结账成功");

		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return returnError("结账异常:" + ex.getMessage());
		}
	}
	
	/**
	 * 期末反结账
	 * 
	 * @return
	 */
	@JSON
	public String unClosing() {

		try {

			if (balanceVo == null) {
				throw new SettlementException("参数值对象为空");
			}

			Date endDate = balanceVo.getEndDate();

			if (endDate == null) {
				throw new SettlementException("截止日期不能为空");
			}

			// 结账
			balanceService.unBalance(endDate);

			return returnSuccess("反结账成功");

		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return returnError("反结账异常:" + ex.getMessage());
		}
	}

	/**
	 * 获取结账当前信息
	 * 
	 * @return
	 */
	@JSON
	public String currentInfo() {

		try {

			// 获取当前结账日期和程序是否在运行
			Date currentBalanceDate = balanceService.currentBalanceDate();
			boolean isBalanceRun = balanceService.isBalanceRun();

			balanceVo = new BalanceVo();
			balanceVo.setCurrentBalanceDate(currentBalanceDate);
			balanceVo.setBalanceRun(isBalanceRun);

			return returnSuccess("查询成功");

		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return returnError("结账/反结账异常:" + ex.getMessage());
		}

	}

	/**
	 * @return balanceBatchService
	 */
	public void setBalanceService(IBalanceService balanceService) {
		this.balanceService = balanceService;
	}

	/**
	 * @return the balanceVo
	 */
	public BalanceVo getBalanceVo() {
		return balanceVo;
	}

	/**
	 * @param balanceVo
	 *            the balanceVo to set
	 */
	public void setBalanceVo(BalanceVo balanceVo) {
		this.balanceVo = balanceVo;
	}

}
