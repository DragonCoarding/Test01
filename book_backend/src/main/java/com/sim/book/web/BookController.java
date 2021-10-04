package com.sim.book.web;

import com.sim.book.domain.Book;
import com.sim.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController {

	//스프링에서 뭔가 문제가 생기면 어노테이션이 메모리에 떴는지 어떤 어노테이션이 어떻게 작동했는지부터 확인
	private final BookService bookService;
	
	@GetMapping("/book/hello")
	public String HelloTest() {
		return "Hello React + Springboot Server";
	}
	
	// security (라이브러리) - CORS 정책을 가지고 있음. -> 나중에는 시큐리티가 CORS를 해제하게 만들고 타고들어오게 해야됨
	// @CrossOrigin는 컨트롤러 진입직전 실행됨 "어? 요청들어왔는데 자바스크립트네 그럼 받아줄게"
	@CrossOrigin // 자바스크립트로 요청할 수 있게 붙여야됨 자바스크립트를 제외한 모든 요청은 받을수있음 그게 CORS정책
	@PostMapping("/book")
	public ResponseEntity<?> inputBook(@RequestBody Book book){
		
		return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED); //201 리턴
	}
	
	@CrossOrigin
	@GetMapping("/book")
	public ResponseEntity<?> findBook(){

		return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK); //200
	}
	
	@CrossOrigin
	@GetMapping("/book/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){

		return new ResponseEntity<>(bookService.findOne(id), HttpStatus.OK); //200
	}
	
	@CrossOrigin
	@PutMapping("/book/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Book book){

		return new ResponseEntity<>(bookService.modify(id,book), HttpStatus.OK); //200
	}
	
	@CrossOrigin
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){

		return new ResponseEntity<>(bookService.delete(id), HttpStatus.OK); //200
	}

}
