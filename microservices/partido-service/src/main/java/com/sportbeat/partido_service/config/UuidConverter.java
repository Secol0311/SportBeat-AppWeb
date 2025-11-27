package com.sportbeat.partido_service.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class UuidConverter implements AttributeConverter<UUID, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(UUID attribute) {
        return attribute == null ? null : attribute.toString().getBytes();
    }
    @Override
    public UUID convertToEntityAttribute(byte[] dbData) {
        return dbData == null ? null : UUID.nameUUIDFromBytes(dbData);
    }
}