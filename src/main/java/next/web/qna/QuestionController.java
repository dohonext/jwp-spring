package next.web.qna;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import next.model.qna.Question;
import next.service.qna.QnaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"", "/questions"})
public class QuestionController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Resource(name = "qnaService")
	private QnaService qnaService;

	@RequestMapping("")
	public String list(Model model) {
		model.addAttribute("questions", qnaService.findAll());
		return "qna/list";
	}
	
	@RequestMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		model.addAttribute("question", qnaService.findById(id));
		return "qna/show";
	}
	
	@RequestMapping("/form")
	public String form(Model model) {
		model.addAttribute("question", new Question());
		model.addAttribute("url","/question");
		return "qna/form";
	}
	
	@RequestMapping("/{id}/form")
	public String modify(Model model, @PathVariable("id") long id) {
		model.addAttribute("question", qnaService.findById(id));
		model.addAttribute("url", "/"+id+"/form");
		return "qna/form";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String save(@Valid Question question, BindingResult bindingResult) {
		logger.debug("Question : {}", question);
		if (bindingResult.hasFieldErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				logger.debug("field : {}, error code : {}", error.getField(), error.getCode());
			}
			return "qna/form";
		}
		qnaService.save(question);
		return "redirect:/";
	}
	
	@RequestMapping(value="/{id}/form", method=RequestMethod.POST)
	public String modify(@PathVariable("id") long id, @Valid Question question, BindingResult bindingResult) {
		logger.debug("Question : {}", question);
		if (bindingResult.hasFieldErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				logger.debug("field : {}, error code : {}", error.getField(), error.getCode());
			}
			return "qna/form";
		}
		question.setQuestionId(id);
		qnaService.modify(question);
		return "redirect:/";
	}
}
