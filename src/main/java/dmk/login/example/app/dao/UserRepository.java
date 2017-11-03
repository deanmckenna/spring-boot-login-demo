package dmk.login.example.app.dao;

import dmk.login.example.app.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface UserRepository extends CrudRepository<User, Long>{

    User findByName(String name);
}
