package com.deppon.foss.module.transfer.dubbo.api.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.dubbo.api.dao.IWaybillDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.ActualFreightEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.BillPayableEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.CrmWaybillServiceDto;
import com.deppon.foss.module.transfer.dubbo.api.define.ProductEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.SignDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryCityProvinceDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillRelateDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillSignDetailVo;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillSignResultEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

public class WaybillDao4dubbo extends BaseDao implements IWaybillDao4dubbo {
	private final static Logger LOGGER = LogManager.getLogger(WaybillDao4dubbo.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillInfoDto> queryWaybillInfoForSOC(WaybillQueryInfoDto waybillQueryInfoDto) {
		return this.getSqlSession().selectList("queryWaybillInfoForSOC4dubbo", waybillQueryInfoDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SignDto getWaybillSignInfo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		List<SignDto> dtos = this.getSqlSession().selectList("getWaybillSignInfo4dubbo", map);
		if (dtos != null && dtos.size() > 0) {
			return dtos.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillSignDetailVo> getWaybillSignList(List<String> waybillNoList) {
		return this.getSqlSession().selectList("getWaybillSignList4dubbo", waybillNoList);
	}

	/**
	 * @TODO 这是个存储过程，需要提出来
	 */
	@Override
	public String queryWaybillStatus(String waybillNo) {
		Map<String, Object> args = new HashMap<String, Object>();
		String vGoodsStatus = "";
		args.put("waybillNo", waybillNo);
		args.put("msg", "");
		args.put("v_goods_status", "");
		this.getSqlSession().selectOne("queryWaybillStatus4dubbo", args);
		vGoodsStatus = (String) args.get("v_goods_status");
		return vGoodsStatus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailListByOrderOrWaybillNo(Map<String, Object> params) {
		return this.getSqlSession().selectList("queryWaybillRelateDetailListByOrderOrWaybillNo4dubbo", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillNoListByParentWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList("queryWaybillNoListByParentWaybillNo4dubbo", waybillNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillExpressEntity> queryWaybillReturnByWaybillNo(WaybillExpressEntity waybillExpress) {
		if (waybillExpress.getWaybillNo() != null) {
			return this.getSqlSession().selectList("queryWaybillReturnByWaybillNo4dubbo", waybillExpress);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WaybillQueryCityProvinceDto queryCityProvince(String orgCode) {
		List<WaybillQueryCityProvinceDto> list = this.getSqlSession().selectList("queryCityProvince4dubbo", orgCode);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(String waybillNo) {
		if (StringUtils.isEmpty(waybillNo)) {
			return null;
		}
		List<WaybillExpressEntity> list = this.getSqlSession().selectList("queryWaybillByOriginalWaybillNo4dubbo",
				waybillNo);
		return list;
	}

	@Override
	public Date queryFirstSignTimeByWaybillNo(String waybillNo) {
		return (Date) this.getSqlSession().selectOne("queryFirstsignTime4dubbo", waybillNo);
	}

	@Override
	public ActualFreightEntity queryByWaybillNo(String waybillNo) {
		return (ActualFreightEntity) this.getSqlSession().selectOne("selectByWaybillNo4dubbo", waybillNo);
	}

	//////////////// DPAPP ↓↓↓↓//////////////////////
	@SuppressWarnings("unchecked")
	@Override
	public List<CrmWaybillServiceDto> queryAppWaybillInfoByCondition(Map<String, Object> args, boolean isRowBounds) {
		if (isRowBounds) {
			int start = (Integer) args.get("pageNum");
			int limit = (Integer) args.get("pageSize");
			if (start > 1) {
				start = (start - 1) * limit;
			} else {
				start = 0;
			}
			RowBounds rb = new RowBounds(start, limit);
			return this.getSqlSession().selectList("queryAppWaybillInfoByCondition4dubbo", args, rb);
		} else {
			return this.getSqlSession().selectList("queryAppWaybillInfoByCondition4dubbo", args);
		}

	}

	@Override
	public int countQueryAppWaybillInfoByCondition(Map<String, Object> args) {
		return (Integer) this.getSqlSession().selectOne("countQueryAppWaybillInfoByCondition4dubbo", args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailByWaybillNos(List<String> waybillNumList, int start,
			int limit, boolean flag) {
		LOGGER.info("进入Dao层 queryWaybillRelateDetailByWaybillNos");
		if (waybillNumList == null || waybillNumList.size() <= 0) {
			LOGGER.error("运单集合为空");
			throw new BusinessException("运单集合不能为空");
		}
		if (flag) {// 分页查询
			if (start < 0)
				start = 0;
			if (limit > ConstantsNumberSonar.SONAR_NUMBER_2000)
				limit = ConstantsNumberSonar.SONAR_NUMBER_2000;
			RowBounds rb = new RowBounds(start, limit);
			return this.getSqlSession().selectList("queryWaybillRelateDetailByWaybillNums4dubbo", waybillNumList, rb);
		} else {// 不进行分页
			return this.getSqlSession().selectList("queryWaybillRelateDetailByWaybillNums4dubbo", waybillNumList);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public WaybillSignResultEntity queryWaybillSignResult(WaybillSignResultEntity entity) {
		List<WaybillSignResultEntity> waybillSignResultEntitys = this.getSqlSession()
				.selectList("queryWaybillSignResult4dubbo", entity);
		return CollectionUtils.isEmpty(waybillSignResultEntitys) ? null : waybillSignResultEntitys.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime) {
		if (StringUtils.isEmpty(productCode)) {
			return false;
		}
		// 判定时间是否为空
		if (billTime == null) {
			billTime = new Date();
		}
		// 快递一级汽运
		List<String> productCodeList = new ArrayList<String>();
		productCodeList.add("TRANS_EXPRESS");
		// 封装查询参数
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("productCode", productCode);
		parameter.put("billTime", billTime);
		parameter.put("active", "Y");
		parameter.put("levels", ConstantsNumberSonar.SONAR_NUMBER_3);
		parameter.put("productCodeList", productCodeList);
		List<ProductEntity> list = this.getSqlSession().selectList("onlineDetermineIsExpressByProductCode4dubbo",
				parameter);
		return CollectionUtils.isEmpty(list) ? false : true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndByBillTypes(List<String> waybillNos, List<String> billTypes) {
		if (CollectionUtils.isEmpty(waybillNos) || CollectionUtils.isEmpty(billTypes)) {
			throw new BusinessException("查询应付单，输入的运单号集合不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("billTypes", billTypes);
		map.put("active", "Y");
		return this.getSqlSession().selectList("selectByWaybillNosAndByBillTypes4dubbo", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillInfoDto> queryWaybillInfo(WaybillQueryInfoDto waybillQueryInfoDto) {
		return this.getSqlSession().selectList("queryWaybillInfo4dubbo", waybillQueryInfoDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WaybillExpressEntity queryWaybillExpressByNo(String waybillNo) {
		List<WaybillExpressEntity> list = this.getSqlSession().selectList("queryWaybillExpressByNo4dubbo", waybillNo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public WaybillExpressEntity queryExpressWaybillByOriginalWaybillNo(String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		return (WaybillExpressEntity) this.getSqlSession().selectOne("queryExpressWaybillByOriginalWaybillNo4dubbo",
				waybillNo);
	}

}
