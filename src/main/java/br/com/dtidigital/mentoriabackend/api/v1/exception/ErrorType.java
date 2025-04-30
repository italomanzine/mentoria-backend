package br.com.dtidigital.mentoriabackend.api.v1.exception;

import lombok.Getter;

/**
 * Enumeration representing possible error types in the application.
 */
@Getter
public enum ErrorType {
    RESOURCE_NOT_FOUND("Resource not found"),
    ENTITY_NOT_FOUND("Entity not found"),
    INVALID_DATA("Invalid data"),
    BAD_REQUEST("Bad request"),
    BUSINESS_EXCEPTION("Business rule violation"),
    ENTITY_ALREADY_IN_USE("Entity already in use"),
    INVALID_PARAMETER("Invalid parameter"),
    ACCESS_DENIED("Access denied"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    DATA_RETRIEVAL_ERROR("Error retrieving data"),
    DATA_SAVING_ERROR("Error saving data");
    private final String title;

    ErrorType(String title) {
        this.title = title;
    }
}