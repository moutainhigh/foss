package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceEntryService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPlrEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPlrQueryService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 偏线月报表Service.
 *
 * @author 095793-foss-LiQin
 * @date 2013-3-7 上午9:55:37
 */
public class MvrPlrQueryService implements IMvrPlrQueryService {

	/** 日志. */
	private static final Logger LOGGER = LogManager.getLogger(MvrPlrQueryService.class);
		
	/** 偏线月报dao. */
	private IMvrPlrEntityDao mvrPlrDao;
	/**
	 * 产品类型（业务类型）service
	 */
	IPriceEntryService priceService;
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;
	
	/**
	 * 查询偏线月报列表.
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 095793-foss-LiQin
	 * @date 2013-3-12 下午5:35:22
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrPlrQueryService#queryPlrByParam(com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity,
	 * int, int)
	 */
	@Override
	public List<MvrPlrEntity> queryMvrPlrByParam(MvrPlrDto dto, int start,
			int limit) {
		LOGGER.info("查询偏线月报列表 service into ....");
		
		/**
		 * 检查查询输入是否合法
		 */
		checkParam(dto);

		/**
		 * 查询报表返回List
		 */
		List<MvrPlrEntity> mvrPlrQRSList = mvrPlrDao.selectMvrPlrByParam(dto, start, limit);
		
		LOGGER.debug("查询偏线月报列表, 返回条数 :" + mvrPlrQRSList.size());
		LOGGER.info("查询偏线月报列表 service exit ....");
		return mvrPlrQRSList;
	}

	/**
	 * 偏线月报查询时校验输入是否合法.
	 *
	 * @param dto the dto
	 * @author 095793-foss-LiQin
	 * @date 2013-3-12 下午8:00:17
	 */
	private void checkParam(MvrPlrDto dto) {
		/**
		 * 校验前端前端传入dto实体
		 */
		if (null == dto) {
			throw new SettlementException("查询偏线月报的实体为空!");
		}
		/**
		 * 校验前端传入期间不能为空
		 */
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空!");
		}
	}

	
	
	/**
	 * 偏线月报查询总数.
	 *
	 * @param dto the dto
	 * @return the mvr plr dto
	 * @author 095793-foss-LiQin
	 * @date 2013-3-14 上午9:58:20
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrPlrQueryService#queryMvrPlrByParamCount(com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity)
	 */
	@Override
	public MvrPlrDto queryMvrPlrByParamTotal(MvrPlrDto dto) {
		LOGGER.info("查询偏线月报总数 service into ....");
		
		/**
		 * 检查查询输入是否合法
		 */
		checkParam(dto);
		/**
		 * 查询偏线月报的条数
		 */
		MvrPlrDto resultDto = mvrPlrDao.selectMvrPlrByParamCount(dto);
		
		LOGGER.debug("查询偏线月报条数总条数返回 :" +resultDto.getCount());
		LOGGER.info("查询偏线月报总数service exit ....");
		/***
		 * 返回偏线月报总数
		 */
		return resultDto;
	}
	
	
	/** 
	 * 导出偏线月报表
	 * @author 095793-foss-LiQin
	 * @date 2013-3-22 下午5:18:10
	 * @param dto
	 * @param cInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrPlrQueryService#exportMvrPlr(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public ExportResource exportMvrPlr(MvrPlrDto dto, CurrentInfo cInfo) {
		
		// 设置用户数据查询权限
		dto.setUserCode(cInfo.getEmpCode());
		
		//查询偏线报表的总条数		
		MvrPlrDto resultDto=this.queryMvrPlrByParamTotal(dto);
		//导出时，在报表界面明细集合		
		List<MvrPlrEntity> mvrPlrQREntity=null;
	
		//当偏线月报的数据大于零的时候，才执行报表明细		
		if(resultDto.getCount()<0){
			throw new SettlementException("偏线月报表导出时，查询结果集为空！");
		}else{
			//根据条件查询偏线月报表		
			mvrPlrQREntity = mvrPlrDao.selectMvrPlrByParam(dto);
			
			//导出时，明细为空时，抛出的异常信息		
			if(CollectionUtils.isEmpty(mvrPlrQREntity)){
				throw new SettlementException("偏线月报表导出时，结果集为空，不能做导出操作！");
			}
		}

		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		//导出列实体集合		
		String[] columns = entityMvrPlrColumns();
		
		//获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}
		
		if (null!=mvrPlrQREntity) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrPlrEntity entity : mvrPlrQREntity) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					
					// 获取对象的值，如果为空，则设置其为空字符串
					cellValue = (fieldValue == null ? "" : fieldValue.toString());
					
					
					if (column.equals("productName")) {
						
						fieldValue = ReflectionUtils.getFieldValue(entity, "productCode");
						
						// 获取对象的值，如果为空，则设置其为空字符串
						cellValue = (fieldValue == null ? "" : fieldValue.toString());
						
						
						
						//默认的产品类型为空
			    		String productEntityName = "";
			    		//如果数据产品类型编码不为空
			    		if(StringUtils.isNotEmpty(cellValue)){
			    			//将产品类型转换编码为名称
							productEntityName=productMap.get(cellValue);
						}
			    		cellValue = productEntityName;
						
					}
					
					colList.add(cellValue);
				}
				resultList.add(colList);	
			}
		
		}
		
		ExportResource sheet = new ExportResource();
		
		sheet.setHeads(setExportMvrPlrHeads());
		sheet.setHeaderHeight(SettlementReportNumber.THREE);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE,SettlementReportNumber.ZERO,SettlementReportNumber.EIGHT,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE,SettlementReportNumber.NINE,SettlementReportNumber.FOURTEEN,"偏线代理成本");
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE,SettlementReportNumber.FIFTEEN, SettlementReportNumber.EIGHTEEN,"还款运单总运费（到付）");
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE,SettlementReportNumber.NINETEEN, SettlementReportNumber.TWENTY_THREE,"预收偏线代理");
		
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		
		sheet.setHeaderList(headerList);
		sheet.setRowList(resultList);
		
		return sheet;
	}
	
	
	/**
	 * 设置偏线月报的实体列名
	 * @author 095793-foss-LiQin
	 * @date 2013-3-22 下午5:25:05
	 */
	private String[] setExportMvrPlrHeads(){
		String[] heads = {			

				
							//数据统计维度				
							"期间","业务类型编码","业务类型名称","客户编码","客户名称","始发部门编码","始发部门名称","偏线到达部门编码","偏线到达部门名称",
							//偏线代理成本				
							"外发单录入","偏线代理成本确认","偏线代理成本反确认","应付偏线代理成本冲应收到付运费已签收","应付偏线代理成本冲应收到付运费未签收","偏线代理成本付款申请",
							//还款运单总运费（到付）				
							"还款现金未签收","还款银行未签收","还款现金已签收","还款银行已签收",
							//预收偏线代理
							"预收偏线代理现金","预收偏线代理银行","预收偏线代理冲应收到付运费已签收","预收偏线代理冲应收到付运费未签收","偏线退预收付款申请",
															

		
		};
		return heads;
	}
	
	
	
	/**
	 * 设置偏线月报的实体列
	 * @author 095793-foss-LiQin
	 * @date 2013-3-22 下午5:45:11
	 * @return
	 *//** 外发反馈录入. */
	
	private String [] entityMvrPlrColumns(){
		String[] columns = {
								//数据统计维度		
								"period","productCode","productName","customerCode","customerName","origOrgCode","origOrgName","destOrgCode","destOrgName",
								
								//偏线代理成本		
								"plCostVech","plCostConfirm","plCostNotConfirm","plCostWoDestRcvPod","plCostWoDestRcvNpod","plCostPayApply",

								//还款运单总运费（到付）	
								"urDestChNpod","urDestCdNpod","urDestChPod","urDestCdPod",
								
								//预收偏线代理	
								"plDrCh","plDrCd","plDrWoDestRcvPod","plDrWoDestRcvNpod", "plDrPayApply"

							};
		return columns;
	}
	
	
	/**
	 * Gets the mvr plr dao.
	 *
	 * @return mvrPlrDao
	 */
	public IMvrPlrEntityDao getMvrPlrDao() {
		return mvrPlrDao;
	}

	/**
	 * Sets the mvr plr dao.
	 *
	 * @param mvrPlrDao the new mvr plr dao
	 */
	public void setMvrPlrDao(IMvrPlrEntityDao mvrPlrDao) {
		this.mvrPlrDao = mvrPlrDao;
	}

	/**
	 * @return productService
	 */
	public IProductService getProductService() {
		return productService;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
}
