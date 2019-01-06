package zin.settleapp.bo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserFinalTake {

	@Id
	String id = UUID.randomUUID().toString();
	
	@Column
	String userTakingMoney;
	
	@Column
	String userGivingMoney;
	
	@Column
	double amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserTakingMoney() {
		return userTakingMoney;
	}

	public void setUserTakingMoney(String userTakingMoney) {
		this.userTakingMoney = userTakingMoney;
	}

	public String getUserGivingMoney() {
		return userGivingMoney;
	}

	public void setUserGivingMoney(String userGivingMoney) {
		this.userGivingMoney = userGivingMoney;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
