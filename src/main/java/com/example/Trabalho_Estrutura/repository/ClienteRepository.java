package com.example.Trabalho_Estrutura.repository;

import com.example.Trabalho_Estrutura.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
