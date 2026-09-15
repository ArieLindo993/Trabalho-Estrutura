package com.example.Trabalho_Estrutura;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"spring.datasource.url=jdbc:h2:mem:clientes-test", "spring.jpa.hibernate.ddl-auto=create-drop"})
class ClienteEndpointsTests {
    @LocalServerPort
    int port;
    final HttpClient http = HttpClient.newHttpClient();

    HttpResponse<String> request(String method, String path, String body) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/clientes/" + path))
            .header("Content-Type", "application/json")
            .method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body))
            .build(), HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void cadastrarListarBuscarAtualizarEExcluir() throws Exception {
        String body = """
            {"nome":"Maria Silva","tipoPessoa":"PF","cpfCnpj":"12345678901",
             "telefone":"44999999999","email":"maria@example.com","logradouro":"Rua Brasil",
             "numero":"100","bairro":"Centro","cep":"87500000","cidade":null}
            """;
        var saved = request("POST", "salvar-cliente", body);
        assertEquals(200, saved.statusCode());
        var matcher = java.util.regex.Pattern.compile("\"id\":(\\d+)").matcher(saved.body());
        assertTrue(matcher.find(), saved.body());
        String id = matcher.group(1);
        assertTrue(saved.body().contains("\"tipoPessoa\":\"PF\""));
        var list = request("GET", "listar-clientes", null);
        assertEquals(200, list.statusCode());
        assertTrue(list.body().contains("Maria Silva"));
        var found = request("GET", "buscar-cliente/" + id, null);
        assertEquals(200, found.statusCode());
        assertEquals(saved.body(), found.body());
        var updated = request("PUT", "atualizar-cliente/" + id,
            body.replace("Maria Silva", "Maria Atualizada").replace("{", "{\"id\":999, "));
        assertEquals(200, updated.statusCode());
        assertTrue(updated.body().contains("\"id\":" + id));
        assertTrue(request("GET", "buscar-cliente/" + id, null).body().contains("Maria Atualizada"));
        assertEquals(200, request("DELETE", "deletar-cliente/" + id, null).statusCode());
        assertEquals("[]", request("GET", "listar-clientes", null).body());
    }
}
