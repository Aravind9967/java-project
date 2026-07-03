package com.BusinessPurpose.OrderedItems.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BusinessPurpose.OrderedItems.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);
	Optional<User> findByUserName(String userName);
	Optional<User> findByEmail(String email);
	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);

}
