
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemService;
import com.member.model.MemVO;
import com.sitLic.model.SitLicService;
import com.sitLic.model.SitLicVO;
import com.sitPhoto.model.SitPhotoService;
import com.sitPhoto.model.SitPhotoVO;

@WebServlet("/picReader")
public class picReader extends HttpServlet {
	private static final long serialVersionUID = 8772772431614292284L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		
		/* ----------�_�@---------- */
		if ("sitLic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String licNo = req.getParameter("licNo");
						
				/***************************2.�}�l�d�߸��*****************************************/
				SitLicService slSvc = new SitLicService();
				SitLicVO sitLic = slSvc.getOneLicByPK(licNo);
						
				// �����g�X
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]��InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(sitLic.getLicPic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
					out.flush();
				}
				bin.close();
						
					
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitLic/showOneSitLic.jsp");
				failureView.forward(req, res);
			}
					
		}
		
		if ("sitFollow".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String memNo = req.getParameter("memNo");
						
				/***************************2.�}�l�d�߸��*****************************************/
				MemService slSvc = new MemService();
				MemVO memVo = slSvc.getOneMem(memNo);
				
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				ByteArrayInputStream bin = new ByteArrayInputStream(memVo.getMemPhoto());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitFollow/listSitFollow.jsp");
				failureView.forward(req, res);
			}
					
		}
		
		/*---------�ɴ�---------*/
		if ("sitPhoto".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String sitPNo = req.getParameter("sitPNo");
				/*************************** 2.�}�l�d�߸�� *****************************************/
				SitPhotoService sitPhotoSrv = new SitPhotoService();
				SitPhotoVO sitPhotoVO = sitPhotoSrv.getByPK(sitPNo);

				// �����g�X
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();

				// byte[]��InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(sitPhotoVO.getSitPhoto());
				byte[] buffer = new byte[4 * 1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				bin.close();

			} catch (Exception e) {

				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
