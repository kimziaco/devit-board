package com.devit.board.repository;

import com.devit.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT * FROM board b WHERE b.title LIKE CONCAT('%',:keyword,'%') OR b.content LIKE CONCAT('%',:keyword,'%')",
            countQuery = "SELECT count(*) FROM board",
            nativeQuery = true)
    Page<Board> findByTitleOrContentContaining(@Param("keyword") String keyword, Pageable pageable);
    Optional<Board> findByBoardUid(UUID id);
}
