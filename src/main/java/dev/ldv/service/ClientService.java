package dev.ldv.service;

import dev.ldv.dto.*;

import java.util.UUID;

public interface ClientService {
    ClientDto create(NewClientRequest newClientRequest);

    ClientDto getById(UUID id);

    PageResponse<ClientShortDto> getPage(ClientFilter clientFilter);

    ClientDto update(UUID id, UpdateClientRequest updateClientRequest);

    void deleteById(UUID id);

    ClientExistenceDto exists(UUID id);
}