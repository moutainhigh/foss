package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAllSalesDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.oms.inteface.domain.SalesDepartmentInfo;
import com.deppon.oms.inteface.domain.SyncAllSalesDepartmentRequest;
import com.deppon.esb.pojo.transformer.jaxb.SyncAllSalesDepartmentRequestTrans;

/**
 * 同步FOSS的营业部信息给周边系统
 * 
 * @author 273311
 * @date 2016-3-22 上午11:50:25
 */
public class SyncAllSalesDepartmentService implements
		ISyncAllSalesDepartmentService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncAllSalesDepartmentService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_DEPARTMENT";

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 同步FOSS的营业部信息给周边系统
	 * 
	 * @author 273311
	 * @date 2016-3-22 上午11:50:25
	 */
	@Override
	public void syncAllSalesDepartmentDataToAll(
			List<SaleDepartmentEntity> saleDepartmentList, String operateType) {
		try {
			if (CollectionUtils.isNotEmpty(saleDepartmentList)) {
				SyncAllSalesDepartmentRequest salesAllDepartRequest = new SyncAllSalesDepartmentRequest();
				List<SalesDepartmentInfo> salesDepartmentLst = new ArrayList<SalesDepartmentInfo>();
				StringBuilder versionNos = new StringBuilder();
				StringBuilder codes = new StringBuilder();
				for (SaleDepartmentEntity entity : saleDepartmentList) {
					SalesDepartmentInfo salesDepartmentInfo = this
							.transFossEntityToEsb(entity, operateType);
					salesDepartmentLst.add(salesDepartmentInfo);
					versionNos.append(SymbolConstants.EN_COMMA);
					versionNos.append(entity.getVersionNo());
					codes.append(SymbolConstants.EN_COMMA);
					codes.append(entity.getCode());
				}
				salesAllDepartRequest.getSalesDepartment().addAll(
						salesDepartmentLst);
				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
				accessHeader.setBusinessId(codes.toString());
				accessHeader.setBusinessDesc1("同步 营业部信息到周边系统");
				accessHeader.setBusinessDesc2(versionNos.toString());
				accessHeader.setVersion("0.1");
				log.info("开始调用 同步营业部到周边系统：\n"
						+ new SyncAllSalesDepartmentRequestTrans()
								.fromMessage(salesAllDepartRequest));
				ESBJMSAccessor.asynReqeust(accessHeader, salesAllDepartRequest);
				log.info("结束调用同步营业部到周边系统：\n"
						+ new SyncAllSalesDepartmentRequestTrans()
								.fromMessage(salesAllDepartRequest));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SynchronousExternalSystemException(" 同步营业部信息",
					" 同步营业部信息 发送数据到ESB错误");
		}
	}

	/**
	 * 同步实体转换
	 * 
	 * @author 273311
	 * @date 2016-3-22 上午11:50:25
	 */
	private SalesDepartmentInfo transFossEntityToEsb(
			SaleDepartmentEntity fossEntity, String operateType) {
		if (null == fossEntity) {
			return null;
		}
		SalesDepartmentInfo info = new SalesDepartmentInfo();
		if (StringUtils.isBlank(fossEntity.getCode())) {
			// 打印日志信息
			log.info("SyncSalesDepartmentService 实体类组织编码为空");
			// 返回空
			return null;
		}
		// 查询组织标杆编码
		OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(fossEntity.getCode());
		// 非空验证
		if (null == entity || StringUtils.isBlank(entity.getUnifiedCode())) {
			// 打印日志信息
			log.info("SyncAllSalesDepartmentService 根据组织编码查询组织标杆编码失败");
			// 返回空
			return null;
		}
		// id
		info.setId(fossEntity.getId());
		// 部门标杆编码编码
		info.setUnifiedCode(entity.getUnifiedCode());
		// 部门编码
		info.setCode(fossEntity.getCode());
		// 部门名称
		info.setName(fossEntity.getName());
		// 拼音
		info.setPinyin(fossEntity.getPinyin());
		// 可出发
		info.setLeave(fossEntity.getLeave());
		// 可到达
		info.setArrive(fossEntity.getArrive());
		// 是否驻地部门
		info.setStation(fossEntity.getStation());
		// 广告语
		info.setSlogans(fossEntity.getSlogans());
		// 开业日期
		info.setOpeningDate(fossEntity.getOpeningDate());
		// 最大临欠额度
		info.setMaxTempArrears(fossEntity.getMaxTempArrears());
		// 已用临欠额度
		info.setUsedTempArrears(fossEntity.getUsedTempArrears());
		// 是否是精准大票
		info.setIsBigGoods(fossEntity.getIsBigGoods());
		// 驻地营业部所属外场
		info.setTransferCenter(fossEntity.getTransferCenter());
		// 驻地营业部所属外场
		info.setTransferCenterName(fossEntity.getTransferCenterName());
		// 取消到达日期
		info.setCancelArrivalDate(fossEntity.getCancelArrivalDate());
		// 转货部门
		info.setTransferGoodDept(fossEntity.getTransferGoodDept());
		// 转货部门
		info.setTransferGoodDeptName(fossEntity.getTransferGoodDeptName());
		// 可自提
		info.setPickupSelf(fossEntity.getPickupSelf());
		// 可派送
		info.setDelivery(fossEntity.getDelivery());
		// 可空运到达
		info.setAirArrive(fossEntity.getAirArrive());
		// 可汽运到达
		info.setTruckArrive(fossEntity.getTruckArrive());
		// 单件重量上限非空判断
		if (null != fossEntity.getSinglePieceLimitkg()) {
			info.setSinglePieceLimitKG((double) fossEntity
					.getSinglePieceLimitkg());
		}
		// 单票重量上限非空判断
		if (null != fossEntity.getSingleBillLimitkg()) {
			// 单票重量上限
			info.setSingleBillLimitKG((double) fossEntity
					.getSingleBillLimitkg());
		}
		// 单件体积上限非空判断
		if (null != fossEntity.getSingleBillLimitkg()) {
			// 单件体积上限
			info.setSinglePieceLimitVOL((double) fossEntity
					.getSinglePieceLimitvol());
		}
		// 单票体积上限非空判断
		if (null != fossEntity.getSingleBillLimitkg()) {
			// 单票体积上限
			info.setSingleBillLimitVOL((double) fossEntity
					.getSingleBillLimitvol());
		}
		// 自提区域描述
		info.setPickupAreaDesc(fossEntity.getPickupAreaDesc());
		// 非空验证
		if (null != fossEntity.getDeliveryAreaDesc()) {
			// 派送区域描述
			info.setDeliveryAreaDesc(fossEntity.getDeliveryAreaDesc());
		}
		// 非空验证
		if (null != fossEntity.getDeliveryCoordinate()) {
			// 派送区坐标编号
			info.setDeliveryCoordinate(fossEntity.getDeliveryCoordinate());
		}
		// 是否启用
		info.setActive(fossEntity.getActive());
		// 是否在集中接送货范围内
		info.setInCentralizedShuttle(fossEntity.getInCentralizedShuttle());
		// 是否可开装卸费
		info.setCanPayServiceFee(fossEntity.getCanPayServiceFee());
		// 是否可开快递一票多件
		info.setCanExpressOneMany(fossEntity.getCanExpressOneMany());
		// 是否可返回签单
		info.setCanReturnSignBill(fossEntity.getCanReturnSignBill());
		// 是否可货到付款
		info.setCanCashOnDelivery(fossEntity.getCanCashOnDelivery());
		// 是否可代收货款
		info.setCanAgentCollected(fossEntity.getCanAgentCollected());
		// 提货网点编码，为4位数字，当为到达部门时必填
		info.setStationNumber(fossEntity.getStationNumber());
		// 自提区域描述是否扩展
		info.setPickAreaIsExpand(fossEntity.getPickAreaIsExpand());
		// 派送区域描述是否扩展
		info.setDeliveryAreaIsExpand(fossEntity.getDeliveryAreaIsExpand());
		// 可快递返回签单
		info.setCanExpressReturnSignBill(fossEntity
				.getCanExpressReturnSignBill());
		// 可快递接货
		info.setCanExpressPickupToDoor(fossEntity.getCanExpressPickupToDoor());
		// 补录不可修改快递目的站
		info.setCanUpdateDestination(fossEntity.getCanUpdateDestination());
		// 可快递派送
		info.setCanExpressDelivery(fossEntity.getCanExpressDelivery());
		// 可快递自提
		info.setCanExpressPickupSelf(fossEntity.getCanExpressPickupSelf());
		// 快递派送区域描述
		info.setExpressDeliveryAreaDesc(fossEntity.getExpressDeliveryAreaDesc());
		// 快递自提区域描述
		info.setExpressPickupAreaDesc(fossEntity.getExpressPickupAreaDesc());
		// 快递自提区域是否扩展
		info.setExpressPickupAreaIsExp(fossEntity.getExpressPickupAreaIsExp());
		// 快递派送区域是否扩展
		info.setExpressDeliveryAreaIsExp(fossEntity
				.getExpressDeliveryAreaIsExp());
		// 快递派送地图坐标编码
		info.setExpressDeliveryCoordinate(fossEntity
				.getExpressDeliveryCoordinate());
		// 是否卫星点部
		info.setSatelliteDept(fossEntity.getSatelliteDept());
		// 快递派送电子地图审核状态
		info.setVerifyState(fossEntity.getVerifyState());
		// 快递派送电子地图审核人工号
		info.setVerifyManCode(fossEntity.getVerifyManCode());
		// 快递派送电子地图审核时间
		info.setVerifyTime(fossEntity.getVerifyTime());
		// 快递派送电子地图申请时间
		info.setApplyTime(fossEntity.getApplyTime());
		// 快递派送电子地图申请人工号
		info.setApplyManCode(fossEntity.getApplyManCode());
		// 营业部服务面积（平方千米）
		info.setDepartServiceArea(fossEntity.getDepartServiceArea());
		// 快递员人数
		info.setExpressManNum(fossEntity.getExpressManNum());
		// 是否可上门发货
		info.setCanExpressPickupToDoor(fossEntity.getCanExpressPickupToDoor());
		// 是否可到快递一票多件
		info.setCanArriveExpressOneMany(fossEntity.getCanArriveExpressOneMany());
		// 是否可货到付款(外发多件)
		info.setCanCashOnDeliveryMany(fossEntity.getCanCashOnDeliveryMany());
		// 是否可代收货款(外发多件)
		info.setCanAgentCollectedMany(fossEntity.getCanAgentCollectedMany());
		// 代收货款上限
		info.setAgentCollectedUpperLimit(fossEntity
				.getAgentCollectedUpperLimit());
		// 是否可家装送装
		info.setCanHomeImproSend(fossEntity.getCanHomeImproSend());
		// 是否加盟网点
		info.setIsLeagueSaleDept(fossEntity.getIsLeagueSaleDept());
		// 创建时间
		info.setCreatTime(fossEntity.getCreateDate());
		// 修改时间
		info.setModifyTime(fossEntity.getModifyDate());
		// 创建人
		info.setCreateUserCode(fossEntity.getCreateUser());
		// 修改人
		info.setModifyUserCode(fossEntity.getModifyUser());
		// 版本号
		info.setVersionNo((double) fossEntity.getVersionNo());
		// 操作
		info.setOperateType(operateType);
		/**
		 * 是否二级网点
		 */
		info.setIsTwoLevelNetwork(fossEntity.getIsTwoLevelNetwork());
		/**
		 * 网点模式
		 */
		info.setNetworkModel(fossEntity.getNetworkModel());
		return info;

	}
}
