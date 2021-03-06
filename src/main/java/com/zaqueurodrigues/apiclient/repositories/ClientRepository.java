package com.zaqueurodrigues.apiclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zaqueurodrigues.apiclient.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

	Client findByCpf(String cpf);
}
