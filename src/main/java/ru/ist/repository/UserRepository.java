package ru.ist.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph("UserWithEmployee")
	User findByName(String name);

}
