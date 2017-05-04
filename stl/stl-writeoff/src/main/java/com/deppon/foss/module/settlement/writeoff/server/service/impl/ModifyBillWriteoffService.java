/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.settlement.common.api.server.service.IPagingExportDataSource;
import com.deppon.foss.module.settlement.common.api.server.service.IPagingExportService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ModifyBillPrintInfoDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WaybillChangeDetailPrintDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WaybillChangeRfcPrintDto;
import com.deppon.foss.util.DateUtils;

/**
 * 核销反核销更改单服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午2:13:56
 */
public class ModifyBillWriteoffService implements IModifyBillWriteoffService {

	/**
	 * 注入结算分批导出excel方法
	 */
	private IPagingExportService pagingExportService;

	/**
	 * 接送货，更改单service
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 每页条数
	 */
	private int pageSize = 50000;

	/**
	 * 配置参数
	 */
	private IConfigurationParamsService configurationParamsService;

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 注入组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService productService;

	/**
	 * 查询待核销的更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-30 下午5:30:56
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#queryModifyBill(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto)
	 */
	@Override
	public BillWriteoffResultDto queryModifyBill(
			BillWriteoffDto modifyBillWriteOffDto, int limit, int start,
			CurrentInfo currentInfo) {

		// 查询dto不能为空
		if (modifyBillWriteOffDto == null) {
			// 提示查询更改单错误，查询条件为空
			throw new SettlementException("查询更改单错误，查询条件为空!");
		}

		// 查询待核销更改单列表
		// 核销反核销更改单result dto
		BillWriteoffResultDto wrifReWChResultDto = new BillWriteoffResultDto();

		// 按区域查询
		setSearchArea(modifyBillWriteOffDto, currentInfo);

		// 将传入查询参数Dto，传入接口
		ModifyBillWriteoffDto modiBillWriDto = new ModifyBillWriteoffDto();

		// 设置起草开始日期、
		modiBillWriDto.setAcceptStartDate(modifyBillWriteOffDto
				.getAcceptStartDate());
		// 设置起草结束日期、
		modiBillWriDto.setAcceptEndDate(DateUtils.addDayToDate(
				modifyBillWriteOffDto.getAcceptEndDate(), 1));

		// 设置起草部门
		modiBillWriDto.setDarftOrgCodeList(modifyBillWriteOffDto
				.getCollectionOrgCodeList());

		// 运单号
		modiBillWriDto.setWaybillNumbers(modifyBillWriteOffDto.getWaybillNos());
		// 核销状态 空为 全部 ”“、核销通过 1 2、核销不通过 0、反核销（未核销状态）
		modiBillWriDto.setWriteoffStatus(modifyBillWriteOffDto
				.getWriteoffStatus());
		// 起草部门
		// modiBillWriDto.setDarftOrgCode(modifyBillWriteOffDto.getDarftOrgName());

		// 当前登录职员编码
		modiBillWriDto.setEmpCode(modifyBillWriteOffDto.getEmpCode());

		// ---小件新增产品类型条件
		modiBillWriDto.setProductType(modifyBillWriteOffDto
				.getProductTypeList());
		if (StringUtils.isNotBlank(modiBillWriDto.getWriteoffStatus())) {
			modiBillWriDto.setChangeAmount(this.readChangeMount());
		} else {
			modiBillWriDto.setChangeAmount(null);
		}

		// 查询更改单总条数
		// long num =
		 //waybillRfcService.queryExpressModifyBillWriteoffResultCount(modiBillWriDto,0,0);

		// 接收返回List ，并封装到前台Dto
		List<BillWriteoffResultDto> resultDtoList = new ArrayList<BillWriteoffResultDto>();
		// 更改单条数大于0
		// if (num > 0) {
		long num = 0;
		// 查询更改单
		List<ModifyBillWriteoffResultDto> queryMoBillList = null;

		Map<Long, List<ModifyBillWriteoffResultDto>> modifyBillWriteoffResultMap = waybillRfcService
				.queryExpressModifyBillWriteoffResult(modiBillWriDto, start,
						limit);
		for (Long key : modifyBillWriteoffResultMap.keySet()) {
			num = key;
			queryMoBillList = modifyBillWriteoffResultMap.get(key);
		}
		// 生成更改单返回dto
		BillWriteoffResultDto billWriteoffResultDto = null;
		// 统计更改单变更金额
		BigDecimal changeAmountAfter = BigDecimal.ZERO;
		// 变更金额
		BigDecimal changeAmount = BigDecimal.ZERO;
		// 循环处理更改单
		for (ModifyBillWriteoffResultDto dto : queryMoBillList) {
			// 生成更改单返回dto
			billWriteoffResultDto = new BillWriteoffResultDto();

			// 更改后预付为空时，方便计算设置初始值为零
			if (null == dto.getNewPrePayAmount()) {
				dto.setNewPrePayAmount(BigDecimal.ZERO);
			}
			// 更改后应付为空时，方便计算设置初始值为零
			if (null == dto.getNewToPayAmount()) {
				dto.setNewToPayAmount(BigDecimal.ZERO);
			}
			// 更改前应付金额为空时，方便计算设置初始值为零
			if (null == dto.getOldPrePayAmount()) {
				dto.setOldPrePayAmount(BigDecimal.ZERO);
			}
			// 更改后预付金额为空时，方便计算设置初始值为零
			if (null == dto.getOldToPayAmount()) {
				dto.setOldToPayAmount(BigDecimal.ZERO);
			}
			// 将查询数据拷贝到更改单返回dto
			BeanUtils.copyProperties(dto, billWriteoffResultDto);

			// 变更金额=（更改后预付+更改后应付）-（预付前金额+应付前金额）
			// 预付前金额+应付前金额
			changeAmountAfter = dto.getNewPrePayAmount().add(
					dto.getNewToPayAmount());
			// （更改后预付+更改后应付）-（更改前预付+更改前应付）
			changeAmount = changeAmountAfter.subtract(dto.getOldPrePayAmount()
					.add(dto.getOldToPayAmount()));
			// 设置更改单返回dto的变更金额
			billWriteoffResultDto.setChangeAmount(changeAmount);
			// 当选择全部时候，则默认不要300限制，否则是要300限制
			resultDtoList.add(billWriteoffResultDto);

		}
		// 设置更改单返回dto
		wrifReWChResultDto.setWaybillRfcList(resultDtoList);
		// 返回更改单条数
		wrifReWChResultDto.setTotalCount(num);
		// }
		// 返回更改单返回dto
		return wrifReWChResultDto;
	}

	/**
	 * 转换更改单核销报表的变更金额
	 * 
	 * @return int
	 */
	private BigDecimal readChangeMount() {
		BigDecimal num = new BigDecimal("300");
		String[] codes = new String[1];
		codes[0] = ConfigurationParamsConstants.STL_WRITEOFF_CHANGE_AMOUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService
				.queryConfigurationParamsBatchByCode(codes);
		if (null != configurationParamsEntitys
				&& configurationParamsEntitys.size() > 0) {
			String value = configurationParamsEntitys.get(0).getConfValue();
			if (value != null) {
				num = new BigDecimal(value);
			}
		}
		return num;
	}

	/**
	 * 更改单按设置查询权限区域（大区、小区）、部门查询条件 大区下有多个部门、小区下多个部门或按指定部门查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-1-12 下午5:03:26
	 */
	private void setSearchArea(BillWriteoffDto dto, CurrentInfo currentInfo) {

		// 设置当前登录用户员工编码
		dto.setEmpCode(currentInfo.getEmpCode());

		// 当前操作部门，查询时最终可以查询的部门
		List<String> orgList = new ArrayList<String>();

		// 如果指定部门按照部门查询
		if (StringUtils.isNotEmpty(dto.getDarftOrgCode())) {
			// 设置当前登录部门
			orgList.add(dto.getDarftOrgCode());

			// 如果按小区查询获取小区下所有部门
		} else if (StringUtils.isEmpty(dto.getDarftOrgCode())
				&& StringUtils.isNotEmpty(dto.getSmallArea())) {
			// 查询小区
			List<OrgAdministrativeInfoEntity> smallOrgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(dto
							.getSmallArea());
			// 添加小区能够查询的权限
			if (CollectionUtils.isNotEmpty(smallOrgList)) {
				// 循环处理小区
				for (OrgAdministrativeInfoEntity entity : smallOrgList) {
					// 增加小区下的每个组织部门code
					orgList.add(entity.getCode());
				}
			}

			// 如果按部门空、小区为空查询获取大区下所有部门
		} else if (StringUtils.isEmpty(dto.getDarftOrgCode())
				&& StringUtils.isEmpty(dto.getSmallArea())
				&& StringUtils.isNotEmpty(dto.getLargeArea())) {
			// 查询大曲
			List<OrgAdministrativeInfoEntity> bigOrgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(dto
							.getLargeArea());
			// 如果大区不为空
			if (CollectionUtils.isNotEmpty(bigOrgList)) {
				// 添加大区下当前登录用户
				for (OrgAdministrativeInfoEntity entity : bigOrgList) {
					// 增加大区下的部门编码
					orgList.add(entity.getCode());
				}
			}
		}
		// 如果部门编码列表不为空
		if (CollectionUtils.isNotEmpty(orgList)) {
			// 设置部门编码查询条件
			dto.setCollectionOrgCodeList(orgList);
		}
	}

	/**
	 * 反核销 更改单
	 * 
	 * 反核销操作只能由核销经理操作
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-30 下午5:30:56
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#writeoffWaybillChange(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto)
	 */
	@Override
	public BillWriteoffResultDto writeoffModifyBill(
			BillWriteoffDto modifyBillWriteoffDto, CurrentInfo cInfo) {

		// 查询dto不能为空
		if (modifyBillWriteoffDto == null) {
			// 提示内部错误，待反核销的更改单为空
			throw new SettlementException("内部错误，待反核销的更改单为空!");
		}

		// 核销更改单
		BillWriteoffResultDto wriReWChResultDto = new BillWriteoffResultDto();
		// 读取当前登陆者信息，读取权限表读取登陆者的权限,以及是否可以做反核销操作,如果身份不是会计经理不出现反审核按钮
		//
		// 设置核销的状态,判断用户是操作是核销通过、不通过、反核销
		if (StringUtil.isEmpty(modifyBillWriteoffDto.getWriteoffSuccess())
				&& StringUtil.isEmpty(modifyBillWriteoffDto.getWriteoffFail())) {
			// 提示请选择需要进行核销操作
			throw new SettlementException("", "请选择需要进行核销操作");
		}

		// 更新更改单表
		// 调用接送货核销或者反核销方法
		// 核销状态后续用常量代替 1表示核销不通过 2表示核销通过 3反核销
		// 核销不通过
		if (WaybillRfcConstants.WRITE_OFF_FAILURE.equals(modifyBillWriteoffDto
				.getWriteoffFail())) {
			// 获取核销是否通过状态
			String status = modifyBillWriteoffDto.getWriteoffFail();
			// 调用接送货提供的服务，更新更改单状态为核销不通过
			waybillRfcService.writeOffWaybillChange(
					modifyBillWriteoffDto.getSelectWaybillChangeNos(),
					modifyBillWriteoffDto.getNotes(), status, cInfo);
			// 核销通过
		} else if (WaybillRfcConstants.WRITE_OFF_SUCCESS
				.equals(modifyBillWriteoffDto.getWriteoffSuccess())) {
			// 调用接送货提供的服务， 更新更改单状态为核销通过
			waybillRfcService.writeOffWaybillChange(
					modifyBillWriteoffDto.getSelectWaybillChangeNos(),
					modifyBillWriteoffDto.getNotes(),
					modifyBillWriteoffDto.getWriteoffSuccess(), cInfo);

		} else {
			// 提示更改单核销状态，不存在
			throw new SettlementException("更改单核销状态，不存在！");
		}

		// 返回更改单dto
		return wriReWChResultDto;
	}

	/**
	 * 反核销更改单状态为核销通过、核销不通过状态的更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-1 上午8:34:27
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#reverseModifyBill(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto)
	 */
	@Override
	public BillWriteoffResultDto reverseModifyBill(
			BillWriteoffDto modifyBillWriteoffDto, CurrentInfo cInfo) {

		// 查询dto不能为空
		if (modifyBillWriteoffDto == null) {
			// 提示内部错误，来待反审核的核销更改单为空
			throw new SettlementException("内部错误，来待反审核的核销更改单为空!");
		}

		// 生成会计经理反核销更改单
		BillWriteoffResultDto wriReWChResultDto = new BillWriteoffResultDto();

		// 获取当前登陆者code
		if (WaybillRfcConstants.NO_WRITE_OFF.equals(modifyBillWriteoffDto
				.getReverse())) {
			// 将核销通过、核销不通过更改单设置为未核销状态
			waybillRfcService.reverseWaybillChange(
					modifyBillWriteoffDto.getSelectWaybillChangeNos(),
					modifyBillWriteoffDto.getNotes(), cInfo);
		} else {
			// 提示更改单核销状态，不存在
			throw new SettlementException("更改单核销状态，不存在！");
		}
		// 返回会计经理反核销更改单
		return wriReWChResultDto;
	}

	/**
	 * 导出更改单查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-20 下午3:17:44
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#queryExportModifyBill(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto)
	 */
	public BillWriteoffResultDto queryExportModifyBill(
			BillWriteoffDto modifyBillDto, CurrentInfo currentInfo, int start,
			int limit) {

		// 参数dto不能为空
		if (modifyBillDto == null) {
			// 提示内部错误，待导出的更改单为空
			throw new SettlementException("内部错误，待导出的更改单为空!");
		}

		// 按区域查询
		setSearchArea(modifyBillDto, currentInfo);

		// 生成更改单返回Dto
		BillWriteoffResultDto bResultDto = new BillWriteoffResultDto();

		// 将传入查询参数Dto，传入接口
		ModifyBillWriteoffDto modiBillWriDto = new ModifyBillWriteoffDto();

		/**
		 * 设置起草开始日期、设置起草结束日期、设置大区、设置小区、运单号、 核销状态 空为 全部 核销通过 1 2、核销不通过
		 * 0、反核销（未核销状态）、起草部门
		 */

		modiBillWriDto.setAcceptStartDate(modifyBillDto.getAcceptStartDate());// 受理开始时间
		modiBillWriDto.setAcceptEndDate(modifyBillDto.getAcceptEndDate());// 受理结束时间
		modiBillWriDto.setLargeArea(modifyBillDto.getLargeArea());// 大区
		modiBillWriDto.setSmallArea(modifyBillDto.getSmallArea());// 小区
		modiBillWriDto.setWaybillNumbers(modifyBillDto.getWaybillNos());// 运单号
		modiBillWriDto.setWriteoffStatus(modifyBillDto.getWriteoffStatus());// 核销状态
		modiBillWriDto.setDarftOrgCode(modifyBillDto.getDarftOrgName());// 起草网点
		// 设置起草部门
		modiBillWriDto.setDarftOrgCodeList(modifyBillDto
				.getCollectionOrgCodeList());
		modiBillWriDto.setEmpCode(currentInfo.getEmpCode());// 员工编码
		// 给变更金额设置
		if (StringUtils.isNotBlank(modiBillWriDto.getWriteoffStatus())) {
			modiBillWriDto.setChangeAmount(this.readChangeMount());
		} else {
			modiBillWriDto.setChangeAmount(null);
		}
		modiBillWriDto.setProductType(modifyBillDto.getProductTypeList()); // 运输性质

		List<ModifyBillWriteoffResultDto> rfcList = null;
		// 查询未受理的更改单
		Map<Long, List<ModifyBillWriteoffResultDto>> modifyBillWriteoffResultMap = waybillRfcService
				.queryExpressModifyBillWriteoffResult(modiBillWriDto, start,
						limit);
		for (Long key : modifyBillWriteoffResultMap.keySet()) {
			rfcList = modifyBillWriteoffResultMap.get(key);
		}

		// 生成更改单返回Dto列表
		List<BillWriteoffResultDto> waybillRfcList = new ArrayList<BillWriteoffResultDto>();
		// 新建更改单返回Dto
		BillWriteoffResultDto billDto = null;

		// 统计更改单变更金额临时
		BigDecimal changeAmountAfter = BigDecimal.ZERO;
		// 统计更改单变更金额
		BigDecimal changeAmount = BigDecimal.ZERO;

		// 获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}
		// 声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_WAYLLBAY_WRITEOFF_STATUS);
		// 后台获取数据字典对应的数据
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(termCodes);

		// 遍历更改单记录，设置到前台
		for (ModifyBillWriteoffResultDto dto : rfcList) {
			// 新建更改单返回Dto
			billDto = new BillWriteoffResultDto();

			// 更改后预付为空时，方便计算设置初始值为零
			if (null == dto.getNewPrePayAmount()) {
				dto.setNewPrePayAmount(BigDecimal.ZERO);
			}
			// 更改后应付为空时，方便计算设置初始值为零
			if (null == dto.getNewToPayAmount()) {
				dto.setNewToPayAmount(BigDecimal.ZERO);
			}
			// 更改前应付金额为空时，方便计算设置初始值为零
			if (null == dto.getOldPrePayAmount()) {
				dto.setOldPrePayAmount(BigDecimal.ZERO);
			}
			// 更改后预付金额为空时，方便计算设置初始值为零
			if (null == dto.getOldToPayAmount()) {
				dto.setOldToPayAmount(BigDecimal.ZERO);
			}

			// 拷贝数据到更改单返回Dto
			BeanUtils.copyProperties(dto, billDto);
			// 转话产品类型数据字典
			if (StringUtils.isNotBlank(billDto.getProductType())) {
				billDto.setProductType(productMap.get(dto.getProductType()));
			}
			// 转话核销类型
			if (StringUtils.isNotBlank(billDto.getWriteoffStatus())) {
				billDto.setWriteoffStatus(SettlementUtil
						.getDataDictionaryByTermsName(
								map,
								DictionaryConstants.BILL_WAYLLBAY_WRITEOFF_STATUS,
								dto.getWriteoffStatus()));
			}

			// 变更金额=（更改后预付+更改后应付）-（预付前金额+应付前金额）
			// 预付前金额+应付前金额
			changeAmountAfter = dto.getNewPrePayAmount().add(
					dto.getNewToPayAmount());
			// （更改后预付+更改后应付）-（更改前预付+更改前应付）
			changeAmount = changeAmountAfter.subtract(dto.getOldPrePayAmount()
					.add(dto.getOldToPayAmount()));
			// 设置更改单返回dto的变更金额
			billDto.setChangeAmount(changeAmount);

			// 更改单返回Dto加入到返回列表
			waybillRfcList.add(billDto);

		}
		// 设置到返回dto
		bResultDto.setWaybillRfcList(waybillRfcList);
		// 返回dto
		return bResultDto;
	}

	/**
	 * 根据更改单i点，查询更改单信息，并打印
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-4-17 下午1:43:31
	 * @param rfcId
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#queryWaybillRfcPrintDtoByRfcid(java.lang.String)
	 */
	@Override
	public WaybillChangeRfcPrintDto queryWaybillRfcPrintDtoByRfcid(String rfcId) {

		// 根据更改单id，查询更改单和更改单明细
		WaybillRfcPrintDto rfcDto = waybillRfcService
				.queryWaybillRfcPrintDtoByRfcid(rfcId);
		List<WaybillChangeDetailPrintDto> list = new ArrayList<WaybillChangeDetailPrintDto>();
		if (null == rfcDto) {
			new SettlementException("打印更改单,根据更改单单号:" + rfcId + "查询更改单实体为空!");
		}

		// 返回到更改单打印界面显示,更改单表头
		WaybillChangeRfcPrintDto waybillChangeDto = new WaybillChangeRfcPrintDto();
		BeanUtils.copyProperties(rfcDto, waybillChangeDto);

		// 如果更改单明细不为空时，复制更改单明细
		if (CollectionUtils.isNotEmpty(rfcDto.getChangeList())) {
			for (WaybillRfcChangeDetailEntity entity : rfcDto.getChangeList()) {
				WaybillChangeDetailPrintDto dDto = new WaybillChangeDetailPrintDto();
				BeanUtils.copyProperties(entity, dDto);
				list.add(dDto);
			}
			waybillChangeDto.setChangeList(list);
		}
		// 返回更改单信息
		return waybillChangeDto;
	}

	/**
	 * 打印次数(传入更改单id和打印次数)
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-4-25 下午3:29:33
	 * @param waybillChangeId
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#updateByPrimaryKeySelective(java.lang.String)
	 */
	@Override
	public int updateByPrimaryKeySelective(String waybillChangeId,
			ModifyBillPrintInfoDto dto) {

		// 更改单关联的运单打印时，新增打印的实体
		PrintInfoEntity entity = new PrintInfoEntity();
		BeanUtils.copyProperties(dto, entity);
		return waybillRfcService.insertPrintInfoSelective(entity);

	}

	/**
	 * 分批导出更改单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-25 下午5:17:01
	 * @param waybillRFCId
	 * @param waybillNo
	 * @return
	 */
	public ByteArrayInputStream exportBillForBatch(BillWriteoffDto modifyDto,
			CurrentInfo currentInfo, final String conpath) {
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (modifyDto == null || modifyDto.getArrayColumnNames() == null
				|| modifyDto.getArrayColumnNames().length == 0) {
			// 提示导出Excel的列头名称不存在，请至少存在一列
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}

		// 表头
		final String[] header = modifyDto.getArrayColumnNames();
		final String[] headerNames = modifyDto.getArrayColumns();
		final BillWriteoffDto dto = modifyDto;
		final CurrentInfo cIfo = currentInfo;
		ByteArrayInputStream stream = null;

		try {
			stream = pagingExportService.pagingExport(
					new IPagingExportDataSource() {

						@Override
						public String tempDirectory() {
							return conpath;
						}

						@Override
						public String[] mappings() {
							return headerNames;
						}

						@Override
						public String[] headers() {
							return header;
						}

						@Override
						public String filename() {
							return "核销更改单";
						}

						@Override
						public List<BillWriteoffResultDto> dataList(int page) {
							int start = page * pageSize;
							int limit = pageSize;
							// 调用执行方法，获取结果集
							BillWriteoffResultDto billWriResDto = queryExportModifyBill(
									dto, cIfo, start, limit);
							// 将核销状态为“未核销”的订单核销时间和核销人设为空
							if (!CollectionUtils.isEmpty(billWriResDto
									.getWaybillRfcList())) {
								for (BillWriteoffResultDto resultDto : billWriResDto
										.getWaybillRfcList()) {
									if (resultDto.getWriteoffStatus().equals(
											"未核销")) {
										resultDto.setWriteOffEmpName(null);
										resultDto.setWriteOffTime(null);
									}
								}
							}
							return billWriResDto.getWaybillRfcList();
						}
					}, pageSize);
		} catch (IOException e) {
			throw new SettlementException("导出异常", e.getMessage());
		}
		return stream;
	}

	/**
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 查询更改单的打印次数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-4-25 下午5:17:42
	 * @param waybillRFCId
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService#queryPrintTimesByWaybillRFCId(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int queryPrintTimesByWaybillRFCId(String waybillRFCId,
			String waybillNo) {
		return waybillRfcService.queryPrintTimesByWaybillRFCId(waybillRFCId,
				waybillNo);
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IPagingExportService getPagingExportService() {
		return pagingExportService;
	}

	public void setPagingExportService(IPagingExportService pagingExportService) {
		this.pagingExportService = pagingExportService;
	}

}
