
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;

public class JobGridClusterEntity
    implements Serializable
{

    public JobGridClusterEntity()
    {
    }

    public String getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    public int getScopeType()
    {
        return scopeType;
    }

    public void setScopeType(int scopeType)
    {
        this.scopeType = scopeType;
    }

    public String getScopeName()
    {
        return scopeName;
    }

    public void setScopeName(String scopeName)
    {
        this.scopeName = scopeName;
    }

    public int getAccessRule()
    {
        return accessRule;
    }

    public void setAccessRule(int accessRule)
    {
        this.accessRule = accessRule;
    }

    public String getScopeTypeName()
    {
        return scopeTypeName;
    }

    public void setScopeTypeName(String scopeTypeName)
    {
        this.scopeTypeName = scopeTypeName;
    }

    private static final long serialVersionUID = 1L;
    private String instanceId;
    private int scopeType;
    private String scopeName;
    private int accessRule;
    private String scopeTypeName;
    private String blongModule;//分组所属模块
	public String getBlongModule() {
		return blongModule;
	}

	public void setBlongModule(String blongModule) {
		this.blongModule = blongModule;
	}
}


