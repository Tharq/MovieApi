package com.bharani.movie.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
public record MailBody(String to,String subject,String text) {
}
