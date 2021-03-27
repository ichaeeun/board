package board.a02_service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import board.a03_dao.A01_BoardDao;
import board.z01_vo.Board;
import board.z01_vo.BoardFile;
import board.z01_vo.BoardSch;

@Service 
public class A01_BoardService {
	@Autowired(required=false)
	private A01_BoardDao dao;
	
//  info 에 있는 설정 값이 들어온다. ${upload} ${uploadTmp} 
	@Value("${upload}")
	private String upload; 
	@Value("${uploadTmp}")
	private String uploadTmp; 
	
	
	public ArrayList<Board> boardList(BoardSch sch){
		if(sch.getWriter()==null) sch.setWriter("");
		if(sch.getSubject()==null) sch.setSubject("");
		// 1. 데이터 총건수 할당 
		sch.setCount(dao.totCnt(sch));
		// 2. 화면에서 요청값으로 가져온 pageSize로 총 페이지 수 처리 
		//    1) 초기 화면에 표시될 pageSize를 default로 설정 
		if(sch.getPageSize()==0) {
			sch.setPageSize(5);
		}
		// 	  2) 총 페이지 수 : 올림처리(총건수/페이지크기)
		sch.setPageCount((int)Math.ceil(sch.getCount()/(double)sch.getPageSize()));
		// 4. 클릭한 현재페이지(요청) default를 1로 선언 
		if(sch.getCurPage()==0) {
			sch.setCurPage(1);
		}
		// start, end 속성을 도출하기 위하여 
		sch.setEnd(sch.getCurPage()*sch.getPageSize());
		sch.setStart((sch.getCurPage()-1)*sch.getPageSize()+1);
		
		
		return dao.boardList(sch);
	}
	public void insertBoard(Board insert){
		System.out.println("upload: "+upload);
		System.out.println("uploadTmp: "+uploadTmp);
		// 2. 데이터베이스 처리 
		dao.insertBoard(insert); // 메인 board테이블에 정보 등록 
		
		// 1. 물리적 파일 업로드 
		String fname = null;
		File tmpFile = null; // 임시위치 
		File orgFile = null; // 업로드 위치 
		
		File pathFile = new File(uploadTmp); //폴더 객체 생성 
		//.listFiles() : 해당 폴더 객체 안에 있는 파일을 가져오기 
		// 임시폴더에 있는 모든 파일을 삭제함으로써 중복예외를 방지한다. 
		for(File f:pathFile.listFiles()) {
			System.out.println("삭제할파일: "+f.getName());
			// 단위파일을 삭제처리 
			f.delete();
		}
		
		// # 다중 파일 처리 / 반복문 수행 
		for(MultipartFile mpf:insert.getReport()) {
			// 1) 파일명 지정 
			fname = mpf.getOriginalFilename();
			// 파일을 등록하지 않았을 때 제외 처리 <input type="file">
			if(fname!=null && !fname.trim().equals("")) {
				// 임시파일 객체 선언(경로 + 파일명)  
				// ps) File 객체는 파일과 폴더를 처리할 수 있다. 
				tmpFile = new File(uploadTmp+fname); 
				// MultipartFile ==> File로 변환처리 
				try {
					mpf.transferTo(tmpFile); 
					// io발생 예외 필수 처리 
					// 해당 위치에 파일이 생성됨 
					// 임시위치에서 다운로드할 폴더로 (z01_upload로) 복사 처리 
					orgFile = new File(upload+fname);
						
					
					// 복사처리 
					// StandardCopyOption.REPLACE_EXISTING : 동일한 파일명 업로드 시 대체 처리 
					Files.copy(tmpFile.toPath(), orgFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
					// 파일명, 업로드위치, 제목 
					dao.uploadFile(new BoardFile(fname,upload,insert.getSubject()));
					
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					System.out.println("상태값 에러: "+e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("파일 생성 에러: "+e.getMessage());
				} catch(Exception e) {
					System.out.println("기타에러: "+e.getMessage());
				}
			}
		}
	}
	public Board getBoard(int no) {
		// 1.조회 cnt 수정 (readcnt) 
		dao.uptReadCnt(no);
		// 2. 기본 board 정보할당 
		Board board = dao.getBoard(no);
		// 3. 첨부파일 정보 할당 
		//		메인 board정보와 연결되어 있는 파일리스트 정보를 
		//		VO객체에서 1:다 관계로 설정 처리 
		board.setFileInfo(dao.getBoardFile(no));
		
		return board;
	}
	
	public void updateBoard(Board upt) {
	if(upt.getFnames()!=null&&upt.getReport()!=null) {
		System.out.println("기존파일 갯수: "+upt.getFnames().length);
		System.out.println("수정할 파일 갯수: "+upt.getReport().length);
		int no = upt.getNo();
		
		// 첨부파일 물리적 위치 지정 
		String fname = null;	// 수정할 파일명 
		String orgFname = null; // 기존 파일명
		File tmpFile = null;
		File orgFile = null;
		BoardFile uptFile = null; 	// 수정 파일 정보 
		// 변경할 파일 
		MultipartFile mpf = null;
		
		File pathFile = new File(uploadTmp); //폴더 객체 생성 
		//.listFiles() : 해당 폴더 객체 안에 있는 파일을 가져오기 
		// 임시폴더에 있는 모든 파일을 삭제함으로써 중복예외를 방지한다. 
//		System.out.println("pathFile: "+pathFile);
		for(File f:pathFile.listFiles()) {
			System.out.println("삭제할파일: "+f.getName());
			// 단위파일을 삭제처리 
			f.delete();
		}
		
		for(int idx=0;idx<upt.getReport().length;idx++) {
			mpf = upt.getReport()[idx];
			fname = mpf.getOriginalFilename();
			
			// 기존 파일명 
			orgFname = upt.getFnames()[idx];
			if(fname!=null&&!fname.trim().equals("")) {
				
				// 해당 폴더에 기존파일은 일단 삭제(임시폴더) 
				tmpFile = new File(uploadTmp+orgFname);
				if(tmpFile.exists()) {
					tmpFile.delete();
				}
				// 해당 폴더에 기존파일은 일단 삭제(대상폴더) 
				orgFile = new File(upload+orgFname);
				if(orgFile.exists()) {
					orgFile.delete();
				}
				
				tmpFile = new File(uploadTmp+fname);
				orgFile = new File(upload+fname);
				
				try {
					// MultipartFile 을 임시파일객체로 변환 
					mpf.transferTo(tmpFile);
					Files.copy(tmpFile.toPath(),orgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("##상태 에러: "+e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("##파일 에러: "+e.getMessage());
				} catch (Exception e) {
					System.out.println("##기타 에러: "+e.getMessage());
				}
				
				// 변경된 파일 정보를 수정처리 
				HashMap<String, String> hs = new HashMap<String, String>();
				hs.put("no", ""+no);
				hs.put("fname", fname);
				hs.put("orgFname",upt.getFnames()[idx]);
				// dao단 호출 처리 
				dao.updateFile(hs);
			}
		}
	}	
		// 게시판 수정 정보 
		dao.updateBoard(upt);
	}
	
	public void deleteBoard(int no) {
		dao.deleteBoard(no);
		dao.deleteFile(no);
	}
}











