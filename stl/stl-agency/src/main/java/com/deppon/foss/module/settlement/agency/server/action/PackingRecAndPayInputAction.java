package com.deppon.foss.module.settlement.agency.server.action;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayInputService;
import com.deppon.foss.module.settlement.agency.api.shared.vo.PackingRecAndPayInputVo;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 包装其它应收应付录入Action 
 * <p style="display:none">modifyRecord</p> 
 * <p style="display:none">version:V1.0,author:105762,date:2014-5-16 上午11:14:40,content:TODO </p>
 * 
 * @author 105762
 * @date 2014-5-16 上午11:14:40
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayInputAction extends AbstractAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(PackingRecAndPayInputAction.class);

	/**
	 * vo
	 */
	private PackingRecAndPayInputVo packingRecAndPayInputVo;

	/**
	 * service
	 */
	private IPackingRecAndPayInputService packingRecAndPayInputService;

	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 包装供应商基础资料service
	 */
	private IPackagingSupplierService packagingSupplierService;

	/**
	 * <p>包装其它应收应付录入</p>
	 * 
	 * @author 105762
	 * @date 2014-5-16 下午5:16:10
	 * @return String
	 * @see com.opensymphony.xwork2.ActionSupport#input()
	 * @throws Exception 
	 */
	@JSON
	public String input() throws Exception {
		LOGGER.info("包装其它应收应付录入 处理开始");
		try {
			validateIfDtoIsNull();
			packingRecAndPayInputService.input(packingRecAndPayInputVo.getPackingRecAndPayInputDto(), FossUserContext.getCurrentInfo());
		} catch (BusinessException e) {
			LOGGER.info("包装其它应收应付录入 处理异常,异常信息:" + e.getErrorCode());
			// 错误编码
			return returnError(e);
		}

		LOGGER.info("包装其它应收应付录入 处理结束");
		return returnSuccess();
	}

	/**
	 * <p>获取包装其它应收应付录入破损奖罚最大金额</p>
	 * @author 105762
	 * @throws Exception 
	 * @date 2014-5-28 下午3:26:40
	 */
	@JSON
	public String queryPackingDamageMaxAmount() throws Exception {
		LOGGER.info("获取破损奖罚最大金额 开始");
		try {
			String value = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_PACKING_DAMAGE_MAX_AMOUNT);
			SettlementUtil.valideIsNull(value, "未查询到系统配置的破损奖罚最大金额,请联系管理员进行配置");
			packingRecAndPayInputVo = new PackingRecAndPayInputVo();
			packingRecAndPayInputVo.setStlPackingDamageMaxAmount(new BigDecimal(value));
		} catch (BusinessException e) {
			LOGGER.info("包装其它应收应付录入 处理异常,异常信息:" + e.getErrorCode());
			// 错误编码
			return returnError(e);
		}

		LOGGER.info("获取破损奖罚最大金额 结束");
		return returnSuccess();
	}

	/**
	 * <p>获取包装其它应收应付包装供应商对应破损率参数</p>
	 * @author 105762
	 * @throws Exception 
	 * @date 2014-5-28 下午3:26:40
	 */
	@JSON
	public String queryPackingStandardDamageRate() throws Exception {
		LOGGER.info("获取包装其它应收应付包装供应商对应破损率参数 开始");
		try {
			SettlementUtil.valideIsNull(packingRecAndPayInputVo, "查询供应商破损率参数为空");
			SettlementUtil.valideIsNull(packingRecAndPayInputVo.getCustomerCode(), "查询供应商破损率参数时供应商编码为空");
			SettlementUtil.valideIsNull(packingRecAndPayInputVo, "查询供应商破损率参数时部门编码为空");
			// 获取破损率参数
			PackagingSupplierEntity en = packagingSupplierService.queryPackagingSupplierByEntity(packingRecAndPayInputVo.getDeptCode(),
					packingRecAndPayInputVo.getCustomerCode());
			SettlementUtil.valideIsNull(en, "未查询到当前部门对应包装供应商破损率参数,请维护包装供应商基础资料后重新录入。");
			if (StringUtils.isNotEmpty(en.getBreakageRate())) {
				packingRecAndPayInputVo.setStandardDamageRate(Double.parseDouble(en.getBreakageRate()));
			}
		} catch (BusinessException e) {
			LOGGER.info("包装其它应收应付录入 处理异常,异常信息:" + e.getErrorCode());
			// 错误编码
			return returnError(e);
		}

		LOGGER.info("获取包装其它应收应付包装供应商对应破损率参数 结束");
		return returnSuccess();
	}

	/**
	 * <p>判断Dto是否为空</p> 
	 * @author 105762
	 * @date 2014-5-17 下午4:17:18
	 */
	private void validateIfDtoIsNull() {
		SettlementUtil.valideIsNull(packingRecAndPayInputVo, "传入参数VO为空");
		SettlementUtil.valideIsNull(packingRecAndPayInputVo.getPackingRecAndPayInputDto(), "传入参数VO为空");
	}

	/**
	  * @return  the packingRecAndPayInputVo
	 */
	public PackingRecAndPayInputVo getPackingRecAndPayInputVo() {
		return packingRecAndPayInputVo;
	}

	/**
	 * @param packingRecAndPayInputVo the packingRecAndPayInputVo to set
	 */
	public void setPackingRecAndPayInputVo(PackingRecAndPayInputVo packingRecAndPayInputVo) {
		this.packingRecAndPayInputVo = packingRecAndPayInputVo;
	}

	/**
	 * @param packingRecAndPayInputService the packingRecAndPayInputService to set
	 */
	public void setPackingRecAndPayInputService(IPackingRecAndPayInputService packingRecAndPayInputService) {
		this.packingRecAndPayInputService = packingRecAndPayInputService;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param packagingSupplierService the packagingSupplierService to set
	 */
	public void setPackagingSupplierService(IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}
}
