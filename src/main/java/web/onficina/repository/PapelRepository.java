package web.onficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
}
