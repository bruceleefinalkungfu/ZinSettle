package zin.settleapp.bo;

import java.util.UUID;

public class Group {

	public String name;
	public String groupId = UUID.randomUUID().toString();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
