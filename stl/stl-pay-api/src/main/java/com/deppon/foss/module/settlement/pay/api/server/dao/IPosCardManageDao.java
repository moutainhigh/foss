/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.api.server.dao
 * FILE    NAME: IBillAdvanceApplysManageDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;


/**
 * POS刷卡管理
 * 
 * @ClassName: IBankReportDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-14 上午9:10:58
 */
public interface IPosCardManageDao {
	/**
	 * 查询POS刷卡实体集合
	 * 
	 * @Title: queryPosCardEntitys
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<PosCardEntity> queryPosCardEntitys(PosCardManageDto dto,
			int start, int limit);

	/**
	 * 根据日期去查询总行数
	 * 
	 * @Title: queryPosCardEntitys
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int getCountByDate(PosCardManageDto dto);

	/**
	 * 查询POS刷卡明细集合
	 * 
	 * @Title: queryPosCardEntitys
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<PosCardDetailEntity> queryPosCardDetail(PosCardManageDto dto);

	/**
	 * 更具交易流水号去查询POS刷卡实体集合
	 * 
	 * @Title: queryPosCardEntitys
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<PosCardEntity> queryPosCardBySerialNos(List<String> serialNos);

	/**
	 * 查询导出数据
	 * 
	 * @Title: queryPosCardEntitys
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<PosCardEntity> queryExportData(List<String> serialNos);

	/**
	 * 更具单据号去查询POS刷卡实体集合
	 * 
	 * @Title: queryPosCardByNumbers
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<PosCardEntity> queryPosCardByNumbers(List<String> serialNos,String empCode);

	/**
	 * POS机刷卡管理--按流水号查询流水信息
	 *
	 * @Title: queryPosCardBySerialNos
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-7 下午12:40:37
	 */
	public List<PosCardEntity> queryPosCardBySerialNosAndEmpCode(List<String> asList,
			String empCode);

}
