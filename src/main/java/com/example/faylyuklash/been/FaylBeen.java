package com.example.faylyuklash.been;

import com.example.faylyuklash.model.Fayl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaylBeen extends JpaRepository<Fayl, Integer> {
}
