package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDistributeService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformPreAssignEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformVehicleInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PlatformDistributeVo;

public class PlatformDistributeAction extends AbstractAction {

	private static final long serialVersionUID = 7903423562147819601L;

	private IPlatformDistributeService platformDistributeService;

	private PlatformDistributeVo platformDistributeVo = new PlatformDistributeVo();

	public PlatformDistributeVo getPlatformDistributeVo() {
		return platformDistributeVo;
	}

	public void setPlatformDistributeVo(
			PlatformDistributeVo platformDistributeVo) {
		this.platformDistributeVo = platformDistributeVo;
	}

	public void setPlatformDistributeService(
			IPlatformDistributeService platformDistributeService) {
		this.platformDistributeService = platformDistributeService;
	}

	public String platformDistributeIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		try {
			Map<String, String> transferCenter = platformDistributeService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				platformDistributeVo.setParentTfrCtrCode(transferCenter
						.get("code"));
				platformDistributeVo.setParentTfrCtrName(transferCenter
						.get("name"));
			} else {
				platformDistributeVo.setParentTfrCtrCode(currentDeptCode);
				platformDistributeVo.setParentTfrCtrName(FossUserContext
						.getCurrentDeptName());
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * 查询在途公司车
	 * 
	 * @return
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	public String queryCompanyOnTheWay() {

		PlatformDistributeQcDto qcDto = platformDistributeVo
				.getPlatformDistributeQcDto();

		List<PlatformVehicleInfoDto> vehicleInfosCompanyOnTheWay = null;
		Long totalCount = 0L;

		if (StringUtils.isEmpty(qcDto.getHandoverNo())) {
			vehicleInfosCompanyOnTheWay = platformDistributeService
					.queryCompanyOnTheWay(qcDto, super.getStart(),
							super.getLimit());
			totalCount = platformDistributeService
					.queryCntCompanyOnTheWay(qcDto);
		} else {
			vehicleInfosCompanyOnTheWay = new ArrayList<PlatformVehicleInfoDto>();
			PlatformVehicleInfoDto companyOnTheWay = platformDistributeService
					.queryCompanyOnTheWayWithNum(qcDto);
			if (companyOnTheWay != null) {
				vehicleInfosCompanyOnTheWay.add(companyOnTheWay);
			}
			totalCount = companyOnTheWay == null ? 0L : 1L;
		}

		platformDistributeVo
				.setVehicleInfosCompanyOnTheWay(vehicleInfosCompanyOnTheWay);
		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 查询在途外请车
	 * 
	 * @return
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	public String queryLeasedOnTheWay() {
		PlatformDistributeQcDto qcDto = platformDistributeVo
				.getPlatformDistributeQcDto();

		List<PlatformVehicleInfoDto> vehicleInfosLeasedOnTheWay = null;
		Long totalCount = 0L;
		if (StringUtils.isEmpty(qcDto.getHandoverNo())) {
			vehicleInfosLeasedOnTheWay = platformDistributeService
					.queryLeasedOnTheWay(qcDto, super.getStart(),
							super.getLimit());
			totalCount = platformDistributeService
					.queryCntLeasedOnTheWay(qcDto);
		} else {
			vehicleInfosLeasedOnTheWay = new ArrayList<PlatformVehicleInfoDto>();
			PlatformVehicleInfoDto leasedOnTheWay = platformDistributeService
					.queryLeasedOnTheWayWithNum(qcDto);

			if (leasedOnTheWay != null) {
				vehicleInfosLeasedOnTheWay.add(leasedOnTheWay);
			}
			totalCount = leasedOnTheWay == null ? 0L : 1L;
		}

		platformDistributeVo
				.setVehicleInfosLeasedOnTheWay(vehicleInfosLeasedOnTheWay);
		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 到达未分配
	 * 
	 * @return
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	public String queryArrived() {

		PlatformDistributeQcDto qcDto = platformDistributeVo
				.getPlatformDistributeQcDto();

		List<PlatformVehicleInfoDto> vehicleInfosCompanyOnTheWay = platformDistributeService
				.queryArrived(qcDto, super.getStart(), super.getLimit());
		totalCount = platformDistributeService.queryCntArrived(qcDto);

		platformDistributeVo
				.setVehicleInfosArrived(vehicleInfosCompanyOnTheWay);
		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 获取最优月台
	 * 
	 * @return
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	public String queryOptimalPlatform() {
		PlatformQcDto qcDto = platformDistributeVo.getPlatformQcDto();
		List<PlatformDto> platformDtos = platformDistributeService
				.queryOptimalPlatform(qcDto);
		platformDistributeVo.setPlatformDtos(platformDtos);
		return SUCCESS;
	}

	/**
	 * 月台预分配
	 * 
	 * @return
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	public String predistribute() {
		PlatformPreAssignEntity info = platformDistributeVo.getPlatformPreAssignEntity();
		try {
			platformDistributeService.preAssign(info);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}
}
