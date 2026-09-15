package com.example.Trabalho_Estrutura;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"spring.datasource.url=jdbc:h2:mem:clientes-test", "spring.jpa.hibernate.ddl-auto=create-drop"})
class ClienteEndpointsTests {
    @Autowired
    TestRestTemplate http;

    ResponseEntity<String> request(String method, String path, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return http.exchange("/clientes/" + path, HttpMethod.valueOf(method),
            new HttpEntity<String>(body, headers), String.class);
    }

    @Test
    void cadastrarListarBuscarAtualizarEExcluir() {
        String body = "{\"nome\":\"Maria Silva\",\"tipoPessoa\":\"PF\",\"cpfCnpj\":\"12345678901\","
            + "\"telefone\":\"44999999999\",\"email\":\"maria@example.com\",\"logradouro\":\"Rua Brasil\","
            + "\"numero\":\"100\",\"bairro\":\"Centro\",\"cep\":\"87500000\",\"cidade\":null}";
        ResponseEntity<String> saved = request("POST", "salvar-cliente", body);
        assertEquals(200, saved.getStatusCodeValue());
        Matcher matcher = Pattern.compile("\"id\":(\\d+)").matcher(saved.getBody());
        assertTrue(matcher.find(), saved.getBody());
        String id = matcher.group(1);
        assertTrue(saved.getBody().contains("\"tipoPessoa\":\"PF\""));
        ResponseEntity<String> list = request("GET", "listar-clientes", null);
        assertEquals(200, list.getStatusCodeValue());
        assertTrue(list.getBody().contains("Maria Silva"));
        ResponseEntity<String> found = request("GET", "buscar-cliente/" + id, null);
        assertEquals(200, found.getStatusCodeValue());
        assertEquals(saved.getBody(), found.getBody());
        ResponseEntity<String> updated = request("PUT", "atualizar-cliente/" + id,
            body.replace("Maria Silva", "Maria Atualizada").replace("{", "{\"id\":999, "));
        assertEquals(200, updated.getStatusCodeValue());
        assertTrue(updated.getBody().contains("\"id\":" + id));
        assertTrue(request("GET", "buscar-cliente/" + id, null).getBody().contains("Maria Atualizada"));
        assertEquals(200, request("DELETE", "deletar-cliente/" + id, null).getStatusCodeValue());
        assertEquals("[]", request("GET", "listar-clientes", null).getBody());
    }
}
