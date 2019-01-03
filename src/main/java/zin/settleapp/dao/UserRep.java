package zin.settleapp.dao;

import org.springframework.data.repository.CrudRepository;

import zin.settleapp.bo.User;

public interface UserRep extends CrudRepository<User, String> {

}
