package com.novel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.novel.core.interceptor.AuthPassport;
import com.novel.model.Article;
import com.novel.model.Novel;
import com.novel.model.Paragraph;
import com.novel.model.Section;
import com.novel.model.Type;
import com.novel.service.EditService;

@Controller
@RequestMapping(value="/admin/edit")
public class EditAdminController {
	
	@Resource(name="editService")
	private EditService editService;
	
	@AuthPassport
	@RequestMapping(value="")
	public ModelAndView editData(Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/main");
		List<Type> types = editService.getTypes(1);
		
		int tid = types.get(0).getId();
		List<Article> articles = editService.getArticlesByTid(tid);
		
		int aid = articles.get(0).getId();
		List<Section> sections = editService.getSectionsByAid(aid);
		
		int sid = sections.get(0).getId();
		List<Paragraph> paragraphes = editService.getParagraphesBySid(sid);
		
		mav.addObject("page", page);
		mav.addObject("tid", tid);
		mav.addObject("aid", aid);
		mav.addObject("sid", sid);
		mav.addObject("types", types);
		mav.addObject("articles", articles);
		mav.addObject("sections", sections);
		mav.addObject("paragraphes", paragraphes);
		return mav;
	}
	
	@AuthPassport
	@RequestMapping(value="/listNovel/easy", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> Novel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String tid_str = request.getParameter("tid");
		String aid_str = request.getParameter("aid");
		String sid_str = request.getParameter("sid");
		
		int tid = 0;
		int aid = 0;
		int sid = 0;
		
		if(sid_str != null && !sid_str.equals("")) {
			sid = Integer.parseInt(sid_str);
		}
		if(aid_str != null && !aid_str.equals("")) {
			aid = Integer.parseInt(aid_str);
		}
		if(tid_str != null && !tid_str.equals("")) {
			tid = Integer.parseInt(tid_str);
		}
		
		List<Article> articles = null;
		List<Section> sections = null;
		List<Paragraph> paragraphes = null;
		
		if(tid != 0) {
			articles = editService.getArticlesByTid(tid);
			if(articles.size() > 0)
				aid = articles.get(0).getId();
		}
		if(aid != 0) {
			sections = editService.getSectionsByAid(aid);
			if(sections.size() > 0)
				sid = sections.get(0).getId();
		}
		if(sid != 0) {
			paragraphes = editService.getParagraphesBySid(sid);
		}
		
		if(articles != null || sections != null || paragraphes != null){
			modelMap.put("success", true);
			modelMap.put("articles", articles);
			modelMap.put("sections", sections);
			modelMap.put("paragraphes", paragraphes);
			
		} else {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/addArticle/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> addArticle(HttpServletRequest request,HttpServletResponse response, Article article) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(article.getArticle() == null || article.getArticle().equals("") || article.getArticle().length() > 5) {
			modelMap.put("success", false);
		} else if(article.getTid() == null || article.getTid() <= 0){
			modelMap.put("success", false);
		} else {
			Integer id = editService.addArticle(article);
			if(id != null) {
				modelMap.put("success", true);
				modelMap.put("id", id);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/updateArticle/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> updateArticle(HttpServletRequest request,HttpServletResponse response, Article article) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(article.getArticle() == null || article.getArticle().equals("") || article.getArticle().length() > 5) {
			modelMap.put("success", false);
		} else if(article.getId() == null || article.getId() <= 0){
			modelMap.put("success", false);
		} else {
			int line = editService.updateArticle(article);
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/deleteArticle/easy", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> deleteArticle(HttpServletRequest request,HttpServletResponse response, Integer aid) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(aid == null || aid <= 0){
			modelMap.put("success", false);
		} else {
			int line = editService.deleteArticle(aid);
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/addSection/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> addSection(HttpServletRequest request,HttpServletResponse response, Section section) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(section.getSection() == null || section.getSection().equals("") || section.getSection().length() > 5) {
			modelMap.put("success", false);
		} else if(section.getAid() == null || section.getAid() <= 0){
			modelMap.put("success", false);
		} else {
			Integer id = editService.addSection(section);
			if(id != null) {
				modelMap.put("success", true);
				modelMap.put("id", id);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/updateSection/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> updateSection(HttpServletRequest request,HttpServletResponse response, Section section) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(section.getSection() == null || section.getSection().equals("") || section.getSection().length() > 5) {
			modelMap.put("success", false);
		} else if(section.getId() == null || section.getId() <= 0){
			modelMap.put("success", false);
		} else {
			int line = editService.updateSection(section);
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/deleteSection/easy", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> deleteSection(HttpServletRequest request,HttpServletResponse response, Integer sid) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(sid == null || sid <= 0){
			modelMap.put("success", false);
		} else {
			int line = editService.deleteSection(sid);
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/addParagraph/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> addParagraph(HttpServletRequest request,HttpServletResponse response, Novel novel) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		if(novel.getParagraph() == null || novel.getParagraph().equals("") || novel.getParagraph().length() > 500) {
			modelMap.put("success", false);
		} else if(novel.getSid() == null || novel.getSid() <= 0){
			modelMap.put("success", false);
		} else {
			Section section = editService.getSection(novel.getSid());
			Article article = editService.getAricle(section.getAid());
			Type type = editService.getType(article.getTid());
			
			novel.setTid(type.getId());
			novel.setType(type.getType());
			novel.setAid(article.getId());
			novel.setArticle(article.getArticle());
			novel.setSection(section.getSection());
			
			Integer id = editService.addParagraph(novel);
			if(id != null) {
				modelMap.put("success", true);
				modelMap.put("id", id);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/updateParagraph/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> updateParagraph(HttpServletRequest request,HttpServletResponse response, Novel novel) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(novel.getParagraph() == null || novel.getParagraph().equals("") || novel.getParagraph().length() > 500) {
			modelMap.put("success", false);
		} else if(novel.getPid() == null || novel.getPid() <= 0){
			modelMap.put("success", false);
		} else {
			Integer line = this.paragraphUpdate(novel, false);
			
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/moveParagraph/easy", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> moveParagraph(HttpServletRequest request,HttpServletResponse response, Novel novel) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(novel.getPid() == null || novel.getPid() <= 0) {
			modelMap.put("success", false);
		} else if(novel.getSid() == null || novel.getSid() <= 0) {
			modelMap.put("success", false);
		} else {
			Integer line = this.paragraphUpdate(novel, true);
			
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/deleteParagraph/easy", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> deleteParagraph(HttpServletRequest request,HttpServletResponse response, Integer pid) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(pid == null || pid <= 0){
			modelMap.put("success", false);
		} else { 
			Paragraph paragraph = editService.getParagraphByPid(pid);
			Section section = editService.getSection(paragraph.getSid());
			Article article = editService.getAricle(section.getAid());
			Type type = editService.getType(article.getTid());
			
			Novel novel = new Novel();
			novel.setPid(pid);
			novel.setParagraph(paragraph.getParagraph());
			novel.setTid(type.getId());
			novel.setType(type.getType());
			novel.setAid(article.getId());
			novel.setArticle(article.getArticle());
			novel.setSid(section.getId());
			novel.setSection(section.getSection());
			
			int line = editService.deleteParagraph(novel);
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/resetIndex/easy", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> resetIndex() throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		editService.resetIndex();
		
		modelMap.put("success", true);
		return modelMap; 
	}
	
	/**
	 * 更新paragraph
	 * @param novel
	 * @param isMove
	 * @return
	 * @throws Exception
	 */
	private Integer paragraphUpdate(Novel novel, boolean isMove) throws Exception {
		Paragraph paragraph = editService.getParagraphByPid(novel.getPid());
		
		Integer sid = isMove ? novel.getSid() : paragraph.getSid();
		
		Section section = editService.getSection(sid);
		Article article = editService.getAricle(section.getAid());
		Type type = editService.getType(article.getTid());
		
		novel.setTid(type.getId());
		novel.setType(type.getType());
		novel.setAid(article.getId());
		novel.setArticle(article.getArticle());
		novel.setSid(section.getId());
		novel.setSection(section.getSection());
		novel.setPid(paragraph.getId());
		novel.setParagraph(paragraph.getParagraph());
		
		return editService.updateParagraph(novel);
	}
}
