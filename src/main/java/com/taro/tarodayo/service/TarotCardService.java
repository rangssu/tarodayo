package com.taro.tarodayo.service;

import com.taro.tarodayo.domain.TarotCard;
import com.taro.tarodayo.dto.DrawnCard;
import com.taro.tarodayo.repository.TarotCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TarotCardService {

    private final TarotCardRepository tarotCardRepository;
    private final Random random = new Random();

    public List<DrawnCard> drawFiveCards() {
        List<TarotCard> allCards = tarotCardRepository.findAll();
        Collections.shuffle(allCards);

        List<DrawnCard> drawn = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            boolean reversed = random.nextBoolean();
            drawn.add(new DrawnCard(allCards.get(i), reversed));
        }
        return drawn;
    }
}
