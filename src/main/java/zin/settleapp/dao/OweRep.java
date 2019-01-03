package zin.settleapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import zin.settleapp.bo.Owe;

public interface OweRep extends CrudRepository<Owe, String> {

	List<Owe> findByOwedUserId(String userId);
	
	List<Owe> findByUserGettingMoneyId(String userId);
	
}
