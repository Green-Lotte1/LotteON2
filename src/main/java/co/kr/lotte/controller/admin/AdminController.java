package co.kr.lotte.controller.admin;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;

import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.entity.product.ProductCate1Entity;
import co.kr.lotte.entity.product.ProductCate2Entity;
import co.kr.lotte.repository.product.ProductRepository;
import co.kr.lotte.security.MyUserDetails;
import co.kr.lotte.service.CateService;
import co.kr.lotte.service.CsCateService;
import co.kr.lotte.service.CsService;
import co.kr.lotte.service.admin.AdminService;
import co.kr.lotte.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CateService cateService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CsCateService csCateService;
    @Autowired
    private CsService csService;
    @Autowired
    private CsPageRequestDTO csPageRequestDTO;

    @GetMapping("/admin/index")
    public String index() {
        return ("/admin/index");
    }

    // admin-product
    @GetMapping("/admin/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO pageResponseDTO = productService.findByAll(pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/admin/product/list";
    }

    @GetMapping("/admin/product/delete")
    public String deleteSelectedProducts(@RequestParam("chk") List<Integer> prodNos) {
        int deletedCount = adminService.deleteByProdNo(prodNos);

        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String pro_register(HttpServletRequest request, Model model) {
        List<ProductCate1Entity> cates1 = productService.getCate1();
        model.addAttribute("cates1",cates1);
        log.info("cates1 : "+ cates1);

        return ("/admin/product/register");
    }

    @PostMapping("/admin/product/register")
    public String pro_register3(HttpServletRequest request, ProductDTO dto) {
        dto.setIp(request.getRemoteAddr());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String sellerId = userDetails.getMember().getName();
            dto.setSeller(sellerId);
        }

        log.info("dto: " + dto);
        adminService.save(dto);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/registerCate2")
    @ResponseBody
    public List<ProductCate2Entity> pro_register2(int cate1){

        log.info("cate1 : " + cate1);
        List<ProductCate2Entity> productCate2Entities = productService.findByCate1(cate1);
        log.info("size : " + productCate2Entities.size());
        return productCate2Entities;
    }

    // admin-cs-notice
    @GetMapping("admin/cs/notice/list")
    public String cs_notice_list(HttpServletRequest request, Model model, String cate, CsPageRequestDTO csPageRequestDTO) {
        List<BoardCateEntity> cates = csCateService.getCate();
        model.addAttribute("cates", cates);
        log.info("cates : "+cates);

        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        model.addAttribute("csPageResponseDTO", csPageResponseDTO);
        return "/admin/cs/notice/list";
    }

    @GetMapping("admin/cs/notice/view")
    public String cs_no_view(Model model ,int bno) {
        BoardDTO boardDTO = csService.findByBnoForAdmin(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/admin/cs/notice/view";
    }
    @GetMapping("admin/cs/notice/write")
    public String cs_no_write(Model model, String cate, CsPageRequestDTO csPageRequestDTO) {
        List<BoardCateEntity> cates = csCateService.getCate();
        model.addAttribute("cates", cates);
        log.info("cates : " + cates);

        BoardDTO boardDTO = new BoardDTO();
        model.addAttribute("boardDTO", boardDTO);

        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        model.addAttribute("csPageResponseDTO", csPageResponseDTO);
        return "/admin/cs/notice/write";
    }

    @PostMapping("/admin/cs/notice/write")
    public String submitNoticeForm(BoardDTO boardDTO) {
        Integer type = boardDTO.getType();
        String cate = boardDTO.getCate();
        csService.saveNotice(boardDTO, type, cate);
        return "redirect:/admin/cs/notice/list?group=notice&cate=null";
    }

    @GetMapping("/admin/cs/notice/modify")
    public String cs_no_modify(@RequestParam int bno, Model model) {
        BoardDTO boardDTO = csService.findByBnoForBoard(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/admin/cs/notice/modify";
    }

    @PostMapping("/admin/cs/notice/modify")
    public String cs_no_modify(@RequestParam int bno, @ModelAttribute BoardDTO boardDTO) {
        String title = boardDTO.getTitle();
        String cate = boardDTO.getCate();
        csService.update(bno, boardDTO);
        return "redirect:/admin/cs/notice/list?group=notice&cate=null";
    }

    // admin-cs-faq
    @GetMapping("admin/cs/faq/list")
    public String cs_faq_list(HttpServletRequest request, Model model, String cate, CsPageRequestDTO csPageRequestDTO) {
        List<BoardCateEntity> cates = csCateService.getCate();
        model.addAttribute("cates", cates);
        log.info("cates : "+cates);

        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        model.addAttribute("csPageResponseDTO", csPageResponseDTO);
        return "/admin/cs/faq/list";
    }

    @GetMapping("admin/cs/faq/view")
    public String cs_faq_view(Model model ,int bno) {
        BoardDTO boardDTO = csService.findByBnoForAdmin(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/admin/cs/faq/view";
    }
    @GetMapping("admin/cs/faq/write")
    public String cs_faq_write(Model model, String cate, CsPageRequestDTO csPageRequestDTO) {
        List<BoardCateEntity> cates = csCateService.getCate();
        model.addAttribute("cates", cates);
        log.info("cates : " + cates);

        BoardDTO boardDTO = new BoardDTO();
        model.addAttribute("boardDTO", boardDTO);

        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        model.addAttribute("csPageResponseDTO", csPageResponseDTO);
        return "/admin/cs/faq/write";
    }

    @PostMapping("/admin/cs/faq/write")
    public String cs_faq_modify(BoardDTO boardDTO) {
        Integer type = boardDTO.getType();
        String cate = boardDTO.getCate();
        csService.saveNotice(boardDTO, type, cate);
        return "redirect:/admin/cs/faq/list?group=notice&cate=null";
    }

    @GetMapping("/admin/cs/faq/modify")
    public String cs_faq_modify(@RequestParam int bno, Model model) {
        BoardDTO boardDTO = csService.findByBnoForBoard(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/admin/cs/faq/modify";
    }

    @PostMapping("/admin/cs/faq/modify")
    public String cs_notice_modify(@RequestParam int bno, @ModelAttribute BoardDTO boardDTO) {
        String title = boardDTO.getTitle();
        String cate = boardDTO.getCate();
        csService.update(bno, boardDTO);
        return "redirect:/admin/cs/faq/list?group=faq&cate=null";
    }


    // admin-cs-qna
    @GetMapping("admin/cs/qna/list")
    public String cs_qna_list(HttpServletRequest request, Model model, String cate, CsPageRequestDTO csPageRequestDTO){
        List<BoardCateEntity> cates = csCateService.getCate();
        model.addAttribute("cates", cates);
        log.info("cates : "+cates);

        CsPageResponseDTO csPageResponseDTO = csService.findByCate(csPageRequestDTO);
        model.addAttribute("cate", csPageRequestDTO.getCate());
        model.addAttribute("csPageResponseDTO", csPageResponseDTO);
        return ("/admin/cs/qna/list");
    }

    @GetMapping("/admin/cs/qna/cate")
    @ResponseBody
    public List<BoardTypeEntity> csCate(String optionValue) {
        log.info(optionValue);
        List<BoardTypeEntity> boardTypeEntities = csCateService.findByCate(optionValue);
        return boardTypeEntities;
    }

    @PostMapping("/admin/cs/qna/reply")
    public String cs_qna_reply(@RequestParam("bno") int bno, @RequestParam("content") String content) {
        BoardDTO boardDTO = csService.findByBnoForBoard(bno);

        if (boardDTO != null) {
            boardDTO.setStatus("답변완료");
            csService.updateStatusAndReply(bno, boardDTO);
            csService.updateReply(bno, content);
        }
        return "redirect:/admin/cs/qna/list?group=qna&cate=null";
    }

    @GetMapping("admin/cs/qna/view")
    public String cs_qna_view(Model model, int bno){
        BoardDTO boardDTO = csService.findByBnoForAdmin(bno);
        model.addAttribute("boardDTO", boardDTO);
        log.info("boardDTO : " + boardDTO);
        return ("/admin/cs/qna/view");
    }

    //CS-Delete 삭제
    @GetMapping({"/admin/cs/qna/delete", "/admin/cs/notice/delete", "/admin/cs/faq/delete"})
    public String deleteSelectedBnos(@RequestParam("chk") List<Integer> bnos, HttpServletRequest request) {
        int deletedCount = adminService.deleteByBno(bnos);

        String mapping = request.getRequestURI();
        if (mapping.contains("/qna")) {
            return "redirect:/admin/cs/qna/list?group=qna&cate=null";
        } else if (mapping.contains("/notice")) {
            return "redirect:/admin/cs/notice/list?group=notice&cate=null";
        } else if (mapping.contains("/faq")) {
            return "redirect:/admin/cs/faq/list?group=faq&cate=null";
        }
        return "redirect:/";
    }
}
