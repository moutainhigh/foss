package com.deppon.foss.deploy;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Artifact implements Comparable {
	private String groupId;
	private String artifactId;
	private String versionId;
	private String type;
	private String scope;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(groupId).append(artifactId)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Artifact == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Artifact rhs = (Artifact) obj;
		return new EqualsBuilder()
				.append(groupId, rhs.groupId)
				.append(artifactId, rhs.artifactId)
				.isEquals();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int compareTo(Object o) {
	     Artifact a = (Artifact) o;
	     
	     return new CompareToBuilder()
	       .append(this.artifactId, a.artifactId)
	       .toComparison();
	}
}
