package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    @Query("SELECT m FROM Media m WHERE m.name = :name AND m.extension = :extension AND m.isDeleted = false")
    Optional<Media> findByNameAndExtensionAndIsDeletedFalse(@Param("name") String name, @Param("extension") String extension);

    @Query("SELECT m FROM Media m WHERE CONCAT(m.name, '.', m.extension) = :filename AND m.isDeleted = false")
    Optional<Media> findByFilenameAndIsDeletedFalse(@Param("filename") String filename);
}
