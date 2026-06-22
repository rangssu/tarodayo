package com.taro.tarodayo;

import com.taro.tarodayo.domain.TarotCard;
import com.taro.tarodayo.repository.TarotCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final TarotCardRepository tarotCardRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (tarotCardRepository.count() > 0) return;

        tarotCardRepository.save(new TarotCard(0, "바보", "The Fool",
                "새로운 시작, 자유, 순수, 모험, 가능성",
                "무모함, 경솔함, 준비 부족, 방황",
                "fool.jpg", "🌬", "0"));
        tarotCardRepository.save(new TarotCard(1, "마법사", "The Magician",
                "의지력, 능력, 창의성, 집중, 실현",
                "속임수, 재능 낭비, 조작, 불신",
                "magician.jpg", "⚗️", "I"));
        tarotCardRepository.save(new TarotCard(2, "여사제", "The High Priestess",
                "직관, 신비, 내면의 지식, 잠재의식",
                "비밀, 억압된 직관, 혼란, 단절",
                "high_priestess.jpg", "🌙", "II"));
        tarotCardRepository.save(new TarotCard(3, "여황제", "The Empress",
                "풍요, 모성, 창조, 자연, 번영",
                "의존성, 창의력 부족, 불임, 과보호",
                "empress.jpg", "🌹", "III"));
        tarotCardRepository.save(new TarotCard(4, "황제", "The Emperor",
                "권위, 안정, 구조, 리더십, 보호",
                "독재, 경직성, 통제, 지배",
                "emperor.jpg", "👑", "IV"));
        tarotCardRepository.save(new TarotCard(5, "교황", "The Hierophant",
                "전통, 믿음, 제도, 도덕, 스승",
                "반항, 고집, 편협함, 혁신 거부",
                "hierophant.jpg", "✙", "V"));
        tarotCardRepository.save(new TarotCard(6, "연인", "The Lovers",
                "사랑, 조화, 선택, 관계, 가치관",
                "불화, 불균형, 잘못된 선택, 갈등",
                "lovers.jpg", "♡", "VI"));
        tarotCardRepository.save(new TarotCard(7, "전차", "The Chariot",
                "승리, 의지, 통제, 결단, 전진",
                "방향 상실, 자기 통제 부족, 패배",
                "chariot.jpg", "⚔", "VII"));
        tarotCardRepository.save(new TarotCard(8, "힘", "Strength",
                "용기, 인내, 자기 통제, 내면의 힘",
                "나약함, 자기 의심, 분노, 방종",
                "strength.jpg", "🦁", "VIII"));
        tarotCardRepository.save(new TarotCard(9, "은둔자", "The Hermit",
                "내면 탐구, 고독, 지혜, 성찰, 안내",
                "고립, 외로움, 세상과 단절, 거절",
                "hermit.jpg", "🏮", "IX"));
        tarotCardRepository.save(new TarotCard(10, "운명의 수레바퀴", "Wheel of Fortune",
                "변화, 행운, 순환, 운명, 전환점",
                "불운, 저항, 외부 통제, 혼돈",
                "wheel_of_fortune.jpg", "☸", "X"));
        tarotCardRepository.save(new TarotCard(11, "정의", "Justice",
                "공정, 진실, 원인과 결과, 명확함",
                "불공정, 부정직, 불균형, 책임 회피",
                "justice.jpg", "⚖", "XI"));
        tarotCardRepository.save(new TarotCard(12, "매달린 사람", "The Hanged Man",
                "희생, 새로운 관점, 수용, 기다림",
                "지연, 저항, 순교, 무의미한 희생",
                "hanged_man.jpg", "🔮", "XII"));
        tarotCardRepository.save(new TarotCard(13, "죽음", "Death",
                "변환, 끝과 시작, 전환, 변화",
                "저항, 변화 거부, 정체, 무기력",
                "death.jpg", "🥀", "XIII"));
        tarotCardRepository.save(new TarotCard(14, "절제", "Temperance",
                "균형, 인내, 조화, 목적, 절제",
                "불균형, 과잉, 부조화, 서두름",
                "temperance.jpg", "⚗", "XIV"));
        tarotCardRepository.save(new TarotCard(15, "악마", "The Devil",
                "속박, 집착, 물질주의, 어둠, 욕망",
                "해방, 독립, 속박에서 벗어남, 각성",
                "devil.jpg", "🜏", "XV"));
        tarotCardRepository.save(new TarotCard(16, "탑", "The Tower",
                "갑작스러운 변화, 혼란, 계시, 해방",
                "재난 회피, 두려움, 위기 지연",
                "tower.jpg", "⚡", "XVI"));
        tarotCardRepository.save(new TarotCard(17, "별", "The Star",
                "희망, 영감, 평온, 신뢰, 재생",
                "절망, 낙심, 불신, 단절",
                "star.jpg", "✦", "XVII"));
        tarotCardRepository.save(new TarotCard(18, "달", "The Moon",
                "환상, 두려움, 무의식, 혼란, 직관",
                "혼란 해소, 진실 드러남, 공포 극복",
                "moon.jpg", "☽", "XVIII"));
        tarotCardRepository.save(new TarotCard(19, "태양", "The Sun",
                "성공, 활력, 기쁨, 긍정, 명확함",
                "일시적 우울, 비관, 자아도취, 지연",
                "sun.jpg", "☀", "XIX"));
        tarotCardRepository.save(new TarotCard(20, "심판", "Judgement",
                "각성, 재생, 내면의 부름, 용서",
                "자기 의심, 후회, 변화 거부",
                "judgement.jpg", "🎺", "XX"));
        tarotCardRepository.save(new TarotCard(21, "세계", "The World",
                "완성, 통합, 성취, 여행, 완전함",
                "미완성, 정체, 지연, 폐쇄적",
                "world.jpg", "🌍", "XXI"));
    }
}
