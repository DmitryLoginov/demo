package dev.ldv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ldv.api.model.*;
import dev.ldv.dto.ClientFilter;
import dev.ldv.exception.ApiError;
import dev.ldv.exception.ApiException;
import dev.ldv.mapper.ClientMapper;
import dev.ldv.model.Client;
import dev.ldv.model.ClientSpecification;
import dev.ldv.repository.AccountRepository;
import dev.ldv.repository.ClientRepository;

import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Override
    public ClientDto create(NewClientRequest newClientRequest) {
        log.debug("\nNew client request:\n{}", newClientRequest);

        if (clientRepository.findByMdmCode(newClientRequest.getMdmCode()).isPresent()) {
            log.warn("Client with mdmCode {} already exists", newClientRequest.getMdmCode());
            throw new ApiException(ApiError.CONFLICT, "Client with given mdmCode already exists");
        }

        Client newClient = ClientMapper.fromNewClientRequest(newClientRequest);
        newClient.setStatus(ClientStatus.ACTIVE);

        newClient = clientRepository.saveAndFlush(newClient);
        log.info("\nNew client saved:\n{}", newClient);

        return ClientMapper.toClientDto(newClient);
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDto getById(UUID id) {
        log.debug("Get client by id: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Client with id {} not found", id);
                    return new ApiException(ApiError.NOT_FOUND, "Client with given id not found");
                });

        log.debug("\nFound client by id:\n{}", client);

        return ClientMapper.toClientDto(client);
    }

    @Transactional(readOnly = true)
    @Override
    public ClientPageResponse getPage(ClientFilter clientFilter) {
        log.debug("\nGet client page:\n{}", clientFilter);

        Specification<Client> specification = ClientSpecification.withFilter(clientFilter);
        Pageable pageable = PageRequest.of(clientFilter.getPage(), clientFilter.getSize());

        Page<Client> clients = clientRepository.findAll(specification, pageable);
        log.debug("Page elements: {}", clients.getNumberOfElements());
        log.debug("Total elements: {}", clients.getTotalElements());
        log.debug("Total pages: {}", clients.getTotalPages());

        return toPageResponse(clients);
    }

    @Override
    public ClientDto update(UUID id, UpdateClientRequest updateClientRequest) {
        log.debug("\nUpdate client request by id {}:\n{}", id, updateClientRequest);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Client with id {} not found", id);
                    return new ApiException(ApiError.NOT_FOUND, "Client with given id not found");
                });

        log.debug("\nFound client by id:\n{}", client);

        ClientMapper.updateFields(client, updateClientRequest);
        clientRepository.flush();

        log.info("\nUpdated client by id:\n{}", client);

        return ClientMapper.toClientDto(client);
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Delete client by id: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Client with id {} not found", id);
                    return new ApiException(ApiError.NOT_FOUND, "Client with given id not found");
                });

        if (accountRepository.existsByClientIdAndStatusName(id, "CREATED")) {
            log.warn("Client has active accounts");
            throw new ApiException(ApiError.CONFLICT, "Client has active accounts");
        }

        log.debug("\nFound client by id:\n{}", client);

        client.setStatus(ClientStatus.DELETED);
        log.info("\nDeleted client by id:\n{}", client);

        clientRepository.flush();
    }

    @Transactional(readOnly = true)
    public ClientExistenceDto exists(UUID id) {
        log.debug("Exists client by id: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Client with id {} not found", id);
                    return new ApiException(ApiError.NOT_FOUND, "Client with given id not found");
                });

        log.debug("\nFound client by id:\n{}", client);

        return new ClientExistenceDto(true, id, client.getStatus());
    }

    private ClientPageResponse toPageResponse(Page<Client> clients) {
        ClientPageResponse pageResponse = new ClientPageResponse();
        PageableObject pageableObject = new PageableObject();

        pageableObject.setPageNumber(clients.getNumber());
        pageableObject.setPageSize(clients.getSize());
        pageableObject.setTotalPages(clients.getTotalPages());
        pageableObject.setTotalElements(clients.getNumberOfElements());

        pageResponse.setPageable(pageableObject);
        pageResponse.setContent(clients.getContent().stream()
                .map(ClientMapper::toClientShortDto)
                .toList());

        return pageResponse;
    }
}
