package org.training360.finalexam.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "select t from Team t order by t.name")
    Collection<Team> listAllTeams();

}
