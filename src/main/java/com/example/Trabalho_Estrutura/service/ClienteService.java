package com.example.Trabalho_Estrutura.service;

import com.example.Trabalho_Estrutura.entidades.Cliente;
import com.example.Trabalho_Estrutura.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
         return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id).
                orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado!"));
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
