package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CustomerInUseDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jgroups.util.UUID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 客户信用额度管理实现类
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-17 上午8:24:39
 */
public class CreditCustomerService implements ICreditCustomerService{

	private static final Logger LOGGER = LogManager
			.getLogger(CreditCustomerService.class);

	/**
	 * 客户收支平衡表Dao
	 */
	private ICreditCustomerEntityDao creditCustomerEntityDao;
    private  static  final  int NUM_SIX =  -6;

	/**
	 * 
	 * 创建客户收支平衡信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午11:34:31
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#addCreditCustomer(com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity)
	 */
	@Override
	public void addCreditCustomer(CreditCustomerEntity item){
		// 检查参数
		// 检查待添加的
		if (item == null){
			throw new SettlementException("传入参数为空!");
		}
		// 检查客户编码
		if (StringUtils.isEmpty(item.getCustomerCode())){
			throw new SettlementException("传入客户编码为空!");
		}
		// 检查已用额度
		if (null == item.getUsedAmount()){
			throw new SettlementException("传入已用额度为空!");
		} else if (item.getUsedAmount().compareTo(BigDecimal.ZERO) < 0){
			throw new SettlementException("传入已用额度小于0!");
		}
		// 如果Id为空，初始化Id
		if (StringUtils.isEmpty(item.getId())){
			item.setId(UUID.randomUUID().toString());
		}
		// 初始化
		Date sysDate = new Date();
		item.setCreateTime(sysDate);
		item.setBusinessDate(sysDate);

		LOGGER.debug("开始新加客户收支平衡,客户编码:" + item.getCustomerCode() + ",客户名称："
				+ item.getCustomerName());
		// 新加
		int result = this.creditCustomerEntityDao.addCreditCustomer(item);
		// 判断返回行数
		if (result != 1){

			LOGGER.error("客户平衡表信息保存失败");

			throw new SettlementException("客户平衡表信息保存失败!");
		}
		LOGGER.debug("新加客户收支平衡信息结束");

	}

	/**
	 * 按客户编码查询客户信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午2:38:41
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#queryByCustomerCode(java.lang.String)
	 */
	@Override
	public CreditCustomerEntity queryByCustomerCode(String customerCode){
		if (StringUtils.isNotEmpty(customerCode)){
			return this.creditCustomerEntityDao
					.queryByCustomerCode(customerCode);
		} else{
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 获取客户的欠款额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午3:30:46
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#queryDebitByCustomerCode(java.lang.String)
	 */
	@Override
	public CreditCustomerDto queryDebitByCustomerCode(String customerCode){
		if (StringUtils.isNotEmpty(customerCode)){
			return this.creditCustomerEntityDao
					.queryDebitByCustomerCode(customerCode);
		} else{
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 更新客户的可用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午2:43:35
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#updateCreditCustomer(com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity)
	 */
	@Override
	public void updateCreditCustomer(CreditCustomerEntity item){
		if (item != null){

			LOGGER.info("修改客户收支平衡信息,客户编码:" + item.getCustomerCode() + ",客户名称："
					+ item.getCustomerName());

			Date date = new Date();
			item.setModifyDate(date);
			item.setBusinessDate(date);

			int updateRows = this.creditCustomerEntityDao
					.updateCreditCustomer(item);
			if (updateRows != 1){

				LOGGER.error("修改客户收支平衡信息失败!");

				throw new SettlementException("内部错误，更新行数不唯一");
			}
			LOGGER.info("修改客户收支平衡信息结束,客户编码:" + item.getCustomerCode()
					+ "，客户名称：" + item.getCustomerName());
		} else{
			throw new SettlementException("内部错误参数为空");
		}

	}

	/**
	 * 获得总客户的行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午2:49:17
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#queryTotalRows()
	 */
	@Override
	public int queryTotalRows(){
		return this.creditCustomerEntityDao.queryTotalRows();
	}

	/**
	 * 通过分页进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午2:49:49
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#queryByPage(int,
	 *      int)
	 */
	@Override
	public List<CreditCustomerDto> queryByPage(int offset, int limit){
		return this.creditCustomerEntityDao.queryCreditCustomerByPage(offset,
				limit);
	}

	/**
	 * 
	 * 更新已用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午2:54:11
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#updateUsedAmount(java.lang.String,
	 *      java.math.BigDecimal)
	 */
	@Override
	public void updateUsedAmount(String customerCode, BigDecimal debtAmount){
		if (StringUtils.isNotEmpty(customerCode) && debtAmount != null){

			LOGGER.info("开始修改客户已用额度,客户编码：" + customerCode + ",已用额度"
					+ debtAmount);

			// 执行更新
			// 更新失败不错强制要求
			this.creditCustomerEntityDao.updateUsedAmount(customerCode,
					debtAmount);

			LOGGER.info("修改客户已用额度结束");
		} else{
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新超期欠款标记
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午11:36:01
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService#updateOverdueStatus(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void updateOverdueStatus(String customerCode, String isOverdue,
			String notes){
		if (StringUtils.isNotEmpty(customerCode)
				&& StringUtils.isNotEmpty(isOverdue)){

			LOGGER.info("更新客户超期标记,客户编码 ：" + customerCode + ",欠款标记:" + isOverdue);

			// 执行更新
			int updateRows = this.creditCustomerEntityDao.updateOverdueStatus(
					customerCode, isOverdue, notes);
			if (updateRows != 1){

				LOGGER.error("更新客户超期标记发生错误!");

				throw new SettlementException("更新行数不唯一!");
			}

			LOGGER.info("更新客户超期标记结束");
		} else{
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerInUseDto> isCustomerInUse(List<String> customerCodes){
		if (CollectionUtils.isEmpty(customerCodes)){
			throw new SettlementException("客户编码为空");
		}

		// 有效
		String active = FossConstants.ACTIVE;

		// 默认查询6个月的数据
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH,NUM_SIX);
		Date acctDate = cal.getTime();

		// 查询应收单
		List<String> rcvCustCodes = this.creditCustomerEntityDao
				.isCustomerInUseRCV(active, acctDate, customerCodes);
		List<String> payCustCodes = null;
		List<String> drCustCodes = null;

		// 查询剩下的客户编码在应付单
		List<String> subCustomerCodes = (List<String>) CollectionUtils
				.subtract(customerCodes, rcvCustCodes);
		if (CollectionUtils.isNotEmpty(subCustomerCodes)){
			//查询应付单
			payCustCodes = this.creditCustomerEntityDao.isCustomerInUsePAY(
					active, acctDate, subCustomerCodes);

			// 在应付单中查询的客户编码不为空
			if (CollectionUtils.isNotEmpty(payCustCodes)){
				subCustomerCodes = (List<String>) CollectionUtils.subtract(
						subCustomerCodes, payCustCodes);
			}

			//查询预收单
			drCustCodes = this.creditCustomerEntityDao.isCustomerInUseDR(
					active, acctDate, subCustomerCodes);
		}
		
		//有效的正在使用的客户
		List<String> custCodes = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(rcvCustCodes)){
			custCodes.addAll(rcvCustCodes);
		}
		if (CollectionUtils.isNotEmpty(payCustCodes)){
			custCodes.addAll(payCustCodes);
		}
		if (CollectionUtils.isNotEmpty(drCustCodes)){
			custCodes.addAll(drCustCodes);
		}
         
		//返回结果集
	    List<CustomerInUseDto> resultList = new ArrayList<CustomerInUseDto>();
	    CustomerInUseDto dto = null;
	    
	    //循环判断是否在使用
		for (String code : customerCodes){
			dto = new CustomerInUseDto();
			dto.setCustomerCode(code);
			dto.setInUse(custCodes.contains(code));
			
			resultList.add(dto);
		}
		
		return resultList;
	}

    /**
     * 通过客户编码查看客户的欠款信息
     * @param customerCode
     * @return
     */
    @Override
    public CreditCustomerDto queryDebitCustomerInfo(String customerCode) {
        //获取客户欠款信息集合
        List<CreditCustomerDto> creditCustomerDtos = creditCustomerEntityDao.queryCustomerDebitInfo(customerCode);
        CreditCustomerDto dto = null;
        //获取查询结果
        if(CollectionUtils.isNotEmpty(creditCustomerDtos)
                &&creditCustomerDtos.size()>0){
            dto =creditCustomerDtos.get(0);
        }
        return dto;
    }

    /**
     * @author 268217
     * 通过客户编码查询客户详细欠款信息
     * @param customerCode
     * @return
     */
    public CreditCustomerDto queryCustomerDebit(String customerCode,String productId){
    	//获取客户欠款信息集合
        List<CreditCustomerDto> creditCustomerDtos = creditCustomerEntityDao.queryCustomerDebit(customerCode,productId);
        CreditCustomerDto dto = null;
        //获取查询结果
        if(CollectionUtils.isNotEmpty(creditCustomerDtos)
                &&creditCustomerDtos.size()>0){
            dto =creditCustomerDtos.get(0);
        }
        return dto;
    }
    
	public void setCreditCustomerEntityDao(
			ICreditCustomerEntityDao creditCustomerEntityDao){
		this.creditCustomerEntityDao = creditCustomerEntityDao;
	}

}
