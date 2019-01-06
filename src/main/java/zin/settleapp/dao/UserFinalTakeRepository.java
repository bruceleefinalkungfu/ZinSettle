package zin.settleapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import zin.settleapp.bo.UserFinalTake;

public interface UserFinalTakeRepository extends CrudRepository<UserFinalTake, String> {

	public List<UserFinalTake> findByUserTakingMoney(String uId);
	
}
