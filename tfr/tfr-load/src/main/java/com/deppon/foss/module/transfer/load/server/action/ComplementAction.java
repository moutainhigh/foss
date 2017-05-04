package com.deppon.foss.module.transfer.load.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.load.api.server.service.IComplementService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AsyncComplementFailedQcDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementResultDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ComplementVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * @className: ComplementAction
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码action
 * @date: 2013-7-23 下午1:41:29
 * 
 */
public class ComplementAction extends AbstractAction {

	private static final long serialVersionUID = -649687892342343231L;

	/**
	 * vo对象
	 */
	private ComplementVo complementVo = new ComplementVo();

	/**
	 * 本模块service
	 */
	private IComplementService complementService;

	/**
	 * 查询界面跳转action
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:44:10
	 */
	public String complementQueryIndex() {
		String transferCenterCode = complementService.queryTransferCenterCode();
		complementVo.setTransferCenterCode(transferCenterCode);
		
		//补码部门，用于失败界面；若当前用户属于外场或外场子部门，则取外场，否则区当前用户所属部门
		String tfrCtrCode = FossUserContext.getCurrentDeptCode();
		String tfrCtrName = FossUserContext.getCurrentDeptName();
		OrgAdministrativeInfoEntity tfrCtr = complementService.querySupTfrCtr(tfrCtrCode);
		if(tfrCtr != null){
			tfrCtrCode = tfrCtr.getCode();
			tfrCtrName = tfrCtr.getName();
		}
		complementVo.setTfrCtrCode(tfrCtrCode);
		complementVo.setTfrCtrName(tfrCtrName);
		
		return returnSuccess();
	}

	/**
	 * 查询补码
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午4:28:15
	 */
	public String queryComplementList() {
		ComplementQueryDto queryDto = complementVo.getQueryComplementDto();
		/////////////335284 2016年9月9日 15:11:14////////////////////////
		queryDto = complementService.configComplementQueryInfo(queryDto);
		/////////////////335284 2016年9月9日 15:11:14////////////////////
		List<ComplementQueryDto> complementList = complementService
				.queryComplementList(queryDto, this.getStart(), this.getLimit());
		Long totalCount = complementService.queryComplementCount(queryDto);
		complementVo.setComplementList(complementList);
		setTotalCount(totalCount);
		return returnSuccess();
	}

	/**
	 * 查询补码退回界面
	 * 
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午4:28:15
	 */
	public String queryComplementBackList() {
		ComplementQueryDto queryDto = complementVo.getQueryComplementDto();
		///////////// 335284 2016年9月9日 15:11:14////////////////////////
		queryDto = complementService.configComplementBackQueryInfo(queryDto);
		///////////// 335284 2016年9月9日 15:11:14////////////////////////
		List<ComplementQueryDto> complementList = complementService
				.queryComplementBackList(queryDto, this.getStart(), this.getLimit());
		Long totalCount = complementService.queryComplementBackCount(queryDto);
		complementVo.setComplementList(complementList);
		setTotalCount(totalCount);
		return returnSuccess();
	}
	/**
	 * 补码
	 * 补码界面的手动补码都改成异步的，此方法作废
	 * @author 045923-foss-shiwei
	 * @date 2013-7-24 下午3:03:21
	 */
	@Deprecated
	@JSON
	public String complementPkpOrg() {
		// 获取前台传入参数
		List<String> waybillNoList = complementVo.getWaybillNoList();
		String pkpOrgCode = complementVo.getPkpOrgCode();
		String pkpOrgName = complementVo.getPkpOrgName();

		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			// 定义待返回的结果
			List<ComplementResultDto> resultList = new ArrayList<ComplementResultDto>();
			for (String waybillNo : waybillNoList) {
				ComplementResultDto dto = new ComplementResultDto();
				// 运单号
				dto.setWaybillNo(waybillNo);
			try {
					complementService.complementPkpOrg(waybillNo, pkpOrgCode,
							pkpOrgName,FossConstants.NO, null);
					// 成功
					dto.setBeSuccess(FossConstants.YES);
				} catch (BusinessException e) {
					dto.setBeSuccess(FossConstants.NO);
					dto.setMessage(StringUtils.isNotBlank(e.getMessage()) ? e
							.getMessage() : super.messageBundle.getMessage(
							e.getErrorCode(), e.getErrorArguments()));
				} catch (Exception e) {
					// 失败
					dto.setBeSuccess(FossConstants.NO);
					// 失败原因
					dto.setMessage(e.getMessage());
				} finally {
					resultList.add(dto);
				}
			}
			// 返回补码结果
			complementVo.setResultList(resultList);
		}

		return returnSuccess();
	}
	
	
	/**
	 * 补码日志查询界面
	 */
	public String complementLogIndex() {
		return returnSuccess();
	}
	
	/**
	 * 查询补码日志
	 * @return
	 * @date 2013-11-4 上午9:42:30
	 * @author Ouyang
	 */
	@JSON
	public String queryComplementLogList() {
		//获取查询条件
		ComplementLogDto complementLogDto = complementVo.getComplementLogDto();
		
		if(complementLogDto ==null){
			throw new BusinessException("传入查询条件为空，请选中相关条件");
		}
		//获取当前部门
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		
		//设置当前部门
		complementLogDto.setOperationOrgCode(currentDeptCode);

		//查询补码日志记录
		List<ComplementLogEntity> complementLogEntityList = complementService
				.queryComplementLogList(complementLogDto, super.getStart(),
						super.getLimit());
		complementVo.setComplementLogEntityList(complementLogEntityList);
		
		//查询补码日志记录总数
		Long totalCount = complementService.queryComplementLogCount(complementLogDto);
		super.setTotalCount(totalCount);

		return returnSuccess();
	}
	
	/**
	 * 补码补码标签打印界面
	 */
	public String complementLabelPrintIndex(){
		return returnSuccess();
	}
	
	/**
	 * 补码标签打印
	 * @return
	 * @date 2013-11-26 上午11:32:48
	 * @author Ouyang
	 */
	@JSON
	public String printComplementLabel(){
		String waybillNo = complementVo.getWaybillNo();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = complementService.printComplementLabel(waybillNo);
		} catch (BusinessException e) {
			return returnError(e);
		}
		//提货网点名称
		complementVo.setPkpOrgName(map.get("pickupOrgName"));
		//补码简称
		complementVo.setComplementAbbr(map.get("complementAbbr"));
		
		//补码标签打印时间
		complementVo.setComplementLabelPrintDate(new Date());
		
		return returnSuccess();
	}

	public void setComplementService(IComplementService complementService) {
		this.complementService = complementService;
	}

	public ComplementVo getComplementVo() {
		return complementVo;
	}

	public void setComplementVo(ComplementVo complementVo) {
		this.complementVo = complementVo;
	}
	
	/**
	 * @desc 手动补码，用于补码查询的补码界面和退回界面,其实这里只是补码校验，真正补码写数据的部分，由后台job异步执行
	 * @return
	 * @date 2015年12月1日 下午4:34:01
	 */
	@JSON
	public String complementByHand() {
		List<String> waybillNoList = complementVo.getWaybillNoList();

		if (CollectionUtils.isEmpty(waybillNoList)) {
			return SUCCESS;
		}

		String pkpOrgCode = complementVo.getPkpOrgCode();
		String pkpOrgName = complementVo.getPkpOrgName();

		// 定义待返回的结果
		List<ComplementResultDto> resultList = new ArrayList<ComplementResultDto>();
		for (String waybillNo : waybillNoList) {
			ComplementResultDto dto = new ComplementResultDto();
			// 运单号
			dto.setWaybillNo(waybillNo);
			try {
				complementService.complementByHand(waybillNo, pkpOrgCode, pkpOrgName);
				// 成功
				dto.setBeSuccess(FossConstants.YES);
			} catch (BusinessException e) {
				dto.setBeSuccess(FossConstants.NO);
				dto.setMessage(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : super.messageBundle
						.getMessage(e.getErrorCode(), e.getErrorArguments()));
			} catch (Exception e) {
				// 失败
				dto.setBeSuccess(FossConstants.NO);
				// 失败原因
				dto.setMessage(e.getMessage());
			} finally {
				resultList.add(dto);
			}
		}
		// 返回补码结果,其实这里是补码校验是否通过的结果，真正补码写数据的部分，由后台job异步执行
		complementVo.setResultList(resultList);
		return SUCCESS;
	}
	
	/**
	 * @desc 查询异步补码失败的运单信息
	 * @return
	 * @date 2015年12月1日 下午4:37:25
	 */
	@JSON
	public String findAsyncComplementFailed() {
		AsyncComplementFailedQcDto paramter = complementVo.getAsyncComplementFailedQcDto();
		List<AsyncComplementEntity> list = complementService.findAsyncComplementFailed(paramter,
				super.getStart(), super.getLimit());
		Long failedCnt = complementService.cntAsyncComplementFailed(paramter);
		complementVo.setAsyncComplementFailedList(list);
		super.setTotalCount(failedCnt);
		return SUCCESS;
	}
	
	/**
	 * @desc 手动补码，异步补码失败界面,包括补码校验，补码写数据的部分
	 * @return
	 * @date 2015年12月1日 下午4:49:55
	 */
	@JSON
	public String complementFailed(){
		List<String> waybillNoList = complementVo.getWaybillNoList();

		if (CollectionUtils.isEmpty(waybillNoList)) {
			return SUCCESS;
		}

		String pkpOrgCode = complementVo.getPkpOrgCode();
		String pkpOrgName = complementVo.getPkpOrgName();

		// 定义待返回的结果
		List<ComplementResultDto> resultList = new ArrayList<ComplementResultDto>();
		for (String waybillNo : waybillNoList) {
			ComplementResultDto dto = new ComplementResultDto();
			// 运单号
			dto.setWaybillNo(waybillNo);
			try {
				complementService.complement4Failed(waybillNo, pkpOrgCode, pkpOrgName);
				dto.setBeSuccess(FossConstants.YES);
			} catch (BusinessException e) {
				dto.setBeSuccess(FossConstants.NO);
				dto.setMessage(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : super.messageBundle
						.getMessage(e.getErrorCode(), e.getErrorArguments()));
			} catch (Exception e) {
				// 失败
				dto.setBeSuccess(FossConstants.NO);
				// 失败原因
				dto.setMessage(e.getMessage());
			} finally {
				resultList.add(dto);
			}
		}
		// 返回补码结果
		complementVo.setResultList(resultList);
		return SUCCESS;
	}
}
