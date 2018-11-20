package com.br.AvaliacaoBruno.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Photo {
    private Long id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
