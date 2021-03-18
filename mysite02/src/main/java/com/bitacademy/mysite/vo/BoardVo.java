package com.bitacademy.mysite.vo;

public class BoardVo {
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", writer=" + writer + ", g_no="
				+ g_no + ", depth=" + depth + ", date=" + date + "]";
	}
	private long no;
	private String title;
	private String contents;
	private String writer;
	private long g_no;
	private int depth;
	private String date;
	private long gorder;
	private long parent;
	public long getNo() {
		return no;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		while(title.length()<=20) {
			title+=" ";
		}
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public long getG_no() {
		return g_no;
	}
	public void setG_no(long g_no) {
		this.g_no = g_no;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public long getGorder() {
		return gorder;
	}
	public void setGorder(long gorder) {
		this.gorder = gorder;
	}
	public long getParent() {
		return parent;
	}
	public void setParent(long parent) {
		this.parent = parent;
	}
	
	

}
