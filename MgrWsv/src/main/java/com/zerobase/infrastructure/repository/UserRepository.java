package com.zerobase.infrastructure.repository;

import com.zerobase.domain.RoleType;
import com.zerobase.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserid(String userid);
    @Query(value = "SELECT v FROM User v ",
            countQuery = "SELECT COUNT(v) FROM User v")
    Page<User> findAllWithUser(Pageable pageable);


    /* Security */
   // Optional<User> findByUsername(String userid);

    /* OAuth */
    //Optional<User> findByEmail(String email);

    /* user GET */
//    User findByNickname(String nickname);

    /* 중복 검사> 중복인 경우 true, 중복되지 않은경우 false 리턴 */
//    boolean existsByUser_id(String user_id);
//    boolean existsByNickname(String nickname);
//    boolean existsByEmail(String email);


}
