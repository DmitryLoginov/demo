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
@RequestMapping("${api.prefix}/clients")
@Slf4j
@Validated
@RequiredArgsConstructor
public class ClientController implements ApiClient {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestBody @Valid NewClientRequest newClientRequest) {
        return clientService.create(newClientRequest);
    }

    @GetMapping("/{clientId}")
    public ClientDto getById(@PathVariable UUID clientId) {
        return clientService.getById(clientId);
    }

    @GetMapping
    public ClientPageResponse getPage(@RequestParam(required = false, defaultValue = "0") Integer page,
                                      @RequestParam(required = false, defaultValue = "20") Integer size,
                                      @RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String middleName,
                                      @RequestParam(required = false) ClientStatus status,
                                      @RequestParam(required = false) Long mdmCode) {
        ClientFilter clientFilter = new ClientFilter(page, size, firstName, lastName, middleName, status, mdmCode);

        return clientService.getPage(clientFilter);
    }

    @PutMapping("/{clientId}")
    public ClientDto update(@PathVariable UUID clientId,
                            @RequestBody @Valid UpdateClientRequest updateClientRequest) {
        return clientService.update(clientId, updateClientRequest);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID clientId) {
        clientService.deleteById(clientId);
    }

    @GetMapping("/{clientId}/exists")
    public ClientExistenceDto exists(@PathVariable UUID clientId) {
        return clientService.exists(clientId);
    }
}