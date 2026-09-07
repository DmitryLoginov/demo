package dev.ldv.mapper;

import dev.ldv.api.model.ClientDto;
import dev.ldv.api.model.ClientShortDto;
import dev.ldv.api.model.NewClientRequest;
import dev.ldv.api.model.UpdateClientRequest;
import dev.ldv.model.Client;
import dev.ldv.model.Document;
import dev.ldv.model.Name;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientMapper {
    public static Client fromNewClientRequest(NewClientRequest newClientRequest) {
        Client client = new Client();

        Name name = new Name();
        name.setFirstName(newClientRequest.getFirstName());
        name.setLastName(newClientRequest.getLastName());
        name.setMiddleName(newClientRequest.getMiddleName());

        Document document = new Document();
        document.setDocumentNumber(newClientRequest.getDocumentNumber());
        document.setDocumentSeries(newClientRequest.getDocumentSeries());
        document.setDocumentType(newClientRequest.getDocumentType());

        client.setFullName(name);
        client.setDocument(document);
        client.setCitizenship(newClientRequest.getCitizenship());
        client.setMdmCode(newClientRequest.getMdmCode());

        return client;
    }

    public static ClientDto toClientDto(Client client) {
        ClientDto clientDto = new ClientDto();

        clientDto.setId(client.getId());
        clientDto.setFirstName(client.getFullName().getFirstName());
        clientDto.setLastName(client.getFullName().getLastName());
        clientDto.setMiddleName(client.getFullName().getMiddleName());
        clientDto.setCitizenship(client.getCitizenship());
        clientDto.setMdmCode(client.getMdmCode());
        clientDto.setStatus(client.getStatus());
        clientDto.setDocumentNumber(client.getDocument().getDocumentNumber());
        clientDto.setDocumentSeries(client.getDocument().getDocumentSeries());
        clientDto.setDocumentType(client.getDocument().getDocumentType());
        clientDto.setCreatedAt(client.getCreatedAt());
        clientDto.setUpdatedAt(client.getUpdatedAt());

        return clientDto;
    }

    public static ClientShortDto toClientShortDto(Client client) {
        ClientShortDto clientShortDto = new ClientShortDto();

        clientShortDto.setId(client.getId());
        clientShortDto.setFirstName(client.getFullName().getFirstName());
        clientShortDto.setLastName(client.getFullName().getLastName());
        clientShortDto.setMiddleName(client.getFullName().getMiddleName());
        clientShortDto.setMdmCode(client.getMdmCode());
        clientShortDto.setStatus(client.getStatus());

        return clientShortDto;
    }

    public static void updateFields(Client client, UpdateClientRequest updateClientRequest) {
        client.getFullName().setFirstName(updateClientRequest.getFirstName());
        client.getFullName().setLastName(updateClientRequest.getLastName());
        client.getFullName().setMiddleName(updateClientRequest.getMiddleName());
        client.setCitizenship(updateClientRequest.getCitizenship());
        client.getDocument().setDocumentNumber(updateClientRequest.getDocumentNumber());
        client.getDocument().setDocumentSeries(updateClientRequest.getDocumentSeries());
        client.getDocument().setDocumentType(updateClientRequest.getDocumentType());
    }
}
