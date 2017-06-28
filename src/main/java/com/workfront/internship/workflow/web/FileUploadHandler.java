package com.workfront.internship.workflow.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Vahag on 6/10/2017
 */
public class FileUploadHandler extends HttpServlet {
    private final String UPLOAD_DIRECTORY = "images/users";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        //process only if its multipart postContent
//        if(ServletFileUpload.isMultipartContent(request)){
//            try {
//                List<FileItem> multiparts = new ServletFileUpload(
//                        new DiskFileItemFactory()).parseRequest(request);
//
//                for(FileItem item : multiparts){
//                    if(!item.isFormField()){
//                        String name = new File(item.getName()).getName();
//                        item.write( new File(request.getContextPath() + UPLOAD_DIRECTORY + File.separator + name));
//                    }
//                }
//
//                //File uploaded successfully
//                request.setAttribute("message", "File Uploaded Successfully");
//            } catch (Exception ex) {
//                request.setAttribute("message", "File Upload Failed due to " + ex);
//            }
//
//        }else{
//            request.setAttribute("message",
//                    "Sorry this Servlet only handles file upload request");
//        }

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);

    }

}
