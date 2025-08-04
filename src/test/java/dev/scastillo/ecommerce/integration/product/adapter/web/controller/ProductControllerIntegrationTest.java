package dev.scastillo.ecommerce.integration.product.adapter.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductDto;
import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.repository.ProductRepository;
import dev.scastillo.ecommerce.product.domain.repository.ProductStockRepository;
import dev.scastillo.ecommerce.shared.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productStockRepository.deleteAll();
    }

    private final UUID USER_ID_MOCK = UUID.randomUUID();

    private String generateToken(UUID userId) {
        return jwtUtil.generateToken(userId);
    }

    @Test
    void createProduct_createsAndPersistsProduct() throws Exception {
        ProductCreateRequestDto request = new ProductCreateRequestDto();
        request.setTitle("Producto Test");
        request.setDescription("Descripción de prueba");
        request.setStockQuantity(10);
        request.setPrice(100.0);
        request.setAvailable(true);

        // Envía la petición y obtiene la respuesta como DTO
        String response = mockMvc.perform(post("/api/v1/products")
                        .header("Authorization", "Bearer " + generateToken(USER_ID_MOCK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto productDto = objectMapper.readValue(response, ProductDto.class);

        // Valida los datos del DTO
        assertEquals("Producto Test", productDto.getTitle());
        assertEquals("Descripción de prueba", productDto.getDescription());
        assertEquals(100.0, productDto.getPrice());
        assertTrue(productDto.isAvailable());

        // Valida que el producto está en la base de datos
        var productInDb = productRepository.findById(productDto.getId()).orElseThrow();
        var productStockInDb = productStockRepository.findStockByProductId(productDto.getId()).orElseThrow();
        assertEquals("Producto Test", productInDb.getTitle());
        assertEquals(10, productStockInDb.getQuantity());
    }

    @Test
    void searchProducts_returnsFilteredProductDtoList() throws Exception {
        // Prepara y guarda productos en la base de datos
        Product product1 = new Product();
        product1.setTitle("Zapato");
        product1.setDescription("Zapato deportivo");
        product1.setPrice(50.0);
        product1.setAvailable(true);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setTitle("Camisa");
        product2.setDescription("Camisa formal");
        product2.setPrice(30.0);
        product2.setAvailable(false);
        productRepository.save(product2);

        // Realiza la petición GET con filtro por título y disponibilidad
        String response = mockMvc.perform(get("/api/v1/products")
                        .param("title", "Zapato")
                        .param("available", "true")
                        .header("Authorization", "Bearer " + generateToken(USER_ID_MOCK)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Deserializa la respuesta como lista de DTOs
        List<ProductDto> products = objectMapper.readValue(
                response,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ProductDto.class)
        );

        // Verifica que solo se retorna el producto filtrado
        assertEquals(2, products.size());
        assertEquals("Zapato", products.get(0).getTitle());
        assertTrue(products.get(0).isAvailable());

        // Verifica que el producto existe en la base de datos
        assertTrue(productRepository.findById(products.get(0).getId()).isPresent());
    }

    @Test
    void getProductById_returnsProductDto_andPersistsInDb() throws Exception {
        // Crear y guardar producto en la base de datos
        Product product = new Product();
        product.setTitle("Producto Test");
        product.setDescription("Descripción de prueba");
        product.setPrice(100.0);
        product.setAvailable(true);
        product = productRepository.save(product);

        // Realizar petición GET al endpoint
        String response = mockMvc.perform(get("/api/v1/products/" + product.getId())
                        .header("Authorization", "Bearer " + generateToken(USER_ID_MOCK)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Deserializar respuesta como DTO
        ProductDto productDto = objectMapper.readValue(response, ProductDto.class);

        // Validar datos del DTO
        assertEquals(product.getId(), productDto.getId());
        assertEquals("Producto Test", productDto.getTitle());
        assertEquals("Descripción de prueba", productDto.getDescription());
        assertEquals(100.0, productDto.getPrice());
        assertTrue(productDto.isAvailable());

        // Validar que el producto existe en la base de datos
        assertTrue(productRepository.findById(product.getId()).isPresent());
    }

}
