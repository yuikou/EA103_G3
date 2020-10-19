package com.petSitter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.petSitter.model.*;
import com.sitOrder.model.*;

@WebServlet("/PetSitterServlet")
public class PetSitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PetSitterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        
		HttpSession session = req.getSession();
		

        String action = req.getParameter("action");

        //--------------�e�x(front-end)--------------
        
        if ("getOneSitter_forDisplay".equals(action) || "getOneSitter_DisplayForMem".equals(action)) { //from sitterFront.jsp

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);
            
            
            try {
                
                String sitNo = (String) session.getAttribute("sitNo");
                if (sitNo == null) {
                	sitNo = (String) req.getParameter("sitNo");
                }
                
                                
                PetSitterService petSitSrv = new PetSitterService();
                PetSitterVO petSitterVO = petSitSrv.getByPK(sitNo);
                
                if (petSitterVO  == null) {
                    errorMsgs.add("�d�L���");
                }
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/front-end/sitterFront.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                
                String url = null;
				if ("getOneSitter_forDisplay".equals(action))
					url = "/front-end/petSitter/listOneSitter.jsp";
				else if ("getOneSitter_DisplayForMem".equals(action))
					url = "/front-end/petSitter/listOneSitterForMem.jsp";
                req.setAttribute("petSitterVO", petSitterVO);
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
        		
        		String sitNo = (String) session.getAttribute("sitNo");
        		PetSitterService petSitterSrv = new PetSitterService();
        		PetSitterVO petSitterVO = petSitterSrv.getByPK(sitNo);
        		
        		req.setAttribute("petSitterVO", petSitterVO);
        		String url = "/front-end/petSitter/updatePetSitter.jsp";
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
        		
        		String memNo = (String) session.getAttribute("memNo");
        		String sitNo = (String) session.getAttribute("sitNo");
        		
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
                Double totalComm = Double.parseDouble(req.getParameter("totalComm"));
                Double totalCus = Double.parseDouble(req.getParameter("totalCus"));
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
                			.getRequestDispatcher("/front-end/petSitter/updatePetSitter.jsp");
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
                String url = "/front-end/petSitter/updatePetSitter.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
        	}
            
        }
        
        if ("insert".equals(action)) {
        	
        	List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				
				String memNo = (String) session.getAttribute("memNo");
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
	            
	            String srvSTime = req.getParameter("srvSTime");
//	            if (srvSTime == null) {
//	            	errorMsgs.add("�п�ܥi�A�ȶ}�l�ɶ�");                	
//	            }
	            
	            String srvETime = req.getParameter("srvETime");
//	            if (srvETime == null) {
//	            	errorMsgs.add("�п�ܥi�A�ȵ����ɶ�");                	
//	            }
	            
	            Integer sitAccStatus = 0;
	            Double totalComm = 0.0;
	            Double totalCus = 0.0;
	            Integer repeatCus = 0;

	            
	            PetSitterVO petSitterVO = new PetSitterVO();
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
	            
	            if (!errorMsgs.isEmpty()) {
					req.setAttribute("petSitterVO", petSitterVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/petSitter/addPetSitter.jsp");
					failureView.forward(req, res);
					return;
				}
	            
	            PetSitterService petSitterSrv = new PetSitterService();
	            petSitterVO = petSitterSrv.insert(memNo,sitInfo,srvSTime,srvETime,bankCode,bankAcc,sitAccStatus,totalComm,totalCus,repeatCus);
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
        
        if ("getOneSitter_forBack".equals(action)) { //from sitterBack.jsp

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                
                String sitNo = req.getParameter("sitNo");
                if (sitNo == null || (sitNo.trim().length() == 0)) {
                    errorMsgs.add("�п�J�O���s��");
                }
                
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/back-end/sitterBack.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                
                String sitNoReg = "[S][0-9]{3}";
                if(!sitNo.trim().matches(sitNoReg)) {
                    errorMsgs.add("�п�J���T�榡");
                }
                
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/back-end/sitterBack.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                                
                PetSitterService petSitSrv = new PetSitterService();
                PetSitterVO petSitterVO = petSitSrv.getByPK(sitNo);
                
                if (petSitterVO  == null) {
                    errorMsgs.add("�d�L���");
                }
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/back-end/sitterBack.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                                
                req.setAttribute("petSitterVO", petSitterVO);
                String url = "/back-end/petSitter/listOneSitterBack.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
            } catch (Exception e) {
                
                errorMsgs.add("�L�k���o���:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/sitterBack.jsp");
                failureView.forward(req, res);                 
            }

        }
       
        
        if ("change_sitAccStatus".equals(action)) {
        	
        	List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);
            
            String sitNo = req.getParameter("sitNo");
            PetSitterService petSitterSrv = new PetSitterService();
            PetSitterVO petSitterVO = petSitterSrv.getByPK(sitNo);
            
            String memNo = petSitterVO.getMemNo();
            String sitInfo = petSitterVO.getSitInfo();
            String srvSTime = petSitterVO.getSrvSTime();
            String srvETime = petSitterVO.getSrvETime();
            String bankCode = petSitterVO.getBankCode();
            String bankAcc = petSitterVO.getBankAcc();
            Integer sitAccStatus = petSitterVO.getSitAccStatus();
            Double totalComm = petSitterVO.getTotalComm();
            Double totalCus = petSitterVO.getTotalCus();
            Integer repeatCus = petSitterVO.getRepeatCus();
            
            
            if (petSitterVO != null) {
            	if (sitAccStatus == 0) {	
            		sitAccStatus = 1;
            	} else {
            		sitAccStatus = 0;
            	}
            }
            System.out.println(sitAccStatus);
            petSitterVO = petSitterSrv.update(sitNo, memNo, sitInfo, srvSTime, srvETime, bankCode, bankAcc, sitAccStatus, totalComm, totalCus, repeatCus);
            
            req.setAttribute("petSitterVO", petSitterVO);
            String url = "/back-end/petSitter/listOneSitterBack.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
            
        }
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
