package com.sim.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sim.book.domain.Book;
import com.sim.book.domain.BookRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

// 서비스가 빈으로 등록되면 기능을 정의할 수 있고, 트랜잭션을 관리할 수 있음.

@RequiredArgsConstructor //final이 붙어있는 애들의 컨스트럭터를 만들어주고 그럼 자동으로 DI가됨 
@Service
public class BookService {
	// 함수 => 송금() -> 레파지토리에 여러개의 함수를 실행하고 잘되면 commit 하고 안되면 rollback 하고 그런걸 서비스에서 진행함
	
	private final BookRepository bookRepository;

	@Transactional // 이 어노테이션의 의미는 서비스함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리하겠다.
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Transactional(readOnly = true) // JPA에서는 변경감지라는 내부 기능이 존재 엔티티가 변경되면 그걸 지켜보고있음
									// 박아두면 내부기능 활성화x  update시의 정합성을 유지해줌 insert의 유령데이터현상(팬텀현상) 못막음
	public Book findOne(Long id) {

		return bookRepository.findById(id) //찾아서 잘되면 리턴해주고 안되면 익셉션을 날림
				.orElseThrow(()-> new IllegalArgumentException("ID를 확인해주세요")); // 리액트에서는 =>라고했는데 여기선 ->
	}

	@Transactional(readOnly = true)
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Transactional
	public Book modify(Long id, Book book) {
		// 더티체팅 update 치기
		Book bookEntity = bookRepository.findById(id) // DB에서 데이터를 들고오면 그걸 영속화 됬다고한다
				.orElseThrow(()-> new IllegalArgumentException("ID를 확인해주세요")); // 영속화(book 오브젝트) -> 영속성 컨텍스트에 보관
		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(book.getAuthor());
		return bookEntity;
	} // 함수의 종료때 => 트랜잭션 종료 => 영속화 되어있는 데이터를 갱신(flush) => commit  -->>>> 더티체킹  업데이트할때는 이렇게 하는게 좋다

	@Transactional
	public String delete(Long id) {
		bookRepository.deleteById(id); // 오류가 터지면 익셉션을 타니까 신경쓰지말고
		return "ok";
	}
	

}
