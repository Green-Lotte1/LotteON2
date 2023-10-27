package co.kr.lotte.controller.cs;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.BoardTypeDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardFileEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.service.CsCateService;
import co.kr.lotte.service.CsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class CsController {

    @Autowired
    private CsService csService;

    @Autowired
    private CsCateService csCateService;

    @GetMapping(value = {"/cs/index", "/cs/"})
    public String index(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        Model model) {

        List<BoardDTO> noticeBoard = csService.getNoticeBoard(page, size);
        log.info("noticeBoard :" + noticeBoard );

        List<BoardDTO> qnaBoard = csService.getQnaBoard(page, size);
        log.info("qnaBoard :" + qnaBoard );


        model.addAttribute("noticeBoard", noticeBoard);
        model.addAttribute("qnaBoard", qnaBoard);
        return "/cs/index";
    }


    @GetMapping("/cs/faq/list")
    public String faqList(Model model, String cate) {
        List<BoardDTO> dtoList = csService.findByCateForFaq(cate);
        List<BoardTypeDTO> boardTypeDTOs = csCateService.findByCateTypeDTOS(cate);
        for (BoardTypeDTO boardTypeDTO : boardTypeDTOs) {
            List<BoardDTO> boardDTOS = new ArrayList<>();
            int i = 0;
            for (BoardDTO boardDTO : dtoList) {
                if (boardDTO.getType() == boardTypeDTO.getType()) {
                    boardDTO.setIndex(i);
                    i++;
                    boardDTOS.add(boardDTO);
                }
            }
            boardTypeDTO.setBoards(boardDTOS);
        }
        log.info("dtoList size : " + dtoList.size());

        model.addAttribute("dtoList", dtoList);
        model.addAttribute("types", boardTypeDTOs);
        model.addAttribute("cate", cate);

        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(Model model ,int bno) {
        BoardDTO boardDTO = csService.findByBnoForBoard(bno);
        model.addAttribute("boardDTO", boardDTO);
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
    public String noticeView(Model model ,int bno, String cate) {
        BoardDTO boardDTO = csService.findByBnoForBoard(bno);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("cate", cate);
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
    public String qnaView(Model model, int bno, String cate) {

        BoardDTO boardDTO = csService.findByBnoForBoard(bno);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("cate", cate);
        log.info("boardDTO : " + boardDTO);
        return "/cs/qna/view";
    }
    @GetMapping("/cs/qna/fileDownload")
    public ResponseEntity<Resource> fileDownloadApi(int fno) throws IOException {
        log.info("fno : " + fno);
        BoardFileEntity sfile = csService.getSfileByFno(fno);
        if (sfile != null) {
            //return csService.fileDownload(sfile.getSfile(),sfile.getOfile());
            return csService.fileDownload(sfile.toDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
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
        dto.setStatus("검토중");
        csService.save(dto);
        return "redirect:/cs/qna/list?group=qna&cate=member&success=200";
    }

    // cate
    @GetMapping("/cs/cate")
    @ResponseBody
    public List<BoardTypeEntity> csCate(String optionValue){

        log.info(optionValue);

        return csCateService.findByCate(optionValue);

    }

    @GetMapping("/cs/qna/modify")
    public String qnaModify(Model model, int bno) {

        try{
            BoardDTO boardDTO = csService.findByBnoForBoard(bno);
            model.addAttribute("boardDTO", boardDTO);
        }catch(Exception e){
            log.info(e.getMessage());
        }

        return "/cs/qna/modify";
    }


    @PostMapping("/cs/qna/modify")
    public String qnaModify(HttpServletRequest request, @RequestParam int bno, BoardDTO dto){
        log.info(dto.toString());
        csService.update(bno, dto);
        return "redirect:/cs/qna/list?success=300";


    }

    @GetMapping("/cs/qna/delete")
    public String qnaDelete(@RequestParam int bno){
        log.info("Delete QnA bno : "+bno);
        csService.delete(bno);
        return "redirect:/cs/qna/list?group=qna&cate=member&success=301";

    }





}
