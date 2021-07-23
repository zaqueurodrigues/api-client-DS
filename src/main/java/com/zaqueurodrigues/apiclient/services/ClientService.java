package com.zaqueurodrigues.apiclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaqueurodrigues.apiclient.dto.ClientDTO;
import com.zaqueurodrigues.apiclient.repositories.ClientRepository;
import com.zaqueurodrigues.apiclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest){
		return repository.findAll(pageRequest).map(ClientDTO::new);
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		return repository.findById(id).map(ClientDTO::new).orElseThrow(() -> new ResourceNotFoundException("Client not exists"));
	}

}
