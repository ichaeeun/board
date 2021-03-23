package board.z01_vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Board {
//	NO NUMBER,
//	refno NUMBER,
//	subject varchar2(100),
//	writer varchar2(30),
// readcnt
//	regdte DATE,
//	uptdte DATE,
//	content varchar2(1000)	
	private int no;
	private int refno;
	private String subject;
	private String writer;
	private int readcnt;
	private Date regdte;
	private Date uptdte;
	private String content;
	private MultipartFile[] report;
	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Board(String subject, String writer) {
		super();
		this.subject = subject;
		this.writer = writer;
	}
	
	public Board(int no, int refno, String subject, String writer, int readcnt, Date regdte, Date uptdte,
			String content) {
		super();
		this.no = no;
		this.refno = refno;
		this.subject = subject;
		this.writer = writer;
		this.readcnt = readcnt;
		this.regdte = regdte;
		this.uptdte = uptdte;
		this.content = content;
	}

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getRefno() {
		return refno;
	}
	public void setRefno(int refno) {
		this.refno = refno;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdte() {
		return regdte;
	}
	public void setRegdte(Date regdte) {
		this.regdte = regdte;
	}
	public Date getUptdte() {
		return uptdte;
	}
	public void setUptdte(Date uptdte) {
		this.uptdte = uptdte;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}

	public MultipartFile[] getReport() {
		return report;
	}

	public void setReport(MultipartFile[] report) {
		this.report = report;
	}
	
	
}
