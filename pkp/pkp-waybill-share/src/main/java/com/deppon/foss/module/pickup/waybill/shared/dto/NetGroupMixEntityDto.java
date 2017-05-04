/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.entity.IEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class NetGroupMixEntityDto implements Serializable, IEntity, Comparable<IEntity>{

	
	private static final long serialVersionUID = 1372509362360011358L;

	private String id;// ID
	private Date createDate;
	private String createUser;
	private Date modifyDate;
	private String modifyUser;

	/**
     * 版本号
     */
    private Long version;

    
    /**
     * 虚拟编码
     */
    private String virtualCode;

    /**
     * 走货路径虚拟编码
     */
    private String freightRouteVirtualCode;

    /**
     * 网点组名称
     */
    private String netGroupCode;

    /**
     * 营业部部门编码
     */
    private String orgCode;

    /**
     * 营业部类型，出发或到达
     */
    private String orgType;

    /**
     * 是否有效
     */
    private String active;

    /**
     * 
     * <p>判断是否出发营业部</p> 
     * @author foss-zhujunyong
     * @date Jun 17, 2013 10:29:36 AM
     * @return
     * @see
     */
    public boolean checkSource() {
	return StringUtils.equals(orgType, ComnConst.ORG_TYPE_SOURCE);
    }
    
    /**
     * 
     * <p>判断是否到达营业部</p> 
     * @author foss-zhujunyong
     * @date Jun 17, 2013 10:30:12 AM
     * @return
     * @see
     */
    public boolean checkTarget() {
	return StringUtils.equals(orgType, ComnConst.ORG_TYPE_TARGET);
    }
    
    /**
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    
    /**
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    
    /**
     * @return  the freightRouteVirtualCode
     */
    public String getFreightRouteVirtualCode() {
        return freightRouteVirtualCode;
    }

    
    /**
     * @param freightRouteVirtualCode the freightRouteVirtualCode to set
     */
    public void setFreightRouteVirtualCode(String freightRouteVirtualCode) {
        this.freightRouteVirtualCode = freightRouteVirtualCode;
    }

    
    /**
     * @return  the netGroupCode
     */
    public String getNetGroupCode() {
        return netGroupCode;
    }

    
    /**
     * @param netGroupCode the netGroupCode to set
     */
    public void setNetGroupCode(String netGroupCode) {
        this.netGroupCode = netGroupCode;
    }

    
    /**
     * @return  the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    
    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    
    /**
     * @return  the orgType
     */
    public String getOrgType() {
        return orgType;
    }

    
    /**
     * @param orgType the orgType to set
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    
    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 13, 2013 9:48:47 AM
     * @return
     * @see
     */
    public Long getVersion() {
	return version;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 13, 2013 9:48:54 AM
     * @param version
     * @see
     */
    public void setVersion(Long version) {
	this.version = version;
    }

	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		if (IEntity.class.isAssignableFrom((obj.getClass()))) {
			final BaseEntity other = (BaseEntity) obj;
			if (id == null) {
				if (other.getId() != null) {
					return false;
				}
			} else if (!id.equals(other.getId())) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(IEntity o) {
		if (null == o) {
			return 0;
		}
		return (o.equals(this) ? 0 : 1);
	}
}
