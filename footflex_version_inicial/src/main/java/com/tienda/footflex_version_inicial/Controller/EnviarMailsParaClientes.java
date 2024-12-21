
package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EnviarMailsParaClientes {

    @Autowired
    private MailService mailService;

    @GetMapping("")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        return "";
    }

    @PostMapping("")
    public String sendMail(@RequestParam("name") String name, @RequestParam("mail") String mail,
            @RequestParam("subject") String subject, @RequestParam("body") String body,
            @RequestParam("recipient") String recipient) {

        String message = body + "\n\n Datos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
        // Utiliza el destinatario del formulario
        mailService.sendMail("footflexsnkr@gmail.com", recipient, subject, message);

        return "";
    }
    
}
