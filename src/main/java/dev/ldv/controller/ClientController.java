package dev.ldv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import dev.ldv.api.ApiClient;
import dev.ldv.api.model.*;
import dev.ldv.dto.ClientFilter;
import dev.ldv.service.ClientService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/clients")
@Slf4j
@Validated
@RequiredArgsConstructor
public class ClientController implements ApiClient {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestBody @Valid NewClientRequest newClientRequest) {
        log.debug("POST /api/v1/clients");
        log.debug("Request body: {}", newClientRequest);

        return clientService.create(newClientRequest);
    }

    @GetMapping("/{clientId}")
    public ClientDto getById(@PathVariable UUID clientId) {
        log.debug("GET /api/v1/clients/{}", clientId);

        return clientService.getById(clientId);
    }

    @GetMapping
    public ClientPageResponse getPage(@RequestParam(required = false, defaultValue = "0") Integer page,
                                      @RequestParam(required = false, defaultValue = "20") Integer size,
                                      @RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String middleName,
                                      @RequestParam(required = false) String status,
                                      @RequestParam(required = false) Long mdmCode) {
        ClientFilter clientFilter = new ClientFilter(page, size, firstName, lastName, middleName, status, mdmCode);

        log.debug("GET /api/v1/clients");
        log.debug("Query params: {}", clientFilter);

        return clientService.getPage(clientFilter);
    }

    @PutMapping("/{clientId}")
    public ClientDto update(@PathVariable UUID clientId,
                            @RequestBody @Valid UpdateClientRequest updateClientRequest) {
        log.debug("PUT /api/v1/clients/{}", clientId);
        log.debug("Request body: {}", updateClientRequest);

        return clientService.update(clientId, updateClientRequest);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID clientId) {
        log.debug("DELETE /api/v1/clients/{}", clientId);

        clientService.deleteById(clientId);
    }

    @GetMapping("/{clientId}/exists")
    public ClientExistenceDto exists(@PathVariable UUID clientId) {
        log.debug("GET /api/v1/clients/{}/exists", clientId);

        return clientService.exists(clientId);
    }
}