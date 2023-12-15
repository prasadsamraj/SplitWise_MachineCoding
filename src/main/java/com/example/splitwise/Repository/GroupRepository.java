package com.example.splitwise.Repository;

import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByAdmin(User user);
}
