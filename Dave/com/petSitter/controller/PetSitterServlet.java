package com.petSitter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.petSitter.model.*;
//import com.sitOrder.model.*;

@WebServlet("/PetSitterServlet")
public class PetSitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PetSitterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//      res.setContentType("text/html; charset=UTF-8");
//      PrintWriter out = res.getWriter();

        String action = req.getParameter("action");

        //--------------�e�x(front-end)--------------
        
        if ("getOneSitter_forDisplay".equals(action)) { //from sitterFront.jsp

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                
                String sitNo = req.getParameter("sitNo");
                if (sitNo == null || (sitNo.trim().length() == 0)) {
                    errorMsgs.add("�п�J�O���s��");
                }
                
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/front-end/sitterFront.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                
                String sitNoReg = "[S][0-9]{3}";
                if(!sitNo.trim().matches(sitNoReg)) {
                    errorMsgs.add("�п�J���T�榡");
                }
                
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/front-end/sitterFront.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                                
                PetSitterService petSitSrv = new PetSitterService();
                PetSitterVO petSitterVO = petSitSrv.getByPK(sitNo);
                if (petSitterVO == null) {
                    errorMsgs.add("�d�L���");
                }
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/front-end/sitterFront.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                req.setAttribute("petSitterVO", petSitterVO);
                String url = "/front-end/petSitter/listOneSitter.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
            } catch (Exception e) {
                
                errorMsgs.add("�L�k���o���:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/front-end/sitterFront.jsp");
                failureView.forward(req, res);                 
            }

        }

        

        if ("getSitter_forUpdate".equals(action)) { //from listOneSitter.jsp

        	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
        	
        	try {
        		
        		String sitNo = req.getParameter("sitNo");
        		PetSitterService petSitterSrv = new PetSitterService();
        		PetSitterVO petSitterVO = petSitterSrv.getByPK(sitNo);
        		
        		req.setAttribute("petSitterVO", petSitterVO);
        		String url = "/front-end/petSitter/sitUpdatePage.jsp";
        		RequestDispatcher successView = req
        				.getRequestDispatcher(url);
        		successView.forward(req, res);
        		
        	} catch (Exception e) {
        		
        		errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
        		RequestDispatcher failureView = req
        				.getRequestDispatcher("/front-end/petSitter/listOneSitter.jsp");
        		failureView.forward(req, res);
        	}
        }
        
        if ("update".equals(action)) {
        	
        	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
            
        	try {
        		
        		String sitNo = req.getParameter("sitNo").trim();
        		String memNo = req.getParameter("memNo").trim();
        		
        		String sitInfo = req.getParameter("sitInfo").trim();
        		if (sitInfo == null || sitInfo.trim().length() == 0) {
        			errorMsgs.add("�O����T�ФŪť�");
        		}
        		
                String bankCode = req.getParameter("bankCode").trim();
                String bankCodeReg = "[0-9]{3}";
                if (bankCode == null || bankCode.trim().length() == 0) {
                	errorMsgs.add("�Ȧ�N�X�Фůd��");                	
                } else if (!bankCode.trim().matches(bankCodeReg)) {
                	errorMsgs.add("�п�J�T�X�Ȧ�N�X");
                }
                
                String bankAcc = req.getParameter("bankAcc").trim();
                String bankAccReg = "[0-9]{16}";
                if (bankAcc == null || bankAcc.trim().length() == 0) {
                	errorMsgs.add("�Ȧ�b���Фůd��");                	
                } else if (!bankAcc.trim().matches(bankAccReg)) {
                	errorMsgs.add("�п�J16�X�Ȧ�b��");
                }
                
                String srvSTime = req.getParameter("srvSTime").trim();
                String srvETime = req.getParameter("srvETime").trim();               
                Integer sitAccStatus = new Integer(req.getParameter("sitAccStatus").trim());
                Integer totalComm = new Integer(req.getParameter("totalComm").trim());
                Integer totalCus = new Integer(req.getParameter("totalCus").trim());
                Integer repeatCus = new Integer(req.getParameter("repeatCus").trim());
                
                PetSitterVO petSitterVO = new PetSitterVO();
                petSitterVO.setSitNo(sitNo);
                petSitterVO.setMemNo(memNo);
                petSitterVO.setSitInfo(sitInfo);
                petSitterVO.setSrvSTime(srvSTime);
                petSitterVO.setSrvETime(srvETime);
                petSitterVO.setBankCode(bankCode);
                petSitterVO.setBankAcc(bankAcc);
                petSitterVO.setSitAccStatus(sitAccStatus);
                petSitterVO.setTotalComm(totalComm);
                petSitterVO.setTotalCus(totalCus);
                petSitterVO.setRepeatCus(repeatCus);
        		
                if(!errorMsgs.isEmpty()) {
                	req.setAttribute("petSitterVO", petSitterVO);
                	RequestDispatcher failureView = req
                			.getRequestDispatcher("/front-end/petSitter/sitUpdatePage.jsp");
                	failureView.forward(req, res);
                	return;
                }
                
                PetSitterService petSitterSrv = new PetSitterService();
                petSitterVO = petSitterSrv.update(sitNo,memNo,sitInfo,srvSTime,srvETime,bankCode,bankAcc,sitAccStatus,totalComm,totalCus,repeatCus);
                
                req.setAttribute("petSitterVO", petSitterVO);
                String url = "/front-end/petSitter/listOneSitter.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
        	} catch (Exception e) {
        		
        		errorMsgs.add("�ק��ƥ���:"+e.getMessage());
                String url = "/front-end/petSitter/sitUpdatePage.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
        	}
            
        }
        
        if ("insert".equals(action)) {
        	
        	List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
        	
			try {
				
				String memNo = req.getParameter("memNo").trim();
	            
	            String sitInfo = req.getParameter("sitInfo").trim();
        		if (sitInfo == null || sitInfo.trim().length() == 0) {
        			errorMsgs.add("�O����T�ФŪť�");
        		}
	            
	            String bankCode = req.getParameter("bankCode").trim();
	            String bankCodeReg = "[0-9]{3}";
	            if (bankCode == null || bankCode.trim().length() == 0) {
	            	errorMsgs.add("�Ȧ�N�X�Фůd��");                	
	            } else if (!bankCode.trim().matches(bankCodeReg)) {
	            	errorMsgs.add("�п�J�T�X�Ȧ�N�X");
	            }
	            
	            String bankAcc = req.getParameter("bankAcc").trim();
	            String bankAccReg = "[0-9]{16}";
	            if (bankAcc == null || bankAcc.trim().length() == 0) {
	            	errorMsgs.add("�Ȧ�b���Фůd��");                	
	            } else if (!bankAcc.trim().matches(bankAccReg)) {
	            	errorMsgs.add("�п�J16�X�Ȧ�b��");
	            }
	            
	            String srvSTime = req.getParameter("srvSTime").trim();
	            if (srvSTime == null) {
	            	errorMsgs.add("�п�ܥi�A�ȶ}�l�ɶ�");                	
	            }
	            
	            String srvETime = req.getParameter("srvETime").trim();
//	            if (srvETime == null) {
//	            	errorMsgs.add("�п�ܥi�A�ȵ����ɶ�");                	
//	            }
	            
	            PetSitterVO petSitterVO = new PetSitterVO();
	            petSitterVO.setMemNo(memNo);
	            petSitterVO.setSitInfo(sitInfo);
	            petSitterVO.setSrvSTime(srvSTime);
	            petSitterVO.setSrvETime(srvETime);
	            petSitterVO.setBankCode(bankCode);
	            petSitterVO.setBankAcc(bankAcc);
	            
	            if (!errorMsgs.isEmpty()) {
					req.setAttribute("petSitterVO", petSitterVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/petSitter/addPetSitter.jsp");
					failureView.forward(req, res);
					return;
				}
	            
	            PetSitterService petSitterSrv = new PetSitterService();
	            petSitterVO = petSitterSrv.insert(memNo,sitInfo,srvSTime,srvETime,bankCode,bankAcc);
	            req.setAttribute("petSitterVO", petSitterVO);
	            String url = "/front-end/petSitter/listOneSitter.jsp";
	            RequestDispatcher successView = req.getRequestDispatcher(url);
	            successView.forward(req, res);
	            
			} catch (Exception e) {
				
				errorMsgs.add("�s�W�O������:"+e.getMessage());
                String url = "/front-end/petSitter/addPetSitter.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
                successView.forward(req, res);
                
			}
            
        }
        
        //--------------��x(back-end)--------------
        
        
        
        
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
