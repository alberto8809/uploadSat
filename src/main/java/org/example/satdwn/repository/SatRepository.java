package org.example.satdwn.repository;

import org.example.satdwn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SatRepository extends JpaRepository<User, Long> {


    @Query(value = "SELECT * FROM dbmaster.user cc WHERE cc.token =:token", nativeQuery = true)
    User getUserByToken(String token);

}
