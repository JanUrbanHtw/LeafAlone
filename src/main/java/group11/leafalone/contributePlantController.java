package group11.leafalone;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@Controller
public class contributePlantController {
    @GetMapping("/contributePlant")
    public String contributePlantForm(Model model) {
        model.addAttribute("plantdescription", new PlantDescription());
        return "contributePlant";
    }

    @PostMapping("/contributePlant")
    public String contributePlantSubmit(@ModelAttribute PlantDescription plantdescription) {
        return "aboutUs";
    }

    @PostMapping("/savefile")
    public String upload(@RequestParam CommonsMultipartFile file, HttpSession session){
       String path=session.getServletContext().getRealPath("/");
       String filename=file.getOriginalFilename();
//
//        System.out.println(path+" "+filename);
        try{
            byte barr[]=file.getBytes();

            BufferedOutputStream bout=new BufferedOutputStream(
                    new FileOutputStream(path+"/"+filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        }catch(Exception e){System.out.println(e);}
//        return new ModelAndView("upload-success","filename",path+"/"+filename);

        return "contributePlant";
    }
}
