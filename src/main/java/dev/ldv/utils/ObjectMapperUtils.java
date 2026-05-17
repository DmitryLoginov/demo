package dev.ldv.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectMapperUtils {
    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper()
            .writerWithDefaultPrettyPrinter();

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_WRITER.writeValueAsString(value);
    }
}