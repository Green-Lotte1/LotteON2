package co.kr.lotte.controller.company;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Log4j2
@Controller
public class CompanyController {

    @GetMapping(value = {"/company", "/company/introduce"})
    public String index() {
      return ("/company/introduce");
    }

    @GetMapping(value = {"/company/notice"})
    public String notice() {
        return ("/company/notice");
    }

    @GetMapping(value = { "/company/promote"})
    public String promote() {
        return ("/company/promote");
    }

    @GetMapping(value = { "/company/manage"})
    public String manage() {
        return ("/company/manage");
    }



}


