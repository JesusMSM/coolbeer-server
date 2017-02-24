

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Blob;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MainServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//uploadImage(requestParamsToJSON(request));
		doGet(request, response);
	}

	public void uploadImage(JSONObject request) throws IOException
	{
	    
	    try {
	    	String imageInString = (String) request.get("image");
	        // Create temp file.
	        File temp = File.createTempFile("photo", ".jpg");

	        // Delete temp file when program exits.
	        temp.deleteOnExit();

	        // Write to temp file
	        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
	        out.write(imageInString);
	        out.close();
	        
	        ITesseract instance = new Tesseract();  // JNA Interface Mapping
	        String result = instance.doOCR(temp);
	        System.out.println(result);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }


	}
	
	public JSONObject requestParamsToJSON(HttpServletRequest req) {
		  JSONObject jsonObj = new JSONObject();
		  Map<String,String[]> params = req.getParameterMap();
		  for (Map.Entry<String,String[]> entry : params.entrySet()) {
		    String v[] = entry.getValue();
		    Object o = (v.length == 1) ? v[0] : v;
		    try{
		    	jsonObj.put(entry.getKey(), o);
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		  }
		  return jsonObj;
		}

}
