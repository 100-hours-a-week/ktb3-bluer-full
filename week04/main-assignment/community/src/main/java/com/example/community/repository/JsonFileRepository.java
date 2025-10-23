package com.example.community.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonFileRepository<T> {

    private final String filePath;
    private final ObjectMapper objectMapper;
    private final TypeReference<List<T>> typeReference;
    private final String readErrorMessage;
    private final String writeErrorMessage;

    protected JsonFileRepository(
            String filePath,
            ObjectMapper objectMapper,
            TypeReference<List<T>> typeReference,
            String readErrorMessage,
            String writeErrorMessage
    ) {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
        this.typeReference = typeReference;
        this.readErrorMessage = readErrorMessage;
        this.writeErrorMessage = writeErrorMessage;
    }

    protected List<T> readAll() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(readErrorMessage, e);
        }
    }

    protected void writeAll(List<T> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException(writeErrorMessage, e);
        }
    }
}
