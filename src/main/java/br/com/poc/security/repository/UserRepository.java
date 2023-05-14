package br.com.poc.security.repository;

import br.com.poc.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
