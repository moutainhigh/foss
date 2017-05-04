package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayForTfrService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseToCubcCallBack;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPdaPackageMainPriceDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPdaPackageMainPriceService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackingToCubcEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackResultEntity;


/**
 * @desc 主要包装金额service
 * @author foss-105795-wqh
 * @date   2014-04-29
 * */
public class PdaPackageMainPriceService implements IPdaPackageMainPriceService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(PdaPackageMainPriceService.class);
	//主包装接口
	private IPdaPackageMainPriceDao pdaPackageMainPriceDao;
	
	//同步至CUBC
	private IFossToCubcService fossToCubcService;
	
	private IPackingRecAndPayForTfrService packingRecAndPayForTfrService;
	private CUBCGrayUtil cubcUtil;
	
    public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}

	public void setPackingRecAndPayForTfrService(
			IPackingRecAndPayForTfrService packingRecAndPayForTfrService) {
		this.packingRecAndPayForTfrService = packingRecAndPayForTfrService;
	}

	/**
	 * 
	 * 处理保留小数位数
	 * 
	 * **/
    @SuppressWarnings("unused")
	private void handlePoint(PackageMainPriceEntity packageMainPriceEntity)
    {
    	DecimalFormat df = new DecimalFormat("#.00");
		//实际打木架体积
		if(packageMainPriceEntity.getActualFrameVolume()!=null)
		{
			packageMainPriceEntity.setActualFrameVolume(new BigDecimal(
					df.format(packageMainPriceEntity.getActualFrameVolume().doubleValue())));
		}
		//实际打木箱体积
		if(packageMainPriceEntity.getActualWoodenVolume()!=null)
		{
			packageMainPriceEntity.setActualWoodenVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getActualWoodenVolume().doubleValue())));
		}
		//理论打木架体积
		if(packageMainPriceEntity.getTheoryFrameVolume()!=null)
		{
			packageMainPriceEntity.setTheoryFrameVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getTheoryFrameVolume().doubleValue())));
		}
		//理论打木箱体积
		if(packageMainPriceEntity.getTheoryWoodenVolume()!=null)
		{
			packageMainPriceEntity.setTheoryWoodenVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getTheoryWoodenVolume().doubleValue())));
		}
		//打木架体积
		if(packageMainPriceEntity.getPackageFrameVolume()!=null)
		{
			packageMainPriceEntity.setPackageFrameVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getPackageFrameVolume().doubleValue())));
		}
		//打木箱体积
		if(packageMainPriceEntity.getPackageWoodenVolume()!=null)
		{
			packageMainPriceEntity.setPackageWoodenVolume(
					new BigDecimal(df.format(packageMainPriceEntity.getPackageWoodenVolume().doubleValue())));
		}
    	
    }
    
    /**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	@Override
	public List<QueryPDAPackResultEntity> queryPdaPackagePriceByWaybillNoOrMaterial(
			QueryPDAPackConditionEntity pdaQueryPackConditionEntity, int limit,
			int start) {
		if(pdaQueryPackConditionEntity==null)
		{
			throw new TfrBusinessException("查询条件不能为空");
		}
		// 运单号优先
		if (StringUtil.isNotEmpty(pdaQueryPackConditionEntity.getWaybillNo())) {
			pdaQueryPackConditionEntity.setPackageOrgCode(null);
			pdaQueryPackConditionEntity.setPackageOrgName(null);
//			pdaQueryPackConditionEntity.setPackageSupplierName(null);
			pdaQueryPackConditionEntity.setPackageSupplierCode(null);
			pdaQueryPackConditionEntity.setPackedBeginDate(null);
			pdaQueryPackConditionEntity.setPackedEndDate(null);
			pdaQueryPackConditionEntity.setAuditStatus(null);
		}
		
		List<QueryPDAPackResultEntity> queryPDAPackResultList = pdaPackageMainPriceDao
				.queryPdaPackagePriceByWaybillNoOrMaterial(
						pdaQueryPackConditionEntity, limit, start);
		List<QueryPDAPackResultEntity> resultList=handlePoint(queryPDAPackResultList);
		return resultList;
	}
	/**
	 * @desc 处理保留小数位数
	 * @author foss-189284-zx
	 * @date 创建时间：2014-5-21 下午2:55:53
	 * @return List<QueryPDAPackResultEntity>
	 */
	private List<QueryPDAPackResultEntity> handlePoint(
			List<QueryPDAPackResultEntity> queryPDAPackResultEntity) {
		LOGGER.info("处理小数位数开始");
		// 定义返回结果集
		List<QueryPDAPackResultEntity> queryPDAPackResultList = new ArrayList<QueryPDAPackResultEntity>();
		if (queryPDAPackResultEntity == null|| queryPDAPackResultEntity.size() == 0) {
			return null;
		}
		BigDecimal zero = new BigDecimal(0);
		// 循环，单条处理
		for (int i = 0; i < queryPDAPackResultEntity.size(); i++) {
			QueryPDAPackResultEntity queryPDAPackEntity = queryPDAPackResultEntity
					.get(i);
			
			// 实际打木架体积
			if (queryPDAPackEntity.getActualFrameVolume() != null) {
				queryPDAPackEntity.setActualFrameVolume(queryPDAPackEntity.getActualFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setActualFrameVolume(zero);
			}
			// 实际打木箱体积
			if (queryPDAPackEntity.getActualWoodenVolume() != null) {
				queryPDAPackEntity.setActualWoodenVolume(queryPDAPackEntity.getActualWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setActualWoodenVolume(zero);
			}
			// 理论打木架体积
			if (queryPDAPackEntity.getTheoryFrameVolume() != null) {
				queryPDAPackEntity.setTheoryFrameVolume(queryPDAPackEntity.getTheoryFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setTheoryFrameVolume(zero);
			}
			// 理论打木箱体积
			if (queryPDAPackEntity.getTheoryWoodenVolume() != null) {
				queryPDAPackEntity.setTheoryWoodenVolume(queryPDAPackEntity.getTheoryWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setTheoryWoodenVolume(zero);
			}
			// 打木架体积
			if (queryPDAPackEntity.getPackageFrameVolume() != null) {
				queryPDAPackEntity.setPackageFrameVolume(queryPDAPackEntity.getPackageFrameVolume() .setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setPackageFrameVolume(zero);
			}
			// 打木箱体积
			if (queryPDAPackEntity.getPackageWoodenVolume() != null) {
				queryPDAPackEntity.setPackageWoodenVolume(queryPDAPackEntity.getPackageWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setPackageWoodenVolume(zero);
			}

			// 应付金额
			if (queryPDAPackEntity.getPackagePayableMoney() != null) {
				queryPDAPackEntity.setPackagePayableMoney(queryPDAPackEntity.getPackagePayableMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				queryPDAPackEntity.setPackagePayableMoney(zero);
			}
			// 将处理后的结果加入处理结果集中
			queryPDAPackResultList.add(queryPDAPackEntity);
		}
		LOGGER.info("处理小数位数结束");
		return queryPDAPackResultList;
	}
	/**
	 * 导出PDA端扫描生成包装金额信息到excel
	* @author foss-189284-zx
	* @date 创建时间：2014-5-26 上午11:18:52 
	* @return InputStream
	 */
	@Override
	public InputStream exportExcelStream(QueryPDAPackConditionEntity pdaQueryPackConditionEntity) {
		//输出流
		InputStream excelStream = null; 
		//excel表头信息
		SheetData sheetData = new SheetData();
		String[] rowHeads ={"运单号","包装时间","开单部门","理论打木架体积","理论打木箱体积","理论打木托个数",
				"实际打木架体积","实际打木箱体积","实际打木托个数","创建人","应付金额","包装供应商"};
	//	rowHeads = PackagingConstants.PACKAGING_EXPORT_PACKED_HEADER;
		sheetData.setRowHeads(rowHeads);
		
		// 运单号优先
				if (StringUtil.isNotEmpty(pdaQueryPackConditionEntity.getWaybillNo())) {
					pdaQueryPackConditionEntity.setPackageOrgCode(null);
					pdaQueryPackConditionEntity.setPackageOrgName(null);
//					pdaQueryPackConditionEntity.setPackageSupplierName(null);
					pdaQueryPackConditionEntity.setPackageSupplierCode(null);
					pdaQueryPackConditionEntity.setPackedBeginDate(null);
					pdaQueryPackConditionEntity.setPackedEndDate(null);
					pdaQueryPackConditionEntity.setAuditStatus(null);
				}
				//根据条查询需要导出的集合
				List<QueryPDAPackResultEntity> queryPDAPackResultList = pdaPackageMainPriceDao.queryPdaPackagePriceResultList(pdaQueryPackConditionEntity);
				List<QueryPDAPackResultEntity> resultList=handlePoint(queryPDAPackResultList);
		//时间格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//excel列表
		List<List<String>> excelList = new ArrayList<List<String>>();
		List<String> tempList = null;
		/**
		 * "运单号","包装时间","开单部门","理论打木架体积","理论打木箱体积","理论打木托个数",
		*	"实际打木架体积","实际打木箱体积","实际打木托个数","创建人","应付金额","包装供应商"
		 */
		//获取配置参数		
		for(QueryPDAPackResultEntity queryPDAPackResultEntity : resultList){
			tempList = new ArrayList<String>();
			//运单号
			tempList.add(queryPDAPackResultEntity.getWaybillNo());
			//包装时间
			if(queryPDAPackResultEntity.getCreateTime() !=null){
				tempList.add(df.format(queryPDAPackResultEntity.getCreateTime()));
			}else{
				tempList.add("");
			}
			/**
			 * JSYH-126  测试代打打木架需求时，PDA导入的数据界面显示和金额都是正确的，但是导出文档显示是错误的，需要开发优化；
			 * */
			//开单部门
			tempList.add(queryPDAPackResultEntity.getBillOrgName());
			//理论打木架体积
			tempList.add(queryPDAPackResultEntity.getTheoryFrameVolume()==null?"0":queryPDAPackResultEntity.getTheoryFrameVolume().toString());
			//理论打木箱体积
			tempList.add(queryPDAPackResultEntity.getTheoryWoodenVolume()==null?"0":queryPDAPackResultEntity.getTheoryWoodenVolume().toString());
			//理论大木托个数
			tempList.add(queryPDAPackResultEntity.getTheoryMaskNumber()==null?"0":queryPDAPackResultEntity.getTheoryMaskNumber()+"");
			//实际打木架体积
			tempList.add(queryPDAPackResultEntity.getActualFrameVolume()==null?"0":queryPDAPackResultEntity.getActualFrameVolume().toString());
			//实际打木箱体积
			tempList.add(queryPDAPackResultEntity.getActualWoodenVolume()==null?"0":queryPDAPackResultEntity.getActualWoodenVolume().toString());
			//实际打木托个数
			tempList.add(queryPDAPackResultEntity.getActualMaskNumber()==null?"0":queryPDAPackResultEntity.getActualMaskNumber().toString());
			//创建人
			tempList.add(queryPDAPackResultEntity.getCreateUserName());
			//应付金额
			tempList.add(queryPDAPackResultEntity.getPackagePayableMoney()==null?"":queryPDAPackResultEntity.getPackagePayableMoney().toString());
			//包装供应商
			tempList.add(queryPDAPackResultEntity.getPackageSupplierName());
			//加入到显示集合中
			excelList.add(tempList);
		}
		
		sheetData.setRowList(excelList);
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(
				sheetData,"pda扫描生成包装金额",PackagingConstants.PACKAGING_EXPORT_FILE_SIZE));
		return excelStream;
	}
	

	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 不分页
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	@Override
	public Long queryPdaPackagePriceList(
			QueryPDAPackConditionEntity pdaQueryPackConditionEntity) {
		// 运单号优先
		if (StringUtil.isNotEmpty(pdaQueryPackConditionEntity.getWaybillNo())) {
			pdaQueryPackConditionEntity.setPackageOrgCode(null);
			pdaQueryPackConditionEntity.setPackageOrgName(null);
//			pdaQueryPackConditionEntity.setPackageSupplierName(null);
			pdaQueryPackConditionEntity.setPackageSupplierCode(null);
			pdaQueryPackConditionEntity.setPackedBeginDate(null);
			pdaQueryPackConditionEntity.setPackedEndDate(null);
			pdaQueryPackConditionEntity.setAuditStatus(null);
		}
		return pdaPackageMainPriceDao
				.queryPdaPackagePriceList(pdaQueryPackConditionEntity);
	}

	/**
	 *设置PDA端扫描生成包装金额信息 pdaPackageMainPriceDao
	 */
	public void setPdaPackageMainPriceDao(
			IPdaPackageMainPriceDao pdaPackageMainPriceDao) {
		this.pdaPackageMainPriceDao = pdaPackageMainPriceDao;
	}
	/**
	 * 用于审核（反审核）
	 * @author 105869-heyongdong
	 * @date 2014年6月25日 14:15:31
	 * */
	@Override
	@Transactional
	public void auditPacked(List<String> id, String auditType, CurrentInfo user) {
		//查询包装实体
		List<QueryPDAPackResultEntity> queryPDAPackResultList =	pdaPackageMainPriceDao.queryPdaPackagePriceByIds(id);
		if(CollectionUtils.isNotEmpty(queryPDAPackResultList)){
			//传递给结算接口的实体lIst
			List<PackingRecAndPayTfrDto>  mainList= new ArrayList<PackingRecAndPayTfrDto>();
			List<String> wayBillList = new ArrayList<String>();
			for(QueryPDAPackResultEntity temp:queryPDAPackResultList){
				//构造实体
				PackingRecAndPayTfrDto packageEntity= new PackingRecAndPayTfrDto();
				//审核校验
				if(StringUtil.equals(auditType,"AUDIT")){
					if(!StringUtil.equals(temp.getAuditStatus(), PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT)){
						throw new TfrBusinessException("运单号:" + temp.getWaybillNo() + "，PDA端包装金额已审核,请核实再操作");
					}
					
				}else if(StringUtil.equals(auditType,"BACKAUDIT")){
					if(!StringUtil.equals(temp.getAuditStatus(), PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED)){
						throw new TfrBusinessException("运单号:" + temp.getWaybillNo() + "，PDA端包装金额未审核不能反审核,请核实再操作");
					}
				}
				//运单号
				packageEntity.setWaybillNo(temp.getWaybillNo());
				//运单开单部门
				packageEntity.setBillOrgCode(temp.getPackageOrgCode());
				//运单开单部门名称
				packageEntity.setBillOrgName(temp.getPackageOrgName());
				//包装部门编码
				packageEntity.setPackageOrgCode(temp.getPackageSupplierCode());
				//包装部门名称
				packageEntity.setPackageOrgName(temp.getPackageSupplierName());
				//包装应付金额
				packageEntity.setPackagePayableMoney(temp.getPackagePayableMoney());
				//添加到集合中
				mainList.add(packageEntity);
				wayBillList.add(temp.getWaybillNo());
			}
			
			/**
			 * 同步审核/反审核至CUBC
			 * @author 316759-RuipengWang-foss
			 * @date 2016-11-13 17:36:02
			 */
			List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
			String status = "";
			if (StringUtil.equals(auditType, "AUDIT")) {
				status = "0";
			} else if (StringUtil.equals(auditType, "BACKAUDIT")) {
				status = "1";
			}
			
			for (PackingRecAndPayTfrDto packingRecAndPayTfrDto : mainList) {
				PackingToCubcEntity cubcEntity = null;
				if (packingRecAndPayTfrDto != null) {
					cubcEntity = new PackingToCubcEntity();
					// 运单号
					cubcEntity.setWaybillNo(packingRecAndPayTfrDto.getWaybillNo());
					// 开单部门code
					cubcEntity.setBillOrgCode(packingRecAndPayTfrDto.getBillOrgCode());
					// 开单部门名称
					cubcEntity.setBillOrgName(packingRecAndPayTfrDto.getBillOrgName());
					// 包装部门code
					cubcEntity.setPackageOrgCode(packingRecAndPayTfrDto.getPackageOrgCode());
					// 包装部门名称
					cubcEntity.setPackageOrgName(packingRecAndPayTfrDto.getPackageOrgName());
					// 应付金额
					cubcEntity.setPackagePayableMoney(packingRecAndPayTfrDto.getPackagePayableMoney());
					// 操作
					cubcEntity.setStatus(status);
					// 当前操作人工号
					cubcEntity.setCurentEmpCode(user.getEmpCode());
					// 当前登录人姓名
					cubcEntity.setCurentEmpName(user.getEmpName());
					// 当前登录人部门编码
					cubcEntity.setCurentEmpDeptCode(user.getCurrentDeptCode());
					// 当前登录人部门名称
					cubcEntity.setCurentEmpDeptName(user.getCurrentDeptName());
				}
				packingToCubcEntitys.add(cubcEntity);
			}
			
			//封装灰度实体，类型是运单
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos((String []) wayBillList.toArray(new String [wayBillList.size()]));
			//调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			//STL CUBC调用标识
			String fossCubc = "";
			List<String> fossWayBillList = new ArrayList<String>();
			List<String> ucbcWayBillList = new ArrayList<String>();
			List<PackingRecAndPayTfrDto> fossEntityList = new ArrayList<PackingRecAndPayTfrDto>();
			List<PackingToCubcEntity> ucbcEntityList = new ArrayList<PackingToCubcEntity>();
			//分析灰度返回结果集，如果是2条的情况
			if (vestResponseDto.getVestBatchResult().size() > 1) {
				fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
				//循环得到分流的运单号
				for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
					 if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
						 fossWayBillList = vestResult.getVestObject();
					 } else {
						 ucbcWayBillList = vestResult.getVestObject();
					 }
				}
				
				//得到运单号对应的实体，提供给FOSS STL用
				for (PackingRecAndPayTfrDto dto : mainList) {
					for (String wayBill : fossWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							fossEntityList.add(dto);
						}
					}
				}
				//得到运单号对应的实体，提供给UCBC用
				for (PackingToCubcEntity dto : packingToCubcEntitys) {
					for (String wayBill : ucbcWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							ucbcEntityList.add(dto);
						}
					}
				}
				
				//赋值
				mainList = fossEntityList;
				packingToCubcEntitys = ucbcEntityList;
			}
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				if(StringUtil.equals(auditType,"AUDIT")){
					//调用结算审核接口
					try {
						packingRecAndPayForTfrService.audit(mainList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING, user);
						
					} catch (SettlementException e) {
						throw new TfrBusinessException("调结算接口异常，信息:{"+e.getErrorCode()+"}");
					}
				}else if(StringUtil.equals(auditType,"BACKAUDIT")){
					//调用结算反审核接口
					try {
						packingRecAndPayForTfrService.reverseAudit(mainList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING, user);
						
					} catch (SettlementException e) {
						throw new TfrBusinessException("调结算接口异常，信息:{"+e.getErrorCode()+"}");
					}
				}
			}
			
			// 灰度返回一条结果，SystemCode等于UCBC或返回多条结果会走一下逻辑
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_CUBC) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				String requestStr = JSONObject.toJSONString(packingToCubcEntitys);
			ResponseToCubcCallBack responseDto = new ResponseToCubcCallBack();

				// CUBC审核 ,  CUBC反审核, 相同的方法调用2遍，所以合并
				try {
					responseDto = fossToCubcService.pushAuditReverseAuditInvalid(requestStr);
					if (null == responseDto) {
						throw new TfrBusinessException("同步信息至CUBC异常：返回参数为null");
					} else if (StringUtils.equals(responseDto.getResult(),
							AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
						throw new TfrBusinessException("同步信息至CUBC异常：" + responseDto.getReason());
					}
				} catch (SettlementException e) {
					throw new TfrBusinessException("同步信息至CUBC异常：" + e.getErrorCode());
				}
			}
		}
		//再次查询PDA包装实体，防止并发
		queryPDAPackResultList = pdaPackageMainPriceDao.queryPdaPackagePriceByIds(id);
		//标记
		boolean flag =true;
		//跟新实体
		List<QueryPDAPackResultEntity> entitys=new ArrayList<QueryPDAPackResultEntity>();
		
		for(QueryPDAPackResultEntity temp:queryPDAPackResultList){
			if(StringUtil.equals(auditType,"AUDIT")){
				QueryPDAPackResultEntity entity=new QueryPDAPackResultEntity();
				//设置id
				entity.setId(temp.getId());
				//设置当前操作人
				entity.setAuditUserName(user.getEmpName());
				//设置当前操作人code
				entity.setAuditUserCode(user.getEmpCode());
				//设置审核时间
				entity.setAuditTime(new Date());
				//修改人
				entity.setModifyUser(user.getEmpName());
				entity.setModifyUserCode(user.getEmpCode());
				//修改时间
				entity.setModifyTime(new Date());
				//设置审核状态
				entity.setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED);
				entitys.add(entity);
				if(!StringUtil.equals(temp.getAuditStatus(), PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT)){
					flag = false;
					throw new TfrBusinessException("存在不是待审核状态的运单,请核实再操作");
				}
				
			}else if(StringUtil.equals(auditType,"BACKAUDIT")){
				QueryPDAPackResultEntity entity=new QueryPDAPackResultEntity();
				//设置id
				entity.setId(temp.getId());
				//设置当前操作人
				entity.setBackAuditName(user.getEmpName());
				//设置当前操作人code
				entity.setBackAuditCode(user.getEmpCode());
				//设置审核时间
				entity.setBackAuditTime(new Date());
				//修改人
				entity.setModifyUser(user.getEmpName());
				entity.setModifyUserCode(user.getEmpCode());
				//修改时间
				entity.setModifyTime(new Date());
				//设置审核状态
				entity.setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT);
				entitys.add(entity);
				if(!StringUtil.equals(temp.getAuditStatus(), PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED)){
					flag = false;
					throw new TfrBusinessException("存在不是审核状态的运单不能反审核,请核实再操作");
				}
			}
		}
		//更新包装审核状态
		if(flag&&CollectionUtils.isNotEmpty(entitys)){
			pdaPackageMainPriceDao.updateAuditPackgeByIds(entitys);
		}
		
	}
	
	
}
