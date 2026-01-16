package com.yazan.bank.repository;

import com.yazan.bank.model.Department;
import com.yazan.bank.model.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors,Long> {

    Optional<Instructors> findByName(String name);

    Optional<Instructors> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Instructors> findByDepartmentAndJobTitle(Department department, String jobTitle);
    List<Instructors> findByDepartmentAndJobTitleNot(Department department, String jobTitle);

    @Modifying
    @Transactional
    @Query("UPDATE Instructors i SET i.unassignedTeachingLoad = i.unassignedTeachingLoad - 3 WHERE i.id = :instructorId")
    void decreaseUnassignedTeachingLoad(Long instructorId);

    @Modifying
    @Transactional
    @Query("UPDATE Instructors i SET i.unassignedTeachingLoad = i.unassignedTeachingLoad + 3 WHERE i.id = :instructorId")
    void increaseUnassignedTeachingLoad(Long instructorId);
}
