package com.lagou.edu.controller;

import com.lagou.edu.pojo.Car.Car;
import com.lagou.edu.pojo.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/demo")
public class DemoController {

    // 编写在Controller里的的异常处理器，只作用于该Controller
//    @ExceptionHandler(ArithmeticException.class)
//    public ModelAndView handleException(ArithmeticException ex, HttpServletResponse response){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("msg",ex.getMessage());
//
//        return modelAndView;
//    }

    @RequestMapping("/handle01")
    public ModelAndView handle01(){
        Date date =  new Date();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        System.out.println(date.toString());
        return modelAndView;
    }

    /**
     * 默认⽀持 Servlet API 作为⽅法参数
     * http://localhost:8080/demo/handle02?id=1
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/handle02")
    public ModelAndView handle02(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        String id = request.getParameter("id");
        System.out.println(id);
        Date date =  new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    // 绑定简单类型
    // http://localhost:8080/demo/handle03?id=1
    @RequestMapping("/handle03")
    public ModelAndView handle03(Integer id){
        System.out.println(id);
        Date date =  new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    // 绑定POJO
    // http://localhost:8080/demo/handle04?age=12&name=zhangsan
    @RequestMapping("/handle04")
    public ModelAndView handle04(User user){
        System.out.println(user.toString());
        Date date =  new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    // POJO包装类型
    // http://localhost:8080/demo/handle05?user.age=12&user.name=zhangsan
    @RequestMapping("/handle05")
    public ModelAndView handle05(Car car){
        System.out.println(car.toString());
        Date date =  new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    // 绑定日期类型
    @RequestMapping("/handle06")
    public ModelAndView handle06(Date date){
        System.out.println(date.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    // ResponseBody RequestBody
    @RequestMapping("/handle07")
    public @ResponseBody User handle07(@RequestBody User user){
        user.setName("张三丰");

        return user;
    }

    // restful风格
    @RequestMapping(value = "/handle08/{name}/{age}",method = {RequestMethod.GET})
    public String handle08(@PathVariable("name") String name, @PathVariable("age")Integer age){
        System.out.println(age);
        return "success";
    }

    // 文件上传
    @RequestMapping(value = "/upload")
    public String upload(MultipartFile uploadFile,HttpServletRequest request) throws IOException {
        // ⽂件原名，如xxx.jpg
        String originalFilename = uploadFile.getOriginalFilename();
        // 获取⽂件的扩展名,如jpg
        String extendName =
                originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());

        String uuid = UUID.randomUUID().toString();
        // 新的⽂件名字
        String newName = uuid + "." + extendName;
        String realPath =
                request.getSession().getServletContext().getRealPath("/uploads");

        // 解决⽂件夹存放⽂件数量限制，按⽇期存放
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File floder = new File(realPath + "/" + datePath);
        if(!floder.exists()) {
            floder.mkdirs();
        }
        uploadFile.transferTo(new File(floder,newName));
        return "success";
    }

    // 测试handle异常解析器
    @RequestMapping(value = "/handle09")
    public String handle09(){
        int i = 3/0;
        return "success";
    }

    // 重定向基于flash属性传参数
    @RequestMapping("/handle10")
    public String handle10(String id , RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("id",id);
        return "redirect:handle03";
    }

    // 重定向
    @RequestMapping("/handle11")
    public void handle11(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getParameter("id"));
        // sendRedirect("url") 没有参数传递
        response.sendRedirect("http://localhost:8080/demo/handle03");
    }

    // 转发
    @RequestMapping("/handle12")
    public void handle12(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(request.getParameter("id"));
        // 转发的url是：demo/handle03
        request.getRequestDispatcher("handle03").forward(request,response);
    }
}
