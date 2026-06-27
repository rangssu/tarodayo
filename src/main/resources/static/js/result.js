let interpretationFullText = '';

function playRevealSound() {
    try {
        const ctx = new (window.AudioContext || window.webkitAudioContext)();
        [392, 523, 659, 784, 1047].forEach((freq, i) => {
            const osc  = ctx.createOscillator();
            const gain = ctx.createGain();
            osc.connect(gain);
            gain.connect(ctx.destination);
            osc.type = 'sine';
            osc.frequency.value = freq;
            const t = ctx.currentTime + i * 0.11;
            gain.gain.setValueAtTime(0.08, t);
            gain.gain.exponentialRampToValueAtTime(0.001, t + 0.5);
            osc.start(t);
            osc.stop(t + 0.55);
        });
    } catch (e) {}
}

function copyReading() {
    const question = document.getElementById('resultQuestion')?.textContent || '';

    const cardLines = [];
    document.querySelectorAll('.result-card-slot').forEach(slot => {
        const position  = slot.querySelector('.position-label')?.textContent || '';
        const nameKo    = slot.querySelector('img')?.alt || '';
        const direction = slot.querySelector('.result-card-direction')?.textContent || '';
        const keywords  = slot.querySelector('.result-keywords')?.textContent || '';
        cardLines.push(`- ${position}: ${nameKo} (${direction}) — ${keywords}`);
    });

    const text = [
        '[타로다요 리딩 결과]',
        '',
        `질문: "${question}"`,
        '',
        '🃏 카드:',
        ...cardLines,
        '',
        '🔮 AI 해석:',
        interpretationFullText
    ].join('\n');

    const btn = document.getElementById('copyBtn');
    const originalText = btn?.textContent || '';

    function onSuccess() {
        if (btn) {
            btn.textContent = '복사됨! ✓';
            setTimeout(() => { btn.textContent = originalText; }, 2000);
        }
    }

    function fallbackCopy() {
        const ta = document.createElement('textarea');
        ta.value = text;
        ta.style.cssText = 'position:fixed;top:0;left:0;opacity:0;pointer-events:none;';
        document.body.appendChild(ta);
        ta.focus();
        ta.select();
        try {
            document.execCommand('copy');
            onSuccess();
        } catch (e) {
            if (btn) btn.textContent = '복사 실패 — 직접 선택해주세요';
            setTimeout(() => { if (btn) btn.textContent = originalText; }, 2500);
        }
        document.body.removeChild(ta);
    }

    if (navigator.clipboard && navigator.clipboard.writeText) {
        navigator.clipboard.writeText(text).then(onSuccess).catch(fallbackCopy);
    } else {
        fallbackCopy();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    setTimeout(playRevealSound, 150);

    const textEl = document.querySelector('.interpretation-text');
    if (!textEl) return;

    interpretationFullText = textEl.textContent;
    textEl.textContent = '';

    let i = 0;
    const speed = 18;

    function type() {
        if (i < interpretationFullText.length) {
            textEl.textContent += interpretationFullText[i++];
            setTimeout(type, speed);
        }
    }

    setTimeout(type, 950);
});
