package com.example.demo.repository;

import com.example.demo.Model.suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends JpaRepository<suppliers, Long> {


}

