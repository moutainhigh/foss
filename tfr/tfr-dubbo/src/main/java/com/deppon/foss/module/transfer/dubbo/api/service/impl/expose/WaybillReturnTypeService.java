package com.deppon.foss.module.transfer.dubbo.api.service.impl.expose;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.dpapp.api.define.WaybillCheckStatus;
import com.deppon.foss.dubbo.dpapp.api.service.IWaybillReturnTypeService;
import com.deppon.foss.module.transfer.dubbo.api.dao.IWaybillDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillQueryService4dubbo;

/**
 * <pre>
 * 德邦APP判别查询原单号关联的单号类型
 * 
 * 过期原因：业务范围不属于中转。
 * 该接口将在中转内被抛弃，由接送货实现
 * </pre>
 * @author 335284
 *
 */
@Deprecated
public class WaybillReturnTypeService implements IWaybillReturnTypeService {
	private final static Logger logger = LogManager.getLogger(WaybillReturnTypeService.class);

	private IWaybillQueryService4dubbo waybillQueryService4dubbo;
	private IWaybillDao4dubbo waybillDao;
	
	private static final String reGoodsNo = "返货单号";
	private static final String returnNo = "签收单返回单号";

	@Override
	public WaybillCheckStatus getWaybillReturnType(String waybillNo) {
		logger.info("开始进入getWaybillReturnType");
		// 返回信息集合
		WaybillCheckStatus commWaybillCheckStatus = new WaybillCheckStatus();
		// 查询快递运单表实体类
		WaybillExpressEntity waybill = new WaybillExpressEntity();
		List<String> waybillList = new ArrayList<String>();
		// 判断是否是原单号查询ni
		String hasReturnType = null;
		// 如果单号不为空就进行查询
		if (!"".equals(waybillNo) && StringUtils.isNotEmpty(waybillNo)) {
			// 定义单号的类型
			String status = null;
			waybillList.add(waybillNo);
			List<WaybillInfoDto> waybillInfoDtoList = getWaybillQueryService4dubbo().queryWaybillInfo(waybillList);
			if (CollectionUtils.isNotEmpty(waybillInfoDtoList)) {
				for (WaybillInfoDto infoDto : waybillInfoDtoList) {
					if (StringUtils.isNotEmpty(infoDto.getWaybillNo())) {
						// 如果原单号为空就根据返货单号查询
						if (StringUtils.isEmpty(infoDto.getOriginalWaybillNo())) {
							WaybillExpressEntity entity = waybillDao.queryWaybillExpressByNo(infoDto.getWaybillNo());
							if (entity != null && StringUtils.isNotEmpty(entity.getOriginalWaybillNo())) {
								hasReturnType = "true";
								if ("RETURN_WAYBILL".equals(entity.getReturnType())
										&& StringUtils.isNotEmpty(entity.getReturnType())) {
									status = returnNo;
									commWaybillCheckStatus.setReturnType(status);
									commWaybillCheckStatus.setWaybillNo(entity.getWaybillNo());
									commWaybillCheckStatus.setOriginalWaybillNo(entity.getOriginalWaybillNo());
									commWaybillCheckStatus.setHasReturnType(hasReturnType);
								} else if (("RETURN_CARGO".equals(entity.getReturnType())
										|| ("RETURN_PIECE".equals(entity.getReturnType())))
										&& StringUtils.isNotEmpty(waybill.getReturnType())) {
									status = reGoodsNo;
									commWaybillCheckStatus.setReturnType(status);
									commWaybillCheckStatus.setWaybillNo(entity.getWaybillNo());
									commWaybillCheckStatus.setOriginalWaybillNo(entity.getOriginalWaybillNo());
									commWaybillCheckStatus.setHasReturnType(hasReturnType);
								}
							} else if (entity != null && StringUtils.isEmpty(entity.getOriginalWaybillNo())) {
								waybill = waybillDao.queryExpressWaybillByOriginalWaybillNo(infoDto.getWaybillNo());
								if (waybill != null && StringUtils.isNotEmpty(waybill.getOriginalWaybillNo())) {
									hasReturnType = "false";
									if ("RETURN_WAYBILL".equals(waybill.getReturnType())
											&& StringUtils.isNotEmpty(waybill.getReturnType())) {
										status = returnNo;
										commWaybillCheckStatus.setReturnType(status);
										commWaybillCheckStatus.setWaybillNo(waybill.getOriginalWaybillNo());
										commWaybillCheckStatus.setOriginalWaybillNo(waybill.getWaybillNo());
										commWaybillCheckStatus.setHasReturnType(hasReturnType);
									} else if (("RETURN_CARGO".equals(waybill.getReturnType())
											|| ("RETURN_PIECE".equals(waybill.getReturnType())))
											&& StringUtils.isNotEmpty(waybill.getReturnType())) {
										status = reGoodsNo;
										commWaybillCheckStatus.setReturnType(status);
										commWaybillCheckStatus.setWaybillNo(waybill.getOriginalWaybillNo());
										commWaybillCheckStatus.setOriginalWaybillNo(waybill.getWaybillNo());
										commWaybillCheckStatus.setHasReturnType(hasReturnType);
									}
								}
							}
						} else {
							waybill = waybillDao.queryExpressWaybillByOriginalWaybillNo(infoDto.getOriginalWaybillNo());
							if (waybill != null && StringUtils.isNotEmpty(waybill.getOriginalWaybillNo())) {
								hasReturnType = "false";
								if ("RETURN_WAYBILL".equals(waybill.getReturnType())
										&& StringUtils.isNotEmpty(waybill.getReturnType())) {
									status = returnNo;
									commWaybillCheckStatus.setReturnType(status);
									commWaybillCheckStatus.setWaybillNo(waybill.getWaybillNo());
									commWaybillCheckStatus.setOriginalWaybillNo(waybill.getOriginalWaybillNo());
									commWaybillCheckStatus.setHasReturnType(hasReturnType);
								} else if (("RETURN_CARGO".equals(waybill.getReturnType())
										|| ("RETURN_PIECE".equals(waybill.getReturnType())))
										&& StringUtils.isNotEmpty(waybill.getReturnType())) {
									status = reGoodsNo;
									commWaybillCheckStatus.setReturnType(status);
									commWaybillCheckStatus.setWaybillNo(waybill.getWaybillNo());
									commWaybillCheckStatus.setOriginalWaybillNo(waybill.getOriginalWaybillNo());
									commWaybillCheckStatus.setHasReturnType(hasReturnType);
								}
							}
						}
					}

				}
			}
		} else {
			commWaybillCheckStatus.setInformation("必须输入单号");
		}
		return commWaybillCheckStatus;
	}

	public IWaybillQueryService4dubbo getWaybillQueryService4dubbo() {
		return waybillQueryService4dubbo;
	}

	@Autowired
	public void setWaybillQueryService4dubbo(IWaybillQueryService4dubbo waybillQueryService4dubbo) {
		this.waybillQueryService4dubbo = waybillQueryService4dubbo;
	}

	public IWaybillDao4dubbo getWaybillDao() {
		return waybillDao;
	}

	@Autowired
	public void setWaybillDao(IWaybillDao4dubbo waybillDao) {
		this.waybillDao = waybillDao;
	}

}
