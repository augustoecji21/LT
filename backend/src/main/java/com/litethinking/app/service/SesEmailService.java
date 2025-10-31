package com.litethinking.app.service;

public interface SesEmailService {
    void enviarInventarioPdfPorEmpresa(String nit, String toEmail, String subject, String fromEmail);
}
