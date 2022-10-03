package mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;
import utils.Paging;

public class BoardController extends HttpServlet {
	
	ServletContext context = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
		doProcess(req, resp);
	}
	
	// Paging
	public Map<String, Object> doPaging (HttpServletRequest req, MVCBoardDAO dao, String pagingURL) 
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");
		if (searchWord != null) {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		} 
		int totalCount = dao.selectCount(map);
		
		ServletContext application = getServletContext();
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

		int pageNum = 1;
		String pageTemp = req.getParameter("pageNum");
		if (pageTemp != null && !pageTemp.equals(""))
			pageNum = Integer.parseInt(pageTemp);

		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		map.put("start", start);
		map.put("end", end);

		
		String pagingImg = Paging.pagingStr(totalCount, pageSize, blockPage, pageNum, pagingURL);
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("blockPage", blockPage);
		map.put("pageNum", pageNum);
		
		return map;
	}
	
	public void doProcess (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); 
		
		String uri = req.getRequestURI();
		String conPath = req.getContextPath();
		String command = uri.substring(conPath.length());
		
		String viewPage = null;
//		String flag = (String)context.getAttribute("flag");
		
		// List
		if (command.equals("/MVCBoard/List.do")) {
			MVCBoardDAO dao = new MVCBoardDAO();
			Map<String, Object> map = doPaging(req, dao, "../MVCBoard/List.do");
			List<MVCBoardDTO> boardLists = dao.selectListPage(map);
			dao.close();
			
			req.setAttribute("boardLists", boardLists);
			req.setAttribute("map", map);
			viewPage = "/MVCBoard/List.jsp";
		}
		// Insert
		if (command.equals("/MVCBoard/Write.do")) {
			if (req.getMethod().equals("POST")){
				String saveDirectory = req.getServletContext().getRealPath("/Uploads");
				ServletContext application = getServletContext();
				int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
				MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);
				
				if (mr == null) {
					JSFunction.alertLocation(resp, "첨부 파일이 제한 용량을 초과했습니다", "../MVCBoard/Write.do");
					return;
				}

				MVCBoardDTO dto = new MVCBoardDTO();
				dto.setName(mr.getParameter("name"));
				dto.setTitle(mr.getParameter("title"));
				dto.setContent(mr.getParameter("content"));
				dto.setPass(mr.getParameter("pass"));
				
				String fileName = mr.getFilesystemName("ofile");
				if (fileName != null) {
					String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
					String ext = fileName.substring(fileName.lastIndexOf("."));
					String newFileName = now + ext;
					
					File oldFile = new File(saveDirectory + File.separator + fileName);
					File newFile = new File(saveDirectory + File.separator + newFileName);
					oldFile.renameTo(newFile);
					
					dto.setOfile(fileName);
					dto.setSfile(newFileName);
				}
				
				MVCBoardDAO dao = new MVCBoardDAO();
				int result = dao.insertWrite(dto);
				dao.close();
				
				if (result == 1) {
					resp.sendRedirect("../MVCBoard/List.do");
					return;
				} else {
					resp.sendRedirect("../MVCBoard/Write.do");
					return;
				}
			} else {
				viewPage = "/MVCBoard/Write.jsp";
			}
		}
		if (command.equals("/MVCBoard/View.do")) {
			MVCBoardDAO dao = new MVCBoardDAO();
			String idx = req.getParameter("idx");
			dao.updateVisitCount(idx);
			MVCBoardDTO dto = dao.selectView(idx);
			dao.close();
			
			dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
			
			req.setAttribute("dto", dto);
			viewPage = "/MVCBoard/View.jsp";
		}
		if (command.equals("/MVCBoard/Download.do")) {
			String ofile = req.getParameter("ofile");
			String sfile = req.getParameter("sfile");
			String idx = req.getParameter("idx");
			FileUtil.download(req, resp, "Uploads", sfile, ofile);
			MVCBoardDAO dao = new  MVCBoardDAO();
			dao.downCountPlus(idx);
			dao.close();
			return;
		}
		if (command.equals("/MVCBoard/Pass.do")) {
			req.setAttribute("mode", req.getParameter("mode"));
			if (req.getMethod().equals("GET")){
				viewPage = "/MVCBoard/Pass.jsp";
			} else {
				String idx = req.getParameter("idx");
				String mode = req.getParameter("mode");
				String pass = req.getParameter("pass");
				MVCBoardDAO dao = new MVCBoardDAO();
				boolean confirmed = dao.confirmPassword(pass, idx);
				dao.close();
				
				if(confirmed) {
					if(mode.equals("edit")) {
						HttpSession session = req.getSession();
						session.setAttribute("pass", pass);
						resp.sendRedirect("../MVCBoard/Edit.do?idx=" + idx);
					} else if (mode.equals("delete")) {
						dao = new MVCBoardDAO();
						MVCBoardDTO dto = dao.selectView(idx);
						int result = dao.deletePost(idx);
						dao.close();
						if(result == 1) {
							String saveFileName = dto.getSfile();
							FileUtil.deleteFile(req, "/Uploads", saveFileName);
						}
						JSFunction.alertLocation(resp, "삭제되었습니다", "../MVCBoard/List.do");
					} else {
						JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다");
					}
				}
			}
		}
		req.getRequestDispatcher(viewPage).forward(req, resp);
	}
}
