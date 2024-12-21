package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ContactanosController {

    @Autowired
    private MailService mailService;

    @GetMapping("/Mail")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "/tienda/send_mail_view";
    }

    /**
     * Coge los datos guardados del formulario y se envia un correo desde el
     * correo de footflex para footflex, para hacer las consultas de los
     * clientes, rellenando con la información del cliente
     *
     * @param name
     * @param mail
     * @param subject
     * @param body
     * @return Vuelve a mostrar la pagina de contactanos
     */
    @PostMapping("/sendMail")
    public String sendMail(@RequestParam("name") String name, @RequestParam("mail") String mail,
            @RequestParam("subject") String subject, @RequestParam("body") String body, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        try {
            String message = body + "\n\nDatos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
            // Utiliza el destinatario del formulario
            mailService.sendMail("footflexsnkr@gmail.com", "footflexsnkr@gmail.com", subject, message);

            return "/tienda/send_mail_view";
        } catch (Exception e) {
            model.addAttribute("error", "Error al enviar el correo. Por favor, inténtalo de nuevo.");
            return "/tienda/send_mail_view";
        }
    }

    // Manejo de excepciones personalizado
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", "Ocurrió un error inesperado. Por favor, inténtalo de nuevo.");
        return "/tienda/send_mail_view";
    }
}
