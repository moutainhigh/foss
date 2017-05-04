package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkActivitiesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkLineDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkMultipleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkOptionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkLineDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkMultipleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 市场活动接口实现类Service层
 * 
 * @author 078816
 * @date   2014-03-31
 *
 */
public class MarkActivitiesService implements IMarkActivitiesService {

	//活动的DAO层接口
	private IMarkActivitiesDao markActivitiesDao;
	
	
	//活动线路部门Service层接口
	private IMarkLineDeptService markLineDeptService;
	
	//优惠活动折扣信息Service接口
	private IMarkOptionsService markOptionsService;
	
	//活动相关枚举信息Service接口
	private IMarkMultipleService markMultipleService;
	
	//组织信息接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	//产品类型
	private IProductService productService;
	
	public void setMarkActivitiesDao(IMarkActivitiesDao markActivitiesDao) {
		this.markActivitiesDao = markActivitiesDao;
	}

	public void setMarkLineDeptService(IMarkLineDeptService markLineDeptService) {
		this.markLineDeptService = markLineDeptService;
	}

	public void setMarkOptionsService(IMarkOptionsService markOptionsService) {
		this.markOptionsService = markOptionsService;
	}

	public void setMarkMultipleService(IMarkMultipleService markMultipleService) {
		this.markMultipleService = markMultipleService;
	}
	
	/**
	 * @return the productService
	 */
	public IProductService getProductService() {
		return productService;
	}

	/**
	 * @param productService the productService to set
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 *新增一个活动对象
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	@Transactional
	public int addMarkActivities(MarkActivitiesEntity entity) {
		if(null == entity){
			throw new MarkActivitiesException("传入的活动对象为空");
		}
		int m = markActivitiesDao.addMarkActivities(entity);
		return m;
	}

	/**
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	public int deleteMarkActivities(MarkActivitiesEntity entity) {
		return 0;
	}

	/**
	 *修改一个活动对象
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	@Transactional
	public int updateMarkActivities(MarkActivitiesEntity entity) {
		if(null == entity){
			throw new MarkActivitiesException("传入的活动对象为空");
		}
		int m = 0;
		String isActive  = entity.getActive();
		//如果传入的对象状态为N，就直接作废，为Y，就先作废，再新增
		if(StringUtils.equals(isActive, FossConstants.INACTIVE)){
			//根据活动的crmID作废和该活动相关的信息
//			this.deleteActivitiesRelatedInformation(entity.getActiveCrmId());
			//作废活动信息
			entity.setActive(FossConstants.ACTIVE);
			m = markActivitiesDao.updateMarkActivities(entity);
		}else if(StringUtils.equals(isActive, FossConstants.ACTIVE)){
			markActivitiesDao.updateMarkActivities(entity);
			m = markActivitiesDao.addMarkActivities(entity);
		}
		return m;
	}

	/**
	 *根据相应的条件查询活动的相关信息
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	public List<MarkActivitiesQueryConditionDto> queryMarkActivitiesByCondition(MarkActivitiesQueryConditionDto entity){
		if(null == entity){
			throw new MarkActivitiesException("传入的活动Dto对象为空");
		}
		//查询的活动优惠信息结果
		List<MarkActivitiesQueryConditionDto> activetyList = markActivitiesDao.queryMarkActivitiesByCondition(entity);
		if(CollectionUtils.isNotEmpty(activetyList)){
			for(MarkActivitiesQueryConditionDto dto:activetyList){
				//获取活动的货物品名信息
				if(dto!=null){					
					addGoodName(dto);
				}
			}			
		}
		return activetyList;
	}

	private void addGoodName(MarkActivitiesQueryConditionDto dto) {
		//枚举信息
		MarkMultipleEntity mulEntityGoodName = new MarkMultipleEntity();
		mulEntityGoodName.setActiviteCrmId(dto.getActiveCrmId());			    			    
		//开单品名(PDA不校验开单品名)				    
		mulEntityGoodName.setEnumType(ComnConst.GOODS_NAME_MARK_ACTIVITY);	
		//枚举列表			    
		List<MarkMultipleEntity> mulList = markMultipleService.queryMarkActivityMultiplie(mulEntityGoodName);				
		if(CollectionUtils.isNotEmpty(mulList)){
			List<String> goodNameList=new ArrayList<String>();
			for(MarkMultipleEntity goodNameEntity:mulList){
				if(goodNameEntity!=null){
					goodNameList.add(goodNameEntity.getValueCode());	
				}						
			}
			dto.setGoodNameList(goodNameList);
		}
	}

	/**
	 *根据活动编码获取活动内容
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode) {
		if(StringUtils.isEmpty(activityCode)){
			throw new MarkActivitiesException("传入的活动编码为空");
		}
		MarkActivitiesEntity activetyEntity = markActivitiesDao.queryMarkActivitiesByCode(activityCode);
		return activetyEntity;
	}	
	
	/**
	 * 根据活动编码和开单时间查询活动信息(不考虑活动是否有效)
	 *
	 * auther:WangQianJin
	 * date:2014-06-12
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCodeAndBilltime(String activityCode,Date billlingTime){
		if(StringUtils.isEmpty(activityCode)){
			throw new MarkActivitiesException("传入的活动编码为空");
		}
		if(billlingTime==null){
			throw new MarkActivitiesException("传入的开单时间为空");
		}
		MarkActivitiesEntity activetyEntity = markActivitiesDao.queryMarkActivitiesByCodeAndBilltime(activityCode,billlingTime);
		return activetyEntity;
	}

	/**
	 *根据活动名称获取活动内容
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByName(String activityName) {
		if(StringUtils.isEmpty(activityName)){
			throw new MarkActivitiesException("传入的活动名称为空");
		}
		MarkActivitiesEntity activetyEntity = markActivitiesDao.queryMarkActivitiesByName(activityName);
		return activetyEntity;
	}

	/**
	 *根据crmID判断是否有活动存在
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	@Override
	public boolean queryIsExistsMarkActivitiesByCrmId(BigDecimal crmId) {
		//标记该crmID对应的活动是否存在，默认不存在
		boolean flag = false;
		if(crmId == null){
			throw new MarkActivitiesException("传入的crmID为空");
		}
		//根据crmId查询是否存在活动
		MarkActivitiesEntity entity = markActivitiesDao.queryMarkActivitiesByCrmId(crmId);
		
		if(null != entity){
			flag = true;
		} 
		return flag;
	}

	/**
	 *根据活动的crmID作废和该活动相关的信息
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	/*@Transactional
	private void deleteActivitiesRelatedInformation(BigDecimal activityCrmId){
		//活动开展部门对象
		MarkActivitiesDeptEntity deptEntity = new MarkActivitiesDeptEntity();
		//活动线路部门对象
		MarkLineDeptEntity lineDeptEntity = new MarkLineDeptEntity();
		//枚举对象
		MarkMultipleEntity enumEntity = new MarkMultipleEntity();
		//折扣信息对象
		MarkOptionsEntity  entity = new MarkOptionsEntity();
		
		deptEntity.setActiviteCrmId(activityCrmId);
		deptEntity.setActive(FossConstants.INACTIVE);
		
		lineDeptEntity.setActiviteCrmId(activityCrmId);
		lineDeptEntity.setActive(FossConstants.INACTIVE);
		
		enumEntity.setActiviteCrmId(activityCrmId);
		enumEntity.setActive(FossConstants.INACTIVE);
		
		entity.setActiveCrmId(activityCrmId);
		entity.setActive(FossConstants.INACTIVE);
		
		//作废活动的相关信息
		//活动的开展部门Service层接口
		markActivitiesDeptService.updateMarkActivitiesDept(deptEntity);
		//活动线路部门Service层接口
		markLineDeptService.updateMarkActivitiesLineDept(lineDeptEntity);
		//活动相关枚举信息Service接口
		List<MarkMultipleEntity> list = new ArrayList<MarkMultipleEntity>();
		list.add(enumEntity);
		markMultipleService.updateMarkActivitiesMultiple(list,FossConstants.INACTIVE);
		//优惠活动折扣信息Service接口
		markOptionsService.updateMarkActivitiesOptions(entity);
		
	}*/
	
	/**
	 * 根据部门编码查询部门名称
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-18
	 *
	 */
	private String queryDeptNameByCode(String deptCode){
		String name = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(deptCode);
		return name;
	}

	/**
	 * 根据开单界面传递过来的条件查询对应的折扣信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-22
	 *
	 */
	@Override
	public MarkActivitiesQueryConditionDto queryMarkActivitiesInfoByCondition(
			MarkActivitiesQueryConditionDto entity) {
		if(null == entity){
			throw new MarkActivitiesException("传入的活动Dto对象为空");
		}
		//获取活动编码
		String activityCode = entity.getCode();
		if(StringUtils.isEmpty(activityCode)){
			throw new MarkActivitiesException("传入的活动编码为空");
		}
		//根据活动编码去获取活动对象
		 MarkActivitiesEntity markActEntity = this.queryMarkActivitiesByCode(activityCode);
		
		 //根据活动编码与开单时间获取活动对象（根据时间建模，适用于更改单）
		 if(null == markActEntity){
			 markActEntity = this.queryMarkActivitiesByCodeAndBilltime(activityCode,entity.getBilllingTime());
		 }			 
		 
		 if(null == markActEntity){
				throw new MarkActivitiesException("根据传入的活动编码查询不到对应的活动信息");
			}else{
				
				//Y N
				 String isExhibitionGoods = markActEntity.getIsExhibitionGoods();
				 String jsh = entity.getIsExhibitionGoods();
				 
					if(!StringUtils.isEmpty(isExhibitionGoods)&&!StringUtils.isEmpty(jsh)&&"Y".equalsIgnoreCase(isExhibitionGoods)&&"N".equalsIgnoreCase(jsh)){		
						throw new MarkActivitiesException("FOSS开单界面未勾选展会货或者传值问题！");
					}
				
				if(null == markActEntity.getActiveStartTime()&& null == markActEntity.getActiveEndTime()){
					entity.setBilllingTime(null);
				}
				
				if(StringUtils.isEmpty(markActEntity.getActivityCategory())){
					entity.setActivityCategory(null);
				}
				
				if(StringUtils.isEmpty(markActEntity.getActivityType())){
					entity.setActivityType(null);
				}
				
				if(null == markActEntity.getMaxCargoAmount() && null == markActEntity.getMinCargoAmount()){
					entity.setBilllingAmount(null);
				}
				
				if(null == markActEntity.getMaxCargoVolume() && null == markActEntity.getMinCargoVolume()){
					entity.setBilllingVolumn(null);
				}
				
				if(null == markActEntity.getMaxCargoWeight() && null == markActEntity.getMinCargoWeight()){
					entity.setBilllingWeight(null);
				}
				
				if(StringUtils.isEmpty(markActEntity.getName())){
					entity.setName(null);
				}
			}
		 //活动编码
		 String activeName =  markActEntity.getName();
		 entity.setName(activeName);
		 
		//活动的crmId
		BigDecimal activityCrmId = markActEntity.getActiveCrmId();
		//查询的活动优惠信息结果
//		List<MarkActivitiesQueryConditionDto> activetyList = markActivitiesDao.queryMarkActivitiesByCondition(entity);
//
//		if(CollectionUtils.isEmpty(activetyList)){
//			throw new MarkActivitiesException("该运单不符合所选活动的参与条件！");
//		}
//		//活动开展部门对象
//		MarkActivitiesDeptEntity deptEntity = new MarkActivitiesDeptEntity();
//		String developDeptCode = entity.getDevelopDeptCode();
//		if(StringUtils.isEmpty(developDeptCode)){
//			throw new MarkActivitiesException("活动开展部门不能为空");
//		}
//		deptEntity.setActiviteCrmId(activityCrmId);
//		//根据活动的crmId查询出来该活动的所有的开展部门
//	    List<MarkActivitiesDeptEntity> deptList = markActivitiesDeptService.queryMarkActivitiesDept(deptEntity);
//	    //根据活动的crmId与开单时间查询出来该活动的所有的开展部门（根据时间建模，适用于更改单）
//		if(CollectionUtils.isEmpty(deptList)){
//			deptList = markActivitiesDeptService.queryMarkActivitiesDeptByBillTime(deptEntity,entity.getBilllingTime());
//		}
//		if(CollectionUtils.isEmpty(deptList)){
//			throw new MarkActivitiesException("活动:"+entity.getName()+"" +
//					"不存在开展部门");
//		}else{
//			//讲当前活动的开展部门封装到一个list里面
//			 List<String> codes = new ArrayList<String>();
//			 for (MarkActivitiesDeptEntity obj : deptList) {
//				 if(StringUtils.isNotEmpty(obj.getOrgCode())){
//					 codes.add(obj.getOrgCode());
//				 }
//			}
//			 //查询当前部门是否在活动的开展部门里面
//			 long orgList = orgAdministrativeInfoService.queryDeptInfoByCode(codes, developDeptCode);
//			 if(orgList==0){
//				 throw new MarkActivitiesException("部门："+this.queryDeptNameByCode(developDeptCode)+"不在活动"+entity.getName()+"" +
//							"开展范围内");
//			 }
//		}
		
		//线路部门对象
		MarkLineDeptEntity lineEntity = new MarkLineDeptEntity();
		lineEntity.setActiviteCrmId(activityCrmId);
		List<MarkLineDeptEntity> lineList = markLineDeptService.queryMarkActivityLineDept(lineEntity);
		if(CollectionUtils.isNotEmpty(lineList)){
			//用来标记活动的出发外场是否存在
			boolean leaveOutFlag = false;
			//标记活动的到达外场是否存在
			boolean arriveOutFlag = false;
			//标记活动的出发区域是否存在
			boolean leaveAreaFlag = false;
			//标记活动的到达区域是否存在
			boolean arriveAreaFlag = false;
			
			//存放出发区域的list
			List<String> leaveAreaList = new ArrayList<String>();
			//存放到达区域的list
			List<String> arriveAreaList = new ArrayList<String>();
			
			for (MarkLineDeptEntity lineDeptEntity : lineList) {
				if(StringUtils.isNotEmpty(lineDeptEntity.getOrigOrgCode()) && !leaveOutFlag){
					leaveOutFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getDestOrgCode()) && !arriveOutFlag){
					arriveOutFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getLeaveAreaCode()) && !leaveAreaFlag){
					leaveAreaFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getArriveAreaCode()) && !arriveAreaFlag){
					arriveAreaFlag = true;
				}
//				if(leaveOutFlag && arriveOutFlag && leaveAreaFlag && arriveAreaFlag){
//					break;
//				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getLeaveAreaCode())){
					
					leaveAreaList.add(lineDeptEntity.getLeaveAreaCode());
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getArriveAreaCode())){
					arriveAreaList.add(lineDeptEntity.getArriveAreaCode());
				}
			}
			
			//获取线路的出发外场
			String leaveOutFieldDept = entity.getStartOutFieldCode();
			//获取线路的到达外场
			String arriveOutFieldDept = entity.getArriveOutFieldCode();
			//出发区域
			String leaveAreaCode = entity.getLeaveAreaCode();
			//到达区域
			String arriveAreaCode = entity.getArriveAreaCode();
			
			//wp_078816_20140611 修改 去掉活动始发外场和到达外场必须同时存在的限制，改为二者至少有一个存在即可
			if(leaveOutFlag || arriveOutFlag){
				
				if(!StringUtils.isEmpty(leaveOutFieldDept)|| !StringUtils.isEmpty(arriveOutFieldDept)){
					
					if(StringUtils.isNotEmpty(leaveOutFieldDept)){
						
						lineEntity.setOrigOrgCode(leaveOutFieldDept);
					}
					if(StringUtils.isNotEmpty(arriveOutFieldDept)){
						
						lineEntity.setDestOrgCode(arriveOutFieldDept);
					}
					
					lineList = markLineDeptService.queryMarkActivityLineDept(lineEntity);
					if(CollectionUtils.isEmpty(lineList)){
						if(null==entity.getIsExpressFous()){
						throw new MarkActivitiesException("线路："+this.queryDeptNameByCode(leaveOutFieldDept)+
								"——"+this.queryDeptNameByCode(arriveOutFieldDept)+"不在活动"+entity.getName()+"" +
								"开展范围内");
						}
					}
				}/*else if(StringUtils.isEmpty(leaveOutFieldDept) && StringUtils.isEmpty(arriveOutFieldDept)){
					throw new MarkActivitiesException("出发外场和到达外场不能为空");
				}else if(StringUtils.isEmpty(arriveOutFieldDept)){
					throw new MarkActivitiesException("到达外场不能为空");
				}*/
			}
			if(leaveAreaFlag){
				//查询当前部门是否在活动的出发区域里面
				 long orgList = orgAdministrativeInfoService.queryDeptInfoByCode(leaveAreaList, leaveAreaCode);
				 if(orgList==0){
					 if(null==entity.getIsExpressFous()){
					 throw new MarkActivitiesException("出发区域："+this.queryDeptNameByCode(leaveAreaCode)+"不在活动"+entity.getName()+"" +
								"开展范围内");
					 }
				 }
			}
			if(arriveAreaFlag){
				//查询当前部门是否在活动的到达区域里面
				 long orgList = orgAdministrativeInfoService.queryDeptInfoByCode(arriveAreaList, arriveAreaCode);
				 if(orgList==0){
					 if(null==entity.getIsExpressFous()){
					 throw new MarkActivitiesException("达到区域："+this.queryDeptNameByCode(arriveAreaCode)+"不在活动"+entity.getName()+"" +
								"开展范围内");
					 }
				 }
			}
		}
		
		
		//枚举信息
		MarkMultipleEntity mulEntity = new MarkMultipleEntity();
		mulEntity.setActiviteCrmId(activityCrmId);
		//开单品名
		String goodsName = entity.getGoodsName();
		//订单来源
		String orderResource = entity.getOrderResource();
		//产品类型
	    String productType = entity.getProductType();
		//一级行业
	    String firstTrade = entity.getFirstTrade();
		//二级行业
	    String secondeTrade = entity.getSecondeTrade();
	    //枚举列表
	    List<MarkMultipleEntity> mulList = null;
	    
	    //开单品名(PDA不校验开单品名)	
	    if(!entity.isPda()){
			mulEntity.setEnumType(ComnConst.GOODS_NAME_MARK_ACTIVITY);
			mulEntity.setValueCode(null);
			mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
			if(CollectionUtils.isNotEmpty(mulList)){
				if(StringUtils.isNotEmpty(goodsName)){
					mulEntity.setValueCode(goodsName);
					mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
					if(CollectionUtils.isEmpty(mulList)){
						if(null==entity.getIsExpressFous()){
						throw new MarkActivitiesException("开单品名："+goodsName+"不在活动"+entity.getName()+"" +
								"开展范围内");
						}
					}
				}else{
					if(null==entity.getIsExpressFous()){
					throw new MarkActivitiesException("开单品名为空，不在活动"+entity.getName()+"" +
							"开展范围内");
					}
				}
				mulList.clear();
			}
	    }
		
		//订单来源	    
		mulEntity.setEnumType(ComnConst.ORDER_RESOURCE_MARK_ACTIVITY);
		mulEntity.setValueCode(null);
		mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
		if(CollectionUtils.isNotEmpty(mulList)){
			if(StringUtils.isNotEmpty(orderResource)){
				mulEntity.setValueCode(orderResource);
				mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
				if(CollectionUtils.isEmpty(mulList)){
					if(null==entity.getIsExpressFous()){
					throw new MarkActivitiesException("订单来源："+orderResource+"不在活动"+entity.getName()+"" +
							"开展范围内");
					}
				}
			}else{
				if(null==entity.getIsExpressFous()){
				throw new MarkActivitiesException("订单来源为空，不在活动"+entity.getName()+"" +
						"开展范围内");
				}
			}
			mulList.clear();
		}		
		
		//产品类型		
		mulEntity.setEnumType(ComnConst.PRODUCT_TYPE_MARK_ACTIVITY);
		mulEntity.setValueCode(null);
		mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
		if(CollectionUtils.isNotEmpty(mulList)){
			if(StringUtils.isNotEmpty(productType)){
				boolean isOk=false;
				for(MarkMultipleEntity markmult:mulList){
				  String valuecode=	markmult.getValueCode();
				  if(productType.equals(valuecode)){
					  isOk=true;
					  break;
				  }
				}
				//mulEntity.setValueCode(productType);
				//mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
				if(!isOk){
					String productName="";
					ProductEntity product=productService.getProductByCache(productType, null);
					if(product!=null){
						productName=product.getName();
					}
					if(null==entity.getIsExpressFous()){
					throw new MarkActivitiesException("产品类型："+productName+"不在活动"+entity.getName()+"" +"开展范围内");
					}
					  }
			}else{
				if(null==entity.getIsExpressFous()){
				throw new MarkActivitiesException("产品类型为空，不在活动"+entity.getName()+"" +
						"开展范围内");
				}
				}
			mulList.clear();
		}			
		
		//一级行业(PDA不校验行业)	
		if(!entity.isPda()){	    
			mulEntity.setEnumType(ComnConst.FIRST_TRADES_MARK_ACTIVITY);
			mulEntity.setValueCode(null);
			mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
			if(CollectionUtils.isNotEmpty(mulList)){
				if(StringUtils.isNotEmpty(firstTrade)){
					mulEntity.setValueCode(firstTrade);
					mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
					if(CollectionUtils.isEmpty(mulList)){
						if(null==entity.getIsExpressFous()){
						throw new MarkActivitiesException("一级行业："+firstTrade+"不在活动"+entity.getName()+"" +
								"开展范围内");
						}
					}
				}else{
					if(null==entity.getIsExpressFous()){
					throw new MarkActivitiesException("一级行业为空，不在活动"+entity.getName()+"" +
							"开展范围内");
					}
				}
				mulList.clear();
			}
		}
		
		//二级行业(PDA不校验行业)		
		if(!entity.isPda()){
			mulEntity.setEnumType(ComnConst.SECOND_TRADES_MARK_ACTIVITY);
			mulEntity.setValueCode(null);
			mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
			if(CollectionUtils.isNotEmpty(mulList)){
				if(StringUtils.isNotEmpty(secondeTrade)){
					mulEntity.setValueCode(secondeTrade);
					mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
					if(CollectionUtils.isEmpty(mulList)){
						if(null==entity.getIsExpressFous()){
						throw new MarkActivitiesException("二级行业："+secondeTrade+"不在活动"+entity.getName()+"" +
								"开展范围内");
						}
					}
				}else{
					if(null==entity.getIsExpressFous()){
					throw new MarkActivitiesException("二级行业为空，不在活动"+entity.getName()+"" +
							"开展范围内");
					}
					}
				mulList.clear();
			}
		}
		
		//折扣对象
		MarkOptionsEntity discountEntity = new MarkOptionsEntity();
		discountEntity.setActiveCrmId(activityCrmId);
		//折扣列表
		List<MarkOptionsEntity> discountList = null;
	    discountList = markOptionsService.queryMarkActivityOptions(discountEntity);
	    //如果等于空，则根据时间建模查询，适用于更改单
	    if(CollectionUtils.isEmpty(discountList)){
	    	discountList = markOptionsService.queryMarkActivityOptionsByBillTime(discountEntity,entity.getBilllingTime());
	    }	    
	    entity = this.convertMarkActivityEntityToDto(markActEntity);
		entity.setOptionList(discountList);
		return entity;
	}
	
	/**
	 * 根据不同的查询条件查询活动信息
	 * 满足PDA快递开单调用的，不含一二级行业的
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-24
	 *
	 */
	@Override
	public List<MarkActivitiesQueryConditionDto> queryMarkActivitiesNoIndurstryByCondition(
			MarkActivitiesQueryConditionDto entity) {
		if (null == entity) {
			throw new MarkActivitiesException("传入的活动Dto对象为空");
		}

		// 查询的活动优惠信息结果
		List<MarkActivitiesQueryConditionDto> activetyList = markActivitiesDao
				.queryMarkActivitiesByCondition(entity);
		if (CollectionUtils.isEmpty(activetyList)) {
			throw new MarkActivitiesException("根据传入的条件查询不到对应的活动信息");
		}
		//返回的活动列表
		List<MarkActivitiesQueryConditionDto> result=new ArrayList<MarkActivitiesQueryConditionDto>();		
		for (int i=0;i<activetyList.size();i++) {
			MarkActivitiesQueryConditionDto dto=activetyList.get(i);
			// 枚举信息
			MarkMultipleEntity mulEntity = new MarkMultipleEntity();
			// 一级行业枚举列表
			List<MarkMultipleEntity> firstTradeList = null;
			// 二级行业枚举列表
			List<MarkMultipleEntity> secondTradeList = null;
			// 活动crmId
			BigDecimal activeCrmId = dto.getActiveCrmId();
			mulEntity.setActiviteCrmId(activeCrmId);

			// 一级行业
			mulEntity.setEnumType(ComnConst.FIRST_TRADES_MARK_ACTIVITY);
			firstTradeList = markMultipleService
					.queryMarkActivityMultiplie(mulEntity);

			// 二级行业
			mulEntity.setEnumType(ComnConst.SECOND_TRADES_MARK_ACTIVITY);
			secondTradeList = markMultipleService
					.queryMarkActivityMultiplie(mulEntity);

			//如果不包含一级行业和二级行业，则添加到活动列表中
			if (CollectionUtils.isEmpty(firstTradeList) && CollectionUtils.isEmpty(secondTradeList)) {
				addGoodName(dto);
				result.add(dto);
			}
		}
		return result;
	}
	
	/**
	 * 将活动信息封装到DTO里
	 *
	 * auther:wangpeng_078816
	 * date:2014-5-19
	 *
	 */
	private MarkActivitiesQueryConditionDto convertMarkActivityEntityToDto(MarkActivitiesEntity entity){
		MarkActivitiesQueryConditionDto dto = new MarkActivitiesQueryConditionDto();
		dto.setActive(entity.getActive());
		dto.setActiveCrmId(entity.getActiveCrmId());
		dto.setActiveStartTime(entity.getActiveStartTime());
		dto.setActiveEndTime(entity.getActiveEndTime());
		dto.setActivityCategory(entity.getActivityCategory());
		dto.setActivityType(entity.getActivityType());
		dto.setCode(entity.getCode());
		dto.setId(entity.getId());
		dto.setMaxCargoAmount(entity.getMaxCargoAmount());
		dto.setMaxCargoVolume(entity.getMaxCargoVolume());
		dto.setMaxCargoWeight(entity.getMaxCargoWeight());
		dto.setMinCargoAmount(entity.getMinCargoAmount());
		dto.setMinCargoVolume(entity.getMinCargoVolume());
		dto.setMinCargoWeight(entity.getMinCargoWeight());
		dto.setName(entity.getName());
		return dto;
	}
	
	/**
	 * 根据开单界面传递过来的条件查询对应的折扣信息
	 *
	 *该方法只适合于快递自动补录调用：：：不适合其他地方调用
	 * author：220125  cellphone：18721080868
	 * date:2015-10-13
	 *
	 */
	@Override
	public MarkActivitiesQueryConditionDto queryMarkActivitiesInfoByConditionExpressAuto
	(MarkActivitiesQueryConditionDto entity) {
		if(null == entity){
			throw new WaybillValidateException("传入的活动Dto对象为空");
		}
		//获取活动编码
		String activityCode = entity.getCode();
		if(StringUtils.isEmpty(activityCode)){
			throw new WaybillValidateException("传入的活动编码为空");
		}
		//根据活动编码去获取活动对象
		 MarkActivitiesEntity markActEntity = this.queryMarkActivitiesByCode(activityCode);
		 //根据活动编码与开单时间获取活动对象（根据时间建模，适用于更改单）
		 if(null == markActEntity){
			 markActEntity = this.queryMarkActivitiesByCodeAndBilltime(activityCode,entity.getBilllingTime());
		 }		 
		 if(null == markActEntity){
				throw new WaybillValidateException("根据传入的活动编码查询不到对应的活动信息");
			}else{
				if(null == markActEntity.getActiveStartTime()&& null == markActEntity.getActiveEndTime()){
					entity.setBilllingTime(null);
				}
				
				if(StringUtils.isEmpty(markActEntity.getActivityCategory())){
					entity.setActivityCategory(null);
				}
				
				if(StringUtils.isEmpty(markActEntity.getActivityType())){
					entity.setActivityType(null);
				}
				
				if(null == markActEntity.getMaxCargoAmount() && null == markActEntity.getMinCargoAmount()){
					entity.setBilllingAmount(null);
				}
				
				if(null == markActEntity.getMaxCargoVolume() && null == markActEntity.getMinCargoVolume()){
					entity.setBilllingVolumn(null);
				}
				
				if(null == markActEntity.getMaxCargoWeight() && null == markActEntity.getMinCargoWeight()){
					entity.setBilllingWeight(null);
				}
				
				if(StringUtils.isEmpty(markActEntity.getName())){
					entity.setName(null);
				}
			}
		 //活动编码
		 String activeName =  markActEntity.getName();
		 entity.setName(activeName);
		 
		//活动的crmId
		BigDecimal activityCrmId = markActEntity.getActiveCrmId();
		
		//======此处原来就是注释掉的
		//查询的活动优惠信息结果
//		List<MarkActivitiesQueryConditionDto> activetyList = markActivitiesDao.queryMarkActivitiesByCondition(entity);
//
//		if(CollectionUtils.isEmpty(activetyList)){
//			throw new MarkActivitiesException("该运单不符合所选活动的参与条件！");
//		}
//		//活动开展部门对象
//		MarkActivitiesDeptEntity deptEntity = new MarkActivitiesDeptEntity();
//		String developDeptCode = entity.getDevelopDeptCode();
//		if(StringUtils.isEmpty(developDeptCode)){
//			throw new MarkActivitiesException("活动开展部门不能为空");
//		}
//		deptEntity.setActiviteCrmId(activityCrmId);
//		//根据活动的crmId查询出来该活动的所有的开展部门
//	    List<MarkActivitiesDeptEntity> deptList = markActivitiesDeptService.queryMarkActivitiesDept(deptEntity);
//	    //根据活动的crmId与开单时间查询出来该活动的所有的开展部门（根据时间建模，适用于更改单）
//		if(CollectionUtils.isEmpty(deptList)){
//			deptList = markActivitiesDeptService.queryMarkActivitiesDeptByBillTime(deptEntity,entity.getBilllingTime());
//		}
//		if(CollectionUtils.isEmpty(deptList)){
//			throw new MarkActivitiesException("活动:"+entity.getName()+"" +
//					"不存在开展部门");
//		}else{
//			//讲当前活动的开展部门封装到一个list里面
//			 List<String> codes = new ArrayList<String>();
//			 for (MarkActivitiesDeptEntity obj : deptList) {
//				 if(StringUtils.isNotEmpty(obj.getOrgCode())){
//					 codes.add(obj.getOrgCode());
//				 }
//			}
//			 //查询当前部门是否在活动的开展部门里面
//			 long orgList = orgAdministrativeInfoService.queryDeptInfoByCode(codes, developDeptCode);
//			 if(orgList==0){
//				 throw new MarkActivitiesException("部门："+this.queryDeptNameByCode(developDeptCode)+"不在活动"+entity.getName()+"" +
//							"开展范围内");
//			 }
//		}
		//======此处原来就是注释掉的
		
		//线路部门对象
		MarkLineDeptEntity lineEntity = new MarkLineDeptEntity();
		lineEntity.setActiviteCrmId(activityCrmId);
		List<MarkLineDeptEntity> lineList = markLineDeptService.queryMarkActivityLineDept(lineEntity);
		if(CollectionUtils.isNotEmpty(lineList)){
			//用来标记活动的出发外场是否存在
			boolean leaveOutFlag = false;
			//标记活动的到达外场是否存在
			boolean arriveOutFlag = false;
			//标记活动的出发区域是否存在
			boolean leaveAreaFlag = false;
			//标记活动的到达区域是否存在
			boolean arriveAreaFlag = false;
			
			//存放出发区域的list
			List<String> leaveAreaList = new ArrayList<String>();
			//存放到达区域的list
			List<String> arriveAreaList = new ArrayList<String>();
			
			for (MarkLineDeptEntity lineDeptEntity : lineList) {
				if(StringUtils.isNotEmpty(lineDeptEntity.getOrigOrgCode()) && !leaveOutFlag){
					leaveOutFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getDestOrgCode()) && !arriveOutFlag){
					arriveOutFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getLeaveAreaCode()) && !leaveAreaFlag){
					leaveAreaFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getArriveAreaCode()) && !arriveAreaFlag){
					arriveAreaFlag = true;
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getLeaveAreaCode())){
					
					leaveAreaList.add(lineDeptEntity.getLeaveAreaCode());
				}
				if(StringUtils.isNotEmpty(lineDeptEntity.getArriveAreaCode())){
					arriveAreaList.add(lineDeptEntity.getArriveAreaCode());
				}
			}
			
			//获取线路的出发外场
			String leaveOutFieldDept = entity.getStartOutFieldCode();
			//获取线路的到达外场
			String arriveOutFieldDept = entity.getArriveOutFieldCode();
			//出发区域
			String leaveAreaCode = entity.getLeaveAreaCode();
			//到达区域
			String arriveAreaCode = entity.getArriveAreaCode();
			
			//wp_078816_20140611 修改 去掉活动始发外场和到达外场必须同时存在的限制，改为二者至少有一个存在即可
			if(leaveOutFlag || arriveOutFlag){
				
				if(!StringUtils.isEmpty(leaveOutFieldDept)|| !StringUtils.isEmpty(arriveOutFieldDept)){
					
					if(StringUtils.isNotEmpty(leaveOutFieldDept)){
						
						lineEntity.setOrigOrgCode(leaveOutFieldDept);
					}
					if(StringUtils.isNotEmpty(arriveOutFieldDept)){
						
						lineEntity.setDestOrgCode(arriveOutFieldDept);
					}
					lineList = markLineDeptService.queryMarkActivityLineDept(lineEntity);
					if(CollectionUtils.isEmpty(lineList)){
						throw new WaybillValidateException("线路："+this.queryDeptNameByCode(leaveOutFieldDept)+
								"——"+this.queryDeptNameByCode(arriveOutFieldDept)+"不在活动"+entity.getName()+"" +
								"开展范围内");
					}
				}
			
			if(leaveAreaFlag){
				//查询当前部门是否在活动的出发区域里面
				 long orgList = orgAdministrativeInfoService.queryDeptInfoByCode(leaveAreaList, leaveAreaCode);
				 if(orgList==0){
					 throw new WaybillValidateException("出发区域："+this.queryDeptNameByCode(leaveAreaCode)+"不在活动"+entity.getName()+"" +
								"开展范围内");
				 }
			}
			if(arriveAreaFlag){
				//查询当前部门是否在活动的到达区域里面
				 long orgList = orgAdministrativeInfoService.queryDeptInfoByCode(arriveAreaList, arriveAreaCode);
				 if(orgList==0){
					 throw new WaybillValidateException("达到区域："+this.queryDeptNameByCode(arriveAreaCode)+"不在活动"+entity.getName()+"" +
								"开展范围内");
				 }
			}
		}
		
		
		//枚举信息
		MarkMultipleEntity mulEntity = new MarkMultipleEntity();
		mulEntity.setActiviteCrmId(activityCrmId);
		//开单品名
	//	String goodsName = entity.getGoodsName();
		//订单来源
		String orderResource = entity.getOrderResource();
		//产品类型
	    String productType = entity.getProductType();
		//一级行业
	    String firstTrade = entity.getFirstTrade();
		//二级行业
	    String secondeTrade = entity.getSecondeTrade();
	    //枚举列表
	    List<MarkMultipleEntity> mulList = null;
	    
	    //开单品名(PDA不校验开单品名)	
//	    if(!entity.isPda()){	    
//			mulEntity.setEnumType(ComnConst.GOODS_NAME_MARK_ACTIVITY);
//			mulEntity.setValueCode(null);
//			mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
//			if(CollectionUtils.isNotEmpty(mulList)){
//				if(StringUtils.isNotEmpty(goodsName)){
//					mulEntity.setValueCode(goodsName);
//					mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
//					if(CollectionUtils.isEmpty(mulList)){
//						throw new MarkActivitiesException("开单品名："+goodsName+"不在活动"+entity.getName()+"" +
//								"开展范围内");
//					}
//				}else{
//					throw new MarkActivitiesException("开单品名为空，不在活动"+entity.getName()+"" +
//							"开展范围内");
//				}
//				mulList.clear();
//			}
//	    }
		
		//订单来源	    
		mulEntity.setEnumType(ComnConst.ORDER_RESOURCE_MARK_ACTIVITY);
		mulEntity.setValueCode(null);
		mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
		if(CollectionUtils.isNotEmpty(mulList)){
			if(StringUtils.isNotEmpty(orderResource)){
				mulEntity.setValueCode(orderResource);
				mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
				if(CollectionUtils.isEmpty(mulList)){
					throw new WaybillValidateException("订单来源："+orderResource+"不在活动"+entity.getName()+"" +
							"开展范围内");
				}
			}else{
				throw new WaybillValidateException("订单来源为空，不在活动"+entity.getName()+"" +
						"开展范围内");
			}
			mulList.clear();
		}		
		
		//产品类型		
		mulEntity.setEnumType(ComnConst.PRODUCT_TYPE_MARK_ACTIVITY);
		mulEntity.setValueCode(null);
		mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
		if(CollectionUtils.isNotEmpty(mulList)){
			if(StringUtils.isNotEmpty(productType)){
				boolean isOk=false;
				for(MarkMultipleEntity markmult:mulList){
				  String valuecode=	markmult.getValueCode();
				  if(productType.equals(valuecode)){
					  isOk=true;
					  break;
				  }
				}
				if(!isOk){
					String productName="";
					ProductEntity product=productService.getProductByCache(productType, null);
					if(product!=null){
						productName=product.getName();
					}
					throw new WaybillValidateException("产品类型："+productName+"不在活动"+entity.getName()+"" +"开展范围内");
				}
			}else{
				throw new WaybillValidateException("产品类型为空，不在活动"+entity.getName()+"" +
						"开展范围内");
			}
			mulList.clear();
		}			
		
		//一级行业(PDA不校验行业)	
		if(!entity.isPda()){	    
			mulEntity.setEnumType(ComnConst.FIRST_TRADES_MARK_ACTIVITY);
			mulEntity.setValueCode(null);
			mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
			if(CollectionUtils.isNotEmpty(mulList)){
				if(StringUtils.isNotEmpty(firstTrade)){
					mulEntity.setValueCode(firstTrade);
					mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
					if(CollectionUtils.isEmpty(mulList)){
						throw new WaybillValidateException("一级行业："+firstTrade+"不在活动"+entity.getName()+"" +
								"开展范围内");
					}
				}else{
					throw new WaybillValidateException("一级行业为空，不在活动"+entity.getName()+"" +
							"开展范围内");
				}
				mulList.clear();
			}
		}
		
		//二级行业(PDA不校验行业)		
		if(!entity.isPda()){	    
			mulEntity.setEnumType(ComnConst.SECOND_TRADES_MARK_ACTIVITY);
			mulEntity.setValueCode(null);
			mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
			if(CollectionUtils.isNotEmpty(mulList)){
				if(StringUtils.isNotEmpty(secondeTrade)){
					mulEntity.setValueCode(secondeTrade);
					mulList = markMultipleService.queryMarkActivityMultiplie(mulEntity);
					if(CollectionUtils.isEmpty(mulList)){
						throw new WaybillValidateException("二级行业："+secondeTrade+"不在活动"+entity.getName()+"" +
								"开展范围内");
					}
				}else{
					throw new WaybillValidateException("二级行业为空，不在活动"+entity.getName()+"" +
							"开展范围内");
				}
				mulList.clear();
			}	
		  }
		}
		
		//折扣对象
		MarkOptionsEntity discountEntity = new MarkOptionsEntity();
		discountEntity.setActiveCrmId(activityCrmId);
		//折扣列表
		List<MarkOptionsEntity> discountList = null;
	    discountList = markOptionsService.queryMarkActivityOptions(discountEntity);
	    //如果等于空，则根据时间建模查询，适用于更改单
	    if(CollectionUtils.isEmpty(discountList)){
	    	discountList = markOptionsService.queryMarkActivityOptionsByBillTime(discountEntity,entity.getBilllingTime());
	    }	    
	    entity = this.convertMarkActivityEntityToDto(markActEntity);
		entity.setOptionList(discountList);
		return entity;
	}
}
