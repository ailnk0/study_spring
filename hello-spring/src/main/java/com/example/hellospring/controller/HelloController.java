package com.example.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /**
     * localhost:8080/hello-page
     * controller가 viewResolver를 호출한다.
     * resources/templates/{$TEMPLATE_NAME}.html에게 model(data:hello!!)를 전달한다.
     *
     * @param model model
     * @return {$TEMPLATE_NAME}
     */
    @GetMapping("hello-page")
    public String hello(Model model) {
        model.addAttribute("data", "World!");
        return "hello";
    }

    /**
     * localhost:8080/hello-mvc?name={$PARAM_DATA}
     * controller가 viewResolver를 호출한다.
     * resources/templates/{$TEMPLATE_NAME}.html에게 model(data:{$PARAM_DATA})를 전달한다.
     *
     * @param name  {$PARAM_DATA}
     * @param model model
     * @return {$TEMPLATE_NAME}
     */
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("data", name);
        return "hello";
    }

    /**
     * localhost:8080/hello-string?name={$PARAM_DATA}
     * ResponseBody가 있으면 viewResolver를 호출하지 않는다.
     * 대신 return 값을 그대로 반환한다.
     * 해당 페이지에 접속하면 hello {$PARAM_DATA}를 보여준다.
     *
     * @param name {$PARAM_DATA}
     * @return {$TEMPLATE_NAME}
     */
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    /**
     * localhost:8080/hello-api?name={$PARAM_DATA}
     * ResponseBody가 있으면 viewResolver를 호출하지 않는다.
     * 대신 return 값을 그대로 반환한다.
     * 반환 타입이 객체이므로 HttpMessageConvert에서 JsonConverter를 사용해
     * 객체를 Json으로 변환해서 반환한다.
     * 기본값으로 MappingJackson2HttpMessageConverter를 사용할 것이다.
     * HelloData 객체의 경우 json으로 {"name":"{$PARAM_DATA}"}을 반환할 것이다.
     *
     * @param name {$PARAM_DATA}
     * @return {$TEMPLATE_NAME}
     */
    @GetMapping("hello-api")
    @ResponseBody
    public HelloData helloApi(@RequestParam("name") String name) {
        HelloData hello = new HelloData();
        hello.setName(name);
        return hello;
    }

    public static class HelloData {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
