/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年3月2日 下午9:09:30
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IWscWayBillManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.WscWayBillManageVo;
import com.deppon.foss.util.DateUtils;

/**
 * 
* @description: 待刷卡运单管理
* @className: WscWayBillManageAction
* 
* @author panshiqi 309613
* @date 2016年3月2日 下午9:10:05 
*
 */
public class WscWayBillManageAction extends AbstractAction {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(WscWayBillManageAction.class);


	/**
	 * 定义异常返回信息
	 */
	private String errorMessage;

	/**
	 * 前后台传参vo
	 */
	private WscWayBillManageVo vo = new WscWayBillManageVo();

	/**
	 *注入service 
	 */
	private IWscWayBillManageService stlPayWscWayBillManageService;

	/**
	 * 导出excel的文件名称
	 */
	private String excelName;

	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream exportStream;

	/**
	 * Ext排序字段
	 */
	private String sort;

	/**
	 * Ext排序方向
	 */
	private String dir;

	/**
	 * 勾选的导出数据ID
	 */
	private String[] exportWscWayBillIds;

	/**
	 * 
	* @description: 根据查询条件获取待刷卡运单数据
	* @title: queryWscWayBill
	* @author panshiqi 309613
	* @date 2016年3月2日 下午10:13:42 
	* @return
	 */
	@JSON
	public String queryWscWayBill() {
		try {

			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 当前用户对象为空或当前登录部门编码为空，返回异常
			if (currentInfo == null || "".equals(currentInfo.getCurrentDeptCode())) {
				throw new SettlementException("获取登录用户信息失败,请重新登录！");
			}

			// 验证查询开始时间
			if (vo.getWscWayBillDto().getQueryStartDate() == null) {
				throw new SettlementException("查询开始日期不能为空！");
			}

			// 验证查询结束时间
			if (vo.getWscWayBillDto().getQueryEndDate() == null) {
				throw new SettlementException("查询结束日期不能为空！");
			}

			// 结束日期加1天
			vo.getWscWayBillDto().setQueryEndDate(
					DateUtils.convert(DateUtils.addDay(vo.getWscWayBillDto().getQueryEndDate(), 1)));

			// 处理数据来源
			if (vo.getWscWayBillDto().getWayBillSource().equals("ALL")) {
				vo.getWscWayBillDto().setWayBillSource("");
			}

			// 处理支付状态
			if (vo.getWscWayBillDto().getPaymentStatus().equals("ALL")) {
				vo.getWscWayBillDto().setPaymentStatus("");
			}

			// 处理有效状态,当前业务上确定只查询有效数据
			vo.getWscWayBillDto().setActive("Y");

			// 限制只能查询登录人所属部门的待刷卡数据
			//vo.getWscWayBillDto().setCurrentDeptCode(currentInfo.getCurrentDeptCode());
			vo.getWscWayBillDto().setCurrentEmpCode(currentInfo.getEmpCode());

			// 查询数据
			WscWayBillManageDto result = new WscWayBillManageDto();
			try {
				result = stlPayWscWayBillManageService.queryBySearchCondition(vo.getWscWayBillDto(), this.getStart(),
						this.getLimit(), currentInfo);
			} catch (Exception e) {
				throw new SettlementException("查询待刷卡运单异常，异常信息：" + e.getMessage());
			}

			// 整合vo
			vo.setWscWayBillDto(result);

			// 设置总条数，分页用
			this.setTotalCount(result.getTotalCount());

			// 返回成功
			return returnSuccess();

		} catch (BusinessException e) {
			logger.error(e.getErrorCode(), e);
			return returnError(e);
		}
	}

	/**
	 * 
	* @description: 导出待刷卡运单数据到Excel
	* @title: exportWscWayBill
	* @author panshiqi 309613
	* @date 2016年3月4日 上午11:30:36 
	* @return
	 */
	public String exportWscWayBill() {
		try {
			logger.debug("导出付款单开始...");

			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 获取选择需要导出的数据ID
			String[] selectedIds = this.getExportWscWayBillIds();

			// 验证选择导出的数据ID
			if (null == selectedIds || selectedIds.length == 0) {
				throw new SettlementException("未勾选需要导出的数据，请先勾选。");
			}

			// 查询待刷卡运单
			WscWayBillManageDto dto = new WscWayBillManageDto();
			try {
				dto = stlPayWscWayBillManageService.queryByIDs(Arrays.asList(selectedIds), currentInfo);
			} catch (Exception e) {
				throw new SettlementException("导出时查询待刷卡运单异常，异常信息：" + e.getMessage());
			}

			// 查询到的结果集不为空时，执行导出操作
			if (dto != null && CollectionUtils.isNotEmpty(dto.getResultList())) {

				// 生成Excel代码
				try {
					// 设置Excel名称
					this.setExcelName(new String(("待刷卡运单").getBytes(SettlementConstants.UNICODE_GBK),
							SettlementConstants.UNICODE_ISO));
				} catch (UnsupportedEncodingException e1) {
					logger.error(e1.getMessage());
				}

				// 限制最大导出行数
				if (dto.getResultList().size() > SettlementConstants.EXPORT_MAX_COUNT) {
					throw new SettlementException("每次最多只能导出" + SettlementConstants.EXPORT_MAX_COUNT + "条数据,请重新查询并导出");
				}

				// 设置Excel工作表,每次最多导出6万条数据
				ExcelExport export = new ExcelExport();
				HSSFWorkbook work = export.exportExcel(fillSheetDataOfWscWayBill(dto), "sheet",
						SettlementConstants.EXPORT_MAX_COUNT);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					// 设置excel字节流
					work.write(baos);
					this.setExportStream(new ByteArrayInputStream(baos.toByteArray()));
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (baos != null) {
						try {
							baos.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}
			} else {
				return returnError("导出数据为空！");
			}

			// 导出成功
			return returnSuccess();

		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 
	* @description: 生成待刷卡运单的excel
	* @title: fillSheetDataOfWscWayBill
	* @author panshiqi 309613
	* @date 2016年3月4日 下午3:02:23 
	* @param dto
	* @return
	 */
	private SheetData fillSheetDataOfWscWayBill(WscWayBillManageDto dto) {

		// 设置Excel sheet 表头
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "序号", "运单号", "待刷卡金额", "开单部门", "开单时间", "刷卡部门", "已刷卡金额", "支付状态", "数据来源" });

		// 获取查询到的运单数据
		List<WSCWayBillEntity> lists = dto.getResultList();

		// 循环处理导出的运单数据
		if (CollectionUtils.isNotEmpty(lists)) {

			// 将数据格式化成：List<List<String>>
			List<List<String>> rowList = new ArrayList<List<String>>();

			// 序号
			long i = 1;

			// 时间格式化对象
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			//String now = df.format(new Date());

			// 循环处理
			for (WSCWayBillEntity entity : lists) {

				// 单元格数据集合
				List<String> cellList = new ArrayList<String>();
				cellList.add(Long.toString(i)); // 序号
				cellList.add(entity.getWayBillNo()); // 运单号
				cellList.add(Double.toString(entity.getWaitSwipeAmount())); // 待刷卡金额
				cellList.add(entity.getCreateBillOrgName()); // 开单部门
				cellList.add(df.format(entity.getCreateBillTime())); // 开单时间
				cellList.add(entity.getSwipeCardOrgName()); // 刷卡部门
				cellList.add(Double.toString(entity.getAlreadySwipeAmount())); // 已刷卡金额
				cellList.add("Y".equals(entity.getPaymentStatus()) ? "已支付" : "未支付"); // 支付状态
				cellList.add("1".equals(entity.getWayBillSource()) ? "运单开单"
						: "2".equals(entity.getWayBillSource()) ? "运单更改" : entity.getWayBillSource()); // 数据来源

				// 序号自增
				i++;

				// 添加数据到行集合
				rowList.add(cellList);
			}

			// 添加数据到excel sheet对象
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}

	/**  
	 * 获取 前后台传参vo  
	 * @return vo 前后台传参vo  
	 */
	public WscWayBillManageVo getVo() {
		return vo;
	}

	/**  
	 * 设置 前后台传参vo  
	 * @param vo 前后台传参vo  
	 */
	public void setVo(WscWayBillManageVo vo) {
		this.vo = vo;
	}

	/**  
	 * 获取 定义异常返回信息  
	 * @return errorMessage 定义异常返回信息  
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**  
	 * 设置 定义异常返回信息  
	 * @param errorMessage 定义异常返回信息  
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**  
	 * 获取 导出excel的文件名称  
	 * @return excelName 导出excel的文件名称  
	 */
	public String getExcelName() {
		return excelName;
	}

	/**  
	 * 设置 导出excel的文件名称  
	 * @param excelName 导出excel的文件名称  
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**  
	 * 获取 导出excel的输入流  
	 * @return exportStream 导出excel的输入流  
	 */
	public ByteArrayInputStream getExportStream() {
		return exportStream;
	}

	/**  
	 * 设置 导出excel的输入流  
	 * @param exportStream 导出excel的输入流  
	 */
	public void setExportStream(ByteArrayInputStream exportStream) {
		this.exportStream = exportStream;
	}

	/**  
	 * 设置 注入service  
	 * @param stlPayWscWayBillManageService 注入service  
	 */
	public void setStlPayWscWayBillManageService(IWscWayBillManageService stlPayWscWayBillManageService) {
		this.stlPayWscWayBillManageService = stlPayWscWayBillManageService;
	}

	/**  
	 * 获取 Ext排序  
	 * @return sort Ext排序  
	 */
	public String getSort() {
		return sort;
	}

	/**  
	 * 设置 Ext排序  
	 * @param sort Ext排序  
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**  
	 * 获取 Ext排序方向  
	 * @return dir Ext排序方向  
	 */
	public String getDir() {
		return dir;
	}

	/**  
	 * 设置 Ext排序方向  
	 * @param dir Ext排序方向  
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**  
	 * 获取 勾选的导出数据ID  
	 * @return exportWscWayBillIds 勾选的导出数据ID  
	 */
	public String[] getExportWscWayBillIds() {
		return exportWscWayBillIds;
	}

	/**  
	 * 设置 勾选的导出数据ID  
	 * @param exportWscWayBillIds 勾选的导出数据ID  
	 */
	public void setExportWscWayBillIds(String[] exportWscWayBillIds) {
		this.exportWscWayBillIds = exportWscWayBillIds;
	}
}
