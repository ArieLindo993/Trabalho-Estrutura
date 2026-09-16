package com.example.Trabalho_Estrutura.controllers;

import com.example.Trabalho_Estrutura.entidades.Cliente;
import com.example.Trabalho_Estrutura.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientesT")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/salvar-cliente")
    public Cliente salvar(@RequestBody Cliente cliente) {

        return clienteService.salvarCliente(cliente);
    }

    @GetMapping("/listar-clientes")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/buscar-cliente/{id}")
    public Cliente buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id);
    }

    @DeleteMapping("/deletar-cliente/{id}")
    public void deletarClientePorId(@PathVariable Long id) {
        clienteService.deletarClientePorId(id);
    }

    @PutMapping("/atualizar-cliente/{id}")
    public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.atualizarClientePorId(id, cliente);
    }

}
