package com.taro.tarodayo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TarotCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cardNumber;
    private String nameKo;
    private String nameEn;

    @Column(length = 500)
    private String uprightKeywords;

    @Column(length = 500)
    private String reversedKeywords;

    private String imageName;

    private String symbol;
    private String romanNumeral;

    public TarotCard(int cardNumber, String nameKo, String nameEn,
                     String uprightKeywords, String reversedKeywords, String imageName,
                     String symbol, String romanNumeral) {
        this.cardNumber = cardNumber;
        this.nameKo = nameKo;
        this.nameEn = nameEn;
        this.uprightKeywords = uprightKeywords;
        this.reversedKeywords = reversedKeywords;
        this.imageName = imageName;
        this.symbol = symbol;
        this.romanNumeral = romanNumeral;
    }
}
