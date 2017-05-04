package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.vo.IdentityEffectivePlanVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCheckBoxColumn;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCreateWaybillTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.BatchWaybillButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
import com.deppon.foss.module.pickup.creating.client.vo.BatchWaybillComVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;

/**
 * 批量开单
 * 
 * @author Foss-105888-guohao
 * @date 2015-5-14 10:31:34
 */
public class BatchWaybillCreateImportAction implements
		IButtonActionListener<OpenBatchCreateWaybillUI> {
	private static final long serialVersionUID = 1L;
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	private static final double ONEPOINTFOUR = 1.4;

	private static final int NUM_3000000 = 3000000;
	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(BatchWaybillCreateImportAction.class);

	//查询区域 hessian接口
	private IAddressServiceHessianRemoting onlineAddressServiceHessianRemoting = DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
	//批量开单远程接口
	IWaybillHessianRemoting waybillHessianRemoting;
	// 对应的UI
	private OpenBatchCreateWaybillUI ui;
	//运单基础资料服务
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	private static final Log LOG = LogFactory.getLog(WaybillSubmitConfirmAction.class);
	// 批量导入事件
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setFileFilter(new FileNameExtensionFilter("*.xls;*.xlsx", "xls",
				"xlsx"));// 添加文本文件过滤
		int result = fc.showOpenDialog(ui);
		File uploadFile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			uploadFile = fc.getSelectedFile();
		}
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 2007
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 2003
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				MsgBox.showInfo("请选择导入文件");
				return;
			}
			if (book != null) {
				importWaybillChange(book);
			}
		} catch (FileException e0) {
			MsgBox.showInfo("导入批量更改重量: " + e0.getMessage());
			return;
		} catch (IOException e1) {
			MsgBox.showInfo("数据出现异常！");
			return;
		} catch (BusinessException e2) {
			MsgBox.showInfo("导入批量更改重量: " + e2.getMessage());
			return;
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e3) {
					MsgBox.showInfo("文件关闭失败");
					return;
				}
			}
		}
	}

	/**
	 * 批量导入开单excel模板
	 * 
	 * @author Foss-105888-guohao
	 * @date 2015-5-14 10:32:29
	 * @param book
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void importWaybillChange(Workbook book) {
		waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		 //通过仓管组 查询对应开单营业部
		String code = waybillHessianRemoting.queryBillingByStore(dept.getCode());
//		List<String> codes = waybillHessianRemoting.queryBillingsByStore(dept.getCode());
		List<Object> list = new ArrayList<Object>();
		List<WaybillPendingEntity> waybillPendingList = new ArrayList<WaybillPendingEntity>();
		List<WoodenRequirePendingEntity> woodenRequirelist = new ArrayList<WoodenRequirePendingEntity>();
		
		WaybillPendingEntity xlsDto = null;
		WoodenRequirePendingEntity woodenRequire = null;
		WaybillPendingDto dto = null;
		List<OrgAdministrativeInfoEntity> orgList = null;
		Map<String,String> map = new HashMap<String,String>();
		ProductEntity produc=null;
		Map<String,BatchWaybillComVo> batchWaybillMap = new HashMap<String,BatchWaybillComVo>();
		//付款方式
		List<DataDictionaryValueEntity>  paidMethodList =  waybillService.selectProductCodeDataDictValue(WaybillConstants.PAYMENT_MODE);
		Map<String,String> paidMethodMap = new HashMap<String,String>();
		for(DataDictionaryValueEntity dictionary:paidMethodList){
			paidMethodMap.put(dictionary.getValueName(), dictionary.getValueCode());
		}
		BatchWaybillComVo loadLineVo = null;
		String key = "";
		DecimalFormat df = new DecimalFormat("0.00");
		/**
		 * 流水号信息
		 * dp-foss-dongjialing
		 * 225131
		 */
		List<LabeledGoodEntity> labeledGoodList = new ArrayList<LabeledGoodEntity>();
		LabeledGoodEntity labeledGoodEntity = null; 
		try {
			String temp = "";
			String temp1 = "";
			String temp2 = "";
			String paper = "";//纸
			String wood = "";//木
			String fibre = "";//纤
			String salver = "";//托
			String membrane = "";//膜
			int maxRow = 0;//excel模板导入的最大行数
			for(int i=0; i < book.getNumberOfSheets(); i++){
				Sheet sheet = book.getSheetAt(i);
				 maxRow = maxRow + sheet.getLastRowNum();
			}
			if(maxRow > NumberConstants.NUMBER_200){
				MsgBox.showInfo("最大只能导入200条记录");
				return;
			}
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < book.getNumberOfSheets(); numSheet++) {
				Sheet hssfSheet = book.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				
				// 循环行Row  
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					Row hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					xlsDto = new WaybillPendingEntity();
					woodenRequire = new WoodenRequirePendingEntity();
					// 循环列Cell
					//序号
					xlsDto.setSerialNumber(""+rowNum);
					// 发货人手机号
					Cell deliveryCustomerMobilephone = hssfRow.getCell(2);		
						temp1 = obtainStringVal(deliveryCustomerMobilephone);
						if (StringUtils.isNotBlank(temp1)) {
						if(!isMobileNo(temp1)){
								MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
										+ rowNum + "行，第3列："+"发货人手机号码格式不对!");
								return;
						}
						xlsDto.setDeliveryCustomerMobilephone(temp1);
					}
					//发货人电话
					Cell deliveryCustomerPhone = hssfRow.getCell(NumberConstants.NUMBER_3);
					temp2 = obtainStringVal(deliveryCustomerPhone);
					if(StringUtils.isNotBlank(temp2)){					
						if(!isPhone(temp2)){
								MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
										+ rowNum + "行，第3列："+"发货人电话应为7-8位，加区号请以'-'分开!");
								return;
						}
						xlsDto.setDeliveryCustomerPhone(temp2);
					}
					if(StringUtils.isBlank(temp1) && StringUtils.isBlank(temp2)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第3和4列："+"发货人手机号和发货人电话必须填写一个！");
						return;
					}
					//发货部门名称
					Cell receiveOrgName = hssfRow.getCell(NumberConstants.NUMBER_4);
					temp = obtainStringVal(receiveOrgName);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第5列："+"发货部门名称不能为空！");
						return;
					}					
					//xlsDto.setDeliveryCustomerName(temp);//发货部门存到发货联系人字段里
					xlsDto.setDeliveryCustomerContact(temp);
					//发货人详细地址
					Cell deliveryCustomerAddress = hssfRow.getCell(NumberConstants.NUMBER_5);
					temp = obtainStringVal(deliveryCustomerAddress);
					if(StringUtils.isNotBlank(temp)){
						String [] address = temp.split("-");
						String []addressCode=addressCode(address).split(",");
						if(addressCode.length==1){
							xlsDto.setDeliveryCustomerProvCode(addressCode[0]);
						}else if(addressCode.length==2){
							xlsDto.setDeliveryCustomerProvCode(addressCode[0]);
							xlsDto.setDeliveryCustomerCityCode(addressCode[1]);
						}else{
							xlsDto.setDeliveryCustomerProvCode(addressCode[0]);
							xlsDto.setDeliveryCustomerCityCode(addressCode[1]);
							xlsDto.setDeliveryCustomerDistCode(addressCode[2]);
						}
					}
					xlsDto.setDeliveryCustomerAddress(temp);
					//收货人手机号
					Cell receiveCustomerMobilephone = hssfRow.getCell(NumberConstants.NUMBER_6);
					temp1 = obtainStringVal(receiveCustomerMobilephone);
					if (StringUtils.isNotBlank(temp1)) {					
						if(!isMobileNo(temp1)){
								MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
										+ rowNum + "行，第7列："+"收货人手机号码格式不对!");
								return;
						}
						xlsDto.setReceiveCustomerMobilephone(temp1);
					}
					//收货人电话
					Cell receiveCustomerPhone = hssfRow.getCell(NumberConstants.NUMBER_7);
					temp2 = obtainStringVal(receiveCustomerPhone);
					if(StringUtils.isNotBlank(temp2)){						
						if(!isPhone(temp2)){
								MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
										+ rowNum + "行，第8列："+"收货人电话应为7-8位，加区号请以'-'分开!");
								return;
						}
						xlsDto.setReceiveCustomerPhone(temp2);
					}
					if(StringUtils.isBlank(temp1) && StringUtils.isBlank(temp2)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第7和8列："+"收货人手机号和收货人电话必须填写一个！");
						return;
					}
					
					//收货部门名称
					Cell customerPickupOrgName = hssfRow.getCell(NumberConstants.NUMBER_8);
					temp = obtainStringVal(customerPickupOrgName);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第9列："+"收货部门名称不能为空！");
						return;
					}					
					//xlsDto.setReceiveCustomerName(temp);//收货部门存到收货客户名称里
					xlsDto.setReceiveCustomerContact(temp);
					//收货人详细地址
					Cell receiveCustomerAddress = hssfRow.getCell(NumberConstants.NUMBER_9);
					temp = obtainStringVal(receiveCustomerAddress);
					if(StringUtils.isNotBlank(temp)){
						String [] address = temp.split("-");
						String []addressCode=addressCode(address).split(",");
						if(addressCode.length==1){
							xlsDto.setReceiveCustomerProvCode(addressCode[0]);
						}else if(addressCode.length==2){
							xlsDto.setReceiveCustomerProvCode(addressCode[0]);
							xlsDto.setReceiveCustomerCityCode(addressCode[1]);
						}else{
							xlsDto.setReceiveCustomerProvCode(addressCode[0]);
							xlsDto.setReceiveCustomerCityCode(addressCode[1]);
							xlsDto.setReceiveCustomerDistCode(addressCode[2]);
						}
					}
					xlsDto.setReceiveCustomerAddress(temp);
					//运输性质
					Cell transportProperiesName = hssfRow.getCell(NumberConstants.NUMBER_10);
					temp = obtainStringVal(transportProperiesName);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第11列："+"运输性质不能为空！");
						return;
					}
					if(null!=batchWaybillMap.get(temp)){
						BatchWaybillComVo comVo =batchWaybillMap.get(temp);
						xlsDto.setProductCode(comVo.getProductCode());
						xlsDto.setProductName(comVo.getProductName());
						xlsDto.setProductId(comVo.getProductId());

					}else{
						produc=waybillService.findProductByName(temp);
						if(null!=produc){
							xlsDto.setProductCode(produc.getCode());
							xlsDto.setProductName(produc.getName());
							xlsDto.setProductId(produc.getId());
							
							BatchWaybillComVo comVo =new BatchWaybillComVo();
							comVo.setProductCode(produc.getCode());
							comVo.setProductName(produc.getName());
							comVo.setProductId(produc.getId());
							batchWaybillMap.put(temp, comVo);
						}else{
							MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
									+ rowNum + "行，第11列："+"运输性质不能为空！");
							return;
						}
					}
					
					//提货方式：只能是内部带货自提 和 内部带货送货
					Cell receiveMethod = hssfRow.getCell(NumberConstants.NUMBER_11);
					temp = obtainStringVal(receiveMethod);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第12列："+"提货方式不能为空！");
						return;
					}	
					/*
					if(!"内部带货自提".equals(temp) && !"内部带货送货".equals(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第12列："+"提货方式只能是'内部带货自提'和'内部带货送货'！");
						return;
					}
					*/
//					xlsDto.setReceiveMethod("INNER_PICKUP");
//					xlsDto.setReceiveMethodName("内部带货自提");
					if("内部带货自提".equals(temp)){
						xlsDto.setReceiveMethod(WaybillConstants.INNER_PICKUP);
					}else if("内部带货送货".equals(temp)){
						if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(produc.getCode())
								|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(produc.getCode())
								|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(produc.getCode())
								|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(produc.getCode())){
							xlsDto.setReceiveMethod(WaybillConstants.DELIVER_INNER_PICKUP);
						}else{
							MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
									+ rowNum + "行，第11列：运输性质'"+produc.getName()+"'的提货方式不能为'内部带货送货'！");
							return;
						}
					}else{
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第12列："+"提货方式只能是'内部带货自提'和'内部带货送货'！");
						return;
					}
					xlsDto.setReceiveMethodName(temp);
					
					//出发网点
					Cell startPoint = hssfRow.getCell(NumberConstants.NUMBER_12);
					temp = obtainStringVal(startPoint);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第13列："+"出发网点不能为空！");
						return;
					}
						//根据网点名称查询组织信息
					orgList = baseDataService.queryOrgInfoByNameOnline(temp);
					if(CollectionUtils.isEmpty(orgList)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第13列："+"该出发网点不存在！");
						return;
					}
					boolean flag  = false;
					for(int i=0;i<orgList.size();i++){
						if(temp.equals(orgList.get(i).getName())){
							// -- sangwenhao
							/*if(CollectionUtils.isEmpty(codes) && !codes.contains(orgList.get(i).getCode())){
								MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
										+ rowNum + "行，第13列："+"该出发网点与当前账号登陆所映射的部门不一致！");
								return;
							}*/
							if(code!=null && !code.equals(orgList.get(i).getCode())){
								MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
										+ rowNum + "行，第13列："+"该出发网点与当前账号登陆所映射的部门不一致！");
								return;
							}
							xlsDto.setReceiveOrgCode(orgList.get(i).getCode());//发货部门
							xlsDto.setCreateOrgCode(orgList.get(i).getCode());//发货部门
							flag = true;
							break;
						}
					}
					if(!flag){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第13列："+"该出发网点与当前账号登陆所映射的部门不一致！");
						return;
					}
					xlsDto.setStartNode(temp);
					//提货网点
					Cell deliveryPoint = hssfRow.getCell(NumberConstants.NUMBER_13);
					temp = obtainStringVal(deliveryPoint);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第14列："+"提货网点不能为空！");
						return;
					}
					if(map.containsKey(temp)){
						xlsDto.setCustomerPickupOrgCode(map.get(temp));//提货网点
						xlsDto.setTargetOrgName(temp);//目的站
						xlsDto.setTargetOrgCode(temp);//目的站
					}else{
						//根据网点名称查询组织信息
						orgList = baseDataService.queryOrgInfoByNameOnline(temp);
						if(CollectionUtils.isEmpty(orgList)){
							MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
									+ rowNum + "行，第14列："+"该提货网点不存在！");
							return;
						}
						boolean flag1 = false;
						for(int i=0;i<orgList.size();i++){
							if(temp.equals(orgList.get(i).getName())){
								xlsDto.setCustomerPickupOrgCode(orgList.get(i).getCode());//提货网点
								xlsDto.setTargetOrgName(orgList.get(i).getName());//目的站
								xlsDto.setTargetOrgCode(orgList.get(i).getName());//目的站
								map.put(temp, orgList.get(i).getCode());
								flag1 = true;
								break;
							}
						}
						if(!flag1){
							MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
									+ rowNum + "行，第14列："+"该提货网点不存在！");
							return;
						}
					}
					//货物名称
					Cell goodsName = hssfRow.getCell(NumberConstants.NUMBER_14);
					temp = obtainStringVal(goodsName);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第15列："+"货物名称不能为空！");
						return;
					}				
					xlsDto.setGoodsName(temp);
					//总件数
					Cell goodsQtyTotal = hssfRow.getCell(NumberConstants.NUMBER_15);
					temp = obtainStringVal(goodsQtyTotal);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第16列："+"总件数不能为空！");
						return;
					}			
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第16列："+"不是纯数字！");
						return;
					}
					xlsDto.setGoodsQtyTotal(Integer.parseInt(temp));
					//总重量
					Cell goodsWeightTotal = hssfRow.getCell(NumberConstants.NUMBER_16);
					temp = df.format(goodsWeightTotal.getNumericCellValue());
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第17列："+"总重量不能为空！");
						return;
					}					
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第17列："+"不是纯数字！");
						return;
					}
					xlsDto.setGoodsWeightTotal(new BigDecimal(temp));
					//总体积
					Cell goodsVolumeTotal = hssfRow.getCell(NumberConstants.NUMBER_17);
					temp = df.format(goodsVolumeTotal.getNumericCellValue());
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第18列："+"总体积不能为空！");
						return;
					}
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第18列："+"不是纯数字");
						return;
					}
					xlsDto.setGoodsVolumeTotal(new BigDecimal(temp).setScale(2, BigDecimal.ROUND_HALF_UP));
					
					//对内备注
					Cell innerNotes = hssfRow.getCell(NumberConstants.NUMBER_18);
					if(null != innerNotes){
						temp = obtainStringVal(innerNotes);
						xlsDto.setInnerNotes(temp);
					}
					//报价声明值默认为0
					xlsDto.setInsuranceAmount(BigDecimal.ZERO);
					//代收货款默认为0
					xlsDto.setCodAmount(BigDecimal.ZERO);
					//付款方式
					Cell paidMethod = hssfRow.getCell(NumberConstants.NUMBER_21);
					temp = obtainStringVal(paidMethod);
					if(StringUtils.isBlank(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第22列："+"付款方式不能为空");
						return;
					}else if(temp.equals("银行卡")){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第22列："+"付款方式不能为银行卡");
						return;
					}else{					
						if(paidMethodMap.containsKey(temp)){
							xlsDto.setPaidMethod(paidMethodMap.get(temp));
							xlsDto.setPaidMethodName(temp);
						}else{
							MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
									+ rowNum + "行，第22列："+"没有该付款方式");
							return;
						}
					}
					//拼接包装
					StringBuilder goodsPackage = new StringBuilder();
					//纸包装
					Cell paperPackage = hssfRow.getCell(NumberConstants.NUMBER_22);
					paper = obtainStringVal(paperPackage);
					if(!isNumber(paper)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第23列："+"不是纯数字");
						return;
					}
					xlsDto.setPaperNum(returnNo(paper));
					if(StringUtils.isNotBlank(paper) && !"0".equals(paper)){
						goodsPackage.append(paper+"纸") ;
					}
					//木包装
					Cell woodPackage = hssfRow.getCell(NumberConstants.NUMBER_23);
					wood = obtainStringVal(woodPackage);
					if(!isNumber(wood)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第24列："+"不是纯数字");
						return;
					}
					xlsDto.setWoodNum(returnNo(wood));
					if(StringUtils.isNotBlank(wood)&& !"0".equals(wood)){
						goodsPackage.append(wood+"木") ;
					}
					//纤包装
					Cell fibrePackage = hssfRow.getCell(NumberConstants.NUMBER_24);
					fibre = obtainStringVal(fibrePackage);
					if(!isNumber(fibre)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第25列："+"不是纯数字");
						return;
					}
					xlsDto.setFibreNum(returnNo(fibre));
					if(StringUtils.isNotBlank(fibre) && !"0".equals(fibre)){
						goodsPackage.append(fibre+"纤") ;
					}
					//托包装
					Cell salverPackage = hssfRow.getCell(NumberConstants.NUMBER_25);
					salver = obtainStringVal(salverPackage);
					if(!isNumber(salver)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第26列："+"不是纯数字");
						return;
					}
					xlsDto.setSalverNum(returnNo(salver));
					if(StringUtils.isNotBlank(salver)  && !"0".equals(salver)){
						goodsPackage.append(salver+"托") ;
					}
					//膜包装
					Cell membranePackage = hssfRow.getCell(NumberConstants.NUMBER_26);
					membrane = obtainStringVal(membranePackage);
					if(!isNumber(membrane)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第27列："+"不是纯数字");
						return;
					}
					xlsDto.setMembraneNum(returnNo(membrane));
					if(StringUtils.isNotBlank(membrane) && !"0".equals(membrane)){
						goodsPackage.append(membrane+"膜") ;
					}
					if(StringUtils.isBlank(paper)&&StringUtils.isBlank(wood)&&StringUtils.isBlank(fibre)&&StringUtils.isBlank(salver)&&StringUtils.isBlank(membrane)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行："+"纸、木、纤、托、膜包装材料中必须选填一种！");
						return;
					}
					//包装
					xlsDto.setGoodsPackage(goodsPackage.toString());
					//木架件数
					Cell woodYokeNum  = hssfRow.getCell(NumberConstants.NUMBER_27);
					temp = obtainStringVal(woodYokeNum);
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第28列："+"不是纯数字");
						return;
					}
					woodenRequire.setStandGoodsNum(returnNo(temp));
					woodenRequire.setStandRequirement(xlsDto.getInnerNotes());
					//木架体积
					Cell woodYokeVolume  = hssfRow.getCell(NumberConstants.NUMBER_28);
					temp = obtainStringVal(woodYokeVolume);
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第29列："+"不是纯数字");
						return;
					}
					woodenRequire.setStandGoodsVolume(BigDecimal.valueOf(returnNo(temp)).setScale(2, BigDecimal.ROUND_HALF_UP));
					//木箱件数
					Cell woodBoxNum = hssfRow.getCell(NumberConstants.NUMBER_29);
					temp = obtainStringVal(woodBoxNum);
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第30列："+"不是纯数字");
						return;
					}
					woodenRequire.setBoxGoodsNum(returnNo(temp));
					woodenRequire.setBoxRequirement(xlsDto.getInnerNotes());
					//木箱体积
					Cell woodBoxVolume = hssfRow.getCell(NumberConstants.NUMBER_30);
					temp = obtainStringVal(woodBoxVolume);
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第31列："+"不是纯数字");
						return;
					}
					woodenRequire.setBoxGoodsVolume(BigDecimal.valueOf(returnNo(temp)).setScale(2, BigDecimal.ROUND_HALF_UP));
					//木托件数
					Cell woodPalletNum = hssfRow.getCell(NumberConstants.NUMBER_31);
					temp = obtainStringVal(woodPalletNum);
					if(!isNumber(temp)){
						MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
								+ rowNum + "行，第31列："+"不是纯数字");
						return;
					}
					woodenRequire.setSalverGoodsNum(returnNo(temp));
					woodenRequire.setSalverRequirement(xlsDto.getInnerNotes());
					
					key = xlsDto.getReceiveOrgCode()+xlsDto.getCustomerPickupOrgCode()+xlsDto.getProductCode();
					setWoodenRequirePending(woodenRequire);
					//加载走货路径
					if(batchWaybillMap.get(key) != null) {
						setLoadLineMap(xlsDto,woodenRequire,loadLineVo,batchWaybillMap,key,true);
					}else {
						setLoadLine(xlsDto,woodenRequire);
						//通过对象保存走货路径信息
						setLoadLineMap(xlsDto,woodenRequire,loadLineVo,batchWaybillMap,key,false);
					}
					setWaybillPending(xlsDto);
					dto = new WaybillPendingDto();
					if(woodenRequire.getBoxGoodsNum()+woodenRequire.getStandGoodsNum()+woodenRequire.getSalverGoodsNum()>0){
						dto.setWoodenRequirePending(woodenRequire);
						//体积转换
						if(woodenRequire.getBoxGoodsNum()+woodenRequire.getStandGoodsNum()>0) {
							if((woodenRequire.getBoxGoodsVolume().add(woodenRequire.getStandGoodsVolume())).compareTo(BigDecimal.ZERO)>0){
								xlsDto.setGoodsVolumeTotal((woodenRequire.getBoxGoodsVolume().add(woodenRequire.getStandGoodsVolume())).multiply(new BigDecimal(ONEPOINTFOUR)).setScale(2, BigDecimal.ROUND_HALF_UP));
								BigDecimal b = woodenRequire.getBoxGoodsVolume().add(woodenRequire.getStandGoodsVolume()).multiply(new BigDecimal(NumberConstants.NUMBER_42)).setScale(2, BigDecimal.ROUND_HALF_UP);
								xlsDto.setGoodsWeightTotal(b.add(xlsDto.getGoodsWeightTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
						}
					}
					if(StringUtils.isNotBlank(xlsDto.getDeliveryCustomerMobilephone())&&StringUtils.isNotBlank(xlsDto.getReceiveCustomerMobilephone())){
						key = xlsDto.getDeliveryCustomerMobilephone()+xlsDto.getReceiveCustomerMobilephone();
					}else if(StringUtils.isNotBlank(xlsDto.getDeliveryCustomerMobilephone())){
						key = xlsDto.getDeliveryCustomerMobilephone();
					}else if(StringUtils.isNotBlank(xlsDto.getReceiveCustomerMobilephone())){
						key = xlsDto.getReceiveCustomerMobilephone();
					}
					if(StringUtils.isNotBlank(xlsDto.getDeliveryCustomerMobilephone())||StringUtils.isNotBlank(xlsDto.getReceiveCustomerMobilephone())){
						//获取map中客户缓存信息
						if(batchWaybillMap.get(key) != null) {
							BatchWaybillComVo bwo = batchWaybillMap.get(key);
							xlsDto.setDeliveryCustomerAddress(bwo.getDeliveryCustomerAddress());
							xlsDto.setDeliveryCustomerCode(bwo.getDeliveryCustomerCode());
							xlsDto.setDeliveryCustomerContact(bwo.getDeliveryCustomerContact());
							xlsDto.setDeliveryCustomerName(bwo.getDeliveryCustomerName());
							xlsDto.setReceiveCustomerAddress(bwo.getReceiveCustomerAddress());
							xlsDto.setReceiveCustomerCode(bwo.getReceiveCustomerCode());
							xlsDto.setReceiveCustomerContact(bwo.getReceiveCustomerContact());
							xlsDto.setReceiveCustomerName(bwo.getReceiveCustomerName());
						}else {
							//查询客户信息并缓存到map中
							queryDeliveryCustomer(xlsDto);
							BatchWaybillComVo bwo=new BatchWaybillComVo();
							bwo.setDeliveryCustomerAddress(xlsDto.getDeliveryCustomerAddress());
							bwo.setDeliveryCustomerCode(xlsDto.getDeliveryCustomerCode());
							bwo.setDeliveryCustomerContact(xlsDto.getDeliveryCustomerContact());
							bwo.setDeliveryCustomerName(xlsDto.getDeliveryCustomerName());
							bwo.setReceiveCustomerAddress(xlsDto.getReceiveCustomerAddress());
							bwo.setReceiveCustomerCode(xlsDto.getReceiveCustomerCode());
							bwo.setReceiveCustomerContact(xlsDto.getReceiveCustomerContact());
							bwo.setReceiveCustomerName(xlsDto.getReceiveCustomerName());
							batchWaybillMap.put(key, bwo);
						}
					}
					//提货方式校验
					if(WaybillConstants.DELIVER_INNER_PICKUP.equals(xlsDto.getReceiveMethod())){
						//提货方式 为 内部带货送货 则校验 收货地址不能为空
						if(StringUtils.isBlank(xlsDto.getReceiveCustomerProvCode())
								&& StringUtils.isBlank(xlsDto.getReceiveCustomerCityCode())
								&& StringUtils.isBlank(xlsDto.getReceiveCustomerDistCode())){
							MsgBox.showInfo("第" + (numSheet + 1) + "页签，" + "第"
									+ rowNum + "行，第10列："+"提货方式为‘内部发货送货’,则收货部门地址不能为空");
							return;
						} 
					}
					
					dto.setWaybillPending(xlsDto);
					list.add(dto);
				}
			}
			 //生成运单号
			createWaybillNo(list,waybillPendingList,woodenRequirelist,dto,labeledGoodList,labeledGoodEntity);
			
			final JXTable table = ui.getTable();
			Object[][] datas = ui.getArray(waybillPendingList);
			// 刷新表格
			BatchCreateWaybillTableModel tableModel = new BatchCreateWaybillTableModel(datas);
			table.setModel(tableModel);
			//new BatchCreateWaybillSearchButtonColumn(table.getColumn(1), ui, tableModel);
			BatchCheckBoxColumn checkColumn = new BatchCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.BatchCheckBoxColumn.choice")), ui);
			List<JCheckBox> lit = checkColumn.getRenderButtonList();
			// send all check box to ui for select all
			ui.setList(lit);
			ui.setCheckBoxColumn(checkColumn);
			
			new BatchWaybillButtonColumn(table.getColumn(i18n.get("foss.gui.creating.BatchWaybillButtonColumn.column")), ui, tableModel,list);
			ui.refreshTable(table);
			//批量模板数据保存到pengding表中
			waybillService.savePengdingWaybill(waybillPendingList);
			//批量保存到代打木架表中
			if(CollectionUtils.isNotEmpty(woodenRequirelist)){
				waybillService.saveWoodenRequirePending(woodenRequirelist);
			}
			waybillService.saveLabeledGood(labeledGoodList);
		} catch (Exception e) {
			MsgBox.showInfo("数据出现异常情况:" + e.getMessage());
			return;
		}
	}

	/**
	 * @Description: 单元格取值
	 * @author Foss-105888-guohao
	 * @date 2015-5-13 10:41:24
	 * @param cell
	 * @return
	 * @version V1.0
	 */
	private String obtainStringVal(Cell cell) {
		if(cell == null){
			return "";
		}
		// 列值
		String cellVal = "";
		// 单元格类型
		switch (cell.getCellType()) {
		// 数值型
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				cellVal = com.deppon.foss.util.DateUtils.convert(
						HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
						com.deppon.foss.util.DateUtils.DATE_FORMAT);
				// 纯数字
			} else {
				DecimalFormat df = new DecimalFormat("0");
				cellVal = df.format(cell.getNumericCellValue());
			}
			break;
		// 此行表示单元格的内容为string类型
		// 字符串型
		case HSSFCell.CELL_TYPE_STRING:
			cellVal = cell.getRichStringCellValue().toString();
			break;
		// 公式型
		case HSSFCell.CELL_TYPE_FORMULA:
			// 读公式计算值
			cellVal = String.valueOf(cell.getNumericCellValue());
			if (cellVal.equals("NaN")) {
				// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		// 布尔
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		// 空值
		case HSSFCell.CELL_TYPE_BLANK:
		// 故障
		case HSSFCell.CELL_TYPE_ERROR:
			cellVal = "";
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal;
	}
	/**
	 * 返回一个数字
	 * @param str
	 * @return
	 */
	public Integer returnNo(String str){
		if("".equals(str)){
			return 0;
		}
		return Integer.parseInt(str);
	}
	/**
	 * 判断是否为纯数字
	 * @param str
	 * @return
	 */
	public boolean isNumber(String str){
		//如果excel单元格内没有输入通过校验
		if("".equals(str)){
			return true;
		}
		return str.matches("^\\d+(\\.\\d+)?$");
	}
	/**
	 * 产生310000001—313000000范围无重复运单号
	 * @param list
	 */
	public void createWaybillNo(List<Object> list,List<WaybillPendingEntity> waybillPendingList,List<WoodenRequirePendingEntity> woodenRequireList,WaybillPendingDto entity
			,List<LabeledGoodEntity> labeledGoodList,LabeledGoodEntity labeledGoodEntity){		
		int minNum = 1;
		 int maxNum =  NUM_3000000;
		 DecimalFormat format = new DecimalFormat("0000000");
		 for(int i=0;i<list.size();i++){
		        Random random = new Random();
		        int s = random.nextInt(maxNum-minNum+1) + minNum;
		        String waybillNo = format.format(s);
		        waybillNo = "31" + waybillNo;
		        String no =	waybillService.selectWaybillNo(waybillNo);
		        StringBuilder sb = new StringBuilder();
		        while(StringUtils.isNotBlank(no)){
		        	 s = random.nextInt(maxNum-minNum+1) + minNum;
		        	 waybillNo = format.format(s);
		        	 waybillNo = sb.append("31").append(waybillNo).toString();
		        	 sb.setLength(0);
				     no = waybillService.selectWaybillNo(waybillNo);
		        }
		        /**
		         * dp-foss-dongjialing
		         * 225131
		         * 保存单号
		         */
		        entity = (WaybillPendingDto)list.get(i);
		        entity.getWaybillPending().setWaybillNo(waybillNo);//运单号
		        entity.getWaybillPending().setId(UUIDUtils.getUUID());//id
		       		        
		        waybillPendingList.add(entity.getWaybillPending());	 
		        if(null!=entity.getWoodenRequirePending()){
		        	 entity.getWoodenRequirePending().setWaybillNo(waybillNo);
		        	 entity.getWoodenRequirePending().setId(UUIDUtils.getUUID());
		        	 woodenRequireList.add(entity.getWoodenRequirePending());
		        }
		        
		        	addLabeledGood(entity.getWaybillPending(),entity.getWoodenRequirePending(),
			        		labeledGoodList,labeledGoodEntity);
		        
		        
		 }
	}
	public void addLabeledGood(WaybillPendingEntity pendingEntity,WoodenRequirePendingEntity wood
			,List<LabeledGoodEntity> labeledGoodList,LabeledGoodEntity labeledGood) {   	
    	if(pendingEntity !=null) {
    		if(pendingEntity.getGoodsQtyTotal()>0) {
    			for (int i = 1; i <= pendingEntity.getGoodsQtyTotal(); i++) {
    				labeledGood = new LabeledGoodEntity();			
    				// id
    				labeledGood.setId(UUIDUtils.getUUID());
    				labeledGood.setWaybillNo(pendingEntity.getWaybillNo());
    				labeledGood.setActive(FossConstants.ACTIVE);
    				labeledGood.setBillTime(pendingEntity.getBillTime());
    				labeledGood.setCreateTime(new Date());
    				labeledGood.setModifyTime(new Date());
    				// 流水号
    				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
    				labeledGood.setInitalVale(FossConstants.YES);
    				if(null!=wood && wood.getStandGoodsNum()>0 && wood.getStandGoodsNum()>=i) {
    					labeledGood.setIsNeedWooden(FossConstants.YES);
    				}    				
    				//labeledGood.setNumberChangItems(labeled.getNumberChangItems());
    				//labeledGood.setOldSerialNo(labeled.getOldSerialNo());
    				//zxy 20131118 ISSUE-4391 包装类型，目前只有木托=SALVER
    				if(null!=wood &&wood.getSalverGoodsNum()>0 && wood.getSalverGoodsNum()>=i){
    					labeledGood.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
    				}    								    				    				
    				
    				labeledGoodList.add(labeledGood);
    			}
    		}
    	}
    }
	
	/**
	 * 校验手机号码格式
	 * @param mobileNo
	 * @return
	 */
	public static boolean isMobileNo(String mobileNo){
		Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");  
		Matcher m = p.matcher(mobileNo); 
		return m.matches();
	}
	/**
	 * 校验座机号码格式
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][0-9]{2,3}-[0-9]{7,8}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{6,7}$");         // 验证没有区号的  
        if(str.length() > NumberConstants.NUMBER_9)
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }
    /**
     * 加载走货线路
     * dp-foss-dongjialing
     * 225131
     * @param bean
     */
	public void setLoadLine(WaybillPendingEntity waybillPending,WoodenRequirePendingEntity woodenRequire) {
		if (waybillPending.getCustomerPickupOrgCode() != null) {	
			IdentityEffectivePlanVo vo = null;
			//先判定是否开启偏线时效方案的开关
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillPending.getProductCode())){
				ConfigurationParamsEntity entity = baseDataService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.OUTER_EFFECTIVE_VERIFIED_CODE,FossConstants.ROOT_ORG_CODE);
				if (entity != null) {
					//判断是否开启开关
					if (FossConstants.YES.equals(entity.getConfValue())) {
						vo = identityOuterEffectivePlan(waybillPending);
						if(vo.isExist()){
							// 查询始发配载部门、最终配载部门以及线路
							queryLodeDepartmentInfo(waybillPending,woodenRequire);
		    				Date leaveTime = getLeaveTime(vo.getDepartDeptCode(),waybillPending);
		    				if (leaveTime != null) {
		    					waybillPending.setPreDepartureTime(leaveTime);// 预计出发时间					
		    					waybillPending.setPreCustomerPickupTime(getPickupDeliveryTime(vo.getDepartDeptCode(),waybillPending));// 预计派送/自提时间	
		    				} else {
		    					LOG.debug("未查询到始发线路发车标准，不能获取预计出发时间");
		    					MsgBox.showITServiceInfo(i18n.get("foss.gui.creating.showpickupstationdialogaction.nosettime"));    					
		    				}
						}else{
							LOG.debug("未查询到产品时效，出发部门："+vo.getDepartDeptName()+"，到达部门："+vo.getArriveDetpName()+"，请确认运输性质是否选择正确！");					
							MsgBox.showITServiceInfo(i18n.get("foss.gui.creating.showpickupstationdialogaction.noproducttime",new Object[]{vo.getDepartDeptName(),vo.getArriveDetpName()}));
						}
					}else{
						// 查询始发配载部门、最终配载部门以及线路
						queryLodeDepartmentInfo(waybillPending,woodenRequire);
					}
				}else{
					// 查询始发配载部门、最终配载部门以及线路
					queryLodeDepartmentInfo(waybillPending,woodenRequire);
				}
			}else if (!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillPending.getProductCode())
					&& !ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybillPending.getProductCode())) {
				//先判断时效存不存在(根据ISSUE-3041修改)
				vo = identityEffectivePlan(waybillPending);
				if(vo.isExist()){
					// 查询始发配载部门、最终配载部门以及线路
					queryLodeDepartmentInfo(waybillPending,woodenRequire);
    				Date leaveTime = getLeaveTime(vo.getDepartDeptCode(),waybillPending);
    				if (leaveTime != null) {
    					waybillPending.setPreDepartureTime(leaveTime);// 预计出发时间					
    					waybillPending.setPreCustomerPickupTime(getPickupDeliveryTime(vo.getDepartDeptCode(),waybillPending));// 预计派送/自提时间	
    				} else {    					
    					LOG.debug("未查询到始发线路发车标准，不能获取预计出发时间");
    					MsgBox.showITServiceInfo(i18n.get("foss.gui.creating.showpickupstationdialogaction.nosettime"));    					
    				}
				}else{					
					LOG.debug("未查询到产品时效，出发部门："+vo.getDepartDeptName()+"，到达部门："+vo.getArriveDetpName()+"，请确认运输性质是否选择正确！");					
					MsgBox.showITServiceInfo(i18n.get("foss.gui.creating.showpickupstationdialogaction.noproducttime",new Object[]{vo.getDepartDeptName(),vo.getArriveDetpName()}));
				}
			}else{
				// 查询始发配载部门、最终配载部门以及线路
				queryLodeDepartmentInfo(waybillPending,woodenRequire);
			}
		}
	}
	private void queryLodeDepartmentInfo(WaybillPendingEntity waybill,WoodenRequirePendingEntity woodenRequire) {
		OrgInfoDto dto = null;
		try {
			// 运输性质非空判断
			if (StringUtil.isBlank(waybill.getProductCode())) {
				LOG.error("运输性质不能为空！");
				throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDepartmentDialog.exception.transType"));
			}

			
		 dto = waybillService.queryLodeDepartmentInfo(false, waybill.getReceiveOrgCode(), waybill.getCustomerPickupOrgCode(), waybill.getProductCode());
			
			if (dto == null 
					/*TO  何龙 ：空运时不需要判断，也可以整体把下面这个判断去掉，现在要考虑下，是否会有潜在的影响？？？ */
					/*|| dto.getFreightRoute() == null*/
					) {								
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			} else {
				FreightRouteEntity freightRoute = dto.getFreightRoute();
				LOG.info("查询走货路径成功。。。");				
				if (freightRoute != null) {
					waybill.setLoadLineCode(freightRoute.getVirtualCode()==null?"NOTEXISTS":freightRoute.getVirtualCode());// 配载线路编码
					LOG.info("配载线路编码:" + freightRoute.getVirtualCode());
					woodenRequire.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码
					LOG.info("代打木架部门编码:" + freightRoute.getPackingOrganizationCode());
					waybill.setDoPacking(freightRoute.getDoPacking());//是否代打木架
					LOG.info("是否代打木架:" + freightRoute.getDoPacking());
				} else {
					waybill.setLoadLineCode("NOTEXISTS");// 配载线路编码
					LOG.info("配载线路编码:获取到的走货路径实体为空");					
				}
				waybill.setLoadOrgCode(dto.getFirstLoadOrgCode());// 配载部门编号
				LOG.info("配载部门编号:" + dto.getFirstLoadOrgCode());
				waybill.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
				LOG.info("最终配载部门编号:" + dto.getLastLoadOrgCode());
				waybill.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());// 最终配置外场
				LOG.info("最终配置外场:" + dto.getLastOutLoadOrgCode());
				//AB货判断
				/*if(dto.getGoodsTypeIsAB()){
					if(waybill.getGoodsWeightTotal().doubleValue()/waybill.getGoodsQtyTotal()>50){
						waybill.setGoodsTypeCode("B");
					}else{//货物平均重量小于50Kg，则进行一下判断
						//若包装带有木或者托，则强制B货，返回TRUE
						if (waybill.getWoodNum().intValue() > 0 || waybill.getSalverNum().intValue() > 0) {
							waybill.setGoodsTypeCode("B");
						}else{
							waybill.setGoodsTypeCode("A");
						}
					}
				}*/
			}
		} catch (BaseInfoInvokeException e) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
		} catch (BusinessException w) {
			
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			
		}
	}
	/**
	 * 获取预计到达派送时间
	 * @param strOrgCode
	 * @param bean
	 * @return
	 */
	private Date getPickupDeliveryTime(String strOrgCode,WaybillPendingEntity waybill) {
		EffectiveDto effectiveDto = new EffectiveDto();
		if (isPickup(waybill)) {
			effectiveDto = waybillService.searchPreSelfPickupTime(strOrgCode, waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				waybill.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getSelfPickupTime();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		} else {
			effectiveDto = waybillService.searchPreDeliveryTime(strOrgCode, waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				waybill.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getDeliveryDate();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		}
	}
	/**
	 * 判断是否自提
	 * @param bean
	 * @return
	 */
	private Boolean isPickup(WaybillPendingEntity waybill) {
		/**
		 * 判断提货方式是否为空
		 */
		if(waybill.getReceiveMethod()!=null){
			String code = waybill.getReceiveMethod();
			if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code) || WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code))

			{
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}		
	}
	/**
	 * 
	 * 获得预计出发时间
	 * 
	 * 
	 */
	private Date getLeaveTime(String strOrgCode,WaybillPendingEntity waybill) {
		Date leaveTime = waybillService.searchPreLeaveTime(strOrgCode, waybill.getLoadOrgCode(), waybill.getProductCode(), new Date());
		return leaveTime;
	}
	/**
	 *  获取时效相关信息
	 */
	private  IdentityEffectivePlanVo identityEffectivePlan(WaybillPendingEntity waybill) {
		String departDeptCode = null;
		IdentityEffectivePlanVo outVo = new IdentityEffectivePlanVo();
		departDeptCode = waybill.getReceiveOrgCode();
		outVo.setDepartDeptCode(departDeptCode);
		outVo.setArriveDetpCode(waybill.getCustomerPickupOrgCode());
		outVo.setArriveDetpName(waybill.getCustomerPickupOrgCode());
		outVo.setExist(waybillService.identityEffectivePlan(departDeptCode,waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), new Date()));
		return outVo;
	}
	
	/**
	 *  获取偏线时效相关信息
	 */
	private  IdentityEffectivePlanVo identityOuterEffectivePlan(WaybillPendingEntity waybill) {
		String departDeptCode = null;
		IdentityEffectivePlanVo outVo = new IdentityEffectivePlanVo();
		departDeptCode = waybill.getReceiveOrgCode();
		outVo.setDepartDeptCode(departDeptCode);
		outVo.setArriveDetpCode(waybill.getCustomerPickupOrgCode());
		outVo.setArriveDetpName(waybill.getCustomerPickupOrgCode());
		outVo.setExist(waybillService.identityOuterEffectivePlan(departDeptCode,waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), new Date()));
		return outVo;
	}
	/**
	 * 设置pengding表信息
	 * dp-foss-dongjialing
	 * 225131	
	 */
	public void setWaybillPending(WaybillPendingEntity waybill) {
		//pc待补录
		waybill.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_PENDING);
		waybill.setIsBatchCreateWaybill(FossConstants.ACTIVE);
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setCreateTime(new Date());// 创建时间
		waybill.setModifyTime(new Date());// 更新时间
		waybill.setBillTime(new Date());// 开单时间
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		String empCode = "";
		String empDeptCode = "";
		String empName="";
		if(user != null){
			EmployeeEntity employee = user.getEmployee();
			if(null != employee){
				empCode = employee.getEmpCode();
				empDeptCode = employee.getOrgCode();
				empName=employee.getEmpName();
			}
		}
		//计费规则 内部代码默认按重量计费
		waybill.setBillingType(WaybillConstants.BILLINGWAY_WEIGHT);
		//返单类别 默认无需返单
		waybill.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		waybill.setCreateUserCode(empCode);
		waybill.setModifyUserCode(empCode);
		waybill.setModifyOrgCode(empDeptCode);
		waybill.setCreateUserName(empName);
		waybill.setPreArriveTime(waybill.getPreCustomerPickupTime());
		waybill.setPickupToDoor(FossConstants.NO);
		waybill.setInsuranceRate(BigDecimal.ZERO);
		waybill.setCodRate(BigDecimal.ZERO);
		waybill.setUnitPrice(BigDecimal.ZERO);
		waybill.setPickupCentralized(FossConstants.NO);
		waybill.setCarDirectDelivery(FossConstants.NO);
		waybill.setPreciousGoods(FossConstants.NO);
		waybill.setSpecialShapedGoods(FossConstants.NO);
		waybill.setSecretPrepaid(FossConstants.NO);
		waybill.setForbiddenLine(FossConstants.YES);
		waybill.setTransportFee(BigDecimal.ZERO);
		waybill.setPackageFee(BigDecimal.ZERO);
		waybill.setValueAddFee(BigDecimal.ZERO);
		waybill.setTotalFee(BigDecimal.ZERO);
		waybill.setCurrencyCode("RMB");
		waybill.setIsWholeVehicle(FossConstants.NO);
		waybill.setWholeVehicleAppfee(BigDecimal.ZERO);
		waybill.setBillWeight(BigDecimal.ZERO);
		waybill.setPickupFee(BigDecimal.ZERO);
		waybill.setServiceFee(BigDecimal.ZERO);
		waybill.setPromotionsFee(BigDecimal.ZERO);
		waybill.setToPayAmount(BigDecimal.ZERO);
		waybill.setPrePayAmount(BigDecimal.ZERO);
		waybill.setCodFee(BigDecimal.ZERO);
		waybill.setOtherFee(BigDecimal.ZERO);
		waybill.setInsuranceFee(BigDecimal.ZERO);
		waybill.setDeliveryGoodsFee(BigDecimal.ZERO);
		
		
	}
	/**
	 * 设置打木架信息
	 * dp-foss-dongjialing
	 * 225131
	 */
    public void setWoodenRequirePending(WoodenRequirePendingEntity woodenRequire) {
    	woodenRequire.setSalverGoodsAmount(BigDecimal.ZERO);
    	woodenRequire.setActive(FossConstants.ACTIVE);
    	woodenRequire.setCreateTime(new Date());
    	woodenRequire.setModifyTime(new Date());
	}
    /**
     * 查询省、市、区编码
     * @param address
     * @return
     */
    public String addressCode(String [] address){
    	AddressFieldDto addressDto = null;
    	AddressFieldDto addressDto2 = null;
    	AddressFieldDto addressDto3 = null;
    	StringBuilder addressCode = new StringBuilder();
    	if (address!=null && address.length>0){
    		if(address.length==1){
    			addressDto = onlineAddressServiceHessianRemoting.onlineQueryProviceByName(address[0]);
    			if(addressDto!=null){
    				  addressCode.append(addressDto.getProvinceId());
    				  addressCode.append(",");
    			}
    		}else if(address.length==2){
    			addressDto=onlineAddressServiceHessianRemoting.onlineQueryProviceByName(address[0]);
    			if(addressDto!=null){
    				addressCode.append(addressDto.getProvinceId());
    				addressCode.append(",");
    			}
    			addressDto2=onlineAddressServiceHessianRemoting.onlineQueryCityByName(address[1]);
    			if(addressDto2!=null){
    				addressCode.append(addressDto2.getCityId());
    				addressCode.append(",");
    			}
    		}else if(address.length >= NumberConstants.NUMBER_3){
    			addressDto=onlineAddressServiceHessianRemoting.onlineQueryProviceByName(address[0]);
    			if(addressDto!=null){
    				addressCode.append(addressDto.getProvinceId());
    				addressCode.append(",");
    			}
    			addressDto2=onlineAddressServiceHessianRemoting.onlineQueryCityByName(address[1]);
    			if(addressDto2!=null){
    				addressCode.append(addressDto2.getCityId());
    				addressCode.append(",");
    			}
    			addressDto3=onlineAddressServiceHessianRemoting.onlineQueryCountyByName(address[2]);
    			if(addressDto3!=null){
    				addressCode.append(addressDto3.getCountyId());
    				addressCode.append(",");
    			}
    			if(address.length > NumberConstants.NUMBER_3 ){
    				addressCode.append(address[NumberConstants.NUMBER_3]);
    				addressCode.append(",");
    			}
    		}
			 
		}
    	if(addressCode.length()>0){
    		addressCode.deleteCharAt(addressCode.lastIndexOf(","));
    	}
    	return addressCode.toString();
    }
    /**
     * dp-foss-dongjialing
     * @param waybillPending
     * @param woodenRequire
     * @param loadLineVo
     * @param loadLineMap
     */
    public void setLoadLineMap(WaybillPendingEntity waybillPending,WoodenRequirePendingEntity woodenRequire,BatchWaybillComVo loadLineVo,Map<String,BatchWaybillComVo> loadLineMap,String key,boolean falg) {
    	if(falg) {
    		loadLineVo = loadLineMap.get(key);
    		waybillPending.setDoPacking(loadLineVo.getDoPacking());
    		waybillPending.setLastLoadOrgCode(loadLineVo.getLastLoadOrgCode());
    		waybillPending.setLastOutLoadOrgCode(loadLineVo.getLastOutLoadOrgCode());
    		waybillPending.setLoadLineCode(loadLineVo.getLoadLineCode());
    		waybillPending.setLoadOrgCode(loadLineVo.getLoadOrgCode());
    		waybillPending.setPreCustomerPickupTime(loadLineVo.getPreCustomerPickupTime());
    		waybillPending.setPreDepartureTime(loadLineVo.getPreDepartureTime());
    		if(loadLineVo.getGoodsTypeAB()!=null){
    			if(waybillPending.getGoodsWeightTotal().doubleValue()/waybillPending.getGoodsQtyTotal()> NumberConstants.NUMBER_50){
        			waybillPending.setGoodsTypeCode("B");
    			}else{//货物平均重量小于50Kg，则进行一下判断
    				//若包装带有木或者托，则强制B货，返回TRUE
    				if (waybillPending.getWoodNum().intValue() > 0 || waybillPending.getSalverNum().intValue() > 0) {
    					waybillPending.setGoodsTypeCode("B");
    				}else{
    					waybillPending.setGoodsTypeCode("A");
    				}
    			}
    		}
    		
    		woodenRequire.setPackageOrgCode(loadLineVo.getPackageOrgCode());     		
    	} else {
    		loadLineVo = new BatchWaybillComVo();
        	loadLineVo.setDoPacking(waybillPending.getDoPacking());
        	loadLineVo.setLastLoadOrgCode(waybillPending.getLastLoadOrgCode());
        	loadLineVo.setLastOutLoadOrgCode(waybillPending.getLastOutLoadOrgCode());
        	loadLineVo.setLoadLineCode(waybillPending.getLoadLineCode());
        	loadLineVo.setLoadOrgCode(waybillPending.getLoadOrgCode());
        	loadLineVo.setPreCustomerPickupTime(waybillPending.getPreCustomerPickupTime());
        	loadLineVo.setPreDepartureTime(waybillPending.getPreDepartureTime());
        	loadLineVo.setPackageOrgCode(woodenRequire.getPackageOrgCode());     
        	loadLineVo.setGoodsTypeAB(waybillPending.getGoodsTypeCode());
        	loadLineMap.put(key,loadLineVo);
    	}    		
    }
    /**
   	 * 根据查询条件查询客户信息
   	 */
   	public void queryDeliveryCustomer(WaybillPendingEntity bean) {
   		List<CustomerQueryConditionDto> contactList=null;
   		if(StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())){
   			// 查询条件
   	   		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
   	   		// 发货人手机
   	   		String mobilePhone = com.deppon.foss.framework.shared.util.string.StringUtil.defaultIfNull(bean.getDeliveryCustomerMobilephone());
   	   		// 将电话号码中的手机号码解析出来
   	   		List<String> mobiles = null;
   	   		// 判断手机是否为空
  	   		 if (StringUtils.isNotEmpty(mobilePhone)) {
  	   			mobiles = new ArrayList<String>();
  	   			mobiles.add(mobilePhone);

  	   			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
  	   			dto.setMobilePhone(mobilePhone);
  	   			dto.setExactQuery(true);
  	   			//发票标记时间
  	   			dto.setInvoiceDate(new Date());
  	   			dtoList.add(dto);
  	   		}
  	   		// 根据条件查询客户信息
  	   		contactList = waybillService.queryCustomerByConditionList(dtoList);
  	   		// 若CRM中查询无果，则从历史客户中查询
  	   		if (CollectionUtils.isEmpty(contactList)) {
  	   			if (CollectionUtils.isNotEmpty(mobiles)) {
  	   				// 将手机中的号码加入集合进行查询
  	   				mobiles.add(mobilePhone);
  	   				List<CustomerQueryConditionDto> custMobile = waybillService.queryHisDeliveryCusByMobileList(mobiles);
  	   				if (CollectionUtils.isNotEmpty(custMobile)) {
  	   					if (CollectionUtils.isEmpty(contactList)) {
  	   						contactList = custMobile;
  	   					} else {
  	   						contactList.addAll(custMobile);
  	   					}
  	   				}
  	   			}   	   			
  	   		}
  	   		if(CollectionUtils.isNotEmpty(contactList) &&  contactList.get(0)!=null){
  	   			if(StringUtils.isEmpty(bean.getDeliveryCustomerCode())){
  	   				//发货客户编码
  	   				bean.setDeliveryCustomerCode(contactList.get(0).getCustCode());
  	   			}else if(StringUtils.isEmpty(bean.getDeliveryCustomerContact())){
  	   				//发货联系人
  	   				bean.setDeliveryCustomerContact(contactList.get(0).getContactName());
  	   			}else if(StringUtils.isEmpty(bean.getDeliveryCustomerAddress())){
  	   				//发货人地址
  	   				bean.setDeliveryCustomerAddress(contactList.get(0).getAddress());
  	   			}else if(StringUtils.isEmpty(bean.getDeliveryCustomerName())){
  	   				//发货客户名称
  	   				bean.setDeliveryCustomerName(contactList.get(0).getCustName());
  	   			}
  	   		}
  		}
   		if(StringUtils.isNotEmpty(bean.getReceiveCustomerMobilephone())){
   			// 查询条件
   	   		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
   	   		// 收货人手机
   	   		String mobilePhone = com.deppon.foss.framework.shared.util.string.StringUtil.defaultIfNull(bean.getReceiveCustomerMobilephone());
   	   		// 将电话号码中的手机号码解析出来
   	   		List<String> mobiles = null;
   	   		// 判断手机是否为空
  	   		 if (StringUtils.isNotEmpty(mobilePhone)) {
  	   			mobiles = new ArrayList<String>();
  	   			mobiles.add(mobilePhone);

  	   			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
  	   			dto.setMobilePhone(mobilePhone);
  	   			dto.setExactQuery(true);
  	   			//发票标记时间
  	   			dto.setInvoiceDate(new Date());
  	   			dtoList.add(dto);
  	   		}
  	   		// 根据条件查询客户信息
  	   		contactList = waybillService.queryCustomerByConditionList(dtoList);
  	   		// 若CRM中查询无果，则从历史客户中查询
  	   		if (CollectionUtils.isEmpty(contactList)) {
  	   			if (CollectionUtils.isNotEmpty(mobiles)) {
  	   				// 将手机中的号码加入集合进行查询
  	   				mobiles.add(mobilePhone);
  	   				List<CustomerQueryConditionDto> custMobile = waybillService.queryHisDeliveryCusByMobileList(mobiles);
  	   				if (CollectionUtils.isNotEmpty(custMobile)) {
  	   					if (CollectionUtils.isEmpty(contactList)) {
  	   						contactList = custMobile;
  	   					} else {
  	   						contactList.addAll(custMobile);
  	   					}
  	   				}
  	   			}   	   			
  	   		}
  	   		if(CollectionUtils.isNotEmpty(contactList) && contactList.get(0)!=null){
  	   			if(StringUtils.isEmpty(bean.getReceiveCustomerCityCode())){
  	   				//收货客户编码
  	   				bean.setReceiveCustomerCityCode(contactList.get(0).getCustCode());
  	   			}else if(StringUtils.isEmpty(bean.getReceiveCustomerContact())){
  	   				//收货客户联系人
  	   				bean.setReceiveCustomerContact(contactList.get(0).getContactName());
  	   			}else if(StringUtils.isEmpty(bean.getReceiveCustomerAddress())){
  	   				//收货人地址
  	   				bean.setReceiveCustomerAddress(contactList.get(0).getAddress());
  	   			}else if(StringUtils.isEmpty(bean.getReceiveCustomerName())){
  	   				//收货客户名称
  	   				bean.setReceiveCustomerName(contactList.get(0).getCustName());
  	   			}
  	   		}
   		}
   	}
	/**
	 * 初始化
	 */

	@Override
	public void setInjectUI(OpenBatchCreateWaybillUI ui) {
		this.ui = ui;
	}

}