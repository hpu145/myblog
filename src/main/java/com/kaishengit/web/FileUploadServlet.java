package com.kaishengit.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

@WebServlet("/file/upload")
@MultipartConfig
public class FileUploadServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Part part = req.getPart("file_key");
		String params = part.getHeader("content-Disposition");
		String fileName = params.split(";")[2].split("\"")[1];
		
		String newName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
		
		File file = new File("D:/upload",newName);
		
		InputStream in = part.getInputStream();
		OutputStream out = new FileOutputStream(file);
		
		IOUtils.copy(in, out);
		out.flush();
		out.close();
		in.close();
		
		Map<String,Object> maps = new HashMap<>();
		maps.put("success", true);
		maps.put("file_path", "http://localhost:8080/avatar?name=" + newName);
				             //http://192.168.1.153:8080
		sendJson(maps, resp);
		
	}

}
