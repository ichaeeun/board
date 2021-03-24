package board.z02_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
// board.z02_util.FileDownloadViewer
public class FileDownloadViewer extends AbstractView{

	// 다운로드 할 절대 경로 설정된 변수 호출 
	@Value("${upload}")
	private String upload;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		// 서버 ==> client html파일 전송 contentType="text/html; charset=utf-8"
		// 				  model명으로는 html에 포함될 데이터 내용 
		// 서버 ==> cliet 파일을 전송 
		//				  model명으로 전송할 파일명 
		
		
		// 1. 모델명으로 파일명을 호출 처리 
		String fname = (String)model.get("downloadFile");
		// 2. 파일 객체를 통해서 파일을 가져온다. 
		File file = new File(upload+fname);
		// 3. server에 있는 파일을 client에 전달하기 위해 response객체 속성 선언 
		//	1) 다운로드를 위한 contenttype 선언 
		response.setContentType("application/download; charset-utf-8");
		// 	2) 파일의 길이(크기)와 파일명 선언 
		response.setContentLength((int)file.length());
		//	3) 한글 파일인 경우 encoding 처리 
		fname = URLEncoder.encode(fname, "utf-8").replaceAll("//+", " "); // % ==> " "
		//	4) 파일명 지정 
		response.setHeader("Content-Disposition", "attachment;filename=\""+fname+"\"");
		//	5) 전송할 파일 방식 binary 파일 
		response.setHeader("Content-Transfer-Encoding", "binary");
		//	6) 파일은 network를 통해 전송할 때는 메모리형 파일 객체인 
		//	   FileInputStream으로 처리하기 떄문에 생성자로 변환 처리 
		FileInputStream fis = new FileInputStream(file);
		//	7) client에 전송된 파일은 다시 OutputStream으로 저장 처리된다. 
		OutputStream out = response.getOutputStream();
		// 		1) 해당 메모리형 파일을 물리적 파일 쓰기로 처리 
		FileCopyUtils.copy(fis, out);
		//		2) buffer의 flush()를 통해서 자원 정리 
		out.flush();
	}
	
	
}
/*
1. 기본 view 상속 
*/