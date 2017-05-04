package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IReverseBillWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.ReverseBillWriteoffVo;
import com.deppon.foss.util.DateUtils;
/**
 * 反核消Action
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 上午10:49:31
 */
public class ReverseBillWriteoffAction extends AbstractAction {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(ReverseBillWriteoffAction.class);

	/**
	 * 反核消Action序列号
	 */
	private static final long serialVersionUID = -539627051054499706L;

	/**
	 * 反核消时，传递参数并返回核销单明细VO类
	 */
	private ReverseBillWriteoffVo reverseBillWriteoffVo = new ReverseBillWriteoffVo();

	/**
	 * 反核消service
	 */
	private IReverseBillWriteoffService reverseBillWriteoffService;

	/**
	 * 导出输出流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 根据传入参数，查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 下午3:19:20
	 */
	public String queryBillWriteoffEntityListByParams() {

		try {
			//记录日志
			logger.debug("查询核销单开始...");

			// 设置查询条件
			setQueryConditionForParam();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 分页查询数据，并设置返回结果
			reverseBillWriteoffVo.setReverseBillWriteoffDto(reverseBillWriteoffService.queryBillWriteoffEntityList(reverseBillWriteoffVo.getReverseBillWriteoffDto(), getStart(),getLimit(), cInfo));
			// 设置返回总条数
			this.setTotalCount(reverseBillWriteoffVo.getReverseBillWriteoffDto().getWriteoffTotalRows());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 反核销
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-29 下午3:07:44
	 */
	public String reverseBillWriteoff() {
		try {
			//记录日志
			logger.debug("反核销核销单开始...");

			// 核销单号不为为空
			if (StringUtils.isNotEmpty(reverseBillWriteoffVo.getReverseBillWriteoffDto().getWriteoffBillNos().trim())) {
				// 获取反核销的核销单号,以,分割成数组
				String[] number = reverseBillWriteoffVo.getReverseBillWriteoffDto().getWriteoffBillNos().split(",");
				// 生成核销单号list
				List<String> writeoffBillNoList = new ArrayList<String>();
				// 循环核销单号数组
				for (int i = 0; i < number.length; i++) {
					// 将核销单号加入核销单号list
					writeoffBillNoList.add(number[i].trim());
				}
				// 设置查询参数dto的核销单号list参数
				reverseBillWriteoffVo.getReverseBillWriteoffDto().setWriteoffBillNoList(writeoffBillNoList);

			} else {
				// 核销单号为空，提示没有选中的待反核销记录
				throw new SettlementException("没有选中的待反核销记录!");
			}

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 查询选中的核销单号数据
			reverseBillWriteoffService.reverseBillWriteOff(reverseBillWriteoffVo.getReverseBillWriteoffDto(), cInfo);

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 导出核销单（符合查询条件的核销单）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 上午10:46:13
	 */
	public String exportReverseWriteoffBill() {

		try {
			//记录日志
			logger.debug("导出核销单开始...");

			// 设置查询条件
			setQueryConditionForParam();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//生成核销单dto
			ReverseBillWriteoffDto dto = null;

			// 查询核销单数据
			dto = reverseBillWriteoffService.queryBillWriteoffEntityAll(reverseBillWriteoffVo.getReverseBillWriteoffDto(), cInfo);

			// 生成Excel代码
			try {

				// 设置Excel名称
				this.setExeclName(new String("核销单报表".getBytes(SettlementConstants.UNICODE_UTF),SettlementConstants.UNICODE_ISO));
			//异常处理
			} catch (UnsupportedEncodingException e1) {
				//记录日志
				logger.error(e1.getMessage(), e1);
			}

			// 查询到的结果集不为空时，执行导出操作
			if (dto != null&& CollectionUtils.isNotEmpty(dto.getBillWriteoffEntityList())) {
				//如果查询数据条数超过限制
				if (dto.getBillWriteoffEntityList().size() > SettlementConstants.EXPORT_MAX_COUNT) {
					//提示每次最多只能导出XXX条数据,请重新查询并导出
					throw new SettlementException("每次最多只能导出"+SettlementConstants.EXPORT_MAX_COUNT+"条数据,请重新查询并导出");
				}

				//生成导出对象
				ExcelExport export = new ExcelExport();
				// 设置每次最多导出6万条数据
				HSSFWorkbook work = export.exportExcel(fillSheetDataByreverseWriteoff(dto), "sheet",SettlementConstants.EXPORT_MAX_COUNT);
				//生成导出输出流
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					//wirte数据流
					work.write(baos);
					//新建导出excel输入流
					inputStream = new ByteArrayInputStream(baos.toByteArray());
				//异常处理	
				} catch (IOException e) {
					//记录日志
					logger.error(e.getMessage(), e);
				} finally {
					//关闭流
					if (baos != null) {
						try {
							//关闭流
							baos.close();
						//异常处理	
						} catch (IOException e) {
							//记录日志
							logger.error(e.getMessage(), e);
						}
					}
				}
			} else {
				//异常返回
				return returnError("导出数据为空！");
			}

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 生产核销单excel详细数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 下午2:13:13
	 */
	private SheetData fillSheetDataByreverseWriteoff(ReverseBillWriteoffDto dto) {
		//生成excel表单
		SheetData sheetData = new SheetData();
		//设置excel表单表头
		sheetData.setRowHeads(new String[] { "核销批次编号", "核销单号", "开始来源运单","目的来源运单", "开始单号", "目的单号", "开始单号记账时间", "目的单号记账时间","对账单号", "核销方式", 
						"核销类型", "核销金额", "核销时间", "核销人编码","核销人名称", "红冲人编码", "红冲人名称", "部门编码", "部门名称", "客户编码","客户名称", "是否有效", "是否红单", "记账日期" });

		//获取核销单列表
		List<BillWriteoffEntity> lists = dto.getBillWriteoffEntityList();
		//因原来在循环里面调用速度比较慢，一次性把所有的缓存获取到
		//生成待获取转换编码
		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);// 是否红单
		types.add(DictionaryConstants.FOSS_ACTIVE);// 是否有效
		types.add(DictionaryConstants.SETTLEMENT__CREATE_TYPE);// 生成方式
		types.add(DictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE);// 核销类型
		//获取全部待转换缓存
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		//如果核销单列表有数据
		if (CollectionUtils.isNotEmpty(lists)) {
			//生成excel数据列
			List<List<String>> rowList = new ArrayList<List<String>>();
			//循环核销单列表
			for (BillWriteoffEntity entity : lists) {
				List<String> cellList = new ArrayList<String>();//新建单元格list
				cellList.add(entity.getWriteoffBatchNo());// 核销批次号
				cellList.add(entity.getWriteoffBillNo());// 核销单号
				cellList.add(entity.getBeginWaybillNo());// 开始来源运单
				cellList.add(entity.getEndWaybillNo());// 目的来源运单
				cellList.add(entity.getBeginNo());// 开始单号
				cellList.add(entity.getEndNo());// 目的单号
				cellList.add(DateUtils.convert(entity.getBeginAccountDate(),DateUtils.DATE_TIME_FORMAT));// 开始单号记账时间
				cellList.add(DateUtils.convert(entity.getEndAccountDate(),DateUtils.DATE_TIME_FORMAT));// 目的单号记账时间
				cellList.add(entity.getStatementBillNo());// 对账单号
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map
						,DictionaryConstants.SETTLEMENT__CREATE_TYPE,entity.getCreateType()));// 核销方式
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE,entity.getWriteoffType()));// 核销类型
				//如果核销金额不为空
				if (entity.getAmount() != null) {
					cellList.add(entity.getAmount().toString());// 核销金额
				//如果核销金额为空
				} else {
					cellList.add("");//核销金额设置为空
				}
				cellList.add(DateUtils.convert(entity.getWriteoffTime(),DateUtils.DATE_TIME_FORMAT));// 核销时间
				cellList.add(entity.getCreateUserCode());// 核销人编码
				cellList.add(entity.getCreateUserName());// 核销人名称
				cellList.add(entity.getRedImpactUserCode());// 红冲人编码
				cellList.add(entity.getRedImpactUserName());// 红冲人名称
				cellList.add(entity.getOrgCode());// 部门编码
				cellList.add(entity.getOrgName());// 部门名称
				cellList.add(entity.getCustomerCode());// 客户编码
				cellList.add(entity.getCustomerName());// 客户名称
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.FOSS_ACTIVE,entity.getActive()));// 是否有效
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.SETTLEMENT__IS_RED_BACK,entity.getIsRedBack()));// 是否红单
				cellList.add(DateUtils.convert(entity.getAccountDate(),DateUtils.DATE_TIME_FORMAT));// 记账日期

				//加入list
				rowList.add(cellList);
			}
			//设置核销excel表单数据列
			sheetData.setRowList(rowList);
		}
		//返回表单信息
		return sheetData;
	}

	/**
	 * 设置查询条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-31 上午10:10:41
	 */
	private void setQueryConditionForParam() {

		// 如果核销业务日期不为空
		if (reverseBillWriteoffVo.getReverseBillWriteoffDto().getStartBusinessDate() != null&& reverseBillWriteoffVo.getReverseBillWriteoffDto().getEndBusinessDate() != null) {
			//设置执行时使用小于结束日期+1天
			String dateEndTemp = DateUtils.addDay(reverseBillWriteoffVo.getReverseBillWriteoffDto().getEndBusinessDate(), 1);
			//转换时间为格式
			Date dateEnd = DateUtils.convert(dateEndTemp);
			//设置查询参数dto的业务结束日期参数
			reverseBillWriteoffVo.getReverseBillWriteoffDto().setEndBusinessDate(dateEnd);
		}

		// 如果核销单号不为空
		if (StringUtils.isNotEmpty(reverseBillWriteoffVo.getReverseBillWriteoffDto().getWriteoffBillNo())) {
			//获取核销单号
			String writeoffNo = reverseBillWriteoffVo.getReverseBillWriteoffDto().getWriteoffBillNo();
			//设置查询参数dto的核销单号参数
			reverseBillWriteoffVo.getReverseBillWriteoffDto().setWriteoffBillNo(writeoffNo.trim());
		}

		// 如果运单号不为空
		if (StringUtils.isNotEmpty(reverseBillWriteoffVo.getReverseBillWriteoffDto().getWaybillNo())) {
			//获取运单号
			String waybillNo = reverseBillWriteoffVo.getReverseBillWriteoffDto().getWaybillNo();
			//设置查询参数dto的运单号参数
			reverseBillWriteoffVo.getReverseBillWriteoffDto().setWaybillNo(waybillNo.trim());
		}

	}

	
	/**
	 * @get
	 * @return reverseBillWriteoffVo
	 */
	public ReverseBillWriteoffVo getReverseBillWriteoffVo() {
		/*
		 * @get
		 * @return reverseBillWriteoffVo
		 */
		return reverseBillWriteoffVo;
	}

	
	/**
	 * @set
	 * @param reverseBillWriteoffVo
	 */
	public void setReverseBillWriteoffVo(ReverseBillWriteoffVo reverseBillWriteoffVo) {
		/*
		 *@set
		 *@this.reverseBillWriteoffVo = reverseBillWriteoffVo
		 */
		this.reverseBillWriteoffVo = reverseBillWriteoffVo;
	}

	
	/**
	 * @get
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		/*
		 * @get
		 * @return inputStream
		 */
		return inputStream;
	}

	
	/**
	 * @set
	 * @param inputStream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}

	
	/**
	 * @get
	 * @return execlName
	 */
	public String getExeclName() {
		/*
		 * @get
		 * @return execlName
		 */
		return execlName;
	}

	
	/**
	 * @set
	 * @param execlName
	 */
	public void setExeclName(String execlName) {
		/*
		 *@set
		 *@this.execlName = execlName
		 */
		this.execlName = execlName;
	}

	
	/**
	 * @set
	 * @param reverseBillWriteoffService
	 */
	public void setReverseBillWriteoffService(
			IReverseBillWriteoffService reverseBillWriteoffService) {
		/*
		 *@set
		 *@this.reverseBillWriteoffService = reverseBillWriteoffService
		 */
		this.reverseBillWriteoffService = reverseBillWriteoffService;
	}

	
}
