package com.deppon.foss.module.transfer.dubbo.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.dubbo.api.define.ActualFreightEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.BillPayableEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.CrmWaybillServiceDto;
import com.deppon.foss.module.transfer.dubbo.api.define.SignDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryCityProvinceDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillRelateDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillSignDetailVo;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillSignResultEntity;

public interface IWaybillDao4dubbo {

	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-6 下午5:21:02
	 */
	List<WaybillInfoDto> queryWaybillInfoForSOC(WaybillQueryInfoDto waybillQueryInfoDto);

	/**
	 * -------小件查询签收员工等信息
	 * 
	 * @param waybillNo
	 * @return
	 */
	SignDto getWaybillSignInfo(String waybillNo);

	/**
	 * 根据运单号集合返回签收情况
	 * 
	 * @param waybillNoList
	 * @return
	 */
	List<WaybillSignDetailVo> getWaybillSignList(List<String> waybillNoList);

	/**
	 * 查询运单状态<br />
	 * @author 苏玉军
	 * @version 0.1 2013-1-7
	 * @param waybillNo
	 * @return String
	 */
	String queryWaybillStatus(String waybillNo);

	List<WaybillRelateDetailEntity> queryWaybillRelateDetailListByOrderOrWaybillNo(Map<String, Object> params);

	List<String> queryWaybillNoListByParentWaybillNo(String waybillNo);

	List<WaybillExpressEntity> queryWaybillReturnByWaybillNo(WaybillExpressEntity waybillExpress);

	WaybillQueryCityProvinceDto queryCityProvince(String orgCode);

	List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(String waybillNo);

	Date queryFirstSignTimeByWaybillNo(String waybillNo);

	ActualFreightEntity queryByWaybillNo(String waybillNo);

	List<CrmWaybillServiceDto> queryAppWaybillInfoByCondition(Map<String, Object> args, boolean isRowBounds);

	int countQueryAppWaybillInfoByCondition(Map<String, Object> args);

	List<WaybillRelateDetailEntity> queryWaybillRelateDetailByWaybillNos(List<String> waybillNumList, int start,
			int limit, boolean flag);

	WaybillSignResultEntity queryWaybillSignResult(WaybillSignResultEntity entity);

	boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);

	List<BillPayableEntity> queryByWaybillNosAndByBillTypes(List<String> waybillNos, List<String> billTypes);

	List<WaybillInfoDto> queryWaybillInfo(WaybillQueryInfoDto waybillQueryInfoDto);

	WaybillExpressEntity queryWaybillExpressByNo(String waybillNo);

	WaybillExpressEntity queryExpressWaybillByOriginalWaybillNo(String waybillNo);
}