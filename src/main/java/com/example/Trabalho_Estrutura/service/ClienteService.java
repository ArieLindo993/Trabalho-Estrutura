package com.example.Trabalho_Estrutura.service;

import com.example.Trabalho_Estrutura.entidades.Cliente;
import com.example.Trabalho_Estrutura.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //injeta a dependencia de forma automatica
public class ClienteService {

    private final ClienteRepository clienteRepository;

/* METODO HARDCODED PARA INJECAO DE DEPENCIA NO CASO CLIENTE REPOSITORY
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

 */

    public Cliente salvarCliente(Cliente cliente) {

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }

    public Cliente buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).
                orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado!"));
        return cliente;
    }

    public void deletarClientePorId(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente enviado");
        }
    }

    public Cliente atualizarClientePorId(Long id, Cliente cliente) {
        Cliente clienteSalvo = buscarClientePorId(id);
        BeanUtils.copyProperties(cliente, clienteSalvo, "id");
        return clienteRepository.save(clienteSalvo);
    }
}
