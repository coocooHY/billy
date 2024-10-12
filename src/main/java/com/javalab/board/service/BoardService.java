package com.javalab.board.service;

import java.util.List;

import com.javalab.board.vo.BoardVo;


/**
 * BoardService 인터페이스
 *
 */
public interface BoardService {
	// 게시물 상세 보기
	public BoardVo getBoard(int bno);
	// 게시물 목록 보기
	public List<BoardVo> listBoard();
	// 게시물 등록
	public int createBoard(BoardVo boardVo);
	// 게시물 업데이트
	public int updateBoard(BoardVo boardVo);
	// 게시물 삭제
	public int deleteBoard(int bno);

}
