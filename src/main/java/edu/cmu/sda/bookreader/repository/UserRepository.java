package edu.cmu.sda.bookreader.repository;
import edu.cmu.sda.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface

UserRepository extends JpaRepository<User, String> {
}
