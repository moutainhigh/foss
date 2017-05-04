package com.deppon.foss.module.transfer.dubbo.api.service.impl.expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.dpapp.api.define.AppWayBillDetaillVo;
import com.deppon.foss.dubbo.dpapp.api.define.QueryAppWaybillInfoEntity;
import com.deppon.foss.dubbo.dpapp.api.define.QueryAppWaybillInfoResponseEntity;
import com.deppon.foss.dubbo.dpapp.api.service.IQueryWaybillInfo4DPAppService;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.dubbo.api.dao.IWaybillDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.BillPayableEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.CrmWaybillServiceDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillRelateDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillSignResultEntity;

/**
 * <pre>
 * 德邦APP查询货物信息的dubbo接口
 * 
 * 过期原因：业务范围不属于中转。
 * 该接口将在中转内被抛弃，由接送货实现
 * </pre>
 * @author 335284
 *
 */
@Deprecated
public class QueryWaybillInfo4DPAppService implements IQueryWaybillInfo4DPAppService {
	private final static Logger logger = LogManager.getLogger(QueryWaybillInfo4DPAppService.class);
	private static final String Y = "Y";
	private static final String STATUS = "status";

	private IWaybillDao4dubbo waybillDao;

	@Override
	public QueryAppWaybillInfoResponseEntity queryAppWaybillRelateDetailInfo(
			QueryAppWaybillInfoEntity appWaybillInfosRequest) {
		logger.info("进入请求 queryAppWaybillRelateDetailInfo ");
		QueryAppWaybillInfoResponseEntity appWaybillInfosResponse = new QueryAppWaybillInfoResponseEntity();
		try {
			logger.info("请求参数：" + appWaybillInfosRequest.toString());
			if (StringUtils.isBlank(appWaybillInfosRequest.getMobilePhone())) {
				throw new BusinessException("电话号码不能为空");
			}
			if (appWaybillInfosRequest.getStartDate() == null || appWaybillInfosRequest.getEndDate() == null) {
				throw new BusinessException("请输入查询时间范围");
			}
			// 把参数封装成map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobilePhone", appWaybillInfosRequest.getMobilePhone());
			map.put("type", appWaybillInfosRequest.getType());
			map.put("status", appWaybillInfosRequest.getStatus());
			map.put("startDate", appWaybillInfosRequest.getStartDate());
			map.put("endDate", appWaybillInfosRequest.getEndDate());
			map.put("pageNum", appWaybillInfosRequest.getPageNum());
			map.put("pageSize", appWaybillInfosRequest.getPageSize());
			map.put("keyWords", appWaybillInfosRequest.getKeyWords());

			Map<String, Object> resultMap = queryAppWaybillInfos_exp(map);
			int count = (Integer) resultMap.get("count");
			logger.info("返回的记录数：" + count);
			@SuppressWarnings("unchecked")
			List<AppWayBillDetaillVo> lists = (List<AppWayBillDetaillVo>) resultMap.get("list");
			appWaybillInfosResponse.setCount(count);
			appWaybillInfosResponse.setWaybillList(lists);
			return appWaybillInfosResponse;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			appWaybillInfosResponse.setSuccess("N");
			appWaybillInfosResponse.setErrorMsg(e.getMessage());
			return appWaybillInfosResponse;
		}

	}

	private Map<String, Object> queryAppWaybillInfos_exp(Map<String, Object> params) {
		logger.info("进入 queryAppWaybillInfos_exp");
		long begin = System.currentTimeMillis();

		// 发货/收货信息
		Map<String, Object> map = queryAppWaybillInfos(params);
		if ((Integer) map.get("count") > 0) {
			List<String> waybillNos = new ArrayList<String>();
			@SuppressWarnings("unchecked")
			List<CrmWaybillServiceDto> list = (List<CrmWaybillServiceDto>) map.get("list");
			for (CrmWaybillServiceDto crmWaybillServiceDto : list) {
				waybillNos.add(crmWaybillServiceDto.getWaybillNo());
			} // for end
			logger.info("----------->根据运单集合查询子母件信息");
			// 根据运单集合查询子母件信息
			List<WaybillRelateDetailEntity> lists = waybillDao.queryWaybillRelateDetailByWaybillNos(waybillNos, 0, 0,
					false);
			Map<String, String> hashMap = new HashMap<String, String>();
			for (WaybillRelateDetailEntity waybillRelateDetailEntity : lists) {
				logger.info("母件运单号：" + waybillRelateDetailEntity.getParentWaybillNo() + "; 子运单号："
						+ waybillRelateDetailEntity.getWaybillNo() + ";" + waybillRelateDetailEntity.getIsPicPackage());
				hashMap.put(waybillRelateDetailEntity.getWaybillNo(), waybillRelateDetailEntity.getIsPicPackage());// 子件母件判定:Y:母件;N:子件
			}
			String keywords = (String) params.get("keyWords");
			// map.put("waybillCZMMap", hashMap);
			// BeanUtils.copyProperties(dest, orig);
			// 把CrmWaybillServiceDto bean 转换为AppWayBillDetaillVo bean
			List<AppWayBillDetaillVo> wayBillDetaillVos = new ArrayList<AppWayBillDetaillVo>();
			List<AppWayBillDetaillVo> wayBillDetaillVoList = convertToAppWayBillDetaillVo(list);
			logger.info("----------->给运单对象AppWayBillDetaillVo增加子母件标示");
			// 增加子母件标示
			for (AppWayBillDetaillVo wayBillDetaillVo : wayBillDetaillVoList) {
				if (hashMap.containsKey(wayBillDetaillVo.getWaybillNum())) {
					// 子母件集合中没有该运单信息 则再次判断是子单或母单
					if (Y.equals(hashMap.get(wayBillDetaillVo.getWaybillNum()))) {
						wayBillDetaillVo.setWaybillType("parent");
						wayBillDetaillVos.add(wayBillDetaillVo);
					} else {
						if (StringUtils.isNotEmpty(keywords)) {
							wayBillDetaillVo.setWaybillType("child");
							wayBillDetaillVos.add(wayBillDetaillVo);
						} else {
							logger.info("keywords 关键字 为空，移除子单信息");
							// wayBillDetaillVos.remove(wayBillDetaillVo);
						}
					}
				} else {// 子母件集合中没有该运单信息 则是正常件
					wayBillDetaillVo.setWaybillType("normal");
					wayBillDetaillVos.add(wayBillDetaillVo);
				}
			} // for end

			int start = (Integer) params.get("pageNum");
			int limit = (Integer) params.get("pageSize");
			int pageCount = 0;
			pageCount = (wayBillDetaillVos.size() - 1) / limit + 1;
			// 跨越总页数置为最后一页
			if (start > pageCount) {
				start = pageCount;
			}
			// 策画须要显示的成果数据
			List<AppWayBillDetaillVo> pageList = new ArrayList<AppWayBillDetaillVo>();
			// start从0开始计数
			for (int i = start * limit; i < wayBillDetaillVos.size() && i < (start + 1) * limit; i++) {
				pageList.add(wayBillDetaillVos.get(i));
			}
			map.put("count", wayBillDetaillVos.size());
			map.put("list", pageList);// 支持内存分页

		}
		logger.info("----------->queryAppWaybillInfos_exp操作结束 耗时：" + (System.currentTimeMillis() - begin));
		return map;

	}

	private List<AppWayBillDetaillVo> convertToAppWayBillDetaillVo(List<CrmWaybillServiceDto> list) {
		logger.info("开始自动对象转换：CrmWaybillServiceDto--->AppWayBillDetaillVo");
		List<AppWayBillDetaillVo> targetList = new ArrayList<AppWayBillDetaillVo>();
		try {
			for (CrmWaybillServiceDto dto : list) {
				AppWayBillDetaillVo info = new AppWayBillDetaillVo();
				info.setConsigneeAddress(dto.getConsigneeAddress());
				info.setConsigneeMobile(dto.getConsigneeMobile());
				info.setConsigneeName(dto.getConsignee());
				info.setConsigneetel(dto.getConsigneePhone());
				info.setCubage(dto.getCubage());
				info.setDeliveryCharge(dto.getDeliveryCharge());
				info.setDeliveryMode(dto.getDeliveryType());
				info.setDeparture(dto.getDeparture());
				info.setDepartureAddress(dto.getDepartureDeptAddr());
				info.setDepartureName(dto.getDepartureDeptName());
				info.setDeparturetel(dto.getDepartureDeptPhone());
				info.setDestination(dto.getDestination());
				info.setGoodsName(dto.getGoodName());
				info.setInsurance(dto.getInsurance());
				info.setInsuranceFee(dto.getInsuranceValue());
				info.setOrderNum(dto.getOrderNumber());
				info.setOtherCharge(dto.getOtherFee());
				info.setPacking(dto.getPacking());
				info.setPackingCharge(dto.getPackingFee());
				info.setPayWay(dto.getPayment());
				info.setPieces(dto.getPieces());
				info.setReceiveCharge(dto.getPickCharge());
				info.setRefund(dto.getRefund());
				info.setRefundFee(dto.getRefundFee());
				info.setRefundType(dto.getRefundType());
				info.setReturnBillType(dto.getSignBackType());
				info.setSendDate(dto.getSendTime());
				info.setShipperAddress(dto.getDepartureDeptAddr());
				info.setShipperMobile(dto.getSenderMobile());
				info.setShipperName(dto.getSender());
				info.setShippertel(dto.getSenderPhone());
				info.setSignedDate(dto.getSignedDate());
				info.setStationaddress(dto.getLadingStationAddr());
				info.setStationtel(dto.getLadingStationPhone());
				info.setStationName(dto.getLadingStationName());
				info.setWayBillState(dto.getWaybillStatus());
				info.setWaybillNum(dto.getWaybillNo());
				info.setPayWay(dto.getPaidMethod());
				info.setTotalCharge(dto.getTotalCharge());
				info.setTransProperties(dto.getTransProperties());
				info.setTranCharge(dto.getTransportFee());
				if (dto.getWeight() != null) {
					info.setWeight(dto.getWeight());
				}
				info.setDepartureFax(dto.getDepartureDeptFax());
				info.setDestinationFax(dto.getLadingStationFax());
				info.setDepartureCityName(dto.getDepartureCityName());
				info.setDestinationCityName(dto.getDestinationCityName());
				info.setPredictArriveTime(dto.getPredictArriveTime());
				targetList.add(info);
			}
		} catch (Exception e) {
			logger.error("CrmWaybillServiceDto对象自动转换为AppWayBillDetaillVo对象失败！");
			throw new BusinessException("对象自动转换失败！", e);
		}
		logger.info("自动对象转换结束：CrmWaybillServiceDto--->AppWayBillDetaillVo");
		return targetList;
	}

	private Map<String, Object> queryAppWaybillInfos(Map<String, Object> args) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CrmWaybillServiceDto> list = new ArrayList<CrmWaybillServiceDto>();
		int count = 0;
		// 官网查询，需要对传入的物流状态进行分页，如果用运单状态来查询，需要进行内存分页
		if (args != null) {
			args.put("active", Y);
			if (args.get("type") != null) {
				String type = (String) args.get("type");
				// 判断客户类型，获取客户信息
				if ("DELIVER".equals(type)) {
					args.put("deliverMobile", args.get("mobilePhone"));
				} else if ("RECEIVE".equals(type)) {
					args.put("receiveMobile", args.get("mobilePhone"));
				} else {
					args.put("deliverMobile", args.get("mobilePhone"));
					args.put("receiveMobile", args.get("mobilePhone"));
				}
			} else {
				args.put("deliverMobile", args.get("mobilePhone"));
				args.put("receiveMobile", args.get("mobilePhone"));
			}
			// 使用内存分页
			if (args.get(STATUS) != null && !"".equals(args.get(STATUS))) {
				int start = (Integer) args.get("pageNum");
				int limit = (Integer) args.get("pageSize");
				// 不分页，采用内存分页
				list = getWaybillDao().queryAppWaybillInfoByCondition(args, false);
				// 设置运单状态
				setWaybillStatus(list);
				if (args.get(STATUS) != null && !"".equals(args.get(STATUS))) {
					if (!StringUtils.isEmpty(args.get(STATUS).toString())) {
						queryWaybillByStatus(list, result, args.get(STATUS).toString(), start, limit);
					}
				}
			} else {
				// 此处也采用内存分页
				list = getWaybillDao().queryAppWaybillInfoByCondition(args, false);
				count = getWaybillDao().countQueryAppWaybillInfoByCondition(args);
				setWaybillStatus(list);// 设置运单状态
				result.put("count", count);
				result.put("list", list);
			}
		}
		return result;
	}

	private void queryWaybillByStatus(List<CrmWaybillServiceDto> list, Map<String, Object> result, String string,
			int start, int limit) {
		List<CrmWaybillServiceDto> waybillStatusList = new ArrayList<CrmWaybillServiceDto>();

		if (list != null && result != null) {
			for (CrmWaybillServiceDto dto : list) {
				String waybillStatus = dto.getWaybillStatus();
				// 返回的运单状态不能为空。且必须与传入的
				if (!StringUtils.isEmpty(dto.getWaybillStatus())) {
					/**
					 * 在途运输时，包含状态： STATION_OUT";// 营业部已出发 TFR_IN";// 已到达中转场
					 * TFR_STOCK";// 中转库存 TFR_OUT";// 中转运输 STATION_IN";// 已到达营业部
					 * STATION_STOCK";// 营业部库存 DELIVERING";// 送货中
					 * 
					 */
					if ("IN_TRANSIT".equals(STATUS)) {

						if ("STATION_OUT".equals(waybillStatus) || "TFR_IN".equals(waybillStatus)
								|| "TFR_STOCK".equals(waybillStatus) || "TFR_OUT".equals(waybillStatus)
								|| "STATION_IN".equals(waybillStatus) || "STATION_STOCK".equals(waybillStatus)
								|| "EFFECTIVE".equals(waybillStatus) || "DELIVERING".equals(waybillStatus)) {
							waybillStatusList.add(dto);

						}
						// 正常签收
					} else if ("NORMAL_SIGN".equals(STATUS)) {
						if ("NORMAL_SIGN".equals(waybillStatus)) {
							waybillStatusList.add(dto);
						}
						// 异常签收
					} else if ("UNNORMAL_SIGN".equals(STATUS)) {
						if ("UNNORMAL_SIGN".equals(waybillStatus)) {
							waybillStatusList.add(dto);
						}
					}
				}
			}
			int pageCount = 0;
			if (waybillStatusList.size() % limit == 0) {
				pageCount = waybillStatusList.size() / limit;
			} else {
				pageCount = (waybillStatusList.size() / limit) + 1;
			}
			// 跨越总页数置为最后一页
			if (start > pageCount) {
				start = pageCount;
			}
			// 策画须要显示的成果数据
			List<CrmWaybillServiceDto> pageList = new ArrayList<CrmWaybillServiceDto>();
			// start从0开始计数
			for (int i = start * limit; i < (start + 1) * limit && i < waybillStatusList.size(); i++) {
				pageList.add(waybillStatusList.get(i));
			}
			result.put("count", waybillStatusList.size());
			result.put("list", pageList);// 支持内存分页
		}

	}

	private void setWaybillStatus(List<CrmWaybillServiceDto> list) {
		for (CrmWaybillServiceDto dto : list) {
			// 查询签收结果信息
			WaybillSignResultEntity signEntity = new WaybillSignResultEntity();
			signEntity.setWaybillNo(dto.getWaybillNo());
			signEntity.setActive(Y);
			// 根据运单号查询运单签结果里的运单信息
			WaybillSignResultEntity newEntity = waybillDao.queryWaybillSignResult(signEntity);
			// (需求DN201603140026只针对零担)当非快递，签收结果不等于null，并且签收情况为弃货时，判断是否申请过理赔
			if (!waybillDao.onlineDetermineIsExpressByProductCode(dto.getTransProperties(), null) && newEntity != null
					&& "UNNORMAL_ABANDONGOODS".equals(newEntity.getSignSituation())) {
				// 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
				List<BillPayableEntity> billPayables = waybillDao
						.queryByWaybillNosAndByBillTypes(Arrays.asList(dto.getWaybillNo()), Arrays.asList("C"));
				// 异常弃货，且有上报理赔，才发送签收信息
				if (CollectionUtils.isNotEmpty(billPayables)) {
					dto.setWaybillStatus(getWaybillDao().queryWaybillStatus(dto.getWaybillNo()));
				} else {
					// 异常弃货，未上报理赔时，把签收时间设为null
					dto.setSignedDate(null);
				}
			} else {
				// 当签收结果等于null，或者签收情况不为弃货时，直接设置运单状态
				dto.setWaybillStatus(getWaybillDao().queryWaybillStatus(dto.getWaybillNo()));
			}
		}
	}

	public IWaybillDao4dubbo getWaybillDao() {
		return waybillDao;
	}

	@Autowired
	public void setWaybillDao(IWaybillDao4dubbo waybillDao) {
		this.waybillDao = waybillDao;
	}

}
