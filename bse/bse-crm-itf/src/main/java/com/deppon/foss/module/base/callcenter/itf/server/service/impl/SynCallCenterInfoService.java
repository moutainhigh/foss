package com.deppon.foss.module.base.callcenter.itf.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.foss.PashReminderRequest;
import com.deppon.esb.inteface.domain.foss.PashReminderResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.inteface.ccsyncfossservice.CallCenterSyncFossService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICallCenterWaybillInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CallCenterWaybillInfoException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.inteface.ccsyncfossservice.CommonException;
/**
 * 同步呼叫中心催运单信息服务 WEB SERVICE服务端
 * 
 * 同步呼叫中心催单信息接口
                     客户在呼叫中心系统进行催单，呼叫中心会将客户催单信息发送给FOSS,由FOSS系统在待办页面展示并由相应部门处理
        前置条件：
        1、	FOSS系统正常运行；
        2、	CC系统能正常调用本接口；
        
        接口场景：
        1、	CC系统中催单更改（新增、修改），则调用此接口，传入客户的催单信息；
        
        后置动作：
        1、	CC系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
        
        接口异常处理：
        接口调用发生异常需要抛出，由CC系统统一处理异常；
 * 
 * @author 132599-foes-shenweihua
 * @date 2014-07-17 下午5:34:21
 * @since
 * @version
 */
public class SynCallCenterInfoService implements CallCenterSyncFossService{
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SynCallCenterInfoService.class);
	
    /**
     * 创建人修改人 常量
     */
 	private static final String CC = "CC";
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 催运单信息接口
     */
    private ICallCenterWaybillInfoService callCenterWaybillInfoService;
    
    /**
     * 运单管理接口
     */
    private IWaybillManagerService waybillManagerService;
    
    /**
     * 货签服务service
     */
    private ILabeledGoodService labeledGoodService;
    
    /**
     * 库存service
     */
    private IStockService stockService;
    
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 营业部信息接口
     */
    private ISaleDepartmentService saleDepartmentService;
    
    /**
     * 库区service
     */
    private IGoodsAreaService goodsAreaService;
     
    /**
     * 从CC系统同步呼叫中心催运单信息
     * @author 132599-foes-shenweihua
     * @date 2014-07-17 下午5:34:21
     * @param syncRequest
	 * @param esbHeader
	 * @return
	 * @throws CommonException
     */
	@Override
	public PashReminderResponse sysQuerypashReminderBasic(
			PashReminderRequest pashReminderRequest, Holder<ESBHeader> esbHeader)
			throws CommonException {
		LOGGER.info("同步CC催运单信息开始...............");
		// 信息头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		// 响应对象
		PashReminderResponse response = new PashReminderResponse();
		if(null != pashReminderRequest && 
				(StringUtils.isNotBlank(pashReminderRequest.getId()))
				&& StringUtils.isNotBlank(pashReminderRequest.getCode())){
			// 业务锁
			MutexElement mutex = new MutexElement(String.valueOf(
					pashReminderRequest.getId()),"CC_PRESS_WAYBILL_NO",
					MutexElementType.CC_PRESS_WAYBILL_NO);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				response.setIfSuccess("N");
				response.setAcceptDepartment(null);
				response.setErrMsg("运单号为:"+pashReminderRequest.getCode()+",催单凭证号为:"+pashReminderRequest.getId()+"的催单信息失败加锁");
				return response;
			}
			//cc催单信息同步数据处理
			response = this.syncInfo(pashReminderRequest);
			
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}else{
			LOGGER.info("请求对象不存在或请求的催单凭证号为空或者请求的运单号为空！.......");
			response.setIfSuccess("N");
			response.setAcceptDepartment(null);
			response.setErrMsg("请求对象不存在或请求的催单凭证号为空或者请求的运单号为空！.......");
		}
		return response;
	}
	
	/**
	 * cc催单信息同步数据处理
     * @author 132599-foes-shenweihua
     * @date 2014-07-18 上午11:34:21
	 * @param pashReminderRequest
	 * @return
	 * @throws CommonException
	 */
	private PashReminderResponse syncInfo(
			PashReminderRequest pashReminderRequest) throws CommonException {
		LOGGER.info("同步CC催运单信息开始...............");
		// 响应对象
		PashReminderResponse response = new PashReminderResponse();
		if (null == pashReminderRequest) {
			return response;
		}
		// 验证同步过来的信息在Foss系统中是否存在
		boolean flag = callCenterWaybillInfoService
				.queryCallInfoByCallCenterWaybillNo(pashReminderRequest.getId());

		LOGGER.info("催单凭证号 :" + pashReminderRequest.getId());
		CallCenterWaybillInfoEntity entity = convertCallCenterInfo(pashReminderRequest);
		if (flag) {
			LOGGER.info("该运单的催单凭证号重复，该运单已经在催单了！");
			response.setIfSuccess("N");
			response.setAcceptDepartment(null);
			response.setErrMsg("运单号为:" + pashReminderRequest.getCode()
					+ ",催单凭证号为:" + pashReminderRequest.getId()
					+ "的催单信息正在处理中，请耐心等待！");
			return response;
			// 下面代码是考虑更新的情况，暂时用不上。
			// try {
			// int result =
			// callCenterWaybillInfoService.updateCallCenterWaybillInfo(entity);
			// if (result > 0) {
			// LOGGER.info("修改CC催运单信息成功！");
			// response.setIfSuccess("Y");
			// WaybillEntity wayentity =
			// waybillManagerService.queryWaybillBasicByNo(pashReminderRequest.getCode());
			// if(null !=wayentity &&
			// StringUtils.isNotBlank(wayentity.getCustomerPickupOrgCode())){
			// String customerPickupOrgName =
			// orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(wayentity.getCustomerPickupOrgCode());
			// if(StringUtils.isNotBlank(customerPickupOrgName)){
			// response.setAcceptDepartment(customerPickupOrgName);
			// }else{
			// response.setErrMsg("根据运单号没有查出相应的到达网点名称！");
			// }
			// return response;
			// }else{
			// response.setErrMsg("根据运单号没有查出相应的运单信息！");
			// return response;
			// }
			// } else {
			// LOGGER.info("修改CC催运单信息失败！");
			// response.setIfSuccess("N");
			// response.setAcceptDepartment(null);
			// response.setErrMsg("运单号为:"+pashReminderRequest.getCode()+",催单凭证号为:"+pashReminderRequest.getId()+"的催单信息失败加锁");
			// return response;
			// }
			// } catch (CallCenterWaybillInfoException e) {
			// LOGGER.error(e.getMessage(), e);
			// throw new CommonException(e.getMessage(), e);
			// }
		}
		try {
			// 货签信息实体，查询流水号用的
			List<LabeledGoodEntity> labelGoodEntityList = labeledGoodService
					.queryAllSerialByWaybillNo(pashReminderRequest.getCode());
			// 根据运单号查询运单实体
			WaybillEntity wayentity = waybillManagerService
					.queryWaybillBasicByNo(pashReminderRequest.getCode());
			if (CollectionUtils.isEmpty(labelGoodEntityList)
					|| null == wayentity) {
				LOGGER.info("新增CC催运单信息失败！");
				response.setIfSuccess("N");
				response.setAcceptDepartment(null);
				response.setErrMsg("根据运单号查询运单实体或者流水号为空！");
				return response;
			}
			if (StringUtils.isBlank(wayentity.getCustomerPickupOrgCode())) {
				LOGGER.info("新增CC催运单信息失败！");
				response.setIfSuccess("N");
				response.setAcceptDepartment(null);
				response.setErrMsg("该运单的到达网点code为空");
				return response;
			}
			// new 一个用来装货件库存实体的list
			List<StockEntity> stockEntityList = new ArrayList<StockEntity>();

			// 根部部门编码获取营业部信息
			SaleDepartmentEntity salEntity = saleDepartmentService
					.querySimpleSaleDepartmentByCode(wayentity
							.getCustomerPickupOrgCode());
			// 判断是否为驻地营业部
			if (null != salEntity
					&& FossConstants.YES.equals(salEntity.getStation())) {
				// 判断驻地营业部是否有驻地外场编码
				if (StringUtils.isBlank(salEntity.getTransferCenter())) {
					LOGGER.info("新增CC催运单信息失败！");
					response.setIfSuccess("N");
					response.setAcceptDepartment(null);
					response.setErrMsg("根据运单号:" + pashReminderRequest.getCode()
							+ "查出是驻地营业部但是驻地外场编码为空！");
					return response;
				}
				// 查询驻地外场所对应的库区
				List<GoodsAreaEntity> goodsAreaList = goodsAreaService
						.queryGoodsAreaListByType(
								salEntity.getTransferCenter(),
								DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				if (CollectionUtils.isEmpty(goodsAreaList)) {
					LOGGER.info("新增CC催运单信息失败！");
					response.setIfSuccess("N");
					response.setAcceptDepartment(null);
					response.setErrMsg("根据运单号:" + pashReminderRequest.getCode()
							+ "查出是驻地营业部但没查到对应的驻地库区！");
					return response;
				}
				// 获取驻地外场对应的库区编码
				String goodAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
				if (StringUtils.isBlank(goodAreaCode)) {
					LOGGER.info("新增CC催运单信息失败！");
					response.setIfSuccess("N");
					response.setAcceptDepartment(null);
					response.setErrMsg("根据运单号:" + pashReminderRequest.getCode()
							+ "查出是驻地营业部但对应的驻地库区编码为空！");
					return response;
				}
				for (LabeledGoodEntity labelEntity : labelGoodEntityList) {
					StockEntity stockEntity = stockService
							.queryStockEntityByNos(
									salEntity.getTransferCenter(),
									pashReminderRequest.getCode(),
									labelEntity.getSerialNo(), goodAreaCode);

					if (null != stockEntity) {
						stockEntityList.add(stockEntity);
						break;
					}
				}

			} else {
				// 非驻地营业部进入这个循环
				for (LabeledGoodEntity labelEntity : labelGoodEntityList) {
					// 根据运单号、流水号、运单的到达网点去确定货物有没有到到达网点的库区
					StockEntity stockEntity = stockService
							.queryStockEntityByNos(
									wayentity.getCustomerPickupOrgCode(),
									pashReminderRequest.getCode(),
									labelEntity.getSerialNo(), null);
					if (null != stockEntity) {
						stockEntityList.add(stockEntity);
						break;
					}
				}
			}
			// 如果货物到到达网点的库区了就新增催单信息并生成待办，否则不新增并向CC反馈失败信息。
			if (CollectionUtils.isEmpty(stockEntityList)) {
				LOGGER.info("新增CC催运单信息失败！");
				response.setIfSuccess("N");
				response.setAcceptDepartment(null);
				response.setErrMsg("运单号为:" + pashReminderRequest.getCode()
						+ "的货物还未到到达网点的库区！");
				return response;
			}
			int result = callCenterWaybillInfoService
					.addCallCenterWaybillInfo(entity);
			if (result <= 0) {
				LOGGER.info("新增CC催运单信息失败！");
				response.setIfSuccess("N");
				response.setAcceptDepartment(null);
				response.setErrMsg("运单号为:" + pashReminderRequest.getCode()
						+ ",催单凭证号为:" + pashReminderRequest.getId()
						+ "的催单信息新增失败！");
				return response;
			}
			LOGGER.info("新增CC催运单信息成功！");
			response.setIfSuccess("Y");
			// 根据到达网点查出对应的组织信息
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(wayentity
							.getCustomerPickupOrgCode());
			if (orgEntity != null
					&& StringUtils.isNotBlank(orgEntity.getUnifiedCode())) {
				response.setAcceptDepartment(orgEntity.getUnifiedCode());
			} else {
				response.setErrMsg("根据运单号没有查出相应的到达网点组织或者组织标杆编码！");
			}
			return response;
		} catch (CallCenterWaybillInfoException e) {
			LOGGER.error(e.getMessage(), e);
			throw new CommonException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 把CC同步过来的信息转化为FOSS信息实体
	 * </p>
	 * 
	 * @author 132599-shenweihua
	 * @date 2014-7-18上午10:10:44
	 * @param pashReminderRequest
	 * @return
	 */
	private CallCenterWaybillInfoEntity convertCallCenterInfo(
			PashReminderRequest pashReminderRequest){
		// 转换主信息
		CallCenterWaybillInfoEntity entity = new CallCenterWaybillInfoEntity();
		if (null == pashReminderRequest) {
			LOGGER.info("请求对象为空");
			return null;
		}
		//催单凭证号
		entity.setPressWaybillNo(pashReminderRequest.getId());
		//运单号
		entity.setWaybillNo(pashReminderRequest.getCode());
		//催单信息
		entity.setPressMsg(pashReminderRequest.getPressMsg());
		//催单人
		entity.setPressUser(pashReminderRequest.getPressUser());
		//催单时间
		entity.setPressTime(xmlDate2Date(pashReminderRequest.getPressTime()));
		WaybillEntity wayentity = waybillManagerService.
	    		queryWaybillBasicByNo(pashReminderRequest.getCode());
		if(wayentity!=null && StringUtils.isNotBlank(wayentity.getCustomerPickupOrgCode())){
			//根据部门编码获取组织信息
		    OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(wayentity.getCustomerPickupOrgCode());
		    if(orgEntity !=null && StringUtils.isNotBlank(orgEntity.getUnifiedCode())){
		    	//受理部门
				entity.setDealDept(orgEntity.getUnifiedCode());
	    	}
			//受理部门名称
			entity.setDealDeptName(wayentity.getCustomerPickupOrgName());
		}
		//创建时间
		entity.setCreateDate(xmlDate2Date(pashReminderRequest.getPressTime()));
		//创建人
		entity.setCreateUser(CC);
		//修改时间
		entity.setModifyDate(new Date());
		//修改人
		entity.setModifyUser(CC);
		return entity;
	}
	/**
	 * 转化xml时间类型方法
	 * @param cal
	 * @return
	 */
	private static Date xmlDate2Date(XMLGregorianCalendar cal){  
        return cal.toGregorianCalendar().getTime();  
    }
	
	/**
	 * 设置业务锁service
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	/**
	 * 设置催运单信息service
	 * @param callCenterWaybillInfoService
	 */
	public void setCallCenterWaybillInfoService(
			ICallCenterWaybillInfoService callCenterWaybillInfoService) {
		this.callCenterWaybillInfoService = callCenterWaybillInfoService;
	}
	
	/**
	 * 设置运单管理接口service
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * 设置货签服务service
	 * @param labeledGoodService
	 */
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	
	/**
	 * 设置库存服务service
	 * @param stockService
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	/**
	 * 设置组织信息service
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 设置营业部service
	 * @param saleDepartmentService
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	/**
	 * 设置库区service
	 * @param goodsAreaService
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	
	
	
}
