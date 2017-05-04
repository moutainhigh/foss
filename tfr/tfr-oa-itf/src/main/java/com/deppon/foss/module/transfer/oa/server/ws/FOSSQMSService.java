package com.deppon.foss.module.transfer.oa.server.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.NolabelRequestFromQMSDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseFromQmsDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception.NoLabelGoodsException;
import com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadService;
import com.deppon.foss.module.transfer.load.api.shared.dto.ErrorVolumeDeductionDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.ESBHeaderConstant;
/**
 * @author niuly
 * @function 无标签多货提供给QMS的服务类
 */
public class FOSSQMSService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FOSSQMSService.class);
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	//无标签多货服务类
	private INoLabelGoodsService noLabelGoodsService;
	//运单服务类
	private IWaybillManagerService waybillManagerService;
	//走货路径
	private ICalculateTransportPathService calculateTransportPathService;
	//部门信息
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private ILoaderWorkloadService loaderWorkloadService;
	
	private IProductService productService;
	/**
	 * @author nly
	 * @date 2015年6月19日 上午9:16:18
	 * @function 用于QMS认领时的FOSS无标签多货登出操作；QMS异常货处理时更新FOSS无标签多货状态
	 * @param requestStr
	 * @return
	 */
	@POST
	@Path("/updateNolabelGoods")
	public String updateNolabelGoods(String requestStr) {
		LOGGER.error("QMS更新无标签信息服务开始...");
		LOGGER.error("QMS更新无标签信息requestStr===" + requestStr);
		
		//ESB必须字段
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		response.setCharacterEncoding("UTF-8");
		
		//响应消息
		String responseStr = "";
		ResponseFromQmsDto responseDto = new ResponseFromQmsDto();
		
		NolabelRequestFromQMSDto nolabelDto = new NolabelRequestFromQMSDto();
		String isExpress = "";
		try {
			//订阅单号信息转换
			nolabelDto  = JSON.parseObject(requestStr,NolabelRequestFromQMSDto.class);
		} catch (Exception e) {
			responseDto.setCallResult("0");
			responseDto.setErrmsg("请求失败" + e.getMessage());
			responseStr = JSON.toJSONString(responseDto);
			return responseStr;
		} 
		
		// 无标签多货编号
		String errorNo = nolabelDto.getErrorCode();
		// 原运单号
		String originalWaybillNo = nolabelDto.getOriginalWaybillNo();
		// 原流水号
		String originalSerialNo = nolabelDto.getOriginalSerialNo();
		// 无标签运单号
		String noLabelBillNo = nolabelDto.getNoLabelBillNo();
		// 无标签流水号
		String noLabelSerialNo = nolabelDto.getNoLabelSerialNo();
		// 操作人工号
		String operatorCode = nolabelDto.getOperatorCode();
		// 操作人姓名
		String operatorName = nolabelDto.getOperatorName();
		// 操作部门编码，非标杆编码
		String operateDeptCode = nolabelDto.getOperateDeptCode();
		//运单类型:ECS、FOSS
		String waybillStyle = nolabelDto.getWaybillStyle();
		LOGGER.info("*********QMS调用FOSS无标签多货找到接口，更新无标签多货信息，无标签多货编号：" + nolabelDto.getErrorCode() 
				+ "，waybillStyle：" + nolabelDto.getWaybillStyle() + "************");
		// QMS认领操作时，FOSS同时进行更新无标签多货及登出操作
		if (StringUtils.equals("FIND", nolabelDto.getOperateType())) {

			// 判断是否有为空的字段
			if (!StringUtils.isEmpty(errorNo)
					&& !StringUtils.isEmpty(originalSerialNo)
					&& !StringUtils.isEmpty(originalWaybillNo)
					&& !StringUtils.isEmpty(noLabelBillNo)
					&& !StringUtils.isEmpty(noLabelSerialNo)
					&& !StringUtils.isEmpty(operatorCode)
					&& !StringUtils.isEmpty(operatorName)
					&& !StringUtils.isEmpty(operateDeptCode)) {


				// 322610-判断运单类型，若为ECS运单，QMS调用ECS接口入库，同时通知FOSS变更无标签多货信息（原运单号、原流水号、无标签多货状态-已确认）
				if("ECS".equals(waybillStyle)){
					LOGGER.info("*********QMS调用FOSS无标签多货找到接口，更新无标签多货信息，无标签多货编号：" + nolabelDto.getErrorCode() 
							+ "，NoLabelBillNo：" + nolabelDto.getNoLabelBillNo() + "************");
					int updateQty = noLabelGoodsService.updateNoLabelGoodsProcessStatus(nolabelDto.getNoLabelBillNo(), 
							ExceptionGoodsConstants.NO_LABEL_GOODS_FIND, nolabelDto.getErrorCode(), nolabelDto.getOriginalWaybillNo(),nolabelDto.getOriginalSerialNo());
					isExpress = this.getWaybillTypeByWaybillNo(originalWaybillNo);
					responseDto.setIsExpress(isExpress); //应qms要求添加是否快递字段
					if(updateQty < 1){
						//更新失败
						responseDto.setCallResult("0");
						responseDto.setErrmsg("认领失败，更新无标签处理结果失败，运单号或流水号：" + originalWaybillNo + "--" + originalSerialNo);
						LOGGER.error("*********QMS调用FOSS无标签多货找到接口，更新无标签多货信息失败，不存在无标签多货编号：" + nolabelDto.getErrorCode() + "************");
					}else{
						responseDto.setCallResult("1");
						responseDto.setErrmsg("认领成功：" + originalWaybillNo + "--" + originalSerialNo);
						LOGGER.error("*********QMS调用FOSS无标签多货找到接口，更新无标签处理结果成功，运单号或流水号："	+ originalWaybillNo	+ "--" + originalSerialNo + "***********");
					}
				}else{
					// 若为FOSS运单，调用FOSS接口入库并变更无标签多货信息

					boolean serialNoExists = waybillManagerService.isSerialNoExsits(originalWaybillNo, originalSerialNo);
					// PDA-876
					if (!serialNoExists) {
							// 运单号或流水号不存在
							responseDto.setCallResult("0");
							responseDto.setErrmsg("认领失败，不存在运单号或流水号：" + originalWaybillNo + "--" + originalSerialNo);
							LOGGER.error("*********QMS调用FOSS无标签多货找到接口，更新无标签处理结果失败，不存在运单号或流水号："	+ originalWaybillNo	+ "--" + originalSerialNo + "***********");
					} else {
						try {
							responseDto = noLabelGoodsService.findAndLogOut(nolabelDto, responseDto);
							isExpress = this.getWaybillTypeByWaybillNo(originalWaybillNo);
							responseDto.setIsExpress(isExpress); //应qms要求添加是否快递字段
						} catch (Exception e) {
							e.printStackTrace();
							responseDto.setCallResult("0");
							responseDto.setErrmsg("认领失败，认领异常"+e.getMessage());
							LOGGER.error("*********QMS调用FOSS无标签多货找到接口，执行失败，更新无标签多货异常************");
						}
					}

				}

			} else {
				responseDto.setCallResult("0");
				responseDto.setErrmsg("认领失败，必填字段为空");
				LOGGER.error("*********QMS调用FOSS无标签多货找到接口，执行失败，必填字段为空************");
			}

		} else if (StringUtils.equals("DONE", nolabelDto.getOperateType())) {
			try {
				// 异常货已处理
				NoLabelGoodsEntity entity = new NoLabelGoodsEntity();
				entity.setOaErrorNo(errorNo);
				entity.setNoLabelBillNo(noLabelBillNo);
				entity.setNoLabelSerialNo(noLabelSerialNo);
				entity.setGoodsStatus(ExceptionGoodsConstants.GOODS_STATUS_EXCEPTIONCONFIRM);
				noLabelGoodsService.updateNoLabelGoods(entity);
				responseDto.setCallResult("1");
				isExpress = this.getWaybillTypeByWaybillNo(originalWaybillNo);
				responseDto.setIsExpress(isExpress); //应qms要求添加是否快递字段
				LOGGER.error("*********QMS调用FOSS无标签多货-异常货已处理确认接口，执行成功，更新无标签多货状态为“异常货已处理确认”************");
			} catch (Exception e) {
				responseDto.setCallResult("0");
				responseDto.setErrmsg("异常货处理已确认失败，发生异常"+e.getMessage());
				LOGGER.error("*********QMS调用FOSS无标签多货找到接口，执行失败，必填字段为空************");
			}

		}

		responseStr = JSON.toJSONString(responseDto);
		LOGGER.error("QMS更新无标签信息responseStr===" + responseStr);
		LOGGER.error("QMS更新无标签信息服务结束...");
		
		return responseStr;
	}
	/**
	 * 根据运单号判断是否快递
	 * @author 257220
	 * @date 2015-6-11下午4:47:25
	 */
	private String getWaybillTypeByWaybillNo(String waybillNo){
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(waybillEntity == null){
			throw new NoLabelGoodsException("运单不存在","运单不存在");  
		}
		String productCode = waybillEntity.getProductCode();
		Boolean isExpress = productService.onlineDetermineIsExpressByProductCode(productCode,new Date());
		return isExpress ? "Y": "N";
	}
	/**
	 * @author nly
	 * @date 2015年6月24日 上午9:13:30
	 * @function QMS无标签多货认领时，调用此接口，根据运单号查询实际走货路径中的部门，即经手部门
	 * @param requestStr
	 * @return
	 */
	@POST
	@Path("/queryPassDepts")
	public String queryDepts(String requestStr) {
		LOGGER.error("QMS查询运单经手部门requestStr===" + requestStr);
		
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject obj = JSON.parseObject(requestStr);
		String waybillNo = obj.getString("waybillNo");
		
		JSONArray codesJson = new JSONArray();  
		JSONArray namesJson = new JSONArray();
		obj.put("passOrgCodes", codesJson);
		obj.put("passOrgNames", namesJson);
		//判断运单号是否存在
		WaybillEntity  waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(null == waybill) {
	        obj.put("errorInfo", "0"); //运单号不存在
	        LOGGER.error("QMS查询运单经手部门-运单号不存在responseStr===" + obj.toJSONString());
	        return obj.toJSONString();
		}
		try{
			List<String>  deptCodes = calculateTransportPathService.queryPassDeptCodes(waybillNo, null);
			
			List<String> depts = new ArrayList<String>();
			List<String> deptNames =  new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(deptCodes)) {
				for(String code : deptCodes){
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
					if(null != org) {
						depts.add(org.getUnifiedCode());
						deptNames.add(org.getName());
					}
				}
			}
			
			codesJson.addAll(depts); 
			namesJson.addAll(deptNames);
			LOGGER.error("QMS查询运单经手部门正常结束responseStr===" + obj.toJSONString());
			
			return obj.toJSONString();
		} catch(Exception e) {
	        obj.put("errorInfo", "1"); //查询失败
	        LOGGER.error("QMS查询运单经手部门异常responseStr===" + obj.toJSONString());
	        return obj.toJSONString();
		}
	}
	/**
	 * 添加差错货量
	 * @return
	 **/
	@POST
	@Path("/addErrorVolumeDeduction")
	public String addErrorVolumeDeduction(String requestStr){
		LOGGER.info("接收数据："+requestStr);
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		response.setCharacterEncoding("UTF-8");
		ResponseFromQmsDto responseDto = new ResponseFromQmsDto();
		ErrorVolumeDeductionDto errorVolumeDeductionDto = new ErrorVolumeDeductionDto();
		String responseStr = "";
		try{
			errorVolumeDeductionDto = JSON.parseObject(requestStr,ErrorVolumeDeductionDto.class);
			errorVolumeDeductionDto.setId(UUIDUtils.getUUID());
			loaderWorkloadService.addErrorVolumnDeduction(errorVolumeDeductionDto);
			responseDto.setCallResult("1");
		}catch(Exception e){
			e.printStackTrace();
			responseDto.setCallResult("0");
			responseDto.setErrmsg("请求失败"+e.getMessage());
			responseStr = JSON.toJSONString(responseDto);
			return responseStr;
		}
		responseStr = JSON.toJSONString(responseDto);
		LOGGER.info("返回数据："+responseStr);
		return responseStr;
	}
 
	public void setNoLabelGoodsService(INoLabelGoodsService noLabelGoodsService) {
		this.noLabelGoodsService = noLabelGoodsService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setLoaderWorkloadService(
			ILoaderWorkloadService loaderWorkloadService) {
		this.loaderWorkloadService = loaderWorkloadService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
}
