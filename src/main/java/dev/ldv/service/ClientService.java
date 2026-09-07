package dev.ldv.service;

import dev.ldv.api.model.*;
import dev.ldv.dto.ClientFilter;

import java.util.UUID;

public interface ClientService {
    ClientDto create(NewClientRequest newClientRequest);

    ClientDto getById(UUID id);

    ClientPageResponse getPage(ClientFilter clientFilter);

    ClientDto update(UUID id, UpdateClientRequest updateClientRequest);

    void deleteById(UUID id);

    ClientExistenceDto exists(UUID id);
}