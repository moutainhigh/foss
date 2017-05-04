package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.unload.api.server.dao.IManualInputDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IManualInputService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputForkVoteEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputGoodsQtyEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ManualInputQcDto;
import com.deppon.foss.util.UUIDUtils;

public class ManualInputService implements IManualInputService {

	private IManualInputDao manualInputDao;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setManualInputDao(IManualInputDao manualInputDao) {
		this.manualInputDao = manualInputDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	private Date getInputDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date inputDate = calendar.getTime();

		return inputDate;
	}

	private String getInputMonth(Date date) {
		String inputMonth = new SimpleDateFormat("yyyy-MM").format(date);
		return inputMonth;
	}

	@Override
	public Map<String, String> queryParentTfrCtrCode(String code) {
		Map<String, String> result = null;

		List<String> bizTypesList = new ArrayList<String>();

		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}

		return result;
	}

	@Override
	public List<ManualInputForkVoteEntity> queryForkVoteEntities(
			ManualInputQcDto dto) {
		return manualInputDao.queryForkVoteEntities(dto);
	}

	private void attachBaseInfo(ManualInputForkVoteEntity entity) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();
		Date nowDate = new Date();

		entity.setCreateUserCode(userCode);
		entity.setCreateUserName(userName);
		entity.setCreateTime(nowDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyUserName(userName);
		entity.setModifyTime(nowDate);

		entity.setId(UUIDUtils.getUUID());
		
		entity.setInputDate(getInputDate(nowDate));
		entity.setInputMonth(getInputMonth(nowDate));
	}

	@Override
	public void insertForkVoteEntity(ManualInputForkVoteEntity entity) {

		attachBaseInfo(entity);

		String transferCenterCode = entity.getTransferCenterCode();
		Date inputDate = entity.getInputDate();

		ManualInputQcDto qcDto = new ManualInputQcDto();
		qcDto.setTransferCenterCode(transferCenterCode);
		qcDto.setInputDate(inputDate);

		String id = manualInputDao.queryForkVoteId(qcDto);
		if (StringUtils.isEmpty(id)) {
			try {
				manualInputDao.insertForkVoteEntity(entity);
			} catch (DuplicateKeyException e) {
				manualInputDao.updateForkVoteEntity(entity);
			}
		} else {
			entity.setId(id);
			manualInputDao.updateForkVoteById(entity);
		}
	}

	@Override
	public List<ManualInputGoodsQtyEntity> queryGoodsQtyEntities(
			ManualInputQcDto dto) {
		return manualInputDao.queryGoodsQtyEntities(dto);
	}

	private void attachBaseInfo(ManualInputGoodsQtyEntity entity) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();
		Date nowDate = new Date();

		entity.setCreateUserCode(userCode);
		entity.setCreateUserName(userName);
		entity.setCreateTime(nowDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyUserName(userName);
		entity.setModifyTime(nowDate);

		entity.setId(UUIDUtils.getUUID());
		
		entity.setInputDate(getInputDate(nowDate));
		entity.setInputMonth(getInputMonth(nowDate));
	}

	@Override
	public void insertGoodsQtyEntity(ManualInputGoodsQtyEntity entity) {

		attachBaseInfo(entity);

		String transferCenterCode = entity.getTransferCenterCode();
		Date inputDate = entity.getInputDate();

		ManualInputQcDto qcDto = new ManualInputQcDto();
		qcDto.setTransferCenterCode(transferCenterCode);
		qcDto.setInputDate(inputDate);

		String id = manualInputDao.queryGoodsQtyId(qcDto);
		if (StringUtils.isEmpty(id)) {
			try {
				manualInputDao.insertGoodsQtyEntity(entity);
			} catch (DuplicateKeyException e) {
				manualInputDao.updateGoodsQtyEntity(entity);
			}
		} else {
			entity.setId(id);
			manualInputDao.updateGoodsQtyById(entity);
		}
	}
}
