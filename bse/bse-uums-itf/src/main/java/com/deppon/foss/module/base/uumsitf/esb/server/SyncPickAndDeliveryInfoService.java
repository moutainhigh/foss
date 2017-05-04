package com.deppon.foss.module.base.uumsitf.esb.server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.foss.DeptInfo;
import com.deppon.esb.inteface.domain.foss.SyncDeliveryRangeRequest;
import com.deppon.esb.inteface.domain.foss.SyncDeliveryRangeResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.inteface.fosssyncwdghservice.CommonException;
import com.deppon.foss.inteface.fosssyncwdghservice.FossSyncWdghService;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PickAreaAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SalesDepartmentException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;

/**
 * 
 * 同步WDGH派送自提信息服务 WEB SERVICE服务端
 * 
 */

public class SyncPickAndDeliveryInfoService implements FossSyncWdghService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncPickAndDeliveryInfoService.class);
	// 用于set给创建人，修改人的值常量
	private static final String WDGH = "WDGH";

	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 营业部service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 组织DAO声明
	 */
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	
	/**
	 * 设置组织信息DAO
	 * @param orgAdministrativeInfoDao
	 */
	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}


	/**
	 * 设置 业务互斥锁服务.
	 * 
	 * @param businessLockService
	 *            the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 设置 营业部服务.
	 * 
	 * @param saleDepartmentService
	 *            the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * <p>
	 * 同步主信息的方法
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-10上午9:49:00
	 * @param syncRequest
	 * @param esbHeader
	 * @return
	 * @throws CommonException
	 */
	@Override
	public SyncDeliveryRangeResponse fossSyncRangeWorkFolw(
			SyncDeliveryRangeRequest syncRequest, Holder<ESBHeader> esbHeader)
			throws CommonException {
		// 消息头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		// 响应对象
		SyncDeliveryRangeResponse response = new SyncDeliveryRangeResponse();
		if (null != syncRequest
				&& (StringUtils.isNotBlank(syncRequest.getDeptCode()))) {
			// 业务锁
			MutexElement mutex = new MutexElement(String.valueOf(syncRequest
					.getDeptCode()), "WDGH_SALES_CODE",
					MutexElementType.WDGH_SALES_CODE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				response.setDeptCode(response.getDeptCode());
				response.setReason("失败加锁");
				response.setResultCode("0");
				return response;
			}
			// 派送自提同步数据处理
			response = this.syncInfo(syncRequest);

			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		} else {
			LOGGER.info("请求对象不存在或请求的code 为空！.......");
			response.setReason("请求对象不存在或请求的code 为空！.......");
			response.setResultCode("0");
		}
		return response;
	}

	/**
	 * 
	 * <p>
	 * 派送自提同步数据的处理
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-10上午9:35:40
	 * @param syncRequest
	 * @return
	 */
	@Transactional
	private SyncDeliveryRangeResponse syncInfo(
			SyncDeliveryRangeRequest syncRequest) throws CommonException {
		LOGGER.info("同步WDGH的派送自提数据开始...............");
		// 响应对象
		SyncDeliveryRangeResponse syncResponse = new SyncDeliveryRangeResponse();
		if (null == syncRequest) {
			return null;
		}
		if (StringUtils.isBlank(syncRequest.getDeptCode())) {
			syncResponse.setReason("请求对象不存在");
			syncResponse.setResultCode("0");
			return syncResponse;
		}
		String deptCode = orgAdministrativeInfoDao
				.queryOrgCodeByOrgUnifiedCode(syncRequest.getDeptCode());
		// 查询数据库中对象是否存在
		SaleDepartmentEntity salesEntity = saleDepartmentService
				.querySaleDepartmentByCodeNoCache(deptCode);
		// 若对象存在
		if (null == salesEntity) {
			syncResponse.setDeptCode(syncRequest.getDeptCode());
			syncResponse.setReason("库中不存在 code=" + syncRequest.getDeptCode()
					+ "的数据");
			syncResponse.setResultCode("0");
			return syncResponse;
		}
			/**
			 * 先取出中同步的主要部门信息
			 */
			PickAreaAndDeliveryAreaEntity pickAndDeliveryEntity = convertFossPickDeliveryEntity(syncRequest);

			try {
				// 更新主要同步信息
				int count = saleDepartmentService
						.updateSaleDepartmentBySync(pickAndDeliveryEntity);
				if (count == 1) {
					LOGGER.info("*****主信息更新成功******");
				} else {
					LOGGER.info("*****主信息更新失败******");
					syncResponse.setDeptCode(syncRequest.getDeptCode());
					syncResponse.setReason("主信息 code="
							+ syncRequest.getDeptCode() + "数据更新失败");
					syncResponse.setResultCode("0");
					return syncResponse;
				}
			} catch (SalesDepartmentException e) {
				LOGGER.info(e.getMessage(), e);
				throw new CommonException(e.getMessage(), e);
			}
			/**
			 * 更新影响该部门的其他部门的派送自提区域
			 * 
			 */
			// 影响部门的其他部门信息集合
			List<PickAreaAndDeliveryAreaEntity> entities = convertFossOtherDeptEntity(syncRequest
					.getDeptInfo());

			// 若存在影响该部门的其他部门的派送自提区域
			if (CollectionUtils.isNotEmpty(entities)) {
				int otherInfoCount = 0;
				try {
					for (PickAreaAndDeliveryAreaEntity pickAreaAndDeliveryAreaEntity : entities) {
						int count = saleDepartmentService
								.updateSaleDepartmentBySync(pickAreaAndDeliveryAreaEntity);
						if (count > 0) {
							otherInfoCount++;
						}
					}
					if (otherInfoCount == entities.size()) {
						LOGGER.info("*****影响Code=" + syncRequest.getDeptCode()
								+ "信息的其他部门派送自提信息更新成功******");
					} else {
						LOGGER.info("*****影响Code=" + syncRequest.getDeptCode()
								+ "信息的其他部门派送自提信息更新失败******");
						syncResponse.setDeptCode(syncRequest.getDeptCode());
						syncResponse.setReason("影响Code="
								+ syncRequest.getDeptCode()
								+ "信息的其他部门派送自提信息更新失败");
						syncResponse.setResultCode("0");
						return syncResponse;
					}
				} catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}
		
		LOGGER.info("同步WDGH的派送自提数据结束...............");
		syncResponse.setDeptCode(syncRequest.getDeptCode());
		syncResponse.setReason("更新 code=" + syncRequest.getDeptCode()
				+ "营业部派送自提数据成功");
		syncResponse.setResultCode("1");
		return syncResponse;
	}
		 

	/**
	 * 
	 * <p>
	 * 把WDGH同步过来的自提派送部门信息转化为FOSS信息实体
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-10上午10:10:44
	 * @param syncRequest
	 * @return
	 */
	private PickAreaAndDeliveryAreaEntity convertFossPickDeliveryEntity(
			SyncDeliveryRangeRequest syncRequest) {
		// 转换主信息
		PickAreaAndDeliveryAreaEntity entity = new PickAreaAndDeliveryAreaEntity();
		if (null == syncRequest) {
			LOGGER.info("请求对象为空");
			return null;
		}
		// 部门编码
		if (StringUtils.isNotBlank(syncRequest.getDeptCode())) {
			// 先根据部门标杆编码从组织表里查询出部门编码
			String deptCode = orgAdministrativeInfoDao.queryOrgCodeByOrgUnifiedCode(syncRequest.getDeptCode());
			if(StringUtils.isNotBlank(deptCode)){
				entity.setCode(deptCode);
			}
			
		}
		// 是否派送
		entity.setDelivery(syncRequest.getIsDelivery());
		// 是否自提
		entity.setPickupSelf(syncRequest.getIsSelfPickUp());
		// 派送范围
		entity.setDeliveryAreaDesc(syncRequest.getDeliveryRange());
		// 自提范围
		entity.setPickupAreaDesc(syncRequest.getSelfPickUpRange());
		// 提货网点编码
		entity.setStationNumber(syncRequest.getLoadingstationCode());
		
		// foss库中 四个上限 都要乘以100
		if(null != syncRequest.getBillWeightLimit()){
			double billWeightLimit = (syncRequest.getBillWeightLimit()
					.doubleValue()) * NumberConstants.NUMBER_100;
			// 单票重量上限
			entity.setSingleBillLimitkg((int) billWeightLimit);
		}
		
		if(null != syncRequest.getBillVolumeLimit()){
			double billVolumeLimit = (syncRequest.getBillVolumeLimit()
					.doubleValue()) * NumberConstants.NUMBER_100;
			// 单票体积上限
			entity.setSingleBillLimitvol((int) billVolumeLimit);
		}
		
		if(null != syncRequest.getItemWeightLimit()){
			double itemWeightLimit = (syncRequest.getItemWeightLimit()
					.doubleValue()) * NumberConstants.NUMBER_100;
			// 单件重量上限
			entity.setSinglePieceLimitkg((int) itemWeightLimit);
		}
		
		if(null != syncRequest.getItemVolumeLimit()){
			double itemVolumeLimit = (syncRequest.getItemVolumeLimit()
					.doubleValue()) * NumberConstants.NUMBER_100;
			// 单件体积上限
			entity.setSinglePieceLimitvol((int) itemVolumeLimit);
		}

		// 创建人
		entity.setCreateUser(WDGH);
		// 修改人
		entity.setModifyUser(WDGH);

		return entity;
	}
	/**
	 * 
	 * <p>
	 * 转换WDGH传过来影响派送自提部门的其他部门信息为FOSS对象集合
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-11上午8:47:21
	 * @param list
	 * @return
	 */
	private List<PickAreaAndDeliveryAreaEntity> convertFossOtherDeptEntity(
			List<DeptInfo> deptInfos) {

		List<PickAreaAndDeliveryAreaEntity> pickAndDeliveryEntities = new ArrayList<PickAreaAndDeliveryAreaEntity>();
		// 判断集合是否为空
		if (CollectionUtils.isNotEmpty(deptInfos)) {
			for (DeptInfo deptInfo : deptInfos) {
				PickAreaAndDeliveryAreaEntity entity = new PickAreaAndDeliveryAreaEntity();
				// 部门编码验证
				if (StringUtils.isBlank(deptInfo.getOtherDeptCode())) {
					continue;
				}
				
				// 先根据部门标杆编码从组织表里查询出部门编码
				String deptCode = orgAdministrativeInfoDao.queryOrgCodeByOrgUnifiedCode(deptInfo.getOtherDeptCode());
				if(StringUtils.isNotBlank(deptCode)){
					entity.setCode(deptCode);
				}
				// 是否其他部门派送
				entity.setDelivery(deptInfo.getOtherIsSelfDelivery());
				// 是否其他部门自提
				entity.setPickupSelf(deptInfo.getOtherIsSelfPickUp());
				// 其他部门派送区域
				entity.setDeliveryAreaDesc(deptInfo.getOtherDeliveryRange());
				// 其他部门自提范围
				entity.setPickupAreaDesc(deptInfo.getOtherDeptRange());
				// 创建人
				entity.setCreateUser(WDGH);
				// 修改人
				entity.setModifyUser(WDGH);
				
				pickAndDeliveryEntities.add(entity);
			}
		}
		return pickAndDeliveryEntities;
	}

}
