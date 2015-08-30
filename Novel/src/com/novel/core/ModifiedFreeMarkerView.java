package com.novel.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.novel.util.HtmlPath;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ModifiedFreeMarkerView extends FreeMarkerView{

	@Override
	protected void doRender(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Expose model to JSP tags (as request attributes).
		exposeModelAsRequestAttributes(model, request);
		// Expose all standard FreeMarker hash models.
		SimpleHash fmModel = buildTemplateModel(model, request, response);
		
		// Grab the locale-specific version of the template.
		Locale locale = RequestContextUtils.getLocale(request);
		
		/*
		 * 默认生成静态文件,除非在编写ModelAndView时指定CREATE_HTML = false,
		 * 这样对静态文件生成的粒度控制更细一点
		 * 例如：ModelAndView mav = new ModelAndView("search");
		 *	   mav.addObject("CREATE_HTML", false); 
		 */
		if(Boolean.FALSE.equals(model.get("CREATE_HTML"))){
			processTemplate(getTemplate(locale), fmModel, response);
		}else{
			createHTML(getTemplate(locale), fmModel, request, response);
		}
	}

	public void createHTML(Template template, SimpleHash model,HttpServletRequest request,
		HttpServletResponse response) throws IOException, TemplateException, ServletException {
		//站点根目录的绝对路径
		String basePath = request.getSession().getServletContext().getRealPath("/");
		String requestHTML = HtmlPath.getRequestHTML(request);
		//静态页面绝对路径
		String htmlPath = basePath + requestHTML;
		
        File htmlFile = new File(htmlPath);
        if(!htmlFile.getParentFile().exists()){
        	htmlFile.getParentFile().mkdirs();
        }
        if(!htmlFile.exists()){
        	htmlFile.createNewFile();
        }
        
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
        //处理模版  
        template.process(model, out);
        out.flush();
        out.close();
        
        /*将请求转发到生成的htm文件*/
        request.getRequestDispatcher(requestHTML).forward(request, response);
	}
}
