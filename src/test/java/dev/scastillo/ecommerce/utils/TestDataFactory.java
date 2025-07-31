package dev.scastillo.ecommerce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserCreateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserUpdateRequestDto;
import dev.scastillo.ecommerce.user.domain.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public final class TestDataFactory {
    private static final String ACTIVE_USER_MOCK_PATH = "/mocks/users/active_user.json";
    private static final String INACTIVE_USER_MOCK_PATH = "/mocks/users/inactive_user.json";
    private static final String CREATE_USER_REQUEST_MOCK_PATH = "/mocks/users/create_user_request.json";
    private static final String UPDATE_USER_REQUEST_MOCK_PATH = "/mocks/users/update_user_request.json";

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private TestDataFactory() {}
    public static User loadActiveUserMock() {
        return loadMockFromFile(ACTIVE_USER_MOCK_PATH, User.class);
    }
    public static User loadInactiveUserMock() {
        return loadMockFromFile(INACTIVE_USER_MOCK_PATH, User.class);
    }
    public static UserCreateRequestDto loadCreateUserRequestMock() {
        return loadMockFromFile(CREATE_USER_REQUEST_MOCK_PATH, UserCreateRequestDto.class);
    }
    public static UserUpdateRequestDto loadUpdateUserRequestMock() {
        return loadMockFromFile(UPDATE_USER_REQUEST_MOCK_PATH, UserUpdateRequestDto.class);
    }


    private static <T> T loadMockFromFile(String path, Class<T> clazz) {
        try (InputStream inputStream = TestDataFactory.class.getResourceAsStream(path)) {
            Objects.requireNonNull(inputStream, "No se pudo encontrar el archivo: " + path);
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar y convertir el archivo JSON: " + path, e);
        }
    }


}
