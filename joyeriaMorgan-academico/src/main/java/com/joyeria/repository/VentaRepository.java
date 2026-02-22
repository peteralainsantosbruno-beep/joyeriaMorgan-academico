package com.joyeria.repository;

import com.joyeria.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT COUNT(v) FROM Venta v")
    Long totalVentas();

    @Query("SELECT SUM(v.total) FROM Venta v")
    Double dineroTotal();

    @Query(value = "SELECT MONTH(fecha) as mes, SUM(total) as total FROM ventas GROUP BY MONTH(fecha) ORDER BY mes", nativeQuery = true)
    List<Object[]> ventasPorMes();
}