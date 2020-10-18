package com.member.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.model.MemService;
import com.member.model.MemVO;


@MultipartConfig
public class MemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);	
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new Hashtable<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				// �|���m�W
				String memName = req.getParameter("memName");
				String memNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (memName == null || memName.trim().length() == 0) {
					errorMsgs.put("memName", "�|���m�W: �ФŪť�");
				} else if (!memName.trim().matches(memNameReg)) {
					errorMsgs.put("memName", "�|���m�W: �u��O���B�^��r�� , �B���ץ��ݦb2��10����");
				}
				// �|���b��
				String memId = req.getParameter("memId");
				String memIdReg = "^[(a-zA-Z0-9)]{6,10}$";
				if (memId == null || memId.trim().length() == 0) {
					errorMsgs.put("memId", "�|���b�����i�ť�");
				} else if (!memId.trim().matches(memIdReg)) {
					errorMsgs.put("memId", "�b��: �u��O�^��B�Ʀr, ���ץ��ݦb6��10����");
				}
				// �|���K�X
				String memPsw = req.getParameter("memPsw");
				String memPswReg = "^[(a-zA-Z0-9)]{6,10}$";
				if (memPsw == null || memPsw.trim().length() == 0) {
					errorMsgs.put("memPsw", "�K�X���i�ť�");
				} else if (!memPsw.trim().matches(memPswReg)) {
					errorMsgs.put("memId", "�K�X�u��O�^��B�Ʀr, ���ץ��ݦb6��10����");
				}
				//�T�{�K�X
				String memPsw2 = req.getParameter("memPsw2");
				if (memPsw2==null || memPsw2.trim().length()==0){
					errorMsgs.put("memPsw2", "�нT�{��J�K�X");
				} else if(!memPsw2.equals(memPsw)) {
					errorMsgs.put("memPsw2", "�нT�{��J�ۦP�K�X!");
				}
				// �|���ͤ�
				java.sql.Date memBirth = null;
				try {
					memBirth = java.sql.Date.valueOf(req.getParameter("memBirth"));
				} catch (IllegalArgumentException e) {
					memBirth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("memBirth", "�п�J���");
				}
				Integer memSex = null;
				if (req.getParameter("memSex") == null) {
					errorMsgs.put("memSex", "�п�ܩʧO");
				} else {
					memSex = new Integer(req.getParameter("memSex"));
				}

				String memNickname = req.getParameter("memNickname");

				String memPhone = req.getParameter("memPhone");
				if (memPhone == null || memPhone.trim().length() == 0) {
					errorMsgs.put("memPhone", "�п�J�p���q��");
				}
				String memEmail = req.getParameter("memEmail");
				if (memEmail == null || memPhone.trim().length() == 0) {
					errorMsgs.put("memEmail", "�п�J�q�l�H�c");
				}
					
				String city = req.getParameter("city");
				String district = req.getParameter("district");
				String address = req.getParameter("address");
				String memAddress = city + district + address;
				
				if (memAddress == null || memAddress.trim().length() == 0) {
					errorMsgs.put("memAddress", "�п�J��}");
				}
					

				Part part = req.getPart("memPhoto");
				byte[] memPhoto = null;
				InputStream in = part.getInputStream();
				if (in.available() != 0) {
					memPhoto = new byte[in.available()];
					in.read(memPhoto);
					in.close();
				} else {					
					memPhoto = getPictureByteArray(getServletContext().getRealPath("/front-end/member/image/memphoto.jpeg"));
				}
				
				MemVO memVO = new MemVO();
				memVO.setMemName(memName);
				memVO.setMemId(memId);
				memVO.setMemPsw(memPsw);
				memVO.setMemBirth(memBirth);
				memVO.setMemNickname(memNickname);
				memVO.setMemPhone(memPhone);
				memVO.setMemSex(memSex);
				memVO.setMemEmail(memEmail);
				memVO.setMemAddress(memAddress);
				memVO.setMemPhoto(memPhoto);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/addMem.jsp");
					failureView.forward(req, res);
					return;
				}
				
				MemService memSvc = new MemService();
				memVO = memSvc.addMem(memName, memBirth, memSex, memPhone, memEmail, memAddress, memId, memPsw,
						memNickname, memPhoto);
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);

				try {
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location"); // *�u�@2: �ݬݦ��L�ӷ����� (-->�p���ӷ�����:�h���ɦܨӷ�����)
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}

				RequestDispatcher successView = req.getRequestDispatcher("/front-end/index/index.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("extraErrMsgs", e.getMessage());
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/addMem.jsp");
				failureView.forward(req, res);
			}
		}

//		if ("login".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			try {
//				String memId = req.getParameter("memId");
//				
//				if (memId == null || memId.trim().length() == 0) {
//					req.setAttribute("memId", memId);
//					errorMsgs.add("�b���B�K�X���i����");
//				}
//				
//				String memPsw = req.getParameter("memPsw");
//
//				if (memPsw == null || memPsw.trim().length() == 0) {
//					errorMsgs.add("�b���B�K�X���i����");
//				}
//
//				if (!(errorMsgs == null)) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/member/login.jsp");
//					failureView.forward(req, res);
//				}
//
//				MemService memSvc = new MemService();
//				MemVO memVO = memSvc.login(memId, memPsw);
//				System.out.println(memVO.getMemId());
//				System.out.println(memVO.getMemPsw());
//				
//				if(memVO.getMemId() != null) {
//					HttpSession session = req.getSession();
//					session.setAttribute("memVO", memVO);
//					RequestDispatcher successView = req.getRequestDispatcher("/member/Success.html");
//					successView.forward(req, res);
//				}
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/member/login.jsp");
//				failureView.forward(req, res);
//			}
//
//		}

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				MemVO memVO1 = (MemVO) req.getSession().getAttribute("memNo");
				String memNo = memVO1.getMemNo();
				if ( memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("�п�J�|���s��");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/index.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memNo);
				if (memVO == null) {
					errorMsgs.add("�d�L���!");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/index.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("memVO", memVO);
				String url = "/front-end/member/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_UpdateAuth".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("�п�J�|���s��");
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMem.jsp");
					failureView.forward(req, res);
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				MemService memSvc = new MemService();
				memSvc.upgradeMemToSit(memNo);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/select_member_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				String url = "/member/listAllMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("�п�J�|���s��");
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMem.jsp");
					failureView.forward(req, res);
				}

				MemService memSvc = new MemService();
				memSvc.deleteMem(memNo);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMem.jsp");
					failureView.forward(req, res);
					return;
				}

				String url = "/member/listAllMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			Map<String, String> errorMsgs = new Hashtable<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.put("memNo", "�|���s�����i����");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
					failureView.forward(req, res);
				}

				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memNo);
				if (memVO == null) {
					errorMsgs.put("memNo", "�d�L�|��!");
				}

				if (!errorMsgs.isEmpty()) {
					System.out.println(111);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				req.setAttribute("memVO", memVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/member/update_mem_input.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("extraErrMsgs", e.getMessage());
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			Map<String, String> errorMsgs = new Hashtable<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				// �|���m�W
				String memName = req.getParameter("memName");
				String memNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (memName == null || memName.trim().length() == 0) {
					errorMsgs.put("memName", "�|���m�W: �ФŪť�");
				} else if (!memName.trim().matches(memNameReg)) {
					errorMsgs.put("memName", "�|���m�W: �u��O���B�^��r�� , �B���ץ��ݦb2��10����");
				}
				// �|���ͤ�
				java.sql.Date memBirth = null;
				try {
					memBirth = java.sql.Date.valueOf(req.getParameter("memBirth"));
				} catch (IllegalArgumentException e) {
					memBirth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("memBirth", "�п�J���");
				}
				Integer memSex = null;
				if (req.getParameter("memSex").trim() == null) {
					errorMsgs.put("memSex", "�п�ܩʧO");
				} else {
					memSex = new Integer(req.getParameter("memSex"));
				}

				String memNickname = req.getParameter("memNickname");

				String memPhone = req.getParameter("memPhone");
				if (memPhone == null || memPhone.trim().length() == 0)
					errorMsgs.put("memPhone", "�п�J�p���q��");

				String memEmail = req.getParameter("memEmail");
				if (memEmail == null || memPhone.trim().length() == 0)
					errorMsgs.put("memEmail", "�п�J�q�l�H�c");

				String memAddress = req.getParameter("memAddress");
				if (memAddress == null || memAddress.trim().length() == 0)
					errorMsgs.put("memAddress", "�п�J��}");

				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.put("memNo", "�|���s�����o����");
				}

				byte[] memPhoto = null;
				Part part = req.getPart("memPhoto");

				if (part.getSize() != 0) {
					InputStream in = part.getInputStream();
					memPhoto = new byte[in.available()];
					in.read(memPhoto);
					in.close();
				} else {
					MemService memSvc = new MemService();
					MemVO memVO = memSvc.getOneMem(memNo);
					memPhoto = memVO.getMemPhoto();
				}

				MemVO memVO = new MemVO();
				memVO.setMemName(memName);
				memVO.setMemBirth(memBirth);
				memVO.setMemNickname(memNickname);
				memVO.setMemPhone(memPhone);
				memVO.setMemSex(memSex);
				memVO.setMemEmail(memEmail);
				memVO.setMemAddress(memAddress);
				memVO.setMemPhoto(memPhoto);
				memVO.setMemNo(memNo);

				MemService memSvc = new MemService();
				memVO = memSvc.updateMem(memName, memBirth, memSex, memPhone, memEmail, memAddress, memNickname,
						memPhoto, memNo);

				if (!errorMsgs.isEmpty()) {
				
					req.setAttribute("memVO", memVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/update_mem_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				memVO = memSvc.getOneMem(memNo);
				req.getSession().setAttribute("memVO", memVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/index/index.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				System.out.println(111);

				errorMsgs.put("extraErrMsgs", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/update_mem_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("logout".equals(action)) {
			req.getSession().invalidate();
			String location = req.getContextPath()+"/front-end/index/index.jsp";
			res.sendRedirect(location);
		}
		
		if("duplicate_memId_Test".equals(action)) {
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			boolean match = false;
			try {
				String memId = req.getParameter("memId");
				System.out.println(memId);
				if(memId == null || memId.trim().length() == 0) {
					out.print("123");
					return;					
				}
				System.out.println(111);
				MemService memSvc = new MemService();
				if(memSvc.isDup(memId)) {
					match = true;
				}	
//					out.print("<span style=\"color:red\">���b���w�s�b!</span>");
					
				
					out.print(match);
//					out.print("<span style=\"color:green\">���b���i���U</span>");
								
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}			
		}

	}
	
	

	// �ϥ�byte[]�覡
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
