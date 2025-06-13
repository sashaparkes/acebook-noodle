package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT a FROM User a WHERE " +
            "LOWER(CONCAT('%', a.firstName, ' ', a.lastName, '%')) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CONCAT('%', a.lastName, ' ', a.firstName, '%')) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CONCAT('%', a.lastName, a.firstName, '%')) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CONCAT('%', a.firstName, a.lastName, '%')) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CONCAT('%', a.username, '%')) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    public List<User> findUsersBySearchInput(@Param("searchInput") String searchInput);
    public Optional<User> findUserByUsername(String username);
    public List<User> findUsersByFirstNameAndLastName(String firstName, String lastName);
}
