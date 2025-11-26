package com.aitsaid.tp19.clientservice.repositories;

import com.aitsaid.tp19.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author radouane
 **/
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
