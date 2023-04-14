package com.tk.reactiveredis.reposity;

import com.tk.reactiveredis.entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Date : 2023/04/14 19:56
 * @Auther : tiankun
 */
@Component
public interface StudentRepository extends ReactiveCrudRepository<Student,Long> {
}
