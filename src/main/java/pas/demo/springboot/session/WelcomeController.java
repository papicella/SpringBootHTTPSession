package pas.demo.springboot.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController
{
    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model, HttpSession session)
    {
        List<String> names = (List<String>) session.getAttribute("nameList");

        if (names == null)
        {
            names = new ArrayList<String>();
            session.setAttribute("nameList", names);
        }
        else
        {
            model.addAttribute("nameList", names);
            model.addAttribute("namesSize", names.size());
        }

        return "welcome";
    }

    @RequestMapping(value="/addName", method = RequestMethod.POST)
    public String addName
            (@RequestParam(value="name") String name,
             Model model,
             HttpSession session)
    {

        List<String> names = (List<String>) session.getAttribute("nameList");

        names.add(name);

        session.setAttribute("nameList", names);

        model.addAttribute("nameList", names);
        model.addAttribute("namesSize", names.size());

        return "welcome";
    }

    @RequestMapping(value = "/killme", method = RequestMethod.GET)
    public String killme(Model model)
    {
        Runtime.getRuntime().halt(-1);
        return "welcome";
    }
}
