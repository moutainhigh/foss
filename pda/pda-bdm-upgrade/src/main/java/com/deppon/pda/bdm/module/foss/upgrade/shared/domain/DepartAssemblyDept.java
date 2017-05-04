package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

public class DepartAssemblyDept {
	private String id;
    //始发ID
	private String departdeptid;
    //配载ID
	private String assemblydeptid;
    //始发CODE
	private String departdeptcode;
    //配载CODE
	private String assemblydeptcode;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDepartdeptid() {
        return departdeptid;
    }
    public void setDepartdeptid(String departdeptid) {
        this.departdeptid = departdeptid;
    }
    public String getAssemblydeptid() {
        return assemblydeptid;
    }
    public void setAssemblydeptid(String assemblydeptid) {
        this.assemblydeptid = assemblydeptid;
    }
    public String getDepartdeptcode() {
        return departdeptcode;
    }
    public void setDepartdeptcode(String departdeptcode) {
        this.departdeptcode = departdeptcode;
    }
    public String getAssemblydeptcode() {
        return assemblydeptcode;
    }
    public void setAssemblydeptcode(String assemblydeptcode) {
        this.assemblydeptcode = assemblydeptcode;
    }
}
