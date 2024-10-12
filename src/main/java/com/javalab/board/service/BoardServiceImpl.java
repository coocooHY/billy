package com.javalab.board.service;

import java.util.List;

import com.javalab.board.repository.BoardMapper;
import com.javalab.board.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalab.board.vo.BoardVo;

import lombok.extern.slf4j.Slf4j;


/**
 * 게시물 서비스 클래스
 * @Service : 나는 서비스 레이어의 스프링 빈 역할을 할 수 있도록 빈으로 생성해라.라는 표시.
 * - root-context.xml 빈 환경설정 파일에 패키지의 위치가 지정되어 있다.
 *   <context:component-scan 
 *   		base-package="com.javalab.mybatis.service">
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

	// 보드 매퍼 인터페이스 의존성 주입
	// 실제로는 매퍼XML을 감싸는 대리인(프로시) 객체가 주입된다.
	@Autowired
	private BoardMapper boardMapper;
	
	// 멤버 매퍼 인터페이스
	@Autowired
    private MemberMapper memberMapper;
	
	// 게시물 내용 보기
	@Override
	public BoardVo getBoard(int bno) {
		BoardVo boardVo = boardMapper.getBoard(bno);
		return boardVo;
	}

	@Override
	public List<BoardVo> listBoard() {
		return boardMapper.listBoard();
	}

	/**
	 * 게시물 생성 비즈니스 로직
	 * - 게시물 등록과
	 * - 게시물 작성 회원의 포인트를 지급하는 두 가지의 업무가 처리됨.
	 *   이렇게 중요한 두 가지 업무를 처리하는 것을 비즈니스 로직이라고 한다.
	 * - 비즈니스 로직은 애플리케이션의 핵심 기능을 수행하며, 데이터베이스 상호 작용, 계산, 
	 *   데이터 검증 등의 작업을 포함. 스프링 MVC 패턴에서 비즈니스 로직은 
	 *   일반적으로 서비스 계층에서 구현
	 * - 트랜잭션 : @Transactional 어노테이션을 사용하여 트랜잭션 관리가 적용. 
	 *   이는 메서드 내의 모든 작업이 하나의 트랜잭션으로 처리됨을 의미. 예를 들어, 
	 *   게시물 생성 후 포인트 지급이 실패하면, 트랜잭션이 롤백되어 게시물 생성도 취소됨.    
	 */
	@Override
	@Transactional
	public int createBoard(BoardVo boardVo) {
		// 게시물 저장 처리
		//int result = boardMapper.createBoard(boardVo);	// 오리지널
		
		log.info("게시물 저장전 bno : " + boardVo.getBno());	// 0
		
		int result = boardMapper.createBoardSelectKey(boardVo); // selectKey 사용
		
		log.info("게시물 저장후 bno : " + boardVo.getBno());	// ?
		
	    if (result > 0) {
	    	// 게시물 작성 회원 포인트 증가
	        //memberMapper.updateMemberPoint(boardVo.getMemberId());
	    }
	    return result;
	}
	
	/*
	public int createBoard(BoardVo boardVo) {
		
		// 게시물 저장 처리(selectKey 사용)
		int result = boardMapper.createBoardSelectKey(boardVo);	// 저장한 bno값이 채워짐.
		
		log.info("게시물 저장후 bno : " + boardVo.getBno());
		
		if (result > 0) {
			// 게시물 작성 회원 포인트 증가
			memberMapper.updateMemberPoint(boardVo.getMemberId());
		}
		return result;
	}
	*/
	
	@Override
	public int updateBoard(BoardVo boardVo) {
		return boardMapper.updateBoard(boardVo);
	}

	@Override
	public int deleteBoard(int bno) {
		return boardMapper.deleteBoard(bno);
	}
	
}
