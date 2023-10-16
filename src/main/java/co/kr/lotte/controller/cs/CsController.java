package co.kr.lotte.controller.cs;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.service.CsCateService;
import co.kr.lotte.service.CsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@Controller
public class CsController {

    @Autowired
    private CsService csService;

    @Autowired
    private CsCateService csCateService;

    @GetMapping("/cs/index")
    public String index() {
        return "/cs/index";
    }
    @GetMapping("/cs/faq/list")
    public String faqList() {
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView() {
        return "/cs/faq/view";
    }

    @GetMapping("/cs/notice/list")
    public String noticeList(Model model, CsPageRequestDTO csPageRequestDTO) {
        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);

        log.info("csPageResponseDTO pg : "+ csPageResponseDTO.getPg());
        log.info("csPageResponseDTO size : "+ csPageResponseDTO.getSize());
        log.info("csPageResponseDTO total : "+ csPageResponseDTO.getTotal());
        log.info("csPageResponseDTO start : "+ csPageResponseDTO.getStart());
        log.info("csPageResponseDTO end : "+ csPageResponseDTO.getEnd());
        log.info("csPageResponseDTO prev : "+ csPageResponseDTO.isPrev());
        log.info("csPageResponseDTO next : "+ csPageResponseDTO.isNext());

        model.addAttribute(csPageResponseDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String noticeView(Model model ,int bno) {
        BoardDTO boardDTO = csService.findByBno(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/cs/notice/view";
    }

    @GetMapping("/cs/qna/list")
    public String qnaList(Model model, CsPageRequestDTO csPageRequestDTO) {
        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);

        log.info("csPageResponseDTO cate : "+ csPageRequestDTO.getCate());
        log.info("csPageResponseDTO pg : "+ csPageResponseDTO.getPg());
        log.info("csPageResponseDTO size : "+ csPageResponseDTO.getSize());
        log.info("csPageResponseDTO total : "+ csPageResponseDTO.getTotal());
        log.info("csPageResponseDTO start : "+ csPageResponseDTO.getStart());
        log.info("csPageResponseDTO end : "+ csPageResponseDTO.getEnd());
        log.info("csPageResponseDTO prev : "+ csPageResponseDTO.isPrev());
        log.info("csPageResponseDTO next : "+ csPageResponseDTO.isNext());
        model.addAttribute(csPageResponseDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/view")
    public String qnaView(Model model, int bno) {

        BoardDTO boardDTO = csService.findByBno(bno);
        model.addAttribute("boardDTO", boardDTO);

        return "/cs/qna/view";
    }

    @GetMapping("/cs/qna/write")
    public String qnaWrite(HttpServletRequest request, Model model, String cate) {

        List<BoardCateEntity> cates = csCateService.getCate();
        model.addAttribute("cates", cates);

        return "/cs/qna/write";
    }

    @PostMapping("cs/qna/write")
    public String qnaWrite(HttpServletRequest request, BoardDTO dto)  {
        log.info(dto.toString());
        csService.save(dto);
        return "redirect:/cs/qna/list";
    }

    // cate
    @GetMapping("/cs/cate")
    @ResponseBody
    public List<BoardTypeEntity> csCate(String optionValue){

        log.info(optionValue);

        return csCateService.findByCate(optionValue);

    }



}
