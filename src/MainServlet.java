

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.Base64;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

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

		String imageString = request.getParameter("image");
		byte[] imageByteArray = Base64.getMimeDecoder().decode(imageString); 
		FileOutputStream fos = new FileOutputStream("temp_photo.jpg");
	    fos.write(imageByteArray);
	    fos.close();
	    
	    ITesseract instance = new Tesseract();  // JNA Interface Mapping
       // File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
	    instance.setDatapath("C://Users//Jesus//workspace//coolbeer-server//tessdata");
	    //instance.setDatapath(tessDataFolder.getParent());
        File imageFile = new File("temp_photo.jpg");
        try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
		
		
	
		
		/*HttpEntity entity = new InputStreamEntity(request.getInputStream(), request.getContentLength());

		try {
			JSONObject imageJson = new JSONObject(EntityUtils.toString(entity));
			String imageContent = imageJson.getString("image").replace("\\","");
			System.out.println(imageJson.getString("image").toString());

			byte[] decoded = Base64.getDecoder().decode(imageContent.getBytes(StandardCharsets.UTF_8));
			FileUtils.writeByteArrayToFile(new File("pathname"), decoded);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		/*String responseXml = EntityUtils.toString(request.getEntity());
		EntityUtils.consume(httpResponse.getEntity());*/
		
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
	        File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
	        instance.setDatapath(tessDataFolder.getParent());
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
