package com.mpellicc.pokedex.dto.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FunTranslationsDto {

    @JsonProperty("contents")
    private Content contents;

    @Getter
    @Setter
    @Builder(toBuilder = true)
    public static class Content {
        @JsonProperty("translation")
        private String translation;

        @JsonProperty("text")
        private String text;

        @JsonProperty("translated")
        private String translated;

        public static boolean isNull(Content obj) {
            return obj == null || ObjectUtils.allNull(obj.translated, obj.text, obj.translated);
        }
    }
}
