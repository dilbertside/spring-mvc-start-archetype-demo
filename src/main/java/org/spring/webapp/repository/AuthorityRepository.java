package org.spring.webapp.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.spring.webapp.entity.Authority;


/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
  
  Optional<Authority> findOneByName(String name);
}
