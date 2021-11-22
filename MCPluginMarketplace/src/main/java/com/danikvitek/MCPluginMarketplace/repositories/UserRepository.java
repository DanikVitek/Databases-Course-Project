package com.danikvitek.MCPluginMarketplace.repositories;

import com.danikvitek.MCPluginMarketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
