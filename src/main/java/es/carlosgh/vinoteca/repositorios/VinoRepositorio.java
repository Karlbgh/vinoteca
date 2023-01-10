package es.carlosgh.vinoteca.repositorios;

import es.carlosgh.vinoteca.entidades.Vino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VinoRepositorio extends JpaRepository<Vino, Long>{
}
