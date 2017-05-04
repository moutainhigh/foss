package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPdaPcPackageTotalPriceService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackResultEntity;
import com.deppon.foss.module.transfer.packaging.server.dao.impl.PdaPcPackageTotalPriceDao;

/** 
 * @author ZhangXu
 * @version 创建时间：2014-5-29 下午5:15:15 
 *  根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间
 * 查询并汇总PDA与PC端包装金额
 */
public class PdaPcPackageTotalPriceService implements IPdaPcPackageTotalPriceService{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PdaPcPackageTotalPriceService.class);
	private PdaPcPackageTotalPriceDao pdaPcPackageTotalPriceDao;
	/**
	 * 
	* @author foss-189284-zx
	* @version 创建时间：2014-5-29 下午7:47:43 
	*  根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间
    * 查询并汇总PDA与PC端包装金额   分页
	* 
	*查询条件和计算都在sql优化完成了
	*查询【包装金额汇总（PDA与PC）】满足以下条件：
    *1、	包装时间：可以输入查询的时间间隔最大为31天，且必选；
    *2、	包装部门必选；
    *3、	包装供应商，提供功能选择器查询；
    *4、运单号，最优先查询，一次填入一个运单；
    * 同单号并且同包装部门同包装供应商，则两条数据进行合并，若不能同时满足三个条件相同则数据分开显示
    *包装时间规则：如果存在PDA扫描的包装时间，取PDA扫描的包装时间，否则取PC端录入时间
    *1、实际打木架体积：哪个界面数据不为0，取哪个界面的数据，若都为0，则显示为0，若两者都不为0，取两者之和；
    *2、实际打木箱体积：哪个界面数据不为0，取哪个界面的数据，若都为0，则显示为0，若两者都不为0，取两者之和；
    *3、实际打木托个数：哪个界面数据不为0，取哪个界面的数据，若都为0，则显示为0，若两者都不为0，取两者之和；
    *应付金额：如果两者不为0，取两者合并，否则取不为0的一者；
    *开单包装费=开单时的包装费用
    *1、	是否盈利判断规则：同一个单号的应付总金额大于或等于开单包装费时，判断为亏损，该字段显示为“否”；
    *2、同一个单号应付总金额小于开单包装费时，判断为盈利，改字段显示为“是”；
	*
	 */
	@Override
	public List<QueryPdaPcPackResultEntity> queryPdaPcTotalPrice(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity, int limit,
			int start) {
		// 运单号优先
				if (StringUtil.isNotEmpty(queryPdaPcPackConditionEntity.getWaybillNo())) {
					queryPdaPcPackConditionEntity.setBillOrgName(null);
					queryPdaPcPackConditionEntity.setBillOrgCode(null);
					queryPdaPcPackConditionEntity.setPackedDeptCode(null);
					queryPdaPcPackConditionEntity.setPackageSupplierName(null);
					queryPdaPcPackConditionEntity.setPackageSupplierCode(null);
					queryPdaPcPackConditionEntity.setPackedBeginDate(null);
					queryPdaPcPackConditionEntity.setPackedEndDate(null);
				}
				List<QueryPdaPcPackResultEntity> queryPdaPcPackResultEntity =pdaPcPackageTotalPriceDao.queryPdaPcTotalPrice(queryPdaPcPackConditionEntity ,limit,start);
				List<QueryPdaPcPackResultEntity> queryPdaPcPackResultList=handlePoint(queryPdaPcPackResultEntity);
		return queryPdaPcPackResultList ;
	}
	/**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
	@Override
	public List<QueryPdaPcPackResultEntity> queryPdaPcTotalList(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity){
		// 运单号优先
				if (StringUtil.isNotEmpty(queryPdaPcPackConditionEntity.getWaybillNo())) {
					queryPdaPcPackConditionEntity.setBillOrgName(null);
					queryPdaPcPackConditionEntity.setBillOrgCode(null);
					queryPdaPcPackConditionEntity.setPackedDeptCode(null);
					queryPdaPcPackConditionEntity.setPackageSupplierName(null);
					queryPdaPcPackConditionEntity.setPackageSupplierCode(null);
					queryPdaPcPackConditionEntity.setPackedBeginDate(null);
					queryPdaPcPackConditionEntity.setPackedEndDate(null);
				}
				List<QueryPdaPcPackResultEntity> queryPdaPcPackResultEntity =pdaPcPackageTotalPriceDao.queryPdaPcTotalList(queryPdaPcPackConditionEntity);
				List<QueryPdaPcPackResultEntity> queryPdaPcPackResultList=handlePoint(queryPdaPcPackResultEntity);
		return queryPdaPcPackResultList ;
	}
	
	/**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额 总数
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
	@Override
	public Long queryPdaPcTotalPriceCount(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity){
		// 运单号优先
				if (StringUtil.isNotEmpty(queryPdaPcPackConditionEntity.getWaybillNo())) {
					queryPdaPcPackConditionEntity.setBillOrgName(null);
					queryPdaPcPackConditionEntity.setBillOrgCode(null);
					queryPdaPcPackConditionEntity.setPackedDeptCode(null);
					queryPdaPcPackConditionEntity.setPackageSupplierName(null);
					queryPdaPcPackConditionEntity.setPackageSupplierCode(null);
					queryPdaPcPackConditionEntity.setPackedBeginDate(null);
					queryPdaPcPackConditionEntity.setPackedEndDate(null);
				}
				Long queryPdaPcPackResultCount =pdaPcPackageTotalPriceDao.queryPdaPcTotalPriceCount(queryPdaPcPackConditionEntity);
		return queryPdaPcPackResultCount ;
	}
	private List<QueryPdaPcPackResultEntity> handlePoint(
			List<QueryPdaPcPackResultEntity> queryPdaPcPackResultEntity) {
		LOGGER.info("处理小数位数开始");
		// 定义返回结果集
		List<QueryPdaPcPackResultEntity> resultList=new ArrayList<QueryPdaPcPackResultEntity>();
		if(queryPdaPcPackResultEntity==null||queryPdaPcPackResultEntity.size()==0){
			return null;
		}
		// 循环，单条处理
		for(QueryPdaPcPackResultEntity resultEntity:queryPdaPcPackResultEntity ){
			
		    // 理论打木架体积 theoryFrameVolume;
			if(resultEntity.getTheoryFrameVolume()!=null){
				resultEntity.setTheoryFrameVolume(resultEntity.getTheoryFrameVolume().setScale(2,BigDecimal.ROUND_HALF_UP));	
			}
		   // 实际打木架体积actualFrameVolume;
			if(resultEntity.getActualFrameVolume()!=null){
				resultEntity.setActualFrameVolume(resultEntity.getActualFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   // 理论打木箱体积theoryWoodenVolume;  
			if(resultEntity.getTheoryWoodenVolume()!=null){
				resultEntity.setTheoryWoodenVolume(resultEntity.getTheoryWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   // 实际打木箱体积actualWoodenVolume;    	
			if(resultEntity.getActualWoodenVolume()!=null){
				resultEntity.setActualWoodenVolume(resultEntity.getActualWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   // 理论打木托个数theoryMaskNumber;    	
			if(resultEntity.getTheoryMaskNumber()!=null){
				resultEntity.setTheoryMaskNumber(resultEntity.getTheoryMaskNumber().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   // 实际打木托个数actualMaskNumber;    	
			if(resultEntity.getActualMaskNumber()!=null){
				resultEntity.setActualMaskNumber(resultEntity.getActualMaskNumber().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   // 木条长度 woodenBarLong;    	
			if(resultEntity.getWoodenBarLong()!=null){
				resultEntity.setWoodenBarLong(resultEntity.getWoodenBarLong().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   // 	气泡膜体积bubbVelamenVolume;    
			if(resultEntity.getBubbVelamenVolume()!=null){
				resultEntity.setBubbVelamenVolume(resultEntity.getBubbVelamenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		   //  	缠绕膜体积bindVelamenVolume;   
			if(resultEntity.getBindVelamenVolume()!=null){
				resultEntity.setBindVelamenVolume(resultEntity.getBindVelamenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			// 	包带根数bagBeltNum;   
			if(resultEntity.getBagBeltNum()!=null){
				resultEntity.setBagBeltNum(resultEntity.getBagBeltNum().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		  //应付金额 packagePayableMoney;
			if(resultEntity.getPackagePayableMoney()!=null){
				resultEntity.setPackagePayableMoney(resultEntity.getPackagePayableMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		  //开单包装费  waybillPackgeFee; 
			if(resultEntity.getWaybillPackgeFee()!=null){
				resultEntity.setWaybillPackgeFee(resultEntity.getWaybillPackgeFee().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			resultList.add(resultEntity);
		}
		return resultList;

	}
	
	
	@Override
	public InputStream exportExcelStream(QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity) {
		//输出流
		InputStream excelStream = null; 
		//excel表头信息
		SheetData sheetData = new SheetData();
		String[] rowHeads ={"运单号","包装时间","开单部门","包装部门","理论打木架体积","理论打木箱体积","理论打木托个数",
				"实际打木架体积","实际打木箱体积","实际打木托个数","木条长度","气泡膜体积","缠绕膜体积","包带根数","应付金额",
				"开单包装费","创建人","新增人","修改人","包装供应商","是否盈利"};
	//	rowHeads = PackagingConstants.PACKAGING_EXPORT_PACKED_HEADER;
		sheetData.setRowHeads(rowHeads);
				//根据条查询需要导出的集合
				List<QueryPdaPcPackResultEntity> queryPdaPcPackResultList = queryPdaPcTotalList( queryPdaPcPackConditionEntity);
		//时间格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//excel列表
		List<List<String>> excelList = new ArrayList<List<String>>();
		List<String> tempList = null;
		/**
		 * "运单号","包装时间","开单部门","包装部门","理论打木架体积","理论打木箱体积","理论打木托个数",
		 *"实际打木架体积","实际打木箱体积","实际打木托个数","木条长度","气泡膜体积","缠绕膜体积","包带根数","应付金额"
		 *,"开单包装费","创建人","新增人","修改人","包装供应商","是否盈利"
		 */
		//获取配置参数		
		for(QueryPdaPcPackResultEntity queryPdaPcPackResultEntity : queryPdaPcPackResultList){
			tempList = new ArrayList<String>();
			//运单号
			tempList.add(queryPdaPcPackResultEntity.getWaybillNo());
			//包装时间
			if(queryPdaPcPackResultEntity.getCreateTime() !=null){
				tempList.add(df.format(queryPdaPcPackResultEntity.getCreateTime()));
			}else{
				tempList.add("");
			}
			//开单部门
			tempList.add(queryPdaPcPackResultEntity.getBillOrgName());
			//包装部门
			tempList.add(queryPdaPcPackResultEntity.getPackageOrgName());
			//理论打木架体积
			tempList.add(queryPdaPcPackResultEntity.getTheoryFrameVolume()==null?"0":queryPdaPcPackResultEntity.getTheoryFrameVolume().toString());
			//理论打木箱体积
			tempList.add(queryPdaPcPackResultEntity.getTheoryWoodenVolume()==null?"0":queryPdaPcPackResultEntity.getTheoryWoodenVolume().toString());
			//理论大木托个数
			tempList.add(queryPdaPcPackResultEntity.getTheoryMaskNumber()==null?"0":queryPdaPcPackResultEntity.getTheoryMaskNumber()+"");
			//实际打木架体积
			tempList.add(queryPdaPcPackResultEntity.getActualFrameVolume()==null?"0":queryPdaPcPackResultEntity.getActualFrameVolume().toString());
			//实际打木箱体积
			tempList.add(queryPdaPcPackResultEntity.getActualWoodenVolume()==null?"0":queryPdaPcPackResultEntity.getActualWoodenVolume().toString());
			//实际打木托个数
			tempList.add(queryPdaPcPackResultEntity.getActualMaskNumber()==null?"0":queryPdaPcPackResultEntity.getActualMaskNumber().toString());
			//"木条长度",
			tempList.add(queryPdaPcPackResultEntity.getWoodenBarLong()==null?"0":queryPdaPcPackResultEntity.getWoodenBarLong().toString());
			//"气泡膜体积",
			tempList.add(queryPdaPcPackResultEntity.getBubbVelamenVolume()==null?"0":queryPdaPcPackResultEntity.getBubbVelamenVolume().toString());
			//"缠绕膜体积",
			tempList.add(queryPdaPcPackResultEntity.getBindVelamenVolume()==null?"0":queryPdaPcPackResultEntity.getBindVelamenVolume().toString());
			//"包带根数"
			tempList.add(queryPdaPcPackResultEntity.getBagBeltNum()==null?"0":queryPdaPcPackResultEntity.getBagBeltNum().toString());
			//应付金额
			tempList.add(queryPdaPcPackResultEntity.getPackagePayableMoney()==null?"0":queryPdaPcPackResultEntity.getPackagePayableMoney().toString());
			//"开单包装费"
			tempList.add(queryPdaPcPackResultEntity.getWaybillPackgeFee()==null?"0":queryPdaPcPackResultEntity.getWaybillPackgeFee().toString());
			//创建人
			tempList.add(queryPdaPcPackResultEntity.getCreateUserName());
			//"新增人",
			tempList.add(queryPdaPcPackResultEntity.getNewAddUserName());
			//"修改人"
			tempList.add(queryPdaPcPackResultEntity.getModifyUserName());
			//包装供应商
			tempList.add(queryPdaPcPackResultEntity.getPackageSupplierName());
			//是否盈利
			tempList.add(queryPdaPcPackResultEntity.getProfitStatus());
			//加入到显示集合中
			excelList.add(tempList);
		}
		
		sheetData.setRowList(excelList);
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(
				sheetData,"包装金额汇总（PDA与PC）",PackagingConstants.PACKAGING_EXPORT_FILE_SIZE));
		return excelStream;
	}
	
	/**
	 *设置pdaPcPackageTotalPriceDao
	 */
	public void setPdaPcPackageTotalPriceDao(
			PdaPcPackageTotalPriceDao pdaPcPackageTotalPriceDao) {
		this.pdaPcPackageTotalPriceDao = pdaPcPackageTotalPriceDao;
	}

	
	
}
