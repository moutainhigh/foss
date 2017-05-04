package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IBigSubTypeDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IBigSubTypeService;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes;
import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

public class BigSubTypeService implements IBigSubTypeService {

	private IBigSubTypeDao bigSubTypeDao;

	@Override
	public List<BigSubTypeEntity> queryBigType(BigSubTypeEntity dto) {

		if (dto == null) {
			throw new SettlementException("参数为空！");
		}
		
		if (StringUtils.isEmpty(dto.getTableName())) {
			throw new SettlementException("明细表名为空！");
		}
		
		String tableName="T_STL_MVR_"+dto.getTableName();
		dto.setTableName(tableName);
		
		List<BigSubTypeEntity> resultList = bigSubTypeDao.queryBigType(dto);

		if (resultList.size() > 0) {
			return resultList;
		} else
			return null;
	}
	
	@Override
	public List<BigSubTypeEntity> queryAllTypes(BigSubTypeEntity dto) {
		if (dto == null) {
			throw new SettlementException("参数为空！");
		}
		
		if (StringUtils.isEmpty(dto.getTableName())) {
			throw new SettlementException("明细表名为空！");
		}
		
		String tableName="T_STL_MVR_"+dto.getTableName();
		dto.setTableName(tableName);
		
		List<BigSubTypeEntity> resultList = bigSubTypeDao.queryAllTypes(dto);

		if (resultList.size() > 0) {
			return resultList;
		} else
			return null;
	}
	
	/**
	 * 根据报表类型得到明细视图名称
	 * 
	 * @param rptType
	 * @return
	 */
	private String getViewName(String rptType) {

		if (VDRptTypes.NRFONO.equals(rptType)) {
			return "V_STL_DVD_NRFONO";
		}

		else if (VDRptTypes.NRFOSO.equals(rptType)) {
			return "V_STL_DVD_NRFOSO";
		}
		if (VDRptTypes.NRFONT.equals(rptType)) {
			return "V_STL_DVD_NRFONT";
		}

		if (VDRptTypes.NRFOST.equals(rptType)) {
			return "V_STL_DVD_NRFOST";
		}

		if (VDRptTypes.NRFDO.equals(rptType)) {
			return "V_STL_DVD_NRFDO";
		}

		if (VDRptTypes.NRFDT.equals(rptType)) {
			return "V_STL_DVD_NRFDT";
		}

		return validaType(rptType);
	}

	private String validaType(String rptType) {
		if (VDRptTypes.NPLR.equals(rptType)) {
			return "V_STL_DVD_NPLR";
		}

		if (VDRptTypes.NAFR.equals(rptType)) {
			return "V_STL_DVD_NAFR";
		}

		if (VDRptTypes.NRFI.equals(rptType)) {
			return "V_STL_DVD_NRFI";
		}

		if (VDRptTypes.NPLI.equals(rptType)) {
			return "V_STL_DVD_NPLI";
		}

		if (VDRptTypes.NAFI.equals(rptType)) {
			return "V_STL_DVD_NAFI";
		}

		if (VDRptTypes.DCO.equals(rptType)) {
			return "V_STL_DVD_DCO";
		}
        if (VDRptTypes.DCD.equals(rptType)) {
            return "V_STL_DVD_DCD";
        }
        if (VDRptTypes.DCI.equals(rptType)) {
            return "V_STL_DVD_DCI";
        } else {
			throw new SettlementException("不支持的报表类型");
		}
	}

	@Override
	public List<BigSubTypeEntity> querySubType(BigSubTypeEntity dto) {
		if (StringUtils.isEmpty(dto.getTableName())) {
			throw new SettlementException("明细表名为空！");
		}

		if (StringUtils.isEmpty(dto.getBigTypeCode())) {
			throw new SettlementException("报表大类为空！");
		}
		
		String tableName="T_STL_MVR_"+dto.getTableName();
		dto.setTableName(tableName);
		
		List<BigSubTypeEntity> resultList = bigSubTypeDao.querySubType(dto);

		if (resultList.size() > 0) {
			return resultList;
		} else
			return null;
	}

	public void setBigSubTypeDao(IBigSubTypeDao bigSubTypeDao) {
		this.bigSubTypeDao = bigSubTypeDao;
	}

	

}
