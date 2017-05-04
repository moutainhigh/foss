package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivablePartnerEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;

/**
 * 操作应收单实体DAO层
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-10 下午3:40:32
 * @since
 * @version
 */
public class BillReceivablePartnerEntityDao extends iBatis3DaoImpl implements IBillReceivablePartnerEntityDao {

	/**
	 *  命名空间路径
	 */
	private static final String NAMESPACES = "foss.stl.BillReceivablePartnerEntityDao.";

	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:38:48
	 * @param receivableNos
	 *  应收单号集合
	 * @param  active 是否有效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByReceivableNOs(List<String> receivableNos, String active) {
		if (CollectionUtils.isEmpty(receivableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivableNos", receivableNos);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectByReceivableNos", map);
	}

	/**
	 * 按照运单号和应付部门编码集合查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:27:19
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("orgCodeList", orgCodeList);
		if(StringUtils.isNotEmpty(active)){
			map.put("active", active);
		}
		map.put("currentInfo", currentInfo);//操作者信息
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndOrgCodes",map);
	}

	/**
	 * 根据来源单号集合和应收部门编码集合，查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午4:42:04
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos, List<String> orgCodes,String sourceBillType, String active,CurrentInfo currentInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("orgCodes", orgCodes);
		map.put("sourceBillType", sourceBillType);
		map.put("active", active);
		//操作者信息
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNOsAndOrgCodes", map);
	}

	/**
	 * 根据应收单号和数据权限对应的部门查询应收单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-6-12 下午6:33:19
	 * @param waybillNos
	 * @param active
	 * @param currentInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryByReceivableNosAndOrgCodes(List<String> receivableNos, String active, CurrentInfo currentInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		//应收单号
		map.put("receivableNos", receivableNos);
		//是否有效
		map.put("active", active);
		//操作者信息
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES+"selectByReceivableNosAndOrgCodes",map);
	}

    /*
    *@author 099995-foss-hemingyu
     * @date 2016-01-14 上午15:47:38
     * @param wayBillNo
     *            运单号
     * @param billType
     *            应收类型
     */
    public List<BillReceivableEntity> selectByWayBillNoAndBillType(String wayBillNo,String billType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billType", billType);
        map.put("wayBillNo", wayBillNo);
        map.put("active","Y");
        return this.getSqlSession().selectList(NAMESPACES + "selectByWayBillNoAndBillType", map);
    }
    
    @SuppressWarnings("unchecked")
	public List<StlBillDetailDto> countReceivableBills(BillingQueryRequestDto requestDto){
    	return this.getSqlSession().selectList(NAMESPACES + "countReceivableBills",requestDto);
    }
}
