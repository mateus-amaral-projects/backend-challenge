package com.itau.insurance.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CrudRepository<ProdutoEntity, UUID> {
	
	boolean existsByNomeAndCategoria(String nome, String categoria);
}
