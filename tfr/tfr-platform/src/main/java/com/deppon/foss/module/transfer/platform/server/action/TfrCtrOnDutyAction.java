package com.deppon.foss.module.transfer.platform.server.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrOnDutyService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrOnDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrOnDutyQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TfrCtrOnDutyVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.DateUtils;

public class TfrCtrOnDutyAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private ITfrCtrOnDutyService tfrCtrOnDutyService;

	private IPlatformCommonService platformCommonService;

	private TfrCtrOnDutyVo tfrCtrOnDutyVo = new TfrCtrOnDutyVo();

	public void setTfrCtrOnDutyService(ITfrCtrOnDutyService tfrCtrOnDutyService) {
		this.tfrCtrOnDutyService = tfrCtrOnDutyService;
	}

	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	public TfrCtrOnDutyVo getTfrCtrOnDutyVo() {
		return tfrCtrOnDutyVo;
	}

	public void setTfrCtrOnDutyVo(TfrCtrOnDutyVo tfrCtrOnDutyVo) {
		this.tfrCtrOnDutyVo = tfrCtrOnDutyVo;
	}

	/**
	 * @desc 入口函数
	 * @return
	 * @date 2015年8月12日下午7:20:46
	 */
	@JSON
	public String tfrCtrOnDutyIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity transferCenter = platformCommonService
				.querySupTfrCtr(currentDeptCode);

		String tfrCtrCode = null;
		String tfrCtrName = null;
		if (transferCenter != null) {
			tfrCtrCode = transferCenter.getCode();
			tfrCtrName = transferCenter.getName();
		}
		tfrCtrOnDutyVo.setTfrCtrCode(tfrCtrCode);
		tfrCtrOnDutyVo.setTfrCtrName(tfrCtrName);

		return SUCCESS;
	}

	/**
	 * @desc 新增
	 * @return
	 * @date 2015年8月12日下午7:20:53
	 */
	@JSON
	public String insertTfrCtrOnDuty() {
		List<TfrCtrOnDutyEntity> tfrCtrOnDutys = tfrCtrOnDutyVo
				.getTfrCtrOnDutys();
		try {
			tfrCtrOnDutyService.insertTfrCtrOnDuty(tfrCtrOnDutys);
		} catch (BusinessException e) {
			returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 新增特殊人员
	 * @return
	 * @date 2015年8月12日下午7:20:58
	 */
	@JSON
	public String insertSpecial() {
		TfrCtrOnDutyEntity parameter = tfrCtrOnDutyVo.getTfrCtrOnDuty();
		try {
			tfrCtrOnDutyService.insertSpecial(parameter);
		} catch (BusinessException e) {
			returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 修改
	 * @return
	 * @date 2015年8月12日下午7:22:25
	 */
	@JSON
	public String updateTfrCtrOnDuty() {
		TfrCtrOnDutyEntity parameter = tfrCtrOnDutyVo.getTfrCtrOnDuty();
		try {
			tfrCtrOnDutyService.updateTfrCtrOnDuty(parameter);
		} catch (BusinessException e) {
			returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 删除
	 * @return
	 * @date 2015年8月12日下午7:21:24
	 */
	@JSON
	public String deleteTfrCtrOnDuty() {
		tfrCtrOnDutyService.deleteTfrCtrOnDuty(tfrCtrOnDutyVo.getId());
		return SUCCESS;
	}

	/**
	 * @desc 查询
	 * @return
	 * @date 2015年8月12日下午7:21:32
	 */
	@JSON
	public String findTfrCtrOnDutys() {
		TfrCtrOnDutyQcDto parameter = tfrCtrOnDutyVo.getQueryParam();

		List<TfrCtrOnDutyEntity> tfrCtrOnDutys = tfrCtrOnDutyService
				.findTfrCtrOnDutys(parameter, start, limit);

		Long totalCount = tfrCtrOnDutyService.cntTfrCtrOnDutys(parameter);

		tfrCtrOnDutyVo.setTfrCtrOnDutys(tfrCtrOnDutys);
		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * @desc 新增时查询
	 * @return
	 * @date 2015年8月12日下午7:21:36
	 */
	@JSON
	public String findInfos4Add() {
		TfrCtrOnDutyQcDto parameter = tfrCtrOnDutyVo.getQueryParam();
		List<TfrCtrOnDutyEntity> tfrCtrOnDutysList = tfrCtrOnDutyService
				.findInfos4Add(parameter);

		/* 2016-4-21 322610
		 * 2015年DP-零担-中转-外场人员出勤信息界面优化用户需求V0.1
		 */
		if(tfrCtrOnDutysList.size() > 0){
			Date tmpDatelimit = DateUtils.addDate(parameter.getOnDutyDate(), Calendar.DATE, -1);
			parameter.setBeginDate(tmpDatelimit);
			parameter.setEndDate(tmpDatelimit);
			List<TfrCtrOnDutyEntity> tmpTfrCtrOnDutys = tfrCtrOnDutyService
					.findTfrCtrOnDutys(parameter, start, limit);
			
			List<String> userCode = new ArrayList<String>();
			for(int index=0; index<tmpTfrCtrOnDutys.size(); index++){
				TfrCtrOnDutyEntity tmpTfrCtrOnDutyEntity = tmpTfrCtrOnDutys.get(index);
				//若前一次录入上班开始日期与结束日期不一致时
				if(isNotEqual(tmpTfrCtrOnDutyEntity.getBeginTime(), 
						tmpTfrCtrOnDutyEntity.getEndTime())){
					// 该用户上班结束日期需要加一天
					userCode.add(tmpTfrCtrOnDutyEntity.getEmpCode());
				}
			}

			for(int index=0; index<tfrCtrOnDutysList.size(); index++){
				TfrCtrOnDutyEntity tmp = tfrCtrOnDutysList.get(index);
				if(userCode.contains(tmp.getEmpCode())){
					//在上班开始日期上加一天
					tmp.setEndDatePart(DateUtils.addDate(parameter.getOnDutyDate(), Calendar.DATE, 1));
					tfrCtrOnDutysList.set(index, tmp);
					
				}
			}
			
		}
		
		tfrCtrOnDutyVo.setTfrCtrOnDutys(tfrCtrOnDutysList);
		return SUCCESS;
	}
	/**
	 * 日期比较
	 * @author foss-meiying
	 * @date 2013-6-12 下午8:56:58
	 * @param map
	 * @return true: 日期相等
	 * @see
	 */
	private Boolean isNotEqual(Date date1, Date date2) {
		// 日期格式化
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		String todayStr = format.format(date1); 
		String signStr = format.format(date2);
		if(todayStr.equals(signStr)){ //日期相等
            return false;  
        }  
        else{ //日期不等
            return true;  
        }  
	}
}
