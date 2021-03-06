package com.petSitter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.member.model.MemService;
import com.petSitter.model.*;
import com.sitOrder.model.*;

@WebServlet("/petSitter/petSitter.do")
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

        //--------------前台(front-end)--------------
        
        if ("getOneSitter_forDisplay".equals(action)) { //from sitterFront.jsp

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);
            
            
            try {
                //屬於擁有保姆資格的會員
                String sessionSitNo = (String) session.getAttribute("sessionSitNo");
                                
                PetSitterService petSitSrv = new PetSitterService();
                PetSitterVO petSitterVO = petSitSrv.getByPK(sessionSitNo);
                
                if (petSitterVO  == null) {
                    errorMsgs.add("查無資料");
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
                
                errorMsgs.add("無法取得資料:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/front-end/sitterFront.jsp");
                failureView.forward(req, res);                 
            }

        }
        
        if ("getOneSitter_DisplayForMem".equals(action)) { //from sitterFront.jsp

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);
            
            
            try {
                // 頁面點選保母後
                String sitNo = (String) req.getParameter("sitNo");
                                
                PetSitterService petSitSrv = new PetSitterService();
                PetSitterVO petSitterVO = petSitSrv.getByPK(sitNo);
                
                if (petSitterVO  == null) {
                    errorMsgs.add("查無資料");
                }
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/front-end/sitterFront.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                
                req.setAttribute("petSitterVO", petSitterVO);
                String url = "/front-end/petSitter/listOneSitterForMem.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
            } catch (Exception e) {
                
                errorMsgs.add("無法取得資料:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/front-end/sitterFront.jsp");
                failureView.forward(req, res);                 
            }

        }

        

        if ("getSitter_forUpdate".equals(action)) { //from listOneSitter.jsp

        	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
        	
        	try {
        		
        		String sessionSitNo = (String) session.getAttribute("sessionSitNo");
        		PetSitterService petSitterSrv = new PetSitterService();
        		PetSitterVO petSitterVO = petSitterSrv.getByPK(sessionSitNo);
        		
        		req.setAttribute("petSitterVO", petSitterVO);
        		String url = "/front-end/petSitter/updatePetSitter.jsp";
        		RequestDispatcher successView = req
        				.getRequestDispatcher(url);
        		successView.forward(req, res);
        		
        	} catch (Exception e) {
        		
        		errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
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
        		String sessionSitNo = (String) session.getAttribute("sessionSitNo");
        		
        		String sitInfo = req.getParameter("sitInfo").trim();
        		if (sitInfo == null || sitInfo.trim().length() == 0) {
        			errorMsgs.add("保母資訊請勿空白");
        		}
        		
                String bankCode = req.getParameter("bankCode").trim();
                String bankCodeReg = "[0-9]{3}";
                if (bankCode == null || bankCode.trim().length() == 0) {
                	errorMsgs.add("銀行代碼請勿留白");                	
                } else if (!bankCode.trim().matches(bankCodeReg)) {
                	errorMsgs.add("請輸入三碼銀行代碼");
                }
                
                String bankAcc = req.getParameter("bankAcc").trim();
                String bankAccReg = "[0-9]{16}";
                if (bankAcc == null || bankAcc.trim().length() == 0) {
                	errorMsgs.add("銀行帳號請勿留白");                	
                } else if (!bankAcc.trim().matches(bankAccReg)) {
                	errorMsgs.add("請輸入16碼銀行帳號");
                }
                
                String srvSTime = req.getParameter("srvSTime").trim();
                String srvETime = req.getParameter("srvETime").trim();               
                Integer sitAccStatus = new Integer(req.getParameter("sitAccStatus").trim());
                Double totalComm = Double.parseDouble(req.getParameter("totalComm"));
                Double totalCus = Double.parseDouble(req.getParameter("totalCus"));
                Integer repeatCus = new Integer(req.getParameter("repeatCus").trim());
                
                PetSitterVO petSitterVO = new PetSitterVO();
                petSitterVO.setSitNo(sessionSitNo);
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
                String sitNo = sessionSitNo;
                PetSitterService petSitterSrv = new PetSitterService();
                petSitterVO = petSitterSrv.update(sitNo,memNo,sitInfo,srvSTime,srvETime,bankCode,bankAcc,sitAccStatus,totalComm,totalCus,repeatCus);
                
                req.setAttribute("petSitterVO", petSitterVO);
                String url = "/front-end/petSitter/listOneSitter.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);
                
        	} catch (Exception e) {
        		
        		errorMsgs.add("修改資料失敗:"+e.getMessage());
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
        			errorMsgs.add("保母資訊請勿空白");
        		}
	            
	            String bankCode = req.getParameter("bankCode").trim();
	            String bankCodeReg = "[0-9]{3}";
	            if (bankCode == null || bankCode.trim().length() == 0) {
	            	errorMsgs.add("銀行代碼請勿留白");                	
	            } else if (!bankCode.trim().matches(bankCodeReg)) {
	            	errorMsgs.add("請輸入三碼銀行代碼");
	            }
	            
	            String bankAcc = req.getParameter("bankAcc").trim();
	            String bankAccReg = "[0-9]{16}";
	            if (bankAcc == null || bankAcc.trim().length() == 0) {
	            	errorMsgs.add("銀行帳號請勿留白");                	
	            } else if (!bankAcc.trim().matches(bankAccReg)) {
	            	errorMsgs.add("請輸入16碼銀行帳號");
	            }
	            
	            String srvSTime = req.getParameter("srvSTime");
//	            if (srvSTime == null) {
//	            	errorMsgs.add("請選擇可服務開始時間");                	
//	            }
	            
	            String srvETime = req.getParameter("srvETime");
//	            if (srvETime == null) {
//	            	errorMsgs.add("請選擇可服務結束時間");                	
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
					req.setAttribute("petSitterVO", petSitterVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/petSitter/addPetSitter.jsp");
					failureView.forward(req, res);
					return;
				}
	            
	            PetSitterService petSitterSrv = new PetSitterService();
	            petSitterVO = petSitterSrv.insert(memNo,sitInfo,srvSTime,srvETime,bankCode,bankAcc,sitAccStatus,totalComm,totalCus,repeatCus);
	            req.setAttribute("petSitterVO", petSitterVO);
	            
	            
	            //會員升級成保母
	            MemService memSrv = new MemService();
	            memSrv.upgradeMemToSit(memNo);
	            
	            String url = "/front-end/petSitter/listOneSitter.jsp";
	            RequestDispatcher successView = req.getRequestDispatcher(url);
	            successView.forward(req, res);
	            
			} catch (Exception e) {
				
				errorMsgs.add("新增保母失敗:"+e.getMessage());
                String url = "/front-end/petSitter/addPetSitter.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
                successView.forward(req, res);
                
			}
        }
        
        
        
        //--------------後台(back-end)--------------
        
        if ("getOneSitter_forBack".equals(action)) { //from sitterBack.jsp

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                
                String sitNo = req.getParameter("sitNo");
                if (sitNo == null || (sitNo.trim().length() == 0)) {
                    errorMsgs.add("請輸入保母編號");
                }
                
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/back-end/sitterBack.jsp");
                    failureView.forward(req, res);
                    return;                 
                }
                
                String sitNoReg = "[S][0-9]{3}";
                if(!sitNo.trim().matches(sitNoReg)) {
                    errorMsgs.add("請輸入正確格式");
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
                    errorMsgs.add("查無資料");
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
                
                errorMsgs.add("無法取得資料:" + e.getMessage());
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
