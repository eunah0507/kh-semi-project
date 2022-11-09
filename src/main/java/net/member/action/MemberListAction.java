package net.member.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.action.Action;
import net.member.action.ActionForward;
import net.member.db.MemberDAO;

public class MemberListAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		
		ActionForward forward = new ActionForward();
		
		HttpSession session=request.getSession();
		MemberDAO memberdao=new MemberDAO();
		
		List memberlist=new ArrayList();
		
		String id=(String)session.getAttribute("id");
		if(id==null) {
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.me");
			return forward;
		}else if(!id.equals("admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('관리자가 아닙니다.');");
			out.println("location.href='./BoardList.bo';");
			out.println("</script>");
			out.close();
			return null;
		}
		
		memberlist=memberdao.getMemberList();
		if(memberlist==null) {
			System.out.println("관리자목록 보기 실패");
			return null;
		}
		
		request.setAttribute("memberlist", memberlist);
		forward.setRedirect(false);
		forward.setPath("./member/member_list.jsp");
		return forward;
	}
	
}