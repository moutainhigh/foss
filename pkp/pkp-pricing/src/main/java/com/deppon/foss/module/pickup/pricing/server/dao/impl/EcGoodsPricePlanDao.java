package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEcGoodsPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PriceBusinessException;
import com.deppon.foss.util.define.FossConstants;

/**
 * @Description:首续重价格方案管理DAO实现
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public class EcGoodsPricePlanDao extends SqlSessionDaoSupport implements  IEcGoodsPricePlanDao{

	/**
	 * 配置文件中的的名称空间namespace
	 */
	private static final String NAME_SPACE = "foss.pkp.pkp-pricing.EcGoodsPricePlanEntityMapper.";
	
	private static final Logger LOGGER = Logger.getLogger(EcGoodsPricePlanDao.class);

	
	/**
	 * 分页查询价格方案信息1、10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE + "queryPricePlanBatchInfo", pricePlanEntity, rowBounds);
	}

	/**
	 * 查询方案总记录数1
	 */
	@Override
	public Long queryPricePlanBatchInfoCount(PricePlanEntity pricePlanEntity) {
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryPricePlanBatchInfoCount", pricePlanEntity);
	}

	/**
	 * 分页查询价格方案明细2、11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultPricePlanDetailBean> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean, int start, int limit) {
		RowBounds row = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE + "queryPricePlanDetailInfo", queryPricePlanDetailBean,row);
	}

	/**
	 * 查询价格方案明细总记录数2
	 */
	@Override
	public Long queryPricePlanDetailInfoCount(QueryPricePlanDetailBean queryPricePlanDetailBean) {
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryPricePlanDetailInfoCount", queryPricePlanDetailBean);
	}

	/**
	 * 通过Id查询价格方案信息3、4、6、7、8
	 */
	@Override
	public PricePlanEntity selectByPrimaryKey(String id) {
		return (PricePlanEntity)getSqlSession().selectOne(NAME_SPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 修改价格方案4
	 */
	@Override
	public int updateByPrimaryKeySelective(PricePlanEntity entity) {
		return getSqlSession().update(NAME_SPACE + "updateByPrimaryKeySelective", entity);
	}
	
	/**
	 * 中止价格方案7、8
	 */
	@Override
	public void stopPricePlan(String id, Date endTime) {
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("id", id);
		map.put("endTime", endTime);
		map.put("versionNo", new Date().getTime());
		//添加修改人工号
		addModifyUser(map);
		getSqlSession().update(NAME_SPACE + "stopPricePlan", map);
	}
	
	/**
	 * 批量删除价格方案5
	 */
	@Override
	public void batchDeletePlan(List<String> pricePlanIds) {
		Map<String ,List<String>> map = new HashMap<String ,List<String>>();
		map.put("pricePlanIds", pricePlanIds);
		getSqlSession().selectList(NAME_SPACE +"batchDeletePlan",map);
	}

	/**
	 * 查询方案的重复性6
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailData(QueryExistPricePlanDetailBean queryExistPriceBean) {
		Map  parameterMap = new HashMap();
		parameterMap.put("beginTime", queryExistPriceBean.getBeginTime());
		parameterMap.put("active", queryExistPriceBean.getActive());
		parameterMap.put("priceRegionId",queryExistPriceBean.getPriceRegionId());
		parameterMap.put("arrvRegionId", queryExistPriceBean.getArrvRegionId());
		parameterMap.put("productCode", queryExistPriceBean.getProductCode());
		parameterMap.put("goodsTypeCode", queryExistPriceBean.getGoodsTypeCode());
		return getSqlSession().selectList(NAME_SPACE +"isExistRpeatPricePlanDetailData",parameterMap);
	}

	/**
	 * 立即激活价格方案6
	 */
	@Override
	public int activePricePlan(String pricePlanId , Date beginTime) {
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("pricePlanId", pricePlanId);
		map.put("versionNo", new Date().getTime());
		map.put("beginTime", beginTime);
		map.put("active", "Y");
		//添加修改人工号
		addModifyUser(map);
		return getSqlSession().update(NAME_SPACE +"activePricePlan",map);
	}
	
/////////////////////////////////////////////////下面是批量插入方法////////////////////////////////////////////////////////////
	/**
	 * 批量插入价格方案和规则明细0
     */
	@Override
	public int insertPricePlanAllBatch(List<PricePlanEntity> pricePlanBatch,
			List<PriceValuationEntity> priceValuationBatch,
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch) {
		if(CollectionUtils.isNotEmpty(pricePlanBatch)){
			Connection con = null;
			PreparedStatement ps = null;
			try {
				con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
				con.setAutoCommit(false);
				this.insertPricePlanBatchList(con, ps, pricePlanBatch);
				pricePlanBatch.clear();//释放内存
				this.insertPriceValuationBatchList(con, ps, priceValuationBatch);
				priceValuationBatch.clear();//释放内存
				this.insertPriceCriteriaDetailBatchList(con, ps, priceCriteriaDetailEntityBatch);
				priceCriteriaDetailEntityBatch.clear();//释放内存
				
			} catch (Exception e) {
				LOGGER.error("插入价格方案表时发生异常：" + e.getMessage());
				try {
					if(con != null){
						con.rollback();
					}
				} catch (SQLException e1) {
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new PriceBusinessException(e1.getMessage());
				}
				throw new PriceBusinessException(e.getMessage());
			}finally{
				try {
					if(ps != null){
						ps.close();
					}
				} catch (SQLException e) {
					try {
						if(con != null){
							con.rollback();
						}
					} catch (SQLException e1) {
						LOGGER.error("事务回滚发生异常：" + e1.getMessage());
						throw new PriceBusinessException(e1.getMessage());
					}
					LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
				}finally{
					if(con != null){
						try {
							con.close();
						} catch (SQLException e) {
							try {
								con.rollback();
							} catch (SQLException e1) {
								LOGGER.error("事务回滚发生异常：" + e1.getMessage());
								throw new PriceBusinessException(e1.getMessage());
							}
							LOGGER.error("关闭connection异常：" + e.getMessage());
						}
					}
				}
			}
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 批量导入价格方案-方案表
	 */
	public void insertPricePlanBatchList(Connection con, PreparedStatement ps, List<PricePlanEntity> pricePlanBatch) throws Exception {
		if(CollectionUtils.isNotEmpty(pricePlanBatch)){
			int count = 0;
			ps = con.prepareStatement("insert /**新增首续重价格方案  2016-08-31 348757-陈程**/ into PKP.T_SRV_EC_PRICE_PLAN(ID, T_SRV_PRICE_REGION_ID, T_SRV_PRICE_REGION_CODE,"
					+ "NAME, BEGIN_TIME, END_TIME, ACTIVE, DESCRIPTION, VERSION_INFO, VERSION_NO, REF_ID, CREATE_TIME, "
					+ "MODIFY_TIME, CREATE_USER_CODE, MODIFY_USER_CODE, CREATE_ORG_CODE, MODIFY_ORG_CODE, CURRENCY_CODE,"
					+ " TRANSPORT_FLAG,CUSTOMER_NAME,CUSTOMER_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
			UserEntity currentUser = FossUserContext.getCurrentUser();
			for(PricePlanEntity pricePlan : pricePlanBatch){
				pricePlan.setCreateUser(currentUser.getEmployee().getEmpCode());
				pricePlan.setCreateOrgCode(currentDept.getCode());
				pricePlan.setModifyUser(currentUser.getEmployee().getEmpCode());
				ps.setString(NumberConstants.NUMBER_1, pricePlan.getId());
				ps.setString(NumberConstants.NUMBER_2, pricePlan.getPriceRegionId());
				ps.setString(NumberConstants.NUMBER_3, pricePlan.getPriceRegionCode());
				ps.setString(NumberConstants.NUMBER_4, pricePlan.getName());
				if(pricePlan.getBeginTime()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_5, new Timestamp(pricePlan.getBeginTime().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_5, null);
				}
				if(pricePlan.getEndTime()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_6, new Timestamp(pricePlan.getEndTime().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_6, null);
				}
				ps.setString(NumberConstants.NUMBER_7, pricePlan.getActive());
				ps.setString(NumberConstants.NUMBER_8, pricePlan.getDescription());
				ps.setString(NumberConstants.NUMBER_9, pricePlan.getVersionInfo());
				ps.setLong(NumberConstants.NUMBER_10, pricePlan.getVersionNo());
				ps.setString(NumberConstants.NUMBER_11, pricePlan.getRefId());
				ps.setTimestamp(NumberConstants.NUMBER_12, new Timestamp(new Date().getTime()));
				ps.setTimestamp(NumberConstants.NUMBER_13, new Timestamp(new Date().getTime()));
				ps.setString(NumberConstants.NUMBER_14, pricePlan.getCreateUser());
				ps.setString(NumberConstants.NUMBER_15, pricePlan.getModifyUser());
				ps.setString(NumberConstants.NUMBER_16, pricePlan.getCreateOrgCode());
				ps.setString(NumberConstants.NUMBER_17, pricePlan.getModifyOrgCode());
				ps.setString(NumberConstants.NUMBER_18, pricePlan.getCurrencyCode());
				ps.setString(NumberConstants.NUMBER_19, pricePlan.getTransportFlag());
				ps.setString(NumberConstants.NUMBER_20, pricePlan.getCustomerName());
				ps.setString(NumberConstants.NUMBER_21, pricePlan.getCustomerCode());
				ps.addBatch();//批处理
				count++;//计数器
				if(count == NumberConstants.NUMBER_500){
					ps.executeBatch();
					con.commit();
					ps.clearBatch();
					count = 0;
				}
			}
			if(count > 0){
				ps.executeBatch();
				con.commit();
				ps.clearBatch();
			}
		}
	}

	/**
	 * 批量导入价格方案-价格规则表
	 */
	public void insertPriceValuationBatchList(Connection con, PreparedStatement ps, List<PriceValuationEntity> priceValuationBatch) throws Exception {
		if(CollectionUtils.isNotEmpty(priceValuationBatch)){
			int count = 0;
			ps = con.prepareStatement("insert /**新增首续重价格方案规则  2016-08-31 348757-陈程**/ into PKP.T_SRV_EC_PRICING_VALUATION(id, goods_type_id, goods_type_code, product_id, product_code, dept_region_id, arrv_region_id, sales_channel_code, sales_channel_id, pricing_entry_id, pricing_entry_code, price_plan_id, price_plan_code, marketing_event_id, marketing_event_code, description, version_no, active, begin_time, end_time, long_or_short, type, currency_code, code, name, centralize_pickup,centralize_delivery, flight_shift, create_time, modify_time, create_user_code, modify_user_code, create_org_code, modify_org_code, pricing_industry_id, pricing_industry_code,self_pick_up, business_begin_time, business_end_time, customer_code, customer_name) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
		    UserEntity currentUser = FossUserContext.getCurrentUser();
			for(PriceValuationEntity priceValuation : priceValuationBatch){
				priceValuation.setCreateUser(currentUser.getEmployee().getEmpCode());//创建人
				priceValuation.setCreateOrgCode(currentDept.getCode());//创建机构
				ps.setString(NumberConstants.NUMBER_1, priceValuation.getId());
				ps.setString(NumberConstants.NUMBER_2, priceValuation.getGoodsTypeId());
				ps.setString(NumberConstants.NUMBER_3, priceValuation.getGoodsTypeCode());
				ps.setString(NumberConstants.NUMBER_4, priceValuation.getProductId());
				ps.setString(NumberConstants.NUMBER_5, priceValuation.getProductCode());
				ps.setString(NumberConstants.NUMBER_6, priceValuation.getDeptRegionId());
				ps.setString(NumberConstants.NUMBER_7, priceValuation.getArrvRegionId());
				ps.setString(NumberConstants.NUMBER_8, priceValuation.getSalesChannelCode());
				ps.setString(NumberConstants.NUMBER_9, priceValuation.getSalesChannelId());
				ps.setString(NumberConstants.NUMBER_10, priceValuation.getPricingEntryId());
				ps.setString(NumberConstants.NUMBER_11, priceValuation.getPricingEntryCode());
				ps.setString(NumberConstants.NUMBER_12, priceValuation.getPricePlanId());
				ps.setString(NumberConstants.NUMBER_13, priceValuation.getPricePlanCode());
				ps.setString(NumberConstants.NUMBER_14, priceValuation.getMarketingEventId());
				ps.setString(NumberConstants.NUMBER_15, priceValuation.getMarketingEventCode());
				ps.setString(NumberConstants.NUMBER_16, priceValuation.getRemarks());
				ps.setLong(NumberConstants.NUMBER_17, priceValuation.getVersionNo());
				ps.setString(NumberConstants.NUMBER_18, priceValuation.getActive());
				this.setBeginAndEndTime(ps, priceValuation);
				ps.setString(NumberConstants.NUMBER_21, priceValuation.getLongOrShort());
				ps.setString(NumberConstants.NUMBER_22, priceValuation.getType());
				ps.setString(NumberConstants.NUMBER_23, priceValuation.getCurrencyCode());
				ps.setString(NumberConstants.NUMBER_24, priceValuation.getCode());
				ps.setString(NumberConstants.NUMBER_25, priceValuation.getName());
				ps.setString(NumberConstants.NUMBER_26, priceValuation.getCentralizePickup());
				ps.setString(NumberConstants.NUMBER_27, priceValuation.getCentralizeDelivery());
				ps.setString(NumberConstants.NUMBER_28, priceValuation.getLightShift());
				ps.setTimestamp(NumberConstants.NUMBER_29, new Timestamp(new Date().getTime()));
				ps.setTimestamp(NumberConstants.NUMBER_30, new Timestamp(new Date().getTime()));
				ps.setString(NumberConstants.NUMBER_31, priceValuation.getCreateUser());
				ps.setString(NumberConstants.NUMBER_32, priceValuation.getModifyUser());
				ps.setString(NumberConstants.NUMBER_33, priceValuation.getCreateOrgCode());
				ps.setString(NumberConstants.NUMBER_34, priceValuation.getModifyOrgCode());
				ps.setString(NumberConstants.NUMBER_35, priceValuation.getPricingIndustryId());
				ps.setString(NumberConstants.NUMBER_36, priceValuation.getPricingIndustryCode());
				ps.setString(NumberConstants.NUMBER_37, priceValuation.getSelfPickUp());
				ps.setString(NumberConstants.NUMBER_38, priceValuation.getBusinessBeginTime());
				ps.setString(NumberConstants.NUMBER_39, priceValuation.getBusinessEndTime());
				ps.setString(NumberConstants.NUMBER_40, priceValuation.getCustomerCode());
				ps.setString(NumberConstants.NUMBER_41, priceValuation.getCustomerName());
				ps.addBatch();//批处理
				count++;//计数器
				if(count == NumberConstants.NUMBER_500){
					ps.executeBatch();
					con.commit();
					ps.clearBatch();
					count = 0;
				}
			}
			if(count > 0){
				ps.executeBatch();
				con.commit();
				ps.clearBatch();
			}
		}
	}

	/**
	 * 批量导入价格方案-价格明细表
	 */
	public void insertPriceCriteriaDetailBatchList(Connection con, PreparedStatement ps, List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch) throws Exception{
		if(CollectionUtils.isNotEmpty(priceCriteriaDetailEntityBatch)){
			int count = 0;
			ps = con.prepareStatement("insert /**新增首续重价格方案明细  2016-08-31 348757-陈程**/ into PKP.T_SRV_EC_PRICING_CRI_DETAIL(Id, Name, caculate_type, fee, fee_rate, leftrange, rightrange, min_fee, max_fee, sub_type, canmodify, Description, process_program, process_parm_val, pricing_criteria_id, parm2, parm1, t_srv_price_rule_id, parm3, parm4, parm5, discount_rate, active, version_no, dept_region_id, pricing_valuation_id, candelete, min_fee_rate, max_fee_rate,dimension, create_time, modify_time, comb_bill_type_code, valueadd_caculate_type, valueadd_leftrange, valueadd_rightrange, min_vote, max_vote, package_type, refund_type, returnbill_type, min_insurance_fee, togeter_category, can_not_charge) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(PriceCriteriaDetailEntity priceCriteriaDetail : priceCriteriaDetailEntityBatch){
				priceCriteriaDetail.setCreateDate(new Date());
				priceCriteriaDetail.setModifyDate(priceCriteriaDetail.getCreateDate());
				ps.setString(NumberConstants.NUMBER_1, priceCriteriaDetail.getId());
				ps.setString(NumberConstants.NUMBER_2, priceCriteriaDetail.getName());
				ps.setString(NumberConstants.NUMBER_3, priceCriteriaDetail.getCaculateType());
				this.setFee(ps, priceCriteriaDetail);
				ps.setBigDecimal(NumberConstants.NUMBER_5, priceCriteriaDetail.getFeeRate());
				ps.setBigDecimal(NumberConstants.NUMBER_6, priceCriteriaDetail.getLeftrange());
				ps.setBigDecimal(NumberConstants.NUMBER_7, priceCriteriaDetail.getRightrange());
				this.setMinFee(ps, priceCriteriaDetail);
				ps.setString(NumberConstants.NUMBER_10, priceCriteriaDetail.getSubType());
				ps.setString(NumberConstants.NUMBER_11, priceCriteriaDetail.getCanmodify());
				ps.setString(NumberConstants.NUMBER_12, priceCriteriaDetail.getDescription());
				ps.setString(NumberConstants.NUMBER_13, priceCriteriaDetail.getProcessProgram());
				ps.setString(NumberConstants.NUMBER_14, priceCriteriaDetail.getProcessParmVal());
				ps.setString(NumberConstants.NUMBER_15, priceCriteriaDetail.getPricingCriteriaId());
				this.setParms(ps, priceCriteriaDetail);
				ps.setBigDecimal(NumberConstants.NUMBER_22, priceCriteriaDetail.getDiscountRate());
				ps.setString(NumberConstants.NUMBER_23, priceCriteriaDetail.getActive());
				ps.setLong(NumberConstants.NUMBER_24, priceCriteriaDetail.getVersionNo());
				ps.setString(NumberConstants.NUMBER_25, priceCriteriaDetail.getDeptRegionId());
				ps.setString(NumberConstants.NUMBER_26, priceCriteriaDetail.getPricingValuationId());
				ps.setString(NumberConstants.NUMBER_27, priceCriteriaDetail.getCandelete());
				ps.setBigDecimal(NumberConstants.NUMBER_28, priceCriteriaDetail.getMinFeeRate());
				ps.setBigDecimal(NumberConstants.NUMBER_29, priceCriteriaDetail.getMaxFeeRate());
				ps.setBigDecimal(NumberConstants.NUMBER_30, priceCriteriaDetail.getDimension());
				this.setCreateDate(ps, priceCriteriaDetail);
				ps.setString(NumberConstants.NUMBER_33, priceCriteriaDetail.getCombBillTypeCode());
				ps.setString(NumberConstants.NUMBER_34, priceCriteriaDetail.getValueaddCaculateType());
				ps.setBigDecimal(NumberConstants.NUMBER_35, priceCriteriaDetail.getValueaddLeftrange());
				ps.setBigDecimal(NumberConstants.NUMBER_36, priceCriteriaDetail.getValueaddRightrange());
				this.setMinVote(ps, priceCriteriaDetail);
				ps.setString(NumberConstants.NUMBER_39, priceCriteriaDetail.getPackageType());
				ps.setString(NumberConstants.NUMBER_40, priceCriteriaDetail.getRefundType());
				ps.setString(NumberConstants.NUMBER_41, priceCriteriaDetail.getReturnbillType());
				this.setMinInsuranceFee(ps, priceCriteriaDetail);
				ps.setString(NumberConstants.NUMBER_43, priceCriteriaDetail.getTogeterCategory());
				ps.setString(NumberConstants.NUMBER_43+1, priceCriteriaDetail.getCanNotCharge());
				ps.addBatch();//批处理
				count++;//计数器
				if(count == NumberConstants.NUMBER_500){
					ps.executeBatch();
					con.commit();
					ps.clearBatch();
					count = 0;
				}
			}
			if(count > 0){
				ps.executeBatch();
				con.commit();
				ps.clearBatch();
			}
		}
	}

//=================================== 以下方法都是上面方法里抽取出的需要判断条件设置值方法==================================================
	private void setMinInsuranceFee(PreparedStatement ps, PriceCriteriaDetailEntity priceCriteriaDetail) throws SQLException {
		if(priceCriteriaDetail.getMinInsuranceFee() != null){
            ps.setDouble(NumberConstants.NUMBER_42, priceCriteriaDetail.getMinInsuranceFee() != null ? priceCriteriaDetail.getMinInsuranceFee() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_42, Types.LONGVARCHAR);
        }
	}

	private void setMinVote(PreparedStatement ps, PriceCriteriaDetailEntity priceCriteriaDetail) throws SQLException {
		if(priceCriteriaDetail.getMinVote() != null){
            ps.setDouble(NumberConstants.NUMBER_37, priceCriteriaDetail.getMinVote() != null ? priceCriteriaDetail.getMinVote() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_37, Types.LONGVARCHAR);
        }
		if(priceCriteriaDetail.getMaxVote() != null){
            ps.setDouble(NumberConstants.NUMBER_38, priceCriteriaDetail.getMaxVote() != null ? priceCriteriaDetail.getMaxVote() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_38, Types.LONGVARCHAR);
        }
	}

	private void setCreateDate(PreparedStatement ps, PriceCriteriaDetailEntity priceCriteriaDetail) throws SQLException {
		if(priceCriteriaDetail.getCreateDate()!= null){
            ps.setTimestamp(NumberConstants.NUMBER_31, new Timestamp(priceCriteriaDetail.getCreateDate().getTime()));
        }else{
            ps.setTimestamp(NumberConstants.NUMBER_31, null);
        }
		if(priceCriteriaDetail.getModifyDate()!= null){
            ps.setTimestamp(NumberConstants.NUMBER_32, new Timestamp(priceCriteriaDetail.getModifyDate().getTime()));
        }else{
            ps.setTimestamp(NumberConstants.NUMBER_32, null);
        }
	}

	private void setFee(PreparedStatement ps, PriceCriteriaDetailEntity priceCriteriaDetail) throws SQLException {
		if(priceCriteriaDetail.getFee() != null){
            ps.setLong(NumberConstants.NUMBER_4, priceCriteriaDetail.getFee() != null ? priceCriteriaDetail.getFee()*NumberConstants.NUMBER_100 : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_4, Types.LONGVARCHAR);
        }
	}

	private void setParms(PreparedStatement ps, PriceCriteriaDetailEntity priceCriteriaDetail) throws SQLException {
		if(priceCriteriaDetail.getParm2() != null){
            ps.setLong(NumberConstants.NUMBER_16, priceCriteriaDetail.getParm2() != null ? priceCriteriaDetail.getParm2() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_16, Types.LONGVARCHAR);
        }
		if(priceCriteriaDetail.getParm1() != null){
            ps.setLong(NumberConstants.NUMBER_17, priceCriteriaDetail.getParm1() != null ? priceCriteriaDetail.getParm1() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_17, Types.LONGVARCHAR);
        }
		ps.setString(NumberConstants.NUMBER_18, priceCriteriaDetail.gettSrvPriceRuleId());

		if(priceCriteriaDetail.getParm3() != null){
            ps.setLong(NumberConstants.NUMBER_19, priceCriteriaDetail.getParm3() != null ? priceCriteriaDetail.getParm3() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_19, Types.LONGVARCHAR);
        }
		if(priceCriteriaDetail.getParm4() != null){
            ps.setLong(NumberConstants.NUMBER_20, priceCriteriaDetail.getParm4() != null ? priceCriteriaDetail.getParm4() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_20, Types.LONGVARCHAR);
        }
		if(priceCriteriaDetail.getParm5() != null){
            ps.setLong(NumberConstants.NUMBER_21, priceCriteriaDetail.getParm5() != null ? priceCriteriaDetail.getParm5() : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_21, Types.LONGVARCHAR);
        }
	}

	private void setMinFee(PreparedStatement ps, PriceCriteriaDetailEntity priceCriteriaDetail) throws SQLException {
		if(priceCriteriaDetail.getMinFee() != null){
            ps.setLong(NumberConstants.NUMBER_8, priceCriteriaDetail.getMinFee() != null ? priceCriteriaDetail.getMinFee()*NumberConstants.NUMBER_100 : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_8, Types.LONGVARCHAR);
        }
		if(priceCriteriaDetail.getMaxFee() != null){
            ps.setLong(NumberConstants.NUMBER_9, priceCriteriaDetail.getMaxFee() != null ? priceCriteriaDetail.getMaxFee()*NumberConstants.NUMBER_100 : 0);
        }else{
            ps.setNull(NumberConstants.NUMBER_9, Types.LONGVARCHAR);
        }
	}

	private void setBeginAndEndTime(PreparedStatement ps, PriceValuationEntity priceValuation) throws SQLException {
		if(priceValuation.getBeginTime()!= null){
			ps.setTimestamp(NumberConstants.NUMBER_19, new Timestamp(priceValuation.getBeginTime().getTime()));
		}else{
			ps.setTimestamp(NumberConstants.NUMBER_19, null);
		}
		if(priceValuation.getEndTime()!= null){
			ps.setTimestamp(NumberConstants.NUMBER_20, new Timestamp(priceValuation.getEndTime().getTime()));
		}else{
			ps.setTimestamp(NumberConstants.NUMBER_20, null);
		}
	}

	private void addModifyUser(Map<String,Object> param){
		if(param==null){
			return;
		}
		UserEntity user = FossUserContext.getCurrentUser();
		param.put("modifyUser", user==null?null:user.getUserName());//userName为工号
	}
}