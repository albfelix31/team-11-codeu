package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.ImageUrl;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/**
 * Handles fetching images to display
 */

 @WebServlet("/images")
 public class ImagesServlet extends HttpServlet {

   private Datastore datastore;

   @Override
   public void init() {
     datastore = new Datastore();
   }

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
   response.setContentType("application/json");

   String id = request.getParameter("id");
   if (id == null || id.equals("")) {
     // Request is invalid, return empty array
     response.getWriter().println("[]");
     return;
   }

   List<ImageUrl> images = datastore.getImage(id);
   Gson gson = new Gson();
   String json = gson.toJson(images);

   response.getWriter().println(json);
 }


}
