package com.RahulNaikB.Library_Management.Repository;

import com.RahulNaikB.Library_Management.Entity.Book;
import com.RahulNaikB.Library_Management.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{

    Optional<User> findByUserName(String username);

}
