package co.kr.lotte.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/index")
    public String index() {
        return ("/admin/index");
    }
    // admin-product
    @GetMapping("/admin/product/list")
    public String pro_list() {
        return ("/admin/product/list");
    }
    @GetMapping("/admin/product/register")
    public String pro_register() {
        return ("/admin/product/register");
    }
    // admin-cs-notice
    @GetMapping("admin/cs/notice/list")
    public String cs_no_list() {
        return ("admin/cs/notice/list");
    }
    @GetMapping("admin/cs/notice/view")
    public String cs_no_view() {
        return ("admin/cs/notice/view");
    }
    @GetMapping("admin/cs/notice/write")
    public String cs_no_write() {
        return ("admin/cs/notice/write");
    }
    @GetMapping("admin/cs/notice/modify")
    public String cs_no_modify() {
        return ("admin/cs/notice/modify");
    }
    // admin-cs-faq
    @GetMapping("admin/cs/faq/list")
    public String cs_faq_list(){
        return ("admin/cs/faq/list");
    }
    @GetMapping("admin/cs/faq/view")
    public String cs_faq_view(){
        return ("admin/cs/faq/view");
    }
    @GetMapping("admin/cs/faq/write")
    public String cs_faq_write(){
        return ("admin/cs/faq/write");
    }
    @GetMapping("admin/cs/faq/modify")
    public String cs_faq_modify(){
        return ("admin/cs/faq/modify");
    }
    // admin-cs-qna
    @GetMapping("admin/cs/qna/list")
    public String cs_qna_list(){
        return ("admin/cs/qna/list");
    }
    @GetMapping("admin/cs/qna/reply")
    public String cs_qna_reply(){
        return ("admin/cs/qna/reply");
    }
    @GetMapping("admin/cs/qna/view")
    public String cs_qna_view(){
        return ("admin/cs/qna/view");
    }

}
