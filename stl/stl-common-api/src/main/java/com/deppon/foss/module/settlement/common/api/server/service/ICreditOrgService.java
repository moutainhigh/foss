package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;

/**
 * 组织信用额度服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-16 下午6:43:42
 */
public interface ICreditOrgService {

	/**
	 * 信息客户用额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午6:47:07
	 */
	void addCreditOrg(CreditOrgEntity item);

	/**
	 * 更新客户的可用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午8:07:57
	 */
	void updateOrgCredit(CreditOrgEntity item);

	/**
	 * 
	 * 查询部门收支平衡表
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:25:26
	 */
	CreditOrgEntity queryByOrgCode(String orgCode);

	/**
	 * 
	 * 按组织编码查询组织的信用额度信息
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午5:41:54
	 * @param orgCode
	 *            组织编码
	 */
	CreditOrgDto queryDebitByOrgCode(String orgCode);

	/**
	 * 
	 * 更新已用的信用额度
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午4:30:50
	 */
	void updateUsedAmount(String orgCode, BigDecimal amount);

	/**
	 * 更新超期欠款标记
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午3:23:48
	 */
	void updateOverdueState(String orgCode, String isOverdue,
			int maxAccountDays, String notes);

	/**
	 * 
	 * 获得组织行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 上午11:22:49
	 */
	int queryTotalRows();

	/**
	 * 
	 * 通过分页的方式查询组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 上午11:28:23
	 */
	List<CreditOrgDto> queryByPage(int offset, int limit);

	/**
	 * 
	 * 查询部门临欠额度、最大账期预警
	 * 
	 * @Title: queryOrgAlarm
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-8 下午2:00:18
	 * @param @param orgCode
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	String queryOrgAlarm(String orgCode);

    /**
     * 根据部门编码动态查询已经使用额度
     * @param orgCode
     * @return
     */
    CreditOrgDto queryOrgCreditInfo(String orgCode);

}
