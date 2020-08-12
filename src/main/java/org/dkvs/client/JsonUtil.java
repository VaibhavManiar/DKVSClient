package org.dkvs.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T toObject(String json, Class<T> clazz) {
        logger.debug("Converting json to object[{}]. JSON: [{}]", clazz, json);
        return mapper.convertValue(json, clazz);
    }

    public static <T> String toJson(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while parsing object to json. Object " + t);
        }
    }
}
