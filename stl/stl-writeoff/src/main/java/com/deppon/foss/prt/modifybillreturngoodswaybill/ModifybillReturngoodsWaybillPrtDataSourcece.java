package com.deppon.foss.prt.modifybillreturngoodswaybill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ModifyBillPrintInfoDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WaybillChangeDetailPrintDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WaybillChangeRfcPrintDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.prt.modifybillwriteoffprt.ModifybillWriteoffPrtDataSource;

/**
 * (更改单返货单打印需要的数据)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:095793-foss-LiQin,date:2013-4-23  下午10:38:31,
 * </p>
 * @author 095793-foss-LiQin
 * @date 2013-4-23 下午3:08:48
 */
public class ModifybillReturngoodsWaybillPrtDataSourcece implements JasperDataSource {
	// 注入日志
	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ModifybillWriteoffPrtDataSource.class);
	
	/**
	 * 更改单打印时查询dto
	 */
	private WaybillChangeRfcPrintDto dto=null;
	
	
	/**
	 * 打印的常量，初始化都是1
	 */
	private  static final int MODIFYBILL_PRINT_TIME=1;
	
	
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws ParseException {
		LOGGER.info("更改单返货单打印,开始填充数据开始.......");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (jasperContext != null && jasperContext.getParamMap() != null) {
			
			/**
			 * 更改单id
			 */
			String waybillChangeId = (String) jasperContext.get("waybillChangeId");
			
			if (StringUtils.isBlank(waybillChangeId)) {
				throw new SettlementException("更改单返货单打印时，传入的更改单参数错误 id为空！");
			}
			//获取更改单返货单Service实例
			IModifyBillWriteoffService waybillChangeService = (IModifyBillWriteoffService) jasperContext.getApplicationContext().getBean("modifyBillWriteoffService");
			//根据ID获取更改单返货单service
			dto = waybillChangeService.queryWaybillRfcPrintDtoByRfcid(waybillChangeId);
			if(null==dto){
				//查询参数不正确
				throw new SettlementException("更改单返货单打印时，根据传入的更改单id,未找到更改单返货单的记录！");
			}

			
			
			LOGGER.info("开始更新更改单打印次数....");
			ModifyBillPrintInfoDto pDto=new ModifyBillPrintInfoDto();
			pDto.setId(waybillChangeId);
			pDto.setModifyDate(new Date());
			pDto.setCreateDate(new Date());
			pDto.setPrintTime(new Date());
			//创建用户编码		
			pDto.setCreateUser((String) jasperContext.get("createUser"));
			pDto.setModifyDate(new Date());
			pDto.setModifyUser((String) jasperContext.get("modifyUser"));
			//打印人组织		
			pDto.setPrintOrg((String) jasperContext.get("empName"));
			pDto.setPrintOrgCode((String) jasperContext.get("empCode"));
			pDto.setPrintTimes(MODIFYBILL_PRINT_TIME);
			
			//打印类型		
			pDto.setPrintType(WaybillRfcConstants.MODIFYBILL_PRINT_WAYBILLRFC);
			pDto.setPrintUser((String) jasperContext.get("empName"));
			pDto.setPrintUserCode((String) jasperContext.get("empCode"));
			pDto.setWaybillId(waybillChangeId);
			pDto.setWaybillNo(dto.getWaybillNo());
			
			//更新打印次数			
			int num=waybillChangeService.updateByPrimaryKeySelective(waybillChangeId,pDto);
			
			if(num!=1){
				throw new SettlementException("更改单编号："+waybillChangeId+"更改单转运单修改打印次数，更新失败！");
			}
			//查询打印次数(传入更改单号和运单编号)			
			int printNum=waybillChangeService.queryPrintTimesByWaybillRFCId(waybillChangeId,dto.getWaybillNo());
			
			paramMap.put("changereason", dto.getRfcReason());// 更改原因
			paramMap.put("waybillNo",dto.getWaybillNo());// 单号
			paramMap.put("applydepartment", dto.getApplyDept());// 申请部门
			paramMap.put("printtimes",printNum);// 打印次数
			paramMap.put("applicant",dto.getApplyPerson());// 申请人
			if(StringUtils.isNotEmpty(dto.getApplyTime())){//由于后台传递过来的时间格式为"2013-06-24 16:22:08.005" 故需要先转换
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date=format.parse(dto.getApplyTime());
				paramMap.put("applytime",format.parse(format.format(date)));// 申请时间 
			}
			paramMap.put("goodsname", dto.getGoodsName());// 货物名称
			paramMap.put("package", dto.getPack());// 货物包装
			paramMap.put("goodsnum", dto.getPieces());// 货物数量
			paramMap.put("weight", dto.getWeight());// 货物重量
			paramMap.put("volume", dto.getVolume());// 货物体积
			paramMap.put("size", dto.getDimension());// 货物尺寸
			
			/**
			 * 根据产品CODE找对应的名称
			 */
			//获取全部有效的第三级别产品类型 
			IProductService productService = (IProductService) jasperContext.getApplicationContext().getBean("productService");
			List<ProductEntity> productList = productService.queryLevel3ProductInfo();
			// 生成存储产品类型的map
			Map<String, String> productMap = new HashMap<String, String>();
			// 如果产品类型不为空，循环加入到map中
			if (!CollectionUtils.isEmpty(productList)) {
				for (ProductEntity entity : productList) {
					productMap.put(entity.getCode(), entity.getName());
				}
			}
			
			if(StringUtils.isNotEmpty(dto.getProductCode())){
				//默认的产品类型为空
	    		String productEntityName = "";
				//将产品类型转换编码为名称
				productEntityName=productMap.get(dto.getProductCode());
				paramMap.put("changetype", productEntityName);// 更改运输性质
			}
			
			paramMap.put("changedeststation", dto.getTargetOrgCode());// 更改站点
			paramMap.put("newpickuporg", dto.getCustomerPickupOrgCode());// 提货网点
	
		}
		LOGGER.info("更改单返货单打印,表头结束.......");
		//返回打印的参数		
		return paramMap;
		
	}

	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) {

		//更改单返货单的明细项		
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Long  costvariance=0l;
		Map<String, Object> paramDMap = null;
		
		//存放更改单返货单明细			
		List<WaybillChangeDetailPrintDto> changeList=new ArrayList<WaybillChangeDetailPrintDto>();
		
		LOGGER.info("更改单返货单打印明细,开始填充数据开始.......");
		if(CollectionUtils.isNotEmpty(dto.getChangeList())){
			
			//获取表头里面的明细			
			changeList=dto.getChangeList();
			//总费用差额			
			Long totalFee=0l;
			
			//打印更改单返货单明细 ----变更项、更改前信息、更改后信息			
			for( WaybillChangeDetailPrintDto wayBillDto: changeList){
				
				//更改单返货单map，存放条数		
				paramDMap = new HashMap<String, Object>();
				//变更项目、变更前金额、变更后金额				
				paramDMap.put("changeitem",wayBillDto.getRfcItemsName());
				paramDMap.put("changebeforeinfo",wayBillDto.getBeforeRfcInfo());
				paramDMap.put("changeafterinfo", wayBillDto.getAfterRfcInfo());
				
				// 总费用差额
				if(wayBillDto.getRfcItems().equals(WaybillRfcConstants.RFCITEMS_TOTAL_FEE)){
					//当变更前金额和变更后金额不为空并且不为为数字时，金额总费用的计算					
					if(StringUtils.isNotEmpty(wayBillDto.getBeforeRfcInfo())&&StringUtils.isNotEmpty(wayBillDto.getAfterRfcInfo())
							&&StringUtils.isNumeric(wayBillDto.getBeforeRfcInfo())&&StringUtils.isNumeric(wayBillDto.getAfterRfcInfo())){
						Long beforeInfo=Long.parseLong(wayBillDto.getBeforeRfcInfo());
						Long afterInfo=Long.parseLong(wayBillDto.getAfterRfcInfo());
						//如果变更后金额大于变更前金额，总金额=变更后-变更前						
						if(afterInfo>beforeInfo){
							totalFee=afterInfo-beforeInfo;
						}else{
							//如果变更后金额大于变更前金额，总金额=变更前-变更后	
							totalFee=beforeInfo-afterInfo;
						}
						// 总费用差额
						costvariance = totalFee;
						paramDMap.put("costvariance",costvariance.toString());
					}
					
				}
				//将明细添加入list，返回				
				list.add(paramDMap);
		}
		}else{
			//当查询的明细数据为空，时，返回空			
			paramDMap = new HashMap<String, Object>();
			list.add(paramDMap);
		}
		//将差额设置进入表头		
		jasperContext.getJasperParameters().put("costvariance",costvariance.toString());
		LOGGER.info("更改单返货单打印明细,结束填充数据.......");
		
		return list;
	}
}