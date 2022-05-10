package com.example.faylyuklash.been;

import com.example.faylyuklash.model.Fayl;
import com.example.faylyuklash.model.FaylSource;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaylSourceBeen extends JpaRepository<FaylSource, Integer> {
  Optional<FaylSource> findByFaylId(Integer id);
}
