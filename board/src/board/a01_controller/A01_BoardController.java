package board.a01_controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.a02_service.A01_BoardService;
import board.z01_vo.Board;
import board.z01_vo.Member;

@Controller
@RequestMapping("board.do")
public class A01_BoardController {
	@Autowired(required=false)
	private A01_BoardService service;
	// http://localhost:8080/board/board.do?method=list
	@RequestMapping(params="method=list")
	public String boardList(@ModelAttribute("sch") Board sch,Model d, HttpServletRequest request) {
		// 세션 설정 
		HttpSession session = request.getSession();
		session.setAttribute("mem", new Member("himan","7777"));
		
		d.addAttribute("boardList",service.boardList(sch));
		return "a01_boardList";
	}
	// http://localhost:8080/board/board.do?method=insForm
	@RequestMapping(params="method=insForm")
	public String insForm(@ModelAttribute("ins") Board ins) {
		return "a02_boardInsert";
	}
	// http://localhost:8080/board/board.do?method=insert
	@RequestMapping(params="method=insert")
	public String insert(@ModelAttribute("ins") Board ins) {
		System.out.println("등록 "+ins.getContent());
		System.out.println("파일: "+ins.getReport()[0].getOriginalFilename());
		service.insertBoard(ins);
		return "a02_boardInsert";
	}
	// http://localhost:8080/board/board.do?method=detail
	// 상세화면 처리 
	@RequestMapping(params="method=detail")
	public String detail(@RequestParam("no") int no, Model d) {
		System.out.println("no: "+no);
		d.addAttribute("board",service.getBoard(no));
		return "a03_boardDetail";
	}
	// http://localhost:8080/board/board.do?method=update
	// http://localhost:8080/board/board.do?method=delete
	// http://localhost:8080/board/board.do?method=download 
	
	// 화면단에 클릭 시, 
	// http://localhost:8080/board/board.do?method=download&fname=파일명 
	@RequestMapping(params="method=download")
	public String download(@RequestParam("fname") String fname, Model d) {
		System.out.println("파일명: "+fname);
		d.addAttribute("downloadFile",fname); 
		// viewer 안에 선언한 모델명 
		// 컨테이너 안에 있는 viewer명 
		return "downloadviewer";
	}
}
