package com.zerobase.infrastructure.repository;

import com.zerobase.domain.Board;
import com.zerobase.domain.User;
import com.zerobase.domain.Voicedata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserOrderByIdDesc(User user);

    @Modifying
    @Query("update Board p set p.views = p.views + 1 where p.id = :id")
    int updateViews(@Param("id") Long id);
    @Query(value = "SELECT v FROM Board v JOIN FETCH v.user WHERE v.id = :id")
    Board findByIdWithUser(@Param("id") Long id);
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.user  where b.title = :title")
    Board findBoardByTitle(@Param("title") String title);

    //Page<Board> findByCategory(Pageable pageable, String category);

    @Query(value = "SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :id")
    Board findByIdWithUser(@Param("id") long id);

    @Query(value = "SELECT b FROM Board b JOIN FETCH b.user WHERE b.category = :category",
            countQuery = "SELECT COUNT(b) FROM Board b JOIN b.user WHERE b.category = :category")
    Page<Board> findByCategory(Pageable pageable, @Param("category") String category);

    @Query(value = "SELECT b FROM Board b JOIN FETCH b.user",
            countQuery = "SELECT COUNT(b) FROM Board b JOIN b.user")
    Page<Board> findAllWithUser(Pageable pageable);

}