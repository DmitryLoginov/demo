package dev.ldv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import dev.ldv.dto.*;
import dev.ldv.service.ClientService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/clients")
@Slf4j
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestHeader("Content-Type") String contentType,
                            @RequestHeader("Accept") String accept,
                            @RequestBody @Valid NewClientRequest newClientRequest) {
        log.debug("POST /api/v1/clients");
        log.debug("Request body: {}", newClientRequest);

        return clientService.create(newClientRequest);
    }

    @GetMapping("/{clientId}")
    public ClientDto getById(@RequestHeader("Content-Type") String contentType,
                             @RequestHeader("Accept") String accept,
                             @PathVariable UUID clientId) {
        log.debug("GET /api/v1/clients/{}", clientId);

        return clientService.getById(clientId);
    }

    @GetMapping
    public PageResponse<ClientShortDto> getPage(@RequestHeader("Content-Type") String contentType,
                                                @RequestHeader("Accept") String accept,
                                                ClientFilter clientFilter) {
        log.debug("GET /api/v1/clients");
        log.debug("Query params: {}", clientFilter);

        return clientService.getPage(clientFilter);
    }

    @PutMapping("/{clientId}")
    public ClientDto update(@RequestHeader("Content-Type") String contentType,
                            @RequestHeader("Accept") String accept,
                            @PathVariable UUID clientId,
                            @RequestBody @Valid UpdateClientRequest updateClientRequest) {
        log.debug("PUT /api/v1/clients/{}", clientId);
        log.debug("Request body: {}", updateClientRequest);

        return clientService.update(clientId, updateClientRequest);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestHeader("Content-Type") String contentType,
                           @RequestHeader("Accept") String accept,
                           @PathVariable UUID clientId) {
        log.debug("DELETE /api/v1/clients/{}", clientId);

        clientService.deleteById(clientId);
    }

    @GetMapping("/{clientId}/exists")
    public ClientExistenceDto exists(@RequestHeader("Content-Type") String contentType,
                                     @RequestHeader("Accept") String accept,
                                     @PathVariable UUID clientId) {
        log.debug("GET /api/v1/clients/{}/exists", clientId);

        return clientService.exists(clientId);
    }

    private void validateHeaders(String contentType, String accept) {
        if (!contentType.equals("application/json")) {
            throw new RuntimeException("Invalid content type: " + contentType);
        }

        if (!accept.equals("application/json")) {
            throw new RuntimeException("Invalid accept type: " + accept);
        }
    }
}