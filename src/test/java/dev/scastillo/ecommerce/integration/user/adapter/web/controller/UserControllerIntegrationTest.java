package dev.scastillo.ecommerce.integration.user.adapter.web.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserCreateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserUpdateRequestDto;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.repository.UserRepository;
import dev.scastillo.ecommerce.utils.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUserAndPersistInDatabase() throws Exception {
        UserCreateRequestDto request = TestDataFactory.loadCreateUserRequestMock();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Verifica que el usuario fue guardado
        User user = userRepository.findByEmail(request.getEmail());
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getEmail(), user.getEmail());
        assertTrue(user.getStatus());
        assertNotNull(user.getId());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());

    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnListOfUsersWithCorrectData() throws Exception {

        User user1 = TestDataFactory.loadActiveUserMock();
        User user2 = TestDataFactory.loadInactiveUserMock();

        userRepository.save(user1);
        userRepository.save(user2);

        MvcResult result = mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<UserDto> users = objectMapper.readValue(json, new TypeReference<>() {});

        assertEquals(2, users.size());
        assertEquals(user1.getFirstName(), users.get(0).getFirstName());
        assertEquals(user1.getLastName(), users.get(0).getLastName());
        assertEquals(user1.getEmail(), users.get(0).getEmail());
        assertEquals(user2.getFirstName(), users.get(1).getFirstName());
        assertEquals(user2.getLastName(), users.get(1).getLastName());
        assertEquals(user2.getEmail(), users.get(1).getEmail());
    }

    @Test
    void shouldReturnUserDetailsWhenUserExists() throws Exception {
        User user = TestDataFactory.loadActiveUserMock();
        userRepository.save(user);

        MvcResult result = mockMvc.perform(get("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/users/" + randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        // Arrange: crea y guarda un usuario
        User user = TestDataFactory.loadActiveUserMock();
        userRepository.save(user);

        // Prepara el request de actualización
        UserUpdateRequestDto updateRequest = TestDataFactory.loadUpdateUserRequestMock();

        // Act: realiza la petición PUT
        MvcResult result = mockMvc.perform(put("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert: valida los campos actualizados
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertEquals(updateRequest.getFirstName(), response.getFirstName());
        assertEquals(updateRequest.getLastName(), response.getLastName());
        assertEquals(updateRequest.getEmail(), response.getEmail());
        assertFalse(response.getStatus());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentUser() throws Exception {
        UUID randomId = UUID.randomUUID();
        UserUpdateRequestDto updateRequest = TestDataFactory.loadUpdateUserRequestMock();

        mockMvc.perform(put("/api/v1/users/" + randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        // Arrange: crea y guarda un usuario
        User user = TestDataFactory.loadActiveUserMock();
        userRepository.save(user);

        // Act & Assert: elimina el usuario
        mockMvc.perform(delete("/api/v1/users/" + user.getId()))
                .andExpect(status().isOk());

        // Verifica que ya no existe en la base de datos
        assertThrows(ResponseStatusException.class, () -> userRepository.findById(user.getId()));
    }


}
