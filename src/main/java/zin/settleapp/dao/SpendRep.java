package zin.settleapp.dao;

import org.springframework.data.repository.CrudRepository;

import zin.settleapp.bo.Spend;

public interface SpendRep extends CrudRepository<Spend, String> {

}
