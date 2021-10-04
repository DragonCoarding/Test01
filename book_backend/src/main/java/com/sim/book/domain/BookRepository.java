package com.sim.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//@Repository를 적어야 스프링 IoC에 빈으로 등록이 되는데 
//JPARepository를 상속하면 생략 가능함
//JPARepository에는 기본적으로 CRUD 함수가 포함되어있음
public interface BookRepository extends JpaRepository<Book, Long> {

}
