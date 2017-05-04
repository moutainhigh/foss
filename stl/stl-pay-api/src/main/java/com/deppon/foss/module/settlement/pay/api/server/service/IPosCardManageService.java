package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;


/**
 * 银行卡报表Service
 * 
 * @ClassName: IBankReportService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-12 下午5:29:41
 */
public interface IPosCardManageService {
	/**
	 * 查询应银行卡报表
	 * 
	 * @Title: queryBankReport
	 * @author： 269052 |zhouyuan008@deppon.com
	 */

	public PosCardManageDto queryPosCard(PosCardManageDto dto, int start,
			int limit);

	/**
	 * 根据流水号查询明细
	 * 
	 * @Title: queryBankReportDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public PosCardManageDto queryPosCardDetail(PosCardManageDto dto);

	/**
	 * 导出EXCEL
	 * 
	 * @Title: queryBankReportDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public HSSFWorkbook exportPosCard(PosCardManageDto dto, int start, int limit);

	/**
	 * 按单据号查询流水号信息
	 * 
	 * @Title: queryBankReportDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public PosCardManageDto queryPosCardByNumber(PosCardManageDto dto);
	
	/**
	 * 导入异常数据
	 * 
	 * @Title: inportExceptionData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-28 下午12:39:50
	 */
	String importExceptionData(String param);

	/**
	 * 修改异常金额
	 * 
	 * @Title: updateExceptionMoney
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-28 下午12:40:37
	 */
	String updateExceptionMoney(String param);

	/**
	 * POS机刷卡管理--按流水号查询流水信息
	 *
	 * @Title: queryPosCardBySerialNos
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-7 下午12:40:37
	 */
	public PosCardManageDto queryPosCardBySerialNos(
			PosCardManageDto posCardManageDto);
	
	/**
	 * POS机刷卡异常管理--删除导入的异常数据,并同步财务自助(事务)
	 *
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午12:40:37
	 */
	String deleteExceptionDataTX(String param);

	/**
	 * POS机刷卡异常管理--冻结交易流水金额
	 *
	 * @Title: frozenPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13 下午12:40:37
	 */
	public void frozenPostCard(List<PosCardEntity> posCardEntitys);

	/**
	 * POS机刷卡异常管理--取消冻结交易流水金额
	 *
	 * @Title: thawPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13 下午12:40:37
	 */
	public void thawPostCard(List<PosCardEntity> posCardEntitys);
}
