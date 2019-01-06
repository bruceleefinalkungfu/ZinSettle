package zin.settleapp.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table()
public class User {
	@Column
	public String name;
	@Id
	@Column
	public String userId = UUID.randomUUID().toString();

	@Transient
	private Map<String, Double> oweToMap = new HashMap<>();
	@Transient
	private Map<String, Double> takeFromMap = new HashMap<>();
	
	public Map<String, Double> getOweToMap() {
		return oweToMap;
	}
	public void setOweToMap(Map<String, Double> oweToMap) {
		this.oweToMap = oweToMap;
	}
	public Map<String, Double> getTakeFromMap() {
		return takeFromMap;
	}
	public void setTakeFromMap(Map<String, Double> takeFromMap) {
		this.takeFromMap = takeFromMap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String toString() {
		
		StringBuilder sb = new StringBuilder(this.name+": {");
		takeFromMap.forEach( (k, v) -> sb.append(this.name+" takes "+v+" from "+k));
		return sb.append("}").toString();
	}
}
