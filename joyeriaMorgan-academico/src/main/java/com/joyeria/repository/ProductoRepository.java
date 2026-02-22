package com.joyeria.repository;

import com.joyeria.model.Producto;
import com.joyeria.model.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByEstado(EstadoProducto estado);

    long countByEstado(EstadoProducto estado);

    @Query("SELECT SUM(p.precio) FROM Producto p WHERE p.estado = com.joyeria.model.EstadoProducto.DISPONIBLE")
    Double valorInventario();
}