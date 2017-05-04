package com.deppon.foss.module.transfer.platform.server.action;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IForkliftGoodsEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverOrgEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForkliftGoodsStayDurationDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.ForkliftGoodsEfficiencyVo;


public class ForkliftGoodsEfficiencyAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 437445501863859244L;

	private static final Logger LOG = LoggerFactory.getLogger(ForkliftGoodsEfficiencyAction.class);
	
	private IForkliftGoodsEfficiencyService forkliftGoodsEfficiencyService;
	private ForkliftGoodsEfficiencyVo forkliftGoodsEfficiencyVo = new ForkliftGoodsEfficiencyVo();
	
	public void setForkliftGoodsEfficiencyService(
			IForkliftGoodsEfficiencyService forkliftGoodsEfficiencyService) {
		this.forkliftGoodsEfficiencyService = forkliftGoodsEfficiencyService;
	}
	public ForkliftGoodsEfficiencyVo getForkliftGoodsEfficiencyVo() {
		return forkliftGoodsEfficiencyVo;
	}
	public void setForkliftGoodsEfficiencyVo(
			ForkliftGoodsEfficiencyVo forkliftGoodsEfficiencyVo) {
		this.forkliftGoodsEfficiencyVo = forkliftGoodsEfficiencyVo;
	}


	/**
	 * 装卸车效率管理跳转首页
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String forkliftGoodsEfficiencyIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = forkliftGoodsEfficiencyService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				forkliftGoodsEfficiencyVo.setTransferCenterCode(transferCenter
						.get("code"));
				forkliftGoodsEfficiencyVo.setTransferCenterName(transferCenter
						.get("name"));
				forkliftGoodsEfficiencyVo.setOperationDeptCode(null);
				forkliftGoodsEfficiencyVo.setOperationDeptName(null);
			} else {
				Map<String, String> operationDept = this.forkliftGoodsEfficiencyService
						.queryOperationDeptCode(currentDeptCode);
				if (operationDept != null) {
					forkliftGoodsEfficiencyVo.setTransferCenterCode(null);
					forkliftGoodsEfficiencyVo.setTransferCenterName(null);
					forkliftGoodsEfficiencyVo.setOperationDeptCode(operationDept
							.get("code"));
					forkliftGoodsEfficiencyVo.setOperationDeptName(operationDept
							.get("name"));
				} else {

					forkliftGoodsEfficiencyVo.setTransferCenterCode(null);
					forkliftGoodsEfficiencyVo.setTransferCenterName(null);
					forkliftGoodsEfficiencyVo.setOperationDeptCode(null);
					forkliftGoodsEfficiencyVo.setOperationDeptName(null);
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/**
	 * 电叉分货效率   转运场
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	public String queryForkliftGoodsEfficiencyOfTfr(){
		try{
			ForkliftEffEntity forkliftEffEntity = new ForkliftEffEntity();
			forkliftEffEntity.setStaDate(forkliftGoodsEfficiencyVo.getStaDate());
			forkliftEffEntity.setTransferCenterCode(forkliftGoodsEfficiencyVo.getTransferCenterCode());
			forkliftEffEntity.setOperationDeptCode(forkliftGoodsEfficiencyVo.getOperationDeptCode());
			List<ForkliftEffEntity> list = this.forkliftGoodsEfficiencyService.queryForkliftGoodsEfficiencyOfTfr(forkliftEffEntity);
			forkliftGoodsEfficiencyVo.setForkliftEffList(list);
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/***
	 * 电叉分货效率   叉车司机所属部门
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	public String queryForkliftGoodsEfficiencyOfOrg(){
		try{
			ForkliftDriverEffEntity forkliftDriverEffEntity = new ForkliftDriverEffEntity();
			forkliftDriverEffEntity.setStaDate(forkliftGoodsEfficiencyVo.getStaDate());
			forkliftDriverEffEntity.setTransferCenterCode(forkliftGoodsEfficiencyVo.getTransferCenterCode());
			forkliftDriverEffEntity.setOperationDeptCode(forkliftGoodsEfficiencyVo.getOperationDeptCode());
			forkliftDriverEffEntity.setDriverCode(forkliftGoodsEfficiencyVo.getDriverCode());
			List<ForkliftDriverOrgEffEntity> list = this.forkliftGoodsEfficiencyService.queryForkliftGoodsEfficiencyOfOrg(forkliftDriverEffEntity,this.getStart(),this.getLimit());
			Long count = this.forkliftGoodsEfficiencyService.queryForkliftGoodsEfficiencyOfOrgCount(forkliftDriverEffEntity);
			super.setTotalCount(count);
			forkliftGoodsEfficiencyVo.setForkliftDriverOrgEffList(list);
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/**
	 * 电叉分货效率  叉车司机
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	public String queryForkliftGoodsEfficiencyOfDriver(){
		try{
			ForkliftDriverEffEntity forkliftDriverEffEntity = new ForkliftDriverEffEntity();
			forkliftDriverEffEntity.setStaDate(forkliftGoodsEfficiencyVo.getStaDate());
			forkliftDriverEffEntity.setTransferCenterCode(forkliftGoodsEfficiencyVo.getTransferCenterCode());
			forkliftDriverEffEntity.setDriverOrgCode(forkliftGoodsEfficiencyVo.getDriverOrgCode());
			List<ForkliftDriverEffEntity> list = this.forkliftGoodsEfficiencyService.queryForkliftGoodsEfficiencyOfDriver(forkliftDriverEffEntity);
			forkliftGoodsEfficiencyVo.setForkliftDriverEffList(list);
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/***
	 * 外场待叉区停留时长占比 查询主界面
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	public String forkliftStayDurationIndex(){
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = forkliftGoodsEfficiencyService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				forkliftGoodsEfficiencyVo.setTransferCenterCode(transferCenter
						.get("code"));
				forkliftGoodsEfficiencyVo.setTransferCenterName(transferCenter
						.get("name"));
				forkliftGoodsEfficiencyVo.setOperationDeptCode(null);
				forkliftGoodsEfficiencyVo.setOperationDeptName(null);
			} else {
				Map<String, String> operationDept = this.forkliftGoodsEfficiencyService
						.queryOperationDeptCode(currentDeptCode);
				if (operationDept != null) {
					forkliftGoodsEfficiencyVo.setTransferCenterCode(null);
					forkliftGoodsEfficiencyVo.setTransferCenterName(null);
					forkliftGoodsEfficiencyVo.setOperationDeptCode(operationDept
							.get("code"));
					forkliftGoodsEfficiencyVo.setOperationDeptName(operationDept
							.get("name"));
				} else {

					forkliftGoodsEfficiencyVo.setTransferCenterCode(null);
					forkliftGoodsEfficiencyVo.setTransferCenterName(null);
					forkliftGoodsEfficiencyVo.setOperationDeptCode(null);
					forkliftGoodsEfficiencyVo.setOperationDeptName(null);
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/**
	 * 查询待叉区停留时长占比
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	public String queryForkliftStayDuration(){
		try{
			ForkliftEffEntity forkliftEffEntity = new ForkliftEffEntity();
			forkliftEffEntity.setStaDate(forkliftGoodsEfficiencyVo.getStaDate());
			forkliftEffEntity.setTransferCenterCode(forkliftGoodsEfficiencyVo.getTransferCenterCode());
			forkliftEffEntity.setOperationDeptCode(forkliftGoodsEfficiencyVo.getOperationDeptCode());
			List<ForkliftGoodsStayDurationDto> list = this.forkliftGoodsEfficiencyService.queryForkliftStayDuration(forkliftEffEntity);
			forkliftGoodsEfficiencyVo.setForkliftGoodsStayDurationList(list);
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
}
