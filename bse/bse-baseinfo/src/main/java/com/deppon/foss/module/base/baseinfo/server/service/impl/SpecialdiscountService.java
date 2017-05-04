package com.deppon.foss.module.base.baseinfo.server.service.impl;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialdiscountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialdiscountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialdiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SpecialdiscountDto;

public class SpecialdiscountService implements ISpecialdiscountService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialdiscountService.class);
	
    /**
     * 
     */
    private static final String USER_CODE = "CRM";	

	private ISpecialdiscountDao specialdiscountDao;

	public void setSpecialdiscountDao(ISpecialdiscountDao specialdiscountDao) {
		this.specialdiscountDao = specialdiscountDao;
	}

	@Override
	public int insert(SpecialdiscountEntity entity) {			
		LOGGER.debug("id: " + entity.getId());		
		return specialdiscountDao.insert(entity);
	}

	@Override
	public int updateBySpecialdiscount(SpecialdiscountEntity entity) {		
		LOGGER.debug("id: " + entity.getId());		
		return specialdiscountDao.updateBySpecialdiscount(entity);
	}

	@Override
	public SpecialdiscountEntity selectByPrimaryKey(SpecialdiscountEntity specialdiscount) {		
		return specialdiscountDao.selectByPrimaryKey(specialdiscount);
	}
	
	/**
	 * 根据传入的客户编码，与传入当前时间查询符合条件的实体信息
	 * 
	 * @param entity
	 * @return
	 * @author css-261997
	 * @date 2015-08-14 上午15:36:32
	 */
	@Override
	public SpecialdiscountEntity selectBySpecialdiscountYX(String code) {	
		return specialdiscountDao.selectBySpecialdiscountYX(code);
	}
	
	@Override
	public SpecialdiscountEntity selectBySpecialdiscountTime(String code,Date createtime) {	
		return specialdiscountDao.selectBySpecialdiscountTime(code, createtime);
	}
	
	@Override
	public int syncInfo(
			SpecialdiscountDto specialdiscountRequestEntity) {
		SpecialdiscountEntity entity = convertSpecialdiscount(specialdiscountRequestEntity);		
		SpecialdiscountEntity specialdiscount = this.selectByPrimaryKey(entity);
		int returnnum = 0;
		if (null==specialdiscount) {
			returnnum = this.insert(entity);
		}else{
			returnnum = this.updateBySpecialdiscount(entity);
		}
		return returnnum;
	}
	
	/**
     * <p>
     * 把CRM同步过来的特惠组信息转化为FOSS特惠组信息实体
     * </p>.
     *
     * @param obj 
     * @return 
     * @author 261997-foss-css
     * @date 2015-07-29 上午09:29:17
     * @see
     */
    private SpecialdiscountEntity convertSpecialdiscount(SpecialdiscountDto obj) {
    	SpecialdiscountEntity entity = new SpecialdiscountEntity();
    	entity.setCode(obj.getCustCode());
    	entity.setCreater(USER_CODE);
    	entity.setCreatetime(new Date());
    	entity.setProductcode(obj.getSpecialGroupCode());
    	entity.setProducttype(obj.getSpecialGroupName());
    	entity.setProposetime(obj.getProposeTime());
    	entity.setQuittime(obj.getQuitTime());   
    	entity.setCrmId(obj.getCrmId());
    	entity.setType(obj.getProductType());
    	entity.setActive(obj.getActive().trim());
    	return entity;
    }
    
    @Override
	public boolean selectBySpecialdiscountKHFQ(String code,Date createtime) {	
		return specialdiscountDao.selectBySpecialdiscountKHFQ(code, createtime);
	}   
    

}
