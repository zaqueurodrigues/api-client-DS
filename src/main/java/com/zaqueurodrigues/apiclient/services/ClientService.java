package com.zaqueurodrigues.apiclient.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaqueurodrigues.apiclient.dto.ClientDTO;
import com.zaqueurodrigues.apiclient.entities.Client;
import com.zaqueurodrigues.apiclient.repositories.ClientRepository;
import com.zaqueurodrigues.apiclient.services.exceptions.DatabaseException;
import com.zaqueurodrigues.apiclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest).map(ClientDTO::new);
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		return repository.findById(id).map(ClientDTO::new)
				.orElseThrow(() -> new ResourceNotFoundException("Client not exists"));
	}

	@Transactional(readOnly = true)
	public ClientDTO findByCpf(String cpf) {
		try {
			Client entity = repository.findByCpf(cpf);
			return new ClientDTO(entity);
		} catch (Exception e) {
			throw new ResourceNotFoundException("CPF not exists: " + cpf);
		}
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthData(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

}
