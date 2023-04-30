package com.example.lab5_20171198.repository;

import com.example.lab5_20171198.dto.JobsporMinMaxSalaryDto;
import com.example.lab5_20171198.entity.Empleado;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado,Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE employees SET enabled = 0 where employee_id = ?1", nativeQuery = true)
    void borrarEmpleado(int employee_id);


    @Transactional
    @Modifying
    @Query(value = "insert into employees (`first_name`, `last_name`, `email`, `password`, `job_id`,`salary`, `manager_id`, `department_id`,`enabled` ) values (?1,?2,?3,?4,?5,?6,?7,?8,1) ",
            nativeQuery = true)
    void guardarEmpleado(String nombre, String apellido, String email, String contrasena, String job_id, double sueldo, int jefe_id, int departamento_id);


    /*@Query(value = "select * from employees where firstName = ?1",
            nativeQuery = true)
    List<Empleado> buscarTransPorCompName(String nombre);*/


    @Query(nativeQuery = true, value = "SELECT e.* from employees e \n" +
            "inner join jobs j on e.job_id=j.job_id\n" +
            "inner join departments d on e.department_id=d.department_id \n" +
            "inner join locations l on d.location_id=l.location_id\n" +
            "where ((e.first_name like %?1%) or (e.last_name like %?1%) or (j.job_title like %?1%) or (d.department_name like %?1%) or (l.city like %?1%))")
    List<Empleado> buscarPorNombreApellidoPuestoDepartment(String texto);

    @Query(value = "select * from employees where enabled =1", nativeQuery = true)
    List<Empleado> listarEmpleadosEnabled();

    /*@Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update shippers set companyname = ?1 where shipperid = ?2")
    void actualizarNombreCompania(String companyName, int shipperId);*/




}
