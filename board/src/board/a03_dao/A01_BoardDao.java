package board.a03_dao;
import java.util.ArrayList;
import java.util.HashMap;

// board.a03_dao.A01_BoardDao
import org.springframework.stereotype.Repository;

import board.z01_vo.Board;
import board.z01_vo.BoardFile;
import board.z01_vo.BoardSch;
import board.z01_vo.Member;

@Repository 
public interface A01_BoardDao {
	// 페이지 총건수 
	public int totCnt(BoardSch sch); 
	
	public ArrayList<Board> boardList(BoardSch sch);
	
	public void insertBoard(Board insert);
	
	public void uploadFile(BoardFile ins);
	
	// 상세화면 
	public Board getBoard(int no);
	
	// 첨부파일 불러오기 
	public ArrayList<BoardFile> getBoardFile(int no);
	
	// 조회수 증가 
	public void uptReadCnt(int no);
	
	// 수정처리 
	public void updateBoard(Board upt); 
	
	// 파일 수정 처리 
	public void updateFile(HashMap<String,String> hs); 
	
	// 파일 삭제 처리 
	public void deleteBoard(int no); 
	public void deleteFile(int no);
	
	public Member login(Member mem);
}
